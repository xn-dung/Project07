
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.StaffDAO;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import model.Staff;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
    private StaffDAO staffDAO;
    private Staff staff;
    
    @Override
    public void init(){
        staffDAO = new StaffDAO();
        staff = new Staff();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        staff.setUsername(username);
        staff.setPassword(password);
        
        try{
            if(staffDAO.checkLogin(staff)){
                HttpSession session = request.getSession();
                Enumeration<String> attributes = session.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String attrName = attributes.nextElement();
                    session.removeAttribute(attrName);
                }
                session.setAttribute("staff", staff);
                response.sendRedirect(request.getContextPath() + "/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp");
                
            }
            else{
                request.setAttribute("errorMessage","Wrong username or password or you may be not a storekeeper");
                request.getRequestDispatcher("LoginView/LoginView.jsp").forward(request,response);
                
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    

}
