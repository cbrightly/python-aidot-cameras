package meshsdk.ctrl;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.model.json.DetectMode;
import meshsdk.util.LDSMeshUtil;

public class DetectModeCtrlAdapter {
    private CmdCtrl cmdCtrl;
    private Handler handler;
    /* access modifiers changed from: private */
    public AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private Runnable nextTask = new Runnable() {
        public void run() {
            DetectModeCtrlAdapter.this.isWaiting.set(false);
            DetectModeCtrlAdapter.this.processQueue();
        }
    };
    private Queue<DetectModeWrap> queue;

    public DetectModeCtrlAdapter(CmdCtrl cmdCtrl2, HandlerThread thread) {
        this.cmdCtrl = cmdCtrl2;
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.handler = new Handler(thread.getLooper());
    }

    public void setDetectionMode(int meshAddr, DetectMode detectMode, MeshCustomcmdCallback customcmdCallback) {
        this.queue.add(new DetectModeWrap(detectMode, meshAddr, customcmdCallback));
        processQueue();
    }

    /* access modifiers changed from: private */
    public void processQueue() {
        DetectModeWrap wrap;
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get() && (wrap = this.queue.poll()) != null) {
                    this.isWaiting.compareAndSet(false, true);
                    if (LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, wrap.meshAddr).protocolVersion < SIGMesh.NEW_PROTOCOL7) {
                        MeshLog.i("setDetectionMode走老协议");
                        this.cmdCtrl.setDetectionMode(wrap.meshAddr, wrap.mode, wrap.customcmdCallback);
                    } else {
                        MeshLog.i("setDetectionMode走新协议");
                        this.cmdCtrl.setDetectionModeNew(wrap.meshAddr, wrap.mode, wrap.customcmdCallback);
                    }
                    this.handler.postDelayed(this.nextTask, 1300);
                }
            }
        }
    }

    public static class DetectModeWrap {
        public MeshCustomcmdCallback customcmdCallback;
        public int meshAddr;
        public DetectMode mode;

        public DetectModeWrap(DetectMode mode2, int meshAddr2, MeshCustomcmdCallback customcmdCallback2) {
            this.mode = mode2;
            this.meshAddr = meshAddr2;
            this.customcmdCallback = customcmdCallback2;
        }
    }
}
