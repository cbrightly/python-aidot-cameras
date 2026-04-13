package com.sensorsdata.analytics.android.sdk.visual.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.sensorsdata.analytics.android.sdk.visual.view.IPairingCodeInterface;
import java.util.Timer;
import java.util.TimerTask;

public class PairingCodeEditText extends EditText implements IPairingCodeInterface, TextWatcher {
    private static final int DEFAULT_CURSOR_DURATION = 400;
    /* access modifiers changed from: private */
    public boolean isCursorShowing;
    private float mBottomLineHeight;
    private int mBottomNormalColor;
    private Paint mBottomNormalPaint;
    private int mBottomSelectedColor;
    private Paint mBottomSelectedPaint;
    private int mCurrentPosition;
    private int mCursorColor;
    private int mCursorDuration;
    private Paint mCursorPaint;
    private Timer mCursorTimer;
    private TimerTask mCursorTimerTask;
    private int mCursorWidth;
    private int mEachRectLength;
    private int mFigures;
    private Paint mNormalBackgroundPaint;
    private int mPairingCodeMargin;
    private int mSelectedBackgroundColor;
    private Paint mSelectedBackgroundPaint;
    private IPairingCodeInterface.OnPairingCodeChangedListener onCodeChangedListener;

    public PairingCodeEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    public PairingCodeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PairingCodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentPosition = 0;
        this.mEachRectLength = 0;
        initAttrs();
        setBackgroundColor(getColor(17170445));
        initPaint();
        initCursorTimer();
        setFocusableInTouchMode(true);
        setSelection(getText().length());
        requestFocus();
        super.addTextChangedListener(this);
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mSelectedBackgroundPaint = paint;
        paint.setColor(this.mSelectedBackgroundColor);
        Paint paint2 = new Paint();
        this.mNormalBackgroundPaint = paint2;
        paint2.setColor(getColor(17170445));
        this.mBottomSelectedPaint = new Paint();
        this.mBottomNormalPaint = new Paint();
        this.mBottomSelectedPaint.setColor(this.mBottomSelectedColor);
        this.mBottomNormalPaint.setColor(this.mBottomNormalColor);
        this.mBottomSelectedPaint.setStrokeWidth(this.mBottomLineHeight);
        this.mBottomNormalPaint.setStrokeWidth(this.mBottomLineHeight);
        Paint paint3 = new Paint();
        this.mCursorPaint = paint3;
        paint3.setAntiAlias(true);
        this.mCursorPaint.setColor(this.mCursorColor);
        this.mCursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mCursorPaint.setStrokeWidth((float) this.mCursorWidth);
    }

    private void initAttrs() {
        this.mFigures = 4;
        this.mPairingCodeMargin = dp2px(10);
        this.mBottomSelectedColor = Color.parseColor("#00c48e");
        this.mBottomNormalColor = getColor(17170432);
        this.mBottomLineHeight = (float) dp2px(2);
        this.mSelectedBackgroundColor = getColor(17170445);
        this.mCursorWidth = dp2px(1);
        this.mCursorColor = Color.parseColor("#00c48e");
        this.mCursorDuration = 400;
        if (Build.VERSION.SDK_INT >= 17) {
            setLayoutDirection(0);
        }
    }

    private void initCursorTimer() {
        this.mCursorTimerTask = new TimerTask() {
            public void run() {
                PairingCodeEditText pairingCodeEditText = PairingCodeEditText.this;
                boolean unused = pairingCodeEditText.isCursorShowing = !pairingCodeEditText.isCursorShowing;
                PairingCodeEditText.this.postInvalidate();
            }
        };
        this.mCursorTimer = new Timer();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mCursorTimer.scheduleAtFixedRate(this.mCursorTimerTask, 0, (long) this.mCursorDuration);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mCursorTimer.cancel();
    }

    public final void setCursorVisible(boolean visible) {
        super.setCursorVisible(visible);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthResult;
        int heightResult;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == 1073741824) {
            widthResult = widthSize;
        } else {
            widthResult = getScreenWidth(getContext());
        }
        int i = this.mPairingCodeMargin;
        int i2 = this.mFigures;
        this.mEachRectLength = (widthResult - (i * (i2 - 1))) / i2;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == 1073741824) {
            heightResult = heightSize;
        } else {
            heightResult = this.mEachRectLength;
        }
        setMeasuredDimension(widthResult, heightResult);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mCurrentPosition = getText().length();
        int width = (this.mEachRectLength - getPaddingLeft()) - getPaddingRight();
        int height = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        for (int i = 0; i < this.mFigures; i++) {
            canvas.save();
            int start = (width * i) + (this.mPairingCodeMargin * i);
            int end = width + start;
            if (i == this.mCurrentPosition) {
                canvas.drawRect((float) start, 0.0f, (float) end, (float) height, this.mSelectedBackgroundPaint);
            } else {
                canvas.drawRect((float) start, 0.0f, (float) end, (float) height, this.mNormalBackgroundPaint);
            }
            canvas.restore();
        }
        String value = getText().toString();
        for (int i2 = 0; i2 < value.length(); i2++) {
            canvas.save();
            int start2 = (width * i2) + (this.mPairingCodeMargin * i2);
            TextPaint paint = getPaint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(getCurrentTextColor());
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float f = ((float) height) - fontMetrics.bottom;
            float f2 = fontMetrics.top;
            Canvas canvas2 = canvas;
            canvas2.drawText(String.valueOf(value.charAt(i2)), (float) ((width / 2) + start2), ((f + f2) / 2.0f) - f2, paint);
            canvas.restore();
        }
        Canvas canvas3 = canvas;
        int i3 = 0;
        while (i3 < this.mFigures) {
            canvas.save();
            float lineY = ((float) height) - (this.mBottomLineHeight / 2.0f);
            int start3 = (width * i3) + (this.mPairingCodeMargin * i3);
            int end2 = width + start3;
            if (i3 < this.mCurrentPosition) {
                canvas.drawLine((float) start3, lineY, (float) end2, lineY, this.mBottomSelectedPaint);
            } else {
                canvas.drawLine((float) start3, lineY, (float) end2, lineY, this.mBottomNormalPaint);
            }
            canvas.restore();
            i3++;
            Canvas canvas4 = canvas;
        }
        boolean isCursorVisible = true;
        if (Build.VERSION.SDK_INT >= 16) {
            isCursorVisible = isCursorVisible();
        }
        if (!this.isCursorShowing && isCursorVisible && this.mCurrentPosition < this.mFigures && hasFocus()) {
            canvas.save();
            int startX = (this.mCurrentPosition * (this.mPairingCodeMargin + width)) + (width / 2);
            canvas.drawLine((float) startX, (float) (height / 4), (float) startX, (float) (height - (height / 4)), this.mCursorPaint);
            canvas.restore();
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.mCurrentPosition = getText().length();
        postInvalidate();
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.mCurrentPosition = getText().length();
        postInvalidate();
        IPairingCodeInterface.OnPairingCodeChangedListener onPairingCodeChangedListener = this.onCodeChangedListener;
        if (onPairingCodeChangedListener != null) {
            onPairingCodeChangedListener.onPairingCodeChanged(getText(), start, before, count);
        }
    }

    public void afterTextChanged(Editable s) {
        this.mCurrentPosition = getText().length();
        postInvalidate();
        if (getText().length() == this.mFigures) {
            IPairingCodeInterface.OnPairingCodeChangedListener onPairingCodeChangedListener = this.onCodeChangedListener;
            if (onPairingCodeChangedListener != null) {
                onPairingCodeChangedListener.onInputCompleted(getText());
            }
        } else if (getText().length() > this.mFigures) {
            getText().delete(this.mFigures, getText().length());
        }
    }

    public void setFigures(int figures) {
        this.mFigures = figures;
        postInvalidate();
    }

    public void setPairingCodeMargin(int margin) {
        this.mPairingCodeMargin = margin;
        postInvalidate();
    }

    public void setBottomSelectedColor(int bottomSelectedColor) {
        this.mBottomSelectedColor = getColor(bottomSelectedColor);
        postInvalidate();
    }

    public void setBottomNormalColor(int bottomNormalColor) {
        this.mBottomSelectedColor = getColor(bottomNormalColor);
        postInvalidate();
    }

    public void setSelectedBackgroundColor(int selectedBackground) {
        this.mSelectedBackgroundColor = getColor(selectedBackground);
        postInvalidate();
    }

    public void setBottomLineHeight(int bottomLineHeight) {
        this.mBottomLineHeight = (float) bottomLineHeight;
        postInvalidate();
    }

    public void setOnPairingCodeChangedListener(IPairingCodeInterface.OnPairingCodeChangedListener listener) {
        this.onCodeChangedListener = listener;
    }

    private int getColor(int color) {
        return getContext().getResources().getColor(color);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics());
    }

    static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService("window");
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
        }
        return metrics.widthPixels;
    }

    /* access modifiers changed from: package-private */
    public void showKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
        if (imm != null) {
            imm.showSoftInput(this, 2);
        }
    }

    /* access modifiers changed from: package-private */
    public void hiddenKeyBord() {
        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (mInputMethodManager != null) {
            mInputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: package-private */
    public void clearText() {
        getText().delete(0, getText().length());
    }
}
