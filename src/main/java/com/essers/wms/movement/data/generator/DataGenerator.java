package com.essers.wms.movement.data.generator;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Movementtype;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.entity.Role;
import com.essers.wms.movement.data.entity.Site;
import com.essers.wms.movement.data.entity.State;
import com.essers.wms.movement.data.entity.Stock;
import com.essers.wms.movement.data.entity.User;
import com.essers.wms.movement.data.entity.Warehouse;
import com.essers.wms.movement.data.repository.CompanyRepository;
import com.essers.wms.movement.data.repository.MovementRepository;
import com.essers.wms.movement.data.repository.PickinglistRepository;
import com.essers.wms.movement.data.repository.ProductRepository;
import com.essers.wms.movement.data.repository.RoleRepository;
import com.essers.wms.movement.data.repository.SiteRepository;
import com.essers.wms.movement.data.repository.StockRepository;
import com.essers.wms.movement.data.repository.UserRepository;
import com.essers.wms.movement.data.repository.WarehouseRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {
    private static final int NUMBER_OF_CYCLES = 40;
    private static final int SEED = 123;
    private static final int RANDOM_GETAL = 9;
    private static final int STOCK = 2;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private PickinglistRepository pickinglistRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            createUser();
            List<Product> products = createProduct(productRepository);
            List<Company> companies = createCompany(companyRepository);
            List<Warehouse> warehouses = creatWarehouse();
            List<Site> sites = siteRepository.saveAll(Stream.of("Winterslag", "Genk").map(Site::new).toList());
            List<Pickinglist> pickinglists = createPickinglist(products, companies, companyRepository, sites,
                    siteRepository, warehouses, warehouseRepository);
            createStocks(pickinglists);
        };
    }

    private void createUser() {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("Admin");
        Role role1 = new Role();
        role.setName("User");
        roles.add(role);
        roles.add(role1);
        roleRepository.saveAll(roles);
        User user = new User();
        user.setUserName("eki");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(roles);
        userRepository.save(user);
        User user2 = new User();
        user2.setUserName("mijenk");
        user2.setPassword(passwordEncoder.encode("user"));
        user2.setRoles(roles);
        userRepository.save(user2);
    }

    private static Movement createMovement(Pickinglist pl, Product p) {
        Movement movement = new Movement();
        movement.setMovementType(Movementtype.FP);
        movement.setInProgressTimestamp(LocalDateTime.now());
        movement.setWmsWarehouse(pl.getWmsWarehouse().getName());
        movement.setWmsSite(pl.getWmsSite().getName());
        movement.setWmsCompany(pl.getCompany().getName());
        movement.setPickinglist(pl);
        movement.setLocationFrom("Floor.col"+RANDOM_GETAL+".row"+STOCK);
        movement.setLocationTo(pl.getLocation());
        movement.setLocation(pl.getLocation());
        movement.setQuantity(pl.getQuantity());
        movement.setUom(pl.getUom());
        movement.setWmsCompany(pl.getCompany().getName());
        movement.setProductId(p.getproductId());
        movement.setState(State.PICK);
        movement.setPalleteNummer("03600029145");
        return movement;
    }

    private static List<Product> createProduct(ProductRepository productRepository) {
        ExampleDataGenerator<Product> productExampleDataGenerator = new ExampleDataGenerator<>(Product.class,
                LocalDateTime.now());
        productExampleDataGenerator.setData(Product::setProductId, DataType.EAN13);
        productExampleDataGenerator.setData(Product::setName, DataType.FOOD_PRODUCT_EAN);
        productExampleDataGenerator.setData(Product::setLocation, DataType.IBAN);
        productExampleDataGenerator.setData(Product::setDescription, DataType.FOOD_PRODUCT_NAME);
        return productRepository.saveAll(productExampleDataGenerator.create(NUMBER_OF_CYCLES, SEED));
    }

    private static List<Company> createCompany(CompanyRepository companyRepository) {
        ExampleDataGenerator<Company> companyExampleDataGenerator = new ExampleDataGenerator<>(Company.class,
                LocalDateTime.now());
        companyExampleDataGenerator.setData(Company::setName, DataType.COMPANY_NAME);
        return companyRepository.saveAll(companyExampleDataGenerator.create(NUMBER_OF_CYCLES, SEED));
    }

    private static List<Pickinglist> createPickinglist(List<Product> products, List<Company> companies,
                                                       CompanyRepository companyRepository, List<Site> sites,
                                                       SiteRepository siteRepository, List<Warehouse> warehouses,
                                                       WarehouseRepository warehouseRepository) {
        SecureRandom random = new SecureRandom();
        ExampleDataGenerator<Pickinglist> pickinglistGenerator = new ExampleDataGenerator<>(Pickinglist.class,
                LocalDateTime.now());
        pickinglistGenerator.setData(Pickinglist::setQuantity, DataType.NUMBER_UP_TO_10);
        pickinglistGenerator.setData(Pickinglist::setUom, DataType.WORD);
        return pickinglistGenerator.create(NUMBER_OF_CYCLES, SEED).stream().map(plist -> {
            plist.setProduct(random.ints(RANDOM_GETAL, 0, products.size()).mapToObj(products::get).toList());
            plist.setCompany(companies.get(random.nextInt(companyRepository.findAll().size())));
            plist.setWmsSite(sites.get(random.nextInt(siteRepository.findAll().size())));
            plist.setWmsWarehouse(warehouses.get(random.nextInt(warehouseRepository.findAll().size())));
            plist.setLocation("RETARUS123456789");
            return plist;
        }).toList();
    }

    private void createStocks(List<Pickinglist> pickinglists) {
        List<Movement> movements = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        for (Pickinglist pickinglist : pickinglists) {
            Stock s = new Stock();
            Pickinglist pl = pickinglist;
            s.setLocation(pl.getLocation());
            s.setQuantity(STOCK);
            for (Product p : pl.getProduct()) {
                s.setProductId(p.getproductId());
                stocks.add(s);
                movements.add(createMovement(pl, p));
            }
            pl.setMovements(movements);
        }
        for (Movement m : movements) {
            for (Stock s : stockRepository.findAll()) {
                if (m.getProductId().equals(s.getProductId())) {
                    m.getStock().add(s);
                }
                movementRepository.save(m);
            }
        }
        pickinglistRepository.saveAll(pickinglists);
        stockRepository.saveAll(stocks);
        movementRepository.saveAll(movements);
    }

    private List<Warehouse> creatWarehouse() {
        return warehouseRepository.saveAll(Stream.of("WH10", "WH11", "WH12").map(Warehouse::new).toList());
    }
}
