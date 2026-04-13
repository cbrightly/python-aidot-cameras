package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.g;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.protobuf.w;

/* compiled from: GeneratedMessageLite */
public abstract class h extends a implements Serializable {
    public abstract /* synthetic */ o getDefaultInstanceForType();

    public abstract /* synthetic */ int getSerializedSize();

    public abstract /* synthetic */ boolean isInitialized();

    public abstract /* synthetic */ o.a newBuilderForType();

    public abstract /* synthetic */ o.a toBuilder();

    public abstract /* synthetic */ void writeTo(CodedOutputStream codedOutputStream);

    protected h() {
    }

    protected h(b builder) {
    }

    public q<? extends o> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    /* access modifiers changed from: protected */
    public boolean parseUnknownField(e input, CodedOutputStream unknownFieldsCodedOutput, f extensionRegistry, int tag) {
        return input.P(tag, unknownFieldsCodedOutput);
    }

    /* access modifiers changed from: protected */
    public void makeExtensionsImmutable() {
    }

    /* compiled from: GeneratedMessageLite */
    public static abstract class b<MessageType extends h, BuilderType extends b> extends a.C0398a<BuilderType> {
        private d c = d.c;

        public abstract BuilderType e(MessageType messagetype);

        protected b() {
        }

        /* renamed from: c */
        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        public final d d() {
            return this.c;
        }

