package kotlin.reflect.jvm.internal.impl.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.meituan.robust.Constants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.kotlin.k;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.c;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: methodSignatureMapping.kt */
public final class m implements l<k> {
    public static final m a = new m();

    private m() {
    }

    @NotNull
    /* renamed from: f */
    public k b(@NotNull k possiblyPrimitiveType) {
        k.f(possiblyPrimitiveType, "possiblyPrimitiveType");
        if (!(possiblyPrimitiveType instanceof k.c) || ((k.c) possiblyPrimitiveType).a() == null) {
            return possiblyPrimitiveType;
        }
        c c = c.c(((k.c) possiblyPrimitiveType).a().getWrapperFqName());
        kotlin.jvm.internal.k.b(c, "JvmClassName.byFqNameWit…mitiveType.wrapperFqName)");
        String f = c.f();
        kotlin.jvm.internal.k.b(f, "JvmClassName.byFqNameWit…apperFqName).internalName");
        return d(f);
    }

    @NotNull
    /* renamed from: g */
    public k a(@NotNull String representation) {
        d it;
        kotlin.jvm.internal.k.f(representation, "representation");
        boolean z = false;
        if (representation.length() > 0) {
            char firstChar = representation.charAt(0);
            d[] values = d.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    it = null;
                    break;
                }
                it = values[i];
                if ((it.getDesc().charAt(0) == firstChar ? 1 : null) != null) {
                    break;
                }
                i++;
            }
            if (it != null) {
                return new k.c(it);
            }
            switch (firstChar) {
                case 'V':
                    return new k.c((d) null);
                case '[':
                    String substring = representation.substring(1);
                    kotlin.jvm.internal.k.b(substring, "(this as java.lang.String).substring(startIndex)");
                    return new k.a(a(substring));
                default:
                    if (firstChar == 'L' && x.V(representation, ';', false, 2, (Object) null)) {
                        z = true;
                    }
                    if (z) {
                        String substring2 = representation.substring(1, representation.length() - 1);
                        kotlin.jvm.internal.k.b(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        return new k.b(substring2);
                    }
                    throw new AssertionError("Type that is not primitive nor array should be Object, but '" + representation + "' was found");
            }
        } else {
            throw new AssertionError("empty string as JvmType");
        }
    }

    @NotNull
    /* renamed from: h */
    public k.b d(@NotNull String internalName) {
        kotlin.jvm.internal.k.f(internalName, "internalName");
        return new k.b(internalName);
    }

    @NotNull
    /* renamed from: j */
    public String c(@NotNull k type) {
        String desc;
        kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (type instanceof k.a) {
            return Constants.ARRAY_TYPE + c(((k.a) type).a());
        } else if (type instanceof k.c) {
            d a2 = ((k.c) type).a();
            if (a2 == null || (desc = a2.getDesc()) == null) {
                return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
            }
            return desc;
        } else if (type instanceof k.b) {
            return "L" + ((k.b) type).a() + Constants.PACKNAME_END;
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    /* renamed from: i */
    public k e() {
        return d("java/lang/Class");
    }
}
