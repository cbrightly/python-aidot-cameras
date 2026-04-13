package meshsdk.ctrl;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshCustomcmdCallback;

public class EffectLinkageCtrlAdapter {
    private CmdCtrl cmdCtrl;
    private Handler handler;
    /* access modifiers changed from: private */
    public AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private Runnable nextTask = new Runnable() {
        public void run() {
            EffectLinkageCtrlAdapter.this.isWaiting.set(false);
            EffectLinkageCtrlAdapter.this.processQueue();
        }
    };
    private Queue<EffLinkageWrap> queue;

    public EffectLinkageCtrlAdapter(CmdCtrl cmdCtrl2, HandlerThread thread) {
        this.cmdCtrl = cmdCtrl2;
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.handler = new Handler(thread.getLooper());
    }

    public void setLinkageMode(int action, int meshAddr, int groupAddr, MeshCustomcmdCallback customcmdCallback) {
        this.queue.add(new EffLinkageWrap(action, meshAddr, groupAddr, customcmdCallback));
        processQueue();
    }

    /* access modifiers changed from: private */
    public void processQueue() {
        EffLinkageWrap wrap;
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get() && (wrap = this.queue.poll()) != null) {
                    this.isWaiting.compareAndSet(false, true);
                    this.cmdCtrl.performEffectLinkage(wrap.action, wrap.meshAddr, wrap.groupAddr, wrap.customcmdCallback);
                    this.handler.postDelayed(this.nextTask, 300);
                }
            }
        }
    }

    public static class EffLinkageWrap {
        public int action;
        public MeshCustomcmdCallback customcmdCallback;
        public int groupAddr;
        public int meshAddr;

        public EffLinkageWrap(int action2, int meshAddr2, int groupAddr2, MeshCustomcmdCallback customcmdCallback2) {
            this.action = action2;
            this.meshAddr = meshAddr2;
            this.customcmdCallback = customcmdCallback2;
            this.groupAddr = groupAddr2;
        }
    }
}
