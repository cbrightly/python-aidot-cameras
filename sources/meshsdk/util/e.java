package meshsdk.util;

import io.reactivex.m;
import io.reactivex.n;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class e implements n {
    public final /* synthetic */ File a;
    public final /* synthetic */ String b;

    public /* synthetic */ e(File file, String str) {
        this.a = file;
        this.b = str;
    }

    public final void subscribe(m mVar) {
        MeshEncryptInfoUtil.lambda$getLocalExistNetWorkInfo$1(this.a, this.b, mVar);
    }
}
