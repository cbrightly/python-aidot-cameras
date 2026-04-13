package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class NavigationMenuItemView extends ForegroundLinearLayout implements MenuView.ItemView {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private final AccessibilityDelegateCompat accessibilityDelegate;
    private FrameLayout actionArea;
    boolean checkable;
    private Drawable emptyDrawable;
    private boolean hasIconTintList;
    private int iconSize;
    private ColorStateList iconTintList;
    private MenuItemImpl itemData;
    private boolean needsEmptyIcon;
    private final CheckedTextView textView;

    public NavigationMenuItemView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public NavigationMenuItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationMenuItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AnonymousClass1 r0 = new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, @NonNull AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setCheckable(NavigationMenuItemView.this.checkable);
            }
        };
        this.accessibilityDelegate = r0;
        setOrientation(0);
        LayoutInflater.from(context).inflate(R.layout.design_navigation_menu_item, this, true);
        setIconSize(context.getResources().getDimensionPixelSize(R.dimen.design_navigation_icon_size));
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(R.id.design_menu_item_text);
        this.textView = checkedTextView;
        checkedTextView.setDuplicateParentStateEnabled(true);
        ViewCompat.setAccessibilityDelegate(checkedTextView, r0);
    }

    public void initialize(@NonNull MenuItemImpl itemData2, int menuType) {
        this.itemData = itemData2;
        if (itemData2.getItemId() > 0) {
            setId(itemData2.getItemId());
        }
        setVisibility(itemData2.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            ViewCompat.setBackground(this, createDefaultBackground());
        }
        setCheckable(itemData2.isCheckable());
        setChecked(itemData2.isChecked());
        setEnabled(itemData2.isEnabled());
        setTitle(itemData2.getTitle());
        setIcon(itemData2.getIcon());
        setActionView(itemData2.getActionView());
        setContentDescription(itemData2.getContentDescription());
        TooltipCompat.setTooltipText(this, itemData2.getTooltipText());
        adjustAppearance();
    }

    private boolean shouldExpandActionArea() {
        return this.itemData.getTitle() == null && this.itemData.getIcon() == null && this.itemData.getActionView() != null;
    }

    private void adjustAppearance() {
        if (shouldExpandActionArea()) {
            this.textView.setVisibility(8);
            FrameLayout frameLayout = this.actionArea;
            if (frameLayout != null) {
                LinearLayoutCompat.LayoutParams params = (LinearLayoutCompat.LayoutParams) frameLayout.getLayoutParams();
                params.width = -1;
                this.actionArea.setLayoutParams(params);
                return;
            }
            return;
        }
        this.textView.setVisibility(0);
        FrameLayout frameLayout2 = this.actionArea;
        if (frameLayout2 != null) {
            LinearLayoutCompat.LayoutParams params2 = (LinearLayoutCompat.LayoutParams) frameLayout2.getLayoutParams();
            params2.width = -2;
            this.actionArea.setLayoutParams(params2);
        }
    }

    public void recycle() {
        FrameLayout frameLayout = this.actionArea;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        this.textView.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    private void setActionView(@Nullable View actionView) {
        if (actionView != null) {
            if (this.actionArea == null) {
                this.actionArea = (FrameLayout) ((ViewStub) findViewById(R.id.design_menu_item_action_area_stub)).inflate();
            }
            this.actionArea.removeAllViews();
            this.actionArea.addView(actionView);
        }
    }

    @Nullable
    private StateListDrawable createDefaultBackground() {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorControlHighlight, value, true)) {
            return null;
        }
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(CHECKED_STATE_SET, new ColorDrawable(value.data));
        drawable.addState(ViewGroup.EMPTY_STATE_SET, new ColorDrawable(0));
        return drawable;
    }

    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    public void setTitle(CharSequence title) {
        this.textView.setText(title);
    }

    public void setCheckable(boolean checkable2) {
        refreshDrawableState();
        if (this.checkable != checkable2) {
            this.checkable = checkable2;
            this.accessibilityDelegate.sendAccessibilityEvent(this.textView, 2048);
        }
    }

    public void setChecked(boolean checked) {
        refreshDrawableState();
        this.textView.setChecked(checked);
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setIcon(@Nullable Drawable icon) {
        if (icon != null) {
            if (this.hasIconTintList) {
                Drawable.ConstantState state = icon.getConstantState();
                icon = DrawableCompat.wrap(state == null ? icon : state.newDrawable()).mutate();
                DrawableCompat.setTintList(icon, this.iconTintList);
            }
            int i = this.iconSize;
            icon.setBounds(0, 0, i, i);
        } else if (this.needsEmptyIcon) {
            if (this.emptyDrawable == null) {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.navigation_empty_icon, getContext().getTheme());
                this.emptyDrawable = drawable;
                if (drawable != null) {
                    int i2 = this.iconSize;
                    drawable.setBounds(0, 0, i2, i2);
                }
            }
            icon = this.emptyDrawable;
        }
        TextViewCompat.setCompoundDrawablesRelative(this.textView, icon, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    public void setIconSize(@Dimension int iconSize2) {
        this.iconSize = iconSize2;
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            ViewGroup.mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    /* access modifiers changed from: package-private */
    public void setIconTintList(ColorStateList tintList) {
        this.iconTintList = tintList;
        this.hasIconTintList = tintList != null;
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null) {
            setIcon(menuItemImpl.getIcon());
        }
    }

    public void setTextAppearance(int textAppearance) {
        TextViewCompat.setTextAppearance(this.textView, textAppearance);
    }

    public void setTextColor(ColorStateList colors) {
        this.textView.setTextColor(colors);
    }

    public void setNeedsEmptyIcon(boolean needsEmptyIcon2) {
        this.needsEmptyIcon = needsEmptyIcon2;
    }

    public void setHorizontalPadding(int padding) {
        setPadding(padding, 0, padding, 0);
    }

    public void setIconPadding(int padding) {
        this.textView.setCompoundDrawablePadding(padding);
    }

    public void setMaxLines(int maxLines) {
        this.textView.setMaxLines(maxLines);
    }
}
