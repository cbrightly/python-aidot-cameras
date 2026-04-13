package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class MatterDeviceBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String fabricId;
    private String houseId;
    private Long id;
    private long nodeId;
    private transient int onoff = -1;
    private transient String vendorId = "";
    private transient String version = "";

    public MatterDeviceBean(Long id2, String fabricId2, long nodeId2, String houseId2) {
        this.id = id2;
        this.fabricId = fabricId2;
        this.nodeId = nodeId2;
        this.houseId = houseId2;
    }

    public MatterDeviceBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getFabricId() {
        return this.fabricId;
    }

    public void setFabricId(String fabricId2) {
        this.fabricId = fabricId2;
    }

    public long getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(long nodeId2) {
        this.nodeId = nodeId2;
    }

    public String getHouseId() {
        return this.houseId;
    }

    public void setHouseId(String houseId2) {
        this.houseId = houseId2;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public int getOnoff() {
        return this.onoff;
    }

    public void setOnoff(int onoff2) {
        this.onoff = onoff2;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public void setVendorId(String vendorId2) {
        this.vendorId = vendorId2;
    }
}
