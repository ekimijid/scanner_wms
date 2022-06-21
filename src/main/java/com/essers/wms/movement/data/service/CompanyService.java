package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;

import java.util.List;


public interface CompanyService {
    List<Company> getAll();

    Company getById(Long id);
}
