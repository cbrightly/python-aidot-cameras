package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.collections.l0;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.i;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: JavaAnnotationMapper.kt */
public class b implements c, i {
    static final /* synthetic */ k[] a = {a0.h(new u(a0.b(b.class), IjkMediaMeta.IJKM_KEY_TYPE, "getType()Lorg/jetbrains/kotlin/types/SimpleType;"))};
    @NotNull
    private final o0 b;
    @NotNull
    private final f c;
    @Nullable
    private final kotlin.reflect.jvm.internal.impl.load.java.structure.b d;
    private final boolean e;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.name.b f;

    @NotNull
    /* renamed from: c */
    public i0 getType() {
        return (i0) kotlin.reflect.jvm.internal.impl.storage.i.a(this.c, this, a[0]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003c, code lost:
        r0 = r5.getArguments();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h r4, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.load.java.structure.a r5, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.name.b r6) {
        /*
            r3 = this;
            java.lang.String r0 = "c"
            kotlin.jvm.internal.k.f(r4, r0)
            java.lang.String r0 = "fqName"
            kotlin.jvm.internal.k.f(r6, r0)
            r3.<init>()
            r3.f = r6
            if (r5 == 0) goto L_0x0022
            r0 = r5
            r1 = 0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.b r2 = r4.a()
            kotlin.reflect.jvm.internal.impl.load.java.sources.b r2 = r2.r()
            kotlin.reflect.jvm.internal.impl.load.java.sources.a r0 = r2.a(r0)
            if (r0 == 0) goto L_0x0022
            goto L_0x0029
        L_0x0022:
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r0 = kotlin.reflect.jvm.internal.impl.descriptors.o0.a
            java.lang.String r1 = "SourceElement.NO_SOURCE"
            kotlin.jvm.internal.k.b(r0, r1)
        L_0x0029:
            r3.b = r0
            kotlin.reflect.jvm.internal.impl.storage.j r0 = r4.e()
            kotlin.reflect.jvm.internal.impl.load.java.components.b$a r1 = new kotlin.reflect.jvm.internal.impl.load.java.components.b$a
            r1.<init>(r3, r4)
            kotlin.reflect.jvm.internal.impl.storage.f r0 = r0.c(r1)
            r3.c = r0
            if (r5 == 0) goto L_0x0049
            java.util.Collection r0 = r5.getArguments()
            if (r0 == 0) goto L_0x0049
            java.lang.Object r0 = kotlin.collections.y.T(r0)
            kotlin.reflect.jvm.internal.impl.load.java.structure.b r0 = (kotlin.reflect.jvm.internal.impl.load.java.structure.b) r0
            goto L_0x004a
        L_0x0049:
            r0 = 0
        L_0x004a:
            r3.d = r0
            r0 = 1
            if (r5 == 0) goto L_0x0056
            boolean r1 = r5.h()
            if (r1 != r0) goto L_0x0056
            goto L_0x0057
        L_0x0056:
            r0 = 0
        L_0x0057:
            r3.e = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.components.b.<init>(kotlin.reflect.jvm.internal.impl.load.java.lazy.h, kotlin.reflect.jvm.internal.impl.load.java.structure.a, kotlin.reflect.jvm.internal.impl.name.b):void");
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.b e() {
        return this.f;
    }

    @NotNull
    public o0 n() {
        return this.b;
    }

    /* compiled from: JavaAnnotationMapper.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ h $c;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b bVar, h hVar) {
            super(0);
            this.this$0 = bVar;
            this.$c = hVar;
        }

        @NotNull
        public final i0 invoke() {
            e o = this.$c.d().j().o(this.this$0.e());
            kotlin.jvm.internal.k.b(o, "c.module.builtIns.getBuiltInClassByFqName(fqName)");
            return o.m();
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final kotlin.reflect.jvm.internal.impl.load.java.structure.b b() {
        return this.d;
    }

    @NotNull
    public Map<kotlin.reflect.jvm.internal.impl.name.f, g<?>> a() {
        return l0.f();
    }

    public boolean h() {
        return this.e;
    }
}
