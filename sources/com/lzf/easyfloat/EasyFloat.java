package com.lzf.easyfloat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.lzf.easyfloat.core.FloatingWindowHelper;
import com.lzf.easyfloat.core.FloatingWindowManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnDisplayHeight;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.lzf.easyfloat.utils.DisplayUtils;
import com.lzf.easyfloat.utils.LifecycleUtils;
import com.lzf.easyfloat.utils.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EasyFloat.kt */
public final class EasyFloat {
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    @Nullable
    public static final x clearFilters() {
        return Companion.clearFilters();
    }

    @Nullable
    public static final x clearFilters(@Nullable String str) {
        return Companion.clearFilters(str);
    }

    @Nullable
    public static final x dismiss() {
        return Companion.dismiss();
    }

    @Nullable
    public static final x dismiss(@Nullable String str) {
        return Companion.dismiss(str);
    }

    @Nullable
    public static final x dismiss(@Nullable String str, boolean z) {
        return Companion.dismiss(str, z);
    }

    @Nullable
    public static final x dragEnable(boolean z) {
        return Companion.dragEnable(z);
    }

    @Nullable
    public static final x dragEnable(boolean z, @Nullable String str) {
        return Companion.dragEnable(z, str);
    }

    @Nullable
    public static final Boolean filterActivities(@Nullable String str, @NotNull Class<?>... clsArr) {
        return Companion.filterActivities(str, clsArr);
    }

    @Nullable
    public static final Boolean filterActivities(@NotNull Class<?>... clsArr) {
        return Companion.filterActivities(clsArr);
    }

    @Nullable
    public static final Boolean filterActivity(@NotNull Activity activity) {
        return Companion.filterActivity(activity);
    }

    @Nullable
    public static final Boolean filterActivity(@NotNull Activity activity, @Nullable String str) {
        return Companion.filterActivity(activity, str);
    }

    @Nullable
    public static final View getFloatView() {
        return Companion.getFloatView();
    }

    @Nullable
    public static final View getFloatView(@Nullable String str) {
        return Companion.getFloatView(str);
    }

    @Nullable
    public static final x hide() {
        return Companion.hide();
    }

    @Nullable
    public static final x hide(@Nullable String str) {
        return Companion.hide(str);
    }

    public static final boolean isShow() {
        return Companion.isShow();
    }

    public static final boolean isShow(@Nullable String str) {
        return Companion.isShow(str);
    }

    @Nullable
    public static final Boolean removeFilter(@NotNull Activity activity) {
        return Companion.removeFilter(activity);
    }

    @Nullable
    public static final Boolean removeFilter(@NotNull Activity activity, @Nullable String str) {
        return Companion.removeFilter(activity, str);
    }

    @Nullable
    public static final Boolean removeFilters(@Nullable String str, @NotNull Class<?>... clsArr) {
        return Companion.removeFilters(str, clsArr);
    }

    @Nullable
    public static final Boolean removeFilters(@NotNull Class<?>... clsArr) {
        return Companion.removeFilters(clsArr);
    }

    @Nullable
    public static final x show() {
        return Companion.show();
    }

    @Nullable
    public static final x show(@Nullable String str) {
        return Companion.show(str);
    }

    @Nullable
    public static final x updateFloat() {
        return Companion.updateFloat();
    }

    @Nullable
    public static final x updateFloat(@Nullable String str) {
        return Companion.updateFloat(str);
    }

    @Nullable
    public static final x updateFloat(@Nullable String str, int i) {
        return Companion.updateFloat(str, i);
    }

    @Nullable
    public static final x updateFloat(@Nullable String str, int i, int i2) {
        return Companion.updateFloat(str, i, i2);
    }

    @Nullable
    public static final x updateFloat(@Nullable String str, int i, int i2, int i3) {
        return Companion.updateFloat(str, i, i2, i3);
    }

