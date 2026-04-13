package com.didichuxing.doraemonkit.aop;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.a;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0002\b\u0003\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Ljava/util/concurrent/ConcurrentHashMap;", "", "", "invoke", "()Ljava/util/concurrent/ConcurrentHashMap;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: MethodCostUtil.kt */
public final class MethodCostUtil$METHOD_COSTS$2 extends kotlin.jvm.internal.l implements a<ConcurrentHashMap<String, Long>> {
    public static final MethodCostUtil$METHOD_COSTS$2 INSTANCE = new MethodCostUtil$METHOD_COSTS$2();

    MethodCostUtil$METHOD_COSTS$2() {
        super(0);
    }

    @NotNull
    public final ConcurrentHashMap<String, Long> invoke() {
        return new ConcurrentHashMap<>();
    }
}
