package com.lzf.easyfloat.utils;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.google.android.material.badge.BadgeDrawable;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.R;
import com.lzf.easyfloat.anim.DefaultAnimator;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import com.lzf.easyfloat.widget.BaseSwitchView;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DragUtils.kt */
public final class DragUtils {
    @NotNull
    private static final String ADD_TAG = "ADD_TAG";
    @NotNull
    private static final String CLOSE_TAG = "CLOSE_TAG";
    @NotNull
    public static final DragUtils INSTANCE = new DragUtils();
    /* access modifiers changed from: private */
    @Nullable
    public static BaseSwitchView addView;
    /* access modifiers changed from: private */
    @Nullable
    public static BaseSwitchView closeView;
    private static float downX;
    private static float offset;
    private static int screenWidth;

    public final void registerDragClose(@NotNull MotionEvent motionEvent) {
        k.e(motionEvent, NotificationCompat.CATEGORY_EVENT);
        registerDragClose$default(this, motionEvent, (OnTouchRangeListener) null, 0, (ShowPattern) null, (OnFloatAnimator) null, 30, (Object) null);
    }

    public final void registerDragClose(@NotNull MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener) {
        k.e(motionEvent, NotificationCompat.CATEGORY_EVENT);
        registerDragClose$default(this, motionEvent, onTouchRangeListener, 0, (ShowPattern) null, (OnFloatAnimator) null, 28, (Object) null);
    }

    public final void registerDragClose(@NotNull MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener, int i) {
        k.e(motionEvent, NotificationCompat.CATEGORY_EVENT);
        registerDragClose$default(this, motionEvent, onTouchRangeListener, i, (ShowPattern) null, (OnFloatAnimator) null, 24, (Object) null);
    }

    public final void registerDragClose(@NotNull MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener, int i, @NotNull ShowPattern showPattern) {
        k.e(motionEvent, NotificationCompat.CATEGORY_EVENT);
        k.e(showPattern, "showPattern");
        registerDragClose$default(this, motionEvent, onTouchRangeListener, i, showPattern, (OnFloatAnimator) null, 16, (Object) null);
    }

    public final void registerSwipeAdd(@Nullable MotionEvent motionEvent) {
        registerSwipeAdd$default(this, motionEvent, (OnTouchRangeListener) null, 0, 0.0f, 0.0f, 0.0f, 62, (Object) null);
    }

    public final void registerSwipeAdd(@Nullable MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener) {
        registerSwipeAdd$default(this, motionEvent, onTouchRangeListener, 0, 0.0f, 0.0f, 0.0f, 60, (Object) null);
    }

    public final void registerSwipeAdd(@Nullable MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener, int i) {
        registerSwipeAdd$default(this, motionEvent, onTouchRangeListener, i, 0.0f, 0.0f, 0.0f, 56, (Object) null);
    }

    public final void registerSwipeAdd(@Nullable MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener, int i, float f) {
        registerSwipeAdd$default(this, motionEvent, onTouchRangeListener, i, f, 0.0f, 0.0f, 48, (Object) null);
    }

    public final void registerSwipeAdd(@Nullable MotionEvent motionEvent, @Nullable OnTouchRangeListener onTouchRangeListener, int i, float f, float f2) {
        registerSwipeAdd$default(this, motionEvent, onTouchRangeListener, i, f, f2, 0.0f, 32, (Object) null);
    }

    private DragUtils() {
    }

    public static /* synthetic */ void registerSwipeAdd$default(DragUtils dragUtils, MotionEvent motionEvent, OnTouchRangeListener onTouchRangeListener, int i, float f, float f2, float f3, int i2, Object obj) {
        OnTouchRangeListener onTouchRangeListener2;
        int i3;
        float f4;
        if ((i2 & 2) != 0) {
            onTouchRangeListener2 = null;
        } else {
            onTouchRangeListener2 = onTouchRangeListener;
        }
        if ((i2 & 4) != 0) {
            i3 = R.layout.default_add_layout;
        } else {
            i3 = i;
        }
        if ((i2 & 8) != 0) {
            f4 = -1.0f;
        } else {
            f4 = f;
        }
        dragUtils.registerSwipeAdd(motionEvent, onTouchRangeListener2, i3, f4, (i2 & 16) != 0 ? 0.1f : f2, (i2 & 32) != 0 ? 0.5f : f3);
    }

    public final void registerSwipeAdd(@Nullable MotionEvent event, @Nullable OnTouchRangeListener listener, int layoutId, float slideOffset, float start, float end) {
        if (event != null) {
            if (slideOffset == -1.0f) {
                screenWidth = DisplayUtils.INSTANCE.getScreenWidth(LifecycleUtils.INSTANCE.getApplication());
                offset = event.getRawX() / ((float) screenWidth);
                switch (event.getAction()) {
                    case 0:
                        downX = event.getRawX();
                        return;
                    case 1:
                    case 3:
                        downX = 0.0f;
                        setAddView(event, offset, listener, layoutId);
                        return;
                    case 2:
                        if (downX < ((float) screenWidth) * start) {
                            float f = offset;
                            if (f >= start) {
                                setAddView(event, Math.min((f - start) / (end - start), 1.0f), listener, layoutId);
                                return;
                            }
                        }
                        dismissAdd();
                        return;
                    default:
                        return;
                }
            } else if (slideOffset >= start) {
                setAddView(event, Math.min((slideOffset - start) / (end - start), 1.0f), listener, layoutId);
            } else {
                dismissAdd();
            }
        }
    }

