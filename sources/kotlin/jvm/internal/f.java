package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CollectionToArray.kt */
public final class f {
    private static final Object[] a = new Object[0];

    @NotNull
    public static final Object[] a(@NotNull Collection<?> collection) {
        k.e(collection, "collection");
        int size$iv = collection.size();
        if (size$iv == 0) {
            return a;
        }
        Iterator iter$iv = collection.iterator();
        if (!iter$iv.hasNext()) {
            return a;
        }
        Object[] result$iv = new Object[size$iv];
        int i$iv = 0;
        while (true) {
            int i$iv2 = i$iv + 1;
            result$iv[i$iv] = iter$iv.next();
            if (i$iv2 >= result$iv.length) {
                if (!iter$iv.hasNext()) {
                    return result$iv;
                }
                int newSize$iv = ((i$iv2 * 3) + 1) >>> 1;
                if (newSize$iv <= i$iv2) {
                    if (i$iv2 < 2147483645) {
                        newSize$iv = 2147483645;
                    } else {
                        throw new OutOfMemoryError();
                    }
                }
                Object[] copyOf = Arrays.copyOf(result$iv, newSize$iv);
                k.d(copyOf, "Arrays.copyOf(result, newSize)");
                result$iv = copyOf;
            } else if (!iter$iv.hasNext()) {
                Object[] copyOf2 = Arrays.copyOf(result$iv, i$iv2);
                k.d(copyOf2, "Arrays.copyOf(result, size)");
                return copyOf2;
            }
            i$iv = i$iv2;
        }
    }

    @NotNull
    public static final Object[] b(@NotNull Collection<?> collection, @Nullable Object[] a2) {
        Object[] objArr;
        k.e(collection, "collection");
        if (a2 != null) {
            int size$iv = collection.size();
            if (size$iv != 0) {
                Iterator iter$iv = collection.iterator();
                if (iter$iv.hasNext()) {
                    int size = size$iv;
                    if (size <= a2.length) {
                        objArr = a2;
                    } else {
                        Object newInstance = Array.newInstance(a2.getClass().getComponentType(), size);
                        if (newInstance != null) {
                            objArr = (Object[]) newInstance;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                        }
                    }
                    Object[] result$iv = objArr;
                    int i$iv = 0;
                    while (true) {
                        int i$iv2 = i$iv + 1;
                        result$iv[i$iv] = iter$iv.next();
                        if (i$iv2 >= result$iv.length) {
                            if (!iter$iv.hasNext()) {
                                return result$iv;
                            }
                            int newSize$iv = ((i$iv2 * 3) + 1) >>> 1;
                            if (newSize$iv <= i$iv2) {
                                if (i$iv2 < 2147483645) {
                                    newSize$iv = 2147483645;
                                } else {
                                    throw new OutOfMemoryError();
                                }
                            }
                            Object[] copyOf = Arrays.copyOf(result$iv, newSize$iv);
                            k.d(copyOf, "Arrays.copyOf(result, newSize)");
                            result$iv = copyOf;
                        } else if (!iter$iv.hasNext()) {
                            Object[] result = result$iv;
                            int size2 = i$iv2;
                            if (result == a2) {
                                a2[size2] = null;
                            } else {
                                Object[] result$iv2 = Arrays.copyOf(result, size2);
                                k.d(result$iv2, "Arrays.copyOf(result, size)");
                                return result$iv2;
                            }
                        }
                        i$iv = i$iv2;
                    }
                } else if (a2.length > 0) {
                    a2[0] = null;
                }
            } else if (a2.length > 0) {
                a2[0] = null;
            }
            return a2;
        }
        throw new NullPointerException();
    }
}
