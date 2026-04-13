package kotlin.reflect.jvm.internal.impl.renderer;

public final /* synthetic */ class g {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;
    public static final /* synthetic */ int[] c;
    public static final /* synthetic */ int[] d;
    public static final /* synthetic */ int[] e;

    static {
        int[] iArr = new int[p.values().length];
        a = iArr;
        p pVar = p.PLAIN;
        iArr[pVar.ordinal()] = 1;
        p pVar2 = p.HTML;
        iArr[pVar2.ordinal()] = 2;
        int[] iArr2 = new int[p.values().length];
        b = iArr2;
        iArr2[pVar.ordinal()] = 1;
        iArr2[pVar2.ordinal()] = 2;
        int[] iArr3 = new int[p.values().length];
        c = iArr3;
        iArr3[pVar.ordinal()] = 1;
        iArr3[pVar2.ordinal()] = 2;
        int[] iArr4 = new int[p.values().length];
        d = iArr4;
        iArr4[pVar.ordinal()] = 1;
        iArr4[pVar2.ordinal()] = 2;
        int[] iArr5 = new int[n.values().length];
        e = iArr5;
        iArr5[n.ALL.ordinal()] = 1;
        iArr5[n.ONLY_NON_SYNTHESIZED.ordinal()] = 2;
        iArr5[n.NONE.ordinal()] = 3;
    }
}
