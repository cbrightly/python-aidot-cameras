package meshsdk.ctrl;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.android.arouter.launcher.a;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceinterface.BleMeshService;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.config.NodeIdentitySetMessage;
import com.telink.ble.mesh.entity.MsgExtra;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.ReliableMessageProcessEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NodeInfo;

public class MeshMessagePool implements EventListener<String> {
    private static MeshMessagePool instance;
    /* access modifiers changed from: private */
    public String TAG = MeshMessagePool.class.getSimpleName();
    private BleMeshService bleMeshService;
    private Set<MeshMessage> busyMsgPool = new CopyOnWriteArraySet();
    /* access modifiers changed from: private */
    public ConcurrentLinkedQueue<MeshMessage> cacheQueue = new ConcurrentLinkedQueue<>();
    private Runnable clearDelayRhythmRef = new c(this);
    private Handler handler = new Handler(Looper.getMainLooper());
    Runnable handlerCmdTask = new Runnable() {
        public void run() {
            int size = MeshMessagePool.this.cacheQueue != null ? MeshMessagePool.this.cacheQueue.size() : 0;
            for (int i = 0; i < size; i++) {
                if (SIGMesh.getInstance().hasConnected()) {
                    MeshMessage message = (MeshMessage) MeshMessagePool.this.cacheQueue.poll();
                    message.D(-1);
                    MeshLog.d(MeshMessagePool.this.TAG + "process cache Message,realiabe:" + message.u());
                    MeshService.k().t(message);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public AtomicBoolean isWaiting = new AtomicBoolean(false);
    private final Object mLock = new Object();
    private ConcurrentLinkedQueue<MeshMessage> queue = new ConcurrentLinkedQueue<>();
    private boolean running = true;
    private ExecutorService threadPool = l.i(1, "mesh-message-pool", 2);
    Runnable timeoutTask = new Runnable() {
        public void run() {
            MeshMessagePool.this.isWaiting.compareAndSet(true, false);
            MeshMessagePool.this.lambda$addAndSend$0();
        }
    };

    public static void init() {
        getInstance();
    }

    public MeshMessagePool() {
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_COMPLETE", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_ERROR_BUSY", this);
    }

    public static MeshMessagePool getInstance() {
        if (instance == null) {
            synchronized (MeshMessagePool.class) {
                if (instance == null) {
                    instance = new MeshMessagePool();
                }
            }
        }
        return instance;
    }

    public BleMeshService getBleMeshService() {
        if (this.bleMeshService == null) {
            this.bleMeshService = (BleMeshService) a.c().g(BleMeshService.class);
        }
        return this.bleMeshService;
    }

    public void addAndSend(MeshMessage meshMessage) {
        String str;
        StringBuilder sb;
        if (!SIGMesh.getInstance().hasConnected()) {
            MeshLog.e("mesh网络还未连上， 队列中不添加消息:" + meshMessage);
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("添加消息到messageQueue:");
        if (meshMessage.t()) {
            sb = new StringBuilder();
            sb.append(" propertyId:");
            str = String.format("0x%04X", new Object[]{Integer.valueOf(meshMessage.m())});
        } else {
            sb = new StringBuilder();
            sb.append(" opcode:");
            str = String.format("0x%04X", new Object[]{Integer.valueOf(meshMessage.k())});
        }
        sb.append(str);
        sb2.append(sb.toString());
        MeshLog.i(sb2.toString());
        this.queue.add(meshMessage);
        if (meshMessage.b().b == 150 || isRhtyCmd(meshMessage)) {
            MeshService.k().b();
        }
        if (!delayRhythmIfNeeded(meshMessage)) {
            lambda$addAndSend$0();
        } else if (getBleMeshService().setDelayRhythmRef("正在控制指令")) {
            this.handler.removeCallbacks(this.clearDelayRhythmRef);
            this.handler.postDelayed(new d(this), 200);
            this.handler.postDelayed(this.clearDelayRhythmRef, 500);
        } else {
            lambda$addAndSend$0();
        }
    }

    public boolean delayRhythmIfNeeded(MeshMessage message) {
        return message.b().b == 150 || (message.b().b == 140 && message.w());
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$1 */
    public /* synthetic */ void b() {
        getBleMeshService().clearDelayRhythmRef();
    }

    public boolean isRhtyCmd(MeshMessage message) {
        if (message.m() == 6 || message.m() == 4 || message.m() == 1) {
            return true;
        }
        return false;
    }

    public void cancelCurrentSendingReliableMessage() {
        MeshService.k().b();
    }

    public void performed(Event<String> event) {
        MeshMessage message;
        String type = event.getType();
        if (type.equals("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_COMPLETE")) {
            MeshLog.d(this.TAG + "=========perform event:EVENT_TYPE_MSG_PROCESS_COMPLETE===========");
            this.handler.removeCallbacks(this.timeoutTask);
            this.isWaiting.compareAndSet(true, false);
            lambda$addAndSend$0();
        } else if (type.equals("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_ERROR_BUSY") && (event instanceof ReliableMessageProcessEvent) && (message = ((ReliableMessageProcessEvent) event).a()) != null) {
            if (message instanceof NodeIdentitySetMessage) {
                MeshLogNew.otaWarn("NodeIdentitySetMessage 收到失败的广播..");
            }
            if (this.busyMsgPool.contains(message)) {
                MeshLogNew.meshMsg("Mesh指令:" + message + ",已经发过一次失败了，就不在补发了..");
                this.busyMsgPool.remove(message);
                return;
            }
            MeshLogNew.meshMsg("Mesh指令:" + message + ",丢失(当前在处理上个消息，没添加到队列)，补到队列中");
            this.busyMsgPool.add(message);
            addAndSend(message);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: processQueue */
    public void lambda$addAndSend$0() {
        StringBuilder sb;
        StringBuilder sb2;
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get()) {
                    MeshMessage message = pollMessageByPriority();
                    if (message != null) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("processQueue 取到消息");
                        if (message.t()) {
                            sb2 = new StringBuilder();
                            sb2.append(" propertyId:");
                            sb2.append(String.format("0x%04X", new Object[]{Integer.valueOf(message.m())}));
                        } else {
                            sb2 = new StringBuilder();
                            sb2.append(" opcode:");
                            sb2.append(String.format("0x%04X", new Object[]{Integer.valueOf(message.k())}));
                        }
                        sb3.append(sb2.toString());
                        MeshLog.i(sb3.toString());
                        processMessage(message);
                    }
                } else {
                    MeshMessage currentProcessing = getCurrentSendingReliableMessage();
                    if (currentProcessing != null) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("processQueue 当前正在处理");
                        if (currentProcessing.t()) {
                            sb = new StringBuilder();
                            sb.append(" propertyId:");
                            sb.append(String.format("0x%04X", new Object[]{Integer.valueOf(currentProcessing.m())}));
                        } else {
                            sb = new StringBuilder();
                            sb.append(" opcode:");
                            sb.append(String.format("0x%04X", new Object[]{Integer.valueOf(currentProcessing.k())}));
                        }
                        sb4.append(sb.toString());
                        MeshLog.i(sb4.toString());
                    }
                }
            }
        }
    }

    public MeshMessage getCurrentSendingReliableMessage() {
        return MeshService.k().g();
    }

    private synchronized void processMessage(MeshMessage message) {
        boolean realiabe = message.u();
        MeshLog.d(this.TAG + ",processMessage,realiabe:" + realiabe);
        if (realiabe) {
            this.isWaiting.set(true);
            this.handler.postDelayed(this.timeoutTask, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        }
        boolean msgSent = MeshService.k().t(message);
        String info = message.getClass().getSimpleName();
        if (!msgSent) {
            MeshLog.d(info + " -> failed");
        }
        innerProcess(msgSent, message);
        if (!realiabe) {
            lambda$addAndSend$0();
        }
    }

    private void innerProcess(boolean msgSent, MeshMessage message) {
        if (!msgSent) {
            try {
                String extra = message.a();
                if (TextUtils.isEmpty(extra) || MsgExtra.a(extra) != null) {
                }
            } catch (Exception e) {
                MeshLog.e("innerProcess exception:" + e.getMessage() + ",msgSent:" + msgSent);
            }
        }
    }

    public void shutDown() {
        this.running = false;
        this.threadPool.shutdown();
        this.cacheQueue.clear();
    }

    public void addCachePool(MeshMessage meshMessage) {
        this.cacheQueue.add(meshMessage);
    }

    public void handlerCachePoolThread() {
        MeshLog.d(this.TAG + " start handler cache pool");
        ExecutorService executorService = this.threadPool;
        if (executorService != null && !executorService.isShutdown()) {
            this.threadPool.execute(this.handlerCmdTask);
        }
    }

    private MeshMessage pollMessageByPriority() {
        String str;
        MeshLog.i("###开始轮询為了得到消息#######");
        int max = -1;
        boolean isFindFocusDeviceCmd = false;
        MeshMessage retMsg = null;
        Iterator<MeshMessage> iterator = this.queue.iterator();
        while (iterator.hasNext()) {
            MeshMessage message = iterator.next();
            NodeInfo nodeInfo = MeshDataManager.deviceFocus;
            if (nodeInfo != null && nodeInfo.meshAddress == message.j() && (!isFindFocusDeviceCmd || message.b().b > max)) {
                max = message.b().b;
                retMsg = message;
                isFindFocusDeviceCmd = true;
            }
            if (!isFindFocusDeviceCmd && message.b().b > max) {
                max = message.b().b;
                retMsg = message;
                isFindFocusDeviceCmd = false;
            }
        }
        if (retMsg != null) {
            this.queue.remove(retMsg);
            StringBuilder sb = new StringBuilder();
            sb.append("得到执行message isFindFocusDeviceCmd:");
            sb.append(isFindFocusDeviceCmd);
            sb.append(",order:");
            sb.append(retMsg.b().b);
            if (retMsg.t()) {
                str = String.format(" propertityId:0x%04X", new Object[]{Integer.valueOf(retMsg.m())});
            } else {
                str = " 标准mesh消息(" + retMsg.getClass().getSimpleName() + ")," + String.format("opcode:0x%04X", new Object[]{Integer.valueOf(retMsg.k())});
            }
            sb.append(str);
            MeshLog.i(sb.toString());
        }
        return retMsg;
    }

    public boolean isMeshQueueEmpty() {
        return this.queue.isEmpty();
    }
}
