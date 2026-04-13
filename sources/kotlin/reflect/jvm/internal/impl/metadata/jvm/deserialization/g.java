package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.network.ui.NetworkListView;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.d0;
import kotlin.collections.k0;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.a;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmNameResolver.kt */
public final class g implements c {
    @NotNull
    private static final List<String> a;
    private static final Map<String, Integer> b;
    public static final a c = new a((DefaultConstructorMarker) null);
    private final Set<Integer> d;
    @NotNull
    private final List<a.e.c> e;
    @NotNull
    private final a.e f;
    @NotNull
    private final String[] g;

    public g(@NotNull a.e types, @NotNull String[] strings) {
        k.f(types, "types");
        k.f(strings, "strings");
        this.f = types;
        this.g = strings;
        List $this$run = types.getLocalNameList();
        this.d = $this$run.isEmpty() ? o0.d() : y.H0($this$run);
        ArrayList arrayList = new ArrayList();
        ArrayList $this$apply = arrayList;
        List records = types.getRecordList();
        $this$apply.ensureCapacity(records.size());
        for (a.e.c record : records) {
            k.b(record, NetworkListView.KEY_RECORD);
            int range = record.getRange();
            for (int i = 0; i < range; i++) {
                int i2 = i;
                $this$apply.add(record);
            }
        }
        $this$apply.trimToSize();
        this.e = arrayList;
    }

    @NotNull
    public String getString(int index) {
        String string;
        int i = index;
        a.e.c record = this.e.get(i);
        if (record.hasString()) {
            string = record.getString();
        } else {
            if (record.hasPredefinedIndex()) {
                List<String> list = a;
                int size = list.size();
                int predefinedIndex = record.getPredefinedIndex();
                if (predefinedIndex >= 0 && size > predefinedIndex) {
                    string = list.get(record.getPredefinedIndex());
                }
            }
            string = this.g[i];
        }
        if (record.getSubstringIndexCount() >= 2) {
            List<Integer> substringIndexList = record.getSubstringIndexList();
            Integer begin = substringIndexList.get(0);
            Integer end = substringIndexList.get(1);
            k.b(begin, "begin");
            if (k.g(0, begin.intValue()) <= 0) {
                int intValue = begin.intValue();
                k.b(end, "end");
                if (k.g(intValue, end.intValue()) <= 0 && k.g(end.intValue(), string.length()) <= 0) {
                    k.b(string, TypedValues.Custom.S_STRING);
                    String substring = string.substring(begin.intValue(), end.intValue());
                    k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    string = substring;
                }
            }
        }
        if (record.getReplaceCharCount() >= 2) {
            List<Integer> replaceCharList = record.getReplaceCharList();
            k.b(string, TypedValues.Custom.S_STRING);
            string = w.G(string, (char) replaceCharList.get(0).intValue(), (char) replaceCharList.get(1).intValue(), false, 4, (Object) null);
        }
        a.e.c.C0394c operation = record.getOperation();
        if (operation == null) {
            operation = a.e.c.C0394c.NONE;
        }
        switch (h.a[operation.ordinal()]) {
            case 2:
                k.b(string, TypedValues.Custom.S_STRING);
                string = w.G(string, '$', '.', false, 4, (Object) null);
                break;
            case 3:
                if (string.length() >= 2) {
                    k.b(string, TypedValues.Custom.S_STRING);
                    String substring2 = string.substring(1, string.length() - 1);
                    k.b(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    string = substring2;
                }
                k.b(string, TypedValues.Custom.S_STRING);
                string = w.G(string, '$', '.', false, 4, (Object) null);
                break;
        }
        k.b(string, TypedValues.Custom.S_STRING);
        return string;
    }

    @NotNull
    public String b(int index) {
        return getString(index);
    }

    public boolean a(int index) {
        return this.d.contains(Integer.valueOf(index));
    }

    /* compiled from: JvmNameResolver.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        List<String> j = q.j("kotlin/Any", "kotlin/Nothing", "kotlin/Unit", "kotlin/Throwable", "kotlin/Number", "kotlin/Byte", "kotlin/Double", "kotlin/Float", "kotlin/Int", "kotlin/Long", "kotlin/Short", "kotlin/Boolean", "kotlin/Char", "kotlin/CharSequence", "kotlin/String", "kotlin/Comparable", "kotlin/Enum", "kotlin/Array", "kotlin/ByteArray", "kotlin/DoubleArray", "kotlin/FloatArray", "kotlin/IntArray", "kotlin/LongArray", "kotlin/ShortArray", "kotlin/BooleanArray", "kotlin/CharArray", "kotlin/Cloneable", "kotlin/Annotation", "kotlin/collections/Iterable", "kotlin/collections/MutableIterable", "kotlin/collections/Collection", "kotlin/collections/MutableCollection", "kotlin/collections/List", "kotlin/collections/MutableList", "kotlin/collections/Set", "kotlin/collections/MutableSet", "kotlin/collections/Map", "kotlin/collections/MutableMap", "kotlin/collections/Map.Entry", "kotlin/collections/MutableMap.MutableEntry", "kotlin/collections/Iterator", "kotlin/collections/MutableIterator", "kotlin/collections/ListIterator", "kotlin/collections/MutableListIterator");
        a = j;
        Iterable $this$associateByTo$iv$iv = y.J0(j);
        Map destination$iv$iv = new LinkedHashMap(n.b(k0.b(r.r($this$associateByTo$iv$iv, 10)), 16));
        for (d0 it : $this$associateByTo$iv$iv) {
            destination$iv$iv.put((String) it.d(), Integer.valueOf(it.c()));
        }
        b = destination$iv$iv;
    }
}
