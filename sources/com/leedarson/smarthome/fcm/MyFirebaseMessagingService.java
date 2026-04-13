package com.leedarson.smarthome.fcm;

import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.utils.m;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import smarthome.bean.PushMessageBean;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static ChangeQuickRedirect changeQuickRedirect;
    LoggerService c;
    private smarthome.repos.a d;
    /* access modifiers changed from: private */
    public String f = null;

    static /* synthetic */ void d(MyFirebaseMessagingService x0, String x1) {
        Class[] clsArr = {MyFirebaseMessagingService.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10658, clsArr, Void.TYPE).isSupported) {
            x0.h(x1);
        }
    }

    static /* synthetic */ void f(MyFirebaseMessagingService x0, String x1) {
        Class[] clsArr = {MyFirebaseMessagingService.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10659, clsArr, Void.TYPE).isSupported) {
            x0.c(x1);
        }
    }

    static /* synthetic */ void g(MyFirebaseMessagingService x0, String x1) {
        Class[] clsArr = {MyFirebaseMessagingService.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10660, clsArr, Void.TYPE).isSupported) {
            x0.i(x1);
        }
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (!PatchProxy.proxy(new Object[]{remoteMessage}, this, changeQuickRedirect, false, 10652, new Class[]{RemoteMessage.class}, Void.TYPE).isSupported) {
            try {
                timber.log.a.c("From: " + remoteMessage.getFrom(), new Object[0]);
                if (remoteMessage.getData().size() > 0) {
                    h("收到google推送");
                    timber.log.a.c("Message data payload: " + remoteMessage.getData(), new Object[0]);
                    Map<String, String> data = remoteMessage.getData();
                    String body = data.get("body");
                    String title = data.get("title");
                    String tag = data.get(Progress.TAG);
                    String url = data.get("url");
                    String bodyContent = "";
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        if (jsonObject.has("unReadNum")) {
                            int unRead = Integer.parseInt(jsonObject.getString("unReadNum"));
                            me.leolin.shortcutbadger.b.a(getApplicationContext(), unRead);
                            SharePreferenceUtils.setPrefInt(getApplicationContext(), "shortcut_badge_count", unRead);
                        }
                        if (jsonObject.has("body") != 0) {
                            bodyContent = jsonObject.getString("body");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    a.d(this, title, bodyContent, url, tag);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("msg_send_id", remoteMessage.getMessageId());
                    params.put("msg_send_title", title);
                    params.put("msg_send_content", body);
                    params.put("link_url", url);
                    params.put("link_page_name", "/app/main/");
                    com.leedarson.log.sensorsdata.a.b().l("MessageSend", params);
                    j(tag);
                }
            } catch (Exception e2) {
                timber.log.a.c("firebase exception: " + e2.toString(), new Object[0]);
            }
        }
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10653, new Class[0], Void.TYPE).isSupported) {
            super.onCreate();
            timber.log.a.c("onCreate: ", new Object[0]);
        }
    }

    public void j(String tag) {
        if (!PatchProxy.proxy(new Object[]{tag}, this, changeQuickRedirect, false, 10654, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.f == null) {
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new a(tag));
            } else {
                c(tag);
            }
        }
    }

    public class a implements OnCompleteListener<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        public void onComplete(@NonNull Task<String> task) {
            if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 10661, new Class[]{Task.class}, Void.TYPE).isSupported) {
                String token = null;
                try {
                    if (!task.isSuccessful()) {
                        timber.log.a.j("getInstanceId failed" + task.getException(), new Object[0]);
                    } else {
                        token = task.getResult();
                    }
                    MyFirebaseMessagingService myFirebaseMessagingService = MyFirebaseMessagingService.this;
                    MyFirebaseMessagingService.d(myFirebaseMessagingService, "获取token成功:" + token);
                    String unused = MyFirebaseMessagingService.this.f = token != null ? token : "";
                    MyFirebaseMessagingService.f(MyFirebaseMessagingService.this, this.a);
                } catch (Exception e) {
                    e.printStackTrace();
                    MyFirebaseMessagingService myFirebaseMessagingService2 = MyFirebaseMessagingService.this;
                    MyFirebaseMessagingService.g(myFirebaseMessagingService2, "获取token失败:" + e.getMessage());
                    MyFirebaseMessagingService myFirebaseMessagingService3 = MyFirebaseMessagingService.this;
                    MyFirebaseMessagingService.d(myFirebaseMessagingService3, "获取token失败:" + e.getMessage());
                }
            }
        }
    }

    private void c(String tag) {
        String pushUuid;
        if (!PatchProxy.proxy(new Object[]{tag}, this, changeQuickRedirect, false, 10655, new Class[]{String.class}, Void.TYPE).isSupported) {
            h("进入真实上报");
            if (this.d == null) {
                this.d = new smarthome.repos.a();
            }
            PushMessageBean pushMessageBean = null;
            try {
                pushMessageBean = (PushMessageBean) m.a(tag, PushMessageBean.class);
            } catch (Exception e) {
                h("exception:" + e.getMessage());
            }
            JSONArray eventRecordArray = new JSONArray();
            JSONObject obj = new JSONObject();
            if (pushMessageBean != null) {
                try {
                    if (pushMessageBean.getData() != null) {
                        pushUuid = pushMessageBean.getData().getPushUuid();
                        obj.put("phoneId", (Object) this.f);
                        obj.put("distinctMessage", (Object) pushUuid);
                        obj.put("receiveTime", System.currentTimeMillis());
                        eventRecordArray.put((Object) obj);
                        h("eventRecordArray:" + eventRecordArray);
                        this.d.d("send_message_confirmed", eventRecordArray, new b());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    i("send_message_confirmed exception:" + e2.getMessage() + ",data:" + tag);
                    StringBuilder sb = new StringBuilder();
                    sb.append("userEventLog send_message_confirmed exception: ");
                    sb.append(e2.getMessage());
                    h(sb.toString());
                    return;
                }
            }
            pushUuid = "";
            obj.put("phoneId", (Object) this.f);
            obj.put("distinctMessage", (Object) pushUuid);
            obj.put("receiveTime", System.currentTimeMillis());
            eventRecordArray.put((Object) obj);
            h("eventRecordArray:" + eventRecordArray);
            this.d.d("send_message_confirmed", eventRecordArray, new b());
        }
    }

    public class b extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 10664, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 10662, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                MyFirebaseMessagingService myFirebaseMessagingService = MyFirebaseMessagingService.this;
                MyFirebaseMessagingService.g(myFirebaseMessagingService, "send_message_confirmed fail:" + e.getMessage());
                MyFirebaseMessagingService myFirebaseMessagingService2 = MyFirebaseMessagingService.this;
                MyFirebaseMessagingService.d(myFirebaseMessagingService2, "userEventLog send_message_confirmed onError: " + e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 10663, new Class[]{String.class}, Void.TYPE).isSupported) {
                MyFirebaseMessagingService myFirebaseMessagingService = MyFirebaseMessagingService.this;
                MyFirebaseMessagingService.d(myFirebaseMessagingService, "userEventLog send_message_confirmed onSuccess: " + response);
            }
        }
    }

    private void i(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10656, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.c == null) {
                this.c = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            }
            LoggerService loggerService = this.c;
            if (loggerService != null) {
                loggerService.reportELK(this, msg, "info", "send_message_confirmed");
            }
        }
    }

    private void h(String msg) {
    }
}
