package smarthome.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.leedarson.base.R$drawable;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import smarthome.utils.l;
import timber.log.a;

public class SplashView extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LottieAnimationView c;
    private ImageView d;
    private boolean f;

    public SplashView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f = true;
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 14232, new Class[]{Context.class}, Void.TYPE).isSupported) {
            try {
                this.c = new LottieAnimationView(context);
                if (l.h(context, "animatedSplashScreen.json")) {
                    this.c.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                    this.c.setAnimation("animatedSplashScreen.json");
                    this.c.setBackgroundColor(-1);
                    this.c.setRepeatCount(0);
                    addView(this.c);
                }
            } catch (Exception exception) {
                exception.getMessage();
            }
            ImageView imageView = new ImageView(context);
            this.d = imageView;
            imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            this.d.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(this.d);
            d();
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14233, new Class[0], Void.TYPE).isSupported) {
            this.c.u();
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14234, new Class[0], Void.TYPE).isSupported) {
            a.g("splash").a("#lottieMode", new Object[0]);
            if (this.f) {
                this.d.setVisibility(8);
                this.c.setVisibility(0);
                c();
            }
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14235, new Class[0], Void.TYPE).isSupported) {
            a.g("splash").a("#staticMode", new Object[0]);
            this.c.setVisibility(8);
            this.d.setVisibility(0);
        }
    }

    public void setSplashImg(int i) {
        Object[] objArr = {new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14236, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            String repositoryName = SharePreferenceUtils.getPrefString(getContext(), "repositoryName", "");
            if ("M071-AiDot".equals(repositoryName)) {
                this.d.setBackgroundResource(R$drawable.lanucher_aidot2);
            } else if ("M071-Linkind".equals(repositoryName)) {
                this.d.setBackgroundResource(R$drawable.lanucher_linkind2);
            } else if ("C610-Innr".equals(repositoryName)) {
                this.d.setBackgroundResource(R$drawable.lanucher_c6102);
            } else if ("leedarson-Leedarson".equals(repositoryName)) {
                this.d.setBackgroundResource(R$drawable.lanucher_leedarson2);
            } else {
                this.d.setBackgroundResource(R$drawable.lanucher_aidot2);
            }
            d();
        }
    }
}
