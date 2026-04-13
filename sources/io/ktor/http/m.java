package io.ktor.http;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HeaderValueWithParameters.kt */
public abstract class m {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    @NotNull
    private final List<l> c;

    public m(@NotNull String content, @NotNull List<l> parameters) {
        k.f(content, FirebaseAnalytics.Param.CONTENT);
        k.f(parameters, "parameters");
        this.b = content;
        this.c = parameters;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final List<l> b() {
        return this.c;
    }

    @Nullable
    public final String c(@NotNull String name) {
        Object element$iv;
        k.f(name, "name");
        Iterator<T> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (w.y(((l) element$iv).c(), name, true)) {
                break;
            }
        }
        l lVar = (l) element$iv;
        if (lVar != null) {
            return lVar.d();
        }
        return null;
    }

    @NotNull
    public String toString() {
        if (this.c.isEmpty()) {
            return this.b;
        }
        int length = this.b.length();
        int sum$iv = 0;
        for (l it : this.c) {
            sum$iv += it.c().length() + it.d().length() + 3;
        }
        StringBuilder sb = new StringBuilder(length + sum$iv);
        StringBuilder $this$apply = sb;
        $this$apply.append(this.b);
        int size = this.c.size();
        for (int index = 0; index < size; index++) {
            l lVar = this.c.get(index);
            String name = lVar.a();
            String value = lVar.b();
            $this$apply.append("; ");
            $this$apply.append(name);
            $this$apply.append("=");
            String $this$escapeIfNeededTo$iv = value;
            if (n.b($this$escapeIfNeededTo$iv)) {
                $this$apply.append(n.c($this$escapeIfNeededTo$iv));
            } else {
                $this$apply.append($this$escapeIfNeededTo$iv);
            }
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder(size).appl…\n            }.toString()");
        return sb2;
    }

    /* compiled from: HeaderValueWithParameters.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
