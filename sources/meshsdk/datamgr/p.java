package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.datamgr.MeshDataManager;

/* compiled from: lambda */
public final /* synthetic */ class p implements e {
    public final /* synthetic */ MeshDataManager c;
    public final /* synthetic */ MeshDataManager.OnUploadCallback d;
    public final /* synthetic */ String f;

    public /* synthetic */ p(MeshDataManager meshDataManager, MeshDataManager.OnUploadCallback onUploadCallback, String str) {
        this.c = meshDataManager;
        this.d = onUploadCallback;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.b(this.d, this.f, (Throwable) obj);
    }
}
