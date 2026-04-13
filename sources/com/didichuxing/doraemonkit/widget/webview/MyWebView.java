package com.didichuxing.doraemonkit.widget.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.widget.webview.MyWebViewClient;

public class MyWebView extends WebView {
    private Activity mContainerActivity;
    private MyWebViewClient mMyWebViewClient;
    private ProgressBar mProgressBar;

    public MyWebView(Context context) {
        super(getFixedContext(context));
        init(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
        init(context);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint({"ObsoleteSdkInt"})
    private static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            return context.createConfigurationContext(new Configuration());
        }
        return context;
    }

    private void init(Context context) {
        if (context instanceof Activity) {
            this.mContainerActivity = (Activity) context;
            WebSettings webSettings = getSettings();
            webSettings.setPluginState(WebSettings.PluginState.ON);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(false);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setBuiltInZoomControls(false);
            webSettings.setDefaultTextEncodingName("UTF-8");
            webSettings.setDomStorageEnabled(true);
            webSettings.setCacheMode(-1);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
            int i = Build.VERSION.SDK_INT;
            if (i < 18) {
                webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            }
            if (i >= 19) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            if (i >= 21) {
                webSettings.setMixedContentMode(0);
            }
            if (i > 10 && i < 17) {
                removeJavascriptInterface("searchBoxJavaBridge_");
                removeJavascriptInterface("accessibilityTraversal");
                removeJavascriptInterface("accessibility");
            }
            MyWebViewClient myWebViewClient = new MyWebViewClient();
            this.mMyWebViewClient = myWebViewClient;
            setWebViewClient(myWebViewClient);
            setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress < 100) {
                        MyWebView.this.showLoadProgress(newProgress);
                    } else {
                        MyWebView.this.hideLoadProgress();
                    }
                }
            });
            addProgressView();
            return;
        }
        throw new RuntimeException("only support Activity context");
    }

    public void addInvokeListener(MyWebViewClient.InvokeListener listener) {
        this.mMyWebViewClient.addInvokeListener(listener);
    }

    public void removeInvokeListener(MyWebViewClient.InvokeListener listener) {
        this.mMyWebViewClient.removeInvokeListener(listener);
    }

    private void addProgressView() {
        ProgressBar progressBar = new ProgressBar(getContext(), (AttributeSet) null, 16842872);
        this.mProgressBar = progressBar;
        progressBar.setLayoutParams(new AbsoluteLayout.LayoutParams(-1, 10, 0, 0));
        this.mProgressBar.setProgressDrawable(new ClipDrawable(new ColorDrawable(Integer.valueOf(getResources().getColor(R.color.dk_color_55A8FD)).intValue()), 3, 1));
        this.mProgressBar.setVisibility(8);
        addView(this.mProgressBar);
    }

    public void showLoadProgress(int progress) {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            if (progressBar.getVisibility() == 8) {
                this.mProgressBar.setVisibility(0);
            }
            this.mProgressBar.setProgress(progress);
        }
    }

    public void hideLoadProgress() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    public void loadUrl(String url) {
        if (!TextUtils.isEmpty(url) && !url.startsWith(NetworkManager.MOCK_SCHEME_HTTP) && !url.startsWith(NetworkManager.MOCK_SCHEME_HTTPS) && !url.startsWith("javascript:")) {
            url = NetworkManager.MOCK_SCHEME_HTTP + url;
        }
        super.loadUrl(url);
    }

    public Activity getActivity() {
        return this.mContainerActivity;
    }
}
