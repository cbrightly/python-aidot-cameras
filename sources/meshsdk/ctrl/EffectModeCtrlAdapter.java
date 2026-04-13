package meshsdk.ctrl;

import android.os.Handler;
import android.os.HandlerThread;
import com.leedarson.serviceinterface.BleMeshService;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.model.json.CustomEffectMode;
import timber.log.a;

public class EffectModeCtrlAdapter {
    private static final String TAG = "EffectModeCtrlAdapter";
    private CmdCtrl cmdCtrl;
    private Handler handler;
    /* access modifiers changed from: private */
    public AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private Runnable nextTask = new Runnable() {
        public void run() {
            EffectModeCtrlAdapter.this.isWaiting.set(false);
            EffectModeCtrlAdapter.this.processQueue();
        }
    };
    private Queue<EffModeWrap> queue;

    public EffectModeCtrlAdapter(CmdCtrl cmdCtrl2, HandlerThread thread) {
        this.cmdCtrl = cmdCtrl2;
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.handler = new Handler(thread.getLooper());
    }

    public void setEffectMode(String macAddress, int effectId, long durationMill, int action, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        a.g(TAG).m(BleMeshService.ACTION_SET_EFFECT_MODE, new Object[0]);
        this.queue.add(new EffModeWrap(macAddress, effectId, durationMill, action, meshAddr, customcmdCallback));
        processQueue();
    }

    public void setCustomEffectMode(String macAddress, CustomEffectMode customEffectMode, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        a.g(TAG).m("setCustomEffectMode", new Object[0]);
        this.queue.add(new EffModeWrap(macAddress, customEffectMode, meshAddr, customcmdCallback));
        processQueue();
    }

    /* access modifiers changed from: private */
    public void processQueue() {
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get()) {
                    EffModeWrap wrap = this.queue.poll();
                    if (wrap != null) {
                        this.isWaiting.compareAndSet(false, true);
                        if (wrap.customEffectMode != null) {
                            MeshLog.i("effect队列中读取 setCustom effectmode执行自定义灯效");
                            this.cmdCtrl.setCustomEffectMode(wrap.macAddress, wrap.customEffectMode, wrap.meshAddr, wrap.customcmdCallback);
                        } else {
                            MeshLog.i("effect队列中读取 effectmode 执行灯效");
                            this.cmdCtrl.setEffectMode(wrap.macAddress, wrap.effectId, wrap.durationMill, wrap.action, wrap.meshAddr, wrap.customcmdCallback);
                        }
                        MeshLog.i("延迟10ms从effectmode队列中读取下一条");
                        this.handler.postDelayed(this.nextTask, 500);
                    } else {
                        MeshLog.i("effectmode队列无数据");
                    }
                }
            }
        }
    }

    public static class EffModeWrap {
        public int action;
        public CustomEffectMode customEffectMode;
        public MeshCustomcmdCallback customcmdCallback;
        public long durationMill;
        public int effectId;
        public String macAddress;
        public int meshAddr;

        public EffModeWrap(String macAddress2, int effectId2, long durationMill2, int action2, int meshAddr2, MeshCustomcmdCallback customcmdCallback2) {
            this.macAddress = macAddress2;
            this.effectId = effectId2;
            this.durationMill = durationMill2;
            this.action = action2;
            this.meshAddr = meshAddr2;
            this.customcmdCallback = customcmdCallback2;
        }

        public EffModeWrap(String macAddress2, CustomEffectMode customEffectMode2, int meshAddr2, MeshCustomcmdCallback customcmdCallback2) {
            this.macAddress = macAddress2;
            this.customEffectMode = customEffectMode2;
            this.meshAddr = meshAddr2;
            this.customcmdCallback = customcmdCallback2;
        }
    }
}
