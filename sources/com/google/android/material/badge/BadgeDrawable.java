package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.annotation.XmlRes;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;

public class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    private static final int BADGE_NUMBER_NONE = -1;
    public static final int BOTTOM_END = 8388693;
    public static final int BOTTOM_START = 8388691;
    static final String DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX = "+";
    private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
    @StyleRes
    private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Badge;
    @AttrRes
    private static final int DEFAULT_THEME_ATTR = R.attr.badgeStyle;
    private static final int MAX_CIRCULAR_BADGE_NUMBER_COUNT = 9;
    public static final int TOP_END = 8388661;
    public static final int TOP_START = 8388659;
    @Nullable
    private WeakReference<View> anchorViewRef;
    @NonNull
    private final Rect badgeBounds = new Rect();
    private float badgeCenterX;
    private float badgeCenterY;
    private final float badgeRadius;
    private final float badgeWidePadding;
    private final float badgeWithTextRadius;
    @NonNull
    private final WeakReference<Context> contextRef;
    private float cornerRadius;
    @Nullable
    private WeakReference<FrameLayout> customBadgeParentRef;
    private float halfBadgeHeight;
    private float halfBadgeWidth;
    private int maxBadgeNumber;
    @NonNull
    private final SavedState savedState;
    @NonNull
    private final MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable();
    @NonNull
    private final TextDrawableHelper textDrawableHelper;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BadgeGravity {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in);
            }

            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        /* access modifiers changed from: private */
        @Dimension(unit = 1)
        public int additionalHorizontalOffset;
        /* access modifiers changed from: private */
        @Dimension(unit = 1)
        public int additionalVerticalOffset;
        /* access modifiers changed from: private */
        public int alpha = 255;
        /* access modifiers changed from: private */
        @ColorInt
        public int backgroundColor;
        /* access modifiers changed from: private */
        public int badgeGravity;
        /* access modifiers changed from: private */
        @ColorInt
        public int badgeTextColor;
        /* access modifiers changed from: private */
        @StringRes
        public int contentDescriptionExceedsMaxBadgeNumberRes;
        /* access modifiers changed from: private */
        @Nullable
        public CharSequence contentDescriptionNumberless;
        /* access modifiers changed from: private */
        @PluralsRes
        public int contentDescriptionQuantityStrings;
        /* access modifiers changed from: private */
        @Dimension(unit = 1)
        public int horizontalOffset;
        /* access modifiers changed from: private */
        public boolean isVisible;
        /* access modifiers changed from: private */
        public int maxCharacterCount;
        /* access modifiers changed from: private */
        public int number = -1;
        /* access modifiers changed from: private */
        @Dimension(unit = 1)
        public int verticalOffset;

        public SavedState(@NonNull Context context) {
            this.badgeTextColor = new TextAppearance(context, R.style.TextAppearance_MaterialComponents_Badge).textColor.getDefaultColor();
            this.contentDescriptionNumberless = context.getString(R.string.mtrl_badge_numberless_content_description);
            this.contentDescriptionQuantityStrings = R.plurals.mtrl_badge_content_description;
            this.contentDescriptionExceedsMaxBadgeNumberRes = R.string.mtrl_exceed_max_badge_number_content_description;
            this.isVisible = true;
        }

        protected SavedState(@NonNull Parcel in) {
            this.backgroundColor = in.readInt();
            this.badgeTextColor = in.readInt();
            this.alpha = in.readInt();
            this.number = in.readInt();
            this.maxCharacterCount = in.readInt();
            this.contentDescriptionNumberless = in.readString();
            this.contentDescriptionQuantityStrings = in.readInt();
            this.badgeGravity = in.readInt();
            this.horizontalOffset = in.readInt();
            this.verticalOffset = in.readInt();
            this.additionalHorizontalOffset = in.readInt();
            this.additionalVerticalOffset = in.readInt();
            this.isVisible = in.readInt() != 0;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(this.backgroundColor);
            dest.writeInt(this.badgeTextColor);
            dest.writeInt(this.alpha);
            dest.writeInt(this.number);
            dest.writeInt(this.maxCharacterCount);
            dest.writeString(this.contentDescriptionNumberless.toString());
            dest.writeInt(this.contentDescriptionQuantityStrings);
            dest.writeInt(this.badgeGravity);
            dest.writeInt(this.horizontalOffset);
            dest.writeInt(this.verticalOffset);
            dest.writeInt(this.additionalHorizontalOffset);
            dest.writeInt(this.additionalVerticalOffset);
            dest.writeInt(this.isVisible ? 1 : 0);
        }
    }

    @NonNull
    public SavedState getSavedState() {
        return this.savedState;
    }

    @NonNull
    static BadgeDrawable createFromSavedState(@NonNull Context context, @NonNull SavedState savedState2) {
        BadgeDrawable badge = new BadgeDrawable(context);
        badge.restoreFromSavedState(savedState2);
        return badge;
    }

    @NonNull
    public static BadgeDrawable create(@NonNull Context context) {
        return createFromAttributes(context, (AttributeSet) null, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    @NonNull
    public static BadgeDrawable createFromResource(@NonNull Context context, @XmlRes int id) {
        AttributeSet attrs = DrawableUtils.parseDrawableXml(context, id, "badge");
        int style = attrs.getStyleAttribute();
        if (style == 0) {
            style = DEFAULT_STYLE;
        }
        return createFromAttributes(context, attrs, DEFAULT_THEME_ATTR, style);
    }

    @NonNull
    private static BadgeDrawable createFromAttributes(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        BadgeDrawable badge = new BadgeDrawable(context);
        badge.loadDefaultStateFromAttributes(context, attrs, defStyleAttr, defStyleRes);
        return badge;
    }

    public void setVisible(boolean visible) {
        setVisible(visible, false);
        boolean unused = this.savedState.isVisible = visible;
        if (BadgeUtils.USE_COMPAT_PARENT && getCustomBadgeParent() != null && !visible) {
            ((ViewGroup) getCustomBadgeParent().getParent()).invalidate();
        }
    }

    private void restoreFromSavedState(@NonNull SavedState savedState2) {
        setMaxCharacterCount(savedState2.maxCharacterCount);
        if (savedState2.number != -1) {
            setNumber(savedState2.number);
        }
        setBackgroundColor(savedState2.backgroundColor);
        setBadgeTextColor(savedState2.badgeTextColor);
        setBadgeGravity(savedState2.badgeGravity);
        setHorizontalOffset(savedState2.horizontalOffset);
        setVerticalOffset(savedState2.verticalOffset);
        setAdditionalHorizontalOffset(savedState2.additionalHorizontalOffset);
        setAdditionalVerticalOffset(savedState2.additionalVerticalOffset);
        setVisible(savedState2.isVisible);
    }

    private void loadDefaultStateFromAttributes(Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.Badge, defStyleAttr, defStyleRes, new int[0]);
        setMaxCharacterCount(a.getInt(R.styleable.Badge_maxCharacterCount, 4));
        int i = R.styleable.Badge_number;
        if (a.hasValue(i)) {
            setNumber(a.getInt(i, 0));
        }
        setBackgroundColor(readColorFromAttributes(context, a, R.styleable.Badge_backgroundColor));
        int i2 = R.styleable.Badge_badgeTextColor;
        if (a.hasValue(i2)) {
            setBadgeTextColor(readColorFromAttributes(context, a, i2));
        }
        setBadgeGravity(a.getInt(R.styleable.Badge_badgeGravity, TOP_END));
        setHorizontalOffset(a.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0));
        setVerticalOffset(a.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0));
        a.recycle();
    }

    private static int readColorFromAttributes(Context context, @NonNull TypedArray a, @StyleableRes int index) {
        return MaterialResources.getColorStateList(context, a, index).getDefaultColor();
    }

    private BadgeDrawable(@NonNull Context context) {
        this.contextRef = new WeakReference<>(context);
        ThemeEnforcement.checkMaterialTheme(context);
        Resources res = context.getResources();
        this.badgeRadius = (float) res.getDimensionPixelSize(R.dimen.mtrl_badge_radius);
        this.badgeWidePadding = (float) res.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding);
        this.badgeWithTextRadius = (float) res.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius);
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        textDrawableHelper2.getTextPaint().setTextAlign(Paint.Align.CENTER);
        this.savedState = new SavedState(context);
        setTextAppearanceResource(R.style.TextAppearance_MaterialComponents_Badge);
    }

    @Deprecated
    public void updateBadgeCoordinates(@NonNull View anchorView, @Nullable ViewGroup customBadgeParent) {
        if (customBadgeParent instanceof FrameLayout) {
            updateBadgeCoordinates(anchorView, (FrameLayout) customBadgeParent);
            return;
        }
        throw new IllegalArgumentException("customBadgeParent must be a FrameLayout");
    }

    public void updateBadgeCoordinates(@NonNull View anchorView) {
        updateBadgeCoordinates(anchorView, (FrameLayout) null);
    }

    public void updateBadgeCoordinates(@NonNull View anchorView, @Nullable FrameLayout customBadgeParent) {
        this.anchorViewRef = new WeakReference<>(anchorView);
        boolean z = BadgeUtils.USE_COMPAT_PARENT;
        if (!z || customBadgeParent != null) {
            this.customBadgeParentRef = new WeakReference<>(customBadgeParent);
        } else {
            tryWrapAnchorInCompatParent(anchorView);
        }
        if (!z) {
            updateAnchorParentToNotClip(anchorView);
        }
        updateCenterAndBounds();
        invalidateSelf();
    }

    @Nullable
    public FrameLayout getCustomBadgeParent() {
        WeakReference<FrameLayout> weakReference = this.customBadgeParentRef;
        if (weakReference != null) {
            return (FrameLayout) weakReference.get();
        }
        return null;
    }

    private void tryWrapAnchorInCompatParent(final View anchorView) {
        ViewGroup anchorViewParent = (ViewGroup) anchorView.getParent();
        if (anchorViewParent == null || anchorViewParent.getId() != R.id.mtrl_anchor_parent) {
            WeakReference<FrameLayout> weakReference = this.customBadgeParentRef;
            if (weakReference == null || weakReference.get() != anchorViewParent) {
                updateAnchorParentToNotClip(anchorView);
                final FrameLayout frameLayout = new FrameLayout(anchorView.getContext());
                frameLayout.setId(R.id.mtrl_anchor_parent);
                frameLayout.setClipChildren(false);
                frameLayout.setClipToPadding(false);
                frameLayout.setLayoutParams(anchorView.getLayoutParams());
                frameLayout.setMinimumWidth(anchorView.getWidth());
                frameLayout.setMinimumHeight(anchorView.getHeight());
                int anchorIndex = anchorViewParent.indexOfChild(anchorView);
                anchorViewParent.removeViewAt(anchorIndex);
                anchorView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                frameLayout.addView(anchorView);
                anchorViewParent.addView(frameLayout, anchorIndex);
                this.customBadgeParentRef = new WeakReference<>(frameLayout);
                frameLayout.post(new Runnable() {
                    public void run() {
                        BadgeDrawable.this.updateBadgeCoordinates(anchorView, frameLayout);
                    }
                });
            }
        }
    }

    private static void updateAnchorParentToNotClip(View anchorView) {
        ViewGroup anchorViewParent = (ViewGroup) anchorView.getParent();
        anchorViewParent.setClipChildren(false);
        anchorViewParent.setClipToPadding(false);
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.shapeDrawable.getFillColor().getDefaultColor();
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        int unused = this.savedState.backgroundColor = backgroundColor;
        ColorStateList backgroundColorStateList = ColorStateList.valueOf(backgroundColor);
        if (this.shapeDrawable.getFillColor() != backgroundColorStateList) {
            this.shapeDrawable.setFillColor(backgroundColorStateList);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getBadgeTextColor() {
        return this.textDrawableHelper.getTextPaint().getColor();
    }

    public void setBadgeTextColor(@ColorInt int badgeTextColor) {
        int unused = this.savedState.badgeTextColor = badgeTextColor;
        if (this.textDrawableHelper.getTextPaint().getColor() != badgeTextColor) {
            this.textDrawableHelper.getTextPaint().setColor(badgeTextColor);
            invalidateSelf();
        }
    }

    public boolean hasNumber() {
        return this.savedState.number != -1;
    }

    public int getNumber() {
        if (!hasNumber()) {
            return 0;
        }
        return this.savedState.number;
    }

    public void setNumber(int number) {
        int number2 = Math.max(0, number);
        if (this.savedState.number != number2) {
            int unused = this.savedState.number = number2;
            this.textDrawableHelper.setTextWidthDirty(true);
            updateCenterAndBounds();
            invalidateSelf();
        }
    }

    public void clearNumber() {
        int unused = this.savedState.number = -1;
        invalidateSelf();
    }

    public int getMaxCharacterCount() {
        return this.savedState.maxCharacterCount;
    }

    public void setMaxCharacterCount(int maxCharacterCount) {
        if (this.savedState.maxCharacterCount != maxCharacterCount) {
            int unused = this.savedState.maxCharacterCount = maxCharacterCount;
            updateMaxBadgeNumber();
            this.textDrawableHelper.setTextWidthDirty(true);
            updateCenterAndBounds();
            invalidateSelf();
        }
    }

    public int getBadgeGravity() {
        return this.savedState.badgeGravity;
    }

    public void setBadgeGravity(int gravity) {
        if (this.savedState.badgeGravity != gravity) {
            int unused = this.savedState.badgeGravity = gravity;
            WeakReference<View> weakReference = this.anchorViewRef;
            if (weakReference != null && weakReference.get() != null) {
                View view = (View) this.anchorViewRef.get();
                WeakReference<FrameLayout> weakReference2 = this.customBadgeParentRef;
                updateBadgeCoordinates(view, weakReference2 != null ? (FrameLayout) weakReference2.get() : null);
            }
        }
    }

    public boolean isStateful() {
        return false;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getAlpha() {
        return this.savedState.alpha;
    }

    public void setAlpha(int alpha) {
        int unused = this.savedState.alpha = alpha;
        this.textDrawableHelper.getTextPaint().setAlpha(alpha);
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    public int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    public int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    public void draw(@NonNull Canvas canvas) {
        if (!getBounds().isEmpty() && getAlpha() != 0 && isVisible()) {
            this.shapeDrawable.draw(canvas);
            if (hasNumber()) {
                drawText(canvas);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onTextSizeChange() {
        invalidateSelf();
    }

    public boolean onStateChange(int[] state) {
        return super.onStateChange(state);
    }

    public void setContentDescriptionNumberless(CharSequence charSequence) {
        CharSequence unused = this.savedState.contentDescriptionNumberless = charSequence;
    }

    public void setContentDescriptionQuantityStringsResource(@PluralsRes int stringsResource) {
        int unused = this.savedState.contentDescriptionQuantityStrings = stringsResource;
    }

    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(@StringRes int stringsResource) {
        int unused = this.savedState.contentDescriptionExceedsMaxBadgeNumberRes = stringsResource;
    }

    @Nullable
    public CharSequence getContentDescription() {
        Context context;
        if (!isVisible()) {
            return null;
        }
        if (!hasNumber()) {
            return this.savedState.contentDescriptionNumberless;
        }
        if (this.savedState.contentDescriptionQuantityStrings <= 0 || (context = (Context) this.contextRef.get()) == null) {
            return null;
        }
        if (getNumber() <= this.maxBadgeNumber) {
            return context.getResources().getQuantityString(this.savedState.contentDescriptionQuantityStrings, getNumber(), new Object[]{Integer.valueOf(getNumber())});
        }
        return context.getString(this.savedState.contentDescriptionExceedsMaxBadgeNumberRes, new Object[]{Integer.valueOf(this.maxBadgeNumber)});
    }

    public void setHorizontalOffset(int px) {
        int unused = this.savedState.horizontalOffset = px;
        updateCenterAndBounds();
    }

    public int getHorizontalOffset() {
        return this.savedState.horizontalOffset;
    }

    /* access modifiers changed from: package-private */
    public void setAdditionalHorizontalOffset(int px) {
        int unused = this.savedState.additionalHorizontalOffset = px;
        updateCenterAndBounds();
    }

    /* access modifiers changed from: package-private */
    public int getAdditionalHorizontalOffset() {
        return this.savedState.additionalHorizontalOffset;
    }

    public void setVerticalOffset(int px) {
        int unused = this.savedState.verticalOffset = px;
        updateCenterAndBounds();
    }

    public int getVerticalOffset() {
        return this.savedState.verticalOffset;
    }

    /* access modifiers changed from: package-private */
    public void setAdditionalVerticalOffset(int px) {
        int unused = this.savedState.additionalVerticalOffset = px;
        updateCenterAndBounds();
    }

    /* access modifiers changed from: package-private */
    public int getAdditionalVerticalOffset() {
        return this.savedState.additionalVerticalOffset;
    }

    private void setTextAppearanceResource(@StyleRes int id) {
        Context context = (Context) this.contextRef.get();
        if (context != null) {
            setTextAppearance(new TextAppearance(context, id));
        }
    }

    private void setTextAppearance(@Nullable TextAppearance textAppearance) {
        Context context;
        if (this.textDrawableHelper.getTextAppearance() != textAppearance && (context = (Context) this.contextRef.get()) != null) {
            this.textDrawableHelper.setTextAppearance(textAppearance, context);
            updateCenterAndBounds();
        }
    }

    private void updateCenterAndBounds() {
        Context context = (Context) this.contextRef.get();
        WeakReference<View> weakReference = this.anchorViewRef;
        ViewGroup customBadgeParent = null;
        View anchorView = weakReference != null ? (View) weakReference.get() : null;
        if (context != null && anchorView != null) {
            Rect tmpRect = new Rect();
            tmpRect.set(this.badgeBounds);
            Rect anchorRect = new Rect();
            anchorView.getDrawingRect(anchorRect);
            WeakReference<FrameLayout> weakReference2 = this.customBadgeParentRef;
            if (weakReference2 != null) {
                customBadgeParent = (FrameLayout) weakReference2.get();
            }
            if (customBadgeParent != null || BadgeUtils.USE_COMPAT_PARENT) {
                (customBadgeParent == null ? (ViewGroup) anchorView.getParent() : customBadgeParent).offsetDescendantRectToMyCoords(anchorView, anchorRect);
            }
            calculateCenterAndBounds(context, anchorRect, anchorView);
            BadgeUtils.updateBadgeBounds(this.badgeBounds, this.badgeCenterX, this.badgeCenterY, this.halfBadgeWidth, this.halfBadgeHeight);
            this.shapeDrawable.setCornerSize(this.cornerRadius);
            if (!tmpRect.equals(this.badgeBounds)) {
                this.shapeDrawable.setBounds(this.badgeBounds);
            }
        }
    }

    private void calculateCenterAndBounds(@NonNull Context context, @NonNull Rect anchorRect, @NonNull View anchorView) {
        float f;
        int totalVerticalOffset = this.savedState.verticalOffset + this.savedState.additionalVerticalOffset;
        switch (this.savedState.badgeGravity) {
            case BOTTOM_START /*8388691*/:
            case BOTTOM_END /*8388693*/:
                this.badgeCenterY = (float) (anchorRect.bottom - totalVerticalOffset);
                break;
            default:
                this.badgeCenterY = (float) (anchorRect.top + totalVerticalOffset);
                break;
        }
        if (getNumber() <= 9) {
            float f2 = !hasNumber() ? this.badgeRadius : this.badgeWithTextRadius;
            this.cornerRadius = f2;
            this.halfBadgeHeight = f2;
            this.halfBadgeWidth = f2;
        } else {
            float f3 = this.badgeWithTextRadius;
            this.cornerRadius = f3;
            this.halfBadgeHeight = f3;
            this.halfBadgeWidth = (this.textDrawableHelper.getTextWidth(getBadgeText()) / 2.0f) + this.badgeWidePadding;
        }
        int inset = context.getResources().getDimensionPixelSize(hasNumber() ? R.dimen.mtrl_badge_text_horizontal_edge_offset : R.dimen.mtrl_badge_horizontal_edge_offset);
        int totalHorizontalOffset = this.savedState.horizontalOffset + this.savedState.additionalHorizontalOffset;
        switch (this.savedState.badgeGravity) {
            case TOP_START /*8388659*/:
            case BOTTOM_START /*8388691*/:
                this.badgeCenterX = ViewCompat.getLayoutDirection(anchorView) == 0 ? (((float) anchorRect.left) - this.halfBadgeWidth) + ((float) inset) + ((float) totalHorizontalOffset) : ((((float) anchorRect.right) + this.halfBadgeWidth) - ((float) inset)) - ((float) totalHorizontalOffset);
                return;
            default:
                if (ViewCompat.getLayoutDirection(anchorView) == 0) {
                    f = ((((float) anchorRect.right) + this.halfBadgeWidth) - ((float) inset)) - ((float) totalHorizontalOffset);
                } else {
                    f = (((float) anchorRect.left) - this.halfBadgeWidth) + ((float) inset) + ((float) totalHorizontalOffset);
                }
                this.badgeCenterX = f;
                return;
        }
    }

    private void drawText(Canvas canvas) {
        Rect textBounds = new Rect();
        String badgeText = getBadgeText();
        this.textDrawableHelper.getTextPaint().getTextBounds(badgeText, 0, badgeText.length(), textBounds);
        canvas.drawText(badgeText, this.badgeCenterX, this.badgeCenterY + ((float) (textBounds.height() / 2)), this.textDrawableHelper.getTextPaint());
    }

    @NonNull
    private String getBadgeText() {
        if (getNumber() <= this.maxBadgeNumber) {
            return NumberFormat.getInstance().format((long) getNumber());
        }
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return "";
        }
        return context.getString(R.string.mtrl_exceed_max_badge_number_suffix, new Object[]{Integer.valueOf(this.maxBadgeNumber), "+"});
    }

    private void updateMaxBadgeNumber() {
        this.maxBadgeNumber = ((int) Math.pow(10.0d, ((double) getMaxCharacterCount()) - 1.0d)) - 1;
    }
}
