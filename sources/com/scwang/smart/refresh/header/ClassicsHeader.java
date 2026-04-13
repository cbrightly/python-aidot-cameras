package com.scwang.smart.refresh.header;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.header.classics.R$id;
import com.scwang.smart.refresh.header.classics.R$layout;
import com.scwang.smart.refresh.header.classics.R$string;
import com.scwang.smart.refresh.header.classics.R$styleable;
import com.scwang.smart.refresh.layout.api.d;
import com.scwang.smart.refresh.layout.api.f;
import com.scwang.smart.refresh.layout.constant.c;
import com.scwang.smart.refresh.layout.util.b;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClassicsHeader extends ClassicsAbstract<ClassicsHeader> implements d {
    public static final int E4 = R$id.srl_classics_update;
    public static String F4 = null;
    public static String G4 = null;
    public static String H4 = null;
    public static String I4 = null;
    public static String J4 = null;
    public static String K4 = null;
    public static String L4 = null;
    public static String M4 = null;
    protected String N4;
    protected Date O4;
    protected TextView P4;
    protected SharedPreferences Q4;
    protected DateFormat R4;
    protected boolean S4;
    protected String T4;
    protected String U4;
    protected String V4;
    protected String W4;
    protected String X4;
    protected String Y4;
    protected String Z4;
    protected String a5;

    public ClassicsHeader(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        FragmentManager manager;
        this.N4 = "LAST_UPDATE_TIME";
        this.S4 = true;
        View.inflate(context, R$layout.srl_classics_header, this);
        ImageView imageView = (ImageView) findViewById(R$id.srl_classics_arrow);
        this.p0 = imageView;
        TextView textView = (TextView) findViewById(R$id.srl_classics_update);
        this.P4 = textView;
        ImageView imageView2 = (ImageView) findViewById(R$id.srl_classics_progress);
        this.a1 = imageView2;
        this.z = (TextView) findViewById(R$id.srl_classics_title);
        TypedArray ta = context.obtainStyledAttributes(attrs, R$styleable.ClassicsHeader);
        RelativeLayout.LayoutParams lpArrow = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams lpProgress = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        new LinearLayout.LayoutParams(-2, -2).topMargin = ta.getDimensionPixelSize(R$styleable.ClassicsHeader_srlTextTimeMarginTop, b.c(0.0f));
        int dimensionPixelSize = ta.getDimensionPixelSize(R$styleable.ClassicsHeader_srlDrawableMarginRight, b.c(20.0f));
        lpProgress.rightMargin = dimensionPixelSize;
        lpArrow.rightMargin = dimensionPixelSize;
        int i = R$styleable.ClassicsHeader_srlDrawableArrowSize;
        lpArrow.width = ta.getLayoutDimension(i, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(i, lpArrow.height);
        int i2 = R$styleable.ClassicsHeader_srlDrawableProgressSize;
        lpProgress.width = ta.getLayoutDimension(i2, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(i2, lpProgress.height);
        int i3 = R$styleable.ClassicsHeader_srlDrawableSize;
        lpArrow.width = ta.getLayoutDimension(i3, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(i3, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(i3, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(i3, lpProgress.height);
        this.A4 = ta.getInt(R$styleable.ClassicsHeader_srlFinishDuration, this.A4);
        this.S4 = ta.getBoolean(R$styleable.ClassicsHeader_srlEnableLastTime, this.S4);
        this.d = c.f[ta.getInt(R$styleable.ClassicsHeader_srlClassicsSpinnerStyle, this.d.g)];
        int i4 = R$styleable.ClassicsHeader_srlDrawableArrow;
        if (ta.hasValue(i4)) {
            this.p0.setImageDrawable(ta.getDrawable(i4));
        } else if (this.p0.getDrawable() == null) {
            com.scwang.smart.refresh.classics.a aVar = new com.scwang.smart.refresh.classics.a();
            this.a2 = aVar;
            aVar.a(-10066330);
            this.p0.setImageDrawable(this.a2);
        }
        int i5 = R$styleable.ClassicsHeader_srlDrawableProgress;
        if (ta.hasValue(i5)) {
            this.a1.setImageDrawable(ta.getDrawable(i5));
        } else if (this.a1.getDrawable() == null) {
            com.scwang.smart.drawable.b bVar = new com.scwang.smart.drawable.b();
            this.p2 = bVar;
            bVar.a(-10066330);
            this.a1.setImageDrawable(this.p2);
        }
        int i6 = R$styleable.ClassicsHeader_srlTextSizeTitle;
        if (ta.hasValue(i6)) {
            this.z.setTextSize(0, (float) ta.getDimensionPixelSize(i6, b.c(16.0f)));
        }
        int i7 = R$styleable.ClassicsHeader_srlTextSizeTime;
        if (ta.hasValue(i7)) {
            this.P4.setTextSize(0, (float) ta.getDimensionPixelSize(i7, b.c(12.0f)));
        }
        int i8 = R$styleable.ClassicsHeader_srlPrimaryColor;
        if (ta.hasValue(i8)) {
            super.t(ta.getColor(i8, 0));
        }
        int i9 = R$styleable.ClassicsHeader_srlAccentColor;
        if (ta.hasValue(i9)) {
            s(ta.getColor(i9, 0));
        }
        int i10 = R$styleable.ClassicsHeader_srlTextPulling;
        if (ta.hasValue(i10)) {
            this.T4 = ta.getString(i10);
        } else {
            String str = F4;
            if (str != null) {
                this.T4 = str;
            } else {
                this.T4 = context.getString(R$string.srl_header_pulling);
            }
        }
        int i11 = R$styleable.ClassicsHeader_srlTextLoading;
        if (ta.hasValue(i11)) {
            this.V4 = ta.getString(i11);
        } else {
            String str2 = H4;
            if (str2 != null) {
                this.V4 = str2;
            } else {
                this.V4 = context.getString(R$string.srl_header_loading);
            }
        }
        int i12 = R$styleable.ClassicsHeader_srlTextRelease;
        if (ta.hasValue(i12)) {
            this.W4 = ta.getString(i12);
        } else {
            String str3 = I4;
            if (str3 != null) {
                this.W4 = str3;
            } else {
                this.W4 = context.getString(R$string.srl_header_release);
            }
        }
        int i13 = R$styleable.ClassicsHeader_srlTextFinish;
        if (ta.hasValue(i13)) {
            this.X4 = ta.getString(i13);
        } else {
            String str4 = J4;
            if (str4 != null) {
                this.X4 = str4;
            } else {
                this.X4 = context.getString(R$string.srl_header_finish);
            }
        }
        int i14 = R$styleable.ClassicsHeader_srlTextFailed;
        if (ta.hasValue(i14)) {
            this.Y4 = ta.getString(i14);
        } else {
            String str5 = K4;
            if (str5 != null) {
                this.Y4 = str5;
            } else {
                this.Y4 = context.getString(R$string.srl_header_failed);
            }
        }
        int i15 = R$styleable.ClassicsHeader_srlTextSecondary;
        if (ta.hasValue(i15)) {
            this.a5 = ta.getString(i15);
        } else {
            String str6 = M4;
            if (str6 != null) {
                this.a5 = str6;
            } else {
                this.a5 = context.getString(R$string.srl_header_secondary);
            }
        }
        int i16 = R$styleable.ClassicsHeader_srlTextRefreshing;
        if (ta.hasValue(i16)) {
            this.U4 = ta.getString(i16);
        } else {
            String str7 = G4;
            if (str7 != null) {
                this.U4 = str7;
            } else {
                this.U4 = context.getString(R$string.srl_header_refreshing);
            }
        }
        int i17 = R$styleable.ClassicsHeader_srlTextUpdate;
        if (ta.hasValue(i17)) {
            this.Z4 = ta.getString(i17);
        } else {
            String str8 = L4;
            if (str8 != null) {
                this.Z4 = str8;
            } else {
                this.Z4 = context.getString(R$string.srl_header_update);
            }
        }
        this.R4 = new SimpleDateFormat(this.Z4, Locale.getDefault());
        ta.recycle();
        imageView2.animate().setInterpolator((TimeInterpolator) null);
        textView.setVisibility(this.S4 ? 0 : 8);
        this.z.setText(isInEditMode() ? this.U4 : this.T4);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
        try {
            if ((context instanceof FragmentActivity) && (manager = ((FragmentActivity) context).getSupportFragmentManager()) != null && manager.getFragments().size() > 0) {
                v(new Date());
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        this.N4 += context.getClass().getName();
        this.Q4 = context.getSharedPreferences("ClassicsHeader", 0);
        v(new Date(this.Q4.getLong(this.N4, System.currentTimeMillis())));
    }

    public int f(@NonNull f layout, boolean success) {
        if (success) {
            this.z.setText(this.X4);
            if (this.O4 != null) {
                v(new Date());
            }
        } else {
            this.z.setText(this.Y4);
        }
        return super.f(layout, success);
    }

    public void h(@NonNull f refreshLayout, @NonNull com.scwang.smart.refresh.layout.constant.b oldState, @NonNull com.scwang.smart.refresh.layout.constant.b newState) {
        View arrowView = this.p0;
        View updateView = this.P4;
        int i = 8;
        switch (a.a[newState.ordinal()]) {
            case 1:
                if (this.S4) {
                    i = 0;
                }
                updateView.setVisibility(i);
                break;
            case 2:
                break;
            case 3:
            case 4:
                this.z.setText(this.U4);
                arrowView.setVisibility(8);
                return;
            case 5:
                this.z.setText(this.W4);
                arrowView.animate().rotation(180.0f);
                return;
            case 6:
                this.z.setText(this.a5);
                arrowView.animate().rotation(0.0f);
                return;
            case 7:
                arrowView.setVisibility(8);
                if (this.S4) {
                    i = 4;
                }
                updateView.setVisibility(i);
                this.z.setText(this.V4);
                return;
            default:
                return;
        }
        this.z.setText(this.T4);
        arrowView.setVisibility(0);
        arrowView.animate().rotation(0.0f);
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
                a[com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.Refreshing.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.RefreshReleased.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.ReleaseToRefresh.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.ReleaseToTwoLevel.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.Loading.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public ClassicsHeader v(Date time) {
        this.O4 = time;
        this.P4.setText(this.R4.format(time));
        if (this.Q4 != null && !isInEditMode()) {
            this.Q4.edit().putLong(this.N4, time.getTime()).apply();
        }
        return this;
    }

    /* renamed from: u */
    public ClassicsHeader s(@ColorInt int accentColor) {
        this.P4.setTextColor((16777215 & accentColor) | -872415232);
        return (ClassicsHeader) super.s(accentColor);
    }
}
