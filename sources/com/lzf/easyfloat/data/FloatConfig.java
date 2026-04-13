package com.lzf.easyfloat.data;

import android.view.View;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnDisplayHeight;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import net.sqlcipher.database.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FloatConfig.kt */
public final class FloatConfig {
    private int bottomBorder;
    @Nullable
    private OnFloatCallbacks callbacks;
    @NotNull
    private OnDisplayHeight displayHeight;
    private boolean dragEnable;
    private boolean filterSelf;
    @NotNull
    private final Set<String> filterSet;
    @Nullable
    private OnFloatAnimator floatAnimator;
    @Nullable
    private FloatCallbacks floatCallbacks;
    @Nullable
    private String floatTag;
    private int gravity;
    private boolean hasEditText;
    private boolean heightMatch;
    private boolean immersionStatusBar;
    @Nullable
    private OnInvokeView invokeView;
    private boolean isAnim;
    private boolean isDrag;
    private boolean isShow;
    private int layoutChangedGravity;
    @Nullable
    private Integer layoutId;
    @Nullable
    private View layoutView;
    private int leftBorder;
    @NotNull
    private n<Integer, Integer> locationPair;
    private boolean needShow;
    @NotNull
    private n<Integer, Integer> offsetPair;
    private int rightBorder;
    @NotNull
    private ShowPattern showPattern;
    @NotNull
    private SidePattern sidePattern;
    private int topBorder;
    private boolean widthMatch;

