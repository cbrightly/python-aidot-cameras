package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ScopesHolderForClass.kt */
public final class m0<T extends h> {
    static final /* synthetic */ k[] a = {a0.h(new u(a0.b(m0.class), "scopeForOwnerModule", "getScopeForOwnerModule()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;"))};
    public static final a b = new a((DefaultConstructorMarker) null);
    private final f c;
    private final e d;
    /* access modifiers changed from: private */
    public final l<i, T> e;
    /* access modifiers changed from: private */
    public final i f;

    private final T d() {
        return (h) kotlin.reflect.jvm.internal.impl.storage.i.a(this.c, this, a[0]);
    }

    private m0(e classDescriptor, j storageManager, l<? super i, ? extends T> scopeFactory, i kotlinTypeRefinerForOwnerModule) {
        this.d = classDescriptor;
        this.e = scopeFactory;
        this.f = kotlinTypeRefinerForOwnerModule;
        this.c = storageManager.c(new c(this));
    }

    public /* synthetic */ m0(e classDescriptor, j storageManager, l scopeFactory, i kotlinTypeRefinerForOwnerModule, DefaultConstructorMarker $constructor_marker) {
        this(classDescriptor, storageManager, scopeFactory, kotlinTypeRefinerForOwnerModule);
    }

    /* compiled from: ScopesHolderForClass.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<T> {
        final /* synthetic */ m0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(m0 m0Var) {
            super(0);
            this.this$0 = m0Var;
        }

        @NotNull
        public final T invoke() {
            return (h) this.this$0.e.invoke(this.this$0.f);
        }
    }

    @NotNull
    public final T c(@NotNull i kotlinTypeRefiner) {
        kotlin.jvm.internal.k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        if (!kotlinTypeRefiner.c(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.m(this.d))) {
            return d();
        }
        u0 i = this.d.i();
        kotlin.jvm.internal.k.b(i, "classDescriptor.typeConstructor");
        if (!kotlinTypeRefiner.d(i)) {
            return d();
        }
        return kotlinTypeRefiner.b(this.d, new b(this, kotlinTypeRefiner));
    }

    /* compiled from: ScopesHolderForClass.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<T> {
        final /* synthetic */ i $kotlinTypeRefiner;
        final /* synthetic */ m0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(m0 m0Var, i iVar) {
            super(0);
            this.this$0 = m0Var;
            this.$kotlinTypeRefiner = iVar;
        }

        @NotNull
        public final T invoke() {
            return (h) this.this$0.e.invoke(this.$kotlinTypeRefiner);
        }
    }

    /* compiled from: ScopesHolderForClass.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final <T extends h> m0<T> a(@NotNull e classDescriptor, @NotNull j storageManager, @NotNull i kotlinTypeRefinerForOwnerModule, @NotNull l<? super i, ? extends T> scopeFactory) {
            kotlin.jvm.internal.k.f(classDescriptor, "classDescriptor");
            kotlin.jvm.internal.k.f(storageManager, "storageManager");
            kotlin.jvm.internal.k.f(kotlinTypeRefinerForOwnerModule, "kotlinTypeRefinerForOwnerModule");
            kotlin.jvm.internal.k.f(scopeFactory, "scopeFactory");
            return new m0(classDescriptor, storageManager, scopeFactory, kotlinTypeRefinerForOwnerModule, (DefaultConstructorMarker) null);
        }
    }
}
