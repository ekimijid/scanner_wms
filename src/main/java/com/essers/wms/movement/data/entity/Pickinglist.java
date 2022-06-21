package com.essers.wms.movement.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Pickinglist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickingListId;

    @OneToOne
    private Company company;

    @OneToOne
    private Site wmsSite;

    @OneToOne
    private Warehouse wmsWarehouse;

    @OneToMany(mappedBy = "pickinglist")
    private List<Product> product = new ArrayList<>();

    @OneToOne
    private Supplier supplierId;

    private Integer quantity;
    private String uom;
    private String location;

    @OneToMany(mappedBy = "pickinglist")
    private List<Movement> movements;

    public Pickinglist(Long pickingListId, String uom, String location) {
        this.pickingListId = pickingListId;
        this.uom = uom;
        this.location = location;
    }

    public Pickinglist() {

    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Site getWmsSite() {
        return wmsSite;
    }

    public void setWmsSite(Site wmsSite) {
        this.wmsSite = wmsSite;
    }

    public Warehouse getWmsWarehouse() {
        return wmsWarehouse;
    }

    public void setWmsWarehouse(Warehouse wmsWarehouse) {
        this.wmsWarehouse = wmsWarehouse;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }
}