    @Nullable
    public static final x updateFloat(@Nullable String str, int i, int i2, int i3, int i4) {
        return Companion.updateFloat(str, i, i2, i3, i4);
    }

    @NotNull
    public static final Builder with(@NotNull Context context) {
        return Companion.with(context);
    }

    /* compiled from: EasyFloat.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final x clearFilters() {
            return clearFilters$default(this, (String) null, 1, (Object) null);
        }

        @Nullable
        public final x dismiss() {
            return dismiss$default(this, (String) null, false, 3, (Object) null);
        }

        @Nullable
        public final x dismiss(@Nullable String str) {
            return dismiss$default(this, str, false, 2, (Object) null);
        }

        @Nullable
        public final x dragEnable(boolean z) {
            return dragEnable$default(this, z, (String) null, 2, (Object) null);
        }

        @Nullable
        public final Boolean filterActivities(@NotNull Class<?>... clsArr) {
            k.e(clsArr, "clazz");
            return filterActivities$default(this, (String) null, clsArr, 1, (Object) null);
        }

        @Nullable
        public final Boolean filterActivity(@NotNull Activity activity) {
            k.e(activity, "activity");
            return filterActivity$default(this, activity, (String) null, 2, (Object) null);
        }

        @Nullable
        public final View getFloatView() {
            return getFloatView$default(this, (String) null, 1, (Object) null);
        }

        @Nullable
        public final x hide() {
            return hide$default(this, (String) null, 1, (Object) null);
        }

        public final boolean isShow() {
            return isShow$default(this, (String) null, 1, (Object) null);
        }

        @Nullable
        public final Boolean removeFilter(@NotNull Activity activity) {
            k.e(activity, "activity");
            return removeFilter$default(this, activity, (String) null, 2, (Object) null);
        }

        @Nullable
        public final Boolean removeFilters(@NotNull Class<?>... clsArr) {
            k.e(clsArr, "clazz");
            return removeFilters$default(this, (String) null, clsArr, 1, (Object) null);
        }

        @Nullable
        public final x show() {
            return show$default(this, (String) null, 1, (Object) null);
        }

        @Nullable
        public final x updateFloat() {
            return updateFloat$default(this, (String) null, 0, 0, 0, 0, 31, (Object) null);
        }

        @Nullable
        public final x updateFloat(@Nullable String str) {
            return updateFloat$default(this, str, 0, 0, 0, 0, 30, (Object) null);
        }

        @Nullable
        public final x updateFloat(@Nullable String str, int i) {
            return updateFloat$default(this, str, i, 0, 0, 0, 28, (Object) null);
        }

        @Nullable
        public final x updateFloat(@Nullable String str, int i, int i2) {
            return updateFloat$default(this, str, i, i2, 0, 0, 24, (Object) null);
        }

        @Nullable
        public final x updateFloat(@Nullable String str, int i, int i2, int i3) {
            return updateFloat$default(this, str, i, i2, i3, 0, 16, (Object) null);
        }

        private Companion() {
        }

        @NotNull
        public final Builder with(@NotNull Context activity) {
            k.e(activity, "activity");
            if (activity instanceof Activity) {
                return new Builder(activity);
            }
            Context topActivity = LifecycleUtils.INSTANCE.getTopActivity();
            if (topActivity == null) {
                topActivity = activity;
            }
            return new Builder(topActivity);
        }

        public static /* synthetic */ x dismiss$default(Companion companion, String str, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            if ((i & 2) != 0) {
                z = false;
            }
            return companion.dismiss(str, z);
        }

        @Nullable
        public final x dismiss(@Nullable String tag, boolean force) {
            return FloatingWindowManager.INSTANCE.dismiss(tag, force);
        }

