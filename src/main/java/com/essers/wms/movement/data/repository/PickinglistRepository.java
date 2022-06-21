package com.essers.wms.movement.data.repository;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickinglistRepository extends JpaRepository<Pickinglist, Long> {
    List<Pickinglist> getPickinglistsByCompany(Company company);
    Pickinglist findPickinglistByPickingListId(Long id);
}
