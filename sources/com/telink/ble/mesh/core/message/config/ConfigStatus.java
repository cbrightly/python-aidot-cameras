package com.telink.ble.mesh.core.message.config;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public enum ConfigStatus {
    SUCCESS(0, "Success"),
    INVALID_ADDRESS(1, "Invalid Address"),
    INVALID_MODEL(2, "Invalid Model"),
    INVALID_APPKEY_INDEX(3, "Invalid AppKey Index"),
    INVALID_NETKEY_INDEX(4, "Invalid NetKey Index"),
    INSUFFICIENT_RESOURCES(5, "Insufficient Resources"),
    KEY_INDEX_ALREADY_STORED(6, "Key Index Already Stored"),
    INVALID_PUBLISH_PARAMETERS(7, "Invalid Publish Parameters"),
    NOT_A_SUBSCRIBE_MODEL(8, "Not a Subscribe Model"),
    STORAGE_FAILURE(9, "Storage Failure"),
    FEATURE_NOT_SUPPORTED(10, "Feature Not Supported"),
    CANNOT_UPDATE(11, "Cannot Update"),
    CANNOT_REMOVE(12, "Cannot Remove"),
    CANNOT_BIND(13, "Cannot Bind"),
    TEMPORARILY_UNABLE_TO_CHANGE_STATE(14, "Temporarily Unable to Change State"),
    CANNOT_SET(15, "Cannot Set"),
    UNSPECIFIED_ERROR(16, "Unspecified Error"),
    INVALID_BINDING(17, "Invalid Binding"),
    UNKNOWN_ERROR(255, "unknown error");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public final int code;
    public final String desc;

    private ConfigStatus(int code2, String desc2) {
        this.code = code2;
        this.desc = desc2;
    }

    public static ConfigStatus valueOf(int code2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code2)}, (Object) null, changeQuickRedirect, true, 12465, new Class[]{Integer.TYPE}, ConfigStatus.class);
        if (proxy.isSupported) {
            return (ConfigStatus) proxy.result;
        }
        for (ConfigStatus status : values()) {
            if (status.code == code2) {
                return status;
            }
        }
        return UNKNOWN_ERROR;
    }
}
