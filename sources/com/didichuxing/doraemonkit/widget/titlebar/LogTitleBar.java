package com.didichuxing.doraemonkit.widget.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.didichuxing.doraemonkit.R;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class LogTitleBar extends FrameLayout {
    private TextView mBack;
    private ImageView mIcon;
    /* access modifiers changed from: private */
    public OnTitleBarClickListener mListener;
    private TextView mTitle;

    public interface OnTitleBarClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public LogTitleBar(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public LogTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.dk_log_title_bar, this, true);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LogTitleBar);
        int icon = a.getResourceId(R.styleable.LogTitleBar_dkIcon, 0);
        String title = a.getString(R.styleable.LogTitleBar_dkTitle);
        String back = a.getString(R.styleable.LogTitleBar_dkBack);
        a.recycle();
        this.mBack = (TextView) findViewById(R.id.back);
        this.mIcon = (ImageView) findViewById(R.id.icon);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mIcon.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LogTitleBar.this.mListener != null) {
                    LogTitleBar.this.mListener.onRightClick();
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.mBack.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (LogTitleBar.this.mListener != null) {
                    LogTitleBar.this.mListener.onLeftClick();
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        setBack(back);
        setTitle(title);
        setIcon(icon);
    }

    public void setTitle(@StringRes int title) {
        setTitle(getResources().getString(title));
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            this.mTitle.setText("");
            return;
        }
        this.mTitle.setText(title);
        this.mTitle.setAlpha(0.0f);
        this.mTitle.setVisibility(0);
        this.mTitle.animate().alpha(1.0f).start();
    }

    public void setBack(String back) {
        if (TextUtils.isEmpty(back)) {
            this.mBack.setText("");
        } else {
            this.mBack.setText(back);
        }
    }

    public void setIcon(@DrawableRes int id) {
        if (id != 0) {
            this.mIcon.setImageResource(id);
            this.mIcon.setVisibility(0);
        }
    }

    public void setListener(OnTitleBarClickListener listener) {
        this.mListener = listener;
    }
}
