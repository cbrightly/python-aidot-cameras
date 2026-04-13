package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.List;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.e;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: CloneableClassScope.kt */
public final class a extends e {
    /* access modifiers changed from: private */
    @NotNull
    public static final f e;
    public static final C0345a f = new C0345a((DefaultConstructorMarker) null);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(@NotNull j storageManager, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e containingClass) {
        super(storageManager, containingClass);
        k.f(storageManager, "storageManager");
        k.f(containingClass, "containingClass");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public List<u> h() {
        f0 $this$apply = f0.d1(k(), g.b.b(), e, b.a.DECLARATION, o0.a);
        $this$apply.J0((l0) null, k().F0(), q.g(), q.g(), kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(k()).j(), w.OPEN, z0.c);
        return p.b($this$apply);
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.jvm.a$a  reason: collision with other inner class name */
    /* compiled from: CloneableClassScope.kt */
    public static final class C0345a {
        private C0345a() {
        }

        public /* synthetic */ C0345a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final f a() {
            return a.e;
        }
    }

    static {
        f f2 = f.f("clone");
        k.b(f2, "Name.identifier(\"clone\")");
        e = f2;
    }
}
