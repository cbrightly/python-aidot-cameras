package smarthome.bean;

import java.io.Serializable;

public class AdvertBean implements Serializable {
    private static final long serialVersionUID = -3986640688241304001L;
    private int displayDuration;
    private int displayTimes;
    private String fileId;
    private String fileType;
    private String id;
    private String linkType;
    private String linkUrl;

    public AdvertBean(String id2, String fileId2, String fileType2, String fileUrl, String linkType2, String linkUrl2, int displayTimes2, int displayDuration2) {
        this.id = id2;
        this.fileId = fileId2;
        this.fileType = fileType2;
        this.linkType = linkType2;
        this.linkUrl = linkUrl2;
        this.displayTimes = displayTimes2;
        this.displayDuration = displayDuration2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId2) {
        this.fileId = fileId2;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType2) {
        this.fileType = fileType2;
    }

    public String getLinkType() {
        return this.linkType;
    }

    public void setLinkType(String linkType2) {
        this.linkType = linkType2;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public void setLinkUrl(String linkUrl2) {
        this.linkUrl = linkUrl2;
    }

    public int getDisplayTimes() {
        return this.displayTimes;
    }

    public void setDisplayTimes(int displayTimes2) {
        this.displayTimes = displayTimes2;
    }

    public int getDisplayDuration() {
        return this.displayDuration;
    }

    public void setDisplayDuration(int displayDuration2) {
        this.displayDuration = displayDuration2;
    }

    public String toString() {
        return "AdvertBean{id='" + this.id + '\'' + ", fileId='" + this.fileId + '\'' + ", fileType='" + this.fileType + '\'' + ", displayTimes='" + this.displayTimes + '\'' + ", displayDuration='" + this.displayDuration + '\'' + ", linkType='" + this.linkType + '\'' + ", linkUrl='" + this.linkUrl + '\'' + '}';
    }
}
