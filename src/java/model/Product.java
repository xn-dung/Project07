/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.Serializable;
import model.Category;
/**
 *
 * @author dungc
 */
public class Product implements Serializable{
    private int id;
    private String name;
    private float unitPrice;
    private String description;
    private Category category;
    
    public Product(){
        
    }
    public Product(String name, float unitPrice, String description, Category category){
        this.name = name;
        this.unitPrice = unitPrice;
        this.description = description;
        this.category = category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getUnitPrice() {
        return unitPrice;
    }
    
}
