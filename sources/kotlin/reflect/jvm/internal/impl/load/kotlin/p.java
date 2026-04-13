package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinJvmBinaryClass.kt */
public interface p {

    /* compiled from: KotlinJvmBinaryClass.kt */
    public interface a {
        void a(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.name.a aVar, @NotNull f fVar2);

        @Nullable
        a b(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.name.a aVar);

        void c(@NotNull f fVar, @NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f fVar2);

        void d(@Nullable f fVar, @Nullable Object obj);

        @Nullable
        b e(@NotNull f fVar);

        void visitEnd();
    }

    /* compiled from: KotlinJvmBinaryClass.kt */
    public interface b {
        void a(@Nullable Object obj);

        void b(@NotNull kotlin.reflect.jvm.internal.impl.name.a aVar, @NotNull f fVar);

        void c(@NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f fVar);

        void visitEnd();
    }

    /* compiled from: KotlinJvmBinaryClass.kt */
    public interface c {
        @Nullable
        a b(@NotNull kotlin.reflect.jvm.internal.impl.name.a aVar, @NotNull o0 o0Var);

        void visitEnd();
    }

    /* compiled from: KotlinJvmBinaryClass.kt */
    public interface d {
        @Nullable
        c a(@NotNull f fVar, @NotNull String str, @Nullable Object obj);

        @Nullable
        e b(@NotNull f fVar, @NotNull String str);
    }

    /* compiled from: KotlinJvmBinaryClass.kt */
    public interface e extends c {
        @Nullable
        a a(int i, @NotNull kotlin.reflect.jvm.internal.impl.name.a aVar, @NotNull o0 o0Var);
    }

    void a(@NotNull d dVar, @Nullable byte[] bArr);

    @NotNull
    kotlin.reflect.jvm.internal.impl.load.kotlin.header.a b();

    void c(@NotNull c cVar, @Nullable byte[] bArr);

    @NotNull
    kotlin.reflect.jvm.internal.impl.name.a d();

    @NotNull
    String getLocation();
}
