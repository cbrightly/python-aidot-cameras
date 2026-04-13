package com.leedarson.serviceimpl.camera;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowImageActivity extends BaseActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A4 = false;
    e B4;
    e C4;
    private View D4;
    /* access modifiers changed from: private */
    public View E4;
    /* access modifiers changed from: private */
    public View F4;
    private View G4;
    private LDSTextView H4;
    private LDSTextView I4;
    private LDSTextView J4;
    private ImageView K4;
    private LDSTextView L4;
    private int M4 = 0;
    private int N4 = 1;
    /* access modifiers changed from: private */
    public boolean O4 = true;
    /* access modifiers changed from: private */
    public boolean P4 = false;
    private List<String> a1 = new ArrayList();
    /* access modifiers changed from: private */
    public int a2;
    private ViewPager2 p0;
    private ShowImageAdapter p1;
    private String p2;
    private String p3;
    private String p4;
    private JSONArray z4;

    static /* synthetic */ void Y(ShowImageActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7452, new Class[]{ShowImageActivity.class}, Void.TYPE).isSupported) {
            x0.m0();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 7441, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setDarkMode(this);
            init();
        }
    }

    public int O() {
        return R$layout.activity_show_image;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7442, new Class[0], Void.TYPE).isSupported) {
            if (getIntent() == null) {
                finish();
            }
            this.a2 = getIntent().getIntExtra("position", 0);
            ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra("datas");
            this.a1 = stringArrayListExtra;
            if (stringArrayListExtra == null || stringArrayListExtra.size() == 0) {
                finish();
            }
            f0();
            this.p0 = (ViewPager2) findViewById(R$id.photo_viewpage);
            List<String> list = this.a1;
            boolean z = true;
            boolean z2 = this.M4 == 0;
            if (this.N4 != 1) {
                z = false;
            }
            ShowImageAdapter showImageAdapter = new ShowImageAdapter(this, list, z2, z);
            this.p1 = showImageAdapter;
            this.p0.setAdapter(showImageAdapter);
            this.p0.setCurrentItem(this.a2, false);
            this.p0.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onPageSelected(int position) {
                    Object[] objArr = {new Integer(position)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7453, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                        int unused = ShowImageActivity.this.a2 = position;
                        ShowImageActivity.Y(ShowImageActivity.this);
                    }
                }
            });
        }
    }

    public void R() {
    }

    private void m0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7443, new Class[0], Void.TYPE).isSupported) {
            if (this.M4 == 0 && TextUtils.isEmpty(this.p2)) {
                this.H4.setText(String.format(Locale.US, "%s/%s", new Object[]{Integer.valueOf(this.a2 + 1), Integer.valueOf(this.a1.size())}));
            }
        }
    }

    private void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7444, new Class[0], Void.TYPE).isSupported) {
            Intent intent = getIntent();
            this.p2 = intent.getStringExtra("title");
            this.p3 = intent.getStringExtra(FirebaseAnalytics.Param.CONTENT);
            this.p4 = intent.getStringExtra("customize");
            String buttonsStr = intent.getStringExtra("buttons");
            int intExtra = intent.getIntExtra("isHideNavbar", this.M4);
            this.M4 = intExtra;
            this.N4 = intent.getIntExtra("supportSaveToAlbum", intExtra);
            if (!TextUtils.isEmpty(buttonsStr)) {
                this.A4 = true;
                this.M4 = 0;
                try {
                    this.z4 = new JSONArray(buttonsStr);
                    for (int i = 0; i < this.z4.length(); i++) {
                        JSONObject item = this.z4.getJSONObject(i);
                        if (item.getString("name").equals("like")) {
                            e eVar = new e();
                            this.B4 = eVar;
                            eVar.a = item.getString("name");
                            this.B4.b = item.getString("normalTitle");
                            this.B4.c = item.getString("selectedTitle");
                            this.B4.d = item.getInt("selected");
                        } else if (item.getString("name").equals("comment")) {
                            e eVar2 = new e();
                            this.C4 = eVar2;
                            eVar2.a = item.getString("name");
                            this.C4.b = item.getString("normalTitle");
                            this.C4.c = item.getString("selectedTitle");
                            this.C4.d = item.getInt("selected");
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            this.E4 = findViewById(R$id.layout_title);
            this.F4 = findViewById(R$id.bottom_layout);
            this.H4 = (LDSTextView) findViewById(R$id.tv_title);
            this.I4 = (LDSTextView) findViewById(R$id.tv_like);
            this.J4 = (LDSTextView) findViewById(R$id.tv_comment);
            this.K4 = (ImageView) findViewById(R$id.iv_like);
            this.L4 = (LDSTextView) findViewById(R$id.tv_content);
            this.D4 = findViewById(R$id.like_layout);
            this.G4 = findViewById(R$id.text_layout);
            findViewById(R$id.btn_back).setOnClickListener(this);
            int i2 = R$id.btn_menu;
            findViewById(i2).setOnClickListener(this);
            findViewById(R$id.commentItemLayout).setOnClickListener(this);
            findViewById(R$id.likeItemLayout).setOnClickListener(this);
            findViewById(i2).setVisibility(8);
            if (this.M4 == 0) {
                StatusBarUtil.setColor(this, ViewCompat.MEASURED_STATE_MASK);
                StatusBarUtil.setDarkMode(this);
                this.E4.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                this.E4.setVisibility(0);
                ((FrameLayout.LayoutParams) this.E4.getLayoutParams()).topMargin = com.leedarson.serviceimpl.system.notch.b.b(this);
            } else {
                this.E4.setVisibility(8);
            }
            if (this.A4) {
                this.F4.setVisibility(0);
                e eVar3 = this.C4;
                if (eVar3 != null) {
                    this.J4.setText(eVar3.b);
                }
                e eVar4 = this.B4;
                if (eVar4 != null) {
                    this.I4.setText(eVar4.d == 0 ? eVar4.b : eVar4.c);
                    this.I4.setTextColor(this.B4.d == 0 ? -1 : -2137759);
                    this.K4.setImageResource(this.B4.d == 0 ? R$drawable.ic_unlike : R$drawable.ic_like);
                    this.K4.setImageTintList(this.B4.d == 0 ? ColorStateList.valueOf(-1) : null);
                }
            } else {
                this.F4.setVisibility(8);
            }
            if (!TextUtils.isEmpty(this.p2)) {
                this.H4.setText(this.p2);
            } else {
                m0();
            }
            if (!TextUtils.isEmpty(this.p3)) {
                this.L4.setText(this.p3);
                this.G4.setVisibility(0);
                return;
            }
            this.G4.setVisibility(8);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7445, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int id = v.getId();
        if (id == R$id.btn_back) {
            e eVar = this.B4;
            if (eVar != null) {
                g0(this.a2, "back", eVar.d, "click");
            }
            finish();
        } else if (id == R$id.btn_menu) {
            g0(this.a2, "menu", this.B4.d, "click");
            timber.log.a.g("LdsIM").a("menu被点击", new Object[0]);
        } else if (id == R$id.likeItemLayout) {
            e eVar2 = this.B4;
            if (eVar2.d == 0) {
                eVar2.d = 1;
            } else {
                eVar2.d = 0;
            }
            this.K4.setImageResource(eVar2.d == 0 ? R$drawable.ic_unlike : R$drawable.ic_like);
            int i = -1;
            this.K4.setImageTintList(this.B4.d == 0 ? ColorStateList.valueOf(-1) : null);
            LDSTextView lDSTextView = this.I4;
            if (this.B4.d != 0) {
                i = -2137759;
            }
            lDSTextView.setTextColor(i);
            g0(this.a2, "like", this.B4.d, "click");
        } else if (id == R$id.commentItemLayout) {
            timber.log.a.g("LdsIM").a("评论被点击", new Object[0]);
            finish();
            g0(this.a2, "comment", this.B4.d, "click");
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    private void g0(int current, String name, int select, String action) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(current), name, new Integer(select), action};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7446, new Class[]{cls2, cls, cls2, cls}, Void.TYPE).isSupported) {
            JSONObject json = new JSONObject();
            try {
                json.put("customize", (Object) this.p4);
                json.put("current", current);
                json.put("name", (Object) name);
                json.put("selected", select);
                json.put("action", (Object) action);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            j0(json.toString());
        }
    }

    private void j0(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 7447, new Class[]{String.class}, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Camera", "onPreviewMessage", message));
        }
    }

    public static class e {
        public String a;
        public String b;
        public String c;
        public int d;

        e() {
        }
    }

    public void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7448, new Class[0], Void.TYPE).isSupported) {
            if (!this.P4) {
                this.P4 = true;
                if (this.O4) {
                    e0();
                } else {
                    k0();
                }
            }
        }
    }

    private void e0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7449, new Class[0], Void.TYPE).isSupported) {
            int statusBarHeight = com.leedarson.serviceimpl.system.notch.b.b(this);
            int top = -this.E4.getLayoutParams().height;
            ValueAnimator animator1 = ValueAnimator.ofInt(new int[]{statusBarHeight, top});
            animator1.setDuration(300);
            animator1.addUpdateListener(new a(top));
            ValueAnimator animator2 = ValueAnimator.ofInt(new int[]{0, -this.D4.getLayoutParams().height});
            animator2.setDuration(300);
            animator2.addUpdateListener(new b());
            animator1.start();
            animator2.start();
        }
    }

    public class a implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        a(int i) {
            this.c = i;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 7454, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ShowImageActivity.this.E4.getLayoutParams();
                params.topMargin = value;
                ShowImageActivity.this.E4.setLayoutParams(params);
                if (value == this.c) {
                    boolean unused = ShowImageActivity.this.O4 = false;
                    boolean unused2 = ShowImageActivity.this.P4 = false;
                }
            }
        }
    }

    public class b implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 7455, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ShowImageActivity.this.F4.getLayoutParams();
                params.bottomMargin = value;
                ShowImageActivity.this.F4.setLayoutParams(params);
            }
        }
    }

    private void k0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7450, new Class[0], Void.TYPE).isSupported) {
            int statusBarHeight = com.leedarson.serviceimpl.system.notch.b.b(this);
            ValueAnimator animator1 = ValueAnimator.ofInt(new int[]{-this.E4.getLayoutParams().height, statusBarHeight});
            animator1.setDuration(300);
            animator1.addUpdateListener(new c(statusBarHeight));
            ValueAnimator animator2 = ValueAnimator.ofInt(new int[]{-this.D4.getLayoutParams().height, 0});
            animator2.setDuration(300);
            animator2.addUpdateListener(new d());
            animator1.start();
            animator2.start();
        }
    }

    public class c implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        c(int i) {
            this.c = i;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 7456, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ShowImageActivity.this.E4.getLayoutParams();
                params.topMargin = value;
                ShowImageActivity.this.E4.setLayoutParams(params);
                if (value == this.c) {
                    boolean unused = ShowImageActivity.this.O4 = true;
                    boolean unused2 = ShowImageActivity.this.P4 = false;
                }
            }
        }
    }

    public class d implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 7457, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ShowImageActivity.this.F4.getLayoutParams();
                params.bottomMargin = value;
                ShowImageActivity.this.F4.setLayoutParams(params);
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7451, new Class[0], Void.TYPE).isSupported) {
            if (this.p1.h()) {
                this.p1.g();
            } else {
                super.onBackPressed();
            }
        }
    }
}
