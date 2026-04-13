package meshsdk.datamgr;

import io.reactivex.functions.e;
import meshsdk.datamgr.MeshDataManager;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class n implements e {
    public final /* synthetic */ JSONObject a1;
    public final /* synthetic */ MeshDataManager.AnonymousClass4 c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ JSONObject p0;
    public final /* synthetic */ String q;
    public final /* synthetic */ String x;
    public final /* synthetic */ String y;
    public final /* synthetic */ String z;

    public /* synthetic */ n(MeshDataManager.AnonymousClass4 r1, String str, String str2, String str3, String str4, String str5, String str6, JSONObject jSONObject, JSONObject jSONObject2) {
        this.c = r1;
        this.d = str;
        this.f = str2;
        this.q = str3;
        this.x = str4;
        this.y = str5;
        this.z = str6;
        this.p0 = jSONObject;
        this.a1 = jSONObject2;
    }

    public final void accept(Object obj) {
        this.c.a(this.d, this.f, this.q, this.x, this.y, this.z, this.p0, this.a1, (String) obj);
    }
}
