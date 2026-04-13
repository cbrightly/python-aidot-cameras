package meshsdk.datamgr;

import io.reactivex.functions.e;
import java.util.List;
import meshsdk.datamgr.DBDataFetcher;
import meshsdk.model.json.MeshStorage;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ DBDataFetcher.AnonymousClass1 c;
    public final /* synthetic */ MeshStorage d;
    public final /* synthetic */ String f;

    public /* synthetic */ d(DBDataFetcher.AnonymousClass1 r1, MeshStorage meshStorage, String str) {
        this.c = r1;
        this.d = meshStorage;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.a(this.d, this.f, (List) obj);
    }
}
