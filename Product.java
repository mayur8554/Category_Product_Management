package com.project.CatProduct;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Product_id")
    int productId;
    @Column(name="ProductName")
    String productName;
    @Column(name="price")
    double price;
   @ManyToOne(cascade= CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId") // Assuming your Category entity uses categoryId as primary key
    private Category category;
    public Product() { 
    }
    public Product(String productName, double price, Category category) {
        super();
        
        this.productName = productName;
        this.price = price;
        this.category = category;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

}
