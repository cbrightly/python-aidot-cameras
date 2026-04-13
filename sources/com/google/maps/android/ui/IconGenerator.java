package com.google.maps.android.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.maps.android.R;

public class IconGenerator {
    public static final int STYLE_BLUE = 4;
    public static final int STYLE_DEFAULT = 1;
    public static final int STYLE_GREEN = 5;
    public static final int STYLE_ORANGE = 7;
    public static final int STYLE_PURPLE = 6;
    public static final int STYLE_RED = 3;
    public static final int STYLE_WHITE = 2;
    private float mAnchorU = 0.5f;
    private float mAnchorV = 1.0f;
    private BubbleDrawable mBackground;
    private ViewGroup mContainer;
    private View mContentView;
    private final Context mContext;
    private int mRotation;
    private RotationLayout mRotationLayout;
    private TextView mTextView;

    public IconGenerator(Context context) {
        this.mContext = context;
        this.mBackground = new BubbleDrawable(context);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.amu_text_bubble, (ViewGroup) null);
        this.mContainer = viewGroup;
        RotationLayout rotationLayout = (RotationLayout) viewGroup.getChildAt(0);
        this.mRotationLayout = rotationLayout;
        TextView textView = (TextView) rotationLayout.findViewById(R.id.amu_text);
        this.mTextView = textView;
        this.mContentView = textView;
        setStyle(1);
    }

    public Bitmap makeIcon(CharSequence text) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setText(text);
        }
        return makeIcon();
    }

    public Bitmap makeIcon() {
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContainer.measure(measureSpec, measureSpec);
        int measuredWidth = this.mContainer.getMeasuredWidth();
        int measuredHeight = this.mContainer.getMeasuredHeight();
        this.mContainer.layout(0, 0, measuredWidth, measuredHeight);
        int i = this.mRotation;
        if (i == 1 || i == 3) {
            measuredHeight = this.mContainer.getMeasuredWidth();
            measuredWidth = this.mContainer.getMeasuredHeight();
        }
        Bitmap r = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        r.eraseColor(0);
        Canvas canvas = new Canvas(r);
        switch (this.mRotation) {
            case 1:
                canvas.translate((float) measuredWidth, 0.0f);
                canvas.rotate(90.0f);
                break;
            case 2:
                canvas.rotate(180.0f, (float) (measuredWidth / 2), (float) (measuredHeight / 2));
                break;
            case 3:
                canvas.translate(0.0f, (float) measuredHeight);
                canvas.rotate(270.0f);
                break;
        }
        this.mContainer.draw(canvas);
        return r;
    }

    public void setContentView(View contentView) {
        this.mRotationLayout.removeAllViews();
        this.mRotationLayout.addView(contentView);
        this.mContentView = contentView;
        View view = this.mRotationLayout.findViewById(R.id.amu_text);
        this.mTextView = view instanceof TextView ? (TextView) view : null;
    }

    public void setContentRotation(int degrees) {
        this.mRotationLayout.setViewRotation(degrees);
    }

    public void setRotation(int degrees) {
        this.mRotation = ((degrees + 360) % 360) / 90;
    }

    public float getAnchorU() {
        return rotateAnchor(this.mAnchorU, this.mAnchorV);
    }

    public float getAnchorV() {
        return rotateAnchor(this.mAnchorV, this.mAnchorU);
    }

    private float rotateAnchor(float u, float v) {
        switch (this.mRotation) {
            case 0:
                return u;
            case 1:
                return 1.0f - v;
            case 2:
                return 1.0f - u;
            case 3:
                return v;
            default:
                throw new IllegalStateException();
        }
    }

    public void setTextAppearance(Context context, int resid) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setTextAppearance(context, resid);
        }
    }

    public void setTextAppearance(int resid) {
        setTextAppearance(this.mContext, resid);
    }

    public void setStyle(int style) {
        setColor(getStyleColor(style));
        setTextAppearance(this.mContext, getTextStyle(style));
    }

    public void setColor(int color) {
        this.mBackground.setColor(color);
        setBackground(this.mBackground);
    }

    public void setBackground(Drawable background) {
        this.mContainer.setBackgroundDrawable(background);
        if (background != null) {
            Rect rect = new Rect();
            background.getPadding(rect);
            this.mContainer.setPadding(rect.left, rect.top, rect.right, rect.bottom);
            return;
        }
        this.mContainer.setPadding(0, 0, 0, 0);
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.mContentView.setPadding(left, top, right, bottom);
    }

    private static int getStyleColor(int style) {
        switch (style) {
            case 3:
                return -3407872;
            case 4:
                return -16737844;
            case 5:
                return -10053376;
            case 6:
                return -6736948;
            case 7:
                return -30720;
            default:
                return -1;
        }
    }

    private static int getTextStyle(int style) {
        switch (style) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return R.style.amu_Bubble_TextAppearance_Light;
            default:
                return R.style.amu_Bubble_TextAppearance_Dark;
        }
    }
}
