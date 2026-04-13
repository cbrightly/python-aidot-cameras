package meshsdk.ctrl;

import android.os.Handler;
import android.os.HandlerThread;
import com.leedarson.serviceimpl.reporters.deviceControl.b;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSModel;

public class ControlDevIntercepter {
    private CmdCtrl cmdCtrl;
    private Handler handler;
    /* access modifiers changed from: private */
    public AtomicBoolean isWaiting;
    private long lastCtrlTime = 0;
    private final Object mLock = new Object();
    private Runnable nextTask = new Runnable() {
        public void run() {
            ControlDevIntercepter.this.isWaiting.set(false);
            ControlDevIntercepter.this.processQueue();
        }
    };
    private Queue<CtrlDevWrap> queue;

    public ControlDevIntercepter(CmdCtrl cmdCtrl2, HandlerThread thread) {
        this.cmdCtrl = cmdCtrl2;
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.handler = new Handler(thread.getLooper());
    }

    public void controlDevice(NodeInfo node, int modelId, Object value, boolean useQueue) {
        if (!useQueue) {
            this.cmdCtrl.controlDevice(0, node, modelId, value, useQueue);
            return;
        }
        CtrlDevWrap ctrlDevWrap = new CtrlDevWrap(node, modelId, value, useQueue, 0);
        if (tooFrequently() || this.lastCtrlTime == 0) {
            ctrlDevWrap.delay = 240;
        }
        b.b().a(ctrlDevWrap);
        this.lastCtrlTime = System.currentTimeMillis();
        this.queue.add(ctrlDevWrap);
        processQueue();
    }

    /* access modifiers changed from: private */
    public void processQueue() {
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get()) {
                    CtrlDevWrap wrap = this.queue.poll();
                    if (wrap != null) {
                        this.isWaiting.compareAndSet(false, true);
                        MeshLog.d(String.format(Locale.US, "controlDevice mac:%s,model:%s,value:%s 调用发送", new Object[]{wrap.node.macAddress, LDSModel.LdsModelName.modelName(wrap.modelId), wrap.value.toString()}));
                        this.cmdCtrl.controlDevice(wrap.taskId, wrap.node, wrap.modelId, wrap.value, wrap.useQueue);
                        this.handler.postDelayed(this.nextTask, (long) wrap.delay);
                    } else {
                        this.lastCtrlTime = 0;
                    }
                }
            }
        }
    }

    public static class CtrlDevWrap {
        public int delay;
        public int modelId;
        public NodeInfo node;
        public long taskId;
        public boolean useQueue;
        public Object value;

        public CtrlDevWrap(NodeInfo node2, int modelId2, Object value2, boolean useQueue2, int delay2) {
            this.node = node2;
            this.modelId = modelId2;
            this.value = value2;
            this.useQueue = useQueue2;
            this.delay = delay2;
        }
    }

    private boolean tooFrequently() {
        return System.currentTimeMillis() - this.lastCtrlTime < 200;
    }
}
