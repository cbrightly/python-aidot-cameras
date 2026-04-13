package com.sensorsdata.analytics.android.sdk.deeplink;

import android.content.Intent;
import com.sensorsdata.analytics.android.sdk.deeplink.DeepLinkManager;

public abstract class AbsDeepLink implements DeepLinkProcessor {
    private String deepLinkUrl;
    protected DeepLinkManager.OnDeepLinkParseFinishCallback mCallBack;

    AbsDeepLink(Intent intent) {
        if (intent != null && intent.getData() != null) {
            setDeepLinkUrl(intent.getData().toString());
        }
    }

    public void setDeepLinkUrl(String deepLinkUrl2) {
        this.deepLinkUrl = deepLinkUrl2;
    }

    public String getDeepLinkUrl() {
        return this.deepLinkUrl;
    }

    public void setDeepLinkParseFinishCallback(DeepLinkManager.OnDeepLinkParseFinishCallback callBack) {
        this.mCallBack = callBack;
    }
}
