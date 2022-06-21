package com.essers.wms.movement.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
public class Damagereport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productID;
    private String movementID;
    private LocalDateTime timestamp;

    @Lob
    @Column(name = "picByte", length = 1000)
    private byte[] image;

    public Damagereport(Long id, String productName, String productID, String movementID, LocalDateTime timestamp) {
        this.id = id;
        this.productName = productName;
        this.productID = productID;
        this.movementID = movementID;
        this.timestamp = timestamp;
    }

    public Damagereport() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getMovementID() {
        return movementID;
    }

    public void setMovementID(String movementID) {
        this.movementID = movementID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getImage() {
        return image.clone();
    }

    public void setImage(byte[] image) {
        this.image = image.clone();
    }
}
