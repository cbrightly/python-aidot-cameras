package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.k;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.sequences.j;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Lkotlin/sequences/j;", "Landroid/view/View;", "Lkotlin/x;", "<anonymous>", "(Lkotlin/sequences/j;)V"}, k = 3, mv = {1, 5, 1})
@f(c = "androidx.core.view.ViewGroupKt$descendants$1", f = "ViewGroup.kt", l = {97, 99}, m = "invokeSuspend")
/* compiled from: ViewGroup.kt */
public final class ViewGroupKt$descendants$1 extends k implements p<j<? super View>, d<? super x>, Object> {
    final /* synthetic */ ViewGroup $this_descendants;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ViewGroupKt$descendants$1(ViewGroup viewGroup, d<? super ViewGroupKt$descendants$1> dVar) {
        super(2, dVar);
        this.$this_descendants = viewGroup;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        ViewGroupKt$descendants$1 viewGroupKt$descendants$1 = new ViewGroupKt$descendants$1(this.$this_descendants, dVar);
        viewGroupKt$descendants$1.L$0 = obj;
        return viewGroupKt$descendants$1;
    }

    @Nullable
    public final Object invoke(@NotNull j<? super View> jVar, @Nullable d<? super x> dVar) {
        return ((ViewGroupKt$descendants$1) create(jVar, dVar)).invokeSuspend(x.a);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v1, resolved type: android.view.ViewGroup} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: android.view.View} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: android.view.ViewGroup} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: kotlin.sequences.j} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: android.view.ViewGroup} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlin.sequences.j} */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x007a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x007b, code lost:
        r9 = r2;
        r8 = r7;
        r7 = r5;
        r5 = r6;
        r6 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0084, code lost:
        if ((r6 instanceof android.view.ViewGroup) == false) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0086, code lost:
        r10 = androidx.core.view.ViewGroupKt.getDescendants((android.view.ViewGroup) r6);
        r1.L$0 = r4;
        r1.L$1 = r7;
        r1.L$2 = null;
        r1.I$0 = r9;
        r1.I$1 = r8;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x009e, code lost:
        if (r4.j(r10, r1) != r0) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00a0, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00a1, code lost:
        r6 = r7;
        r7 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a4, code lost:
        r2 = r8;
        r12 = r6;
        r6 = r5;
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a9, code lost:
        r6 = r5;
        r5 = r7;
        r7 = r8;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ad, code lost:
        if (r2 < r7) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b2, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0059, code lost:
        if (r7 > 0) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x005b, code lost:
        r8 = r2;
        r2 = r2 + 1;
        r10 = r5.getChildAt(r8);
        kotlin.jvm.internal.k.d(r10, "getChildAt(index)");
        r1.L$0 = r4;
        r1.L$1 = r5;
        r1.L$2 = r10;
        r1.I$0 = r2;
        r1.I$1 = r7;
        r1.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0078, code lost:
        if (r4.g(r10, r1) != r0) goto L_0x007b;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r14) {
        /*
            r13 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r13.label
            r2 = 0
            r3 = 0
            switch(r1) {
                case 0: goto L_0x004a;
                case 1: goto L_0x002c;
                case 2: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L_0x0013:
            r1 = r13
            r4 = r3
            r5 = r2
            r6 = r3
            r5 = 0
            r2 = 0
            int r7 = r1.I$1
            int r8 = r1.I$0
            java.lang.Object r9 = r1.L$1
            r6 = r9
            android.view.ViewGroup r6 = (android.view.ViewGroup) r6
            java.lang.Object r9 = r1.L$0
            r4 = r9
            kotlin.sequences.j r4 = (kotlin.sequences.j) r4
            kotlin.p.b(r14)
            goto L_0x00a4
        L_0x002c:
            r1 = r13
            r4 = r3
            r5 = r2
            r6 = r3
            r7 = r3
            r5 = 0
            r2 = 0
            int r8 = r1.I$1
            int r9 = r1.I$0
            java.lang.Object r10 = r1.L$2
            r6 = r10
            android.view.View r6 = (android.view.View) r6
            java.lang.Object r10 = r1.L$1
            r7 = r10
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            java.lang.Object r10 = r1.L$0
            r4 = r10
            kotlin.sequences.j r4 = (kotlin.sequences.j) r4
            kotlin.p.b(r14)
            goto L_0x0081
        L_0x004a:
            kotlin.p.b(r14)
            r1 = r13
            java.lang.Object r4 = r1.L$0
            kotlin.sequences.j r4 = (kotlin.sequences.j) r4
            android.view.ViewGroup r5 = r1.$this_descendants
            r6 = 0
            int r7 = r5.getChildCount()
            if (r7 <= 0) goto L_0x00af
        L_0x005b:
            r8 = r2
            r9 = 1
            int r2 = r2 + r9
            android.view.View r10 = r5.getChildAt(r8)
            java.lang.String r11 = "getChildAt(index)"
            kotlin.jvm.internal.k.d(r10, r11)
            r11 = 0
            r1.L$0 = r4
            r1.L$1 = r5
            r1.L$2 = r10
            r1.I$0 = r2
            r1.I$1 = r7
            r1.label = r9
            java.lang.Object r8 = r4.g(r10, r1)
            if (r8 != r0) goto L_0x007b
            return r0
        L_0x007b:
            r9 = r2
            r8 = r7
            r2 = r11
            r7 = r5
            r5 = r6
            r6 = r10
        L_0x0081:
            boolean r10 = r6 instanceof android.view.ViewGroup
            if (r10 == 0) goto L_0x00a9
            r10 = r6
            android.view.ViewGroup r10 = (android.view.ViewGroup) r10
            kotlin.sequences.h r10 = androidx.core.view.ViewGroupKt.getDescendants(r10)
            r1.L$0 = r4
            r1.L$1 = r7
            r1.L$2 = r3
            r1.I$0 = r9
            r1.I$1 = r8
            r11 = 2
            r1.label = r11
            java.lang.Object r6 = r4.j(r10, r1)
            if (r6 != r0) goto L_0x00a1
            return r0
        L_0x00a1:
            r6 = r7
            r7 = r8
            r8 = r9
        L_0x00a4:
            r2 = r8
            r12 = r6
            r6 = r5
            r5 = r12
            goto L_0x00ad
        L_0x00a9:
            r6 = r5
            r5 = r7
            r7 = r8
            r2 = r9
        L_0x00ad:
            if (r2 < r7) goto L_0x005b
        L_0x00af:
            kotlin.x r0 = kotlin.x.a
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.ViewGroupKt$descendants$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
