package kotlin.reflect.jvm.internal;

import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.c;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.load.java.r;
import kotlin.reflect.jvm.internal.impl.load.kotlin.j;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.f;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.a;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.name.g;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RuntimeTypeMapper.kt */
public abstract class d {
    @NotNull
    public abstract String a();

    private d() {
    }

    public /* synthetic */ d(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class c extends d {
        private final String a;
        @NotNull
        private final i0 b;
        @NotNull
        private final n c;
        @NotNull
        private final a.d d;
        @NotNull
        private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.c e;
        @NotNull
        private final h f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull i0 descriptor, @NotNull n proto, @NotNull a.d signature, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull h typeTable) {
            super((DefaultConstructorMarker) null);
            String desc;
            k.f(descriptor, "descriptor");
            k.f(proto, "proto");
            k.f(signature, "signature");
            k.f(nameResolver, "nameResolver");
            k.f(typeTable, "typeTable");
            this.b = descriptor;
            this.c = proto;
            this.d = signature;
            this.e = nameResolver;
            this.f = typeTable;
            if (signature.hasGetter()) {
                StringBuilder sb = new StringBuilder();
                a.c getter = signature.getGetter();
                k.b(getter, "signature.getter");
                sb.append(nameResolver.getString(getter.getName()));
                a.c getter2 = signature.getGetter();
                k.b(getter2, "signature.getter");
                sb.append(nameResolver.getString(getter2.getDesc()));
                desc = sb.toString();
            } else {
                e.a d2 = i.d(i.b, proto, nameResolver, typeTable, false, 8, (Object) null);
                if (d2 != null) {
                    String name = d2.d();
                    desc = r.b(name) + c() + "()" + d2.e();
                } else {
                    throw new y("No field signature for property: " + descriptor);
                }
            }
            this.a = desc;
        }

        @NotNull
        public final i0 b() {
            return this.b;
        }

        @NotNull
        public final n e() {
            return this.c;
        }

        @NotNull
        public final a.d f() {
            return this.d;
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.metadata.deserialization.c d() {
            return this.e;
        }

        @NotNull
        public final h g() {
            return this.f;
        }

        private final String c() {
            String moduleName;
            m containingDeclaration = this.b.b();
            k.b(containingDeclaration, "descriptor.containingDeclaration");
            if (k.a(this.b.getVisibility(), z0.d) && (containingDeclaration instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d)) {
                kotlin.reflect.jvm.internal.impl.metadata.c classProto = ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) containingDeclaration).Q0();
                h.f<kotlin.reflect.jvm.internal.impl.metadata.c, Integer> fVar = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.i;
                k.b(fVar, "JvmProtoBuf.classModuleName");
                Integer num = (Integer) f.a(classProto, fVar);
                if (num == null || (moduleName = this.e.getString(num.intValue())) == null) {
                    moduleName = LogcatHelper.BUFFER_MAIN;
                }
                return "$" + g.a(moduleName);
            } else if (!k.a(this.b.getVisibility(), z0.a) || !(containingDeclaration instanceof b0)) {
                return "";
            } else {
                i0 i0Var = this.b;
                if (i0Var != null) {
                    kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e packagePartSource = ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i) i0Var).U0();
                    if (!(packagePartSource instanceof j) || ((j) packagePartSource).e() == null) {
                        return "";
                    }
                    return "$" + ((j) packagePartSource).g().b();
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor");
            }
        }

        @NotNull
        public String a() {
            return this.a;
        }
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class b extends d {
        @NotNull
        private final Method a;
        @Nullable
        private final Method b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull Method getterMethod, @Nullable Method setterMethod) {
            super((DefaultConstructorMarker) null);
            k.f(getterMethod, "getterMethod");
            this.a = getterMethod;
            this.b = setterMethod;
        }

        @NotNull
        public final Method b() {
            return this.a;
        }

        @Nullable
        public final Method c() {
            return this.b;
        }

        @NotNull
        public String a() {
            return f0.b(this.a);
        }
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class a extends d {
        @NotNull
        private final Field a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull Field field) {
            super((DefaultConstructorMarker) null);
            k.f(field, "field");
            this.a = field;
        }

        @NotNull
        public final Field b() {
            return this.a;
        }

        @NotNull
        public String a() {
            StringBuilder sb = new StringBuilder();
            sb.append(r.b(this.a.getName()));
            sb.append("()");
            Class<?> type = this.a.getType();
            k.b(type, "field.type");
            sb.append(kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.c(type));
            return sb.toString();
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.d$d  reason: collision with other inner class name */
    /* compiled from: RuntimeTypeMapper.kt */
    public static final class C0336d extends d {
        @NotNull
        private final c.e a;
        @Nullable
        private final c.e b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0336d(@NotNull c.e getterSignature, @Nullable c.e setterSignature) {
            super((DefaultConstructorMarker) null);
            k.f(getterSignature, "getterSignature");
            this.a = getterSignature;
            this.b = setterSignature;
        }

        @NotNull
        public final c.e b() {
            return this.a;
        }

        @Nullable
        public final c.e c() {
            return this.b;
        }

        @NotNull
        public String a() {
            return this.a.a();
        }
    }
}
