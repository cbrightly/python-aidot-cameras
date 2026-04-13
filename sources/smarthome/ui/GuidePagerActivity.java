package smarthome.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.alibaba.android.arouter.launcher.a;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.R$drawable;
import com.leedarson.base.R$id;
import com.leedarson.base.R$layout;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.utils.m;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import org.json.JSONObject;
import timber.log.a;

public class GuidePagerActivity extends BaseActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView[] A4;
    private LinearLayout B4;
    private LDSTextView C4;
    private LDSTextView D4;
    /* access modifiers changed from: private */
    public int E4 = 0;
    /* access modifiers changed from: private */
    public boolean F4 = false;
    String G4;
    private ImageView a1;
    private ImageView a2;
    private ViewPager p0;
    private ImageView p1;
    private ImageView p2;
    private ImageView p3;
    /* access modifiers changed from: private */
    public Button p4;
    private GuidePagerAdapter z4;

    static /* synthetic */ void Z(GuidePagerActivity x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 14157, new Class[]{GuidePagerActivity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.e0(x1);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 14150, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
        }
    }

    public int O() {
        return R$layout.activity_page;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14151, new Class[0], Void.TYPE).isSupported) {
            a.c().e(this);
            this.F4 = getIntent().getBooleanExtra("firstIn", false);
            initView();
            c0();
        }
    }

    public void R() {
    }

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14152, new Class[0], Void.TYPE).isSupported) {
            GuidePagerAdapter guidePagerAdapter = new GuidePagerAdapter(getSupportFragmentManager());
            this.z4 = guidePagerAdapter;
            this.A4 = new ImageView[]{this.a1, this.a2, this.p2, this.p3};
            this.p0.setAdapter(guidePagerAdapter);
            this.p0.setCurrentItem(this.E4);
            e0(this.E4);
            this.p0.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    int i = 0;
                    if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 14159, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                        int unused = GuidePagerActivity.this.E4 = position;
                        GuidePagerActivity guidePagerActivity = GuidePagerActivity.this;
                        GuidePagerActivity.Z(guidePagerActivity, guidePagerActivity.E4);
                        if (GuidePagerActivity.this.F4) {
                            Button b0 = GuidePagerActivity.this.p4;
                            if (position != 3) {
                                i = 8;
                            }
                            b0.setVisibility(i);
                        }
                    }
                }

                public void onPageScrollStateChanged(int i) {
                }
            });
        }
    }

    private void e0(int position) {
        if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 14153, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int i = 0;
            while (true) {
                ImageView[] imageViewArr = this.A4;
                if (i < imageViewArr.length) {
                    imageViewArr[i].setBackgroundResource(i == position ? R$drawable.indicator_selected : R$drawable.indicator_unselected);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14154, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (ViewPager) findViewById(R$id.container);
            this.a1 = (ImageView) findViewById(R$id.image_indicator_zero);
            this.a2 = (ImageView) findViewById(R$id.image_indicator_one);
            this.p2 = (ImageView) findViewById(R$id.image_indicator_two);
            this.p3 = (ImageView) findViewById(R$id.image_indicator_three);
            this.B4 = (LinearLayout) findViewById(R$id.title_layout);
            this.C4 = (LDSTextView) findViewById(R$id.text_title);
            this.p1 = (ImageView) findViewById(R$id.btn_back);
            this.p4 = (Button) findViewById(R$id.btn_sign);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.text_skip);
            this.D4 = lDSTextView;
            lDSTextView.setOnClickListener(this);
            if (this.F4) {
                this.B4.setVisibility(8);
                this.p4.setOnClickListener(this);
                StatusBarUtil.setTranslucent(this);
                StatusBarUtil.hideFakeStatusBarView(this);
                this.D4.setVisibility(0);
                return;
            }
            this.p1.setOnClickListener(this);
            if (!TextUtils.isEmpty(this.G4)) {
                try {
                    JSONObject jsonObject = new JSONObject(this.G4);
                    a.b g = timber.log.a.g("LdsSystem");
                    g.h("guidePageBean:" + jsonObject.toString(), new Object[0]);
                    if (jsonObject.has("navBar")) {
                        LinkedTreeMap barMap = m.b(jsonObject.optString("navBar"));
                        if (barMap.containsKey("backgroundColor")) {
                            StatusBarUtil.setColor(this, Color.parseColor(barMap.get("backgroundColor").toString()), 0);
                            this.B4.setBackgroundColor(Color.parseColor(barMap.get("backgroundColor").toString()));
                        } else {
                            this.B4.setBackgroundColor(Color.parseColor("#485865"));
                            StatusBarUtil.setColor(this, Color.parseColor("#485865"), 0);
                        }
                        if (barMap.containsKey("title")) {
                            this.C4.setText(barMap.get("title").toString());
                        }
                        if (barMap.containsKey("titleColor")) {
                            this.C4.setTextColor(Color.parseColor(barMap.get("titleColor").toString()));
                        }
                        if (barMap.containsKey("backButtonColor")) {
                            this.p1.setColorFilter(Color.parseColor(barMap.get("backButtonColor").toString()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 14155, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int ID = v.getId();
        int i = R$id.btn_sign;
        if (ID == i) {
            finish();
        } else if (ID == i) {
            finish();
        } else if (ID == i) {
            finish();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14156, new Class[0], Void.TYPE).isSupported) {
            super.onBackPressed();
            finish();
        }
    }
}
