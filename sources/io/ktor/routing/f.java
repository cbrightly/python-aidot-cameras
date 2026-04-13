package io.ktor.routing;

import io.ktor.http.b0;
import io.ktor.http.y;
import java.util.ArrayList;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RouteSelector.kt */
public final class f extends j {
    @NotNull
    private final String b;
    @NotNull
    private final String c;

    public f() {
        this((String) null, (String) null, 3, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return k.a(this.b, fVar.b) && k.a(this.c, fVar.c);
    }

    public int hashCode() {
        String str = this.b;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.c;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f(@NotNull String name, @NotNull String prefix) {
        super(0.1d);
        k.f(name, "name");
        k.f(prefix, "prefix");
        this.b = name;
        this.c = prefix;
        CharSequence $this$none$iv = prefix;
        boolean z = false;
        int i = 0;
        while (true) {
            boolean z2 = true;
            if (i >= $this$none$iv.length()) {
                z = true;
                break;
            }
            if ($this$none$iv.charAt(i) != '/' ? false : z2) {
                break;
            }
            i++;
        }
        if (!z) {
            throw new IllegalArgumentException("Multisegment prefix is not supported".toString());
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ f(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2);
    }

    @NotNull
    public k a(@NotNull v context, int segmentIndex) {
        y values;
        double quality;
        String segmentText;
        k.f(context, "context");
        boolean z = true;
        if ((this.c.length() > 0) && ((segmentText = (String) kotlin.collections.y.V(context.b(), segmentIndex)) == null || !w.N(segmentText, this.c, false, 2, (Object) null))) {
            return k.f.c();
        }
        if (this.b.length() != 0) {
            z = false;
        }
        if (z) {
            values = b0.a();
        } else {
            String str = this.b;
            Iterable $this$mapIndexedTo$iv$iv = kotlin.collections.y.O(context.b(), segmentIndex);
            ArrayList arrayList = new ArrayList(r.r($this$mapIndexedTo$iv$iv, 10));
            int index$iv$iv = 0;
            for (T next : $this$mapIndexedTo$iv$iv) {
                int index$iv$iv2 = index$iv$iv + 1;
                if (index$iv$iv < 0) {
                    q.q();
                }
                String segment = (String) next;
                if (index$iv$iv == 0) {
                    segment = z.f1(segment, this.c.length());
                }
                arrayList.add(segment);
                index$iv$iv = index$iv$iv2;
            }
            values = b0.c(str, arrayList);
        }
        if (segmentIndex < context.b().size()) {
            quality = 0.1d;
        } else {
            quality = 0.2d;
        }
        return new k(true, quality, values, context.b().size() - segmentIndex);
    }

    @NotNull
    public String toString() {
        return "{...}";
    }
}
