package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.UserDAO;
import util.DataAccessException;
import util.InputValidator;

 @WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!InputValidator.isValidUsername(username) || !InputValidator.isValidPassword(password)) {
            response.sendRedirect("error.jsp?msg=Invalid username or password format");
            return;
        }

        try {
            UserDAO dao = new UserDAO();

            if (dao.validateUser(username, password)) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("error.jsp?msg=Invalid credentials");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?msg=A database error occurred.");
        }
    }
}