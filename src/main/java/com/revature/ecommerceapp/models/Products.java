package com.revature.ecommerceapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid;

    private String name;
    private String color;

    private double price;

    @ManyToOne(targetEntity = Suppliers.class)
    private Suppliers supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Orders> orders;


}
