/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.ImportInvoice;
import model.ImportedProduct;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Product;
/**
 *
 * @author dungc
 */
public class ImportInvoiceDAO extends DAO{
    public ImportInvoiceDAO(){
        super();
    }
    
    public boolean createNewImportInvoice(ImportInvoice i){
        boolean check = true;
        String sql = "INSERT INTO tblImportInvoice(date,tblStaffId,tblSupplierId) VALUES (?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(1,sdf.format(i.getDate()));
            ps.setInt(2,i.getStaff().getId());
            ps.setInt(3,i.getSupplier().getId());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                i.setId(rs.getInt(1));
                for(ImportedProduct ip : i.getList()){
                    String sql2 = "INSERT INTO tblImportedProduct(quantity,unitPrice,tblImportInvoiceId,tblProductId) VALUES (?,?,?,?)";
                    try{
                        PreparedStatement ps2 = con.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
                        ps2.setFloat(1, ip.getQuantity());
                        ps2.setFloat(2, ip.getUnitPrice());
                        ps2.setInt(3, i.getId());
                        ps2.setInt(4, ip.getProduct().getId());
                        
                        ps2.executeUpdate();
                        
                        ResultSet rs2 = ps2.getGeneratedKeys();
                        if(rs2.next()){
                            ip.setId(rs2.getInt(1));
                        }
                        else check = false;
                    }catch(Exception e){
                        e.printStackTrace();
                        check = false;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    
    
}
