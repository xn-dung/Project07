
package controller;

import dao.ImportInvoiceDAO;
import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import model.ImportInvoice;
import model.ImportedProduct;
import model.Product;
import model.Staff;
import model.Supplier;

/**
 *
 * @author dungc
 */
@WebServlet(name = "ImportInvoiceServlet", urlPatterns = {"/ImportInvoiceServlet"})
public class ImportInvoiceServlet extends HttpServlet {
    private ImportInvoiceDAO imdao;
    private ProductDAO prdao;
    private ImportInvoice i;
    
    private List<Product> getSelectedProduct(HttpSession session) {
        List<String> selectedIds = (List<String>) session.getAttribute("selectedProducts");
        List<Product> list = new ArrayList<>();

        if (selectedIds != null && !selectedIds.isEmpty()) {
            for (String id : selectedIds) {
                try {
                    int productId = Integer.parseInt(id);
                    Product b = prdao.getProductByID(productId);
                    if (b != null) {
                        list.add(b);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    
    @Override
    public void init(){
        imdao = new ImportInvoiceDAO();
        i = new ImportInvoice();
        prdao = new ProductDAO();
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession();
        Staff staff = (Staff)session.getAttribute("staff");
        Supplier supplier = (Supplier)session.getAttribute("supplier");
        
        List<Product> products = getSelectedProduct(session);
        String[] quantities = request.getParameterValues("quantity");
        String[] unitPrices = request.getParameterValues("unitPrice");
        
        if(staff != null && products != null && quantities != null && unitPrices != null){
            ArrayList<ImportedProduct> listIP = new ArrayList<>();
            for(int i = 0; i < products.size(); i++){
                try{
                    float quantity = Float.parseFloat(quantities[i]);
                    float unitPrice = Float.parseFloat(unitPrices[i]);
                    
                    if(quantity > 0 && unitPrice >= 0){
                        ImportedProduct ip = new ImportedProduct(quantity, unitPrice, products.get(i));
                        listIP.add(ip);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    session.setAttribute("toastMessage","The quantity or unit price must be real number");
                    session.setAttribute("toastType","warning");
                    doGet(request,response);
                }
            }
            if(listIP.size() == products.size()){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date today = sdf.parse(sdf.format(new Date()));
                    i = new ImportInvoice(today, listIP, staff, supplier);
                    if(imdao.createNewImportInvoice(i)){
                        Enumeration<String> attributes = session.getAttributeNames();
                        while (attributes.hasMoreElements()) {
                            String attrName = attributes.nextElement();
                            session.removeAttribute(attrName);
                        }
                        session.setAttribute("staff", staff);
                        session.setAttribute("toastMessage","Create an import invoice successfully");
                        session.setAttribute("toastType","success");
                        session.setAttribute("importinvoice", i);
                        response.sendRedirect(request.getContextPath() + "/PrintInvoiceServlet");
                    }
                    else{
                        request.setAttribute("errorMessage","An error occurred while creating an import invoice");
                        request.getRequestDispatcher("Storekeeper/ImportInvoiceView/ImportInvoiceView.jsp").forward(request, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                
            }
            else{
                request.getRequestDispatcher("Storekeeper/ImportInvoiceView/ImportInvoiceView.jsp").forward(request, response);
            }
        }
       
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession();
        List<Product> list = getSelectedProduct(session);
        list.sort((s1, s2) -> Integer.compare(s1.getId(), s2.getId()));
        session.setAttribute("importedProducts",list);
        request.getRequestDispatcher("ImportInvoice/ImportInvoice.jsp").forward(request, response);
        
    }
    

}
