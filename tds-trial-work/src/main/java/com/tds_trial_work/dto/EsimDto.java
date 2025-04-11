package com.tds_trial_work.dto;

public class EsimDto {
    private Long id;
    private String ICCID;
    private String IMSI;
    private String activationCode;
    private String eID;
    private Long deviceId; 
    public EsimDto() {
    }
    public EsimDto(Long id, String ICCID, String IMSI, String activationCode, String eID, Long deviceId) {
        this.id = id;
        this.ICCID = ICCID;
        this.IMSI = IMSI;
        this.activationCode = activationCode;
        this.eID = eID;
        this.deviceId = deviceId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String geteID() {
        return eID;
    }

    public void seteID(String eID) {
        this.eID = eID;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}