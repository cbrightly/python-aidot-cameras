package meshsdk.datamgr;

import android.os.Handler;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.listener.a;
import com.leedarson.serviceimpl.reporters.g;
import com.leedarson.serviceimpl.reporters.k;
import com.telink.ble.mesh.core.ble.GattConnection;
import com.telink.ble.mesh.foundation.MeshService;
import com.tencent.bugly.Bugly;
import io.reactivex.disposables.b;
import io.reactivex.l;
import io.reactivex.q;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.cache.CacheHandler;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.callback.MeshGroupCallbackWrapper;
import meshsdk.callback.OnHttpCallback;
import meshsdk.ctrl.MeshMessagePool;
import meshsdk.model.CustomGroup;
import meshsdk.model.GroupInfo;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.CloudDevice;
import meshsdk.model.json.CloudGroup;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONArray;
import org.json.JSONObject;

public class GroupFixHelper {
    private static final int DEVICE_MAX_GROUP_LIMIT = 8;
    private static final String TAG = "GroupFixHelper";
    private static GroupFixHelper instance = new GroupFixHelper();
    private Runnable checkRunnable = new Runnable() {
        public void run() {
            GroupFixHelper.this.startFixCheck();
        }
    };
    HashMap<String, ArrayList<Integer>> deviceBindedGroups;
    /* access modifiers changed from: private */
    public Handler handler = new Handler(SIGMesh.getInstance().getHandlerThread().getLooper());
    /* access modifiers changed from: private */
    public LDSGroupApi ldsGroupApi = new LDSGroupApi();

    private GroupFixHelper() {
    }

    public static GroupFixHelper getInstance() {
        return instance;
    }

    public static abstract class GetDeviceTimeoutRunnable implements Runnable {
        private String macAddress;

        public GetDeviceTimeoutRunnable(String macAddress2) {
            this.macAddress = macAddress2;
        }

        public String getMacAddress() {
            return this.macAddress;
        }

        public String toString() {
            return "GetDeviceTimeoutRunnable{macAddress='" + this.macAddress + '\'' + '}';
        }
    }

    public void startFixCheck() {
        if (SIGMesh.getInstance().hasConnected()) {
            this.handler.removeCallbacks(this.checkRunnable);
            if (!MeshMessagePool.getInstance().isMeshQueueEmpty() || !MeshService.k().l().I0() || !SIGMesh.getInstance().hasConnected() || MeshDataManager.flagNetConfingAdddevices || MeshDataManager.flagAddGroup || MeshDataManager.flagOTA) {
                log("mesh网络通?" + SIGMesh.getInstance().hasConnected() + ",队列空?" + MeshMessagePool.getInstance().isMeshQueueEmpty() + ",isOTA:" + MeshDataManager.flagOTA + ",isAddDevices:" + MeshDataManager.flagNetConfingAdddevices + ",addGroup:" + MeshDataManager.flagAddGroup);
                this.handler.postDelayed(this.checkRunnable, CacheHandler.delayTime);
                return;
            }
            GattConnection gattConnection = MeshService.k().l().y0();
            String emtpyGatterRequest = "";
            if (gattConnection != null) {
                emtpyGatterRequest = gattConnection.M().isEmpty() + emtpyGatterRequest;
            }
            logE("mesh网络空闲，开始执行自动修复组功能,gatterRequestEmpty:" + emtpyGatterRequest);
            fixRemoveUnuseGroup();
        }
    }

