package com.ivanbet.sport.app;

/**
 * Created by combo on 10/31/2017.
 * Horoscope
 */

public class UData {

    private String uuid;
    private String model;
    private String platform;
    private String version;
    private String cversion;
    private Integer vrt;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCversion() {
        return cversion;
    }

    public void setCversion(String cversion) {
        this.cversion = cversion;
    }

    public Integer getVrt() {
        return vrt;
    }

    public void setVrt(Integer vrt) {
        this.vrt = vrt;
    }
}
