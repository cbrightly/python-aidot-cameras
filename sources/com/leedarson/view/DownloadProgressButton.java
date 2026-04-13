package com.leedarson.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.leedarson.R$styleable;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class DownloadProgressButton extends AppCompatTextView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private float A4;
    private float B4;
    private float C4;
    private float D4;
    private boolean E4;
    private RectF F4;
    private LinearGradient G4;
    private ValueAnimator H4;
    private CharSequence I4;
    private int J4;
    private ArrayList<ValueAnimator> K4;
    /* access modifiers changed from: private */
    public float[] L4;
    /* access modifiers changed from: private */
    public float[] M4;
    private float a1;
    /* access modifiers changed from: private */
    public float a2;
    private Paint c;
    private volatile Paint d;
    private int f;
    private float p0;
    private int p1;
    /* access modifiers changed from: private */
    public float p2;
    private int p3;
    private int p4;
    private int q;
    private int x;
    private int y;
    private int z;
    private float z4;

    public DownloadProgressButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public DownloadProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.p1 = 2;
        this.a2 = -1.0f;
        this.C4 = 4.0f;
        this.D4 = 6.0f;
        this.L4 = new float[]{1.0f, 1.0f, 1.0f};
        this.M4 = new float[3];
        if (!isInEditMode()) {
            n(context, attrs);
            m();
            p();
        }
    }

    private void n(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 11623, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            TypedArray a3 = context.obtainStyledAttributes(attrs, R$styleable.DownloadProgressButton);
            try {
                this.f = a3.getColor(R$styleable.DownloadProgressButton_progress_btn_background_color, Color.parseColor("#3385FF"));
                this.q = a3.getColor(R$styleable.DownloadProgressButton_progress_btn_background_second_color, Color.parseColor("#ffffff"));
                this.x = a3.getColor(R$styleable.DownloadProgressButton_progress_border_color, 0);
                this.p0 = a3.getDimension(R$styleable.DownloadProgressButton_progress_btn_radius, 0.0f);
                this.y = a3.getColor(R$styleable.DownloadProgressButton_progress_btn_text_color, this.f);
                this.z = a3.getColor(R$styleable.DownloadProgressButton_progress_btn_text_cover_color, -1);
                this.a1 = a3.getDimension(R$styleable.DownloadProgressButton_progress_btn_border_width, (float) h(2));
                this.p1 = a3.getInt(R$styleable.DownloadProgressButton_progress_btn_ball_style, 2);
            } finally {
                a3.recycle();
            }
        }
    }

    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11624, new Class[0], Void.TYPE).isSupported) {
            this.p3 = 100;
            this.p4 = 0;
            this.a2 = 0.0f;
            this.E4 = true;
            Paint paint = new Paint();
            this.c = paint;
            paint.setAntiAlias(true);
            this.c.setStyle(Paint.Style.FILL);
            this.d = new Paint();
            this.d.setAntiAlias(true);
            this.d.setTextSize((float) h(16));
            if (Build.VERSION.SDK_INT >= 11) {
                setLayerType(1, this.d);
            }
            this.J4 = 0;
            invalidate();
        }
    }

    public class a implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11643, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                float timePercent = ((Float) animation.getAnimatedValue()).floatValue();
                DownloadProgressButton downloadProgressButton = DownloadProgressButton.this;
                float unused = downloadProgressButton.a2 = ((downloadProgressButton.p2 - DownloadProgressButton.this.a2) * timePercent) + DownloadProgressButton.this.a2;
                DownloadProgressButton.this.invalidate();
            }
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11625, new Class[0], Void.TYPE).isSupported) {
            ValueAnimator duration = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}).setDuration(500);
            this.H4 = duration;
            duration.addUpdateListener(new a());
            setBallStyle(this.p1);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11626, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            if (!isInEditMode()) {
                l(canvas);
            }
        }
    }

    private void l(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11627, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            i(canvas);
            k(canvas);
        }
    }

    private void i(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11628, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            RectF rectF = new RectF();
            this.F4 = rectF;
            boolean z2 = this.E4;
            rectF.left = z2 ? this.a1 : 0.0f;
            rectF.top = z2 ? this.a1 : 0.0f;
            rectF.right = ((float) getMeasuredWidth()) - (this.E4 ? this.a1 : 0.0f);
            RectF rectF2 = this.F4;
            float measuredHeight = (float) getMeasuredHeight();
            boolean z3 = this.E4;
            rectF2.bottom = measuredHeight - (z3 ? this.a1 : 0.0f);
            if (z3) {
                this.c.setStyle(Paint.Style.STROKE);
                this.c.setColor(this.x);
                this.c.setStrokeWidth(this.a1);
                RectF rectF3 = this.F4;
                float f2 = this.p0;
                canvas.drawRoundRect(rectF3, f2, f2, this.c);
            }
            this.c.setStyle(Paint.Style.FILL);
            switch (this.J4) {
                case 0:
                    this.c.setColor(this.f);
                    RectF rectF4 = this.F4;
                    float f3 = this.p0;
                    canvas.drawRoundRect(rectF4, f3, f3, this.c);
                    return;
                case 1:
                case 2:
                    this.z4 = this.a2 / (((float) this.p3) + 0.0f);
                    this.c.setColor(this.q);
                    canvas.save();
                    RectF rectF5 = this.F4;
                    float f4 = this.p0;
                    canvas.drawRoundRect(rectF5, f4, f4, this.c);
                    PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
                    this.c.setColor(this.f);
                    this.c.setXfermode(porterDuffXfermode);
                    RectF rectF6 = this.F4;
                    canvas.drawRect(rectF6.left, rectF6.top, rectF6.right * this.z4, rectF6.bottom, this.c);
                    canvas.restore();
                    this.c.setXfermode((Xfermode) null);
                    return;
                case 3:
                    this.c.setColor(this.f);
                    RectF rectF7 = this.F4;
                    float f5 = this.p0;
                    canvas.drawRoundRect(rectF7, f5, f5, this.c);
                    return;
                default:
                    return;
            }
        }
    }

    private void k(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11629, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Canvas canvas2 = canvas;
            float y2 = ((float) (canvas2.getHeight() / 2)) - ((this.d.descent() / 2.0f) + (this.d.ascent() / 2.0f));
            if (this.I4 == null) {
                this.I4 = "";
            }
            float textWidth = this.d.measureText(this.I4.toString());
            this.B4 = y2;
            this.A4 = (((float) getMeasuredWidth()) + textWidth) / 2.0f;
            switch (this.J4) {
                case 0:
                    this.d.setShader((Shader) null);
                    this.d.setColor(this.z);
                    canvas2.drawText(this.I4.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y2, this.d);
                    return;
                case 1:
                case 2:
                    float coverLength = ((float) getMeasuredWidth()) * this.z4;
                    float indicator1 = ((float) (getMeasuredWidth() / 2)) - (textWidth / 2.0f);
                    float indicator2 = ((float) (getMeasuredWidth() / 2)) + (textWidth / 2.0f);
                    float textProgress = (((textWidth / 2.0f) - ((float) (getMeasuredWidth() / 2))) + coverLength) / textWidth;
                    if (coverLength <= indicator1) {
                        this.d.setShader((Shader) null);
                        this.d.setColor(this.y);
                    } else if (indicator1 >= coverLength || coverLength > indicator2) {
                        this.d.setShader((Shader) null);
                        this.d.setColor(this.z);
                    } else {
                        this.G4 = new LinearGradient((((float) getMeasuredWidth()) - textWidth) / 2.0f, 0.0f, (((float) getMeasuredWidth()) + textWidth) / 2.0f, 0.0f, new int[]{this.z, this.y}, new float[]{textProgress, 0.001f + textProgress}, Shader.TileMode.CLAMP);
                        this.d.setColor(this.y);
                        this.d.setShader(this.G4);
                    }
                    canvas2.drawText(this.I4.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y2, this.d);
                    return;
                case 3:
                    this.d.setColor(this.z);
                    canvas2.drawText(this.I4.toString(), (((float) getMeasuredWidth()) - textWidth) / 2.0f, y2, this.d);
                    j(canvas2);
                    return;
                default:
                    return;
            }
        }
    }

    public void j(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11630, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            for (int i = 0; i < 3; i++) {
                canvas.save();
                canvas.translate(this.A4 + 10.0f + (this.D4 * 2.0f * ((float) i)) + (this.C4 * ((float) i)), this.B4);
                canvas.drawCircle(0.0f, this.M4[i], this.D4 * this.L4[i], this.d);
                canvas.restore();
            }
        }
    }

    private void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11631, new Class[0], Void.TYPE).isSupported) {
            for (int i = 0; i < this.K4.size(); i++) {
                this.K4.get(i).start();
            }
        }
    }

    private void r() {
        ArrayList<ValueAnimator> arrayList;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11632, new Class[0], Void.TYPE).isSupported && (arrayList = this.K4) != null) {
            Iterator<ValueAnimator> it = arrayList.iterator();
            while (it.hasNext()) {
                ValueAnimator animator = it.next();
                if (animator != null && animator.isStarted()) {
                    animator.end();
                }
            }
        }
    }

    public ArrayList<ValueAnimator> g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11633, new Class[0], ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = {120, 240, 360};
        for (int i = 0; i < 3; i++) {
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.3f, 1.0f});
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay((long) delays[i]);
            scaleAnim.addUpdateListener(new b(i));
            animators.add(scaleAnim);
        }
        return animators;
    }

    public class b implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        b(int i) {
            this.c = i;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11644, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                DownloadProgressButton.this.L4[this.c] = ((Float) animation.getAnimatedValue()).floatValue();
                DownloadProgressButton.this.postInvalidate();
            }
        }
    }

    public ArrayList<ValueAnimator> f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11634, new Class[0], ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = {70, NeedPermissionEvent.PER_ANDROID_S_BLE, 210};
        for (int i = 0; i < 3; i++) {
            float f2 = this.B4;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{f2, f2 - (this.D4 * 2.0f), f2});
            scaleAnim.setDuration(600);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay((long) delays[i]);
            scaleAnim.addUpdateListener(new c(i));
            animators.add(scaleAnim);
        }
        return animators;
    }

    public class c implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        c(int i) {
            this.c = i;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11645, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                DownloadProgressButton.this.M4[this.c] = ((Float) animation.getAnimatedValue()).floatValue();
                DownloadProgressButton.this.postInvalidate();
            }
        }
    }

    public int getState() {
        return this.J4;
    }

    public void setState(int state) {
        Object[] objArr = {new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11635, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.J4 != state) {
                this.J4 = state;
                invalidate();
                if (state == 3) {
                    q();
                } else {
                    r();
                }
            }
        }
    }

    public void setCurrentText(CharSequence charSequence) {
        if (!PatchProxy.proxy(new Object[]{charSequence}, this, changeQuickRedirect, false, 11636, new Class[]{CharSequence.class}, Void.TYPE).isSupported) {
            this.I4 = charSequence;
            invalidate();
        }
    }

    @TargetApi(19)
    public void o(String text, float progress) {
        if (!PatchProxy.proxy(new Object[]{text, new Float(progress)}, this, changeQuickRedirect, false, 11637, new Class[]{String.class, Float.TYPE}, Void.TYPE).isSupported) {
            int i = this.p4;
            if (progress >= ((float) i) && progress <= ((float) this.p3)) {
                DecimalFormat format = new DecimalFormat("#");
                this.I4 = text + format.format((double) progress) + "%";
                this.p2 = progress;
                if (this.H4.isRunning()) {
                    this.H4.resume();
                    this.H4.start();
                    return;
                }
                this.H4.start();
            } else if (progress < ((float) i)) {
                this.a2 = 0.0f;
            } else if (progress > ((float) this.p3)) {
                this.a2 = 100.0f;
                this.I4 = text + progress + "%";
                invalidate();
            }
        }
    }

    private void setBallStyle(int mBallStyle) {
        Object[] objArr = {new Integer(mBallStyle)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11638, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.p1 = mBallStyle;
            if (mBallStyle == 1) {
                this.K4 = g();
            } else {
                this.K4 = f();
            }
        }
    }

    public void setShowBorder(boolean showBorder) {
        this.E4 = showBorder;
    }

    public float getBorderWidth() {
        return this.a1;
    }

    public void setBorderWidth(int width) {
        Object[] objArr = {new Integer(width)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11639, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.a1 = (float) h(width);
        }
    }

    public float getProgress() {
        return this.a2;
    }

    public void setProgress(float progress) {
        this.a2 = progress;
    }

    public float getButtonRadius() {
        return this.p0;
    }

    public void setButtonRadius(float buttonRadius) {
        this.p0 = buttonRadius;
    }

    public int getTextColor() {
        return this.y;
    }

    public void setTextColor(int textColor) {
        this.y = textColor;
    }

    public int getTextCoverColor() {
        return this.z;
    }

    public void setTextCoverColor(int textCoverColor) {
        this.z = textCoverColor;
    }

    public int getMinProgress() {
        return this.p4;
    }

    public void setMinProgress(int minProgress) {
        this.p4 = minProgress;
    }

    public int getMaxProgress() {
        return this.p3;
    }

    public void setMaxProgress(int maxProgress) {
        this.p3 = maxProgress;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 11640, new Class[]{Parcelable.class}, Void.TYPE).isSupported) {
            try {
                SavedState ss = (SavedState) state;
                super.onRestoreInstanceState(ss.getSuperState());
                this.J4 = ss.d;
                this.a2 = (float) ss.c;
                this.I4 = ss.f;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11641, new Class[0], Parcelable.class);
        if (proxy.isSupported) {
            return (Parcelable) proxy.result;
        }
        try {
            return new SavedState(super.onSaveInstanceState(), (int) this.a2, this.J4, this.I4.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return super.onSaveInstanceState();
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public int d;
        /* access modifiers changed from: private */
        public String f;

        /* synthetic */ SavedState(Parcel x0, a x1) {
            this(x0);
        }

        public SavedState(Parcelable parcel, int progress, int state, String currentText) {
            super(parcel);
            this.c = progress;
            this.d = state;
            this.f = currentText;
        }

        private SavedState(Parcel in) {
            super(in);
            this.c = in.readInt();
            this.d = in.readInt();
            this.f = in.readString();
        }

        public void writeToParcel(Parcel out, int flags) {
            if (!PatchProxy.proxy(new Object[]{out, new Integer(flags)}, this, changeQuickRedirect, false, 11646, new Class[]{Parcel.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.writeToParcel(out, flags);
                out.writeInt(this.c);
                out.writeInt(this.d);
                out.writeString(this.f);
            }
        }

        public class a implements Parcelable.Creator<SavedState> {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parcel}, this, changeQuickRedirect, false, 11649, new Class[]{Parcel.class}, Object.class);
                return proxy.isSupported ? proxy.result : a(parcel);
            }

            public /* bridge */ /* synthetic */ Object[] newArray(int i) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 11648, new Class[]{Integer.TYPE}, Object[].class);
                return proxy.isSupported ? (Object[]) proxy.result : b(i);
            }

            public SavedState a(Parcel in) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{in}, this, changeQuickRedirect, false, 11647, new Class[]{Parcel.class}, SavedState.class);
                if (proxy.isSupported) {
                    return (SavedState) proxy.result;
                }
                return new SavedState(in, (a) null);
            }

            public SavedState[] b(int size) {
                return new SavedState[size];
            }
        }
    }

    private int h(int dp) {
        Object[] objArr = {new Integer(dp)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11642, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) (((float) dp) * getContext().getResources().getDisplayMetrics().density);
    }
}