    public FloatConfig() {
        this((Integer) null, (View) null, (String) null, false, false, false, false, false, false, (SidePattern) null, (ShowPattern) null, false, false, 0, (n) null, (n) null, 0, 0, 0, 0, (OnInvokeView) null, (OnFloatCallbacks) null, (FloatCallbacks) null, (OnFloatAnimator) null, (OnDisplayHeight) null, (Set) null, false, false, 0, 536870911, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ FloatConfig copy$default(FloatConfig floatConfig, Integer num, View view, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, SidePattern sidePattern2, ShowPattern showPattern2, boolean z7, boolean z8, int i, n nVar, n nVar2, int i2, int i3, int i4, int i5, OnInvokeView onInvokeView, OnFloatCallbacks onFloatCallbacks, FloatCallbacks floatCallbacks2, OnFloatAnimator onFloatAnimator, OnDisplayHeight onDisplayHeight, Set set, boolean z9, boolean z10, int i6, int i7, Object obj) {
        FloatConfig floatConfig2 = floatConfig;
        int i8 = i7;
        return floatConfig.copy((i8 & 1) != 0 ? floatConfig2.layoutId : num, (i8 & 2) != 0 ? floatConfig2.layoutView : view, (i8 & 4) != 0 ? floatConfig2.floatTag : str, (i8 & 8) != 0 ? floatConfig2.dragEnable : z, (i8 & 16) != 0 ? floatConfig2.isDrag : z2, (i8 & 32) != 0 ? floatConfig2.isAnim : z3, (i8 & 64) != 0 ? floatConfig2.isShow : z4, (i8 & 128) != 0 ? floatConfig2.hasEditText : z5, (i8 & 256) != 0 ? floatConfig2.immersionStatusBar : z6, (i8 & 512) != 0 ? floatConfig2.sidePattern : sidePattern2, (i8 & 1024) != 0 ? floatConfig2.showPattern : showPattern2, (i8 & 2048) != 0 ? floatConfig2.widthMatch : z7, (i8 & 4096) != 0 ? floatConfig2.heightMatch : z8, (i8 & 8192) != 0 ? floatConfig2.gravity : i, (i8 & 16384) != 0 ? floatConfig2.offsetPair : nVar, (i8 & 32768) != 0 ? floatConfig2.locationPair : nVar2, (i8 & 65536) != 0 ? floatConfig2.leftBorder : i2, (i8 & 131072) != 0 ? floatConfig2.topBorder : i3, (i8 & 262144) != 0 ? floatConfig2.rightBorder : i4, (i8 & 524288) != 0 ? floatConfig2.bottomBorder : i5, (i8 & 1048576) != 0 ? floatConfig2.invokeView : onInvokeView, (i8 & 2097152) != 0 ? floatConfig2.callbacks : onFloatCallbacks, (i8 & 4194304) != 0 ? floatConfig2.floatCallbacks : floatCallbacks2, (i8 & 8388608) != 0 ? floatConfig2.floatAnimator : onFloatAnimator, (i8 & 16777216) != 0 ? floatConfig2.displayHeight : onDisplayHeight, (i8 & 33554432) != 0 ? floatConfig2.filterSet : set, (i8 & 67108864) != 0 ? floatConfig2.filterSelf : z9, (i8 & 134217728) != 0 ? floatConfig2.needShow : z10, (i8 & SQLiteDatabase.CREATE_IF_NECESSARY) != 0 ? floatConfig2.layoutChangedGravity : i6);
    }

    @Nullable
    public final Integer component1() {
        return this.layoutId;
    }

    @NotNull
    public final SidePattern component10() {
        return this.sidePattern;
    }

    @NotNull
    public final ShowPattern component11() {
        return this.showPattern;
    }

    public final boolean component12() {
        return this.widthMatch;
    }

    public final boolean component13() {
        return this.heightMatch;
    }

    public final int component14() {
        return this.gravity;
    }

    @NotNull
    public final n<Integer, Integer> component15() {
        return this.offsetPair;
    }

    @NotNull
    public final n<Integer, Integer> component16() {
        return this.locationPair;
    }

    public final int component17() {
        return this.leftBorder;
    }

    public final int component18() {
        return this.topBorder;
    }

    public final int component19() {
        return this.rightBorder;
    }

    @Nullable
    public final View component2() {
        return this.layoutView;
    }

    public final int component20() {
        return this.bottomBorder;
    }

    @Nullable
    public final OnInvokeView component21() {
        return this.invokeView;
    }

    @Nullable
    public final OnFloatCallbacks component22() {
        return this.callbacks;
    }

    @Nullable
    public final FloatCallbacks component23() {
        return this.floatCallbacks;
    }

    @Nullable
    public final OnFloatAnimator component24() {
        return this.floatAnimator;
    }

    @NotNull
    public final OnDisplayHeight component25() {
        return this.displayHeight;
    }

    @NotNull
    public final Set<String> component26() {
        return this.filterSet;
    }

    public final boolean component27$easyfloat_release() {
        return this.filterSelf;
    }

    public final boolean component28$easyfloat_release() {
        return this.needShow;
    }

    public final int component29() {
        return this.layoutChangedGravity;
    }

    @Nullable
    public final String component3() {
        return this.floatTag;
    }

    public final boolean component4() {
        return this.dragEnable;
    }

    public final boolean component5() {
        return this.isDrag;
    }

    public final boolean component6() {
        return this.isAnim;
    }

    public final boolean component7() {
        return this.isShow;
    }

    public final boolean component8() {
        return this.hasEditText;
    }

    public final boolean component9() {
        return this.immersionStatusBar;
    }

    @NotNull
    public final FloatConfig copy(@Nullable Integer num, @Nullable View view, @Nullable String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, @NotNull SidePattern sidePattern2, @NotNull ShowPattern showPattern2, boolean z7, boolean z8, int i, @NotNull n<Integer, Integer> nVar, @NotNull n<Integer, Integer> nVar2, int i2, int i3, int i4, int i5, @Nullable OnInvokeView onInvokeView, @Nullable OnFloatCallbacks onFloatCallbacks, @Nullable FloatCallbacks floatCallbacks2, @Nullable OnFloatAnimator onFloatAnimator, @NotNull OnDisplayHeight onDisplayHeight, @NotNull Set<String> set, boolean z9, boolean z10, int i6) {
        k.e(sidePattern2, "sidePattern");
        k.e(showPattern2, "showPattern");
        k.e(nVar, "offsetPair");
        k.e(nVar2, "locationPair");
        k.e(onDisplayHeight, "displayHeight");
        k.e(set, "filterSet");
        return new FloatConfig(num, view, str, z, z2, z3, z4, z5, z6, sidePattern2, showPattern2, z7, z8, i, nVar, nVar2, i2, i3, i4, i5, onInvokeView, onFloatCallbacks, floatCallbacks2, onFloatAnimator, onDisplayHeight, set, z9, z10, i6);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FloatConfig)) {
            return false;
        }
        FloatConfig floatConfig = (FloatConfig) obj;
        return k.a(this.layoutId, floatConfig.layoutId) && k.a(this.layoutView, floatConfig.layoutView) && k.a(this.floatTag, floatConfig.floatTag) && this.dragEnable == floatConfig.dragEnable && this.isDrag == floatConfig.isDrag && this.isAnim == floatConfig.isAnim && this.isShow == floatConfig.isShow && this.hasEditText == floatConfig.hasEditText && this.immersionStatusBar == floatConfig.immersionStatusBar && this.sidePattern == floatConfig.sidePattern && this.showPattern == floatConfig.showPattern && this.widthMatch == floatConfig.widthMatch && this.heightMatch == floatConfig.heightMatch && this.gravity == floatConfig.gravity && k.a(this.offsetPair, floatConfig.offsetPair) && k.a(this.locationPair, floatConfig.locationPair) && this.leftBorder == floatConfig.leftBorder && this.topBorder == floatConfig.topBorder && this.rightBorder == floatConfig.rightBorder && this.bottomBorder == floatConfig.bottomBorder && k.a(this.invokeView, floatConfig.invokeView) && k.a(this.callbacks, floatConfig.callbacks) && k.a(this.floatCallbacks, floatConfig.floatCallbacks) && k.a(this.floatAnimator, floatConfig.floatAnimator) && k.a(this.displayHeight, floatConfig.displayHeight) && k.a(this.filterSet, floatConfig.filterSet) && this.filterSelf == floatConfig.filterSelf && this.needShow == floatConfig.needShow && this.layoutChangedGravity == floatConfig.layoutChangedGravity;
    }

