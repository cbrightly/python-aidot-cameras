package kotlin.reflect.jvm.internal.impl.load.java.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SignaturePropagator */
public interface k {
    public static final k a = new a();

    @NotNull
    b a(@NotNull q qVar, @NotNull e eVar, @NotNull b0 b0Var, @Nullable b0 b0Var2, @NotNull List<w0> list, @NotNull List<t0> list2);

    void b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b bVar, @NotNull List<String> list);

    /* compiled from: SignaturePropagator */
    public static final class a implements k {
        private static /* synthetic */ void c(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "owner";
                    break;
                case 2:
                    objArr[0] = "returnType";
                    break;
                case 3:
                    objArr[0] = "valueParameters";
                    break;
                case 4:
                    objArr[0] = "typeParameters";
                    break;
                case 5:
                    objArr[0] = "descriptor";
                    break;
                case 6:
                    objArr[0] = "signatureErrors";
                    break;
                default:
                    objArr[0] = FirebaseAnalytics.Param.METHOD;
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/SignaturePropagator$1";
            switch (i) {
                case 5:
                case 6:
                    objArr[2] = "reportSignatureErrors";
                    break;
                default:
                    objArr[2] = "resolvePropagatedSignature";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        a() {
        }

        @NotNull
        public b a(@NotNull q method, @NotNull e owner, @NotNull b0 returnType, @Nullable b0 receiverType, @NotNull List<w0> valueParameters, @NotNull List<t0> typeParameters) {
            if (method == null) {
                c(0);
            }
            if (owner == null) {
                c(1);
            }
            if (returnType == null) {
                c(2);
            }
            if (valueParameters == null) {
                c(3);
            }
            if (typeParameters == null) {
                c(4);
            }
            return new b(returnType, receiverType, valueParameters, typeParameters, Collections.emptyList(), false);
        }

        public void b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b descriptor, @NotNull List<String> signatureErrors) {
            if (descriptor == null) {
                c(5);
            }
            if (signatureErrors == null) {
                c(6);
            }
            throw new UnsupportedOperationException("Should not be called");
        }
    }

    /* compiled from: SignaturePropagator */
    public static class b {
        private final b0 a;
        private final b0 b;
        private final List<w0> c;
        private final List<t0> d;
        private final List<String> e;
        private final boolean f;

        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "valueParameters";
                    break;
                case 2:
                    objArr[0] = "typeParameters";
                    break;
                case 3:
                    objArr[0] = "signatureErrors";
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/components/SignaturePropagator$PropagatedSignature";
                    break;
                default:
                    objArr[0] = "returnType";
                    break;
            }
            switch (i) {
                case 4:
                    objArr[1] = "getReturnType";
                    break;
                case 5:
                    objArr[1] = "getValueParameters";
                    break;
                case 6:
                    objArr[1] = "getTypeParameters";
                    break;
                case 7:
                    objArr[1] = "getErrors";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/SignaturePropagator$PropagatedSignature";
                    break;
            }
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public b(@NotNull b0 returnType, @Nullable b0 receiverType, @NotNull List<w0> valueParameters, @NotNull List<t0> typeParameters, @NotNull List<String> signatureErrors, boolean hasStableParameterNames) {
            if (returnType == null) {
                a(0);
            }
            if (valueParameters == null) {
                a(1);
            }
            if (typeParameters == null) {
                a(2);
            }
            if (signatureErrors == null) {
                a(3);
            }
            this.a = returnType;
            this.b = receiverType;
            this.c = valueParameters;
            this.d = typeParameters;
            this.e = signatureErrors;
            this.f = hasStableParameterNames;
        }

        @NotNull
        public b0 d() {
            b0 b0Var = this.a;
            if (b0Var == null) {
                a(4);
            }
            return b0Var;
        }

        @Nullable
        public b0 c() {
            return this.b;
        }

        @NotNull
        public List<w0> f() {
            List<w0> list = this.c;
            if (list == null) {
                a(5);
            }
            return list;
        }

        @NotNull
        public List<t0> e() {
            List<t0> list = this.d;
            if (list == null) {
                a(6);
            }
            return list;
        }

        public boolean g() {
            return this.f;
        }

        @NotNull
        public List<String> b() {
            List<String> list = this.e;
            if (list == null) {
                a(7);
            }
            return list;
        }
    }
}
