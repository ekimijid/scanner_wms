package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;

public interface PickingListService {
    List<Pickinglist> getAll();

    Pickinglist getById(Long id);

    List<Pickinglist> getByCompany(Company company);

    void save(Pickinglist pickinglist);
}
