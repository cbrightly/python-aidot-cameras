package androidx.core.view;

import android.view.View;
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
@f(c = "androidx.core.view.ViewKt$allViews$1", f = "View.kt", l = {406, 408}, m = "invokeSuspend")
/* compiled from: View.kt */
public final class ViewKt$allViews$1 extends k implements p<j<? super View>, d<? super x>, Object> {
    final /* synthetic */ View $this_allViews;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ViewKt$allViews$1(View view, d<? super ViewKt$allViews$1> dVar) {
        super(2, dVar);
        this.$this_allViews = view;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        ViewKt$allViews$1 viewKt$allViews$1 = new ViewKt$allViews$1(this.$this_allViews, dVar);
        viewKt$allViews$1.L$0 = obj;
        return viewKt$allViews$1;
    }

    @Nullable
    public final Object invoke(@NotNull j<? super View> jVar, @Nullable d<? super x> dVar) {
        return ((ViewKt$allViews$1) create(jVar, dVar)).invokeSuspend(x.a);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: kotlin.sequences.j} */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r6.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x0022;
                case 1: goto L_0x0017;
                case 2: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L_0x0012:
            r0 = r6
            kotlin.p.b(r7)
            goto L_0x0051
        L_0x0017:
            r1 = r6
            r3 = r2
            java.lang.Object r4 = r1.L$0
            r3 = r4
            kotlin.sequences.j r3 = (kotlin.sequences.j) r3
            kotlin.p.b(r7)
            goto L_0x0038
        L_0x0022:
            kotlin.p.b(r7)
            r1 = r6
            java.lang.Object r3 = r1.L$0
            kotlin.sequences.j r3 = (kotlin.sequences.j) r3
            android.view.View r4 = r1.$this_allViews
            r1.L$0 = r3
            r5 = 1
            r1.label = r5
            java.lang.Object r4 = r3.g(r4, r1)
            if (r4 != r0) goto L_0x0038
            return r0
        L_0x0038:
            android.view.View r4 = r1.$this_allViews
            boolean r5 = r4 instanceof android.view.ViewGroup
            if (r5 == 0) goto L_0x0052
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            kotlin.sequences.h r4 = androidx.core.view.ViewGroupKt.getDescendants(r4)
            r1.L$0 = r2
            r2 = 2
            r1.label = r2
            java.lang.Object r2 = r3.j(r4, r1)
            if (r2 != r0) goto L_0x0050
            return r0
        L_0x0050:
            r0 = r1
        L_0x0051:
            r1 = r0
        L_0x0052:
            kotlin.x r0 = kotlin.x.a
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.ViewKt$allViews$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
