/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mitchell
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();
        RoleService rs = new RoleService();
        String action = request.getParameter("action");

        try{
            if (action != null && action.equals("edit")) {
                String email = request.getParameter("email");
                User selectUser = us.get(email);
                request.setAttribute("selectUser", selectUser);
                request.setAttribute("email", selectUser.getEmail());
            } else if (action != null && action.equals("delete")) {
                String email = request.getParameter("email");
                us.delete(email);
                List<User> users = us.getAll();
                if (users.isEmpty()) {
                    request.setAttribute("message", "No users found.");
                }
            }

            List<User> users = us.getAll();
            request.setAttribute("users", users);

            List<Role> roles = rs.getAll();
            request.setAttribute("roles", roles);

        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        Role newRole = new Role(request.getParameter("role"));
        String action = request.getParameter("action");

        try {
            if (action.equals("add")) {
                us.insert(email, firstname, lastname, password, newRole);
            } else if (action.equals("edit")) {
                us.update(email, firstname, lastname, password, newRole);
            }

            List<User> users = us.getAll();
            request.setAttribute("users", users);

        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
