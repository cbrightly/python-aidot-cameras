package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    @NonNull
    @KeepForSdk
    public static <T> T[] concat(@NonNull T[]... arrays) {
        if (arrays.length == 0) {
            return (Object[]) Array.newInstance(arrays.getClass(), 0);
        }
        int i = 0;
        for (T[] length : arrays) {
            i += length.length;
        }
        T[] copyOf = Arrays.copyOf(arrays[0], i);
        int length2 = arrays[0].length;
        for (int i2 = 1; i2 < arrays.length; i2++) {
            T[] tArr = arrays[i2];
            int length3 = tArr.length;
            System.arraycopy(tArr, 0, copyOf, length2, length3);
            length2 += length3;
        }
        return copyOf;
    }

    @NonNull
    @KeepForSdk
    public static byte[] concatByteArrays(@NonNull byte[]... arrays) {
        if (arrays.length == 0) {
            return new byte[0];
        }
        int i = 0;
        for (byte[] length : arrays) {
            i += length.length;
        }
        byte[] copyOf = Arrays.copyOf(arrays[0], i);
        int length2 = arrays[0].length;
        for (int i2 = 1; i2 < arrays.length; i2++) {
            byte[] bArr = arrays[i2];
            int length3 = bArr.length;
            System.arraycopy(bArr, 0, copyOf, length2, length3);
            length2 += length3;
        }
        return copyOf;
    }

    @KeepForSdk
    public static boolean contains(@Nullable int[] array, int value) {
        if (array != null) {
            for (int i : array) {
                if (i == value) {
                    return true;
                }
            }
        }
        return false;
    }

    @NonNull
    @KeepForSdk
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    @KeepForSdk
    @Nullable
    public static <T> T[] removeAll(@NonNull T[] array, @NonNull T... toBeRemoved) {
        int length;
        int i;
        if (array == null) {
            return null;
        }
        if (toBeRemoved == null || (length = toBeRemoved.length) == 0) {
            return Arrays.copyOf(array, array.length);
        }
        T[] tArr = (Object[]) Array.newInstance(toBeRemoved.getClass().getComponentType(), r3);
        if (length == 1) {
            i = 0;
            for (T t : array) {
                if (!Objects.equal(toBeRemoved[0], t)) {
                    tArr[i] = t;
                    i++;
                }
            }
        } else {
            int i2 = 0;
            for (T t2 : array) {
                if (!contains(toBeRemoved, t2)) {
                    tArr[i2] = t2;
                    i2++;
                }
            }
            i = i2;
        }
        if (tArr == null) {
            return null;
        }
        if (i == tArr.length) {
            return tArr;
        }
        return Arrays.copyOf(tArr, i);
    }

    @NonNull
    @KeepForSdk
    public static <T> ArrayList<T> toArrayList(@NonNull T[] src) {
        ArrayList<T> arrayList = new ArrayList<>(r0);
        for (T add : src) {
            arrayList.add(add);
        }
        return arrayList;
    }

    @NonNull
    @KeepForSdk
    public static int[] toPrimitiveArray(@Nullable Collection<Integer> integerCollection) {
        int i = 0;
        if (integerCollection == null || integerCollection.isEmpty()) {
            return new int[0];
        }
        int[] iArr = new int[integerCollection.size()];
        for (Integer intValue : integerCollection) {
            iArr[i] = intValue.intValue();
            i++;
        }
        return iArr;
    }

    @KeepForSdk
    @Nullable
    public static Integer[] toWrapperArray(@Nullable int[] array) {
        if (array == null) {
            return null;
        }
        int length = array.length;
        Integer[] numArr = new Integer[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = Integer.valueOf(array[i]);
        }
        return numArr;
    }

    @KeepForSdk
    public static void writeArray(@NonNull StringBuilder sb, @NonNull double[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Double.toString(value[i]));
        }
    }

    @KeepForSdk
    public static void writeStringArray(@NonNull StringBuilder sb, @NonNull String[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("\"");
            sb.append(value[i]);
            sb.append("\"");
        }
    }

    @KeepForSdk
    public static <T> boolean contains(@NonNull T[] array, @NonNull T searchValue) {
        int length = array != null ? array.length : 0;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (!Objects.equal(array[i], searchValue)) {
                i++;
            } else if (i >= 0) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public static void writeArray(@NonNull StringBuilder sb, @NonNull float[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Float.toString(value[i]));
        }
    }

    @KeepForSdk
    public static void writeArray(@NonNull StringBuilder sb, @NonNull int[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Integer.toString(value[i]));
        }
    }

    @KeepForSdk
    public static void writeArray(@NonNull StringBuilder sb, @NonNull long[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(value[i]));
        }
    }

    @KeepForSdk
    public static <T> void writeArray(@NonNull StringBuilder sb, @NonNull T[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(value[i]);
        }
    }

    @KeepForSdk
    public static void writeArray(@NonNull StringBuilder sb, @NonNull boolean[] value) {
        int length = value.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Boolean.toString(value[i]));
        }
    }
}
