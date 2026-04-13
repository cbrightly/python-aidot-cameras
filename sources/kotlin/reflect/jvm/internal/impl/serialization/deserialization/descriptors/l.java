package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.b;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.g;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.n;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: DeserializedTypeParameterDescriptor.kt */
public final class l extends b {
    @NotNull
    private final a a2;
    /* access modifiers changed from: private */
    public final n p2;
    @NotNull
    private final s p3;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public l(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r11, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.s r12, int r13) {
        /*
            r10 = this;
            java.lang.String r0 = "c"
            kotlin.jvm.internal.k.f(r11, r0)
            java.lang.String r0 = "proto"
            kotlin.jvm.internal.k.f(r12, r0)
            kotlin.reflect.jvm.internal.impl.storage.j r2 = r11.h()
            kotlin.reflect.jvm.internal.impl.descriptors.m r3 = r11.e()
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r0 = r11.g()
            int r1 = r12.getName()
            kotlin.reflect.jvm.internal.impl.name.f r4 = kotlin.reflect.jvm.internal.impl.serialization.deserialization.y.b(r0, r1)
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.c0 r0 = kotlin.reflect.jvm.internal.impl.serialization.deserialization.c0.a
            kotlin.reflect.jvm.internal.impl.metadata.s$c r1 = r12.getVariance()
            java.lang.String r5 = "proto.variance"
            kotlin.jvm.internal.k.b(r1, r5)
            kotlin.reflect.jvm.internal.impl.types.h1 r5 = r0.e(r1)
            boolean r6 = r12.getReified()
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r8 = kotlin.reflect.jvm.internal.impl.descriptors.o0.a
            kotlin.reflect.jvm.internal.impl.descriptors.r0$a r9 = kotlin.reflect.jvm.internal.impl.descriptors.r0.a.a
            r1 = r10
            r7 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            r10.p2 = r11
            r10.p3 = r12
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.a r0 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.a
            kotlin.reflect.jvm.internal.impl.storage.j r1 = r11.h()
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.l$a r2 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.l$a
            r2.<init>(r10)
            r0.<init>(r1, r2)
            r10.a2 = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.l.<init>(kotlin.reflect.jvm.internal.impl.serialization.deserialization.n, kotlin.reflect.jvm.internal.impl.metadata.s, int):void");
    }

    @NotNull
    public final s G0() {
        return this.p3;
    }

    /* compiled from: DeserializedTypeParameterDescriptor.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends c>> {
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(l lVar) {
            super(0);
            this.this$0 = lVar;
        }

        @NotNull
        public final List<c> invoke() {
            return y.C0(this.this$0.p2.c().d().f(this.this$0.G0(), this.this$0.p2.g()));
        }
    }

    @NotNull
    /* renamed from: C0 */
    public a getAnnotations() {
        return this.a2;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public List<b0> l0() {
        List<q> o = g.o(this.p3, this.p2.j());
        if (o.isEmpty()) {
            return p.b(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(this).y());
        }
        Iterable<q> $this$mapTo$iv$iv = o;
        e0 i = this.p2.i();
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (q p1 : $this$mapTo$iv$iv) {
            arrayList.add(i.n(p1));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: H0 */
    public Void f0(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        throw new IllegalStateException("There should be no cycles for deserialized type parameters, but found for: " + this);
    }
}
