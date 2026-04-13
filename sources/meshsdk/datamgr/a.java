package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.datamgr.DBDataFetcher;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ DBDataFetcher.AnonymousClass1 c;
    public final /* synthetic */ String d;

    public /* synthetic */ a(DBDataFetcher.AnonymousClass1 r1, String str) {
        this.c = r1;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.c(this.d, (Throwable) obj);
    }
}
