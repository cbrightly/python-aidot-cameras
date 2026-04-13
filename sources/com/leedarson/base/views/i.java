package com.leedarson.base.views;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.leedarson.base.utils.d;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$style;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: UpgradeDialogView */
public class i extends Dialog {
    public static int c = -9515841;
    public static ChangeQuickRedirect changeQuickRedirect;
    private View a1;
    LinearLayout a2;
    private View d;
    private View f;
    private LDSTextView p0;
    private String p1 = "";
    /* access modifiers changed from: private */
    public Context p2;
    private ListView p3;
    b p4;
    private LDSTextView q;
    private LDSTextView x;
    private LDSTextView y;
    private LDSTextView z;
    private View z4;

    public i(Context context, String id) {
        super(context, R$style.Theme_dialog);
        this.p1 = id;
        this.p2 = context;
    }

    private ShapeDrawable b(String str, int i, boolean z2, boolean bottomRound) {
        Object[] objArr = {str, new Integer(i), new Byte(z2 ? (byte) 1 : 0), new Byte(bottomRound ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        Class[] clsArr = {String.class, Integer.TYPE, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 814, clsArr, ShapeDrawable.class);
        if (proxy.isSupported) {
            return (ShapeDrawable) proxy.result;
        }
        int radius = i;
        String color = str;
        boolean isFill = z2;
        float[] outerRadii = {(float) radius, (float) radius, (float) radius, (float) radius, (float) radius, (float) radius, (float) radius, (float) radius};
        if (!bottomRound) {
            outerRadii[4] = 0.0f;
            outerRadii[5] = 0.0f;
            outerRadii[6] = 0.0f;
            outerRadii[7] = 0.0f;
        }
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(outerRadii, (RectF) null, (float[]) null));
        drawable.getPaint().setColor(Color.parseColor(color));
        drawable.getPaint().setAntiAlias(true);
        if (isFill) {
            drawable.getPaint().setStyle(Paint.Style.FILL);
        } else {
            drawable.getPaint().setStyle(Paint.Style.STROKE);
        }
        return drawable;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 815, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setContentView(R$layout.upgrade_dialog_layout);
            setCanceledOnTouchOutside(false);
            this.q = (LDSTextView) findViewById(R$id.upgrade_tip_title_tv);
            this.x = (LDSTextView) findViewById(R$id.upgrade_tip_dialog_tv);
            this.y = (LDSTextView) findViewById(R$id.tv_content_tip);
            this.z = (LDSTextView) findViewById(R$id.upgrade_later_btn_tv);
            this.p0 = (LDSTextView) findViewById(R$id.upgrade_sure_btn_tv);
            this.a1 = findViewById(R$id.upgrade_view_line);
            this.z4 = findViewById(R$id.upgrade_view_line0);
            this.a2 = (LinearLayout) findViewById(R$id.ll_dialog_content);
            this.p3 = (ListView) findViewById(R$id.lv_content);
            this.d = findViewById(R$id.later_btn_layout);
            this.f = findViewById(R$id.sure_btn_layout);
            c();
        }
    }

