package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class UploadFileResultEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int code;
    private String fileId;

    public UploadFileResultEvent(int code2, String fileId2) {
        this.fileId = fileId2;
        this.code = code2;
    }

    public String getFileId() {
        return this.fileId;
    }

    public int getCode() {
        return this.code;
    }
}
