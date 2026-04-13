package meshsdk.datamgr;

import io.reactivex.functions.e;
import java.util.HashMap;
import meshsdk.datamgr.DataFetcher;

/* compiled from: lambda */
public final /* synthetic */ class l implements e {
    public final /* synthetic */ MeshDataManager c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ HashMap q;
    public final /* synthetic */ String x;

    public /* synthetic */ l(MeshDataManager meshDataManager, String str, String str2, HashMap hashMap, String str3) {
        this.c = meshDataManager;
        this.d = str;
        this.f = str2;
        this.q = hashMap;
        this.x = str3;
    }

    public final void accept(Object obj) {
        this.c.f(this.d, this.f, this.q, this.x, (DataFetcher.FetcherResp) obj);
    }
}
