package com.essers.wms.movement.data.repository;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> getMovementsByPickinglist(Pickinglist pickinglist);
    Movement findMovementByMovementId(Long id);
}
