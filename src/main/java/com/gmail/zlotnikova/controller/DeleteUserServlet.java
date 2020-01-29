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
import com.gmail.zlotnikova.service.impl.TableServiceImpl;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;

public class DeleteUserServlet extends HttpServlet {

    private static final String DELETE_SUCCESS_JSP = "/WEB-INF/pages/delete-success.jsp";
    private static final String DELETE_FAIL_JSP = "/WEB-INF/pages/delete-fail.jsp";
    private UserService userService = UserServiceImpl.getInstance();
    private TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void init() {
        tableService.deleteAllTables();
        tableService.createAllTables();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long id = Long.parseLong(req.getParameter("id"));
        int expectedRowsDeleted = 1;
        int rowsDeleted = userService.deleteById(id);
        if (rowsDeleted == expectedRowsDeleted) {
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(DELETE_SUCCESS_JSP);
            requestDispatcher.forward(req, resp);
        } else {
            req.setAttribute("id", id);
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(DELETE_FAIL_JSP);
            requestDispatcher.forward(req, resp);
        }
    }

}