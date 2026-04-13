package com.leedarson.base.utils;

import com.leedarson.base.beans.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONArray;

/* compiled from: StartTimeRecord */
public enum t {
    INSTANCE;
    
    public static ChangeQuickRedirect changeQuickRedirect;
    private String[] messageArray;
    public String[] timeArray;

    public String getStartTimeJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 515, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        JSONArray ja = new JSONArray();
        int i = 0;
        while (true) {
            String[] strArr = this.messageArray;
            if (i >= strArr.length) {
                return ja.toString();
            }
            ja.put((Object) new b(this.timeArray[i], strArr[i]).a());
            i++;
        }
    }
}
