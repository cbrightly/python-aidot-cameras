package kotlin.reflect.jvm.internal.impl.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorSubstitutor */
public class p {
    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = "originalSubstitution";
                break;
            case 2:
            case 7:
                objArr[0] = "newContainingDeclaration";
                break;
            case 3:
            case 8:
                objArr[0] = "result";
                break;
            case 4:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/DescriptorSubstitutor";
                break;
            default:
                objArr[0] = "typeParameters";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "substituteTypeParameters";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/types/DescriptorSubstitutor";
                break;
        }
        switch (i) {
            case 4:
                break;
            default:
                objArr[2] = "substituteTypeParameters";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static TypeSubstitutor b(@NotNull List<t0> typeParameters, @NotNull z0 originalSubstitution, @NotNull m newContainingDeclaration, @NotNull List<t0> result) {
        if (typeParameters == null) {
            a(0);
        }
        if (originalSubstitution == null) {
            a(1);
        }
        if (newContainingDeclaration == null) {
            a(2);
        }
        if (result == null) {
            a(3);
        }
        TypeSubstitutor substitutor = c(typeParameters, originalSubstitution, newContainingDeclaration, result, (boolean[]) null);
        if (substitutor != null) {
            return substitutor;
        }
        throw new AssertionError("Substitution failed");
    }

    @Nullable
    public static TypeSubstitutor c(@NotNull List<t0> typeParameters, @NotNull z0 originalSubstitution, @NotNull m newContainingDeclaration, @NotNull List<t0> result, @Nullable boolean[] wereChanges) {
        z0 z0Var = originalSubstitution;
        List<t0> list = result;
        if (typeParameters == null) {
            a(5);
        }
        if (z0Var == null) {
            a(6);
        }
        if (newContainingDeclaration == null) {
            a(7);
        }
        if (list == null) {
            a(8);
        }
        Map<TypeConstructor, TypeProjection> mutableSubstitution = new HashMap<>();
        Map<TypeParameterDescriptor, TypeParameterDescriptorImpl> substitutedMap = new HashMap<>();
        int index = 0;
        for (t0 descriptor : typeParameters) {
            j0 substituted = j0.I0(newContainingDeclaration, descriptor.getAnnotations(), descriptor.t(), descriptor.y(), descriptor.getName(), index, o0.a, descriptor.J());
            mutableSubstitution.put(descriptor.i(), new y0(substituted.m()));
            substitutedMap.put(descriptor, substituted);
            list.add(substituted);
            index++;
        }
        TypeSubstitutor substitutor = TypeSubstitutor.h(z0Var, v0.i(mutableSubstitution));
        for (t0 descriptor2 : typeParameters) {
            j0 substituted2 = (j0) substitutedMap.get(descriptor2);
            for (b0 upperBound : descriptor2.getUpperBounds()) {
                b0 substitutedBound = substitutor.n(upperBound, h1.IN_VARIANCE);
                if (substitutedBound == null) {
                    return null;
                }
                if (!(substitutedBound == upperBound || wereChanges == null)) {
                    wereChanges[0] = true;
                }
                substituted2.A0(substitutedBound);
            }
            substituted2.M0();
        }
        return substitutor;
    }
}
