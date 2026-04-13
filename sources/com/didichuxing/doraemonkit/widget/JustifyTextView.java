package com.didichuxing.doraemonkit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class JustifyTextView extends AppCompatTextView {
    public static final String TWO_CHINESE_BLANK = "  ";
    private int mLineY;
    private int mViewWidth;

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        this.mViewWidth = getMeasuredWidth();
        String text = getText().toString();
        this.mLineY = 0;
        this.mLineY = (int) (((float) 0) + getTextSize());
        Layout layout = getLayout();
        if (layout != null) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            int textHeight = (int) ((((float) ((int) Math.ceil((double) (fm.descent - fm.ascent)))) * layout.getSpacingMultiplier()) + layout.getSpacingAdd());
            for (int i = 0; i < layout.getLineCount(); i++) {
                int lineStart = layout.getLineStart(i);
                int lineEnd = layout.getLineEnd(i);
                float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
                String line = text.substring(lineStart, lineEnd);
                if (i >= layout.getLineCount() - 1) {
                    canvas.drawText(line, 0.0f, (float) this.mLineY, paint);
                } else if (needScale(line)) {
                    drawScaledText(canvas, lineStart, line, width);
                } else {
                    canvas.drawText(line, 0.0f, (float) this.mLineY, paint);
                }
                this.mLineY += textHeight;
            }
        }
    }

    private void drawScaledText(Canvas canvas, int lineStart, String line, float lineWidth) {
        float x = 0.0f;
        if (isFirstLineOfParagraph(lineStart, line)) {
            canvas.drawText(TWO_CHINESE_BLANK, 0.0f, (float) this.mLineY, getPaint());
            x = 0.0f + StaticLayout.getDesiredWidth(TWO_CHINESE_BLANK, getPaint());
            line = line.substring(3);
        }
        int gapCount = line.length() - 1;
        int i = 0;
        if (line.length() > 2 && line.charAt(0) == 12288 && line.charAt(1) == 12288) {
            String substring = line.substring(0, 2);
            float cw = StaticLayout.getDesiredWidth(substring, getPaint());
            canvas.drawText(substring, x, (float) this.mLineY, getPaint());
            x += cw;
            i = 0 + 2;
        }
        float d = (((float) this.mViewWidth) - lineWidth) / ((float) gapCount);
        while (i < line.length()) {
            String c = String.valueOf(line.charAt(i));
            float cw2 = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, (float) this.mLineY, getPaint());
            x += cw2 + d;
            i++;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        if (line.length() > 3 && line.charAt(0) == ' ' && line.charAt(1) == ' ') {
            return true;
        }
        return false;
    }

    private boolean needScale(String line) {
        if (line == null || line.length() == 0 || line.charAt(line.length() - 1) == 10) {
            return false;
        }
        return true;
    }
}
