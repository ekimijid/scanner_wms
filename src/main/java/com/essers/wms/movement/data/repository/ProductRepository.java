package com.essers.wms.movement.data.repository;

import com.essers.wms.movement.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductId(String id);

}
