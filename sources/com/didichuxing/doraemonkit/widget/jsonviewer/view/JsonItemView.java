package com.didichuxing.doraemonkit.widget.jsonviewer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.jsonviewer.adapter.BaseJsonViewerAdapter;

public class JsonItemView extends LinearLayout {
    public static int TEXT_SIZE_DP = 12;
    private Context mContext;
    private ImageView mIvIcon;
    private TextView mTvLeft;
    private TextView mTvRight;

    public JsonItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public JsonItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JsonItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(1);
        LayoutInflater.from(this.mContext).inflate(R.layout.dk_jsonviewer_layout_item_view, this, true);
        this.mTvLeft = (TextView) findViewById(R.id.tv_left);
        this.mTvRight = (TextView) findViewById(R.id.tv_right);
        this.mIvIcon = (ImageView) findViewById(R.id.iv_icon);
    }

    public void setTextSize(float textSizeDp) {
        if (textSizeDp < 12.0f) {
            textSizeDp = 12.0f;
        } else if (textSizeDp > 30.0f) {
            textSizeDp = 30.0f;
        }
        int i = (int) textSizeDp;
        TEXT_SIZE_DP = i;
        this.mTvLeft.setTextSize((float) i);
        this.mTvRight.setTextSize((float) TEXT_SIZE_DP);
        this.mTvRight.setTextColor(BaseJsonViewerAdapter.BRACES_COLOR);
        int textSize = (int) TypedValue.applyDimension(1, (float) TEXT_SIZE_DP, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIvIcon.getLayoutParams();
        layoutParams.height = textSize;
        layoutParams.width = textSize;
        layoutParams.topMargin = textSize / 5;
        this.mIvIcon.setLayoutParams(layoutParams);
    }

    public void setRightColor(int color) {
        this.mTvRight.setTextColor(color);
    }

    public void hideLeft() {
        this.mTvLeft.setVisibility(8);
    }

    public void showLeft(CharSequence text) {
        this.mTvLeft.setVisibility(0);
        if (text != null) {
            this.mTvLeft.setText(text);
        }
    }

    public void hideRight() {
        this.mTvRight.setVisibility(8);
    }

    public void showRight(CharSequence text) {
        this.mTvRight.setVisibility(0);
        if (text != null) {
            this.mTvRight.setText(text);
        }
    }

    public CharSequence getRightText() {
        return this.mTvRight.getText();
    }

    public void hideIcon() {
        this.mIvIcon.setVisibility(8);
    }

    public void showIcon(boolean isPlus) {
        this.mIvIcon.setVisibility(0);
        this.mIvIcon.setImageResource(isPlus ? R.drawable.dk_jsonviewer_plus : R.drawable.dk_jsonviewer_minus);
        this.mIvIcon.setContentDescription(getResources().getString(isPlus ? R.string.dk_jsonViewer_icon_plus : R.string.dk_jsonViewer_icon_minus));
    }

    public void setIconClickListener(View.OnClickListener listener) {
        this.mIvIcon.setOnClickListener(listener);
    }

    public void addViewNoInvalidate(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null && (params = generateDefaultLayoutParams()) == null) {
            throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
        }
        addViewInLayout(child, -1, params);
    }
}
