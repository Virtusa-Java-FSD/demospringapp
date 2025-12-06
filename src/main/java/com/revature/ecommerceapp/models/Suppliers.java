package com.revature.ecommerceapp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="suppliers")
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplier_id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Products> products = new ArrayList<>();

    public Suppliers(Long supplier_id, String name, String email, List<Products> products) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.email = email;
        this.products = products;
    }

    public Suppliers(){}

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
