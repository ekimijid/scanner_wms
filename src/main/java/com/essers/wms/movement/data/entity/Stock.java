package com.essers.wms.movement.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private String location;
    private String productId;

    public Stock(Long id, Integer quantity, String location, String productId) {
        this.id = id;
        this.quantity = quantity;
        this.location = location;
        this.productId = productId;
    }

    public Stock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long movementId) {
        this.id = movementId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
