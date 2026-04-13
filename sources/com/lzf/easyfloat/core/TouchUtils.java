package com.lzf.easyfloat.core;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.utils.DisplayUtils;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: TouchUtils.kt */
public final class TouchUtils {
    private int bottomBorder;
    private int bottomDistance;
    @NotNull
    private final FloatConfig config;
    @NotNull
    private final Context context;
    private int emptyHeight;
    private float lastX;
    private float lastY;
    private int leftBorder;
    private int leftDistance;
    @NotNull
    private final int[] location = new int[2];
    private int minX;
    private int minY;
    private int parentHeight;
    @NotNull
    private Rect parentRect = new Rect();
    private int parentWidth;
    private int rightBorder;
    private int rightDistance;
    private int statusBarHeight;
    private int topBorder;
    private int topDistance;

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
    /* compiled from: TouchUtils.kt */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SidePattern.values().length];
            iArr[SidePattern.LEFT.ordinal()] = 1;
            iArr[SidePattern.RIGHT.ordinal()] = 2;
            iArr[SidePattern.TOP.ordinal()] = 3;
            iArr[SidePattern.BOTTOM.ordinal()] = 4;
            iArr[SidePattern.AUTO_HORIZONTAL.ordinal()] = 5;
            iArr[SidePattern.AUTO_VERTICAL.ordinal()] = 6;
            iArr[SidePattern.AUTO_SIDE.ordinal()] = 7;
            iArr[SidePattern.RESULT_LEFT.ordinal()] = 8;
            iArr[SidePattern.RESULT_RIGHT.ordinal()] = 9;
            iArr[SidePattern.RESULT_TOP.ordinal()] = 10;
            iArr[SidePattern.RESULT_BOTTOM.ordinal()] = 11;
            iArr[SidePattern.RESULT_HORIZONTAL.ordinal()] = 12;
            iArr[SidePattern.RESULT_VERTICAL.ordinal()] = 13;
            iArr[SidePattern.RESULT_SIDE.ordinal()] = 14;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TouchUtils(@NotNull Context context2, @NotNull FloatConfig config2) {
        k.e(context2, "context");
        k.e(config2, "config");
        this.context = context2;
        this.config = config2;
    }

    @NotNull
    public final FloatConfig getConfig() {
        return this.config;
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0107, code lost:
        if (r4 > r5) goto L_0x010c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateFloat(@org.jetbrains.annotations.NotNull android.view.View r9, @org.jetbrains.annotations.NotNull android.view.MotionEvent r10, @org.jetbrains.annotations.NotNull android.view.WindowManager r11, @org.jetbrains.annotations.NotNull android.view.WindowManager.LayoutParams r12) {
        /*
            r8 = this;
            java.lang.String r0 = "view"
            kotlin.jvm.internal.k.e(r9, r0)
            java.lang.String r0 = "event"
            kotlin.jvm.internal.k.e(r10, r0)
            java.lang.String r0 = "windowManager"
            kotlin.jvm.internal.k.e(r11, r0)
            java.lang.String r0 = "params"
            kotlin.jvm.internal.k.e(r12, r0)
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.interfaces.OnFloatCallbacks r0 = r0.getCallbacks()
            if (r0 != 0) goto L_0x0020
            goto L_0x0023
        L_0x0020:
            r0.touchEvent(r9, r10)
        L_0x0023:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.interfaces.FloatCallbacks r0 = r0.getFloatCallbacks()
            if (r0 != 0) goto L_0x002c
        L_0x002b:
            goto L_0x003d
        L_0x002c:
            com.lzf.easyfloat.interfaces.FloatCallbacks$Builder r0 = r0.getBuilder()
            if (r0 != 0) goto L_0x0033
            goto L_0x002b
        L_0x0033:
            kotlin.jvm.functions.p r0 = r0.getTouchEvent$easyfloat_release()
            if (r0 != 0) goto L_0x003a
            goto L_0x002b
        L_0x003a:
            r0.invoke(r9, r10)
        L_0x003d:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            boolean r0 = r0.getDragEnable()
            r1 = 0
            if (r0 == 0) goto L_0x0275
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            boolean r0 = r0.isAnim()
            if (r0 == 0) goto L_0x0050
            goto L_0x0275
        L_0x0050:
            int r0 = r10.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            switch(r0) {
                case 0: goto L_0x0260;
                case 1: goto L_0x01f5;
                case 2: goto L_0x005a;
                case 3: goto L_0x01f5;
                default: goto L_0x0059;
            }
        L_0x0059:
            return
        L_0x005a:
            float r0 = r10.getRawX()
            int r2 = r8.leftBorder
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x01f4
            float r0 = r10.getRawX()
            int r2 = r8.rightBorder
            int r3 = r9.getWidth()
            int r2 = r2 + r3
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x01f4
            float r0 = r10.getRawY()
            int r2 = r8.topBorder
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x01f4
            float r0 = r10.getRawY()
            int r2 = r8.bottomBorder
            int r3 = r9.getHeight()
            int r2 = r2 + r3
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0092
            goto L_0x01f4
        L_0x0092:
            float r0 = r10.getRawX()
            float r2 = r8.lastX
            float r0 = r0 - r2
            float r2 = r10.getRawY()
            float r3 = r8.lastY
            float r2 = r2 - r3
            com.lzf.easyfloat.data.FloatConfig r3 = r8.config
            boolean r3 = r3.isDrag()
            if (r3 != 0) goto L_0x00b4
            float r3 = r0 * r0
            float r4 = r2 * r2
            float r3 = r3 + r4
            r4 = 1117913088(0x42a20000, float:81.0)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x00b4
            return
        L_0x00b4:
            com.lzf.easyfloat.data.FloatConfig r3 = r8.config
            r4 = 1
            r3.setDrag(r4)
            int r3 = r12.x
            int r4 = (int) r0
            int r3 = r3 + r4
            int r4 = r12.y
            int r5 = (int) r2
            int r4 = r4 + r5
            int r5 = r8.leftBorder
            if (r3 >= r5) goto L_0x00c8
            goto L_0x00ce
        L_0x00c8:
            int r5 = r8.rightBorder
            if (r3 <= r5) goto L_0x00cd
            goto L_0x00ce
        L_0x00cd:
            r5 = r3
        L_0x00ce:
            r3 = r5
            com.lzf.easyfloat.data.FloatConfig r5 = r8.config
            com.lzf.easyfloat.enums.ShowPattern r5 = r5.getShowPattern()
            com.lzf.easyfloat.enums.ShowPattern r6 = com.lzf.easyfloat.enums.ShowPattern.CURRENT_ACTIVITY
            if (r5 != r6) goto L_0x00ec
            int r5 = r8.statusBarHeight(r9)
            if (r4 >= r5) goto L_0x00ec
            com.lzf.easyfloat.data.FloatConfig r5 = r8.config
            boolean r5 = r5.getImmersionStatusBar()
            if (r5 != 0) goto L_0x00ec
            int r5 = r8.statusBarHeight(r9)
            r4 = r5
        L_0x00ec:
            int r5 = r8.topBorder
            if (r4 >= r5) goto L_0x00f2
            goto L_0x010c
        L_0x00f2:
            if (r4 >= 0) goto L_0x0105
            com.lzf.easyfloat.data.FloatConfig r5 = r8.config
            boolean r5 = r5.getImmersionStatusBar()
            if (r5 == 0) goto L_0x0103
            int r5 = r8.statusBarHeight
            int r6 = -r5
            if (r4 >= r6) goto L_0x010b
            int r5 = -r5
            goto L_0x010c
        L_0x0103:
            r5 = r1
            goto L_0x010c
        L_0x0105:
            int r5 = r8.bottomBorder
            if (r4 <= r5) goto L_0x010a
            goto L_0x010c
        L_0x010a:
        L_0x010b:
            r5 = r4
        L_0x010c:
            r4 = r5
            com.lzf.easyfloat.data.FloatConfig r5 = r8.config
            com.lzf.easyfloat.enums.SidePattern r5 = r5.getSidePattern()
            int[] r6 = com.lzf.easyfloat.core.TouchUtils.WhenMappings.$EnumSwitchMapping$0
            int r5 = r5.ordinal()
            r5 = r6[r5]
            r6 = 2
            switch(r5) {
                case 1: goto L_0x01b8;
                case 2: goto L_0x01af;
                case 3: goto L_0x01ad;
                case 4: goto L_0x01aa;
                case 5: goto L_0x0195;
                case 6: goto L_0x0178;
                case 7: goto L_0x0121;
                default: goto L_0x011f;
            }
        L_0x011f:
            goto L_0x01b9
        L_0x0121:
            float r5 = r10.getRawX()
            int r5 = (int) r5
            r8.leftDistance = r5
            int r5 = r8.parentWidth
            float r6 = r10.getRawX()
            int r6 = (int) r6
            int r5 = r5 - r6
            r8.rightDistance = r5
            float r5 = r10.getRawY()
            int r5 = (int) r5
            android.graphics.Rect r6 = r8.parentRect
            int r6 = r6.top
            int r5 = r5 - r6
            r8.topDistance = r5
            int r5 = r8.parentHeight
            int r5 = r5 + r6
            float r6 = r10.getRawY()
            int r6 = (int) r6
            int r5 = r5 - r6
            r8.bottomDistance = r5
            int r5 = r8.leftDistance
            int r6 = r8.rightDistance
            int r5 = java.lang.Math.min(r5, r6)
            r8.minX = r5
            int r5 = r8.topDistance
            int r6 = r8.bottomDistance
            int r5 = java.lang.Math.min(r5, r6)
            r8.minY = r5
            int r6 = r8.minX
            if (r6 >= r5) goto L_0x016f
            int r5 = r8.leftDistance
            if (r5 != r6) goto L_0x0166
            goto L_0x016d
        L_0x0166:
            int r1 = r8.parentWidth
            int r5 = r9.getWidth()
            int r1 = r1 - r5
        L_0x016d:
            r3 = r1
            goto L_0x01b9
        L_0x016f:
            int r6 = r8.topDistance
            if (r6 != r5) goto L_0x0174
            goto L_0x0176
        L_0x0174:
            int r1 = r8.emptyHeight
        L_0x0176:
            r4 = r1
            goto L_0x01b9
        L_0x0178:
            float r5 = r10.getRawY()
            android.graphics.Rect r7 = r8.parentRect
            int r7 = r7.top
            float r7 = (float) r7
            float r5 = r5 - r7
            float r6 = (float) r6
            float r5 = r5 * r6
            int r6 = r8.parentHeight
            float r7 = (float) r6
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L_0x0192
            int r1 = r9.getHeight()
            int r1 = r6 - r1
            goto L_0x0193
        L_0x0192:
        L_0x0193:
            r4 = r1
            goto L_0x01b9
        L_0x0195:
            float r5 = r10.getRawX()
            float r6 = (float) r6
            float r5 = r5 * r6
            int r6 = r8.parentWidth
            float r7 = (float) r6
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L_0x01a8
            int r1 = r9.getWidth()
            int r1 = r6 - r1
        L_0x01a8:
            r3 = r1
            goto L_0x01b9
        L_0x01aa:
            int r4 = r8.emptyHeight
            goto L_0x01b9
        L_0x01ad:
            r4 = 0
            goto L_0x01b9
        L_0x01af:
            int r1 = r8.parentWidth
            int r5 = r9.getWidth()
            int r3 = r1 - r5
            goto L_0x01b9
        L_0x01b8:
            r3 = 0
        L_0x01b9:
            r12.x = r3
            r12.y = r4
            r11.updateViewLayout(r9, r12)
            com.lzf.easyfloat.data.FloatConfig r1 = r8.config
            com.lzf.easyfloat.interfaces.OnFloatCallbacks r1 = r1.getCallbacks()
            if (r1 != 0) goto L_0x01c9
            goto L_0x01cc
        L_0x01c9:
            r1.drag(r9, r10)
        L_0x01cc:
            com.lzf.easyfloat.data.FloatConfig r1 = r8.config
            com.lzf.easyfloat.interfaces.FloatCallbacks r1 = r1.getFloatCallbacks()
            if (r1 != 0) goto L_0x01d5
        L_0x01d4:
            goto L_0x01e6
        L_0x01d5:
            com.lzf.easyfloat.interfaces.FloatCallbacks$Builder r1 = r1.getBuilder()
            if (r1 != 0) goto L_0x01dc
            goto L_0x01d4
        L_0x01dc:
            kotlin.jvm.functions.p r1 = r1.getDrag$easyfloat_release()
            if (r1 != 0) goto L_0x01e3
            goto L_0x01d4
        L_0x01e3:
            r1.invoke(r9, r10)
        L_0x01e6:
            float r1 = r10.getRawX()
            r8.lastX = r1
            float r1 = r10.getRawY()
            r8.lastY = r1
            goto L_0x0274
        L_0x01f4:
            return
        L_0x01f5:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            boolean r0 = r0.isDrag()
            if (r0 != 0) goto L_0x01fe
            return
        L_0x01fe:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.interfaces.OnFloatCallbacks r0 = r0.getCallbacks()
            if (r0 != 0) goto L_0x0207
            goto L_0x020a
        L_0x0207:
            r0.drag(r9, r10)
        L_0x020a:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.interfaces.FloatCallbacks r0 = r0.getFloatCallbacks()
            if (r0 != 0) goto L_0x0213
        L_0x0212:
            goto L_0x0224
        L_0x0213:
            com.lzf.easyfloat.interfaces.FloatCallbacks$Builder r0 = r0.getBuilder()
            if (r0 != 0) goto L_0x021a
            goto L_0x0212
        L_0x021a:
            kotlin.jvm.functions.p r0 = r0.getDrag$easyfloat_release()
            if (r0 != 0) goto L_0x0221
            goto L_0x0212
        L_0x0221:
            r0.invoke(r9, r10)
        L_0x0224:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.enums.SidePattern r0 = r0.getSidePattern()
            int[] r1 = com.lzf.easyfloat.core.TouchUtils.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r1[r0]
            switch(r0) {
                case 8: goto L_0x023e;
                case 9: goto L_0x023e;
                case 10: goto L_0x023e;
                case 11: goto L_0x023e;
                case 12: goto L_0x023e;
                case 13: goto L_0x023e;
                case 14: goto L_0x023e;
                default: goto L_0x0235;
            }
        L_0x0235:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.interfaces.OnFloatCallbacks r0 = r0.getCallbacks()
            if (r0 != 0) goto L_0x0242
            goto L_0x0245
        L_0x023e:
            r8.sideAnim(r9, r12, r11)
            goto L_0x0274
        L_0x0242:
            r0.dragEnd(r9)
        L_0x0245:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            com.lzf.easyfloat.interfaces.FloatCallbacks r0 = r0.getFloatCallbacks()
            if (r0 != 0) goto L_0x024e
        L_0x024d:
            goto L_0x0274
        L_0x024e:
            com.lzf.easyfloat.interfaces.FloatCallbacks$Builder r0 = r0.getBuilder()
            if (r0 != 0) goto L_0x0255
            goto L_0x024d
        L_0x0255:
            kotlin.jvm.functions.l r0 = r0.getDragEnd$easyfloat_release()
            if (r0 != 0) goto L_0x025c
            goto L_0x024d
        L_0x025c:
            r0.invoke(r9)
            goto L_0x0274
        L_0x0260:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            r0.setDrag(r1)
            float r0 = r10.getRawX()
            r8.lastX = r0
            float r0 = r10.getRawY()
            r8.lastY = r0
            r8.initBoarderValue(r9, r12)
        L_0x0274:
            return
        L_0x0275:
            com.lzf.easyfloat.data.FloatConfig r0 = r8.config
            r0.setDrag(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzf.easyfloat.core.TouchUtils.updateFloat(android.view.View, android.view.MotionEvent, android.view.WindowManager, android.view.WindowManager$LayoutParams):void");
    }

    public final void updateFloat(@NotNull View view, @NotNull WindowManager.LayoutParams params, @NotNull WindowManager windowManager) {
        k.e(view, "view");
        k.e(params, "params");
        k.e(windowManager, "windowManager");
        initBoarderValue(view, params);
        sideAnim(view, params, windowManager);
    }

    private final void initBoarderValue(View view, WindowManager.LayoutParams params) {
        int i;
        int i2;
        this.parentWidth = DisplayUtils.INSTANCE.getScreenWidth(this.context);
        this.parentHeight = this.config.getDisplayHeight().getDisplayRealHeight(this.context);
        view.getLocationOnScreen(this.location);
        this.statusBarHeight = this.location[1] > params.y ? statusBarHeight(view) : 0;
        this.emptyHeight = (this.parentHeight - view.getHeight()) - this.statusBarHeight;
        this.leftBorder = Math.max(0, this.config.getLeftBorder());
        this.rightBorder = Math.min(this.parentWidth, this.config.getRightBorder()) - view.getWidth();
        ShowPattern showPattern = this.config.getShowPattern();
        ShowPattern showPattern2 = ShowPattern.CURRENT_ACTIVITY;
        if (showPattern == showPattern2) {
            i = this.config.getImmersionStatusBar() ? this.config.getTopBorder() : this.config.getTopBorder() + statusBarHeight(view);
        } else {
            i = this.config.getImmersionStatusBar() ? this.config.getTopBorder() - statusBarHeight(view) : this.config.getTopBorder();
        }
        this.topBorder = i;
        if (this.config.getShowPattern() == showPattern2) {
            if (this.config.getImmersionStatusBar()) {
                i2 = Math.min(this.emptyHeight, this.config.getBottomBorder() - view.getHeight());
            } else {
                i2 = Math.min(this.emptyHeight, (this.config.getBottomBorder() + statusBarHeight(view)) - view.getHeight());
            }
        } else if (this.config.getImmersionStatusBar()) {
            i2 = Math.min(this.emptyHeight, (this.config.getBottomBorder() - statusBarHeight(view)) - view.getHeight());
        } else {
            i2 = Math.min(this.emptyHeight, this.config.getBottomBorder() - view.getHeight());
        }
        this.bottomBorder = i2;
    }

    private final void sideAnim(View view, WindowManager.LayoutParams params, WindowManager windowManager) {
        int end;
        boolean isX;
        initDistanceValue(params);
        switch (WhenMappings.$EnumSwitchMapping$0[this.config.getSidePattern().ordinal()]) {
            case 8:
                isX = true;
                end = this.leftBorder;
                break;
            case 9:
                isX = true;
                end = params.x + this.rightDistance;
                break;
            case 10:
                isX = false;
                end = this.topBorder;
                break;
            case 11:
                isX = false;
                end = this.bottomBorder;
                break;
            case 12:
                isX = true;
                int i = this.leftDistance;
                int i2 = this.rightDistance;
                if (i >= i2) {
                    end = params.x + i2;
                    break;
                } else {
                    end = this.leftBorder;
                    break;
                }
            case 13:
                isX = false;
                if (this.topDistance >= this.bottomDistance) {
                    end = this.bottomBorder;
                    break;
                } else {
                    end = this.topBorder;
                    break;
                }
            case 14:
                if (this.minX >= this.minY) {
                    isX = false;
                    if (this.topDistance >= this.bottomDistance) {
                        end = this.bottomBorder;
                        break;
                    } else {
                        end = this.topBorder;
                        break;
                    }
                } else {
                    isX = true;
                    int i3 = this.leftDistance;
                    int i4 = this.rightDistance;
                    if (i3 >= i4) {
                        end = params.x + i4;
                        break;
                    } else {
                        end = this.leftBorder;
                        break;
                    }
                }
            default:
                return;
        }
        int[] iArr = new int[2];
        iArr[0] = isX ? params.x : params.y;
        iArr[1] = end;
        ValueAnimator animator = ValueAnimator.ofInt(iArr);
        animator.addUpdateListener(new d(isX, params, windowManager, view, animator));
        animator.addListener(new TouchUtils$sideAnim$2(this, view));
        animator.start();
    }

    /* access modifiers changed from: private */
    /* renamed from: sideAnim$lambda-0  reason: not valid java name */
    public static final void m12sideAnim$lambda0(boolean $isX, WindowManager.LayoutParams $params, WindowManager $windowManager, View $view, ValueAnimator $animator, ValueAnimator it) {
        NullPointerException nullPointerException;
        k.e($params, "$params");
        k.e($windowManager, "$windowManager");
        k.e($view, "$view");
        if ($isX) {
            try {
                Object animatedValue = it.getAnimatedValue();
                if (animatedValue != null) {
                    $params.x = ((Integer) animatedValue).intValue();
                    $windowManager.updateViewLayout($view, $params);
                    return;
                }
                nullPointerException = new NullPointerException("null cannot be cast to non-null type kotlin.Int");
            } catch (Exception e) {
                $animator.cancel();
                return;
            }
        } else {
            Object animatedValue2 = it.getAnimatedValue();
            if (animatedValue2 != null) {
                $params.y = ((Integer) animatedValue2).intValue();
                $windowManager.updateViewLayout($view, $params);
                return;
            }
            nullPointerException = new NullPointerException("null cannot be cast to non-null type kotlin.Int");
        }
        throw nullPointerException;
    }

    /* access modifiers changed from: private */
    public final void dragEnd(View view) {
        FloatCallbacks.Builder builder;
        kotlin.jvm.functions.l<View, x> dragEnd$easyfloat_release;
        this.config.setAnim(false);
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.dragEnd(view);
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks != null && (builder = floatCallbacks.getBuilder()) != null && (dragEnd$easyfloat_release = builder.getDragEnd$easyfloat_release()) != null) {
            dragEnd$easyfloat_release.invoke(view);
        }
    }

    private final void initDistanceValue(WindowManager.LayoutParams params) {
        int i = params.x;
        int i2 = i - this.leftBorder;
        this.leftDistance = i2;
        int i3 = this.rightBorder - i;
        this.rightDistance = i3;
        int i4 = params.y;
        this.topDistance = i4 - this.topBorder;
        this.bottomDistance = this.bottomBorder - i4;
        this.minX = Math.min(i2, i3);
        this.minY = Math.min(this.topDistance, this.bottomDistance);
    }

    private final int statusBarHeight(View view) {
        return DisplayUtils.INSTANCE.statusBarHeight(view);
    }
}
