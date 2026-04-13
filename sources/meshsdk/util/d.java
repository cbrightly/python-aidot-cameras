package meshsdk.util;

import io.reactivex.m;
import io.reactivex.n;

/* compiled from: lambda */
public final /* synthetic */ class d implements n {
    public final /* synthetic */ String a;

    public /* synthetic */ d(String str) {
        this.a = str;
    }

    public final void subscribe(m mVar) {
        CheckMeshConfigUtils.lambda$queryConfigFileUrlByFileId$3(this.a, mVar);
    }
}
