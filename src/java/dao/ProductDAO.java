/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.util.ArrayList;
import model.Product;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Category;
/**
 *
 * @author dungc
 */
public class ProductDAO extends DAO {
    public ProductDAO(){
        super();
    }
    public ArrayList<Product> searchProductByName(String name){
        
        String sql = "SELECT * FROM tblproduct WHERE name like ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            
            ResultSet rs = ps.executeQuery();
            
            ArrayList<Product> pr = new ArrayList<>();
            while(rs.next()){
                Product s = new Product();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setUnitPrice(rs.getFloat("unitPrice"));
                s.setDescription(rs.getString("description"));
                
                try{
                    String sql2 = "SELECT * FROM tblCategory WHERE id = ?";
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, rs.getInt("tblCategoryId"));
                    
                    ResultSet rs2 = ps2.executeQuery();
                    if(rs2.next())
                    {
                        Category ca = new Category();
                        ca.setId(rs2.getInt("id"));
                        ca.setName(rs2.getString("name"));
                        ca.setNote(rs2.getString("note"));
                        
                        s.setCategory(ca);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                pr.add(s);
            }
            return pr;  
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
        
    }
    public Product getProductByID(int id){
        String sql = "SELECT * FROM tblProduct WHERE id = ?";
	try{
		PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if(rs.next()){
                    Product pr = new Product();
                    pr.setId(rs.getInt("id"));
                    pr.setDescription(rs.getString("description"));
                    pr.setName(rs.getString("name"));
                    pr.setUnitPrice(rs.getFloat("unitPrice"));
                    try{
                    String sql2 = "SELECT * FROM tblCategory WHERE id = ?";
                        PreparedStatement ps2 = con.prepareStatement(sql2);
                        ps2.setInt(1, rs.getInt("tblCategoryId"));

                        ResultSet rs2 = ps2.executeQuery();
                        if(rs2.next())
                        {
                            Category ca = new Category();
                            ca.setId(rs2.getInt("id"));
                            ca.setName(rs2.getString("name"));
                            ca.setNote(rs2.getString("note"));

                            pr.setCategory(ca);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        return null;
                    }
                    
                    return pr;
		}
	}catch(Exception e){
            e.printStackTrace();
	}
        return null;
    }
    public boolean addNewProduct(Product su){
        String sql = "INSERT INTO tblProduct(name,description,unitPrice,tblCategoryId) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,su.getName());
            ps.setString(2,su.getDescription());
            ps.setFloat(3,su.getUnitPrice());
            ps.setInt(4,su.getCategory().getId());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                su.setId(rs.getInt(1));
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
        
    }

    
}
