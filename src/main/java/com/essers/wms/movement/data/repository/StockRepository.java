package com.essers.wms.movement.data.repository;

import com.essers.wms.movement.data.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findStockById(Long id);

}
