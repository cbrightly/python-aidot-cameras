package com.chad.library.adapter.base.viewholder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
/* compiled from: BaseViewHolder.kt */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views = new SparseArray<>();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseViewHolder(@NotNull View view) {
        super(view);
        k.e(view, "view");
    }

    @Nullable
    public <B extends ViewDataBinding> B getBinding() {
        return DataBindingUtil.getBinding(this.itemView);
    }

    @NotNull
    public <T extends View> T getView(@IdRes int viewId) {
        View view = getViewOrNull(viewId);
        if (view != null) {
            return view;
        }
        throw new IllegalStateException(("No view found with id " + viewId).toString());
    }

    @Nullable
    public <T extends View> T getViewOrNull(@IdRes int viewId) {
        View it;
        View view = this.views.get(viewId);
        if (view == null && (it = this.itemView.findViewById(viewId)) != null) {
            this.views.put(viewId, it);
            return it;
        } else if (!(view instanceof View)) {
            return null;
        } else {
            return view;
        }
    }

    @Nullable
    public <T extends View> T findView(int $this$findView) {
        return this.itemView.findViewById($this$findView);
    }

    @NotNull
    public BaseViewHolder setText(@IdRes int viewId, @Nullable CharSequence value) {
        ((TextView) getView(viewId)).setText(value);
        return this;
    }

    @Nullable
    public BaseViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        ((TextView) getView(viewId)).setText(strId);
        return this;
    }

    @NotNull
    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        ((TextView) getView(viewId)).setTextColor(color);
        return this;
    }

    @NotNull
    public BaseViewHolder setTextColorRes(@IdRes int viewId, @ColorRes int colorRes) {
        View view = this.itemView;
        k.d(view, "itemView");
        ((TextView) getView(viewId)).setTextColor(view.getResources().getColor(colorRes));
        return this;
    }

    @NotNull
    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ((ImageView) getView(viewId)).setImageResource(imageResId);
        return this;
    }

    @NotNull
    public BaseViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    @NotNull
    public BaseViewHolder setImageBitmap(@IdRes int viewId, @Nullable Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    @NotNull
    public BaseViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }

    @NotNull
    public BaseViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    @NotNull
    public BaseViewHolder setVisible(@IdRes int viewId, boolean isVisible) {
        getView(viewId).setVisibility(isVisible ? 0 : 4);
        return this;
    }

    @NotNull
    public BaseViewHolder setGone(@IdRes int viewId, boolean isGone) {
        getView(viewId).setVisibility(isGone ? 8 : 0);
        return this;
    }

    @NotNull
    public BaseViewHolder setEnabled(@IdRes int viewId, boolean isEnabled) {
        getView(viewId).setEnabled(isEnabled);
        return this;
    }
}
