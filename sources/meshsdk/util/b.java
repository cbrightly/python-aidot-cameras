package meshsdk.util;

import io.reactivex.functions.f;
import meshsdk.datamgr.MeshDataManager;

/* compiled from: lambda */
public final /* synthetic */ class b implements f {
    public final /* synthetic */ String c;

    public /* synthetic */ b(String str) {
        this.c = str;
    }

    public final Object apply(Object obj) {
        return MeshDataManager.exportTraceByELK("根据文件id=" + this.c + "查询到remoteUrl=" + ((String) obj), "info", "toCheckRemoteFileUploadSuccess");
    }
}
