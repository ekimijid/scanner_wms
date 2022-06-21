package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.repository.PickinglistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class PickingListServiceImplementation implements PickingListService, Serializable {


    private transient PickinglistRepository pickinglistRepository;

    @Autowired
    public PickingListServiceImplementation(PickinglistRepository pickinglistRepository) {
        this.pickinglistRepository = pickinglistRepository;
    }

    @Override
    public List<Pickinglist> getAll() {
        return pickinglistRepository.findAll();

    }

    @Override
    public Pickinglist getById(Long id) {
        return pickinglistRepository.findPickinglistByPickingListId(id);
    }

    @Override
    public List<Pickinglist> getByCompany(Company company) {
        return pickinglistRepository.getPickinglistsByCompany(company);
    }
    @Override
    public void save(Pickinglist pickinglist) {
        pickinglistRepository.save(pickinglist);
    }
}
