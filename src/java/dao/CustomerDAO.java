/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Customer;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 *
 * @author dungc
 */
public class CustomerDAO extends DAO {
    public CustomerDAO(){
        super();
    }
    public boolean addNewCustomer(Customer cus){
        String sql = "INSERT INTO tblMember(username,password,name,birth,address,tel,email) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,cus.getUsername());
            ps.setString(2,cus.getPassword());
            ps.setString(3,cus.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(4,sdf.format(cus.getBirth()));
            ps.setString(5,cus.getAddress());
            ps.setString(6,cus.getTel());
            ps.setString(7,cus.getEmail());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                cus.setId(rs.getInt(1));
                String sql2 = "INSERT INTO tblCustomer(customerid,tblMemberid) VALUES (?,?)";
                try{
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setString(1,cus.getCustomerId());
                    ps2.setInt(2,cus.getId());
                    
                    int rs2 = ps2.executeUpdate();
                    if(rs2 != 0){
                        return true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
}
