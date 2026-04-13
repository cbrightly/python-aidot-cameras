package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int INVALID_ITEM_POSITION = -1;
    @Nullable
    private BadgeDrawable badgeDrawable;
    private final int defaultMargin;
    /* access modifiers changed from: private */
    public ImageView icon;
    @Nullable
    private ColorStateList iconTint;
    private boolean isShifting;
    @Nullable
    private MenuItemImpl itemData;
    private int itemPosition = -1;
    private final ViewGroup labelGroup;
    private int labelVisibilityMode;
    private final TextView largeLabel;
    @Nullable
    private Drawable originalIconDrawable;
    private float scaleDownFactor;
    private float scaleUpFactor;
    private float shiftAmount;
    private final TextView smallLabel;
    @Nullable
    private Drawable wrappedIconDrawable;

    /* access modifiers changed from: protected */
    @LayoutRes
    public abstract int getItemLayoutResId();

    public NavigationBarItemView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(getItemLayoutResId(), this, true);
        this.icon = (ImageView) findViewById(R.id.navigation_bar_item_icon_view);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.navigation_bar_item_labels_group);
        this.labelGroup = viewGroup;
        TextView textView = (TextView) findViewById(R.id.navigation_bar_item_small_label_view);
        this.smallLabel = textView;
        TextView textView2 = (TextView) findViewById(R.id.navigation_bar_item_large_label_view);
        this.largeLabel = textView2;
        setBackgroundResource(getItemBackgroundResId());
        this.defaultMargin = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        viewGroup.setTag(R.id.mtrl_view_tag_bottom_padding, Integer.valueOf(viewGroup.getPaddingBottom()));
        ViewCompat.setImportantForAccessibility(textView, 2);
        ViewCompat.setImportantForAccessibility(textView2, 2);
        setFocusable(true);
        calculateTextScaleFactors(textView.getTextSize(), textView2.getTextSize());
        ImageView imageView = this.icon;
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (NavigationBarItemView.this.icon.getVisibility() == 0) {
                        NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                        navigationBarItemView.tryUpdateBadgeBounds(navigationBarItemView.icon);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams labelGroupParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        return Math.max(getSuggestedIconWidth(), labelGroupParams.leftMargin + this.labelGroup.getMeasuredWidth() + labelGroupParams.rightMargin);
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams labelGroupParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        return getSuggestedIconHeight() + labelGroupParams.topMargin + this.labelGroup.getMeasuredHeight() + labelGroupParams.bottomMargin;
    }

    public void initialize(@NonNull MenuItemImpl itemData2, int menuType) {
        CharSequence tooltipText;
        this.itemData = itemData2;
        setCheckable(itemData2.isCheckable());
        setChecked(itemData2.isChecked());
        setEnabled(itemData2.isEnabled());
        setIcon(itemData2.getIcon());
        setTitle(itemData2.getTitle());
        setId(itemData2.getItemId());
        if (!TextUtils.isEmpty(itemData2.getContentDescription())) {
            setContentDescription(itemData2.getContentDescription());
        }
        if (!TextUtils.isEmpty(itemData2.getTooltipText())) {
            tooltipText = itemData2.getTooltipText();
        } else {
            tooltipText = itemData2.getTitle();
        }
        int i = Build.VERSION.SDK_INT;
        if (i < 21 || i > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
        setVisibility(itemData2.isVisible() ? 0 : 8);
    }

    public void setItemPosition(int position) {
        this.itemPosition = position;
    }

    public int getItemPosition() {
        return this.itemPosition;
    }

    public void setShifting(boolean shifting) {
        if (this.isShifting != shifting) {
            this.isShifting = shifting;
            MenuItemImpl menuItemImpl = this.itemData;
            if (menuItemImpl != null) {
                setChecked(menuItemImpl.isChecked());
            }
        }
    }

    public void setLabelVisibilityMode(int mode) {
        if (this.labelVisibilityMode != mode) {
            this.labelVisibilityMode = mode;
            MenuItemImpl menuItemImpl = this.itemData;
            if (menuItemImpl != null) {
                setChecked(menuItemImpl.isChecked());
            }
        }
    }

    @Nullable
    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    public void setTitle(@Nullable CharSequence title) {
        CharSequence tooltipText;
        this.smallLabel.setText(title);
        this.largeLabel.setText(title);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl == null || TextUtils.isEmpty(menuItemImpl.getContentDescription())) {
            setContentDescription(title);
        }
        MenuItemImpl menuItemImpl2 = this.itemData;
        if (menuItemImpl2 == null || TextUtils.isEmpty(menuItemImpl2.getTooltipText())) {
            tooltipText = title;
        } else {
            tooltipText = this.itemData.getTooltipText();
        }
        int i = Build.VERSION.SDK_INT;
        if (i < 21 || i > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
    }

    public void setCheckable(boolean checkable) {
        refreshDrawableState();
    }

    public void setChecked(boolean checked) {
        TextView textView = this.largeLabel;
        textView.setPivotX((float) (textView.getWidth() / 2));
        TextView textView2 = this.largeLabel;
        textView2.setPivotY((float) textView2.getBaseline());
        TextView textView3 = this.smallLabel;
        textView3.setPivotX((float) (textView3.getWidth() / 2));
        TextView textView4 = this.smallLabel;
        textView4.setPivotY((float) textView4.getBaseline());
        switch (this.labelVisibilityMode) {
            case -1:
                if (!this.isShifting) {
                    ViewGroup viewGroup = this.labelGroup;
                    updateViewPaddingBottom(viewGroup, ((Integer) viewGroup.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                    if (!checked) {
                        setViewLayoutParams(this.icon, this.defaultMargin, 49);
                        TextView textView5 = this.largeLabel;
                        float f = this.scaleDownFactor;
                        setViewScaleValues(textView5, f, f, 4);
                        setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                        break;
                    } else {
                        setViewLayoutParams(this.icon, (int) (((float) this.defaultMargin) + this.shiftAmount), 49);
                        setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                        TextView textView6 = this.smallLabel;
                        float f2 = this.scaleUpFactor;
                        setViewScaleValues(textView6, f2, f2, 4);
                        break;
                    }
                } else {
                    if (checked) {
                        setViewLayoutParams(this.icon, this.defaultMargin, 49);
                        ViewGroup viewGroup2 = this.labelGroup;
                        updateViewPaddingBottom(viewGroup2, ((Integer) viewGroup2.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                        this.largeLabel.setVisibility(0);
                    } else {
                        setViewLayoutParams(this.icon, this.defaultMargin, 17);
                        updateViewPaddingBottom(this.labelGroup, 0);
                        this.largeLabel.setVisibility(4);
                    }
                    this.smallLabel.setVisibility(4);
                    break;
                }
            case 0:
                if (checked) {
                    setViewLayoutParams(this.icon, this.defaultMargin, 49);
                    ViewGroup viewGroup3 = this.labelGroup;
                    updateViewPaddingBottom(viewGroup3, ((Integer) viewGroup3.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                    this.largeLabel.setVisibility(0);
                } else {
                    setViewLayoutParams(this.icon, this.defaultMargin, 17);
                    updateViewPaddingBottom(this.labelGroup, 0);
                    this.largeLabel.setVisibility(4);
                }
                this.smallLabel.setVisibility(4);
                break;
            case 1:
                ViewGroup viewGroup4 = this.labelGroup;
                updateViewPaddingBottom(viewGroup4, ((Integer) viewGroup4.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                if (!checked) {
                    setViewLayoutParams(this.icon, this.defaultMargin, 49);
                    TextView textView7 = this.largeLabel;
                    float f3 = this.scaleDownFactor;
                    setViewScaleValues(textView7, f3, f3, 4);
                    setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                    break;
                } else {
                    setViewLayoutParams(this.icon, (int) (((float) this.defaultMargin) + this.shiftAmount), 49);
                    setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                    TextView textView8 = this.smallLabel;
                    float f4 = this.scaleUpFactor;
                    setViewScaleValues(textView8, f4, f4, 4);
                    break;
                }
            case 2:
                setViewLayoutParams(this.icon, this.defaultMargin, 17);
                this.largeLabel.setVisibility(8);
                this.smallLabel.setVisibility(8);
                break;
        }
        refreshDrawableState();
        setSelected(checked);
    }

    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 != null && badgeDrawable2.isVisible()) {
            CharSequence customContentDescription = this.itemData.getTitle();
            if (!TextUtils.isEmpty(this.itemData.getContentDescription())) {
                customContentDescription = this.itemData.getContentDescription();
            }
            info.setContentDescription(customContentDescription + ", " + this.badgeDrawable.getContentDescription());
        }
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
        if (isSelected()) {
            infoCompat.setClickable(false);
            infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        infoCompat.setRoleDescription(getResources().getString(R.string.item_view_role_description));
    }

    private int getItemVisiblePosition() {
        ViewGroup parent = (ViewGroup) getParent();
        int index = parent.indexOfChild(this);
        int visiblePosition = 0;
        for (int i = 0; i < index; i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof NavigationBarItemView) && child.getVisibility() == 0) {
                visiblePosition++;
            }
        }
        return visiblePosition;
    }

    private static void setViewLayoutParams(@NonNull View view, int topMargin, int gravity) {
        FrameLayout.LayoutParams viewParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        viewParams.topMargin = topMargin;
        viewParams.gravity = gravity;
        view.setLayoutParams(viewParams);
    }

    private static void setViewScaleValues(@NonNull View view, float scaleX, float scaleY, int visibility) {
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setVisibility(visibility);
    }

    private static void updateViewPaddingBottom(@NonNull View view, int paddingBottom) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), paddingBottom);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.smallLabel.setEnabled(enabled);
        this.largeLabel.setEnabled(enabled);
        this.icon.setEnabled(enabled);
        if (enabled) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        } else {
            ViewCompat.setPointerIcon(this, (PointerIconCompat) null);
        }
    }

    @NonNull
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            FrameLayout.mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setIcon(@Nullable Drawable iconDrawable) {
        Drawable drawable;
        if (iconDrawable != this.originalIconDrawable) {
            this.originalIconDrawable = iconDrawable;
            if (iconDrawable != null) {
                Drawable.ConstantState state = iconDrawable.getConstantState();
                if (state == null) {
                    drawable = iconDrawable;
                } else {
                    drawable = state.newDrawable();
                }
                iconDrawable = DrawableCompat.wrap(drawable).mutate();
                this.wrappedIconDrawable = iconDrawable;
                ColorStateList colorStateList = this.iconTint;
                if (colorStateList != null) {
                    DrawableCompat.setTintList(iconDrawable, colorStateList);
                }
            }
            this.icon.setImageDrawable(iconDrawable);
        }
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(@Nullable ColorStateList tint) {
        Drawable drawable;
        this.iconTint = tint;
        if (this.itemData != null && (drawable = this.wrappedIconDrawable) != null) {
            DrawableCompat.setTintList(drawable, tint);
            this.wrappedIconDrawable.invalidateSelf();
        }
    }

    public void setIconSize(int iconSize) {
        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        iconParams.width = iconSize;
        iconParams.height = iconSize;
        this.icon.setLayoutParams(iconParams);
    }

    public void setTextAppearanceInactive(@StyleRes int inactiveTextAppearance) {
        TextViewCompat.setTextAppearance(this.smallLabel, inactiveTextAppearance);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextAppearanceActive(@StyleRes int activeTextAppearance) {
        TextViewCompat.setTextAppearance(this.largeLabel, activeTextAppearance);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextColor(@Nullable ColorStateList color) {
        if (color != null) {
            this.smallLabel.setTextColor(color);
            this.largeLabel.setTextColor(color);
        }
    }

    private void calculateTextScaleFactors(float smallLabelSize, float largeLabelSize) {
        this.shiftAmount = smallLabelSize - largeLabelSize;
        this.scaleUpFactor = (largeLabelSize * 1.0f) / smallLabelSize;
        this.scaleDownFactor = (1.0f * smallLabelSize) / largeLabelSize;
    }

    public void setItemBackground(int background) {
        Drawable backgroundDrawable;
        if (background == 0) {
            backgroundDrawable = null;
        } else {
            backgroundDrawable = ContextCompat.getDrawable(getContext(), background);
        }
        setItemBackground(backgroundDrawable);
    }

    public void setItemBackground(@Nullable Drawable background) {
        if (!(background == null || background.getConstantState() == null)) {
            background = background.getConstantState().newDrawable().mutate();
        }
        ViewCompat.setBackground(this, background);
    }

    /* access modifiers changed from: package-private */
    public void setBadge(@NonNull BadgeDrawable badgeDrawable2) {
        this.badgeDrawable = badgeDrawable2;
        ImageView imageView = this.icon;
        if (imageView != null) {
            tryAttachBadgeToAnchor(imageView);
        }
    }

    @Nullable
    public BadgeDrawable getBadge() {
        return this.badgeDrawable;
    }

    /* access modifiers changed from: package-private */
    public void removeBadge() {
        tryRemoveBadgeFromAnchor(this.icon);
    }

    private boolean hasBadge() {
        return this.badgeDrawable != null;
    }

    /* access modifiers changed from: private */
    public void tryUpdateBadgeBounds(View anchorView) {
        if (hasBadge()) {
            BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
        }
    }

    private void tryAttachBadgeToAnchor(@Nullable View anchorView) {
        if (hasBadge() && anchorView != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
        }
    }

    private void tryRemoveBadgeFromAnchor(@Nullable View anchorView) {
        if (hasBadge()) {
            if (anchorView != null) {
                setClipChildren(true);
                setClipToPadding(true);
                BadgeUtils.detachBadgeDrawable(this.badgeDrawable, anchorView);
            }
            this.badgeDrawable = null;
        }
    }

    @Nullable
    private FrameLayout getCustomParentForBadge(View anchorView) {
        ImageView imageView = this.icon;
        if (anchorView != imageView || !BadgeUtils.USE_COMPAT_PARENT) {
            return null;
        }
        return (FrameLayout) imageView.getParent();
    }

    private int getSuggestedIconWidth() {
        int badgeWidth;
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 == null) {
            badgeWidth = 0;
        } else {
            badgeWidth = badgeDrawable2.getMinimumWidth() - this.badgeDrawable.getHorizontalOffset();
        }
        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        return Math.max(badgeWidth, iconParams.leftMargin) + this.icon.getMeasuredWidth() + Math.max(badgeWidth, iconParams.rightMargin);
    }

    private int getSuggestedIconHeight() {
        int badgeHeight = 0;
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 != null) {
            badgeHeight = badgeDrawable2.getMinimumHeight() / 2;
        }
        return Math.max(badgeHeight, ((FrameLayout.LayoutParams) this.icon.getLayoutParams()).topMargin) + this.icon.getMeasuredWidth() + badgeHeight;
    }

    /* access modifiers changed from: protected */
    @DrawableRes
    public int getItemBackgroundResId() {
        return R.drawable.mtrl_navigation_bar_item_background;
    }

    /* access modifiers changed from: protected */
    @DimenRes
    public int getItemDefaultMarginResId() {
        return R.dimen.mtrl_navigation_bar_item_default_margin;
    }
}
