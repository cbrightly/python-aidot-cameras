package kotlin.collections;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.ranges.i;
import kotlin.sequences.h;
import kotlin.sequences.m;
import kotlin.t;
import kotlin.text.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: _Arrays.kt */
public class l extends k {

    /* compiled from: Sequences.kt */
    public static final class a implements h<T> {
        final /* synthetic */ Object[] a;

        public a(Object[] objArr) {
            this.a = objArr;
        }

        @NotNull
        public Iterator<T> iterator() {
            return kotlin.jvm.internal.b.a(this.a);
        }
    }

    public static final <T> boolean r(@NotNull T[] $this$contains, T element) {
        k.e($this$contains, "$this$contains");
        return B($this$contains, element) >= 0;
    }

    public static final boolean q(@NotNull int[] $this$contains, int element) {
        k.e($this$contains, "$this$contains");
        return A($this$contains, element) >= 0;
    }

    public static final <T> T u(@NotNull T[] $this$first) {
        k.e($this$first, "$this$first");
        if (!($this$first.length == 0)) {
            return $this$first[0];
        }
        throw new NoSuchElementException("Array is empty.");
    }

    @Nullable
    public static final <T> T v(@NotNull T[] $this$firstOrNull) {
        k.e($this$firstOrNull, "$this$firstOrNull");
        if ($this$firstOrNull.length == 0) {
            return null;
        }
        return $this$firstOrNull[0];
    }

    @Nullable
    public static final Integer z(@NotNull int[] $this$getOrNull, int index) {
        k.e($this$getOrNull, "$this$getOrNull");
        if (index < 0 || index > x($this$getOrNull)) {
            return null;
        }
        return Integer.valueOf($this$getOrNull[index]);
    }

    public static final <T> int B(@NotNull T[] $this$indexOf, T element) {
        k.e($this$indexOf, "$this$indexOf");
        int index = 0;
        if (element == null) {
            int length = $this$indexOf.length;
            while (index < length) {
                if ($this$indexOf[index] == null) {
                    return index;
                }
                index++;
            }
            return -1;
        }
        int length2 = $this$indexOf.length;
        while (index < length2) {
            if (k.a(element, $this$indexOf[index])) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static final int A(@NotNull int[] $this$indexOf, int element) {
        k.e($this$indexOf, "$this$indexOf");
        int length = $this$indexOf.length;
        for (int index = 0; index < length; index++) {
            if (element == $this$indexOf[index]) {
                return index;
            }
        }
        return -1;
    }

    public static final <T> T F(@NotNull T[] $this$last) {
        k.e($this$last, "$this$last");
        if (!($this$last.length == 0)) {
            return $this$last[y($this$last)];
        }
        throw new NoSuchElementException("Array is empty.");
    }

    public static final int G(@NotNull int[] $this$lastIndexOf, int element) {
        k.e($this$lastIndexOf, "$this$lastIndexOf");
        for (int index = $this$lastIndexOf.length - 1; index >= 0; index--) {
            if (element == $this$lastIndexOf[index]) {
                return index;
            }
        }
        return -1;
    }

    public static final <T> T J(@NotNull T[] $this$single) {
        k.e($this$single, "$this$single");
        switch ($this$single.length) {
            case 0:
                throw new NoSuchElementException("Array is empty.");
            case 1:
                return $this$single[0];
            default:
                throw new IllegalArgumentException("Array has more than one element.");
        }
    }

    public static final char I(@NotNull char[] $this$single) {
        k.e($this$single, "$this$single");
        switch ($this$single.length) {
            case 0:
                throw new NoSuchElementException("Array is empty.");
            case 1:
                return $this$single[0];
            default:
                throw new IllegalArgumentException("Array has more than one element.");
        }
    }

    @Nullable
    public static final <T> T K(@NotNull T[] $this$singleOrNull) {
        k.e($this$singleOrNull, "$this$singleOrNull");
        if ($this$singleOrNull.length == 1) {
            return $this$singleOrNull[0];
        }
        return null;
    }

    @NotNull
    public static final <T> List<T> s(@NotNull T[] $this$filterNotNull) {
        k.e($this$filterNotNull, "$this$filterNotNull");
        return (List) t($this$filterNotNull, new ArrayList());
    }

    @NotNull
    public static final <C extends Collection<? super T>, T> C t(@NotNull T[] $this$filterNotNullTo, @NotNull C destination) {
        k.e($this$filterNotNullTo, "$this$filterNotNullTo");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        for (Object element : $this$filterNotNullTo) {
            if (element != null) {
                destination.add(element);
            }
        }
        return destination;
    }

    @NotNull
    public static final <T> T[] L(@NotNull T[] $this$sortedArrayWith, @NotNull Comparator<? super T> comparator) {
        k.e($this$sortedArrayWith, "$this$sortedArrayWith");
        k.e(comparator, "comparator");
        if ($this$sortedArrayWith.length == 0) {
            return $this$sortedArrayWith;
        }
        Object[] $this$apply = Arrays.copyOf($this$sortedArrayWith, $this$sortedArrayWith.length);
        k.d($this$apply, "java.util.Arrays.copyOf(this, size)");
        k.o($this$apply, comparator);
        return $this$apply;
    }

    @NotNull
    public static final <T> List<T> M(@NotNull T[] $this$sortedWith, @NotNull Comparator<? super T> comparator) {
        k.e($this$sortedWith, "$this$sortedWith");
        k.e(comparator, "comparator");
        return k.c(L($this$sortedWith, comparator));
    }

    @NotNull
    public static final <T> i w(@NotNull T[] $this$indices) {
        k.e($this$indices, "$this$indices");
        return new i(0, y($this$indices));
    }

    public static final <T> int y(@NotNull T[] $this$lastIndex) {
        k.e($this$lastIndex, "$this$lastIndex");
        return $this$lastIndex.length - 1;
    }

    public static final int x(@NotNull int[] $this$lastIndex) {
        k.e($this$lastIndex, "$this$lastIndex");
        return $this$lastIndex.length - 1;
    }

    @NotNull
    public static final <T, C extends Collection<? super T>> C N(@NotNull T[] $this$toCollection, @NotNull C destination) {
        k.e($this$toCollection, "$this$toCollection");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        for (Object item : $this$toCollection) {
            destination.add(item);
        }
        return destination;
    }

    @NotNull
    public static final <T> List<T> U(@NotNull T[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b($this$toList[0]);
            default:
                return d0($this$toList);
        }
    }

    @NotNull
    public static final List<Byte> O(@NotNull byte[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Byte.valueOf($this$toList[0]));
            default:
                return X($this$toList);
        }
    }

    @NotNull
    public static final List<Short> V(@NotNull short[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Short.valueOf($this$toList[0]));
            default:
                return e0($this$toList);
        }
    }

