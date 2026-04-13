package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import androidx.exifinterface.media.ExifInterface;
import com.didichuxing.doraemonkit.constant.SpInputType;
import com.meituan.robust.Constants;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.q;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.ranges.g;
import kotlin.ranges.n;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassMapperLite.kt */
public final class b {
    private static final Map<String, String> a;
    public static final b b = new b();

    /* compiled from: ClassMapperLite.kt */
    public static final class a extends l implements p<String, String, x> {
        final /* synthetic */ Map $this_apply;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Map map) {
            super(2);
            this.$this_apply = map;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((String) obj, (String) obj2);
            return x.a;
        }

        public final void invoke(@NotNull String kotlinSimpleName, @NotNull String javaInternalName) {
            k.f(kotlinSimpleName, "kotlinSimpleName");
            k.f(javaInternalName, "javaInternalName");
            this.$this_apply.put("kotlin/" + kotlinSimpleName, Constants.OBJECT_TYPE + javaInternalName + ';');
        }
    }

    static {
        Map linkedHashMap = new LinkedHashMap();
        Map $this$apply = linkedHashMap;
        List primitives = q.j(SpInputType.BOOLEAN, "Z", "Char", "C", "Byte", "B", "Short", ExifInterface.LATITUDE_SOUTH, "Int", "I", SpInputType.FLOAT, "F", SpInputType.LONG, "J", "Double", "D");
        g k = n.k(q.h(primitives), 2);
        int i = k.a();
        int b2 = k.b();
        int e = k.e();
        if (e < 0 ? i >= b2 : i <= b2) {
            while (true) {
                $this$apply.put("kotlin/" + ((String) primitives.get(i)), primitives.get(i + 1));
                $this$apply.put("kotlin/" + ((String) primitives.get(i)) + "Array", '[' + ((String) primitives.get(i + 1)));
                if (i == b2) {
                    break;
                }
                i += e;
            }
        }
        $this$apply.put("kotlin/Unit", ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        a $fun$add$1 = new a($this$apply);
        $fun$add$1.invoke("Any", "java/lang/Object");
        $fun$add$1.invoke("Nothing", "java/lang/Void");
        $fun$add$1.invoke("Annotation", "java/lang/annotation/Annotation");
        for (String klass : q.j(SpInputType.STRING, "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum")) {
            $fun$add$1.invoke(klass, "java/lang/" + klass);
        }
        for (String klass2 : q.j("Iterator", "Collection", "List", "Set", com.leedarson.bean.Constants.SERVICE_MAP, "ListIterator")) {
            $fun$add$1.invoke("collections/" + klass2, "java/util/" + klass2);
            $fun$add$1.invoke("collections/Mutable" + klass2, "java/util/" + klass2);
        }
        $fun$add$1.invoke("collections/Iterable", "java/lang/Iterable");
        $fun$add$1.invoke("collections/MutableIterable", "java/lang/Iterable");
        $fun$add$1.invoke("collections/Map.Entry", "java/util/Map$Entry");
        $fun$add$1.invoke("collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        for (int i2 = 0; i2 <= 22; i2++) {
            $fun$add$1.invoke("Function" + i2, "kotlin/jvm/functions/Function" + i2);
            $fun$add$1.invoke("reflect/KFunction" + i2, "kotlin/reflect/KFunction");
        }
        for (String klass3 : q.j("Char", "Byte", "Short", "Int", SpInputType.FLOAT, SpInputType.LONG, "Double", SpInputType.STRING, "Enum")) {
            $fun$add$1.invoke(klass3 + ".Companion", "kotlin/jvm/internal/" + klass3 + "CompanionObject");
        }
        a = linkedHashMap;
    }

    private b() {
    }

    @NotNull
    public static final String a(@NotNull String classId) {
        k.f(classId, "classId");
        String str = a.get(classId);
        if (str != null) {
            return str;
        }
        return Constants.OBJECT_TYPE + w.G(classId, '.', '$', false, 4, (Object) null) + ';';
    }
}
