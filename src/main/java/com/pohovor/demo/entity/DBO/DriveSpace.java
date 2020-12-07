package com.pohovor.demo.entity.DBO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DriveSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "totalSpaceInBytes")
    private Long totalSpaceInBytes;

    @Column(name = "freeSpaceInBytes")
    private Long freeSpaceInBytes;

    @Column(name = "percentageAvailable")
    private int percentageAvailable;

    @Column(name = "timeCreated")
    private LocalDateTime timeCreated;

    public void setPercentageAvailable(int percentageAvailable) {
        this.percentageAvailable = percentageAvailable;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setTotalSpaceInBytes(Long totalSpaceInBytes) {
        this.totalSpaceInBytes = totalSpaceInBytes;
    }

    public void setFreeSpaceInBytes(Long freeSpaceInBytes) {
        this.freeSpaceInBytes = freeSpaceInBytes;
    }
}
