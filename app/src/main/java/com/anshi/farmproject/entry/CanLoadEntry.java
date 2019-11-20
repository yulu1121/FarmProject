package com.anshi.farmproject.entry;

import java.io.Serializable;

public class CanLoadEntry implements Serializable {
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
    private String zhiwuName;
    private int zhiwuId;
    private String teaName;
    private String chainName;
    public String getZhiwuName() {
        return zhiwuName;
    }

    public void setZhiwuName(String zhiwuName) {
        this.zhiwuName = zhiwuName;
    }

    public int getZhiwuId() {
        return zhiwuId;
    }

    public void setZhiwuId(int zhiwuId) {
        this.zhiwuId = zhiwuId;
    }

    public CanLoadEntry() {
    }

    public Long getUploadNumber() {
        return uploadNumber;
    }

    public void setUploadNumber(Long uploadNumber) {
        this.uploadNumber = uploadNumber;
    }

    public String getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(String realNumber) {
        this.realNumber = realNumber;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getDealTypePosition() {
        return dealTypePosition;
    }

    public void setDealTypePosition(int dealTypePosition) {
        this.dealTypePosition = dealTypePosition;
    }

    public int getVillagePosition() {
        return villagePosition;
    }

    public void setVillagePosition(int villagePosition) {
        this.villagePosition = villagePosition;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getAroundIvPath() {
        return aroundIvPath;
    }

    public void setAroundIvPath(String aroundIvPath) {
        this.aroundIvPath = aroundIvPath;
    }

    public String getNumberIvPath() {
        return numberIvPath;
    }

    public void setNumberIvPath(String numberIvPath) {
        this.numberIvPath = numberIvPath;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getOwnTown() {
        return ownTown;
    }

    public void setOwnTown(String ownTown) {
        this.ownTown = ownTown;
    }

    public String getWokerName() {
        return wokerName;
    }

    public void setWokerName(String wokerName) {
        this.wokerName = wokerName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }
}
