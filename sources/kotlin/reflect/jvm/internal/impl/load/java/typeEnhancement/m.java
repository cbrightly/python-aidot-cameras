package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.d0;
import kotlin.collections.k0;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.load.kotlin.v;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import kotlin.t;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: predefinedEnhancementInfo.kt */
public final class m {
    /* access modifiers changed from: private */
    public final Map<String, j> a = new LinkedHashMap();

    /* compiled from: predefinedEnhancementInfo.kt */
    public final class a {
        @NotNull
        private final String a;
        final /* synthetic */ m b;

        public a(@NotNull m $outer, String className) {
            k.f(className, "className");
            this.b = $outer;
            this.a = className;
        }

        @NotNull
        public final String b() {
            return this.a;
        }

        public final void a(@NotNull String name, @NotNull l<? super C0369a, x> block) {
            k.f(name, "name");
            k.f(block, "block");
            Map a2 = this.b.a;
            C0369a aVar = new C0369a(this, name);
            block.invoke(aVar);
            n<String, j> a3 = aVar.a();
            a2.put(a3.getFirst(), a3.getSecond());
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.m$a$a  reason: collision with other inner class name */
        /* compiled from: predefinedEnhancementInfo.kt */
        public final class C0369a {
            private final List<n<String, r>> a = new ArrayList();
            private n<String, r> b = t.a(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, null);
            @NotNull
            private final String c;
            final /* synthetic */ a d;

            public C0369a(@NotNull a $outer, String functionName) {
                k.f(functionName, "functionName");
                this.d = $outer;
                this.c = functionName;
            }

            public final void b(@NotNull String type, @NotNull d... qualifiers) {
                r rVar;
                k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                k.f(qualifiers, "qualifiers");
                List<n<String, r>> list = this.a;
                if (qualifiers.length == 0) {
                    rVar = null;
                } else {
                    Iterable $this$associateBy$iv = kotlin.collections.l.h0(qualifiers);
                    Map destination$iv$iv = new LinkedHashMap(kotlin.ranges.n.b(k0.b(r.r($this$associateBy$iv, 10)), 16));
                    for (Object element$iv$iv : $this$associateBy$iv) {
                        destination$iv$iv.put(Integer.valueOf(((d0) element$iv$iv).c()), (d) ((d0) element$iv$iv).d());
                    }
                    rVar = new r(destination$iv$iv);
                }
                list.add(t.a(type, rVar));
            }

            public final void c(@NotNull String type, @NotNull d... qualifiers) {
                k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                k.f(qualifiers, "qualifiers");
                Iterable $this$associateBy$iv = kotlin.collections.l.h0(qualifiers);
                Map destination$iv$iv = new LinkedHashMap(kotlin.ranges.n.b(k0.b(r.r($this$associateBy$iv, 10)), 16));
                for (Object element$iv$iv : $this$associateBy$iv) {
                    destination$iv$iv.put(Integer.valueOf(((d0) element$iv$iv).c()), (d) ((d0) element$iv$iv).d());
                }
                this.b = t.a(type, new r(destination$iv$iv));
            }

            public final void d(@NotNull d type) {
                k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                this.b = t.a(type.getDesc(), null);
            }

            @NotNull
            public final n<String, j> a() {
                v $this$with = v.a;
                String b2 = this.d.b();
                String str = this.c;
                Iterable<n> $this$mapTo$iv$iv = this.a;
                ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (n it : $this$mapTo$iv$iv) {
                    arrayList.add((String) it.getFirst());
                }
                String k = $this$with.k(b2, $this$with.j(str, arrayList, this.b.getFirst()));
                r second = this.b.getSecond();
                Iterable<n> $this$mapTo$iv$iv2 = this.a;
                ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
                for (n it2 : $this$mapTo$iv$iv2) {
                    arrayList2.add((r) it2.getSecond());
                }
                return t.a(k, new j(second, arrayList2));
            }
        }
    }

    @NotNull
    public final Map<String, j> b() {
        return this.a;
    }
}
