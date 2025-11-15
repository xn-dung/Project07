/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Staff;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author dungc
 */
public class StaffDAO extends DAO {
    public StaffDAO(){
        super();
    }

    public boolean checkLogin(Staff staff) {
        String sql = "SELECT * FROM tblMember WHERE username = ? AND password = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, staff.getUsername());
            ps.setString(2,staff.getPassword());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setAddress(rs.getString("address"));
                staff.setEmail(rs.getString("email"));
                staff.setTel(rs.getString("tel"));
                staff.setBirth(rs.getDate("birth"));
                
                String sql2 = "SELECT role FROM tblStaff WHERE tblMemberid = ?";
                try{
                   PreparedStatement ps2 = con.prepareStatement(sql2);
                   ps2.setInt(1, staff.getId());
                   
                   ResultSet rs2 = ps2.executeQuery();
                   if(rs2.next()){
                        staff.setRole(rs2.getString("role"));
                        if(!staff.getRole().equals("storekeeper")){
                           return false;
                        }else return true;
                   }
                   else return false;
                    
                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            else{
                return false;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
}
