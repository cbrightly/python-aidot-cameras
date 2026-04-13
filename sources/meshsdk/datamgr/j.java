package meshsdk.datamgr;

import io.reactivex.m;
import io.reactivex.n;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class j implements n {
    public final /* synthetic */ LDSGroupApi a;
    public final /* synthetic */ String b;
    public final /* synthetic */ JSONObject c;

    public /* synthetic */ j(LDSGroupApi lDSGroupApi, String str, JSONObject jSONObject) {
        this.a = lDSGroupApi;
        this.b = str;
        this.c = jSONObject;
    }

    public final void subscribe(m mVar) {
        this.a.a(this.b, this.c, mVar);
    }
}
