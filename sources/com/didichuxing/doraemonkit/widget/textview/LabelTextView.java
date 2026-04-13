package com.didichuxing.doraemonkit.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.didichuxing.doraemonkit.R;

public class LabelTextView extends LinearLayout {
    private TextView mLabel;
    private TextView mText;

    public LabelTextView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public LabelTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(0);
        LayoutInflater.from(context).inflate(R.layout.dk_label_text_view, this);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LabelTextView);
        String label = a.getString(R.styleable.LabelTextView_dkLabel);
        String text = a.getString(R.styleable.LabelTextView_dkText);
        int maxLines = a.getInt(R.styleable.LabelTextView_dkMaxLines, 0);
        a.recycle();
        this.mLabel = (TextView) findViewById(R.id.label);
        this.mText = (TextView) findViewById(R.id.text);
        setLabel(label);
        setText(text);
        if (maxLines > 0) {
            this.mText.setMaxLines(maxLines);
        }
    }

    public void setText(String text) {
        this.mText.setText(text);
    }

    public void setText(@StringRes int text) {
        this.mText.setText(text);
    }

    public void setLabel(String label) {
        this.mLabel.setText(label);
    }

    public void setLabel(@StringRes int label) {
        this.mLabel.setText(label);
    }

    public void setTextColor(@ColorRes int color) {
        this.mText.setTextColor(getResources().getColor(color));
    }
}
