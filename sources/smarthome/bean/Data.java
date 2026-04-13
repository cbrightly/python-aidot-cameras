package smarthome.bean;

public class Data {
    private String link;
    private int openType;
    private String postId;
    private String pushUuid;
    private String topicId;

    public String getLink() {
        return this.link;
    }

    public void setLink(String link2) {
        this.link = link2;
    }

    public int getOpenType() {
        return this.openType;
    }

    public void setOpenType(int openType2) {
        this.openType = openType2;
    }

    public String getPushUuid() {
        return this.pushUuid;
    }

    public void setPushUuid(String pushUuid2) {
        this.pushUuid = pushUuid2;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId2) {
        this.postId = postId2;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId2) {
        this.topicId = topicId2;
    }
}
