package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
import com.leedarson.base.utils.r;
import com.leedarson.bean.H5ActionName;
import com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.f;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.smartcamera.reporters.WebRtcLogStepBean;
import com.leedarson.smartcamera.reporters.WebRtcReporterV3;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.CloseReason;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.json.JSONObject;

/* compiled from: KVSWebSocketClient */
public class n {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Session a;
    /* access modifiers changed from: private */
    public ExecutorService b = Executors.newFixedThreadPool(3, new r("kvs-websocket"));
    /* access modifiers changed from: private */
    public f0 c;
    /* access modifiers changed from: private */
    public final Gson d = new Gson();
    /* access modifiers changed from: private */
    public f e;
    private String f = "";
    private String g = "";
    /* access modifiers changed from: private */
    public c h = new c(Looper.getMainLooper());
    public WebRtcServiceStateChangeLogBean i = new WebRtcServiceStateChangeLogBean();
    /* access modifiers changed from: private */
    public WebRtcLogStepBean j;
    WebRtcServiceStateChangeLogBean k = new WebRtcServiceStateChangeLogBean();

    public void v(String peerId) {
        this.f = peerId;
    }

    public void t(String deviceId) {
        this.g = deviceId;
    }

    public void s(f callback) {
        this.e = callback;
    }

    /* compiled from: KVSWebSocketClient */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        a(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, H5ActionName.PLAYER_LIVE_CLICK_SLEEP, new Class[0], Void.TYPE).isSupported) {
                ClientEndpointConfig cec = ClientEndpointConfig.a.c().a();
                ClientManager clientManager = new ClientManager();
                clientManager.getProperties().put(ClientProperties.LOG_HTTP_UPGRADE, true);
                try {
                    Session unused = n.this.a = clientManager.connectToServer(new C0175a(), cec, new URI(this.c));
                } catch (DeploymentException | IOException | URISyntaxException e) {
                    if (n.this.h != null) {
                        Message msg = Message.obtain();
                        msg.what = 7;
                        msg.obj = e;
                        n.this.h.sendMessage(msg);
                    }
                }
            }
        }

        /* renamed from: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n$a$a  reason: collision with other inner class name */
        /* compiled from: KVSWebSocketClient */
        public class C0175a extends Endpoint {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0175a() {
            }

            public void onOpen(Session session, EndpointConfig endpointConfig) {
                if (!PatchProxy.proxy(new Object[]{session, endpointConfig}, this, changeQuickRedirect, false, 10032, new Class[]{Session.class, EndpointConfig.class}, Void.TYPE).isSupported) {
                    if (n.this.h != null) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        n.this.h.sendMessage(msg);
                    }
                    session.addMessageHandler(new C0176a());
                }
            }

            /* renamed from: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n$a$a$a  reason: collision with other inner class name */
            /* compiled from: KVSWebSocketClient */
            public class C0176a implements MessageHandler.Whole<String> {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0176a() {
                }

                public /* bridge */ /* synthetic */ void onMessage(Object obj) {
                    if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 10036, new Class[]{Object.class}, Void.TYPE).isSupported) {
                        a((String) obj);
                    }
                }

                public void a(String message) {
                    Event evt;
                    if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 10035, new Class[]{String.class}, Void.TYPE).isSupported) {
                        Log.d("KVSWebSocketClient", "websocket Received message=" + message);
                        if (!message.isEmpty() && message.contains("messagePayload") && (evt = (Event) n.this.d.fromJson(message, Event.class)) != null && evt.getMessageType() != null && !evt.getMessagePayload().isEmpty()) {
                            if (evt.getMessageType().equalsIgnoreCase("SDP_OFFER")) {
                                Log.d("KVSWebSocketClient", "Offer received: SenderClientId=" + evt.getSenderClientId());
                                Log.d("KVSWebSocketClient", new String(Base64.decode(evt.getMessagePayload(), 0)));
                                if (n.this.h != null) {
                                    Message msg = Message.obtain();
                                    msg.what = 2;
                                    msg.obj = evt;
                                    n.this.h.sendMessage(msg);
                                }
                            }
                            if (evt.getMessageType().equalsIgnoreCase("SDP_ANSWER")) {
                                Log.d("KVSWebSocketClient", "Answer received: SenderClientId=" + evt.getSenderClientId());
                                if (n.this.e != null) {
                                    if (n.this.h != null) {
                                        Message msg2 = Message.obtain();
                                        msg2.what = 3;
                                        msg2.obj = evt;
                                        n.this.h.sendMessage(msg2);
                                    }
                                    JSONObject sdpJsonResObj = new JSONObject();
                                    n.this.i.putData(sdpJsonResObj, "payload", evt.getMessagePayload());
                                    n.this.i.putData(sdpJsonResObj, "senderClientId", evt.getSenderClientId());
                                    n.this.i.putData(sdpJsonResObj, "sdp", evt.getSdp());
                                    n.this.i.putData(sdpJsonResObj, "body", evt.getBody());
                                    n.this.i.endTraceSuccess(sdpJsonResObj);
                                    n.this.c.F0.onNext(n.this.i);
                                    if (n.this.j != null) {
                                        n.this.j.responseParams = sdpJsonResObj.toString();
                                        n.this.j.endTrace("KVS收到设备Answer", 200);
                                    }
                                }
                            }
                            if (evt.getMessageType().equalsIgnoreCase("ICE_CANDIDATE")) {
                                Log.d("KVSWebSocketClient", "Ice Candidate received: SenderClientId=" + evt.getSenderClientId());
                                JSONObject jsonCandidateObj = new JSONObject();
                                n.this.k.putData(jsonCandidateObj, "payload", evt.getMessagePayload());
                                n.this.k.putData(jsonCandidateObj, "clientId", evt.getSenderClientId());
                                n.this.k.putData(jsonCandidateObj, "body", evt.getBody());
                                WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean = n.this.k;
                                if (webRtcServiceStateChangeLogBean.rsponseTime != 0 || webRtcServiceStateChangeLogBean.requestTime == 0) {
                                    WebRtcServiceStateChangeLogBean tempWebRtcStateBean = new WebRtcServiceStateChangeLogBean();
                                    WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean2 = n.this.k;
                                    tempWebRtcStateBean.requestTime = webRtcServiceStateChangeLogBean2.requestTime;
                                    tempWebRtcStateBean.requestObj = webRtcServiceStateChangeLogBean2.requestObj;
                                    tempWebRtcStateBean.endTraceSuccess(jsonCandidateObj);
                                    n.this.c.G0.onNext(tempWebRtcStateBean);
                                } else {
                                    webRtcServiceStateChangeLogBean.endTraceSuccess(jsonCandidateObj);
                                    n.this.c.G0.onNext(n.this.k);
                                }
                                Log.d("KVSWebSocketClient", new String(Base64.decode(evt.getMessagePayload(), 0)));
                                if (n.this.h != null) {
                                    Message msg3 = Message.obtain();
                                    msg3.what = 4;
                                    msg3.obj = evt;
                                    n.this.h.sendMessage(msg3);
                                }
                            }
                        }
                    }
                }
            }

            public void onClose(Session session, CloseReason closeReason) {
                Class[] clsArr = {Session.class, CloseReason.class};
                if (!PatchProxy.proxy(new Object[]{session, closeReason}, this, changeQuickRedirect, false, 10033, clsArr, Void.TYPE).isSupported) {
                    super.onClose(session, closeReason);
                    if (n.this.h != null && session != null && closeReason != null) {
                        Message msg = Message.obtain();
                        msg.what = 5;
                        msg.obj = "session: requestUri=" + session.getRequestURI() + ",closeReqson=" + closeReason.b();
                        n.this.h.sendMessage(msg);
                    }
                }
            }

            public void onError(Session session, Throwable thr) {
                Class[] clsArr = {Session.class, Throwable.class};
                if (!PatchProxy.proxy(new Object[]{session, thr}, this, changeQuickRedirect, false, 10034, clsArr, Void.TYPE).isSupported) {
                    super.onError(session, thr);
                    Log.w("KVSWebSocketClient", thr);
                    if (n.this.h != null && session != null && thr != null) {
                        Event event = new Event();
                        event.setBody("session:" + session.toString() + ", error:=" + thr.toString());
                        Message msg = Message.obtain();
                        msg.what = 6;
                        msg.obj = event;
                        n.this.h.sendMessage(msg);
                    }
                }
            }
        }
    }

    public void i(String wsHost) {
        if (!PatchProxy.proxy(new Object[]{wsHost}, this, changeQuickRedirect, false, H5ActionName.PLAYER_ALARM_STATUS_OPEN, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.b.submit(new a(wsHost));
        }
    }

    public boolean k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, H5ActionName.PLAYER_ALARM_STATUS_CLOSED, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Session session = this.a;
        if (session != null) {
            return session.isOpen();
        }
        return false;
    }

    public void r(com.leedarson.smartcamera.kvswebrtc.signaling.model.Message offer) {
        if (!PatchProxy.proxy(new Object[]{offer}, this, changeQuickRedirect, false, 10023, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.model.Message.class}, Void.TYPE).isSupported) {
            this.b.submit(new f(this, offer));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ void o(com.leedarson.smartcamera.kvswebrtc.signaling.model.Message offer) {
        if (!PatchProxy.proxy(new Object[]{offer}, this, changeQuickRedirect, false, H5ActionName.PLAYER_LIVE_CLOSE_ALARM, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.model.Message.class}, Void.TYPE).isSupported) {
            if (offer.getAction().equalsIgnoreCase("SDP_OFFER")) {
                com.leedarson.log.f.b("KVSWebSocketClient", "Sending Offer ：" + offer.getSenderClientId() + " message:" + offer.getMessagePayload());
                this.i = new WebRtcServiceStateChangeLogBean();
                JSONObject requestObj = new JSONObject();
                this.i.putData(requestObj, "clientId", offer.getSenderClientId());
                this.i.putData(requestObj, "payload", offer.getMessagePayload());
                this.i.putData(requestObj, "recipientClient", offer.getRecipientClientId());
                this.i.begainTrace(requestObj);
                WebRtcLogStepBean webRtcLogStepBean = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_SDP, -31007501);
                this.j = webRtcLogStepBean;
                webRtcLogStepBean.desc = "媒体协商超时";
                webRtcLogStepBean.requestParams = new Gson().toJson((Object) offer);
                WebRtcReporterV3.v(this.f, this.g).K(this.j);
                p(offer);
            }
        }
    }

    public void q(com.leedarson.smartcamera.kvswebrtc.signaling.model.Message candidate) {
        if (!PatchProxy.proxy(new Object[]{candidate}, this, changeQuickRedirect, false, 10025, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.model.Message.class}, Void.TYPE).isSupported) {
            this.b.submit(new e(this, candidate));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public /* synthetic */ void m(com.leedarson.smartcamera.kvswebrtc.signaling.model.Message candidate) {
        if (!PatchProxy.proxy(new Object[]{candidate}, this, changeQuickRedirect, false, H5ActionName.PLAYER_LIVE_GOBACK, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.model.Message.class}, Void.TYPE).isSupported) {
            if (candidate.getAction().equalsIgnoreCase("ICE_CANDIDATE")) {
                this.k = new WebRtcServiceStateChangeLogBean();
                JSONObject jsonObject = new JSONObject();
                this.k.putData(jsonObject, "payload", candidate.getMessagePayload());
                this.k.putData(jsonObject, "senderClientId", candidate.getSenderClientId());
                this.k.putData(jsonObject, "recipientClientId", candidate.getRecipientClientId());
                this.k.begainTrace(jsonObject);
                p(candidate);
                WebRtcLogStepBean _sendCandidiateStep = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_CENDIDITE_SEND_TO, 200);
                _sendCandidiateStep.requestParams = jsonObject.toString();
                _sendCandidiateStep.endTrace("候选人交换(App==>Device)", 200);
                WebRtcReporterV3.v(this.f, this.g).K(_sendCandidiateStep);
            }
            Log.d("KVSWebSocketClient", "Sent Ice candidate message");
        }
    }

    /* compiled from: KVSWebSocketClient */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10038, new Class[0], Void.TYPE).isSupported) {
                Log.d("KVSWebSocketClient", "disconnect: ");
                try {
                    if (n.this.a.isOpen()) {
                        n.this.a.close();
                    } else {
                        Log.w("KVSWebSocketClient", "Connection already closed for " + n.this.a.getRequestURI());
                    }
                    n.this.b.shutdownNow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, H5ActionName.PLAYER_MUTI_STATUS_DESTROY, new Class[0], Void.TYPE).isSupported) {
            this.b.submit(new b());
            try {
                this.b.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e2) {
                Log.e("KVSWebSocketClient", "Error in disconnect");
            }
        }
    }

    private void p(com.leedarson.smartcamera.kvswebrtc.signaling.model.Message message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, H5ActionName.PLAYER_MUTI_STATUS_CREATED, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.model.Message.class}, Void.TYPE).isSupported) {
            String jsonMessage = this.d.toJson((Object) message);
            JSONObject endJsonObj = new JSONObject();
            Log.d("KVSWebSocketClient", "websocket Sending JSON Message= " + jsonMessage);
            try {
                this.a.getBasicRemote().sendText(jsonMessage);
            } catch (IOException e2) {
                Log.e("KVSWebSocketClient", "Exception" + e2.getMessage());
                WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean = this.i;
                webRtcServiceStateChangeLogBean.putData(endJsonObj, "error", " exception:=" + e2.toString());
                this.i.endTraceExcept(BaseResp.ERR_MSG_SEND_FAIL, endJsonObj);
                this.c.F0.onNext(this.i);
            } catch (Exception e3) {
                Log.e("KVSWebSocketClient", "web socket send: exception");
                WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean2 = this.i;
                webRtcServiceStateChangeLogBean2.putData(endJsonObj, "error", " exception:=" + e3.toString());
                this.i.endTraceExcept(BaseResp.ERR_MSG_SEND_FAIL, endJsonObj);
                this.c.F0.onNext(this.i);
            }
        }
    }

    public void u(f0 kvsWebRtcChannel) {
        this.c = kvsWebRtcChannel;
    }

    /* compiled from: KVSWebSocketClient */
    public class c extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        public c(Looper mainLooper) {
            super(mainLooper);
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10039, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        if (n.this.e != null) {
                            n.this.e.b();
                            return;
                        }
                        return;
                    case 2:
                        if (n.this.e != null) {
                            n.this.e.d((Event) msg.obj);
                            return;
                        }
                        return;
                    case 3:
                        if (n.this.e != null) {
                            n.this.e.c((Event) msg.obj);
                            return;
                        }
                        return;
                    case 4:
                        if (n.this.e != null) {
                            n.this.e.f((Event) msg.obj);
                            return;
                        }
                        return;
                    case 5:
                        if (n.this.e != null) {
                            n.this.e.e((String) msg.obj);
                            return;
                        }
                        return;
                    case 6:
                        if (n.this.e != null) {
                            n.this.e.a((Event) msg.obj);
                            return;
                        }
                        return;
                    case 7:
                        if (n.this.e != null) {
                            n.this.e.onException((Exception) msg.obj);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
