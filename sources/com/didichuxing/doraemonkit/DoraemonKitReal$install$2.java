package com.didichuxing.doraemonkit;

import android.app.Application;
import com.blankj.utilcode.util.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DoraemonKitReal.kt */
public final class DoraemonKitReal$install$2 extends b0.e<Object> {
    final /* synthetic */ Application $app;

    DoraemonKitReal$install$2(Application $captured_local_variable$0) {
        this.$app = $captured_local_variable$0;
    }

    @NotNull
    public Object doInBackground() {
        DoraemonKitReal.INSTANCE.addInnerKit(this.$app);
        return new Object();
    }

    public void onSuccess(@Nullable Object result) {
    }
}
