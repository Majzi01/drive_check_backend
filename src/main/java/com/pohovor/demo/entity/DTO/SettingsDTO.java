package com.pohovor.demo.entity.DTO;

public class SettingsDTO {

    private int percentageBelow;
    private int driveCheckerRefresh;
    private String email;

    public int getPercentageBelow() {
        return percentageBelow;
    }

    public void setPercentageBelow(int percentageBelow) {
        this.percentageBelow = percentageBelow;
    }

    public int getDriveCheckerRefresh() {
        return driveCheckerRefresh;
    }

    public void setDriveCheckerRefresh(int driveCheckerRefresh) {
        this.driveCheckerRefresh = driveCheckerRefresh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SettingsDTO(int percentageBelow, int driveCheckerRefresh, String email) {
        this.percentageBelow = percentageBelow;
        this.driveCheckerRefresh = driveCheckerRefresh;
        this.email = email;
    }
}
