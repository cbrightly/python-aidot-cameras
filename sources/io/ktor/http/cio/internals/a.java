package io.ktor.http.cio.internals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.collections.q;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: AsciiCharTree.kt */
public final class a<T> {
    public static final C0251a a = new C0251a((DefaultConstructorMarker) null);
    @NotNull
    private final b<T> b;

    /* compiled from: AsciiCharTree.kt */
    public static final class b<T> {
        @NotNull
        private final b<T>[] a;
        private final char b;
        @NotNull
        private final List<T> c;
        @NotNull
        private final List<b<T>> d;

        public b(char ch, @NotNull List<? extends T> exact, @NotNull List<b<T>> children) {
            List<? extends T> list = exact;
            List<b<T>> list2 = children;
            k.f(list, "exact");
            k.f(list2, "children");
            this.b = ch;
            this.c = list;
            this.d = list2;
            b<T>[] bVarArr = new b[256];
            int i = 0;
            for (int i2 = 256; i < i2; i2 = 256) {
                int chi = i;
                Object single$iv = null;
                boolean found$iv = false;
                Iterator<T> it = this.d.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        if (((b) element$iv).b == chi) {
                            if (found$iv) {
                                single$iv = null;
                                break;
                            } else {
                                single$iv = element$iv;
                                found$iv = true;
                            }
                        }
                    } else if (!found$iv) {
                        single$iv = null;
                    }
                }
                bVarArr[i] = (b) single$iv;
                i++;
            }
            this.a = bVarArr;
        }

        @NotNull
        public final List<T> b() {
            return this.c;
        }

        @NotNull
        public final b<T>[] a() {
            return this.a;
        }
    }

    public a(@NotNull b<T> root) {
        k.f(root, "root");
        this.b = root;
    }

    public static /* synthetic */ List b(a aVar, CharSequence charSequence, int i, int i2, boolean z, p pVar, int i3, Object obj) {
        boolean z2;
        int i4 = (i3 & 2) != 0 ? 0 : i;
        if ((i3 & 4) != 0) {
            i2 = charSequence.length();
        }
        int i5 = i2;
        if ((i3 & 8) != 0) {
            z2 = false;
        } else {
            z2 = z;
        }
        return aVar.a(charSequence, i4, i5, z2, pVar);
    }

    @NotNull
    public final List<T> a(@NotNull CharSequence sequence, int fromIdx, int end, boolean lowerCase, @NotNull p<? super Character, ? super Integer, Boolean> stopPredicate) {
        k.f(sequence, "sequence");
        k.f(stopPredicate, "stopPredicate");
        if (!(sequence.length() == 0)) {
            b node = this.b;
            for (int index = fromIdx; index < end; index++) {
                char current = sequence.charAt(index);
                int currentCode = current;
                if (stopPredicate.invoke(Character.valueOf(current), Integer.valueOf(currentCode)).booleanValue()) {
                    break;
                }
                b nextNode = node.a()[currentCode];
                if (nextNode == null) {
                    nextNode = lowerCase ? node.a()[Character.toLowerCase(current)] : null;
                }
                if (nextNode == null) {
                    return q.g();
                }
                node = nextNode;
            }
            return node.b();
        }
        throw new IllegalArgumentException("Couldn't search in char tree for empty string");
    }

    /* renamed from: io.ktor.http.cio.internals.a$a  reason: collision with other inner class name */
    /* compiled from: AsciiCharTree.kt */
    public static final class C0251a {

        /* renamed from: io.ktor.http.cio.internals.a$a$a  reason: collision with other inner class name */
        /* compiled from: AsciiCharTree.kt */
        public static final class C0252a extends l implements kotlin.jvm.functions.l<T, Integer> {
            public static final C0252a INSTANCE = new C0252a();

            C0252a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Integer.valueOf(invoke((CharSequence) obj));
            }

            public final int invoke(@NotNull T it) {
                k.f(it, "it");
                return it.length();
            }
        }

        /* renamed from: io.ktor.http.cio.internals.a$a$b */
        /* compiled from: AsciiCharTree.kt */
        public static final class b extends l implements p<T, Integer, Character> {
            public static final b INSTANCE = new b();

            b() {
                super(2);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return Character.valueOf(invoke((CharSequence) obj, ((Number) obj2).intValue()));
            }

            public final char invoke(@NotNull T s, int idx) {
                k.f(s, "s");
                return s.charAt(idx);
            }
        }

        private C0251a() {
        }

        public /* synthetic */ C0251a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final <T extends CharSequence> a<T> a(@NotNull List<? extends T> from) {
            k.f(from, "from");
            return b(from, C0252a.INSTANCE, b.INSTANCE);
        }

        @NotNull
        public final <T> a<T> b(@NotNull List<? extends T> from, @NotNull kotlin.jvm.functions.l<? super T, Integer> length, @NotNull p<? super T, ? super Integer, Character> charAt) {
            Object maxElem$iv;
            Integer invoke;
            Object it;
            k.f(from, "from");
            k.f(length, "length");
            k.f(charAt, "charAt");
            Iterator iterator$iv = from.iterator();
            if (!iterator$iv.hasNext()) {
                maxElem$iv = null;
            } else {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    Comparable maxValue$iv = length.invoke(maxElem$iv);
                    do {
                        Object e$iv = iterator$iv.next();
                        Comparable v$iv = length.invoke(e$iv);
                        if (maxValue$iv.compareTo(v$iv) < 0) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            }
            if (maxElem$iv == null || (invoke = length.invoke(maxElem$iv)) == null) {
                throw new NoSuchElementException("Unable to build char tree from an empty list");
            }
            int maxLen = invoke.intValue();
            List<? extends T> list = from;
            boolean z = true;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it2 = list.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = false;
                        break;
                    }
                    if (length.invoke(it2.next()).intValue() == 0) {
                        it = 1;
                        continue;
                    } else {
                        it = null;
                        continue;
                    }
                    if (it != null) {
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (!z) {
                ArrayList root = new ArrayList();
                c(root, from, maxLen, 0, length, charAt);
                root.trimToSize();
                return new a<>(new b(0, q.g(), root));
            }
            throw new IllegalArgumentException("There should be no empty entries");
        }

        private final <T> void c(List<b<T>> resultList, List<? extends T> from, int maxLength, int idx, kotlin.jvm.functions.l<? super T, Integer> length, p<? super T, ? super Integer, Character> charAt) {
            kotlin.jvm.functions.l<? super T, Integer> lVar = length;
            Map destination$iv$iv = new LinkedHashMap();
            for (Object it : from) {
                Object key$iv$iv = Character.valueOf(charAt.invoke(it, Integer.valueOf(idx)).charValue());
                Map $this$getOrPut$iv$iv$iv = destination$iv$iv;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    Object answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                    value$iv$iv$iv = answer$iv$iv$iv;
                }
                ((List) value$iv$iv$iv).add(it);
            }
            p<? super T, ? super Integer, Character> pVar = charAt;
            Map $this$forEach$iv = destination$iv$iv;
            for (Map.Entry $dstr$ch$list : $this$forEach$iv.entrySet()) {
                char ch = ((Character) $dstr$ch$list.getKey()).charValue();
                Iterable list = (List) $dstr$ch$list.getValue();
                int nextIdx = idx + 1;
                ArrayList children = new ArrayList();
                C0251a aVar = a.a;
                Iterable $this$filter$iv = list;
                int $i$f$filter = false;
                ArrayList arrayList = new ArrayList();
                Iterator it2 = $this$filter$iv.iterator();
                while (true) {
                    boolean z = false;
                    if (!it2.hasNext()) {
                        break;
                    }
                    Iterable $this$filter$iv2 = $this$filter$iv;
                    Object element$iv$iv = it2.next();
                    int $i$f$filter2 = $i$f$filter;
                    Object it3 = element$iv$iv;
                    Object obj = it3;
                    if (((Number) lVar.invoke(it3)).intValue() > nextIdx) {
                        z = true;
                    }
                    if (z) {
                        arrayList.add(element$iv$iv);
                    }
                    $this$filter$iv = $this$filter$iv2;
                    $i$f$filter = $i$f$filter2;
                }
                int i = $i$f$filter;
                ArrayList children2 = children;
                Map $this$forEach$iv2 = $this$forEach$iv;
                int nextIdx2 = nextIdx;
                aVar.c(children, arrayList, maxLength, nextIdx, length, charAt);
                children2.trimToSize();
                Iterable $this$filter$iv3 = list;
                ArrayList arrayList2 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv3) {
                    Iterable $this$filter$iv4 = $this$filter$iv3;
                    Object it4 = element$iv$iv2;
                    Object obj2 = it4;
                    if (((Number) lVar.invoke(it4)).intValue() == nextIdx2) {
                        arrayList2.add(element$iv$iv2);
                    }
                    $this$filter$iv3 = $this$filter$iv4;
                }
                resultList.add(new b(ch, arrayList2, children2));
                $this$forEach$iv = $this$forEach$iv2;
            }
        }
    }
}
