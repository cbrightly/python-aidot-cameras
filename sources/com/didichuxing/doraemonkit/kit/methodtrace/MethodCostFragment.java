package com.didichuxing.doraemonkit.kit.methodtrace;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.didichuxing.doraemonkit.widget.webview.MyWebView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;

public class MethodCostFragment extends BaseFragment {
    MyWebView mWebView;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_method_cost;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                MethodCostFragment.this.getActivity().finish();
            }
        });
        MyWebView myWebView = (MyWebView) findViewById(R.id.webview);
        this.mWebView = myWebView;
        myWebView.loadUrl(NetworkManager.APP_DOCUMENT_URL);
        SensorsDataAutoTrackHelper.loadUrl2(myWebView, NetworkManager.APP_DOCUMENT_URL);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
