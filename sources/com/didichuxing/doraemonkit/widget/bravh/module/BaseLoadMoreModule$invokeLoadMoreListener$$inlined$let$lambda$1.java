package com.didichuxing.doraemonkit.widget.bravh.module;

import com.didichuxing.doraemonkit.widget.bravh.listener.OnLoadMoreListener;
import kotlin.l;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0004\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002¨\u0006\u0003"}, d2 = {"Lkotlin/x;", "run", "()V", "com/didichuxing/doraemonkit/widget/bravh/module/BaseLoadMoreModule$invokeLoadMoreListener$1$1", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: LoadMoreModule.kt */
public final class BaseLoadMoreModule$invokeLoadMoreListener$$inlined$let$lambda$1 implements Runnable {
    final /* synthetic */ BaseLoadMoreModule this$0;

    BaseLoadMoreModule$invokeLoadMoreListener$$inlined$let$lambda$1(BaseLoadMoreModule baseLoadMoreModule) {
        this.this$0 = baseLoadMoreModule;
    }

    public final void run() {
        OnLoadMoreListener access$getMLoadMoreListener$p = this.this$0.mLoadMoreListener;
        if (access$getMLoadMoreListener$p != null) {
            access$getMLoadMoreListener$p.onLoadMore();
        }
    }
}
