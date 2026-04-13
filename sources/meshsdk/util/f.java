package meshsdk.util;

/* compiled from: lambda */
public final /* synthetic */ class f implements io.reactivex.functions.f {
    public final /* synthetic */ MeshEncryptInfoUtil c;
    public final /* synthetic */ String d;

    public /* synthetic */ f(MeshEncryptInfoUtil meshEncryptInfoUtil, String str) {
        this.c = meshEncryptInfoUtil;
        this.d = str;
    }

    public final Object apply(Object obj) {
        return this.c.a(this.d, obj);
    }
}
