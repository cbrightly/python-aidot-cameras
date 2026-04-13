package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class NavigationBarView extends FrameLayout {
    public static final int LABEL_VISIBILITY_AUTO = -1;
    public static final int LABEL_VISIBILITY_LABELED = 1;
    public static final int LABEL_VISIBILITY_SELECTED = 0;
    public static final int LABEL_VISIBILITY_UNLABELED = 2;
    private static final int MENU_PRESENTER_ID = 1;
    @Nullable
    private ColorStateList itemRippleColor;
    @NonNull
    private final NavigationBarMenu menu;
    private MenuInflater menuInflater;
    @NonNull
    private final NavigationBarMenuView menuView;
    @NonNull
    private final NavigationBarPresenter presenter;
    /* access modifiers changed from: private */
    public OnItemReselectedListener reselectedListener;
    /* access modifiers changed from: private */
    public OnItemSelectedListener selectedListener;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LabelVisibility {
    }

    public interface OnItemReselectedListener {
        void onNavigationItemReselected(@NonNull MenuItem menuItem);
    }

    public interface OnItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract NavigationBarMenuView createNavigationBarMenuView(@NonNull Context context);

    public abstract int getMaxItemCount();

    public NavigationBarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, defStyleRes), attrs, defStyleAttr);
        NavigationBarPresenter navigationBarPresenter = new NavigationBarPresenter();
        this.presenter = navigationBarPresenter;
        Context context2 = getContext();
        int[] iArr = R.styleable.NavigationBarView;
        int i = R.styleable.NavigationBarView_itemTextAppearanceInactive;
        int i2 = R.styleable.NavigationBarView_itemTextAppearanceActive;
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs, iArr, defStyleAttr, defStyleRes, i, i2);
        NavigationBarMenu navigationBarMenu = new NavigationBarMenu(context2, getClass(), getMaxItemCount());
        this.menu = navigationBarMenu;
        NavigationBarMenuView createNavigationBarMenuView = createNavigationBarMenuView(context2);
        this.menuView = createNavigationBarMenuView;
        navigationBarPresenter.setMenuView(createNavigationBarMenuView);
        navigationBarPresenter.setId(1);
        createNavigationBarMenuView.setPresenter(navigationBarPresenter);
        navigationBarMenu.addMenuPresenter(navigationBarPresenter);
        navigationBarPresenter.initForMenu(getContext(), navigationBarMenu);
        int i3 = R.styleable.NavigationBarView_itemIconTint;
        if (attributes.hasValue(i3)) {
            createNavigationBarMenuView.setIconTintList(attributes.getColorStateList(i3));
        } else {
            createNavigationBarMenuView.setIconTintList(createNavigationBarMenuView.createDefaultColorStateList(16842808));
        }
        setItemIconSize(attributes.getDimensionPixelSize(R.styleable.NavigationBarView_itemIconSize, getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_bar_item_default_icon_size)));
        if (attributes.hasValue(i)) {
            setItemTextAppearanceInactive(attributes.getResourceId(i, 0));
        }
        if (attributes.hasValue(i2)) {
            setItemTextAppearanceActive(attributes.getResourceId(i2, 0));
        }
        int i4 = R.styleable.NavigationBarView_itemTextColor;
        if (attributes.hasValue(i4)) {
            setItemTextColor(attributes.getColorStateList(i4));
        }
        if (getBackground() == null || (getBackground() instanceof ColorDrawable)) {
            ViewCompat.setBackground(this, createMaterialShapeDrawableBackground(context2));
        }
        int i5 = R.styleable.NavigationBarView_elevation;
        if (attributes.hasValue(i5)) {
            setElevation((float) attributes.getDimensionPixelSize(i5, 0));
        }
        DrawableCompat.setTintList(getBackground().mutate(), MaterialResources.getColorStateList(context2, attributes, R.styleable.NavigationBarView_backgroundTint));
        setLabelVisibilityMode(attributes.getInteger(R.styleable.NavigationBarView_labelVisibilityMode, -1));
        int itemBackground = attributes.getResourceId(R.styleable.NavigationBarView_itemBackground, 0);
        if (itemBackground != 0) {
            createNavigationBarMenuView.setItemBackgroundRes(itemBackground);
        } else {
            setItemRippleColor(MaterialResources.getColorStateList(context2, attributes, R.styleable.NavigationBarView_itemRippleColor));
        }
        int i6 = R.styleable.NavigationBarView_menu;
        if (attributes.hasValue(i6)) {
            inflateMenu(attributes.getResourceId(i6, 0));
        }
        attributes.recycle();
        addView(createNavigationBarMenuView);
        navigationBarMenu.setCallback(new MenuBuilder.Callback() {
            public boolean onMenuItemSelected(MenuBuilder menu, @NonNull MenuItem item) {
                if (NavigationBarView.this.reselectedListener != null && item.getItemId() == NavigationBarView.this.getSelectedItemId()) {
                    NavigationBarView.this.reselectedListener.onNavigationItemReselected(item);
                    return true;
                } else if (NavigationBarView.this.selectedListener == null || NavigationBarView.this.selectedListener.onNavigationItemSelected(item)) {
                    return false;
                } else {
                    return true;
                }
            }

            public void onMenuModeChange(MenuBuilder menu) {
            }
        });
        applyWindowInsets();
    }

    private void applyWindowInsets() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() {
            @NonNull
            public WindowInsetsCompat onApplyWindowInsets(View view, @NonNull WindowInsetsCompat insets, @NonNull ViewUtils.RelativePadding initialPadding) {
                initialPadding.bottom += insets.getSystemWindowInsetBottom();
                boolean z = true;
                if (ViewCompat.getLayoutDirection(view) != 1) {
                    z = false;
                }
                boolean isRtl = z;
                int systemWindowInsetLeft = insets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = insets.getSystemWindowInsetRight();
                initialPadding.start += isRtl ? systemWindowInsetRight : systemWindowInsetLeft;
                initialPadding.end += isRtl ? systemWindowInsetLeft : systemWindowInsetRight;
                initialPadding.applyToView(view);
                return insets;
            }
        });
    }

    @NonNull
    private MaterialShapeDrawable createMaterialShapeDrawableBackground(Context context) {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        Drawable originalBackground = getBackground();
        if (originalBackground instanceof ColorDrawable) {
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) originalBackground).getColor()));
        }
        materialShapeDrawable.initializeElevationOverlay(context);
        return materialShapeDrawable;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    public void setElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(elevation);
        }
        MaterialShapeUtils.setElevation(this, elevation);
    }

    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener listener) {
        this.selectedListener = listener;
    }

    public void setOnItemReselectedListener(@Nullable OnItemReselectedListener listener) {
        this.reselectedListener = listener;
    }

    @NonNull
    public Menu getMenu() {
        return this.menu;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public MenuView getMenuView() {
        return this.menuView;
    }

    public void inflateMenu(int resId) {
        this.presenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.menu);
        this.presenter.setUpdateSuspended(false);
        this.presenter.updateMenuView(true);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.menuView.getIconTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList tint) {
        this.menuView.setIconTintList(tint);
    }

    public void setItemIconSize(@Dimension int iconSize) {
        this.menuView.setItemIconSize(iconSize);
    }

    public void setItemIconSizeRes(@DimenRes int iconSizeRes) {
        setItemIconSize(getResources().getDimensionPixelSize(iconSizeRes));
    }

    @Dimension
    public int getItemIconSize() {
        return this.menuView.getItemIconSize();
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.menuView.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList textColor) {
        this.menuView.setItemTextColor(textColor);
    }

    @Deprecated
    @DrawableRes
    public int getItemBackgroundResource() {
        return this.menuView.getItemBackgroundRes();
    }

    public void setItemBackgroundResource(@DrawableRes int resId) {
        this.menuView.setItemBackgroundRes(resId);
        this.itemRippleColor = null;
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.menuView.getItemBackground();
    }

    public void setItemBackground(@Nullable Drawable background) {
        this.menuView.setItemBackground(background);
        this.itemRippleColor = null;
    }

    @Nullable
    public ColorStateList getItemRippleColor() {
        return this.itemRippleColor;
    }

    public void setItemRippleColor(@Nullable ColorStateList itemRippleColor2) {
        if (this.itemRippleColor != itemRippleColor2) {
            this.itemRippleColor = itemRippleColor2;
            if (itemRippleColor2 == null) {
                this.menuView.setItemBackground((Drawable) null);
                return;
            }
            ColorStateList rippleDrawableColor = RippleUtils.convertToRippleDrawableColor(itemRippleColor2);
            if (Build.VERSION.SDK_INT >= 21) {
                this.menuView.setItemBackground(new RippleDrawable(rippleDrawableColor, (Drawable) null, (Drawable) null));
                return;
            }
            GradientDrawable rippleDrawable = new GradientDrawable();
            rippleDrawable.setCornerRadius(1.0E-5f);
            Drawable rippleDrawableCompat = DrawableCompat.wrap(rippleDrawable);
            DrawableCompat.setTintList(rippleDrawableCompat, rippleDrawableColor);
            this.menuView.setItemBackground(rippleDrawableCompat);
        } else if (itemRippleColor2 == null && this.menuView.getItemBackground() != null) {
            this.menuView.setItemBackground((Drawable) null);
        }
    }

    @IdRes
    public int getSelectedItemId() {
        return this.menuView.getSelectedItemId();
    }

    public void setSelectedItemId(@IdRes int itemId) {
        MenuItem item = this.menu.findItem(itemId);
        if (item != null && !this.menu.performItemAction(item, this.presenter, 0)) {
            item.setChecked(true);
        }
    }

    public void setLabelVisibilityMode(int labelVisibilityMode) {
        if (this.menuView.getLabelVisibilityMode() != labelVisibilityMode) {
            this.menuView.setLabelVisibilityMode(labelVisibilityMode);
            this.presenter.updateMenuView(false);
        }
    }

    public int getLabelVisibilityMode() {
        return this.menuView.getLabelVisibilityMode();
    }

    public void setItemTextAppearanceInactive(@StyleRes int textAppearanceRes) {
        this.menuView.setItemTextAppearanceInactive(textAppearanceRes);
    }

    @StyleRes
    public int getItemTextAppearanceInactive() {
        return this.menuView.getItemTextAppearanceInactive();
    }

    public void setItemTextAppearanceActive(@StyleRes int textAppearanceRes) {
        this.menuView.setItemTextAppearanceActive(textAppearanceRes);
    }

    @StyleRes
    public int getItemTextAppearanceActive() {
        return this.menuView.getItemTextAppearanceActive();
    }

    public void setItemOnTouchListener(int menuItemId, @Nullable View.OnTouchListener onTouchListener) {
        this.menuView.setItemOnTouchListener(menuItemId, onTouchListener);
    }

    @Nullable
    public BadgeDrawable getBadge(int menuItemId) {
        return this.menuView.getBadge(menuItemId);
    }

    @NonNull
    public BadgeDrawable getOrCreateBadge(int menuItemId) {
        return this.menuView.getOrCreateBadge(menuItemId);
    }

    public void removeBadge(int menuItemId) {
        this.menuView.removeBadge(menuItemId);
    }

    private MenuInflater getMenuInflater() {
        if (this.menuInflater == null) {
            this.menuInflater = new SupportMenuInflater(getContext());
        }
        return this.menuInflater;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public NavigationBarPresenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.menuPresenterState = bundle;
        this.menu.savePresenterStates(bundle);
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(@Nullable Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.menu.restorePresenterStates(savedState.menuPresenterState);
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
        Bundle menuPresenterState;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(@NonNull Parcel source, ClassLoader loader) {
            super(source, loader);
            readFromParcel(source, loader == null ? getClass().getClassLoader() : loader);
        }

        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBundle(this.menuPresenterState);
        }

        private void readFromParcel(@NonNull Parcel in, ClassLoader loader) {
            this.menuPresenterState = in.readBundle(loader);
        }
    }
}
