package com.essers.wms.movement.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
public class Movement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;
    private String wmsCompany;
    private String wmsSite;
    private String wmsWarehouse;
    private Movementtype movementType;
    private String priority;
    private String productId;
    private String supplierId;
    private Integer quantity;
    private String uom;
    private String locationFrom;
    private String locationTo;
    private LocalDateTime inProgressTimestamp;
    private String inProgressUser;
    private String location;
    private State state;
    private String handledUser;
    private String palleteNummer;
    @Valid
    @ManyToOne
    private Pickinglist pickinglist;
    @ManyToMany
    private List<Stock> stock = new ArrayList<>();
    public Movement(Long movementId, String wmsCompany, String wmsSite, String wmsWarehouse,
                    Movementtype movementType, String supplierId) {
        this.movementId = movementId;
        this.wmsCompany = wmsCompany;
        this.wmsSite = wmsSite;
        this.wmsWarehouse = wmsWarehouse;
        this.movementType = movementType;
        this.supplierId = supplierId;
    }

    public Movement() {

    }
    public String getSupplierId() {
        return supplierId;
    }

    public Long getMovementId() {
        return movementId;
    }

    public String getWmsCompany() {
        return wmsCompany;
    }

    public void setWmsCompany(String wmsCompany) {
        this.wmsCompany = wmsCompany;
    }

    public String getWmsSite() {
        return wmsSite;
    }

    public void setWmsSite(String wmsSite) {
        this.wmsSite = wmsSite;
    }

    public String getWmsWarehouse() {
        return wmsWarehouse;
    }

    public void setWmsWarehouse(String wmsWarehouse) {
        this.wmsWarehouse = wmsWarehouse;
    }

    public Movementtype getMovementType() {
        return movementType;
    }

    public void setMovementType(Movementtype movementType) {
        this.movementType = movementType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public LocalDateTime getInProgressTimestamp() {
        return inProgressTimestamp;
    }

    public void setInProgressTimestamp(LocalDateTime inProgressTimestamp) {
        this.inProgressTimestamp = inProgressTimestamp;
    }

    public String getInProgressUser() {
        return inProgressUser;
    }

    public void setInProgressUser(String inProgressUser) {
        this.inProgressUser = inProgressUser;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getHandledUser() {
        return handledUser;
    }

    public void setHandledUser(String handledUser) {
        this.handledUser = handledUser;
    }

    public String getPalleteNummer() {
        return palleteNummer;
    }

    public void setPalleteNummer(String palleteNummer) {
        this.palleteNummer = palleteNummer;
    }

    public Pickinglist getPickinglist() {
        return pickinglist;
    }

    public void setPickinglist(Pickinglist pickinglist) {
        this.pickinglist = pickinglist;
    }

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

}