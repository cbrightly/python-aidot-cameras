package com.leedarson.newui.view.radar.sdcard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.newui.view.radar.e;
import com.leedarson.newui.view.radar.g;
import com.leedarson.newui.view.radar.sdcard.SDRadarViewLayout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class SDCardRadarLayoutWrapper extends FrameLayout implements View.OnClickListener, SDRadarViewLayout.g {
    public static ChangeQuickRedirect changeQuickRedirect;
    AnimatorSet A4;
    private ImageView a1;
    /* access modifiers changed from: private */
    public SDRadarViewLayout a2;
    private int c = com.leedarson.view.a.a(BaseApplication.b(), 6.0f);
    private int d = com.leedarson.view.a.a(BaseApplication.b(), 8.0f);
    private int f = com.leedarson.view.a.a(BaseApplication.b(), 12.0f);
    private int p0 = com.leedarson.view.a.a(BaseApplication.b(), 81.0f);
    /* access modifiers changed from: private */
    public View p1;
    private int p2;
    private ViewGroup p3;
    /* access modifiers changed from: private */
    public c p4 = c.STATE_IDLE;
    private int q = com.leedarson.view.a.a(BaseApplication.b(), 72.0f);
    private int x = com.leedarson.view.a.a(BaseApplication.b(), 12.0f);
    private int y = com.leedarson.view.a.a(BaseApplication.b(), 18.0f);
    private int z = com.leedarson.view.a.a(BaseApplication.b(), 23.0f);
    /* access modifiers changed from: private */
    public e z4;

    public enum c {
        STATE_IDLE,
        STATE_RADAR,
        STATE_COLLPOSE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public SDCardRadarLayoutWrapper(@NonNull Context context) {
        super(context);
        i(context);
    }

    public SDCardRadarLayoutWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        i(context);
    }

    public SDCardRadarLayoutWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        i(context);
    }

    public void i(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5522, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.z4 = new e();
            View view = LayoutInflater.from(getContext()).inflate(R$layout.layout_sdcard_radar_wrapper, (ViewGroup) null);
            ImageView imageView = (ImageView) view.findViewById(R$id.collopse_radar_btn);
            this.a1 = imageView;
            imageView.setOnClickListener(this);
            View findViewById = view.findViewById(R$id.layout_sdcard_radar_expand);
            this.p1 = findViewById;
            findViewById.setOnClickListener(this);
            this.a2 = (SDRadarViewLayout) view.findViewById(R$id.radarViewLayout);
            addView(view);
            n();
            this.a2.setOnSizeChangeListener(this);
        }
    }

    public void setPlayerLayout(ViewGroup playerLayout) {
        if (!PatchProxy.proxy(new Object[]{playerLayout}, this, changeQuickRedirect, false, 5523, new Class[]{ViewGroup.class}, Void.TYPE).isSupported) {
            g.a("playlayout111 init:" + playerLayout);
            this.p3 = playerLayout;
            this.p2 = playerLayout.indexOfChild(this);
        }
    }

    public ViewGroup getPlayerLayout() {
        return this.p3;
    }

    public e getDragHelper() {
        return this.z4;
    }

    public int getIndexOfRadarLayoutContainer() {
        return this.p2;
    }

    public void k(Activity activity, String deviceId) {
        Class[] clsArr = {Activity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{activity, deviceId}, this, changeQuickRedirect, false, 5524, clsArr, Void.TYPE).isSupported) {
            this.a2.G(activity, deviceId, this);
        }
    }

    private void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5525, new Class[0], Void.TYPE).isSupported) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.a1.getLayoutParams();
            params.topMargin = this.c;
            params.rightMargin = this.d;
            this.a1.setLayoutParams(params);
            ViewGroup.MarginLayoutParams expandParams = (ViewGroup.MarginLayoutParams) this.p1.getLayoutParams();
            expandParams.topMargin = this.x;
            expandParams.rightMargin = this.y;
            this.p1.setLayoutParams(expandParams);
        }
    }

    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5526, new Class[0], Void.TYPE).isSupported) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.a1.getLayoutParams();
            params.topMargin = this.f;
            params.rightMargin = this.q;
            this.a1.setLayoutParams(params);
            ViewGroup.MarginLayoutParams expandParams = (ViewGroup.MarginLayoutParams) this.p1.getLayoutParams();
            expandParams.topMargin = this.z;
            expandParams.rightMargin = this.p0;
            this.p1.setLayoutParams(expandParams);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5527, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (view2.getId() == R$id.collopse_radar_btn) {
            g.a("sufun.test collopse_radar_btn click");
            h();
            SDRadarViewLayout sDRadarViewLayout = this.a2;
            sDRadarViewLayout.setPivotX((float) sDRadarViewLayout.getRight());
            SDRadarViewLayout sDRadarViewLayout2 = this.a2;
            sDRadarViewLayout2.setPivotY((float) sDRadarViewLayout2.getTop());
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(this.a2, "scaleX", new float[]{1.0f, 0.0f});
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(this.a2, "scaleY", new float[]{1.0f, 0.0f});
            AnimatorSet animatorSet = this.A4;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.A4.removeAllListeners();
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.A4 = animatorSet2;
            animatorSet2.playTogether(new Animator[]{animator1, animator2});
            this.A4.setDuration(300);
            this.A4.addListener(new a());
            this.A4.start();
        } else if (view2.getId() == R$id.layout_sdcard_radar_expand) {
            this.a2.setVisibility(0);
            this.p1.setVisibility(4);
            SDRadarViewLayout sDRadarViewLayout3 = this.a2;
            sDRadarViewLayout3.setPivotX((float) sDRadarViewLayout3.getRight());
            SDRadarViewLayout sDRadarViewLayout4 = this.a2;
            sDRadarViewLayout4.setPivotY((float) sDRadarViewLayout4.getTop());
            ObjectAnimator animator12 = ObjectAnimator.ofFloat(this.a2, "scaleX", new float[]{0.0f, 1.0f});
            ObjectAnimator animator22 = ObjectAnimator.ofFloat(this.a2, "scaleY", new float[]{0.0f, 1.0f});
            AnimatorSet animatorSet3 = this.A4;
            if (animatorSet3 != null) {
                animatorSet3.cancel();
                this.A4.removeAllListeners();
            }
            AnimatorSet animatorSet4 = new AnimatorSet();
            this.A4 = animatorSet4;
            animatorSet4.playTogether(new Animator[]{animator12, animator22});
            this.A4.setDuration(300);
            this.A4.addListener(new b());
            this.A4.start();
            g.e("sufun.test 展开显示雷达");
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class a extends AnimatorListenerAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onAnimationEnd(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5535, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                if (SDCardRadarLayoutWrapper.this.z4 != null) {
                    g.a("sufun.test 隐藏雷达视图 只可点击");
                    SDCardRadarLayoutWrapper.this.z4.f(com.petterp.floatingx.assist.c.ClickOnly);
                }
                c unused = SDCardRadarLayoutWrapper.this.p4 = c.STATE_COLLPOSE;
                SDCardRadarLayoutWrapper.this.a2.setVisibility(4);
                SDCardRadarLayoutWrapper.this.a2.setScaleX(1.0f);
                SDCardRadarLayoutWrapper.this.a2.setScaleY(1.0f);
                SDCardRadarLayoutWrapper.this.p1.setVisibility(0);
                g.e("sufun.test 隐藏雷达视图");
            }
        }
    }

    public class b extends AnimatorListenerAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAnimationEnd(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5536, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                if (SDCardRadarLayoutWrapper.this.z4 != null) {
                    g.a("sufun.test 显示雷达视图 可拖动");
                    SDCardRadarLayoutWrapper.this.z4.f(com.petterp.floatingx.assist.c.Normal);
                }
                SDCardRadarLayoutWrapper.this.o();
            }
        }
    }

    public void f(List<com.leedarson.smartcamera.kvswebrtc.beans.a> dots) {
        if (!PatchProxy.proxy(new Object[]{dots}, this, changeQuickRedirect, false, 5528, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.a2.n(dots);
        }
    }

    public void j(int phyRadarRadius) {
        Object[] objArr = {new Integer(phyRadarRadius)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5529, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.a2.w(phyRadarRadius);
        }
    }

    public SDRadarViewLayout getRadarViewLayout() {
        return this.a2;
    }

    public c getCurrentState() {
        return this.p4;
    }

    public void l(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 5530, new Class[]{String.class}, Void.TYPE).isSupported) {
            SDRadarViewLayout sDRadarViewLayout = this.a2;
            sDRadarViewLayout.I(this, ref + "SDCardRadarLayoutWrapper.onWindowSizeChange");
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5531, new Class[0], Void.TYPE).isSupported) {
            this.a1.setVisibility(8);
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5532, new Class[0], Void.TYPE).isSupported) {
            this.a1.setVisibility(0);
            this.p1.setVisibility(8);
            this.p4 = c.STATE_RADAR;
        }
    }

    public void a(boolean isSamll, boolean isPlayerLandscape) {
        Object[] objArr = {new Byte(isSamll ? (byte) 1 : 0), new Byte(isPlayerLandscape ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5533, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (!isSamll) {
                return;
            }
            if (isPlayerLandscape) {
                m();
            } else {
                n();
            }
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5534, new Class[0], Void.TYPE).isSupported) {
            this.p4 = c.STATE_IDLE;
            h();
            this.p1.setVisibility(8);
            getRadarViewLayout().H();
            getRadarViewLayout().o();
            setVisibility(8);
        }
    }
}
