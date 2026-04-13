package com.scwang.smart.refresh.footer;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.classics.R$id;
import com.scwang.smart.refresh.footer.classics.R$layout;
import com.scwang.smart.refresh.footer.classics.R$string;
import com.scwang.smart.refresh.footer.classics.R$styleable;
import com.scwang.smart.refresh.layout.api.c;
import com.scwang.smart.refresh.layout.api.f;
import com.scwang.smart.refresh.layout.util.b;

public class ClassicsFooter extends ClassicsAbstract<ClassicsFooter> implements c {
    public static String E4 = null;
    public static String F4 = null;
    public static String G4 = null;
    public static String H4 = null;
    public static String I4 = null;
    public static String J4 = null;
    public static String K4 = null;
    protected String L4;
    protected String M4;
    protected String N4;
    protected String O4;
    protected String P4;
    protected String Q4;
    protected String R4;
    protected boolean S4;

    public ClassicsFooter(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClassicsFooter(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.S4 = false;
        View.inflate(context, R$layout.srl_classics_footer, this);
        ImageView imageView = (ImageView) findViewById(R$id.srl_classics_arrow);
        this.p0 = imageView;
        ImageView imageView2 = (ImageView) findViewById(R$id.srl_classics_progress);
        this.a1 = imageView2;
        this.z = (TextView) findViewById(R$id.srl_classics_title);
        TypedArray ta = context.obtainStyledAttributes(attrs, R$styleable.ClassicsFooter);
        RelativeLayout.LayoutParams lpArrow = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams lpProgress = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        int dimensionPixelSize = ta.getDimensionPixelSize(R$styleable.ClassicsFooter_srlDrawableMarginRight, b.c(20.0f));
        lpProgress.rightMargin = dimensionPixelSize;
        lpArrow.rightMargin = dimensionPixelSize;
        int i = R$styleable.ClassicsFooter_srlDrawableArrowSize;
        lpArrow.width = ta.getLayoutDimension(i, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(i, lpArrow.height);
        int i2 = R$styleable.ClassicsFooter_srlDrawableProgressSize;
        lpProgress.width = ta.getLayoutDimension(i2, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(i2, lpProgress.height);
        int i3 = R$styleable.ClassicsFooter_srlDrawableSize;
        lpArrow.width = ta.getLayoutDimension(i3, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(i3, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(i3, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(i3, lpProgress.height);
        this.A4 = ta.getInt(R$styleable.ClassicsFooter_srlFinishDuration, this.A4);
        this.d = com.scwang.smart.refresh.layout.constant.c.f[ta.getInt(R$styleable.ClassicsFooter_srlClassicsSpinnerStyle, this.d.g)];
        int i4 = R$styleable.ClassicsFooter_srlDrawableArrow;
        if (ta.hasValue(i4)) {
            this.p0.setImageDrawable(ta.getDrawable(i4));
        } else if (this.p0.getDrawable() == null) {
            com.scwang.smart.refresh.classics.a aVar = new com.scwang.smart.refresh.classics.a();
            this.a2 = aVar;
            aVar.a(-10066330);
            this.p0.setImageDrawable(this.a2);
        }
        int i5 = R$styleable.ClassicsFooter_srlDrawableProgress;
        if (ta.hasValue(i5)) {
            this.a1.setImageDrawable(ta.getDrawable(i5));
        } else if (this.a1.getDrawable() == null) {
            com.scwang.smart.drawable.b bVar = new com.scwang.smart.drawable.b();
            this.p2 = bVar;
            bVar.a(-10066330);
            this.a1.setImageDrawable(this.p2);
        }
        int i6 = R$styleable.ClassicsFooter_srlTextSizeTitle;
        if (ta.hasValue(i6)) {
            this.z.setTextSize(0, (float) ta.getDimensionPixelSize(i6, b.c(16.0f)));
        }
        int i7 = R$styleable.ClassicsFooter_srlPrimaryColor;
        if (ta.hasValue(i7)) {
            super.t(ta.getColor(i7, 0));
        }
        int i8 = R$styleable.ClassicsFooter_srlAccentColor;
        if (ta.hasValue(i8)) {
            super.s(ta.getColor(i8, 0));
        }
        int i9 = R$styleable.ClassicsFooter_srlTextPulling;
        if (ta.hasValue(i9)) {
            this.L4 = ta.getString(i9);
        } else {
            String str = E4;
            if (str != null) {
                this.L4 = str;
            } else {
                this.L4 = context.getString(R$string.srl_footer_pulling);
            }
        }
        int i10 = R$styleable.ClassicsFooter_srlTextRelease;
        if (ta.hasValue(i10)) {
            this.M4 = ta.getString(i10);
        } else {
            String str2 = F4;
            if (str2 != null) {
                this.M4 = str2;
            } else {
                this.M4 = context.getString(R$string.srl_footer_release);
            }
        }
        int i11 = R$styleable.ClassicsFooter_srlTextLoading;
        if (ta.hasValue(i11)) {
            this.N4 = ta.getString(i11);
        } else {
            String str3 = G4;
            if (str3 != null) {
                this.N4 = str3;
            } else {
                this.N4 = context.getString(R$string.srl_footer_loading);
            }
        }
        int i12 = R$styleable.ClassicsFooter_srlTextRefreshing;
        if (ta.hasValue(i12)) {
            this.O4 = ta.getString(i12);
        } else {
            String str4 = H4;
            if (str4 != null) {
                this.O4 = str4;
            } else {
                this.O4 = context.getString(R$string.srl_footer_refreshing);
            }
        }
        int i13 = R$styleable.ClassicsFooter_srlTextFinish;
        if (ta.hasValue(i13)) {
            this.P4 = ta.getString(i13);
        } else {
            String str5 = I4;
            if (str5 != null) {
                this.P4 = str5;
            } else {
                this.P4 = context.getString(R$string.srl_footer_finish);
            }
        }
        int i14 = R$styleable.ClassicsFooter_srlTextFailed;
        if (ta.hasValue(i14)) {
            this.Q4 = ta.getString(i14);
        } else {
            String str6 = J4;
            if (str6 != null) {
                this.Q4 = str6;
            } else {
                this.Q4 = context.getString(R$string.srl_footer_failed);
            }
        }
        int i15 = R$styleable.ClassicsFooter_srlTextNothing;
        if (ta.hasValue(i15)) {
            this.R4 = ta.getString(i15);
        } else {
            String str7 = K4;
            if (str7 != null) {
                this.R4 = str7;
            } else {
                this.R4 = context.getString(R$string.srl_footer_nothing);
            }
        }
        ta.recycle();
        imageView2.animate().setInterpolator((TimeInterpolator) null);
        this.z.setText(isInEditMode() ? this.N4 : this.L4);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
    }

    public int f(@NonNull f layout, boolean success) {
        super.f(layout, success);
        if (this.S4) {
            return 0;
        }
        this.z.setText(success ? this.P4 : this.Q4);
        return this.A4;
    }

    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (this.d == com.scwang.smart.refresh.layout.constant.c.c) {
            super.setPrimaryColors(colors);
        }
    }

    public boolean b(boolean noMoreData) {
        if (this.S4 == noMoreData) {
            return true;
        }
        this.S4 = noMoreData;
        View arrowView = this.p0;
        if (noMoreData) {
            this.z.setText(this.R4);
            arrowView.setVisibility(8);
            return true;
        }
        this.z.setText(this.L4);
        arrowView.setVisibility(0);
        return true;
    }

    public void h(@NonNull f refreshLayout, @NonNull com.scwang.smart.refresh.layout.constant.b oldState, @NonNull com.scwang.smart.refresh.layout.constant.b newState) {
        View arrowView = this.p0;
        if (!this.S4) {
            switch (a.a[newState.ordinal()]) {
                case 1:
                    arrowView.setVisibility(0);
                    break;
                case 2:
                    break;
                case 3:
                case 4:
                    arrowView.setVisibility(8);
                    this.z.setText(this.N4);
                    return;
                case 5:
                    this.z.setText(this.M4);
                    arrowView.animate().rotation(0.0f);
                    return;
                case 6:
                    this.z.setText(this.O4);
                    arrowView.setVisibility(8);
                    return;
                default:
                    return;
            }
            this.z.setText(this.L4);
            arrowView.animate().rotation(180.0f);
        }
    }

    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[com.scwang.smart.refresh.layout.constant.b.values().length];
            a = iArr;
            try {
                iArr[com.scwang.smart.refresh.layout.constant.b.None.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.PullUpToLoad.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.Loading.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.LoadReleased.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.ReleaseToLoad.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.Refreshing.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
