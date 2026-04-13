package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.widget.AutoSizeableTextView;
import java.lang.ref.WeakReference;

public class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;
    @NonNull
    private final TextView mView;

    AppCompatTextHelper(@NonNull TextView view) {
        this.mView = view;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(view);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x01fa  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0201  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0232  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x028a  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x02a9  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x02c1  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x02d1  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x02e1  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x02e8  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02f2  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0300  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0329  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x033a  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0362  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x0369  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0370  */
    /* JADX WARNING: Removed duplicated region for block: B:157:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0185  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01a2  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01c8  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01d6  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01e6  */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadFromAttributes(@androidx.annotation.Nullable android.util.AttributeSet r37, int r38) {
        /*
            r36 = this;
            r7 = r36
            r8 = r37
            r9 = r38
            android.widget.TextView r0 = r7.mView
            android.content.Context r10 = r0.getContext()
            androidx.appcompat.widget.AppCompatDrawableManager r11 = androidx.appcompat.widget.AppCompatDrawableManager.get()
            int[] r2 = androidx.appcompat.R.styleable.AppCompatTextHelper
            r12 = 0
            androidx.appcompat.widget.TintTypedArray r13 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r10, r8, r2, r9, r12)
            android.widget.TextView r0 = r7.mView
            android.content.Context r1 = r0.getContext()
            android.content.res.TypedArray r4 = r13.getWrappedTypeArray()
            r6 = 0
            r3 = r37
            r5 = r38
            androidx.core.view.ViewCompat.saveAttributeDataForStyleable(r0, r1, r2, r3, r4, r5, r6)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_textAppearance
            r14 = -1
            int r15 = r13.getResourceId(r0, r14)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableLeft
            boolean r1 = r13.hasValue(r0)
            if (r1 == 0) goto L_0x0043
            int r0 = r13.getResourceId(r0, r12)
            androidx.appcompat.widget.TintInfo r0 = createTintInfo(r10, r11, r0)
            r7.mDrawableLeftTint = r0
        L_0x0043:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableTop
            boolean r1 = r13.hasValue(r0)
            if (r1 == 0) goto L_0x0056
            int r0 = r13.getResourceId(r0, r12)
            androidx.appcompat.widget.TintInfo r0 = createTintInfo(r10, r11, r0)
            r7.mDrawableTopTint = r0
        L_0x0056:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableRight
            boolean r1 = r13.hasValue(r0)
            if (r1 == 0) goto L_0x0069
            int r0 = r13.getResourceId(r0, r12)
            androidx.appcompat.widget.TintInfo r0 = createTintInfo(r10, r11, r0)
            r7.mDrawableRightTint = r0
        L_0x0069:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableBottom
            boolean r1 = r13.hasValue(r0)
            if (r1 == 0) goto L_0x007c
            int r0 = r13.getResourceId(r0, r12)
            androidx.appcompat.widget.TintInfo r0 = createTintInfo(r10, r11, r0)
            r7.mDrawableBottomTint = r0
        L_0x007c:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 17
            if (r0 < r1) goto L_0x00a8
            int r1 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableStart
            boolean r2 = r13.hasValue(r1)
            if (r2 == 0) goto L_0x0095
            int r1 = r13.getResourceId(r1, r12)
            androidx.appcompat.widget.TintInfo r1 = createTintInfo(r10, r11, r1)
            r7.mDrawableStartTint = r1
        L_0x0095:
            int r1 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableEnd
            boolean r2 = r13.hasValue(r1)
            if (r2 == 0) goto L_0x00a8
            int r1 = r13.getResourceId(r1, r12)
            androidx.appcompat.widget.TintInfo r1 = createTintInfo(r10, r11, r1)
            r7.mDrawableEndTint = r1
        L_0x00a8:
            r13.recycle()
            android.widget.TextView r1 = r7.mView
            android.text.method.TransformationMethod r1 = r1.getTransformationMethod()
            boolean r6 = r1 instanceof android.text.method.PasswordTransformationMethod
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r16 = 0
            r17 = 0
            if (r15 == r14) goto L_0x0122
            int[] r14 = androidx.appcompat.R.styleable.TextAppearance
            androidx.appcompat.widget.TintTypedArray r13 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes((android.content.Context) r10, (int) r15, (int[]) r14)
            if (r6 != 0) goto L_0x00d4
            int r14 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r21 = r13.hasValue(r14)
            if (r21 == 0) goto L_0x00d4
            r2 = 1
            r12 = 0
            boolean r1 = r13.getBoolean(r14, r12)
        L_0x00d4:
            r7.updateTypefaceAndStyle(r10, r13)
            r12 = 23
            if (r0 >= r12) goto L_0x00ff
            int r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            boolean r14 = r13.hasValue(r12)
            if (r14 == 0) goto L_0x00e7
            android.content.res.ColorStateList r3 = r13.getColorStateList(r12)
        L_0x00e7:
            int r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            boolean r14 = r13.hasValue(r12)
            if (r14 == 0) goto L_0x00f3
            android.content.res.ColorStateList r4 = r13.getColorStateList(r12)
        L_0x00f3:
            int r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            boolean r14 = r13.hasValue(r12)
            if (r14 == 0) goto L_0x00ff
            android.content.res.ColorStateList r5 = r13.getColorStateList(r12)
        L_0x00ff:
            int r12 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            boolean r14 = r13.hasValue(r12)
            if (r14 == 0) goto L_0x010d
            java.lang.String r12 = r13.getString(r12)
            r17 = r12
        L_0x010d:
            r12 = 26
            if (r0 < r12) goto L_0x011f
            int r12 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            boolean r14 = r13.hasValue(r12)
            if (r14 == 0) goto L_0x011f
            java.lang.String r12 = r13.getString(r12)
            r16 = r12
        L_0x011f:
            r13.recycle()
        L_0x0122:
            int[] r12 = androidx.appcompat.R.styleable.TextAppearance
            r14 = 0
            androidx.appcompat.widget.TintTypedArray r12 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r10, r8, r12, r9, r14)
            if (r6 != 0) goto L_0x013b
            int r13 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r18 = r12.hasValue(r13)
            if (r18 == 0) goto L_0x013b
            r2 = 1
            boolean r1 = r12.getBoolean(r13, r14)
            r13 = r1
            r14 = r2
            goto L_0x013d
        L_0x013b:
            r13 = r1
            r14 = r2
        L_0x013d:
            r1 = 23
            if (r0 >= r1) goto L_0x0171
            int r1 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            boolean r2 = r12.hasValue(r1)
            if (r2 == 0) goto L_0x014d
            android.content.res.ColorStateList r3 = r12.getColorStateList(r1)
        L_0x014d:
            int r1 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            boolean r2 = r12.hasValue(r1)
            if (r2 == 0) goto L_0x0159
            android.content.res.ColorStateList r4 = r12.getColorStateList(r1)
        L_0x0159:
            int r1 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            boolean r2 = r12.hasValue(r1)
            if (r2 == 0) goto L_0x016b
            android.content.res.ColorStateList r5 = r12.getColorStateList(r1)
            r35 = r5
            r5 = r3
            r3 = r35
            goto L_0x0176
        L_0x016b:
            r35 = r5
            r5 = r3
            r3 = r35
            goto L_0x0176
        L_0x0171:
            r35 = r5
            r5 = r3
            r3 = r35
        L_0x0176:
            int r1 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            boolean r2 = r12.hasValue(r1)
            if (r2 == 0) goto L_0x0185
            java.lang.String r17 = r12.getString(r1)
            r2 = r17
            goto L_0x0187
        L_0x0185:
            r2 = r17
        L_0x0187:
            r1 = 26
            if (r0 < r1) goto L_0x019a
            int r1 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            boolean r17 = r12.hasValue(r1)
            if (r17 == 0) goto L_0x019a
            java.lang.String r16 = r12.getString(r1)
            r1 = r16
            goto L_0x019c
        L_0x019a:
            r1 = r16
        L_0x019c:
            r16 = r15
            r15 = 28
            if (r0 < r15) goto L_0x01be
            int r15 = androidx.appcompat.R.styleable.TextAppearance_android_textSize
            boolean r17 = r12.hasValue(r15)
            if (r17 == 0) goto L_0x01bb
            r17 = r11
            r11 = -1
            int r15 = r12.getDimensionPixelSize(r15, r11)
            if (r15 != 0) goto L_0x01c0
            android.widget.TextView r11 = r7.mView
            r15 = 0
            r8 = 0
            r11.setTextSize(r8, r15)
            goto L_0x01c0
        L_0x01bb:
            r17 = r11
            goto L_0x01c0
        L_0x01be:
            r17 = r11
        L_0x01c0:
            r7.updateTypefaceAndStyle(r10, r12)
            r12.recycle()
            if (r5 == 0) goto L_0x01cd
            android.widget.TextView r8 = r7.mView
            r8.setTextColor(r5)
        L_0x01cd:
            if (r4 == 0) goto L_0x01d4
            android.widget.TextView r8 = r7.mView
            r8.setHintTextColor(r4)
        L_0x01d4:
            if (r3 == 0) goto L_0x01db
            android.widget.TextView r8 = r7.mView
            r8.setLinkTextColor(r3)
        L_0x01db:
            if (r6 != 0) goto L_0x01e2
            if (r14 == 0) goto L_0x01e2
            r7.setAllCaps(r13)
        L_0x01e2:
            android.graphics.Typeface r8 = r7.mFontTypeface
            if (r8 == 0) goto L_0x01f8
            int r11 = r7.mFontWeight
            r15 = -1
            if (r11 != r15) goto L_0x01f3
            android.widget.TextView r11 = r7.mView
            int r15 = r7.mStyle
            r11.setTypeface(r8, r15)
            goto L_0x01f8
        L_0x01f3:
            android.widget.TextView r11 = r7.mView
            r11.setTypeface(r8)
        L_0x01f8:
            if (r1 == 0) goto L_0x01ff
            android.widget.TextView r8 = r7.mView
            r8.setFontVariationSettings(r1)
        L_0x01ff:
            if (r2 == 0) goto L_0x0227
            r8 = 24
            if (r0 < r8) goto L_0x020f
            android.widget.TextView r0 = r7.mView
            android.os.LocaleList r8 = android.os.LocaleList.forLanguageTags(r2)
            r0.setTextLocales(r8)
            goto L_0x0227
        L_0x020f:
            r8 = 21
            if (r0 < r8) goto L_0x0227
            r0 = 44
            int r0 = r2.indexOf(r0)
            r8 = 0
            java.lang.String r0 = r2.substring(r8, r0)
            android.widget.TextView r8 = r7.mView
            java.util.Locale r11 = java.util.Locale.forLanguageTag(r0)
            r8.setTextLocale(r11)
        L_0x0227:
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r0 = r7.mAutoSizeTextHelper
            r8 = r37
            r0.loadFromAttributes(r8, r9)
            boolean r0 = androidx.core.widget.AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE
            if (r0 == 0) goto L_0x028a
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r0 = r7.mAutoSizeTextHelper
            int r0 = r0.getAutoSizeTextType()
            if (r0 == 0) goto L_0x0283
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r0 = r7.mAutoSizeTextHelper
            int[] r0 = r0.getAutoSizeTextAvailableSizes()
            int r11 = r0.length
            if (r11 <= 0) goto L_0x027c
            android.widget.TextView r11 = r7.mView
            int r11 = r11.getAutoSizeStepGranularity()
            float r11 = (float) r11
            r15 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r11 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r11 == 0) goto L_0x026f
            android.widget.TextView r11 = r7.mView
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r15 = r7.mAutoSizeTextHelper
            int r15 = r15.getAutoSizeMinTextSize()
            r19 = r1
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r1 = r7.mAutoSizeTextHelper
            int r1 = r1.getAutoSizeMaxTextSize()
            r21 = r2
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r2 = r7.mAutoSizeTextHelper
            int r2 = r2.getAutoSizeStepGranularity()
            r22 = r3
            r3 = 0
            r11.setAutoSizeTextTypeUniformWithConfiguration(r15, r1, r2, r3)
            goto L_0x0290
        L_0x026f:
            r19 = r1
            r21 = r2
            r22 = r3
            r3 = 0
            android.widget.TextView r1 = r7.mView
            r1.setAutoSizeTextTypeUniformWithPresetSizes(r0, r3)
            goto L_0x0290
        L_0x027c:
            r19 = r1
            r21 = r2
            r22 = r3
            goto L_0x0290
        L_0x0283:
            r19 = r1
            r21 = r2
            r22 = r3
            goto L_0x0290
        L_0x028a:
            r19 = r1
            r21 = r2
            r22 = r3
        L_0x0290:
            int[] r0 = androidx.appcompat.R.styleable.AppCompatTextView
            androidx.appcompat.widget.TintTypedArray r11 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes((android.content.Context) r10, (android.util.AttributeSet) r8, (int[]) r0)
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r12 = 0
            r15 = 0
            r18 = r0
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableLeftCompat
            r23 = r1
            r1 = -1
            int r0 = r11.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x02b0
            r1 = r17
            android.graphics.drawable.Drawable r17 = r1.getDrawable(r10, r0)
            goto L_0x02b4
        L_0x02b0:
            r1 = r17
            r17 = r18
        L_0x02b4:
            r18 = r0
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTopCompat
            r24 = r2
            r2 = -1
            int r0 = r11.getResourceId(r0, r2)
            if (r0 == r2) goto L_0x02c7
            android.graphics.drawable.Drawable r20 = r1.getDrawable(r10, r0)
            r23 = r20
        L_0x02c7:
            r25 = r0
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableRightCompat
            int r0 = r11.getResourceId(r0, r2)
            if (r0 == r2) goto L_0x02d7
            android.graphics.drawable.Drawable r20 = r1.getDrawable(r10, r0)
            r24 = r20
        L_0x02d7:
            r26 = r0
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableBottomCompat
            int r0 = r11.getResourceId(r0, r2)
            if (r0 == r2) goto L_0x02e8
            android.graphics.drawable.Drawable r3 = r1.getDrawable(r10, r0)
            r27 = r3
            goto L_0x02ea
        L_0x02e8:
            r27 = r3
        L_0x02ea:
            int r3 = androidx.appcompat.R.styleable.AppCompatTextView_drawableStartCompat
            int r3 = r11.getResourceId(r3, r2)
            if (r3 == r2) goto L_0x02f6
            android.graphics.drawable.Drawable r12 = r1.getDrawable(r10, r3)
        L_0x02f6:
            r28 = r0
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableEndCompat
            int r0 = r11.getResourceId(r0, r2)
            if (r0 == r2) goto L_0x0304
            android.graphics.drawable.Drawable r15 = r1.getDrawable(r10, r0)
        L_0x0304:
            r29 = r0
            r0 = r36
            r30 = r19
            r19 = r1
            r1 = r17
            r2 = r23
            r31 = r3
            r3 = r24
            r32 = r4
            r4 = r27
            r33 = r5
            r5 = r12
            r34 = r6
            r6 = r15
            r0.setCompoundDrawables(r1, r2, r3, r4, r5, r6)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTint
            boolean r1 = r11.hasValue(r0)
            if (r1 == 0) goto L_0x0332
            android.content.res.ColorStateList r0 = r11.getColorStateList(r0)
            android.widget.TextView r1 = r7.mView
            androidx.core.widget.TextViewCompat.setCompoundDrawableTintList(r1, r0)
        L_0x0332:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTintMode
            boolean r1 = r11.hasValue(r0)
            if (r1 == 0) goto L_0x034a
            r1 = -1
            int r0 = r11.getInt(r0, r1)
            r1 = 0
            android.graphics.PorterDuff$Mode r0 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r0, r1)
            android.widget.TextView r1 = r7.mView
            androidx.core.widget.TextViewCompat.setCompoundDrawableTintMode(r1, r0)
        L_0x034a:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_firstBaselineToTopHeight
            r1 = -1
            int r0 = r11.getDimensionPixelSize(r0, r1)
            int r2 = androidx.appcompat.R.styleable.AppCompatTextView_lastBaselineToBottomHeight
            int r2 = r11.getDimensionPixelSize(r2, r1)
            int r3 = androidx.appcompat.R.styleable.AppCompatTextView_lineHeight
            int r3 = r11.getDimensionPixelSize(r3, r1)
            r11.recycle()
            if (r0 == r1) goto L_0x0367
            android.widget.TextView r4 = r7.mView
            androidx.core.widget.TextViewCompat.setFirstBaselineToTopHeight(r4, r0)
        L_0x0367:
            if (r2 == r1) goto L_0x036e
            android.widget.TextView r4 = r7.mView
            androidx.core.widget.TextViewCompat.setLastBaselineToBottomHeight(r4, r2)
        L_0x036e:
            if (r3 == r1) goto L_0x0375
            android.widget.TextView r1 = r7.mView
            androidx.core.widget.TextViewCompat.setLineHeight(r1, r3)
        L_0x0375:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray a) {
        String fontFamilyName;
        this.mStyle = a.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        int i = Build.VERSION.SDK_INT;
        boolean z = false;
        if (i >= 28) {
            int i2 = a.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            this.mFontWeight = i2;
            if (i2 != -1) {
                this.mStyle = (this.mStyle & 2) | 0;
            }
        }
        int fontFamilyId = R.styleable.TextAppearance_android_fontFamily;
        if (a.hasValue(fontFamilyId) || a.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int i3 = R.styleable.TextAppearance_fontFamily;
            if (a.hasValue(i3)) {
                fontFamilyId = i3;
            }
            final int fontWeight = this.mFontWeight;
            final int style = this.mStyle;
            if (!context.isRestricted()) {
                final WeakReference<TextView> textViewWeak = new WeakReference<>(this.mView);
                try {
                    Typeface typeface = a.getFont(fontFamilyId, this.mStyle, new ResourcesCompat.FontCallback() {
                        public void onFontRetrieved(@NonNull Typeface typeface) {
                            int i;
                            if (Build.VERSION.SDK_INT >= 28 && (i = fontWeight) != -1) {
                                typeface = Typeface.create(typeface, i, (style & 2) != 0);
                            }
                            AppCompatTextHelper.this.onAsyncTypefaceReceived(textViewWeak, typeface);
                        }

                        public void onFontRetrievalFailed(int reason) {
                        }
                    });
                    if (typeface != null) {
                        if (i < 28 || this.mFontWeight == -1) {
                            this.mFontTypeface = typeface;
                        } else {
                            this.mFontTypeface = Typeface.create(Typeface.create(typeface, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                        }
                    }
                    this.mAsyncFontPending = this.mFontTypeface == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException e) {
                }
            }
            if (this.mFontTypeface == null && (fontFamilyName = a.getString(fontFamilyId)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                    this.mFontTypeface = Typeface.create(fontFamilyName, this.mStyle);
                    return;
                }
                Typeface create = Typeface.create(fontFamilyName, 0);
                int i4 = this.mFontWeight;
                if ((this.mStyle & 2) != 0) {
                    z = true;
                }
                this.mFontTypeface = Typeface.create(create, i4, z);
                return;
            }
            return;
        }
        int i5 = R.styleable.TextAppearance_android_typeface;
        if (a.hasValue(i5)) {
            this.mAsyncFontPending = false;
            switch (a.getInt(i5, 1)) {
                case 1:
                    this.mFontTypeface = Typeface.SANS_SERIF;
                    return;
                case 2:
                    this.mFontTypeface = Typeface.SERIF;
                    return;
                case 3:
                    this.mFontTypeface = Typeface.MONOSPACE;
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onAsyncTypefaceReceived(WeakReference<TextView> textViewWeak, final Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            final TextView textView = (TextView) textViewWeak.get();
            if (textView == null) {
                return;
            }
            if (ViewCompat.isAttachedToWindow(textView)) {
                final int style = this.mStyle;
                textView.post(new Runnable() {
                    public void run() {
                        textView.setTypeface(typeface, style);
                    }
                });
                return;
            }
            textView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: package-private */
    public void onSetTextAppearance(Context context, int resId) {
        String fontVariation;
        ColorStateList textColorHint;
        ColorStateList textColorLink;
        ColorStateList textColor;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, resId, R.styleable.TextAppearance);
        int i = R.styleable.TextAppearance_textAllCaps;
        if (a.hasValue(i)) {
            setAllCaps(a.getBoolean(i, false));
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 23) {
            int i3 = R.styleable.TextAppearance_android_textColor;
            if (a.hasValue(i3) && (textColor = a.getColorStateList(i3)) != null) {
                this.mView.setTextColor(textColor);
            }
            int i4 = R.styleable.TextAppearance_android_textColorLink;
            if (a.hasValue(i4) && (textColorLink = a.getColorStateList(i4)) != null) {
                this.mView.setLinkTextColor(textColorLink);
            }
            int i5 = R.styleable.TextAppearance_android_textColorHint;
            if (a.hasValue(i5) && (textColorHint = a.getColorStateList(i5)) != null) {
                this.mView.setHintTextColor(textColorHint);
            }
        }
        int i6 = R.styleable.TextAppearance_android_textSize;
        if (a.hasValue(i6) && a.getDimensionPixelSize(i6, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, a);
        if (i2 >= 26) {
            int i7 = R.styleable.TextAppearance_fontVariationSettings;
            if (a.hasValue(i7) && (fontVariation = a.getString(i7)) != null) {
                this.mView.setFontVariationSettings(fontVariation);
            }
        }
        a.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAllCaps(boolean allCaps) {
        this.mView.setAllCaps(allCaps);
    }

    /* access modifiers changed from: package-private */
    public void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    /* access modifiers changed from: package-private */
    public void applyCompoundDrawablesTints() {
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (Build.VERSION.SDK_INT < 17) {
            return;
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawables2 = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawables2[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawables2[2], this.mDrawableEndTint);
        }
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo info) {
        if (drawable != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable, info, this.mView.getDrawableState());
        }
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager drawableManager, int drawableId) {
        ColorStateList tintList = drawableManager.getTintList(context, drawableId);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setTextSize(int unit, float size) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size);
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int unit, float size) {
        this.mAutoSizeTextHelper.setTextSizeInternal(unit, size);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] presetSizes, int unit) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    /* access modifiers changed from: package-private */
    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintList(@Nullable ColorStateList tintList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintList = tintList;
        tintInfo.mHasTintList = tintList != null;
        setCompoundTints();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintMode = tintMode;
        tintInfo.mHasTintMode = tintMode != null;
        setCompoundTints();
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setCompoundDrawables(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom, Drawable drawableStart, Drawable drawableEnd) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 17 && (drawableStart != null || drawableEnd != null)) {
            Drawable[] existingRel = this.mView.getCompoundDrawablesRelative();
            this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart != null ? drawableStart : existingRel[0], drawableTop != null ? drawableTop : existingRel[1], drawableEnd != null ? drawableEnd : existingRel[2], drawableBottom != null ? drawableBottom : existingRel[3]);
        } else if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null) {
            if (i >= 17) {
                Drawable[] existingRel2 = this.mView.getCompoundDrawablesRelative();
                if (!(existingRel2[0] == null && existingRel2[2] == null)) {
                    this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(existingRel2[0], drawableTop != null ? drawableTop : existingRel2[1], existingRel2[2], drawableBottom != null ? drawableBottom : existingRel2[3]);
                    return;
                }
            }
            Drawable[] existingAbs = this.mView.getCompoundDrawables();
            this.mView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft != null ? drawableLeft : existingAbs[0], drawableTop != null ? drawableTop : existingAbs[1], drawableRight != null ? drawableRight : existingAbs[2], drawableBottom != null ? drawableBottom : existingAbs[3]);
        }
    }

    /* access modifiers changed from: package-private */
    public void populateSurroundingTextIfNeeded(@NonNull TextView textView, @Nullable InputConnection inputConnection, @NonNull EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT < 30 && inputConnection != null) {
            EditorInfoCompat.setInitialSurroundingText(editorInfo, textView.getText());
        }
    }
}
