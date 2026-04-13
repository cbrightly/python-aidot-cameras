package kotlin.text;

import kotlin.jvm.internal.l;

/* compiled from: Regex.kt */
public final class k extends l implements kotlin.jvm.functions.l<T, Boolean> {
    final /* synthetic */ int $value$inlined;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k(int i) {
        super(1);
        this.$value$inlined = i;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((Enum) obj));
    }

    public final boolean invoke(T it) {
        return (this.$value$inlined & ((e) it).getMask()) == ((e) it).getValue();
    }
}
