package meshsdk.sql;

import io.netty.util.internal.StringUtil;

public class MeshDictBean {
    public static final int STATE_TEMP = 0;
    public static final int STATE_UPLOADED = 1;
    private Long id;
    private int localAddr;
    private String meshUUID;
    private String provisionerUUID;
    private int seqNum;
    private int state;

    public MeshDictBean(Long id2, String meshUUID2, String provisionerUUID2, int localAddr2, int seqNum2, int state2) {
        this.id = id2;
        this.meshUUID = meshUUID2;
        this.provisionerUUID = provisionerUUID2;
        this.localAddr = localAddr2;
        this.seqNum = seqNum2;
        this.state = state2;
    }

    public MeshDictBean() {
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

    public String getProvisionerUUID() {
        return this.provisionerUUID;
    }

    public void setProvisionerUUID(String provisionerUUID2) {
        this.provisionerUUID = provisionerUUID2;
    }

    public int getLocalAddr() {
        return this.localAddr;
    }

    public void setLocalAddr(int localAddr2) {
        this.localAddr = localAddr2;
    }

    public int getSeqNum() {
        return this.seqNum;
    }

    public void setSeqNum(int seqNum2) {
        this.seqNum = seqNum2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }

    public String toString() {
        return "{" + "\"id\":" + this.id + ",\"meshUUID\":\"" + this.meshUUID + StringUtil.DOUBLE_QUOTE + ",\"provisionerUUID\":\"" + this.provisionerUUID + StringUtil.DOUBLE_QUOTE + ",\"localAddr\":" + this.localAddr + ",\"seqNum\":" + this.seqNum + ",\"state\":" + this.state + '}';
    }
}
