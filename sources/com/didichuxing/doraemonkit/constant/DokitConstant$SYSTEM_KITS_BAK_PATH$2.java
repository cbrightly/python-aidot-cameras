package com.didichuxing.doraemonkit.constant;

import com.blankj.utilcode.util.r;
import java.io.File;
import kotlin.jvm.functions.a;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\b\n\u0002\u0010\u000e\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"", "invoke", "()Ljava/lang/String;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DokitConstant.kt */
public final class DokitConstant$SYSTEM_KITS_BAK_PATH$2 extends kotlin.jvm.internal.l implements a<String> {
    public static final DokitConstant$SYSTEM_KITS_BAK_PATH$2 INSTANCE = new DokitConstant$SYSTEM_KITS_BAK_PATH$2();

    DokitConstant$SYSTEM_KITS_BAK_PATH$2() {
        super(0);
    }

    @NotNull
    public final String invoke() {
        return r.d() + File.separator + "system_kit_bak_3.2.0.json";
    }
}
