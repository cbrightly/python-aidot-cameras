package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.builtins.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.load.kotlin.s;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.g;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.a;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.a0;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
public abstract class a<A, C> implements kotlin.reflect.jvm.internal.impl.serialization.deserialization.c<A, C> {
    @NotNull
    private static final Set<kotlin.reflect.jvm.internal.impl.name.a> a;
    public static final C0370a b = new C0370a((DefaultConstructorMarker) null);
    private final kotlin.reflect.jvm.internal.impl.storage.c<p, c<A, C>> c;
    private final n d;

    /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
    public enum b {
        PROPERTY,
        BACKING_FIELD,
        DELEGATE_FIELD
    }

    /* access modifiers changed from: protected */
    @NotNull
    public abstract A B(@NotNull kotlin.reflect.jvm.internal.impl.metadata.b bVar, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract C D(@NotNull C c2);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract p.a w(@NotNull kotlin.reflect.jvm.internal.impl.name.a aVar, @NotNull o0 o0Var, @NotNull List<A> list);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract C z(@NotNull String str, @NotNull Object obj);

    public a(@NotNull j storageManager, @NotNull n kotlinClassFinder) {
        k.f(storageManager, "storageManager");
        k.f(kotlinClassFinder, "kotlinClassFinder");
        this.d = kotlinClassFinder;
        this.c = storageManager.h(new f(this));
    }

    /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
    public static final class f extends l implements kotlin.jvm.functions.l<p, c<? extends A, ? extends C>> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        @NotNull
        public final c<A, C> invoke(@NotNull p kotlinClass) {
            k.f(kotlinClass, "kotlinClass");
            return this.this$0.y(kotlinClass);
        }
    }

    /* access modifiers changed from: private */
    public final p.a x(kotlin.reflect.jvm.internal.impl.name.a annotationClassId, o0 source, List<A> result) {
        if (a.contains(annotationClassId)) {
            return null;
        }
        return w(annotationClassId, source, result);
    }

