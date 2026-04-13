package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import androidx.exifinterface.media.ExifInterface;
import com.meituan.robust.Constants;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmPrimitiveType */
public enum d {
    BOOLEAN(h.BOOLEAN, "boolean", "Z", Constants.LANG_BOOLEAN),
    CHAR(h.CHAR, Constants.CHAR, "C", "java.lang.Character"),
    BYTE(h.BYTE, Constants.BYTE, "B", Constants.LANG_BYTE),
    SHORT(h.SHORT, Constants.SHORT, ExifInterface.LATITUDE_SOUTH, Constants.LANG_SHORT),
    INT(h.INT, Constants.INT, "I", Constants.LANG_INT),
    FLOAT(h.FLOAT, "float", "F", Constants.LANG_FLOAT),
    LONG(h.LONG, Constants.LONG, "J", Constants.LANG_LONG),
    DOUBLE(h.DOUBLE, Constants.DOUBLE, "D", Constants.LANG_DOUBLE);
    
    private static final Set<b> c = null;
    private static final Map<String, d> d = null;
    private static final Map<h, d> f = null;
    private static final Map<String, d> q = null;
    private final String desc;
    private final String name;
    private final h primitiveType;
    private final b wrapperFqName;

    static {
        c = new HashSet();
        d = new HashMap();
        f = new EnumMap(h.class);
        q = new HashMap();
        for (d type : values()) {
            c.add(type.getWrapperFqName());
            d.put(type.getJavaKeywordName(), type);
            f.put(type.getPrimitiveType(), type);
            q.put(type.getDesc(), type);
        }
    }

    @NotNull
    public static d get(@NotNull String name2) {
        if (name2 == null) {
            a(1);
        }
        d result = d.get(name2);
        if (result != null) {
            return result;
        }
        throw new AssertionError("Non-primitive type name passed: " + name2);
    }

    @NotNull
    public static d get(@NotNull h type) {
        if (type == null) {
            a(3);
        }
        d dVar = f.get(type);
        if (dVar == null) {
            a(4);
        }
        return dVar;
    }

    private d(@NotNull h primitiveType2, @NotNull String name2, @NotNull String desc2, @NotNull String wrapperClassName) {
        if (primitiveType2 == null) {
            a(6);
        }
        if (name2 == null) {
            a(7);
        }
        if (desc2 == null) {
            a(8);
        }
        if (wrapperClassName == null) {
            a(9);
        }
        this.primitiveType = primitiveType2;
        this.name = name2;
        this.desc = desc2;
        this.wrapperFqName = new b(wrapperClassName);
    }

    @NotNull
    public h getPrimitiveType() {
        h hVar = this.primitiveType;
        if (hVar == null) {
            a(10);
        }
        return hVar;
    }

    @NotNull
    public String getJavaKeywordName() {
        String str = this.name;
        if (str == null) {
            a(11);
        }
        return str;
    }

    @NotNull
    public String getDesc() {
        String str = this.desc;
        if (str == null) {
            a(12);
        }
        return str;
    }

    @NotNull
    public b getWrapperFqName() {
        b bVar = this.wrapperFqName;
        if (bVar == null) {
            a(13);
        }
        return bVar;
    }
}
