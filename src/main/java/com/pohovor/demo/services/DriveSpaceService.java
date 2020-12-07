package com.pohovor.demo.services;

import com.pohovor.demo.entity.DBO.DriveSpace;
import com.pohovor.demo.repository.DriveSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveSpaceService {

    private final DriveSpaceRepository driveSpaceRepository;

    public DriveSpaceService(DriveSpaceRepository driveSpaceRepository) {
        this.driveSpaceRepository = driveSpaceRepository;
    }

    public DriveSpace create(DriveSpace driveSpace) {
        return driveSpaceRepository.save(driveSpace);
    }

    public List<DriveSpace> getAll() {
        return (List<DriveSpace>) driveSpaceRepository.findAll();
    }
}
