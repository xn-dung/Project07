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
public class Member implements Serializable{
    private int id;
    private String username;
    private String password;
    private String name;
    private String address;
    private Date birth;
    private String email;
    private String tel;
    
    public Member(){}
    public Member(String username, String password, String name, String address,Date birth,String email, String tel){
        this.username = username;
        this.password = password;
        this.address = address;
        this.birth = birth;
        this.email = email;
        this.tel = tel;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    
    
    
    
}
