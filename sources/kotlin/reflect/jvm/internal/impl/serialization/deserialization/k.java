package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContractDeserializer.kt */
public interface k {
    public static final a a = a.b;

    @Nullable
    n<a.C0348a<?>, Object> a(@NotNull i iVar, @NotNull u uVar, @NotNull h hVar, @NotNull e0 e0Var);

    /* compiled from: ContractDeserializer.kt */
    public static final class a {
        @NotNull
        private static final k a = new C0420a();
        static final /* synthetic */ a b = new a();

        /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.k$a$a  reason: collision with other inner class name */
        /* compiled from: ContractDeserializer.kt */
        public static final class C0420a implements k {
            C0420a() {
            }

            @Nullable
            public n a(@NotNull i proto, @NotNull u ownerFunction, @NotNull h typeTable, @NotNull e0 typeDeserializer) {
                kotlin.jvm.internal.k.f(proto, "proto");
                kotlin.jvm.internal.k.f(ownerFunction, "ownerFunction");
                kotlin.jvm.internal.k.f(typeTable, "typeTable");
                kotlin.jvm.internal.k.f(typeDeserializer, "typeDeserializer");
                return null;
            }
        }

        private a() {
        }

        @NotNull
        public final k a() {
            return a;
        }
    }
}