    @NotNull
    public static final List<Integer> S(@NotNull int[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Integer.valueOf($this$toList[0]));
            default:
                return b0($this$toList);
        }
    }

    @NotNull
    public static final List<Long> T(@NotNull long[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Long.valueOf($this$toList[0]));
            default:
                return c0($this$toList);
        }
    }

    @NotNull
    public static final List<Float> R(@NotNull float[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Float.valueOf($this$toList[0]));
            default:
                return a0($this$toList);
        }
    }

    @NotNull
    public static final List<Double> Q(@NotNull double[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Double.valueOf($this$toList[0]));
            default:
                return Z($this$toList);
        }
    }

    @NotNull
    public static final List<Boolean> W(@NotNull boolean[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Boolean.valueOf($this$toList[0]));
            default:
                return f0($this$toList);
        }
    }

    @NotNull
    public static final List<Character> P(@NotNull char[] $this$toList) {
        k.e($this$toList, "$this$toList");
        switch ($this$toList.length) {
            case 0:
                return q.g();
            case 1:
                return p.b(Character.valueOf($this$toList[0]));
            default:
                return Y($this$toList);
        }
    }

    @NotNull
    public static final <T> List<T> d0(@NotNull T[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        return new ArrayList(q.d($this$toMutableList));
    }

    @NotNull
    public static final List<Byte> X(@NotNull byte[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (byte item : $this$toMutableList) {
            list.add(Byte.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Short> e0(@NotNull short[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (short item : $this$toMutableList) {
            list.add(Short.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Integer> b0(@NotNull int[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (int item : $this$toMutableList) {
            list.add(Integer.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Long> c0(@NotNull long[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (long item : $this$toMutableList) {
            list.add(Long.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Float> a0(@NotNull float[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (float item : $this$toMutableList) {
            list.add(Float.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Double> Z(@NotNull double[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (double item : $this$toMutableList) {
            list.add(Double.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Boolean> f0(@NotNull boolean[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (boolean item : $this$toMutableList) {
            list.add(Boolean.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final List<Character> Y(@NotNull char[] $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        ArrayList list = new ArrayList($this$toMutableList.length);
        for (char item : $this$toMutableList) {
            list.add(Character.valueOf(item));
        }
        return list;
    }

    @NotNull
    public static final <T> Set<T> g0(@NotNull T[] $this$toSet) {
        k.e($this$toSet, "$this$toSet");
        switch ($this$toSet.length) {
            case 0:
                return o0.d();
            case 1:
                return n0.c($this$toSet[0]);
            default:
                return (Set) N($this$toSet, new LinkedHashSet(k0.b($this$toSet.length)));
        }
    }

    @NotNull
    public static final <T, R> List<R> H(@NotNull T[] $this$map, @NotNull kotlin.jvm.functions.l<? super T, ? extends R> transform) {
        k.e($this$map, "$this$map");
        k.e(transform, "transform");
        ArrayList arrayList = new ArrayList($this$map.length);
        for (Object item$iv : $this$map) {
            arrayList.add(transform.invoke(item$iv));
        }
        return arrayList;
    }

    /* compiled from: _Arrays.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Iterator<? extends T>> {
        final /* synthetic */ Object[] $this_withIndex;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Object[] objArr) {
            super(0);
            this.$this_withIndex = objArr;
        }

        @NotNull
        public final Iterator<T> invoke() {
            return kotlin.jvm.internal.b.a(this.$this_withIndex);
        }
    }

    @NotNull
    public static final <T> Iterable<d0<T>> h0(@NotNull T[] $this$withIndex) {
        k.e($this$withIndex, "$this$withIndex");
        return new e0(new b($this$withIndex));
    }

    @NotNull
    public static final <T, R> List<n<T, R>> i0(@NotNull T[] $this$zip, @NotNull R[] other) {
        k.e($this$zip, "$this$zip");
        k.e(other, "other");
        Object[] $this$zip$iv = $this$zip;
        int size$iv = Math.min($this$zip$iv.length, other.length);
        ArrayList list$iv = new ArrayList(size$iv);
        for (int i$iv = 0; i$iv < size$iv; i$iv++) {
            list$iv.add(t.a($this$zip$iv[i$iv], other[i$iv]));
        }
        return list$iv;
    }

    @NotNull
    public static final <T, A extends Appendable> A C(@NotNull T[] $this$joinTo, @NotNull A buffer, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence postfix, int limit, @NotNull CharSequence truncated, @Nullable kotlin.jvm.functions.l<? super T, ? extends CharSequence> transform) {
        k.e($this$joinTo, "$this$joinTo");
        k.e(buffer, "buffer");
        k.e(separator, "separator");
        k.e(prefix, "prefix");
        k.e(postfix, "postfix");
        k.e(truncated, "truncated");
        buffer.append(prefix);
        int count = 0;
        for (Object element : $this$joinTo) {
            count++;
            if (count > 1) {
                buffer.append(separator);
            }
            if (limit >= 0 && count > limit) {
                break;
            }
            o.a(buffer, element, transform);
        }
        if (limit >= 0 && count > limit) {
            buffer.append(truncated);
        }
        buffer.append(postfix);
        return buffer;
    }

    public static /* synthetic */ String E(Object[] objArr, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, kotlin.jvm.functions.l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charSequence = ", ";
        }
        CharSequence charSequence5 = "";
        CharSequence charSequence6 = (i2 & 2) != 0 ? charSequence5 : charSequence2;
        if ((i2 & 4) == 0) {
            charSequence5 = charSequence3;
        }
        if ((i2 & 8) != 0) {
            i = -1;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            charSequence4 = "...";
        }
        CharSequence charSequence7 = charSequence4;
        if ((i2 & 32) != 0) {
            lVar = null;
        }
        return D(objArr, charSequence, charSequence6, charSequence5, i3, charSequence7, lVar);
    }

    @NotNull
    public static final <T> String D(@NotNull T[] $this$joinToString, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence postfix, int limit, @NotNull CharSequence truncated, @Nullable kotlin.jvm.functions.l<? super T, ? extends CharSequence> transform) {
        k.e($this$joinToString, "$this$joinToString");
        k.e(separator, "separator");
        k.e(prefix, "prefix");
        k.e(postfix, "postfix");
        k.e(truncated, "truncated");
        String sb = ((StringBuilder) C($this$joinToString, new StringBuilder(), separator, prefix, postfix, limit, truncated, transform)).toString();
        k.d(sb, "joinTo(StringBuilder(), …ed, transform).toString()");
        return sb;
    }

    @NotNull
    public static final <T> h<T> p(@NotNull T[] $this$asSequence) {
        k.e($this$asSequence, "$this$asSequence");
        if ($this$asSequence.length == 0) {
            return m.e();
        }
        return new a($this$asSequence);
    }
}
