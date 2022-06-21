package com.essers.wms.movement;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Movementtype;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.entity.Role;
import com.essers.wms.movement.data.entity.Site;
import com.essers.wms.movement.data.entity.State;
import com.essers.wms.movement.data.entity.Stock;
import com.essers.wms.movement.data.entity.Supplier;
import com.essers.wms.movement.data.entity.User;
import com.essers.wms.movement.data.entity.Warehouse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TestDataBuilder {
    private Pickinglist pickinglist;
    private Company company;
    private Warehouse warehouse;
    private Movement movement;
    private Product product;
    private Role role;
    private Site site;
    private Stock stock;
    private Supplier supplier;
    private User user;
    private List<Product> products = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();
    private List<Movement> movements = new ArrayList<>();
    private List<Pickinglist> pickinglists = new ArrayList<>();
    private List<Company> companies = new ArrayList<>();
    private Damagereport damagereport;

    public TestDataBuilder() {
        this.pickinglist = new Pickinglist();

        this.company = new Company();
        this.company.setName("PXL");

        companies.add(company);

        this.warehouse = new Warehouse();
        this.warehouse.setName("Bloc B");

        this.product = new Product();
        this.product.setPickinglist(pickinglist);
        this.product.setLocation("AB/1010");
        this.product.setName("product1");
        this.product.setDescription("Dit is product1");
        products.add(this.product);

        this.site = new Site();
        this.site.setName("PXL");

        this.stock = new Stock();
        this.stock.setProductId(this.product.getproductId());
        this.stock.setQuantity(2);
        this.stock.setLocation(this.pickinglist.getLocation());


        this.user = new User();
        this.user.setUserName("mijenk");
        this.user.setPassword("user");

        this.role = new Role();
        this.role.setName("user");
        this.role.setName(this.user.getUserName());

        this.pickinglist.setLocation("BB/1234");
        this.pickinglist.setProduct(this.products);
        this.pickinglist.setCompany(this.company);
        this.pickinglist.setWmsSite(this.site);
        this.pickinglist.setPickingListId(1L);
        this.pickinglist.setWmsWarehouse(this.warehouse);

        this.movement = new Movement();
        this.movement.setInProgressUser(this.user.getUserName());
        this.movement.setState(State.PICK);
        this.movement.setInProgressTimestamp(LocalDateTime.now());
        this.movement.setPalleteNummer("0000");
        this.movement.setLocation("1111");
        this.movement.setQuantity(2);
        this.movement.setPickinglist(this.pickinglist);
        this.movement.setLocationFrom(this.product.getLocation());
        this.movement.setLocationTo(this.pickinglist.getLocation());
        this.movement.setWmsCompany(this.company.getName());
        this.movement.setMovementType(Movementtype.FP);
        this.movement.setStock(this.stocks);
        this.movements.add(this.movement);

        this.pickinglist.setMovements(this.movements);
        pickinglists.add(pickinglist);

    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Pickinglist> getPickinglists() {
        return pickinglists;
    }

    public void setPickinglists(List<Pickinglist> pickinglists) {
        this.pickinglists = pickinglists;
    }

    public Pickinglist getPickinglist() {
        return pickinglist;
    }

    public void setPickinglist(Pickinglist pickinglist) {
        this.pickinglist = pickinglist;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public Damagereport getDamagereport() {
        return damagereport;
    }

    public void setDamagereport(Damagereport damagereport) {
        this.damagereport = damagereport;
    }
}
