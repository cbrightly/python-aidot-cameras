package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.f;
import com.typesafe.config.impl.SerializedConfigValue;
import com.typesafe.config.impl.b0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: SimpleConfigOrigin */
public final class f0 implements f {
    private final String a;
    private final int b;
    private final int c;
    private final r d;
    private final String e;
    private final String f;
    private final List<String> g;

    protected f0(String description, int lineNumber, int endLineNumber, r originType, String urlOrNull, String resourceOrNull, List<String> commentsOrNull) {
        if (description != null) {
            this.a = description;
            this.b = lineNumber;
            this.c = endLineNumber;
            this.d = originType;
            this.e = urlOrNull;
            this.f = resourceOrNull;
            this.g = commentsOrNull;
            return;
        }
        throw new ConfigException.BugOrBroken("description may not be null");
    }

    static f0 l(String description) {
        return new f0(description, -1, -1, r.GENERIC, (String) null, (String) null, (List<String>) null);
    }

    public f0 p(int lineNumber) {
        if (lineNumber == this.b && lineNumber == this.c) {
            return this;
        }
        return new f0(this.a, lineNumber, lineNumber, this.d, this.e, this.f, this.g);
    }

    public String a() {
        int i = this.b;
        if (i < 0) {
            return this.a;
        }
        if (this.c == i) {
            return this.a + ": " + this.b;
        }
        return this.a + ": " + this.b + "-" + this.c;
    }

