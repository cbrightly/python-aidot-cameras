package com.bumptech.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.request.b;
import com.bumptech.glide.request.c;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.g;
import com.bumptech.glide.request.target.j;
import com.bumptech.glide.request.target.k;
import com.bumptech.glide.util.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: RequestBuilder */
public class h<TranscodeType> extends com.bumptech.glide.request.a<h<TranscodeType>> implements Cloneable {
    protected static final f L4 = ((f) ((f) ((f) new f().f(i.c)).e0(g.LOW)).m0(true));
    private final Context M4;
    private final i N4;
    private final Class<TranscodeType> O4;
    private final b P4;
    private final d Q4;
    @NonNull
    private j<?, ? super TranscodeType> R4;
    @Nullable
    private Object S4;
    @Nullable
    private List<e<TranscodeType>> T4;
    @Nullable
    private h<TranscodeType> U4;
    @Nullable
    private h<TranscodeType> V4;
    @Nullable
    private Float W4;
    private boolean X4 = true;
    private boolean Y4;
    private boolean Z4;

    @SuppressLint({"CheckResult"})
    protected h(@NonNull b glide, i requestManager, Class<TranscodeType> transcodeClass, Context context) {
        this.P4 = glide;
        this.N4 = requestManager;
        this.O4 = transcodeClass;
        this.M4 = context;
        this.R4 = requestManager.o(transcodeClass);
        this.Q4 = glide.i();
        C0(requestManager.m());
        a(requestManager.n());
    }

    @SuppressLint({"CheckResult"})
    private void C0(List<e<Object>> requestListeners) {
        Iterator<e<Object>> it = requestListeners.iterator();
        while (it.hasNext()) {
            v0((e) it.next());
        }
    }

