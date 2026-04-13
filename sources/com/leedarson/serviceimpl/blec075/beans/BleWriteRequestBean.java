package com.leedarson.serviceimpl.blec075.beans;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class BleWriteRequestBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String characteristicsUUID = "";
    public String encrypt = "";
    public String encryptIV = "";
    public String encryptKey = "";
    public String mac = "";
    public String serviceUUID = "";

    public boolean checkCharacterOrServiceUuidIsShortMode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6469, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (TextUtils.isEmpty(this.characteristicsUUID) || this.characteristicsUUID.length() != 36) {
            return true;
        }
        return false;
    }
}
