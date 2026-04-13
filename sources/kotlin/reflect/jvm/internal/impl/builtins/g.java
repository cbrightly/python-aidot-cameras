package kotlin.reflect.jvm.internal.impl.builtins;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.constant.SpInputType;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.builtins.functions.b;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.x;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.r;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: KotlinBuiltIns */
public abstract class g {
    public static final kotlin.reflect.jvm.internal.impl.name.f a;
    public static final kotlin.reflect.jvm.internal.impl.name.b b;
    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.name.b c;
    public static final kotlin.reflect.jvm.internal.impl.name.b d;
    public static final kotlin.reflect.jvm.internal.impl.name.b e;
    public static final kotlin.reflect.jvm.internal.impl.name.b f;
    public static final Set<kotlin.reflect.jvm.internal.impl.name.b> g;
    public static final e h = new e();
    public static final kotlin.reflect.jvm.internal.impl.name.f i = kotlin.reflect.jvm.internal.impl.name.f.k("<built-ins module>");
    /* access modifiers changed from: private */
    public x j;
    private final kotlin.reflect.jvm.internal.impl.storage.f<f> k;
    private final kotlin.reflect.jvm.internal.impl.storage.f<Collection<e0>> l;
    private final kotlin.reflect.jvm.internal.impl.storage.c<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.e> m;
    private final j n;

