package com.didichuxing.doraemonkit.widget.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.didichuxing.doraemonkit.R;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class TitleBar extends FrameLayout {
    private ImageView mLeftIcon;
    private TextView mLeftText;
    /* access modifiers changed from: private */
    public OnTitleBarClickListener mListener;
    private ImageView mRightIcon;
    private TextView mRightText;
    private TextView mTitle;

    public interface OnTitleBarCheckListener {
        void onCheckChange(boolean z);
    }

    public interface OnTitleBarClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public TitleBar(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.dk_title_bar, this, true);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        int leftIcon = a.getResourceId(R.styleable.TitleBar_dkLeftIcon, 0);
        int rightIcon = a.getResourceId(R.styleable.TitleBar_dkRightIcon, 0);
        int rightSubIcon = a.getResourceId(R.styleable.TitleBar_dkRightSubIcon, 0);
        String title = a.getString(R.styleable.TitleBar_dkTitle);
        int titleColor = a.getColor(R.styleable.TitleBar_dkTitleColor, 0);
        int titleBackground = a.getColor(R.styleable.TitleBar_dkTitleBackground, getResources().getColor(R.color.dk_color_FFFFFF));
        String rightText = a.getString(R.styleable.TitleBar_dkRightText);
        String leftText = a.getString(R.styleable.TitleBar_dkLeftText);
        a.recycle();
        this.mLeftIcon = (ImageView) findViewById(R.id.left_icon);
        this.mRightIcon = (ImageView) findViewById(R.id.right_icon);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mRightText = (TextView) findViewById(R.id.right_text);
        this.mLeftText = (TextView) findViewById(R.id.left_text);
        ((ViewGroup) this.mLeftIcon.getParent()).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (TitleBar.this.mListener != null) {
                    TitleBar.this.mListener.onLeftClick();
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        if (rightSubIcon == 0) {
            ((ViewGroup) this.mRightIcon.getParent()).setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (TitleBar.this.mListener != null) {
                        TitleBar.this.mListener.onRightClick();
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            ((ViewGroup) this.mRightText.getParent()).setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (TitleBar.this.mListener != null) {
                        TitleBar.this.mListener.onRightClick();
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        } else {
            this.mRightIcon.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (TitleBar.this.mListener != null) {
                        TitleBar.this.mListener.onRightClick();
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
        setLeftIcon(leftIcon);
        setLeftText(leftText);
        setRightText(rightText);
        setRightIcon(rightIcon);
        setRightTextColor(titleColor);
        setTitle(title);
        setTitleColor(titleColor);
        setBackgroundColor(titleBackground);
    }

    private void setRightTextColor(int titleColor) {
        if (titleColor != 0) {
            this.mRightText.setTextColor(titleColor);
            this.mRightText.setVisibility(0);
        }
    }

    private void setTitleColor(int titleColor) {
        if (titleColor != 0) {
            this.mTitle.setTextColor(titleColor);
            this.mTitle.setVisibility(0);
        }
    }

    public void setTitle(String title) {
        setTitle(title, true);
    }

    public void setTitle(String title, boolean alpha) {
        if (TextUtils.isEmpty(title)) {
            this.mTitle.setText("");
            return;
        }
        this.mTitle.setText(title);
        this.mTitle.setVisibility(0);
        if (alpha) {
            this.mTitle.setAlpha(0.0f);
            this.mTitle.animate().alpha(1.0f).start();
        }
    }

    public void setTitle(@StringRes int title) {
        setTitle(getResources().getString(title));
    }

    public void setTitleImage(int resId) {
        this.mTitle.setBackgroundResource(resId);
        this.mTitle.setVisibility(0);
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener listener) {
        this.mListener = listener;
    }

    public void setLeftIcon(@DrawableRes int id) {
        if (id != 0) {
            this.mLeftIcon.setImageResource(id);
            this.mLeftIcon.setVisibility(0);
        }
    }

    public void setRightIcon(@DrawableRes int id) {
        if (id != 0) {
            this.mRightIcon.setImageResource(id);
            this.mRightIcon.setVisibility(0);
        }
    }

    public void setRightIcon(Bitmap bitmap) {
        if (bitmap != null) {
            this.mRightIcon.setImageBitmap(bitmap);
            this.mRightIcon.setVisibility(0);
            this.mRightText.setVisibility(8);
        }
    }

    public View getLeftView() {
        return this.mLeftIcon;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mRightText.setText(text);
            this.mRightText.setVisibility(0);
            this.mRightIcon.setVisibility(8);
        }
    }

    public void setLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mLeftText.setText(text);
            this.mLeftText.setVisibility(0);
        }
    }

    public void hideRight() {
        this.mRightText.setVisibility(8);
        this.mRightIcon.setVisibility(8);
    }

    public ImageView getRightIcon() {
        return this.mRightIcon;
    }

    public TextView getRightText() {
        return this.mRightText;
    }

    public ImageView getLeftIcon() {
        return this.mLeftIcon;
    }
}
