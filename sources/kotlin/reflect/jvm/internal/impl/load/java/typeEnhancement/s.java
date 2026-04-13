package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

public final /* synthetic */ class s {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[e.values().length];
        a = iArr;
        iArr[e.READ_ONLY.ordinal()] = 1;
        iArr[e.MUTABLE.ordinal()] = 2;
        int[] iArr2 = new int[g.values().length];
        b = iArr2;
        iArr2[g.NULLABLE.ordinal()] = 1;
        iArr2[g.NOT_NULL.ordinal()] = 2;
    }
}
