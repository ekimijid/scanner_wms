package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Stock;
import com.essers.wms.movement.data.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImplementation implements StockService {


    private StockRepository stockRepository;

    @Autowired
    public StockServiceImplementation(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getById(Long id) {
        return stockRepository.findStockById(id);
    }
}
