package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.datamgr.DBDataFetcher;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ DBDataFetcher.AnonymousClass1 c;
    public final /* synthetic */ String d;

    public /* synthetic */ c(DBDataFetcher.AnonymousClass1 r1, String str) {
        this.c = r1;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.b(this.d, (String) obj);
    }
}
