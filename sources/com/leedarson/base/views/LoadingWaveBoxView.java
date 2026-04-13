package com.leedarson.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;

public class LoadingWaveBoxView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<LoadingStepBean> c;
    private int d;
    private View f;
    private LoadingView q;
    /* access modifiers changed from: private */
    public LDSTextView x;
    private LDSTextView y;
    private View z;

    public static class LoadingStepBean {
        public String description = "";
        public int stepIndex = 0;
    }

    public LoadingWaveBoxView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LoadingWaveBoxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingWaveBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadingWaveBoxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.c = new ArrayList<>();
        this.d = 0;
        this.f = null;
        this.q = null;
        this.z = null;
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETRCD_DURATION_RESP, new Class[]{Context.class}, Void.TYPE).isSupported) {
            View inflate = LayoutInflater.from(context).inflate(R$layout.lds_ipc_player_loading_layout, (ViewGroup) null);
            this.f = inflate;
            this.q = (LoadingView) inflate.findViewById(R$id.ldView);
            this.x = (LDSTextView) this.f.findViewById(R$id.tvStepDescription);
            this.q.c();
            this.x.setVisibility(8);
            LDSTextView lDSTextView = (LDSTextView) this.f.findViewById(R$id.tvWifiTips);
            this.y = lDSTextView;
            lDSTextView.setVisibility(8);
            this.z = LayoutInflater.from(context).inflate(R$layout.lds_ipc_player_loading_dot_layout, (ViewGroup) null);
            addView(this.z, new RelativeLayout.LayoutParams(-1, -1));
        }
    }

    public void setConnectStepDatas(ArrayList<LoadingStepBean> datas) {
        if (!PatchProxy.proxy(new Object[]{datas}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_RESP, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            removeAllViews();
            addView(this.f, new RelativeLayout.LayoutParams(-1, -1));
            this.c.clear();
            this.c.addAll(datas);
            if (datas.size() > 0) {
                this.x.setVisibility(0);
                this.x.setText(datas.get(0).description);
            }
        }
    }

    public void d(boolean show, String tips) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Byte(show ? (byte) 1 : 0), tips}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            this.y.setText(tips);
            LDSTextView lDSTextView = this.y;
            if (!show) {
                i = 8;
            }
            lDSTextView.setVisibility(i);
        }
    }

    public void c(int position, boolean hasAnimation) {
        Object[] objArr = {new Integer(position), new Byte(hasAnimation ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.d != position) {
                this.d = position;
                this.x.clearAnimation();
                if (position < this.c.size()) {
                    this.x.setText(this.c.get(position).description);
                    if (position != 0 && hasAnimation) {
                        Animation animationToHide = new AlphaAnimation(1.0f, 0.0f);
                        animationToHide.setDuration(500);
                        Animation animationToShow = new AlphaAnimation(0.0f, 1.0f);
                        animationToShow.setDuration(500);
                        animationToHide.setAnimationListener(new a(animationToShow));
                        this.x.setAnimation(animationToHide);
                    }
                }
            }
        }
    }

    public class a implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Animation a;

        a(Animation animation) {
            this.a = animation;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTFILE_RESP, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                LoadingWaveBoxView.this.x.setAnimation(this.a);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }
}
