package meshsdk.datamgr;

import android.content.Context;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.reporters.k;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.m;
import io.reactivex.n;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class e implements n {
    public final /* synthetic */ Context a;
    public final /* synthetic */ String b;
    public final /* synthetic */ JSONObject c;

    public /* synthetic */ e(Context context, String str, JSONObject jSONObject) {
        this.a = context;
        this.b = str;
        this.c = jSONObject;
    }

    public final void subscribe(m mVar) {
        b0.b().L(this.a, (b<a>) null, this.b, this.c.toString(), (String) null, new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                k.a("获取云端meshjson信息【失败】:" + e.getMessage());
                m.this.onError(e);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                k.a("获取云端meshjson信息成功:" + response);
                m.this.onNext(response);
                m.this.onComplete();
            }
        }, l.g);
    }
}
