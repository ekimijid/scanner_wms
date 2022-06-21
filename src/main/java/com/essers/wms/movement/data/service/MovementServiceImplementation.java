package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementServiceImplementation implements MovementService {


    private MovementRepository movementRepository;

    @Autowired
    public MovementServiceImplementation(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public List<Movement> getAll() {
        return movementRepository.findAll();
    }

    @Override
    public Movement getById(Long id) {
        return movementRepository.findMovementByMovementId(id);
    }

    @Override
    public List<Movement> getByPickinglist(Pickinglist pickinglist) {
        return movementRepository.getMovementsByPickinglist(pickinglist);
    }

    @Override
    public void remove(Movement movement) {
        movementRepository.delete(movement);
    }

    @Override
    public void save(Movement movement) {
        movementRepository.save(movement);
    }
}
