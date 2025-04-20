package com.tds_trial_work.dto;

public class DeviceDto {
    private Long id;
    private String deviceId;
    private String deviceType;
    private String OS;
    private String metatag;
    private Long userId;


    public DeviceDto() {
    }
    public DeviceDto(Long id, String deviceId, String deviceType, String OS, String metatag, Long userId) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.OS = OS;
        this.metatag = metatag;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getMetatag() {
        return metatag;
    }

    public void setMetatag(String metatag) {
        this.metatag = metatag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}