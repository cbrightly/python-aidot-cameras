package com.tencent.bugly.proguard;

import com.google.maps.android.BuildConfig;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class h {
    private StringBuilder a;
    private int b = 0;

    private void a(String str) {
        for (int i = 0; i < this.b; i++) {
            this.a.append(9);
        }
        if (str != null) {
            StringBuilder sb = this.a;
            sb.append(str);
            sb.append(": ");
        }
    }

    public h(StringBuilder sb, int i) {
        this.a = sb;
        this.b = i;
    }

    public final h a(boolean z, String str) {
        a(str);
        StringBuilder sb = this.a;
        sb.append(z ? 'T' : 'F');
        sb.append(10);
        return this;
    }

    public final h a(byte b2, String str) {
        a(str);
        StringBuilder sb = this.a;
        sb.append(b2);
        sb.append(10);
        return this;
    }

    public final h a(short s, String str) {
        a(str);
        StringBuilder sb = this.a;
        sb.append(s);
        sb.append(10);
        return this;
    }

    public final h a(int i, String str) {
        a(str);
        StringBuilder sb = this.a;
        sb.append(i);
        sb.append(10);
        return this;
    }

    public final h a(long j, String str) {
        a(str);
        StringBuilder sb = this.a;
        sb.append(j);
        sb.append(10);
        return this;
    }

    public final h a(String str, String str2) {
        a(str2);
        if (str == null) {
            this.a.append("null\n");
        } else {
            StringBuilder sb = this.a;
            sb.append(str);
            sb.append(10);
        }
        return this;
    }

    public final h a(byte[] bArr, String str) {
        a(str);
        if (bArr == null) {
            this.a.append("null\n");
            return this;
        } else if (bArr.length == 0) {
            StringBuilder sb = this.a;
            sb.append(bArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.a;
            sb2.append(bArr.length);
            sb2.append(", [\n");
            h hVar = new h(this.a, this.b + 1);
            for (byte append : bArr) {
                hVar.a((String) null);
                StringBuilder sb3 = hVar.a;
                sb3.append(append);
                sb3.append(10);
            }
            a((String) null);
            StringBuilder sb4 = this.a;
            sb4.append(']');
            sb4.append(10);
            return this;
        }
    }

    public final <K, V> h a(Map<K, V> map, String str) {
        a(str);
        if (map == null) {
            this.a.append("null\n");
            return this;
        } else if (map.isEmpty()) {
            StringBuilder sb = this.a;
            sb.append(map.size());
            sb.append(", {}\n");
            return this;
        } else {
            StringBuilder sb2 = this.a;
            sb2.append(map.size());
            sb2.append(", {\n");
            h hVar = new h(this.a, this.b + 1);
            h hVar2 = new h(this.a, this.b + 2);
            for (Map.Entry next : map.entrySet()) {
                hVar.a((String) null);
                StringBuilder sb3 = hVar.a;
                sb3.append('(');
                sb3.append(10);
                hVar2.a(next.getKey(), (String) null);
                hVar2.a(next.getValue(), (String) null);
                hVar.a((String) null);
                StringBuilder sb4 = hVar.a;
                sb4.append(')');
                sb4.append(10);
            }
            a((String) null);
            StringBuilder sb5 = this.a;
            sb5.append('}');
            sb5.append(10);
            return this;
        }
    }

    private <T> h a(T[] tArr, String str) {
        a(str);
        if (tArr == null) {
            this.a.append("null\n");
            return this;
        } else if (tArr.length == 0) {
            StringBuilder sb = this.a;
            sb.append(tArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.a;
            sb2.append(tArr.length);
            sb2.append(", [\n");
            h hVar = new h(this.a, this.b + 1);
            for (T a2 : tArr) {
                hVar.a(a2, (String) null);
            }
            a((String) null);
            StringBuilder sb3 = this.a;
            sb3.append(']');
            sb3.append(10);
            return this;
        }
    }

    private <T> h a(T t, String str) {
        if (t == null) {
            this.a.append("null\n");
        } else if (t instanceof Byte) {
            byte byteValue = ((Byte) t).byteValue();
            a(str);
            StringBuilder sb = this.a;
            sb.append(byteValue);
            sb.append(10);
        } else if (t instanceof Boolean) {
            boolean booleanValue = ((Boolean) t).booleanValue();
            a(str);
            StringBuilder sb2 = this.a;
            sb2.append(booleanValue ? 'T' : 'F');
            sb2.append(10);
        } else if (t instanceof Short) {
            short shortValue = ((Short) t).shortValue();
            a(str);
            StringBuilder sb3 = this.a;
            sb3.append(shortValue);
            sb3.append(10);
        } else if (t instanceof Integer) {
            int intValue = ((Integer) t).intValue();
            a(str);
            StringBuilder sb4 = this.a;
            sb4.append(intValue);
            sb4.append(10);
        } else if (t instanceof Long) {
            long longValue = ((Long) t).longValue();
            a(str);
            StringBuilder sb5 = this.a;
            sb5.append(longValue);
            sb5.append(10);
        } else if (t instanceof Float) {
            float floatValue = ((Float) t).floatValue();
            a(str);
            StringBuilder sb6 = this.a;
            sb6.append(floatValue);
            sb6.append(10);
        } else if (t instanceof Double) {
            double doubleValue = ((Double) t).doubleValue();
            a(str);
            StringBuilder sb7 = this.a;
            sb7.append(doubleValue);
            sb7.append(10);
        } else if (t instanceof String) {
            a((String) t, str);
        } else if (t instanceof Map) {
            a((Map) t, str);
        } else if (t instanceof List) {
            a((T[]) ((List) t).toArray(), str);
        } else if (t instanceof k) {
            a((k) t, str);
        } else if (t instanceof byte[]) {
            a((byte[]) t, str);
        } else if (t instanceof boolean[]) {
            a((boolean[]) t, str);
        } else {
            int i = 0;
            if (t instanceof short[]) {
                short[] sArr = (short[]) t;
                a(str);
                if (sArr.length == 0) {
                    StringBuilder sb8 = this.a;
                    sb8.append(sArr.length);
                    sb8.append(", []\n");
                } else {
                    StringBuilder sb9 = this.a;
                    sb9.append(sArr.length);
                    sb9.append(", [\n");
                    h hVar = new h(this.a, this.b + 1);
                    int length = sArr.length;
                    while (i < length) {
                        short s = sArr[i];
                        hVar.a((String) null);
                        StringBuilder sb10 = hVar.a;
                        sb10.append(s);
                        sb10.append(10);
                        i++;
                    }
                    a((String) null);
                    StringBuilder sb11 = this.a;
                    sb11.append(']');
                    sb11.append(10);
                }
            } else if (t instanceof int[]) {
                int[] iArr = (int[]) t;
                a(str);
                if (iArr.length == 0) {
                    StringBuilder sb12 = this.a;
                    sb12.append(iArr.length);
                    sb12.append(", []\n");
                } else {
                    StringBuilder sb13 = this.a;
                    sb13.append(iArr.length);
                    sb13.append(", [\n");
                    h hVar2 = new h(this.a, this.b + 1);
                    int length2 = iArr.length;
                    while (i < length2) {
                        int i2 = iArr[i];
                        hVar2.a((String) null);
                        StringBuilder sb14 = hVar2.a;
                        sb14.append(i2);
                        sb14.append(10);
                        i++;
                    }
                    a((String) null);
                    StringBuilder sb15 = this.a;
                    sb15.append(']');
                    sb15.append(10);
                }
            } else if (t instanceof long[]) {
                long[] jArr = (long[]) t;
                a(str);
                if (jArr.length == 0) {
                    StringBuilder sb16 = this.a;
                    sb16.append(jArr.length);
                    sb16.append(", []\n");
                } else {
                    StringBuilder sb17 = this.a;
                    sb17.append(jArr.length);
                    sb17.append(", [\n");
                    h hVar3 = new h(this.a, this.b + 1);
                    int length3 = jArr.length;
                    while (i < length3) {
                        long j = jArr[i];
                        hVar3.a((String) null);
                        StringBuilder sb18 = hVar3.a;
                        sb18.append(j);
                        sb18.append(10);
                        i++;
                    }
                    a((String) null);
                    StringBuilder sb19 = this.a;
                    sb19.append(']');
                    sb19.append(10);
                }
            } else if (t instanceof float[]) {
                float[] fArr = (float[]) t;
                a(str);
                if (fArr.length == 0) {
                    StringBuilder sb20 = this.a;
                    sb20.append(fArr.length);
                    sb20.append(", []\n");
                } else {
                    StringBuilder sb21 = this.a;
                    sb21.append(fArr.length);
                    sb21.append(", [\n");
                    h hVar4 = new h(this.a, this.b + 1);
                    int length4 = fArr.length;
                    while (i < length4) {
                        float f = fArr[i];
                        hVar4.a((String) null);
                        StringBuilder sb22 = hVar4.a;
                        sb22.append(f);
                        sb22.append(10);
                        i++;
                    }
                    a((String) null);
                    StringBuilder sb23 = this.a;
                    sb23.append(']');
                    sb23.append(10);
                }
            } else if (t instanceof double[]) {
                double[] dArr = (double[]) t;
                a(str);
                if (dArr.length == 0) {
                    StringBuilder sb24 = this.a;
                    sb24.append(dArr.length);
                    sb24.append(", []\n");
                } else {
                    StringBuilder sb25 = this.a;
                    sb25.append(dArr.length);
                    sb25.append(", [\n");
                    h hVar5 = new h(this.a, this.b + 1);
                    int length5 = dArr.length;
                    while (i < length5) {
                        double d = dArr[i];
                        hVar5.a((String) null);
                        StringBuilder sb26 = hVar5.a;
                        sb26.append(d);
                        sb26.append(10);
                        i++;
                    }
                    a((String) null);
                    StringBuilder sb27 = this.a;
                    sb27.append(']');
                    sb27.append(10);
                }
            } else if (t.getClass().isArray()) {
                a((T[]) (Object[]) t, str);
            } else {
                throw new b("write object error: unsupport type.");
            }
        }
        return this;
    }

    public final h a(k kVar, String str) {
        a(str);
        StringBuilder sb = this.a;
        sb.append('{');
        sb.append(10);
        if (kVar == null) {
            StringBuilder sb2 = this.a;
            sb2.append(9);
            sb2.append(BuildConfig.TRAVIS);
        } else {
            kVar.a(this.a, this.b + 1);
        }
        a((String) null);
        StringBuilder sb3 = this.a;
        sb3.append('}');
        sb3.append(10);
        return this;
    }
}
