package com.didichuxing.doraemonkit.kit.dataclean;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.r;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.util.DataCleanUtil;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.v;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DataCleanFragment.kt */
public final class DataCleanFragment extends BaseFragment {
    private HashMap _$_findViewCache;
    private List<String> dirs;
    private Button mBtnClean;
    /* access modifiers changed from: private */
    public LinearLayout mItemWrap;
    /* access modifiers changed from: private */
    public SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;

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

    public static final /* synthetic */ LinearLayout access$getMItemWrap$p(DataCleanFragment $this) {
        LinearLayout linearLayout = $this.mItemWrap;
        if (linearLayout == null) {
            k.t("mItemWrap");
        }
        return linearLayout;
    }

    public static final /* synthetic */ SettingItemAdapter access$getMSettingItemAdapter$p(DataCleanFragment $this) {
        SettingItemAdapter settingItemAdapter = $this.mSettingItemAdapter;
        if (settingItemAdapter == null) {
            k.t("mSettingItemAdapter");
        }
        return settingItemAdapter;
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_data_clean;
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        k.f(view, "view");
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private final void initView() {
        Collection destination$iv$iv;
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new DataCleanFragment$initView$1(this));
        View findViewById = findViewById(R.id.setting_list);
        k.b(findViewById, "findViewById(R.id.setting_list)");
        this.mSettingList = (RecyclerView) findViewById;
        View findViewById2 = findViewById(R.id.item_wrap);
        k.b(findViewById2, "findViewById(R.id.item_wrap)");
        this.mItemWrap = (LinearLayout) findViewById2;
        View findViewById3 = findViewById(R.id.btn_clean);
        k.b(findViewById3, "findViewById(R.id.btn_clean)");
        this.mBtnClean = (Button) findViewById3;
        String string = DokitUtil.getString(R.string.dk_kit_cache_check_all);
        k.b(string, "DokitUtil.getString(R.st…g.dk_kit_cache_check_all)");
        this.dirs = q.m(string);
        File[] listFiles = new File(r.c()).listFiles();
        if (listFiles != null) {
            ArrayList arrayList = new ArrayList();
            for (File file : listFiles) {
                File file2 = file;
                k.b(file2, "file");
                if (file2.isDirectory()) {
                    arrayList.add(file);
                }
            }
            Iterable<File> $this$mapTo$iv$iv = arrayList;
            destination$iv$iv = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
            for (File file3 : $this$mapTo$iv$iv) {
                k.b(file3, "file");
                destination$iv$iv.add(file3.getName());
            }
        } else {
            destination$iv$iv = null;
        }
        Collection innerDirs = destination$iv$iv;
        List<String> list = this.dirs;
        if (list == null) {
            k.t("dirs");
        }
        if (innerDirs == null) {
            k.n();
        }
        v.x(list, innerDirs);
        Iterable<String> $this$forEach$iv = this.dirs;
        if ($this$forEach$iv == null) {
            k.t("dirs");
        }
        for (String it : $this$forEach$iv) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dk_item_data_clean, (ViewGroup) null);
            if (inflate != null) {
                RelativeLayout item = (RelativeLayout) inflate;
                View findViewById4 = item.findViewById(R.id.tv_name);
                k.b(findViewById4, "item.findViewById<TextView>(R.id.tv_name)");
                ((TextView) findViewById4).setText(it);
                View findViewById5 = item.findViewById(R.id.switch_btn);
                k.b(findViewById5, "item.findViewById<Switch>(R.id.switch_btn)");
                ((Switch) findViewById5).setChecked(false);
                item.setOnClickListener(new DataCleanFragment$initView$$inlined$forEach$lambda$1(this));
                LinearLayout linearLayout = this.mItemWrap;
                if (linearLayout == null) {
                    k.t("mItemWrap");
                }
                linearLayout.addView(item);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type android.widget.RelativeLayout");
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = this.mSettingList;
        if (recyclerView == null) {
            k.t("mSettingList");
        }
        recyclerView.setLayoutManager(layoutManager);
        List settingItems = new ArrayList();
        SettingItem settingItem = new SettingItem(R.string.dk_kit_data_clean);
        settingItem.rightDesc = DataCleanUtil.getApplicationDataSizeStr(getContext());
        settingItems.add(settingItem);
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        if (settingItemAdapter == null) {
            k.t("mSettingItemAdapter");
        }
        settingItemAdapter.setData(settingItems);
        Button button = this.mBtnClean;
        if (button == null) {
            k.t("mBtnClean");
        }
        button.setOnClickListener(new DataCleanFragment$initView$3(this));
        RecyclerView recyclerView2 = this.mSettingList;
        if (recyclerView2 == null) {
            k.t("mSettingList");
        }
        SettingItemAdapter settingItemAdapter2 = this.mSettingItemAdapter;
        if (settingItemAdapter2 == null) {
            k.t("mSettingItemAdapter");
        }
        recyclerView2.setAdapter(settingItemAdapter2);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        RecyclerView recyclerView3 = this.mSettingList;
        if (recyclerView3 == null) {
            k.t("mSettingList");
        }
        recyclerView3.addItemDecoration(decoration);
    }

    /* access modifiers changed from: private */
    public final void cleanCache() {
        LinearLayout linearLayout = this.mItemWrap;
        if (linearLayout == null) {
            k.t("mItemWrap");
        }
        int childCount = linearLayout.getChildCount();
        for (int index = 1; index < childCount; index++) {
            LinearLayout linearLayout2 = this.mItemWrap;
            if (linearLayout2 == null) {
                k.t("mItemWrap");
            }
            View item = linearLayout2.getChildAt(index);
            TextView name = (TextView) item.findViewById(R.id.tv_name);
            Switch switchR = (Switch) item.findViewById(R.id.switch_btn);
            k.b(switchR, "switch");
            if (switchR.isChecked()) {
                StringBuilder sb = new StringBuilder();
                sb.append(r.c());
                sb.append(File.separator);
                k.b(name, "name");
                sb.append(name.getText());
                File file = new File(sb.toString());
                if (file.isDirectory()) {
                    FileUtil.deleteDirectory(file);
                }
            }
        }
    }
}
