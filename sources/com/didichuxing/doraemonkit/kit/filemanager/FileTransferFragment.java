package com.didichuxing.doraemonkit.kit.filemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.NetworkUtils;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import io.ktor.server.engine.a;
import java.net.BindException;
import java.util.HashMap;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileTransferFragment.kt */
public final class FileTransferFragment extends BaseFragment {
    private HashMap _$_findViewCache;

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
        return R.layout.dk_fragment_file_transfer;
    }

    @SuppressLint({"SetTextI18n"})
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        k.f(view, "view");
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) _$_findCachedViewById(R.id.tv_ip);
        k.b(textView, "tv_ip");
        textView.setText(NetworkUtils.c() + ':' + DokitConstant.INSTANCE.getFILE_MANAGER_HTTP_PORT());
        ((HomeTitleBar) _$_findCachedViewById(R.id.title_bar)).setListener(new FileTransferFragment$onViewCreated$1(this));
        int i = R.id.tv_tip_top;
        TextView textView2 = (TextView) _$_findCachedViewById(i);
        k.b(textView2, "tv_tip_top");
        textView2.setText(Html.fromHtml(DokitUtil.getString(R.string.dk_file_manager_tip_top)));
        ((TextView) _$_findCachedViewById(i)).setOnClickListener(new FileTransferFragment$onViewCreated$2(this));
        initKtor();
    }

    private final void initKtor() {
        try {
            a.b.b(HttpServer.INSTANCE.getServer(), false, 1, (Object) null);
        } catch (BindException e) {
        }
    }
}
