package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThrowingCaller.kt */
public final class j implements d {
    public static final j a = new j();

    private j() {
    }

    public /* bridge */ /* synthetic */ Member b() {
        return (Member) c();
    }

    @Nullable
    public Void c() {
        return null;
    }

    @NotNull
    public List<Type> a() {
        return q.g();
    }

    @NotNull
    public Type getReturnType() {
        Class cls = Void.TYPE;
        k.b(cls, "Void.TYPE");
        return cls;
    }

    @Nullable
    public Object call(@NotNull Object[] args) {
        k.f(args, "args");
        throw new UnsupportedOperationException("call/callBy are not supported for this declaration.");
    }
}
