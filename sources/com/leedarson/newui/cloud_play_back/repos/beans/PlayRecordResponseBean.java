package com.leedarson.newui.cloud_play_back.repos.beans;

import com.meituan.robust.ChangeQuickRedirect;

public class PlayRecordResponseBean extends LDSBaseBean<PlayRecordResponseBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Integer heartbeat;
    private Integer port;
    private Integer taskId;
    private String token;
    private String videoKey;

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port2) {
        this.port = port2;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId2) {
        this.taskId = taskId2;
    }

    public Integer getHeartbeat() {
        return this.heartbeat;
    }

    public void setHeartbeat(Integer heartbeat2) {
        this.heartbeat = heartbeat2;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public String getVideoKey() {
        return this.videoKey;
    }

    public void setVideoKey(String videoKey2) {
        this.videoKey = videoKey2;
    }
}
