package kotlin.collections;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.jetbrains.annotations.NotNull;

/* compiled from: _ArraysJvm.kt */
public class k extends j {
    @NotNull
    public static final <T> List<T> c(@NotNull T[] $this$asList) {
        kotlin.jvm.internal.k.e($this$asList, "$this$asList");
        List<T> a2 = m.a($this$asList);
        kotlin.jvm.internal.k.d(a2, "ArraysUtilJVM.asList(this)");
        return a2;
    }

    /* compiled from: _ArraysJvm.kt */
    public static final class a extends d<Integer> implements RandomAccess {
        final /* synthetic */ int[] d;

        a(int[] $receiver) {
            this.d = $receiver;
        }

        public final /* bridge */ boolean contains(Object obj) {
            if (obj instanceof Integer) {
                return b(((Number) obj).intValue());
            }
            return false;
        }

        public final /* bridge */ int indexOf(Object obj) {
            if (obj instanceof Integer) {
                return f(((Number) obj).intValue());
            }
            return -1;
        }

        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj instanceof Integer) {
                return g(((Number) obj).intValue());
            }
            return -1;
        }

        public int a() {
            return this.d.length;
        }

        public boolean isEmpty() {
            return this.d.length == 0;
        }

        public boolean b(int element) {
            return l.q(this.d, element);
        }

        @NotNull
        /* renamed from: e */
        public Integer get(int index) {
            return Integer.valueOf(this.d[index]);
        }

        public int f(int element) {
            return l.A(this.d, element);
        }

        public int g(int element) {
            return l.G(this.d, element);
        }
    }

    @NotNull
    public static final List<Integer> b(@NotNull int[] $this$asList) {
        kotlin.jvm.internal.k.e($this$asList, "$this$asList");
        return new a($this$asList);
    }

    public static /* synthetic */ Object[] g(Object[] objArr, Object[] objArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = objArr.length;
        }
        return e(objArr, objArr2, i, i2, i3);
    }

    @NotNull
    public static final <T> T[] e(@NotNull T[] $this$copyInto, @NotNull T[] destination, int destinationOffset, int startIndex, int endIndex) {
        kotlin.jvm.internal.k.e($this$copyInto, "$this$copyInto");
        kotlin.jvm.internal.k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ byte[] f(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = bArr.length;
        }
        return d(bArr, bArr2, i, i2, i3);
    }

    @NotNull
    public static final byte[] d(@NotNull byte[] $this$copyInto, @NotNull byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        kotlin.jvm.internal.k.e($this$copyInto, "$this$copyInto");
        kotlin.jvm.internal.k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    @NotNull
    public static final <T> T[] i(@NotNull T[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        kotlin.jvm.internal.k.e($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        i.a(toIndex, $this$copyOfRangeImpl.length);
        T[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        kotlin.jvm.internal.k.d(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    @NotNull
    public static final byte[] h(@NotNull byte[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        kotlin.jvm.internal.k.e($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        i.a(toIndex, $this$copyOfRangeImpl.length);
        byte[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        kotlin.jvm.internal.k.d(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static /* synthetic */ void l(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        k(objArr, obj, i, i2);
    }

    public static final <T> void k(@NotNull T[] $this$fill, T element, int fromIndex, int toIndex) {
        kotlin.jvm.internal.k.e($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static final void j(@NotNull int[] $this$fill, int element, int fromIndex, int toIndex) {
        kotlin.jvm.internal.k.e($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    @NotNull
    public static final byte[] m(@NotNull byte[] $this$plus, @NotNull byte[] elements) {
        kotlin.jvm.internal.k.e($this$plus, "$this$plus");
        kotlin.jvm.internal.k.e(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        byte[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        kotlin.jvm.internal.k.d(result, "result");
        return result;
    }

    public static final <T> void n(@NotNull T[] $this$sort) {
        kotlin.jvm.internal.k.e($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final <T> void o(@NotNull T[] $this$sortWith, @NotNull Comparator<? super T> comparator) {
        kotlin.jvm.internal.k.e($this$sortWith, "$this$sortWith");
        kotlin.jvm.internal.k.e(comparator, "comparator");
        if ($this$sortWith.length > 1) {
            Arrays.sort($this$sortWith, comparator);
        }
    }
}
