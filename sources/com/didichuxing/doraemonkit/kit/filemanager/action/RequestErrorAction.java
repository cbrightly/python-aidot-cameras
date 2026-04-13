package com.didichuxing.doraemonkit.kit.filemanager.action;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import meshsdk.BaseResp;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestErrorAction.kt */
public final class RequestErrorAction {
    public static final RequestErrorAction INSTANCE = new RequestErrorAction();

    private RequestErrorAction() {
    }

    @NotNull
    public final Map<String, Object> createErrorInfo(@NotNull String error) {
        k.f(error, "error");
        Map linkedHashMap = new LinkedHashMap();
        Map $this$apply = linkedHashMap;
        $this$apply.put("code", Integer.valueOf(BaseResp.ERR_MSG_SEND_FAIL));
        $this$apply.put("data", error);
        return linkedHashMap;
    }
}