        public final BuilderType f(d unknownFields) {
            this.c = unknownFields;
            return this;
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static abstract class d<MessageType extends d<MessageType>> extends h implements p {
        /* access modifiers changed from: private */
        public final g<e> extensions;

        public abstract /* synthetic */ o getDefaultInstanceForType();

        public abstract /* synthetic */ int getSerializedSize();

        public abstract /* synthetic */ boolean isInitialized();

        public abstract /* synthetic */ o.a newBuilderForType();

        public abstract /* synthetic */ o.a toBuilder();

        public abstract /* synthetic */ void writeTo(CodedOutputStream codedOutputStream);

        protected d() {
            this.extensions = g.t();
        }

        protected d(c<MessageType, ?> builder) {
            this.extensions = builder.i();
        }

        private void b(f<MessageType, ?> extension) {
            if (extension.b() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> boolean hasExtension(f<MessageType, Type> extension) {
            b(extension);
            return this.extensions.m(extension.d);
        }

        public final <Type> int getExtensionCount(f<MessageType, List<Type>> extension) {
            b(extension);
            return this.extensions.j(extension.d);
        }

        public final <Type> Type getExtension(f<MessageType, Type> extension) {
            b(extension);
            Object value = this.extensions.h(extension.d);
            if (value == null) {
                return extension.b;
            }
            return extension.a(value);
        }

        public final <Type> Type getExtension(f<MessageType, List<Type>> extension, int index) {
            b(extension);
            return extension.e(this.extensions.i(extension.d, index));
        }

        /* access modifiers changed from: protected */
        public boolean extensionsAreInitialized() {
            return this.extensions.n();
        }

        /* access modifiers changed from: protected */
        public boolean parseUnknownField(e input, CodedOutputStream unknownFieldsCodedOutput, f extensionRegistry, int tag) {
            return h.a(this.extensions, getDefaultInstanceForType(), input, unknownFieldsCodedOutput, extensionRegistry, tag);
        }

        /* access modifiers changed from: protected */
        public void makeExtensionsImmutable() {
            this.extensions.q();
        }

        /* compiled from: GeneratedMessageLite */
        public class a {
            private final Iterator<Map.Entry<e, Object>> a;
            private Map.Entry<e, Object> b;
            private final boolean c;

            /* synthetic */ a(d x0, boolean x1, a x2) {
                this(x1);
            }

            private a(boolean messageSetWireFormat) {
                Iterator<Map.Entry<e, Object>> p = d.this.extensions.p();
                this.a = p;
                if (p.hasNext()) {
                    this.b = p.next();
                }
                this.c = messageSetWireFormat;
            }

            public void a(int end, CodedOutputStream output) {
                while (true) {
                    Map.Entry<e, Object> entry = this.b;
                    if (entry != null && entry.getKey().getNumber() < end) {
                        e extension = this.b.getKey();
                        if (!this.c || extension.E() != w.c.MESSAGE || extension.r()) {
                            g.z(extension, this.b.getValue(), output);
                        } else {
                            output.f0(extension.getNumber(), (o) this.b.getValue());
                        }
                        if (this.a.hasNext()) {
                            this.b = this.a.next();
                        } else {
                            this.b = null;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public d<MessageType>.defpackage.a newExtensionWriter() {
            return new a(this, false, (a) null);
        }

        /* access modifiers changed from: protected */
        public int extensionsSerializedSize() {
            return this.extensions.k();
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static abstract class c<MessageType extends d<MessageType>, BuilderType extends c<MessageType, BuilderType>> extends b<MessageType, BuilderType> implements p {
        private g<e> d = g.g();
        private boolean f;

        protected c() {
        }

        private void j() {
            if (!this.f) {
                this.d = this.d.clone();
                this.f = true;
            }
        }

        /* access modifiers changed from: private */
        public g<e> i() {
            this.d.q();
            this.f = false;
            return this.d;
        }

        /* access modifiers changed from: protected */
        public boolean k() {
            return this.d.n();
        }

        /* access modifiers changed from: protected */
        public final void l(MessageType other) {
            j();
            this.d.r(other.extensions);
        }
    }

    /* access modifiers changed from: private */
    public static <MessageType extends o> boolean a(g<e> extensions, MessageType defaultInstance, e input, CodedOutputStream unknownFieldsCodedOutput, f extensionRegistry, int tag) {
        Object value;
        o existingValue;
        g<e> gVar = extensions;
        e eVar = input;
        CodedOutputStream codedOutputStream = unknownFieldsCodedOutput;
        f fVar = extensionRegistry;
        int i = tag;
        int wireType = w.b(tag);
        GeneratedMessageLite.GeneratedExtension<MessageType, ?> extension = fVar.b(defaultInstance, w.a(tag));
        boolean unknown = false;
        boolean packed = false;
        boolean z = true;
        if (extension == null) {
            unknown = true;
        } else if (wireType == g.l(extension.d.s(), false)) {
            packed = false;
        } else {
            e eVar2 = extension.d;
            if (!eVar2.q || !eVar2.f.isPackable() || wireType != g.l(extension.d.s(), true)) {
                unknown = true;
            } else {
                packed = true;
            }
        }
        if (unknown) {
            return eVar.P(i, codedOutputStream);
        }
        if (packed) {
            int limit = eVar.j(input.A());
            if (extension.d.s() == w.b.ENUM) {
                while (input.e() > 0) {
                    Object value2 = extension.d.b().a(input.n());
                    if (value2 == null) {
                        return z;
                    }
                    gVar.a(extension.d, extension.f(value2));
                    wireType = wireType;
                    z = true;
                }
            } else {
                while (input.e() > 0) {
                    gVar.a(extension.d, g.u(eVar, extension.d.s(), false));
                }
            }
            eVar.i(limit);
            return true;
        }
        switch (a.a[extension.d.E().ordinal()]) {
            case 1:
                o.a subBuilder = null;
                if (!extension.d.r() && (existingValue = (o) gVar.h(extension.d)) != null) {
                    subBuilder = existingValue.toBuilder();
                }
                if (subBuilder == null) {
                    subBuilder = extension.c().newBuilderForType();
                }
                if (extension.d.s() == w.b.GROUP) {
                    eVar.r(extension.d(), subBuilder, fVar);
                } else {
                    eVar.v(subBuilder, fVar);
                }
                value = subBuilder.build();
                break;
            case 2:
                int rawValue = input.n();
                value = extension.d.b().a(rawValue);
                if (value == null) {
                    codedOutputStream.o0(i);
                    codedOutputStream.y0(rawValue);
                    return true;
                }
                break;
            default:
                value = g.u(eVar, extension.d.s(), false);
                break;
        }
        if (extension.d.r()) {
            gVar.a(extension.d, extension.f(value));
            return true;
        }
        gVar.v(extension.d, extension.f(value));
        return true;
    }

    /* compiled from: GeneratedMessageLite */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[w.c.values().length];
            a = iArr;
            try {
                iArr[w.c.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[w.c.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static <ContainingType extends o, Type> f<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, o messageDefaultInstance, i.b<?> enumTypeMap, int number, w.b type, Class singularType) {
        return new f(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new e(enumTypeMap, number, type, false, false), singularType);
    }

    public static <ContainingType extends o, Type> f<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingTypeDefaultInstance, o messageDefaultInstance, i.b<?> enumTypeMap, int number, w.b type, boolean isPacked, Class singularType) {
        return new f(containingTypeDefaultInstance, Collections.emptyList(), messageDefaultInstance, new e(enumTypeMap, number, type, true, isPacked), singularType);
    }

    /* compiled from: GeneratedMessageLite */
    public static final class e implements g.b<e> {
        final i.b<?> c;
        final int d;
        final w.b f;
        final boolean q;
        final boolean x;

        e(i.b<?> enumTypeMap, int number, w.b type, boolean isRepeated, boolean isPacked) {
            this.c = enumTypeMap;
            this.d = number;
            this.f = type;
            this.q = isRepeated;
            this.x = isPacked;
        }

        public int getNumber() {
            return this.d;
        }

        public w.b s() {
            return this.f;
        }

        public w.c E() {
            return this.f.getJavaType();
        }

        public boolean r() {
            return this.q;
        }

        public boolean J() {
            return this.x;
        }

        public i.b<?> b() {
            return this.c;
        }

        public o.a n(o.a to, o from) {
            return ((b) to).e((h) from);
        }

        /* renamed from: a */
        public int compareTo(e other) {
            return this.d - other.d;
        }
    }

    static Method getMethodOrDie(Class clazz, String name, Class... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e2) {
            String valueOf = String.valueOf(String.valueOf(clazz.getName()));
            String valueOf2 = String.valueOf(String.valueOf(name));
            StringBuilder sb = new StringBuilder(valueOf.length() + 45 + valueOf2.length());
            sb.append("Generated message class \"");
            sb.append(valueOf);
            sb.append("\" missing method \"");
            sb.append(valueOf2);
            sb.append("\".");
            throw new RuntimeException(sb.toString(), e2);
        }
    }

    static Object invokeOrDie(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static class f<ContainingType extends o, Type> {
        final ContainingType a;
        final Type b;
        final o c;
        final e d;
        final Class e;
        final Method f;

        f(ContainingType containingTypeDefaultInstance, Type defaultValue, o messageDefaultInstance, e descriptor, Class singularType) {
            if (containingTypeDefaultInstance == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (descriptor.s() == w.b.MESSAGE && messageDefaultInstance == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.a = containingTypeDefaultInstance;
                this.b = defaultValue;
                this.c = messageDefaultInstance;
                this.d = descriptor;
                this.e = singularType;
                if (i.a.class.isAssignableFrom(singularType)) {
                    this.f = h.getMethodOrDie(singularType, "valueOf", Integer.TYPE);
                    return;
                }
                this.f = null;
            }
        }

        public ContainingType b() {
            return this.a;
        }

        public int d() {
            return this.d.getNumber();
        }

        public o c() {
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public Object a(Object value) {
            if (!this.d.r()) {
                return e(value);
            }
            if (this.d.E() != w.c.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(e(element));
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public Object e(Object value) {
            if (this.d.E() != w.c.ENUM) {
                return value;
            }
            return h.invokeOrDie(this.f, (Object) null, (Integer) value);
        }

        /* access modifiers changed from: package-private */
        public Object f(Object value) {
            if (this.d.E() == w.c.ENUM) {
                return Integer.valueOf(((i.a) value).getNumber());
            }
            return value;
        }
    }
}
