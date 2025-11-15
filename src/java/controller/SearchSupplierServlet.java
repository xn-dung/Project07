package controller;
import dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import model.Supplier;

@WebServlet(name = "SearchSupplierServlet", urlPatterns = {"/SearchSupplierServlet"})
public class SearchSupplierServlet extends HttpServlet{
    private SupplierDAO sudao;
    private Supplier s;
    
    @Override
    public void init(){
        sudao = new SupplierDAO();
        s = new Supplier();
    }
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        
        s = new Supplier(name,address,email,tel);
        s.setId(id);
        
        session.setAttribute("supplier", s);
        response.sendRedirect(request.getContextPath() + "/SearchProductServlet");
    }
    
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
           HttpSession session = request.getSession();
           String name = request.getParameter("supplierName");
           ArrayList<Supplier>list = new ArrayList<>();
           if(request.getParameter("supplierName") != null){
              list = sudao.searchSupplierByName(name);
           }
           session.setAttribute("listSupplier",list);
           request.getRequestDispatcher("Storekeeper/SearchSupplierView/SearchSupplierView.jsp").forward(request, response);
    }
    
}