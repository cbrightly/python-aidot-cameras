package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.b;

public final /* synthetic */ class f {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[b.C0379b.c.C0382c.values().length];
        a = iArr;
        iArr[b.C0379b.c.C0382c.BYTE.ordinal()] = 1;
        iArr[b.C0379b.c.C0382c.CHAR.ordinal()] = 2;
        iArr[b.C0379b.c.C0382c.SHORT.ordinal()] = 3;
        iArr[b.C0379b.c.C0382c.INT.ordinal()] = 4;
        iArr[b.C0379b.c.C0382c.LONG.ordinal()] = 5;
        iArr[b.C0379b.c.C0382c.FLOAT.ordinal()] = 6;
        iArr[b.C0379b.c.C0382c.DOUBLE.ordinal()] = 7;
        iArr[b.C0379b.c.C0382c.BOOLEAN.ordinal()] = 8;
        iArr[b.C0379b.c.C0382c.STRING.ordinal()] = 9;
        b.C0379b.c.C0382c cVar = b.C0379b.c.C0382c.CLASS;
        iArr[cVar.ordinal()] = 10;
        iArr[b.C0379b.c.C0382c.ENUM.ordinal()] = 11;
        iArr[b.C0379b.c.C0382c.ANNOTATION.ordinal()] = 12;
        b.C0379b.c.C0382c cVar2 = b.C0379b.c.C0382c.ARRAY;
        iArr[cVar2.ordinal()] = 13;
        int[] iArr2 = new int[b.C0379b.c.C0382c.values().length];
        b = iArr2;
        iArr2[cVar.ordinal()] = 1;
        iArr2[cVar2.ordinal()] = 2;
    }
}
