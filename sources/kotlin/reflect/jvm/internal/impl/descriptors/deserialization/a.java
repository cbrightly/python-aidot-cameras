package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import java.util.Collection;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdditionalClassPartsProvider.kt */
public interface a {
    @NotNull
    Collection<n0> a(@NotNull f fVar, @NotNull e eVar);

    @NotNull
    Collection<d> c(@NotNull e eVar);

    @NotNull
    Collection<b0> d(@NotNull e eVar);

    @NotNull
    Collection<f> e(@NotNull e eVar);

    /* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a$a  reason: collision with other inner class name */
    /* compiled from: AdditionalClassPartsProvider.kt */
    public static final class C0350a implements a {
        public static final C0350a a = new C0350a();

        private C0350a() {
        }

        @NotNull
        public Collection<b0> d(@NotNull e classDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            return q.g();
        }

        @NotNull
        public Collection<n0> a(@NotNull f name, @NotNull e classDescriptor) {
            k.f(name, "name");
            k.f(classDescriptor, "classDescriptor");
            return q.g();
        }

        @NotNull
        public Collection<f> e(@NotNull e classDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            return q.g();
        }

        @NotNull
        public Collection<d> c(@NotNull e classDescriptor) {
            k.f(classDescriptor, "classDescriptor");
            return q.g();
        }
    }
}
