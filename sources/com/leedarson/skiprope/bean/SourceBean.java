package com.leedarson.skiprope.bean;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.apache.http.l;

public class SourceBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String bgmSourceName;
    public int bgmVersion = 0;
    public boolean needDownloadBgm = false;
    public boolean needDownloadVoice = false;
    public String voiceSourceName;
    public int voiceVersion = 0;

    public String getBgmDownloadUrl(String host) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{host}, this, changeQuickRedirect, false, 9501, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return getBaseDownloadUrl(host) + this.bgmSourceName;
    }

    public String getVoiceDownloadUrl(String host) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{host}, this, changeQuickRedirect, false, 9502, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return getBaseDownloadUrl(host) + this.voiceSourceName;
    }

    private String getBaseDownloadUrl(String host) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{host}, this, changeQuickRedirect, false, 9503, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (host.startsWith(l.DEFAULT_SCHEME_NAME)) {
            return host + "/media/skipRope/";
        }
        return NetworkManager.MOCK_SCHEME_HTTPS + host + "/media/skipRope/";
    }
}