    private static /* synthetic */ void a(int i2) {
        String str;
        int i3;
        Throwable th;
        switch (i2) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 73:
            case 80:
            case 82:
            case 83:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i2) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 73:
            case 80:
            case 82:
            case 83:
                i3 = 2;
                break;
            default:
                i3 = 3;
                break;
        }
        Object[] objArr = new Object[i3];
        switch (i2) {
            case 1:
            case 71:
                objArr[0] = "module";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 73:
            case 80:
            case 82:
            case 83:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns";
                break;
            case 8:
            case 9:
            case 76:
            case 77:
            case 85:
            case 92:
            case 99:
            case 103:
            case 104:
            case 136:
            case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV:
            case NeedPermissionEvent.PER_GET_LOCATION_BLE:
            case 147:
            case Opcodes.LCMP:
            case Opcodes.FCMPL:
                objArr[0] = "descriptor";
                break;
            case 11:
            case 94:
            case 96:
            case 98:
            case 100:
            case 102:
            case 126:
                objArr[0] = "fqName";
                break;
            case 13:
                objArr[0] = "simpleName";
                break;
            case 15:
            case 16:
            case 52:
            case 84:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 93:
            case 95:
            case 101:
            case 105:
            case 106:
            case 107:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case NeedPermissionEvent.PER_IPC_SPEAK_PERM:
            case 128:
            case NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM:
            case NeedPermissionEvent.PER_IPC_ALBUM_PERM:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 138:
            case NeedPermissionEvent.PER_ANDROID_S_BLE:
            case NeedPermissionEvent.PER_ANDROID_NOTIFICATION:
            case 142:
            case 143:
            case IjkMediaMeta.FF_PROFILE_H264_HIGH_444 /*144*/:
            case 145:
            case 146:
            case Opcodes.DCMPL:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            case 45:
                objArr[0] = "classSimpleName";
                break;
            case 66:
                objArr[0] = "arrayType";
                break;
            case 70:
                objArr[0] = "notNullArrayType";
                break;
            case 72:
            case 152:
                objArr[0] = "primitiveType";
                break;
            case 74:
                objArr[0] = "kotlinType";
                break;
            case 75:
                objArr[0] = "arrayFqName";
                break;
            case 78:
                objArr[0] = "projectionType";
                break;
            case 79:
            case 81:
                objArr[0] = "argument";
                break;
            case 97:
                objArr[0] = "typeConstructor";
                break;
            case 108:
                objArr[0] = "classDescriptor";
                break;
            case 150:
                objArr[0] = "declarationDescriptor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        switch (i2) {
            case 2:
                objArr[1] = "getAdditionalClassPartsProvider";
                break;
            case 3:
                objArr[1] = "getPlatformDependentDeclarationFilter";
                break;
            case 4:
                objArr[1] = "getClassDescriptorFactories";
                break;
            case 5:
                objArr[1] = "getStorageManager";
                break;
            case 6:
                objArr[1] = "getBuiltInsModule";
                break;
            case 7:
                objArr[1] = "getBuiltInPackagesImportedByDefault";
                break;
            case 10:
                objArr[1] = "getBuiltInsPackageScope";
                break;
            case 12:
                objArr[1] = "getBuiltInClassByFqName";
                break;
            case 14:
                objArr[1] = "getBuiltInClassByName";
                break;
            case 17:
                objArr[1] = "getFunctionName";
                break;
            case 18:
                objArr[1] = "getSuspendFunction";
                break;
            case 19:
                objArr[1] = "getKClass";
                break;
            case 20:
                objArr[1] = "getKDeclarationContainer";
                break;
            case 21:
                objArr[1] = "getKCallable";
                break;
            case 22:
                objArr[1] = "getKProperty";
                break;
            case 23:
                objArr[1] = "getKProperty0";
                break;
            case 24:
                objArr[1] = "getKProperty1";
                break;
            case 25:
                objArr[1] = "getKProperty2";
                break;
            case 26:
                objArr[1] = "getKMutableProperty0";
                break;
            case 27:
                objArr[1] = "getKMutableProperty1";
                break;
            case 28:
                objArr[1] = "getKMutableProperty2";
                break;
            case 29:
                objArr[1] = "getIterator";
                break;
            case 30:
                objArr[1] = "getIterable";
                break;
            case 31:
                objArr[1] = "getMutableIterable";
                break;
            case 32:
                objArr[1] = "getMutableIterator";
                break;
            case 33:
                objArr[1] = "getCollection";
                break;
            case 34:
                objArr[1] = "getMutableCollection";
                break;
            case 35:
                objArr[1] = "getList";
                break;
            case 36:
                objArr[1] = "getMutableList";
                break;
            case 37:
                objArr[1] = "getSet";
                break;
            case 38:
                objArr[1] = "getMutableSet";
                break;
            case 39:
                objArr[1] = "getMap";
                break;
            case 40:
                objArr[1] = "getMutableMap";
                break;
            case 41:
                objArr[1] = "getMapEntry";
                break;
            case 42:
                objArr[1] = "getMutableMapEntry";
                break;
            case 43:
                objArr[1] = "getListIterator";
                break;
            case 44:
                objArr[1] = "getMutableListIterator";
                break;
            case 46:
                objArr[1] = "getBuiltInTypeByClassName";
                break;
            case 47:
                objArr[1] = "getNothingType";
                break;
            case 48:
                objArr[1] = "getNullableNothingType";
                break;
            case 49:
                objArr[1] = "getAnyType";
                break;
            case 50:
                objArr[1] = "getNullableAnyType";
                break;
            case 51:
                objArr[1] = "getDefaultBound";
                break;
            case 53:
                objArr[1] = "getPrimitiveKotlinType";
                break;
            case 54:
                objArr[1] = "getNumberType";
                break;
            case 55:
                objArr[1] = "getByteType";
                break;
            case 56:
                objArr[1] = "getShortType";
                break;
            case 57:
                objArr[1] = "getIntType";
                break;
            case 58:
                objArr[1] = "getLongType";
                break;
            case 59:
                objArr[1] = "getFloatType";
                break;
            case 60:
                objArr[1] = "getDoubleType";
                break;
            case 61:
                objArr[1] = "getCharType";
                break;
            case 62:
                objArr[1] = "getBooleanType";
                break;
            case 63:
                objArr[1] = "getUnitType";
                break;
            case 64:
                objArr[1] = "getStringType";
                break;
            case 65:
                objArr[1] = "getIterableType";
                break;
            case 67:
            case 68:
            case 69:
                objArr[1] = "getArrayElementType";
                break;
            case 73:
                objArr[1] = "getPrimitiveArrayKotlinType";
                break;
            case 80:
                objArr[1] = "getArrayType";
                break;
            case 82:
                objArr[1] = "getEnumType";
                break;
            case 83:
                objArr[1] = "getAnnotationType";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns";
                break;
        }
        switch (i2) {
            case 1:
                objArr[2] = "setBuiltInsModule";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 73:
            case 80:
            case 82:
            case 83:
                break;
            case 8:
                objArr[2] = "isBuiltIn";
                break;
            case 9:
                objArr[2] = "isUnderKotlinPackage";
                break;
            case 11:
                objArr[2] = "getBuiltInClassByFqName";
                break;
            case 13:
                objArr[2] = "getBuiltInClassByName";
                break;
            case 15:
                objArr[2] = "getPrimitiveClassDescriptor";
                break;
            case 16:
                objArr[2] = "getPrimitiveArrayClassDescriptor";
                break;
            case 45:
                objArr[2] = "getBuiltInTypeByClassName";
                break;
            case 52:
                objArr[2] = "getPrimitiveKotlinType";
                break;
            case 66:
                objArr[2] = "getArrayElementType";
                break;
            case 70:
            case 71:
                objArr[2] = "getElementTypeForUnsignedArray";
                break;
            case 72:
                objArr[2] = "getPrimitiveArrayKotlinType";
                break;
            case 74:
                objArr[2] = "getPrimitiveArrayKotlinTypeByPrimitiveKotlinType";
                break;
            case 75:
            case 87:
                objArr[2] = "isPrimitiveArray";
                break;
            case 76:
            case 89:
                objArr[2] = "getPrimitiveType";
                break;
            case 77:
                objArr[2] = "getPrimitiveArrayType";
                break;
            case 78:
            case 79:
                objArr[2] = "getArrayType";
                break;
            case 81:
                objArr[2] = "getEnumType";
                break;
            case 84:
                objArr[2] = "isArray";
                break;
            case 85:
            case 86:
                objArr[2] = "isArrayOrPrimitiveArray";
                break;
            case 88:
                objArr[2] = "getPrimitiveArrayElementType";
                break;
            case 90:
                objArr[2] = "isPrimitiveType";
                break;
            case 91:
                objArr[2] = "isPrimitiveTypeOrNullablePrimitiveType";
                break;
            case 92:
                objArr[2] = "isPrimitiveClass";
                break;
            case 93:
            case 94:
            case 95:
            case 96:
                objArr[2] = "isConstructedFromGivenClass";
                break;
            case 97:
            case 98:
                objArr[2] = "isTypeConstructorForGivenClass";
                break;
            case 99:
            case 100:
                objArr[2] = "classFqNameEquals";
                break;
            case 101:
            case 102:
                objArr[2] = "isNotNullConstructedFromGivenClass";
                break;
            case 103:
                objArr[2] = "isSpecialClassWithNoSupertypes";
                break;
            case 104:
            case 105:
                objArr[2] = "isAny";
                break;
            case 106:
            case 108:
                objArr[2] = "isBoolean";
                break;
            case 107:
                objArr[2] = "isBooleanOrNullableBoolean";
                break;
            case 109:
                objArr[2] = "isNumber";
                break;
            case 110:
                objArr[2] = "isChar";
                break;
            case 111:
                objArr[2] = "isCharOrNullableChar";
                break;
            case 112:
                objArr[2] = "isInt";
                break;
            case 113:
                objArr[2] = "isByte";
                break;
            case 114:
                objArr[2] = "isLong";
                break;
            case 115:
                objArr[2] = "isLongOrNullableLong";
                break;
            case 116:
                objArr[2] = "isShort";
                break;
            case 117:
                objArr[2] = "isFloat";
                break;
            case 118:
                objArr[2] = "isFloatOrNullableFloat";
                break;
            case 119:
                objArr[2] = "isDouble";
                break;
            case 120:
                objArr[2] = "isUByte";
                break;
            case 121:
                objArr[2] = "isUShort";
                break;
            case 122:
                objArr[2] = "isUInt";
                break;
            case 123:
                objArr[2] = "isULong";
                break;
            case 124:
                objArr[2] = "isDoubleOrNullableDouble";
                break;
            case 125:
            case 126:
                objArr[2] = "isConstructedFromGivenClassAndNotNullable";
                break;
            case NeedPermissionEvent.PER_IPC_SPEAK_PERM:
                objArr[2] = "isNothing";
                break;
            case 128:
                objArr[2] = "isNullableNothing";
                break;
            case NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM:
                objArr[2] = "isNothingOrNullableNothing";
                break;
            case NeedPermissionEvent.PER_IPC_ALBUM_PERM:
                objArr[2] = "isAnyOrNullableAny";
                break;
            case 131:
                objArr[2] = "isNullableAny";
                break;
            case 132:
                objArr[2] = "isDefaultBound";
                break;
            case 133:
                objArr[2] = "isUnit";
                break;
            case 134:
                objArr[2] = "isUnitOrNullableUnit";
                break;
            case 135:
                objArr[2] = "isBooleanOrSubtype";
                break;
            case 136:
                objArr[2] = "isMemberOfAny";
                break;
            case NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV:
            case 138:
                objArr[2] = "isEnum";
                break;
            case NeedPermissionEvent.PER_GET_LOCATION_BLE:
            case NeedPermissionEvent.PER_ANDROID_S_BLE:
                objArr[2] = "isComparable";
                break;
            case NeedPermissionEvent.PER_ANDROID_NOTIFICATION:
                objArr[2] = "isCollectionOrNullableCollection";
                break;
            case 142:
                objArr[2] = "isListOrNullableList";
                break;
            case 143:
                objArr[2] = "isSetOrNullableSet";
                break;
            case IjkMediaMeta.FF_PROFILE_H264_HIGH_444 /*144*/:
                objArr[2] = "isMapOrNullableMap";
                break;
            case 145:
                objArr[2] = "isIterableOrNullableIterable";
                break;
            case 146:
                objArr[2] = "isThrowableOrNullableThrowable";
                break;
            case 147:
                objArr[2] = "isKClass";
                break;
            case Opcodes.LCMP:
                objArr[2] = "isNonPrimitiveArray";
                break;
            case Opcodes.FCMPL:
                objArr[2] = "isCloneable";
                break;
            case 150:
                objArr[2] = "isDeprecated";
                break;
            case Opcodes.DCMPL:
                objArr[2] = "isNotNullOrNullableFunctionSupertype";
                break;
            case 152:
                objArr[2] = "getPrimitiveFqName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i2) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 73:
            case 80:
            case 82:
            case 83:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    static {
        kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f("kotlin");
        a = f2;
        kotlin.reflect.jvm.internal.impl.name.b k2 = kotlin.reflect.jvm.internal.impl.name.b.k(f2);
        b = k2;
        kotlin.reflect.jvm.internal.impl.name.b c2 = k2.c(kotlin.reflect.jvm.internal.impl.name.f.f("annotation"));
        c = c2;
        kotlin.reflect.jvm.internal.impl.name.b c3 = k2.c(kotlin.reflect.jvm.internal.impl.name.f.f("collections"));
        d = c3;
        kotlin.reflect.jvm.internal.impl.name.b c4 = k2.c(kotlin.reflect.jvm.internal.impl.name.f.f("ranges"));
        e = c4;
        f = k2.c(kotlin.reflect.jvm.internal.impl.name.f.f("text"));
        g = o0.g(k2, c3, c4, c2, j.a(), k2.c(kotlin.reflect.jvm.internal.impl.name.f.f("internal")), kotlin.reflect.jvm.internal.impl.resolve.c.c);
    }

    protected g(@NotNull j storageManager) {
        if (storageManager == null) {
            a(0);
        }
        this.n = storageManager;
        this.l = storageManager.c(new a());
        this.k = storageManager.c(new b());
        this.m = storageManager.h(new c());
    }

    /* compiled from: KotlinBuiltIns */
    public class a implements kotlin.jvm.functions.a<Collection<e0>> {
        a() {
        }

        /* renamed from: a */
        public Collection<e0> invoke() {
            return Arrays.asList(new e0[]{g.this.j.e0(g.b), g.this.j.e0(g.d), g.this.j.e0(g.e), g.this.j.e0(g.c)});
        }
    }

    /* compiled from: KotlinBuiltIns */
    public class b implements kotlin.jvm.functions.a<f> {
        b() {
        }

        /* renamed from: a */
        public f invoke() {
            Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType = new EnumMap<>(h.class);
            Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType = new HashMap<>();
            Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType = new HashMap<>();
            for (h primitive : h.values()) {
                i0 type = g.this.q(primitive.getTypeName().b());
                i0 arrayType = g.this.q(primitive.getArrayTypeName().b());
                primitiveTypeToArrayKotlinType.put(primitive, arrayType);
                primitiveKotlinTypeToKotlinArrayType.put(type, arrayType);
                kotlinArrayTypeToPrimitiveKotlinType.put(arrayType, type);
            }
            return new f(primitiveTypeToArrayKotlinType, primitiveKotlinTypeToKotlinArrayType, kotlinArrayTypeToPrimitiveKotlinType, (a) null);
        }
    }

    /* compiled from: KotlinBuiltIns */
    public class c implements l<kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.e> {
        c() {
        }

        /* renamed from: a */
        public kotlin.reflect.jvm.internal.impl.descriptors.e invoke(kotlin.reflect.jvm.internal.impl.name.f name) {
            h classifier = g.this.s().c(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_BUILTINS);
            if (classifier == null) {
                throw new AssertionError("Built-in class " + g.b.c(name) + " is not found");
            } else if (classifier instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) {
                return (kotlin.reflect.jvm.internal.impl.descriptors.e) classifier;
            } else {
                throw new AssertionError("Must be a class descriptor " + name + ", but was " + classifier);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void g(boolean isFallback) {
        x xVar = new x(i, this.n, this, (kotlin.reflect.jvm.internal.impl.platform.a) null);
        this.j = xVar;
        xVar.K0(a.a.a().a(this.n, this.j, v(), O(), h(), isFallback));
        x xVar2 = this.j;
        xVar2.Q0(xVar2);
    }

    /* compiled from: KotlinBuiltIns */
    public class d implements kotlin.jvm.functions.a<Void> {
        final /* synthetic */ x c;

        d(x xVar) {
            this.c = xVar;
        }

        /* renamed from: a */
        public Void invoke() {
            if (g.this.j == null) {
                x unused = g.this.j = this.c;
                return null;
            }
            throw new AssertionError("Built-ins module is already set: " + g.this.j + " (attempting to reset to " + this.c + ")");
        }
    }

    public void K0(@NotNull x module) {
        if (module == null) {
            a(1);
        }
        this.n.d(new d(module));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a h() {
        a.C0350a aVar = a.C0350a.a;
        if (aVar == null) {
            a(2);
        }
        return aVar;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c O() {
        c.b bVar = c.b.a;
        if (bVar == null) {
            a(3);
        }
        return bVar;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Iterable<kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> v() {
        List singletonList = Collections.singletonList(new kotlin.reflect.jvm.internal.impl.builtins.functions.a(this.n, this.j));
        if (singletonList == null) {
            a(4);
        }
        return singletonList;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public j W() {
        j jVar = this.n;
        if (jVar == null) {
            a(5);
        }
        return jVar;
    }

    /* compiled from: KotlinBuiltIns */
    public static class f {
        public final Map<h, i0> a;
        public final Map<b0, i0> b;
        public final Map<i0, i0> c;

        private static /* synthetic */ void a(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "primitiveKotlinTypeToKotlinArrayType";
                    break;
                case 2:
                    objArr[0] = "kotlinArrayTypeToPrimitiveKotlinType";
                    break;
                default:
                    objArr[0] = "primitiveTypeToArrayKotlinType";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns$Primitives";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* synthetic */ f(Map x0, Map x1, Map x2, a x3) {
            this(x0, x1, x2);
        }

        private f(@NotNull Map<h, i0> primitiveTypeToArrayKotlinType, @NotNull Map<b0, i0> primitiveKotlinTypeToKotlinArrayType, @NotNull Map<i0, i0> kotlinArrayTypeToPrimitiveKotlinType) {
            if (primitiveTypeToArrayKotlinType == null) {
                a(0);
            }
            if (primitiveKotlinTypeToKotlinArrayType == null) {
                a(1);
            }
            if (kotlinArrayTypeToPrimitiveKotlinType == null) {
                a(2);
            }
            this.a = primitiveTypeToArrayKotlinType;
            this.b = primitiveKotlinTypeToKotlinArrayType;
            this.c = kotlinArrayTypeToPrimitiveKotlinType;
        }
    }

    /* compiled from: KotlinBuiltIns */
    public static class e {
        public final kotlin.reflect.jvm.internal.impl.name.b A = d("ExtensionFunctionType");
        public final kotlin.reflect.jvm.internal.impl.name.b B = d("ParameterName");
        public final kotlin.reflect.jvm.internal.impl.name.b C = d("Annotation");
        public final kotlin.reflect.jvm.internal.impl.name.b D = b("Target");
        public final kotlin.reflect.jvm.internal.impl.name.b E = b("AnnotationTarget");
        public final kotlin.reflect.jvm.internal.impl.name.b F = b("AnnotationRetention");
        public final kotlin.reflect.jvm.internal.impl.name.b G = b("Retention");
        public final kotlin.reflect.jvm.internal.impl.name.b H = b("Repeatable");
        public final kotlin.reflect.jvm.internal.impl.name.b I = b("MustBeDocumented");
        public final kotlin.reflect.jvm.internal.impl.name.b J = d("UnsafeVariance");
        public final kotlin.reflect.jvm.internal.impl.name.b K = d("PublishedApi");
        public final kotlin.reflect.jvm.internal.impl.name.b L = c("Iterator");
        public final kotlin.reflect.jvm.internal.impl.name.b M = c("Iterable");
        public final kotlin.reflect.jvm.internal.impl.name.b N = c("Collection");
        public final kotlin.reflect.jvm.internal.impl.name.b O = c("List");
        public final kotlin.reflect.jvm.internal.impl.name.b P = c("ListIterator");
        public final kotlin.reflect.jvm.internal.impl.name.b Q = c("Set");
        public final kotlin.reflect.jvm.internal.impl.name.b R;
        public final kotlin.reflect.jvm.internal.impl.name.b S;
        public final kotlin.reflect.jvm.internal.impl.name.b T;
        public final kotlin.reflect.jvm.internal.impl.name.b U;
        public final kotlin.reflect.jvm.internal.impl.name.b V;
        public final kotlin.reflect.jvm.internal.impl.name.b W;
        public final kotlin.reflect.jvm.internal.impl.name.b X;
        public final kotlin.reflect.jvm.internal.impl.name.b Y;
        public final kotlin.reflect.jvm.internal.impl.name.b Z;
        public final kotlin.reflect.jvm.internal.impl.name.c a = e("Any");
        public final kotlin.reflect.jvm.internal.impl.name.b a0;
        public final kotlin.reflect.jvm.internal.impl.name.c b = e("Nothing");
        public final kotlin.reflect.jvm.internal.impl.name.c b0;
        public final kotlin.reflect.jvm.internal.impl.name.c c = e("Cloneable");
        public final kotlin.reflect.jvm.internal.impl.name.c c0;
        public final kotlin.reflect.jvm.internal.impl.name.b d = d("Suppress");
        public final kotlin.reflect.jvm.internal.impl.name.c d0;
        public final kotlin.reflect.jvm.internal.impl.name.c e = e("Unit");
        public final kotlin.reflect.jvm.internal.impl.name.c e0;
        public final kotlin.reflect.jvm.internal.impl.name.c f = e("CharSequence");
        public final kotlin.reflect.jvm.internal.impl.name.c f0;
        public final kotlin.reflect.jvm.internal.impl.name.c g = e(SpInputType.STRING);
        public final kotlin.reflect.jvm.internal.impl.name.c g0;
        public final kotlin.reflect.jvm.internal.impl.name.c h = e("Array");
        public final kotlin.reflect.jvm.internal.impl.name.c h0;
        public final kotlin.reflect.jvm.internal.impl.name.c i = e(SpInputType.BOOLEAN);
        public final kotlin.reflect.jvm.internal.impl.name.c i0;
        public final kotlin.reflect.jvm.internal.impl.name.c j = e("Char");
        public final kotlin.reflect.jvm.internal.impl.name.c j0;
        public final kotlin.reflect.jvm.internal.impl.name.c k = e("Byte");
        public final kotlin.reflect.jvm.internal.impl.name.c k0;
        public final kotlin.reflect.jvm.internal.impl.name.c l = e("Short");
        public final kotlin.reflect.jvm.internal.impl.name.a l0;
        public final kotlin.reflect.jvm.internal.impl.name.c m = e("Int");
        public final kotlin.reflect.jvm.internal.impl.name.c m0;
        public final kotlin.reflect.jvm.internal.impl.name.c n = e(SpInputType.LONG);
        public final kotlin.reflect.jvm.internal.impl.name.b n0;
        public final kotlin.reflect.jvm.internal.impl.name.c o = e(SpInputType.FLOAT);
        public final kotlin.reflect.jvm.internal.impl.name.b o0;
        public final kotlin.reflect.jvm.internal.impl.name.c p = e("Double");
        public final kotlin.reflect.jvm.internal.impl.name.b p0;
        public final kotlin.reflect.jvm.internal.impl.name.c q = e("Number");
        public final kotlin.reflect.jvm.internal.impl.name.b q0;
        public final kotlin.reflect.jvm.internal.impl.name.c r = e("Enum");
        public final kotlin.reflect.jvm.internal.impl.name.a r0;
        public final kotlin.reflect.jvm.internal.impl.name.c s = e("Function");
        public final kotlin.reflect.jvm.internal.impl.name.a s0;
        public final kotlin.reflect.jvm.internal.impl.name.b t = d("Throwable");
        public final kotlin.reflect.jvm.internal.impl.name.a t0;
        public final kotlin.reflect.jvm.internal.impl.name.b u = d("Comparable");
        public final kotlin.reflect.jvm.internal.impl.name.a u0;
        public final kotlin.reflect.jvm.internal.impl.name.c v = f("IntRange");
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> v0;
        public final kotlin.reflect.jvm.internal.impl.name.c w = f("LongRange");
        public final Set<kotlin.reflect.jvm.internal.impl.name.f> w0;
        public final kotlin.reflect.jvm.internal.impl.name.b x = d("Deprecated");
        public final Map<kotlin.reflect.jvm.internal.impl.name.c, h> x0;
        public final kotlin.reflect.jvm.internal.impl.name.b y = d("DeprecationLevel");
        public final Map<kotlin.reflect.jvm.internal.impl.name.c, h> y0;
        public final kotlin.reflect.jvm.internal.impl.name.b z = d("ReplaceWith");

        private static /* synthetic */ void a(int i2) {
            String str;
            int i3;
            Throwable th;
            switch (i2) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i2) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                    i3 = 2;
                    break;
                default:
                    i3 = 3;
                    break;
            }
            Object[] objArr = new Object[i3];
            switch (i2) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns$FqNames";
                    break;
                default:
                    objArr[0] = "simpleName";
                    break;
            }
            switch (i2) {
                case 1:
                    objArr[1] = "fqNameUnsafe";
                    break;
                case 3:
                    objArr[1] = "fqName";
                    break;
                case 5:
                    objArr[1] = "collectionsFqName";
                    break;
                case 7:
                    objArr[1] = "rangesFqName";
                    break;
                case 9:
                    objArr[1] = "reflect";
                    break;
                case 11:
                    objArr[1] = "annotationName";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns$FqNames";
                    break;
            }
            switch (i2) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                    break;
                case 2:
                    objArr[2] = "fqName";
                    break;
                case 4:
                    objArr[2] = "collectionsFqName";
                    break;
                case 6:
                    objArr[2] = "rangesFqName";
                    break;
                case 8:
                    objArr[2] = "reflect";
                    break;
                case 10:
                    objArr[2] = "annotationName";
                    break;
                default:
                    objArr[2] = "fqNameUnsafe";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i2) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 9:
                case 11:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        public e() {
            kotlin.reflect.jvm.internal.impl.name.b c2 = c(Constants.SERVICE_MAP);
            this.R = c2;
            this.S = c2.c(kotlin.reflect.jvm.internal.impl.name.f.f("Entry"));
            this.T = c("MutableIterator");
            this.U = c("MutableIterable");
            this.V = c("MutableCollection");
            this.W = c("MutableList");
            this.X = c("MutableListIterator");
            this.Y = c("MutableSet");
            kotlin.reflect.jvm.internal.impl.name.b c3 = c("MutableMap");
            this.Z = c3;
            this.a0 = c3.c(kotlin.reflect.jvm.internal.impl.name.f.f("MutableEntry"));
            this.b0 = g("KClass");
            this.c0 = g("KCallable");
            this.d0 = g("KProperty0");
            this.e0 = g("KProperty1");
            this.f0 = g("KProperty2");
            this.g0 = g("KMutableProperty0");
            this.h0 = g("KMutableProperty1");
            this.i0 = g("KMutableProperty2");
            kotlin.reflect.jvm.internal.impl.name.c g2 = g("KProperty");
            this.j0 = g2;
            this.k0 = g("KMutableProperty");
            this.l0 = kotlin.reflect.jvm.internal.impl.name.a.m(g2.l());
            this.m0 = g("KDeclarationContainer");
            kotlin.reflect.jvm.internal.impl.name.b d2 = d("UByte");
            this.n0 = d2;
            kotlin.reflect.jvm.internal.impl.name.b d3 = d("UShort");
            this.o0 = d3;
            kotlin.reflect.jvm.internal.impl.name.b d4 = d("UInt");
            this.p0 = d4;
            kotlin.reflect.jvm.internal.impl.name.b d5 = d("ULong");
            this.q0 = d5;
            this.r0 = kotlin.reflect.jvm.internal.impl.name.a.m(d2);
            this.s0 = kotlin.reflect.jvm.internal.impl.name.a.m(d3);
            this.t0 = kotlin.reflect.jvm.internal.impl.name.a.m(d4);
            this.u0 = kotlin.reflect.jvm.internal.impl.name.a.m(d5);
            this.v0 = kotlin.reflect.jvm.internal.impl.utils.a.f(h.values().length);
            this.w0 = kotlin.reflect.jvm.internal.impl.utils.a.f(h.values().length);
            this.x0 = kotlin.reflect.jvm.internal.impl.utils.a.e(h.values().length);
            this.y0 = kotlin.reflect.jvm.internal.impl.utils.a.e(h.values().length);
            for (h primitiveType : h.values()) {
                this.v0.add(primitiveType.getTypeName());
                this.w0.add(primitiveType.getArrayTypeName());
                this.x0.put(e(primitiveType.getTypeName().b()), primitiveType);
                this.y0.put(e(primitiveType.getArrayTypeName().b()), primitiveType);
            }
        }

        @NotNull
        private static kotlin.reflect.jvm.internal.impl.name.c e(@NotNull String simpleName) {
            if (simpleName == null) {
                a(0);
            }
            kotlin.reflect.jvm.internal.impl.name.c j2 = d(simpleName).j();
            if (j2 == null) {
                a(1);
            }
            return j2;
        }

        @NotNull
        private static kotlin.reflect.jvm.internal.impl.name.b d(@NotNull String simpleName) {
            if (simpleName == null) {
                a(2);
            }
            kotlin.reflect.jvm.internal.impl.name.b c2 = g.b.c(kotlin.reflect.jvm.internal.impl.name.f.f(simpleName));
            if (c2 == null) {
                a(3);
            }
            return c2;
        }

        @NotNull
        private static kotlin.reflect.jvm.internal.impl.name.b c(@NotNull String simpleName) {
            if (simpleName == null) {
                a(4);
            }
            kotlin.reflect.jvm.internal.impl.name.b c2 = g.d.c(kotlin.reflect.jvm.internal.impl.name.f.f(simpleName));
            if (c2 == null) {
                a(5);
            }
            return c2;
        }

        @NotNull
        private static kotlin.reflect.jvm.internal.impl.name.c f(@NotNull String simpleName) {
            if (simpleName == null) {
                a(6);
            }
            kotlin.reflect.jvm.internal.impl.name.c j2 = g.e.c(kotlin.reflect.jvm.internal.impl.name.f.f(simpleName)).j();
            if (j2 == null) {
                a(7);
            }
            return j2;
        }

        @NotNull
        private static kotlin.reflect.jvm.internal.impl.name.c g(@NotNull String simpleName) {
            if (simpleName == null) {
                a(8);
            }
            kotlin.reflect.jvm.internal.impl.name.c j2 = j.a().c(kotlin.reflect.jvm.internal.impl.name.f.f(simpleName)).j();
            if (j2 == null) {
                a(9);
            }
            return j2;
        }

        @NotNull
        private static kotlin.reflect.jvm.internal.impl.name.b b(@NotNull String simpleName) {
            if (simpleName == null) {
                a(10);
            }
            kotlin.reflect.jvm.internal.impl.name.b c2 = g.c.c(kotlin.reflect.jvm.internal.impl.name.f.f(simpleName));
            if (c2 == null) {
                a(11);
            }
            return c2;
        }
    }

    @NotNull
    public x r() {
        x xVar = this.j;
        if (xVar == null) {
            a(6);
        }
        return xVar;
    }

    public static boolean h0(@NotNull m descriptor) {
        if (descriptor == null) {
            a(8);
        }
        return kotlin.reflect.jvm.internal.impl.resolve.c.r(descriptor, b.class, false) != null;
    }

    public static boolean I0(@NotNull m descriptor) {
        if (descriptor == null) {
            a(9);
        }
        for (m current = descriptor; current != null; current = current.b()) {
            if (current instanceof kotlin.reflect.jvm.internal.impl.descriptors.b0) {
                return ((kotlin.reflect.jvm.internal.impl.descriptors.b0) current).e().i(a);
            }
        }
        return false;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.resolve.scopes.h s() {
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h l2 = this.j.e0(b).l();
        if (l2 == null) {
            a(10);
        }
        return l2;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e o(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        if (fqName == null) {
            a(11);
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e descriptor = r.a(this.j, fqName, kotlin.reflect.jvm.internal.impl.incremental.components.d.FROM_BUILTINS);
        if (descriptor != null) {
            if (descriptor == null) {
                a(12);
            }
            return descriptor;
        }
        throw new AssertionError("Can't find built-in class " + fqName);
    }

    @NotNull
    private kotlin.reflect.jvm.internal.impl.descriptors.e p(@NotNull String simpleName) {
        if (simpleName == null) {
            a(13);
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e invoke = this.m.invoke(kotlin.reflect.jvm.internal.impl.name.f.f(simpleName));
        if (invoke == null) {
            a(14);
        }
        return invoke;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e i() {
        return p("Any");
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e I() {
        return p("Nothing");
    }

    @NotNull
    private kotlin.reflect.jvm.internal.impl.descriptors.e R(@NotNull h type) {
        if (type == null) {
            a(15);
        }
        return p(type.getTypeName().b());
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e k() {
        return p("Array");
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e M() {
        return p("Number");
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e a0() {
        return p("Unit");
    }

    @NotNull
    public static String E(int parameterCount) {
        String str = "Function" + parameterCount;
        if (str == null) {
            a(17);
        }
        return str;
    }

    @NotNull
    public static kotlin.reflect.jvm.internal.impl.name.a D(int parameterCount) {
        return new kotlin.reflect.jvm.internal.impl.name.a(b, kotlin.reflect.jvm.internal.impl.name.f.f(E(parameterCount)));
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e C(int parameterCount) {
        return p(E(parameterCount));
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e Z(int parameterCount) {
        kotlin.reflect.jvm.internal.impl.descriptors.e o = o(kotlin.reflect.jvm.internal.impl.resolve.c.c.c(kotlin.reflect.jvm.internal.impl.name.f.f(b.d.SuspendFunction.getClassNamePrefix() + parameterCount)));
        if (o == null) {
            a(18);
        }
        return o;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e X() {
        return p(SpInputType.STRING);
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e x() {
        return p("Comparable");
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e G() {
        kotlin.reflect.jvm.internal.impl.descriptors.e o = o(h.b0.l());
        if (o == null) {
            a(19);
        }
        return o;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.e w() {
        kotlin.reflect.jvm.internal.impl.descriptors.e o = o(h.N);
        if (o == null) {
            a(33);
        }
        return o;
    }

    /* access modifiers changed from: private */
    @NotNull
    public i0 q(@NotNull String classSimpleName) {
        if (classSimpleName == null) {
            a(45);
        }
        i0 m2 = p(classSimpleName).m();
        if (m2 == null) {
            a(46);
        }
        return m2;
    }

    @NotNull
    public i0 J() {
        i0 m2 = I().m();
        if (m2 == null) {
            a(47);
        }
        return m2;
    }

    @NotNull
    public i0 L() {
        i0 P0 = J().P0(true);
        if (P0 == null) {
            a(48);
        }
        return P0;
    }

    @NotNull
    public i0 j() {
        i0 m2 = i().m();
        if (m2 == null) {
            a(49);
        }
        return m2;
    }

    @NotNull
    public i0 K() {
        i0 P0 = j().P0(true);
        if (P0 == null) {
            a(50);
        }
        return P0;
    }

    @NotNull
    public i0 y() {
        i0 K = K();
        if (K == null) {
            a(51);
        }
        return K;
    }

    @NotNull
    public i0 T(@NotNull h type) {
        if (type == null) {
            a(52);
        }
        i0 m2 = R(type).m();
        if (m2 == null) {
            a(53);
        }
        return m2;
    }

    @NotNull
    public i0 N() {
        i0 m2 = M().m();
        if (m2 == null) {
            a(54);
        }
        return m2;
    }

    @NotNull
    public i0 t() {
        i0 T = T(h.BYTE);
        if (T == null) {
            a(55);
        }
        return T;
    }

    @NotNull
    public i0 V() {
        i0 T = T(h.SHORT);
        if (T == null) {
            a(56);
        }
        return T;
    }

    @NotNull
    public i0 F() {
        i0 T = T(h.INT);
        if (T == null) {
            a(57);
        }
        return T;
    }

    @NotNull
    public i0 H() {
        i0 T = T(h.LONG);
        if (T == null) {
            a(58);
        }
        return T;
    }

    @NotNull
    public i0 B() {
        i0 T = T(h.FLOAT);
        if (T == null) {
            a(59);
        }
        return T;
    }

    @NotNull
    public i0 z() {
        i0 T = T(h.DOUBLE);
        if (T == null) {
            a(60);
        }
        return T;
    }

    @NotNull
    public i0 u() {
        i0 T = T(h.CHAR);
        if (T == null) {
            a(61);
        }
        return T;
    }

    @NotNull
    public i0 n() {
        i0 T = T(h.BOOLEAN);
        if (T == null) {
            a(62);
        }
        return T;
    }

    @NotNull
    public i0 b0() {
        i0 m2 = a0().m();
        if (m2 == null) {
            a(63);
        }
        return m2;
    }

    @NotNull
    public i0 Y() {
        i0 m2 = X().m();
        if (m2 == null) {
            a(64);
        }
        return m2;
    }

    @NotNull
    public b0 l(@NotNull b0 arrayType) {
        b0 unsignedType;
        if (arrayType == null) {
            a(66);
        }
        if (!e0(arrayType)) {
            b0 notNullArrayType = c1.n(arrayType);
            b0 primitiveType = ((f) this.k.invoke()).c.get(notNullArrayType);
            if (primitiveType != null) {
                return primitiveType;
            }
            y module = kotlin.reflect.jvm.internal.impl.resolve.c.i(notNullArrayType);
            if (module != null && (unsignedType = A(notNullArrayType, module)) != null) {
                return unsignedType;
            }
            throw new IllegalStateException("not array: " + arrayType);
        } else if (arrayType.H0().size() == 1) {
            b0 type = arrayType.H0().get(0).getType();
            if (type == null) {
                a(67);
            }
            return type;
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    private static b0 A(@NotNull b0 notNullArrayType, @NotNull y module) {
        kotlin.reflect.jvm.internal.impl.name.a arrayClassId;
        kotlin.reflect.jvm.internal.impl.name.a elementClassId;
        kotlin.reflect.jvm.internal.impl.descriptors.e elementClassDescriptor;
        if (notNullArrayType == null) {
            a(70);
        }
        if (module == null) {
            a(71);
        }
        h descriptor = notNullArrayType.I0().c();
        if (descriptor == null) {
            return null;
        }
        m mVar = m.e;
        if (!mVar.b(descriptor.getName()) || (arrayClassId = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i(descriptor)) == null || (elementClassId = mVar.a(arrayClassId)) == null || (elementClassDescriptor = t.a(module, elementClassId)) == null) {
            return null;
        }
        return elementClassDescriptor.m();
    }

    @NotNull
    public i0 P(@NotNull h primitiveType) {
        if (primitiveType == null) {
            a(72);
        }
        i0 i0Var = ((f) this.k.invoke()).a.get(primitiveType);
        if (i0Var == null) {
            a(73);
        }
        return i0Var;
    }

    public static boolean z0(@NotNull kotlin.reflect.jvm.internal.impl.name.c arrayFqName) {
        if (arrayFqName == null) {
            a(75);
        }
        return h.y0.get(arrayFqName) != null;
    }

    @Nullable
    public static h U(@NotNull m descriptor) {
        if (descriptor == null) {
            a(76);
        }
        e eVar = h;
        if (eVar.v0.contains(descriptor.getName())) {
            return eVar.x0.get(kotlin.reflect.jvm.internal.impl.resolve.c.m(descriptor));
        }
        return null;
    }

    @Nullable
    public static h Q(@NotNull m descriptor) {
        if (descriptor == null) {
            a(77);
        }
        e eVar = h;
        if (eVar.w0.contains(descriptor.getName())) {
            return eVar.y0.get(kotlin.reflect.jvm.internal.impl.resolve.c.m(descriptor));
        }
        return null;
    }

    @NotNull
    public i0 m(@NotNull h1 projectionType, @NotNull b0 argument) {
        if (projectionType == null) {
            a(78);
        }
        if (argument == null) {
            a(79);
        }
        i0 g2 = c0.g(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b(), k(), Collections.singletonList(new y0(projectionType, argument)));
        if (g2 == null) {
            a(80);
        }
        return g2;
    }

    public static boolean e0(@NotNull b0 type) {
        if (type == null) {
            a(84);
        }
        return k0(type, h.h);
    }

    public static boolean f0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e descriptor) {
        if (descriptor == null) {
            a(85);
        }
        return f(descriptor, h.h) || Q(descriptor) != null;
    }

    public static boolean A0(@NotNull b0 type) {
        if (type == null) {
            a(87);
        }
        h descriptor = type.I0().c();
        return (descriptor == null || Q(descriptor) == null) ? false : true;
    }

    public static boolean C0(@NotNull b0 type) {
        if (type == null) {
            a(90);
        }
        return !type.J0() && D0(type);
    }

    public static boolean D0(@NotNull b0 type) {
        if (type == null) {
            a(91);
        }
        h descriptor = type.I0().c();
        return (descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) && B0((kotlin.reflect.jvm.internal.impl.descriptors.e) descriptor);
    }

    public static boolean B0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e descriptor) {
        if (descriptor == null) {
            a(92);
        }
        return U(descriptor) != null;
    }

    private static boolean k0(@NotNull b0 type, @NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
        if (type == null) {
            a(93);
        }
        if (fqName == null) {
            a(94);
        }
        return H0(type.I0(), fqName);
    }

    public static boolean H0(@NotNull u0 typeConstructor, @NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
        if (typeConstructor == null) {
            a(97);
        }
        if (fqName == null) {
            a(98);
        }
        h descriptor = typeConstructor.c();
        return (descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) && f(descriptor, fqName);
    }

    private static boolean f(@NotNull h descriptor, @NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
        if (descriptor == null) {
            a(99);
        }
        if (fqName == null) {
            a(100);
        }
        return descriptor.getName().equals(fqName.i()) && fqName.equals(kotlin.reflect.jvm.internal.impl.resolve.c.m(descriptor));
    }

    private static boolean v0(@NotNull b0 type, @NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
        if (type == null) {
            a(101);
        }
        if (fqName == null) {
            a(102);
        }
        return !type.J0() && k0(type, fqName);
    }

    public static boolean F0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e descriptor) {
        if (descriptor == null) {
            a(103);
        }
        e eVar = h;
        return f(descriptor, eVar.a) || f(descriptor, eVar.b);
    }

    public static boolean c0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e descriptor) {
        if (descriptor == null) {
            a(104);
        }
        return f(descriptor, h.a);
    }

    public static boolean g0(@NotNull b0 type) {
        if (type == null) {
            a(106);
        }
        return l0(type, h.i);
    }

    public static boolean j0(@NotNull b0 type) {
        if (type == null) {
            a(110);
        }
        return l0(type, h.j);
    }

    public static boolean s0(@NotNull b0 type) {
        if (type == null) {
            a(112);
        }
        return l0(type, h.m);
    }

    public static boolean i0(@NotNull b0 type) {
        if (type == null) {
            a(113);
        }
        return l0(type, h.k);
    }

    public static boolean u0(@NotNull b0 type) {
        if (type == null) {
            a(114);
        }
        return l0(type, h.n);
    }

    public static boolean E0(@NotNull b0 type) {
        if (type == null) {
            a(116);
        }
        return l0(type, h.l);
    }

    public static boolean q0(@NotNull b0 type) {
        if (type == null) {
            a(117);
        }
        return r0(type) && !type.J0();
    }

    public static boolean r0(@NotNull b0 type) {
        if (type == null) {
            a(118);
        }
        return k0(type, h.o);
    }

    public static boolean o0(@NotNull b0 type) {
        if (type == null) {
            a(119);
        }
        return p0(type) && !type.J0();
    }

    public static boolean p0(@NotNull b0 type) {
        if (type == null) {
            a(124);
        }
        return k0(type, h.p);
    }

    private static boolean l0(@NotNull b0 type, @NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
        if (type == null) {
            a(125);
        }
        if (fqName == null) {
            a(126);
        }
        return k0(type, fqName) && !type.J0();
    }

    public static boolean w0(@NotNull b0 type) {
        if (type == null) {
            a(NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        }
        return x0(type) && !c1.l(type);
    }

    public static boolean x0(@NotNull b0 type) {
        if (type == null) {
            a(NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM);
        }
        return k0(type, h.b);
    }

    public static boolean d0(@NotNull b0 type) {
        if (type == null) {
            a(NeedPermissionEvent.PER_IPC_ALBUM_PERM);
        }
        return k0(type, h.a);
    }

    public static boolean y0(@NotNull b0 type) {
        if (type == null) {
            a(131);
        }
        return d0(type) && type.J0();
    }

    public static boolean m0(@NotNull b0 type) {
        if (type == null) {
            a(132);
        }
        return y0(type);
    }

    public static boolean J0(@NotNull b0 type) {
        if (type == null) {
            a(133);
        }
        return v0(type, h.e);
    }

    public static boolean G0(@Nullable b0 type) {
        return type != null && v0(type, h.g);
    }

    public static boolean t0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e descriptor) {
        if (descriptor == null) {
            a(147);
        }
        return f(descriptor, h.b0);
    }

    public static boolean n0(@NotNull m declarationDescriptor) {
        if (declarationDescriptor == null) {
            a(150);
        }
        if (declarationDescriptor.a().getAnnotations().I(h.x)) {
            return true;
        }
        if (!(declarationDescriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.i0)) {
            return false;
        }
        boolean isVar = ((kotlin.reflect.jvm.internal.impl.descriptors.i0) declarationDescriptor).K();
        j0 getter = ((kotlin.reflect.jvm.internal.impl.descriptors.i0) declarationDescriptor).getGetter();
        k0 setter = ((kotlin.reflect.jvm.internal.impl.descriptors.i0) declarationDescriptor).getSetter();
        if (getter != null && n0(getter)) {
            if (!isVar) {
                return true;
            }
            if (setter != null && n0(setter)) {
                return true;
            }
        }
        return false;
    }

    public static kotlin.reflect.jvm.internal.impl.name.b S(@NotNull h primitiveType) {
        if (primitiveType == null) {
            a(152);
        }
        return b.c(primitiveType.getTypeName());
    }
}