    public int hashCode() {
        Integer num = this.layoutId;
        int i = 0;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        View view = this.layoutView;
        int hashCode2 = (hashCode + (view == null ? 0 : view.hashCode())) * 31;
        String str = this.floatTag;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        boolean z = this.dragEnable;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i2 = (hashCode3 + (z ? 1 : 0)) * 31;
        boolean z3 = this.isDrag;
        if (z3) {
            z3 = true;
        }
        int i3 = (i2 + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.isAnim;
        if (z4) {
            z4 = true;
        }
        int i4 = (i3 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.isShow;
        if (z5) {
            z5 = true;
        }
        int i5 = (i4 + (z5 ? 1 : 0)) * 31;
        boolean z6 = this.hasEditText;
        if (z6) {
            z6 = true;
        }
        int i6 = (i5 + (z6 ? 1 : 0)) * 31;
        boolean z7 = this.immersionStatusBar;
        if (z7) {
            z7 = true;
        }
        int hashCode4 = (((((i6 + (z7 ? 1 : 0)) * 31) + this.sidePattern.hashCode()) * 31) + this.showPattern.hashCode()) * 31;
        boolean z8 = this.widthMatch;
        if (z8) {
            z8 = true;
        }
        int i7 = (hashCode4 + (z8 ? 1 : 0)) * 31;
        boolean z9 = this.heightMatch;
        if (z9) {
            z9 = true;
        }
        int hashCode5 = (((((((((((((((i7 + (z9 ? 1 : 0)) * 31) + this.gravity) * 31) + this.offsetPair.hashCode()) * 31) + this.locationPair.hashCode()) * 31) + this.leftBorder) * 31) + this.topBorder) * 31) + this.rightBorder) * 31) + this.bottomBorder) * 31;
        OnInvokeView onInvokeView = this.invokeView;
        int hashCode6 = (hashCode5 + (onInvokeView == null ? 0 : onInvokeView.hashCode())) * 31;
        OnFloatCallbacks onFloatCallbacks = this.callbacks;
        int hashCode7 = (hashCode6 + (onFloatCallbacks == null ? 0 : onFloatCallbacks.hashCode())) * 31;
        FloatCallbacks floatCallbacks2 = this.floatCallbacks;
        int hashCode8 = (hashCode7 + (floatCallbacks2 == null ? 0 : floatCallbacks2.hashCode())) * 31;
        OnFloatAnimator onFloatAnimator = this.floatAnimator;
        if (onFloatAnimator != null) {
            i = onFloatAnimator.hashCode();
        }
        int hashCode9 = (((((hashCode8 + i) * 31) + this.displayHeight.hashCode()) * 31) + this.filterSet.hashCode()) * 31;
        boolean z10 = this.filterSelf;
        if (z10) {
            z10 = true;
        }
        int i8 = (hashCode9 + (z10 ? 1 : 0)) * 31;
        boolean z11 = this.needShow;
        if (!z11) {
            z2 = z11;
        }
        return ((i8 + (z2 ? 1 : 0)) * 31) + this.layoutChangedGravity;
    }

    @NotNull
    public String toString() {
        return "FloatConfig(layoutId=" + this.layoutId + ", layoutView=" + this.layoutView + ", floatTag=" + this.floatTag + ", dragEnable=" + this.dragEnable + ", isDrag=" + this.isDrag + ", isAnim=" + this.isAnim + ", isShow=" + this.isShow + ", hasEditText=" + this.hasEditText + ", immersionStatusBar=" + this.immersionStatusBar + ", sidePattern=" + this.sidePattern + ", showPattern=" + this.showPattern + ", widthMatch=" + this.widthMatch + ", heightMatch=" + this.heightMatch + ", gravity=" + this.gravity + ", offsetPair=" + this.offsetPair + ", locationPair=" + this.locationPair + ", leftBorder=" + this.leftBorder + ", topBorder=" + this.topBorder + ", rightBorder=" + this.rightBorder + ", bottomBorder=" + this.bottomBorder + ", invokeView=" + this.invokeView + ", callbacks=" + this.callbacks + ", floatCallbacks=" + this.floatCallbacks + ", floatAnimator=" + this.floatAnimator + ", displayHeight=" + this.displayHeight + ", filterSet=" + this.filterSet + ", filterSelf=" + this.filterSelf + ", needShow=" + this.needShow + ", layoutChangedGravity=" + this.layoutChangedGravity + ')';
    }

    public FloatConfig(@Nullable Integer layoutId2, @Nullable View layoutView2, @Nullable String floatTag2, boolean dragEnable2, boolean isDrag2, boolean isAnim2, boolean isShow2, boolean hasEditText2, boolean immersionStatusBar2, @NotNull SidePattern sidePattern2, @NotNull ShowPattern showPattern2, boolean widthMatch2, boolean heightMatch2, int gravity2, @NotNull n<Integer, Integer> offsetPair2, @NotNull n<Integer, Integer> locationPair2, int leftBorder2, int topBorder2, int rightBorder2, int bottomBorder2, @Nullable OnInvokeView invokeView2, @Nullable OnFloatCallbacks callbacks2, @Nullable FloatCallbacks floatCallbacks2, @Nullable OnFloatAnimator floatAnimator2, @NotNull OnDisplayHeight displayHeight2, @NotNull Set<String> filterSet2, boolean filterSelf2, boolean needShow2, int layoutChangedGravity2) {
        SidePattern sidePattern3 = sidePattern2;
        ShowPattern showPattern3 = showPattern2;
        n<Integer, Integer> nVar = offsetPair2;
        n<Integer, Integer> nVar2 = locationPair2;
        OnDisplayHeight onDisplayHeight = displayHeight2;
        Set<String> set = filterSet2;
        k.e(sidePattern3, "sidePattern");
        k.e(showPattern3, "showPattern");
        k.e(nVar, "offsetPair");
        k.e(nVar2, "locationPair");
        k.e(onDisplayHeight, "displayHeight");
        k.e(set, "filterSet");
        this.layoutId = layoutId2;
        this.layoutView = layoutView2;
        this.floatTag = floatTag2;
        this.dragEnable = dragEnable2;
        this.isDrag = isDrag2;
        this.isAnim = isAnim2;
        this.isShow = isShow2;
        this.hasEditText = hasEditText2;
        this.immersionStatusBar = immersionStatusBar2;
        this.sidePattern = sidePattern3;
        this.showPattern = showPattern3;
        this.widthMatch = widthMatch2;
        this.heightMatch = heightMatch2;
        this.gravity = gravity2;
        this.offsetPair = nVar;
        this.locationPair = nVar2;
        this.leftBorder = leftBorder2;
        this.topBorder = topBorder2;
        this.rightBorder = rightBorder2;
        this.bottomBorder = bottomBorder2;
        this.invokeView = invokeView2;
        this.callbacks = callbacks2;
        this.floatCallbacks = floatCallbacks2;
        this.floatAnimator = floatAnimator2;
        this.displayHeight = onDisplayHeight;
        this.filterSet = set;
        this.filterSelf = filterSelf2;
        this.needShow = needShow2;
        this.layoutChangedGravity = layoutChangedGravity2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ FloatConfig(java.lang.Integer r31, android.view.View r32, java.lang.String r33, boolean r34, boolean r35, boolean r36, boolean r37, boolean r38, boolean r39, com.lzf.easyfloat.enums.SidePattern r40, com.lzf.easyfloat.enums.ShowPattern r41, boolean r42, boolean r43, int r44, kotlin.n r45, kotlin.n r46, int r47, int r48, int r49, int r50, com.lzf.easyfloat.interfaces.OnInvokeView r51, com.lzf.easyfloat.interfaces.OnFloatCallbacks r52, com.lzf.easyfloat.interfaces.FloatCallbacks r53, com.lzf.easyfloat.interfaces.OnFloatAnimator r54, com.lzf.easyfloat.interfaces.OnDisplayHeight r55, java.util.Set r56, boolean r57, boolean r58, int r59, int r60, kotlin.jvm.internal.DefaultConstructorMarker r61) {
        /*
            r30 = this;
            r0 = r60
            r1 = r0 & 1
            if (r1 == 0) goto L_0x0008
            r1 = 0
            goto L_0x000a
        L_0x0008:
            r1 = r31
        L_0x000a:
            r3 = r0 & 2
            if (r3 == 0) goto L_0x0010
            r3 = 0
            goto L_0x0012
        L_0x0010:
            r3 = r32
        L_0x0012:
            r4 = r0 & 4
            if (r4 == 0) goto L_0x0018
            r4 = 0
            goto L_0x001a
        L_0x0018:
            r4 = r33
        L_0x001a:
            r5 = r0 & 8
            if (r5 == 0) goto L_0x0020
            r5 = 1
            goto L_0x0022
        L_0x0020:
            r5 = r34
        L_0x0022:
            r7 = r0 & 16
            if (r7 == 0) goto L_0x0028
            r7 = 0
            goto L_0x002a
        L_0x0028:
            r7 = r35
        L_0x002a:
            r9 = r0 & 32
            if (r9 == 0) goto L_0x0030
            r9 = 0
            goto L_0x0032
        L_0x0030:
            r9 = r36
        L_0x0032:
            r10 = r0 & 64
            if (r10 == 0) goto L_0x0038
            r10 = 0
            goto L_0x003a
        L_0x0038:
            r10 = r37
        L_0x003a:
            r11 = r0 & 128(0x80, float:1.794E-43)
            if (r11 == 0) goto L_0x0040
            r11 = 0
            goto L_0x0042
        L_0x0040:
            r11 = r38
        L_0x0042:
            r12 = r0 & 256(0x100, float:3.59E-43)
            if (r12 == 0) goto L_0x0048
            r12 = 0
            goto L_0x004a
        L_0x0048:
            r12 = r39
        L_0x004a:
            r13 = r0 & 512(0x200, float:7.175E-43)
            if (r13 == 0) goto L_0x0051
            com.lzf.easyfloat.enums.SidePattern r13 = com.lzf.easyfloat.enums.SidePattern.DEFAULT
            goto L_0x0053
        L_0x0051:
            r13 = r40
        L_0x0053:
            r14 = r0 & 1024(0x400, float:1.435E-42)
            if (r14 == 0) goto L_0x005a
            com.lzf.easyfloat.enums.ShowPattern r14 = com.lzf.easyfloat.enums.ShowPattern.CURRENT_ACTIVITY
            goto L_0x005c
        L_0x005a:
            r14 = r41
        L_0x005c:
            r15 = r0 & 2048(0x800, float:2.87E-42)
            if (r15 == 0) goto L_0x0062
            r15 = 0
            goto L_0x0064
        L_0x0062:
            r15 = r42
        L_0x0064:
            r2 = r0 & 4096(0x1000, float:5.74E-42)
            if (r2 == 0) goto L_0x006a
            r2 = 0
            goto L_0x006c
        L_0x006a:
            r2 = r43
        L_0x006c:
            r6 = r0 & 8192(0x2000, float:1.14794E-41)
            if (r6 == 0) goto L_0x0072
            r6 = 0
            goto L_0x0074
        L_0x0072:
            r6 = r44
        L_0x0074:
            r8 = r0 & 16384(0x4000, float:2.2959E-41)
            if (r8 == 0) goto L_0x008c
            kotlin.n r8 = new kotlin.n
            r17 = r6
            r16 = 0
            java.lang.Integer r6 = java.lang.Integer.valueOf(r16)
            r18 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r16)
            r8.<init>(r6, r2)
            goto L_0x0094
        L_0x008c:
            r18 = r2
            r17 = r6
            r16 = 0
            r8 = r45
        L_0x0094:
            r2 = 32768(0x8000, float:4.5918E-41)
            r2 = r2 & r0
            if (r2 == 0) goto L_0x00aa
            kotlin.n r2 = new kotlin.n
            java.lang.Integer r6 = java.lang.Integer.valueOf(r16)
            r19 = r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r16)
            r2.<init>(r6, r8)
            goto L_0x00ae
        L_0x00aa:
            r19 = r8
            r2 = r46
        L_0x00ae:
            r6 = 65536(0x10000, float:9.18355E-41)
            r6 = r6 & r0
            if (r6 == 0) goto L_0x00b6
            r6 = r16
            goto L_0x00b8
        L_0x00b6:
            r6 = r47
        L_0x00b8:
            r8 = 131072(0x20000, float:1.83671E-40)
            r8 = r8 & r0
            if (r8 == 0) goto L_0x00c0
            r8 = -999(0xfffffffffffffc19, float:NaN)
            goto L_0x00c2
        L_0x00c0:
            r8 = r48
        L_0x00c2:
            r20 = 262144(0x40000, float:3.67342E-40)
            r20 = r0 & r20
            r21 = 9999(0x270f, float:1.4012E-41)
            if (r20 == 0) goto L_0x00cd
            r20 = r21
            goto L_0x00cf
        L_0x00cd:
            r20 = r49
        L_0x00cf:
            r22 = 524288(0x80000, float:7.34684E-40)
            r22 = r0 & r22
            if (r22 == 0) goto L_0x00d6
            goto L_0x00d8
        L_0x00d6:
            r21 = r50
        L_0x00d8:
            r22 = 1048576(0x100000, float:1.469368E-39)
            r22 = r0 & r22
            if (r22 == 0) goto L_0x00e1
            r22 = 0
            goto L_0x00e3
        L_0x00e1:
            r22 = r51
        L_0x00e3:
            r23 = 2097152(0x200000, float:2.938736E-39)
            r23 = r0 & r23
            if (r23 == 0) goto L_0x00ec
            r23 = 0
            goto L_0x00ee
        L_0x00ec:
            r23 = r52
        L_0x00ee:
            r24 = 4194304(0x400000, float:5.877472E-39)
            r24 = r0 & r24
            if (r24 == 0) goto L_0x00f7
            r24 = 0
            goto L_0x00f9
        L_0x00f7:
            r24 = r53
        L_0x00f9:
            r25 = 8388608(0x800000, float:1.17549435E-38)
            r25 = r0 & r25
            if (r25 == 0) goto L_0x0105
            com.lzf.easyfloat.anim.DefaultAnimator r25 = new com.lzf.easyfloat.anim.DefaultAnimator
            r25.<init>()
            goto L_0x0107
        L_0x0105:
            r25 = r54
        L_0x0107:
            r26 = 16777216(0x1000000, float:2.3509887E-38)
            r26 = r0 & r26
            if (r26 == 0) goto L_0x0113
            com.lzf.easyfloat.utils.DefaultDisplayHeight r26 = new com.lzf.easyfloat.utils.DefaultDisplayHeight
            r26.<init>()
            goto L_0x0115
        L_0x0113:
            r26 = r55
        L_0x0115:
            r27 = 33554432(0x2000000, float:9.403955E-38)
            r27 = r0 & r27
            if (r27 == 0) goto L_0x0121
            java.util.LinkedHashSet r27 = new java.util.LinkedHashSet
            r27.<init>()
            goto L_0x0123
        L_0x0121:
            r27 = r56
        L_0x0123:
            r28 = 67108864(0x4000000, float:1.5046328E-36)
            r28 = r0 & r28
            if (r28 == 0) goto L_0x012a
            goto L_0x012c
        L_0x012a:
            r16 = r57
        L_0x012c:
            r28 = 134217728(0x8000000, float:3.85186E-34)
            r28 = r0 & r28
            if (r28 == 0) goto L_0x0135
            r28 = 1
            goto L_0x0137
        L_0x0135:
            r28 = r58
        L_0x0137:
            r29 = 268435456(0x10000000, float:2.5243549E-29)
            r0 = r0 & r29
            if (r0 == 0) goto L_0x0141
            r0 = 8388659(0x800033, float:1.1755015E-38)
            goto L_0x0143
        L_0x0141:
            r0 = r59
        L_0x0143:
            r31 = r30
            r32 = r1
            r33 = r3
            r34 = r4
            r35 = r5
            r36 = r7
            r37 = r9
            r38 = r10
            r39 = r11
            r40 = r12
            r41 = r13
            r42 = r14
            r43 = r15
            r44 = r18
            r45 = r17
            r46 = r19
            r47 = r2
            r48 = r6
            r49 = r8
            r50 = r20
            r51 = r21
            r52 = r22
            r53 = r23
            r54 = r24
            r55 = r25
            r56 = r26
            r57 = r27
            r58 = r16
            r59 = r28
            r60 = r0
            r31.<init>(r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55, r56, r57, r58, r59, r60)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzf.easyfloat.data.FloatConfig.<init>(java.lang.Integer, android.view.View, java.lang.String, boolean, boolean, boolean, boolean, boolean, boolean, com.lzf.easyfloat.enums.SidePattern, com.lzf.easyfloat.enums.ShowPattern, boolean, boolean, int, kotlin.n, kotlin.n, int, int, int, int, com.lzf.easyfloat.interfaces.OnInvokeView, com.lzf.easyfloat.interfaces.OnFloatCallbacks, com.lzf.easyfloat.interfaces.FloatCallbacks, com.lzf.easyfloat.interfaces.OnFloatAnimator, com.lzf.easyfloat.interfaces.OnDisplayHeight, java.util.Set, boolean, boolean, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final Integer getLayoutId() {
        return this.layoutId;
    }

    public final void setLayoutId(@Nullable Integer num) {
        this.layoutId = num;
    }

    @Nullable
    public final View getLayoutView() {
        return this.layoutView;
    }

    public final void setLayoutView(@Nullable View view) {
        this.layoutView = view;
    }

    @Nullable
    public final String getFloatTag() {
        return this.floatTag;
    }

    public final void setFloatTag(@Nullable String str) {
        this.floatTag = str;
    }

    public final boolean getDragEnable() {
        return this.dragEnable;
    }

    public final void setDragEnable(boolean z) {
        this.dragEnable = z;
    }

    public final boolean isDrag() {
        return this.isDrag;
    }

    public final void setDrag(boolean z) {
        this.isDrag = z;
    }

    public final boolean isAnim() {
        return this.isAnim;
    }

    public final void setAnim(boolean z) {
        this.isAnim = z;
    }

    public final boolean isShow() {
        return this.isShow;
    }

    public final void setShow(boolean z) {
        this.isShow = z;
    }

    public final boolean getHasEditText() {
        return this.hasEditText;
    }

    public final void setHasEditText(boolean z) {
        this.hasEditText = z;
    }

    public final boolean getImmersionStatusBar() {
        return this.immersionStatusBar;
    }

    public final void setImmersionStatusBar(boolean z) {
        this.immersionStatusBar = z;
    }

    @NotNull
    public final SidePattern getSidePattern() {
        return this.sidePattern;
    }

    public final void setSidePattern(@NotNull SidePattern sidePattern2) {
        k.e(sidePattern2, "<set-?>");
        this.sidePattern = sidePattern2;
    }

    @NotNull
    public final ShowPattern getShowPattern() {
        return this.showPattern;
    }

    public final void setShowPattern(@NotNull ShowPattern showPattern2) {
        k.e(showPattern2, "<set-?>");
        this.showPattern = showPattern2;
    }

    public final boolean getWidthMatch() {
        return this.widthMatch;
    }

    public final void setWidthMatch(boolean z) {
        this.widthMatch = z;
    }

    public final boolean getHeightMatch() {
        return this.heightMatch;
    }

    public final void setHeightMatch(boolean z) {
        this.heightMatch = z;
    }

    public final int getGravity() {
        return this.gravity;
    }

    public final void setGravity(int i) {
        this.gravity = i;
    }

    @NotNull
    public final n<Integer, Integer> getOffsetPair() {
        return this.offsetPair;
    }

    public final void setOffsetPair(@NotNull n<Integer, Integer> nVar) {
        k.e(nVar, "<set-?>");
        this.offsetPair = nVar;
    }

    @NotNull
    public final n<Integer, Integer> getLocationPair() {
        return this.locationPair;
    }

    public final void setLocationPair(@NotNull n<Integer, Integer> nVar) {
        k.e(nVar, "<set-?>");
        this.locationPair = nVar;
    }

    public final int getLeftBorder() {
        return this.leftBorder;
    }

    public final void setLeftBorder(int i) {
        this.leftBorder = i;
    }

    public final int getTopBorder() {
        return this.topBorder;
    }

    public final void setTopBorder(int i) {
        this.topBorder = i;
    }

    public final int getRightBorder() {
        return this.rightBorder;
    }

    public final void setRightBorder(int i) {
        this.rightBorder = i;
    }

    public final int getBottomBorder() {
        return this.bottomBorder;
    }

    public final void setBottomBorder(int i) {
        this.bottomBorder = i;
    }

    @Nullable
    public final OnInvokeView getInvokeView() {
        return this.invokeView;
    }

    public final void setInvokeView(@Nullable OnInvokeView onInvokeView) {
        this.invokeView = onInvokeView;
    }

    @Nullable
    public final OnFloatCallbacks getCallbacks() {
        return this.callbacks;
    }

    public final void setCallbacks(@Nullable OnFloatCallbacks onFloatCallbacks) {
        this.callbacks = onFloatCallbacks;
    }

    @Nullable
    public final FloatCallbacks getFloatCallbacks() {
        return this.floatCallbacks;
    }

    public final void setFloatCallbacks(@Nullable FloatCallbacks floatCallbacks2) {
        this.floatCallbacks = floatCallbacks2;
    }

    @Nullable
    public final OnFloatAnimator getFloatAnimator() {
        return this.floatAnimator;
    }

    public final void setFloatAnimator(@Nullable OnFloatAnimator onFloatAnimator) {
        this.floatAnimator = onFloatAnimator;
    }

    @NotNull
    public final OnDisplayHeight getDisplayHeight() {
        return this.displayHeight;
    }

    public final void setDisplayHeight(@NotNull OnDisplayHeight onDisplayHeight) {
        k.e(onDisplayHeight, "<set-?>");
        this.displayHeight = onDisplayHeight;
    }

    @NotNull
    public final Set<String> getFilterSet() {
        return this.filterSet;
    }

    public final boolean getFilterSelf$easyfloat_release() {
        return this.filterSelf;
    }

    public final void setFilterSelf$easyfloat_release(boolean z) {
        this.filterSelf = z;
    }

    public final boolean getNeedShow$easyfloat_release() {
        return this.needShow;
    }

    public final void setNeedShow$easyfloat_release(boolean z) {
        this.needShow = z;
    }

    public final int getLayoutChangedGravity() {
        return this.layoutChangedGravity;
    }

    public final void setLayoutChangedGravity(int i) {
        this.layoutChangedGravity = i;
    }
}
