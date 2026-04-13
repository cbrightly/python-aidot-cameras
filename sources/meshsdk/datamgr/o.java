package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.json.MeshStorage;

/* compiled from: lambda */
public final /* synthetic */ class o implements e {
    public final /* synthetic */ MeshDataManager c;
    public final /* synthetic */ MeshDataManager.OnUploadCallback d;
    public final /* synthetic */ String f;
    public final /* synthetic */ MeshStorage q;

    public /* synthetic */ o(MeshDataManager meshDataManager, MeshDataManager.OnUploadCallback onUploadCallback, String str, MeshStorage meshStorage) {
        this.c = meshDataManager;
        this.d = onUploadCallback;
        this.f = str;
        this.q = meshStorage;
    }

    public final void accept(Object obj) {
        this.c.d(this.d, this.f, this.q, (Throwable) obj);
    }
}
