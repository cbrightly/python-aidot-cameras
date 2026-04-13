package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.load.java.s;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.a;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
public class b implements p.c {
    private static final boolean a = "true".equals(System.getProperty("kotlin.ignore.old.metadata"));
    private static final Map<kotlin.reflect.jvm.internal.impl.name.a, a.C0373a> b;
    /* access modifiers changed from: private */
    public int[] c = null;
    /* access modifiers changed from: private */
    public kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c d = null;
    /* access modifiers changed from: private */
    public String e = null;
    /* access modifiers changed from: private */
    public int f = 0;
    /* access modifiers changed from: private */
    public String g = null;
    /* access modifiers changed from: private */
    public String[] h = null;
    /* access modifiers changed from: private */
    public String[] i = null;
    private String[] j = null;
    /* access modifiers changed from: private */
    public a.C0373a k = null;

    private static /* synthetic */ void c(int i2) {
        Object[] objArr = new Object[3];
        switch (i2) {
            case 1:
                objArr[0] = "source";
                break;
            default:
                objArr[0] = "classId";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor";
        objArr[2] = "visitAnnotation";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static {
        HashMap hashMap = new HashMap();
        b = hashMap;
        hashMap.put(kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.internal.KotlinClass")), a.C0373a.CLASS);
        hashMap.put(kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.internal.KotlinFileFacade")), a.C0373a.FILE_FACADE);
        hashMap.put(kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.internal.KotlinMultifileClass")), a.C0373a.MULTIFILE_CLASS);
        hashMap.put(kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.internal.KotlinMultifileClassPart")), a.C0373a.MULTIFILE_CLASS_PART);
        hashMap.put(kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b("kotlin.jvm.internal.KotlinSyntheticClass")), a.C0373a.SYNTHETIC_CLASS);
    }

    @Nullable
    public a m() {
        if (this.k == null || this.c == null) {
            return null;
        }
        f metadataVersion = new f(this.c, (this.f & 8) != 0);
        if (!metadataVersion.g()) {
            this.j = this.h;
            this.h = null;
        } else if (n() && this.h == null) {
            return null;
        }
        a.C0373a aVar = this.k;
        kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c cVar = this.d;
        if (cVar == null) {
            cVar = kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c.h;
        }
        return new a(aVar, metadataVersion, cVar, this.h, this.j, this.i, this.e, this.f, this.g);
    }

    private boolean n() {
        a.C0373a aVar = this.k;
        return aVar == a.C0373a.CLASS || aVar == a.C0373a.FILE_FACADE || aVar == a.C0373a.MULTIFILE_CLASS_PART;
    }

    @Nullable
    public p.a b(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull o0 source) {
        a.C0373a newKind;
        if (classId == null) {
            c(0);
        }
        if (source == null) {
            c(1);
        }
        if (classId.b().equals(s.a)) {
            return new c();
        }
        if (a || this.k != null || (newKind = b.get(classId)) == null) {
            return null;
        }
        this.k = newKind;
        return new d();
    }

    public void visitEnd() {
    }

    /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
    public class c implements p.a {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "classLiteralValue";
                    break;
                case 4:
                    objArr[0] = "enumClassId";
                    break;
                case 5:
                    objArr[0] = "enumEntryName";
                    break;
                case 7:
                    objArr[0] = "classId";
                    break;
                default:
                    objArr[0] = "name";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$KotlinMetadataArgumentVisitor";
            switch (i) {
                case 2:
                    objArr[2] = "visitArray";
                    break;
                case 3:
                case 4:
                case 5:
                    objArr[2] = "visitEnum";
                    break;
                case 6:
                case 7:
                    objArr[2] = "visitAnnotation";
                    break;
                default:
                    objArr[2] = "visitClassLiteral";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private c() {
        }

        public void d(@Nullable kotlin.reflect.jvm.internal.impl.name.f name, @Nullable Object value) {
            if (name != null) {
                String string = name.b();
                if ("k".equals(string)) {
                    if (value instanceof Integer) {
                        a.C0373a unused = b.this.k = a.C0373a.getById(((Integer) value).intValue());
                    }
                } else if ("mv".equals(string)) {
                    if (value instanceof int[]) {
                        int[] unused2 = b.this.c = (int[]) value;
                    }
                } else if ("bv".equals(string)) {
                    if (value instanceof int[]) {
                        kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c unused3 = b.this.d = new kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c((int[]) value);
                    }
                } else if ("xs".equals(string)) {
                    if (value instanceof String) {
                        String unused4 = b.this.e = (String) value;
                    }
                } else if ("xi".equals(string)) {
                    if (value instanceof Integer) {
                        int unused5 = b.this.f = ((Integer) value).intValue();
                    }
                } else if ("pn".equals(string) && (value instanceof String)) {
                    String unused6 = b.this.g = (String) value;
                }
            }
        }

        public void c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f classLiteralValue) {
            if (name == null) {
                f(0);
            }
            if (classLiteralValue == null) {
                f(1);
            }
        }

        @Nullable
        public p.b e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            if (name == null) {
                f(2);
            }
            String string = name.b();
            if ("d1".equals(string)) {
                return g();
            }
            if ("d2".equals(string)) {
                return h();
            }
            return null;
        }

        /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
        public class a extends C0375b {
            private static /* synthetic */ void d(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"result", "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$KotlinMetadataArgumentVisitor$1", "visitEnd"}));
            }

            a() {
            }

            /* access modifiers changed from: protected */
            public void e(@NotNull String[] result) {
                if (result == null) {
                    d(0);
                }
                String[] unused = b.this.h = result;
            }
        }

        @NotNull
        private p.b g() {
            return new a();
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.header.b$c$b  reason: collision with other inner class name */
        /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
        public class C0376b extends C0375b {
            private static /* synthetic */ void d(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"result", "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$KotlinMetadataArgumentVisitor$2", "visitEnd"}));
            }

            C0376b() {
            }

            /* access modifiers changed from: protected */
            public void e(@NotNull String[] result) {
                if (result == null) {
                    d(0);
                }
                String[] unused = b.this.i = result;
            }
        }

