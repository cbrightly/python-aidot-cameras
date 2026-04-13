package meshsdk.datamgr;

import io.reactivex.functions.e;
import java.util.HashMap;

/* compiled from: lambda */
public final /* synthetic */ class t implements e {
    public final /* synthetic */ MeshDataManager c;
    public final /* synthetic */ HashMap d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;

    public /* synthetic */ t(MeshDataManager meshDataManager, HashMap hashMap, String str, String str2) {
        this.c = meshDataManager;
        this.d = hashMap;
        this.f = str;
        this.q = str2;
    }

    public final void accept(Object obj) {
        this.c.g(this.d, this.f, this.q, (Throwable) obj);
    }
}
