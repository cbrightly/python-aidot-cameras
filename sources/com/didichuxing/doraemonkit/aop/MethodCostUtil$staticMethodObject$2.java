package com.didichuxing.doraemonkit.aop;

import com.didichuxing.doraemonkit.aop.method_stack.StaticMethodObject;
import kotlin.jvm.functions.a;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lcom/didichuxing/doraemonkit/aop/method_stack/StaticMethodObject;", "invoke", "()Lcom/didichuxing/doraemonkit/aop/method_stack/StaticMethodObject;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: MethodCostUtil.kt */
public final class MethodCostUtil$staticMethodObject$2 extends kotlin.jvm.internal.l implements a<StaticMethodObject> {
    public static final MethodCostUtil$staticMethodObject$2 INSTANCE = new MethodCostUtil$staticMethodObject$2();

    MethodCostUtil$staticMethodObject$2() {
        super(0);
    }

    @NotNull
    public final StaticMethodObject invoke() {
        return new StaticMethodObject();
    }
}
