package org.abol.springstarter.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BaseUser user;
}