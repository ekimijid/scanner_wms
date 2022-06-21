package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Product;

public interface ProductService {
    Product getByID(String id);
}