        @NotNull
        private p.b h() {
            return new C0376b();
        }

        public void a(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.name.a enumClassId, @NotNull kotlin.reflect.jvm.internal.impl.name.f enumEntryName) {
            if (name == null) {
                f(3);
            }
            if (enumClassId == null) {
                f(4);
            }
            if (enumEntryName == null) {
                f(5);
            }
        }

        @Nullable
        public p.a b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
            if (name == null) {
                f(6);
            }
            if (classId != null) {
                return null;
            }
            f(7);
            return null;
        }

        public void visitEnd() {
        }
    }

    /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
    public class d implements p.a {
        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "classLiteralValue";
                    break;
                case 4:
                    objArr[0] = "enumClassId";
                    break;
                case 5:
                    objArr[0] = "enumEntryName";
                    break;
                case 7:
                    objArr[0] = "classId";
                    break;
                default:
                    objArr[0] = "name";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$OldDeprecatedAnnotationArgumentVisitor";
            switch (i) {
                case 2:
                    objArr[2] = "visitArray";
                    break;
                case 3:
                case 4:
                case 5:
                    objArr[2] = "visitEnum";
                    break;
                case 6:
                case 7:
                    objArr[2] = "visitAnnotation";
                    break;
                default:
                    objArr[2] = "visitClassLiteral";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private d() {
        }

        public void d(@Nullable kotlin.reflect.jvm.internal.impl.name.f name, @Nullable Object value) {
            if (name != null) {
                String string = name.b();
                if (ConfigUtil.VERSION_FILE.equals(string)) {
                    if (value instanceof int[]) {
                        int[] unused = b.this.c = (int[]) value;
                        if (b.this.d == null) {
                            kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c unused2 = b.this.d = new kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.c((int[]) value);
                        }
                    }
                } else if ("multifileClassName".equals(string)) {
                    String unused3 = b.this.e = value instanceof String ? (String) value : null;
                }
            }
        }

        public void c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f classLiteralValue) {
            if (name == null) {
                f(0);
            }
            if (classLiteralValue == null) {
                f(1);
            }
        }

        @Nullable
        public p.b e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name) {
            if (name == null) {
                f(2);
            }
            String string = name.b();
            if ("data".equals(string) || "filePartClassNames".equals(string)) {
                return g();
            }
            if ("strings".equals(string)) {
                return h();
            }
            return null;
        }

        /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
        public class a extends C0375b {
            private static /* synthetic */ void d(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"data", "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$OldDeprecatedAnnotationArgumentVisitor$1", "visitEnd"}));
            }

            a() {
            }

            /* access modifiers changed from: protected */
            public void e(@NotNull String[] data) {
                if (data == null) {
                    d(0);
                }
                String[] unused = b.this.h = data;
            }
        }

        @NotNull
        private p.b g() {
            return new a();
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.header.b$d$b  reason: collision with other inner class name */
        /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
        public class C0377b extends C0375b {
            private static /* synthetic */ void d(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"data", "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$OldDeprecatedAnnotationArgumentVisitor$2", "visitEnd"}));
            }

            C0377b() {
            }

            /* access modifiers changed from: protected */
            public void e(@NotNull String[] data) {
                if (data == null) {
                    d(0);
                }
                String[] unused = b.this.i = data;
            }
        }

        @NotNull
        private p.b h() {
            return new C0377b();
        }

        public void a(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.name.a enumClassId, @NotNull kotlin.reflect.jvm.internal.impl.name.f enumEntryName) {
            if (name == null) {
                f(3);
            }
            if (enumClassId == null) {
                f(4);
            }
            if (enumEntryName == null) {
                f(5);
            }
        }

        @Nullable
        public p.a b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
            if (name == null) {
                f(6);
            }
            if (classId != null) {
                return null;
            }
            f(7);
            return null;
        }

        public void visitEnd() {
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.header.b$b  reason: collision with other inner class name */
    /* compiled from: ReadKotlinClassHeaderAnnotationVisitor */
    public static abstract class C0375b implements p.b {
        private final List<String> a = new ArrayList();

        private static /* synthetic */ void d(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "enumEntryName";
                    break;
                case 2:
                    objArr[0] = "classLiteralValue";
                    break;
                default:
                    objArr[0] = "enumClassId";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor$CollectStringArrayAnnotationVisitor";
            switch (i) {
                case 2:
                    objArr[2] = "visitClassLiteral";
                    break;
                default:
                    objArr[2] = "visitEnum";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* access modifiers changed from: protected */
        public abstract void e(@NotNull String[] strArr);

        public void a(@Nullable Object value) {
            if (value instanceof String) {
                this.a.add((String) value);
            }
        }

        public void b(@NotNull kotlin.reflect.jvm.internal.impl.name.a enumClassId, @NotNull kotlin.reflect.jvm.internal.impl.name.f enumEntryName) {
            if (enumClassId == null) {
                d(0);
            }
            if (enumEntryName == null) {
                d(1);
            }
        }

        public void c(@NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.f classLiteralValue) {
            if (classLiteralValue == null) {
                d(2);
            }
        }

        public void visitEnd() {
            e((String[]) this.a.toArray(new String[0]));
        }
    }
}
