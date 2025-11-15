/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Supplier;

/**
 *
 * @author dungc
 */
@WebServlet(name = "AddSupplierServlet", urlPatterns = {"/AddSupplierServlet"})
public class AddSupplierServlet extends HttpServlet {
    private SupplierDAO sudao;
    @Override
    public void init(){
        sudao = new SupplierDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        
        Supplier s = new Supplier(name,address,email,tel);
        if(!checkSo(tel)){
                
            request.setAttribute("errorMessage","Phone must contain all numbers");
            request.getRequestDispatcher("Storekeeper/AddSupplierView/AddSupplierView.jsp").forward(request, response);
            return;
        }
        if(sudao.addNewSupplier(s)){
            session.setAttribute("toastMessage","Create new supplier successfully");
            session.setAttribute("toastType","success");
            response.sendRedirect(request.getContextPath() + "/Storekeeper/SearchSupplierView/SearchSupplierView.jsp");
        }
        else{
            request.setAttribute("errorMessage","An error occurred while adding the new supplier");
            request.getRequestDispatcher("Storekeeper/AddSupplierView/AddSupplierView.jsp").forward(request, response);
        }
        
    }
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
           
        request.getRequestDispatcher("Storekeeper/AddSupplierView/AddSupplierView.jsp").forward(request, response);
    }
    private boolean checkSo(String s){
        for(int i = 0; i < s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
