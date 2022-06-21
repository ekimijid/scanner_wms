package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;

public interface MovementService {
    List<Movement> getAll();
    Movement getById(Long id);
    List<Movement>getByPickinglist(Pickinglist pickinglist);
    void remove(Movement movement);

    void save(Movement movement);
}
