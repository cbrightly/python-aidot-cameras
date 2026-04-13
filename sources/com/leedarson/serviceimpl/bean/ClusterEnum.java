package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: ClusterEnum.kt */
public enum ClusterEnum {
    OnOff(6, 1, 0),
    HSL_H(768, 1, 0),
    HSL_S(768, 1, 1),
    CCT(768, 1, 7),
    DIM(8, 1, 0);
    
    public static ChangeQuickRedirect changeQuickRedirect;
    private long attrId;
    private long clusterId;
    private long endpointId;

    private ClusterEnum(long cid, long eid, long aid) {
        this.clusterId = cid;
        this.endpointId = eid;
        this.attrId = aid;
    }

    public final long getClusterId() {
        return this.clusterId;
    }

    public final void setClusterId(long j) {
        this.clusterId = j;
    }

    public final long getEndpointId() {
        return this.endpointId;
    }

    public final void setEndpointId(long j) {
        this.endpointId = j;
    }

    public final long getAttrId() {
        return this.attrId;
    }

    public final void setAttrId(long j) {
        this.attrId = j;
    }
}
