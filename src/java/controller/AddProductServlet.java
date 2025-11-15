/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.Product;

@WebServlet(name = "AddProductServlet", urlPatterns = { "/AddProductServlet" })
public class AddProductServlet extends HttpServlet {
    private ProductDAO prdao;

    @Override
    public void init() {
        prdao = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select a category");
            request.getRequestDispatcher("Storekeeper/AddProductView/AddProductView.jsp").forward(request, response);
            return;
        }
        String[] C = category.split("\\|");
        if (C.length < 3) {
            request.setAttribute("errorMessage", "Invalid category value");
            request.getRequestDispatcher("Storekeeper/AddProductView/AddProductView.jsp").forward(request, response);
            return;
        }
        Category c = new Category(C[1], C[2]);
        int catgoryId;
        try {
            catgoryId = Integer.parseInt(C[0]);
        } catch (NumberFormatException nfe) {
            request.setAttribute("errorMessage", "Invalid category id");
            request.getRequestDispatcher("Storekeeper/AddProductView/AddProductView.jsp").forward(request, response);
            return;
        }
        c.setId(catgoryId);
        try {
            float unitPrice = Float.parseFloat(request.getParameter("unitprice"));
            Product p = new Product(name, unitPrice, description, c);

            if (prdao.addNewProduct(p)) {
                session.setAttribute("toastMessage", "Create new product successfully");
                session.setAttribute("toastType", "success");
                response.sendRedirect(request.getContextPath() + "/SearchProductServlet");
            } else {
                request.setAttribute("errorMessage", "An error occurred while adding the new product");
                request.getRequestDispatcher("Storekeeper/AddProductView/AddProductView.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unit price must be real number");
            request.getRequestDispatcher("Storekeeper/AddProductView/AddProductView.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("Storekeeper/AddProductView/AddProductView.jsp").forward(request, response);
    }

}
