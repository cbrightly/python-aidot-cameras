package com.didichuxing.doraemonkit.kit.toolpanel;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import java.util.HashMap;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DokitSettingFragment.kt */
public final class DokitSettingFragment extends BaseFragment {
    private HashMap _$_findViewCache;
    private DokitSettingAdapter mAdapter;
    private final List<String> mSettings = q.m(DokitUtil.getString(R.string.dk_setting_kit_manager));

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
    @LayoutRes
    public int onRequestLayout() {
        return R.layout.dk_fragment_setting;
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        k.f(view, "view");
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public final void initView() {
        ((HomeTitleBar) _$_findCachedViewById(R.id.title_bar)).setListener(new DokitSettingFragment$initView$1(this));
        this.mAdapter = new DokitSettingAdapter(this.mSettings);
        int i = R.id.setting_list;
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(i);
        k.b(recyclerView, "setting_list");
        DokitSettingAdapter dokitSettingAdapter = this.mAdapter;
        if (dokitSettingAdapter == null) {
            k.t("mAdapter");
        }
        recyclerView.setAdapter(dokitSettingAdapter);
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(i);
        k.b(recyclerView2, "setting_list");
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        DokitSettingAdapter dokitSettingAdapter2 = this.mAdapter;
        if (dokitSettingAdapter2 == null) {
            k.t("mAdapter");
        }
        dokitSettingAdapter2.setOnItemClickListener(new DokitSettingFragment$initView$2(this));
    }
}
