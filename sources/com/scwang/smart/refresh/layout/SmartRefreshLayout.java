package com.scwang.smart.refresh.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.scwang.smart.refresh.layout.kernel.R$id;
import com.scwang.smart.refresh.layout.kernel.R$string;
import com.scwang.smart.refresh.layout.kernel.R$styleable;
import com.scwang.smart.refresh.layout.wrapper.RefreshFooterWrapper;
import com.scwang.smart.refresh.layout.wrapper.RefreshHeaderWrapper;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

@SuppressLint({"RestrictedApi"})
public class SmartRefreshLayout extends ViewGroup implements com.scwang.smart.refresh.layout.api.f, NestedScrollingParent {
    protected static com.scwang.smart.refresh.layout.listener.b c = null;
    protected static com.scwang.smart.refresh.layout.listener.c d = null;
    protected static com.scwang.smart.refresh.layout.listener.d f = null;
    protected static ViewGroup.MarginLayoutParams q = new ViewGroup.MarginLayoutParams(-1, -1);
    protected float A4;
    protected int A5;
    protected char B4;
    protected int B5;
    protected boolean C4;
    protected float C5;
    protected boolean D4;
    protected float D5;
    protected boolean E4;
    protected float E5;
    protected int F4;
    protected float F5;
    protected int G4;
    protected float G5;
    protected int H4;
    protected com.scwang.smart.refresh.layout.api.a H5;
    protected int I4;
    protected com.scwang.smart.refresh.layout.api.a I5;
    protected int J4;
    protected com.scwang.smart.refresh.layout.api.b J5;
    protected int K4;
    protected Paint K5;
    protected int L4;
    protected Handler L5;
    protected Scroller M4;
    protected com.scwang.smart.refresh.layout.api.e M5;
    protected VelocityTracker N4;
    protected com.scwang.smart.refresh.layout.constant.b N5;
    protected Interpolator O4;
    protected com.scwang.smart.refresh.layout.constant.b O5;
    protected int[] P4;
    protected long P5;
    protected boolean Q4;
    protected int Q5;
    protected boolean R4;
    protected int R5;
    protected boolean S4;
    protected boolean S5;
    protected boolean T4;
    protected boolean T5;
    protected boolean U4;
    protected boolean U5;
    protected boolean V4;
    protected boolean V5;
    protected boolean W4;
    protected boolean W5;
    protected boolean X4;
    protected MotionEvent X5;
    protected boolean Y4;
    protected Runnable Y5;
    protected boolean Z4;
    protected ValueAnimator Z5;
    protected int a1;
    protected int a2;
    protected boolean a5;
    protected boolean b5;
    protected boolean c5;
    protected boolean d5;
    protected boolean e5;
    protected boolean f5;
    protected boolean g5;
    protected boolean h5;
    protected boolean i5;
    protected boolean j5;
    protected boolean k5;
    protected boolean l5;
    protected boolean m5;
    protected com.scwang.smart.refresh.layout.listener.g n5;
    protected com.scwang.smart.refresh.layout.listener.e o5;
    protected int p0;
    protected int p1;
    protected float p2;
    protected float p3;
    protected float p4;
    protected com.scwang.smart.refresh.layout.listener.f p5;
    protected com.scwang.smart.refresh.layout.listener.i q5;
    protected int r5;
    protected boolean s5;
    protected int[] t5;
    protected NestedScrollingChildHelper u5;
    protected NestedScrollingParentHelper v5;
    protected int w5;
    protected int x;
    protected com.scwang.smart.refresh.layout.constant.a x5;
    protected int y;
    protected int y5;
    protected int z;
    protected float z4;
    protected com.scwang.smart.refresh.layout.constant.a z5;

