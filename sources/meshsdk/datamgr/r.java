package meshsdk.datamgr;

/* compiled from: lambda */
public final /* synthetic */ class r implements Runnable {
    public final /* synthetic */ MeshDataManager c;
    public final /* synthetic */ String d;

    public /* synthetic */ r(MeshDataManager meshDataManager, String str) {
        this.c = meshDataManager;
        this.d = str;
    }

    public final void run() {
        this.c.e(this.d);
    }
}
