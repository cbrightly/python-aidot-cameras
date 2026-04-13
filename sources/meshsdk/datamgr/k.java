package meshsdk.datamgr;

import io.reactivex.m;
import io.reactivex.n;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class k implements n {
    public final /* synthetic */ LDSSceneApi a;
    public final /* synthetic */ String b;
    public final /* synthetic */ JSONObject c;
    public final /* synthetic */ JSONObject d;
    public final /* synthetic */ int e;
    public final /* synthetic */ String f;

    public /* synthetic */ k(LDSSceneApi lDSSceneApi, String str, JSONObject jSONObject, JSONObject jSONObject2, int i, String str2) {
        this.a = lDSSceneApi;
        this.b = str;
        this.c = jSONObject;
        this.d = jSONObject2;
        this.e = i;
        this.f = str2;
    }

    public final void subscribe(m mVar) {
        this.a.a(this.b, this.c, this.d, this.e, this.f, mVar);
    }
}
