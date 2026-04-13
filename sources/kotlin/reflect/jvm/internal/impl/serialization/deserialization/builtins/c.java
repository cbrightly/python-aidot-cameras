package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.b;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.metadata.m;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.q;
import kotlin.reflect.jvm.internal.impl.storage.j;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;

/* compiled from: BuiltInsPackageFragmentImpl.kt */
public final class c extends q implements b {
    public static final a p3 = new a((DefaultConstructorMarker) null);
    private final boolean p4;

    private c(kotlin.reflect.jvm.internal.impl.name.b fqName, j storageManager, y module, m proto, kotlin.reflect.jvm.internal.impl.metadata.builtins.a metadataVersion, boolean isFallback) {
        super(fqName, storageManager, module, proto, metadataVersion, (e) null);
        this.p4 = isFallback;
    }

    public /* synthetic */ c(kotlin.reflect.jvm.internal.impl.name.b fqName, j storageManager, y module, m proto, kotlin.reflect.jvm.internal.impl.metadata.builtins.a metadataVersion, boolean isFallback, DefaultConstructorMarker $constructor_marker) {
        this(fqName, storageManager, module, proto, metadataVersion, isFallback);
    }

    /* compiled from: BuiltInsPackageFragmentImpl.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final c a(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull j storageManager, @NotNull y module, @NotNull InputStream inputStream, boolean isFallback) {
            Throwable th;
            InputStream inputStream2 = inputStream;
            k.f(fqName, "fqName");
            k.f(storageManager, "storageManager");
            k.f(module, "module");
            k.f(inputStream2, "inputStream");
            InputStream stream = inputStream;
            try {
                kotlin.reflect.jvm.internal.impl.metadata.builtins.a a = kotlin.reflect.jvm.internal.impl.metadata.builtins.a.i.a(stream);
                if (a == null) {
                    try {
                        k.t(ConfigUtil.VERSION_FILE);
                    } catch (Throwable th2) {
                        th = th2;
                        kotlin.reflect.jvm.internal.impl.metadata.builtins.a aVar = a;
                        try {
                            throw th;
                        } catch (Throwable th3) {
                            Throwable th4 = th3;
                            kotlin.io.b.a(inputStream2, th);
                            throw th4;
                        }
                    }
                }
                if (a.g()) {
                    m parseFrom = m.parseFrom(stream, a.n.e());
                    kotlin.io.b.a(inputStream2, (Throwable) null);
                    m proto = parseFrom;
                    k.b(proto, "proto");
                    return new c(fqName, storageManager, module, proto, a, isFallback, (DefaultConstructorMarker) null);
                }
                throw new UnsupportedOperationException("Kotlin built-in definition format version is not supported: " + "expected " + kotlin.reflect.jvm.internal.impl.metadata.builtins.a.g + ", actual " + a + ". " + "Please update Kotlin");
            } catch (Throwable th5) {
                th = th5;
                throw th;
            }
        }
    }
}
