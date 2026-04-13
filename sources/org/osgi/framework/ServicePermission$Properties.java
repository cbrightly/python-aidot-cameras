package org.osgi.framework;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ServicePermission$Properties extends AbstractMap<String, Object> {
    private final Map<String, Object> c;
    private final d<?> d;
    private volatile transient Set<Map.Entry<String, Object>> f;

    public Object get(Object k) {
        if (!(k instanceof String)) {
            return null;
        }
        String key = (String) k;
        if (key.charAt(0) == '@') {
            return this.d.o(key.substring(1));
        }
        Object value = this.c.get(key);
        if (value != null) {
            return value;
        }
        return this.d.o(key);
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        if (this.f != null) {
            return this.f;
        }
        Set<Map.Entry<String, Object>> all = new HashSet<>(this.c.entrySet());
        for (String key : this.d.u()) {
            Iterator<String> it = this.c.keySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (key.equalsIgnoreCase(it.next())) {
                        break;
                    }
                } else {
                    all.add(new a(key, this.d.o(key)));
                    break;
                }
            }
        }
        Set<T> unmodifiableSet = Collections.unmodifiableSet(all);
        this.f = unmodifiableSet;
        return unmodifiableSet;
    }

    public static final class a implements Map.Entry<String, Object> {
        private final String c;
        private final Object d;

        a(String key, Object value) {
            this.c = key;
            this.d = value;
        }

        /* renamed from: a */
        public String getKey() {
            return this.c;
        }

        public Object getValue() {
            return this.d;
        }

        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.c + "=" + this.d;
        }

        public int hashCode() {
            String str = this.c;
            int i = 0;
            int hashCode = str == null ? 0 : str.hashCode();
            Object obj = this.d;
            if (obj != null) {
                i = obj.hashCode();
            }
            return hashCode ^ i;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
            r4 = r1.getValue();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r7) {
            /*
                r6 = this;
                r0 = 1
                if (r7 != r6) goto L_0x0004
                return r0
            L_0x0004:
                boolean r1 = r7 instanceof java.util.Map.Entry
                r2 = 0
                if (r1 != 0) goto L_0x000a
                return r2
            L_0x000a:
                r1 = r7
                java.util.Map$Entry r1 = (java.util.Map.Entry) r1
                java.lang.Object r3 = r1.getKey()
                java.lang.String r4 = r6.c
                if (r4 == r3) goto L_0x001d
                if (r4 == 0) goto L_0x002e
                boolean r4 = r4.equals(r3)
                if (r4 == 0) goto L_0x002e
            L_0x001d:
                java.lang.Object r4 = r1.getValue()
                java.lang.Object r5 = r6.d
                if (r5 == r4) goto L_0x002f
                if (r5 == 0) goto L_0x002e
                boolean r5 = r5.equals(r4)
                if (r5 == 0) goto L_0x002e
                goto L_0x002f
            L_0x002e:
                return r2
            L_0x002f:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osgi.framework.ServicePermission$Properties.a.equals(java.lang.Object):boolean");
        }
    }
}
