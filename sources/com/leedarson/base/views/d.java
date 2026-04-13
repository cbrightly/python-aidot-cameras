package com.leedarson.base.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$style;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BottomPopupWindow */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    /* access modifiers changed from: private */
    public Dialog b;
    private LDSTextView c;
    private LDSTextView d;
    private LinearLayout e;
    private ScrollView f;
    private boolean g = false;
    private List<c> h;
    private Display i;
    private View j;
    private boolean k = true;
    private boolean l = false;
    private int m = 0;

    /* compiled from: BottomPopupWindow */
    public interface b {
        void a(int i);
    }

    public d(Context context) {
        this.a = context;
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            this.i = windowManager.getDefaultDisplay();
        }
    }

    public d d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 632, new Class[0], d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        View view = LayoutInflater.from(this.a).inflate(R$layout.popup_window_bottom_layout, (ViewGroup) null);
        view.setMinimumWidth(this.i.getWidth() < this.i.getHeight() ? this.i.getWidth() : this.i.getHeight());
        this.f = (ScrollView) view.findViewById(R$id.scroll_view_layout_content);
        this.e = (LinearLayout) view.findViewById(R$id.layout_popup_window_content);
        this.c = (LDSTextView) view.findViewById(R$id.tv_popup_window_title);
        this.j = view.findViewById(R$id.view_line);
        LDSTextView lDSTextView = (LDSTextView) view.findViewById(R$id.tv_popup_window_cancel);
        this.d = lDSTextView;
        lDSTextView.setOnClickListener(new a());
        Dialog dialog = new Dialog(this.a, R$style.ActionGeneralDialog);
        this.b = dialog;
        dialog.setContentView(view);
        Window dialogWindow = this.b.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(81);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
        }
        return this;
    }

    /* compiled from: BottomPopupWindow */
    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 649, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            d.this.b.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public d j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 636, new Class[0], d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        this.d.setVisibility(8);
        return this;
    }

    public d b(String[] strItems, int color, b listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{strItems, new Integer(color), listener}, this, changeQuickRedirect, false, 641, new Class[]{String[].class, Integer.TYPE, b.class}, d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        if (this.h == null) {
            this.h = new ArrayList();
        }
        if (strItems != null && strItems.length > 0) {
            for (String cVar : strItems) {
                this.h.add(new c(cVar, color, listener));
            }
        }
        return this;
    }

    public d c(int defaultCheck, String[] strItems, int color, b listener) {
        Object[] objArr = {new Integer(defaultCheck), strItems, new Integer(color), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 642, new Class[]{cls, String[].class, cls, b.class}, d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        this.l = true;
        this.m = defaultCheck;
        if (this.h == null) {
            this.h = new ArrayList();
        }
        this.h.clear();
        if (strItems != null && strItems.length > 0) {
            for (int i2 = 0; i2 < strItems.length; i2++) {
                if (!this.l) {
                    this.h.add(new c(strItems[i2], color, listener));
                } else if (i2 == this.m) {
                    this.h.add(new c(true, strItems[i2], color, listener));
                } else {
                    this.h.add(new c(false, strItems[i2], color, listener));
                }
            }
        }
        return this;
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 644, new Class[0], Void.TYPE).isSupported) {
            k();
            this.b.show();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0017, code lost:
        r1 = r18;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k() {
        /*
            r18 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 645(0x285, float:9.04E-43)
            r2 = r18
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0017
            return
        L_0x0017:
            r1 = r18
            java.util.List<com.leedarson.base.views.d$c> r2 = r1.h
            if (r2 == 0) goto L_0x0111
            int r2 = r2.size()
            if (r2 > 0) goto L_0x0025
            goto L_0x0111
        L_0x0025:
            android.widget.LinearLayout r2 = r1.e
            r2.removeAllViews()
            java.util.List<com.leedarson.base.views.d$c> r2 = r1.h
            int r2 = r2.size()
            r3 = 7
            if (r2 < r3) goto L_0x004e
            boolean r3 = r1.k
            if (r3 == 0) goto L_0x004e
            android.widget.ScrollView r3 = r1.f
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r3 = (android.widget.LinearLayout.LayoutParams) r3
            android.view.Display r4 = r1.i
            int r4 = r4.getHeight()
            int r4 = r4 / 2
            r3.height = r4
            android.widget.ScrollView r4 = r1.f
            r4.setLayoutParams(r3)
        L_0x004e:
            r3 = 0
        L_0x004f:
            int r4 = r2 + -1
            if (r3 > r4) goto L_0x0110
            r4 = r3
            java.util.List<com.leedarson.base.views.d$c> r5 = r1.h
            java.lang.Object r5 = r5.get(r3)
            com.leedarson.base.views.d$c r5 = (com.leedarson.base.views.d.c) r5
            java.lang.String r6 = r5.a
            int r7 = r5.c
            com.leedarson.base.views.d$b r8 = r5.b
            android.content.Context r9 = r1.a
            android.view.LayoutInflater r9 = android.view.LayoutInflater.from(r9)
            int r10 = com.leedarson.module_base.R$layout.layout_dialog_sheet_item
            r11 = 0
            android.view.View r9 = r9.inflate(r10, r11)
            int r10 = com.leedarson.module_base.R$id.tvTip
            android.view.View r10 = r9.findViewById(r10)
            com.leedarson.base.views.common.LDSTextView r10 = (com.leedarson.base.views.common.LDSTextView) r10
            r10.setTextColor(r7)
            r10.setText(r6)
            int r11 = r5.d
            if (r11 != 0) goto L_0x0082
            goto L_0x0086
        L_0x0082:
            float r11 = (float) r11
            r10.setTextSize(r11)
        L_0x0086:
            android.content.Context r11 = r1.a
            android.content.res.Resources r11 = r11.getResources()
            android.util.DisplayMetrics r11 = r11.getDisplayMetrics()
            float r11 = r11.density
            r12 = 1113325568(0x425c0000, float:55.0)
            float r12 = r12 * r11
            r13 = 1056964608(0x3f000000, float:0.5)
            float r12 = r12 + r13
            int r12 = (int) r12
            com.leedarson.base.views.a r13 = new com.leedarson.base.views.a
            r13.<init>(r1, r4, r8)
            r10.setOnClickListener(r13)
            com.leedarson.base.views.b r13 = new com.leedarson.base.views.b
            r13.<init>(r1, r4, r8)
            r9.setOnClickListener(r13)
            int r13 = com.leedarson.module_base.R$id.imvCheckBox
            android.view.View r13 = r9.findViewById(r13)
            android.widget.ImageView r13 = (android.widget.ImageView) r13
            boolean r14 = r5.e
            if (r14 == 0) goto L_0x00c8
            r13.setVisibility(r0)
            android.content.Context r14 = r1.a
            android.content.res.Resources r14 = r14.getResources()
            int r15 = com.leedarson.module_base.R$drawable.ic_selected
            android.graphics.drawable.Drawable r14 = r14.getDrawable(r15)
            r13.setBackground(r14)
            goto L_0x00cd
        L_0x00c8:
            r14 = 8
            r13.setVisibility(r14)
        L_0x00cd:
            android.widget.LinearLayout$LayoutParams r14 = new android.widget.LinearLayout$LayoutParams
            r15 = -1
            r14.<init>(r15, r12)
            r9.setLayoutParams(r14)
            android.widget.LinearLayout r0 = r1.e
            r0.addView(r9)
            if (r3 < 0) goto L_0x0105
            int r0 = r2 + -1
            if (r3 >= r0) goto L_0x0105
            android.view.View r0 = new android.view.View
            android.content.Context r15 = r1.a
            r0.<init>(r15)
            java.lang.String r15 = "#D2D6D9"
            int r15 = android.graphics.Color.parseColor(r15)
            r0.setBackgroundColor(r15)
            android.widget.LinearLayout$LayoutParams r15 = new android.widget.LinearLayout$LayoutParams
            r16 = r2
            r2 = 1
            r17 = r4
            r4 = -1
            r15.<init>(r4, r2)
            r0.setLayoutParams(r15)
            android.widget.LinearLayout r2 = r1.e
            r2.addView(r0)
            goto L_0x0109
        L_0x0105:
            r16 = r2
            r17 = r4
        L_0x0109:
            int r3 = r3 + 1
            r2 = r16
            r0 = 0
            goto L_0x004f
        L_0x0110:
            return
        L_0x0111:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.d.k():void");
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: f */
    public /* synthetic */ void g(int index, b listener, View view) {
        Object[] objArr = {new Integer(index), listener, view};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 648, new Class[]{Integer.TYPE, b.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.m = index;
        listener.a(index);
        this.b.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: h */
    public /* synthetic */ void i(int index, b listener, View view) {
        Object[] objArr = {new Integer(index), listener, view};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 647, new Class[]{Integer.TYPE, b.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.m = index;
        listener.a(index);
        this.b.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* compiled from: BottomPopupWindow */
    public static class c {
        String a;
        b b;
        int c;
        int d = 0;
        boolean e = false;

        public c(String name, int color, b itemClickListener) {
            this.a = name;
            this.c = color;
            this.b = itemClickListener;
        }

        public c(boolean isSelected, String name, int color, b itemClickListener) {
            this.e = isSelected;
            this.a = name;
            this.c = color;
            this.b = itemClickListener;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 646(0x286, float:9.05E-43)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.app.Dialog r1 = r0.b
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isShowing()
            if (r1 == 0) goto L_0x0026
            android.app.Dialog r1 = r0.b
            r1.dismiss()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.d.e():void");
    }
}
