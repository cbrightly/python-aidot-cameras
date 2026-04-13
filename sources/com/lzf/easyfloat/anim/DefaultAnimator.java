package com.lzf.easyfloat.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.utils.DisplayUtils;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.s;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultAnimator.kt */
public class DefaultAnimator implements OnFloatAnimator {

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
    /* compiled from: DefaultAnimator.kt */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SidePattern.values().length];
            iArr[SidePattern.LEFT.ordinal()] = 1;
            iArr[SidePattern.RESULT_LEFT.ordinal()] = 2;
            iArr[SidePattern.RIGHT.ordinal()] = 3;
            iArr[SidePattern.RESULT_RIGHT.ordinal()] = 4;
            iArr[SidePattern.TOP.ordinal()] = 5;
            iArr[SidePattern.RESULT_TOP.ordinal()] = 6;
            iArr[SidePattern.BOTTOM.ordinal()] = 7;
            iArr[SidePattern.RESULT_BOTTOM.ordinal()] = 8;
            iArr[SidePattern.DEFAULT.ordinal()] = 9;
            iArr[SidePattern.AUTO_HORIZONTAL.ordinal()] = 10;
            iArr[SidePattern.RESULT_HORIZONTAL.ordinal()] = 11;
            iArr[SidePattern.AUTO_VERTICAL.ordinal()] = 12;
            iArr[SidePattern.RESULT_VERTICAL.ordinal()] = 13;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Nullable
    public Animator enterAnim(@NotNull View view, @NotNull WindowManager.LayoutParams params, @NotNull WindowManager windowManager, @NotNull SidePattern sidePattern) {
        k.e(view, "view");
        k.e(params, "params");
        k.e(windowManager, "windowManager");
        k.e(sidePattern, "sidePattern");
        return getAnimator(view, params, windowManager, sidePattern, false);
    }

    @Nullable
    public Animator exitAnim(@NotNull View view, @NotNull WindowManager.LayoutParams params, @NotNull WindowManager windowManager, @NotNull SidePattern sidePattern) {
        k.e(view, "view");
        k.e(params, "params");
        k.e(windowManager, "windowManager");
        k.e(sidePattern, "sidePattern");
        return getAnimator(view, params, windowManager, sidePattern, true);
    }

    private final Animator getAnimator(View view, WindowManager.LayoutParams params, WindowManager windowManager, SidePattern sidePattern, boolean isExit) {
        s triple = initValue(view, params, windowManager, sidePattern);
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{((Number) (isExit ? triple.getSecond() : triple.getFirst())).intValue(), ((Number) (isExit ? triple.getFirst() : triple.getSecond())).intValue()});
        ValueAnimator $this$getAnimator_u24lambda_u2d1 = ofInt;
        $this$getAnimator_u24lambda_u2d1.addUpdateListener(new a(triple, params, windowManager, view, $this$getAnimator_u24lambda_u2d1));
        k.d(ofInt, "ofInt(start, end).apply …}\n            }\n        }");
        return ofInt;
    }

    /* access modifiers changed from: private */
    /* renamed from: getAnimator$lambda-1$lambda-0  reason: not valid java name */
    public static final void m8getAnimator$lambda1$lambda0(s $triple, WindowManager.LayoutParams $params, WindowManager $windowManager, View $view, ValueAnimator $this_apply, ValueAnimator it) {
        k.e($triple, "$triple");
        k.e($params, "$params");
        k.e($windowManager, "$windowManager");
        k.e($view, "$view");
        try {
            Object animatedValue = it.getAnimatedValue();
            if (animatedValue != null) {
                int value = ((Integer) animatedValue).intValue();
                if (((Boolean) $triple.getThird()).booleanValue()) {
                    $params.x = value;
                } else {
                    $params.y = value;
                }
                $windowManager.updateViewLayout($view, $params);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
        } catch (Exception e) {
            $this_apply.cancel();
        }
    }

    private final s<Integer, Integer, Boolean> initValue(View view, WindowManager.LayoutParams params, WindowManager windowManager, SidePattern sidePattern) {
        int startValue;
        int endValue;
        boolean isHorizontal;
        WindowManager.LayoutParams layoutParams = params;
        Rect parentRect = new Rect();
        windowManager.getDefaultDisplay().getRectSize(parentRect);
        int leftDistance = layoutParams.x;
        int rightDistance = parentRect.right - (view.getRight() + leftDistance);
        int topDistance = layoutParams.y;
        int bottomDistance = parentRect.bottom - (view.getBottom() + topDistance);
        int minX = Math.min(leftDistance, rightDistance);
        int minY = Math.min(topDistance, bottomDistance);
        switch (WhenMappings.$EnumSwitchMapping$0[sidePattern.ordinal()]) {
            case 1:
            case 2:
                isHorizontal = true;
                endValue = layoutParams.x;
                startValue = -view.getRight();
                break;
            case 3:
            case 4:
                isHorizontal = true;
                endValue = layoutParams.x;
                startValue = parentRect.right;
                break;
            case 5:
            case 6:
                isHorizontal = false;
                endValue = layoutParams.y;
                startValue = -view.getBottom();
                break;
            case 7:
            case 8:
                isHorizontal = false;
                endValue = layoutParams.y;
                startValue = parentRect.bottom + getCompensationHeight(view, params);
                break;
            case 9:
            case 10:
            case 11:
                isHorizontal = true;
                endValue = layoutParams.x;
                if (leftDistance >= rightDistance) {
                    startValue = parentRect.right;
                    break;
                } else {
                    startValue = -view.getRight();
                    break;
                }
            case 12:
            case 13:
                isHorizontal = false;
                endValue = layoutParams.y;
                if (topDistance >= bottomDistance) {
                    startValue = parentRect.bottom + getCompensationHeight(view, params);
                    break;
                } else {
                    startValue = -view.getBottom();
                    break;
                }
            default:
                if (minX > minY) {
                    isHorizontal = false;
                    endValue = layoutParams.y;
                    if (topDistance >= bottomDistance) {
                        startValue = parentRect.bottom + getCompensationHeight(view, params);
                        break;
                    } else {
                        startValue = -view.getBottom();
                        break;
                    }
                } else {
                    isHorizontal = true;
                    endValue = layoutParams.x;
                    if (leftDistance >= rightDistance) {
                        startValue = parentRect.right;
                        break;
                    } else {
                        startValue = -view.getRight();
                        break;
                    }
                }
        }
        return new s<>(Integer.valueOf(startValue), Integer.valueOf(endValue), Boolean.valueOf(isHorizontal));
    }

    private final int getCompensationHeight(View view, WindowManager.LayoutParams params) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (location[1] == params.y) {
            return DisplayUtils.INSTANCE.statusBarHeight(view);
        }
        return 0;
    }
}