        public static /* synthetic */ x hide$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.hide(str);
        }

        @Nullable
        public final x hide(@Nullable String tag) {
            return FloatingWindowManager.INSTANCE.visible(false, tag, false);
        }

        public static /* synthetic */ x show$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.show(str);
        }

        @Nullable
        public final x show(@Nullable String tag) {
            return FloatingWindowManager.INSTANCE.visible(true, tag, true);
        }

        public static /* synthetic */ x dragEnable$default(Companion companion, boolean z, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.dragEnable(z, str);
        }

        @Nullable
        public final x dragEnable(boolean dragEnable, @Nullable String tag) {
            FloatConfig it = getConfig(tag);
            if (it == null) {
                return null;
            }
            it.setDragEnable(dragEnable);
            return x.a;
        }

        public static /* synthetic */ boolean isShow$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.isShow(str);
        }

        public final boolean isShow(@Nullable String tag) {
            FloatConfig config = getConfig(tag);
            if (config == null) {
                return false;
            }
            return config.isShow();
        }

        public static /* synthetic */ View getFloatView$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.getFloatView(str);
        }

        @Nullable
        public final View getFloatView(@Nullable String tag) {
            FloatConfig config = getConfig(tag);
            if (config == null) {
                return null;
            }
            return config.getLayoutView();
        }

        public static /* synthetic */ x updateFloat$default(Companion companion, String str, int i, int i2, int i3, int i4, int i5, Object obj) {
            int i6;
            int i7;
            int i8;
            if ((i5 & 1) != 0) {
                str = null;
            }
            int i9 = -1;
            if ((i5 & 2) != 0) {
                i6 = -1;
            } else {
                i6 = i;
            }
            if ((i5 & 4) != 0) {
                i7 = -1;
            } else {
                i7 = i2;
            }
            if ((i5 & 8) != 0) {
                i8 = -1;
            } else {
                i8 = i3;
            }
            if ((i5 & 16) == 0) {
                i9 = i4;
            }
            return companion.updateFloat(str, i6, i7, i8, i9);
        }

        @Nullable
        public final x updateFloat(@Nullable String tag, int x, int y, int width, int height) {
            FloatingWindowHelper helper = FloatingWindowManager.INSTANCE.getHelper(tag);
            if (helper == null) {
                return null;
            }
            helper.updateFloat(x, y, width, height);
            return x.a;
        }

        public static /* synthetic */ Boolean filterActivity$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.filterActivity(activity, str);
        }

        @Nullable
        public final Boolean filterActivity(@NotNull Activity activity, @Nullable String tag) {
            k.e(activity, "activity");
            Set<String> filterSet = getFilterSet(tag);
            if (filterSet == null) {
                return null;
            }
            String className = activity.getComponentName().getClassName();
            k.d(className, "activity.componentName.className");
            return Boolean.valueOf(filterSet.add(className));
        }

        public static /* synthetic */ Boolean filterActivities$default(Companion companion, String str, Class[] clsArr, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.filterActivities(str, clsArr);
        }

        @Nullable
        public final Boolean filterActivities(@Nullable String tag, @NotNull Class<?>... clazz) {
            k.e(clazz, "clazz");
            Set<String> filterSet = getFilterSet(tag);
            if (filterSet == null) {
                return null;
            }
            Class[] clsArr = clazz;
            Collection destination$iv$iv = new ArrayList(clsArr.length);
            for (Class it : clsArr) {
                String name = it.getName();
                k.d(name, "it.name");
                destination$iv$iv.add(name);
            }
            return Boolean.valueOf(filterSet.addAll(destination$iv$iv));
        }

        public static /* synthetic */ Boolean removeFilter$default(Companion companion, Activity activity, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            return companion.removeFilter(activity, str);
        }

        @Nullable
        public final Boolean removeFilter(@NotNull Activity activity, @Nullable String tag) {
            k.e(activity, "activity");
            Set<String> filterSet = getFilterSet(tag);
            if (filterSet == null) {
                return null;
            }
            return Boolean.valueOf(filterSet.remove(activity.getComponentName().getClassName()));
        }

        public static /* synthetic */ Boolean removeFilters$default(Companion companion, String str, Class[] clsArr, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.removeFilters(str, clsArr);
        }

        @Nullable
        public final Boolean removeFilters(@Nullable String tag, @NotNull Class<?>... clazz) {
            k.e(clazz, "clazz");
            Set<String> filterSet = getFilterSet(tag);
            if (filterSet == null) {
                return null;
            }
            Class[] clsArr = clazz;
            Collection destination$iv$iv = new ArrayList(clsArr.length);
            for (Class it : clsArr) {
                String name = it.getName();
                k.d(name, "it.name");
                destination$iv$iv.add(name);
            }
            return Boolean.valueOf(filterSet.removeAll(destination$iv$iv));
        }

        public static /* synthetic */ x clearFilters$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.clearFilters(str);
        }

        @Nullable
        public final x clearFilters(@Nullable String tag) {
            Set<String> filterSet = getFilterSet(tag);
            if (filterSet == null) {
                return null;
            }
            filterSet.clear();
            return x.a;
        }

        private final FloatConfig getConfig(String tag) {
            FloatingWindowHelper helper = FloatingWindowManager.INSTANCE.getHelper(tag);
            if (helper == null) {
                return null;
            }
            return helper.getConfig();
        }

        private final Set<String> getFilterSet(String tag) {
            FloatConfig config = getConfig(tag);
            if (config == null) {
                return null;
            }
            return config.getFilterSet();
        }
    }

    /* compiled from: EasyFloat.kt */
    public static final class Builder implements OnPermissionResult {
        @NotNull
        private final Context activity;
        @NotNull
        private final FloatConfig config = new FloatConfig((Integer) null, (View) null, (String) null, false, false, false, false, false, false, (SidePattern) null, (ShowPattern) null, false, false, 0, (n) null, (n) null, 0, 0, 0, 0, (OnInvokeView) null, (OnFloatCallbacks) null, (FloatCallbacks) null, (OnFloatAnimator) null, (OnDisplayHeight) null, (Set) null, false, false, 0, 536870911, (DefaultConstructorMarker) null);

        @NotNull
        public final Builder setBorder() {
            return setBorder$default(this, 0, 0, 0, 0, 15, (Object) null);
        }

        @NotNull
        public final Builder setBorder(int i) {
            return setBorder$default(this, i, 0, 0, 0, 14, (Object) null);
        }

        @NotNull
        public final Builder setBorder(int i, int i2) {
            return setBorder$default(this, i, i2, 0, 0, 12, (Object) null);
        }

        @NotNull
        public final Builder setBorder(int i, int i2, int i3) {
            return setBorder$default(this, i, i2, i3, 0, 8, (Object) null);
        }

        @NotNull
        public final Builder setGravity(int i) {
            return setGravity$default(this, i, 0, 0, 6, (Object) null);
        }

        @NotNull
        public final Builder setGravity(int i, int i2) {
            return setGravity$default(this, i, i2, 0, 4, (Object) null);
        }

        @NotNull
        public final Builder setLayout(int i) {
            return setLayout$default(this, i, (OnInvokeView) null, 2, (Object) null);
        }

        @NotNull
        public final Builder setLayout(@NotNull View view) {
            k.e(view, "layoutView");
            return setLayout$default(this, view, (OnInvokeView) null, 2, (Object) null);
        }

        public Builder(@NotNull Context activity2) {
            Context context = activity2;
            k.e(context, "activity");
            this.activity = context;
        }

        @NotNull
        public final Builder setSidePattern(@NotNull SidePattern sidePattern) {
            k.e(sidePattern, "sidePattern");
            this.config.setSidePattern(sidePattern);
            return this;
        }

        @NotNull
        public final Builder setShowPattern(@NotNull ShowPattern showPattern) {
            k.e(showPattern, "showPattern");
            this.config.setShowPattern(showPattern);
            return this;
        }

        public static /* synthetic */ Builder setLayout$default(Builder builder, int i, OnInvokeView onInvokeView, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                onInvokeView = null;
            }
            return builder.setLayout(i, onInvokeView);
        }

        @NotNull
        public final Builder setLayout(int layoutId, @Nullable OnInvokeView invokeView) {
            this.config.setLayoutId(Integer.valueOf(layoutId));
            this.config.setInvokeView(invokeView);
            return this;
        }

        public static /* synthetic */ Builder setLayout$default(Builder builder, View view, OnInvokeView onInvokeView, int i, Object obj) {
            if ((i & 2) != 0) {
                onInvokeView = null;
            }
            return builder.setLayout(view, onInvokeView);
        }

        @NotNull
        public final Builder setLayout(@NotNull View layoutView, @Nullable OnInvokeView invokeView) {
            k.e(layoutView, "layoutView");
            this.config.setLayoutView(layoutView);
            this.config.setInvokeView(invokeView);
            return this;
        }

        public static /* synthetic */ Builder setGravity$default(Builder builder, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 2) != 0) {
                i2 = 0;
            }
            if ((i4 & 4) != 0) {
                i3 = 0;
            }
            return builder.setGravity(i, i2, i3);
        }

        @NotNull
        public final Builder setGravity(int gravity, int offsetX, int offsetY) {
            this.config.setGravity(gravity);
            this.config.setOffsetPair(new n(Integer.valueOf(offsetX), Integer.valueOf(offsetY)));
            return this;
        }

        @NotNull
        public final Builder setLayoutChangedGravity(int gravity) {
            this.config.setLayoutChangedGravity(gravity);
            return this;
        }

        @NotNull
        public final Builder setLocation(int x, int y) {
            this.config.setLocationPair(new n(Integer.valueOf(x), Integer.valueOf(y)));
            return this;
        }

        public static /* synthetic */ Builder setBorder$default(Builder builder, int i, int i2, int i3, int i4, int i5, Object obj) {
            if ((i5 & 1) != 0) {
                i = 0;
            }
            if ((i5 & 2) != 0) {
                i2 = -DisplayUtils.INSTANCE.getStatusBarHeight(builder.activity);
            }
            if ((i5 & 4) != 0) {
                i3 = DisplayUtils.INSTANCE.getScreenWidth(builder.activity);
            }
            if ((i5 & 8) != 0) {
                i4 = DisplayUtils.INSTANCE.getScreenHeight(builder.activity);
            }
            return builder.setBorder(i, i2, i3, i4);
        }

        @NotNull
        public final Builder setBorder(int left, int top, int right, int bottom) {
            this.config.setLeftBorder(left);
            this.config.setTopBorder(top);
            this.config.setRightBorder(right);
            this.config.setBottomBorder(bottom);
            return this;
        }

        @NotNull
        public final Builder setTag(@Nullable String floatTag) {
            this.config.setFloatTag(floatTag);
            return this;
        }

        @NotNull
        public final Builder setDragEnable(boolean dragEnable) {
            this.config.setDragEnable(dragEnable);
            return this;
        }

        @NotNull
        public final Builder setImmersionStatusBar(boolean immersionStatusBar) {
            this.config.setImmersionStatusBar(immersionStatusBar);
            return this;
        }

        @NotNull
        public final Builder hasEditText(boolean hasEditText) {
            this.config.setHasEditText(hasEditText);
            return this;
        }

        @NotNull
        public final Builder registerCallbacks(@NotNull OnFloatCallbacks callbacks) {
            k.e(callbacks, "callbacks");
            this.config.setCallbacks(callbacks);
            return this;
        }

        @NotNull
        public final Builder registerCallback(@NotNull l<? super FloatCallbacks.Builder, x> builder) {
            k.e(builder, "builder");
            FloatConfig floatConfig = this.config;
            FloatCallbacks $this$registerCallback_u24lambda_u2d14_u24lambda_u2d13 = new FloatCallbacks();
            $this$registerCallback_u24lambda_u2d14_u24lambda_u2d13.registerListener(builder);
            x xVar = x.a;
            floatConfig.setFloatCallbacks($this$registerCallback_u24lambda_u2d14_u24lambda_u2d13);
            return this;
        }

        @NotNull
        public final Builder setAnimator(@Nullable OnFloatAnimator floatAnimator) {
            this.config.setFloatAnimator(floatAnimator);
            return this;
        }

        @NotNull
        public final Builder setDisplayHeight(@NotNull OnDisplayHeight displayHeight) {
            k.e(displayHeight, "displayHeight");
            this.config.setDisplayHeight(displayHeight);
            return this;
        }

        public static /* synthetic */ Builder setMatchParent$default(Builder builder, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                z = false;
            }
            if ((i & 2) != 0) {
                z2 = false;
            }
            return builder.setMatchParent(z, z2);
        }

        @NotNull
        public final Builder setMatchParent(boolean widthMatch, boolean heightMatch) {
            this.config.setWidthMatch(widthMatch);
            this.config.setHeightMatch(heightMatch);
            return this;
        }

        @NotNull
        public final Builder setFilter(@NotNull Class<?>... clazz) {
            k.e(clazz, "clazz");
            for (Class it : clazz) {
                Set<String> filterSet = this.config.getFilterSet();
                String name = it.getName();
                k.d(name, "it.name");
                filterSet.add(name);
                if ((this.activity instanceof Activity) && k.a(it.getName(), ((Activity) this.activity).getComponentName().getClassName())) {
                    this.config.setFilterSelf$easyfloat_release(true);
                }
            }
            return this;
        }

        public final void show() {
            if (this.config.getLayoutId() == null && this.config.getLayoutView() == null) {
                callbackCreateFailed(EasyFloatMessageKt.WARN_NO_LAYOUT);
            } else if (this.config.getShowPattern() == ShowPattern.CURRENT_ACTIVITY) {
                createFloat();
            } else if (PermissionUtils.checkPermission(this.activity)) {
                createFloat();
            } else {
                requestPermission();
            }
        }

        private final void createFloat() {
            FloatingWindowManager.INSTANCE.create(this.activity, this.config);
        }

        private final void requestPermission() {
            Context context = this.activity;
            if (context instanceof Activity) {
                PermissionUtils.requestPermission((Activity) context, this);
            } else {
                callbackCreateFailed(EasyFloatMessageKt.WARN_CONTEXT_REQUEST);
            }
        }

        public void permissionResult(boolean isOpen) {
            if (isOpen) {
                createFloat();
            } else {
                callbackCreateFailed(EasyFloatMessageKt.WARN_PERMISSION);
            }
        }

        private final void callbackCreateFailed(String reason) {
            FloatCallbacks.Builder builder;
            q<Boolean, String, View, x> createdResult$easyfloat_release;
            OnFloatCallbacks callbacks = this.config.getCallbacks();
            if (callbacks != null) {
                callbacks.createdResult(false, reason, (View) null);
            }
            FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
            if (!(floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) == null)) {
                createdResult$easyfloat_release.invoke(false, reason, null);
            }
            Logger.INSTANCE.w(reason);
            switch (reason.hashCode()) {
                case 324317221:
                    if (!reason.equals(EasyFloatMessageKt.WARN_CONTEXT_ACTIVITY)) {
                        return;
                    }
                    break;
                case 832581388:
                    if (!reason.equals(EasyFloatMessageKt.WARN_NO_LAYOUT)) {
                        return;
                    }
                    break;
                case 952571600:
                    if (!reason.equals(EasyFloatMessageKt.WARN_UNINITIALIZED)) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            throw new Exception(reason);
        }
    }
}
