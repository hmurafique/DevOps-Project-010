package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Registration Success</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0;");
        out.println("display: flex; justify-content: center; align-items: center;");
        out.println("height: 100vh; margin: 0; }");
        out.println(".container { background-color: white; padding: 40px;");
        out.println("border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #4CAF50; }");
        out.println("</style></head>");
        out.println("<body><div class='container'>");
        out.println("<h2>Registration Successful!</h2>");
        out.println("<p><strong>Username:</strong> " + username + "</p>");
        out.println("<p><strong>Email:</strong> " + email + "</p>");
        out.println("<a href='index.jsp'>Go Back</a>");
        out.println("</div></body></html>");
    }
}
