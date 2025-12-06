package com.revature.ecommerceapp.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;
    private Date orderdate;

    @ManyToOne()
    @JoinColumn(name="productid")
    private Products product;

}
