package com.leedarson.base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.leedarson.module_base.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.AVIOCTRLDEFs;

public class LoadingView extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LottieAnimationView c;
    private int d;
    private String f;

    public LoadingView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.d = 0;
        this.f = "logo_loading.json";
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETRCD_DURATION_REQ, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            try {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R$styleable.LoadingView);
                if (typedArray != null) {
                    this.d = typedArray.getColor(R$styleable.LoadingView_bgColor, 0);
                    String temp_assets = typedArray.getString(R$styleable.LoadingView_lds_asset_name);
                    if (!TextUtils.isEmpty(temp_assets)) {
                        this.f = temp_assets;
                    }
                    typedArray.recycle();
                }
                LottieAnimationView lottieAnimationView = new LottieAnimationView(context);
                this.c = lottieAnimationView;
                lottieAnimationView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                this.c.setAnimation(this.f);
                this.c.setBackgroundColor(this.d);
                this.c.setRepeatCount(-1);
                addView(this.c);
            } catch (Exception exception) {
                exception.getMessage();
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETRCD_DURATION_RESP, new Class[0], Void.TYPE).isSupported) {
            this.c.u();
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETRCD_DURATION_REQ, new Class[0], Void.TYPE).isSupported) {
            this.c.t();
        }
    }
}
