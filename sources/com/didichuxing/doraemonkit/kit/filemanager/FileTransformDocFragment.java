package com.didichuxing.doraemonkit.kit.filemanager;

import android.os.Bundle;
import android.view.View;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.didichuxing.doraemonkit.widget.webview.MyWebView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import java.util.HashMap;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileTransformDocFragment.kt */
public final class FileTransformDocFragment extends BaseFragment {
    private HashMap _$_findViewCache;
    private MyWebView mWebView;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_file_manager_doc;
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        k.f(view, "view");
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private final void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new FileTransformDocFragment$initView$1(this));
        MyWebView myWebView = (MyWebView) findViewById(R.id.webview);
        this.mWebView = myWebView;
        if (myWebView != null) {
            myWebView.loadUrl(NetworkManager.FILE_MANAGER_DOCUMENT_URL);
            SensorsDataAutoTrackHelper.loadUrl2(myWebView, NetworkManager.FILE_MANAGER_DOCUMENT_URL);
        }
    }
}
