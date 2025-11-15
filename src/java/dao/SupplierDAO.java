/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import model.Supplier;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author dungc
 */
public class SupplierDAO extends DAO {
    public SupplierDAO(){
        super();
    }
    public ArrayList<Supplier> searchSupplierByName(String name){
        
        String sql = "SELECT * FROM tblSupplier WHERE name like ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            
            ResultSet rs = ps.executeQuery();
            
            ArrayList<Supplier> su = new ArrayList<>();
            while(rs.next()){
                Supplier s = new Supplier();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAddress(rs.getString("address"));
                s.setEmail(rs.getString("email"));
                s.setTel(rs.getString("tel"));
                su.add(s);
            }
            return su;
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
        
    }
    public boolean addNewSupplier(Supplier su){
        String sql = "INSERT INTO tblSupplier(name,address,email,tel) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,su.getName());
            ps.setString(2,su.getAddress());
            ps.setString(3,su.getEmail());
            ps.setString(4,su.getTel());
            
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