    public SmartRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a1 = IjkMediaCodecInfo.RANK_SECURE;
        this.p1 = IjkMediaCodecInfo.RANK_SECURE;
        this.A4 = 0.5f;
        this.B4 = 'n';
        this.F4 = -1;
        this.G4 = -1;
        this.H4 = -1;
        this.I4 = -1;
        this.Q4 = true;
        this.R4 = false;
        this.S4 = true;
        this.T4 = true;
        this.U4 = true;
        this.V4 = true;
        this.W4 = false;
        this.X4 = true;
        this.Y4 = true;
        this.Z4 = false;
        this.a5 = true;
        this.b5 = false;
        this.c5 = true;
        this.d5 = true;
        this.e5 = true;
        this.f5 = true;
        this.g5 = false;
        this.h5 = false;
        this.i5 = false;
        this.j5 = false;
        this.k5 = false;
        this.l5 = false;
        this.m5 = false;
        this.t5 = new int[2];
        this.u5 = new NestedScrollingChildHelper(this);
        this.v5 = new NestedScrollingParentHelper(this);
        com.scwang.smart.refresh.layout.constant.a aVar = com.scwang.smart.refresh.layout.constant.a.a;
        this.x5 = aVar;
        this.z5 = aVar;
        this.C5 = 2.5f;
        this.D5 = 2.5f;
        this.E5 = 1.0f;
        this.F5 = 1.0f;
        this.G5 = 0.16666667f;
        this.M5 = new k();
        com.scwang.smart.refresh.layout.constant.b bVar = com.scwang.smart.refresh.layout.constant.b.None;
        this.N5 = bVar;
        this.O5 = bVar;
        this.P5 = 0;
        this.Q5 = 0;
        this.R5 = 0;
        this.V5 = false;
        this.W5 = false;
        this.X5 = null;
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.L5 = new Handler(Looper.getMainLooper());
        this.M4 = new Scroller(context);
        this.N4 = VelocityTracker.obtain();
        this.a2 = context.getResources().getDisplayMetrics().heightPixels;
        this.O4 = new com.scwang.smart.refresh.layout.util.b(com.scwang.smart.refresh.layout.util.b.a);
        this.x = configuration.getScaledTouchSlop();
        this.J4 = configuration.getScaledMinimumFlingVelocity();
        this.K4 = configuration.getScaledMaximumFlingVelocity();
        this.y5 = com.scwang.smart.refresh.layout.util.b.c(60.0f);
        this.w5 = com.scwang.smart.refresh.layout.util.b.c(100.0f);
        TypedArray ta = context.obtainStyledAttributes(attrs, R$styleable.SmartRefreshLayout);
        if (!ta.hasValue(R$styleable.SmartRefreshLayout_android_clipToPadding)) {
            super.setClipToPadding(false);
        }
        if (!ta.hasValue(R$styleable.SmartRefreshLayout_android_clipChildren)) {
            super.setClipChildren(false);
        }
        com.scwang.smart.refresh.layout.listener.d dVar = f;
        if (dVar != null) {
            dVar.a(context, this);
        }
        this.A4 = ta.getFloat(R$styleable.SmartRefreshLayout_srlDragRate, this.A4);
        this.C5 = ta.getFloat(R$styleable.SmartRefreshLayout_srlHeaderMaxDragRate, this.C5);
        this.D5 = ta.getFloat(R$styleable.SmartRefreshLayout_srlFooterMaxDragRate, this.D5);
        this.E5 = ta.getFloat(R$styleable.SmartRefreshLayout_srlHeaderTriggerRate, this.E5);
        this.F5 = ta.getFloat(R$styleable.SmartRefreshLayout_srlFooterTriggerRate, this.F5);
        this.Q4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableRefresh, this.Q4);
        this.p1 = ta.getInt(R$styleable.SmartRefreshLayout_srlReboundDuration, this.p1);
        int i2 = R$styleable.SmartRefreshLayout_srlEnableLoadMore;
        this.R4 = ta.getBoolean(i2, this.R4);
        int i3 = R$styleable.SmartRefreshLayout_srlHeaderHeight;
        this.w5 = ta.getDimensionPixelOffset(i3, this.w5);
        int i4 = R$styleable.SmartRefreshLayout_srlFooterHeight;
        this.y5 = ta.getDimensionPixelOffset(i4, this.y5);
        this.A5 = ta.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlHeaderInsetStart, this.A5);
        this.B5 = ta.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlFooterInsetStart, this.B5);
        this.g5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlDisableContentWhenRefresh, this.g5);
        this.h5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlDisableContentWhenLoading, this.h5);
        int i6 = R$styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent;
        this.U4 = ta.getBoolean(i6, this.U4);
        int i7 = R$styleable.SmartRefreshLayout_srlEnableFooterTranslationContent;
        this.V4 = ta.getBoolean(i7, this.V4);
        this.X4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnablePreviewInEditMode, this.X4);
        this.a5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableAutoLoadMore, this.a5);
        this.Y4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableOverScrollBounce, this.Y4);
        this.b5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnablePureScrollMode, this.b5);
        this.c5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableScrollContentWhenLoaded, this.c5);
        this.d5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableScrollContentWhenRefreshed, this.d5);
        this.e5 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull, this.e5);
        boolean z2 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterFollowWhenLoadFinished, this.W4);
        this.W4 = z2;
        this.W4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterFollowWhenNoMoreData, z2);
        this.S4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableClipHeaderWhenFixedBehind, this.S4);
        this.T4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableClipFooterWhenFixedBehind, this.T4);
        this.Z4 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableOverScrollDrag, this.Z4);
        this.F4 = ta.getResourceId(R$styleable.SmartRefreshLayout_srlFixedHeaderViewId, this.F4);
        this.G4 = ta.getResourceId(R$styleable.SmartRefreshLayout_srlFixedFooterViewId, this.G4);
        this.H4 = ta.getResourceId(R$styleable.SmartRefreshLayout_srlHeaderTranslationViewId, this.H4);
        this.I4 = ta.getResourceId(R$styleable.SmartRefreshLayout_srlFooterTranslationViewId, this.I4);
        boolean z3 = ta.getBoolean(R$styleable.SmartRefreshLayout_srlEnableNestedScrolling, this.f5);
        this.f5 = z3;
        this.u5.setNestedScrollingEnabled(z3);
        this.k5 = this.k5 || ta.hasValue(i2);
        this.l5 = this.l5 || ta.hasValue(i6);
        this.m5 = this.m5 || ta.hasValue(i7);
        this.x5 = ta.hasValue(i3) ? com.scwang.smart.refresh.layout.constant.a.g : this.x5;
        this.z5 = ta.hasValue(i4) ? com.scwang.smart.refresh.layout.constant.a.g : this.z5;
        int accentColor = ta.getColor(R$styleable.SmartRefreshLayout_srlAccentColor, 0);
        int primaryColor = ta.getColor(R$styleable.SmartRefreshLayout_srlPrimaryColor, 0);
        if (primaryColor != 0) {
            if (accentColor != 0) {
                this.P4 = new int[]{primaryColor, accentColor};
            } else {
                this.P4 = new int[]{primaryColor};
            }
        } else if (accentColor != 0) {
            this.P4 = new int[]{0, accentColor};
        }
        if (this.b5 && !this.k5 && !this.R4) {
            this.R4 = true;
        }
        ta.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        int count = super.getChildCount();
        if (count <= 3) {
            int contentLevel = 0;
            int indexContent = -1;
            int i2 = 0;
            while (true) {
                int i3 = 0;
                if (i2 >= count) {
                    break;
                }
                View view = super.getChildAt(i2);
                if (com.scwang.smart.refresh.layout.util.b.e(view) && (contentLevel < 2 || i2 == 1)) {
                    indexContent = i2;
                    contentLevel = 2;
                } else if (!(view instanceof com.scwang.smart.refresh.layout.api.a) && contentLevel < 1) {
                    indexContent = i2;
                    if (i2 > 0) {
                        i3 = 1;
                    }
                    contentLevel = i3;
                }
                i2++;
            }
            int indexHeader = -1;
            int indexFooter = -1;
            if (indexContent >= 0) {
                this.J5 = new com.scwang.smart.refresh.layout.wrapper.a(super.getChildAt(indexContent));
                if (indexContent == 1) {
                    indexHeader = 0;
                    if (count == 3) {
                        indexFooter = 2;
                    }
                } else if (count == 2) {
                    indexFooter = 1;
                }
            }
            for (int i4 = 0; i4 < count; i4++) {
                View view2 = super.getChildAt(i4);
                if (i4 == indexHeader || (i4 != indexFooter && indexHeader == -1 && this.H5 == null && (view2 instanceof com.scwang.smart.refresh.layout.api.d))) {
                    this.H5 = view2 instanceof com.scwang.smart.refresh.layout.api.d ? (com.scwang.smart.refresh.layout.api.d) view2 : new RefreshHeaderWrapper(view2);
                } else if (i4 == indexFooter || (indexFooter == -1 && (view2 instanceof com.scwang.smart.refresh.layout.api.c))) {
                    this.R4 = this.R4 || !this.k5;
                    this.I5 = view2 instanceof com.scwang.smart.refresh.layout.api.c ? (com.scwang.smart.refresh.layout.api.c) view2 : new RefreshFooterWrapper(view2);
                }
            }
            return;
        }
        throw new RuntimeException("最多只支持3个子View，Most only support three sub view");
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        com.scwang.smart.refresh.layout.api.a aVar;
        com.scwang.smart.refresh.layout.listener.c cVar;
        super.onAttachedToWindow();
        boolean z2 = true;
        this.U5 = true;
        if (!isInEditMode()) {
            if (this.H5 == null && (cVar = d) != null) {
                com.scwang.smart.refresh.layout.api.d header = cVar.a(getContext(), this);
                if (header != null) {
                    H(header);
                } else {
                    throw new RuntimeException("DefaultRefreshHeaderCreator can not return null");
                }
            }
            if (this.I5 == null) {
                com.scwang.smart.refresh.layout.listener.b bVar = c;
                if (bVar != null) {
                    com.scwang.smart.refresh.layout.api.c footer = bVar.a(getContext(), this);
                    if (footer != null) {
                        F(footer);
                    } else {
                        throw new RuntimeException("DefaultRefreshFooterCreator can not return null");
                    }
                }
            } else {
                if (!this.R4 && this.k5) {
                    z2 = false;
                }
                this.R4 = z2;
            }
            if (this.J5 == null) {
                int len = getChildCount();
                for (int i2 = 0; i2 < len; i2++) {
                    View view = getChildAt(i2);
                    com.scwang.smart.refresh.layout.api.a aVar2 = this.H5;
                    if ((aVar2 == null || view != aVar2.getView()) && ((aVar = this.I5) == null || view != aVar.getView())) {
                        this.J5 = new com.scwang.smart.refresh.layout.wrapper.a(view);
                    }
                }
            }
            if (this.J5 == null) {
                int padding = com.scwang.smart.refresh.layout.util.b.c(20.0f);
                TextView errorView = new TextView(getContext());
                errorView.setTextColor(-39424);
                errorView.setGravity(17);
                errorView.setTextSize(20.0f);
                errorView.setText(R$string.srl_content_empty);
                super.addView(errorView, 0, new LayoutParams(-1, -1));
                com.scwang.smart.refresh.layout.wrapper.a aVar3 = new com.scwang.smart.refresh.layout.wrapper.a(errorView);
                this.J5 = aVar3;
                aVar3.getView().setPadding(padding, padding, padding, padding);
            }
            View fixedHeaderView = findViewById(this.F4);
            View fixedFooterView = findViewById(this.G4);
            this.J5.a(this.q5);
            this.J5.d(this.e5);
            this.J5.c(this.M5, fixedHeaderView, fixedFooterView);
            if (this.y != 0) {
                z(com.scwang.smart.refresh.layout.constant.b.None);
                com.scwang.smart.refresh.layout.api.b bVar2 = this.J5;
                this.y = 0;
                bVar2.h(0, this.H4, this.I4);
            }
        }
        int[] iArr = this.P4;
        if (iArr != null) {
            com.scwang.smart.refresh.layout.api.a aVar4 = this.H5;
            if (aVar4 != null) {
                aVar4.setPrimaryColors(iArr);
            }
            com.scwang.smart.refresh.layout.api.a aVar5 = this.I5;
            if (aVar5 != null) {
                aVar5.setPrimaryColors(this.P4);
            }
        }
        com.scwang.smart.refresh.layout.api.b bVar3 = this.J5;
        if (bVar3 != null) {
            super.bringChildToFront(bVar3.getView());
        }
        com.scwang.smart.refresh.layout.api.a aVar6 = this.H5;
        if (aVar6 != null && aVar6.getSpinnerStyle().h) {
            super.bringChildToFront(this.H5.getView());
        }
        com.scwang.smart.refresh.layout.api.a aVar7 = this.I5;
        if (aVar7 != null && aVar7.getSpinnerStyle().h) {
            super.bringChildToFront(this.I5.getView());
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0254  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0275  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x028b  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x02b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r25, int r26) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r26
            r3 = 0
            r4 = 0
            r5 = r24
            boolean r6 = r5.isInEditMode()
            if (r6 == 0) goto L_0x0016
            boolean r6 = r0.X4
            if (r6 == 0) goto L_0x0016
            r6 = 1
            goto L_0x0017
        L_0x0016:
            r6 = 0
        L_0x0017:
            r9 = 0
            int r10 = super.getChildCount()
        L_0x001c:
            if (r9 >= r10) goto L_0x038d
            android.view.View r11 = super.getChildAt(r9)
            int r12 = r11.getVisibility()
            r13 = 8
            if (r12 == r13) goto L_0x037f
            int r12 = com.scwang.smart.refresh.layout.kernel.R$id.srl_tag
            java.lang.Object r12 = r11.getTag(r12)
            java.lang.String r13 = "GONE"
            boolean r12 = r13.equals(r12)
            if (r12 == 0) goto L_0x003e
            r17 = r6
            r20 = r10
            goto L_0x0385
        L_0x003e:
            com.scwang.smart.refresh.layout.api.a r12 = r0.H5
            if (r12 == 0) goto L_0x0186
            android.view.View r12 = r12.getView()
            if (r12 != r11) goto L_0x0186
            com.scwang.smart.refresh.layout.api.a r12 = r0.H5
            android.view.View r12 = r12.getView()
            android.view.ViewGroup$LayoutParams r15 = r12.getLayoutParams()
            boolean r7 = r15 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r7 == 0) goto L_0x005a
            r7 = r15
            android.view.ViewGroup$MarginLayoutParams r7 = (android.view.ViewGroup.MarginLayoutParams) r7
            goto L_0x005c
        L_0x005a:
            android.view.ViewGroup$MarginLayoutParams r7 = q
        L_0x005c:
            int r14 = r7.leftMargin
            int r8 = r7.rightMargin
            int r14 = r14 + r8
            int r8 = r15.width
            int r8 = android.view.ViewGroup.getChildMeasureSpec(r1, r14, r8)
            int r14 = r0.w5
            com.scwang.smart.refresh.layout.constant.a r13 = r0.x5
            r20 = r10
            int r10 = r13.n
            r21 = r14
            com.scwang.smart.refresh.layout.constant.a r14 = com.scwang.smart.refresh.layout.constant.a.g
            int r14 = r14.n
            if (r10 >= r14) goto L_0x00f8
            int r10 = r15.height
            if (r10 <= 0) goto L_0x009b
            int r14 = r7.bottomMargin
            int r10 = r10 + r14
            int r14 = r7.topMargin
            int r14 = r14 + r10
            com.scwang.smart.refresh.layout.constant.a r10 = com.scwang.smart.refresh.layout.constant.a.e
            boolean r13 = r13.a(r10)
            if (r13 == 0) goto L_0x0098
            int r13 = r15.height
            r21 = r14
            int r14 = r7.bottomMargin
            int r13 = r13 + r14
            int r14 = r7.topMargin
            int r13 = r13 + r14
            r0.w5 = r13
            r0.x5 = r10
            goto L_0x00f8
        L_0x0098:
            r21 = r14
            goto L_0x00f8
        L_0x009b:
            r13 = -2
            if (r10 != r13) goto L_0x00f8
            com.scwang.smart.refresh.layout.api.a r10 = r0.H5
            com.scwang.smart.refresh.layout.constant.c r10 = r10.getSpinnerStyle()
            com.scwang.smart.refresh.layout.constant.c r13 = com.scwang.smart.refresh.layout.constant.c.e
            if (r10 != r13) goto L_0x00ae
            com.scwang.smart.refresh.layout.constant.a r10 = r0.x5
            boolean r10 = r10.o
            if (r10 != 0) goto L_0x00f8
        L_0x00ae:
            int r10 = android.view.View.MeasureSpec.getSize(r26)
            int r13 = r7.bottomMargin
            int r10 = r10 - r13
            int r13 = r7.topMargin
            int r10 = r10 - r13
            r13 = 0
            int r10 = java.lang.Math.max(r10, r13)
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
            int r14 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r13)
            r12.measure(r8, r14)
            int r13 = r12.getMeasuredHeight()
            if (r13 <= 0) goto L_0x00f4
            r14 = -1
            if (r13 == r10) goto L_0x00ed
            r22 = r10
            com.scwang.smart.refresh.layout.constant.a r10 = r0.x5
            r21 = r14
            com.scwang.smart.refresh.layout.constant.a r14 = com.scwang.smart.refresh.layout.constant.a.c
            boolean r10 = r10.a(r14)
            if (r10 == 0) goto L_0x00ea
            int r10 = r7.bottomMargin
            int r10 = r10 + r13
            r23 = r13
            int r13 = r7.topMargin
            int r10 = r10 + r13
            r0.w5 = r10
            r0.x5 = r14
            goto L_0x00f8
        L_0x00ea:
            r23 = r13
            goto L_0x00f8
        L_0x00ed:
            r22 = r10
            r23 = r13
            r21 = r14
            goto L_0x00f8
        L_0x00f4:
            r22 = r10
            r23 = r13
        L_0x00f8:
            r14 = r21
            com.scwang.smart.refresh.layout.api.a r10 = r0.H5
            com.scwang.smart.refresh.layout.constant.c r10 = r10.getSpinnerStyle()
            com.scwang.smart.refresh.layout.constant.c r13 = com.scwang.smart.refresh.layout.constant.c.e
            if (r10 != r13) goto L_0x0109
            int r14 = android.view.View.MeasureSpec.getSize(r26)
            goto L_0x0126
        L_0x0109:
            com.scwang.smart.refresh.layout.api.a r10 = r0.H5
            com.scwang.smart.refresh.layout.constant.c r10 = r10.getSpinnerStyle()
            boolean r10 = r10.i
            if (r10 == 0) goto L_0x0126
            if (r6 != 0) goto L_0x0126
            boolean r10 = r0.Q4
            boolean r10 = r0.w(r10)
            if (r10 == 0) goto L_0x0120
            int r10 = r0.y
            goto L_0x0121
        L_0x0120:
            r10 = 0
        L_0x0121:
            r13 = 0
            int r14 = java.lang.Math.max(r13, r10)
        L_0x0126:
            r10 = -1
            if (r14 == r10) goto L_0x013e
            int r10 = r7.bottomMargin
            int r10 = r14 - r10
            int r13 = r7.topMargin
            int r10 = r10 - r13
            r13 = 0
            int r10 = java.lang.Math.max(r10, r13)
            r13 = 1073741824(0x40000000, float:2.0)
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r10, r13)
            r12.measure(r8, r10)
        L_0x013e:
            com.scwang.smart.refresh.layout.constant.a r10 = r0.x5
            boolean r13 = r10.o
            if (r13 != 0) goto L_0x016b
            float r13 = r0.C5
            r16 = 1092616192(0x41200000, float:10.0)
            int r21 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r21 >= 0) goto L_0x0153
            r21 = r7
            int r7 = r0.w5
            float r7 = (float) r7
            float r13 = r13 * r7
            goto L_0x0155
        L_0x0153:
            r21 = r7
        L_0x0155:
            r7 = r13
            com.scwang.smart.refresh.layout.constant.a r10 = r10.b()
            r0.x5 = r10
            com.scwang.smart.refresh.layout.api.a r10 = r0.H5
            com.scwang.smart.refresh.layout.api.e r13 = r0.M5
            r22 = r8
            int r8 = r0.w5
            r23 = r14
            int r14 = (int) r7
            r10.g(r13, r8, r14)
            goto L_0x0171
        L_0x016b:
            r21 = r7
            r22 = r8
            r23 = r14
        L_0x0171:
            if (r6 == 0) goto L_0x0188
            boolean r7 = r0.Q4
            boolean r7 = r0.w(r7)
            if (r7 == 0) goto L_0x0188
            int r7 = r12.getMeasuredWidth()
            int r3 = r3 + r7
            int r7 = r12.getMeasuredHeight()
            int r4 = r4 + r7
            goto L_0x0188
        L_0x0186:
            r20 = r10
        L_0x0188:
            com.scwang.smart.refresh.layout.api.a r7 = r0.I5
            if (r7 == 0) goto L_0x02cc
            android.view.View r7 = r7.getView()
            if (r7 != r11) goto L_0x02cc
            com.scwang.smart.refresh.layout.api.a r7 = r0.I5
            android.view.View r7 = r7.getView()
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            boolean r10 = r8 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r10 == 0) goto L_0x01a4
            r10 = r8
            android.view.ViewGroup$MarginLayoutParams r10 = (android.view.ViewGroup.MarginLayoutParams) r10
            goto L_0x01a6
        L_0x01a4:
            android.view.ViewGroup$MarginLayoutParams r10 = q
        L_0x01a6:
            int r12 = r10.leftMargin
            int r13 = r10.rightMargin
            int r12 = r12 + r13
            int r13 = r8.width
            int r12 = android.view.ViewGroup.getChildMeasureSpec(r1, r12, r13)
            int r13 = r0.y5
            com.scwang.smart.refresh.layout.constant.a r14 = r0.z5
            int r15 = r14.n
            r21 = r13
            com.scwang.smart.refresh.layout.constant.a r13 = com.scwang.smart.refresh.layout.constant.a.g
            int r13 = r13.n
            if (r15 >= r13) goto L_0x0241
            int r13 = r8.height
            if (r13 <= 0) goto L_0x01e4
            int r15 = r10.topMargin
            int r13 = r13 + r15
            int r15 = r10.bottomMargin
            int r13 = r13 + r15
            com.scwang.smart.refresh.layout.constant.a r15 = com.scwang.smart.refresh.layout.constant.a.e
            boolean r14 = r14.a(r15)
            if (r14 == 0) goto L_0x01e1
            int r14 = r8.height
            r22 = r8
            int r8 = r10.topMargin
            int r14 = r14 + r8
            int r8 = r10.bottomMargin
            int r14 = r14 + r8
            r0.y5 = r14
            r0.z5 = r15
            goto L_0x0245
        L_0x01e1:
            r22 = r8
            goto L_0x0245
        L_0x01e4:
            r22 = r8
            r8 = -2
            if (r13 != r8) goto L_0x0243
            com.scwang.smart.refresh.layout.api.a r8 = r0.I5
            com.scwang.smart.refresh.layout.constant.c r8 = r8.getSpinnerStyle()
            com.scwang.smart.refresh.layout.constant.c r13 = com.scwang.smart.refresh.layout.constant.c.e
            if (r8 != r13) goto L_0x01f9
            com.scwang.smart.refresh.layout.constant.a r8 = r0.z5
            boolean r8 = r8.o
            if (r8 != 0) goto L_0x0243
        L_0x01f9:
            int r8 = android.view.View.MeasureSpec.getSize(r26)
            int r13 = r10.bottomMargin
            int r8 = r8 - r13
            int r13 = r10.topMargin
            int r8 = r8 - r13
            r13 = 0
            int r8 = java.lang.Math.max(r8, r13)
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r13)
            r7.measure(r12, r13)
            int r13 = r7.getMeasuredHeight()
            if (r13 <= 0) goto L_0x023c
            r14 = -1
            if (r13 == r8) goto L_0x0236
            com.scwang.smart.refresh.layout.constant.a r15 = r0.z5
            r18 = r8
            com.scwang.smart.refresh.layout.constant.a r8 = com.scwang.smart.refresh.layout.constant.a.c
            boolean r15 = r15.a(r8)
            if (r15 == 0) goto L_0x0233
            int r15 = r10.topMargin
            int r15 = r15 + r13
            r19 = r13
            int r13 = r10.bottomMargin
            int r15 = r15 + r13
            r0.y5 = r15
            r0.z5 = r8
            goto L_0x023a
        L_0x0233:
            r19 = r13
            goto L_0x023a
        L_0x0236:
            r18 = r8
            r19 = r13
        L_0x023a:
            r13 = r14
            goto L_0x0245
        L_0x023c:
            r18 = r8
            r19 = r13
            goto L_0x0243
        L_0x0241:
            r22 = r8
        L_0x0243:
            r13 = r21
        L_0x0245:
            com.scwang.smart.refresh.layout.api.a r8 = r0.I5
            com.scwang.smart.refresh.layout.constant.c r8 = r8.getSpinnerStyle()
            com.scwang.smart.refresh.layout.constant.c r14 = com.scwang.smart.refresh.layout.constant.c.e
            if (r8 != r14) goto L_0x0254
            int r13 = android.view.View.MeasureSpec.getSize(r26)
            goto L_0x0272
        L_0x0254:
            com.scwang.smart.refresh.layout.api.a r8 = r0.I5
            com.scwang.smart.refresh.layout.constant.c r8 = r8.getSpinnerStyle()
            boolean r8 = r8.i
            if (r8 == 0) goto L_0x0272
            if (r6 != 0) goto L_0x0272
            boolean r8 = r0.R4
            boolean r8 = r0.w(r8)
            if (r8 == 0) goto L_0x026c
            int r8 = r0.y
            int r8 = -r8
            goto L_0x026d
        L_0x026c:
            r8 = 0
        L_0x026d:
            r14 = 0
            int r13 = java.lang.Math.max(r14, r8)
        L_0x0272:
            r8 = -1
            if (r13 == r8) goto L_0x028b
            int r8 = r10.bottomMargin
            int r8 = r13 - r8
            int r14 = r10.topMargin
            int r8 = r8 - r14
            r14 = 0
            int r8 = java.lang.Math.max(r8, r14)
            r15 = 1073741824(0x40000000, float:2.0)
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r15)
            r7.measure(r12, r8)
            goto L_0x028c
        L_0x028b:
            r14 = 0
        L_0x028c:
            com.scwang.smart.refresh.layout.constant.a r8 = r0.z5
            boolean r15 = r8.o
            if (r15 != 0) goto L_0x02b4
            float r15 = r0.D5
            r16 = 1092616192(0x41200000, float:10.0)
            int r16 = (r15 > r16 ? 1 : (r15 == r16 ? 0 : -1))
            if (r16 >= 0) goto L_0x029e
            int r14 = r0.y5
            float r14 = (float) r14
            float r15 = r15 * r14
        L_0x029e:
            r14 = r15
            com.scwang.smart.refresh.layout.constant.a r8 = r8.b()
            r0.z5 = r8
            com.scwang.smart.refresh.layout.api.a r8 = r0.I5
            com.scwang.smart.refresh.layout.api.e r15 = r0.M5
            r16 = r10
            int r10 = r0.y5
            r17 = r12
            int r12 = (int) r14
            r8.g(r15, r10, r12)
            goto L_0x02b8
        L_0x02b4:
            r16 = r10
            r17 = r12
        L_0x02b8:
            if (r6 == 0) goto L_0x02cc
            boolean r8 = r0.R4
            boolean r8 = r0.w(r8)
            if (r8 == 0) goto L_0x02cc
            int r8 = r7.getMeasuredWidth()
            int r3 = r3 + r8
            int r8 = r7.getMeasuredHeight()
            int r4 = r4 + r8
        L_0x02cc:
            com.scwang.smart.refresh.layout.api.b r7 = r0.J5
            if (r7 == 0) goto L_0x037a
            android.view.View r7 = r7.getView()
            if (r7 != r11) goto L_0x037a
            com.scwang.smart.refresh.layout.api.b r7 = r0.J5
            android.view.View r7 = r7.getView()
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            boolean r10 = r8 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r10 == 0) goto L_0x02e8
            r10 = r8
            android.view.ViewGroup$MarginLayoutParams r10 = (android.view.ViewGroup.MarginLayoutParams) r10
            goto L_0x02ea
        L_0x02e8:
            android.view.ViewGroup$MarginLayoutParams r10 = q
        L_0x02ea:
            com.scwang.smart.refresh.layout.api.a r12 = r0.H5
            if (r12 == 0) goto L_0x0302
            boolean r12 = r0.Q4
            boolean r12 = r0.w(r12)
            if (r12 == 0) goto L_0x0302
            boolean r12 = r0.U4
            com.scwang.smart.refresh.layout.api.a r13 = r0.H5
            boolean r12 = r0.x(r12, r13)
            if (r12 == 0) goto L_0x0302
            r13 = 1
            goto L_0x0303
        L_0x0302:
            r13 = 0
        L_0x0303:
            r12 = r13
            com.scwang.smart.refresh.layout.api.a r13 = r0.I5
            if (r13 == 0) goto L_0x031c
            boolean r13 = r0.R4
            boolean r13 = r0.w(r13)
            if (r13 == 0) goto L_0x031c
            boolean r13 = r0.V4
            com.scwang.smart.refresh.layout.api.a r14 = r0.I5
            boolean r13 = r0.x(r13, r14)
            if (r13 == 0) goto L_0x031c
            r13 = 1
            goto L_0x031d
        L_0x031c:
            r13 = 0
        L_0x031d:
            int r14 = r5.getPaddingLeft()
            int r15 = r5.getPaddingRight()
            int r14 = r14 + r15
            int r15 = r10.leftMargin
            int r14 = r14 + r15
            int r15 = r10.rightMargin
            int r14 = r14 + r15
            int r15 = r8.width
            int r14 = android.view.ViewGroup.getChildMeasureSpec(r1, r14, r15)
            int r15 = r5.getPaddingTop()
            int r16 = r5.getPaddingBottom()
            int r15 = r15 + r16
            r16 = r11
            int r11 = r10.topMargin
            int r15 = r15 + r11
            int r11 = r10.bottomMargin
            int r15 = r15 + r11
            if (r6 == 0) goto L_0x034d
            if (r12 == 0) goto L_0x034d
            int r11 = r0.w5
            goto L_0x034e
        L_0x034d:
            r11 = 0
        L_0x034e:
            int r15 = r15 + r11
            if (r6 == 0) goto L_0x0356
            if (r13 == 0) goto L_0x0356
            int r11 = r0.y5
            goto L_0x0357
        L_0x0356:
            r11 = 0
        L_0x0357:
            int r15 = r15 + r11
            int r11 = r8.height
            int r11 = android.view.ViewGroup.getChildMeasureSpec(r2, r15, r11)
            r7.measure(r14, r11)
            int r15 = r7.getMeasuredWidth()
            r17 = r6
            int r6 = r10.leftMargin
            int r15 = r15 + r6
            int r6 = r10.rightMargin
            int r15 = r15 + r6
            int r3 = r3 + r15
            int r6 = r7.getMeasuredHeight()
            int r15 = r10.topMargin
            int r6 = r6 + r15
            int r15 = r10.bottomMargin
            int r6 = r6 + r15
            int r4 = r4 + r6
            goto L_0x0385
        L_0x037a:
            r17 = r6
            r16 = r11
            goto L_0x0385
        L_0x037f:
            r17 = r6
            r20 = r10
            r16 = r11
        L_0x0385:
            int r9 = r9 + 1
            r6 = r17
            r10 = r20
            goto L_0x001c
        L_0x038d:
            r17 = r6
            int r6 = r5.getPaddingLeft()
            int r7 = r5.getPaddingRight()
            int r6 = r6 + r7
            int r3 = r3 + r6
            int r6 = r5.getPaddingTop()
            int r7 = r5.getPaddingBottom()
            int r6 = r6 + r7
            int r4 = r4 + r6
            int r6 = super.getSuggestedMinimumWidth()
            int r6 = java.lang.Math.max(r3, r6)
            int r6 = android.view.View.resolveSize(r6, r1)
            int r7 = super.getSuggestedMinimumHeight()
            int r7 = java.lang.Math.max(r4, r7)
            int r7 = android.view.View.resolveSize(r7, r2)
            super.setMeasuredDimension(r6, r7)
            int r6 = r5.getMeasuredWidth()
            float r6 = (float) r6
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 / r7
            r0.p4 = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b2) {
        int paddingLeft;
        View thisView;
        int bottom;
        View thisView2 = this;
        int paddingLeft2 = thisView2.getPaddingLeft();
        int paddingTop = thisView2.getPaddingTop();
        int paddingBottom = thisView2.getPaddingBottom();
        int i2 = 0;
        int len = super.getChildCount();
        while (i2 < len) {
            View child = super.getChildAt(i2);
            if (child.getVisibility() == 8) {
                thisView = thisView2;
                paddingLeft = paddingLeft2;
            } else if ("GONE".equals(child.getTag(R$id.srl_tag))) {
                thisView = thisView2;
                paddingLeft = paddingLeft2;
            } else {
                com.scwang.smart.refresh.layout.api.b bVar = this.J5;
                if (bVar == null || bVar.getView() != child) {
                    paddingLeft = paddingLeft2;
                } else {
                    boolean isPreviewMode = thisView2.isInEditMode() && this.X4 && w(this.Q4) && this.H5 != null;
                    View contentView = this.J5.getView();
                    ViewGroup.LayoutParams lp = contentView.getLayoutParams();
                    ViewGroup.MarginLayoutParams mlp = lp instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) lp : q;
                    int left = mlp.leftMargin + paddingLeft2;
                    int top = mlp.topMargin + paddingTop;
                    int right = left + contentView.getMeasuredWidth();
                    int bottom2 = top + contentView.getMeasuredHeight();
                    if (isPreviewMode) {
                        paddingLeft = paddingLeft2;
                        if (x(this.U4, this.H5)) {
                            int i3 = this.w5;
                            top += i3;
                            bottom = bottom2 + i3;
                            contentView.layout(left, top, right, bottom);
                        }
                    } else {
                        paddingLeft = paddingLeft2;
                    }
                    bottom = bottom2;
                    contentView.layout(left, top, right, bottom);
                }
                com.scwang.smart.refresh.layout.api.a aVar = this.H5;
                if (aVar != null && aVar.getView() == child) {
                    boolean isPreviewMode2 = thisView2.isInEditMode() && this.X4 && w(this.Q4);
                    View headerView = this.H5.getView();
                    ViewGroup.LayoutParams lp2 = headerView.getLayoutParams();
                    ViewGroup.MarginLayoutParams mlp2 = lp2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) lp2 : q;
                    int left2 = mlp2.leftMargin;
                    int top2 = mlp2.topMargin + this.A5;
                    int right2 = headerView.getMeasuredWidth() + left2;
                    int bottom3 = headerView.getMeasuredHeight() + top2;
                    if (!isPreviewMode2) {
                        boolean z2 = isPreviewMode2;
                        if (this.H5.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.a) {
                            int i4 = this.w5;
                            top2 -= i4;
                            bottom3 -= i4;
                        }
                    }
                    headerView.layout(left2, top2, right2, bottom3);
                }
                com.scwang.smart.refresh.layout.api.a aVar2 = this.I5;
                if (aVar2 == null || aVar2.getView() != child) {
                    thisView = thisView2;
                } else {
                    boolean isPreviewMode3 = thisView2.isInEditMode() && this.X4 && w(this.R4);
                    View footerView = this.I5.getView();
                    ViewGroup.LayoutParams lp3 = footerView.getLayoutParams();
                    ViewGroup.MarginLayoutParams mlp3 = lp3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) lp3 : q;
                    com.scwang.smart.refresh.layout.constant.c style = this.I5.getSpinnerStyle();
                    int left3 = mlp3.leftMargin;
                    int top3 = (mlp3.topMargin + thisView2.getMeasuredHeight()) - this.B5;
                    if (!this.i5 || !this.j5 || !this.W4 || this.J5 == null) {
                        thisView = thisView2;
                    } else if (this.I5.getSpinnerStyle() != com.scwang.smart.refresh.layout.constant.c.a) {
                        thisView = thisView2;
                    } else if (w(this.R4)) {
                        View contentView2 = this.J5.getView();
                        ViewGroup.LayoutParams clp = contentView2.getLayoutParams();
                        thisView = thisView2;
                        top3 = paddingTop + paddingTop + (clp instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) clp).topMargin : 0) + contentView2.getMeasuredHeight();
                    } else {
                        thisView = thisView2;
                    }
                    if (style == com.scwang.smart.refresh.layout.constant.c.e) {
                        top3 = mlp3.topMargin - this.B5;
                    } else if (isPreviewMode3 || style == com.scwang.smart.refresh.layout.constant.c.d || style == com.scwang.smart.refresh.layout.constant.c.c) {
                        top3 -= this.y5;
                    } else if (style.i && this.y < 0) {
                        top3 -= Math.max(w(this.R4) ? -this.y : 0, 0);
                    }
                    footerView.layout(left3, top3, footerView.getMeasuredWidth() + left3, footerView.getMeasuredHeight() + top3);
                }
            }
            i2++;
            thisView2 = thisView;
            paddingLeft2 = paddingLeft;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.U5 = false;
        this.k5 = true;
        this.Y5 = null;
        if (this.Z5 != null) {
            this.Z5.removeAllListeners();
            this.Z5.removeAllUpdateListeners();
            this.Z5.setDuration(0);
            this.Z5.cancel();
            this.Z5 = null;
        }
        com.scwang.smart.refresh.layout.api.a aVar = this.H5;
        if (aVar != null && this.N5 == com.scwang.smart.refresh.layout.constant.b.Refreshing) {
            aVar.f(this, false);
        }
        com.scwang.smart.refresh.layout.api.a aVar2 = this.I5;
        if (aVar2 != null && this.N5 == com.scwang.smart.refresh.layout.constant.b.Loading) {
            aVar2.f(this, false);
        }
        if (this.y != 0) {
            this.M5.c(0, true);
        }
        com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
        com.scwang.smart.refresh.layout.constant.b bVar2 = com.scwang.smart.refresh.layout.constant.b.None;
        if (bVar != bVar2) {
            z(bVar2);
        }
        Handler handler = this.L5;
        if (handler != null) {
            handler.removeCallbacksAndMessages((Object) null);
        }
        this.V5 = false;
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Paint paint;
        Paint paint2;
        com.scwang.smart.refresh.layout.api.b bVar = this.J5;
        View contentView = bVar != null ? bVar.getView() : null;
        com.scwang.smart.refresh.layout.api.a aVar = this.H5;
        if (aVar != null && aVar.getView() == child) {
            if (!w(this.Q4) || (!this.X4 && isInEditMode())) {
                return true;
            }
            if (contentView != null) {
                int bottom = Math.max(contentView.getTop() + contentView.getPaddingTop() + this.y, child.getTop());
                int i2 = this.Q5;
                if (!(i2 == 0 || (paint2 = this.K5) == null)) {
                    paint2.setColor(i2);
                    if (this.H5.getSpinnerStyle().i) {
                        bottom = child.getBottom();
                    } else if (this.H5.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.a) {
                        bottom = child.getBottom() + this.y;
                    }
                    canvas.drawRect(0.0f, (float) child.getTop(), (float) getWidth(), (float) bottom, this.K5);
                }
                if ((this.S4 && this.H5.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.c) || this.H5.getSpinnerStyle().i) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), child.getTop(), child.getRight(), bottom);
                    boolean ret = super.drawChild(canvas, child, drawingTime);
                    canvas.restore();
                    return ret;
                }
            }
        }
        com.scwang.smart.refresh.layout.api.a aVar2 = this.I5;
        if (aVar2 != null && aVar2.getView() == child) {
            if (!w(this.R4) || (!this.X4 && isInEditMode())) {
                return true;
            }
            if (contentView != null) {
                int top = Math.min((contentView.getBottom() - contentView.getPaddingBottom()) + this.y, child.getBottom());
                int i3 = this.R5;
                if (!(i3 == 0 || (paint = this.K5) == null)) {
                    paint.setColor(i3);
                    if (this.I5.getSpinnerStyle().i) {
                        top = child.getTop();
                    } else if (this.I5.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.a) {
                        top = child.getTop() + this.y;
                    }
                    canvas.drawRect(0.0f, (float) top, (float) getWidth(), (float) child.getBottom(), this.K5);
                }
                if ((this.T4 && this.I5.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.c) || this.I5.getSpinnerStyle().i) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), top, child.getRight(), child.getBottom());
                    boolean ret2 = super.drawChild(canvas, child, drawingTime);
                    canvas.restore();
                    return ret2;
                }
            }
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    public void computeScroll() {
        int currY = this.M4.getCurrY();
        if (this.M4.computeScrollOffset()) {
            int finalY = this.M4.getFinalY();
            if ((finalY >= 0 || ((!this.Q4 && !this.Z4) || !this.J5.g())) && (finalY <= 0 || ((!this.R4 && !this.Z4) || !this.J5.i()))) {
                this.W5 = true;
                invalidate();
                return;
            }
            if (this.W5) {
                float velocity = this.M4.getCurrVelocity();
                if (finalY > 0) {
                    velocity = -velocity;
                }
                l(velocity);
            }
            this.M4.forceFinished(true);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00dc, code lost:
        if (r2.isFinishing == false) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00e0, code lost:
        if (r2.isHeader == false) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00ee, code lost:
        if (r2.isFinishing == false) goto L_0x00f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00f2, code lost:
        if (r2.isFooter == false) goto L_0x00f5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dispatchTouchEvent(android.view.MotionEvent r32) {
        /*
            r31 = this;
            r0 = r31
            r1 = r32
            int r10 = r32.getActionMasked()
            r2 = 6
            if (r10 != r2) goto L_0x000d
            r3 = 1
            goto L_0x000e
        L_0x000d:
            r3 = 0
        L_0x000e:
            r13 = r3
            if (r13 == 0) goto L_0x0016
            int r3 = r32.getActionIndex()
            goto L_0x0017
        L_0x0016:
            r3 = -1
        L_0x0017:
            r14 = r3
            r3 = 0
            r4 = 0
            int r15 = r32.getPointerCount()
            r5 = 0
            r16 = r3
            r17 = r4
        L_0x0023:
            if (r5 >= r15) goto L_0x0037
            if (r14 != r5) goto L_0x0028
            goto L_0x0034
        L_0x0028:
            float r3 = r1.getX(r5)
            float r16 = r16 + r3
            float r3 = r1.getY(r5)
            float r17 = r17 + r3
        L_0x0034:
            int r5 = r5 + 1
            goto L_0x0023
        L_0x0037:
            if (r13 == 0) goto L_0x003c
            int r3 = r15 + -1
            goto L_0x003d
        L_0x003c:
            r3 = r15
        L_0x003d:
            r9 = r3
            float r3 = (float) r9
            float r8 = r16 / r3
            float r3 = (float) r9
            float r7 = r17 / r3
            if (r10 == r2) goto L_0x0049
            r2 = 5
            if (r10 != r2) goto L_0x0056
        L_0x0049:
            boolean r2 = r0.C4
            if (r2 == 0) goto L_0x0056
            float r2 = r0.p3
            float r3 = r0.z4
            float r3 = r7 - r3
            float r2 = r2 + r3
            r0.p3 = r2
        L_0x0056:
            r0.p4 = r8
            r0.z4 = r7
            r18 = r31
            boolean r2 = r0.s5
            if (r2 == 0) goto L_0x00b5
            int r2 = r0.r5
            boolean r3 = super.dispatchTouchEvent(r32)
            r4 = 2
            if (r10 != r4) goto L_0x00b4
            int r4 = r0.r5
            if (r2 != r4) goto L_0x00b4
            float r4 = r0.p4
            int r4 = (int) r4
            int r5 = r18.getWidth()
            float r6 = r0.p4
            if (r5 != 0) goto L_0x007a
            r12 = 1
            goto L_0x007b
        L_0x007a:
            r12 = r5
        L_0x007b:
            float r11 = (float) r12
            float r6 = r6 / r11
            boolean r11 = r0.Q4
            boolean r11 = r0.w(r11)
            if (r11 == 0) goto L_0x0099
            int r11 = r0.y
            if (r11 <= 0) goto L_0x0099
            com.scwang.smart.refresh.layout.api.a r11 = r0.H5
            if (r11 == 0) goto L_0x0099
            boolean r11 = r11.m()
            if (r11 == 0) goto L_0x0099
            com.scwang.smart.refresh.layout.api.a r11 = r0.H5
            r11.k(r6, r4, r5)
            goto L_0x00b4
        L_0x0099:
            boolean r11 = r0.R4
            boolean r11 = r0.w(r11)
            if (r11 == 0) goto L_0x00b4
            int r11 = r0.y
            if (r11 >= 0) goto L_0x00b4
            com.scwang.smart.refresh.layout.api.a r11 = r0.I5
            if (r11 == 0) goto L_0x00b4
            boolean r11 = r11.m()
            if (r11 == 0) goto L_0x00b4
            com.scwang.smart.refresh.layout.api.a r11 = r0.I5
            r11.k(r6, r4, r5)
        L_0x00b4:
            return r3
        L_0x00b5:
            boolean r2 = r18.isEnabled()
            if (r2 == 0) goto L_0x03d9
            boolean r2 = r0.Q4
            if (r2 != 0) goto L_0x00d0
            boolean r2 = r0.R4
            if (r2 != 0) goto L_0x00d0
            boolean r2 = r0.Z4
            if (r2 == 0) goto L_0x00c8
            goto L_0x00d0
        L_0x00c8:
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
            goto L_0x03df
        L_0x00d0:
            boolean r2 = r0.S5
            if (r2 == 0) goto L_0x00e2
            com.scwang.smart.refresh.layout.constant.b r2 = r0.N5
            boolean r3 = r2.isOpening
            if (r3 != 0) goto L_0x00de
            boolean r3 = r2.isFinishing
            if (r3 == 0) goto L_0x00e2
        L_0x00de:
            boolean r2 = r2.isHeader
            if (r2 != 0) goto L_0x00c8
        L_0x00e2:
            boolean r2 = r0.T5
            if (r2 == 0) goto L_0x00f5
            com.scwang.smart.refresh.layout.constant.b r2 = r0.N5
            boolean r3 = r2.isOpening
            if (r3 != 0) goto L_0x00f0
            boolean r3 = r2.isFinishing
            if (r3 == 0) goto L_0x00f5
        L_0x00f0:
            boolean r2 = r2.isFooter
            if (r2 == 0) goto L_0x00f5
            goto L_0x00c8
        L_0x00f5:
            boolean r2 = r0.v(r10)
            if (r2 != 0) goto L_0x03d1
            com.scwang.smart.refresh.layout.constant.b r2 = r0.N5
            boolean r3 = r2.isFinishing
            if (r3 != 0) goto L_0x03d1
            com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.Loading
            if (r2 != r3) goto L_0x0112
            boolean r4 = r0.h5
            if (r4 != 0) goto L_0x010a
            goto L_0x0112
        L_0x010a:
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
            goto L_0x03d7
        L_0x0112:
            com.scwang.smart.refresh.layout.constant.b r4 = com.scwang.smart.refresh.layout.constant.b.Refreshing
            if (r2 != r4) goto L_0x011b
            boolean r2 = r0.g5
            if (r2 == 0) goto L_0x011b
            goto L_0x010a
        L_0x011b:
            r4 = 104(0x68, float:1.46E-43)
            switch(r10) {
                case 0: goto L_0x037b;
                case 1: goto L_0x0311;
                case 2: goto L_0x0128;
                case 3: goto L_0x032d;
                default: goto L_0x0120;
            }
        L_0x0120:
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
            goto L_0x03cc
        L_0x0128:
            float r6 = r0.p2
            float r6 = r8 - r6
            float r2 = r0.p3
            float r2 = r7 - r2
            android.view.VelocityTracker r11 = r0.N4
            r11.addMovement(r1)
            boolean r11 = r0.C4
            if (r11 != 0) goto L_0x0208
            boolean r11 = r0.E4
            if (r11 != 0) goto L_0x0208
            char r11 = r0.B4
            if (r11 == r4) goto L_0x0208
            com.scwang.smart.refresh.layout.api.b r12 = r0.J5
            if (r12 == 0) goto L_0x0208
            r12 = 118(0x76, float:1.65E-43)
            if (r11 == r12) goto L_0x0180
            float r11 = java.lang.Math.abs(r2)
            int r5 = r0.x
            float r5 = (float) r5
            int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r5 < 0) goto L_0x0161
            float r5 = java.lang.Math.abs(r6)
            float r11 = java.lang.Math.abs(r2)
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 >= 0) goto L_0x0161
            goto L_0x0180
        L_0x0161:
            float r3 = java.lang.Math.abs(r6)
            int r5 = r0.x
            float r5 = (float) r5
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 < 0) goto L_0x0208
            float r3 = java.lang.Math.abs(r6)
            float r5 = java.lang.Math.abs(r2)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x0208
            char r3 = r0.B4
            if (r3 == r12) goto L_0x0208
            r0.B4 = r4
            goto L_0x0208
        L_0x0180:
            r0.B4 = r12
            r4 = 0
            int r5 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x01a6
            int r4 = r0.y
            if (r4 < 0) goto L_0x019b
            boolean r4 = r0.Z4
            if (r4 != 0) goto L_0x0193
            boolean r4 = r0.Q4
            if (r4 == 0) goto L_0x01a6
        L_0x0193:
            com.scwang.smart.refresh.layout.api.b r4 = r0.J5
            boolean r4 = r4.g()
            if (r4 == 0) goto L_0x01a6
        L_0x019b:
            r3 = 1
            r0.C4 = r3
            int r3 = r0.x
            float r3 = (float) r3
            float r3 = r7 - r3
            r0.p3 = r3
            goto L_0x01d0
        L_0x01a6:
            r4 = 0
            int r5 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r5 >= 0) goto L_0x01d0
            int r4 = r0.y
            if (r4 > 0) goto L_0x01c7
            boolean r4 = r0.Z4
            if (r4 != 0) goto L_0x01b7
            boolean r4 = r0.R4
            if (r4 == 0) goto L_0x01d0
        L_0x01b7:
            com.scwang.smart.refresh.layout.constant.b r4 = r0.N5
            if (r4 != r3) goto L_0x01bf
            boolean r3 = r0.V5
            if (r3 != 0) goto L_0x01c7
        L_0x01bf:
            com.scwang.smart.refresh.layout.api.b r3 = r0.J5
            boolean r3 = r3.i()
            if (r3 == 0) goto L_0x01d0
        L_0x01c7:
            r3 = 1
            r0.C4 = r3
            int r3 = r0.x
            float r3 = (float) r3
            float r3 = r3 + r7
            r0.p3 = r3
        L_0x01d0:
            boolean r3 = r0.C4
            if (r3 == 0) goto L_0x0208
            float r3 = r0.p3
            float r2 = r7 - r3
            boolean r3 = r0.D4
            if (r3 == 0) goto L_0x01e3
            r3 = 3
            r1.setAction(r3)
            super.dispatchTouchEvent(r32)
        L_0x01e3:
            com.scwang.smart.refresh.layout.api.e r3 = r0.M5
            int r4 = r0.y
            if (r4 > 0) goto L_0x01f4
            if (r4 != 0) goto L_0x01f1
            r4 = 0
            int r5 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x01f1
            goto L_0x01f4
        L_0x01f1:
            com.scwang.smart.refresh.layout.constant.b r4 = com.scwang.smart.refresh.layout.constant.b.PullUpToLoad
            goto L_0x01f6
        L_0x01f4:
            com.scwang.smart.refresh.layout.constant.b r4 = com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh
        L_0x01f6:
            r3.f(r4)
            android.view.ViewParent r3 = r18.getParent()
            boolean r4 = r3 instanceof android.view.ViewGroup
            if (r4 == 0) goto L_0x0208
            r4 = r3
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            r5 = 1
            r4.requestDisallowInterceptTouchEvent(r5)
        L_0x0208:
            boolean r3 = r0.C4
            if (r3 == 0) goto L_0x02ef
            int r3 = (int) r2
            int r4 = r0.p0
            int r3 = r3 + r4
            com.scwang.smart.refresh.layout.constant.b r4 = r0.O5
            boolean r5 = r4.isHeader
            if (r5 == 0) goto L_0x021c
            if (r3 < 0) goto L_0x0226
            int r5 = r0.z
            if (r5 < 0) goto L_0x0226
        L_0x021c:
            boolean r4 = r4.isFooter
            if (r4 == 0) goto L_0x02e9
            if (r3 > 0) goto L_0x0226
            int r4 = r0.z
            if (r4 <= 0) goto L_0x02e9
        L_0x0226:
            r0.z = r3
            long r4 = r32.getEventTime()
            android.view.MotionEvent r11 = r0.X5
            if (r11 != 0) goto L_0x0249
            r27 = 0
            float r11 = r0.p2
            float r28 = r11 + r6
            float r11 = r0.p3
            r30 = 0
            r23 = r4
            r25 = r4
            r29 = r11
            android.view.MotionEvent r11 = android.view.MotionEvent.obtain(r23, r25, r27, r28, r29, r30)
            r0.X5 = r11
            super.dispatchTouchEvent(r11)
        L_0x0249:
            r27 = 2
            float r11 = r0.p2
            float r28 = r11 + r6
            float r11 = r0.p3
            float r12 = (float) r3
            float r29 = r11 + r12
            r30 = 0
            r23 = r4
            r25 = r4
            android.view.MotionEvent r11 = android.view.MotionEvent.obtain(r23, r25, r27, r28, r29, r30)
            super.dispatchTouchEvent(r11)
            boolean r12 = r0.V5
            if (r12 == 0) goto L_0x0273
            int r12 = r0.x
            float r12 = (float) r12
            int r12 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r12 <= 0) goto L_0x0273
            int r12 = r0.y
            if (r12 >= 0) goto L_0x0273
            r12 = 0
            r0.V5 = r12
        L_0x0273:
            if (r3 <= 0) goto L_0x0299
            boolean r12 = r0.Z4
            if (r12 != 0) goto L_0x027d
            boolean r12 = r0.Q4
            if (r12 == 0) goto L_0x0299
        L_0x027d:
            com.scwang.smart.refresh.layout.api.b r12 = r0.J5
            boolean r12 = r12.g()
            if (r12 == 0) goto L_0x0299
            r0.z4 = r7
            r0.p3 = r7
            r12 = 0
            r3 = r12
            r0.p0 = r12
            com.scwang.smart.refresh.layout.api.e r12 = r0.M5
            r20 = r3
            com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh
            r12.f(r3)
            r3 = r20
            goto L_0x02be
        L_0x0299:
            if (r3 >= 0) goto L_0x02be
            boolean r12 = r0.Z4
            if (r12 != 0) goto L_0x02a3
            boolean r12 = r0.R4
            if (r12 == 0) goto L_0x02be
        L_0x02a3:
            com.scwang.smart.refresh.layout.api.b r12 = r0.J5
            boolean r12 = r12.i()
            if (r12 == 0) goto L_0x02be
            r0.z4 = r7
            r0.p3 = r7
            r12 = 0
            r3 = r12
            r0.p0 = r12
            com.scwang.smart.refresh.layout.api.e r12 = r0.M5
            r20 = r3
            com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.PullUpToLoad
            r12.f(r3)
            r3 = r20
        L_0x02be:
            com.scwang.smart.refresh.layout.constant.b r12 = r0.O5
            r23 = r4
            boolean r4 = r12.isHeader
            if (r4 == 0) goto L_0x02c8
            if (r3 < 0) goto L_0x02ce
        L_0x02c8:
            boolean r4 = r12.isFooter
            if (r4 == 0) goto L_0x02d8
            if (r3 <= 0) goto L_0x02d8
        L_0x02ce:
            int r4 = r0.y
            if (r4 == 0) goto L_0x02d6
            r4 = 0
            r0.y(r4)
        L_0x02d6:
            r4 = 1
            return r4
        L_0x02d8:
            android.view.MotionEvent r4 = r0.X5
            if (r4 == 0) goto L_0x02e6
            r4 = 0
            r0.X5 = r4
            r4 = 3
            r11.setAction(r4)
            super.dispatchTouchEvent(r11)
        L_0x02e6:
            r11.recycle()
        L_0x02e9:
            float r4 = (float) r3
            r0.y(r4)
            r4 = 1
            return r4
        L_0x02ef:
            boolean r3 = r0.V5
            if (r3 == 0) goto L_0x0309
            int r3 = r0.x
            float r3 = (float) r3
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0309
            int r3 = r0.y
            if (r3 >= 0) goto L_0x0309
            r3 = 0
            r0.V5 = r3
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
            goto L_0x03cc
        L_0x0309:
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
            goto L_0x03cc
        L_0x0311:
            android.view.VelocityTracker r2 = r0.N4
            r2.addMovement(r1)
            android.view.VelocityTracker r2 = r0.N4
            r3 = 1000(0x3e8, float:1.401E-42)
            int r4 = r0.K4
            float r4 = (float) r4
            r2.computeCurrentVelocity(r3, r4)
            android.view.VelocityTracker r2 = r0.N4
            float r2 = r2.getYVelocity()
            int r2 = (int) r2
            r0.L4 = r2
            r2 = 0
            r0.J(r2)
        L_0x032d:
            android.view.VelocityTracker r2 = r0.N4
            r2.clear()
            r2 = 110(0x6e, float:1.54E-43)
            r0.B4 = r2
            android.view.MotionEvent r2 = r0.X5
            if (r2 == 0) goto L_0x0365
            r2.recycle()
            r2 = 0
            r0.X5 = r2
            long r11 = r32.getEventTime()
            float r6 = r0.p2
            r19 = 0
            r2 = r11
            r4 = r11
            r21 = r6
            r6 = r10
            r22 = r7
            r7 = r21
            r21 = r10
            r10 = r8
            r8 = r22
            r23 = r9
            r9 = r19
            android.view.MotionEvent r2 = android.view.MotionEvent.obtain(r2, r4, r6, r7, r8, r9)
            super.dispatchTouchEvent(r2)
            r2.recycle()
            goto L_0x036c
        L_0x0365:
            r22 = r7
            r23 = r9
            r21 = r10
            r10 = r8
        L_0x036c:
            r31.A()
            boolean r2 = r0.C4
            if (r2 == 0) goto L_0x0378
            r2 = 0
            r0.C4 = r2
            r3 = 1
            return r3
        L_0x0378:
            r2 = r22
            goto L_0x03cc
        L_0x037b:
            r22 = r7
            r23 = r9
            r21 = r10
            r2 = 0
            r3 = 1
            r10 = r8
            r0.L4 = r2
            android.view.VelocityTracker r2 = r0.N4
            r2.addMovement(r1)
            android.widget.Scroller r2 = r0.M4
            r2.forceFinished(r3)
            r0.p2 = r10
            r2 = r22
            r0.p3 = r2
            r3 = 0
            r0.z = r3
            int r5 = r0.y
            r0.p0 = r5
            r0.C4 = r3
            r0.E4 = r3
            boolean r3 = super.dispatchTouchEvent(r32)
            r0.D4 = r3
            com.scwang.smart.refresh.layout.constant.b r3 = r0.N5
            com.scwang.smart.refresh.layout.constant.b r5 = com.scwang.smart.refresh.layout.constant.b.TwoLevel
            if (r3 != r5) goto L_0x03c3
            float r3 = r0.p3
            int r5 = r18.getMeasuredHeight()
            float r5 = (float) r5
            r6 = 1065353216(0x3f800000, float:1.0)
            float r7 = r0.G5
            float r6 = r6 - r7
            float r5 = r5 * r6
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L_0x03c3
            r0.B4 = r4
            boolean r3 = r0.D4
            return r3
        L_0x03c3:
            com.scwang.smart.refresh.layout.api.b r3 = r0.J5
            if (r3 == 0) goto L_0x03ca
            r3.b(r1)
        L_0x03ca:
            r3 = 1
            return r3
        L_0x03cc:
            boolean r3 = super.dispatchTouchEvent(r32)
            return r3
        L_0x03d1:
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
        L_0x03d7:
            r3 = 0
            return r3
        L_0x03d9:
            r2 = r7
            r23 = r9
            r21 = r10
            r10 = r8
        L_0x03df:
            boolean r3 = super.dispatchTouchEvent(r32)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        View target = this.J5.f();
        if ((Build.VERSION.SDK_INT >= 21 || !(target instanceof AbsListView)) && ViewCompat.isNestedScrollingEnabled(target)) {
            this.E4 = disallowIntercept;
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    /* access modifiers changed from: protected */
    public boolean J(float flingVelocity) {
        float velocity = flingVelocity == 0.0f ? (float) this.L4 : flingVelocity;
        if (Build.VERSION.SDK_INT > 27 && this.J5 != null) {
            float scaleY = getScaleY();
            View contentView = this.J5.getView();
            if (getScaleY() == -1.0f && contentView.getScaleY() == -1.0f) {
                velocity = -velocity;
            }
        }
        if (Math.abs(velocity) > ((float) this.J4)) {
            int i2 = this.y;
            if (((float) i2) * velocity < 0.0f) {
                com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
                if (bVar == com.scwang.smart.refresh.layout.constant.b.Refreshing || bVar == com.scwang.smart.refresh.layout.constant.b.Loading || (i2 < 0 && this.i5)) {
                    this.Y5 = new j(velocity).a();
                    return true;
                } else if (bVar.isReleaseToOpening) {
                    return true;
                }
            }
            if ((velocity < 0.0f && ((this.Y4 && (this.R4 || this.Z4)) || ((this.N5 == com.scwang.smart.refresh.layout.constant.b.Loading && i2 >= 0) || (this.a5 && w(this.R4))))) || (velocity > 0.0f && ((this.Y4 && this.Q4) || this.Z4 || (this.N5 == com.scwang.smart.refresh.layout.constant.b.Refreshing && this.y <= 0)))) {
                this.W5 = false;
                this.M4.fling(0, 0, 0, (int) (-velocity), 0, 0, -2147483647, Integer.MAX_VALUE);
                this.M4.computeScrollOffset();
                invalidate();
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean v(int action) {
        if (action == 0) {
            if (this.Z5 != null) {
                com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
                if (bVar.isFinishing || bVar == com.scwang.smart.refresh.layout.constant.b.TwoLevelReleased || bVar == com.scwang.smart.refresh.layout.constant.b.RefreshReleased || bVar == com.scwang.smart.refresh.layout.constant.b.LoadReleased) {
                    return true;
                }
                if (bVar == com.scwang.smart.refresh.layout.constant.b.PullDownCanceled) {
                    this.M5.f(com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh);
                } else if (bVar == com.scwang.smart.refresh.layout.constant.b.PullUpCanceled) {
                    this.M5.f(com.scwang.smart.refresh.layout.constant.b.PullUpToLoad);
                }
                this.Z5.setDuration(0);
                this.Z5.cancel();
                this.Z5 = null;
            }
            this.Y5 = null;
        }
        if (this.Z5 != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void z(com.scwang.smart.refresh.layout.constant.b state) {
        com.scwang.smart.refresh.layout.constant.b oldState = this.N5;
        if (oldState != state) {
            this.N5 = state;
            this.O5 = state;
            com.scwang.smart.refresh.layout.listener.h refreshHeader = this.H5;
            com.scwang.smart.refresh.layout.listener.h refreshFooter = this.I5;
            com.scwang.smart.refresh.layout.listener.h refreshListener = this.p5;
            if (refreshHeader != null) {
                refreshHeader.h(this, oldState, state);
            }
            if (refreshFooter != null) {
                refreshFooter.h(this, oldState, state);
            }
            if (refreshListener != null) {
                refreshListener.h(this, oldState, state);
            }
            if (state == com.scwang.smart.refresh.layout.constant.b.LoadFinish) {
                this.V5 = false;
                return;
            }
            return;
        }
        com.scwang.smart.refresh.layout.constant.b bVar = this.O5;
        com.scwang.smart.refresh.layout.constant.b bVar2 = this.N5;
        if (bVar != bVar2) {
            this.O5 = bVar2;
        }
    }

    /* access modifiers changed from: protected */
    public void setStateDirectLoading(boolean triggerLoadMoreEvent) {
        com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
        com.scwang.smart.refresh.layout.constant.b bVar2 = com.scwang.smart.refresh.layout.constant.b.Loading;
        if (bVar != bVar2) {
            this.P5 = System.currentTimeMillis();
            this.V5 = true;
            z(bVar2);
            com.scwang.smart.refresh.layout.listener.e eVar = this.o5;
            if (eVar != null) {
                if (triggerLoadMoreEvent) {
                    eVar.f(this);
                }
            } else if (this.p5 == null) {
                b(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
            }
            com.scwang.smart.refresh.layout.api.a aVar = this.I5;
            if (aVar != null) {
                float maxDragHeight = this.D5;
                if (maxDragHeight < 10.0f) {
                    maxDragHeight *= (float) this.y5;
                }
                aVar.i(this, this.y5, (int) maxDragHeight);
            }
            if (this.p5 != null && (this.I5 instanceof com.scwang.smart.refresh.layout.api.c)) {
                com.scwang.smart.refresh.layout.listener.e listener = this.p5;
                if (triggerLoadMoreEvent) {
                    listener.f(this);
                }
                float maxDragHeight2 = this.D5;
                if (maxDragHeight2 < 10.0f) {
                    maxDragHeight2 *= (float) this.y5;
                }
                this.p5.p((com.scwang.smart.refresh.layout.api.c) this.I5, this.y5, (int) maxDragHeight2);
            }
        }
    }

    public class b extends AnimatorListenerAdapter {
        final /* synthetic */ boolean c;

        b(boolean z) {
            this.c = z;
        }

        public void onAnimationEnd(Animator animation) {
            if (animation == null || animation.getDuration() != 0) {
                SmartRefreshLayout.this.setStateDirectLoading(this.c);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setStateLoading(boolean notify) {
        AnimatorListenerAdapter listener = new b(notify);
        z(com.scwang.smart.refresh.layout.constant.b.LoadReleased);
        ValueAnimator animator = this.M5.b(-this.y5);
        if (animator != null) {
            animator.addListener(listener);
        }
        com.scwang.smart.refresh.layout.api.a aVar = this.I5;
        if (aVar != null) {
            float maxDragHeight = this.D5;
            if (maxDragHeight < 10.0f) {
                maxDragHeight *= (float) this.y5;
            }
            aVar.j(this, this.y5, (int) maxDragHeight);
        }
        com.scwang.smart.refresh.layout.listener.f fVar = this.p5;
        if (fVar != null) {
            com.scwang.smart.refresh.layout.api.a aVar2 = this.I5;
            if (aVar2 instanceof com.scwang.smart.refresh.layout.api.c) {
                float maxDragHeight2 = this.D5;
                if (maxDragHeight2 < 10.0f) {
                    maxDragHeight2 *= (float) this.y5;
                }
                fVar.e((com.scwang.smart.refresh.layout.api.c) aVar2, this.y5, (int) maxDragHeight2);
            }
        }
        if (animator == null) {
            listener.onAnimationEnd((Animator) null);
        }
    }

    public class c extends AnimatorListenerAdapter {
        final /* synthetic */ boolean c;

        c(boolean z) {
            this.c = z;
        }

        public void onAnimationEnd(Animator animation) {
            if (animation == null || animation.getDuration() != 0) {
                SmartRefreshLayout.this.P5 = System.currentTimeMillis();
                SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.Refreshing);
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                com.scwang.smart.refresh.layout.listener.g gVar = smartRefreshLayout.n5;
                if (gVar != null) {
                    if (this.c) {
                        gVar.b(smartRefreshLayout);
                    }
                } else if (smartRefreshLayout.p5 == null) {
                    smartRefreshLayout.r(3000);
                }
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                com.scwang.smart.refresh.layout.api.a aVar = smartRefreshLayout2.H5;
                if (aVar != null) {
                    float f = smartRefreshLayout2.C5;
                    if (f < 10.0f) {
                        f *= (float) smartRefreshLayout2.w5;
                    }
                    aVar.i(smartRefreshLayout2, smartRefreshLayout2.w5, (int) f);
                }
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                com.scwang.smart.refresh.layout.listener.f fVar = smartRefreshLayout3.p5;
                if (fVar != null && (smartRefreshLayout3.H5 instanceof com.scwang.smart.refresh.layout.api.d)) {
                    if (this.c) {
                        fVar.b(smartRefreshLayout3);
                    }
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    float f2 = smartRefreshLayout4.C5;
                    if (f2 < 10.0f) {
                        f2 *= (float) smartRefreshLayout4.w5;
                    }
                    smartRefreshLayout4.p5.a((com.scwang.smart.refresh.layout.api.d) smartRefreshLayout4.H5, smartRefreshLayout4.w5, (int) f2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setStateRefreshing(boolean notify) {
        AnimatorListenerAdapter listener = new c(notify);
        z(com.scwang.smart.refresh.layout.constant.b.RefreshReleased);
        ValueAnimator animator = this.M5.b(this.w5);
        if (animator != null) {
            animator.addListener(listener);
        }
        com.scwang.smart.refresh.layout.api.a aVar = this.H5;
        if (aVar != null) {
            float f2 = this.C5;
            if (f2 < 10.0f) {
                f2 *= (float) this.w5;
            }
            aVar.j(this, this.w5, (int) f2);
        }
        com.scwang.smart.refresh.layout.listener.f fVar = this.p5;
        if (fVar != null) {
            com.scwang.smart.refresh.layout.api.a aVar2 = this.H5;
            if (aVar2 instanceof com.scwang.smart.refresh.layout.api.d) {
                float f3 = this.C5;
                if (f3 < 10.0f) {
                    f3 *= (float) this.w5;
                }
                fVar.d((com.scwang.smart.refresh.layout.api.d) aVar2, this.w5, (int) f3);
            }
        }
        if (animator == null) {
            listener.onAnimationEnd((Animator) null);
        }
    }

    /* access modifiers changed from: protected */
    public void setViceState(com.scwang.smart.refresh.layout.constant.b state) {
        com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
        if (bVar.isDragging && bVar.isHeader != state.isHeader) {
            z(com.scwang.smart.refresh.layout.constant.b.None);
        }
        if (this.O5 != state) {
            this.O5 = state;
        }
    }

    /* access modifiers changed from: protected */
    public boolean x(boolean enable, @Nullable com.scwang.smart.refresh.layout.api.a internal) {
        return enable || this.b5 || internal == null || internal.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.c;
    }

    /* access modifiers changed from: protected */
    public boolean w(boolean enable) {
        return enable && !this.b5;
    }

    public class j implements Runnable {
        int c;
        int d = 0;
        int f = 10;
        float q;
        float x = 0.98f;
        long y = 0;
        long z = AnimationUtils.currentAnimationTimeMillis();

        j(float velocity) {
            this.q = velocity;
            this.c = SmartRefreshLayout.this.y;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0049, code lost:
            if (r0.y >= (-r0.y5)) goto L_0x004b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0057, code lost:
            if (r0.y > r0.w5) goto L_0x0059;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Runnable a() {
            /*
                r11 = this;
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r1 = r0.N5
                boolean r2 = r1.isFinishing
                r3 = 0
                if (r2 == 0) goto L_0x000a
                return r3
            L_0x000a:
                int r2 = r0.y
                if (r2 == 0) goto L_0x00a8
                boolean r1 = r1.isOpening
                if (r1 != 0) goto L_0x0026
                boolean r1 = r0.i5
                if (r1 == 0) goto L_0x0059
                boolean r1 = r0.W4
                if (r1 == 0) goto L_0x0059
                boolean r1 = r0.j5
                if (r1 == 0) goto L_0x0059
                boolean r1 = r0.R4
                boolean r0 = r0.w(r1)
                if (r0 == 0) goto L_0x0059
            L_0x0026:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r1 = r0.N5
                com.scwang.smart.refresh.layout.constant.b r2 = com.scwang.smart.refresh.layout.constant.b.Loading
                if (r1 == r2) goto L_0x0042
                boolean r1 = r0.i5
                if (r1 == 0) goto L_0x004b
                boolean r1 = r0.W4
                if (r1 == 0) goto L_0x004b
                boolean r1 = r0.j5
                if (r1 == 0) goto L_0x004b
                boolean r1 = r0.R4
                boolean r0 = r0.w(r1)
                if (r0 == 0) goto L_0x004b
            L_0x0042:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r1 = r0.y
                int r0 = r0.y5
                int r0 = -r0
                if (r1 < r0) goto L_0x0059
            L_0x004b:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r1 = r0.N5
                com.scwang.smart.refresh.layout.constant.b r2 = com.scwang.smart.refresh.layout.constant.b.Refreshing
                if (r1 != r2) goto L_0x00a8
                int r1 = r0.y
                int r0 = r0.w5
                if (r1 <= r0) goto L_0x00a8
            L_0x0059:
                r0 = 0
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r2 = r1.y
                int r1 = r1.y
                float r4 = r11.q
            L_0x0062:
                int r5 = r1 * r2
                if (r5 <= 0) goto L_0x00a8
                double r5 = (double) r4
                float r7 = r11.x
                double r7 = (double) r7
                int r0 = r0 + 1
                int r9 = r11.f
                int r9 = r9 * r0
                float r9 = (float) r9
                r10 = 1092616192(0x41200000, float:10.0)
                float r9 = r9 / r10
                double r9 = (double) r9
                double r7 = java.lang.Math.pow(r7, r9)
                double r5 = r5 * r7
                float r4 = (float) r5
                int r5 = r11.f
                float r5 = (float) r5
                r6 = 1065353216(0x3f800000, float:1.0)
                float r5 = r5 * r6
                r7 = 1148846080(0x447a0000, float:1000.0)
                float r5 = r5 / r7
                float r5 = r5 * r4
                float r7 = java.lang.Math.abs(r5)
                int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                if (r6 >= 0) goto L_0x00a4
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r7 = r6.N5
                boolean r8 = r7.isOpening
                if (r8 == 0) goto L_0x00a3
                com.scwang.smart.refresh.layout.constant.b r8 = com.scwang.smart.refresh.layout.constant.b.Refreshing
                if (r7 != r8) goto L_0x009c
                int r9 = r6.w5
                if (r2 > r9) goto L_0x00a3
            L_0x009c:
                if (r7 == r8) goto L_0x00a8
                int r6 = r6.y5
                int r6 = -r6
                if (r2 >= r6) goto L_0x00a8
            L_0x00a3:
                return r3
            L_0x00a4:
                float r6 = (float) r2
                float r6 = r6 + r5
                int r2 = (int) r6
                goto L_0x0062
            L_0x00a8:
                long r0 = android.view.animation.AnimationUtils.currentAnimationTimeMillis()
                r11.y = r0
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                android.os.Handler r0 = r0.L5
                int r1 = r11.f
                long r1 = (long) r1
                r0.postDelayed(r11, r1)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.j.a():java.lang.Runnable");
        }

        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.Y5 == this && !smartRefreshLayout.N5.isFinishing) {
                long now = AnimationUtils.currentAnimationTimeMillis();
                float pow = (float) (((double) this.q) * Math.pow((double) this.x, (double) (((float) (now - this.y)) / (1000.0f / ((float) this.f)))));
                this.q = pow;
                float velocity = pow * ((((float) (now - this.z)) * 1.0f) / 1000.0f);
                if (Math.abs(velocity) > 1.0f) {
                    this.z = now;
                    int i = (int) (((float) this.c) + velocity);
                    this.c = i;
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    if (smartRefreshLayout2.y * i > 0) {
                        smartRefreshLayout2.M5.c(i, true);
                        SmartRefreshLayout.this.L5.postDelayed(this, (long) this.f);
                        return;
                    }
                    smartRefreshLayout2.Y5 = null;
                    smartRefreshLayout2.M5.c(0, true);
                    com.scwang.smart.refresh.layout.util.b.d(SmartRefreshLayout.this.J5.f(), (int) (-this.q));
                    SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    if (smartRefreshLayout3.V5 && velocity > 0.0f) {
                        smartRefreshLayout3.V5 = false;
                        return;
                    }
                    return;
                }
                SmartRefreshLayout.this.Y5 = null;
            }
        }
    }

    public class i implements Runnable {
        int c = 0;
        int d = 10;
        int f;
        long q;
        float x = 0.0f;
        float y;

        i(float velocity, int smoothDistance) {
            this.y = velocity;
            this.f = smoothDistance;
            this.q = AnimationUtils.currentAnimationTimeMillis();
            SmartRefreshLayout.this.L5.postDelayed(this, (long) this.d);
            if (velocity > 0.0f) {
                SmartRefreshLayout.this.M5.f(com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh);
            } else {
                SmartRefreshLayout.this.M5.f(com.scwang.smart.refresh.layout.constant.b.PullUpToLoad);
            }
        }

        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.Y5 == this && !smartRefreshLayout.N5.isFinishing) {
                if (Math.abs(smartRefreshLayout.y) < Math.abs(this.f)) {
                    int i = this.c + 1;
                    this.c = i;
                    this.y = (float) (((double) this.y) * Math.pow(0.949999988079071d, (double) (i * 2)));
                } else if (this.f != 0) {
                    int i2 = this.c + 1;
                    this.c = i2;
                    this.y = (float) (((double) this.y) * Math.pow(0.44999998807907104d, (double) (i2 * 2)));
                } else {
                    int i3 = this.c + 1;
                    this.c = i3;
                    this.y = (float) (((double) this.y) * Math.pow(0.8500000238418579d, (double) (i3 * 2)));
                }
                long now = AnimationUtils.currentAnimationTimeMillis();
                float velocity = this.y * ((((float) (now - this.q)) * 1.0f) / 1000.0f);
                if (Math.abs(velocity) >= 1.0f) {
                    this.q = now;
                    float f2 = this.x + velocity;
                    this.x = f2;
                    SmartRefreshLayout.this.y(f2);
                    SmartRefreshLayout.this.L5.postDelayed(this, (long) this.d);
                    return;
                }
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                com.scwang.smart.refresh.layout.constant.b bVar = smartRefreshLayout2.O5;
                boolean z2 = bVar.isDragging;
                if (z2 && bVar.isHeader) {
                    smartRefreshLayout2.M5.f(com.scwang.smart.refresh.layout.constant.b.PullDownCanceled);
                } else if (z2 && bVar.isFooter) {
                    smartRefreshLayout2.M5.f(com.scwang.smart.refresh.layout.constant.b.PullUpCanceled);
                }
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                smartRefreshLayout3.Y5 = null;
                if (Math.abs(smartRefreshLayout3.y) >= Math.abs(this.f)) {
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    smartRefreshLayout4.k(this.f, 0, smartRefreshLayout4.O4, Math.min(Math.max((int) com.scwang.smart.refresh.layout.util.b.i(Math.abs(SmartRefreshLayout.this.y - this.f)), 30), 100) * 10);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ValueAnimator k(int endSpinner, int startDelay, Interpolator interpolator, int duration) {
        if (this.y == endSpinner) {
            return null;
        }
        ValueAnimator valueAnimator = this.Z5;
        if (valueAnimator != null) {
            valueAnimator.setDuration(0);
            this.Z5.cancel();
            this.Z5 = null;
        }
        this.Y5 = null;
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.y, endSpinner});
        this.Z5 = ofInt;
        ofInt.setDuration((long) duration);
        this.Z5.setInterpolator(interpolator);
        this.Z5.addListener(new d());
        this.Z5.addUpdateListener(new e());
        this.Z5.setStartDelay((long) startDelay);
        this.Z5.start();
        return this.Z5;
    }

    public class d extends AnimatorListenerAdapter {
        d() {
        }

        public void onAnimationEnd(Animator animation) {
            com.scwang.smart.refresh.layout.constant.b bVar;
            com.scwang.smart.refresh.layout.constant.b bVar2;
            if (animation == null || animation.getDuration() != 0) {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                smartRefreshLayout.Z5 = null;
                if (smartRefreshLayout.y != 0 || (bVar = smartRefreshLayout.N5) == (bVar2 = com.scwang.smart.refresh.layout.constant.b.None) || bVar.isOpening || bVar.isDragging) {
                    com.scwang.smart.refresh.layout.constant.b bVar3 = smartRefreshLayout.N5;
                    if (bVar3 != smartRefreshLayout.O5) {
                        smartRefreshLayout.setViceState(bVar3);
                        return;
                    }
                    return;
                }
                smartRefreshLayout.z(bVar2);
            }
        }
    }

    public class e implements ValueAnimator.AnimatorUpdateListener {
        e() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            SmartRefreshLayout.this.M5.c(((Integer) animation.getAnimatedValue()).intValue(), false);
        }
    }

    /* access modifiers changed from: protected */
    public void l(float velocity) {
        com.scwang.smart.refresh.layout.constant.b bVar;
        if (this.Z5 != null) {
            return;
        }
        if (velocity > 0.0f && ((bVar = this.N5) == com.scwang.smart.refresh.layout.constant.b.Refreshing || bVar == com.scwang.smart.refresh.layout.constant.b.TwoLevel)) {
            this.Y5 = new i(velocity, this.w5);
        } else if (velocity < 0.0f && (this.N5 == com.scwang.smart.refresh.layout.constant.b.Loading || ((this.W4 && this.i5 && this.j5 && w(this.R4)) || (this.a5 && !this.i5 && w(this.R4) && this.N5 != com.scwang.smart.refresh.layout.constant.b.Refreshing)))) {
            this.Y5 = new i(velocity, -this.y5);
        } else if (this.y == 0 && this.Y4) {
            this.Y5 = new i(velocity, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void A() {
        com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
        if (bVar == com.scwang.smart.refresh.layout.constant.b.TwoLevel) {
            if (this.L4 > -1000 && this.y > getHeight() / 2) {
                ValueAnimator animator = this.M5.b(getHeight());
                if (animator != null) {
                    animator.setDuration((long) this.a1);
                }
            } else if (this.C4) {
                this.M5.a();
            }
        } else {
            com.scwang.smart.refresh.layout.constant.b bVar2 = com.scwang.smart.refresh.layout.constant.b.Loading;
            if (bVar == bVar2 || (this.W4 && this.i5 && this.j5 && this.y < 0 && w(this.R4))) {
                int i2 = this.y;
                int i3 = this.y5;
                if (i2 < (-i3)) {
                    this.M5.b(-i3);
                } else if (i2 > 0) {
                    this.M5.b(0);
                }
            } else {
                com.scwang.smart.refresh.layout.constant.b bVar3 = this.N5;
                com.scwang.smart.refresh.layout.constant.b bVar4 = com.scwang.smart.refresh.layout.constant.b.Refreshing;
                if (bVar3 == bVar4) {
                    int i4 = this.y;
                    int i6 = this.w5;
                    if (i4 > i6) {
                        this.M5.b(i6);
                    } else if (i4 < 0) {
                        this.M5.b(0);
                    }
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh) {
                    this.M5.f(com.scwang.smart.refresh.layout.constant.b.PullDownCanceled);
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.PullUpToLoad) {
                    this.M5.f(com.scwang.smart.refresh.layout.constant.b.PullUpCanceled);
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.ReleaseToRefresh) {
                    this.M5.f(bVar4);
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.ReleaseToLoad) {
                    this.M5.f(bVar2);
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.ReleaseToTwoLevel) {
                    this.M5.f(com.scwang.smart.refresh.layout.constant.b.TwoLevelReleased);
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.RefreshReleased) {
                    if (this.Z5 == null) {
                        this.M5.b(this.w5);
                    }
                } else if (bVar3 == com.scwang.smart.refresh.layout.constant.b.LoadReleased) {
                    if (this.Z5 == null) {
                        this.M5.b(-this.y5);
                    }
                } else if (bVar3 != com.scwang.smart.refresh.layout.constant.b.LoadFinish && this.y != 0) {
                    this.M5.b(0);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void y(float spinner) {
        float spinner2;
        com.scwang.smart.refresh.layout.constant.b bVar;
        if (!this.s5 || this.e5 || spinner >= 0.0f || this.J5.i()) {
            spinner2 = spinner;
        } else {
            spinner2 = 0.0f;
        }
        if (spinner2 > ((float) (this.a2 * 5)) && getTag() == null) {
            int i2 = R$id.srl_tag;
            if (getTag(i2) == null) {
                float f2 = this.z4;
                int i3 = this.a2;
                if (f2 < ((float) i3) / 6.0f && this.p4 < ((float) i3) / 16.0f) {
                    Toast.makeText(getContext(), "你这么死拉，臣妾做不到啊！", 0).show();
                    setTag(i2, "你这么死拉，臣妾做不到啊！");
                }
            }
        }
        com.scwang.smart.refresh.layout.constant.b bVar2 = this.N5;
        if (bVar2 == com.scwang.smart.refresh.layout.constant.b.TwoLevel && spinner2 > 0.0f) {
            this.M5.c(Math.min((int) spinner2, getMeasuredHeight()), true);
        } else if (bVar2 == com.scwang.smart.refresh.layout.constant.b.Refreshing && spinner2 >= 0.0f) {
            int i4 = this.w5;
            if (spinner2 < ((float) i4)) {
                this.M5.c((int) spinner2, true);
            } else {
                float maxDragHeight = this.C5;
                if (maxDragHeight < 10.0f) {
                    maxDragHeight *= (float) i4;
                }
                float f3 = maxDragHeight;
                double M = (double) (maxDragHeight - ((float) i4));
                int max = Math.max((this.a2 * 4) / 3, getHeight());
                int i6 = this.w5;
                double H = (double) (max - i6);
                double x2 = (double) Math.max(0.0f, (spinner2 - ((float) i6)) * this.A4);
                this.M5.c(((int) Math.min((1.0d - Math.pow(100.0d, (-x2) / (H == 0.0d ? 1.0d : H))) * M, x2)) + this.w5, true);
            }
        } else if (spinner2 < 0.0f && (bVar2 == com.scwang.smart.refresh.layout.constant.b.Loading || ((this.W4 && this.i5 && this.j5 && w(this.R4)) || (this.a5 && !this.i5 && w(this.R4))))) {
            int i7 = this.y5;
            if (spinner2 > ((float) (-i7))) {
                this.M5.c((int) spinner2, true);
            } else {
                float maxDragHeight2 = this.D5;
                if (maxDragHeight2 < 10.0f) {
                    maxDragHeight2 *= (float) i7;
                }
                double M2 = (double) (maxDragHeight2 - ((float) i7));
                int max2 = Math.max((this.a2 * 4) / 3, getHeight());
                int i8 = this.y5;
                double H2 = (double) (max2 - i8);
                double x3 = (double) (-Math.min(0.0f, (((float) i8) + spinner2) * this.A4));
                float f4 = maxDragHeight2;
                this.M5.c(((int) (-Math.min((1.0d - Math.pow(100.0d, (-x3) / (H2 == 0.0d ? 1.0d : H2))) * M2, x3))) - this.y5, true);
            }
        } else if (spinner2 >= 0.0f) {
            float f6 = this.C5;
            double M3 = f6 < 10.0f ? (double) (((float) this.w5) * f6) : (double) f6;
            double H3 = (double) Math.max(this.a2 / 2, getHeight());
            double x4 = (double) Math.max(0.0f, this.A4 * spinner2);
            this.M5.c((int) Math.min((1.0d - Math.pow(100.0d, (-x4) / (H3 == 0.0d ? 1.0d : H3))) * M3, x4), true);
        } else {
            float f7 = this.D5;
            double M6 = f7 < 10.0f ? (double) (((float) this.y5) * f7) : (double) f7;
            double H6 = (double) Math.max(this.a2 / 2, getHeight());
            double x6 = (double) (-Math.min(0.0f, this.A4 * spinner2));
            this.M5.c((int) (-Math.min((1.0d - Math.pow(100.0d, (-x6) / (H6 == 0.0d ? 1.0d : H6))) * M6, x6)), true);
        }
        if (this.a5 && !this.i5 && w(this.R4) && spinner2 < 0.0f && (bVar = this.N5) != com.scwang.smart.refresh.layout.constant.b.Refreshing && bVar != com.scwang.smart.refresh.layout.constant.b.Loading && bVar != com.scwang.smart.refresh.layout.constant.b.LoadFinish) {
            if (this.h5) {
                this.Y5 = null;
                this.M5.b(-this.y5);
            }
            setStateDirectLoading(false);
            this.L5.postDelayed(new f(), (long) this.p1);
        }
    }

    public class f implements Runnable {
        f() {
        }

        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            com.scwang.smart.refresh.layout.listener.e eVar = smartRefreshLayout.o5;
            if (eVar != null) {
                eVar.f(smartRefreshLayout);
            } else if (smartRefreshLayout.p5 == null) {
                smartRefreshLayout.b(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
            }
            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
            com.scwang.smart.refresh.layout.listener.e listener = smartRefreshLayout2.p5;
            if (listener != null) {
                listener.f(smartRefreshLayout2);
            }
        }
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int backgroundColor = 0;
        public com.scwang.smart.refresh.layout.constant.c spinnerStyle = null;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray ta = context.obtainStyledAttributes(attrs, R$styleable.SmartRefreshLayout_Layout);
            this.backgroundColor = ta.getColor(R$styleable.SmartRefreshLayout_Layout_layout_srlBackgroundColor, this.backgroundColor);
            int i = R$styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle;
            if (ta.hasValue(i)) {
                this.spinnerStyle = com.scwang.smart.refresh.layout.constant.c.f[ta.getInt(i, com.scwang.smart.refresh.layout.constant.c.a.g)];
            }
            ta.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    public int getNestedScrollAxes() {
        return this.v5.getNestedScrollAxes();
    }

    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        boolean accepted = true;
        if (!(isEnabled() && isNestedScrollingEnabled() && (nestedScrollAxes & 2) != 0) || (!this.Z4 && !this.Q4 && !this.R4)) {
            accepted = false;
        }
        return accepted;
    }

    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        this.v5.onNestedScrollAccepted(child, target, axes);
        this.u5.startNestedScroll(axes & 2);
        this.r5 = this.y;
        this.s5 = true;
        v(0);
    }

    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        int consumedY = 0;
        int i2 = this.r5;
        if (dy * i2 > 0) {
            if (Math.abs(dy) > Math.abs(this.r5)) {
                consumedY = this.r5;
                this.r5 = 0;
            } else {
                consumedY = dy;
                this.r5 -= dy;
            }
            y((float) this.r5);
        } else if (dy > 0 && this.V5) {
            consumedY = dy;
            int i3 = i2 - dy;
            this.r5 = i3;
            y((float) i3);
        }
        this.u5.dispatchNestedPreScroll(dx, dy - consumedY, consumed, (int[]) null);
        consumed[1] = consumed[1] + consumedY;
    }

    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        ViewParent parent;
        com.scwang.smart.refresh.layout.listener.i iVar;
        com.scwang.smart.refresh.layout.listener.i iVar2;
        boolean scrolled = this.u5.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, this.t5);
        int dy = this.t5[1] + dyUnconsumed;
        if ((dy < 0 && ((this.Q4 || this.Z4) && (this.r5 != 0 || (iVar2 = this.q5) == null || iVar2.a(this.J5.getView())))) || (dy > 0 && ((this.R4 || this.Z4) && (this.r5 != 0 || (iVar = this.q5) == null || iVar.b(this.J5.getView()))))) {
            com.scwang.smart.refresh.layout.constant.b bVar = this.O5;
            if (bVar == com.scwang.smart.refresh.layout.constant.b.None || bVar.isOpening) {
                this.M5.f(dy > 0 ? com.scwang.smart.refresh.layout.constant.b.PullUpToLoad : com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh);
                if (!scrolled && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            int i2 = this.r5 - dy;
            this.r5 = i2;
            y((float) i2);
        }
        if (this.V5 && dyConsumed < 0) {
            this.V5 = false;
        }
    }

    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return (this.V5 && velocityY > 0.0f) || J(-velocityY) || this.u5.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return this.u5.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public void onStopNestedScroll(@NonNull View target) {
        this.v5.onStopNestedScroll(target);
        this.s5 = false;
        this.r5 = 0;
        A();
        this.u5.stopNestedScroll();
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        this.f5 = enabled;
        this.u5.setNestedScrollingEnabled(enabled);
    }

    public boolean isNestedScrollingEnabled() {
        return this.f5 && (this.Z4 || this.Q4 || this.R4);
    }

    public com.scwang.smart.refresh.layout.api.f c(boolean enabled) {
        this.k5 = true;
        this.R4 = enabled;
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f B(boolean enabled) {
        this.Q4 = enabled;
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f d(boolean enabled) {
        setNestedScrollingEnabled(enabled);
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f H(@NonNull com.scwang.smart.refresh.layout.api.d header) {
        return I(header, 0, 0);
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [android.view.ViewGroup$LayoutParams] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.scwang.smart.refresh.layout.api.f I(@androidx.annotation.NonNull com.scwang.smart.refresh.layout.api.d r6, int r7, int r8) {
        /*
            r5 = this;
            com.scwang.smart.refresh.layout.api.a r0 = r5.H5
            if (r0 == 0) goto L_0x000b
            android.view.View r0 = r0.getView()
            super.removeView(r0)
        L_0x000b:
            r5.H5 = r6
            r0 = 0
            r5.Q5 = r0
            r5.S5 = r0
            com.scwang.smart.refresh.layout.constant.a r1 = com.scwang.smart.refresh.layout.constant.a.a
            r5.x5 = r1
            if (r7 != 0) goto L_0x001a
            r1 = -1
            goto L_0x001b
        L_0x001a:
            r1 = r7
        L_0x001b:
            r7 = r1
            if (r8 != 0) goto L_0x0020
            r1 = -2
            goto L_0x0021
        L_0x0020:
            r1 = r8
        L_0x0021:
            r8 = r1
            com.scwang.smart.refresh.layout.SmartRefreshLayout$LayoutParams r1 = new com.scwang.smart.refresh.layout.SmartRefreshLayout$LayoutParams
            r1.<init>((int) r7, (int) r8)
            android.view.View r2 = r6.getView()
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            boolean r3 = r2 instanceof com.scwang.smart.refresh.layout.SmartRefreshLayout.LayoutParams
            if (r3 == 0) goto L_0x0036
            r1 = r2
            com.scwang.smart.refresh.layout.SmartRefreshLayout$LayoutParams r1 = (com.scwang.smart.refresh.layout.SmartRefreshLayout.LayoutParams) r1
        L_0x0036:
            com.scwang.smart.refresh.layout.api.a r3 = r5.H5
            com.scwang.smart.refresh.layout.constant.c r3 = r3.getSpinnerStyle()
            boolean r3 = r3.h
            if (r3 == 0) goto L_0x004f
            r0 = r5
            com.scwang.smart.refresh.layout.api.a r3 = r5.H5
            android.view.View r3 = r3.getView()
            int r4 = r0.getChildCount()
            super.addView(r3, r4, r1)
            goto L_0x0058
        L_0x004f:
            com.scwang.smart.refresh.layout.api.a r3 = r5.H5
            android.view.View r3 = r3.getView()
            super.addView(r3, r0, r1)
        L_0x0058:
            int[] r0 = r5.P4
            if (r0 == 0) goto L_0x0063
            com.scwang.smart.refresh.layout.api.a r3 = r5.H5
            if (r3 == 0) goto L_0x0063
            r3.setPrimaryColors(r0)
        L_0x0063:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.I(com.scwang.smart.refresh.layout.api.d, int, int):com.scwang.smart.refresh.layout.api.f");
    }

    public com.scwang.smart.refresh.layout.api.f F(@NonNull com.scwang.smart.refresh.layout.api.c footer) {
        return G(footer, 0, 0);
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [android.view.ViewGroup$LayoutParams] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.scwang.smart.refresh.layout.api.f G(@androidx.annotation.NonNull com.scwang.smart.refresh.layout.api.c r6, int r7, int r8) {
        /*
            r5 = this;
            com.scwang.smart.refresh.layout.api.a r0 = r5.I5
            if (r0 == 0) goto L_0x000b
            android.view.View r0 = r0.getView()
            super.removeView(r0)
        L_0x000b:
            r5.I5 = r6
            r0 = 0
            r5.V5 = r0
            r5.R5 = r0
            r5.j5 = r0
            r5.T5 = r0
            com.scwang.smart.refresh.layout.constant.a r1 = com.scwang.smart.refresh.layout.constant.a.a
            r5.z5 = r1
            boolean r1 = r5.k5
            if (r1 == 0) goto L_0x0025
            boolean r1 = r5.R4
            if (r1 == 0) goto L_0x0023
            goto L_0x0025
        L_0x0023:
            r1 = r0
            goto L_0x0026
        L_0x0025:
            r1 = 1
        L_0x0026:
            r5.R4 = r1
            if (r7 != 0) goto L_0x002c
            r1 = -1
            goto L_0x002d
        L_0x002c:
            r1 = r7
        L_0x002d:
            r7 = r1
            if (r8 != 0) goto L_0x0032
            r1 = -2
            goto L_0x0033
        L_0x0032:
            r1 = r8
        L_0x0033:
            r8 = r1
            com.scwang.smart.refresh.layout.SmartRefreshLayout$LayoutParams r1 = new com.scwang.smart.refresh.layout.SmartRefreshLayout$LayoutParams
            r1.<init>((int) r7, (int) r8)
            android.view.View r2 = r6.getView()
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            boolean r3 = r2 instanceof com.scwang.smart.refresh.layout.SmartRefreshLayout.LayoutParams
            if (r3 == 0) goto L_0x0048
            r1 = r2
            com.scwang.smart.refresh.layout.SmartRefreshLayout$LayoutParams r1 = (com.scwang.smart.refresh.layout.SmartRefreshLayout.LayoutParams) r1
        L_0x0048:
            com.scwang.smart.refresh.layout.api.a r3 = r5.I5
            com.scwang.smart.refresh.layout.constant.c r3 = r3.getSpinnerStyle()
            boolean r3 = r3.h
            if (r3 == 0) goto L_0x0061
            r0 = r5
            com.scwang.smart.refresh.layout.api.a r3 = r5.I5
            android.view.View r3 = r3.getView()
            int r4 = r0.getChildCount()
            super.addView(r3, r4, r1)
            goto L_0x006a
        L_0x0061:
            com.scwang.smart.refresh.layout.api.a r3 = r5.I5
            android.view.View r3 = r3.getView()
            super.addView(r3, r0, r1)
        L_0x006a:
            int[] r0 = r5.P4
            if (r0 == 0) goto L_0x0075
            com.scwang.smart.refresh.layout.api.a r3 = r5.I5
            if (r3 == 0) goto L_0x0075
            r3.setPrimaryColors(r0)
        L_0x0075:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.G(com.scwang.smart.refresh.layout.api.c, int, int):com.scwang.smart.refresh.layout.api.f");
    }

    @Nullable
    public com.scwang.smart.refresh.layout.api.c getRefreshFooter() {
        com.scwang.smart.refresh.layout.api.a aVar = this.I5;
        if (aVar instanceof com.scwang.smart.refresh.layout.api.c) {
            return (com.scwang.smart.refresh.layout.api.c) aVar;
        }
        return null;
    }

    @Nullable
    public com.scwang.smart.refresh.layout.api.d getRefreshHeader() {
        com.scwang.smart.refresh.layout.api.a aVar = this.H5;
        if (aVar instanceof com.scwang.smart.refresh.layout.api.d) {
            return (com.scwang.smart.refresh.layout.api.d) aVar;
        }
        return null;
    }

    @NonNull
    public com.scwang.smart.refresh.layout.constant.b getState() {
        return this.N5;
    }

    @NonNull
    public ViewGroup getLayout() {
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f E(com.scwang.smart.refresh.layout.listener.g listener) {
        this.n5 = listener;
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f D(com.scwang.smart.refresh.layout.listener.e listener) {
        this.o5 = listener;
        this.R4 = this.R4 || (!this.k5 && listener != null);
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f C(boolean noMoreData) {
        com.scwang.smart.refresh.layout.constant.b bVar = this.N5;
        if (bVar == com.scwang.smart.refresh.layout.constant.b.Refreshing && noMoreData) {
            u();
        } else if (bVar == com.scwang.smart.refresh.layout.constant.b.Loading && noMoreData) {
            p();
        } else if (this.i5 != noMoreData) {
            this.i5 = noMoreData;
            com.scwang.smart.refresh.layout.api.a aVar = this.I5;
            if (aVar instanceof com.scwang.smart.refresh.layout.api.c) {
                if (((com.scwang.smart.refresh.layout.api.c) aVar).b(noMoreData)) {
                    this.j5 = true;
                    if (this.i5 && this.W4 && this.y > 0 && this.I5.getSpinnerStyle() == com.scwang.smart.refresh.layout.constant.c.a && w(this.R4) && x(this.Q4, this.H5)) {
                        this.I5.getView().setTranslationY((float) this.y);
                    }
                } else {
                    this.j5 = false;
                    new RuntimeException("Footer:" + this.I5 + " NoMoreData is not supported.(不支持NoMoreData，请使用[ClassicsFooter]或者[自定义Footer并实现setNoMoreData方法且返回true])").printStackTrace();
                }
            }
        }
        return this;
    }

    public com.scwang.smart.refresh.layout.api.f a() {
        return C(false);
    }

    public com.scwang.smart.refresh.layout.api.f q() {
        return t(true);
    }

    public com.scwang.smart.refresh.layout.api.f m() {
        return o(true);
    }

    public com.scwang.smart.refresh.layout.api.f r(int delayed) {
        return s(delayed, true, Boolean.FALSE);
    }

    public com.scwang.smart.refresh.layout.api.f t(boolean success) {
        if (success) {
            return s(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.P5))), IjkMediaCodecInfo.RANK_SECURE) << 16, true, Boolean.FALSE);
        }
        return s(0, false, (Boolean) null);
    }

    public com.scwang.smart.refresh.layout.api.f s(int delayed, boolean success, Boolean noMoreData) {
        int delay = (delayed << 16) >> 16;
        Runnable runnable = new g(delayed >> 16, noMoreData, success);
        if (delay > 0) {
            this.L5.postDelayed(runnable, (long) delay);
        } else {
            runnable.run();
        }
        return this;
    }

    public class g implements Runnable {
        int c = 0;
        final /* synthetic */ int d;
        final /* synthetic */ Boolean f;
        final /* synthetic */ boolean q;

        g(int i, Boolean bool, boolean z) {
            this.d = i;
            this.f = bool;
            this.q = z;
        }

        public void run() {
            int i = this.c;
            if (i == 0) {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                com.scwang.smart.refresh.layout.constant.b bVar = smartRefreshLayout.N5;
                com.scwang.smart.refresh.layout.constant.b bVar2 = com.scwang.smart.refresh.layout.constant.b.None;
                if (bVar == bVar2 && smartRefreshLayout.O5 == com.scwang.smart.refresh.layout.constant.b.Refreshing) {
                    smartRefreshLayout.O5 = bVar2;
                } else {
                    ValueAnimator valueAnimator = smartRefreshLayout.Z5;
                    if (valueAnimator != null && bVar.isHeader && (bVar.isDragging || bVar == com.scwang.smart.refresh.layout.constant.b.RefreshReleased)) {
                        valueAnimator.setDuration(0);
                        SmartRefreshLayout.this.Z5.cancel();
                        SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                        smartRefreshLayout2.Z5 = null;
                        if (smartRefreshLayout2.M5.b(0) == null) {
                            SmartRefreshLayout.this.z(bVar2);
                        } else {
                            SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.PullDownCanceled);
                        }
                    } else if (!(bVar != com.scwang.smart.refresh.layout.constant.b.Refreshing || smartRefreshLayout.H5 == null || smartRefreshLayout.J5 == null)) {
                        this.c = i + 1;
                        smartRefreshLayout.L5.postDelayed(this, (long) this.d);
                        SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.RefreshFinish);
                        if (this.f == Boolean.FALSE) {
                            SmartRefreshLayout.this.C(false);
                        }
                    }
                }
                if (this.f == Boolean.TRUE) {
                    SmartRefreshLayout.this.C(true);
                    return;
                }
                return;
            }
            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
            int startDelay = smartRefreshLayout3.H5.f(smartRefreshLayout3, this.q);
            SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
            com.scwang.smart.refresh.layout.listener.f fVar = smartRefreshLayout4.p5;
            if (fVar != null) {
                com.scwang.smart.refresh.layout.api.a aVar = smartRefreshLayout4.H5;
                if (aVar instanceof com.scwang.smart.refresh.layout.api.d) {
                    fVar.n((com.scwang.smart.refresh.layout.api.d) aVar, this.q);
                }
            }
            if (startDelay < Integer.MAX_VALUE) {
                SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                if (smartRefreshLayout5.C4 || smartRefreshLayout5.s5) {
                    long time = System.currentTimeMillis();
                    SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                    if (smartRefreshLayout6.C4) {
                        float f2 = smartRefreshLayout6.z4;
                        smartRefreshLayout6.p3 = f2;
                        smartRefreshLayout6.p0 = 0;
                        smartRefreshLayout6.C4 = false;
                        long j = time;
                        boolean unused = SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, j, 0, smartRefreshLayout6.p4, (f2 + ((float) smartRefreshLayout6.y)) - ((float) (smartRefreshLayout6.x * 2)), 0));
                        SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                        boolean unused2 = SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, j, 2, smartRefreshLayout7.p4, smartRefreshLayout7.z4 + ((float) smartRefreshLayout7.y), 0));
                    }
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    if (smartRefreshLayout8.s5) {
                        smartRefreshLayout8.r5 = 0;
                        boolean unused3 = SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(time, time, 1, smartRefreshLayout8.p4, smartRefreshLayout8.z4, 0));
                        SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                        smartRefreshLayout9.s5 = false;
                        smartRefreshLayout9.p0 = 0;
                    }
                }
                SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                int i2 = smartRefreshLayout10.y;
                if (i2 > 0) {
                    ValueAnimator.AnimatorUpdateListener updateListener = null;
                    ValueAnimator valueAnimator2 = smartRefreshLayout10.k(0, startDelay, smartRefreshLayout10.O4, smartRefreshLayout10.p1);
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    if (smartRefreshLayout11.d5) {
                        updateListener = smartRefreshLayout11.J5.e(smartRefreshLayout11.y);
                    }
                    if (valueAnimator2 != null && updateListener != null) {
                        valueAnimator2.addUpdateListener(updateListener);
                    }
                } else if (i2 < 0) {
                    smartRefreshLayout10.k(0, startDelay, smartRefreshLayout10.O4, smartRefreshLayout10.p1);
                } else {
                    smartRefreshLayout10.M5.c(0, false);
                    SmartRefreshLayout.this.M5.f(com.scwang.smart.refresh.layout.constant.b.None);
                }
            }
        }
    }

    public com.scwang.smart.refresh.layout.api.f u() {
        return s(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.P5))), IjkMediaCodecInfo.RANK_SECURE) << 16, true, Boolean.TRUE);
    }

    public com.scwang.smart.refresh.layout.api.f b(int delayed) {
        return n(delayed, true, false);
    }

    public com.scwang.smart.refresh.layout.api.f o(boolean success) {
        return n(success ? Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.P5))), IjkMediaCodecInfo.RANK_SECURE) << 16 : 0, success, false);
    }

    public com.scwang.smart.refresh.layout.api.f n(int delayed, boolean success, boolean noMoreData) {
        int delay = (delayed << 16) >> 16;
        Runnable runnable = new h(delayed >> 16, noMoreData, success);
        if (delay > 0) {
            this.L5.postDelayed(runnable, (long) delay);
        } else {
            runnable.run();
        }
        return this;
    }

    public class h implements Runnable {
        int c = 0;
        final /* synthetic */ int d;
        final /* synthetic */ boolean f;
        final /* synthetic */ boolean q;

        h(int i, boolean z, boolean z2) {
            this.d = i;
            this.f = z;
            this.q = z2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b0, code lost:
            if (r6.J5.i() != false) goto L_0x00b4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r19 = this;
                r0 = r19
                int r1 = r0.c
                r2 = 0
                r4 = 1
                r5 = 0
                if (r1 != 0) goto L_0x007a
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r7 = r6.N5
                com.scwang.smart.refresh.layout.constant.b r8 = com.scwang.smart.refresh.layout.constant.b.None
                if (r7 != r8) goto L_0x001b
                com.scwang.smart.refresh.layout.constant.b r9 = r6.O5
                com.scwang.smart.refresh.layout.constant.b r10 = com.scwang.smart.refresh.layout.constant.b.Loading
                if (r9 != r10) goto L_0x001b
                r6.O5 = r8
                goto L_0x006f
            L_0x001b:
                android.animation.ValueAnimator r9 = r6.Z5
                if (r9 == 0) goto L_0x0050
                boolean r10 = r7.isDragging
                if (r10 != 0) goto L_0x0027
                com.scwang.smart.refresh.layout.constant.b r10 = com.scwang.smart.refresh.layout.constant.b.LoadReleased
                if (r7 != r10) goto L_0x0050
            L_0x0027:
                boolean r10 = r7.isFooter
                if (r10 == 0) goto L_0x0050
                r9.setDuration(r2)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                android.animation.ValueAnimator r1 = r1.Z5
                r1.cancel()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                r2 = 0
                r1.Z5 = r2
                com.scwang.smart.refresh.layout.api.e r1 = r1.M5
                android.animation.ValueAnimator r1 = r1.b(r5)
                if (r1 != 0) goto L_0x0048
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                r1.z(r8)
                goto L_0x006f
            L_0x0048:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r2 = com.scwang.smart.refresh.layout.constant.b.PullUpCanceled
                r1.z(r2)
                goto L_0x006f
            L_0x0050:
                com.scwang.smart.refresh.layout.constant.b r2 = com.scwang.smart.refresh.layout.constant.b.Loading
                if (r7 != r2) goto L_0x006f
                com.scwang.smart.refresh.layout.api.a r2 = r6.I5
                if (r2 == 0) goto L_0x006f
                com.scwang.smart.refresh.layout.api.b r2 = r6.J5
                if (r2 == 0) goto L_0x006f
                int r1 = r1 + r4
                r0.c = r1
                android.os.Handler r1 = r6.L5
                int r2 = r0.d
                long r2 = (long) r2
                r1.postDelayed(r0, r2)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r2 = com.scwang.smart.refresh.layout.constant.b.LoadFinish
                r1.z(r2)
                return
            L_0x006f:
                boolean r1 = r0.f
                if (r1 == 0) goto L_0x0150
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                r1.C(r4)
                goto L_0x0150
            L_0x007a:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r6 = r1.I5
                boolean r7 = r0.q
                int r1 = r6.f(r1, r7)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.listener.f r7 = r6.p5
                if (r7 == 0) goto L_0x0097
                com.scwang.smart.refresh.layout.api.a r6 = r6.I5
                boolean r8 = r6 instanceof com.scwang.smart.refresh.layout.api.c
                if (r8 == 0) goto L_0x0097
                com.scwang.smart.refresh.layout.api.c r6 = (com.scwang.smart.refresh.layout.api.c) r6
                boolean r8 = r0.q
                r7.c(r6, r8)
            L_0x0097:
                r6 = 2147483647(0x7fffffff, float:NaN)
                if (r1 >= r6) goto L_0x0150
                boolean r6 = r0.f
                if (r6 == 0) goto L_0x00b3
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r7 = r6.W4
                if (r7 == 0) goto L_0x00b3
                int r7 = r6.y
                if (r7 >= 0) goto L_0x00b3
                com.scwang.smart.refresh.layout.api.b r6 = r6.J5
                boolean r6 = r6.i()
                if (r6 == 0) goto L_0x00b3
                goto L_0x00b4
            L_0x00b3:
                r4 = r5
            L_0x00b4:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r7 = r6.y
                if (r4 == 0) goto L_0x00c2
                int r6 = r6.y5
                int r6 = -r6
                int r6 = java.lang.Math.max(r7, r6)
                goto L_0x00c3
            L_0x00c2:
                r6 = r5
            L_0x00c3:
                int r7 = r7 - r6
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r8 = r6.C4
                if (r8 != 0) goto L_0x00ce
                boolean r6 = r6.s5
                if (r6 == 0) goto L_0x013a
            L_0x00ce:
                long r16 = java.lang.System.currentTimeMillis()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r8 = r6.C4
                if (r8 == 0) goto L_0x011b
                float r8 = r6.z4
                r6.p3 = r8
                int r9 = r6.y
                int r9 = r9 - r7
                r6.p0 = r9
                r6.C4 = r5
                boolean r9 = r6.V4
                if (r9 == 0) goto L_0x00e9
                r9 = r7
                goto L_0x00ea
            L_0x00e9:
                r9 = r5
            L_0x00ea:
                r15 = r9
                r12 = 0
                float r13 = r6.p4
                float r9 = (float) r15
                float r8 = r8 + r9
                int r9 = r6.x
                int r9 = r9 * 2
                float r9 = (float) r9
                float r14 = r8 + r9
                r18 = 0
                r8 = r16
                r10 = r16
                r2 = r15
                r15 = r18
                android.view.MotionEvent r3 = android.view.MotionEvent.obtain(r8, r10, r12, r13, r14, r15)
                boolean unused = com.scwang.smart.refresh.layout.SmartRefreshLayout.super.dispatchTouchEvent(r3)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                r12 = 2
                float r13 = r3.p4
                float r6 = r3.z4
                float r8 = (float) r2
                float r14 = r6 + r8
                r15 = 0
                r8 = r16
                android.view.MotionEvent r6 = android.view.MotionEvent.obtain(r8, r10, r12, r13, r14, r15)
                boolean unused = com.scwang.smart.refresh.layout.SmartRefreshLayout.super.dispatchTouchEvent(r6)
            L_0x011b:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r3 = r2.s5
                if (r3 == 0) goto L_0x013a
                r2.r5 = r5
                r12 = 1
                float r13 = r2.p4
                float r14 = r2.z4
                r15 = 0
                r8 = r16
                r10 = r16
                android.view.MotionEvent r3 = android.view.MotionEvent.obtain(r8, r10, r12, r13, r14, r15)
                boolean unused = com.scwang.smart.refresh.layout.SmartRefreshLayout.super.dispatchTouchEvent(r3)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                r2.s5 = r5
                r2.p0 = r5
            L_0x013a:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                android.os.Handler r2 = r2.L5
                com.scwang.smart.refresh.layout.SmartRefreshLayout$h$a r3 = new com.scwang.smart.refresh.layout.SmartRefreshLayout$h$a
                r3.<init>(r7)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r5 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r5 = r5.y
                if (r5 >= 0) goto L_0x014b
                long r5 = (long) r1
                goto L_0x014d
            L_0x014b:
                r5 = 0
            L_0x014d:
                r2.postDelayed(r3, r5)
            L_0x0150:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.h.run():void");
        }

        public class a implements Runnable {
            final /* synthetic */ int c;

            a(int i) {
                this.c = i;
            }

            public void run() {
                ValueAnimator.AnimatorUpdateListener updateListener = null;
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (smartRefreshLayout.c5 && this.c < 0 && (updateListener = smartRefreshLayout.J5.e(smartRefreshLayout.y)) != null) {
                    updateListener.onAnimationUpdate(ValueAnimator.ofInt(new int[]{0, 0}));
                }
                ValueAnimator animator = null;
                AnimatorListenerAdapter listenerAdapter = new C0202a();
                h hVar = h.this;
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                int i = smartRefreshLayout2.y;
                if (i > 0) {
                    animator = smartRefreshLayout2.M5.b(0);
                } else if (updateListener != null || i == 0) {
                    ValueAnimator valueAnimator = smartRefreshLayout2.Z5;
                    if (valueAnimator != null) {
                        valueAnimator.setDuration(0);
                        SmartRefreshLayout.this.Z5.cancel();
                        SmartRefreshLayout.this.Z5 = null;
                    }
                    SmartRefreshLayout.this.M5.c(0, false);
                    SmartRefreshLayout.this.M5.f(com.scwang.smart.refresh.layout.constant.b.None);
                } else if (!hVar.f || !smartRefreshLayout2.W4) {
                    animator = smartRefreshLayout2.M5.b(0);
                } else {
                    int i2 = smartRefreshLayout2.y5;
                    if (i >= (-i2)) {
                        smartRefreshLayout2.z(com.scwang.smart.refresh.layout.constant.b.None);
                    } else {
                        animator = smartRefreshLayout2.M5.b(-i2);
                    }
                }
                if (animator != null) {
                    animator.addListener(listenerAdapter);
                } else {
                    listenerAdapter.onAnimationEnd((Animator) null);
                }
            }

            /* renamed from: com.scwang.smart.refresh.layout.SmartRefreshLayout$h$a$a  reason: collision with other inner class name */
            public class C0202a extends AnimatorListenerAdapter {
                C0202a() {
                }

                public void onAnimationEnd(Animator animation) {
                    if (animation == null || animation.getDuration() != 0) {
                        h hVar = h.this;
                        SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                        smartRefreshLayout.V5 = false;
                        if (hVar.f) {
                            smartRefreshLayout.C(true);
                        }
                        SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                        if (smartRefreshLayout2.N5 == com.scwang.smart.refresh.layout.constant.b.LoadFinish) {
                            smartRefreshLayout2.z(com.scwang.smart.refresh.layout.constant.b.None);
                        }
                    }
                }
            }
        }
    }

    public com.scwang.smart.refresh.layout.api.f p() {
        return n(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.P5))), IjkMediaCodecInfo.RANK_SECURE) << 16, true, true);
    }

    public static void setDefaultRefreshHeaderCreator(@NonNull com.scwang.smart.refresh.layout.listener.c creator) {
        d = creator;
    }

    public static void setDefaultRefreshFooterCreator(@NonNull com.scwang.smart.refresh.layout.listener.b creator) {
        c = creator;
    }

    public static void setDefaultRefreshInitializer(@NonNull com.scwang.smart.refresh.layout.listener.d initializer) {
        f = initializer;
    }

    public class k implements com.scwang.smart.refresh.layout.api.e {
        public k() {
        }

        @NonNull
        public com.scwang.smart.refresh.layout.api.f d() {
            return SmartRefreshLayout.this;
        }

        public com.scwang.smart.refresh.layout.api.e f(@NonNull com.scwang.smart.refresh.layout.constant.b state) {
            switch (a.a[state.ordinal()]) {
                case 1:
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    com.scwang.smart.refresh.layout.constant.b bVar = smartRefreshLayout.N5;
                    com.scwang.smart.refresh.layout.constant.b bVar2 = com.scwang.smart.refresh.layout.constant.b.None;
                    if (bVar != bVar2 && smartRefreshLayout.y == 0) {
                        smartRefreshLayout.z(bVar2);
                        return null;
                    } else if (smartRefreshLayout.y == 0) {
                        return null;
                    } else {
                        b(0);
                        return null;
                    }
                case 2:
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    if (smartRefreshLayout2.N5.isOpening || !smartRefreshLayout2.w(smartRefreshLayout2.Q4)) {
                        SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh);
                        return null;
                    }
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh);
                    return null;
                case 3:
                    SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    if (smartRefreshLayout3.w(smartRefreshLayout3.R4)) {
                        SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                        com.scwang.smart.refresh.layout.constant.b bVar3 = smartRefreshLayout4.N5;
                        if (!bVar3.isOpening && !bVar3.isFinishing && (!smartRefreshLayout4.i5 || !smartRefreshLayout4.W4 || !smartRefreshLayout4.j5)) {
                            smartRefreshLayout4.z(com.scwang.smart.refresh.layout.constant.b.PullUpToLoad);
                            return null;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.PullUpToLoad);
                    return null;
                case 4:
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (smartRefreshLayout5.N5.isOpening || !smartRefreshLayout5.w(smartRefreshLayout5.Q4)) {
                        SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.PullDownCanceled);
                        return null;
                    }
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.PullDownCanceled);
                    f(com.scwang.smart.refresh.layout.constant.b.None);
                    return null;
                case 5:
                    SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                    if (smartRefreshLayout6.w(smartRefreshLayout6.R4)) {
                        SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                        if (!smartRefreshLayout7.N5.isOpening && (!smartRefreshLayout7.i5 || !smartRefreshLayout7.W4 || !smartRefreshLayout7.j5)) {
                            smartRefreshLayout7.z(com.scwang.smart.refresh.layout.constant.b.PullUpCanceled);
                            f(com.scwang.smart.refresh.layout.constant.b.None);
                            return null;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.PullUpCanceled);
                    return null;
                case 6:
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    if (smartRefreshLayout8.N5.isOpening || !smartRefreshLayout8.w(smartRefreshLayout8.Q4)) {
                        SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.ReleaseToRefresh);
                        return null;
                    }
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.ReleaseToRefresh);
                    return null;
                case 7:
                    SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                    if (smartRefreshLayout9.w(smartRefreshLayout9.R4)) {
                        SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                        com.scwang.smart.refresh.layout.constant.b bVar4 = smartRefreshLayout10.N5;
                        if (!bVar4.isOpening && !bVar4.isFinishing && (!smartRefreshLayout10.i5 || !smartRefreshLayout10.W4 || !smartRefreshLayout10.j5)) {
                            smartRefreshLayout10.z(com.scwang.smart.refresh.layout.constant.b.ReleaseToLoad);
                            return null;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.ReleaseToLoad);
                    return null;
                case 8:
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    if (smartRefreshLayout11.N5.isOpening || !smartRefreshLayout11.w(smartRefreshLayout11.Q4)) {
                        SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.ReleaseToTwoLevel);
                        return null;
                    }
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.ReleaseToTwoLevel);
                    return null;
                case 9:
                    SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                    if (smartRefreshLayout12.N5.isOpening || !smartRefreshLayout12.w(smartRefreshLayout12.Q4)) {
                        SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.RefreshReleased);
                        return null;
                    }
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.RefreshReleased);
                    return null;
                case 10:
                    SmartRefreshLayout smartRefreshLayout13 = SmartRefreshLayout.this;
                    if (smartRefreshLayout13.N5.isOpening || !smartRefreshLayout13.w(smartRefreshLayout13.R4)) {
                        SmartRefreshLayout.this.setViceState(com.scwang.smart.refresh.layout.constant.b.LoadReleased);
                        return null;
                    }
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.LoadReleased);
                    return null;
                case 11:
                    SmartRefreshLayout.this.setStateRefreshing(true);
                    return null;
                case 12:
                    SmartRefreshLayout.this.setStateLoading(true);
                    return null;
                default:
                    SmartRefreshLayout.this.z(state);
                    return null;
            }
        }

        public com.scwang.smart.refresh.layout.api.e a() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.N5 == com.scwang.smart.refresh.layout.constant.b.TwoLevel) {
                smartRefreshLayout.M5.f(com.scwang.smart.refresh.layout.constant.b.TwoLevelFinish);
                if (SmartRefreshLayout.this.y == 0) {
                    c(0, false);
                    SmartRefreshLayout.this.z(com.scwang.smart.refresh.layout.constant.b.None);
                } else {
                    b(0).setDuration((long) SmartRefreshLayout.this.a1);
                }
            }
            return this;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:70:0x0103, code lost:
            r2 = r2.H5;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.scwang.smart.refresh.layout.api.e c(int r19, boolean r20) {
            /*
                r18 = this;
                r0 = r18
                r1 = r19
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.y
                if (r3 != r1) goto L_0x0021
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                if (r2 == 0) goto L_0x0014
                boolean r2 = r2.m()
                if (r2 != 0) goto L_0x0021
            L_0x0014:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                if (r2 == 0) goto L_0x0020
                boolean r2 = r2.m()
                if (r2 != 0) goto L_0x0021
            L_0x0020:
                return r0
            L_0x0021:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r9 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r10 = r2.y
                r2.y = r1
                r11 = 1092616192(0x41200000, float:10.0)
                if (r20 == 0) goto L_0x0087
                com.scwang.smart.refresh.layout.constant.b r3 = r2.O5
                boolean r4 = r3.isDragging
                if (r4 != 0) goto L_0x0037
                boolean r3 = r3.isOpening
                if (r3 == 0) goto L_0x0087
            L_0x0037:
                float r3 = (float) r1
                float r4 = r2.E5
                int r5 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
                if (r5 >= 0) goto L_0x0042
                int r5 = r2.w5
                float r5 = (float) r5
                float r4 = r4 * r5
            L_0x0042:
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 <= 0) goto L_0x0054
                com.scwang.smart.refresh.layout.constant.b r3 = r2.N5
                com.scwang.smart.refresh.layout.constant.b r4 = com.scwang.smart.refresh.layout.constant.b.ReleaseToTwoLevel
                if (r3 == r4) goto L_0x0087
                com.scwang.smart.refresh.layout.api.e r2 = r2.M5
                com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.ReleaseToRefresh
                r2.f(r3)
                goto L_0x0087
            L_0x0054:
                int r3 = -r1
                float r3 = (float) r3
                float r4 = r2.F5
                int r5 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
                if (r5 >= 0) goto L_0x0060
                int r5 = r2.y5
                float r5 = (float) r5
                float r4 = r4 * r5
            L_0x0060:
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 <= 0) goto L_0x0070
                boolean r3 = r2.i5
                if (r3 != 0) goto L_0x0070
                com.scwang.smart.refresh.layout.api.e r2 = r2.M5
                com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.ReleaseToLoad
                r2.f(r3)
                goto L_0x0087
            L_0x0070:
                if (r1 >= 0) goto L_0x007e
                boolean r3 = r2.i5
                if (r3 != 0) goto L_0x007e
                com.scwang.smart.refresh.layout.api.e r2 = r2.M5
                com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.PullUpToLoad
                r2.f(r3)
                goto L_0x0087
            L_0x007e:
                if (r1 <= 0) goto L_0x0087
                com.scwang.smart.refresh.layout.api.e r2 = r2.M5
                com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh
                r2.f(r3)
            L_0x0087:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.b r3 = r2.J5
                r13 = 0
                if (r3 == 0) goto L_0x0150
                r3 = 0
                r4 = 0
                if (r1 < 0) goto L_0x00a4
                boolean r5 = r2.U4
                com.scwang.smart.refresh.layout.api.a r6 = r2.H5
                boolean r2 = r2.x(r5, r6)
                if (r2 == 0) goto L_0x00a0
                r4 = 1
                r3 = r19
                goto L_0x00a4
            L_0x00a0:
                if (r10 >= 0) goto L_0x00a4
                r4 = 1
                r3 = 0
            L_0x00a4:
                if (r1 > 0) goto L_0x00ba
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r5 = r2.V4
                com.scwang.smart.refresh.layout.api.a r6 = r2.I5
                boolean r2 = r2.x(r5, r6)
                if (r2 == 0) goto L_0x00b6
                r4 = 1
                r3 = r19
                goto L_0x00ba
            L_0x00b6:
                if (r10 <= 0) goto L_0x00ba
                r4 = 1
                r3 = 0
            L_0x00ba:
                if (r4 == 0) goto L_0x0150
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.b r5 = r2.J5
                int r6 = r2.H4
                int r2 = r2.I4
                r5.h(r3, r6, r2)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r5 = r2.i5
                if (r5 == 0) goto L_0x00fd
                boolean r5 = r2.j5
                if (r5 == 0) goto L_0x00fd
                boolean r5 = r2.W4
                if (r5 == 0) goto L_0x00fd
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                boolean r5 = r2 instanceof com.scwang.smart.refresh.layout.api.c
                if (r5 == 0) goto L_0x00fd
                com.scwang.smart.refresh.layout.constant.c r2 = r2.getSpinnerStyle()
                com.scwang.smart.refresh.layout.constant.c r5 = com.scwang.smart.refresh.layout.constant.c.a
                if (r2 != r5) goto L_0x00fd
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r5 = r2.R4
                boolean r2 = r2.w(r5)
                if (r2 == 0) goto L_0x00fd
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                android.view.View r2 = r2.getView()
                int r5 = java.lang.Math.max(r13, r3)
                float r5 = (float) r5
                r2.setTranslationY(r5)
            L_0x00fd:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r5 = r2.S4
                if (r5 == 0) goto L_0x0111
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                if (r2 == 0) goto L_0x0111
                com.scwang.smart.refresh.layout.constant.c r2 = r2.getSpinnerStyle()
                com.scwang.smart.refresh.layout.constant.c r5 = com.scwang.smart.refresh.layout.constant.c.c
                if (r2 != r5) goto L_0x0111
                r2 = 1
                goto L_0x0112
            L_0x0111:
                r2 = r13
            L_0x0112:
                if (r2 != 0) goto L_0x011d
                com.scwang.smart.refresh.layout.SmartRefreshLayout r5 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r5 = r5.Q5
                if (r5 == 0) goto L_0x011b
                goto L_0x011d
            L_0x011b:
                r5 = r13
                goto L_0x011e
            L_0x011d:
                r5 = 1
            L_0x011e:
                r2 = r5
                com.scwang.smart.refresh.layout.SmartRefreshLayout r5 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                boolean r6 = r5.T4
                if (r6 == 0) goto L_0x0133
                com.scwang.smart.refresh.layout.api.a r5 = r5.I5
                if (r5 == 0) goto L_0x0133
                com.scwang.smart.refresh.layout.constant.c r5 = r5.getSpinnerStyle()
                com.scwang.smart.refresh.layout.constant.c r6 = com.scwang.smart.refresh.layout.constant.c.c
                if (r5 != r6) goto L_0x0133
                r5 = 1
                goto L_0x0134
            L_0x0133:
                r5 = r13
            L_0x0134:
                if (r5 != 0) goto L_0x013f
                com.scwang.smart.refresh.layout.SmartRefreshLayout r6 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r6 = r6.R5
                if (r6 == 0) goto L_0x013d
                goto L_0x013f
            L_0x013d:
                r6 = r13
                goto L_0x0140
            L_0x013f:
                r6 = 1
            L_0x0140:
                r5 = r6
                if (r2 == 0) goto L_0x0147
                if (r3 >= 0) goto L_0x014d
                if (r10 > 0) goto L_0x014d
            L_0x0147:
                if (r5 == 0) goto L_0x0150
                if (r3 <= 0) goto L_0x014d
                if (r10 >= 0) goto L_0x0150
            L_0x014d:
                r9.invalidate()
            L_0x0150:
                r14 = 1065353216(0x3f800000, float:1.0)
                r15 = 1073741824(0x40000000, float:2.0)
                if (r1 >= 0) goto L_0x0158
                if (r10 <= 0) goto L_0x027e
            L_0x0158:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                if (r2 == 0) goto L_0x027e
                int r8 = java.lang.Math.max(r1, r13)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r7 = r2.w5
                float r3 = r2.C5
                int r4 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
                if (r4 >= 0) goto L_0x0170
                int r4 = r2.w5
                float r4 = (float) r4
                float r3 = r3 * r4
            L_0x0170:
                int r6 = (int) r3
                float r3 = (float) r8
                float r3 = r3 * r14
                float r4 = r2.E5
                int r5 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
                if (r5 >= 0) goto L_0x017d
                int r5 = r2.w5
                float r5 = (float) r5
                float r4 = r4 * r5
            L_0x017d:
                float r16 = r3 / r4
                boolean r3 = r2.Q4
                boolean r2 = r2.w(r3)
                if (r2 != 0) goto L_0x0196
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r2 = r2.N5
                com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.RefreshFinish
                if (r2 != r3) goto L_0x0192
                if (r20 != 0) goto L_0x0192
                goto L_0x0196
            L_0x0192:
                r11 = r6
                r12 = r7
                goto L_0x025c
            L_0x0196:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.y
                if (r10 == r3) goto L_0x0235
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                com.scwang.smart.refresh.layout.constant.c r2 = r2.getSpinnerStyle()
                com.scwang.smart.refresh.layout.constant.c r3 = com.scwang.smart.refresh.layout.constant.c.a
                if (r2 != r3) goto L_0x01ce
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                android.view.View r2 = r2.getView()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r3.y
                float r3 = (float) r3
                r2.setTranslationY(r3)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.Q5
                if (r3 == 0) goto L_0x0224
                android.graphics.Paint r3 = r2.K5
                if (r3 == 0) goto L_0x0224
                boolean r3 = r2.U4
                com.scwang.smart.refresh.layout.api.a r4 = r2.H5
                boolean r2 = r2.x(r3, r4)
                if (r2 != 0) goto L_0x0224
                r9.invalidate()
                goto L_0x0224
            L_0x01ce:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                com.scwang.smart.refresh.layout.constant.c r2 = r2.getSpinnerStyle()
                boolean r2 = r2.i
                if (r2 == 0) goto L_0x0224
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                android.view.View r2 = r2.getView()
                android.view.ViewGroup$LayoutParams r3 = r2.getLayoutParams()
                boolean r4 = r3 instanceof android.view.ViewGroup.MarginLayoutParams
                if (r4 == 0) goto L_0x01ee
                r4 = r3
                android.view.ViewGroup$MarginLayoutParams r4 = (android.view.ViewGroup.MarginLayoutParams) r4
                goto L_0x01f0
            L_0x01ee:
                android.view.ViewGroup$MarginLayoutParams r4 = com.scwang.smart.refresh.layout.SmartRefreshLayout.q
            L_0x01f0:
                int r5 = r2.getMeasuredWidth()
                int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r15)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r12 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r12 = r12.y
                int r14 = r4.bottomMargin
                int r12 = r12 - r14
                int r14 = r4.topMargin
                int r12 = r12 - r14
                int r12 = java.lang.Math.max(r12, r13)
                int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r15)
                r2.measure(r5, r12)
                int r12 = r4.leftMargin
                int r14 = r4.topMargin
                com.scwang.smart.refresh.layout.SmartRefreshLayout r15 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r15 = r15.A5
                int r14 = r14 + r15
                int r15 = r2.getMeasuredWidth()
                int r15 = r15 + r12
                int r17 = r2.getMeasuredHeight()
                int r11 = r14 + r17
                r2.layout(r12, r14, r15, r11)
            L_0x0224:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                r3 = r20
                r4 = r16
                r5 = r8
                r11 = r6
                r6 = r7
                r12 = r7
                r7 = r11
                r2.q(r3, r4, r5, r6, r7)
                goto L_0x0237
            L_0x0235:
                r11 = r6
                r12 = r7
            L_0x0237:
                if (r20 == 0) goto L_0x025c
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                boolean r2 = r2.m()
                if (r2 == 0) goto L_0x025c
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                float r2 = r2.p4
                int r2 = (int) r2
                int r3 = r9.getWidth()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r4 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                float r5 = r4.p4
                if (r3 != 0) goto L_0x0254
                r6 = 1
                goto L_0x0255
            L_0x0254:
                r6 = r3
            L_0x0255:
                float r6 = (float) r6
                float r5 = r5 / r6
                com.scwang.smart.refresh.layout.api.a r4 = r4.H5
                r4.k(r5, r2, r3)
            L_0x025c:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.y
                if (r10 == r3) goto L_0x027d
                com.scwang.smart.refresh.layout.listener.f r3 = r2.p5
                if (r3 == 0) goto L_0x027d
                com.scwang.smart.refresh.layout.api.a r2 = r2.H5
                boolean r4 = r2 instanceof com.scwang.smart.refresh.layout.api.d
                if (r4 == 0) goto L_0x027d
                r4 = r2
                com.scwang.smart.refresh.layout.api.d r4 = (com.scwang.smart.refresh.layout.api.d) r4
                r2 = r3
                r3 = r4
                r4 = r20
                r5 = r16
                r6 = r8
                r7 = r12
                r14 = r8
                r8 = r11
                r2.l(r3, r4, r5, r6, r7, r8)
                goto L_0x027e
            L_0x027d:
                r14 = r8
            L_0x027e:
                if (r1 <= 0) goto L_0x0282
                if (r10 >= 0) goto L_0x03a8
            L_0x0282:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                if (r2 == 0) goto L_0x03a8
                int r2 = java.lang.Math.min(r1, r13)
                int r11 = -r2
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r12 = r2.y5
                float r3 = r2.D5
                r4 = 1092616192(0x41200000, float:10.0)
                int r5 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r5 >= 0) goto L_0x029d
                int r4 = r2.y5
                float r4 = (float) r4
                float r3 = r3 * r4
            L_0x029d:
                int r14 = (int) r3
                float r3 = (float) r11
                r4 = 1065353216(0x3f800000, float:1.0)
                float r3 = r3 * r4
                float r4 = r2.F5
                r5 = 1092616192(0x41200000, float:10.0)
                int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r5 >= 0) goto L_0x02ae
                int r5 = r2.y5
                float r5 = (float) r5
                float r4 = r4 * r5
            L_0x02ae:
                float r15 = r3 / r4
                boolean r3 = r2.R4
                boolean r2 = r2.w(r3)
                if (r2 != 0) goto L_0x02c2
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.b r2 = r2.N5
                com.scwang.smart.refresh.layout.constant.b r3 = com.scwang.smart.refresh.layout.constant.b.LoadFinish
                if (r2 != r3) goto L_0x038a
                if (r20 != 0) goto L_0x038a
            L_0x02c2:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.y
                if (r10 == r3) goto L_0x0365
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                com.scwang.smart.refresh.layout.constant.c r2 = r2.getSpinnerStyle()
                com.scwang.smart.refresh.layout.constant.c r3 = com.scwang.smart.refresh.layout.constant.c.a
                if (r2 != r3) goto L_0x02fa
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                android.view.View r2 = r2.getView()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r3.y
                float r3 = (float) r3
                r2.setTranslationY(r3)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.R5
                if (r3 == 0) goto L_0x0358
                android.graphics.Paint r3 = r2.K5
                if (r3 == 0) goto L_0x0358
                boolean r3 = r2.V4
                com.scwang.smart.refresh.layout.api.a r4 = r2.I5
                boolean r2 = r2.x(r3, r4)
                if (r2 != 0) goto L_0x0358
                r9.invalidate()
                goto L_0x0358
            L_0x02fa:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                com.scwang.smart.refresh.layout.constant.c r2 = r2.getSpinnerStyle()
                boolean r2 = r2.i
                if (r2 == 0) goto L_0x0358
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                android.view.View r2 = r2.getView()
                android.view.ViewGroup$LayoutParams r3 = r2.getLayoutParams()
                boolean r4 = r3 instanceof android.view.ViewGroup.MarginLayoutParams
                if (r4 == 0) goto L_0x031a
                r4 = r3
                android.view.ViewGroup$MarginLayoutParams r4 = (android.view.ViewGroup.MarginLayoutParams) r4
                goto L_0x031c
            L_0x031a:
                android.view.ViewGroup$MarginLayoutParams r4 = com.scwang.smart.refresh.layout.SmartRefreshLayout.q
            L_0x031c:
                int r5 = r2.getMeasuredWidth()
                r6 = 1073741824(0x40000000, float:2.0)
                int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r6)
                com.scwang.smart.refresh.layout.SmartRefreshLayout r7 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r7 = r7.y
                int r7 = -r7
                int r8 = r4.bottomMargin
                int r7 = r7 - r8
                int r8 = r4.topMargin
                int r7 = r7 - r8
                int r7 = java.lang.Math.max(r7, r13)
                int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r6)
                r2.measure(r5, r6)
                int r6 = r4.leftMargin
                int r7 = r4.topMargin
                int r8 = r9.getMeasuredHeight()
                int r7 = r7 + r8
                com.scwang.smart.refresh.layout.SmartRefreshLayout r8 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r8 = r8.B5
                int r7 = r7 - r8
                int r8 = r2.getMeasuredHeight()
                int r8 = r7 - r8
                int r13 = r2.getMeasuredWidth()
                int r13 = r13 + r6
                r2.layout(r6, r8, r13, r7)
            L_0x0358:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                r3 = r20
                r4 = r15
                r5 = r11
                r6 = r12
                r7 = r14
                r2.q(r3, r4, r5, r6, r7)
            L_0x0365:
                if (r20 == 0) goto L_0x038a
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                boolean r2 = r2.m()
                if (r2 == 0) goto L_0x038a
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                float r2 = r2.p4
                int r2 = (int) r2
                int r3 = r9.getWidth()
                com.scwang.smart.refresh.layout.SmartRefreshLayout r4 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                float r5 = r4.p4
                if (r3 != 0) goto L_0x0382
                r6 = 1
                goto L_0x0383
            L_0x0382:
                r6 = r3
            L_0x0383:
                float r6 = (float) r6
                float r5 = r5 / r6
                com.scwang.smart.refresh.layout.api.a r4 = r4.I5
                r4.k(r5, r2, r3)
            L_0x038a:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r2 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r3 = r2.y
                if (r10 == r3) goto L_0x03a8
                com.scwang.smart.refresh.layout.listener.f r3 = r2.p5
                if (r3 == 0) goto L_0x03a8
                com.scwang.smart.refresh.layout.api.a r2 = r2.I5
                boolean r4 = r2 instanceof com.scwang.smart.refresh.layout.api.c
                if (r4 == 0) goto L_0x03a8
                r4 = r2
                com.scwang.smart.refresh.layout.api.c r4 = (com.scwang.smart.refresh.layout.api.c) r4
                r2 = r3
                r3 = r4
                r4 = r20
                r5 = r15
                r6 = r11
                r7 = r12
                r8 = r14
                r2.o(r3, r4, r5, r6, r7, r8)
            L_0x03a8:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.k.c(int, boolean):com.scwang.smart.refresh.layout.api.e");
        }

        public ValueAnimator b(int endSpinner) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            return smartRefreshLayout.k(endSpinner, 0, smartRefreshLayout.O4, smartRefreshLayout.p1);
        }

        public com.scwang.smart.refresh.layout.api.e e(@NonNull com.scwang.smart.refresh.layout.api.a internal, int backgroundColor) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.K5 == null && backgroundColor != 0) {
                smartRefreshLayout.K5 = new Paint();
            }
            if (internal.equals(SmartRefreshLayout.this.H5)) {
                SmartRefreshLayout.this.Q5 = backgroundColor;
            } else if (internal.equals(SmartRefreshLayout.this.I5)) {
                SmartRefreshLayout.this.R5 = backgroundColor;
            }
            return this;
        }
    }

    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[com.scwang.smart.refresh.layout.constant.b.values().length];
            a = iArr;
            try {
                iArr[com.scwang.smart.refresh.layout.constant.b.None.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.PullUpToLoad.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.PullDownCanceled.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.PullUpCanceled.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.ReleaseToRefresh.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.ReleaseToLoad.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.ReleaseToTwoLevel.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.RefreshReleased.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.LoadReleased.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.Refreshing.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[com.scwang.smart.refresh.layout.constant.b.Loading.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }
}
