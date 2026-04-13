package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TextInputLayout extends LinearLayout {
    public static final int BOX_BACKGROUND_FILLED = 1;
    public static final int BOX_BACKGROUND_NONE = 0;
    public static final int BOX_BACKGROUND_OUTLINE = 2;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_TextInputLayout;
    public static final int END_ICON_CLEAR_TEXT = 2;
    public static final int END_ICON_CUSTOM = -1;
    public static final int END_ICON_DROPDOWN_MENU = 3;
    public static final int END_ICON_NONE = 0;
    public static final int END_ICON_PASSWORD_TOGGLE = 1;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
    private static final String LOG_TAG = "TextInputLayout";
    private static final int NO_WIDTH = -1;
    private ValueAnimator animator;
    @Nullable
    private MaterialShapeDrawable boxBackground;
    @ColorInt
    private int boxBackgroundColor;
    private int boxBackgroundMode;
    private int boxCollapsedPaddingTopPx;
    private int boxLabelCutoutHeight;
    private final int boxLabelCutoutPaddingPx;
    @ColorInt
    private int boxStrokeColor;
    private int boxStrokeWidthDefaultPx;
    private int boxStrokeWidthFocusedPx;
    private int boxStrokeWidthPx;
    @Nullable
    private MaterialShapeDrawable boxUnderline;
    final CollapsingTextHelper collapsingTextHelper;
    boolean counterEnabled;
    private int counterMaxLength;
    private int counterOverflowTextAppearance;
    @Nullable
    private ColorStateList counterOverflowTextColor;
    private boolean counterOverflowed;
    private int counterTextAppearance;
    @Nullable
    private ColorStateList counterTextColor;
    @Nullable
    private TextView counterView;
    @ColorInt
    private int defaultFilledBackgroundColor;
    private ColorStateList defaultHintTextColor;
    @ColorInt
    private int defaultStrokeColor;
    @ColorInt
    private int disabledColor;
    @ColorInt
    private int disabledFilledBackgroundColor;
    EditText editText;
    private final LinkedHashSet<OnEditTextAttachedListener> editTextAttachedListeners;
    @Nullable
    private Drawable endDummyDrawable;
    private int endDummyDrawableWidth;
    private final LinkedHashSet<OnEndIconChangedListener> endIconChangedListeners;
    private final SparseArray<EndIconDelegate> endIconDelegates;
    @NonNull
    private final FrameLayout endIconFrame;
    private int endIconMode;
    private View.OnLongClickListener endIconOnLongClickListener;
    private ColorStateList endIconTintList;
    private PorterDuff.Mode endIconTintMode;
    /* access modifiers changed from: private */
    @NonNull
    public final CheckableImageButton endIconView;
    @NonNull
    private final LinearLayout endLayout;
    private View.OnLongClickListener errorIconOnLongClickListener;
    private ColorStateList errorIconTintList;
    @NonNull
    private final CheckableImageButton errorIconView;
    private boolean expandedHintEnabled;
    @ColorInt
    private int focusedFilledBackgroundColor;
    @ColorInt
    private int focusedStrokeColor;
    private ColorStateList focusedTextColor;
    private boolean hasEndIconTintList;
    private boolean hasEndIconTintMode;
    private boolean hasStartIconTintList;
    private boolean hasStartIconTintMode;
    private CharSequence hint;
    private boolean hintAnimationEnabled;
    private boolean hintEnabled;
    private boolean hintExpanded;
    @ColorInt
    private int hoveredFilledBackgroundColor;
    @ColorInt
    private int hoveredStrokeColor;
    private boolean inDrawableStateChanged;
    private final IndicatorViewController indicatorViewController;
    @NonNull
    private final FrameLayout inputFrame;
    private boolean isProvidingHint;
    private int maxWidth;
    private int minWidth;
    private Drawable originalEditTextEndDrawable;
    private CharSequence originalHint;
    /* access modifiers changed from: private */
    public boolean placeholderEnabled;
    private CharSequence placeholderText;
    private int placeholderTextAppearance;
    @Nullable
    private ColorStateList placeholderTextColor;
    private TextView placeholderTextView;
    @Nullable
    private CharSequence prefixText;
    @NonNull
    private final TextView prefixTextView;
    /* access modifiers changed from: private */
    public boolean restoringSavedState;
    @NonNull
    private ShapeAppearanceModel shapeAppearanceModel;
    @Nullable
    private Drawable startDummyDrawable;
    private int startDummyDrawableWidth;
    private View.OnLongClickListener startIconOnLongClickListener;
    private ColorStateList startIconTintList;
    private PorterDuff.Mode startIconTintMode;
    @NonNull
    private final CheckableImageButton startIconView;
    @NonNull
    private final LinearLayout startLayout;
    private ColorStateList strokeErrorColor;
    @Nullable
    private CharSequence suffixText;
    @NonNull
    private final TextView suffixTextView;
    private final Rect tmpBoundsRect;
    private final Rect tmpRect;
    private final RectF tmpRectF;
    private Typeface typeface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BoxBackgroundMode {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EndIconMode {
    }

    public interface OnEditTextAttachedListener {
        void onEditTextAttached(@NonNull TextInputLayout textInputLayout);
    }

    public interface OnEndIconChangedListener {
        void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int i);
    }

    public TextInputLayout(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public TextInputLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.textInputStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextInputLayout(@androidx.annotation.NonNull android.content.Context r32, @androidx.annotation.Nullable android.util.AttributeSet r33, int r34) {
        /*
            r31 = this;
            r0 = r31
            r7 = r33
            r8 = r34
            int r9 = DEF_STYLE_RES
            r1 = r32
            android.content.Context r2 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r2, r7, r8)
            r10 = -1
            r0.minWidth = r10
            r0.maxWidth = r10
            com.google.android.material.textfield.IndicatorViewController r2 = new com.google.android.material.textfield.IndicatorViewController
            r2.<init>(r0)
            r0.indicatorViewController = r2
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r0.tmpRect = r2
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r0.tmpBoundsRect = r2
            android.graphics.RectF r2 = new android.graphics.RectF
            r2.<init>()
            r0.tmpRectF = r2
            java.util.LinkedHashSet r2 = new java.util.LinkedHashSet
            r2.<init>()
            r0.editTextAttachedListeners = r2
            r11 = 0
            r0.endIconMode = r11
            android.util.SparseArray r2 = new android.util.SparseArray
            r2.<init>()
            r0.endIconDelegates = r2
            java.util.LinkedHashSet r2 = new java.util.LinkedHashSet
            r2.<init>()
            r0.endIconChangedListeners = r2
            com.google.android.material.internal.CollapsingTextHelper r2 = new com.google.android.material.internal.CollapsingTextHelper
            r2.<init>(r0)
            r0.collapsingTextHelper = r2
            android.content.Context r12 = r31.getContext()
            r13 = 1
            r0.setOrientation(r13)
            r0.setWillNotDraw(r11)
            r0.setAddStatesFromChildren(r13)
            android.widget.FrameLayout r1 = new android.widget.FrameLayout
            r1.<init>(r12)
            r0.inputFrame = r1
            r1.setAddStatesFromChildren(r13)
            r0.addView(r1)
            android.widget.LinearLayout r3 = new android.widget.LinearLayout
            r3.<init>(r12)
            r0.startLayout = r3
            r3.setOrientation(r11)
            android.widget.FrameLayout$LayoutParams r4 = new android.widget.FrameLayout$LayoutParams
            r14 = -2
            r5 = 8388611(0x800003, float:1.1754948E-38)
            r4.<init>(r14, r10, r5)
            r3.setLayoutParams(r4)
            r1.addView(r3)
            android.widget.LinearLayout r15 = new android.widget.LinearLayout
            r15.<init>(r12)
            r0.endLayout = r15
            r15.setOrientation(r11)
            android.widget.FrameLayout$LayoutParams r3 = new android.widget.FrameLayout$LayoutParams
            r4 = 8388613(0x800005, float:1.175495E-38)
            r3.<init>(r14, r10, r4)
            r15.setLayoutParams(r3)
            r1.addView(r15)
            android.widget.FrameLayout r1 = new android.widget.FrameLayout
            r1.<init>(r12)
            r0.endIconFrame = r1
            android.widget.FrameLayout$LayoutParams r3 = new android.widget.FrameLayout$LayoutParams
            r3.<init>(r14, r10)
            r1.setLayoutParams(r3)
            android.animation.TimeInterpolator r1 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r2.setTextSizeInterpolator(r1)
            r2.setPositionInterpolator(r1)
            r1 = 8388659(0x800033, float:1.1755015E-38)
            r2.setCollapsedTextGravity(r1)
            int[] r3 = com.google.android.material.R.styleable.TextInputLayout
            r1 = 5
            int[] r6 = new int[r1]
            int r5 = com.google.android.material.R.styleable.TextInputLayout_counterTextAppearance
            r6[r11] = r5
            int r4 = com.google.android.material.R.styleable.TextInputLayout_counterOverflowTextAppearance
            r6[r13] = r4
            int r2 = com.google.android.material.R.styleable.TextInputLayout_errorTextAppearance
            r1 = 2
            r6[r1] = r2
            int r14 = com.google.android.material.R.styleable.TextInputLayout_helperTextTextAppearance
            r11 = 3
            r6[r11] = r14
            int r11 = com.google.android.material.R.styleable.TextInputLayout_hintTextAppearance
            r17 = 4
            r6[r17] = r11
            r1 = r12
            r18 = r2
            r2 = r33
            r19 = r4
            r4 = r34
            r20 = r5
            r5 = r9
            androidx.appcompat.widget.TintTypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r2 = com.google.android.material.R.styleable.TextInputLayout_hintEnabled
            boolean r2 = r1.getBoolean(r2, r13)
            r0.hintEnabled = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_android_hint
            java.lang.CharSequence r2 = r1.getText(r2)
            r0.setHint((java.lang.CharSequence) r2)
            int r2 = com.google.android.material.R.styleable.TextInputLayout_hintAnimationEnabled
            boolean r2 = r1.getBoolean(r2, r13)
            r0.hintAnimationEnabled = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_expandedHintEnabled
            boolean r2 = r1.getBoolean(r2, r13)
            r0.expandedHintEnabled = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_android_minWidth
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x0117
            int r2 = r1.getDimensionPixelSize(r2, r10)
            r0.setMinWidth(r2)
        L_0x0117:
            int r2 = com.google.android.material.R.styleable.TextInputLayout_android_maxWidth
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x0126
            int r2 = r1.getDimensionPixelSize(r2, r10)
            r0.setMaxWidth(r2)
        L_0x0126:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r2 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r12, (android.util.AttributeSet) r7, (int) r8, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.build()
            r0.shapeAppearanceModel = r2
            android.content.res.Resources r2 = r12.getResources()
            int r3 = com.google.android.material.R.dimen.mtrl_textinput_box_label_cutout_padding
            int r2 = r2.getDimensionPixelOffset(r3)
            r0.boxLabelCutoutPaddingPx = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_boxCollapsedPaddingTop
            r3 = 0
            int r2 = r1.getDimensionPixelOffset(r2, r3)
            r0.boxCollapsedPaddingTopPx = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_boxStrokeWidth
            android.content.res.Resources r3 = r12.getResources()
            int r4 = com.google.android.material.R.dimen.mtrl_textinput_box_stroke_width_default
            int r3 = r3.getDimensionPixelSize(r4)
            int r2 = r1.getDimensionPixelSize(r2, r3)
            r0.boxStrokeWidthDefaultPx = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_boxStrokeWidthFocused
            android.content.res.Resources r3 = r12.getResources()
            int r4 = com.google.android.material.R.dimen.mtrl_textinput_box_stroke_width_focused
            int r3 = r3.getDimensionPixelSize(r4)
            int r2 = r1.getDimensionPixelSize(r2, r3)
            r0.boxStrokeWidthFocusedPx = r2
            int r2 = r0.boxStrokeWidthDefaultPx
            r0.boxStrokeWidthPx = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_boxCornerRadiusTopStart
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r2 = r1.getDimension(r2, r3)
            int r4 = com.google.android.material.R.styleable.TextInputLayout_boxCornerRadiusTopEnd
            float r4 = r1.getDimension(r4, r3)
            int r5 = com.google.android.material.R.styleable.TextInputLayout_boxCornerRadiusBottomEnd
            float r5 = r1.getDimension(r5, r3)
            int r6 = com.google.android.material.R.styleable.TextInputLayout_boxCornerRadiusBottomStart
            float r3 = r1.getDimension(r6, r3)
            com.google.android.material.shape.ShapeAppearanceModel r6 = r0.shapeAppearanceModel
            com.google.android.material.shape.ShapeAppearanceModel$Builder r6 = r6.toBuilder()
            r9 = 0
            int r17 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r17 < 0) goto L_0x0197
            r6.setTopLeftCornerSize((float) r2)
        L_0x0197:
            int r17 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r17 < 0) goto L_0x019e
            r6.setTopRightCornerSize((float) r4)
        L_0x019e:
            int r17 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r17 < 0) goto L_0x01a5
            r6.setBottomRightCornerSize((float) r5)
        L_0x01a5:
            int r9 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r9 < 0) goto L_0x01ac
            r6.setBottomLeftCornerSize((float) r3)
        L_0x01ac:
            com.google.android.material.shape.ShapeAppearanceModel r9 = r6.build()
            r0.shapeAppearanceModel = r9
            int r9 = com.google.android.material.R.styleable.TextInputLayout_boxBackgroundColor
            android.content.res.ColorStateList r9 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r9)
            if (r9 == 0) goto L_0x0223
            int r10 = r9.getDefaultColor()
            r0.defaultFilledBackgroundColor = r10
            r0.boxBackgroundColor = r10
            boolean r10 = r9.isStateful()
            r21 = -16842910(0xfffffffffefeff62, float:-1.6947497E38)
            if (r10 == 0) goto L_0x01f7
            int[] r10 = new int[r13]
            r16 = 0
            r10[r16] = r21
            r13 = -1
            int r10 = r9.getColorForState(r10, r13)
            r0.disabledFilledBackgroundColor = r10
            r10 = 2
            int[] r13 = new int[r10]
            r13 = {16842908, 16842910} // fill-array
            r10 = -1
            int r13 = r9.getColorForState(r13, r10)
            r0.focusedFilledBackgroundColor = r13
            r13 = 2
            int[] r10 = new int[r13]
            r10 = {16843623, 16842910} // fill-array
            r13 = -1
            int r10 = r9.getColorForState(r10, r13)
            r0.hoveredFilledBackgroundColor = r10
            r22 = r2
            r21 = r3
            goto L_0x0232
        L_0x01f7:
            int r10 = r0.defaultFilledBackgroundColor
            r0.focusedFilledBackgroundColor = r10
            int r10 = com.google.android.material.R.color.mtrl_filled_background_color
            android.content.res.ColorStateList r10 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r12, r10)
            r22 = r2
            r13 = 1
            int[] r2 = new int[r13]
            r13 = 0
            r2[r13] = r21
            r13 = -1
            int r2 = r10.getColorForState(r2, r13)
            r0.disabledFilledBackgroundColor = r2
            r2 = 1
            int[] r13 = new int[r2]
            r2 = 16843623(0x1010367, float:2.3696E-38)
            r21 = r3
            r3 = 0
            r13[r3] = r2
            r2 = -1
            int r13 = r10.getColorForState(r13, r2)
            r0.hoveredFilledBackgroundColor = r13
            goto L_0x0232
        L_0x0223:
            r22 = r2
            r21 = r3
            r3 = 0
            r0.boxBackgroundColor = r3
            r0.defaultFilledBackgroundColor = r3
            r0.disabledFilledBackgroundColor = r3
            r0.focusedFilledBackgroundColor = r3
            r0.hoveredFilledBackgroundColor = r3
        L_0x0232:
            int r2 = com.google.android.material.R.styleable.TextInputLayout_android_textColorHint
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x0243
            android.content.res.ColorStateList r2 = r1.getColorStateList(r2)
            r0.focusedTextColor = r2
            r0.defaultHintTextColor = r2
        L_0x0243:
            int r2 = com.google.android.material.R.styleable.TextInputLayout_boxStrokeColor
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            r10 = 0
            int r2 = r1.getColor(r2, r10)
            r0.focusedStrokeColor = r2
            int r2 = com.google.android.material.R.color.mtrl_textinput_default_box_stroke_color
            int r2 = androidx.core.content.ContextCompat.getColor(r12, r2)
            r0.defaultStrokeColor = r2
            int r2 = com.google.android.material.R.color.mtrl_textinput_disabled_color
            int r2 = androidx.core.content.ContextCompat.getColor(r12, r2)
            r0.disabledColor = r2
            int r2 = com.google.android.material.R.color.mtrl_textinput_hovered_box_stroke_color
            int r2 = androidx.core.content.ContextCompat.getColor(r12, r2)
            r0.hoveredStrokeColor = r2
            if (r3 == 0) goto L_0x026d
            r0.setBoxStrokeColorStateList(r3)
        L_0x026d:
            int r2 = com.google.android.material.R.styleable.TextInputLayout_boxStrokeErrorColor
            boolean r10 = r1.hasValue(r2)
            if (r10 == 0) goto L_0x027d
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            r0.setBoxStrokeErrorColor(r2)
        L_0x027d:
            r2 = -1
            int r10 = r1.getResourceId(r11, r2)
            if (r10 == r2) goto L_0x028d
            r2 = 0
            int r11 = r1.getResourceId(r11, r2)
            r0.setHintTextAppearance(r11)
            goto L_0x028e
        L_0x028d:
            r2 = 0
        L_0x028e:
            r11 = r18
            int r11 = r1.getResourceId(r11, r2)
            int r13 = com.google.android.material.R.styleable.TextInputLayout_errorContentDescription
            java.lang.CharSequence r13 = r1.getText(r13)
            r18 = r3
            int r3 = com.google.android.material.R.styleable.TextInputLayout_errorEnabled
            boolean r3 = r1.getBoolean(r3, r2)
            android.content.Context r16 = r31.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r16)
            r23 = r4
            int r4 = com.google.android.material.R.layout.design_text_input_end_icon
            r24 = r5
            r5 = 0
            android.view.View r2 = r2.inflate(r4, r15, r5)
            com.google.android.material.internal.CheckableImageButton r2 = (com.google.android.material.internal.CheckableImageButton) r2
            r0.errorIconView = r2
            int r5 = com.google.android.material.R.id.text_input_error_icon
            r2.setId(r5)
            r5 = 8
            r2.setVisibility(r5)
            boolean r15 = com.google.android.material.resources.MaterialResources.isFontScaleAtLeast1_3(r12)
            if (r15 == 0) goto L_0x02d6
            android.view.ViewGroup$LayoutParams r15 = r2.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r15 = (android.view.ViewGroup.MarginLayoutParams) r15
            r5 = 0
            androidx.core.view.MarginLayoutParamsCompat.setMarginStart(r15, r5)
        L_0x02d6:
            int r5 = com.google.android.material.R.styleable.TextInputLayout_errorIconDrawable
            boolean r15 = r1.hasValue(r5)
            if (r15 == 0) goto L_0x02e5
            android.graphics.drawable.Drawable r5 = r1.getDrawable(r5)
            r0.setErrorIconDrawable((android.graphics.drawable.Drawable) r5)
        L_0x02e5:
            int r5 = com.google.android.material.R.styleable.TextInputLayout_errorIconTint
            boolean r15 = r1.hasValue(r5)
            if (r15 == 0) goto L_0x02f5
            android.content.res.ColorStateList r5 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r5)
            r0.setErrorIconTintList(r5)
        L_0x02f5:
            int r5 = com.google.android.material.R.styleable.TextInputLayout_errorIconTintMode
            boolean r15 = r1.hasValue(r5)
            r25 = r6
            r6 = 0
            if (r15 == 0) goto L_0x030d
            r15 = -1
            int r5 = r1.getInt(r5, r15)
            android.graphics.PorterDuff$Mode r5 = com.google.android.material.internal.ViewUtils.parseTintMode(r5, r6)
            r0.setErrorIconTintMode(r5)
        L_0x030d:
            android.content.res.Resources r5 = r31.getResources()
            int r15 = com.google.android.material.R.string.error_icon_content_description
            java.lang.CharSequence r5 = r5.getText(r15)
            r2.setContentDescription(r5)
            r5 = 2
            androidx.core.view.ViewCompat.setImportantForAccessibility(r2, r5)
            r5 = 0
            r2.setClickable(r5)
            r2.setPressable(r5)
            r2.setFocusable(r5)
            int r2 = r1.getResourceId(r14, r5)
            int r14 = com.google.android.material.R.styleable.TextInputLayout_helperTextEnabled
            boolean r14 = r1.getBoolean(r14, r5)
            int r15 = com.google.android.material.R.styleable.TextInputLayout_helperText
            java.lang.CharSequence r15 = r1.getText(r15)
            int r6 = com.google.android.material.R.styleable.TextInputLayout_placeholderTextAppearance
            int r6 = r1.getResourceId(r6, r5)
            int r5 = com.google.android.material.R.styleable.TextInputLayout_placeholderText
            java.lang.CharSequence r5 = r1.getText(r5)
            int r7 = com.google.android.material.R.styleable.TextInputLayout_prefixTextAppearance
            r8 = 0
            int r7 = r1.getResourceId(r7, r8)
            int r8 = com.google.android.material.R.styleable.TextInputLayout_prefixText
            java.lang.CharSequence r8 = r1.getText(r8)
            r26 = r9
            int r9 = com.google.android.material.R.styleable.TextInputLayout_suffixTextAppearance
            r27 = r10
            r10 = 0
            int r9 = r1.getResourceId(r9, r10)
            int r10 = com.google.android.material.R.styleable.TextInputLayout_suffixText
            java.lang.CharSequence r10 = r1.getText(r10)
            r28 = r9
            int r9 = com.google.android.material.R.styleable.TextInputLayout_counterEnabled
            r29 = r10
            r10 = 0
            boolean r9 = r1.getBoolean(r9, r10)
            int r10 = com.google.android.material.R.styleable.TextInputLayout_counterMaxLength
            r30 = r9
            r9 = -1
            int r10 = r1.getInt(r10, r9)
            r0.setCounterMaxLength(r10)
            r9 = r20
            r10 = 0
            int r9 = r1.getResourceId(r9, r10)
            r0.counterTextAppearance = r9
            r9 = r19
            int r9 = r1.getResourceId(r9, r10)
            r0.counterOverflowTextAppearance = r9
            android.content.Context r9 = r31.getContext()
            android.view.LayoutInflater r9 = android.view.LayoutInflater.from(r9)
            int r10 = com.google.android.material.R.layout.design_text_input_start_icon
            r19 = r7
            android.widget.LinearLayout r7 = r0.startLayout
            r20 = r8
            r8 = 0
            android.view.View r7 = r9.inflate(r10, r7, r8)
            com.google.android.material.internal.CheckableImageButton r7 = (com.google.android.material.internal.CheckableImageButton) r7
            r0.startIconView = r7
            r8 = 8
            r7.setVisibility(r8)
            boolean r8 = com.google.android.material.resources.MaterialResources.isFontScaleAtLeast1_3(r12)
            if (r8 == 0) goto L_0x03bd
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r7 = (android.view.ViewGroup.MarginLayoutParams) r7
            r8 = 0
            androidx.core.view.MarginLayoutParamsCompat.setMarginEnd(r7, r8)
        L_0x03bd:
            r7 = 0
            r0.setStartIconOnClickListener(r7)
            r0.setStartIconOnLongClickListener(r7)
            int r7 = com.google.android.material.R.styleable.TextInputLayout_startIconDrawable
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x03ed
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r7)
            r0.setStartIconDrawable((android.graphics.drawable.Drawable) r7)
            int r7 = com.google.android.material.R.styleable.TextInputLayout_startIconContentDescription
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x03e3
            java.lang.CharSequence r7 = r1.getText(r7)
            r0.setStartIconContentDescription((java.lang.CharSequence) r7)
        L_0x03e3:
            int r7 = com.google.android.material.R.styleable.TextInputLayout_startIconCheckable
            r8 = 1
            boolean r7 = r1.getBoolean(r7, r8)
            r0.setStartIconCheckable(r7)
        L_0x03ed:
            int r7 = com.google.android.material.R.styleable.TextInputLayout_startIconTint
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x03fd
            android.content.res.ColorStateList r7 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r7)
            r0.setStartIconTintList(r7)
        L_0x03fd:
            int r7 = com.google.android.material.R.styleable.TextInputLayout_startIconTintMode
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x0413
            r8 = -1
            int r7 = r1.getInt(r7, r8)
            r8 = 0
            android.graphics.PorterDuff$Mode r7 = com.google.android.material.internal.ViewUtils.parseTintMode(r7, r8)
            r0.setStartIconTintMode(r7)
        L_0x0413:
            int r7 = com.google.android.material.R.styleable.TextInputLayout_boxBackgroundMode
            r8 = 0
            int r7 = r1.getInt(r7, r8)
            r0.setBoxBackgroundMode(r7)
            android.content.Context r7 = r31.getContext()
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r7)
            android.widget.FrameLayout r9 = r0.endIconFrame
            android.view.View r4 = r7.inflate(r4, r9, r8)
            com.google.android.material.internal.CheckableImageButton r4 = (com.google.android.material.internal.CheckableImageButton) r4
            r0.endIconView = r4
            android.widget.FrameLayout r4 = r0.endIconFrame
            com.google.android.material.internal.CheckableImageButton r7 = r0.endIconView
            r4.addView(r7)
            com.google.android.material.internal.CheckableImageButton r4 = r0.endIconView
            r7 = 8
            r4.setVisibility(r7)
            boolean r4 = com.google.android.material.resources.MaterialResources.isFontScaleAtLeast1_3(r12)
            if (r4 == 0) goto L_0x0450
            com.google.android.material.internal.CheckableImageButton r4 = r0.endIconView
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r4 = (android.view.ViewGroup.MarginLayoutParams) r4
            r7 = 0
            androidx.core.view.MarginLayoutParamsCompat.setMarginStart(r4, r7)
        L_0x0450:
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r4 = r0.endIconDelegates
            com.google.android.material.textfield.CustomEndIconDelegate r7 = new com.google.android.material.textfield.CustomEndIconDelegate
            r7.<init>(r0)
            r8 = -1
            r4.append(r8, r7)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r4 = r0.endIconDelegates
            com.google.android.material.textfield.NoEndIconDelegate r7 = new com.google.android.material.textfield.NoEndIconDelegate
            r7.<init>(r0)
            r8 = 0
            r4.append(r8, r7)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r4 = r0.endIconDelegates
            com.google.android.material.textfield.PasswordToggleEndIconDelegate r7 = new com.google.android.material.textfield.PasswordToggleEndIconDelegate
            r7.<init>(r0)
            r8 = 1
            r4.append(r8, r7)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r4 = r0.endIconDelegates
            com.google.android.material.textfield.ClearTextEndIconDelegate r7 = new com.google.android.material.textfield.ClearTextEndIconDelegate
            r7.<init>(r0)
            r8 = 2
            r4.append(r8, r7)
            android.util.SparseArray<com.google.android.material.textfield.EndIconDelegate> r4 = r0.endIconDelegates
            com.google.android.material.textfield.DropdownMenuEndIconDelegate r7 = new com.google.android.material.textfield.DropdownMenuEndIconDelegate
            r7.<init>(r0)
            r8 = 3
            r4.append(r8, r7)
            int r4 = com.google.android.material.R.styleable.TextInputLayout_endIconMode
            boolean r7 = r1.hasValue(r4)
            if (r7 == 0) goto L_0x04c1
            r7 = 0
            int r4 = r1.getInt(r4, r7)
            r0.setEndIconMode(r4)
            int r4 = com.google.android.material.R.styleable.TextInputLayout_endIconDrawable
            boolean r7 = r1.hasValue(r4)
            if (r7 == 0) goto L_0x04a6
            android.graphics.drawable.Drawable r4 = r1.getDrawable(r4)
            r0.setEndIconDrawable((android.graphics.drawable.Drawable) r4)
        L_0x04a6:
            int r4 = com.google.android.material.R.styleable.TextInputLayout_endIconContentDescription
            boolean r7 = r1.hasValue(r4)
            if (r7 == 0) goto L_0x04b6
            java.lang.CharSequence r4 = r1.getText(r4)
            r0.setEndIconContentDescription((java.lang.CharSequence) r4)
        L_0x04b6:
            int r4 = com.google.android.material.R.styleable.TextInputLayout_endIconCheckable
            r7 = 1
            boolean r4 = r1.getBoolean(r4, r7)
            r0.setEndIconCheckable(r4)
            goto L_0x050a
        L_0x04c1:
            int r4 = com.google.android.material.R.styleable.TextInputLayout_passwordToggleEnabled
            boolean r7 = r1.hasValue(r4)
            if (r7 == 0) goto L_0x050a
            r7 = 0
            boolean r4 = r1.getBoolean(r4, r7)
            r0.setEndIconMode(r4)
            int r7 = com.google.android.material.R.styleable.TextInputLayout_passwordToggleDrawable
            android.graphics.drawable.Drawable r7 = r1.getDrawable(r7)
            r0.setEndIconDrawable((android.graphics.drawable.Drawable) r7)
            int r7 = com.google.android.material.R.styleable.TextInputLayout_passwordToggleContentDescription
            java.lang.CharSequence r7 = r1.getText(r7)
            r0.setEndIconContentDescription((java.lang.CharSequence) r7)
            int r7 = com.google.android.material.R.styleable.TextInputLayout_passwordToggleTint
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x04f4
            android.content.res.ColorStateList r7 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r7)
            r0.setEndIconTintList(r7)
        L_0x04f4:
            int r7 = com.google.android.material.R.styleable.TextInputLayout_passwordToggleTintMode
            boolean r8 = r1.hasValue(r7)
            if (r8 == 0) goto L_0x050a
            r8 = -1
            int r7 = r1.getInt(r7, r8)
            r8 = 0
            android.graphics.PorterDuff$Mode r7 = com.google.android.material.internal.ViewUtils.parseTintMode(r7, r8)
            r0.setEndIconTintMode(r7)
        L_0x050a:
            int r4 = com.google.android.material.R.styleable.TextInputLayout_passwordToggleEnabled
            boolean r4 = r1.hasValue(r4)
            if (r4 != 0) goto L_0x0538
            int r4 = com.google.android.material.R.styleable.TextInputLayout_endIconTint
            boolean r7 = r1.hasValue(r4)
            if (r7 == 0) goto L_0x0522
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r12, (androidx.appcompat.widget.TintTypedArray) r1, (int) r4)
            r0.setEndIconTintList(r4)
        L_0x0522:
            int r4 = com.google.android.material.R.styleable.TextInputLayout_endIconTintMode
            boolean r7 = r1.hasValue(r4)
            if (r7 == 0) goto L_0x0538
            r7 = -1
            int r4 = r1.getInt(r4, r7)
            r7 = 0
            android.graphics.PorterDuff$Mode r4 = com.google.android.material.internal.ViewUtils.parseTintMode(r4, r7)
            r0.setEndIconTintMode(r4)
        L_0x0538:
            androidx.appcompat.widget.AppCompatTextView r4 = new androidx.appcompat.widget.AppCompatTextView
            r4.<init>(r12)
            r0.prefixTextView = r4
            int r7 = com.google.android.material.R.id.textinput_prefix_text
            r4.setId(r7)
            android.widget.FrameLayout$LayoutParams r7 = new android.widget.FrameLayout$LayoutParams
            r8 = -2
            r7.<init>(r8, r8)
            r4.setLayoutParams(r7)
            r7 = 1
            androidx.core.view.ViewCompat.setAccessibilityLiveRegion(r4, r7)
            android.widget.LinearLayout r7 = r0.startLayout
            com.google.android.material.internal.CheckableImageButton r8 = r0.startIconView
            r7.addView(r8)
            android.widget.LinearLayout r7 = r0.startLayout
            r7.addView(r4)
            androidx.appcompat.widget.AppCompatTextView r4 = new androidx.appcompat.widget.AppCompatTextView
            r4.<init>(r12)
            r0.suffixTextView = r4
            int r7 = com.google.android.material.R.id.textinput_suffix_text
            r4.setId(r7)
            android.widget.FrameLayout$LayoutParams r7 = new android.widget.FrameLayout$LayoutParams
            r8 = 80
            r9 = -2
            r7.<init>(r9, r9, r8)
            r4.setLayoutParams(r7)
            r7 = 1
            androidx.core.view.ViewCompat.setAccessibilityLiveRegion(r4, r7)
            android.widget.LinearLayout r7 = r0.endLayout
            r7.addView(r4)
            android.widget.LinearLayout r4 = r0.endLayout
            com.google.android.material.internal.CheckableImageButton r7 = r0.errorIconView
            r4.addView(r7)
            android.widget.LinearLayout r4 = r0.endLayout
            android.widget.FrameLayout r7 = r0.endIconFrame
            r4.addView(r7)
            r0.setHelperTextEnabled(r14)
            r0.setHelperText(r15)
            r0.setHelperTextTextAppearance(r2)
            r0.setErrorEnabled(r3)
            r0.setErrorTextAppearance(r11)
            r0.setErrorContentDescription(r13)
            int r4 = r0.counterTextAppearance
            r0.setCounterTextAppearance(r4)
            int r4 = r0.counterOverflowTextAppearance
            r0.setCounterOverflowTextAppearance(r4)
            r0.setPlaceholderText(r5)
            r0.setPlaceholderTextAppearance(r6)
            r4 = r20
            r0.setPrefixText(r4)
            r7 = r19
            r0.setPrefixTextAppearance(r7)
            r8 = r29
            r0.setSuffixText(r8)
            r9 = r28
            r0.setSuffixTextAppearance(r9)
            int r10 = com.google.android.material.R.styleable.TextInputLayout_errorTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x05d0
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setErrorTextColor(r10)
        L_0x05d0:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_helperTextTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x05df
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setHelperTextColor(r10)
        L_0x05df:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_hintTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x05ee
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setHintTextColor(r10)
        L_0x05ee:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_counterTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x05fd
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setCounterTextColor(r10)
        L_0x05fd:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_counterOverflowTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x060d
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setCounterOverflowTextColor(r10)
        L_0x060d:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_placeholderTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x061d
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setPlaceholderTextColor(r10)
        L_0x061d:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_prefixTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x062c
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setPrefixTextColor(r10)
        L_0x062c:
            int r10 = com.google.android.material.R.styleable.TextInputLayout_suffixTextColor
            boolean r16 = r1.hasValue(r10)
            if (r16 == 0) goto L_0x063b
            android.content.res.ColorStateList r10 = r1.getColorStateList(r10)
            r0.setSuffixTextColor(r10)
        L_0x063b:
            r10 = r30
            r0.setCounterEnabled(r10)
            r32 = r2
            int r2 = com.google.android.material.R.styleable.TextInputLayout_android_enabled
            r16 = r3
            r3 = 1
            boolean r2 = r1.getBoolean(r2, r3)
            r0.setEnabled(r2)
            r1.recycle()
            r2 = 2
            androidx.core.view.ViewCompat.setImportantForAccessibility(r0, r2)
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 26
            if (r2 < r3) goto L_0x065f
            r2 = 1
            androidx.core.view.ViewCompat.setImportantForAutofill(r0, r2)
        L_0x065f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void addView(@NonNull View child, int index, @NonNull ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(params);
            flp.gravity = (flp.gravity & -113) | 16;
            this.inputFrame.addView(child, flp);
            this.inputFrame.setLayoutParams(params);
            updateInputLayoutMargins();
            setEditText((EditText) child);
            return;
        }
        super.addView(child, index, params);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public MaterialShapeDrawable getBoxBackground() {
        int i = this.boxBackgroundMode;
        if (i == 1 || i == 2) {
            return this.boxBackground;
        }
        throw new IllegalStateException();
    }

    public void setBoxBackgroundMode(int boxBackgroundMode2) {
        if (boxBackgroundMode2 != this.boxBackgroundMode) {
            this.boxBackgroundMode = boxBackgroundMode2;
            if (this.editText != null) {
                onApplyBoxBackgroundMode();
            }
        }
    }

    public int getBoxBackgroundMode() {
        return this.boxBackgroundMode;
    }

    private void onApplyBoxBackgroundMode() {
        assignBoxBackgroundByMode();
        setEditTextBoxBackground();
        updateTextInputBoxState();
        updateBoxCollapsedPaddingTop();
        adjustFilledEditTextPaddingForLargeFont();
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
    }

    private void assignBoxBackgroundByMode() {
        switch (this.boxBackgroundMode) {
            case 0:
                this.boxBackground = null;
                this.boxUnderline = null;
                return;
            case 1:
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
                this.boxUnderline = new MaterialShapeDrawable();
                return;
            case 2:
                if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                    this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
                } else {
                    this.boxBackground = new CutoutDrawable(this.shapeAppearanceModel);
                }
                this.boxUnderline = null;
                return;
            default:
                throw new IllegalArgumentException(this.boxBackgroundMode + " is illegal; only @BoxBackgroundMode constants are supported.");
        }
    }

    private void setEditTextBoxBackground() {
        if (shouldUseEditTextBackgroundForBoxBackground()) {
            ViewCompat.setBackground(this.editText, this.boxBackground);
        }
    }

    private boolean shouldUseEditTextBackgroundForBoxBackground() {
        EditText editText2 = this.editText;
        return (editText2 == null || this.boxBackground == null || editText2.getBackground() != null || this.boxBackgroundMode == 0) ? false : true;
    }

    private void updateBoxCollapsedPaddingTop() {
        if (this.boxBackgroundMode != 1) {
            return;
        }
        if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
            this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(R.dimen.material_font_2_0_box_collapsed_padding_top);
        } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(R.dimen.material_font_1_3_box_collapsed_padding_top);
        }
    }

    private void adjustFilledEditTextPaddingForLargeFont() {
        if (this.editText != null && this.boxBackgroundMode == 1) {
            if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
                EditText editText2 = this.editText;
                ViewCompat.setPaddingRelative(editText2, ViewCompat.getPaddingStart(editText2), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_2_0_padding_top), ViewCompat.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText3 = this.editText;
                ViewCompat.setPaddingRelative(editText3, ViewCompat.getPaddingStart(editText3), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_1_3_padding_top), ViewCompat.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
    }

    public void setBoxStrokeWidthResource(@DimenRes int boxStrokeWidthResId) {
        setBoxStrokeWidth(getResources().getDimensionPixelSize(boxStrokeWidthResId));
    }

    public void setBoxStrokeWidth(int boxStrokeWidth) {
        this.boxStrokeWidthDefaultPx = boxStrokeWidth;
        updateTextInputBoxState();
    }

    public int getBoxStrokeWidth() {
        return this.boxStrokeWidthDefaultPx;
    }

    public void setBoxStrokeWidthFocusedResource(@DimenRes int boxStrokeWidthFocusedResId) {
        setBoxStrokeWidthFocused(getResources().getDimensionPixelSize(boxStrokeWidthFocusedResId));
    }

    public void setBoxStrokeWidthFocused(int boxStrokeWidthFocused) {
        this.boxStrokeWidthFocusedPx = boxStrokeWidthFocused;
        updateTextInputBoxState();
    }

    public int getBoxStrokeWidthFocused() {
        return this.boxStrokeWidthFocusedPx;
    }

    public void setBoxStrokeColor(@ColorInt int boxStrokeColor2) {
        if (this.focusedStrokeColor != boxStrokeColor2) {
            this.focusedStrokeColor = boxStrokeColor2;
            updateTextInputBoxState();
        }
    }

    public int getBoxStrokeColor() {
        return this.focusedStrokeColor;
    }

    public void setBoxStrokeColorStateList(@NonNull ColorStateList boxStrokeColorStateList) {
        if (boxStrokeColorStateList.isStateful()) {
            this.defaultStrokeColor = boxStrokeColorStateList.getDefaultColor();
            this.disabledColor = boxStrokeColorStateList.getColorForState(new int[]{-16842910}, -1);
            this.hoveredStrokeColor = boxStrokeColorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.focusedStrokeColor = boxStrokeColorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.focusedStrokeColor != boxStrokeColorStateList.getDefaultColor()) {
            this.focusedStrokeColor = boxStrokeColorStateList.getDefaultColor();
        }
        updateTextInputBoxState();
    }

    public void setBoxStrokeErrorColor(@Nullable ColorStateList strokeErrorColor2) {
        if (this.strokeErrorColor != strokeErrorColor2) {
            this.strokeErrorColor = strokeErrorColor2;
            updateTextInputBoxState();
        }
    }

    @Nullable
    public ColorStateList getBoxStrokeErrorColor() {
        return this.strokeErrorColor;
    }

    public void setBoxBackgroundColorResource(@ColorRes int boxBackgroundColorId) {
        setBoxBackgroundColor(ContextCompat.getColor(getContext(), boxBackgroundColorId));
    }

    public void setBoxBackgroundColor(@ColorInt int boxBackgroundColor2) {
        if (this.boxBackgroundColor != boxBackgroundColor2) {
            this.boxBackgroundColor = boxBackgroundColor2;
            this.defaultFilledBackgroundColor = boxBackgroundColor2;
            this.focusedFilledBackgroundColor = boxBackgroundColor2;
            this.hoveredFilledBackgroundColor = boxBackgroundColor2;
            applyBoxAttributes();
        }
    }

    public void setBoxBackgroundColorStateList(@NonNull ColorStateList boxBackgroundColorStateList) {
        int defaultColor = boxBackgroundColorStateList.getDefaultColor();
        this.defaultFilledBackgroundColor = defaultColor;
        this.boxBackgroundColor = defaultColor;
        this.disabledFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{-16842910}, -1);
        this.focusedFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        this.hoveredFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
        applyBoxAttributes();
    }

    public int getBoxBackgroundColor() {
        return this.boxBackgroundColor;
    }

    public void setBoxCornerRadiiResources(@DimenRes int boxCornerRadiusTopStartId, @DimenRes int boxCornerRadiusTopEndId, @DimenRes int boxCornerRadiusBottomEndId, @DimenRes int boxCornerRadiusBottomStartId) {
        setBoxCornerRadii(getContext().getResources().getDimension(boxCornerRadiusTopStartId), getContext().getResources().getDimension(boxCornerRadiusTopEndId), getContext().getResources().getDimension(boxCornerRadiusBottomStartId), getContext().getResources().getDimension(boxCornerRadiusBottomEndId));
    }

    public void setBoxCornerRadii(float boxCornerRadiusTopStart, float boxCornerRadiusTopEnd, float boxCornerRadiusBottomStart, float boxCornerRadiusBottomEnd) {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable == null || materialShapeDrawable.getTopLeftCornerResolvedSize() != boxCornerRadiusTopStart || this.boxBackground.getTopRightCornerResolvedSize() != boxCornerRadiusTopEnd || this.boxBackground.getBottomRightCornerResolvedSize() != boxCornerRadiusBottomEnd || this.boxBackground.getBottomLeftCornerResolvedSize() != boxCornerRadiusBottomStart) {
            this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCornerSize(boxCornerRadiusTopStart).setTopRightCornerSize(boxCornerRadiusTopEnd).setBottomRightCornerSize(boxCornerRadiusBottomEnd).setBottomLeftCornerSize(boxCornerRadiusBottomStart).build();
            applyBoxAttributes();
        }
    }

    public float getBoxCornerRadiusTopStart() {
        return this.boxBackground.getTopLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusTopEnd() {
        return this.boxBackground.getTopRightCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomEnd() {
        return this.boxBackground.getBottomLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomStart() {
        return this.boxBackground.getBottomRightCornerResolvedSize();
    }

    public void setTypeface(@Nullable Typeface typeface2) {
        if (typeface2 != this.typeface) {
            this.typeface = typeface2;
            this.collapsingTextHelper.setTypefaces(typeface2);
            this.indicatorViewController.setTypefaces(typeface2);
            TextView textView = this.counterView;
            if (textView != null) {
                textView.setTypeface(typeface2);
            }
        }
    }

    @Nullable
    public Typeface getTypeface() {
        return this.typeface;
    }

    @TargetApi(26)
    public void dispatchProvideAutofillStructure(@NonNull ViewStructure structure, int flags) {
        EditText editText2 = this.editText;
        if (editText2 == null) {
            super.dispatchProvideAutofillStructure(structure, flags);
        } else if (this.originalHint != null) {
            boolean wasProvidingHint = this.isProvidingHint;
            this.isProvidingHint = false;
            CharSequence hint2 = editText2.getHint();
            this.editText.setHint(this.originalHint);
            try {
                super.dispatchProvideAutofillStructure(structure, flags);
            } finally {
                this.editText.setHint(hint2);
                this.isProvidingHint = wasProvidingHint;
            }
        } else {
            structure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(structure, flags);
            onProvideAutofillVirtualStructure(structure, flags);
            structure.setChildCount(this.inputFrame.getChildCount());
            for (int i = 0; i < this.inputFrame.getChildCount(); i++) {
                View child = this.inputFrame.getChildAt(i);
                ViewStructure childStructure = structure.newChild(i);
                child.dispatchProvideAutofillStructure(childStructure, flags);
                if (child == this.editText) {
                    childStructure.setHint(getHint());
                }
            }
        }
    }

    private void setEditText(EditText editText2) {
        if (this.editText == null) {
            if (this.endIconMode != 3 && !(editText2 instanceof TextInputEditText)) {
                Log.i(LOG_TAG, "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.editText = editText2;
            setMinWidth(this.minWidth);
            setMaxWidth(this.maxWidth);
            onApplyBoxBackgroundMode();
            setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
            this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
            this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
            int editTextGravity = this.editText.getGravity();
            this.collapsingTextHelper.setCollapsedTextGravity((editTextGravity & -113) | 48);
            this.collapsingTextHelper.setExpandedTextGravity(editTextGravity);
            this.editText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(@NonNull Editable s) {
                    TextInputLayout textInputLayout = TextInputLayout.this;
                    textInputLayout.updateLabelState(!textInputLayout.restoringSavedState);
                    TextInputLayout textInputLayout2 = TextInputLayout.this;
                    if (textInputLayout2.counterEnabled) {
                        textInputLayout2.updateCounter(s.length());
                    }
                    if (TextInputLayout.this.placeholderEnabled) {
                        TextInputLayout.this.updatePlaceholderText(s.length());
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
            if (this.defaultHintTextColor == null) {
                this.defaultHintTextColor = this.editText.getHintTextColors();
            }
            if (this.hintEnabled) {
                if (TextUtils.isEmpty(this.hint)) {
                    CharSequence hint2 = this.editText.getHint();
                    this.originalHint = hint2;
                    setHint(hint2);
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            if (this.counterView != null) {
                updateCounter(this.editText.getText().length());
            }
            updateEditTextBackground();
            this.indicatorViewController.adjustIndicatorPadding();
            this.startLayout.bringToFront();
            this.endLayout.bringToFront();
            this.endIconFrame.bringToFront();
            this.errorIconView.bringToFront();
            dispatchOnEditTextAttached();
            updatePrefixTextViewPadding();
            updateSuffixTextViewPadding();
            if (!isEnabled()) {
                editText2.setEnabled(false);
            }
            updateLabelState(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void updateInputLayoutMargins() {
        if (this.boxBackgroundMode != 1) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
            int newTopMargin = calculateLabelMarginTop();
            if (newTopMargin != lp.topMargin) {
                lp.topMargin = newTopMargin;
                this.inputFrame.requestLayout();
            }
        }
    }

    public int getBaseline() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            return editText2.getBaseline() + getPaddingTop() + calculateLabelMarginTop();
        }
        return super.getBaseline();
    }

    /* access modifiers changed from: package-private */
    public void updateLabelState(boolean animate) {
        updateLabelState(animate, false);
    }

    private void updateLabelState(boolean animate, boolean force) {
        ColorStateList colorStateList;
        TextView textView;
        boolean isEnabled = isEnabled();
        EditText editText2 = this.editText;
        boolean hasText = editText2 != null && !TextUtils.isEmpty(editText2.getText());
        EditText editText3 = this.editText;
        boolean hasFocus = editText3 != null && editText3.hasFocus();
        boolean errorShouldBeShown = this.indicatorViewController.errorShouldBeShown();
        ColorStateList colorStateList2 = this.defaultHintTextColor;
        if (colorStateList2 != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList2);
            this.collapsingTextHelper.setExpandedTextColor(this.defaultHintTextColor);
        }
        if (!isEnabled) {
            ColorStateList colorStateList3 = this.defaultHintTextColor;
            int disabledHintColor = colorStateList3 != null ? colorStateList3.getColorForState(new int[]{-16842910}, this.disabledColor) : this.disabledColor;
            this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(disabledHintColor));
            this.collapsingTextHelper.setExpandedTextColor(ColorStateList.valueOf(disabledHintColor));
        } else if (errorShouldBeShown) {
            this.collapsingTextHelper.setCollapsedTextColor(this.indicatorViewController.getErrorViewTextColors());
        } else if (this.counterOverflowed && (textView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(textView.getTextColors());
        } else if (hasFocus && (colorStateList = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (hasText || !this.expandedHintEnabled || (isEnabled() && hasFocus)) {
            if (force || this.hintExpanded) {
                collapseHint(animate);
            }
        } else if (force || !this.hintExpanded) {
            expandHint(animate);
        }
    }

    @Nullable
    public EditText getEditText() {
        return this.editText;
    }

    public void setMinWidth(@Px int minWidth2) {
        this.minWidth = minWidth2;
        EditText editText2 = this.editText;
        if (editText2 != null && minWidth2 != -1) {
            editText2.setMinWidth(minWidth2);
        }
    }

    public void setMinWidthResource(@DimenRes int minWidthId) {
        setMinWidth(getContext().getResources().getDimensionPixelSize(minWidthId));
    }

    @Px
    public int getMinWidth() {
        return this.minWidth;
    }

    public void setMaxWidth(@Px int maxWidth2) {
        this.maxWidth = maxWidth2;
        EditText editText2 = this.editText;
        if (editText2 != null && maxWidth2 != -1) {
            editText2.setMaxWidth(maxWidth2);
        }
    }

    public void setMaxWidthResource(@DimenRes int maxWidthId) {
        setMaxWidth(getContext().getResources().getDimensionPixelSize(maxWidthId));
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setHint(@Nullable CharSequence hint2) {
        if (this.hintEnabled) {
            setHintInternal(hint2);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHint(@StringRes int textHintId) {
        setHint(textHintId != 0 ? getResources().getText(textHintId) : null);
    }

    private void setHintInternal(CharSequence hint2) {
        if (!TextUtils.equals(hint2, this.hint)) {
            this.hint = hint2;
            this.collapsingTextHelper.setText(hint2);
            if (!this.hintExpanded) {
                openCutout();
            }
        }
    }

    @Nullable
    public CharSequence getHint() {
        if (this.hintEnabled) {
            return this.hint;
        }
        return null;
    }

    public void setHintEnabled(boolean enabled) {
        if (enabled != this.hintEnabled) {
            this.hintEnabled = enabled;
            if (!enabled) {
                this.isProvidingHint = false;
                if (!TextUtils.isEmpty(this.hint) && TextUtils.isEmpty(this.editText.getHint())) {
                    this.editText.setHint(this.hint);
                }
                setHintInternal((CharSequence) null);
            } else {
                CharSequence editTextHint = this.editText.getHint();
                if (!TextUtils.isEmpty(editTextHint)) {
                    if (TextUtils.isEmpty(this.hint)) {
                        setHint(editTextHint);
                    }
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            if (this.editText != null) {
                updateInputLayoutMargins();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.hintEnabled;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isProvidingHint() {
        return this.isProvidingHint;
    }

    public void setHintTextAppearance(@StyleRes int resId) {
        this.collapsingTextHelper.setCollapsedTextAppearance(resId);
        this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
        if (this.editText != null) {
            updateLabelState(false);
            updateInputLayoutMargins();
        }
    }

    public void setHintTextColor(@Nullable ColorStateList hintTextColor) {
        if (this.focusedTextColor != hintTextColor) {
            if (this.defaultHintTextColor == null) {
                this.collapsingTextHelper.setCollapsedTextColor(hintTextColor);
            }
            this.focusedTextColor = hintTextColor;
            if (this.editText != null) {
                updateLabelState(false);
            }
        }
    }

    @Nullable
    public ColorStateList getHintTextColor() {
        return this.focusedTextColor;
    }

    public void setDefaultHintTextColor(@Nullable ColorStateList textColor) {
        this.defaultHintTextColor = textColor;
        this.focusedTextColor = textColor;
        if (this.editText != null) {
            updateLabelState(false);
        }
    }

    @Nullable
    public ColorStateList getDefaultHintTextColor() {
        return this.defaultHintTextColor;
    }

    public void setErrorEnabled(boolean enabled) {
        this.indicatorViewController.setErrorEnabled(enabled);
    }

    public void setErrorTextAppearance(@StyleRes int errorTextAppearance) {
        this.indicatorViewController.setErrorTextAppearance(errorTextAppearance);
    }

    public void setErrorTextColor(@Nullable ColorStateList errorTextColor) {
        this.indicatorViewController.setErrorViewTextColor(errorTextColor);
    }

    @ColorInt
    public int getErrorCurrentTextColors() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public void setHelperTextTextAppearance(@StyleRes int helperTextTextAppearance) {
        this.indicatorViewController.setHelperTextAppearance(helperTextTextAppearance);
    }

    public void setHelperTextColor(@Nullable ColorStateList helperTextColor) {
        this.indicatorViewController.setHelperTextViewTextColor(helperTextColor);
    }

    public boolean isErrorEnabled() {
        return this.indicatorViewController.isErrorEnabled();
    }

    public void setHelperTextEnabled(boolean enabled) {
        this.indicatorViewController.setHelperTextEnabled(enabled);
    }

    public void setHelperText(@Nullable CharSequence helperText) {
        if (!TextUtils.isEmpty(helperText)) {
            if (!isHelperTextEnabled()) {
                setHelperTextEnabled(true);
            }
            this.indicatorViewController.showHelper(helperText);
        } else if (isHelperTextEnabled()) {
            setHelperTextEnabled(false);
        }
    }

    public boolean isHelperTextEnabled() {
        return this.indicatorViewController.isHelperTextEnabled();
    }

    @ColorInt
    public int getHelperTextCurrentTextColor() {
        return this.indicatorViewController.getHelperTextViewCurrentTextColor();
    }

    public void setErrorContentDescription(@Nullable CharSequence errorContentDecription) {
        this.indicatorViewController.setErrorContentDescription(errorContentDecription);
    }

    @Nullable
    public CharSequence getErrorContentDescription() {
        return this.indicatorViewController.getErrorContentDescription();
    }

    public void setError(@Nullable CharSequence errorText) {
        if (!this.indicatorViewController.isErrorEnabled()) {
            if (!TextUtils.isEmpty(errorText)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(errorText)) {
            this.indicatorViewController.showError(errorText);
        } else {
            this.indicatorViewController.hideError();
        }
    }

    public void setErrorIconDrawable(@DrawableRes int resId) {
        setErrorIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
        refreshErrorIconDrawableState();
    }

    public void setErrorIconDrawable(@Nullable Drawable errorIconDrawable) {
        this.errorIconView.setImageDrawable(errorIconDrawable);
        setErrorIconVisible(errorIconDrawable != null && this.indicatorViewController.isErrorEnabled());
    }

    @Nullable
    public Drawable getErrorIconDrawable() {
        return this.errorIconView.getDrawable();
    }

    public void setErrorIconTintList(@Nullable ColorStateList errorIconTintList2) {
        this.errorIconTintList = errorIconTintList2;
        Drawable icon = this.errorIconView.getDrawable();
        if (icon != null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, errorIconTintList2);
        }
        if (this.errorIconView.getDrawable() != icon) {
            this.errorIconView.setImageDrawable(icon);
        }
    }

    public void setErrorIconTintMode(@Nullable PorterDuff.Mode errorIconTintMode) {
        Drawable icon = this.errorIconView.getDrawable();
        if (icon != null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintMode(icon, errorIconTintMode);
        }
        if (this.errorIconView.getDrawable() != icon) {
            this.errorIconView.setImageDrawable(icon);
        }
    }

    public void setCounterEnabled(boolean enabled) {
        if (this.counterEnabled != enabled) {
            if (enabled) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView;
                appCompatTextView.setId(R.id.textinput_counter);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.counterView.setTypeface(typeface2);
                }
                this.counterView.setMaxLines(1);
                this.indicatorViewController.addIndicator(this.counterView, 2);
                MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.counterView.getLayoutParams(), getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_counter_margin_start));
                updateCounterTextAppearanceAndColor();
                updateCounter();
            } else {
                this.indicatorViewController.removeIndicator(this.counterView, 2);
                this.counterView = null;
            }
            this.counterEnabled = enabled;
        }
    }

    public void setCounterTextAppearance(int counterTextAppearance2) {
        if (this.counterTextAppearance != counterTextAppearance2) {
            this.counterTextAppearance = counterTextAppearance2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterTextColor(@Nullable ColorStateList counterTextColor2) {
        if (this.counterTextColor != counterTextColor2) {
            this.counterTextColor = counterTextColor2;
            updateCounterTextAppearanceAndColor();
        }
    }

    @Nullable
    public ColorStateList getCounterTextColor() {
        return this.counterTextColor;
    }

    public void setCounterOverflowTextAppearance(int counterOverflowTextAppearance2) {
        if (this.counterOverflowTextAppearance != counterOverflowTextAppearance2) {
            this.counterOverflowTextAppearance = counterOverflowTextAppearance2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterOverflowTextColor(@Nullable ColorStateList counterOverflowTextColor2) {
        if (this.counterOverflowTextColor != counterOverflowTextColor2) {
            this.counterOverflowTextColor = counterOverflowTextColor2;
            updateCounterTextAppearanceAndColor();
        }
    }

    @Nullable
    public ColorStateList getCounterOverflowTextColor() {
        return this.counterTextColor;
    }

    public boolean isCounterEnabled() {
        return this.counterEnabled;
    }

    public void setCounterMaxLength(int maxLength) {
        if (this.counterMaxLength != maxLength) {
            if (maxLength > 0) {
                this.counterMaxLength = maxLength;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.counterEnabled) {
                updateCounter();
            }
        }
    }

    private void updateCounter() {
        if (this.counterView != null) {
            EditText editText2 = this.editText;
            updateCounter(editText2 == null ? 0 : editText2.getText().length());
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCounter(int length) {
        boolean wasCounterOverflowed = this.counterOverflowed;
        int i = this.counterMaxLength;
        if (i == -1) {
            this.counterView.setText(String.valueOf(length));
            this.counterView.setContentDescription((CharSequence) null);
            this.counterOverflowed = false;
        } else {
            this.counterOverflowed = length > i;
            updateCounterContentDescription(getContext(), this.counterView, length, this.counterMaxLength, this.counterOverflowed);
            if (wasCounterOverflowed != this.counterOverflowed) {
                updateCounterTextAppearanceAndColor();
            }
            this.counterView.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(R.string.character_counter_pattern, new Object[]{Integer.valueOf(length), Integer.valueOf(this.counterMaxLength)})));
        }
        if (this.editText != null && wasCounterOverflowed != this.counterOverflowed) {
            updateLabelState(false);
            updateTextInputBoxState();
            updateEditTextBackground();
        }
    }

    private static void updateCounterContentDescription(@NonNull Context context, @NonNull TextView counterView2, int length, int counterMaxLength2, boolean counterOverflowed2) {
        counterView2.setContentDescription(context.getString(counterOverflowed2 ? R.string.character_counter_overflowed_content_description : R.string.character_counter_content_description, new Object[]{Integer.valueOf(length), Integer.valueOf(counterMaxLength2)}));
    }

    public void setPlaceholderText(@Nullable CharSequence placeholderText2) {
        if (!this.placeholderEnabled || !TextUtils.isEmpty(placeholderText2)) {
            if (!this.placeholderEnabled) {
                setPlaceholderTextEnabled(true);
            }
            this.placeholderText = placeholderText2;
        } else {
            setPlaceholderTextEnabled(false);
        }
        updatePlaceholderText();
    }

    @Nullable
    public CharSequence getPlaceholderText() {
        if (this.placeholderEnabled) {
            return this.placeholderText;
        }
        return null;
    }

    private void setPlaceholderTextEnabled(boolean placeholderEnabled2) {
        if (this.placeholderEnabled != placeholderEnabled2) {
            if (placeholderEnabled2) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.placeholderTextView = appCompatTextView;
                appCompatTextView.setId(R.id.textinput_placeholder);
                ViewCompat.setAccessibilityLiveRegion(this.placeholderTextView, 1);
                setPlaceholderTextAppearance(this.placeholderTextAppearance);
                setPlaceholderTextColor(this.placeholderTextColor);
                addPlaceholderTextView();
            } else {
                removePlaceholderTextView();
                this.placeholderTextView = null;
            }
            this.placeholderEnabled = placeholderEnabled2;
        }
    }

    private void updatePlaceholderText() {
        EditText editText2 = this.editText;
        updatePlaceholderText(editText2 == null ? 0 : editText2.getText().length());
    }

    /* access modifiers changed from: private */
    public void updatePlaceholderText(int inputTextLength) {
        if (inputTextLength != 0 || this.hintExpanded) {
            hidePlaceholderText();
        } else {
            showPlaceholderText();
        }
    }

    private void showPlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView != null && this.placeholderEnabled) {
            textView.setText(this.placeholderText);
            this.placeholderTextView.setVisibility(0);
            this.placeholderTextView.bringToFront();
        }
    }

    private void hidePlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView != null && this.placeholderEnabled) {
            textView.setText((CharSequence) null);
            this.placeholderTextView.setVisibility(4);
        }
    }

    private void addPlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            this.inputFrame.addView(textView);
            this.placeholderTextView.setVisibility(0);
        }
    }

    private void removePlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void setPlaceholderTextColor(@Nullable ColorStateList placeholderTextColor2) {
        if (this.placeholderTextColor != placeholderTextColor2) {
            this.placeholderTextColor = placeholderTextColor2;
            TextView textView = this.placeholderTextView;
            if (textView != null && placeholderTextColor2 != null) {
                textView.setTextColor(placeholderTextColor2);
            }
        }
    }

    @Nullable
    public ColorStateList getPlaceholderTextColor() {
        return this.placeholderTextColor;
    }

    public void setPlaceholderTextAppearance(@StyleRes int placeholderTextAppearance2) {
        this.placeholderTextAppearance = placeholderTextAppearance2;
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, placeholderTextAppearance2);
        }
    }

    @StyleRes
    public int getPlaceholderTextAppearance() {
        return this.placeholderTextAppearance;
    }

    public void setPrefixText(@Nullable CharSequence prefixText2) {
        this.prefixText = TextUtils.isEmpty(prefixText2) ? null : prefixText2;
        this.prefixTextView.setText(prefixText2);
        updatePrefixTextVisibility();
    }

    @Nullable
    public CharSequence getPrefixText() {
        return this.prefixText;
    }

    @NonNull
    public TextView getPrefixTextView() {
        return this.prefixTextView;
    }

    private void updatePrefixTextVisibility() {
        this.prefixTextView.setVisibility((this.prefixText == null || isHintExpanded()) ? 8 : 0);
        updateDummyDrawables();
    }

    public void setPrefixTextColor(@NonNull ColorStateList prefixTextColor) {
        this.prefixTextView.setTextColor(prefixTextColor);
    }

    @Nullable
    public ColorStateList getPrefixTextColor() {
        return this.prefixTextView.getTextColors();
    }

    public void setPrefixTextAppearance(@StyleRes int prefixTextAppearance) {
        TextViewCompat.setTextAppearance(this.prefixTextView, prefixTextAppearance);
    }

    private void updatePrefixTextViewPadding() {
        if (this.editText != null) {
            ViewCompat.setPaddingRelative(this.prefixTextView, isStartIconVisible() ? 0 : ViewCompat.getPaddingStart(this.editText), this.editText.getCompoundPaddingTop(), getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), this.editText.getCompoundPaddingBottom());
        }
    }

    public void setSuffixText(@Nullable CharSequence suffixText2) {
        this.suffixText = TextUtils.isEmpty(suffixText2) ? null : suffixText2;
        this.suffixTextView.setText(suffixText2);
        updateSuffixTextVisibility();
    }

    @Nullable
    public CharSequence getSuffixText() {
        return this.suffixText;
    }

    @NonNull
    public TextView getSuffixTextView() {
        return this.suffixTextView;
    }

    private void updateSuffixTextVisibility() {
        int oldSuffixVisibility = this.suffixTextView.getVisibility();
        int i = 0;
        boolean visible = this.suffixText != null && !isHintExpanded();
        TextView textView = this.suffixTextView;
        if (!visible) {
            i = 8;
        }
        textView.setVisibility(i);
        if (oldSuffixVisibility != this.suffixTextView.getVisibility()) {
            getEndIconDelegate().onSuffixVisibilityChanged(visible);
        }
        updateDummyDrawables();
    }

    public void setSuffixTextColor(@NonNull ColorStateList suffixTextColor) {
        this.suffixTextView.setTextColor(suffixTextColor);
    }

    @Nullable
    public ColorStateList getSuffixTextColor() {
        return this.suffixTextView.getTextColors();
    }

    public void setSuffixTextAppearance(@StyleRes int suffixTextAppearance) {
        TextViewCompat.setTextAppearance(this.suffixTextView, suffixTextAppearance);
    }

    private void updateSuffixTextViewPadding() {
        if (this.editText != null) {
            ViewCompat.setPaddingRelative(this.suffixTextView, getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), this.editText.getPaddingTop(), (isEndIconVisible() || isErrorIconVisible()) ? 0 : ViewCompat.getPaddingEnd(this.editText), this.editText.getPaddingBottom());
        }
    }

    public void setEnabled(boolean enabled) {
        recursiveSetEnabled(this, enabled);
        super.setEnabled(enabled);
    }

    private static void recursiveSetEnabled(@NonNull ViewGroup vg, boolean enabled) {
        int count = vg.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) child, enabled);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.counterMaxLength;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (!this.counterEnabled || !this.counterOverflowed || (textView = this.counterView) == null) {
            return null;
        }
        return textView.getContentDescription();
    }

    private void updateCounterTextAppearanceAndColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        TextView textView = this.counterView;
        if (textView != null) {
            setTextAppearanceCompatWithErrorFallback(textView, this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (this.counterOverflowed && (colorStateList = this.counterOverflowTextColor) != null) {
                this.counterView.setTextColor(colorStateList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setTextAppearanceCompatWithErrorFallback(@NonNull TextView textView, @StyleRes int textAppearance) {
        boolean useDefaultColor = false;
        try {
            TextViewCompat.setTextAppearance(textView, textAppearance);
            if (Build.VERSION.SDK_INT >= 23 && textView.getTextColors().getDefaultColor() == -65281) {
                useDefaultColor = true;
            }
        } catch (Exception e) {
            useDefaultColor = true;
        }
        if (useDefaultColor) {
            TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_AppCompat_Caption);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.design_error));
        }
    }

    private int calculateLabelMarginTop() {
        if (!this.hintEnabled) {
            return 0;
        }
        switch (this.boxBackgroundMode) {
            case 0:
            case 1:
                return (int) this.collapsingTextHelper.getCollapsedTextHeight();
            case 2:
                return (int) (this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f);
            default:
                return 0;
        }
    }

    @NonNull
    private Rect calculateCollapsedTextBounds(@NonNull Rect rect) {
        if (this.editText != null) {
            Rect bounds = this.tmpBoundsRect;
            boolean z = true;
            if (ViewCompat.getLayoutDirection(this) != 1) {
                z = false;
            }
            boolean isRtl = z;
            bounds.bottom = rect.bottom;
            switch (this.boxBackgroundMode) {
                case 1:
                    bounds.left = getLabelLeftBoundAlightWithPrefix(rect.left, isRtl);
                    bounds.top = rect.top + this.boxCollapsedPaddingTopPx;
                    bounds.right = getLabelRightBoundAlignedWithSuffix(rect.right, isRtl);
                    return bounds;
                case 2:
                    bounds.left = rect.left + this.editText.getPaddingLeft();
                    bounds.top = rect.top - calculateLabelMarginTop();
                    bounds.right = rect.right - this.editText.getPaddingRight();
                    return bounds;
                default:
                    bounds.left = getLabelLeftBoundAlightWithPrefix(rect.left, isRtl);
                    bounds.top = getPaddingTop();
                    bounds.right = getLabelRightBoundAlignedWithSuffix(rect.right, isRtl);
                    return bounds;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private int getLabelLeftBoundAlightWithPrefix(int rectLeft, boolean isRtl) {
        int left = this.editText.getCompoundPaddingLeft() + rectLeft;
        if (this.prefixText == null || isRtl) {
            return left;
        }
        return (left - this.prefixTextView.getMeasuredWidth()) + this.prefixTextView.getPaddingLeft();
    }

    private int getLabelRightBoundAlignedWithSuffix(int rectRight, boolean isRtl) {
        int right = rectRight - this.editText.getCompoundPaddingRight();
        if (this.prefixText == null || !isRtl) {
            return right;
        }
        return right + (this.prefixTextView.getMeasuredWidth() - this.prefixTextView.getPaddingRight());
    }

    @NonNull
    private Rect calculateExpandedTextBounds(@NonNull Rect rect) {
        if (this.editText != null) {
            Rect bounds = this.tmpBoundsRect;
            float labelHeight = this.collapsingTextHelper.getExpandedTextHeight();
            bounds.left = rect.left + this.editText.getCompoundPaddingLeft();
            bounds.top = calculateExpandedLabelTop(rect, labelHeight);
            bounds.right = rect.right - this.editText.getCompoundPaddingRight();
            bounds.bottom = calculateExpandedLabelBottom(rect, bounds, labelHeight);
            return bounds;
        }
        throw new IllegalStateException();
    }

    private int calculateExpandedLabelTop(@NonNull Rect rect, float labelHeight) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) rect.centerY()) - (labelHeight / 2.0f));
        }
        return rect.top + this.editText.getCompoundPaddingTop();
    }

    private int calculateExpandedLabelBottom(@NonNull Rect rect, @NonNull Rect bounds, float labelHeight) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) bounds.top) + labelHeight);
        }
        return rect.bottom - this.editText.getCompoundPaddingBottom();
    }

    private boolean isSingleLineFilledTextField() {
        if (this.boxBackgroundMode != 1 || (Build.VERSION.SDK_INT >= 16 && this.editText.getMinLines() > 1)) {
            return false;
        }
        return true;
    }

    private int calculateBoxBackgroundColor() {
        int backgroundColor = this.boxBackgroundColor;
        if (this.boxBackgroundMode == 1) {
            return MaterialColors.layer(MaterialColors.getColor((View) this, R.attr.colorSurface, 0), this.boxBackgroundColor);
        }
        return backgroundColor;
    }

    private void applyBoxAttributes() {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
            if (canDrawOutlineStroke()) {
                this.boxBackground.setStroke((float) this.boxStrokeWidthPx, this.boxStrokeColor);
            }
            int calculateBoxBackgroundColor = calculateBoxBackgroundColor();
            this.boxBackgroundColor = calculateBoxBackgroundColor;
            this.boxBackground.setFillColor(ColorStateList.valueOf(calculateBoxBackgroundColor));
            if (this.endIconMode == 3) {
                this.editText.getBackground().invalidateSelf();
            }
            applyBoxUnderlineAttributes();
            invalidate();
        }
    }

    private void applyBoxUnderlineAttributes() {
        if (this.boxUnderline != null) {
            if (canDrawStroke()) {
                this.boxUnderline.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
            }
            invalidate();
        }
    }

    private boolean canDrawOutlineStroke() {
        return this.boxBackgroundMode == 2 && canDrawStroke();
    }

    private boolean canDrawStroke() {
        return this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0;
    }

    /* access modifiers changed from: package-private */
    public void updateEditTextBackground() {
        Drawable editTextBackground;
        TextView textView;
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackgroundMode == 0 && (editTextBackground = editText2.getBackground()) != null) {
            if (DrawableUtils.canSafelyMutateDrawable(editTextBackground)) {
                editTextBackground = editTextBackground.mutate();
            }
            if (this.indicatorViewController.errorShouldBeShown()) {
                editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.indicatorViewController.getErrorViewCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            } else if (!this.counterOverflowed || (textView = this.counterView) == null) {
                DrawableCompat.clearColorFilter(editTextBackground);
                this.editText.refreshDrawableState();
            } else {
                editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Nullable
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        @Nullable
        CharSequence error;
        @Nullable
        CharSequence helperText;
        @Nullable
        CharSequence hintText;
        boolean isEndIconChecked;
        @Nullable
        CharSequence placeholderText;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(@NonNull Parcel source, ClassLoader loader) {
            super(source, loader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            this.isEndIconChecked = source.readInt() != 1 ? false : true;
            this.hintText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            this.helperText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            this.placeholderText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        }

        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            TextUtils.writeToParcel(this.error, dest, flags);
            dest.writeInt(this.isEndIconChecked ? 1 : 0);
            TextUtils.writeToParcel(this.hintText, dest, flags);
            TextUtils.writeToParcel(this.helperText, dest, flags);
            TextUtils.writeToParcel(this.placeholderText, dest, flags);
        }

        @NonNull
        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + " hint=" + this.hintText + " helperText=" + this.helperText + " placeholderText=" + this.placeholderText + "}";
        }
    }

    @Nullable
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (this.indicatorViewController.errorShouldBeShown()) {
            ss.error = getError();
        }
        ss.isEndIconChecked = hasEndIcon() && this.endIconView.isChecked();
        ss.hintText = getHint();
        ss.helperText = getHelperText();
        ss.placeholderText = getPlaceholderText();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(@Nullable Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setError(ss.error);
        if (ss.isEndIconChecked) {
            this.endIconView.post(new Runnable() {
                public void run() {
                    TextInputLayout.this.endIconView.performClick();
                    TextInputLayout.this.endIconView.jumpDrawablesToCurrentState();
                }
            });
        }
        setHint(ss.hintText);
        setHelperText(ss.helperText);
        setPlaceholderText(ss.placeholderText);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(@NonNull SparseArray<Parcelable> container) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(container);
        this.restoringSavedState = false;
    }

    @Nullable
    public CharSequence getError() {
        if (this.indicatorViewController.isErrorEnabled()) {
            return this.indicatorViewController.getErrorText();
        }
        return null;
    }

    @Nullable
    public CharSequence getHelperText() {
        if (this.indicatorViewController.isHelperTextEnabled()) {
            return this.indicatorViewController.getHelperText();
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.hintAnimationEnabled;
    }

    public void setHintAnimationEnabled(boolean enabled) {
        this.hintAnimationEnabled = enabled;
    }

    public boolean isExpandedHintEnabled() {
        return this.expandedHintEnabled;
    }

    public void setExpandedHintEnabled(boolean enabled) {
        if (this.expandedHintEnabled != enabled) {
            this.expandedHintEnabled = enabled;
            updateLabelState(false);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        boolean updatedHeight = updateEditTextHeightBasedOnIcon();
        boolean updatedIcon = updateDummyDrawables();
        if (updatedHeight || updatedIcon) {
            this.editText.post(new Runnable() {
                public void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
        updatePlaceholderMeasurementsBasedOnEditText();
        updatePrefixTextViewPadding();
        updateSuffixTextViewPadding();
    }

    private boolean updateEditTextHeightBasedOnIcon() {
        int maxIconHeight;
        if (this.editText == null || this.editText.getMeasuredHeight() >= (maxIconHeight = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            return false;
        }
        this.editText.setMinimumHeight(maxIconHeight);
        return true;
    }

    private void updatePlaceholderMeasurementsBasedOnEditText() {
        EditText editText2;
        if (this.placeholderTextView != null && (editText2 = this.editText) != null) {
            this.placeholderTextView.setGravity(editText2.getGravity());
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
        }
    }

    public void setStartIconDrawable(@DrawableRes int resId) {
        setStartIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    public void setStartIconDrawable(@Nullable Drawable startIconDrawable) {
        this.startIconView.setImageDrawable(startIconDrawable);
        if (startIconDrawable != null) {
            setStartIconVisible(true);
            refreshStartIconDrawableState();
            return;
        }
        setStartIconVisible(false);
        setStartIconOnClickListener((View.OnClickListener) null);
        setStartIconOnLongClickListener((View.OnLongClickListener) null);
        setStartIconContentDescription((CharSequence) null);
    }

    @Nullable
    public Drawable getStartIconDrawable() {
        return this.startIconView.getDrawable();
    }

    public void setStartIconOnClickListener(@Nullable View.OnClickListener startIconOnClickListener) {
        setIconOnClickListener(this.startIconView, startIconOnClickListener, this.startIconOnLongClickListener);
    }

    public void setStartIconOnLongClickListener(@Nullable View.OnLongClickListener startIconOnLongClickListener2) {
        this.startIconOnLongClickListener = startIconOnLongClickListener2;
        setIconOnLongClickListener(this.startIconView, startIconOnLongClickListener2);
    }

    public void setStartIconVisible(boolean visible) {
        if (isStartIconVisible() != visible) {
            this.startIconView.setVisibility(visible ? 0 : 8);
            updatePrefixTextViewPadding();
            updateDummyDrawables();
        }
    }

    public boolean isStartIconVisible() {
        return this.startIconView.getVisibility() == 0;
    }

    public void refreshStartIconDrawableState() {
        refreshIconDrawableState(this.startIconView, this.startIconTintList);
    }

    public void setStartIconCheckable(boolean startIconCheckable) {
        this.startIconView.setCheckable(startIconCheckable);
    }

    public boolean isStartIconCheckable() {
        return this.startIconView.isCheckable();
    }

    public void setStartIconContentDescription(@StringRes int resId) {
        setStartIconContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    public void setStartIconContentDescription(@Nullable CharSequence startIconContentDescription) {
        if (getStartIconContentDescription() != startIconContentDescription) {
            this.startIconView.setContentDescription(startIconContentDescription);
        }
    }

    @Nullable
    public CharSequence getStartIconContentDescription() {
        return this.startIconView.getContentDescription();
    }

    public void setStartIconTintList(@Nullable ColorStateList startIconTintList2) {
        if (this.startIconTintList != startIconTintList2) {
            this.startIconTintList = startIconTintList2;
            this.hasStartIconTintList = true;
            applyStartIconTint();
        }
    }

    public void setStartIconTintMode(@Nullable PorterDuff.Mode startIconTintMode2) {
        if (this.startIconTintMode != startIconTintMode2) {
            this.startIconTintMode = startIconTintMode2;
            this.hasStartIconTintMode = true;
            applyStartIconTint();
        }
    }

    public void setEndIconMode(int endIconMode2) {
        int previousEndIconMode = this.endIconMode;
        this.endIconMode = endIconMode2;
        dispatchOnEndIconChanged(previousEndIconMode);
        setEndIconVisible(endIconMode2 != 0);
        if (getEndIconDelegate().isBoxBackgroundModeSupported(this.boxBackgroundMode)) {
            getEndIconDelegate().initialize();
            applyEndIconTint();
            return;
        }
        throw new IllegalStateException("The current box background mode " + this.boxBackgroundMode + " is not supported by the end icon mode " + endIconMode2);
    }

    public int getEndIconMode() {
        return this.endIconMode;
    }

    public void setEndIconOnClickListener(@Nullable View.OnClickListener endIconOnClickListener) {
        setIconOnClickListener(this.endIconView, endIconOnClickListener, this.endIconOnLongClickListener);
    }

    public void setErrorIconOnClickListener(@Nullable View.OnClickListener errorIconOnClickListener) {
        setIconOnClickListener(this.errorIconView, errorIconOnClickListener, this.errorIconOnLongClickListener);
    }

    public void setEndIconOnLongClickListener(@Nullable View.OnLongClickListener endIconOnLongClickListener2) {
        this.endIconOnLongClickListener = endIconOnLongClickListener2;
        setIconOnLongClickListener(this.endIconView, endIconOnLongClickListener2);
    }

    public void setErrorIconOnLongClickListener(@Nullable View.OnLongClickListener errorIconOnLongClickListener2) {
        this.errorIconOnLongClickListener = errorIconOnLongClickListener2;
        setIconOnLongClickListener(this.errorIconView, errorIconOnLongClickListener2);
    }

    public void refreshErrorIconDrawableState() {
        refreshIconDrawableState(this.errorIconView, this.errorIconTintList);
    }

    public void setEndIconVisible(boolean visible) {
        if (isEndIconVisible() != visible) {
            this.endIconView.setVisibility(visible ? 0 : 8);
            updateSuffixTextViewPadding();
            updateDummyDrawables();
        }
    }

    public boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    public void setEndIconActivated(boolean endIconActivated) {
        this.endIconView.setActivated(endIconActivated);
    }

    public void refreshEndIconDrawableState() {
        refreshIconDrawableState(this.endIconView, this.endIconTintList);
    }

    public void setEndIconCheckable(boolean endIconCheckable) {
        this.endIconView.setCheckable(endIconCheckable);
    }

    public boolean isEndIconCheckable() {
        return this.endIconView.isCheckable();
    }

    public void setEndIconDrawable(@DrawableRes int resId) {
        setEndIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    public void setEndIconDrawable(@Nullable Drawable endIconDrawable) {
        this.endIconView.setImageDrawable(endIconDrawable);
        refreshEndIconDrawableState();
    }

    @Nullable
    public Drawable getEndIconDrawable() {
        return this.endIconView.getDrawable();
    }

    public void setEndIconContentDescription(@StringRes int resId) {
        setEndIconContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    public void setEndIconContentDescription(@Nullable CharSequence endIconContentDescription) {
        if (getEndIconContentDescription() != endIconContentDescription) {
            this.endIconView.setContentDescription(endIconContentDescription);
        }
    }

    @Nullable
    public CharSequence getEndIconContentDescription() {
        return this.endIconView.getContentDescription();
    }

    public void setEndIconTintList(@Nullable ColorStateList endIconTintList2) {
        if (this.endIconTintList != endIconTintList2) {
            this.endIconTintList = endIconTintList2;
            this.hasEndIconTintList = true;
            applyEndIconTint();
        }
    }

    public void setEndIconTintMode(@Nullable PorterDuff.Mode endIconTintMode2) {
        if (this.endIconTintMode != endIconTintMode2) {
            this.endIconTintMode = endIconTintMode2;
            this.hasEndIconTintMode = true;
            applyEndIconTint();
        }
    }

    public void addOnEndIconChangedListener(@NonNull OnEndIconChangedListener listener) {
        this.endIconChangedListeners.add(listener);
    }

    public void removeOnEndIconChangedListener(@NonNull OnEndIconChangedListener listener) {
        this.endIconChangedListeners.remove(listener);
    }

    public void clearOnEndIconChangedListeners() {
        this.endIconChangedListeners.clear();
    }

    public void addOnEditTextAttachedListener(@NonNull OnEditTextAttachedListener listener) {
        this.editTextAttachedListeners.add(listener);
        if (this.editText != null) {
            listener.onEditTextAttached(this);
        }
    }

    public void removeOnEditTextAttachedListener(@NonNull OnEditTextAttachedListener listener) {
        this.editTextAttachedListeners.remove(listener);
    }

    public void clearOnEditTextAttachedListeners() {
        this.editTextAttachedListeners.clear();
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(@DrawableRes int resId) {
        setPasswordVisibilityToggleDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable icon) {
        this.endIconView.setImageDrawable(icon);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(@StringRes int resId) {
        setPasswordVisibilityToggleContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence description) {
        this.endIconView.setContentDescription(description);
    }

    @Deprecated
    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.endIconView.getDrawable();
    }

    @Deprecated
    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.endIconView.getContentDescription();
    }

    @Deprecated
    public boolean isPasswordVisibilityToggleEnabled() {
        return this.endIconMode == 1;
    }

    @Deprecated
    public void setPasswordVisibilityToggleEnabled(boolean enabled) {
        if (enabled && this.endIconMode != 1) {
            setEndIconMode(1);
        } else if (!enabled) {
            setEndIconMode(0);
        }
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList tintList) {
        this.endIconTintList = tintList;
        this.hasEndIconTintList = true;
        applyEndIconTint();
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.endIconTintMode = mode;
        this.hasEndIconTintMode = true;
        applyEndIconTint();
    }

    @Deprecated
    public void passwordVisibilityToggleRequested(boolean shouldSkipAnimations) {
        if (this.endIconMode == 1) {
            this.endIconView.performClick();
            if (shouldSkipAnimations) {
                this.endIconView.jumpDrawablesToCurrentState();
            }
        }
    }

    public void setTextInputAccessibilityDelegate(@Nullable AccessibilityDelegate delegate) {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            ViewCompat.setAccessibilityDelegate(editText2, delegate);
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public CheckableImageButton getEndIconView() {
        return this.endIconView;
    }

    private EndIconDelegate getEndIconDelegate() {
        EndIconDelegate endIconDelegate = this.endIconDelegates.get(this.endIconMode);
        return endIconDelegate != null ? endIconDelegate : this.endIconDelegates.get(0);
    }

    private void dispatchOnEditTextAttached() {
        Iterator it = this.editTextAttachedListeners.iterator();
        while (it.hasNext()) {
            ((OnEditTextAttachedListener) it.next()).onEditTextAttached(this);
        }
    }

    private void applyStartIconTint() {
        applyIconTint(this.startIconView, this.hasStartIconTintList, this.startIconTintList, this.hasStartIconTintMode, this.startIconTintMode);
    }

    private boolean hasEndIcon() {
        return this.endIconMode != 0;
    }

    private void dispatchOnEndIconChanged(int previousIcon) {
        Iterator it = this.endIconChangedListeners.iterator();
        while (it.hasNext()) {
            ((OnEndIconChangedListener) it.next()).onEndIconChanged(this, previousIcon);
        }
    }

    private void tintEndIconOnError(boolean tintEndIconOnError) {
        if (!tintEndIconOnError || getEndIconDrawable() == null) {
            applyEndIconTint();
            return;
        }
        Drawable endIconDrawable = DrawableCompat.wrap(getEndIconDrawable()).mutate();
        DrawableCompat.setTint(endIconDrawable, this.indicatorViewController.getErrorViewCurrentTextColor());
        this.endIconView.setImageDrawable(endIconDrawable);
    }

    private void applyEndIconTint() {
        applyIconTint(this.endIconView, this.hasEndIconTintList, this.endIconTintList, this.hasEndIconTintMode, this.endIconTintMode);
    }

    private boolean updateDummyDrawables() {
        if (this.editText == null) {
            return false;
        }
        boolean updatedIcon = false;
        if (shouldUpdateStartDummyDrawable()) {
            int right = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
            if (this.startDummyDrawable == null || this.startDummyDrawableWidth != right) {
                ColorDrawable colorDrawable = new ColorDrawable();
                this.startDummyDrawable = colorDrawable;
                this.startDummyDrawableWidth = right;
                colorDrawable.setBounds(0, 0, right, 1);
            }
            Drawable[] compounds = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable drawable = compounds[0];
            Drawable drawable2 = this.startDummyDrawable;
            if (drawable != drawable2) {
                TextViewCompat.setCompoundDrawablesRelative(this.editText, drawable2, compounds[1], compounds[2], compounds[3]);
                updatedIcon = true;
            }
        } else if (this.startDummyDrawable != null) {
            Drawable[] compounds2 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            TextViewCompat.setCompoundDrawablesRelative(this.editText, (Drawable) null, compounds2[1], compounds2[2], compounds2[3]);
            this.startDummyDrawable = null;
            updatedIcon = true;
        }
        if (shouldUpdateEndDummyDrawable()) {
            int right2 = this.suffixTextView.getMeasuredWidth() - this.editText.getPaddingRight();
            View iconView = getEndIconToUpdateDummyDrawable();
            if (iconView != null) {
                right2 = iconView.getMeasuredWidth() + right2 + MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) iconView.getLayoutParams());
            }
            Drawable[] compounds3 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable drawable3 = this.endDummyDrawable;
            if (drawable3 == null || this.endDummyDrawableWidth == right2) {
                if (drawable3 == null) {
                    ColorDrawable colorDrawable2 = new ColorDrawable();
                    this.endDummyDrawable = colorDrawable2;
                    this.endDummyDrawableWidth = right2;
                    colorDrawable2.setBounds(0, 0, right2, 1);
                }
                Drawable drawable4 = compounds3[2];
                Drawable drawable5 = this.endDummyDrawable;
                if (drawable4 == drawable5) {
                    return updatedIcon;
                }
                this.originalEditTextEndDrawable = compounds3[2];
                TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds3[0], compounds3[1], drawable5, compounds3[3]);
                return true;
            }
            this.endDummyDrawableWidth = right2;
            drawable3.setBounds(0, 0, right2, 1);
            TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds3[0], compounds3[1], this.endDummyDrawable, compounds3[3]);
            return true;
        } else if (this.endDummyDrawable == null) {
            return updatedIcon;
        } else {
            Drawable[] compounds4 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            if (compounds4[2] == this.endDummyDrawable) {
                TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds4[0], compounds4[1], this.originalEditTextEndDrawable, compounds4[3]);
                updatedIcon = true;
            }
            this.endDummyDrawable = null;
            return updatedIcon;
        }
    }

    private boolean shouldUpdateStartDummyDrawable() {
        return !(getStartIconDrawable() == null && this.prefixText == null) && this.startLayout.getMeasuredWidth() > 0;
    }

    private boolean shouldUpdateEndDummyDrawable() {
        return (this.errorIconView.getVisibility() == 0 || ((hasEndIcon() && isEndIconVisible()) || this.suffixText != null)) && this.endLayout.getMeasuredWidth() > 0;
    }

    @Nullable
    private CheckableImageButton getEndIconToUpdateDummyDrawable() {
        if (this.errorIconView.getVisibility() == 0) {
            return this.errorIconView;
        }
        if (!hasEndIcon() || !isEndIconVisible()) {
            return null;
        }
        return this.endIconView;
    }

    private void applyIconTint(@NonNull CheckableImageButton iconView, boolean hasIconTintList, ColorStateList iconTintList, boolean hasIconTintMode, PorterDuff.Mode iconTintMode) {
        Drawable icon = iconView.getDrawable();
        if (icon != null && (hasIconTintList || hasIconTintMode)) {
            icon = DrawableCompat.wrap(icon).mutate();
            if (hasIconTintList) {
                DrawableCompat.setTintList(icon, iconTintList);
            }
            if (hasIconTintMode) {
                DrawableCompat.setTintMode(icon, iconTintMode);
            }
        }
        if (iconView.getDrawable() != icon) {
            iconView.setImageDrawable(icon);
        }
    }

    private static void setIconOnClickListener(@NonNull CheckableImageButton iconView, @Nullable View.OnClickListener onClickListener, @Nullable View.OnLongClickListener onLongClickListener) {
        iconView.setOnClickListener(onClickListener);
        setIconClickable(iconView, onLongClickListener);
    }

    private static void setIconOnLongClickListener(@NonNull CheckableImageButton iconView, @Nullable View.OnLongClickListener onLongClickListener) {
        iconView.setOnLongClickListener(onLongClickListener);
        setIconClickable(iconView, onLongClickListener);
    }

    private static void setIconClickable(@NonNull CheckableImageButton iconView, @Nullable View.OnLongClickListener onLongClickListener) {
        boolean iconClickable = ViewCompat.hasOnClickListeners(iconView);
        boolean iconFocusable = false;
        int i = 1;
        boolean iconLongClickable = onLongClickListener != null;
        if (iconClickable || iconLongClickable) {
            iconFocusable = true;
        }
        iconView.setFocusable(iconFocusable);
        iconView.setClickable(iconClickable);
        iconView.setPressable(iconClickable);
        iconView.setLongClickable(iconLongClickable);
        if (!iconFocusable) {
            i = 2;
        }
        ViewCompat.setImportantForAccessibility(iconView, i);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        EditText editText2 = this.editText;
        if (editText2 != null) {
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(this, editText2, rect);
            updateBoxUnderlineBounds(rect);
            if (this.hintEnabled) {
                this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
                int editTextGravity = this.editText.getGravity();
                this.collapsingTextHelper.setCollapsedTextGravity((editTextGravity & -113) | 48);
                this.collapsingTextHelper.setExpandedTextGravity(editTextGravity);
                this.collapsingTextHelper.setCollapsedBounds(calculateCollapsedTextBounds(rect));
                this.collapsingTextHelper.setExpandedBounds(calculateExpandedTextBounds(rect));
                this.collapsingTextHelper.recalculate();
                if (cutoutEnabled() && !this.hintExpanded) {
                    openCutout();
                }
            }
        }
    }

    private void updateBoxUnderlineBounds(@NonNull Rect bounds) {
        MaterialShapeDrawable materialShapeDrawable = this.boxUnderline;
        if (materialShapeDrawable != null) {
            int i = bounds.bottom;
            materialShapeDrawable.setBounds(bounds.left, i - this.boxStrokeWidthFocusedPx, bounds.right, i);
        }
    }

    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        drawHint(canvas);
        drawBoxUnderline(canvas);
    }

    private void drawHint(@NonNull Canvas canvas) {
        if (this.hintEnabled) {
            this.collapsingTextHelper.draw(canvas);
        }
    }

    private void drawBoxUnderline(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable = this.boxUnderline;
        if (materialShapeDrawable != null) {
            Rect underlineBounds = materialShapeDrawable.getBounds();
            underlineBounds.top = underlineBounds.bottom - this.boxStrokeWidthPx;
            this.boxUnderline.draw(canvas);
        }
    }

    private void collapseHint(boolean animate) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!animate || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(1.0f);
        } else {
            animateToExpansionFraction(1.0f);
        }
        this.hintExpanded = false;
        if (cutoutEnabled()) {
            openCutout();
        }
        updatePlaceholderText();
        updatePrefixTextVisibility();
        updateSuffixTextVisibility();
    }

    private boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    private void openCutout() {
        if (cutoutEnabled()) {
            RectF cutoutBounds = this.tmpRectF;
            this.collapsingTextHelper.getCollapsedTextActualBounds(cutoutBounds, this.editText.getWidth(), this.editText.getGravity());
            applyCutoutPadding(cutoutBounds);
            int i = this.boxStrokeWidthPx;
            this.boxLabelCutoutHeight = i;
            cutoutBounds.top = 0.0f;
            cutoutBounds.bottom = (float) i;
            cutoutBounds.offset((float) (-getPaddingLeft()), 0.0f);
            ((CutoutDrawable) this.boxBackground).setCutout(cutoutBounds);
        }
    }

    private void updateCutout() {
        if (cutoutEnabled() && !this.hintExpanded && this.boxLabelCutoutHeight != this.boxStrokeWidthPx) {
            closeCutout();
            openCutout();
        }
    }

    private void closeCutout() {
        if (cutoutEnabled()) {
            ((CutoutDrawable) this.boxBackground).removeCutout();
        }
    }

    private void applyCutoutPadding(@NonNull RectF cutoutBounds) {
        float f = cutoutBounds.left;
        int i = this.boxLabelCutoutPaddingPx;
        cutoutBounds.left = f - ((float) i);
        cutoutBounds.right += (float) i;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean cutoutIsOpen() {
        return cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.inDrawableStateChanged) {
            boolean z = true;
            this.inDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] state = getDrawableState();
            boolean changed = false;
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            if (collapsingTextHelper2 != null) {
                changed = false | collapsingTextHelper2.setState(state);
            }
            if (this.editText != null) {
                if (!ViewCompat.isLaidOut(this) || !isEnabled()) {
                    z = false;
                }
                updateLabelState(z);
            }
            updateEditTextBackground();
            updateTextInputBoxState();
            if (changed) {
                invalidate();
            }
            this.inDrawableStateChanged = false;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r6.editText;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateTextInputBoxState() {
        /*
            r6 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r6.boxBackground
            if (r0 == 0) goto L_0x00fa
            int r0 = r6.boxBackgroundMode
            if (r0 != 0) goto L_0x000a
            goto L_0x00fa
        L_0x000a:
            boolean r0 = r6.isFocused()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001f
            android.widget.EditText r0 = r6.editText
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.hasFocus()
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = r1
            goto L_0x0020
        L_0x001f:
            r0 = r2
        L_0x0020:
            boolean r3 = r6.isHovered()
            if (r3 != 0) goto L_0x0033
            android.widget.EditText r3 = r6.editText
            if (r3 == 0) goto L_0x0031
            boolean r3 = r3.isHovered()
            if (r3 == 0) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            r3 = r1
            goto L_0x0034
        L_0x0033:
            r3 = r2
        L_0x0034:
            boolean r4 = r6.isEnabled()
            if (r4 != 0) goto L_0x003f
            int r4 = r6.disabledColor
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x003f:
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x0058
            android.content.res.ColorStateList r4 = r6.strokeErrorColor
            if (r4 == 0) goto L_0x004f
            r6.updateStrokeErrorColor(r0, r3)
            goto L_0x0081
        L_0x004f:
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            int r4 = r4.getErrorViewCurrentTextColor()
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x0058:
            boolean r4 = r6.counterOverflowed
            if (r4 == 0) goto L_0x006f
            android.widget.TextView r4 = r6.counterView
            if (r4 == 0) goto L_0x006f
            android.content.res.ColorStateList r5 = r6.strokeErrorColor
            if (r5 == 0) goto L_0x0068
            r6.updateStrokeErrorColor(r0, r3)
            goto L_0x0081
        L_0x0068:
            int r4 = r4.getCurrentTextColor()
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x006f:
            if (r0 == 0) goto L_0x0076
            int r4 = r6.focusedStrokeColor
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x0076:
            if (r3 == 0) goto L_0x007d
            int r4 = r6.hoveredStrokeColor
            r6.boxStrokeColor = r4
            goto L_0x0081
        L_0x007d:
            int r4 = r6.defaultStrokeColor
            r6.boxStrokeColor = r4
        L_0x0081:
            android.graphics.drawable.Drawable r4 = r6.getErrorIconDrawable()
            if (r4 == 0) goto L_0x009a
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            boolean r4 = r4.isErrorEnabled()
            if (r4 == 0) goto L_0x009a
            com.google.android.material.textfield.IndicatorViewController r4 = r6.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x009a
            r1 = r2
            goto L_0x009b
        L_0x009a:
        L_0x009b:
            r6.setErrorIconVisible(r1)
            r6.refreshErrorIconDrawableState()
            r6.refreshStartIconDrawableState()
            r6.refreshEndIconDrawableState()
            com.google.android.material.textfield.EndIconDelegate r1 = r6.getEndIconDelegate()
            boolean r1 = r1.shouldTintIconOnError()
            if (r1 == 0) goto L_0x00ba
            com.google.android.material.textfield.IndicatorViewController r1 = r6.indicatorViewController
            boolean r1 = r1.errorShouldBeShown()
            r6.tintEndIconOnError(r1)
        L_0x00ba:
            if (r0 == 0) goto L_0x00c7
            boolean r1 = r6.isEnabled()
            if (r1 == 0) goto L_0x00c7
            int r1 = r6.boxStrokeWidthFocusedPx
            r6.boxStrokeWidthPx = r1
            goto L_0x00cb
        L_0x00c7:
            int r1 = r6.boxStrokeWidthDefaultPx
            r6.boxStrokeWidthPx = r1
        L_0x00cb:
            int r1 = r6.boxBackgroundMode
            r4 = 2
            if (r1 != r4) goto L_0x00d3
            r6.updateCutout()
        L_0x00d3:
            int r1 = r6.boxBackgroundMode
            if (r1 != r2) goto L_0x00f6
            boolean r1 = r6.isEnabled()
            if (r1 != 0) goto L_0x00e2
            int r1 = r6.disabledFilledBackgroundColor
            r6.boxBackgroundColor = r1
            goto L_0x00f6
        L_0x00e2:
            if (r3 == 0) goto L_0x00eb
            if (r0 != 0) goto L_0x00eb
            int r1 = r6.hoveredFilledBackgroundColor
            r6.boxBackgroundColor = r1
            goto L_0x00f6
        L_0x00eb:
            if (r0 == 0) goto L_0x00f2
            int r1 = r6.focusedFilledBackgroundColor
            r6.boxBackgroundColor = r1
            goto L_0x00f6
        L_0x00f2:
            int r1 = r6.defaultFilledBackgroundColor
            r6.boxBackgroundColor = r1
        L_0x00f6:
            r6.applyBoxAttributes()
            return
        L_0x00fa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateTextInputBoxState():void");
    }

    private void updateStrokeErrorColor(boolean hasFocus, boolean isHovered) {
        int defaultStrokeErrorColor = this.strokeErrorColor.getDefaultColor();
        int hoveredStrokeErrorColor = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, defaultStrokeErrorColor);
        int focusedStrokeErrorColor = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, defaultStrokeErrorColor);
        if (hasFocus) {
            this.boxStrokeColor = focusedStrokeErrorColor;
        } else if (isHovered) {
            this.boxStrokeColor = hoveredStrokeErrorColor;
        } else {
            this.boxStrokeColor = defaultStrokeErrorColor;
        }
    }

    private void setErrorIconVisible(boolean errorIconVisible) {
        int i = 0;
        this.errorIconView.setVisibility(errorIconVisible ? 0 : 8);
        FrameLayout frameLayout = this.endIconFrame;
        if (errorIconVisible) {
            i = 8;
        }
        frameLayout.setVisibility(i);
        updateSuffixTextViewPadding();
        if (!hasEndIcon()) {
            updateDummyDrawables();
        }
    }

    private boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    private void refreshIconDrawableState(CheckableImageButton iconView, ColorStateList colorStateList) {
        Drawable icon = iconView.getDrawable();
        if (iconView.getDrawable() != null && colorStateList != null && colorStateList.isStateful()) {
            int color = colorStateList.getColorForState(mergeIconState(iconView), colorStateList.getDefaultColor());
            Drawable icon2 = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon2, ColorStateList.valueOf(color));
            iconView.setImageDrawable(icon2);
        }
    }

    private int[] mergeIconState(CheckableImageButton iconView) {
        int[] textInputStates = getDrawableState();
        int[] iconStates = iconView.getDrawableState();
        int index = textInputStates.length;
        int[] states = Arrays.copyOf(textInputStates, textInputStates.length + iconStates.length);
        System.arraycopy(iconStates, 0, states, index, iconStates.length);
        return states;
    }

    private void expandHint(boolean animate) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!animate || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(0.0f);
        } else {
            animateToExpansionFraction(0.0f);
        }
        if (cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout()) {
            closeCutout();
        }
        this.hintExpanded = true;
        hidePlaceholderText();
        updatePrefixTextVisibility();
        updateSuffixTextVisibility();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void animateToExpansionFraction(float target) {
        if (this.collapsingTextHelper.getExpansionFraction() != target) {
            if (this.animator == null) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.animator = valueAnimator;
                valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                this.animator.setDuration(167);
                this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                        TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) animator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.animator.setFloatValues(new float[]{this.collapsingTextHelper.getExpansionFraction(), target});
            this.animator.start();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final boolean isHintExpanded() {
        return this.hintExpanded;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final boolean isHelperTextDisplayed() {
        return this.indicatorViewController.helperTextIsDisplayed();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final int getHintCurrentCollapsedTextColor() {
        return this.collapsingTextHelper.getCurrentCollapsedTextColor();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final int getErrorTextCurrentColor() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final TextInputLayout layout;

        public AccessibilityDelegate(@NonNull TextInputLayout layout2) {
            this.layout = layout2;
        }

        public void onInitializeAccessibilityNodeInfo(@NonNull View host, @NonNull AccessibilityNodeInfoCompat info) {
            String hint;
            String text;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            EditText editText = this.layout.getEditText();
            CharSequence inputText = editText != null ? editText.getText() : null;
            CharSequence hintText = this.layout.getHint();
            CharSequence errorText = this.layout.getError();
            CharSequence placeholderText = this.layout.getPlaceholderText();
            int maxCharLimit = this.layout.getCounterMaxLength();
            CharSequence counterOverflowDesc = this.layout.getCounterOverflowDescription();
            boolean showingText = !TextUtils.isEmpty(inputText);
            boolean hasHint = !TextUtils.isEmpty(hintText);
            boolean isHintCollapsed = !this.layout.isHintExpanded();
            boolean showingError = !TextUtils.isEmpty(errorText);
            boolean contentInvalid = showingError || !TextUtils.isEmpty(counterOverflowDesc);
            String hint2 = hasHint ? hintText.toString() : "";
            if (showingText) {
                accessibilityNodeInfoCompat.setText(inputText);
                hint = hint2;
            } else if (!TextUtils.isEmpty(hint2)) {
                hint = hint2;
                accessibilityNodeInfoCompat.setText(hint);
                if (isHintCollapsed && placeholderText != null) {
                    accessibilityNodeInfoCompat.setText(hint + ", " + placeholderText);
                }
            } else {
                hint = hint2;
                if (placeholderText != null) {
                    accessibilityNodeInfoCompat.setText(placeholderText);
                }
            }
            if (!TextUtils.isEmpty(hint)) {
                CharSequence charSequence = hintText;
                if (Build.VERSION.SDK_INT >= 26) {
                    accessibilityNodeInfoCompat.setHintText(hint);
                } else {
                    if (showingText) {
                        text = inputText + ", " + hint;
                    } else {
                        text = hint;
                    }
                    accessibilityNodeInfoCompat.setText(text);
                }
                accessibilityNodeInfoCompat.setShowingHintText(!showingText);
            }
            accessibilityNodeInfoCompat.setMaxTextLength((inputText == null || inputText.length() != maxCharLimit) ? -1 : maxCharLimit);
            if (contentInvalid) {
                accessibilityNodeInfoCompat.setError(showingError ? errorText : counterOverflowDesc);
            }
            if (Build.VERSION.SDK_INT >= 17 && editText != null) {
                editText.setLabelFor(R.id.textinput_helper_text);
            }
        }
    }
}
