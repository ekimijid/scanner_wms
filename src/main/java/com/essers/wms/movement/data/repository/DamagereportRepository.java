package com.essers.wms.movement.data.repository;

import com.essers.wms.movement.data.entity.Damagereport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamagereportRepository extends JpaRepository<Damagereport, Long> {
    Damagereport findDamagereportById(Long id);

}