    private final p C(@NotNull a0.a $this$toBinaryClass) {
        o0 c2 = $this$toBinaryClass.c();
        if (!(c2 instanceof r)) {
            c2 = null;
        }
        r rVar = (r) c2;
        if (rVar != null) {
            return rVar.d();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public byte[] q(@NotNull p kotlinClass) {
        k.f(kotlinClass, "kotlinClass");
        return null;
    }

    @NotNull
    public List<A> b(@NotNull a0.a container) {
        k.f(container, "container");
        p kotlinClass = C(container);
        if (kotlinClass != null) {
            ArrayList result = new ArrayList(1);
            kotlinClass.c(new e(this, result), q(kotlinClass));
            return result;
        }
        throw new IllegalStateException(("Class for loading annotations is not found: " + container.a()).toString());
    }

    /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
    public static final class e implements p.c {
        final /* synthetic */ a a;
        final /* synthetic */ ArrayList b;

        e(a $outer, ArrayList $captured_local_variable$1) {
            this.a = $outer;
            this.b = $captured_local_variable$1;
        }

        @Nullable
        public p.a b(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull o0 source) {
            k.f(classId, "classId");
            k.f(source, "source");
            return this.a.x(classId, source, this.b);
        }

        public void visitEnd() {
        }
    }

    @NotNull
    public List<A> e(@NotNull a0 container, @NotNull o proto, @NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.b kind) {
        a0 a0Var = container;
        o oVar = proto;
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.b bVar = kind;
        k.f(container, "container");
        k.f(proto, "proto");
        k.f(bVar, "kind");
        if (bVar == kotlin.reflect.jvm.internal.impl.serialization.deserialization.b.PROPERTY) {
            return A(container, (n) oVar, b.PROPERTY);
        }
        s signature = s(this, proto, container.b(), container.d(), kind, false, 16, (Object) null);
        if (signature != null) {
            return o(this, container, signature, false, false, (Boolean) null, false, 60, (Object) null);
        }
        return q.g();
    }

    @NotNull
    public List<A> h(@NotNull a0 container, @NotNull n proto) {
        k.f(container, "container");
        k.f(proto, "proto");
        return A(container, proto, b.BACKING_FIELD);
    }

    @NotNull
    public List<A> j(@NotNull a0 container, @NotNull n proto) {
        k.f(container, "container");
        k.f(proto, "proto");
        return A(container, proto, b.DELEGATE_FIELD);
    }

    private final List<A> A(a0 container, n proto, b element) {
        b bVar = element;
        Boolean g = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.w.d(proto.getFlags());
        k.b(g, "Flags.IS_CONST.get(proto.flags)");
        boolean isConst = g.booleanValue();
        boolean isMovedFromInterfaceCompanion = i.f(proto);
        if (bVar == b.PROPERTY) {
            s syntheticFunctionSignature = u(this, proto, container.b(), container.d(), false, true, false, 40, (Object) null);
            if (syntheticFunctionSignature == null) {
                return q.g();
            }
            return o(this, container, syntheticFunctionSignature, true, false, Boolean.valueOf(isConst), isMovedFromInterfaceCompanion, 8, (Object) null);
        }
        s fieldSignature = u(this, proto, container.b(), container.d(), true, false, false, 48, (Object) null);
        if (fieldSignature == null) {
            return q.g();
        }
        boolean z = false;
        boolean isDelegated = x.S(fieldSignature.a(), "$delegate", false, 2, (Object) null);
        if (bVar == b.DELEGATE_FIELD) {
            z = true;
        }
        if (isDelegated != z) {
            return q.g();
        }
        return n(container, fieldSignature, true, true, Boolean.valueOf(isConst), isMovedFromInterfaceCompanion);
    }

    @NotNull
    public List<A> d(@NotNull a0 container, @NotNull g proto) {
        k.f(container, "container");
        k.f(proto, "proto");
        s.a aVar = s.a;
        String string = container.b().getString(proto.getName());
        String c2 = ((a0.a) container).e().c();
        k.b(c2, "(container as ProtoConta…Class).classId.asString()");
        return o(this, container, aVar.a(string, kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.b.a(c2)), false, false, (Boolean) null, false, 60, (Object) null);
    }

    static /* synthetic */ List o(a aVar, a0 a0Var, s sVar, boolean z, boolean z2, Boolean bool, boolean z3, int i, Object obj) {
        boolean z4;
        Boolean bool2;
        boolean z5;
        if (obj == null) {
            boolean z6 = (i & 4) != 0 ? false : z;
            if ((i & 8) != 0) {
                z4 = false;
            } else {
                z4 = z2;
            }
            if ((i & 16) != 0) {
                bool2 = null;
            } else {
                bool2 = bool;
            }
            if ((i & 32) != 0) {
                z5 = false;
            } else {
                z5 = z3;
            }
            return aVar.n(a0Var, sVar, z6, z4, bool2, z5);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findClassAndLoadMemberAnnotations");
    }

    private final List<A> n(a0 container, s signature, boolean property, boolean field, Boolean isConst, boolean isMovedFromInterfaceCompanion) {
        p kotlinClass = p(container, v(container, property, field, isConst, isMovedFromInterfaceCompanion));
        if (kotlinClass == null) {
            return q.g();
        }
        List<A> list = (List) this.c.invoke(kotlinClass).a().get(signature);
        return list != null ? list : q.g();
    }

    @NotNull
    public List<A> a(@NotNull a0 container, @NotNull o callableProto, @NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.b kind, int parameterIndex, @NotNull u proto) {
        k.f(container, "container");
        k.f(callableProto, "callableProto");
        k.f(kind, "kind");
        k.f(proto, "proto");
        s methodSignature = s(this, callableProto, container.b(), container.d(), kind, false, 16, (Object) null);
        if (methodSignature == null) {
            return q.g();
        }
        return o(this, container, s.a.e(methodSignature, parameterIndex + m(container, callableProto)), false, false, (Boolean) null, false, 60, (Object) null);
    }

    private final int m(a0 container, o message) {
        if (message instanceof kotlin.reflect.jvm.internal.impl.metadata.i) {
            return kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.d((kotlin.reflect.jvm.internal.impl.metadata.i) message) ? 1 : 0;
        }
        if (message instanceof n) {
            if (kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.e((n) message)) {
                return 1;
            }
            return 0;
        } else if (!(message instanceof kotlin.reflect.jvm.internal.impl.metadata.d)) {
            throw new UnsupportedOperationException("Unsupported message: " + message.getClass());
        } else if (container == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.ProtoContainer.Class");
        } else if (((a0.a) container).g() == c.C0384c.ENUM_CLASS) {
            return 2;
        } else {
            if (((a0.a) container).i()) {
                return 1;
            }
            return 0;
        }
    }

    @NotNull
    public List<A> i(@NotNull a0 container, @NotNull o proto, @NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.b kind) {
        k.f(container, "container");
        k.f(proto, "proto");
        k.f(kind, "kind");
        s methodSignature = s(this, proto, container.b(), container.d(), kind, false, 16, (Object) null);
        if (methodSignature != null) {
            return o(this, container, s.a.e(methodSignature, 0), false, false, (Boolean) null, false, 60, (Object) null);
        }
        return q.g();
    }

    @NotNull
    public List<A> c(@NotNull kotlin.reflect.jvm.internal.impl.metadata.q proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        k.f(proto, "proto");
        k.f(nameResolver, "nameResolver");
        Object extension = proto.getExtension(kotlin.reflect.jvm.internal.impl.metadata.jvm.a.f);
        k.b(extension, "proto.getExtension(JvmProtoBuf.typeAnnotation)");
        Iterable<kotlin.reflect.jvm.internal.impl.metadata.b> $this$map$iv = (Iterable) extension;
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.metadata.b it : $this$map$iv) {
            k.b(it, "it");
            arrayList.add(B(it, nameResolver));
        }
        return arrayList;
    }

    @NotNull
    public List<A> f(@NotNull kotlin.reflect.jvm.internal.impl.metadata.s proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver) {
        k.f(proto, "proto");
        k.f(nameResolver, "nameResolver");
        Object extension = proto.getExtension(kotlin.reflect.jvm.internal.impl.metadata.jvm.a.h);
        k.b(extension, "proto.getExtension(JvmPr….typeParameterAnnotation)");
        Iterable<kotlin.reflect.jvm.internal.impl.metadata.b> $this$map$iv = (Iterable) extension;
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.metadata.b it : $this$map$iv) {
            k.b(it, "it");
            arrayList.add(B(it, nameResolver));
        }
        return arrayList;
    }

    @Nullable
    public C g(@NotNull a0 container, @NotNull n proto, @NotNull b0 expectedType) {
        s signature;
        Object obj;
        k.f(container, "container");
        k.f(proto, "proto");
        k.f(expectedType, "expectedType");
        p kotlinClass = p(container, v(container, true, true, kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.w.d(proto.getFlags()), i.f(proto)));
        if (kotlinClass == null || (signature = r(proto, container.b(), container.d(), kotlin.reflect.jvm.internal.impl.serialization.deserialization.b.PROPERTY, kotlinClass.b().d().d(e.f.a()))) == null || (obj = this.c.invoke(kotlinClass).b().get(signature)) == null) {
            return null;
        }
        Object constant = obj;
        return m.e.d(expectedType) ? D(constant) : constant;
    }

    private final p p(a0 container, p specialCase) {
        if (specialCase != null) {
            return specialCase;
        }
        if (container instanceof a0.a) {
            return C((a0.a) container);
        }
        return null;
    }

    private final p v(a0 container, boolean property, boolean field, Boolean isConst, boolean isMovedFromInterfaceCompanion) {
        a0.a outerClass;
        if (property) {
            if (isConst == null) {
                throw new IllegalStateException(("isConst should not be null for property (container=" + container + ')').toString());
            } else if ((container instanceof a0.a) && ((a0.a) container).g() == c.C0384c.INTERFACE) {
                n nVar = this.d;
                kotlin.reflect.jvm.internal.impl.name.a d2 = ((a0.a) container).e().d(kotlin.reflect.jvm.internal.impl.name.f.f("DefaultImpls"));
                k.b(d2, "container.classId.create…EFAULT_IMPLS_CLASS_NAME))");
                return o.b(nVar, d2);
            } else if (isConst.booleanValue() && (container instanceof a0.b)) {
                o0 c2 = container.c();
                if (!(c2 instanceof j)) {
                    c2 = null;
                }
                j jVar = (j) c2;
                kotlin.reflect.jvm.internal.impl.resolve.jvm.c facadeClassName = jVar != null ? jVar.e() : null;
                if (facadeClassName != null) {
                    n nVar2 = this.d;
                    String f2 = facadeClassName.f();
                    k.b(f2, "facadeClassName.internalName");
                    kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b(w.G(f2, '/', '.', false, 4, (Object) null)));
                    k.b(m, "ClassId.topLevel(FqName(…lName.replace('/', '.')))");
                    return o.b(nVar2, m);
                }
            }
        }
        if (field && (container instanceof a0.a) && ((a0.a) container).g() == c.C0384c.COMPANION_OBJECT && (outerClass = ((a0.a) container).h()) != null && (outerClass.g() == c.C0384c.CLASS || outerClass.g() == c.C0384c.ENUM_CLASS || (isMovedFromInterfaceCompanion && (outerClass.g() == c.C0384c.INTERFACE || outerClass.g() == c.C0384c.ANNOTATION_CLASS)))) {
            return C(outerClass);
        }
        if (!(container instanceof a0.b) || !(container.c() instanceof j)) {
            return null;
        }
        o0 c3 = container.c();
        if (c3 != null) {
            j jvmPackagePartSource = (j) c3;
            p f3 = jvmPackagePartSource.f();
            return f3 != null ? f3 : o.b(this.d, jvmPackagePartSource.d());
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource");
    }

    /* access modifiers changed from: private */
    public final c<A, C> y(p kotlinClass) {
        HashMap memberAnnotations = new HashMap();
        HashMap propertyConstants = new HashMap();
        kotlinClass.a(new d(this, memberAnnotations, propertyConstants), q(kotlinClass));
        return new c<>(memberAnnotations, propertyConstants);
    }

    /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
    public static final class d implements p.d {
        final /* synthetic */ a a;
        final /* synthetic */ HashMap b;
        final /* synthetic */ HashMap c;

        d(a $outer, HashMap $captured_local_variable$1, HashMap $captured_local_variable$2) {
            this.a = $outer;
            this.b = $captured_local_variable$1;
            this.c = $captured_local_variable$2;
        }

        @Nullable
        public p.e b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull String desc) {
            k.f(name, "name");
            k.f(desc, "desc");
            s.a aVar = s.a;
            String b2 = name.b();
            k.b(b2, "name.asString()");
            return new C0371a(this, aVar.d(b2, desc));
        }

        @Nullable
        public p.c a(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull String desc, @Nullable Object initializer) {
            Object constant;
            k.f(name, "name");
            k.f(desc, "desc");
            s.a aVar = s.a;
            String b2 = name.b();
            k.b(b2, "name.asString()");
            s signature = aVar.a(b2, desc);
            if (!(initializer == null || (constant = this.a.z(desc, initializer)) == null)) {
                this.c.put(signature, constant);
            }
            return new b(this, signature);
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.a$d$a  reason: collision with other inner class name */
        /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
        public final class C0371a extends b implements p.e {
            final /* synthetic */ d d;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0371a(@NotNull d $outer, s signature) {
                super($outer, signature);
                k.f(signature, "signature");
                this.d = $outer;
            }

            @Nullable
            public p.a a(int index, @NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull o0 source) {
                k.f(classId, "classId");
                k.f(source, "source");
                s paramSignature = s.a.e(c(), index);
                List result = (List) this.d.b.get(paramSignature);
                if (result == null) {
                    result = new ArrayList();
                    this.d.b.put(paramSignature, result);
                }
                return this.d.a.x(classId, source, result);
            }
        }

        /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
        public class b implements p.c {
            private final ArrayList<A> a = new ArrayList<>();
            @NotNull
            private final s b;
            final /* synthetic */ d c;

            public b(@NotNull d $outer, s signature) {
                k.f(signature, "signature");
                this.c = $outer;
                this.b = signature;
            }

            /* access modifiers changed from: protected */
            @NotNull
            public final s c() {
                return this.b;
            }

            @Nullable
            public p.a b(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, @NotNull o0 source) {
                k.f(classId, "classId");
                k.f(source, "source");
                return this.c.a.x(classId, source, this.a);
            }

            public void visitEnd() {
                if (!this.a.isEmpty()) {
                    this.c.b.put(this.b, this.a);
                }
            }
        }
    }

    static /* synthetic */ s u(a aVar, n nVar, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar, h hVar, boolean z, boolean z2, boolean z3, int i, Object obj) {
        boolean z4;
        boolean z5;
        if (obj == null) {
            boolean z6 = (i & 8) != 0 ? false : z;
            if ((i & 16) != 0) {
                z4 = false;
            } else {
                z4 = z2;
            }
            if ((i & 32) != 0) {
                z5 = true;
            } else {
                z5 = z3;
            }
            return aVar.t(nVar, cVar, hVar, z6, z4, z5);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getPropertySignature");
    }

    private final s t(n proto, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, h typeTable, boolean field, boolean synthetic, boolean requireHasFieldFlagForField) {
        h.f<n, a.d> fVar = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.d;
        k.b(fVar, "propertySignature");
        a.d signature = (a.d) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a(proto, fVar);
        if (signature == null) {
            return null;
        }
        if (field) {
            e.a fieldSignature = i.b.c(proto, nameResolver, typeTable, requireHasFieldFlagForField);
            if (fieldSignature == null) {
                return null;
            }
            return s.a.b(fieldSignature);
        } else if (!synthetic || !signature.hasSyntheticMethod()) {
            return null;
        } else {
            s.a aVar = s.a;
            a.c syntheticMethod = signature.getSyntheticMethod();
            k.b(syntheticMethod, "signature.syntheticMethod");
            return aVar.c(nameResolver, syntheticMethod);
        }
    }

    static /* synthetic */ s s(a aVar, o oVar, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar, kotlin.reflect.jvm.internal.impl.metadata.deserialization.h hVar, kotlin.reflect.jvm.internal.impl.serialization.deserialization.b bVar, boolean z, int i, Object obj) {
        if (obj == null) {
            return aVar.r(oVar, cVar, hVar, bVar, (i & 16) != 0 ? false : z);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCallableSignature");
    }

    private final s r(o proto, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, kotlin.reflect.jvm.internal.impl.metadata.deserialization.h typeTable, kotlin.reflect.jvm.internal.impl.serialization.deserialization.b kind, boolean requireHasFieldFlagForField) {
        if (proto instanceof kotlin.reflect.jvm.internal.impl.metadata.d) {
            s.a aVar = s.a;
            e.b b2 = i.b.b((kotlin.reflect.jvm.internal.impl.metadata.d) proto, nameResolver, typeTable);
            if (b2 != null) {
                return aVar.b(b2);
            }
            return null;
        } else if (proto instanceof kotlin.reflect.jvm.internal.impl.metadata.i) {
            s.a aVar2 = s.a;
            e.b e2 = i.b.e((kotlin.reflect.jvm.internal.impl.metadata.i) proto, nameResolver, typeTable);
            if (e2 != null) {
                return aVar2.b(e2);
            }
            return null;
        } else if (!(proto instanceof n)) {
            return null;
        } else {
            h.f<n, a.d> fVar = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.d;
            k.b(fVar, "propertySignature");
            a.d signature = (a.d) kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a((h.d) proto, fVar);
            if (signature == null) {
                return null;
            }
            switch (b.a[kind.ordinal()]) {
                case 1:
                    if (!signature.hasGetter()) {
                        return null;
                    }
                    s.a aVar3 = s.a;
                    a.c getter = signature.getGetter();
                    k.b(getter, "signature.getter");
                    return aVar3.c(nameResolver, getter);
                case 2:
                    if (!signature.hasSetter()) {
                        return null;
                    }
                    s.a aVar4 = s.a;
                    a.c setter = signature.getSetter();
                    k.b(setter, "signature.setter");
                    return aVar4.c(nameResolver, setter);
                case 3:
                    return t((n) proto, nameResolver, typeTable, true, true, requireHasFieldFlagForField);
                default:
                    return null;
            }
        }
    }

    /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
    public static final class c<A, C> {
        @NotNull
        private final Map<s, List<A>> a;
        @NotNull
        private final Map<s, C> b;

        public c(@NotNull Map<s, ? extends List<? extends A>> memberAnnotations, @NotNull Map<s, ? extends C> propertyConstants) {
            k.f(memberAnnotations, "memberAnnotations");
            k.f(propertyConstants, "propertyConstants");
            this.a = memberAnnotations;
            this.b = propertyConstants;
        }

        @NotNull
        public final Map<s, List<A>> a() {
            return this.a;
        }

        @NotNull
        public final Map<s, C> b() {
            return this.b;
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.kotlin.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
    public static final class C0370a {
        private C0370a() {
        }

        public /* synthetic */ C0370a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        Iterable<kotlin.reflect.jvm.internal.impl.name.b> $this$mapTo$iv$iv = q.j(kotlin.reflect.jvm.internal.impl.load.java.s.a, kotlin.reflect.jvm.internal.impl.load.java.s.d, kotlin.reflect.jvm.internal.impl.load.java.s.e, new kotlin.reflect.jvm.internal.impl.name.b("java.lang.annotation.Target"), new kotlin.reflect.jvm.internal.impl.name.b("java.lang.annotation.Retention"), new kotlin.reflect.jvm.internal.impl.name.b("java.lang.annotation.Documented"));
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.name.b p1 : $this$mapTo$iv$iv) {
            destination$iv$iv.add(kotlin.reflect.jvm.internal.impl.name.a.m(p1));
        }
        a = y.H0(destination$iv$iv);
    }
}
