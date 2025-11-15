/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author dungc
 */
@WebServlet(name = "SearchProductServlet", urlPatterns = { "/SearchProductServlet" })
public class SearchProductServlet extends HttpServlet {
    private ProductDAO prdao;

    @Override
    public void init() {
        prdao = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ArrayList<String> selectedIds = (ArrayList<String>) session.getAttribute("selectedProducts");
        if (selectedIds == null) {
            selectedIds = new ArrayList<>();
        }

        String productId = request.getParameter("productId");
        String checked = request.getParameter("checked");

        if (productId != null && checked != null) {
            if ("true".equals(checked)) {
                if (!selectedIds.contains(productId)) {
                    selectedIds.add(productId);
                }
            } else {
                selectedIds.remove(productId);
            }
            session.setAttribute("selectedProducts", selectedIds);

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = request.getParameter("productName");
        ArrayList<Product> list = new ArrayList<>();
        HashSet<Category> listCa = new HashSet<>();
        list = prdao.searchProductByName("");
        for (Product x : list) {
            listCa.add(x.getCategory());
        }
        session.setAttribute("categories", listCa);
        if(request.getParameter("productName") != null){
            list = prdao.searchProductByName(name);
        }
        session.setAttribute("listProduct", list);
        ArrayList<String> selectedIds = (ArrayList<String>) session.getAttribute("selectedProducts");
        if (selectedIds == null) {
            selectedIds = new ArrayList<>();
            session.setAttribute("selectedProducts", selectedIds);
        }
        request.getRequestDispatcher("Storekeeper/SearchProductView/SearchProductView.jsp").forward(request, response);
    }
}
