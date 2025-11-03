package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;
import model.UserDAO;
import model.UserFactory;
import util.DataAccessException;
import util.InputValidator;

 @WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!InputValidator.isValidUsername(username)) {
            response.sendRedirect("error.jsp?msg=Invalid username format");
            return;
        }

        if (!InputValidator.isValidPassword(password)) {
            response.sendRedirect("error.jsp?msg=Password must be at least 8 characters long");
            return;
        }

        try {
            User user = UserFactory.createUser(username, password);
            UserDAO dao = new UserDAO();

            if (dao.saveUser(user)) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("error.jsp?msg=Registration failed");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?msg=A database error occurred.");
        }
    }
}