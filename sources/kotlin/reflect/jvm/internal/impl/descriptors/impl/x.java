package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.o0;
import kotlin.collections.r;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.InvalidModuleException;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.c;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.q;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ModuleDescriptorImpl.kt */
public final class x extends j implements y {
    /* access modifiers changed from: private */
    public final j a1;
    @Nullable
    private final kotlin.reflect.jvm.internal.impl.platform.a a2;
    private final Map<y.a<?>, Object> f;
    private final g p0;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.builtins.g p1;
    @Nullable
    private final f p2;
    /* access modifiers changed from: private */
    public v q;
    /* access modifiers changed from: private */
    public c0 x;
    private boolean y;
    private final c<kotlin.reflect.jvm.internal.impl.name.b, e0> z;

    public x(@NotNull f fVar, @NotNull j jVar, @NotNull kotlin.reflect.jvm.internal.impl.builtins.g gVar, @Nullable kotlin.reflect.jvm.internal.impl.platform.a aVar) {
        this(fVar, jVar, gVar, aVar, (Map) null, (f) null, 48, (DefaultConstructorMarker) null);
    }

    private final i J0() {
        return (i) this.p0.getValue();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public x(@NotNull f moduleName, @NotNull j storageManager, @NotNull kotlin.reflect.jvm.internal.impl.builtins.g builtIns, @Nullable kotlin.reflect.jvm.internal.impl.platform.a platform, @NotNull Map<y.a<?>, ? extends Object> capabilities, @Nullable f stableName) {
        super(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), moduleName);
        k.f(moduleName, "moduleName");
        k.f(storageManager, "storageManager");
        k.f(builtIns, "builtIns");
        k.f(capabilities, "capabilities");
        this.a1 = storageManager;
        this.p1 = builtIns;
        this.a2 = platform;
        this.p2 = stableName;
        if (moduleName.h()) {
            Map<y.a<?>, Object> u = l0.u(capabilities);
            this.f = u;
            u.put(kotlin.reflect.jvm.internal.impl.types.checker.j.a(), new q(null));
            this.y = true;
            this.z = storageManager.h(new b(this));
            this.p0 = i.b(new a(this));
            return;
        }
        throw new IllegalArgumentException("Module name must be special: " + moduleName);
    }

    @Nullable
    public m b() {
        return y.b.b(this);
    }

