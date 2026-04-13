package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Indent.kt */
public class p extends o {
    public static /* synthetic */ String h(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return g(str, str2);
    }

    @NotNull
    public static final String g(@NotNull String $this$trimMargin, @NotNull String marginPrefix) {
        k.e($this$trimMargin, "$this$trimMargin");
        k.e(marginPrefix, "marginPrefix");
        return e($this$trimMargin, "", marginPrefix);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ef, code lost:
        r0 = (java.lang.String) r11.invoke(r3);
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String e(@org.jetbrains.annotations.NotNull java.lang.String r36, @org.jetbrains.annotations.NotNull java.lang.String r37, @org.jetbrains.annotations.NotNull java.lang.String r38) {
        /*
            java.lang.String r0 = "$this$replaceIndentByMargin"
            r1 = r36
            kotlin.jvm.internal.k.e(r1, r0)
            java.lang.String r0 = "newIndent"
            r2 = r37
            kotlin.jvm.internal.k.e(r2, r0)
            java.lang.String r0 = "marginPrefix"
            r9 = r38
            kotlin.jvm.internal.k.e(r9, r0)
            boolean r0 = kotlin.text.w.A(r38)
            r0 = r0 ^ 1
            if (r0 == 0) goto L_0x013f
            java.util.List r0 = kotlin.text.x.o0(r36)
            int r3 = r36.length()
            int r4 = r37.length()
            int r5 = r0.size()
            int r4 = r4 * r5
            int r10 = r3 + r4
            kotlin.jvm.functions.l r11 = b(r37)
            r12 = r0
            r13 = 0
            int r14 = kotlin.collections.q.i(r12)
            r15 = r12
            r16 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r8 = r3
            r17 = r15
            r18 = 0
            r19 = r17
            r20 = 0
            r3 = 0
            java.util.Iterator r21 = r19.iterator()
        L_0x0054:
            boolean r4 = r21.hasNext()
            if (r4 == 0) goto L_0x0110
            java.lang.Object r22 = r21.next()
            int r23 = r3 + 1
            if (r3 >= 0) goto L_0x0065
            kotlin.collections.q.q()
        L_0x0065:
            r24 = r3
            r25 = r22
            r26 = 0
            r27 = r25
            java.lang.String r27 = (java.lang.String) r27
            r7 = r24
            r28 = 0
            r29 = 0
            if (r7 == 0) goto L_0x0079
            if (r7 != r14) goto L_0x0084
        L_0x0079:
            boolean r3 = kotlin.text.w.A(r27)
            if (r3 == 0) goto L_0x0084
            r32 = r0
            r1 = r8
            goto L_0x00fd
        L_0x0084:
            r6 = r27
            r30 = 0
            r3 = r6
            r4 = 0
            int r5 = r3.length()
            r32 = r0
            r0 = 0
        L_0x0091:
            r1 = -1
            if (r0 >= r5) goto L_0x00a9
            char r31 = r3.charAt(r0)
            r33 = 0
            boolean r34 = kotlin.text.a.c(r31)
            r31 = r34 ^ 1
            if (r31 == 0) goto L_0x00a3
            goto L_0x00aa
        L_0x00a3:
            int r0 = r0 + 1
            r1 = r36
            goto L_0x0091
        L_0x00a9:
            r0 = r1
        L_0x00aa:
            if (r0 != r1) goto L_0x00b4
            r31 = r7
            r1 = r8
            r3 = r29
            goto L_0x00ed
        L_0x00b4:
            r1 = 0
            r31 = 4
            r33 = 0
            r3 = r6
            r4 = r38
            r5 = r0
            r35 = r6
            r6 = r1
            r1 = r7
            r7 = r31
            r31 = r1
            r1 = r8
            r8 = r33
            boolean r3 = kotlin.text.w.M(r3, r4, r5, r6, r7, r8)
            if (r3 == 0) goto L_0x00e9
            int r3 = r38.length()
            int r3 = r3 + r0
            r4 = r35
            if (r4 == 0) goto L_0x00e1
            java.lang.String r3 = r4.substring(r3)
            java.lang.String r5 = "(this as java.lang.String).substring(startIndex)"
            kotlin.jvm.internal.k.d(r3, r5)
            goto L_0x00ed
        L_0x00e1:
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            java.lang.String r5 = "null cannot be cast to non-null type java.lang.String"
            r3.<init>(r5)
            throw r3
        L_0x00e9:
            r4 = r35
            r3 = r29
        L_0x00ed:
            if (r3 == 0) goto L_0x00fa
            java.lang.Object r0 = r11.invoke(r3)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x00fa
            r29 = r0
            goto L_0x00fc
        L_0x00fa:
            r29 = r27
        L_0x00fc:
        L_0x00fd:
            if (r29 == 0) goto L_0x0106
            r0 = r29
            r3 = 0
            r1.add(r0)
            goto L_0x0107
        L_0x0106:
        L_0x0107:
            r8 = r1
            r3 = r23
            r0 = r32
            r1 = r36
            goto L_0x0054
        L_0x0110:
            r32 = r0
            r1 = r8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r10)
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 124(0x7c, float:1.74E-43)
            r26 = 0
            java.lang.String r19 = "\n"
            r17 = r1
            r18 = r0
            java.lang.Appendable r0 = kotlin.collections.y.Z(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)
            java.lang.StringBuilder r0 = (java.lang.StringBuilder) r0
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "mapIndexedNotNull { inde…\"\\n\")\n        .toString()"
            kotlin.jvm.internal.k.d(r0, r1)
            return r0
        L_0x013f:
            r0 = 0
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "marginPrefix must be non-blank string."
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.p.e(java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    @NotNull
    public static final String f(@NotNull String $this$trimIndent) {
        k.e($this$trimIndent, "$this$trimIndent");
        return d($this$trimIndent, "");
    }

    @NotNull
    public static final String d(@NotNull String $this$replaceIndent, @NotNull String newIndent) {
        Object it$iv$iv$iv;
        Object obj;
        k.e($this$replaceIndent, "$this$replaceIndent");
        k.e(newIndent, "newIndent");
        List lines = x.o0($this$replaceIndent);
        ArrayList arrayList = new ArrayList();
        for (T next : lines) {
            if (!w.A((String) next)) {
                arrayList.add(next);
            }
        }
        Iterable<String> $this$mapTo$iv$iv = arrayList;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (String p1 : $this$mapTo$iv$iv) {
            destination$iv$iv.add(Integer.valueOf(c(p1)));
        }
        Integer num = (Integer) y.j0(destination$iv$iv);
        int minCommonIndent = num != null ? num.intValue() : 0;
        int resultSizeEstimate$iv = $this$replaceIndent.length() + (newIndent.length() * lines.size());
        l indentAddFunction$iv = b(newIndent);
        List $this$reindent$iv = lines;
        int lastIndex$iv = q.i($this$reindent$iv);
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv = 0;
        for (Object element$iv$iv$iv : $this$reindent$iv) {
            int index$iv$iv$iv$iv = index$iv$iv$iv + 1;
            if (index$iv$iv$iv < 0) {
                q.q();
            }
            String value$iv = (String) element$iv$iv$iv;
            List lines2 = lines;
            int index$iv = index$iv$iv$iv;
            if ((index$iv == 0 || index$iv == lastIndex$iv) && w.A(value$iv)) {
                it$iv$iv$iv = null;
            } else {
                int i = index$iv;
                String line = z.f1(value$iv, minCommonIndent);
                it$iv$iv$iv = (line == null || (obj = (String) indentAddFunction$iv.invoke(line)) == null) ? value$iv : obj;
            }
            if (it$iv$iv$iv != null) {
                destination$iv$iv$iv.add(it$iv$iv$iv);
            }
            index$iv$iv$iv = index$iv$iv$iv$iv;
            lines = lines2;
        }
        String sb = ((StringBuilder) y.Z(destination$iv$iv$iv, new StringBuilder(resultSizeEstimate$iv), "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 124, (Object) null)).toString();
        k.d(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }

    private static final int c(String $this$indentWidth) {
        CharSequence $this$indexOfFirst$iv = $this$indentWidth;
        int length = $this$indexOfFirst$iv.length();
        int index$iv = 0;
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            } else if ((a.c($this$indexOfFirst$iv.charAt(index$iv)) ^ 1) != 0) {
                break;
            } else {
                index$iv++;
            }
        }
        int it = index$iv;
        return it == -1 ? $this$indentWidth.length() : it;
    }

    /* compiled from: Indent.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<String, String> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull String line) {
            k.e(line, "line");
            return line;
        }
    }

    private static final l<String, String> b(String indent) {
        if (indent.length() == 0) {
            return a.INSTANCE;
        }
        return new b(indent);
    }

    /* compiled from: Indent.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<String, String> {
        final /* synthetic */ String $indent;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String str) {
            super(1);
            this.$indent = str;
        }

        @NotNull
        public final String invoke(@NotNull String line) {
            k.e(line, "line");
            return this.$indent + line;
        }
    }
}
