package com.meituan.robust;

import java.io.File;

public class Patch implements Cloneable {
    private String appHash;
    private boolean isAppliedSuccess;
    private String localPath;
    private String md5;
    private String name;
    private String patchesInfoImplClassFullName;
    private String tempPath;
    private String url;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getLocalPath() {
        return this.localPath + ".jar";
    }

    public void setLocalPath(String localPath2) {
        this.localPath = localPath2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public boolean isAppliedSuccess() {
        return this.isAppliedSuccess;
    }

    public void setAppliedSuccess(boolean appliedSuccess) {
        this.isAppliedSuccess = appliedSuccess;
    }

    public void delete(String path) {
        new File(path).delete();
    }

    public String getPatchesInfoImplClassFullName() {
        return this.patchesInfoImplClassFullName;
    }

    public void setPatchesInfoImplClassFullName(String patchesInfoImplClassFullName2) {
        this.patchesInfoImplClassFullName = patchesInfoImplClassFullName2;
    }

    public String getAppHash() {
        return this.appHash;
    }

    public void setAppHash(String appHash2) {
        this.appHash = appHash2;
    }

    public String getTempPath() {
        return this.tempPath + "_temp.jar";
    }

    public void setTempPath(String tempPath2) {
        this.tempPath = tempPath2;
    }

    public Patch clone() {
        try {
            return (Patch) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
