package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: KotlinTypeRefiner.kt */
public abstract class i {
    @Nullable
    public abstract e a(@NotNull kotlin.reflect.jvm.internal.impl.name.a aVar);

    @NotNull
    public abstract <S extends h> S b(@NotNull e eVar, @NotNull kotlin.jvm.functions.a<? extends S> aVar);

    public abstract boolean c(@NotNull y yVar);

    public abstract boolean d(@NotNull u0 u0Var);

    @Nullable
    public abstract kotlin.reflect.jvm.internal.impl.descriptors.h e(@NotNull m mVar);

    @NotNull
    public abstract Collection<b0> f(@NotNull e eVar);

    @NotNull
    public abstract b0 g(@NotNull b0 b0Var);

    /* compiled from: KotlinTypeRefiner.kt */
    public static final class a extends i {
        public static final a a = new a();

        private a() {
        }

        @NotNull
        public b0 g(@NotNull b0 type) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            return type;
        }

        @NotNull
        public Collection<b0> f(@NotNull e classDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            u0 i = classDescriptor.i();
            k.b(i, "classDescriptor.typeConstructor");
            Collection<b0> b = i.b();
            k.b(b, "classDescriptor.typeConstructor.supertypes");
            return b;
        }

        @Nullable
        /* renamed from: h */
        public e e(@NotNull m descriptor) {
            k.f(descriptor, "descriptor");
            return null;
        }

        @Nullable
        public e a(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
            k.f(classId, "classId");
            return null;
        }

        public boolean c(@NotNull y moduleDescriptor) {
            k.f(moduleDescriptor, "moduleDescriptor");
            return false;
        }

        public boolean d(@NotNull u0 typeConstructor) {
            k.f(typeConstructor, "typeConstructor");
            return false;
        }

        @NotNull
        public <S extends h> S b(@NotNull e classDescriptor, @NotNull kotlin.jvm.functions.a<? extends S> compute) {
            k.f(classDescriptor, "classDescriptor");
            k.f(compute, "compute");
            return (h) compute.invoke();
        }
    }
}
