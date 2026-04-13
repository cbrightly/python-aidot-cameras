package okio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import kotlin.collections.d;
import kotlin.collections.l;
import kotlin.collections.q;
import kotlin.collections.u;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Options.kt */
public final class s extends d<f> implements RandomAccess {
    @NotNull
    public static final a d = new a((DefaultConstructorMarker) null);
    @NotNull
    private final f[] f;
    @NotNull
    private final int[] q;

    @NotNull
    public static final s k(@NotNull f... fVarArr) {
        return d.d(fVarArr);
    }

    private s(f[] byteStrings, int[] trie) {
        this.f = byteStrings;
        this.q = trie;
    }

    public /* synthetic */ s(f[] byteStrings, int[] trie, DefaultConstructorMarker $constructor_marker) {
        this(byteStrings, trie);
    }

    public /* bridge */ boolean b(f fVar) {
        return super.contains(fVar);
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof f) {
            return b((f) obj);
        }
        return false;
    }

    public /* bridge */ int h(f fVar) {
        return super.indexOf(fVar);
    }

    public /* bridge */ int i(f fVar) {
        return super.lastIndexOf(fVar);
    }

    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof f) {
            return h((f) obj);
        }
        return -1;
    }

    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof f) {
            return i((f) obj);
        }
        return -1;
    }

    @NotNull
    public final f[] f() {
        return this.f;
    }

    @NotNull
    public final int[] g() {
        return this.q;
    }

    public int a() {
        return this.f.length;
    }

    @NotNull
    /* renamed from: e */
    public f get(int index) {
        return this.f[index];
    }

    /* compiled from: Options.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final s d(@NotNull f... byteStrings) {
            f[] fVarArr = byteStrings;
            k.e(fVarArr, "byteStrings");
            if (fVarArr.length == 0) {
                return new s(new f[0], new int[]{0, -1}, (DefaultConstructorMarker) null);
            }
            List list = l.d0(byteStrings);
            u.v(list);
            f[] fVarArr2 = byteStrings;
            ArrayList arrayList = new ArrayList(fVarArr2.length);
            for (f fVar : fVarArr2) {
                arrayList.add(-1);
            }
            ArrayList arrayList2 = arrayList;
            Object[] array = arrayList.toArray(new Integer[0]);
            if (array != null) {
                Integer[] numArr = (Integer[]) array;
                List indexes = q.m((Integer[]) Arrays.copyOf(numArr, numArr.length));
                f[] fVarArr3 = byteStrings;
                int index$iv = 0;
                int length = fVarArr3.length;
                int i = 0;
                while (i < length) {
                    indexes.set(q.f(list, fVarArr3[i], 0, 0, 6, (Object) null), Integer.valueOf(index$iv));
                    i++;
                    index$iv++;
                }
                if (((f) list.get(0)).size() > 0) {
                    int a = 0;
                    while (a < list.size()) {
                        f prefix = (f) list.get(a);
                        int b = a + 1;
                        while (b < list.size()) {
                            f byteString = (f) list.get(b);
                            if (!byteString.startsWith(prefix)) {
                                continue;
                                break;
                            }
                            if (!(byteString.size() != prefix.size())) {
                                throw new IllegalArgumentException(("duplicate option: " + byteString).toString());
                            } else if (((Number) indexes.get(b)).intValue() > ((Number) indexes.get(a)).intValue()) {
                                list.remove(b);
                                indexes.remove(b);
                            } else {
                                b++;
                            }
                        }
                        a++;
                    }
                    c trieBytes = new c();
                    int i2 = a;
                    List list2 = indexes;
                    b(this, 0, trieBytes, 0, list, 0, 0, indexes, 53, (Object) null);
                    int[] trie = new int[((int) c(trieBytes))];
                    int i3 = 0;
                    while (!trieBytes.r0()) {
                        trie[i3] = trieBytes.readInt();
                        i3++;
                    }
                    Object[] copyOf = Arrays.copyOf(fVarArr, fVarArr.length);
                    k.d(copyOf, "java.util.Arrays.copyOf(this, size)");
                    return new s((f[]) copyOf, trie, (DefaultConstructorMarker) null);
                }
                throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }

        static /* synthetic */ void b(a aVar, long j, c cVar, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            int i5;
            int i6;
            int i7;
            long j2 = (i4 & 1) != 0 ? 0 : j;
            if ((i4 & 4) != 0) {
                i5 = 0;
            } else {
                i5 = i;
            }
            if ((i4 & 16) != 0) {
                i6 = 0;
            } else {
                i6 = i2;
            }
            if ((i4 & 32) != 0) {
                i7 = list.size();
            } else {
                i7 = i3;
            }
            aVar.a(j2, cVar, i5, list, i6, i7, list2);
        }

        private final void a(long nodeOffset, c node, int byteStringOffset, List<? extends f> byteStrings, int fromIndex, int toIndex, List<Integer> indexes) {
            int fromIndex2;
            f from;
            int prefixIndex;
            f from2;
            f to;
            f to2;
            int selectChoiceCount;
            int rangeEnd;
            f from3;
            int prefixIndex2;
            int fromIndex3;
            c cVar = node;
            int i = byteStringOffset;
            List<? extends f> list = byteStrings;
            int i2 = toIndex;
            List<Integer> list2 = indexes;
            boolean z = false;
            int i3 = fromIndex;
            if (i3 < i2) {
                int i4 = i3;
                while (i4 < i2) {
                    if (((f) list.get(i4)).size() >= i) {
                        i4++;
                    } else {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                }
                int fromIndex4 = fromIndex;
                f from4 = (f) list.get(fromIndex4);
                f to3 = (f) list.get(i2 - 1);
                if (i == from4.size()) {
                    int prefixIndex3 = list2.get(fromIndex4).intValue();
                    int fromIndex5 = fromIndex4 + 1;
                    fromIndex2 = fromIndex5;
                    from = (f) list.get(fromIndex5);
                    prefixIndex = prefixIndex3;
                } else {
                    fromIndex2 = fromIndex4;
                    from = from4;
                    prefixIndex = -1;
                }
                if (from.getByte(i) != to3.getByte(i)) {
                    int i5 = fromIndex2 + 1;
                    int selectChoiceCount2 = 1;
                    while (i5 < i2) {
                        int i6 = i5;
                        if (((f) list.get(i6 - 1)).getByte(i) != ((f) list.get(i6)).getByte(i)) {
                            selectChoiceCount2++;
                        }
                        i5 = i6 + 1;
                    }
                    f to4 = to3;
                    long childNodesOffset = nodeOffset + c(cVar) + ((long) 2) + ((long) (selectChoiceCount2 * 2));
                    cVar.writeInt(selectChoiceCount2);
                    cVar.writeInt(prefixIndex);
                    for (int i7 = fromIndex2; i7 < i2; i7++) {
                        int rangeByte = ((f) list.get(i7)).getByte(i);
                        if (i7 == fromIndex2 || rangeByte != ((f) list.get(i7 - 1)).getByte(i)) {
                            cVar.writeInt(255 & rangeByte);
                        }
                    }
                    c childNodes = new c();
                    int rangeStart = fromIndex2;
                    while (rangeStart < i2) {
                        byte rangeByte2 = ((f) list.get(rangeStart)).getByte(i);
                        int rangeEnd2 = toIndex;
                        int i8 = rangeStart + 1;
                        while (true) {
                            if (i8 >= i2) {
                                i8 = rangeEnd2;
                                break;
                            } else if (rangeByte2 != ((f) list.get(i8)).getByte(i)) {
                                int rangeEnd3 = i8;
                                break;
                            } else {
                                i8++;
                            }
                        }
                        if (rangeStart + 1 == i8 && i + 1 == ((f) list.get(rangeStart)).size()) {
                            cVar.writeInt(list2.get(rangeStart).intValue());
                            rangeEnd = i8;
                            selectChoiceCount = selectChoiceCount2;
                            byte b = rangeByte2;
                            prefixIndex2 = prefixIndex;
                            from3 = from;
                            fromIndex3 = fromIndex2;
                            to2 = to4;
                            int i9 = rangeStart;
                        } else {
                            cVar.writeInt(((int) (childNodesOffset + c(childNodes))) * -1);
                            rangeEnd = i8;
                            selectChoiceCount = selectChoiceCount2;
                            byte b2 = rangeByte2;
                            prefixIndex2 = prefixIndex;
                            from3 = from;
                            fromIndex3 = fromIndex2;
                            to2 = to4;
                            int i10 = rangeStart;
                            a(childNodesOffset, childNodes, i + 1, byteStrings, rangeStart, rangeEnd, indexes);
                        }
                        rangeStart = rangeEnd;
                        int i11 = fromIndex;
                        fromIndex2 = fromIndex3;
                        prefixIndex = prefixIndex2;
                        from = from3;
                        selectChoiceCount2 = selectChoiceCount;
                        to4 = to2;
                        list = byteStrings;
                        list2 = indexes;
                    }
                    int i12 = selectChoiceCount2;
                    f to5 = to4;
                    int i13 = rangeStart;
                    cVar.writeAll(childNodes);
                    int i14 = fromIndex2;
                    int i15 = prefixIndex;
                    f fVar = from;
                    f fVar2 = to5;
                    List<? extends f> list3 = byteStrings;
                    List<Integer> list4 = indexes;
                    return;
                }
                int prefixIndex4 = prefixIndex;
                f from5 = from;
                int fromIndex6 = fromIndex2;
                f to6 = to3;
                int min = Math.min(from5.size(), to6.size());
                int scanByteCount = 0;
                int i16 = i;
                while (true) {
                    if (i16 >= min) {
                        from2 = from5;
                        to = to6;
                        break;
                    }
                    from2 = from5;
                    to = to6;
                    if (from2.getByte(i16) != to.getByte(i16)) {
                        break;
                    }
                    scanByteCount++;
                    i16++;
                    to6 = to;
                    from5 = from2;
                }
                long childNodesOffset2 = nodeOffset + c(cVar) + ((long) 2) + ((long) scanByteCount) + 1;
                cVar.writeInt(-scanByteCount);
                cVar.writeInt(prefixIndex4);
                int i17 = i + scanByteCount;
                for (int i18 = i; i18 < i17; i18++) {
                    cVar.writeInt(from2.getByte(i18) & 255);
                }
                if (fromIndex6 + 1 == i2) {
                    int fromIndex7 = fromIndex6;
                    if (i + scanByteCount == ((f) byteStrings.get(fromIndex7)).size()) {
                        z = true;
                    }
                    if (z) {
                        int i19 = prefixIndex4;
                        cVar.writeInt(indexes.get(fromIndex7).intValue());
                        int i20 = fromIndex7;
                        f fVar3 = to;
                        f fVar4 = from2;
                        return;
                    }
                    throw new IllegalStateException("Check failed.".toString());
                }
                int fromIndex8 = fromIndex6;
                int i21 = prefixIndex4;
                List<? extends f> list5 = byteStrings;
                List<Integer> list6 = indexes;
                c childNodes2 = new c();
                cVar.writeInt(((int) (childNodesOffset2 + c(childNodes2))) * -1);
                int i22 = scanByteCount;
                int i23 = fromIndex8;
                f fVar5 = to;
                f fVar6 = from2;
                a(childNodesOffset2, childNodes2, i + scanByteCount, byteStrings, fromIndex8, toIndex, indexes);
                cVar.writeAll(childNodes2);
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        private final long c(c $this$intCount) {
            return $this$intCount.e1() / ((long) 4);
        }
    }
}
