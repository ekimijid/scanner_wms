package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getAll();

    Stock getById(Long id);
}
