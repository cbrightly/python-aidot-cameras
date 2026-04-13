package smarthome.ui.navigationbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.i0;
import com.leedarson.base.R$id;
import com.leedarson.base.R$layout;
import com.leedarson.base.utils.d;
import com.leedarson.base.views.common.LDSTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class NavigationItem extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a1;
    private String a2;
    private LDSTextView c;
    private ImageView d;
    private LDSTextView f;
    private j p0;
    private int p1;
    private LottieAnimationView q;
    private ImageView x;
    private ImageView y;
    private int z;

    public NavigationItem(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public NavigationItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.z = 11;
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 14271, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.item_navigation, this, true);
            this.q = (LottieAnimationView) findViewById(R$id.lav);
            this.c = (LDSTextView) findViewById(R$id.tv_item_title);
            this.d = (ImageView) findViewById(R$id.iv_item_src);
            this.f = (LDSTextView) findViewById(R$id.tv_item_dot);
            this.x = (ImageView) findViewById(R$id.iv_new);
            this.y = (ImageView) findViewById(R$id.iv_sale);
            g(d.b(context, 27.0f), this.z);
        }
    }

    private void c(String json) {
        if (!PatchProxy.proxy(new Object[]{json}, this, changeQuickRedirect, false, 14272, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.q.setAnimation(json);
            this.q.setBackgroundColor(-1);
            this.q.setRepeatCount(0);
            this.q.e(new a());
            this.q.setProgress(0.0f);
        }
    }

    public class a implements i0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(c0 composition) {
            if (!PatchProxy.proxy(new Object[]{composition}, this, changeQuickRedirect, false, 14285, new Class[]{c0.class}, Void.TYPE).isSupported) {
                float p = composition.p();
            }
        }
    }

    private void g(int imageSize, int i) {
        Object[] objArr = {new Integer(imageSize), new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14273, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            ViewGroup.LayoutParams layoutParams = this.d.getLayoutParams();
            layoutParams.height = imageSize;
            layoutParams.width = imageSize;
            ViewGroup.LayoutParams lavParams = this.q.getLayoutParams();
            lavParams.height = imageSize;
            lavParams.width = imageSize;
        }
    }

    public void d(j tabEnum, String color, String selectColor) {
        Class<String> cls = String.class;
        Class[] clsArr = {j.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{tabEnum, color, selectColor}, this, changeQuickRedirect, false, 14274, clsArr, Void.TYPE).isSupported) {
            this.p0 = tabEnum;
            this.a1 = Color.parseColor(color);
            this.p1 = Color.parseColor(selectColor);
            this.a2 = tabEnum.name();
            setSelectState(false);
            c(tabEnum.getLottieFile());
        }
    }

    public void f(boolean selected, boolean anim) {
        Object[] objArr = {new Byte(selected ? (byte) 1 : 0), new Byte(anim ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14275, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (selected) {
                j jVar = this.p0;
                i(jVar.normalSrcId, jVar.getTitle(), this.p1);
                if (anim) {
                    e();
                } else {
                    h();
                }
            } else {
                a();
                j jVar2 = this.p0;
                i(jVar2.normalSrcId, jVar2.getTitle(), this.a1);
            }
        }
    }

    public void setSelectState(boolean selected) {
        Object[] objArr = {new Byte(selected ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14276, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            f(selected, true);
        }
    }

    public void i(int srcId, String title, int textColor) {
        Object[] objArr = {new Integer(srcId), title, new Integer(textColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14277, new Class[]{cls, String.class, cls}, Void.TYPE).isSupported) {
            this.d.setImageResource(srcId);
            this.c.setText(title);
            this.c.setTextColor(textColor);
        }
    }

    public String getKey() {
        return this.a2;
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14278, new Class[0], Void.TYPE).isSupported) {
            if (this.q != null) {
                this.d.setVisibility(4);
                this.q.setVisibility(0);
                this.q.d(new b());
                this.q.u();
            }
        }
    }

    public class b extends AnimatorListenerAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAnimationCancel(Animator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 14286, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                super.onAnimationCancel(animation);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 14287, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                super.onAnimationEnd(animation);
            }
        }
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14279, new Class[0], Void.TYPE).isSupported) {
            if (this.q != null) {
                this.d.setVisibility(4);
                this.q.setVisibility(0);
                this.q.setProgress(1.0f);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14280(0x37c8, float:2.001E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.airbnb.lottie.LottieAnimationView r1 = r0.q
            if (r1 == 0) goto L_0x002c
            boolean r1 = r1.n()
            if (r1 == 0) goto L_0x0026
            com.airbnb.lottie.LottieAnimationView r1 = r0.q
            r1.g()
        L_0x0026:
            com.airbnb.lottie.LottieAnimationView r1 = r0.q
            r2 = 0
            r1.setProgress(r2)
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.navigationbar.NavigationItem.a():void");
    }

    public void setLottieProgress(float progress) {
        LottieAnimationView lottieAnimationView;
        Object[] objArr = {new Float(progress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14281, new Class[]{Float.TYPE}, Void.TYPE).isSupported && (lottieAnimationView = this.q) != null) {
            lottieAnimationView.setProgress(progress);
        }
    }

    public void setBadge(int badge) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(badge)}, this, changeQuickRedirect, false, 14282, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            LDSTextView lDSTextView = this.f;
            if (badge <= 0) {
                i = 8;
            }
            lDSTextView.setVisibility(i);
        }
    }

    public void setNewIcon(int badge) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(badge)}, this, changeQuickRedirect, false, 14283, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            ImageView imageView = this.x;
            if (badge <= 0) {
                i = 8;
            }
            imageView.setVisibility(i);
        }
    }

    public void setSaleIcon(int badge) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(badge)}, this, changeQuickRedirect, false, 14284, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            ImageView imageView = this.y;
            if (badge <= 0) {
                i = 8;
            }
            imageView.setVisibility(i);
        }
    }
}