    static /* synthetic */ void setAddView$default(DragUtils dragUtils, MotionEvent motionEvent, float f, OnTouchRangeListener onTouchRangeListener, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            onTouchRangeListener = null;
        }
        dragUtils.setAddView(motionEvent, f, onTouchRangeListener, i);
    }

    private final void setAddView(MotionEvent event, float progress, OnTouchRangeListener listener, int layoutId) {
        BaseSwitchView it = addView;
        if (it != null) {
            it.setTouchRangeListener(event, listener);
            float f = (float) 1;
            it.setTranslationX(((float) it.getWidth()) * (f - progress));
            it.setTranslationY(((float) it.getWidth()) * (f - progress));
        }
        if (event.getAction() == 1 || event.getAction() == 3) {
            dismissAdd();
        } else {
            showAdd(layoutId);
        }
    }

    private final void showAdd(int layoutId) {
        EasyFloat.Companion companion = EasyFloat.Companion;
        if (!companion.isShow(ADD_TAG)) {
            EasyFloat.Builder.setGravity$default(EasyFloat.Builder.setLayout$default(companion.with(LifecycleUtils.INSTANCE.getApplication()), layoutId, (OnInvokeView) null, 2, (Object) null).setShowPattern(ShowPattern.CURRENT_ACTIVITY).setTag(ADD_TAG).setDragEnable(false).setSidePattern(SidePattern.BOTTOM), BadgeDrawable.BOTTOM_END, 0, 0, 6, (Object) null).setAnimator((OnFloatAnimator) null).registerCallback(DragUtils$showAdd$1.INSTANCE).show();
        }
    }

    public static /* synthetic */ void registerDragClose$default(DragUtils dragUtils, MotionEvent motionEvent, OnTouchRangeListener onTouchRangeListener, int i, ShowPattern showPattern, OnFloatAnimator onFloatAnimator, int i2, Object obj) {
        OnTouchRangeListener onTouchRangeListener2;
        int i3;
        ShowPattern showPattern2;
        if ((i2 & 2) != 0) {
            onTouchRangeListener2 = null;
        } else {
            onTouchRangeListener2 = onTouchRangeListener;
        }
        if ((i2 & 4) != 0) {
            i3 = R.layout.default_close_layout;
        } else {
            i3 = i;
        }
        if ((i2 & 8) != 0) {
            showPattern2 = ShowPattern.CURRENT_ACTIVITY;
        } else {
            showPattern2 = showPattern;
        }
        dragUtils.registerDragClose(motionEvent, onTouchRangeListener2, i3, showPattern2, (i2 & 16) != 0 ? new DefaultAnimator() : onFloatAnimator);
    }

    public final void registerDragClose(@NotNull MotionEvent event, @Nullable OnTouchRangeListener listener, int layoutId, @NotNull ShowPattern showPattern, @Nullable OnFloatAnimator appFloatAnimator) {
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        k.e(showPattern, "showPattern");
        showClose(layoutId, showPattern, appFloatAnimator);
        BaseSwitchView baseSwitchView = closeView;
        if (baseSwitchView != null) {
            baseSwitchView.setTouchRangeListener(event, listener);
        }
        if (event.getAction() == 1 || event.getAction() == 3) {
            dismissClose();
        }
    }

    private final void showClose(int layoutId, ShowPattern showPattern, OnFloatAnimator appFloatAnimator) {
        EasyFloat.Companion companion = EasyFloat.Companion;
        if (!companion.isShow(CLOSE_TAG)) {
            EasyFloat.Builder.setGravity$default(EasyFloat.Builder.setMatchParent$default(EasyFloat.Builder.setLayout$default(companion.with(LifecycleUtils.INSTANCE.getApplication()), layoutId, (OnInvokeView) null, 2, (Object) null).setShowPattern(showPattern), true, false, 2, (Object) null).setTag(CLOSE_TAG).setSidePattern(SidePattern.BOTTOM), 80, 0, 0, 6, (Object) null).setAnimator(appFloatAnimator).registerCallback(DragUtils$showClose$1.INSTANCE).show();
        }
    }

    private final x dismissAdd() {
        return EasyFloat.Companion.dismiss$default(EasyFloat.Companion, ADD_TAG, false, 2, (Object) null);
    }

    private final x dismissClose() {
        return EasyFloat.Companion.dismiss$default(EasyFloat.Companion, CLOSE_TAG, false, 2, (Object) null);
    }
}
