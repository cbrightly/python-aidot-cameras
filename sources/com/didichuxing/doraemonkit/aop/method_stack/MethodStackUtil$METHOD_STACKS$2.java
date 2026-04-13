package com.didichuxing.doraemonkit.aop.method_stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.a;
import kotlin.l;

@l(d1 = {"\u0000\u001a\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\u0010\b\u001aV\u0012$\u0012\"\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003 \u0004*\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00010\u0001 \u0004**\u0012$\u0012\"\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003 \u0004*\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00010\u0001\u0018\u00010\u00050\u0000H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/didichuxing/doraemonkit/aop/method_stack/MethodInvokNode;", "kotlin.jvm.PlatformType", "", "invoke", "()Ljava/util/List;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: MethodStackUtil.kt */
public final class MethodStackUtil$METHOD_STACKS$2 extends kotlin.jvm.internal.l implements a<List<ConcurrentHashMap<String, MethodInvokNode>>> {
    public static final MethodStackUtil$METHOD_STACKS$2 INSTANCE = new MethodStackUtil$METHOD_STACKS$2();

    MethodStackUtil$METHOD_STACKS$2() {
        super(0);
    }

    public final List<ConcurrentHashMap<String, MethodInvokNode>> invoke() {
        return Collections.synchronizedList(new ArrayList());
    }
}
