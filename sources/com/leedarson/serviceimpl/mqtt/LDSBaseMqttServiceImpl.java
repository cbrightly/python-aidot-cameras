package com.leedarson.serviceimpl.mqtt;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.mqtt.beans.MqttServiceRequestTaskBean;
import com.leedarson.mqtt.beans.MqttSubscribeRequestBean;
import com.leedarson.mqtt.l;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LDSBaseMqttServiceImpl implements LDSBaseMqttService {
    public static ChangeQuickRedirect changeQuickRedirect;
    public final String a = "mqttConnect";
    public final String b = "publish";
    public final String c = "subscribe";
    public final String d = "unsubscribe";
    public final String e = "end";

    static /* synthetic */ void i(LDSBaseMqttServiceImpl x0, int x1, String x2, String x3, String x4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1), x2, x3, x4}, (Object) null, changeQuickRedirect, true, 8468, new Class[]{LDSBaseMqttServiceImpl.class, Integer.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.j(x1, x2, x3, x4);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x005b, code lost:
        if (r3.equals("subscribe") != false) goto L_0x007d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(com.leedarson.base.jsbridge2.WVJBWebView r19, java.lang.String r20, android.app.Activity r21, java.lang.String r22, java.lang.String r23) {
        /*
            r18 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r19
            r10 = 1
            r2[r10] = r20
            r11 = 2
            r2[r11] = r21
            r12 = 3
            r2[r12] = r22
            r13 = 4
            r2[r13] = r23
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<com.leedarson.base.jsbridge2.WVJBWebView> r1 = com.leedarson.base.jsbridge2.WVJBWebView.class
            r7[r9] = r1
            r7[r10] = r0
            java.lang.Class<android.app.Activity> r1 = android.app.Activity.class
            r7[r11] = r1
            r7[r12] = r0
            r7[r13] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 8454(0x2106, float:1.1847E-41)
            r3 = r18
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0036
            return
        L_0x0036:
            r1 = r18
            r2 = r20
            r3 = r22
            r4 = r19
            r5 = r21
            r6 = r23
            r0 = -1
            int r7 = r3.hashCode()
            switch(r7) {
                case -235365105: goto L_0x0072;
                case 100571: goto L_0x0068;
                case 381861158: goto L_0x005e;
                case 514841930: goto L_0x0055;
                case 583281361: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x007c
        L_0x004b:
            java.lang.String r7 = "unsubscribe"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x004a
            r11 = r12
            goto L_0x007d
        L_0x0055:
            java.lang.String r7 = "subscribe"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x004a
            goto L_0x007d
        L_0x005e:
            java.lang.String r7 = "mqttConnect"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x004a
            r11 = r9
            goto L_0x007d
        L_0x0068:
            java.lang.String r7 = "end"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x004a
            r11 = r13
            goto L_0x007d
        L_0x0072:
            java.lang.String r7 = "publish"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x004a
            r11 = r10
            goto L_0x007d
        L_0x007c:
            r11 = r0
        L_0x007d:
            r0 = -4000008(0xffffffffffc2f6f8, float:NaN)
            r7 = 0
            java.lang.String r8 = ""
            switch(r11) {
                case 0: goto L_0x01e9;
                case 1: goto L_0x00bc;
                case 2: goto L_0x00b7;
                case 3: goto L_0x00b2;
                case 4: goto L_0x009c;
                default: goto L_0x0086;
            }
        L_0x0086:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "传入action不可识别: data="
            r7.append(r9)
            r7.append(r6)
            java.lang.String r7 = r7.toString()
            r1.j(r0, r2, r8, r7)
            goto L_0x01f3
        L_0x009c:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "传入action暂未实现（视情况开放）: data="
            r7.append(r9)
            r7.append(r6)
            java.lang.String r7 = r7.toString()
            r1.j(r0, r2, r8, r7)
            goto L_0x01f3
        L_0x00b2:
            r1.h(r2, r6, r7)
            goto L_0x01f3
        L_0x00b7:
            r1.a(r2, r6, r7)
            goto L_0x01f3
        L_0x00bc:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            r7 = r0
            java.lang.Class<com.leedarson.mqtt.beans.MqttWebPubInvokeParamsBean> r0 = com.leedarson.mqtt.beans.MqttWebPubInvokeParamsBean.class
            java.lang.Object r0 = r7.fromJson((java.lang.String) r6, r0)
            r9 = r0
            com.leedarson.mqtt.beans.MqttWebPubInvokeParamsBean r9 = (com.leedarson.mqtt.beans.MqttWebPubInvokeParamsBean) r9
            java.lang.String r0 = r9.topic
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01ce
            org.json.JSONObject r0 = r9.message
            if (r0 != 0) goto L_0x00d9
            goto L_0x01ce
        L_0x00d9:
            java.lang.String r11 = "{}"
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00fe }
            r0.<init>((java.lang.String) r6)     // Catch:{ JSONException -> 0x00fe }
            java.lang.String r12 = "message"
            boolean r13 = r0.has(r12)     // Catch:{ JSONException -> 0x00fe }
            if (r13 == 0) goto L_0x00fd
            boolean r13 = r0.isNull(r12)     // Catch:{ JSONException -> 0x00fe }
            if (r13 != 0) goto L_0x00fd
            org.json.JSONObject r13 = r0.getJSONObject(r12)     // Catch:{ JSONException -> 0x00fe }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x00fe }
            r11 = r13
            org.json.JSONObject r13 = r0.getJSONObject(r12)     // Catch:{ JSONException -> 0x00fe }
            r9.message = r13     // Catch:{ JSONException -> 0x00fe }
        L_0x00fd:
            goto L_0x0102
        L_0x00fe:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0102:
            com.leedarson.combeans.MqttMessageConfigBean r0 = new com.leedarson.combeans.MqttMessageConfigBean
            r0.<init>()
            r15 = r0
            org.json.JSONObject r0 = r9.message
            java.lang.String r12 = "seq"
            if (r0 == 0) goto L_0x0172
            boolean r0 = r0.has(r12)
            if (r0 == 0) goto L_0x0172
            org.json.JSONObject r0 = r9.message     // Catch:{ JSONException -> 0x013c }
            java.lang.String r0 = r0.getString(r12)     // Catch:{ JSONException -> 0x013c }
            r15.seq = r0     // Catch:{ JSONException -> 0x013c }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ JSONException -> 0x013c }
            if (r0 == 0) goto L_0x0171
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x013c }
            r0.<init>()     // Catch:{ JSONException -> 0x013c }
            com.leedarson.mqtt.l r12 = com.leedarson.mqtt.l.C()     // Catch:{ JSONException -> 0x013c }
            int r12 = r12.v()     // Catch:{ JSONException -> 0x013c }
            r0.append(r12)     // Catch:{ JSONException -> 0x013c }
            r0.append(r8)     // Catch:{ JSONException -> 0x013c }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x013c }
            r15.seq = r0     // Catch:{ JSONException -> 0x013c }
            goto L_0x0171
        L_0x013c:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            com.leedarson.mqtt.l r13 = com.leedarson.mqtt.l.C()
            int r13 = r13.v()
            r12.append(r13)
            r12.append(r8)
            java.lang.String r8 = r12.toString()
            r15.seq = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = "publish 获取seq失败 exception="
            r8.append(r12)
            java.lang.String r12 = r0.toString()
            r8.append(r12)
            java.lang.String r8 = r8.toString()
            com.leedarson.mqtt.logs.b.a(r8)
        L_0x0171:
            goto L_0x019d
        L_0x0172:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            com.leedarson.mqtt.l r13 = com.leedarson.mqtt.l.C()
            int r13 = r13.v()
            r0.append(r13)
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            r15.seq = r8
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0199 }
            r0.<init>((java.lang.String) r11)     // Catch:{ JSONException -> 0x0199 }
            r0.put((java.lang.String) r12, (java.lang.Object) r8)     // Catch:{ JSONException -> 0x0199 }
            java.lang.String r12 = r0.toString()     // Catch:{ JSONException -> 0x0199 }
            r11 = r12
            goto L_0x019d
        L_0x0199:
            r0 = move-exception
            r0.printStackTrace()
        L_0x019d:
            java.lang.String r0 = r9.topic
            java.lang.String r8 = "Req"
            boolean r0 = r0.contains(r8)
            r0 = r0 ^ r10
            r15.onlyPubAck = r0
            java.lang.String r0 = r9.topic
            java.lang.String r8 = "iceCandidateReq"
            boolean r0 = r0.contains(r8)
            r15.onlyPubAck = r0
            int r0 = r9.timeout
            long r12 = (long) r0
            r15.timeOutLimitOfMs = r12
            r15.qos = r10
            com.leedarson.mqtt.l r12 = com.leedarson.mqtt.l.C()
            java.lang.String r13 = r9.topic
            com.leedarson.serviceimpl.mqtt.LDSBaseMqttServiceImpl$a r0 = new com.leedarson.serviceimpl.mqtt.LDSBaseMqttServiceImpl$a
            r0.<init>(r2)
            r17 = 0
            r14 = r11
            r8 = r15
            r16 = r0
            r12.S(r13, r14, r15, r16, r17)
            goto L_0x01f3
        L_0x01ce:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r8 = "mqtt.publish 调用参数缺失: data"
            r0.append(r8)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            com.leedarson.mqtt.logs.b.a(r0)
            r8 = -4000010(0xffffffffffc2f6f6, float:NaN)
            r1.j(r8, r2, r6, r0)
            return
        L_0x01e9:
            com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.C()
            java.lang.String r7 = "loginConnect"
            r0.F(r9, r7)
        L_0x01f3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.mqtt.LDSBaseMqttServiceImpl.handleData(com.leedarson.base.jsbridge2.WVJBWebView, java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    public class a implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 8469, clsArr, Void.TYPE).isSupported) {
                LDSBaseMqttServiceImpl.i(LDSBaseMqttServiceImpl.this, 200, this.a, callbackData.toString(), "消息发布成功");
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8470, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                LDSBaseMqttServiceImpl lDSBaseMqttServiceImpl = LDSBaseMqttServiceImpl.this;
                String str = this.a;
                LDSBaseMqttServiceImpl.i(lDSBaseMqttServiceImpl, -4000009, str, "", "消息发布失败 code=" + code + ", taskId=" + taskId + " , desc=" + desc);
            }
        }
    }

    private void h(String callbackKey, String data, LDSBaseMqttService.MqttActionHandler _nativeHandler) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, data, _nativeHandler}, this, changeQuickRedirect, false, 8455, new Class[]{cls, cls, LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            try {
                MqttSubscribeRequestBean _requestRequestBean = (MqttSubscribeRequestBean) new Gson().fromJson(data, MqttSubscribeRequestBean.class);
                String[] strArr = _requestRequestBean.topic;
                if (strArr != null) {
                    if (strArr.length != 0) {
                        l.C().X(_requestRequestBean.topic, new b(_nativeHandler, callbackKey));
                        return;
                    }
                }
                String tip = "取消订阅参数不合规: topic为空 data=" + data;
                com.leedarson.mqtt.logs.b.a(tip);
                j(-4000006, callbackKey, "", tip);
            } catch (Exception exception) {
                String tip2 = "取消订阅参数不合规: topic为空 exception=" + exception.toString() + ",data=" + data;
                com.leedarson.mqtt.logs.b.a(tip2);
                j(-4000004, callbackKey, "", tip2);
            }
        }
    }

    public class b implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LDSBaseMqttService.MqttActionHandler a;
        final /* synthetic */ String b;

        b(LDSBaseMqttService.MqttActionHandler mqttActionHandler, String str) {
            this.a = mqttActionHandler;
            this.b = str;
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData}, this, changeQuickRedirect, false, 8471, clsArr, Void.TYPE).isSupported) {
                LDSBaseMqttService.MqttActionHandler mqttActionHandler = this.a;
                if (mqttActionHandler != null) {
                    mqttActionHandler.onActionSuccess(taskId, callbackData);
                } else {
                    LDSBaseMqttServiceImpl.i(LDSBaseMqttServiceImpl.this, 200, this.b, "", "取消订阅成功");
                }
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8472, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                LDSBaseMqttService.MqttActionHandler mqttActionHandler = this.a;
                if (mqttActionHandler != null) {
                    mqttActionHandler.onActionFail(code, taskId, desc);
                    return;
                }
                LDSBaseMqttServiceImpl lDSBaseMqttServiceImpl = LDSBaseMqttServiceImpl.this;
                String str = this.b;
                LDSBaseMqttServiceImpl.i(lDSBaseMqttServiceImpl, 4081, str, "", "取消订阅失败 code=" + code + ", taskId=" + taskId + ", mqtt_desc=" + desc);
            }
        }
    }

    private void a(String str, String str2, LDSBaseMqttService.MqttActionHandler mqttActionHandler) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, mqttActionHandler}, this, changeQuickRedirect, false, 8456, new Class[]{cls, cls, LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            String data = str2;
            String callbackKey = str;
            LDSBaseMqttService.MqttActionHandler _nativeHandler = mqttActionHandler;
            try {
                MqttSubscribeRequestBean _requestRequestBean = new MqttSubscribeRequestBean();
                JSONObject _requestBean = new JSONObject(data);
                if (!_requestBean.has("topic")) {
                    String tip = "收到订阅参数不合规: topic字段不对 data=" + data;
                    com.leedarson.mqtt.logs.b.a(tip);
                    j(-4000004, callbackKey, "", tip);
                    return;
                }
                if (_requestBean.get("topic") instanceof JSONArray) {
                    JSONArray _topicsArr = _requestBean.getJSONArray("topic");
                    _requestRequestBean.topic = new String[_topicsArr.length()];
                    for (int i = 0; i < _topicsArr.length(); i++) {
                        _requestRequestBean.topic[i] = _topicsArr.getString(i);
                    }
                } else {
                    String[] strArr = new String[1];
                    _requestRequestBean.topic = strArr;
                    strArr[0] = _requestBean.getString("topic");
                }
                String[] strArr2 = _requestRequestBean.topic;
                if (strArr2 != null) {
                    if (strArr2.length != 0) {
                        l.C().W(_requestRequestBean.topic, new c(_nativeHandler, callbackKey, _requestRequestBean), false);
                        return;
                    }
                }
                String tip2 = "收到订阅参数不合规: topic字段不对 data=" + data;
                com.leedarson.mqtt.logs.b.a(tip2);
                j(-4000004, callbackKey, "", tip2);
            } catch (Exception exception) {
                String tip3 = "收到订阅参数不合规: topic为空 exception=" + exception.toString() + "  data=" + data;
                com.leedarson.mqtt.logs.b.a(tip3);
                j(-4000004, callbackKey, "", tip3);
            }
        }
    }

    public class c implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LDSBaseMqttService.MqttActionHandler a;
        final /* synthetic */ String b;
        final /* synthetic */ MqttSubscribeRequestBean c;

        c(LDSBaseMqttService.MqttActionHandler mqttActionHandler, String str, MqttSubscribeRequestBean mqttSubscribeRequestBean) {
            this.a = mqttActionHandler;
            this.b = str;
            this.c = mqttSubscribeRequestBean;
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData}, this, changeQuickRedirect, false, 8473, clsArr, Void.TYPE).isSupported) {
                LDSBaseMqttService.MqttActionHandler mqttActionHandler = this.a;
                if (mqttActionHandler != null) {
                    mqttActionHandler.onActionSuccess(taskId, callbackData);
                    return;
                }
                LDSBaseMqttServiceImpl lDSBaseMqttServiceImpl = LDSBaseMqttServiceImpl.this;
                String str = this.b;
                String jSONObject = callbackData.toString();
                LDSBaseMqttServiceImpl.i(lDSBaseMqttServiceImpl, 200, str, jSONObject, "订阅成功, topic=" + new Gson().toJson((Object) this.c.topic));
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8474, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                LDSBaseMqttService.MqttActionHandler mqttActionHandler = this.a;
                if (mqttActionHandler != null) {
                    mqttActionHandler.onActionFail(code, taskId, desc);
                    return;
                }
                LDSBaseMqttServiceImpl lDSBaseMqttServiceImpl = LDSBaseMqttServiceImpl.this;
                String str = this.b;
                LDSBaseMqttServiceImpl.i(lDSBaseMqttServiceImpl, -4000005, str, "", "订阅失败 code=" + code + ", taskId=" + taskId + ", mqtt_desc=" + desc);
            }
        }
    }

    public void init(Context context, JSONObject jSONObject, String ref) {
        Class[] clsArr = {Context.class, JSONObject.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, jSONObject, ref}, this, changeQuickRedirect, false, 8457, clsArr, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("收到mqtt serviceimpl 初始化--->");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            if (TextUtils.isEmpty(accessToken) || BuildConfig.TRAVIS.equals(accessToken)) {
                com.leedarson.mqtt.logs.b.a("用户未登陆，中断mqtt服务的初始化工作...");
            } else if (TextUtils.isEmpty(ref)) {
                l.C().F(false, "coldStartConnect");
            } else {
                l.C().F(false, ref);
            }
        }
    }

    public void connect(LDSBaseMqttService.MqttActionHandler _handler) {
    }

    public void disconnect(LDSBaseMqttService.MqttActionHandler _handler) {
    }

    public void loginIn(LDSBaseMqttService.MqttActionHandler _handler) {
        if (!PatchProxy.proxy(new Object[]{_handler}, this, changeQuickRedirect, false, 8458, new Class[]{LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("收到mqtt 登陆请求....");
            l.C().F(true, "loginConnect");
            JSONObject _respObj = new JSONObject();
            try {
                _respObj.put("desc", (Object) "mqtt 登陆请求处理中");
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            _handler.onActionSuccess(l.C().v() + "", _respObj);
        }
    }

    public void loginOut(LDSBaseMqttService.MqttActionHandler _handler) {
        if (!PatchProxy.proxy(new Object[]{_handler}, this, changeQuickRedirect, false, 8459, new Class[]{LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("收到mqtt 退出请求....");
            l.C().x();
            com.leedarson.mqtt.logs.b.b("Mqtt 退出成功");
            JSONObject _respObj = new JSONObject();
            try {
                _respObj.put("desc", (Object) "mqtt 退出成功");
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            _handler.onActionSuccess(l.C().v() + "", _respObj);
        }
    }

    public void subscribeTopic(JSONObject data, LDSBaseMqttService.MqttActionHandler _handler) {
        Class[] clsArr = {JSONObject.class, LDSBaseMqttService.MqttActionHandler.class};
        if (!PatchProxy.proxy(new Object[]{data, _handler}, this, changeQuickRedirect, false, 8460, clsArr, Void.TYPE).isSupported) {
            a("", data.toString(), _handler);
        }
    }

    public void unSubscribeTopic(JSONObject data, LDSBaseMqttService.MqttActionHandler _handler) {
        Class[] clsArr = {JSONObject.class, LDSBaseMqttService.MqttActionHandler.class};
        if (!PatchProxy.proxy(new Object[]{data, _handler}, this, changeQuickRedirect, false, 8461, clsArr, Void.TYPE).isSupported) {
            h("", data.toString(), _handler);
        }
    }

    public void publish(String topic, MqttMessageConfigBean configBean, JSONObject data, LDSBaseMqttService.MqttActionHandler _handler) {
        if (!PatchProxy.proxy(new Object[]{topic, configBean, data, _handler}, this, changeQuickRedirect, false, 8462, new Class[]{String.class, MqttMessageConfigBean.class, JSONObject.class, LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            l C = l.C();
            C.S(topic, data.toString(), configBean, new d(_handler), (MqttServiceRequestTaskBean) null);
        }
    }

    public class d implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LDSBaseMqttService.MqttActionHandler a;

        d(LDSBaseMqttService.MqttActionHandler mqttActionHandler) {
            this.a = mqttActionHandler;
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
            LDSBaseMqttService.MqttActionHandler mqttActionHandler;
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData}, this, changeQuickRedirect, false, 8475, clsArr, Void.TYPE).isSupported && (mqttActionHandler = this.a) != null) {
                mqttActionHandler.onActionSuccess(taskId, callbackData);
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            LDSBaseMqttService.MqttActionHandler mqttActionHandler;
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8476, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported && (mqttActionHandler = this.a) != null) {
                mqttActionHandler.onActionFail(code, taskId, desc);
            }
        }
    }

    public int getMqttConnectStatue() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8463, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return l.C().D();
    }

    public String getErrorDetailMessage() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8464, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return l.C().A();
    }

    public void init(Context context) {
    }

    private void j(int code, String callbackKey, String dataObj, String desc) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(code), callbackKey, dataObj, desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8465, new Class[]{Integer.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            JSONObject _result = new JSONObject();
            if (code == 200) {
                com.leedarson.mqtt.logs.b.b("desc=" + desc);
            } else {
                com.leedarson.mqtt.logs.b.a("desc=" + desc);
            }
            try {
                _result.put("code", code);
                _result.put("desc", (Object) desc);
                if (!TextUtils.isEmpty(dataObj)) {
                    _result.put("data", (Object) new JSONObject(dataObj));
                }
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, _result.toString()));
            } catch (JSONException exception) {
                exception.printStackTrace();
                com.leedarson.mqtt.logs.b.a("返回给web端数据转化异常:" + exception.toString());
            }
        }
    }

    public e<Boolean> fullIpcDevicesUpdate(JSONObject _data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_data}, this, changeQuickRedirect, false, 8466, new Class[]{JSONObject.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        return l.C().z(_data);
    }

    public e<JSONObject> getFullIpcDevicesJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8467, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        return l.C().B();
    }
}
