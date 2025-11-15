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
public class Customer extends Member implements Serializable{
    private String customerId;
    
    public Customer(){
        super();
    }
    public Customer(String customerId, String username, String password, String name, String address, Date birth, String email, String tel){
        super(username, password,name,address,birth,email,tel);
        this.customerId = customerId;
        
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
    
    
    
}
