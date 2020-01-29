package com.gmail.zlotnikova.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.zlotnikova.service.TableService;
import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.helper.validator.IntegerValidator;
import com.gmail.zlotnikova.service.helper.validator.LengthValidator;
import com.gmail.zlotnikova.service.helper.validator.MayBeEmptyValidator;
import com.gmail.zlotnikova.service.helper.validator.NotEmptyValidator;
import com.gmail.zlotnikova.service.helper.validator.PatternValidator;
import com.gmail.zlotnikova.service.helper.validator.Validator;
import com.gmail.zlotnikova.service.helper.validator.ValidatorImpl;
import com.gmail.zlotnikova.service.impl.TableServiceImpl;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;
import com.gmail.zlotnikova.service.model.UserDTO;
import com.gmail.zlotnikova.util.ConvertUtil;

public class NewUserServlet extends HttpServlet {

    private static final String ADD_SUCCESS_JSP = "/WEB-INF/pages/add-success.jsp";
    private static final String ADD_FAIL_JSP = "/WEB-INF/pages/add-fail.jsp";
    private static final String ADD_FAIL_INVALID_DATA_JSP = "/WEB-INF/pages/add-fail-invalid-data.jsp";
    private UserService userService = UserServiceImpl.getInstance();
    private TableService tableService = TableServiceImpl.getInstance();
    private ConvertUtil convertUtil = ConvertUtil.getInstance();

    @Override
    public void init() {
        tableService.deleteAllTables();
        tableService.createAllTables();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        boolean valid = validate(req);
        if (!valid) {
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(ADD_FAIL_INVALID_DATA_JSP);
            requestDispatcher.forward(req, resp);
        } else {
            UserDTO userDTO = createUserDTO(req);
            UserDTO newUserDTO = userService.add(userDTO);
            if (newUserDTO.getId() != null) {
                ServletContext servletContext = getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(ADD_SUCCESS_JSP);
                requestDispatcher.forward(req, resp);
            } else {
                ServletContext servletContext = getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(ADD_FAIL_JSP);
                requestDispatcher.forward(req, resp);
            }
        }
    }

    private boolean validate(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String age = req.getParameter("age");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        return validateUsername(username) &&
                validatePassword(password) &&
                validateAge(age) &&
                validateAddress(address) &&
                validateTelephone(telephone);
    }

    private boolean validateUsername(String username) {
        int maxUsernameLength = 40;
        Validator validator = new NotEmptyValidator(new LengthValidator(new ValidatorImpl(), maxUsernameLength));
        return validator.validate(username);
    }

    private boolean validatePassword(String password) {
        int maxPasswordLength = 40;
        Validator validator = new NotEmptyValidator(new LengthValidator(new ValidatorImpl(), maxPasswordLength));
        return validator.validate(password);
    }

    private boolean validateAge(String age) {
        Validator validator = new NotEmptyValidator(new IntegerValidator(new ValidatorImpl()));
        return validator.validate(age);
    }

    private boolean validateAddress(String address) {
        int maxAddressLength = 100;
        Validator validator = new MayBeEmptyValidator(new LengthValidator(new ValidatorImpl(), maxAddressLength));
        return validator.validate(address);
    }

    private boolean validateTelephone(String telephone) {
        int maxTelephoneLength = 40;
        String telephonePattern = "[0-9()\\s+-]+";
        Validator validator = new MayBeEmptyValidator(new LengthValidator(
                new PatternValidator(new ValidatorImpl(), telephonePattern), maxTelephoneLength));
        return validator.validate(telephone);
    }

    private UserDTO createUserDTO(HttpServletRequest req) {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Boolean isActive = convertUtil.parseBoolean(req.getParameter("isActive"));
        Integer age = Integer.parseInt(req.getParameter("age"));
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setTelephone(telephone);
        userDTO.setAddress(address);
        userDTO.setActive(isActive);
        userDTO.setAge(age);

        return userDTO;
    }

}