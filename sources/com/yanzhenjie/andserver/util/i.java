package com.yanzhenjie.andserver.util;

import android.text.TextUtils;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.yanzhenjie.andserver.error.InvalidMimeTypeException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

/* compiled from: MimeType */
public class i implements Comparable<i>, Serializable {
    protected static final String WILDCARD_TYPE = "*";
    private static final BitSet c;
    private final Map<String, String> parameters;
    private final String subtype;
    private final String type;

    static {
        BitSet ctl = new BitSet(128);
        for (int i = 0; i <= 31; i++) {
            ctl.set(i);
        }
        ctl.set(NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        BitSet separators = new BitSet(128);
        separators.set(40);
        separators.set(41);
        separators.set(60);
        separators.set(62);
        separators.set(64);
        separators.set(44);
        separators.set(59);
        separators.set(58);
        separators.set(92);
        separators.set(34);
        separators.set(47);
        separators.set(91);
        separators.set(93);
        separators.set(63);
        separators.set(61);
        separators.set(123);
        separators.set(125);
        separators.set(32);
        separators.set(9);
        BitSet bitSet = new BitSet(128);
        c = bitSet;
        bitSet.set(0, 128);
        bitSet.andNot(ctl);
        bitSet.andNot(separators);
    }

    public i(String type2) {
        this(type2, "*");
    }

    public i(String type2, String subtype2) {
        this(type2, subtype2, (Map<String, String>) Collections.emptyMap());
    }

    public i(String type2, String subtype2, Charset charset) {
        this(type2, subtype2, (Map<String, String>) Collections.singletonMap("charset", charset.name()));
    }

    public i(i other, Charset charset) {
        this(other.getType(), other.getSubtype(), a(charset, other.getParameters()));
    }

    public i(i other, Map<String, String> parameters2) {
        this(other.getType(), other.getSubtype(), parameters2);
    }

    public i(String type2, String subtype2, Map<String, String> parameters2) {
        c(type2);
        c(subtype2);
        Locale locale = Locale.ENGLISH;
        this.type = type2.toLowerCase(locale);
        this.subtype = subtype2.toLowerCase(locale);
        if (parameters2 == null || parameters2.isEmpty()) {
            this.parameters = Collections.emptyMap();
            return;
        }
        Map<String, String> map = new f<>(parameters2.size(), locale);
        for (Map.Entry<String, String> entry : parameters2.entrySet()) {
            String attribute = entry.getKey();
            String value = entry.getValue();
            checkParameters(attribute, value);
            map.put(attribute, value);
        }
        this.parameters = Collections.unmodifiableMap(map);
    }

    private void c(String token) {
        int i = 0;
        while (i < token.length()) {
            char ch = token.charAt(i);
            if (c.get(ch)) {
                i++;
            } else {
                throw new IllegalArgumentException("Invalid token character '" + ch + "' in token \"" + token + "\"");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkParameters(String attribute, String value) {
        a.a(attribute, "'attribute' must not be empty.");
        a.a(value, "'value' must not be empty.");
        c(attribute);
        if ("charset".equals(attribute)) {
            Charset.forName(unquote(value));
        } else if (!d(value)) {
            c(value);
        }
    }

    private boolean d(String s) {
        if (s.length() < 2) {
            return false;
        }
        if ((!s.startsWith("\"") || !s.endsWith("\"")) && (!s.startsWith("'") || !s.endsWith("'"))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public String unquote(String s) {
        if (s == null) {
            return null;
        }
        return d(s) ? s.substring(1, s.length() - 1) : s;
    }

    public boolean isWildcardType() {
        return "*".equals(getType());
    }

    public boolean isWildcardSubtype() {
        return "*".equals(getSubtype()) || getSubtype().startsWith("*+");
    }

    public boolean isConcrete() {
        return !isWildcardType() && !isWildcardSubtype();
    }

    public String getType() {
        return this.type;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public Charset getCharset() {
        String charset = getParameter("charset");
        if (charset != null) {
            return Charset.forName(unquote(charset));
        }
        return null;
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public boolean includes(i other) {
        if (other == null) {
            return false;
        }
        if (isWildcardType()) {
            return true;
        }
        if (getType().equals(other.getType())) {
            if (getSubtype().equals(other.getSubtype())) {
                return true;
            }
            if (isWildcardSubtype()) {
                int thisPlusIdx = getSubtype().indexOf(43);
                if (thisPlusIdx == -1) {
                    return true;
                }
                int otherPlusIdx = other.getSubtype().indexOf(43);
                if (otherPlusIdx != -1) {
                    String thisSubtypeNoSuffix = getSubtype().substring(0, thisPlusIdx);
                    if (!getSubtype().substring(thisPlusIdx + 1).equals(other.getSubtype().substring(otherPlusIdx + 1)) || !"*".equals(thisSubtypeNoSuffix)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCompatibleWith(i other) {
        if (other == null) {
            return false;
        }
        if (isWildcardType() || other.isWildcardType()) {
            return true;
        }
        if (getType().equals(other.getType())) {
            if (getSubtype().equals(other.getSubtype())) {
                return true;
            }
            if (isWildcardSubtype() || other.isWildcardSubtype()) {
                int thisPlusIdx = getSubtype().indexOf(43);
                int otherPlusIdx = other.getSubtype().indexOf(43);
                if (thisPlusIdx == -1 && otherPlusIdx == -1) {
                    return true;
                }
                if (!(thisPlusIdx == -1 || otherPlusIdx == -1)) {
                    String thisSubtypeNoSuffix = getSubtype().substring(0, thisPlusIdx);
                    String otherSubtypeNoSuffix = other.getSubtype().substring(0, otherPlusIdx);
                    if (!getSubtype().substring(thisPlusIdx + 1).equals(other.getSubtype().substring(otherPlusIdx + 1)) || (!"*".equals(thisSubtypeNoSuffix) && !"*".equals(otherSubtypeNoSuffix))) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Object other) {
        if (!equalsExcludeParameter(other)) {
            return false;
        }
        return e((i) other);
    }

    public boolean equalsExcludeParameter(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof i)) {
            return false;
        }
        i otherType = (i) other;
        if (!this.type.equalsIgnoreCase(otherType.type) || !this.subtype.equalsIgnoreCase(otherType.subtype)) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0021  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean e(com.yanzhenjie.andserver.util.i r7) {
        /*
            r6 = this;
            java.util.Map<java.lang.String, java.lang.String> r0 = r6.parameters
            int r0 = r0.size()
            java.util.Map<java.lang.String, java.lang.String> r1 = r7.parameters
            int r1 = r1.size()
            r2 = 0
            if (r0 == r1) goto L_0x0010
            return r2
        L_0x0010:
            java.util.Map<java.lang.String, java.lang.String> r0 = r6.parameters
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x001a:
            boolean r1 = r0.hasNext()
            r3 = 1
            if (r1 == 0) goto L_0x0066
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.util.Map<java.lang.String, java.lang.String> r4 = r7.parameters
            boolean r4 = r4.containsKey(r1)
            if (r4 != 0) goto L_0x0030
            return r2
        L_0x0030:
            java.lang.String r4 = "charset"
            boolean r4 = r4.equals(r1)
            if (r4 == 0) goto L_0x004b
            java.nio.charset.Charset r0 = r6.getCharset()
            java.nio.charset.Charset r4 = r7.getCharset()
            if (r0 == 0) goto L_0x004a
            boolean r5 = r0.equals(r4)
            if (r5 != 0) goto L_0x0049
            goto L_0x004a
        L_0x0049:
            return r3
        L_0x004a:
            return r2
        L_0x004b:
            java.util.Map<java.lang.String, java.lang.String> r3 = r6.parameters
            java.lang.Object r3 = r3.get(r1)
            java.lang.String r3 = (java.lang.String) r3
            java.util.Map<java.lang.String, java.lang.String> r4 = r7.parameters
            java.lang.Object r4 = r4.get(r1)
            java.lang.String r4 = (java.lang.String) r4
            if (r3 == 0) goto L_0x0065
            boolean r5 = r3.equals(r4)
            if (r5 != 0) goto L_0x0064
            goto L_0x0065
        L_0x0064:
            goto L_0x001a
        L_0x0065:
            return r2
        L_0x0066:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yanzhenjie.andserver.util.i.e(com.yanzhenjie.andserver.util.i):boolean");
    }

    public int hashCode() {
        return (((this.type.hashCode() * 31) + this.subtype.hashCode()) * 31) + this.parameters.hashCode();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        appendTo(builder);
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    public void appendTo(StringBuilder builder) {
        builder.append(this.type);
        builder.append('/');
        builder.append(this.subtype);
        b(this.parameters, builder);
    }

    private void b(Map<String, String> map, StringBuilder builder) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(';');
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
        }
    }

    public int compareTo(i other) {
        int comp = getType().compareToIgnoreCase(other.getType());
        if (comp != 0) {
            return comp;
        }
        int comp2 = getSubtype().compareToIgnoreCase(other.getSubtype());
        if (comp2 != 0) {
            return comp2;
        }
        int comp3 = getParameters().size() - other.getParameters().size();
        if (comp3 != 0) {
            return comp3;
        }
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        TreeSet<String> thisAttributes = new TreeSet<>(comparator);
        thisAttributes.addAll(getParameters().keySet());
        TreeSet treeSet = new TreeSet(comparator);
        treeSet.addAll(other.getParameters().keySet());
        Iterator<String> thisAttributesIterator = thisAttributes.iterator();
        Iterator<String> otherAttributesIterator = treeSet.iterator();
        while (thisAttributesIterator.hasNext()) {
            String thisAttribute = thisAttributesIterator.next();
            String otherAttribute = otherAttributesIterator.next();
            int comp4 = thisAttribute.compareToIgnoreCase(otherAttribute);
            if (comp4 != 0) {
                return comp4;
            }
            String thisValue = getParameters().get(thisAttribute);
            String otherValue = other.getParameters().get(otherAttribute);
            if (otherValue == null) {
                otherValue = "";
            }
            int comp5 = thisValue.compareTo(otherValue);
            if (comp5 != 0) {
                return comp5;
            }
        }
        return 0;
    }

    public static i valueOf(String mimeType) {
        String str = mimeType;
        if (!TextUtils.isEmpty(mimeType)) {
            char c2 = ';';
            int index = str.indexOf(59);
            String fullType = (index >= 0 ? str.substring(0, index) : str).trim();
            if (!fullType.isEmpty()) {
                if ("*".equals(fullType)) {
                    fullType = h.ALL_VALUE;
                }
                int subIndex = fullType.indexOf(47);
                if (subIndex == -1) {
                    throw new InvalidMimeTypeException(str, "does not contain '/'");
                } else if (subIndex != fullType.length() - 1) {
                    String type2 = fullType.substring(0, subIndex);
                    String subtype2 = fullType.substring(subIndex + 1, fullType.length());
                    if (!"*".equals(type2) || "*".equals(subtype2)) {
                        Map<String, String> parameters2 = null;
                        while (true) {
                            int nextIndex = index + 1;
                            boolean quoted = false;
                            while (nextIndex < mimeType.length()) {
                                char ch = str.charAt(nextIndex);
                                if (ch == c2) {
                                    if (!quoted) {
                                        break;
                                    }
                                } else if (ch == '\"') {
                                    quoted = !quoted;
                                }
                                nextIndex++;
                            }
                            String parameter = str.substring(index + 1, nextIndex).trim();
                            if (parameter.length() > 0) {
                                if (parameters2 == null) {
                                    parameters2 = new LinkedHashMap<>(4);
                                }
                                int eqIndex = parameter.indexOf(61);
                                if (eqIndex >= 0) {
                                    parameters2.put(parameter.substring(0, eqIndex), parameter.substring(eqIndex + 1, parameter.length()));
                                }
                            }
                            index = nextIndex;
                            if (index >= mimeType.length()) {
                                try {
                                    return new i(type2, subtype2, parameters2);
                                } catch (UnsupportedCharsetException ex) {
                                    throw new InvalidMimeTypeException(str, "unsupported charset '" + ex.getCharsetName() + "'");
                                } catch (IllegalArgumentException ex2) {
                                    throw new InvalidMimeTypeException(str, ex2.getMessage());
                                }
                            } else {
                                c2 = ';';
                            }
                        }
                    } else {
                        throw new InvalidMimeTypeException(str, "wildcard type is legal only in '*/*' (all mime types)");
                    }
                } else {
                    throw new InvalidMimeTypeException(str, "does not contain subtype after '/'");
                }
            } else {
                throw new InvalidMimeTypeException(str, "'contentType' must not be empty");
            }
        } else {
            throw new InvalidMimeTypeException(str, "[mimeType] must not be empty");
        }
    }

    public static String toString(Collection<? extends i> mimeTypes) {
        StringBuilder builder = new StringBuilder();
        Iterator<? extends i> it = mimeTypes.iterator();
        while (it.hasNext()) {
            ((i) it.next()).appendTo(builder);
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private static Map<String, String> a(Charset charset, Map<String, String> parameters2) {
        Map<String, String> map = new LinkedHashMap<>(parameters2);
        map.put("charset", charset.name());
        return map;
    }

    /* compiled from: MimeType */
    public static class a<T extends i> implements Comparator<T> {
        /* renamed from: a */
        public int compare(T mimeType1, T mimeType2) {
            if (mimeType1.isWildcardType() && !mimeType2.isWildcardType()) {
                return 1;
            }
            if (mimeType2.isWildcardType() && !mimeType1.isWildcardType()) {
                return -1;
            }
            if (!mimeType1.getType().equals(mimeType2.getType())) {
                return 0;
            }
            if (mimeType1.isWildcardSubtype() && !mimeType2.isWildcardSubtype()) {
                return 1;
            }
            if (mimeType2.isWildcardSubtype() && !mimeType1.isWildcardSubtype()) {
                return -1;
            }
            if (!mimeType1.getSubtype().equals(mimeType2.getSubtype())) {
                return 0;
            }
            return b(mimeType1, mimeType2);
        }

        /* access modifiers changed from: protected */
        public int b(T mimeType1, T mimeType2) {
            int paramsSize1 = mimeType1.getParameters().size();
            int paramsSize2 = mimeType2.getParameters().size();
            if (paramsSize2 < paramsSize1) {
                return -1;
            }
            return paramsSize2 == paramsSize1 ? 0 : 1;
        }
    }
}
