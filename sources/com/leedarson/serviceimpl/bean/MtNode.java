package com.leedarson.serviceimpl.bean;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.work.WorkRequest;
import chip.devicecontroller.ChipIdLookup;
import chip.devicecontroller.model.AttributeState;
import chip.devicecontroller.model.ClusterState;
import chip.devicecontroller.model.EndpointState;
import chip.devicecontroller.model.NodeState;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.listener.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: MtNode.kt */
public final class MtNode {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final long HEART_BEAT_INTERVAL = 10000;
    private final long OFFLINE_TIMEOUT = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
    @Nullable
    private c globalCallback;
    @Nullable
    private Handler handler;
    private boolean hasSubscrbe;
    @NotNull
    private final HeartBeatTask heartBeatTask;
    @NotNull
    private List<z1> jobList;
    private final long matterAddr;
    @NotNull
    private final OfflineTask offlineTask;
    @NotNull
    private final MtNode$reportCallback$1 reportCallback;

    public MtNode(long addr) {
        this.matterAddr = addr;
        this.offlineTask = new OfflineTask(this);
        this.heartBeatTask = new HeartBeatTask(this);
        this.jobList = new ArrayList();
        this.reportCallback = new MtNode$reportCallback$1(this);
    }

    public static final /* synthetic */ String access$nodeStateToDebugString(MtNode $this, NodeState nodeState) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, nodeState}, (Object) null, changeQuickRedirect, true, 6187, new Class[]{MtNode.class, NodeState.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : $this.nodeStateToDebugString(nodeState);
    }

    public static final /* synthetic */ void access$parseAttr(MtNode $this, NodeState nodeState) {
        Class[] clsArr = {MtNode.class, NodeState.class};
        if (!PatchProxy.proxy(new Object[]{$this, nodeState}, (Object) null, changeQuickRedirect, true, 6188, clsArr, Void.TYPE).isSupported) {
            $this.parseAttr(nodeState);
        }
    }

    public final long getOFFLINE_TIMEOUT() {
        return this.OFFLINE_TIMEOUT;
    }

    public final long getHEART_BEAT_INTERVAL() {
        return this.HEART_BEAT_INTERVAL;
    }

    public final long getMatterAddr() {
        return this.matterAddr;
    }

    @Nullable
    public final Handler getHandler() {
        return this.handler;
    }

    public final void setHandler(@Nullable Handler handler2) {
        this.handler = handler2;
    }

    @NotNull
    public final OfflineTask getOfflineTask() {
        return this.offlineTask;
    }

    @NotNull
    public final HeartBeatTask getHeartBeatTask() {
        return this.heartBeatTask;
    }

    @Nullable
    public final c getGlobalCallback() {
        return this.globalCallback;
    }

    public final void setGlobalCallback(@Nullable c cVar) {
        this.globalCallback = cVar;
    }

    public final boolean getHasSubscrbe() {
        return this.hasSubscrbe;
    }

    public final void setHasSubscrbe(boolean z) {
        this.hasSubscrbe = z;
    }

    @NotNull
    public final List<z1> getJobList() {
        return this.jobList;
    }

    public final void setJobList(@NotNull List<z1> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 6179, new Class[]{List.class}, Void.TYPE).isSupported) {
            k.e(list, "<set-?>");
            this.jobList = list;
        }
    }

    public final void attachThread(@Nullable HandlerThread handlerThread, @Nullable c callback) {
        Looper it;
        Class[] clsArr = {HandlerThread.class, c.class};
        if (!PatchProxy.proxy(new Object[]{handlerThread, callback}, this, changeQuickRedirect, false, 6180, clsArr, Void.TYPE).isSupported) {
            if (!(this.handler != null || handlerThread == null || (it = handlerThread.getLooper()) == null)) {
                setHandler(new Handler(it));
            }
            this.globalCallback = callback;
        }
    }

    public final void syncDeviceProperties() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6181, new Class[0], Void.TYPE).isSupported) {
            Handler handler2 = this.handler;
            if (handler2 != null) {
                handler2.removeCallbacks(this.offlineTask);
            }
            Handler handler3 = this.handler;
            if (handler3 != null) {
                handler3.removeCallbacks(this.heartBeatTask);
            }
            Handler handler4 = this.handler;
            if (handler4 != null) {
                handler4.post(this.heartBeatTask);
            }
        }
    }

    public final void queryOnline() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6182, new Class[0], Void.TYPE).isSupported) {
            i iVar = i.a;
            a.i(k.l(" MtNode.queryOnline==>Invoke, isMatterSdkInit:", Boolean.valueOf(iVar.v())), new Object[0]);
            if (iVar.v()) {
                this.jobList.add(iVar.i().k(this.matterAddr, new MtNode$queryOnline$job$1(this)));
            }
        }
    }

    public final void shutDown() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6183, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("取消心跳定时和订阅 addr:", Long.valueOf(this.matterAddr)), (String) null, 2, (Object) null);
            Handler handler2 = this.handler;
            if (handler2 != null) {
                handler2.removeCallbacks(this.offlineTask);
            }
            Handler handler3 = this.handler;
            if (handler3 != null) {
                handler3.removeCallbacks(this.heartBeatTask);
            }
            for (z1 job : this.jobList) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.b(kVar, "job isActive:" + job.isActive() + ",isCancelled:" + job.isCancelled() + ",isCompleted:" + job.I() + 10, (String) null, 2, (Object) null);
                if (job.isActive() && job.isCancelled()) {
                    z1.a.a(job, (CancellationException) null, 1, (Object) null);
                }
            }
        }
    }

    /* compiled from: MtNode.kt */
    public final class OfflineTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MtNode this$0;

        public OfflineTask(MtNode this$02) {
            k.e(this$02, "this$0");
            this.this$0 = this$02;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6190, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.h(kVar, "检测到设备离线:addr:$" + this.this$0.getMatterAddr() + ",超时时间:" + this.this$0.getOFFLINE_TIMEOUT(), (String) null, 2, (Object) null);
                c globalCallback = this.this$0.getGlobalCallback();
                if (globalCallback != null) {
                    globalCallback.a(this.this$0.getMatterAddr(), 0);
                }
                this.this$0.setHasSubscrbe(false);
            }
        }
    }

    /* compiled from: MtNode.kt */
    public final class HeartBeatTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MtNode this$0;

        public HeartBeatTask(MtNode this$02) {
            k.e(this$02, "this$0");
            this.this$0 = this$02;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6189, new Class[0], Void.TYPE).isSupported) {
                this.this$0.queryOnline();
                Handler handler = this.this$0.getHandler();
                if (handler != null) {
                    handler.postDelayed(this.this$0.getHeartBeatTask(), this.this$0.getHEART_BEAT_INTERVAL());
                }
            }
        }
    }

    public final void subscribe() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6184, new Class[0], Void.TYPE).isSupported) {
            i.a.M(this.matterAddr, new MtNode$subscribe$1(this), this.reportCallback);
        }
    }

    private final String nodeStateToDebugString(NodeState nodeState) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{nodeState}, this, changeQuickRedirect, false, 6185, new Class[]{NodeState.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        MtNode mtNode = this;
        NodeState nodeState2 = nodeState;
        StringBuilder stringBuilder = new StringBuilder();
        Map $this$forEach$iv = nodeState2.getEndpointStates();
        k.d($this$forEach$iv, "nodeState.endpointStates");
        int $i$f$forEach = false;
        Iterator<Map.Entry<Integer, EndpointState>> it = $this$forEach$iv.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element$iv = it.next();
            Map.Entry $dstr$endpointId$endpointState = element$iv;
            stringBuilder.append("Endpoint " + ((Integer) $dstr$endpointId$endpointState.getKey()) + ": {\n");
            Map $this$forEach$iv2 = ((EndpointState) $dstr$endpointId$endpointState.getValue()).getClusterStates();
            k.d($this$forEach$iv2, "endpointState.clusterStates");
            for (Map.Entry $dstr$clusterId$clusterState : $this$forEach$iv2.entrySet()) {
                MtNode mtNode2 = mtNode;
                Long clusterId = (Long) $dstr$clusterId$clusterState.getKey();
                NodeState nodeState3 = nodeState2;
                StringBuilder sb = new StringBuilder();
                Map $this$forEach$iv3 = $this$forEach$iv;
                sb.append(9);
                k.d(clusterId, "clusterId");
                sb.append(ChipIdLookup.clusterIdToName(clusterId.longValue()));
                sb.append("Cluster: {\n");
                stringBuilder.append(sb.toString());
                Map attributeStates = ((ClusterState) $dstr$clusterId$clusterState.getValue()).getAttributeStates();
                k.d(attributeStates, "clusterState.attributeStates");
                int i = false;
                for (Map.Entry $dstr$attributeId$attributeState : attributeStates.entrySet()) {
                    Map $this$forEach$iv4 = attributeStates;
                    Long attributeId = (Long) $dstr$attributeId$attributeState.getKey();
                    int $i$f$forEach2 = i;
                    int $i$f$forEach3 = $i$f$forEach;
                    long longValue = clusterId.longValue();
                    Long clusterId2 = clusterId;
                    k.d(attributeId, "attributeId");
                    String attributeName = ChipIdLookup.attributeIdToName(longValue, attributeId.longValue());
                    stringBuilder.append("\t\t" + attributeName + ": " + ((AttributeState) $dstr$attributeId$attributeState.getValue()).getValue() + 10);
                    it = it;
                    attributeStates = $this$forEach$iv4;
                    $i$f$forEach = $i$f$forEach3;
                    i = $i$f$forEach2;
                    clusterId = clusterId2;
                    element$iv = element$iv;
                }
                Map map = attributeStates;
                int i2 = i;
                int i3 = $i$f$forEach;
                Iterator<Map.Entry<Integer, EndpointState>> it2 = it;
                Map.Entry entry = element$iv;
                stringBuilder.append("\t}\n");
                nodeState2 = nodeState3;
                mtNode = mtNode2;
                $this$forEach$iv = $this$forEach$iv3;
            }
            MtNode mtNode3 = mtNode;
            Map map2 = $this$forEach$iv;
            int i4 = $i$f$forEach;
            Iterator<Map.Entry<Integer, EndpointState>> it3 = it;
            Map.Entry entry2 = element$iv;
            stringBuilder.append("}\n");
            nodeState2 = nodeState2;
            mtNode = mtNode3;
        }
        MtNode mtNode4 = mtNode;
        NodeState nodeState4 = nodeState2;
        String sb2 = stringBuilder.toString();
        k.d(sb2, "stringBuilder.toString()");
        return sb2;
    }

    private final void parseAttr(NodeState nodeState) {
        Map $this$forEach$iv;
        NodeState nodeState2;
        Iterator<Map.Entry<Integer, EndpointState>> it;
        int $i$f$forEach;
        int cct;
        int i = 1;
        if (!PatchProxy.proxy(new Object[]{nodeState}, this, changeQuickRedirect, false, 6186, new Class[]{NodeState.class}, Void.TYPE).isSupported) {
            NodeState nodeState3 = nodeState;
            Map $this$forEach$iv2 = nodeState3.getEndpointStates();
            k.d($this$forEach$iv2, "nodeState.endpointStates");
            int $i$f$forEach2 = 0;
            Iterator<Map.Entry<Integer, EndpointState>> it2 = $this$forEach$iv2.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry element$iv = it2.next();
                Map.Entry $dstr$endpointId$endpointState = element$iv;
                Integer num = (Integer) $dstr$endpointId$endpointState.getKey();
                EndpointState endpointState = (EndpointState) $dstr$endpointId$endpointState.getValue();
                Map<Long, ClusterState> clusterStates = endpointState.getClusterStates();
                ClusterEnum clusterEnum = ClusterEnum.OnOff;
                if (clusterStates.containsKey(Long.valueOf(clusterEnum.getClusterId()))) {
                    ClusterState clusterState = endpointState.getClusterState(clusterEnum.getClusterId());
                    AttributeState attr = clusterState == null ? null : clusterState.getAttributeState(clusterEnum.getAttrId());
                    if (attr != null) {
                        JSONObject json = new JSONObject();
                        Object value = attr.getValue();
                        if (value != null) {
                            if (((Boolean) value).booleanValue()) {
                                json.put("OnOff", i);
                            } else {
                                json.put("OnOff", 0);
                            }
                            c globalCallback2 = getGlobalCallback();
                            nodeState2 = nodeState3;
                            $this$forEach$iv = $this$forEach$iv2;
                            if (globalCallback2 != null) {
                                globalCallback2.h(getMatterAddr(), json);
                            }
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
                        }
                    } else {
                        nodeState2 = nodeState3;
                        $this$forEach$iv = $this$forEach$iv2;
                    }
                } else {
                    nodeState2 = nodeState3;
                    $this$forEach$iv = $this$forEach$iv2;
                }
                Map<Long, ClusterState> clusterStates2 = endpointState.getClusterStates();
                ClusterEnum clusterEnum2 = ClusterEnum.HSL_H;
                if (clusterStates2.containsKey(Long.valueOf(clusterEnum2.getClusterId()))) {
                    ClusterState clusterState2 = endpointState.getClusterState(clusterEnum2.getClusterId());
                    AttributeState attr_h = clusterState2 == null ? null : clusterState2.getAttributeState(clusterEnum2.getAttrId());
                    JSONObject json2 = new JSONObject();
                    JSONArray hslArr = new JSONArray();
                    if (attr_h != null) {
                        hslArr.put(attr_h.getValue());
                    }
                    AttributeState attr_s = clusterState2 == null ? null : clusterState2.getAttributeState(ClusterEnum.HSL_S.getAttrId());
                    if (attr_s != null) {
                        hslArr.put(attr_s.getValue());
                    }
                    json2.put("HSL", (Object) hslArr);
                    c globalCallback3 = getGlobalCallback();
                    $i$f$forEach = $i$f$forEach2;
                    it = it2;
                    if (globalCallback3 != null) {
                        globalCallback3.h(getMatterAddr(), json2);
                    }
                } else {
                    $i$f$forEach = $i$f$forEach2;
                    it = it2;
                }
                Map<Long, ClusterState> clusterStates3 = endpointState.getClusterStates();
                ClusterEnum clusterEnum3 = ClusterEnum.CCT;
                if (clusterStates3.containsKey(Long.valueOf(clusterEnum3.getClusterId()))) {
                    ClusterState clusterState3 = endpointState.getClusterState(clusterEnum3.getClusterId());
                    AttributeState attr2 = clusterState3 == null ? null : clusterState3.getAttributeState(clusterEnum3.getAttrId());
                    if (attr2 == null || k.a(attr2.getValue(), 0)) {
                        Map.Entry entry = element$iv;
                    } else {
                        JSONObject json3 = new JSONObject();
                        if (attr2.getValue() instanceof Integer) {
                            Object value2 = attr2.getValue();
                            if (value2 != null) {
                                cct = 1000000 / ((Integer) value2).intValue();
                            } else {
                                throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
                            }
                        } else {
                            long j = (long) 1000000;
                            Object value3 = attr2.getValue();
                            if (value3 != null) {
                                cct = (int) (j / ((Long) value3).longValue());
                            } else {
                                throw new NullPointerException("null cannot be cast to non-null type kotlin.Long");
                            }
                        }
                        json3.put("CCT", cct);
                        c globalCallback4 = getGlobalCallback();
                        Map.Entry entry2 = element$iv;
                        if (globalCallback4 != null) {
                            globalCallback4.h(getMatterAddr(), json3);
                        }
                    }
                }
                Map<Long, ClusterState> clusterStates4 = endpointState.getClusterStates();
                ClusterEnum clusterEnum4 = ClusterEnum.DIM;
                if (clusterStates4.containsKey(Long.valueOf(clusterEnum4.getClusterId()))) {
                    ClusterState clusterState4 = endpointState.getClusterState(clusterEnum4.getClusterId());
                    AttributeState attr3 = clusterState4 == null ? null : clusterState4.getAttributeState(clusterEnum4.getAttrId());
                    if (attr3 != null) {
                        JSONObject json4 = new JSONObject();
                        Object value4 = attr3.getValue();
                        if (value4 != null) {
                            float dim = ((float) ((((Integer) value4).intValue() * 100) / 254)) * 1.0f;
                            json4.put("Dimming", dim > 100.0f ? 100 : Float.valueOf(dim));
                            c globalCallback5 = getGlobalCallback();
                            if (globalCallback5 != null) {
                                AttributeState attributeState = attr3;
                                float f = dim;
                                globalCallback5.h(getMatterAddr(), json4);
                            }
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
                        }
                    }
                }
                nodeState3 = nodeState2;
                $i$f$forEach2 = $i$f$forEach;
                $this$forEach$iv2 = $this$forEach$iv;
                it2 = it;
                i = 1;
            }
        }
    }
}
