package kotlin.collections;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.sequences.h;
import kotlin.t;
import kotlin.text.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: _Collections.kt */
public class y extends x {

    /* compiled from: Sequences.kt */
    public static final class a implements h<T> {
        final /* synthetic */ Iterable a;

        public a(Iterable iterable) {
            this.a = iterable;
        }

        @NotNull
        public Iterator<T> iterator() {
            return this.a.iterator();
        }
    }

    public static final <T> boolean M(@NotNull Iterable<? extends T> $this$contains, T element) {
        k.e($this$contains, "$this$contains");
        if ($this$contains instanceof Collection) {
            return ((Collection) $this$contains).contains(element);
        }
        return W($this$contains, element) >= 0;
    }

    public static final <T> T R(@NotNull Iterable<? extends T> $this$first) {
        k.e($this$first, "$this$first");
        if ($this$first instanceof List) {
            return S((List) $this$first);
        }
        Iterator iterator = $this$first.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new NoSuchElementException("Collection is empty.");
    }

    public static final <T> T S(@NotNull List<? extends T> $this$first) {
        k.e($this$first, "$this$first");
        if (!$this$first.isEmpty()) {
            return $this$first.get(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    @Nullable
    public static final <T> T T(@NotNull Iterable<? extends T> $this$firstOrNull) {
        k.e($this$firstOrNull, "$this$firstOrNull");
        if (!($this$firstOrNull instanceof List)) {
            Iterator iterator = $this$firstOrNull.iterator();
            if (!iterator.hasNext()) {
                return null;
            }
            return iterator.next();
        } else if (((List) $this$firstOrNull).isEmpty()) {
            return null;
        } else {
            return ((List) $this$firstOrNull).get(0);
        }
    }

    @Nullable
    public static final <T> T U(@NotNull List<? extends T> $this$firstOrNull) {
        k.e($this$firstOrNull, "$this$firstOrNull");
        if ($this$firstOrNull.isEmpty()) {
            return null;
        }
        return $this$firstOrNull.get(0);
    }

    @Nullable
    public static final <T> T V(@NotNull List<? extends T> $this$getOrNull, int index) {
        k.e($this$getOrNull, "$this$getOrNull");
        if (index < 0 || index > q.i($this$getOrNull)) {
            return null;
        }
        return $this$getOrNull.get(index);
    }

    public static final <T> int W(@NotNull Iterable<? extends T> $this$indexOf, T element) {
        k.e($this$indexOf, "$this$indexOf");
        if ($this$indexOf instanceof List) {
            return ((List) $this$indexOf).indexOf(element);
        }
        int index = 0;
        for (Object item : $this$indexOf) {
            if (index < 0) {
                q.q();
            }
            if (k.a(element, item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static final <T> T c0(@NotNull Iterable<? extends T> $this$last) {
        k.e($this$last, "$this$last");
        if ($this$last instanceof List) {
            return d0((List) $this$last);
        }
        Iterator iterator = $this$last.iterator();
        if (iterator.hasNext()) {
            Object last = iterator.next();
            while (iterator.hasNext()) {
                last = iterator.next();
            }
            return last;
        }
        throw new NoSuchElementException("Collection is empty.");
    }

    public static final <T> T d0(@NotNull List<? extends T> $this$last) {
        k.e($this$last, "$this$last");
        if (!$this$last.isEmpty()) {
            return $this$last.get(q.i($this$last));
        }
        throw new NoSuchElementException("List is empty.");
    }

    @Nullable
    public static final <T> T e0(@NotNull Iterable<? extends T> $this$lastOrNull) {
        k.e($this$lastOrNull, "$this$lastOrNull");
        if (!($this$lastOrNull instanceof List)) {
            Iterator iterator = $this$lastOrNull.iterator();
            if (!iterator.hasNext()) {
                return null;
            }
            Object last = iterator.next();
            while (iterator.hasNext()) {
                last = iterator.next();
            }
            return last;
        } else if (((List) $this$lastOrNull).isEmpty()) {
            return null;
        } else {
            return ((List) $this$lastOrNull).get(((List) $this$lastOrNull).size() - 1);
        }
    }

    @Nullable
    public static final <T> T f0(@NotNull List<? extends T> $this$lastOrNull) {
        k.e($this$lastOrNull, "$this$lastOrNull");
        if ($this$lastOrNull.isEmpty()) {
            return null;
        }
        return $this$lastOrNull.get($this$lastOrNull.size() - 1);
    }

    public static final <T> T p0(@NotNull Iterable<? extends T> $this$single) {
        k.e($this$single, "$this$single");
        if ($this$single instanceof List) {
            return q0((List) $this$single);
        }
        Iterator iterator = $this$single.iterator();
        if (iterator.hasNext()) {
            Object single = iterator.next();
            if (!iterator.hasNext()) {
                return single;
            }
            throw new IllegalArgumentException("Collection has more than one element.");
        }
        throw new NoSuchElementException("Collection is empty.");
    }

    public static final <T> T q0(@NotNull List<? extends T> $this$single) {
        k.e($this$single, "$this$single");
        switch ($this$single.size()) {
            case 0:
                throw new NoSuchElementException("List is empty.");
            case 1:
                return $this$single.get(0);
            default:
                throw new IllegalArgumentException("List has more than one element.");
        }
    }

    @Nullable
    public static final <T> T r0(@NotNull Iterable<? extends T> $this$singleOrNull) {
        k.e($this$singleOrNull, "$this$singleOrNull");
        if (!($this$singleOrNull instanceof List)) {
            Iterator iterator = $this$singleOrNull.iterator();
            if (!iterator.hasNext()) {
                return null;
            }
            Object single = iterator.next();
            if (iterator.hasNext()) {
                return null;
            }
            return single;
        } else if (((List) $this$singleOrNull).size() == 1) {
            return ((List) $this$singleOrNull).get(0);
        } else {
            return null;
        }
    }

    @Nullable
    public static final <T> T s0(@NotNull List<? extends T> $this$singleOrNull) {
        k.e($this$singleOrNull, "$this$singleOrNull");
        if ($this$singleOrNull.size() == 1) {
            return $this$singleOrNull.get(0);
        }
        return null;
    }

    @NotNull
    public static final <T> List<T> O(@NotNull Iterable<? extends T> $this$drop, int n) {
        ArrayList list;
        k.e($this$drop, "$this$drop");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested element count " + n + " is less than zero.").toString());
        } else if (n == 0) {
            return C0($this$drop);
        } else {
            if ($this$drop instanceof Collection) {
                int resultSize = ((Collection) $this$drop).size() - n;
                if (resultSize <= 0) {
                    return q.g();
                }
                if (resultSize == 1) {
                    return p.b(c0($this$drop));
                }
                list = new ArrayList(resultSize);
                if ($this$drop instanceof List) {
                    if ($this$drop instanceof RandomAccess) {
                        int size = ((Collection) $this$drop).size();
                        for (int index = n; index < size; index++) {
                            list.add(((List) $this$drop).get(index));
                        }
                    } else {
                        ListIterator listIterator = ((List) $this$drop).listIterator(n);
                        while (listIterator.hasNext()) {
                            list.add(listIterator.next());
                        }
                    }
                    return list;
                }
            } else {
                list = new ArrayList();
            }
            int count = 0;
            for (Object item : $this$drop) {
                if (count >= n) {
                    list.add(item);
                } else {
                    count++;
                }
            }
            return q.n(list);
        }
    }

    @NotNull
    public static final <T> List<T> P(@NotNull List<? extends T> $this$dropLast, int n) {
        k.e($this$dropLast, "$this$dropLast");
        if (n >= 0) {
            return w0($this$dropLast, n.b($this$dropLast.size() - n, 0));
        }
        throw new IllegalArgumentException(("Requested element count " + n + " is less than zero.").toString());
    }

    @NotNull
    public static final <T> List<T> Q(@NotNull Iterable<? extends T> $this$filter, @NotNull l<? super T, Boolean> predicate) {
        k.e($this$filter, "$this$filter");
        k.e(predicate, "predicate");
        ArrayList arrayList = new ArrayList();
        for (Object element$iv : $this$filter) {
            if (predicate.invoke(element$iv).booleanValue()) {
                arrayList.add(element$iv);
            }
        }
        return arrayList;
    }

    @NotNull
    public static final <T> List<T> w0(@NotNull Iterable<? extends T> $this$take, int n) {
        k.e($this$take, "$this$take");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested element count " + n + " is less than zero.").toString());
        } else if (n == 0) {
            return q.g();
        } else {
            if ($this$take instanceof Collection) {
                if (n >= ((Collection) $this$take).size()) {
                    return C0($this$take);
                }
                if (n == 1) {
                    return p.b(R($this$take));
                }
            }
            int count = 0;
            ArrayList list = new ArrayList(n);
            for (Object item : $this$take) {
                list.add(item);
                count++;
                if (count == n) {
                    break;
                }
            }
            return q.n(list);
        }
    }

    @NotNull
    public static final <T> List<T> x0(@NotNull List<? extends T> $this$takeLast, int n) {
        k.e($this$takeLast, "$this$takeLast");
        if (!(n >= 0)) {
            throw new IllegalArgumentException(("Requested element count " + n + " is less than zero.").toString());
        } else if (n == 0) {
            return q.g();
        } else {
            int size = $this$takeLast.size();
            if (n >= size) {
                return C0($this$takeLast);
            }
            if (n == 1) {
                return p.b(d0($this$takeLast));
            }
            ArrayList list = new ArrayList(n);
            if ($this$takeLast instanceof RandomAccess) {
                for (int index = size - n; index < size; index++) {
                    list.add($this$takeLast.get(index));
                }
            } else {
                ListIterator<? extends T> listIterator = $this$takeLast.listIterator(size - n);
                while (listIterator.hasNext()) {
                    list.add(listIterator.next());
                }
            }
            return list;
        }
    }

    @NotNull
    public static final <T extends Comparable<? super T>> List<T> t0(@NotNull Iterable<? extends T> $this$sorted) {
        k.e($this$sorted, "$this$sorted");
        if (!($this$sorted instanceof Collection)) {
            List $this$apply = E0($this$sorted);
            u.v($this$apply);
            return $this$apply;
        } else if (((Collection) $this$sorted).size() <= 1) {
            return C0($this$sorted);
        } else {
            Object[] array = ((Collection) $this$sorted).toArray(new Comparable[0]);
            if (array != null) {
                Comparable[] $this$apply2 = (Comparable[]) array;
                k.n($this$apply2);
                return k.c($this$apply2);
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    @NotNull
    public static final <T> List<T> u0(@NotNull Iterable<? extends T> $this$sortedWith, @NotNull Comparator<? super T> comparator) {
        k.e($this$sortedWith, "$this$sortedWith");
        k.e(comparator, "comparator");
        if (!($this$sortedWith instanceof Collection)) {
            List $this$apply = E0($this$sortedWith);
            u.w($this$apply, comparator);
            return $this$apply;
        } else if (((Collection) $this$sortedWith).size() <= 1) {
            return C0($this$sortedWith);
        } else {
            Object[] $this$apply2 = ((Collection) $this$sortedWith).toArray(new Object[0]);
            if ($this$apply2 != null) {
                k.o($this$apply2, comparator);
                return k.c($this$apply2);
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    @NotNull
    public static final byte[] y0(@NotNull Collection<Byte> $this$toByteArray) {
        k.e($this$toByteArray, "$this$toByteArray");
        byte[] result = new byte[$this$toByteArray.size()];
        int index = 0;
        for (Byte byteValue : $this$toByteArray) {
            result[index] = byteValue.byteValue();
            index++;
        }
        return result;
    }

    @NotNull
    public static final int[] B0(@NotNull Collection<Integer> $this$toIntArray) {
        k.e($this$toIntArray, "$this$toIntArray");
        int[] result = new int[$this$toIntArray.size()];
        int index = 0;
        for (Integer intValue : $this$toIntArray) {
            result[index] = intValue.intValue();
            index++;
        }
        return result;
    }

    @NotNull
    public static final long[] D0(@NotNull Collection<Long> $this$toLongArray) {
        k.e($this$toLongArray, "$this$toLongArray");
        long[] result = new long[$this$toLongArray.size()];
        int index = 0;
        for (Long longValue : $this$toLongArray) {
            result[index] = longValue.longValue();
            index++;
        }
        return result;
    }

    @NotNull
    public static final <T, C extends Collection<? super T>> C z0(@NotNull Iterable<? extends T> $this$toCollection, @NotNull C destination) {
        k.e($this$toCollection, "$this$toCollection");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        for (Object item : $this$toCollection) {
            destination.add(item);
        }
        return destination;
    }

    @NotNull
    public static final <T> HashSet<T> A0(@NotNull Iterable<? extends T> $this$toHashSet) {
        k.e($this$toHashSet, "$this$toHashSet");
        return (HashSet) z0($this$toHashSet, new HashSet(k0.b(r.r($this$toHashSet, 12))));
    }

    @NotNull
    public static final <T> List<T> C0(@NotNull Iterable<? extends T> $this$toList) {
        k.e($this$toList, "$this$toList");
        if (!($this$toList instanceof Collection)) {
            return q.n(E0($this$toList));
        }
        switch (((Collection) $this$toList).size()) {
            case 0:
                return q.g();
            case 1:
                return p.b($this$toList instanceof List ? ((List) $this$toList).get(0) : $this$toList.iterator().next());
            default:
                return F0((Collection) $this$toList);
        }
    }

    @NotNull
    public static final <T> List<T> E0(@NotNull Iterable<? extends T> $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        if ($this$toMutableList instanceof Collection) {
            return F0((Collection) $this$toMutableList);
        }
        return (List) z0($this$toMutableList, new ArrayList());
    }

    @NotNull
    public static final <T> List<T> F0(@NotNull Collection<? extends T> $this$toMutableList) {
        k.e($this$toMutableList, "$this$toMutableList");
        return new ArrayList($this$toMutableList);
    }

    @NotNull
    public static final <T> Set<T> H0(@NotNull Iterable<? extends T> $this$toSet) {
        k.e($this$toSet, "$this$toSet");
        if (!($this$toSet instanceof Collection)) {
            return o0.f((Set) z0($this$toSet, new LinkedHashSet()));
        }
        switch (((Collection) $this$toSet).size()) {
            case 0:
                return o0.d();
            case 1:
                return n0.c($this$toSet instanceof List ? ((List) $this$toSet).get(0) : $this$toSet.iterator().next());
            default:
                return (Set) z0($this$toSet, new LinkedHashSet(k0.b(((Collection) $this$toSet).size())));
        }
    }

    @NotNull
    public static final <T, R> List<R> g0(@NotNull Iterable<? extends T> $this$map, @NotNull l<? super T, ? extends R> transform) {
        k.e($this$map, "$this$map");
        k.e(transform, "transform");
        ArrayList arrayList = new ArrayList(r.r($this$map, 10));
        for (Object item$iv : $this$map) {
            arrayList.add(transform.invoke(item$iv));
        }
        return arrayList;
    }

    /* compiled from: _Collections.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Iterator<? extends T>> {
        final /* synthetic */ Iterable $this_withIndex;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Iterable iterable) {
            super(0);
            this.$this_withIndex = iterable;
        }

        @NotNull
        public final Iterator<T> invoke() {
            return this.$this_withIndex.iterator();
        }
    }

    @NotNull
    public static final <T> Iterable<d0<T>> J0(@NotNull Iterable<? extends T> $this$withIndex) {
        k.e($this$withIndex, "$this$withIndex");
        return new e0(new b($this$withIndex));
    }

    @NotNull
    public static final <T> List<T> N(@NotNull Iterable<? extends T> $this$distinct) {
        k.e($this$distinct, "$this$distinct");
        return C0(G0($this$distinct));
    }

    @NotNull
    public static final <T> Set<T> X(@NotNull Iterable<? extends T> $this$intersect, @NotNull Iterable<? extends T> other) {
        k.e($this$intersect, "$this$intersect");
        k.e(other, "other");
        Set set = G0($this$intersect);
        v.E(set, other);
        return set;
    }

    @NotNull
    public static final <T> Set<T> v0(@NotNull Iterable<? extends T> $this$subtract, @NotNull Iterable<? extends T> other) {
        k.e($this$subtract, "$this$subtract");
        k.e(other, "other");
        Set set = G0($this$subtract);
        v.A(set, other);
        return set;
    }

    @NotNull
    public static final <T> Set<T> G0(@NotNull Iterable<? extends T> $this$toMutableSet) {
        k.e($this$toMutableSet, "$this$toMutableSet");
        if ($this$toMutableSet instanceof Collection) {
            return new LinkedHashSet((Collection) $this$toMutableSet);
        }
        return (Set) z0($this$toMutableSet, new LinkedHashSet());
    }

    @NotNull
    public static final <T> Set<T> I0(@NotNull Iterable<? extends T> $this$union, @NotNull Iterable<? extends T> other) {
        k.e($this$union, "$this$union");
        k.e(other, "other");
        Set set = G0($this$union);
        v.x(set, other);
        return set;
    }

    public static final <T> boolean K(@NotNull Iterable<? extends T> $this$all, @NotNull l<? super T, Boolean> predicate) {
        k.e($this$all, "$this$all");
        k.e(predicate, "predicate");
        if (($this$all instanceof Collection) && ((Collection) $this$all).isEmpty()) {
            return true;
        }
        for (Object element : $this$all) {
            if (!predicate.invoke(element).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static final <T extends Comparable<? super T>> T h0(@NotNull Iterable<? extends T> $this$max) {
        k.e($this$max, "$this$max");
        return i0($this$max);
    }

    @Nullable
    public static final <T extends Comparable<? super T>> T i0(@NotNull Iterable<? extends T> $this$maxOrNull) {
        k.e($this$maxOrNull, "$this$maxOrNull");
        Iterator iterator = $this$maxOrNull.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        Comparable max = (Comparable) iterator.next();
        while (iterator.hasNext()) {
            Comparable e = (Comparable) iterator.next();
            if (max.compareTo(e) < 0) {
                max = e;
            }
        }
        return max;
    }

    @Nullable
    public static final <T extends Comparable<? super T>> T j0(@NotNull Iterable<? extends T> $this$minOrNull) {
        k.e($this$minOrNull, "$this$minOrNull");
        Iterator iterator = $this$minOrNull.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        Comparable min = (Comparable) iterator.next();
        while (iterator.hasNext()) {
            Comparable e = (Comparable) iterator.next();
            if (min.compareTo(e) > 0) {
                min = e;
            }
        }
        return min;
    }

    @NotNull
    public static final <T> List<T> k0(@NotNull Iterable<? extends T> $this$minus, T element) {
        boolean z;
        k.e($this$minus, "$this$minus");
        ArrayList result = new ArrayList(r.r($this$minus, 10));
        boolean removed = false;
        for (Object next : $this$minus) {
            Object it = next;
            if (removed || !k.a(it, element)) {
                z = true;
            } else {
                removed = true;
                z = false;
            }
            if (z) {
                result.add(next);
            }
        }
        return result;
    }

    @NotNull
    public static final <T> List<T> m0(@NotNull Iterable<? extends T> $this$plus, T element) {
        k.e($this$plus, "$this$plus");
        if ($this$plus instanceof Collection) {
            return o0((Collection) $this$plus, element);
        }
        ArrayList result = new ArrayList();
        v.x(result, $this$plus);
        result.add(element);
        return result;
    }

    @NotNull
    public static final <T> List<T> o0(@NotNull Collection<? extends T> $this$plus, T element) {
        k.e($this$plus, "$this$plus");
        ArrayList result = new ArrayList($this$plus.size() + 1);
        result.addAll($this$plus);
        result.add(element);
        return result;
    }

    @NotNull
    public static final <T> List<T> l0(@NotNull Iterable<? extends T> $this$plus, @NotNull Iterable<? extends T> elements) {
        k.e($this$plus, "$this$plus");
        k.e(elements, "elements");
        if ($this$plus instanceof Collection) {
            return n0((Collection) $this$plus, elements);
        }
        ArrayList result = new ArrayList();
        v.x(result, $this$plus);
        v.x(result, elements);
        return result;
    }

    @NotNull
    public static final <T> List<T> n0(@NotNull Collection<? extends T> $this$plus, @NotNull Iterable<? extends T> elements) {
        k.e($this$plus, "$this$plus");
        k.e(elements, "elements");
        if (elements instanceof Collection) {
            ArrayList result = new ArrayList($this$plus.size() + ((Collection) elements).size());
            result.addAll($this$plus);
            result.addAll((Collection) elements);
            return result;
        }
        ArrayList result2 = new ArrayList($this$plus);
        v.x(result2, elements);
        return result2;
    }

    @NotNull
    public static final <T, R> List<kotlin.n<T, R>> K0(@NotNull Iterable<? extends T> $this$zip, @NotNull Iterable<? extends R> other) {
        k.e($this$zip, "$this$zip");
        k.e(other, "other");
        Iterable $this$zip$iv = $this$zip;
        Iterator first$iv = $this$zip$iv.iterator();
        Iterator second$iv = other.iterator();
        ArrayList list$iv = new ArrayList(Math.min(r.r($this$zip$iv, 10), r.r(other, 10)));
        while (first$iv.hasNext() && second$iv.hasNext()) {
            list$iv.add(t.a(first$iv.next(), second$iv.next()));
        }
        return list$iv;
    }

    public static /* synthetic */ Appendable Z(Iterable iterable, Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, l lVar, int i2, Object obj) {
        String str = (i2 & 2) != 0 ? ", " : charSequence;
        CharSequence charSequence5 = "";
        CharSequence charSequence6 = (i2 & 4) != 0 ? charSequence5 : charSequence2;
        if ((i2 & 8) == 0) {
            charSequence5 = charSequence3;
        }
        return Y(iterable, appendable, str, charSequence6, charSequence5, (i2 & 16) != 0 ? -1 : i, (i2 & 32) != 0 ? "..." : charSequence4, (i2 & 64) != 0 ? null : lVar);
    }

    @NotNull
    public static final <T, A extends Appendable> A Y(@NotNull Iterable<? extends T> $this$joinTo, @NotNull A buffer, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence postfix, int limit, @NotNull CharSequence truncated, @Nullable l<? super T, ? extends CharSequence> transform) {
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

    public static /* synthetic */ String b0(Iterable iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, l lVar, int i2, Object obj) {
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
        return a0(iterable, charSequence, charSequence6, charSequence5, i3, charSequence7, lVar);
    }

    @NotNull
    public static final <T> String a0(@NotNull Iterable<? extends T> $this$joinToString, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence postfix, int limit, @NotNull CharSequence truncated, @Nullable l<? super T, ? extends CharSequence> transform) {
        k.e($this$joinToString, "$this$joinToString");
        k.e(separator, "separator");
        k.e(prefix, "prefix");
        k.e(postfix, "postfix");
        k.e(truncated, "truncated");
        String sb = ((StringBuilder) Y($this$joinToString, new StringBuilder(), separator, prefix, postfix, limit, truncated, transform)).toString();
        k.d(sb, "joinTo(StringBuilder(), …ed, transform).toString()");
        return sb;
    }

    @NotNull
    public static final <T> h<T> L(@NotNull Iterable<? extends T> $this$asSequence) {
        k.e($this$asSequence, "$this$asSequence");
        return new a($this$asSequence);
    }
}
