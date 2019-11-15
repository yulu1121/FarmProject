package com.anshi.farmproject.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UploadLocationEntry  {
    @Id
    private Long uploadNumber;
    private String realNumber;
    private String dealType;
    private int groupNumber;
    private String addressName;
    private String villageName;
    private int dealTypePosition;
    private int villagePosition;
    private double radius;
    private String aroundIvPath;
    private String numberIvPath;
    private String dealTime;
    private String ownTown;
    private String wokerName;
    private double latitude;
    private double longtitude;
    private String detailAddress;
    @Generated(hash = 437880930)
    public UploadLocationEntry() {
    }
    @Generated(hash = 1105178203)
    public UploadLocationEntry(Long uploadNumber, String realNumber, String dealType, int groupNumber, String addressName, String villageName, int dealTypePosition, int villagePosition, double radius, String aroundIvPath, String numberIvPath, String dealTime, String ownTown,
            String wokerName, double latitude, double longtitude, String detailAddress) {
        this.uploadNumber = uploadNumber;
        this.realNumber = realNumber;
        this.dealType = dealType;
        this.groupNumber = groupNumber;
        this.addressName = addressName;
        this.villageName = villageName;
        this.dealTypePosition = dealTypePosition;
        this.villagePosition = villagePosition;
        this.radius = radius;
        this.aroundIvPath = aroundIvPath;
        this.numberIvPath = numberIvPath;
        this.dealTime = dealTime;
        this.ownTown = ownTown;
        this.wokerName = wokerName;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.detailAddress = detailAddress;
    }
    public Long getUploadNumber() {
        return this.uploadNumber;
    }
    public void setUploadNumber(Long uploadNumber) {
        this.uploadNumber = uploadNumber;
    }
    public String getRealNumber() {
        return this.realNumber;
    }
    public void setRealNumber(String realNumber) {
        this.realNumber = realNumber;
    }
    public String getDealType() {
        return this.dealType;
    }
    public void setDealType(String dealType) {
        this.dealType = dealType;
    }
    public int getGroupNumber() {
        return this.groupNumber;
    }
    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
    public String getAddressName() {
        return this.addressName;
    }
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    public double getRadius() {
        return this.radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public String getAroundIvPath() {
        return this.aroundIvPath;
    }
    public void setAroundIvPath(String aroundIvPath) {
        this.aroundIvPath = aroundIvPath;
    }
    public String getNumberIvPath() {
        return this.numberIvPath;
    }
    public void setNumberIvPath(String numberIvPath) {
        this.numberIvPath = numberIvPath;
    }
    public String getDealTime() {
        return this.dealTime;
    }
    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
    public String getOwnTown() {
        return this.ownTown;
    }
    public void setOwnTown(String ownTown) {
        this.ownTown = ownTown;
    }
    public String getWokerName() {
        return this.wokerName;
    }
    public void setWokerName(String wokerName) {
        this.wokerName = wokerName;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongtitude() {
        return this.longtitude;
    }
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
    public String getVillageName() {
        return this.villageName;
    }
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
    public int getDealTypePosition() {
        return this.dealTypePosition;
    }
    public void setDealTypePosition(int dealTypePosition) {
        this.dealTypePosition = dealTypePosition;
    }
    public int getVillagePosition() {
        return this.villagePosition;
    }
    public void setVillagePosition(int villagePosition) {
        this.villagePosition = villagePosition;
    }
    public String getDetailAddress() {
        return this.detailAddress;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
