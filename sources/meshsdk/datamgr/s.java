package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.datamgr.MeshDataManager;

/* compiled from: lambda */
public final /* synthetic */ class s implements e {
    public final /* synthetic */ MeshDataManager c;
    public final /* synthetic */ String d;
    public final /* synthetic */ MeshDataManager.OnUploadCallback f;

    public /* synthetic */ s(MeshDataManager meshDataManager, String str, MeshDataManager.OnUploadCallback onUploadCallback) {
        this.c = meshDataManager;
        this.d = str;
        this.f = onUploadCallback;
    }

    public final void accept(Object obj) {
        this.c.a(this.d, this.f, (String) obj);
    }
}
