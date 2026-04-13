package com.didichuxing.doraemonkit.kit.webdoor;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;

public class WebDoorDefaultFragment extends BaseFragment {
    private String mUrl;
    private WebView mWebView;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_web_door_default;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUrl = getArguments() == null ? null : getArguments().getString(BundleKey.KEY_URL);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView) findViewById(R.id.webview);
        this.mWebView = webView;
        String str = this.mUrl;
        webView.loadUrl(str);
        SensorsDataAutoTrackHelper.loadUrl2(webView, str);
    }

    /* access modifiers changed from: protected */
    public boolean onBackPressed() {
        if (!this.mWebView.canGoBack()) {
            return super.onBackPressed();
        }
        this.mWebView.goBack();
        return true;
    }
}