    @CheckResult
    @NonNull
    /* renamed from: w0 */
    public h<TranscodeType> a(@NonNull com.bumptech.glide.request.a<?> requestOptions) {
        com.bumptech.glide.util.i.d(requestOptions);
        return (h) super.a(requestOptions);
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> J0(@Nullable e<TranscodeType> requestListener) {
        if (G()) {
            return d().J0(requestListener);
        }
        this.T4 = null;
        return v0(requestListener);
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> v0(@Nullable e<TranscodeType> requestListener) {
        if (G()) {
            return d().v0(requestListener);
        }
        if (requestListener != null) {
            if (this.T4 == null) {
                this.T4 = new ArrayList();
            }
            this.T4.add(requestListener);
        }
        return (h) i0();
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> L0(@Nullable Object model) {
        return N0(model);
    }

    @NonNull
    private h<TranscodeType> N0(@Nullable Object model) {
        if (G()) {
            return d().N0(model);
        }
        this.S4 = model;
        this.Y4 = true;
        return (h) i0();
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> M0(@Nullable String string) {
        return N0(string);
    }

    @CheckResult
    @NonNull
    public h<TranscodeType> K0(@RawRes @DrawableRes @Nullable Integer resourceId) {
        return N0(resourceId).a(f.y0(com.bumptech.glide.signature.a.a(this.M4)));
    }

    @CheckResult
    /* renamed from: A0 */
    public h<TranscodeType> d() {
        RequestBuilder<TranscodeType> result = (h) super.clone();
        result.R4 = result.R4.clone();
        if (result.T4 != null) {
            result.T4 = new ArrayList(result.T4);
        }
        h<TranscodeType> hVar = result.U4;
        if (hVar != null) {
            result.U4 = hVar.d();
        }
        h<TranscodeType> hVar2 = result.V4;
        if (hVar2 != null) {
            result.V4 = hVar2.d();
        }
        return result;
    }

    @NonNull
    public <Y extends j<TranscodeType>> Y D0(@NonNull Y target) {
        return G0(target, (e) null, d.b());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <Y extends j<TranscodeType>> Y G0(@NonNull Y target, @Nullable e<TranscodeType> targetListener, Executor callbackExecutor) {
        return E0(target, targetListener, this, callbackExecutor);
    }

    private <Y extends j<TranscodeType>> Y E0(@NonNull Y target, @Nullable e<TranscodeType> targetListener, com.bumptech.glide.request.a<?> options, Executor callbackExecutor) {
        com.bumptech.glide.util.i.d(target);
        if (this.Y4) {
            c request = x0(target, targetListener, options, callbackExecutor);
            c previous = target.getRequest();
            if (!request.g(previous) || I0(options, previous)) {
                this.N4.l(target);
                target.e(request);
                this.N4.w(target, request);
                return target;
            }
            if (!((c) com.bumptech.glide.util.i.d(previous)).isRunning()) {
                previous.h();
            }
            return target;
        }
        throw new IllegalArgumentException("You must call #load() before calling #into()");
    }

    private boolean I0(com.bumptech.glide.request.a<?> options, c previous) {
        return !options.H() && previous.isComplete();
    }

    @NonNull
    public k<ImageView, TranscodeType> H0(@NonNull ImageView view) {
        com.bumptech.glide.util.j.a();
        com.bumptech.glide.util.i.d(view);
        com.bumptech.glide.request.a aVar = this;
        if (!aVar.Q() && aVar.N() && view.getScaleType() != null) {
            switch (a.a[view.getScaleType().ordinal()]) {
                case 1:
                    aVar = aVar.clone().U();
                    break;
                case 2:
                    aVar = aVar.clone().V();
                    break;
                case 3:
                case 4:
                case 5:
                    aVar = aVar.clone().X();
                    break;
                case 6:
                    aVar = aVar.clone().V();
                    break;
            }
        }
        return (k) E0(this.Q4.a(view, this.O4), (e) null, aVar, d.b());
    }

    @NonNull
    public j<TranscodeType> R0(int width, int height) {
        return D0(g.i(this.N4, width, height));
    }

    @NonNull
    public j<TranscodeType> Q0() {
        return R0(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    /* compiled from: RequestBuilder */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[g.values().length];
            b = iArr;
            try {
                iArr[g.LOW.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[g.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[g.HIGH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[g.IMMEDIATE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[ImageView.ScaleType.values().length];
            a = iArr2;
            try {
                iArr2[ImageView.ScaleType.CENTER_CROP.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[ImageView.ScaleType.FIT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[ImageView.ScaleType.FIT_START.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[ImageView.ScaleType.FIT_END.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[ImageView.ScaleType.FIT_XY.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[ImageView.ScaleType.CENTER.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[ImageView.ScaleType.MATRIX.ordinal()] = 8;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    @NonNull
    private g B0(@NonNull g current) {
        switch (a.b[current.ordinal()]) {
            case 1:
                return g.NORMAL;
            case 2:
                return g.HIGH;
            case 3:
            case 4:
                return g.IMMEDIATE;
            default:
                throw new IllegalArgumentException("unknown priority: " + w());
        }
    }

    private c x0(j<TranscodeType> target, @Nullable e<TranscodeType> targetListener, com.bumptech.glide.request.a<?> requestOptions, Executor callbackExecutor) {
        return y0(new Object(), target, targetListener, (com.bumptech.glide.request.d) null, this.R4, requestOptions.w(), requestOptions.t(), requestOptions.s(), requestOptions, callbackExecutor);
    }

    private c y0(Object requestLock, j<TranscodeType> target, @Nullable e<TranscodeType> targetListener, @Nullable com.bumptech.glide.request.d parentCoordinator, j<?, ? super TranscodeType> transitionOptions, g priority, int overrideWidth, int overrideHeight, com.bumptech.glide.request.a<?> requestOptions, Executor callbackExecutor) {
        com.bumptech.glide.request.d parentCoordinator2;
        com.bumptech.glide.request.d errorRequestCoordinator;
        if (this.V4 != null) {
            com.bumptech.glide.request.d errorRequestCoordinator2 = new b(requestLock, parentCoordinator);
            errorRequestCoordinator = errorRequestCoordinator2;
            parentCoordinator2 = errorRequestCoordinator2;
        } else {
            Object obj = requestLock;
            errorRequestCoordinator = null;
            parentCoordinator2 = parentCoordinator;
        }
        c mainRequest = z0(requestLock, target, targetListener, parentCoordinator2, transitionOptions, priority, overrideWidth, overrideHeight, requestOptions, callbackExecutor);
        if (errorRequestCoordinator == null) {
            return mainRequest;
        }
        int errorOverrideWidth = this.V4.t();
        int errorOverrideHeight = this.V4.s();
        if (com.bumptech.glide.util.j.t(overrideWidth, overrideHeight) && !this.V4.R()) {
            errorOverrideWidth = requestOptions.t();
            errorOverrideHeight = requestOptions.s();
        }
        h<TranscodeType> hVar = this.V4;
        b errorRequestCoordinator3 = errorRequestCoordinator;
        errorRequestCoordinator3.n(mainRequest, hVar.y0(requestLock, target, targetListener, errorRequestCoordinator3, hVar.R4, hVar.w(), errorOverrideWidth, errorOverrideHeight, this.V4, callbackExecutor));
        return errorRequestCoordinator3;
    }

    /* JADX WARNING: type inference failed for: r35v0, types: [com.bumptech.glide.request.a, com.bumptech.glide.request.a<?>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.bumptech.glide.request.c z0(java.lang.Object r27, com.bumptech.glide.request.target.j<TranscodeType> r28, com.bumptech.glide.request.e<TranscodeType> r29, @androidx.annotation.Nullable com.bumptech.glide.request.d r30, com.bumptech.glide.j<?, ? super TranscodeType> r31, com.bumptech.glide.g r32, int r33, int r34, com.bumptech.glide.request.a<?> r35, java.util.concurrent.Executor r36) {
        /*
            r26 = this;
            r11 = r26
            r15 = r27
            r14 = r30
            r13 = r32
            com.bumptech.glide.h<TranscodeType> r0 = r11.U4
            if (r0 == 0) goto L_0x00b0
            boolean r1 = r11.Z4
            if (r1 != 0) goto L_0x00a5
            com.bumptech.glide.j<?, ? super TranscodeType> r1 = r0.R4
            boolean r2 = r0.X4
            if (r2 == 0) goto L_0x001b
            r1 = r31
            r23 = r1
            goto L_0x001d
        L_0x001b:
            r23 = r1
        L_0x001d:
            boolean r0 = r0.I()
            if (r0 == 0) goto L_0x002c
            com.bumptech.glide.h<TranscodeType> r0 = r11.U4
            com.bumptech.glide.g r0 = r0.w()
            r18 = r0
            goto L_0x0032
        L_0x002c:
            com.bumptech.glide.g r0 = r11.B0(r13)
            r18 = r0
        L_0x0032:
            com.bumptech.glide.h<TranscodeType> r0 = r11.U4
            int r0 = r0.t()
            com.bumptech.glide.h<TranscodeType> r1 = r11.U4
            int r1 = r1.s()
            boolean r2 = com.bumptech.glide.util.j.t(r33, r34)
            if (r2 == 0) goto L_0x005a
            com.bumptech.glide.h<TranscodeType> r2 = r11.U4
            boolean r2 = r2.R()
            if (r2 != 0) goto L_0x005a
            int r0 = r35.t()
            int r1 = r35.s()
            r24 = r0
            r25 = r1
            goto L_0x005e
        L_0x005a:
            r24 = r0
            r25 = r1
        L_0x005e:
            com.bumptech.glide.request.i r0 = new com.bumptech.glide.request.i
            r0.<init>(r15, r14)
            r12 = r0
            r0 = r26
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r35
            r5 = r12
            r6 = r31
            r7 = r32
            r8 = r33
            r9 = r34
            r10 = r36
            com.bumptech.glide.request.c r0 = r0.O0(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r1 = 1
            r11.Z4 = r1
            com.bumptech.glide.h<TranscodeType> r1 = r11.U4
            r2 = r12
            r12 = r1
            r10 = r13
            r13 = r27
            r9 = r14
            r14 = r28
            r8 = r15
            r15 = r29
            r16 = r2
            r17 = r23
            r19 = r24
            r20 = r25
            r21 = r1
            r22 = r36
            com.bumptech.glide.request.c r1 = r12.y0(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            r3 = 0
            r11.Z4 = r3
            r2.m(r0, r1)
            return r2
        L_0x00a5:
            r10 = r13
            r9 = r14
            r8 = r15
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "You cannot use a request as both the main request and a thumbnail, consider using clone() on the request(s) passed to thumbnail()"
            r0.<init>(r1)
            throw r0
        L_0x00b0:
            r10 = r13
            r9 = r14
            r8 = r15
            java.lang.Float r0 = r11.W4
            if (r0 == 0) goto L_0x00f9
            com.bumptech.glide.request.i r0 = new com.bumptech.glide.request.i
            r0.<init>(r8, r9)
            r12 = r0
            r0 = r26
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r35
            r5 = r12
            r6 = r31
            r7 = r32
            r8 = r33
            r9 = r34
            r13 = r10
            r10 = r36
            com.bumptech.glide.request.c r14 = r0.O0(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            com.bumptech.glide.request.a r0 = r35.clone()
            java.lang.Float r1 = r11.W4
            float r1 = r1.floatValue()
            com.bumptech.glide.request.a r15 = r0.l0(r1)
            com.bumptech.glide.g r7 = r11.B0(r13)
            r0 = r26
            r1 = r27
            r4 = r15
            com.bumptech.glide.request.c r0 = r0.O0(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r12.m(r14, r0)
            return r12
        L_0x00f9:
            r13 = r10
            r0 = r26
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r35
            r5 = r30
            r6 = r31
            r7 = r32
            r8 = r33
            r9 = r34
            r10 = r36
            com.bumptech.glide.request.c r0 = r0.O0(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.h.z0(java.lang.Object, com.bumptech.glide.request.target.j, com.bumptech.glide.request.e, com.bumptech.glide.request.d, com.bumptech.glide.j, com.bumptech.glide.g, int, int, com.bumptech.glide.request.a, java.util.concurrent.Executor):com.bumptech.glide.request.c");
    }

    private c O0(Object requestLock, j<TranscodeType> target, e<TranscodeType> targetListener, com.bumptech.glide.request.a<?> requestOptions, com.bumptech.glide.request.d requestCoordinator, j<?, ? super TranscodeType> transitionOptions, g priority, int overrideWidth, int overrideHeight, Executor callbackExecutor) {
        Context context = this.M4;
        d dVar = this.Q4;
        return com.bumptech.glide.request.h.w(context, dVar, requestLock, this.S4, this.O4, requestOptions, overrideWidth, overrideHeight, priority, target, targetListener, this.T4, requestCoordinator, dVar.f(), transitionOptions.b(), callbackExecutor);
    }
}
