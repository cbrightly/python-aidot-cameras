package meshsdk.sql;

import io.netty.util.internal.StringUtil;

public class RandomOffsetBean {
    private Long id;
    private String mac;
    private String meshUUID;
    private int offset;

    public RandomOffsetBean() {
    }

    public RandomOffsetBean(Long id2, String meshUUID2, String mac2, int offset2) {
        this.id = id2;
        this.meshUUID = meshUUID2;
        this.mac = mac2;
        this.offset = offset2;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getMeshUUID() {
        return this.meshUUID;
    }

    public void setMeshUUID(String meshUUID2) {
        this.meshUUID = meshUUID2;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset2) {
        this.offset = offset2;
    }

    public String toString() {
        return "{" + "\"id\":" + this.id + ",\"meshUUID\":\"" + this.meshUUID + StringUtil.DOUBLE_QUOTE + ",\"mac\":\"" + this.mac + StringUtil.DOUBLE_QUOTE + ",\"offset\":" + this.offset + '}';
    }
}
