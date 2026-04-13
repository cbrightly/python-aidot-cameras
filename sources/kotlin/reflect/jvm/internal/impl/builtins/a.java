package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ServiceLoader;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: BuiltInsLoader.kt */
public interface a {
    public static final C0341a a = C0341a.b;

    @NotNull
    c0 a(@NotNull j jVar, @NotNull y yVar, @NotNull Iterable<? extends b> iterable, @NotNull c cVar, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a aVar, boolean z);

    /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.a$a  reason: collision with other inner class name */
    /* compiled from: BuiltInsLoader.kt */
    public static final class C0341a {
        @NotNull
        private static final g a = i.a(k.PUBLICATION, C0342a.INSTANCE);
        static final /* synthetic */ C0341a b = new C0341a();

        @NotNull
        public final a a() {
            return (a) a.getValue();
        }

        private C0341a() {
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.a$a$a  reason: collision with other inner class name */
        /* compiled from: BuiltInsLoader.kt */
        public static final class C0342a extends l implements kotlin.jvm.functions.a<a> {
            public static final C0342a INSTANCE = new C0342a();

            C0342a() {
                super(0);
            }

            @NotNull
            public final a invoke() {
                Class<a> cls = a.class;
                ServiceLoader implementations = ServiceLoader.load(cls, cls.getClassLoader());
                kotlin.jvm.internal.k.b(implementations, "implementations");
                a aVar = (a) kotlin.collections.y.T(implementations);
                if (aVar != null) {
                    return aVar;
                }
                throw new IllegalStateException("No BuiltInsLoader implementation was found. Please ensure that the META-INF/services/ is not stripped from your application and that the Java virtual machine is not running under a security manager");
            }
        }
    }
}
