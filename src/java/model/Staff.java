/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.*;
import java.io.Serializable;
/**
 *
 * @author dungc
 */
public class Staff extends Member implements Serializable{
    private String role;
    
    public Staff(){
        super();
    }
    
    public Staff(String role,String username, String password, String name, String address, Date birth, String email, String tel){
        super(username,password, name, address, birth,email,tel);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}