package io.ktor.routing;

import io.ktor.http.y;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RouteSelector.kt */
public final class h extends j {
    private final List<String> b;
    private final k c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h(@NotNull String rootPath) {
        super(1.0d);
        String str = rootPath;
        k.f(str, "rootPath");
        Iterable<t> $this$mapTo$iv$iv = s.b.b(str).b();
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (t it : $this$mapTo$iv$iv) {
            if (it.c() == u.Constant) {
                arrayList.add(it.d());
            } else {
                throw new IllegalArgumentException("rootPath should be constant, no wildcards supported.".toString());
            }
        }
        this.b = arrayList;
        this.c = new k(true, 1.0d, (y) null, arrayList.size(), 4, (DefaultConstructorMarker) null);
    }

    @NotNull
    public k a(@NotNull v context, int segmentIndex) {
        k.f(context, "context");
        if (!(segmentIndex == 0)) {
            throw new IllegalStateException("Root selector should be evaluated first.".toString());
        } else if (this.b.isEmpty()) {
            return k.f.a();
        } else {
            List parts = this.b;
            List segments = context.b();
            if (segments.size() < parts.size()) {
                return k.f.c();
            }
            int size = parts.size() + segmentIndex;
            for (int index = segmentIndex; index < size; index++) {
                if (!k.a(segments.get(index), parts.get(index))) {
                    return k.f.c();
                }
            }
            return this.c;
        }
    }

    @NotNull
    public String toString() {
        return kotlin.collections.y.b0(this.b, "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
    }
}
