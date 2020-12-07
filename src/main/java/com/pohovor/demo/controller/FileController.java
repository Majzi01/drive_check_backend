package com.pohovor.demo.controller;

import com.pohovor.demo.entity.DBO.DriveSpace;
import com.pohovor.demo.entity.DTO.SettingsDTO;
import com.pohovor.demo.entity.email.EmailDTO;
import com.pohovor.demo.services.DriveSpaceService;
import com.pohovor.demo.services.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final DriveSpaceService driveSpaceService;
    private final EmailService emailService;

    private int percentageBelow;
    private int driveCheckerRefresh;
    private String email;

    public FileController(DriveSpaceService driveSpaceService, EmailService emailService) {
        this.driveSpaceService = driveSpaceService;
        this.emailService = emailService;
    }

    @GetMapping("/drives")
    public List<String> getAllDrives() {

        List<String> drives = new ArrayList<>();

        File[] paths;

        paths = File.listRoots();

        for (File path: paths) {
            drives.add(path.toString());
        }

        return drives;
    }

    @Scheduled(fixedRate = 50000)
    @GetMapping("/scheduledDriveSpace")
    public void scheduledSaveDriveSpace() {

        long totalSpaceInAllDisks = 0;
        long totalFreeSpaceInAllDisks = 0;

        for (Path root : FileSystems.getDefault().getRootDirectories()) {

            try {
                FileStore store = Files.getFileStore(root);

                long totalSpaceInBytes = store.getTotalSpace();
                long freeSpaceInBytes = store.getUsableSpace();

                totalSpaceInAllDisks += totalSpaceInBytes;
                totalFreeSpaceInAllDisks += freeSpaceInBytes;

            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        int percentage = (int) (totalFreeSpaceInAllDisks * 100 / totalSpaceInAllDisks);

        DriveSpace driveSpace = new DriveSpace();
        driveSpace.setTotalSpaceInBytes(totalSpaceInAllDisks);
        driveSpace.setFreeSpaceInBytes(totalFreeSpaceInAllDisks);
        driveSpace.setPercentageAvailable(percentage);
        driveSpace.setTimeCreated(LocalDateTime.now());
        driveSpaceService.create(driveSpace);

        if (percentage < percentageBelow) {
            if (email != null) {
                EmailDTO emailDTO = new EmailDTO();
                emailDTO.setEmailFrom("test@email.com");
                emailDTO.setEmailTo("test@email.com");
                emailDTO.setSubject("Report");
                emailDTO.setMessage("Celkove volne miesto disku sa dostalo pod " + percentageBelow + "% na hranicu " + percentage + "%\n " +
                        "Aktualne celkove volne miesto: " + (totalFreeSpaceInAllDisks / 1024 / 1024 / 1024) + "GB");
                emailService.sendEmail(emailDTO);
            }
        }
    }

    @PostMapping("/settings")
    public void setPercentageBelow(@RequestBody SettingsDTO settings)
    {
        this.percentageBelow = settings.getPercentageBelow();
        this.driveCheckerRefresh = settings.getDriveCheckerRefresh();
        this.email = settings.getEmail();
    }


    @PostMapping("/path")
    public Map<String,Long> activityList(@RequestBody Map<String, String> json) throws IOException {

        Map<String,Long> driveInfo = new HashMap<>();
        FileStore fileStore = Files.getFileStore(Paths.get(json.get("drive")));
        long totalSpaceInBytes = fileStore.getTotalSpace();
        long freeSpaceInBytes = fileStore.getUsableSpace();
        long usedSpaceInBytes = totalSpaceInBytes - freeSpaceInBytes;

        driveInfo.put("totalSpace", (totalSpaceInBytes / 1024 / 1024 / 1024));
        driveInfo.put("freeSpace", (freeSpaceInBytes / 1024 / 1024 / 1024));
        driveInfo.put("usedSpace", (usedSpaceInBytes / 1024 / 1024 / 1024));

        return driveInfo;
    }
}
