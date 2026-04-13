package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.k0;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinClassHeader.kt */
public final class a {
    @NotNull
    private final C0373a a;
    @NotNull
    private final f b;
    @NotNull
    private final c c;
    @Nullable
    private final String[] d;
    @Nullable
    private final String[] e;
    @Nullable
    private final String[] f;
    private final String g;
    private final int h;
    @Nullable
    private final String i;

    public a(@NotNull C0373a kind, @NotNull f metadataVersion, @NotNull c bytecodeVersion, @Nullable String[] data, @Nullable String[] incompatibleData, @Nullable String[] strings, @Nullable String extraString, int extraInt, @Nullable String packageName) {
        k.f(kind, "kind");
        k.f(metadataVersion, "metadataVersion");
        k.f(bytecodeVersion, "bytecodeVersion");
        this.a = kind;
        this.b = metadataVersion;
        this.c = bytecodeVersion;
        this.d = data;
        this.e = incompatibleData;
        this.f = strings;
        this.g = extraString;
        this.h = extraInt;
        this.i = packageName;
    }

    @NotNull
    public final C0373a c() {
        return this.a;
    }

    @NotNull
    public final f d() {
        return this.b;
    }

    @Nullable
    public final String[] a() {
        return this.d;
    }

    @Nullable
    public final String[] b() {
        return this.e;
    }

    @Nullable
    public final String[] g() {
        return this.f;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.header.a$a  reason: collision with other inner class name */
    /* compiled from: KotlinClassHeader.kt */
    public enum C0373a {
        UNKNOWN(0),
        CLASS(1),
        FILE_FACADE(2),
        SYNTHETIC_CLASS(3),
        MULTIFILE_CLASS(4),
        MULTIFILE_CLASS_PART(5);
        
        public static final C0374a Companion = null;
        /* access modifiers changed from: private */
        public static final Map<Integer, C0373a> d = null;
        private final int id;

        @NotNull
        public static final C0373a getById(int i) {
            return Companion.a(i);
        }

        private C0373a(int id2) {
            this.id = id2;
        }

        static {
            int i;
            Companion = new C0374a((DefaultConstructorMarker) null);
            C0373a[] values = values();
            Map destination$iv$iv = new LinkedHashMap(n.b(k0.b(values.length), 16));
            for (C0373a aVar : values) {
                destination$iv$iv.put(Integer.valueOf(aVar.id), aVar);
            }
            d = destination$iv$iv;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.header.a$a$a  reason: collision with other inner class name */
        /* compiled from: KotlinClassHeader.kt */
        public static final class C0374a {
            private C0374a() {
            }

            public /* synthetic */ C0374a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @NotNull
            public final C0373a a(int id) {
                C0373a aVar = (C0373a) C0373a.d.get(Integer.valueOf(id));
                return aVar != null ? aVar : C0373a.UNKNOWN;
            }
        }
    }

    @Nullable
    public final String e() {
        String str = this.g;
        String str2 = str;
        if (this.a == C0373a.MULTIFILE_CLASS_PART) {
            return str;
        }
        return null;
    }

    @NotNull
    public final List<String> f() {
        String[] strArr = this.d;
        String[] strArr2 = strArr;
        List<String> list = null;
        if (!(this.a == C0373a.MULTIFILE_CLASS)) {
            strArr = null;
        }
        if (strArr != null) {
            list = kotlin.collections.k.c(strArr);
        }
        return list != null ? list : q.g();
    }

    public final boolean h() {
        return (this.h & 2) != 0;
    }

    @NotNull
    public String toString() {
        return this.a + " version=" + this.b;
    }
}
