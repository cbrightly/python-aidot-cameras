package meshsdk.datamgr;

import com.leedarson.base.http.exception.ApiException;
import io.reactivex.m;
import meshsdk.datamgr.DBDataFetcher;

/* compiled from: lambda */
public final /* synthetic */ class g implements DBDataFetcher.PingCallback {
    public final /* synthetic */ m a;
    public final /* synthetic */ ApiException b;

    public /* synthetic */ g(m mVar, ApiException apiException) {
        this.a = mVar;
        this.b = apiException;
    }

    public final void callback() {
        this.a.onError(this.b);
    }
}