    private void fixRemoveUnuseGroup() {
        log("执行自动检测删除无用组逻辑");
        final List<CloudGroup> cloudGroups = new ArrayList<>();
        l.I(this.ldsGroupApi.getVisibleGroups(), this.ldsGroupApi.getMusicGroup()).a(new q<List<CloudGroup>>() {
            public void onSubscribe(b d) {
            }

            public void onNext(List<CloudGroup> groups) {
                cloudGroups.addAll(groups);
            }

            public void onError(Throwable e) {
                GroupFixHelper.logE("fixRemoveUnuseGroup error:" + e.getMessage() + ",try to fixAddCustomGroupToMeshGroup");
                GroupFixHelper.this.fixAddCustomGroupToMeshGroup("fixRemoveUnuseGroup getCloudGroups error");
            }

            public void onComplete() {
                GroupFixHelper.log("fixRemoveUnuseGroup query group onComplete ==>" + cloudGroups);
                if (cloudGroups.size() > 0) {
                    List<GroupInfo> tempGroupInfos = new ArrayList<>();
                    tempGroupInfos.addAll(SIGMesh.getInstance().getMeshInfo().groups);
                    GroupFixHelper.this.checkRemoveGroupInMeshJson(cloudGroups);
                    for (CloudGroup cloudGroup : cloudGroups) {
                        for (GroupInfo groupInfo : tempGroupInfos) {
                            if (cloudGroup.getId() == groupInfo.groupId) {
                                cloudGroup.setGroupAddress(groupInfo.address);
                            }
                        }
                    }
                    GroupFixHelper.this.getDeviceGroups(new a() {
                        public void onSuccess(Object object) {
                            if (GroupFixHelper.this.deviceBindedGroups.size() > 0) {
                                AnonymousClass2 r0 = AnonymousClass2.this;
                                GroupFixHelper groupFixHelper = GroupFixHelper.this;
                                groupFixHelper.checkRemoveGroupMember(cloudGroups, groupFixHelper.deviceBindedGroups);
                            }
                        }

                        public void onFail(String msg) {
                        }
                    });
                    return;
                }
                GroupFixHelper.this.fixAddCustomGroupToMeshGroup("fixRemoveUnuseGroup getCloudEmpty");
            }
        });
    }

