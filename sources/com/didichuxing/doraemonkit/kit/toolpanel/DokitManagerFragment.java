package com.didichuxing.doraemonkit.kit.toolpanel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.toolpanel.decoration.HorizontalDividerItemDecoration;
import com.didichuxing.doraemonkit.kit.toolpanel.decoration.VerticalDividerItemDecoration;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment extends BaseFragment {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static boolean IS_EDIT;
    private HashMap _$_findViewCache;
    /* access modifiers changed from: private */
    public DokitManagerAdapter mAdapter;
    /* access modifiers changed from: private */
    public final LinkedHashMap<String, List<KitWrapItem>> mBakGlobalKits = new LinkedHashMap<>();
    /* access modifiers changed from: private */
    public final List<KitWrapItem> mBakKits = new ArrayList();
    /* access modifiers changed from: private */
    public int mDragStartPos = -1;
    /* access modifiers changed from: private */
    public final List<KitWrapItem> mKits = new ArrayList();

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

    public static final /* synthetic */ DokitManagerAdapter access$getMAdapter$p(DokitManagerFragment $this) {
        DokitManagerAdapter dokitManagerAdapter = $this.mAdapter;
        if (dokitManagerAdapter == null) {
            k.t("mAdapter");
        }
        return dokitManagerAdapter;
    }

    /* access modifiers changed from: protected */
    @LayoutRes
    public int onRequestLayout() {
        return R.layout.dk_fragment_kit_manager;
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        k.f(view, "view");
        super.onViewCreated(view, savedInstanceState);
        generateData();
        initView();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
        r2 = r10.mBakGlobalKits.get(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0053, code lost:
        if (r2 == null) goto L_0x000a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0055, code lost:
        r2.clear();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void updateGlobalBakKits() {
        /*
            r10 = this;
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r0 = r10.mBakGlobalKits
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x000a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005a
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            int r2 = r1.hashCode()
            switch(r2) {
                case -1858998038: goto L_0x0042;
                case -1812529378: goto L_0x0039;
                case -746547417: goto L_0x0030;
                case 570131901: goto L_0x0027;
                case 1742937308: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x0058
        L_0x001e:
            java.lang.String r2 = "dk_category_platform"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
        L_0x0026:
            goto L_0x004b
        L_0x0027:
            java.lang.String r2 = "dk_category_ui"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x0030:
            java.lang.String r2 = "dk_category_performance"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x0039:
            java.lang.String r2 = "dk_category_comms"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x0042:
            java.lang.String r2 = "dk_category_weex"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x004b:
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r2 = r10.mBakGlobalKits
            java.lang.Object r2 = r2.get(r1)
            java.util.List r2 = (java.util.List) r2
            if (r2 == 0) goto L_0x0058
            r2.clear()
        L_0x0058:
            goto L_0x000a
        L_0x005a:
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r0 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0064:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00b1
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r2 = r10.mBakGlobalKits
            java.lang.String r3 = "group"
            kotlin.jvm.internal.k.b(r1, r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2.put(r1, r3)
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r2 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            java.lang.Object r2 = r2.get(r1)
            java.util.List r2 = (java.util.List) r2
            if (r2 == 0) goto L_0x00af
            r3 = 0
            java.util.Iterator r4 = r2.iterator()
        L_0x008e:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00af
            java.lang.Object r5 = r4.next()
            r6 = r5
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r6 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r6
            r7 = 0
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r8 = r10.mBakGlobalKits
            java.lang.Object r8 = r8.get(r1)
            java.util.List r8 = (java.util.List) r8
            if (r8 == 0) goto L_0x00ad
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r9 = r6.clone()
            r8.add(r9)
        L_0x00ad:
            goto L_0x008e
        L_0x00af:
            goto L_0x0064
        L_0x00b1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.updateGlobalBakKits():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004c, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_PERFORMANCE) != false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0055, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_COMM) != false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0058, code lost:
        r17 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006f, code lost:
        if (((java.util.List) r5.getValue()).size() == 0) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0071, code lost:
        r7 = r0.mKits;
        r10 = com.didichuxing.doraemonkit.util.DokitUtil.getString(com.didichuxing.doraemonkit.util.DokitUtil.getStringId((java.lang.String) r5.getKey()));
        kotlin.jvm.internal.k.b(r10, "DokitUtil.getString(Doki…l.getStringId(group.key))");
        r17 = r1;
        r1 = r8;
        r8 = new com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem(999, r10, false, (java.lang.String) null, (com.didichuxing.doraemonkit.kit.AbstractKit) null, 12, (kotlin.jvm.internal.DefaultConstructorMarker) null);
        r7.add(r1);
        r8 = ((java.lang.Iterable) r5.getValue()).iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ac, code lost:
        if (r8.hasNext() == false) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ae, code lost:
        r10 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r8.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ba, code lost:
        if (r10.getChecked() == false) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bc, code lost:
        r0.mKits.add(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00c4, code lost:
        r17 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c9, code lost:
        r1 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003a, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_PLATFORM) != false) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0043, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_UI) != false) goto L_0x0065;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void generateData() {
        /*
            r18 = this;
            r0 = r18
            r18.updateGlobalBakKits()
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r1 = r0.mKits
            r1.clear()
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r1 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            r2 = 0
            java.util.Set r3 = r1.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0015:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x00ce
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r5 = r4
            r6 = 0
            java.lang.Object r7 = r5.getKey()
            java.lang.String r7 = (java.lang.String) r7
            int r8 = r7.hashCode()
            switch(r8) {
                case -1858998038: goto L_0x005c;
                case -1812529378: goto L_0x004f;
                case -746547417: goto L_0x0046;
                case 570131901: goto L_0x003d;
                case 1742937308: goto L_0x0034;
                default: goto L_0x0030;
            }
        L_0x0030:
            r17 = r1
            goto L_0x00c9
        L_0x0034:
            java.lang.String r8 = "dk_category_platform"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0058
        L_0x003c:
            goto L_0x0065
        L_0x003d:
            java.lang.String r8 = "dk_category_ui"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0058
            goto L_0x003c
        L_0x0046:
            java.lang.String r8 = "dk_category_performance"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0058
            goto L_0x003c
        L_0x004f:
            java.lang.String r8 = "dk_category_comms"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0058
            goto L_0x003c
        L_0x0058:
            r17 = r1
            goto L_0x00c9
        L_0x005c:
            java.lang.String r8 = "dk_category_weex"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x00c7
            goto L_0x003c
        L_0x0065:
            java.lang.Object r7 = r5.getValue()
            java.util.List r7 = (java.util.List) r7
            int r7 = r7.size()
            if (r7 == 0) goto L_0x00c4
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r7 = r0.mKits
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r15 = new com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem
            r9 = 999(0x3e7, float:1.4E-42)
            java.lang.Object r8 = r5.getKey()
            java.lang.String r8 = (java.lang.String) r8
            int r8 = com.didichuxing.doraemonkit.util.DokitUtil.getStringId(r8)
            java.lang.String r10 = com.didichuxing.doraemonkit.util.DokitUtil.getString(r8)
            java.lang.String r8 = "DokitUtil.getString(Doki…l.getStringId(group.key))"
            kotlin.jvm.internal.k.b(r10, r8)
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 12
            r16 = 0
            r8 = r15
            r17 = r1
            r1 = r15
            r15 = r16
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            r7.add(r1)
            java.lang.Object r1 = r5.getValue()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            r7 = 0
            java.util.Iterator r8 = r1.iterator()
        L_0x00a8:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00c3
            java.lang.Object r9 = r8.next()
            r10 = r9
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r10 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r10
            r11 = 0
            boolean r12 = r10.getChecked()
            if (r12 == 0) goto L_0x00c1
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r12 = r0.mKits
            r12.add(r10)
        L_0x00c1:
            goto L_0x00a8
        L_0x00c3:
            goto L_0x00c9
        L_0x00c4:
            r17 = r1
            goto L_0x00c9
        L_0x00c7:
            r17 = r1
        L_0x00c9:
            r1 = r17
            goto L_0x0015
        L_0x00ce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.generateData():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0042, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_UI) != false) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004b, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_PERFORMANCE) != false) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0054, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_COMM) != false) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0057, code lost:
        r17 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
        if (((java.util.List) r5.getValue()).size() == 0) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006f, code lost:
        r7 = r0.mKits;
        r10 = com.didichuxing.doraemonkit.util.DokitUtil.getString(com.didichuxing.doraemonkit.util.DokitUtil.getStringId((java.lang.String) r5.getKey()));
        kotlin.jvm.internal.k.b(r10, "DokitUtil.getString(Doki…l.getStringId(group.key))");
        r17 = r1;
        r1 = r8;
        r8 = new com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem(999, r10, false, (java.lang.String) null, (com.didichuxing.doraemonkit.kit.AbstractKit) null, 12, (kotlin.jvm.internal.DefaultConstructorMarker) null);
        r7.add(r1);
        r8 = ((java.lang.Iterable) r5.getValue()).iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00aa, code lost:
        if (r8.hasNext() == false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ac, code lost:
        r0.mKits.add((com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r8.next());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00bc, code lost:
        r17 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c1, code lost:
        r1 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0039, code lost:
        if (r7.equals(com.didichuxing.doraemonkit.constant.DokitConstant.GROUP_ID_PLATFORM) != false) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void reSetKits(boolean r19) {
        /*
            r18 = this;
            r0 = r18
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r1 = r0.mKits
            r1.clear()
            if (r19 == 0) goto L_0x00c9
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r1 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            r2 = 0
            java.util.Set r3 = r1.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0014:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x00c6
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r5 = r4
            r6 = 0
            java.lang.Object r7 = r5.getKey()
            java.lang.String r7 = (java.lang.String) r7
            int r8 = r7.hashCode()
            switch(r8) {
                case -1858998038: goto L_0x005a;
                case -1812529378: goto L_0x004e;
                case -746547417: goto L_0x0045;
                case 570131901: goto L_0x003c;
                case 1742937308: goto L_0x0033;
                default: goto L_0x002f;
            }
        L_0x002f:
            r17 = r1
            goto L_0x00c1
        L_0x0033:
            java.lang.String r8 = "dk_category_platform"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
        L_0x003b:
            goto L_0x0063
        L_0x003c:
            java.lang.String r8 = "dk_category_ui"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
            goto L_0x003b
        L_0x0045:
            java.lang.String r8 = "dk_category_performance"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
            goto L_0x003b
        L_0x004e:
            java.lang.String r8 = "dk_category_comms"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
            goto L_0x003b
        L_0x0057:
            r17 = r1
            goto L_0x00c1
        L_0x005a:
            java.lang.String r8 = "dk_category_weex"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x00bf
            goto L_0x003b
        L_0x0063:
            java.lang.Object r7 = r5.getValue()
            java.util.List r7 = (java.util.List) r7
            int r7 = r7.size()
            if (r7 == 0) goto L_0x00bc
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r7 = r0.mKits
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r15 = new com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem
            r9 = 999(0x3e7, float:1.4E-42)
            java.lang.Object r8 = r5.getKey()
            java.lang.String r8 = (java.lang.String) r8
            int r8 = com.didichuxing.doraemonkit.util.DokitUtil.getStringId(r8)
            java.lang.String r10 = com.didichuxing.doraemonkit.util.DokitUtil.getString(r8)
            java.lang.String r8 = "DokitUtil.getString(Doki…l.getStringId(group.key))"
            kotlin.jvm.internal.k.b(r10, r8)
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 12
            r16 = 0
            r8 = r15
            r17 = r1
            r1 = r15
            r15 = r16
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            r7.add(r1)
            java.lang.Object r1 = r5.getValue()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            r7 = 0
            java.util.Iterator r8 = r1.iterator()
        L_0x00a6:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00bb
            java.lang.Object r9 = r8.next()
            r10 = r9
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r10 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r10
            r11 = 0
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r12 = r0.mKits
            r12.add(r10)
            goto L_0x00a6
        L_0x00bb:
            goto L_0x00c1
        L_0x00bc:
            r17 = r1
            goto L_0x00c1
        L_0x00bf:
            r17 = r1
        L_0x00c1:
            r1 = r17
            goto L_0x0014
        L_0x00c6:
            r17 = r1
            goto L_0x00cc
        L_0x00c9:
            r18.generateData()
        L_0x00cc:
            com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerAdapter r1 = r0.mAdapter
            if (r1 != 0) goto L_0x00d6
            java.lang.String r2 = "mAdapter"
            kotlin.jvm.internal.k.t(r2)
        L_0x00d6:
            r1.notifyDataSetChanged()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.reSetKits(boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0068, code lost:
        r7 = new com.didichuxing.doraemonkit.kit.toolpanel.KitGroupBean((java.lang.String) r5.getKey(), new java.util.ArrayList());
        r0.add(r7);
        r10 = ((java.lang.Iterable) r5.getValue()).iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x008b, code lost:
        if (r10.hasNext() == false) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x008d, code lost:
        r12 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r10.next();
        r14 = r7.getKits();
        r16 = r12.getKit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009f, code lost:
        if (r16 != null) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a1, code lost:
        kotlin.jvm.internal.k.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a4, code lost:
        r17 = r1;
        r1 = r16.getClass().getCanonicalName();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ae, code lost:
        if (r1 != null) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b0, code lost:
        kotlin.jvm.internal.k.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b3, code lost:
        kotlin.jvm.internal.k.b(r1, "it.kit!!.javaClass.canonicalName!!");
        r14.add(new com.didichuxing.doraemonkit.kit.toolpanel.KitBean(r1, r12.getChecked(), r12.getKit().innerKitId()));
        r2 = r2;
        r1 = r17;
        r3 = r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void saveSystemKits() {
        /*
            r20 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r1 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            r2 = 0
            java.util.Set r3 = r1.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0010:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x00eb
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r5 = r4
            r6 = 0
            java.lang.Object r7 = r5.getKey()
            java.lang.String r7 = (java.lang.String) r7
            int r8 = r7.hashCode()
            switch(r8) {
                case -1858998038: goto L_0x005f;
                case -1812529378: goto L_0x004e;
                case -746547417: goto L_0x0045;
                case 570131901: goto L_0x003c;
                case 1742937308: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            r17 = r1
            r16 = r2
            r19 = r3
            goto L_0x00e2
        L_0x0033:
            java.lang.String r8 = "dk_category_platform"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
        L_0x003b:
            goto L_0x0068
        L_0x003c:
            java.lang.String r8 = "dk_category_ui"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
            goto L_0x003b
        L_0x0045:
            java.lang.String r8 = "dk_category_performance"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
            goto L_0x003b
        L_0x004e:
            java.lang.String r8 = "dk_category_comms"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0057
            goto L_0x003b
        L_0x0057:
            r17 = r1
            r16 = r2
            r19 = r3
            goto L_0x00e2
        L_0x005f:
            java.lang.String r8 = "dk_category_weex"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x00dc
            goto L_0x003b
        L_0x0068:
            java.lang.Object r7 = r5.getKey()
            java.lang.String r7 = (java.lang.String) r7
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            com.didichuxing.doraemonkit.kit.toolpanel.KitGroupBean r9 = new com.didichuxing.doraemonkit.kit.toolpanel.KitGroupBean
            r9.<init>(r7, r8)
            r7 = r9
            r0.add(r7)
            java.lang.Object r8 = r5.getValue()
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            r9 = 0
            java.util.Iterator r10 = r8.iterator()
        L_0x0087:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x00d5
            java.lang.Object r11 = r10.next()
            r12 = r11
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r12 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r12
            r13 = 0
            java.util.List r14 = r7.getKits()
            com.didichuxing.doraemonkit.kit.toolpanel.KitBean r15 = new com.didichuxing.doraemonkit.kit.toolpanel.KitBean
            com.didichuxing.doraemonkit.kit.AbstractKit r16 = r12.getKit()
            if (r16 != 0) goto L_0x00a4
            kotlin.jvm.internal.k.n()
        L_0x00a4:
            java.lang.Class r16 = r16.getClass()
            r17 = r1
            java.lang.String r1 = r16.getCanonicalName()
            if (r1 != 0) goto L_0x00b3
            kotlin.jvm.internal.k.n()
        L_0x00b3:
            r16 = r2
            java.lang.String r2 = "it.kit!!.javaClass.canonicalName!!"
            kotlin.jvm.internal.k.b(r1, r2)
            boolean r2 = r12.getChecked()
            com.didichuxing.doraemonkit.kit.AbstractKit r18 = r12.getKit()
            r19 = r3
            java.lang.String r3 = r18.innerKitId()
            r15.<init>(r1, r2, r3)
            r14.add(r15)
            r2 = r16
            r1 = r17
            r3 = r19
            goto L_0x0087
        L_0x00d5:
            r17 = r1
            r16 = r2
            r19 = r3
            goto L_0x00e2
        L_0x00dc:
            r17 = r1
            r16 = r2
            r19 = r3
        L_0x00e2:
            r2 = r16
            r1 = r17
            r3 = r19
            goto L_0x0010
        L_0x00eb:
            r17 = r1
            r16 = r2
            java.lang.String r1 = com.blankj.utilcode.util.k.j(r0)
            com.didichuxing.doraemonkit.constant.DokitConstant r2 = com.didichuxing.doraemonkit.constant.DokitConstant.INSTANCE
            java.lang.String r2 = r2.getSYSTEM_KITS_BAK_PATH()
            r3 = 0
            com.blankj.utilcode.util.i.g(r2, r1, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.saveSystemKits():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
        r2 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS.get(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0053, code lost:
        if (r2 == null) goto L_0x000a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0055, code lost:
        r2.clear();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void reGroupForKit() {
        /*
            r8 = this;
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r0 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x000a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005a
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            int r2 = r1.hashCode()
            switch(r2) {
                case -1858998038: goto L_0x0042;
                case -1812529378: goto L_0x0039;
                case -746547417: goto L_0x0030;
                case 570131901: goto L_0x0027;
                case 1742937308: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x0058
        L_0x001e:
            java.lang.String r2 = "dk_category_platform"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
        L_0x0026:
            goto L_0x004b
        L_0x0027:
            java.lang.String r2 = "dk_category_ui"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x0030:
            java.lang.String r2 = "dk_category_performance"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x0039:
            java.lang.String r2 = "dk_category_comms"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x0042:
            java.lang.String r2 = "dk_category_weex"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0026
        L_0x004b:
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r2 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            java.lang.Object r2 = r2.get(r1)
            java.util.List r2 = (java.util.List) r2
            if (r2 == 0) goto L_0x0058
            r2.clear()
        L_0x0058:
            goto L_0x000a
        L_0x005a:
            java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem> r0 = r8.mKits
            r1 = 0
            java.util.Iterator r2 = r0.iterator()
        L_0x0061:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x008a
            java.lang.Object r3 = r2.next()
            r4 = r3
            com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem r4 = (com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem) r4
            r5 = 0
            int r6 = r4.getItemType()
            r7 = 201(0xc9, float:2.82E-43)
            if (r6 != r7) goto L_0x0088
            java.util.LinkedHashMap<java.lang.String, java.util.List<com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem>> r6 = com.didichuxing.doraemonkit.constant.DokitConstant.GLOBAL_KITS
            java.lang.String r7 = r4.getGroupName()
            java.lang.Object r6 = r6.get(r7)
            java.util.List r6 = (java.util.List) r6
            if (r6 == 0) goto L_0x0088
            r6.add(r4)
        L_0x0088:
            goto L_0x0061
        L_0x008a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.reGroupForKit():void");
    }

    /* access modifiers changed from: private */
    public final void dealBack() {
        if (IS_EDIT) {
            showDialog((DialogProvider) new ConfirmDialogProvider(DokitUtil.getString(R.string.dk_toolpanel_dialog_edit_tip), new DokitManagerFragment$dealBack$1(this)));
        } else {
            finish();
        }
        IS_EDIT = false;
    }

    private final void dealTitleBar() {
        int i = R.id.tv_reset;
        TextView textView = (TextView) _$_findCachedViewById(i);
        k.b(textView, "tv_reset");
        textView.setVisibility(8);
        ((LinearLayout) _$_findCachedViewById(R.id.ll_back)).setOnClickListener(new DokitManagerFragment$dealTitleBar$1(this));
        ((TextView) _$_findCachedViewById(R.id.tv_edit)).setOnClickListener(new DokitManagerFragment$dealTitleBar$2(this));
        ((TextView) _$_findCachedViewById(i)).setOnClickListener(new DokitManagerFragment$dealTitleBar$3(this));
    }

    private final void initView() {
        dealTitleBar();
        DokitManagerAdapter dokitManagerAdapter = new DokitManagerAdapter(this.mKits);
        this.mAdapter = dokitManagerAdapter;
        if (dokitManagerAdapter == null) {
            k.t("mAdapter");
        }
        dokitManagerAdapter.getDraggableModule().setDragEnabled(false);
        DokitManagerAdapter dokitManagerAdapter2 = this.mAdapter;
        if (dokitManagerAdapter2 == null) {
            k.t("mAdapter");
        }
        dokitManagerAdapter2.getDraggableModule().setOnItemDragListener(new DokitManagerFragment$initView$1(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        DokitManagerAdapter dokitManagerAdapter3 = this.mAdapter;
        if (dokitManagerAdapter3 == null) {
            k.t("mAdapter");
        }
        dokitManagerAdapter3.setGridSpanSizeLookup(DokitManagerFragment$initView$2.INSTANCE);
        DokitManagerAdapter dokitManagerAdapter4 = this.mAdapter;
        if (dokitManagerAdapter4 == null) {
            k.t("mAdapter");
        }
        dokitManagerAdapter4.setOnItemClickListener(new DokitManagerFragment$initView$3(this));
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(getActivity());
        FragmentActivity activity = getActivity();
        if (activity == null) {
            k.n();
        }
        int i = R.color.dk_color_E5E5E5;
        HorizontalDividerItemDecoration horizontalDividerItemDecoration = ((HorizontalDividerItemDecoration.Builder) ((HorizontalDividerItemDecoration.Builder) ((HorizontalDividerItemDecoration.Builder) builder.color(ContextCompat.getColor(activity, i))).size(1)).showLastDivider()).build();
        VerticalDividerItemDecoration.Builder builder2 = new VerticalDividerItemDecoration.Builder(getActivity());
        FragmentActivity activity2 = getActivity();
        if (activity2 == null) {
            k.n();
        }
        VerticalDividerItemDecoration verticalDividerItemDecoration = ((VerticalDividerItemDecoration.Builder) ((VerticalDividerItemDecoration.Builder) ((VerticalDividerItemDecoration.Builder) builder2.color(ContextCompat.getColor(activity2, i))).size(1)).showLastDivider()).build();
        int i2 = R.id.rv_kits;
        ((RecyclerView) _$_findCachedViewById(i2)).addItemDecoration(horizontalDividerItemDecoration);
        ((RecyclerView) _$_findCachedViewById(i2)).addItemDecoration(verticalDividerItemDecoration);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(i2);
        k.b(recyclerView, "rv_kits");
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(i2);
        k.b(recyclerView2, "rv_kits");
        DokitManagerAdapter dokitManagerAdapter5 = this.mAdapter;
        if (dokitManagerAdapter5 == null) {
            k.t("mAdapter");
        }
        recyclerView2.setAdapter(dokitManagerAdapter5);
    }

    /* access modifiers changed from: protected */
    public boolean onBackPressed() {
        dealBack();
        return true;
    }

    /* compiled from: DokitManagerFragment.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean getIS_EDIT() {
            return DokitManagerFragment.IS_EDIT;
        }

        public final void setIS_EDIT(boolean z) {
            DokitManagerFragment.IS_EDIT = z;
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        DokitManagerAdapter dokitManagerAdapter = this.mAdapter;
        if (dokitManagerAdapter == null) {
            k.t("mAdapter");
        }
        dokitManagerAdapter.setContext((Context) null);
        _$_clearFindViewByIdCache();
    }
}