    public <R, D> R w(@NotNull o<R, D> visitor, D data) {
        k.f(visitor, "visitor");
        return y.b.a(this, visitor, data);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.builtins.g j() {
        return this.p1;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ x(kotlin.reflect.jvm.internal.impl.name.f r10, kotlin.reflect.jvm.internal.impl.storage.j r11, kotlin.reflect.jvm.internal.impl.builtins.g r12, kotlin.reflect.jvm.internal.impl.platform.a r13, java.util.Map r14, kotlin.reflect.jvm.internal.impl.name.f r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
        /*
            r9 = this;
            r0 = r16 & 8
            r1 = 0
            if (r0 == 0) goto L_0x0007
            r6 = r1
            goto L_0x0008
        L_0x0007:
            r6 = r13
        L_0x0008:
            r0 = r16 & 16
            if (r0 == 0) goto L_0x0012
            java.util.Map r0 = kotlin.collections.l0.f()
            r7 = r0
            goto L_0x0013
        L_0x0012:
            r7 = r14
        L_0x0013:
            r0 = r16 & 32
            if (r0 == 0) goto L_0x0019
            r8 = r1
            goto L_0x001a
        L_0x0019:
            r8 = r15
        L_0x001a:
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.impl.x.<init>(kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.storage.j, kotlin.reflect.jvm.internal.impl.builtins.g, kotlin.reflect.jvm.internal.impl.platform.a, java.util.Map, kotlin.reflect.jvm.internal.impl.name.f, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public boolean M0() {
        return this.y;
    }

    public void G0() {
        if (!M0()) {
            throw new InvalidModuleException("Accessing invalid module descriptor " + this);
        }
    }

    /* compiled from: ModuleDescriptorImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.b, r> {
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(x xVar) {
            super(1);
            this.this$0 = xVar;
        }

        @NotNull
        public final r invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
            k.f(fqName, "fqName");
            x xVar = this.this$0;
            return new r(xVar, fqName, xVar.a1);
        }
    }

    @NotNull
    public List<y> u0() {
        v vVar = this.q;
        if (vVar != null) {
            return vVar.b();
        }
        throw new AssertionError("Dependencies of module " + H0() + " were not set");
    }

    @NotNull
    public e0 e0(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        k.f(fqName, "fqName");
        G0();
        return this.z.invoke(fqName);
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.name.b> k(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(fqName, "fqName");
        k.f(nameFilter, "nameFilter");
        G0();
        return I0().k(fqName, nameFilter);
    }

    /* compiled from: ModuleDescriptorImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<i> {
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(x xVar) {
            super(0);
            this.this$0 = xVar;
        }

        @NotNull
        public final i invoke() {
            v moduleDependencies = this.this$0.q;
            if (moduleDependencies != null) {
                List<x> $this$forEach$iv = moduleDependencies.a();
                if ($this$forEach$iv.contains(this.this$0)) {
                    for (x dependency : $this$forEach$iv) {
                        if (!dependency.L0()) {
                            throw new AssertionError("Dependency module " + dependency.H0() + " was not initialized by the time contents of dependent module " + this.this$0.H0() + " were queried");
                        }
                    }
                    Iterable<x> $this$mapTo$iv$iv = $this$forEach$iv;
                    ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (x it : $this$mapTo$iv$iv) {
                        c0 l0 = it.x;
                        if (l0 == null) {
                            k.n();
                        }
                        arrayList.add(l0);
                    }
                    return new i(arrayList);
                }
                throw new AssertionError("Module " + this.this$0.H0() + " is not contained in his own dependencies, this is probably a misconfiguration");
            }
            throw new AssertionError("Dependencies of module " + this.this$0.H0() + " were not set before querying module content");
        }
    }

    /* access modifiers changed from: private */
    public final boolean L0() {
        return this.x != null;
    }

    public final void P0(@NotNull v dependencies) {
        k.f(dependencies, "dependencies");
        if (this.q == null) {
            this.q = dependencies;
            return;
        }
        throw new AssertionError("Dependencies of " + H0() + " were already set");
    }

    public final void Q0(@NotNull x... descriptors) {
        k.f(descriptors, "descriptors");
        N0(kotlin.collections.l.U(descriptors));
    }

    public final void N0(@NotNull List<x> descriptors) {
        k.f(descriptors, "descriptors");
        O0(descriptors, o0.d());
    }

    public final void O0(@NotNull List<x> descriptors, @NotNull Set<x> friends) {
        k.f(descriptors, "descriptors");
        k.f(friends, "friends");
        P0(new w(descriptors, friends, kotlin.collections.q.g()));
    }

    public boolean H(@NotNull y targetModule) {
        k.f(targetModule, "targetModule");
        if (k.a(this, targetModule)) {
            return true;
        }
        v vVar = this.q;
        if (vVar == null) {
            k.n();
        }
        if (!kotlin.collections.y.M(vVar.c(), targetModule) && !u0().contains(targetModule) && !targetModule.u0().contains(this)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final String H0() {
        String fVar = getName().toString();
        k.b(fVar, "name.toString()");
        return fVar;
    }

    public final void K0(@NotNull c0 providerForModuleContent) {
        k.f(providerForModuleContent, "providerForModuleContent");
        if (!L0()) {
            this.x = providerForModuleContent;
            return;
        }
        throw new AssertionError("Attempt to initialize module " + H0() + " twice");
    }

    @NotNull
    public final c0 I0() {
        G0();
        return J0();
    }

    @Nullable
    public <T> T i0(@NotNull y.a<T> capability) {
        k.f(capability, "capability");
        T t = this.f.get(capability);
        if (!(t instanceof Object)) {
            return null;
        }
        return t;
    }
}