    private void c() {
        int buttonHeight;
        int rootLayoutPaddingBottom;
        int btnLayoutHeight;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, new Class[0], Void.TYPE).isSupported) {
            String contextTextColor = SharePreferenceUtils.getPrefString(getContext(), "contextTextColor", "");
            String titleTextColor = SharePreferenceUtils.getPrefString(getContext(), "titleTextColor", "");
            String okTextColor = SharePreferenceUtils.getPrefString(getContext(), "okTextColor", "");
            String cancelTextColor = SharePreferenceUtils.getPrefString(getContext(), "cancelTextColor", "");
            String okBgColor = SharePreferenceUtils.getPrefString(getContext(), "okBgColor", "");
            String cancelBgColor = SharePreferenceUtils.getPrefString(getContext(), "cancelBgColor", "");
            boolean cancelBgFill = SharePreferenceUtils.getPrefBoolean(getContext(), "cancelBgFill", true);
            int buttonRadius = SharePreferenceUtils.getPrefInt(getContext(), "buttonRadius", 10);
            int buttonHeight2 = SharePreferenceUtils.getPrefInt(getContext(), "buttonHeight", 0);
            String surfaceMain = SharePreferenceUtils.getPrefString(getContext(), "surfaceMain", "");
            String themeColor = SharePreferenceUtils.getPrefString(getContext(), "themeColor", "");
            String dialogTitle = SharePreferenceUtils.getPrefString(getContext(), "dialogTitle", "");
            String dialogContent = SharePreferenceUtils.getPrefString(getContext(), "dialogContent", "");
            String str = titleTextColor;
            String secondaryMain = SharePreferenceUtils.getPrefString(getContext(), "secondaryMain", "");
            String str2 = themeColor;
            String str3 = "";
            boolean hasDot = SharePreferenceUtils.getPrefBoolean(getContext(), "hasDot", true);
            int buttonHeight3 = buttonHeight2;
            boolean showBottomDialog = SharePreferenceUtils.getPrefBoolean(getContext(), "showBottomDialog", false);
            if (!TextUtils.isEmpty(surfaceMain)) {
                this.a2.setBackground(b(surfaceMain, d.b(this.p2, 10.0f), true, true));
            }
            if (!TextUtils.isEmpty(contextTextColor)) {
                this.q.setTextColor(Color.parseColor(dialogTitle));
                this.x.setTextColor(Color.parseColor(dialogContent));
            }
            if (!TextUtils.isEmpty(okTextColor)) {
                this.p0.setTextColor(Color.parseColor(okTextColor));
            }
            if (!TextUtils.isEmpty(cancelTextColor)) {
                this.z.setTextColor(Color.parseColor(cancelTextColor));
            }
            if (!TextUtils.isEmpty(okBgColor)) {
                this.p0.setBackground(b(okBgColor, buttonRadius, true, true));
                String str4 = dialogTitle;
                this.a1.setVisibility(4);
                this.z4.setVisibility(4);
            }
            if (!TextUtils.isEmpty(cancelBgColor)) {
                this.z.setBackground(b(cancelBgColor, buttonRadius, cancelBgFill, true));
                this.a1.setVisibility(4);
                this.z4.setVisibility(4);
            }
            Resources resources = this.p2.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            float f2 = dm.density;
            Resources resources2 = resources;
            int width3 = (int) (((float) dm.widthPixels) * 0.72f);
            String str5 = contextTextColor;
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = width3;
            int i = width3;
            getWindow().setAttributes(lp);
            WindowManager.LayoutParams layoutParams = lp;
            b bVar = new b();
            this.p4 = bVar;
            this.p3.setAdapter(bVar);
            this.p4.a(dialogContent);
            this.p4.c(hasDot);
            this.p4.d(secondaryMain);
            ViewGroup.LayoutParams layoutParams2 = this.a1.getLayoutParams();
            if (buttonHeight3 != 0) {
                buttonHeight = buttonHeight3;
                layoutParams2.height = buttonHeight;
            } else {
                buttonHeight = buttonHeight3;
            }
            if (showBottomDialog) {
                ViewGroup.LayoutParams layoutParams3 = layoutParams2;
                int i2 = buttonHeight;
                getWindow().setGravity(80);
                int width4 = dm.widthPixels;
                WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                lp1.width = width4;
                int i3 = width4;
                getWindow().setAttributes(lp1);
                WindowManager.LayoutParams layoutParams4 = lp1;
                String str6 = secondaryMain;
                this.a2.setBackground(b(surfaceMain, 30, true, false));
            } else {
                int i4 = buttonHeight;
                String str7 = secondaryMain;
            }
            String repositoryName = SharePreferenceUtils.getPrefString(getContext(), "repositoryName", str3);
            int btnLayoutHeight2 = d.b(getContext(), 44.0f);
            if ("C610-Innr".equals(repositoryName)) {
                btnLayoutHeight = d.b(getContext(), 50.0f);
                boolean z2 = showBottomDialog;
                String str8 = okTextColor;
                this.z.setBackground(b(cancelBgColor, btnLayoutHeight / 2, cancelBgFill, true));
                this.p0.setBackground(b(okBgColor, btnLayoutHeight / 2, true, true));
                rootLayoutPaddingBottom = d.b(getContext(), 20.0f);
            } else {
                boolean z3 = showBottomDialog;
                String str9 = okTextColor;
                if ("leedarson-Leedarson".equals(repositoryName) || "leedarson-NewLeedarson".equals(repositoryName)) {
                    rootLayoutPaddingBottom = d.b(getContext(), 16.0f);
                    btnLayoutHeight = btnLayoutHeight2;
                } else {
                    btnLayoutHeight = btnLayoutHeight2;
                    rootLayoutPaddingBottom = 0;
                }
            }
            Log.d("CZB", "btnLayoutHeight=" + btnLayoutHeight);
            LinearLayout btnLayout = (LinearLayout) findViewById(R$id.btnLayout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnLayout.getLayoutParams();
            params.height = btnLayoutHeight;
            btnLayout.setLayoutParams(params);
            String str10 = repositoryName;
            this.a2.setPadding(0, 0, 0, rootLayoutPaddingBottom);
        }
    }

    /* compiled from: UpgradeDialogView */
    public class b extends BaseAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;
        List<String> c;
        String d;
        String f;
        boolean q;

        private b() {
            this.c = new ArrayList();
            this.d = "#333333";
            this.f = "#2ba68b";
            this.q = true;
        }

        public void c(boolean hasShowDot) {
            this.q = hasShowDot;
        }

        public void d(String secondaryColor) {
            this.f = secondaryColor;
        }

        public void b(List<String> data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETPASSWORD_RESP, new Class[]{List.class}, Void.TYPE).isSupported) {
                this.c = data;
                notifyDataSetChanged();
            }
        }

        public void a(String color) {
            this.d = color;
        }

        public int getCount() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 820, new Class[0], Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.c.size();
        }

        public Object getItem(int position) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 821, new Class[]{Integer.TYPE}, Object.class);
            return proxy.isSupported ? proxy.result : this.c.get(position);
        }

        public boolean isEnabled(int position) {
            return false;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View view, ViewGroup viewGroup) {
            Object[] objArr = {new Integer(position), view, viewGroup};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 822, new Class[]{Integer.TYPE, View.class, ViewGroup.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            View convertView = View.inflate(i.this.p2, R$layout.layout_dialog_tip, (ViewGroup) null);
            LDSTextView tvTip = (LDSTextView) convertView.findViewById(R$id.upgrade_tip);
            tvTip.setText(this.c.get(position));
            tvTip.setTextColor(Color.parseColor(this.d));
            RoundDotView roundDotView = (RoundDotView) convertView.findViewById(R$id.upgrade_round_view);
            roundDotView.setColor(Color.parseColor(this.f));
            if (!this.q) {
                roundDotView.setVisibility(8);
            }
            roundDotView.invalidate();
            return convertView;
        }
    }

    public void d(String str, String str2, String str3, String str4, int i, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, new Integer(i), onClickListener, onClickListener2}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_RESP, new Class[]{cls, cls, cls, cls, Integer.TYPE, View.OnClickListener.class, View.OnClickListener.class}, Void.TYPE).isSupported) {
            String content = str2;
            View.OnClickListener leftClickListener = onClickListener;
            String rightStr = str4;
            String title = str;
            String leftStr = str3;
            View.OnClickListener rightClickListener = onClickListener2;
            int i2 = i;
            show();
            if (title == null || title.isEmpty()) {
                this.q.setVisibility(8);
            } else {
                this.q.setText(title);
            }
            this.x.setText(content);
            List<String> strings = new ArrayList<>();
            if (content != null) {
                strings = Arrays.asList(content.split("\\n"));
            }
            this.p4.b(strings);
            if (leftStr == null || leftStr.isEmpty()) {
                this.d.setVisibility(8);
                this.a1.setVisibility(4);
            } else {
                this.d.setVisibility(0);
                this.z.setText(leftStr);
                if (leftClickListener != null) {
                    this.z.setOnClickListener(leftClickListener);
                }
            }
            if (rightStr == null || rightStr.isEmpty()) {
                this.f.setVisibility(8);
                this.a1.setVisibility(4);
                return;
            }
            this.p0.setText(rightStr);
            if (rightClickListener != null) {
                this.p0.setOnClickListener(rightClickListener);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0081 A[Catch:{ Exception -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00af A[SYNTHETIC, Splitter:B:23:0x00af] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00de A[Catch:{ Exception -> 0x0107 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, android.view.View.OnClickListener r22, android.view.View.OnClickListener r23) {
        /*
            r16 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 7
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r17
            r10 = 1
            r2[r10] = r18
            r3 = 2
            r2[r3] = r19
            r4 = 3
            r2[r4] = r20
            r11 = 4
            r2[r11] = r21
            r5 = 5
            r2[r5] = r22
            r6 = 6
            r2[r6] = r23
            com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
            java.lang.Class[] r1 = new java.lang.Class[r1]
            r1[r9] = r0
            r1[r10] = r0
            r1[r3] = r0
            r1[r4] = r0
            r1[r11] = r0
            java.lang.Class<android.view.View$OnClickListener> r0 = android.view.View.OnClickListener.class
            r1[r5] = r0
            java.lang.Class<android.view.View$OnClickListener> r0 = android.view.View.OnClickListener.class
            r1[r6] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 818(0x332, float:1.146E-42)
            r3 = r16
            r4 = r7
            r7 = r1
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0042
            return
        L_0x0042:
            r1 = r16
            r2 = r18
            r3 = r22
            r4 = r20
            r5 = r17
            r6 = r19
            r7 = r23
            r8 = r21
            r1.show()     // Catch:{ Exception -> 0x0107 }
            com.leedarson.base.views.common.LDSTextView r0 = r1.y     // Catch:{ Exception -> 0x0107 }
            r0.setVisibility(r9)     // Catch:{ Exception -> 0x0107 }
            r0 = 8
            if (r5 == 0) goto L_0x006b
            boolean r12 = r5.isEmpty()     // Catch:{ Exception -> 0x0107 }
            if (r12 == 0) goto L_0x0065
            goto L_0x006b
        L_0x0065:
            com.leedarson.base.views.common.LDSTextView r12 = r1.q     // Catch:{ Exception -> 0x0107 }
            r12.setText(r5)     // Catch:{ Exception -> 0x0107 }
            goto L_0x0070
        L_0x006b:
            com.leedarson.base.views.common.LDSTextView r12 = r1.q     // Catch:{ Exception -> 0x0107 }
            r12.setVisibility(r0)     // Catch:{ Exception -> 0x0107 }
        L_0x0070:
            com.leedarson.base.views.common.LDSTextView r12 = r1.x     // Catch:{ Exception -> 0x0107 }
            r12.setText(r2)     // Catch:{ Exception -> 0x0107 }
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ Exception -> 0x0107 }
            r12.<init>()     // Catch:{ Exception -> 0x0107 }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ Exception -> 0x0107 }
            r13.<init>()     // Catch:{ Exception -> 0x0107 }
            if (r2 == 0) goto L_0x00a6
            java.lang.String[] r14 = r2.split(r8)     // Catch:{ Exception -> 0x0107 }
            if (r14 == 0) goto L_0x0090
            com.leedarson.base.views.common.LDSTextView r15 = r1.y     // Catch:{ Exception -> 0x0107 }
            int r11 = r14.length     // Catch:{ Exception -> 0x0107 }
            int r11 = r11 - r10
            r11 = r14[r11]     // Catch:{ Exception -> 0x0107 }
            r15.setText(r11)     // Catch:{ Exception -> 0x0107 }
        L_0x0090:
            java.util.List r11 = java.util.Arrays.asList(r14)     // Catch:{ Exception -> 0x0107 }
            r12 = r11
            r13.addAll(r12)     // Catch:{ Exception -> 0x0107 }
            int r11 = r13.size()     // Catch:{ Exception -> 0x0107 }
            if (r11 <= 0) goto L_0x00a6
            int r11 = r13.size()     // Catch:{ Exception -> 0x0107 }
            int r11 = r11 - r10
            r13.remove(r11)     // Catch:{ Exception -> 0x0107 }
        L_0x00a6:
            com.leedarson.base.views.i$b r10 = r1.p4     // Catch:{ Exception -> 0x0107 }
            r10.b(r13)     // Catch:{ Exception -> 0x0107 }
            java.lang.String r10 = "#EAA20D"
            if (r6 == 0) goto L_0x00d1
            boolean r11 = r6.isEmpty()     // Catch:{ Exception -> 0x0107 }
            if (r11 == 0) goto L_0x00b6
            goto L_0x00d1
        L_0x00b6:
            android.view.View r11 = r1.d     // Catch:{ Exception -> 0x0107 }
            r11.setVisibility(r9)     // Catch:{ Exception -> 0x0107 }
            com.leedarson.base.views.common.LDSTextView r9 = r1.z     // Catch:{ Exception -> 0x0107 }
            r9.setText(r6)     // Catch:{ Exception -> 0x0107 }
            com.leedarson.base.views.common.LDSTextView r9 = r1.z     // Catch:{ Exception -> 0x0107 }
            int r11 = android.graphics.Color.parseColor(r10)     // Catch:{ Exception -> 0x0107 }
            r9.setTextColor(r11)     // Catch:{ Exception -> 0x0107 }
            if (r3 == 0) goto L_0x00dc
            com.leedarson.base.views.common.LDSTextView r9 = r1.z     // Catch:{ Exception -> 0x0107 }
            r9.setOnClickListener(r3)     // Catch:{ Exception -> 0x0107 }
            goto L_0x00dc
        L_0x00d1:
            android.view.View r9 = r1.d     // Catch:{ Exception -> 0x0107 }
            r9.setVisibility(r0)     // Catch:{ Exception -> 0x0107 }
            android.view.View r9 = r1.a1     // Catch:{ Exception -> 0x0107 }
            r11 = 4
            r9.setVisibility(r11)     // Catch:{ Exception -> 0x0107 }
        L_0x00dc:
            if (r4 == 0) goto L_0x00fb
            boolean r9 = r4.isEmpty()     // Catch:{ Exception -> 0x0107 }
            if (r9 == 0) goto L_0x00e5
            goto L_0x00fb
        L_0x00e5:
            com.leedarson.base.views.common.LDSTextView r0 = r1.p0     // Catch:{ Exception -> 0x0107 }
            r0.setText(r4)     // Catch:{ Exception -> 0x0107 }
            com.leedarson.base.views.common.LDSTextView r0 = r1.p0     // Catch:{ Exception -> 0x0107 }
            int r9 = android.graphics.Color.parseColor(r10)     // Catch:{ Exception -> 0x0107 }
            r0.setTextColor(r9)     // Catch:{ Exception -> 0x0107 }
            if (r7 == 0) goto L_0x0106
            com.leedarson.base.views.common.LDSTextView r0 = r1.p0     // Catch:{ Exception -> 0x0107 }
            r0.setOnClickListener(r7)     // Catch:{ Exception -> 0x0107 }
            goto L_0x0106
        L_0x00fb:
            android.view.View r9 = r1.f     // Catch:{ Exception -> 0x0107 }
            r9.setVisibility(r0)     // Catch:{ Exception -> 0x0107 }
            android.view.View r0 = r1.a1     // Catch:{ Exception -> 0x0107 }
            r9 = 4
            r0.setVisibility(r9)     // Catch:{ Exception -> 0x0107 }
        L_0x0106:
            goto L_0x010b
        L_0x0107:
            r0 = move-exception
            r0.getMessage()
        L_0x010b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.i.e(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, android.view.View$OnClickListener, android.view.View$OnClickListener):void");
    }
}
