package com.didichuxing.doraemonkit.widget.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;
import java.util.List;

public class MyWebViewClient extends WebViewClient {
    private List<InvokeListener> mListeners = new ArrayList();

    public interface InvokeListener {
        void onNativeInvoke(String str);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!url.startsWith("doraemon://invokeNative")) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        handleInvokeFromJs(url);
        return true;
    }

    private void handleInvokeFromJs(String url) {
        for (InvokeListener listener : this.mListeners) {
            listener.onNativeInvoke(url);
        }
    }

    public void addInvokeListener(InvokeListener listener) {
        this.mListeners.add(listener);
    }

    public void removeInvokeListener(InvokeListener listener) {
        this.mListeners.remove(listener);
    }
}
