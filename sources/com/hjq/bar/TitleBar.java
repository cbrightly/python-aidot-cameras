package com.hjq.bar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hjq.bar.style.b;
import com.hjq.bar.style.c;
import com.hjq.bar.style.d;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class TitleBar extends FrameLayout implements View.OnClickListener, Runnable {
    private static a c;
    private b d;
    private LinearLayout f;
    private TextView q;
    private TextView x;
    private TextView y;
    private View z;

    public TitleBar(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b(context);
        a(attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE || View.MeasureSpec.getMode(heightMeasureSpec) == 0) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthMeasureSpec, 1073741824);
        }
        if (View.MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE || View.MeasureSpec.getMode(heightMeasureSpec) == 0) {
            if (c.a() <= 0) {
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(c.b(getContext()), 1073741824);
            }
            Drawable background = getBackground();
            if (background instanceof BitmapDrawable) {
                this.f.getLayoutParams().height = View.MeasureSpec.getSize(heightMeasureSpec);
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (((double) background.getIntrinsicHeight()) * (((double) View.MeasureSpec.getSize(widthMeasureSpec)) / ((double) background.getIntrinsicWidth()))), 1073741824);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void b(Context context) {
        c builder = new c(context);
        this.f = builder.f();
        this.z = builder.e();
        this.x = builder.h();
        this.q = builder.d();
        this.y = builder.g();
        this.q.setEnabled(false);
        this.x.setEnabled(false);
        this.y.setEnabled(false);
        this.f.addView(this.q);
        this.f.addView(this.x);
        this.f.addView(this.y);
        addView(this.f, 0);
        addView(this.z, 1);
    }

    private void a(AttributeSet attrs) {
        a style;
        CharSequence label;
        if (c == null) {
            c = new b(getContext());
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R$styleable.TitleBar);
        switch (array.getInt(R$styleable.TitleBar_barStyle, 0)) {
            case 16:
                style = new b(getContext());
                break;
            case 32:
                style = new c(getContext());
                break;
            case 48:
                style = new d(getContext());
                break;
            default:
                style = c;
                break;
        }
        int i = R$styleable.TitleBar_leftTitle;
        if (array.hasValue(i)) {
            setLeftTitle((CharSequence) array.getString(i));
        }
        int i2 = R$styleable.TitleBar_title;
        if (array.hasValue(i2)) {
            setTitle((CharSequence) array.getString(i2));
        } else if ((getContext() instanceof Activity) && (label = c.c((Activity) getContext())) != null && !"".equals(label.toString())) {
            setTitle(label);
        }
        int i3 = R$styleable.TitleBar_rightTitle;
        if (array.hasValue(i3)) {
            setRightTitle((CharSequence) array.getString(i3));
        }
        int i4 = R$styleable.TitleBar_leftIcon;
        if (array.hasValue(i4)) {
            setLeftIcon(getContext().getResources().getDrawable(array.getResourceId(i4, 0)));
        } else {
            if (array.getBoolean(R$styleable.TitleBar_backButton, style.m() != null)) {
                setLeftIcon(style.m());
            }
        }
        int i5 = R$styleable.TitleBar_rightIcon;
        if (array.hasValue(i5)) {
            setRightIcon(getContext().getResources().getDrawable(array.getResourceId(i5, 0)));
        }
        setLeftColor(array.getColor(R$styleable.TitleBar_leftColor, style.l()));
        setTitleColor(array.getColor(R$styleable.TitleBar_titleColor, style.j()));
        setRightColor(array.getColor(R$styleable.TitleBar_rightColor, style.k()));
        c(0, (float) array.getDimensionPixelSize(R$styleable.TitleBar_leftSize, (int) style.e()));
        e(0, (float) array.getDimensionPixelSize(R$styleable.TitleBar_titleSize, (int) style.g()));
        d(0, (float) array.getDimensionPixelSize(R$styleable.TitleBar_rightSize, (int) style.d()));
        int i6 = R$styleable.TitleBar_leftBackground;
        if (array.hasValue(i6)) {
            setLeftBackground(array.getDrawable(i6));
        } else {
            setLeftBackground(style.i());
        }
        int i7 = R$styleable.TitleBar_rightBackground;
        if (array.hasValue(i7)) {
            setRightBackground(array.getDrawable(i7));
        } else {
            setRightBackground(style.h());
        }
        int i8 = R$styleable.TitleBar_lineColor;
        if (array.hasValue(i8)) {
            setLineDrawable(array.getDrawable(i8));
        } else {
            setLineDrawable(style.c());
        }
        setLineVisible(array.getBoolean(R$styleable.TitleBar_lineVisible, style.f()));
        setLineSize(array.getDimensionPixelSize(R$styleable.TitleBar_lineSize, style.b()));
        array.recycle();
        if (getBackground() != null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(style.getBackground());
        } else {
            setBackgroundDrawable(style.getBackground());
        }
    }

    public void run() {
        int leftSize = this.q.getWidth();
        int rightSize = this.y.getWidth();
        boolean z2 = false;
        if (leftSize != rightSize) {
            if (leftSize > rightSize) {
                this.x.setPadding(0, 0, leftSize - rightSize, 0);
            } else {
                this.x.setPadding(rightSize - leftSize, 0, 0, 0);
            }
        }
        TextView textView = this.q;
        textView.setEnabled(!"".equals(textView.getText().toString()) || c.i(this.q));
        TextView textView2 = this.x;
        textView2.setEnabled(!"".equals(textView2.getText().toString()) || c.i(this.x));
        TextView textView3 = this.y;
        if (!"".equals(textView3.getText().toString()) || c.i(this.y)) {
            z2 = true;
        }
        textView3.setEnabled(z2);
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        View v = view;
        if (getOnTitleBarListener() == null) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int id = v.getId();
        if (id == R$id.bar_id_left_view) {
            getOnTitleBarListener().a(v);
        } else if (id == R$id.bar_id_title_view) {
            getOnTitleBarListener().b(v);
        } else if (id == R$id.bar_id_right_view) {
            getOnTitleBarListener().c(v);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.x.setOnClickListener(this);
        this.q.setOnClickListener(this);
        this.y.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.x.setOnClickListener((View.OnClickListener) null);
        this.q.setOnClickListener((View.OnClickListener) null);
        this.y.setOnClickListener((View.OnClickListener) null);
        super.onDetachedFromWindow();
    }

    public b getOnTitleBarListener() {
        return this.d;
    }

    public void setOnTitleBarListener(b l) {
        this.d = l;
    }

    public CharSequence getTitle() {
        return this.x.getText();
    }

    public void setTitle(int stringId) {
        setTitle((CharSequence) getResources().getString(stringId));
    }

    public void setTitle(CharSequence text) {
        this.x.setText(text);
        post(this);
    }

    public void setLeftTitle(int stringId) {
        setLeftTitle((CharSequence) getResources().getString(stringId));
    }

    public void setLeftTitle(CharSequence text) {
        this.q.setText(text);
        post(this);
    }

    public void setRightTitle(int stringId) {
        setRightTitle((CharSequence) getResources().getString(stringId));
    }

    public void setRightTitle(CharSequence text) {
        this.y.setText(text);
        post(this);
    }

    public void setLeftIcon(int iconId) {
        if (iconId > 0) {
            setLeftIcon(getContext().getResources().getDrawable(iconId));
        }
    }

    public void setLeftIcon(Drawable drawable) {
        this.q.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
        post(this);
    }

    public void setRightIcon(int iconId) {
        if (iconId > 0) {
            setRightIcon(getContext().getResources().getDrawable(iconId));
        }
    }

    public void setRightIcon(Drawable drawable) {
        this.y.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
        post(this);
    }

    public void setTitleColor(int color) {
        this.x.setTextColor(color);
    }

    public void setLeftColor(int color) {
        this.q.setTextColor(color);
    }

    public void setRightColor(int color) {
        this.y.setTextColor(color);
    }

    public void setLeftBackground(int bgId) {
        if (bgId > 0) {
            this.q.setBackgroundResource(bgId);
        }
    }

    public void setLeftBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.q.setBackground(drawable);
        } else {
            this.q.setBackgroundDrawable(drawable);
        }
        post(this);
    }

    public void setRightBackground(int bgId) {
        if (bgId > 0) {
            this.y.setBackgroundResource(bgId);
        }
    }

    public void setRightBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.y.setBackground(drawable);
        } else {
            this.y.setBackgroundDrawable(drawable);
        }
        post(this);
    }

    public void c(int unit, float size) {
        this.q.setTextSize(unit, size);
        post(this);
    }

    public void e(int unit, float size) {
        this.x.setTextSize(unit, size);
        post(this);
    }

    public void d(int unit, float size) {
        this.y.setTextSize(unit, size);
        post(this);
    }

    public void setLineVisible(boolean visible) {
        this.z.setVisibility(visible ? 0 : 8);
    }

    public void setLineColor(int color) {
        setLineDrawable(new ColorDrawable(color));
    }

    public void setLineDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.z.setBackground(drawable);
        } else {
            this.z.setBackgroundDrawable(drawable);
        }
    }

    public void setLineSize(int size) {
        ViewGroup.LayoutParams layoutParams = this.z.getLayoutParams();
        layoutParams.height = size;
        this.z.setLayoutParams(layoutParams);
    }

    public LinearLayout getMainLayout() {
        return this.f;
    }

    public TextView getLeftView() {
        return this.q;
    }

    public TextView getTitleView() {
        return this.x;
    }

    public TextView getRightView() {
        return this.y;
    }

    public View getLineView() {
        return this.z;
    }
}
