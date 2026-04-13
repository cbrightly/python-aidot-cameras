package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* compiled from: IndexAction.kt */
public final class IndexAction {
    public static final IndexAction INSTANCE = new IndexAction();

    private IndexAction() {
    }

    @NotNull
    public final Map<String, Object> indexInfoRes() {
        Map linkedHashMap = new LinkedHashMap();
        Map $this$apply = linkedHashMap;
        $this$apply.put("code", 200);
        $this$apply.put("data", "请在www.dokit.cn里的控制台中的文件同步助手中使用该功能");
        return linkedHashMap;
    }
}
