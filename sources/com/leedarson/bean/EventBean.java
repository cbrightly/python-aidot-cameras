package com.leedarson.bean;

import com.leedarson.newui.ai.beans.AiMarkPositionBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;
import java.util.ArrayList;

public class EventBean implements Serializable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long begin = 0;
    private long createTime;
    private String deviceId;
    private String deviceName;
    private long end = 0;
    private String eventCode;
    private String[] eventDescList;
    private String eventUuid;
    public int expiredFlag = 0;
    private int feedback = -1;
    private int hasVideo = -1;
    private String imgUrl;
    private boolean isChecked = false;
    private boolean isFamily = false;
    public String picUrl = "https://t7.baidu.com/it/u=4198287529,2774471735&fm=193&f=GIF";
    private int playFlag;
    public ArrayList<ArrayList<String>> positions = new ArrayList<>();
    private int recordExpired;
    private long recordExpiredDate = -1;
    private int reviewEventLimits = -1;
    private int tagCode;
    private String tagName;
    private String tagTip;
    private String url;
    public long videoLength = 0;
    public String videoPath = "";
    public String videoUrl = "https://alivc-demo-cms.alicdn.com/video/videoAD.mp4";

    public ArrayList<AiMarkPositionBean> getAiMarksList() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1001, new Class[0], ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<AiMarkPositionBean> markPositions = new ArrayList<>();
        ArrayList<ArrayList<String>> arrayList = this.positions;
        if (arrayList == null || arrayList.size() == 0) {
            return markPositions;
        }
        for (int i = 0; i < this.positions.size(); i++) {
            AiMarkPositionBean positionBean = new AiMarkPositionBean();
            ArrayList<String> positionItem = this.positions.get(i);
            positionBean.topLeft = Float.parseFloat(positionItem.get(0));
            positionBean.topTop = Float.parseFloat(positionItem.get(1));
            positionBean.bottomLeft = Float.parseFloat(positionItem.get(2));
            positionBean.bottomTop = Float.parseFloat(positionItem.get(3));
            markPositions.add(positionBean);
        }
        return markPositions;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public String getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(String eventCode2) {
        this.eventCode = eventCode2;
    }

    public String[] getEventDescList() {
        return this.eventDescList;
    }

    public void setEventDescList(String[] eventDescList2) {
        this.eventDescList = eventDescList2;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime2) {
        this.createTime = createTime2;
    }

    public int getRecordExpired() {
        return this.recordExpired;
    }

    public void setRecordExpired(int recordExpired2) {
        this.recordExpired = recordExpired2;
    }

    public String getEventUuid() {
        return this.eventUuid;
    }

    public void setEventUuid(String eventUuid2) {
        this.eventUuid = eventUuid2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public boolean isFamily() {
        return this.isFamily;
    }

    public void setFamily(boolean family) {
        this.isFamily = family;
    }

    public int getHasVideo() {
        return this.hasVideo;
    }

    public void setHasVideo(int hasVideo2) {
        this.hasVideo = hasVideo2;
    }

    public int getFeedback() {
        return this.feedback;
    }

    public void setFeedback(int feedback2) {
        this.feedback = feedback2;
    }

    public int getReviewEventLimits() {
        return this.reviewEventLimits;
    }

    public void setReviewEventLimits(int reviewEventLimits2) {
        this.reviewEventLimits = reviewEventLimits2;
    }

    public long getRecordExpiredDate() {
        return this.recordExpiredDate;
    }

    public void setRecordExpiredDate(long recordExpiredDate2) {
        this.recordExpiredDate = recordExpiredDate2;
    }

    public long getBegin() {
        return this.begin;
    }

    public void setBegin(long begin2) {
        this.begin = begin2;
    }

    public long getEnd() {
        return this.end;
    }

    public void setEnd(long end2) {
        this.end = end2;
    }

    public int getPlayFlag() {
        return this.playFlag;
    }

    public void setPlayFlag(int playFlag2) {
        this.playFlag = playFlag2;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName2) {
        this.tagName = tagName2;
    }

    public String getTagTip() {
        return this.tagTip;
    }

    public void setTagTip(String tagTip2) {
        this.tagTip = tagTip2;
    }

    public int getTagCode() {
        return this.tagCode;
    }

    public void setTagCode(int tagCode2) {
        this.tagCode = tagCode2;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1002, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "EventBean{eventUuid='" + this.eventUuid + '\'' + ", imgUrl='" + this.imgUrl + '\'' + '}';
    }
}
