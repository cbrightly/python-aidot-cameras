package meshsdk;

public class MeshScanLog {
    private static final String TAG = "MeshScanLog";

    public static void d(String msg) {
        MeshLog.d("MeshScanLog:" + msg);
    }
}
