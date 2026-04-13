package com.leedarson.newui.repos.beans;

import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class VideoUrlItemBean {
    public static final int VIDEO_TYPE_M3U8 = 2;
    public static final int VIDEO_TYPE_MP4 = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    public long begin = 0;
    public long end = 0;
    public int hasRadar = 0;
    public String local_path_url = "";
    public int type = 1;
    public String url = "";

    public String getAvailableUrl() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4470, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!TextUtils.isEmpty(this.local_path_url)) {
            return this.local_path_url;
        }
        return this.url;
    }
}
