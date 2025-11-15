/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author dungc
 */
public class ImportInvoice implements Serializable {
    private int id;
    private Date date;
    private ArrayList<ImportedProduct> list;
    private Staff staff;
    private Supplier supplier;
    
    public ImportInvoice(){}
    public ImportInvoice(Date date, ArrayList<ImportedProduct> list, Staff staff, Supplier supplier){
        this.date = date;
        this.list = list;
        this.staff = staff;
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public ArrayList<ImportedProduct> getList() {
        return list;
    }

    public Staff getStaff() {
        return staff;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setList(ArrayList<ImportedProduct> list) {
        this.list = list;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    
}
