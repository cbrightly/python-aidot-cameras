package com.didichuxing.doraemonkit.kit.toolpanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ToolPanelDokitView.kt */
public final class ToolPanelDokitView extends AbsDokitView {
    private ToolPanelAdapter mAdapter;
    /* access modifiers changed from: private */
    public List<KitWrapItem> mKits = new ArrayList();

    public void onCreate(@NotNull Context context) {
        k.f(context, "context");
    }

    @NotNull
    public View onCreateView(@NotNull Context context, @NotNull FrameLayout view) {
        k.f(context, "context");
        k.f(view, "view");
        View inflate = LayoutInflater.from(context).inflate(R.layout.dk_tool_panel, view, false);
        k.b(inflate, "LayoutInflater.from(cont…_tool_panel, view, false)");
        return inflate;
    }

    public void onViewCreated(@NotNull FrameLayout view) {
        k.f(view, "view");
        generateKits();
        initView();
    }

    private final void generateKits() {
        Map $this$forEach$iv;
        Map $this$forEach$iv2 = DokitConstant.GLOBAL_KITS;
        for (Map.Entry group : $this$forEach$iv2.entrySet()) {
            String str = (String) group.getKey();
            if (k.a(str, DokitUtil.getString(R.string.dk_category_mode))) {
                List<KitWrapItem> list = this.mKits;
                $this$forEach$iv = $this$forEach$iv2;
                KitWrapItem kitWrapItem = r8;
                KitWrapItem kitWrapItem2 = new KitWrapItem(KitWrapItem.TYPE_MODE, (String) group.getKey(), false, (String) null, (AbstractKit) null, 12, (DefaultConstructorMarker) null);
                list.add(kitWrapItem);
            } else {
                $this$forEach$iv = $this$forEach$iv2;
                if (k.a(str, DokitUtil.getString(R.string.dk_category_exit))) {
                    this.mKits.add(new KitWrapItem(KitWrapItem.TYPE_EXIT, (String) group.getKey(), false, (String) null, (AbstractKit) null, 12, (DefaultConstructorMarker) null));
                } else if (k.a(str, DokitUtil.getString(R.string.dk_category_version))) {
                    this.mKits.add(new KitWrapItem(KitWrapItem.TYPE_VERSION, (String) group.getKey(), false, (String) null, (AbstractKit) null, 12, (DefaultConstructorMarker) null));
                } else if (!k.a(str, DokitConstant.GROUP_ID_PLATFORM) && !k.a(str, DokitConstant.GROUP_ID_COMM) && !k.a(str, DokitConstant.GROUP_ID_WEEX) && !k.a(str, DokitConstant.GROUP_ID_PERFORMANCE) && !k.a(str, DokitConstant.GROUP_ID_UI)) {
                    if (((List) group.getValue()).size() != 0) {
                        this.mKits.add(new KitWrapItem(999, (String) group.getKey(), false, (String) null, (AbstractKit) null, 12, (DefaultConstructorMarker) null));
                        for (KitWrapItem kitWrap : (Iterable) group.getValue()) {
                            if (kitWrap.getChecked()) {
                                this.mKits.add(kitWrap);
                            }
                        }
                    }
                } else if (((List) group.getValue()).size() != 0) {
                    List<KitWrapItem> list2 = this.mKits;
                    String string = DokitUtil.getString(DokitUtil.getStringId((String) group.getKey()));
                    k.b(string, "DokitUtil.getString(Doki…l.getStringId(group.key))");
                    list2.add(new KitWrapItem(999, string, false, (String) null, (AbstractKit) null, 12, (DefaultConstructorMarker) null));
                    for (KitWrapItem kitWrap2 : (Iterable) group.getValue()) {
                        if (kitWrap2.getChecked()) {
                            this.mKits.add(kitWrap2);
                        }
                    }
                }
            }
            $this$forEach$iv2 = $this$forEach$iv;
        }
    }

    private final void initView() {
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new ToolPanelDokitView$initView$1(this));
        this.mAdapter = new ToolPanelAdapter(this.mKits);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        ToolPanelAdapter toolPanelAdapter = this.mAdapter;
        if (toolPanelAdapter == null) {
            k.t("mAdapter");
        }
        toolPanelAdapter.setGridSpanSizeLookup(ToolPanelDokitView$initView$2.INSTANCE);
        ToolPanelAdapter toolPanelAdapter2 = this.mAdapter;
        if (toolPanelAdapter2 == null) {
            k.t("mAdapter");
        }
        toolPanelAdapter2.setOnItemClickListener(new ToolPanelDokitView$initView$3(this));
        RecyclerView rvKits = (RecyclerView) findViewById(R.id.rv_kits);
        k.b(rvKits, "rvKits");
        rvKits.setLayoutManager(gridLayoutManager);
        ToolPanelAdapter toolPanelAdapter3 = this.mAdapter;
        if (toolPanelAdapter3 == null) {
            k.t("mAdapter");
        }
        rvKits.setAdapter(toolPanelAdapter3);
    }

    public void initDokitViewLayoutParams(@NotNull DokitViewLayoutParams params) {
        k.f(params, "params");
        params.x = 0;
        params.y = 0;
        int i = DokitViewLayoutParams.MATCH_PARENT;
        params.width = i;
        params.height = i;
    }

    public boolean onBackPressed() {
        detach();
        return true;
    }

    public boolean shouldDealBackKey() {
        return true;
    }

    public void onHomeKeyPress() {
        detach();
    }

    public void onRecentAppKeyPress() {
        detach();
    }

    public void onResume() {
        super.onResume();
        resumeData();
        ToolPanelAdapter toolPanelAdapter = this.mAdapter;
        if (toolPanelAdapter == null) {
            k.t("mAdapter");
        }
        toolPanelAdapter.notifyDataSetChanged();
    }

    private final void resumeData() {
        this.mKits.clear();
        generateKits();
    }
}
