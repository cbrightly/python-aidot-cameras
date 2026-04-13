package okhttp3.internal.concurrent;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Task.kt */
public abstract class a {
    @Nullable
    private d a;
    private long b;
    @NotNull
    private final String c;
    private final boolean d;

    public abstract long f();

    public a(@NotNull String name, boolean cancelable) {
        k.f(name, "name");
        this.c = name;
        this.d = cancelable;
        this.b = -1;
    }

    @NotNull
    public final String b() {
        return this.c;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? true : z);
    }

    public final boolean a() {
        return this.d;
    }

    @Nullable
    public final d d() {
        return this.a;
    }

    public final long c() {
        return this.b;
    }

    public final void g(long j) {
        this.b = j;
    }

    public final void e(@NotNull d queue) {
        k.f(queue, "queue");
        d dVar = this.a;
        if (dVar != queue) {
            if (dVar == null) {
                this.a = queue;
                return;
            }
            throw new IllegalStateException("task is in multiple queues".toString());
        }
    }

    @NotNull
    public String toString() {
        return this.c;
    }
}
