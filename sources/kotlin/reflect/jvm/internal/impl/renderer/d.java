package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.reflect.jvm.internal.impl.descriptors.f;

public final /* synthetic */ class d {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[f.values().length];
        a = iArr;
        iArr[f.CLASS.ordinal()] = 1;
        iArr[f.INTERFACE.ordinal()] = 2;
        iArr[f.ENUM_CLASS.ordinal()] = 3;
        iArr[f.OBJECT.ordinal()] = 4;
        iArr[f.ANNOTATION_CLASS.ordinal()] = 5;
        iArr[f.ENUM_ENTRY.ordinal()] = 6;
    }
}
