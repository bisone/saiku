package org.saiku.web.rest.bisone;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by will on 15-8-27.
 */
public class SoneLicense implements Serializable {

    private static final long serialVersionUID = -200000188776554441l;

    public static final String TYPE_FULL_MAX_MEM = "full-maxmem";
    private String email = "bisone@github.com";
    private Date expiration = new Date();
    private String licenseNumber = "1234567";
    private String licenseType = TYPE_FULL_MAX_MEM;
    private String name = "BISONE";
    private String version = "1.0";

    private int memoryLimit=0;
    private String hostname="sone";
    private int userLimit=0;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
