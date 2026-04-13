package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.CompoundButtonCompat;

public class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private PorterDuff.Mode mButtonTintMode = null;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    @NonNull
    private final CompoundButton mView;

    AppCompatCompoundButtonHelper(@NonNull CompoundButton view) {
        this.mView = view;
    }

    /* access modifiers changed from: package-private */
    public void loadFromAttributes(@Nullable AttributeSet attrs, int defStyleAttr) {
        int resourceId;
        int resourceId2;
        Context context = this.mView.getContext();
        int[] iArr = R.styleable.CompoundButton;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, iArr, defStyleAttr, 0);
        CompoundButton compoundButton = this.mView;
        ViewCompat.saveAttributeDataForStyleable(compoundButton, compoundButton.getContext(), iArr, attrs, a.getWrappedTypeArray(), defStyleAttr, 0);
        boolean buttonDrawableLoaded = false;
        try {
            int i = R.styleable.CompoundButton_buttonCompat;
            if (a.hasValue(i) && (resourceId2 = a.getResourceId(i, 0)) != 0) {
                try {
                    CompoundButton compoundButton2 = this.mView;
                    compoundButton2.setButtonDrawable(AppCompatResources.getDrawable(compoundButton2.getContext(), resourceId2));
                    buttonDrawableLoaded = true;
                } catch (Resources.NotFoundException e) {
                }
            }
            if (!buttonDrawableLoaded) {
                int i2 = R.styleable.CompoundButton_android_button;
                if (a.hasValue(i2) && (resourceId = a.getResourceId(i2, 0)) != 0) {
                    CompoundButton compoundButton3 = this.mView;
                    compoundButton3.setButtonDrawable(AppCompatResources.getDrawable(compoundButton3.getContext(), resourceId));
                }
            }
            int resourceId3 = R.styleable.CompoundButton_buttonTint;
            if (a.hasValue(resourceId3)) {
                CompoundButtonCompat.setButtonTintList(this.mView, a.getColorStateList(resourceId3));
            }
            int i3 = R.styleable.CompoundButton_buttonTintMode;
            if (a.hasValue(i3)) {
                CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode(a.getInt(i3, -1), (PorterDuff.Mode) null));
            }
        } finally {
            a.recycle();
        }
    }

    /* access modifiers changed from: package-private */
    public void setSupportButtonTintList(ColorStateList tint) {
        this.mButtonTintList = tint;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList;
    }

    /* access modifiers changed from: package-private */
    public void setSupportButtonTintMode(@Nullable PorterDuff.Mode tintMode) {
        this.mButtonTintMode = tintMode;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode getSupportButtonTintMode() {
        return this.mButtonTintMode;
    }

    /* access modifiers changed from: package-private */
    public void onSetButtonDrawable() {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
            return;
        }
        this.mSkipNextApply = true;
        applyButtonTint();
    }

    /* access modifiers changed from: package-private */
    public void applyButtonTint() {
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
        if (buttonDrawable == null) {
            return;
        }
        if (this.mHasButtonTint || this.mHasButtonTintMode) {
            Drawable buttonDrawable2 = DrawableCompat.wrap(buttonDrawable).mutate();
            if (this.mHasButtonTint) {
                DrawableCompat.setTintList(buttonDrawable2, this.mButtonTintList);
            }
            if (this.mHasButtonTintMode) {
                DrawableCompat.setTintMode(buttonDrawable2, this.mButtonTintMode);
            }
            if (buttonDrawable2.isStateful()) {
                buttonDrawable2.setState(this.mView.getDrawableState());
            }
            this.mView.setButtonDrawable(buttonDrawable2);
        }
    }

    /* access modifiers changed from: package-private */
    public int getCompoundPaddingLeft(int superValue) {
        Drawable buttonDrawable;
        if (Build.VERSION.SDK_INT >= 17 || (buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView)) == null) {
            return superValue;
        }
        return superValue + buttonDrawable.getIntrinsicWidth();
    }
}
