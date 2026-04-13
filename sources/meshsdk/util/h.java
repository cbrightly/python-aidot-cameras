package meshsdk.util;

import io.reactivex.functions.e;
import meshsdk.MeshLog;

/* compiled from: lambda */
public final /* synthetic */ class h implements e {
    public static final /* synthetic */ h c = new h();

    private /* synthetic */ h() {
    }

    public final void accept(Object obj) {
        MeshLog.e("doOnError 查询云端DB接口出错:" + ((Throwable) obj).getMessage());
    }
}
