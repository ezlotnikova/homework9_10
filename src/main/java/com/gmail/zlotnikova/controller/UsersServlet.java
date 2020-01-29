package com.gmail.zlotnikova.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.zlotnikova.service.TableService;
import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.impl.TableServiceImpl;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;
import com.gmail.zlotnikova.service.model.UserDTO;

public class UsersServlet extends HttpServlet {

    private static final String USER_LIST_JSP = "/WEB-INF/pages/users.jsp";
    private UserService userService = UserServiceImpl.getInstance();
    private TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void init() {
        tableService.deleteAllTables();
        tableService.createAllTables();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<UserDTO> userDTOList = userService.findAll();
        req.setAttribute("users", userDTOList);
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(USER_LIST_JSP);
        requestDispatcher.forward(req, resp);
    }

}