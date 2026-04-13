package meshsdk.ctrl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.NonNull;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionSetMessage;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.event.MeshEvent;
import com.telink.ble.mesh.foundation.event.ReliableMessageProcessEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NodeInfo;
import org.json.JSONObject;

public class GroupCtrlAdapter extends CtrlLifecycle implements MeshGroupCallback, EventListener<String> {
    public static final int MAX_RETRY_COUNT = 0;
    public static final long RETRY_TIMEOUT = 3000;
    public static final String TAG = "GroupCtrlAdapter";
    public static final int TIMEOUT_WAIT_MESH_ONLINE = 20000;
    public static final int WHAT_WAIT_MESH_ONLINE = -1000;
    private HashMap<String, MeshGroupCallback> callbackHashMap;
    private GroupCtrl groupCtrl;
    private Handler handler;
    private AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private GroupMemberWrap processingGroupMemberWrap;
    private Queue<GroupMemberWrap> queue;
    private int retryCount;

    public GroupCtrlAdapter(SIGMesh sigMesh, HandlerThread handlerThread) {
        super(sigMesh);
        this.groupCtrl = new GroupCtrl(sigMesh);
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.callbackHashMap = new HashMap<>();
        this.handler = new Handler(handlerThread.getLooper()) {
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what != -1000) {
                    GroupMemberWrap groupMemberWrap = (GroupMemberWrap) msg.obj;
                    String mac = groupMemberWrap.nodeInfo.macAddress;
                    GroupCtrlAdapter.this.onFailTimeout(-1, "groupMember 任务超时 mac=:" + mac, groupMemberWrap);
                }
            }
        };
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_PROCESSING", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED", this);
    }

    public void onCreate() {
    }

    public JSONObject controlGroup(int groupAddr, int modelId, Object value, int groupId) {
        return this.groupCtrl.controlGroup(groupAddr, modelId, value, groupId);
    }

    public synchronized void onFailByMeshDisconnect() {
    }

    public synchronized void addGroupMember(NodeInfo node, int groupAddr, int meshAddress, MeshGroupCallback meshGroupCallback) {
        MeshDataManager.flagAddGroup = true;
        GroupMemberWrap item = new GroupMemberWrap(node, groupAddr, meshAddress, true);
        this.callbackHashMap.put(getCallbackKey(meshAddress, groupAddr), meshGroupCallback);
        if (SIGMesh.getInstance().hasConnected()) {
            log("添加到队列,mac:" + node.macAddress + ",meshAddress:" + meshAddress);
            this.queue.add(item);
            processGroupMember();
        } else {
            log("addGroupMember mesh has not connected, callback to add local group");
            this.callbackHashMap.get(getCallbackKey(meshAddress, groupAddr)).onFail(-1, "disconnected", meshAddress, groupAddr, 0);
            this.callbackHashMap.remove(getCallbackKey(meshAddress, groupAddr));
        }
    }

    public synchronized void removeGroupMember(NodeInfo node, int groupAddr, int meshAddress, MeshGroupCallback meshGroupCallback) {
        if (!SIGMesh.getInstance().hasConnected()) {
            log("removeGroupMember return,mesh has not connected");
            meshGroupCallback.onFail(-1, "removeGroupMember SIGMesh network has not connected", 0, groupAddr, -1);
            return;
        }
        MeshDataManager.flagAddGroup = true;
        this.queue.add(new GroupMemberWrap(node, groupAddr, meshAddress, false));
        this.callbackHashMap.put(getCallbackKey(meshAddress, groupAddr), meshGroupCallback);
        processGroupMember();
    }

    public synchronized String getCallbackKey(int meshAddress, int groupAddress) {
        return meshAddress + "_" + groupAddress;
    }

    private void processGroupMember() {
        synchronized (this.mLock) {
            if (!this.isWaiting.get()) {
                GroupMemberWrap wrap = this.queue.poll();
                if (wrap != null) {
                    this.isWaiting.compareAndSet(false, true);
                    this.processingGroupMemberWrap = wrap;
                    if (!wrap.isAdd) {
                        this.groupCtrl.unregisterGroup(wrap.nodeInfo, wrap.groupAddr, wrap.meshAddress, this);
                    } else if (SIGMesh.getInstance().hasConnected()) {
                        this.groupCtrl.registerGroup(wrap.nodeInfo, wrap.groupAddr, wrap.meshAddress, this);
                    } else {
                        addToCustomGroup(this.processingGroupMemberWrap);
                    }
                }
            } else {
                log("当前" + getCurrentOpr() + "组成员正在执行 isWaiting =true，继续排队等待...");
            }
        }
    }

    public void onSuccess(int meshAddr, int groupAddr, int nouse) {
        log("时序4: onSuccess " + getCurrentOpr() + "组成功");
        this.handler.removeMessages(meshAddr);
        MeshGroupCallback callback = this.callbackHashMap.get(getCallbackKey(meshAddr, groupAddr));
        if (callback != null) {
            callback.onSuccess(meshAddr, groupAddr, this.retryCount);
            this.callbackHashMap.remove(getCallbackKey(meshAddr, groupAddr));
        } else {
            log(getCurrentOpr() + "组成功了，callback是空的，哪里有问题吧，请检查逻辑异常场景");
        }
        unlock();
        processGroupMember();
    }

    public void onFail(int code, String msg, int meshAddr, int groupAddress, int nouse) {
        this.handler.removeMessages(meshAddr);
        MeshGroupCallback callback = this.callbackHashMap.get(getCallbackKey(meshAddr, groupAddress));
        if (callback != null) {
            callback.onFail(code, msg, meshAddr, groupAddress, this.retryCount);
            this.callbackHashMap.remove(getCallbackKey(meshAddr, groupAddress));
        }
        unlock();
        processGroupMember();
    }

    public void onFailTimeout(int code, String msg, GroupMemberWrap groupMemberWrap) {
        this.handler.removeMessages(groupMemberWrap.meshAddress);
        int i = this.retryCount;
        if (i < 0) {
            this.retryCount = i + 1;
            log("groupMember mac:" + groupMemberWrap.nodeInfo.macAddress + ",第:" + this.retryCount + "次重试");
            if (groupMemberWrap.isAdd) {
                log("onFailTimeout processGroupMember#registerGroup");
                this.groupCtrl.registerGroup(groupMemberWrap.nodeInfo, groupMemberWrap.groupAddr, groupMemberWrap.meshAddress, this);
                return;
            }
            log("onFailTimeout processGroupMember#unregisterGroup");
            this.groupCtrl.unregisterGroup(groupMemberWrap.nodeInfo, groupMemberWrap.groupAddr, groupMemberWrap.meshAddress, this);
            return;
        }
        log("groupMember mac:" + groupMemberWrap.nodeInfo.macAddress + ",达到重试次数:" + 0 + "，还是失败");
        MeshGroupCallback callback = this.callbackHashMap.get(getCallbackKey(groupMemberWrap.meshAddress, groupMemberWrap.groupAddr));
        if (callback != null) {
            callback.onFail(code, msg, groupMemberWrap.meshAddress, groupMemberWrap.groupAddr, this.retryCount);
            this.callbackHashMap.remove(getCallbackKey(groupMemberWrap.meshAddress, groupMemberWrap.groupAddr));
        }
        unlock();
        processGroupMember();
    }

    public static class GroupMemberWrap {
        public int groupAddr;
        public boolean isAdd;
        public int meshAddress;
        public NodeInfo nodeInfo;

        public GroupMemberWrap(NodeInfo nodeInfo2, int groupAddr2, int meshAddress2, boolean isAdd2) {
            this.nodeInfo = nodeInfo2;
            this.isAdd = isAdd2;
            this.groupAddr = groupAddr2;
            this.meshAddress = meshAddress2;
        }
    }

    public void unlock() {
        this.isWaiting.set(false);
        this.retryCount = 0;
        this.processingGroupMemberWrap = null;
        log("unlock");
        HashMap<String, MeshGroupCallback> hashMap = this.callbackHashMap;
        if (hashMap == null || hashMap.size() == 0) {
            log("释放flagAddGroup = false，可以继续进行数据更新");
            MeshDataManager.flagAddGroup = false;
        }
    }

    public synchronized void addToCustomGroup(GroupMemberWrap item) {
        MeshGroupCallback callback = this.callbackHashMap.get(getCallbackKey(item.meshAddress, item.groupAddr));
        if (callback != null) {
            log("ble disconnected when add mesh group, now add to customgroup");
            this.callbackHashMap.remove(getCallbackKey(item.meshAddress, item.groupAddr));
            callback.onFail(-1, "ble disconnected", item.meshAddress, item.groupAddr, 0);
            unlock();
            processGroupMember();
        } else {
            log("ble disconnected when add mesh group, but call back is null 请检查业务是否有问题");
        }
    }

    public void performed(Event<String> event) {
        String type = event.getType();
        if ("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_PROCESSING".equals(type)) {
            if (event instanceof ReliableMessageProcessEvent) {
                MeshMessage message = ((ReliableMessageProcessEvent) event).a();
                if ((message instanceof ModelSubscriptionSetMessage) && ((ModelSubscriptionSetMessage) message).J() && this.processingGroupMemberWrap != null) {
                    log("时序3: 收到ModelSubscriptionSetMessage 第一个model 发送出去的通知，开启" + getCurrentOpr() + "组成员超时检测");
                    this.handler.removeMessages(this.processingGroupMemberWrap.meshAddress);
                    Message msg = Message.obtain(this.handler);
                    GroupMemberWrap groupMemberWrap = this.processingGroupMemberWrap;
                    msg.obj = groupMemberWrap;
                    msg.what = groupMemberWrap.meshAddress;
                    this.handler.sendMessageDelayed(msg, RETRY_TIMEOUT);
                }
            }
        } else if (!"com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN".equals(type) && "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED".equals(type) && (event instanceof MeshEvent) && ((MeshEvent) event).a()) {
            log("收到mesh网络断开, isAddGroup:" + MeshDataManager.flagAddGroup);
            if (MeshDataManager.flagAddGroup) {
                GroupMemberWrap groupMemberWrap2 = this.processingGroupMemberWrap;
                if (groupMemberWrap2 != null) {
                    String opt = groupMemberWrap2.isAdd ? "建" : "删";
                    log("当前正在" + opt + "组，收到mesh网络断事件，把当前的任务:" + this.processingGroupMemberWrap + ",加到本地组");
                    addToCustomGroup(this.processingGroupMemberWrap);
                }
            } else if (this.processingGroupMemberWrap != null) {
                log("检查逻辑是否有问题，mesh网络断开了。。");
            }
        }
    }

    private void log(String message) {
        String text;
        GroupMemberWrap groupMemberWrap = this.processingGroupMemberWrap;
        if (groupMemberWrap == null || groupMemberWrap.nodeInfo == null) {
            text = "";
        } else {
            text = "currentprocessing mac:" + this.processingGroupMemberWrap.nodeInfo.macAddress;
        }
        MeshLog.logAddGroup("GroupCtrlAdapter," + message + "," + text);
    }

    private String getCurrentOpr() {
        GroupMemberWrap groupMemberWrap = this.processingGroupMemberWrap;
        if (groupMemberWrap == null) {
            return "";
        }
        return groupMemberWrap.isAdd ? "添加" : "删除";
    }
}