    /* access modifiers changed from: private */
    public void fixAddCustomGroupToMeshGroup(final String bz) {
        final List<CloudGroup> cloudGroups = new ArrayList<>();
        this.ldsGroupApi.getVisibleGroups().b0(com.leedarson.base.http.observer.l.g).J(io.reactivex.android.schedulers.a.a()).a(new q<List<CloudGroup>>() {
            public void onSubscribe(b d) {
            }

            public void onNext(List<CloudGroup> groups) {
                cloudGroups.addAll(groups);
            }

            public void onError(Throwable e) {
                GroupFixHelper.logE("fixAddCustomGroupToMeshGroup queryCloud error:" + e.getMessage());
            }

            public void onComplete() {
                GroupFixHelper.this.checkAddGroupMember(bz, cloudGroups);
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkRemoveGroupInMeshJson(List<CloudGroup> cloudGroups) {
        boolean hasChanged = false;
        List<GroupInfo> groupInfos = SIGMesh.getInstance().getMeshInfo().groups;
        log("checkRemoveGroupInMeshJson 本地标准组:" + groupInfos + ",云端:" + cloudGroups);
        if (groupInfos != null && groupInfos.size() > 0) {
            Iterator<GroupInfo> iterator = groupInfos.iterator();
            while (iterator.hasNext()) {
                boolean delete = true;
                GroupInfo groupInfo = iterator.next();
                for (CloudGroup cloudGroup : cloudGroups) {
                    if (cloudGroup.getId() == groupInfo.groupId) {
                        delete = false;
                    }
                }
                if (delete) {
                    logE("groupId:" + groupInfo.groupId + "不存在云端中，从meshjson中删除");
                    iterator.remove();
                    hasChanged = true;
                }
            }
        }
        Iterator<CustomGroup> iterator2 = SIGMesh.getInstance().getMeshInfo().getCustomGroups().values().iterator();
        while (iterator2.hasNext()) {
            boolean inCloud = false;
            CustomGroup customGroup = iterator2.next();
            if (customGroup.getDevices().size() == 0) {
                logE("本地组:" + customGroup.getGroupId() + " 下不存在设备，删除了吧");
                iterator2.remove();
                hasChanged = true;
            } else {
                for (CloudGroup cloudGroup2 : cloudGroups) {
                    if (customGroup.getGroupId().equals(String.valueOf(cloudGroup2.getId()))) {
                        inCloud = true;
                    }
                }
                if (!inCloud) {
                    iterator2.remove();
                    hasChanged = true;
                    logE("checkRemoveGroupInMeshJson 本地组groupId:" + customGroup.getGroupId() + "不存在云端中，从meshjson中删除");
                }
            }
        }
        if (hasChanged) {
            SIGMesh.getInstance().getMeshInfo().saveOrUpdate(BaseApplication.b(), "remove group not in cloud");
            SharedPreferenceHelper.setNeedUpload(SIGMesh.getInstance().getContext(), true);
            k.a("自动修复删除meshjson无用组，设置上传标志");
        }
    }

    /* access modifiers changed from: private */
    public void getDeviceGroups(a callbackListener) {
        this.deviceBindedGroups = new HashMap<>();
        int requestCount = 0;
        for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
            if (nodeInfo.isOnline()) {
                requestCount++;
            }
        }
        final int count = requestCount;
        final List<String> callbackCount = new ArrayList<>();
        for (NodeInfo nodeInfo2 : SIGMesh.getInstance().getMeshInfo().nodes) {
            if (nodeInfo2.isOnline()) {
                final a aVar = callbackListener;
                AnonymousClass4 r0 = new GetDeviceTimeoutRunnable(nodeInfo2.macAddress) {
                    public void run() {
                        GroupFixHelper.logE("查询设备:" + getMacAddress() + " 绑定组超时");
                        callbackCount.add("timeout");
                        if (callbackCount.size() == count) {
                            aVar.onSuccess(GroupFixHelper.this.deviceBindedGroups);
                        }
                    }
                };
                this.handler.postDelayed(r0, 10000);
                log("请求查询设备:" + nodeInfo2.macAddress + "绑定的组");
                SIGMesh instance2 = SIGMesh.getInstance();
                String str = nodeInfo2.macAddress;
                final List<String> list = callbackCount;
                final NodeInfo nodeInfo3 = nodeInfo2;
                final AnonymousClass4 r9 = r0;
                final int i = count;
                AnonymousClass4 r16 = r0;
                AnonymousClass5 r02 = r5;
                final a aVar2 = callbackListener;
                AnonymousClass5 r5 = new MeshCustomcmdCallback() {
                    public void onSuccess(Object data) {
                        list.add(FirebaseAnalytics.Param.SUCCESS);
                        GroupFixHelper.logE("查询设备:" + nodeInfo3.macAddress + " 绑定组成功:" + data + ",removeCallback:" + r9);
                        GroupFixHelper.this.handler.removeCallbacks(r9);
                        if (data != null && (data instanceof ArrayList)) {
                            GroupFixHelper.this.deviceBindedGroups.put(nodeInfo3.macAddress, (ArrayList) data);
                        }
                        if (list.size() == i) {
                            aVar2.onSuccess(GroupFixHelper.this.deviceBindedGroups);
                        }
                    }

                    public void onFail(int code, String msg, Object data) {
                        GroupFixHelper.this.handler.removeCallbacks(r9);
                        GroupFixHelper.logE("查询设备:" + nodeInfo3.macAddress + " 绑定组失败:" + code + ",msg:" + msg + ",removeCallback:" + r9);
                        list.add(Bugly.SDK_IS_DEV);
                        if (list.size() == i) {
                            aVar2.onSuccess(GroupFixHelper.this.deviceBindedGroups);
                        }
                    }
                };
                instance2.getDeviceGroups(str, r02);
            }
        }
        if (requestCount == 0) {
            logE("无在线设备，不需要走自动修复组逻辑");
        }
    }

    /* access modifiers changed from: private */
    public void checkRemoveGroupMember(final List<CloudGroup> cloudGroups, final HashMap<String, ArrayList<Integer>> bindedGroups) {
        final List<TaskBean> targetRemoveGroupMemberBeans = new ArrayList<>();
        for (String mac : bindedGroups.keySet()) {
            Iterator it = bindedGroups.get(mac).iterator();
            while (it.hasNext()) {
                Integer groupAddress = (Integer) it.next();
                int deleteGroupId = 0;
                boolean delete = true;
                for (CloudGroup cloudGroup : cloudGroups) {
                    if (cloudGroup.getGroupAddress() == groupAddress.intValue()) {
                        delete = false;
                    }
                    deleteGroupId = cloudGroup.getId();
                }
                if (delete) {
                    targetRemoveGroupMemberBeans.add(new TaskBean(mac, groupAddress, Integer.valueOf(deleteGroupId), "组没在云端"));
                }
            }
        }
        getCloudDevices(new OnHttpCallback<List<CloudDevice>>() {
            public void onResult(List<CloudDevice> cloudDevices) {
                CloudDevice cloudDevice;
                MeshLog.i("获取家下的设备列表-成功");
                if (cloudDevices != null) {
                    HashMap<String, CloudDevice> devices = new HashMap<>();
                    for (CloudDevice cloudDevice2 : cloudDevices) {
                        devices.put(cloudDevice2.mac, cloudDevice2);
                    }
                    for (String mac : bindedGroups.keySet()) {
                        Iterator it = ((ArrayList) bindedGroups.get(mac)).iterator();
                        while (it.hasNext()) {
                            Integer groupAddress = (Integer) it.next();
                            for (CloudGroup cloudGroup : cloudGroups) {
                                if (cloudGroup.getGroupAddress() == groupAddress.intValue() && !TextUtils.isEmpty(cloudGroup.getRoomId()) && (cloudDevice = devices.get(mac)) != null && !cloudGroup.getRoomId().equalsIgnoreCase(cloudDevice.roomId)) {
                                    GroupFixHelper.log("组id:" + cloudGroup.getId() + "所属的roomid:" + cloudGroup.getRoomId() + "与设备:" + mac + "所在的roomid:" + cloudDevice.roomId + "不一致，需要解绑");
                                    targetRemoveGroupMemberBeans.add(new TaskBean(mac, groupAddress, Integer.valueOf(cloudGroup.getId()), "组归属的roomid与设备归属的roomid不一致"));
                                }
                            }
                        }
                    }
                    GroupFixHelper.this.toRemoveGroupMember(targetRemoveGroupMemberBeans, false);
                    return;
                }
                GroupFixHelper.this.toRemoveGroupMember(targetRemoveGroupMemberBeans, false);
            }
        });
    }

    public void getCloudDevices(OnHttpCallback<List<CloudDevice>> callback) {
        new LDSDeviceApi().getCloudDevices(SharedPreferenceHelper.getHouseId(BaseApplication.b()), callback);
    }

    /* access modifiers changed from: private */
    public void toRemoveGroupMember(List<TaskBean> targetRemoveGroupMemberBeans, boolean onlyRemoveGroupMember) {
        if (targetRemoveGroupMemberBeans.size() > 0) {
            sendRemoveGroup(targetRemoveGroupMemberBeans, onlyRemoveGroupMember, (MeshCustomcmdCallback) null);
        } else if (!onlyRemoveGroupMember) {
            fixAddCustomGroupToMeshGroup("no devices group to remove");
        }
    }

    /* access modifiers changed from: private */
    public void sendRemoveGroup(List<TaskBean> targetRemoveGroupMemberBeans, boolean onlyRemoveGroupMember, MeshCustomcmdCallback callback) {
        MeshCustomcmdCallback meshCustomcmdCallback = callback;
        log("sendRemoveGroup size:" + targetRemoveGroupMemberBeans.size());
        final JSONArray completeRemoveMemberArray = new JSONArray();
        Iterator<TaskBean> it = targetRemoveGroupMemberBeans.iterator();
        while (it.hasNext()) {
            TaskBean removeGroupMemberBean = it.next();
            log("checkRemoveGroupMember 设备绑定的组:" + removeGroupMemberBean.groupId + " 不存在云端或者组与设备不在同一个房间，执行从设备中删除该组");
            long startTime = System.currentTimeMillis();
            SIGMesh instance2 = SIGMesh.getInstance();
            String str = removeGroupMemberBean.macAddress;
            final TaskBean taskBean = removeGroupMemberBean;
            final long j = startTime;
            final List<TaskBean> list = targetRemoveGroupMemberBeans;
            Iterator<TaskBean> it2 = it;
            AnonymousClass7 r10 = r0;
            final boolean z = onlyRemoveGroupMember;
            TaskBean taskBean2 = removeGroupMemberBean;
            int intValue = removeGroupMemberBean.groupAddress.intValue();
            final MeshCustomcmdCallback meshCustomcmdCallback2 = callback;
            AnonymousClass7 r0 = new MeshGroupCallback() {
                public void onSuccess(int meshAddr, int groupAddr, int retryCountToELk) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("code", 200);
                        object.put("mac", (Object) taskBean.macAddress);
                        object.put("groupId", (Object) taskBean.groupId);
                        object.put("groupAddress", (Object) taskBean.groupAddress);
                        completeRemoveMemberArray.put((Object) object);
                        GroupFixHelper.log("设备:" + taskBean.macAddress + " 解绑组id:" + taskBean.groupId + "成功");
                        TaskBean taskBean = taskBean;
                        g.c(200, taskBean.macAddress, taskBean.groupId.intValue(), taskBean.groupAddress.intValue(), System.currentTimeMillis() - j, taskBean.bzReson);
                        if (completeRemoveMemberArray.length() == list.size()) {
                            MeshDataManager.flagAddGroup = false;
                            SharedPreferenceHelper.setNeedUpload(SIGMesh.getInstance().getContext(), true);
                            GroupFixHelper.log("所有自动修复删除设备无用组完成,fixAddCustomGroupToMeshGroup");
                            k.a("所有自动修复删除设备无用组完成:" + completeRemoveMemberArray + "设置上传标志");
                            if (!z) {
                                GroupFixHelper.this.fixAddCustomGroupToMeshGroup("remove groupmembers complete");
                            }
                            MeshCustomcmdCallback meshCustomcmdCallback = meshCustomcmdCallback2;
                            if (meshCustomcmdCallback != null) {
                                meshCustomcmdCallback.onSuccess(completeRemoveMemberArray);
                            }
                        }
                    } catch (Exception e) {
                        GroupFixHelper.logE("异常onsuccess toRemoveGroupMember");
                    }
                }

                public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCountToElk) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("code", 0);
                        object.put("mac", (Object) taskBean.macAddress);
                        object.put("groupId", (Object) taskBean.groupId);
                        completeRemoveMemberArray.put((Object) object);
                        GroupFixHelper.log("设备:" + taskBean.macAddress + "解绑组:" + taskBean.groupId + "失败");
                        TaskBean taskBean = taskBean;
                        g.c(0, taskBean.macAddress, taskBean.groupId.intValue(), taskBean.groupAddress.intValue(), System.currentTimeMillis() - j, taskBean.bzReson);
                        if (completeRemoveMemberArray.length() == list.size()) {
                            GroupFixHelper.log("所有自动修复删除设备无用组完成,fixAddCustomGroupToMeshGroup");
                            MeshDataManager.flagAddGroup = false;
                            SharedPreferenceHelper.setNeedUpload(SIGMesh.getInstance().getContext(), true);
                            k.a("自动修复删除设备无用组完成:" + completeRemoveMemberArray + "设置上传标志");
                            if (!z) {
                                GroupFixHelper.this.fixAddCustomGroupToMeshGroup("remove groupmembers complete");
                            }
                            MeshCustomcmdCallback meshCustomcmdCallback = meshCustomcmdCallback2;
                            if (meshCustomcmdCallback != null) {
                                meshCustomcmdCallback.onSuccess(completeRemoveMemberArray);
                            }
                        }
                    } catch (Exception e) {
                        GroupFixHelper.logE("异常onfail toRemoveGroupMember");
                    }
                }
            };
            instance2.removeGroupMember2(str, intValue, r10);
            it = it2;
        }
        if (targetRemoveGroupMemberBeans.size() == 0 && meshCustomcmdCallback != null) {
            meshCustomcmdCallback.onSuccess("");
        }
    }

    public void checkAddGroupMember(String bz, List<CloudGroup> cloudGroups) {
        log(bz + " 开始执行本地组转换mesh组逻辑检测逻辑");
        HashMap<String, List<CloudGroup>> addGroupMemberTasks = new HashMap<>();
        if (cloudGroups != null && cloudGroups.size() > 0) {
            MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
            for (CloudGroup cloudGroup : cloudGroups) {
                int groupId = cloudGroup.getId();
                CustomGroup customGroup = meshInfo.getCustomGroups().get(String.valueOf(groupId));
                if (customGroup != null && customGroup.getDevices() != null && customGroup.getDevices().size() > 0) {
                    Iterator<String> it = customGroup.getDevices().iterator();
                    while (it.hasNext()) {
                        String macAddress = it.next();
                        CloudGroup cl = new CloudGroup();
                        cl.setId(groupId);
                        cl.setGroupAddress(cloudGroup.getGroupAddress());
                        if (addGroupMemberTasks.containsKey(macAddress)) {
                            addGroupMemberTasks.get(macAddress).add(cl);
                        } else {
                            List<CloudGroup> cls = new ArrayList<>();
                            cls.add(cl);
                            addGroupMemberTasks.put(macAddress, cls);
                        }
                    }
                } else if (customGroup != null) {
                    logE("checkAddGroupMember 本地组:" + customGroup.getGroupId() + "下不存在设备,建组的时候出问题了，没加到本地组，排查下是否是因为网络等异常，等到云端保存组成员接口没成功");
                }
            }
            if (addGroupMemberTasks.isEmpty()) {
                logE("没有需要补建的任务，流程结束");
            } else {
                sendAddGroupMemberTask(buildAddGroupMemberTask(addGroupMemberTasks, this.deviceBindedGroups));
            }
        }
    }

    private void sendAddGroupMemberTask(List<TaskBean> addGroupMemberTask) {
        final JSONArray completedArray = new JSONArray();
        if (addGroupMemberTask != null && addGroupMemberTask.size() > 0) {
            for (TaskBean taskBean : addGroupMemberTask) {
                logE("checkAddGroupMember 设备(" + taskBean.macAddress + ")补发建组,groupId:" + taskBean.groupId);
                final TaskBean taskBean2 = taskBean;
                final long currentTimeMillis = System.currentTimeMillis();
                final List<TaskBean> list = addGroupMemberTask;
                addGroupMember(taskBean.groupId.intValue(), taskBean.macAddress, new MeshGroupCallbackWrapper() {
                    private String onDegradeToLocalGroupReason = "";

                    public void onSuccess(int meshAddr, int groupAddr, int retryCountToELk) {
                        int code = groupAddr == 0 ? 201 : 200;
                        try {
                            JSONObject object = new JSONObject();
                            object.put("code", code);
                            object.put("macAddress", (Object) taskBean2.macAddress);
                            object.put("groupAddr", groupAddr);
                            object.put("groupId", (Object) taskBean2.groupId);
                            completedArray.put((Object) object);
                            TaskBean taskBean = taskBean2;
                            g.b(code, taskBean.macAddress, taskBean.groupId.intValue(), groupAddr, System.currentTimeMillis() - currentTimeMillis, this.onDegradeToLocalGroupReason, taskBean2.bzReson);
                            if (completedArray.length() == list.size()) {
                                GroupFixHelper.logE("本地组转换mesh标准组完成:" + completedArray);
                                toRemoveCustomGroupMember(completedArray);
                            }
                        } catch (Exception e) {
                            GroupFixHelper.logE("sendAddGroupMemberTask onSuccess exception:" + e.getMessage() + ",groupAddr:" + groupAddr);
                        }
                    }

                    public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCountToElk) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("groupAddr", 0);
                            object.put("groupId", (Object) taskBean2.groupId);
                            object.put("macAddress", (Object) taskBean2.macAddress);
                            completedArray.put((Object) object);
                            TaskBean taskBean = taskBean2;
                            g.b(code, taskBean.macAddress, taskBean.groupId.intValue(), groupAddr, System.currentTimeMillis() - currentTimeMillis, this.onDegradeToLocalGroupReason, taskBean2.bzReson);
                            if (completedArray.length() == list.size()) {
                                GroupFixHelper.logE("本地组转换mesh标准组完成:" + completedArray);
                                toRemoveCustomGroupMember(completedArray);
                            }
                        } catch (Exception e) {
                            GroupFixHelper.logE("sendAddGroupMemberTask onFailed exception:" + e.getMessage() + ",meshAddr:" + meshAddr);
                        }
                    }

                    public void onDegradeToLocalGroup(String reason) {
                        this.onDegradeToLocalGroupReason = reason;
                    }

                    private void toRemoveCustomGroupMember(JSONArray completedArray) {
                        try {
                            JSONArray toRemoveCustomGroupMemberTasks = new JSONArray();
                            for (int i = 0; i < completedArray.length(); i++) {
                                JSONObject object = completedArray.getJSONObject(i);
                                if (object.getInt("groupAddr") > 0) {
                                    toRemoveCustomGroupMemberTasks.put((Object) object);
                                }
                            }
                            final List counts = new ArrayList();
                            for (int i2 = 0; i2 < toRemoveCustomGroupMemberTasks.length(); i2++) {
                                JSONObject object2 = toRemoveCustomGroupMemberTasks.getJSONObject(i2);
                                int groupId = object2.getInt("groupId");
                                String macAddress = object2.getString("macAddress");
                                final int i3 = groupId;
                                final String str = macAddress;
                                final JSONArray jSONArray = toRemoveCustomGroupMemberTasks;
                                SIGMesh.getInstance().removeCustomGroupMember(groupId, macAddress, new MeshGroupCallback() {
                                    public void onSuccess(int meshAddr, int groupAddr, int retryCountToELk) {
                                        GroupFixHelper.log("删除本地组(" + i3 + ")成员(" + str + ")成功");
                                        counts.add(0);
                                        if (counts.size() == jSONArray.length()) {
                                            update(i3);
                                        }
                                    }

                                    public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCountToElk) {
                                        GroupFixHelper.logE("删除本地组(" + i3 + ")成员(" + str + ")失败");
                                        counts.add(0);
                                        if (counts.size() == jSONArray.length()) {
                                            update(i3);
                                        }
                                    }

                                    public void update(int groupId) {
                                        GroupFixHelper.log("自动修复-本地组转换为标准组任务完成,提交meshjson");
                                        MeshDataManager.flagAddGroup = false;
                                        SIGMesh.getInstance().getMeshInfo().saveOrUpdate(BaseApplication.b(), "自动修复-本地组转换为标准组任务完成");
                                        SharedPreferenceHelper.setNeedUpload(SIGMesh.getInstance().getContext(), true);
                                        k.a("自动修复-本地组转换为标准组任务完成，删除本地组，设置上传标志");
                                        GroupFixHelper.this.ldsGroupApi.editGroup(groupId, "fix addGroupMember");
                                    }
                                });
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            }
        }
    }

    private List<TaskBean> buildAddGroupMemberTask(HashMap<String, List<CloudGroup>> addGroupMemberTasks, HashMap<String, ArrayList<Integer>> devicesBindGroups) {
        List<CloudGroup> customGroups;
        List<TaskBean> realAddGroupMembersTask = new ArrayList<>();
        for (String mac : addGroupMemberTasks.keySet()) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac);
            if (nodeInfo != null && nodeInfo.isOnline() && (customGroups = addGroupMemberTasks.get(mac)) != null && customGroups.size() > 0) {
                for (CloudGroup customGroup : customGroups) {
                    List<Integer> bindedGroups = devicesBindGroups.get(mac);
                    if (bindedGroups != null && !bindedGroups.contains(Integer.valueOf(customGroup.getGroupAddress()))) {
                        if (bindedGroups.size() >= 8) {
                            logE("sendAddGroupMember 设备:" + mac + "已绑定组达到:" + 8 + "个，无法再补建标准组");
                        } else {
                            realAddGroupMembersTask.add(new TaskBean(mac, Integer.valueOf(customGroup.getGroupAddress()), Integer.valueOf(customGroup.getId()), "本地组转化"));
                        }
                    }
                }
            }
        }
        return realAddGroupMembersTask;
    }

    private void addGroupMember(final int groupId, String macAddress, final MeshGroupCallbackWrapper callback) {
        final StringBuffer stringBuffer = new StringBuffer("设备:" + macAddress + " 本地组id:" + groupId + "转换为mesh标准组调用");
        SIGMesh.getInstance().addGroupMember(groupId, macAddress, new MeshGroupCallbackWrapper() {
            public void onSuccess(int meshAddr, int groupAddr, int retryCountToELk) {
                int code = groupAddr == 0 ? 201 : 200;
                StringBuilder sb = new StringBuilder();
                sb.append("转换为mesh标准组");
                sb.append(code == 200 ? "成功" : "失败");
                sb.append(",对应组地址:0x%04X");
                String msg = String.format(sb.toString(), new Object[]{Integer.valueOf(groupAddr)});
                StringBuffer stringBuffer = stringBuffer;
                stringBuffer.append(",");
                stringBuffer.append(msg);
                GroupFixHelper.logE(stringBuffer.toString());
                MeshGroupCallbackWrapper meshGroupCallbackWrapper = callback;
                if (meshGroupCallbackWrapper != null) {
                    meshGroupCallbackWrapper.onSuccess(meshAddr, groupAddr, retryCountToELk);
                }
            }

            public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCountToElk) {
                GroupFixHelper.logE("转换为mesh标准组失败:groupid->" + groupId + ",code:" + code + ",msg:" + msg);
                MeshGroupCallbackWrapper meshGroupCallbackWrapper = callback;
                if (meshGroupCallbackWrapper != null) {
                    meshGroupCallbackWrapper.onFail(code, msg, meshAddr, groupAddr, retryCountToElk);
                }
            }

            public void onDegradeToLocalGroup(String reason) {
                MeshGroupCallbackWrapper meshGroupCallbackWrapper = callback;
                if (meshGroupCallbackWrapper != null) {
                    meshGroupCallbackWrapper.onDegradeToLocalGroup(reason);
                }
                GroupFixHelper.logE("补建组失败:" + reason + ",降级到本地组");
            }
        });
    }

    public void removeInvalidateGroupInDevice(String mac, JSONArray bindedAddress, MeshCustomcmdCallback callback) {
        final List<CloudGroup> cloudGroups = new ArrayList<>();
        final JSONArray jSONArray = bindedAddress;
        final String str = mac;
        final MeshCustomcmdCallback meshCustomcmdCallback = callback;
        l.I(this.ldsGroupApi.getVisibleGroups(), this.ldsGroupApi.getMusicGroup()).a(new q<List<CloudGroup>>() {
            public void onSubscribe(b d) {
            }

            public void onNext(List<CloudGroup> groups) {
                cloudGroups.addAll(groups);
            }

            public void onError(Throwable e) {
            }

            public void onComplete() {
                GroupFixHelper.log("fixRemoveUnuseGroup query group onComplete ==>" + cloudGroups);
                if (cloudGroups.size() > 0) {
                    List<GroupInfo> tempGroupInfos = new ArrayList<>();
                    tempGroupInfos.addAll(SIGMesh.getInstance().getMeshInfo().groups);
                    GroupFixHelper.this.checkRemoveGroupInMeshJson(cloudGroups);
                    for (CloudGroup cloudGroup : cloudGroups) {
                        for (GroupInfo groupInfo : tempGroupInfos) {
                            if (cloudGroup.getId() == groupInfo.groupId && cloudGroup.getGroupAddress() == 0) {
                                cloudGroup.setGroupAddress(groupInfo.address);
                            }
                        }
                    }
                    try {
                        final List<TaskBean> removeGroups = new ArrayList<>();
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject object = jSONArray.getJSONObject(i);
                            int groupId = object.getInt("groupId");
                            int groupAddress = object.getInt("groupAddress");
                            if (groupId != 0) {
                                boolean exist = false;
                                for (CloudGroup cloudGroup2 : cloudGroups) {
                                    if (cloudGroup2.getId() == groupId) {
                                        GroupFixHelper.log("groupId:" + groupId + "，存在云端");
                                        exist = true;
                                    }
                                }
                                if (!exist) {
                                    removeGroups.add(new TaskBean(str, Integer.valueOf(groupAddress), Integer.valueOf(groupId), "组不存在于云端"));
                                }
                            }
                        }
                        GroupFixHelper.this.getCloudDevices(new OnHttpCallback<List<CloudDevice>>() {
                            public void onResult(List<CloudDevice> cloudDevices) {
                                CloudDevice cloudDevice;
                                MeshLog.i("获取家下的设备列表-成功");
                                if (cloudDevices != null) {
                                    HashMap<String, CloudDevice> devices = new HashMap<>();
                                    for (CloudDevice cloudDevice2 : cloudDevices) {
                                        devices.put(cloudDevice2.mac, cloudDevice2);
                                    }
                                    for (int i = 0; i < jSONArray.length(); i++) {
                                        JSONObject object = jSONArray.optJSONObject(i);
                                        int groupId = object.optInt("groupId");
                                        int groupAddress = object.optInt("groupAddress");
                                        if (groupId != 0) {
                                            for (CloudGroup cloudGroup : cloudGroups) {
                                                if (cloudGroup.getId() == groupId && !TextUtils.isEmpty(cloudGroup.getRoomId()) && (cloudDevice = devices.get(str)) != null && !cloudGroup.getRoomId().equalsIgnoreCase(cloudDevice.roomId)) {
                                                    GroupFixHelper.log("组id:" + cloudGroup.getId() + "所属的roomid:" + cloudGroup.getRoomId() + "与设备:" + str + "所在的roomid:" + cloudDevice.roomId + "不一致，需要解绑");
                                                    removeGroups.add(new TaskBean(str, Integer.valueOf(groupAddress), Integer.valueOf(cloudGroup.getId()), "组归属的roomid与设备归属的roomid不一致"));
                                                }
                                            }
                                        }
                                    }
                                    AnonymousClass10 r3 = AnonymousClass10.this;
                                    GroupFixHelper.this.sendRemoveGroup(removeGroups, true, meshCustomcmdCallback);
                                    return;
                                }
                                AnonymousClass10 r2 = AnonymousClass10.this;
                                GroupFixHelper.this.sendRemoveGroup(removeGroups, true, meshCustomcmdCallback);
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    public static class TaskBean {
        public String bzReson;
        public Integer groupAddress;
        public Integer groupId;
        public String macAddress;

        public TaskBean(String macAddress2, Integer groupAddress2, Integer groupId2, String bzReson2) {
            this.macAddress = macAddress2;
            this.groupAddress = groupAddress2;
            this.groupId = groupId2;
            this.bzReson = bzReson2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            TaskBean taskBean = (TaskBean) obj;
            if (this.macAddress.equals(taskBean.macAddress) && this.groupAddress == taskBean.groupAddress && this.groupId == taskBean.groupId) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
        MeshLog.d("GroupFixHelper:" + msg);
    }

    /* access modifiers changed from: private */
    public static void logE(String msg) {
        MeshLog.e("GroupFixHelper:" + msg);
        com.leedarson.log.elk.a.y(GroupFixHelper.class).e(TAG).c(MeshDataManager.class.getSimpleName()).t("BleMesh").p(msg).a().b();
    }
}
