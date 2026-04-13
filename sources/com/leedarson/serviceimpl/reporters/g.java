package com.leedarson.serviceimpl.reporters;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.log.elk.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.model.GroupInfo;
import meshsdk.util.MeshConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GroupMemberReporter */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(JSONArray jSONArray, int i, int i2, String str, long j) {
        long duration;
        int code;
        StringBuffer macsBuffer;
        Object[] objArr = {jSONArray, new Integer(i), new Integer(i2), str, new Long(j)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8547, new Class[]{JSONArray.class, cls, cls, String.class, Long.TYPE}, Void.TYPE).isSupported) {
            JSONArray resultArray = jSONArray;
            int groupAddress = i2;
            long duration2 = j;
            int groupId = i;
            String groupType = str;
            StringBuffer successBuffer = new StringBuffer();
            StringBuffer localSuccessBuffer = new StringBuffer();
            StringBuffer errorBuffer = new StringBuffer();
            StringBuffer macsBuffer2 = new StringBuffer();
            int errorCount = 0;
            int localSuccessCount = 0;
            int successCount = 0;
            int i3 = 0;
            while (true) {
                duration = duration2;
                if (i3 >= resultArray.length()) {
                    break;
                }
                JSONObject object = resultArray.optJSONObject(i3);
                if (object != null) {
                    int code2 = object.optInt("code");
                    String mac = object.optString("mac");
                    JSONObject jSONObject = object;
                    if (macsBuffer2.toString().length() > 0) {
                        macsBuffer2.append(",");
                    }
                    macsBuffer2.append(mac);
                    macsBuffer = macsBuffer2;
                    if (code2 == 200) {
                        successCount++;
                        if (successBuffer.toString().length() > 0) {
                            successBuffer.append(",");
                        }
                        successBuffer.append(mac);
                    } else if (code2 == 201) {
                        localSuccessCount++;
                        if (localSuccessBuffer.toString().length() > 0) {
                            localSuccessBuffer.append(",");
                        }
                        localSuccessBuffer.append(mac);
                    } else if (GroupInfo.TYPE_NORMAL.equals(groupType)) {
                        errorCount++;
                        if (errorBuffer.toString().length() > 0) {
                            errorBuffer.append(",");
                        }
                        errorBuffer.append(mac);
                    }
                } else {
                    macsBuffer = macsBuffer2;
                }
                i3++;
                macsBuffer2 = macsBuffer;
                duration2 = duration;
            }
            StringBuffer macsBuffer3 = macsBuffer2;
            successBuffer.append(" ");
            successBuffer.append(successCount);
            successBuffer.append("个设备绑定组" + groupId + "成功;");
            if (localSuccessBuffer.toString().length() > 0) {
                localSuccessBuffer.append(" ");
                localSuccessBuffer.append(localSuccessCount);
                localSuccessBuffer.append("个设备创建本地组成功;");
            }
            if (errorBuffer.toString().length() > 0) {
                errorBuffer.append(" ");
                errorBuffer.append(errorCount);
                errorBuffer.append("个绑定组" + groupId + "失败,创建本地组失败;");
            }
            JSONObject payload = new JSONObject();
            try {
                payload.put("groupId", (Object) String.valueOf(groupId));
                payload.put("groupAddress", (Object) String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}));
                payload.put("deviceResult", (Object) resultArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (localSuccessCount > 0) {
                code = 201;
            } else if (successCount == resultArray.length()) {
                code = 200;
            } else {
                code = BaseResp.ERR_MSG_SEND_FAIL;
            }
            String message = successBuffer + localSuccessBuffer.toString() + errorBuffer;
            StringBuilder sb = new StringBuilder();
            int i4 = groupAddress;
            sb.append("reportAddGroupMembers result==>");
            sb.append(message);
            sb.append(",payload:");
            sb.append(payload);
            MeshLog.e(sb.toString());
            a b = a.y(g.class).t("BleMesh").e(MeshConstants.EVENT_ADD_GROUP_MEMBERS).o("info").b(8);
            StringBuilder sb2 = new StringBuilder();
            StringBuffer stringBuffer = successBuffer;
            sb2.append("共");
            sb2.append(resultArray.length());
            sb2.append("个设备建组;");
            sb2.append(message);
            b.p(sb2.toString()).u("macs", macsBuffer3.toString()).u("code", Integer.valueOf(code)).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("groupId", String.valueOf(groupId)).u("payload", payload.toString()).u("groupType", groupType).a().b();
        }
    }

    public static void e(JSONArray jSONArray, int i, int i2, String str, long j) {
        int code;
        long duration;
        Object[] objArr = {jSONArray, new Integer(i), new Integer(i2), str, new Long(j)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8548, new Class[]{JSONArray.class, cls, cls, String.class, Long.TYPE}, Void.TYPE).isSupported) {
            JSONArray resultArray = jSONArray;
            int groupAddress = i2;
            long duration2 = j;
            int groupId = i;
            String groupType = str;
            StringBuffer successBuffer = new StringBuffer();
            StringBuffer errorBuffer = new StringBuffer();
            StringBuffer macsBuffer = new StringBuffer();
            int successCount = 0;
            int i3 = 0;
            int errorCount = 0;
            while (i3 < resultArray.length()) {
                JSONObject object = resultArray.optJSONObject(i3);
                if (object != null) {
                    int code2 = object.optInt("code");
                    String mac = object.optString("mac");
                    JSONObject jSONObject = object;
                    if (macsBuffer.toString().length() > 0) {
                        macsBuffer.append(",");
                    }
                    macsBuffer.append(mac);
                    duration = duration2;
                    if (code2 == 200) {
                        successCount++;
                        if (successBuffer.toString().length() > 0) {
                            successBuffer.append(",");
                        }
                        successBuffer.append(mac);
                    } else {
                        errorCount++;
                        if (errorBuffer.toString().length() > 0) {
                            errorBuffer.append(",");
                        }
                        errorBuffer.append(mac);
                    }
                } else {
                    duration = duration2;
                    JSONObject jSONObject2 = object;
                }
                i3++;
                duration2 = duration;
            }
            long duration3 = duration2;
            successBuffer.append(" ");
            successBuffer.append(successCount);
            successBuffer.append("个设备删除组" + groupId + "成功;");
            if (errorBuffer.toString().length() > 0) {
                errorBuffer.append(" ");
                errorBuffer.append(errorCount);
                errorBuffer.append("个设备删除组" + groupId + "失败;");
            }
            JSONObject payload = new JSONObject();
            try {
                payload.put("groupId", (Object) String.valueOf(groupId));
                payload.put("groupAddress", (Object) String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}));
                payload.put("deviceResult", (Object) resultArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (successCount == resultArray.length()) {
                code = 200;
            } else if (errorCount == resultArray.length()) {
                code = BaseResp.ERR_MSG_SEND_FAIL;
            } else {
                code = 201;
            }
            String message = successBuffer.toString() + errorBuffer;
            MeshLog.e("reportRemoveGroupMembers result==>" + message + ",payload:" + payload);
            a b = a.y(g.class).t("BleMesh").e(MeshConstants.EVENT_REMOVE_GROUP_MEMBERS).o("info").b(8);
            StringBuilder sb = new StringBuilder();
            int i4 = groupAddress;
            sb.append("共");
            sb.append(resultArray.length());
            sb.append("个设备删组;");
            sb.append(message);
            b.p(sb.toString()).u("macs", macsBuffer.toString()).u("groupType", groupType).u("code", Integer.valueOf(code)).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration3)).u("groupId", String.valueOf(groupId)).u("payload", payload.toString()).a().b();
        }
    }

    public static void d(JSONArray jSONArray, String str, String str2, long j) {
        int code;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{jSONArray, str, str2, new Long(j)}, (Object) null, changeQuickRedirect, true, 8549, new Class[]{JSONArray.class, cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            JSONArray resultArray = jSONArray;
            String groupType = str2;
            String mac = str;
            long duration = j;
            StringBuffer successBuffer = new StringBuffer();
            StringBuffer errorBuffer = new StringBuffer();
            StringBuffer groupIdsBuffer = new StringBuffer();
            int successCount = 0;
            int errorCount = 0;
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject object = resultArray.optJSONObject(i);
                if (object != null) {
                    int code2 = object.optInt("code");
                    String groupId = object.optString("groupId");
                    if (groupIdsBuffer.toString().length() > 0) {
                        groupIdsBuffer.append(",");
                    }
                    groupIdsBuffer.append(groupId);
                    if (code2 == 200) {
                        successCount++;
                        if (successBuffer.toString().length() > 0) {
                            successBuffer.append(",");
                        }
                        successBuffer.append(groupId);
                        successBuffer.append("解绑成功");
                    } else {
                        errorCount++;
                        if (errorBuffer.toString().length() > 0) {
                            errorBuffer.append(",");
                        }
                        errorBuffer.append(groupId);
                        errorBuffer.append("解绑失败");
                    }
                }
            }
            JSONObject payload = new JSONObject();
            try {
                payload.put("deviceResult", (Object) resultArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (successCount == resultArray.length()) {
                code = 200;
            } else if (errorCount == resultArray.length()) {
                code = BaseResp.ERR_MSG_SEND_FAIL;
            } else {
                code = 201;
            }
            String message = successBuffer.toString() + errorBuffer;
            MeshLog.e("reportRemoveDeviceFromGroups result==>" + message + ",payload:" + payload);
            a b = a.y(g.class).t("BleMesh").e(MeshConstants.EVENT_REMOVE_GROUP_MEMBERS).o("info").b(8);
            StringBuilder sb = new StringBuilder();
            JSONArray jSONArray2 = resultArray;
            sb.append("设备");
            sb.append(mac);
            sb.append("删组[");
            sb.append(groupIdsBuffer);
            sb.append("];");
            sb.append(message);
            b.p(sb.toString()).u("mac", mac).u("groupType", groupType).u("code", Integer.valueOf(code)).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("payload", payload.toString()).a().b();
        }
    }

    public static void b(int i, String str, int i2, int i3, long j, String str2, String str3) {
        String message;
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(i), str, new Integer(i2), new Integer(i3), new Long(j), str2, str3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8550, new Class[]{cls2, cls, cls2, cls2, Long.TYPE, cls, cls}, Void.TYPE).isSupported) {
            int code = i;
            int groupId = i2;
            String onDegradeToLocalGroupReason = str2;
            long duration = j;
            String mac = str;
            int groupAddress = i3;
            String bz = str3;
            if (code == 200) {
                message = mac + "绑定组id:" + groupId + " 成功，组地址:" + String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}) + ",归因:" + bz;
            } else {
                message = mac + "绑定组id:" + groupId + " 失败(" + onDegradeToLocalGroupReason + ")，组地址:" + String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}) + ",归因:" + bz;
            }
            JSONObject payload = new JSONObject();
            try {
                payload.put("groupId", (Object) String.valueOf(groupId));
                payload.put("groupAddress", (Object) String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MeshLog.e("reportAutoRepairBindGroup result==>" + message + ",payload:" + payload);
            a.y(g.class).t("BleMesh").e(MeshConstants.EVENT_AUTO_REPAIR_BIND_GROUP).o("info").b(8).p(message).u("code", Integer.valueOf(code)).u("mac", mac).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("groupId", String.valueOf(groupId)).u("payload", payload.toString()).a().b();
        }
    }

    public static void c(int i, String str, int i2, int i3, long j, String str2) {
        String message;
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(i), str, new Integer(i2), new Integer(i3), new Long(j), str2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8551, new Class[]{cls2, cls, cls2, cls2, Long.TYPE, cls}, Void.TYPE).isSupported) {
            int code = i;
            int groupId = i2;
            String bz = str2;
            long duration = j;
            String mac = str;
            int groupAddress = i3;
            if (code == 200) {
                message = mac + "解绑组id:" + groupId + " 成功，组地址:" + String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}) + ",归因:" + bz;
            } else {
                message = mac + "解绑组id:" + groupId + " 失败，组地址:" + String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}) + ",归因:" + bz;
            }
            JSONObject payload = new JSONObject();
            try {
                payload.put("groupId", (Object) String.valueOf(groupId));
                payload.put("groupAddress", (Object) String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MeshLog.e("reportAutoRepairUnBindGroup result==>" + message + ",payload:" + payload);
            a.y(g.class).t("BleMesh").e(MeshConstants.EVENT_AUTO_REPAIR_UNBIND_GROUP).o("info").b(8).p(message).u("code", Integer.valueOf(code)).u("mac", mac).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("groupId", String.valueOf(groupId)).u("payload", payload.toString()).a().b();
        }
    }
}
