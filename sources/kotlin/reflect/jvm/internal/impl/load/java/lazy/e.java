package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Iterator;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.storage.d;
import kotlin.sequences.h;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaAnnotations.kt */
public final class e implements g {
    private final d<kotlin.reflect.jvm.internal.impl.load.java.structure.a, c> c;
    /* access modifiers changed from: private */
    public final h d;
    private final kotlin.reflect.jvm.internal.impl.load.java.structure.d f;

    public e(@NotNull h c2, @NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.d annotationOwner) {
        k.f(c2, "c");
        k.f(annotationOwner, "annotationOwner");
        this.d = c2;
        this.f = annotationOwner;
        this.c = c2.a().s().g(new a(this));
    }

    public boolean I(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return g.b.b(this, fqName);
    }

    /* compiled from: LazyJavaAnnotations.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.load.java.structure.a, c> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e eVar) {
            super(1);
            this.this$0 = eVar;
        }

        @Nullable
        public final c invoke(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.a annotation) {
            k.f(annotation, "annotation");
            return kotlin.reflect.jvm.internal.impl.load.java.components.c.k.e(annotation, this.this$0.d);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000d, code lost:
        r0 = r3.c.invoke(r0);
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public kotlin.reflect.jvm.internal.impl.descriptors.annotations.c c(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.name.b r4) {
        /*
            r3 = this;
            java.lang.String r0 = "fqName"
            kotlin.jvm.internal.k.f(r4, r0)
            kotlin.reflect.jvm.internal.impl.load.java.structure.d r0 = r3.f
            kotlin.reflect.jvm.internal.impl.load.java.structure.a r0 = r0.c(r4)
            if (r0 == 0) goto L_0x0018
            kotlin.reflect.jvm.internal.impl.storage.d<kotlin.reflect.jvm.internal.impl.load.java.structure.a, kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> r1 = r3.c
            java.lang.Object r0 = r1.invoke(r0)
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.c r0 = (kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) r0
            if (r0 == 0) goto L_0x0018
            goto L_0x0022
        L_0x0018:
            kotlin.reflect.jvm.internal.impl.load.java.components.c r0 = kotlin.reflect.jvm.internal.impl.load.java.components.c.k
            kotlin.reflect.jvm.internal.impl.load.java.structure.d r1 = r3.f
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h r2 = r3.d
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.c r0 = r0.a(r4, r1, r2)
        L_0x0022:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.e.c(kotlin.reflect.jvm.internal.impl.name.b):kotlin.reflect.jvm.internal.impl.descriptors.annotations.c");
    }

    @NotNull
    public Iterator<c> iterator() {
        h<c> w = o.w(y.L(this.f.getAnnotations()), this.c);
        kotlin.reflect.jvm.internal.impl.load.java.components.c cVar = kotlin.reflect.jvm.internal.impl.load.java.components.c.k;
        b bVar = kotlin.reflect.jvm.internal.impl.builtins.g.h.x;
        k.b(bVar, "KotlinBuiltIns.FQ_NAMES.deprecated");
        return o.q(o.z(w, cVar.a(bVar, this.f, this.d))).iterator();
    }

    public boolean isEmpty() {
        return this.f.getAnnotations().isEmpty() && !this.f.w();
    }
}
