package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import dao.CustomerDAO;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@WebServlet(name = "RegisterSerlvet", urlPatterns = {"/RegisterServlet"})
public class RegisterSerlvet extends HttpServlet {
    private Customer cus;
    private CustomerDAO cusDAO;
    
    @Override
    public void init(){
        cus = new Customer();
        cusDAO = new CustomerDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birth = sdf.parse(request.getParameter("birth"));
            
            
            String email = request.getParameter("email");
            String tel = request.getParameter("tel");
            String customerId = request.getParameter("customerId");
            
            if(!checkSo(customerId)){
                
                request.setAttribute("errorMessage","ID Card must contain all numbers");
                request.getRequestDispatcher("Customer/RegisterView/RegisterView.jsp").forward(request, response);
                return;
            }
            if(!checkSo(tel)){
                request.setAttribute("errorMessage","Phone number must contain all numbers");
                request.getRequestDispatcher("Customer/RegisterView/RegisterView.jsp").forward(request, response);
                return;
            }
            LocalDate birthDate = birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!birthDate.isBefore(LocalDate.now())) {
                request.setAttribute("errorMessage", "Birth date must be before today");
                request.getRequestDispatcher("Customer/RegisterView/RegisterView.jsp").forward(request, response);
                return;
            }
            cus = new Customer(customerId,username,password,name,address,birth,email,tel);
            if(cusDAO.addNewCustomer(cus)){
                HttpSession session = request.getSession();
                session.setAttribute("toastMessage","Register sucessfully");
                session.setAttribute("toastType","success");
                response.sendRedirect(request.getContextPath() + "/LoginView/LoginView.jsp");
            }
            else{
                request.setAttribute("errorMessage","Username is already used");
                request.getRequestDispatcher("Customer/RegisterView/RegisterView.jsp").forward(request, response);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage","An error occurred during registration");
            request.getRequestDispatcher("Customer/RegisterView/RegisterView.jsp").forward(request,response);
        }
          
        
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
