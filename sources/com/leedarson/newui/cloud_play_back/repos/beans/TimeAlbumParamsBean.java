package com.leedarson.newui.cloud_play_back.repos.beans;

import com.meituan.robust.ChangeQuickRedirect;

public class TimeAlbumParamsBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String albumId;
    private long date;
    private String deviceId;
    private long endTime;
    private int eventType;
    private String eventUuid;
    private boolean isOwner;
    private String poster;
    private String source;
    private long startTime;
    private long videoDuration;
    private String videoId;
    private long videoSize;
    private int videoType;
    private String videoUrl;

    public void setIsOwner(boolean isOwner2) {
        this.isOwner = isOwner2;
    }

    public boolean getIsOwner() {
        return this.isOwner;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setSource(String source2) {
        this.source = source2;
    }

    public String getSource() {
        return this.source;
    }

    public void setDate(long date2) {
        this.date = date2;
    }

    public long getDate() {
        return this.date;
    }

    public void setPoster(String poster2) {
        this.poster = poster2;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setAlbumId(String albumId2) {
        this.albumId = albumId2;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setVideoId(String videoId2) {
        this.videoId = videoId2;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setEventUuid(String eventUuid2) {
        this.eventUuid = eventUuid2;
    }

    public String getEventUuid() {
        return this.eventUuid;
    }

    public void setEndTime(long endTime2) {
        this.endTime = endTime2;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setStartTime(long startTime2) {
        this.startTime = startTime2;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setEventType(int eventType2) {
        this.eventType = eventType2;
    }

    public int getEventType() {
        return this.eventType;
    }

    public void setVideoType(int videoType2) {
        this.videoType = videoType2;
    }

    public int getVideoType() {
        return this.videoType;
    }

    public void setVideoUrl(String videoUrl2) {
        this.videoUrl = videoUrl2;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoSize(long videoSize2) {
        this.videoSize = videoSize2;
    }

    public long getVideoSize() {
        return this.videoSize;
    }

    public void setVideoDuration(int videoDuration2) {
        this.videoDuration = (long) videoDuration2;
    }

    public long getVideoDuration() {
        return this.videoDuration;
    }
}
