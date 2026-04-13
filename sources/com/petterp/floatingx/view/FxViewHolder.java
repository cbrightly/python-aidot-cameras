package com.petterp.floatingx.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxViewHolder.kt */
public final class FxViewHolder {
    @Nullable
    private final View itemView;
    @NotNull
    private final SparseArray<View> views = new SparseArray<>();

    public FxViewHolder(@Nullable View itemView2) {
        this.itemView = itemView2;
    }

    @NotNull
    public final <T extends View> T getView(@IdRes int viewId) {
        View view = getViewOrNull(viewId);
        if (view != null) {
            return view;
        }
        throw new IllegalStateException(k.l("No view found with id ", Integer.valueOf(viewId)).toString());
    }

    @Nullable
    public final <T extends View> T getViewOrNull(@IdRes int viewId) {
        View findViewById;
        View view = this.views.get(viewId);
        if (view != null) {
            return view;
        }
        View view2 = this.itemView;
        if (view2 == null || (findViewById = view2.findViewById(viewId)) == null) {
            return null;
        }
        View it = findViewById;
        this.views.put(viewId, it);
        return it;
    }

    @NotNull
    public final FxViewHolder setOnClickListener(@IdRes int viewId, @NotNull View.OnClickListener listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    @NotNull
    public final FxViewHolder setText(@IdRes int viewId, @Nullable CharSequence value) {
        ((TextView) getView(viewId)).setText(value);
        return this;
    }

    @NotNull
    public final FxViewHolder setText(@IdRes int viewId, @StringRes int resId) {
        ((TextView) getView(viewId)).setText(resId);
        return this;
    }

    @NotNull
    public final FxViewHolder setTextSize(@IdRes int viewId, float size) {
        ((TextView) getView(viewId)).setTextSize(size);
        return this;
    }

    @NotNull
    public final FxViewHolder setTextSize(@IdRes int viewId, int unit, float size) {
        ((TextView) getView(viewId)).setTextSize(unit, size);
        return this;
    }

    @NotNull
    public final FxViewHolder setImageResource(@IdRes int viewId, @DrawableRes int source) {
        ((ImageView) getView(viewId)).setImageResource(source);
        return this;
    }

    @NotNull
    public final FxViewHolder setImageBitMap(@IdRes int viewId, @Nullable Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    @NotNull
    public final FxViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    @NotNull
    public final FxViewHolder setBackgroundResource(@IdRes int id, @DrawableRes int source) {
        getView(id).setBackgroundResource(source);
        return this;
    }

    @NotNull
    public final FxViewHolder setBackgroundColor(@IdRes int id, @ColorInt int color) {
        getView(id).setBackgroundColor(color);
        return this;
    }

    @NotNull
    public final FxViewHolder setGone(@IdRes int viewId, boolean isGone) {
        getView(viewId).setVisibility(isGone ? 8 : 0);
        return this;
    }

    @NotNull
    public final FxViewHolder setEnabled(@IdRes int viewId, boolean isEnabled) {
        getView(viewId).setEnabled(isEnabled);
        return this;
    }
}