    public boolean equals(Object other) {
        if (!(other instanceof f0)) {
            return false;
        }
        f0 otherOrigin = (f0) other;
        if (!this.a.equals(otherOrigin.a) || this.b != otherOrigin.b || this.c != otherOrigin.c || this.d != otherOrigin.d || !g.a(this.e, otherOrigin.e) || !g.a(this.f, otherOrigin.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = this.b;
        int h = this.c;
        int h2 = (this.d.hashCode() + ((h + ((i + ((this.a.hashCode() + 41) * 41)) * 41)) * 41)) * 41;
        String str = this.e;
        if (str != null) {
            h2 = (str.hashCode() + h2) * 41;
        }
        String str2 = this.f;
        if (str2 != null) {
            return (str2.hashCode() + h2) * 41;
        }
        return h2;
    }

    public String toString() {
        return "ConfigOrigin(" + this.a + ")";
    }

    public int b() {
        return this.b;
    }

    public List<String> d() {
        List<String> list = this.g;
        if (list != null) {
            return Collections.unmodifiableList(list);
        }
        return Collections.emptyList();
    }

    private static f0 k(f0 a2, f0 b2) {
        r mergedType;
        String aDesc;
        String bDesc;
        int mergedEndLine;
        int mergedStartLine;
        String mergedDesc;
        String mergedURL;
        String mergedResource;
        List<String> mergedComments;
        int mergedStartLine2;
        f0 f0Var = a2;
        f0 f0Var2 = b2;
        if (f0Var.d == f0Var2.d) {
            mergedType = f0Var.d;
        } else {
            mergedType = r.GENERIC;
        }
        String aDesc2 = f0Var.a;
        String bDesc2 = f0Var2.a;
        if (aDesc2.startsWith("merge of ")) {
            aDesc = aDesc2.substring("merge of ".length());
        } else {
            aDesc = aDesc2;
        }
        if (bDesc2.startsWith("merge of ")) {
            bDesc = bDesc2.substring("merge of ".length());
        } else {
            bDesc = bDesc2;
        }
        if (aDesc.equals(bDesc)) {
            String mergedDesc2 = aDesc;
            int mergedStartLine3 = f0Var.b;
            if (mergedStartLine3 < 0) {
                mergedStartLine2 = f0Var2.b;
            } else {
                int i = f0Var2.b;
                if (i < 0) {
                    mergedStartLine2 = f0Var.b;
                } else {
                    mergedStartLine2 = Math.min(mergedStartLine3, i);
                }
            }
            mergedDesc = mergedDesc2;
            mergedStartLine = mergedStartLine2;
            mergedEndLine = Math.max(f0Var.c, f0Var2.c);
        } else {
            String aFull = a2.a();
            String bFull = b2.a();
            if (aFull.startsWith("merge of ")) {
                aFull = aFull.substring("merge of ".length());
            }
            if (bFull.startsWith("merge of ")) {
                bFull = bFull.substring("merge of ".length());
            }
            mergedDesc = "merge of " + aFull + "," + bFull;
            mergedStartLine = -1;
            mergedEndLine = -1;
        }
        if (g.a(f0Var.e, f0Var2.e)) {
            mergedURL = f0Var.e;
        } else {
            mergedURL = null;
        }
        if (g.a(f0Var.f, f0Var2.f)) {
            mergedResource = f0Var.f;
        } else {
            mergedResource = null;
        }
        if (g.a(f0Var.g, f0Var2.g)) {
            mergedComments = f0Var.g;
        } else {
            List<String> mergedComments2 = new ArrayList<>();
            List<String> list = f0Var.g;
            if (list != null) {
                mergedComments2.addAll(list);
            }
            List<String> list2 = f0Var2.g;
            if (list2 != null) {
                mergedComments2.addAll(list2);
            }
            mergedComments = mergedComments2;
        }
        return new f0(mergedDesc, mergedStartLine, mergedEndLine, mergedType, mergedURL, mergedResource, mergedComments);
    }

    private static int m(f0 a2, f0 b2) {
        int count = 0;
        if (a2.d == b2.d) {
            count = 0 + 1;
        }
        if (!a2.a.equals(b2.a)) {
            return count;
        }
        int count2 = count + 1;
        if (a2.b == b2.b) {
            count2++;
        }
        if (a2.c == b2.c) {
            count2++;
        }
        if (g.a(a2.e, b2.e)) {
            count2++;
        }
        if (g.a(a2.f, b2.f)) {
            return count2 + 1;
        }
        return count2;
    }

    private static f0 j(f0 a2, f0 b2, f0 c2) {
        if (m(a2, b2) >= m(b2, c2)) {
            return k(k(a2, b2), c2);
        }
        return k(a2, k(b2, c2));
    }

    static f h(f a2, f b2) {
        return k((f0) a2, (f0) b2);
    }

    static f i(Collection<? extends f> stack) {
        if (stack.isEmpty()) {
            throw new ConfigException.BugOrBroken("can't merge empty list of origins");
        } else if (stack.size() == 1) {
            return (f) stack.iterator().next();
        } else {
            if (stack.size() == 2) {
                Iterator<? extends f> it = stack.iterator();
                return k((f0) it.next(), (f0) it.next());
            }
            List<SimpleConfigOrigin> remaining = new ArrayList<>();
            for (f o : stack) {
                remaining.add((f0) o);
            }
            while (remaining.size() > 2) {
                remaining.remove(remaining.size() - 1);
                f0 b2 = (f0) remaining.get(remaining.size() - 1);
                remaining.remove(remaining.size() - 1);
                f0 a2 = (f0) remaining.get(remaining.size() - 1);
                remaining.remove(remaining.size() - 1);
                remaining.add(j(a2, b2, (f0) remaining.get(remaining.size() - 1)));
            }
            return i(remaining);
        }
    }

    /* access modifiers changed from: package-private */
    public Map<b0.c, Object> n() {
        Map<SerializedConfigValue.SerializedField, Object> m = new EnumMap<>(b0.c.class);
        m.put(b0.c.ORIGIN_DESCRIPTION, this.a);
        int i = this.b;
        if (i >= 0) {
            m.put(b0.c.ORIGIN_LINE_NUMBER, Integer.valueOf(i));
        }
        int i2 = this.c;
        if (i2 >= 0) {
            m.put(b0.c.ORIGIN_END_LINE_NUMBER, Integer.valueOf(i2));
        }
        m.put(b0.c.ORIGIN_TYPE, Integer.valueOf(this.d.ordinal()));
        String str = this.e;
        if (str != null) {
            m.put(b0.c.ORIGIN_URL, str);
        }
        String str2 = this.f;
        if (str2 != null) {
            m.put(b0.c.ORIGIN_RESOURCE, str2);
        }
        List<String> list = this.g;
        if (list != null) {
            m.put(b0.c.ORIGIN_COMMENTS, list);
        }
        return m;
    }

    /* access modifiers changed from: package-private */
    public Map<b0.c, Object> o(f0 baseOrigin) {
        Map<b0.c, Object> map;
        if (baseOrigin != null) {
            map = baseOrigin.n();
        } else {
            map = Collections.emptyMap();
        }
        return e(map, n());
    }

    static Map<b0.c, Object> e(Map<b0.c, Object> base, Map<b0.c, Object> child) {
        Map<SerializedConfigValue.SerializedField, Object> m = new EnumMap<>(child);
        for (Map.Entry<SerializedConfigValue.SerializedField, Object> baseEntry : base.entrySet()) {
            b0.c f2 = (b0.c) baseEntry.getKey();
            if (m.containsKey(f2) && g.a(baseEntry.getValue(), m.get(f2))) {
                m.remove(f2);
            } else if (!m.containsKey(f2)) {
                switch (a.a[f2.ordinal()]) {
                    case 1:
                        throw new ConfigException.BugOrBroken("origin missing description field? " + child);
                    case 2:
                        m.put(b0.c.ORIGIN_LINE_NUMBER, -1);
                        break;
                    case 3:
                        m.put(b0.c.ORIGIN_END_LINE_NUMBER, -1);
                        break;
                    case 4:
                        throw new ConfigException.BugOrBroken("should always be an ORIGIN_TYPE field");
                    case 5:
                        m.put(b0.c.ORIGIN_NULL_URL, "");
                        break;
                    case 6:
                        m.put(b0.c.ORIGIN_NULL_RESOURCE, "");
                        break;
                    case 7:
                        m.put(b0.c.ORIGIN_NULL_COMMENTS, "");
                        break;
                    case 8:
                    case 9:
                    case 10:
                        throw new ConfigException.BugOrBroken("computing delta, base object should not contain " + f2 + " " + base);
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        throw new ConfigException.BugOrBroken("should not appear here: " + f2);
                }
            } else {
                continue;
            }
        }
        return m;
    }

    /* compiled from: SimpleConfigOrigin */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b0.c.values().length];
            a = iArr;
            try {
                iArr[b0.c.ORIGIN_DESCRIPTION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b0.c.ORIGIN_LINE_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b0.c.ORIGIN_END_LINE_NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[b0.c.ORIGIN_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[b0.c.ORIGIN_URL.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[b0.c.ORIGIN_RESOURCE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[b0.c.ORIGIN_COMMENTS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[b0.c.ORIGIN_NULL_URL.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[b0.c.ORIGIN_NULL_RESOURCE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[b0.c.ORIGIN_NULL_COMMENTS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[b0.c.END_MARKER.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[b0.c.ROOT_VALUE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[b0.c.ROOT_WAS_CONFIG.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[b0.c.UNKNOWN.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                a[b0.c.VALUE_DATA.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                a[b0.c.VALUE_ORIGIN.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
        }
    }

    static f0 g(Map<b0.c, Object> m) {
        String resourceOrNull;
        Map<b0.c, Object> map = m;
        if (m.isEmpty()) {
            return null;
        }
        String description = (String) map.get(b0.c.ORIGIN_DESCRIPTION);
        Integer lineNumber = (Integer) map.get(b0.c.ORIGIN_LINE_NUMBER);
        Integer endLineNumber = (Integer) map.get(b0.c.ORIGIN_END_LINE_NUMBER);
        Number originTypeOrdinal = (Number) map.get(b0.c.ORIGIN_TYPE);
        if (originTypeOrdinal != null) {
            r originType = r.values()[originTypeOrdinal.byteValue()];
            String urlOrNull = (String) map.get(b0.c.ORIGIN_URL);
            String resourceOrNull2 = (String) map.get(b0.c.ORIGIN_RESOURCE);
            List<String> commentsOrNull = (List) map.get(b0.c.ORIGIN_COMMENTS);
            if (originType == r.RESOURCE && resourceOrNull2 == null) {
                resourceOrNull = description;
            } else {
                resourceOrNull = resourceOrNull2;
            }
            int i = -1;
            int intValue = lineNumber != null ? lineNumber.intValue() : -1;
            if (endLineNumber != null) {
                i = endLineNumber.intValue();
            }
            return new f0(description, intValue, i, originType, urlOrNull, resourceOrNull, commentsOrNull);
        }
        throw new IOException("Missing ORIGIN_TYPE field");
    }

    static Map<b0.c, Object> c(Map<b0.c, Object> base, Map<b0.c, Object> delta) {
        Map<SerializedConfigValue.SerializedField, Object> m = new EnumMap<>(delta);
        for (Map.Entry<b0.c, Object> baseEntry : base.entrySet()) {
            b0.c f2 = (b0.c) baseEntry.getKey();
            if (!delta.containsKey(f2)) {
                switch (a.a[f2.ordinal()]) {
                    case 1:
                        m.put(f2, base.get(f2));
                        break;
                    case 2:
                    case 3:
                    case 4:
                        m.put(f2, base.get(f2));
                        break;
                    case 5:
                        b0.c cVar = b0.c.ORIGIN_NULL_URL;
                        if (!delta.containsKey(cVar)) {
                            m.put(f2, base.get(f2));
                            break;
                        } else {
                            m.remove(cVar);
                            break;
                        }
                    case 6:
                        b0.c cVar2 = b0.c.ORIGIN_NULL_RESOURCE;
                        if (!delta.containsKey(cVar2)) {
                            m.put(f2, base.get(f2));
                            break;
                        } else {
                            m.remove(cVar2);
                            break;
                        }
                    case 7:
                        b0.c cVar3 = b0.c.ORIGIN_NULL_COMMENTS;
                        if (!delta.containsKey(cVar3)) {
                            m.put(f2, base.get(f2));
                            break;
                        } else {
                            m.remove(cVar3);
                            break;
                        }
                    case 8:
                    case 9:
                    case 10:
                        throw new ConfigException.BugOrBroken("applying fields, base object should not contain " + f2 + " " + base);
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        throw new ConfigException.BugOrBroken("should not appear here: " + f2);
                }
            }
        }
        return m;
    }

    static f0 f(f0 baseOrigin, Map<b0.c, Object> delta) {
        Map<b0.c, Object> map;
        if (baseOrigin != null) {
            map = baseOrigin.n();
        } else {
            map = Collections.emptyMap();
        }
        return g(c(map, delta));
    }
}
