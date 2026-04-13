package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class UpdateFaceInfoBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String faceId;
    private String faceName;
    private String faceSeq;
    private String image;
    private String remark;

    public String getFaceName() {
        return this.faceName;
    }

    public void setFaceName(String faceName2) {
        this.faceName = faceName2;
    }

    public String getFaceSeq() {
        return this.faceSeq;
    }

    public void setFaceSeq(String faceSeq2) {
        this.faceSeq = faceSeq2;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark2) {
        this.remark = remark2;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }

    public String getFaceId() {
        return this.faceId;
    }

    public void setFaceId(String faceId2) {
        this.faceId = faceId2;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1122, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "UpdateFaceInfoBean{faceId='" + this.faceId + '\'' + ", image='" + this.image + '\'' + ", faceName='" + this.faceName + '\'' + ", faceSeq='" + this.faceSeq + '\'' + ", remark='" + this.remark + '\'' + '}';
    }
}
