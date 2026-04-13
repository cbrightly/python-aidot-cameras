package okhttp3.internal.concurrent;

import kotlin.jvm.functions.a;

/* compiled from: TaskQueue.kt */
public final class c extends a {
    final /* synthetic */ a e;
    final /* synthetic */ String f;
    final /* synthetic */ boolean g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(a $captured_local_variable$0, String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4) {
        super($super_call_param$3, $super_call_param$4);
        this.e = $captured_local_variable$0;
        this.f = $captured_local_variable$1;
        this.g = $captured_local_variable$2;
    }

    public long f() {
        this.e.invoke();
        return -1;
    }
}
