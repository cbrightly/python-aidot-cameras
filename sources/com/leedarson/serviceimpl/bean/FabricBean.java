package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterInfo.kt */
public final class FabricBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final long fabricId;
    private final int fabricIndex;
    private int isCurrentFabric;
    private final int vendorId;
    @Nullable
    private final String vendorName;

    public FabricBean(long fid, int fIndex, int vid, @Nullable String name) {
        this.fabricId = fid;
        this.fabricIndex = fIndex;
        this.vendorId = vid;
        this.vendorName = name;
    }

    public final long getFabricId() {
        return this.fabricId;
    }

    public final int getFabricIndex() {
        return this.fabricIndex;
    }

    public final int getVendorId() {
        return this.vendorId;
    }

    @Nullable
    public final String getVendorName() {
        return this.vendorName;
    }

    public final int isCurrentFabric() {
        return this.isCurrentFabric;
    }

    public final void setCurrentFabric(int i) {
        this.isCurrentFabric = i;
    }

    public final void checkIsCurrentFabricIndex(long _currentFrabicId) {
        if (this.fabricId == _currentFrabicId) {
            this.isCurrentFabric = 1;
        } else {
            this.isCurrentFabric = 0;
        }
    }
}
