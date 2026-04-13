package meshsdk.datamgr;

import io.reactivex.m;
import io.reactivex.n;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class h implements n {
    public final /* synthetic */ DbDataUploader a;
    public final /* synthetic */ String b;
    public final /* synthetic */ JSONObject c;
    public final /* synthetic */ JSONObject d;
    public final /* synthetic */ String e;
    public final /* synthetic */ String f;

    public /* synthetic */ h(DbDataUploader dbDataUploader, String str, JSONObject jSONObject, JSONObject jSONObject2, String str2, String str3) {
        this.a = dbDataUploader;
        this.b = str;
        this.c = jSONObject;
        this.d = jSONObject2;
        this.e = str2;
        this.f = str3;
    }

    public final void subscribe(m mVar) {
        this.a.a(this.b, this.c, this.d, this.e, this.f, mVar);
    }
}
