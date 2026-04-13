package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.MeshLog;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public static final /* synthetic */ b c = new b();

    private /* synthetic */ b() {
    }

    public final void accept(Object obj) {
        MeshLog.e("getDevices 出错:" + ((Throwable) obj).getMessage());
    }
}
