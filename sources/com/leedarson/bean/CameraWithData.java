package com.leedarson.bean;

import com.leedarson.view.CameraVideoView;
import com.meituan.robust.ChangeQuickRedirect;

public class CameraWithData {
    public static ChangeQuickRedirect changeQuickRedirect;
    private CameraVideoView cameraVideoView;
    private DeviceBean deviceBean;
    private boolean isSingleClick = true;
    private boolean isSingleDoubleClick = false;
    private long lastClickTime = 0;

    public CameraVideoView getCameraVideoView() {
        return this.cameraVideoView;
    }

    public void setCameraVideoView(CameraVideoView cameraVideoView2) {
        this.cameraVideoView = cameraVideoView2;
    }

    public DeviceBean getDeviceBean() {
        return this.deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean2) {
        this.deviceBean = deviceBean2;
    }

    public long getLastClickTime() {
        return this.lastClickTime;
    }

    public void setLastClickTime(long lastClickTime2) {
        this.lastClickTime = lastClickTime2;
    }

    public boolean isSingleClick() {
        return this.isSingleClick;
    }

    public void setSingleClick(boolean singleClick) {
        this.isSingleClick = singleClick;
    }

    public boolean isSingleDoubleClick() {
        return this.isSingleDoubleClick;
    }

    public void setSingleDoubleClick(boolean singleDoubleClick) {
        this.isSingleDoubleClick = singleDoubleClick;
    }
}
