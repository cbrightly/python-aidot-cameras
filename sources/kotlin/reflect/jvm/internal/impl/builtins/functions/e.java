package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: FunctionClassScope.kt */
public final class e extends kotlin.reflect.jvm.internal.impl.resolve.scopes.e {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@NotNull j storageManager, @NotNull b containingClass) {
        super(storageManager, containingClass);
        k.f(storageManager, "storageManager");
        k.f(containingClass, "containingClass");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public List<u> h() {
        kotlin.reflect.jvm.internal.impl.descriptors.e k = k();
        if (k != null) {
            switch (d.a[((b) k).M0().ordinal()]) {
                case 1:
                    return p.b(f.O4.a((b) k(), false));
                case 2:
                    return p.b(f.O4.a((b) k(), true));
                default:
                    return q.g();
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
        }
    }
}
