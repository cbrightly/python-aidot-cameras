package com.leedarson.serviceimpl.ipcservice;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.Constants;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PushBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.view.ldsnakebar.TopSnackbar;
import com.leedarson.serviceinterface.INoNetSnapTipView;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.ui.SnapAnimaFragment;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.utils.ScreenUtils;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.util.MeshConstants;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class IpcServiceImpl implements IpcService {
    public static ArrayList<IpcDeviceBean> a = new ArrayList<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g c;
    /* access modifiers changed from: private */
    public Toast d;
    private int e = 0;
    /* access modifiers changed from: private */
    public f0 f;
    private long g = 0;
    com.leedarson.serviceimpl.ipcservice.data_processors.b h = new com.leedarson.serviceimpl.ipcservice.data_processors.b();
    private Handler i = new Handler();
    private io.reactivex.disposables.b j;
    private long k = 0;
    long l = System.currentTimeMillis();
    com.leedarson.newui.repos.n m = new com.leedarson.newui.repos.n();

    public static IpcDeviceBean p(String id) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{id}, (Object) null, changeQuickRedirect, true, 8023, new Class[]{String.class}, IpcDeviceBean.class);
        if (proxy.isSupported) {
            return (IpcDeviceBean) proxy.result;
        }
        for (int i2 = 0; i2 < a.size(); i2++) {
            if (id.equals(a.get(i2).id)) {
                return a.get(i2);
            }
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v1, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v2, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v3, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v4, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v5, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v6, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v8, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v9, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v10, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v22, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v11, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v23, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v12, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v13, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v24, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v14, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v25, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v15, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v26, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v16, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v27, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v17, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v18, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v29, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v19, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v20, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v31, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v21, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v32, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v22, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v33, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v23, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v34, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v24, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v35, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v25, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v36, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v26, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v37, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v27, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v38, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v28, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v39, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v29, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v40, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v30, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v31, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v42, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v32, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v33, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v43, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v34, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v35, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v44, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v36, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v45, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v37, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v38, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v46, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v39, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v47, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v40, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v41, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v48, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v42, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v43, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v49, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v44, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v45, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v50, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v46, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v47, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v51, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v48, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v49, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v52, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v50, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v51, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: android.content.Context} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v52, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: android.content.Context} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v18, resolved type: android.content.Context} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v53, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v54, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v55, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v55, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v56, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v56, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v57, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v57, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v58, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v59, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v58, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v60, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v61, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v62, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v63, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v28, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v64, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v65, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v66, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v67, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v68, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v69, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v70, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v71, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v72, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v73, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v30, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v74, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v75, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v31, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v76, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v77, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v33, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v34, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v73, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v36, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v75, resolved type: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v37, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v77, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v40, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v41, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v43, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v44, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v88, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v46, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v47, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v78, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r33v79, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v6, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v9, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v19, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v84, resolved type: org.json.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r42v8, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r42v10, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v107, resolved type: org.json.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r42v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v112, resolved type: org.json.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v73, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v107, resolved type: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v108, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v75, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v76, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v77, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v110, resolved type: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v111, resolved type: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v112, resolved type: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v79, resolved type: android.app.Activity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v113, resolved type: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl} */
    /* JADX WARNING: type inference failed for: r5v81, types: [com.leedarson.serviceinterface.event.JsBridgeCallbackEvent, java.lang.Object] */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x02ce, code lost:
        if (r9.has("tab") == false) goto L_0x02d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:?, code lost:
        r10 = r9.getString("tab");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02d7, code lost:
        r10 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02df, code lost:
        if (r9.has("needFinish") == false) goto L_0x02e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:?, code lost:
        r12 = r9.getBoolean("needFinish");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02e8, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02ea, code lost:
        r40 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x02f2, code lost:
        if (r9.has("cloudPlayback") == false) goto L_0x0302;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02fa, code lost:
        r11 = r9.getBoolean("cloudPlayback");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02fc, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02fd, code lost:
        r1 = r0;
        r41 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
        r3 = a.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x030c, code lost:
        if (r3.hasNext() == false) goto L_0x0396;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x030e, code lost:
        r38 = r3.next();
        r39 = r3;
        r3 = timber.log.a.g(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x031c, code lost:
        r41 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        r5 = new java.lang.StringBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0323, code lost:
        r42 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
        r5.append("进入直播页: hasCode:");
        r5.append(r38.hashCode());
        r5.append(",id:");
        r7 = r38;
        r38 = r10;
        r5.append(r7.id);
        r5.append(",radarConfig:");
        r5.append(r7.hasPath());
        r19 = r12;
        r3.m(r5.toString(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x035d, code lost:
        if (r1.equals(r7.id) == false) goto L_0x037b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0365, code lost:
        if (r9.has("isOwner") == false) goto L_0x0371;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0367, code lost:
        r7.isOwner = r9.getBoolean("isOwner");
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0371, code lost:
        r3 = true;
        r7.isOwner = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0374, code lost:
        r7.isCurrentDevice = java.lang.Boolean.valueOf(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x037b, code lost:
        r7.isCurrentDevice = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0382, code lost:
        r7.cloudPlayback = r11;
        r10 = r38;
        r3 = r39;
        r5 = r41;
        r7 = r42;
        r12 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0390, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0391, code lost:
        r42 = r7;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0396, code lost:
        r41 = r5;
        r42 = r7;
        r9 = r10;
        r10 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x03a3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x03a4, code lost:
        r41 = r5;
        r42 = r7;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x03ab, code lost:
        r40 = r3;
        r41 = r5;
        r42 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x03b1, code lost:
        r9 = r38;
        r10 = r39;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x03b5, code lost:
        r3 = null;
        r5 = new android.os.Bundle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x03c1, code lost:
        if (com.leedarson.bean.H5ActionName.LIVE_PAGE.equals(r8) == false) goto L_0x03f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x03c3, code lost:
        if (r10 == false) goto L_0x03cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x03c5, code lost:
        com.leedarson.base.utils.c.h().g();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x03cc, code lost:
        r3 = new android.content.Intent(r6, com.leedarson.newui.IpcLiveActivity.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x03d8, code lost:
        if (android.text.TextUtils.isEmpty(r9) != false) goto L_0x0475;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03e0, code lost:
        if ("events".equals(r9) == false) goto L_0x0475;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x03e2, code lost:
        r5.putBoolean("isEvent", true);
        r3 = new android.content.Intent(r6, com.leedarson.newui.EventsActivity.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x03f8, code lost:
        if (com.leedarson.bean.H5ActionName.SECURITY_CAMERAS.equals(r8) == false) goto L_0x0410;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x03fa, code lost:
        r3 = new android.content.Intent(r6, com.leedarson.newui.smartwidget.SecurityCamsActivity.class);
        r5.putBoolean("cloudPlayback", r11);
        com.leedarson.base.utils.c.h().t(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0416, code lost:
        if (com.leedarson.bean.H5ActionName.IPC_RADARMAP.equals(r8) == false) goto L_0x0442;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0418, code lost:
        android.util.Log.i(r2, "radar deviceId:" + r1);
        com.alibaba.android.arouter.launcher.a.c().a("/location/radar").T("deviceId", r1).D(r6);
        r6.overridePendingTransition(0, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0441, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x0448, code lost:
        if (com.leedarson.bean.H5ActionName.IPC_SIGNALTEST.equals(r8) == false) goto L_0x0475;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x044a, code lost:
        r3 = new android.content.Intent(r6, com.leedarson.newui.signal_test.SignalTestActivity.class);
        r2 = null;
        r7 = a.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x045d, code lost:
        if (r7.hasNext() == false) goto L_0x0470;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x045f, code lost:
        r12 = r7.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x046b, code lost:
        if (r12.id.equals(r1) == false) goto L_0x046f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x046d, code lost:
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0470, code lost:
        r5.putParcelable("current_device", r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0475, code lost:
        if (r3 != null) goto L_0x0478;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0477, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0478, code lost:
        r5.putParcelableArrayList("devices", a);
        r3.putExtras(r5);
        r6.startActivity(r3);
        r6.overridePendingTransition(com.leedarson.R$anim.ipc_slide_in_right, com.leedarson.R$anim.ipc_slide_out_left);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x048c, code lost:
        r7 = r42;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x048f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0490, code lost:
        r7 = r42;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0494, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x0495, code lost:
        r40 = r3;
        r41 = r5;
        r42 = r7;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x049d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x049e, code lost:
        r40 = r3;
        r41 = r5;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x04a3, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x04a7, code lost:
        r10 = r40;
        r19 = r41;
        r3 = r4;
        r34 = r6;
        r35 = r13;
        r33 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0181, code lost:
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0183, code lost:
        switch(r8) {
            case 0: goto L_0x01ef;
            case 1: goto L_0x018c;
            default: goto L_0x0186;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0186, code lost:
        r40 = r3;
        r41 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x018c, code lost:
        r8 = com.leedarson.base.utils.c.h().i();
        android.util.Log.e(r2, "Navigator ACTION_POP: " + r8 + "==" + r6.getClass());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01b4, code lost:
        if (r8 == null) goto L_0x01db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01c0, code lost:
        if (r8.getSimpleName().equals("IpcMainActivity") == false) goto L_0x01db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01c2, code lost:
        com.leedarson.base.utils.c.h().r((java.lang.Class) null);
        r4.C(r6);
        com.leedarson.base.utils.c.h().t(false);
        r40 = r3;
        r41 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01db, code lost:
        org.greenrobot.eventbus.c.c().l(new com.leedarson.serviceinterface.event.Event("CloseWebViewShowEvent", r1, r1, r1));
        r40 = r3;
        r41 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01f4, code lost:
        r7 = new org.json.JSONObject(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        android.util.Log.e(r2, "Navigator.push dataObj: " + r7);
        r8 = r7.getString(com.luck.picture.lib.config.PictureConfig.EXTRA_PAGE);
        r9 = r1;
        r11 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0217, code lost:
        switch(r8.hashCode()) {
            case -1674056802: goto L_0x023c;
            case 1059298460: goto L_0x0231;
            case 1247249960: goto L_0x0226;
            case 1962705036: goto L_0x021b;
            default: goto L_0x021a;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0221, code lost:
        if (r8.equals(com.leedarson.bean.H5ActionName.IPC_SIGNALTEST) == false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0223, code lost:
        r17 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x022c, code lost:
        if (r8.equals(com.leedarson.bean.H5ActionName.IPC_RADARMAP) == false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x022e, code lost:
        r17 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0237, code lost:
        if (r8.equals(com.leedarson.bean.H5ActionName.SECURITY_CAMERAS) == false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0239, code lost:
        r17 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0242, code lost:
        if (r8.equals(com.leedarson.bean.H5ActionName.LIVE_PAGE) == false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0244, code lost:
        r17 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0247, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0248, code lost:
        r1 = r0;
        r40 = r3;
        r41 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x024f, code lost:
        r17 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0251, code lost:
        switch(r17) {
            case 0: goto L_0x0260;
            case 1: goto L_0x0260;
            case 2: goto L_0x0260;
            case 3: goto L_0x0260;
            default: goto L_0x0254;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0254, code lost:
        r40 = r3;
        r41 = r5;
        r42 = r7;
        r38 = r9;
        r39 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        r38 = r9;
        r39 = true;
        r16 = java.lang.System.currentTimeMillis() - r4.g;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0270, code lost:
        if (r16 >= 400) goto L_0x0279;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0276, code lost:
        if (r16 <= 0) goto L_0x0279;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0278, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x028f, code lost:
        if (com.leedarson.base.utils.c.h().c().getClass().getSimpleName().equals("IpcMainActivity") == false) goto L_0x029c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        com.leedarson.base.utils.c.h().c().finish();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        r4.g = java.lang.System.currentTimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x02a8, code lost:
        if (r7.has("params") == false) goto L_0x03ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x02aa, code lost:
        com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4.b, "current_params", r7.getString("params"));
        r9 = r7.getJSONObject("params");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x02c1, code lost:
        if (r9.has("deviceId") == false) goto L_0x02c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x02c7, code lost:
        r1 = r9.getString("deviceId");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:977:0x1a4c  */
    /* JADX WARNING: Removed duplicated region for block: B:993:0x1a89  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(android.app.Activity r38, java.lang.String r39, java.lang.String r40, java.lang.String r41, java.lang.String r42) {
        /*
            r37 = this;
            java.lang.String r1 = "hasPTZ"
            java.lang.String r2 = "talkStyle"
            java.lang.String r3 = "productId"
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            java.lang.String r5 = "playerFrame"
            java.lang.String r6 = "skin"
            r7 = 5
            java.lang.Object[] r8 = new java.lang.Object[r7]
            r15 = 0
            r8[r15] = r38
            r14 = 1
            r8[r14] = r39
            r13 = 2
            r8[r13] = r40
            r12 = 3
            r8[r12] = r41
            r9 = 4
            r8[r9] = r42
            com.meituan.robust.ChangeQuickRedirect r10 = changeQuickRedirect
            java.lang.Class[] r11 = new java.lang.Class[r7]
            java.lang.Class<android.app.Activity> r9 = android.app.Activity.class
            r11[r15] = r9
            r11[r14] = r4
            r11[r13] = r4
            r11[r12] = r4
            r9 = 4
            r11[r9] = r4
            java.lang.Class r4 = java.lang.Void.TYPE
            r16 = 0
            r17 = 8024(0x1f58, float:1.1244E-41)
            r9 = r37
            r18 = r11
            r11 = r16
            r7 = r12
            r12 = r17
            r13 = r18
            r7 = r14
            r14 = r4
            com.meituan.robust.PatchProxyResult r4 = com.meituan.robust.PatchProxy.proxy(r8, r9, r10, r11, r12, r13, r14)
            boolean r4 = r4.isSupported
            if (r4 == 0) goto L_0x004b
            return
        L_0x004b:
            r4 = r37
            r14 = r39
            r13 = r41
            r12 = r38
            r11 = r40
            r10 = r42
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "service:"
            r8.append(r9)
            r8.append(r11)
            java.lang.String r9 = " callbackKey="
            r8.append(r9)
            r8.append(r14)
            java.lang.String r9 = "  action:"
            r8.append(r9)
            r8.append(r13)
            java.lang.String r9 = "  data:"
            r8.append(r9)
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            java.lang.String r9 = "IpcServiceImpl"
            com.leedarson.log.g.a(r9, r8)
            r8 = 0
            boolean r18 = android.text.TextUtils.isEmpty(r10)     // Catch:{ JSONException -> 0x0094 }
            if (r18 != 0) goto L_0x0092
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0094 }
            r7.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0094 }
            r8 = r7
        L_0x0092:
            r7 = r8
            goto L_0x009a
        L_0x0094:
            r0 = move-exception
            r7 = r0
            r7.printStackTrace()
            r7 = r8
        L_0x009a:
            java.lang.String r8 = "KVSWebRTC"
            boolean r8 = r8.equals(r11)
            r20 = -1
            java.lang.String r15 = "deviceId"
            if (r8 == 0) goto L_0x0144
            int r1 = r13.hashCode()
            switch(r1) {
                case 530405532: goto L_0x00b9;
                case 951351530: goto L_0x00ae;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            goto L_0x00c4
        L_0x00ae:
            java.lang.String r1 = "connect"
            boolean r1 = r13.equals(r1)
            if (r1 == 0) goto L_0x00ad
            r18 = 0
            goto L_0x00c6
        L_0x00b9:
            java.lang.String r1 = "disconnect"
            boolean r1 = r13.equals(r1)
            if (r1 == 0) goto L_0x00ad
            r18 = 1
            goto L_0x00c6
        L_0x00c4:
            r18 = r20
        L_0x00c6:
            switch(r18) {
                case 0: goto L_0x00e8;
                case 1: goto L_0x00cb;
                default: goto L_0x00c9;
            }
        L_0x00c9:
            goto L_0x0139
        L_0x00cb:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e2 }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x00e2 }
            r7 = r1
            java.lang.String r1 = r7.getString(r15)     // Catch:{ Exception -> 0x00e2 }
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x00e2 }
            com.leedarson.event.KVSWebrtcDisConnectEvent r3 = new com.leedarson.event.KVSWebrtcDisConnectEvent     // Catch:{ Exception -> 0x00e2 }
            r3.<init>(r14, r1)     // Catch:{ Exception -> 0x00e2 }
            r2.l(r3)     // Catch:{ Exception -> 0x00e2 }
            goto L_0x0139
        L_0x00e2:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            goto L_0x0139
        L_0x00e8:
            java.lang.String r1 = "UTF-8"
            java.lang.String r1 = java.net.URLDecoder.decode(r10, r1)     // Catch:{ Exception -> 0x0133 }
            r10 = r1
            java.lang.String r1 = "ACTION_KVS_CONNECT"
            com.leedarson.smartcamera.utils.e.b(r1, r10)     // Catch:{ Exception -> 0x0133 }
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0133 }
            if (r1 != 0) goto L_0x0132
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0133 }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x0133 }
            r7 = r1
            java.lang.String r1 = "accessKeyId"
            java.lang.String r22 = r7.getString(r1)     // Catch:{ Exception -> 0x0133 }
            java.lang.String r1 = "secretAccessKey"
            java.lang.String r23 = r7.getString(r1)     // Catch:{ Exception -> 0x0133 }
            java.lang.String r1 = "sessionToken"
            java.lang.String r24 = r7.getString(r1)     // Catch:{ Exception -> 0x0133 }
            java.lang.String r1 = "channelArn"
            java.lang.String r25 = r7.getString(r1)     // Catch:{ Exception -> 0x0133 }
            java.lang.String r1 = "region"
            java.lang.String r26 = r7.getString(r1)     // Catch:{ Exception -> 0x0133 }
            java.lang.String r27 = r7.getString(r15)     // Catch:{ Exception -> 0x0133 }
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0133 }
            com.leedarson.event.KVSWebrtcConnectEvent r2 = new com.leedarson.event.KVSWebrtcConnectEvent     // Catch:{ Exception -> 0x0133 }
            r20 = r2
            r21 = r14
            r20.<init>(r21, r22, r23, r24, r25, r26, r27)     // Catch:{ Exception -> 0x0133 }
            r1.l(r2)     // Catch:{ Exception -> 0x0133 }
        L_0x0132:
            goto L_0x0139
        L_0x0133:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x0139:
            r3 = r4
            r19 = r11
            r34 = r12
            r35 = r13
            r33 = r14
            goto L_0x1a2a
        L_0x0144:
            java.lang.String r8 = "Navigator"
            boolean r8 = r8.equals(r11)
            r22 = r1
            java.lang.String r1 = ""
            if (r8 == 0) goto L_0x04b4
            com.leedarson.serviceimpl.ipcservice.data_processors.b r8 = r4.h
            r2 = r9
            r9 = r12
            r3 = r10
            r10 = r14
            r5 = r11
            r6 = r12
            r12 = r13
            r38 = r13
            r13 = r3
            r8.a(r9, r10, r11, r12, r13)
            int r8 = r38.hashCode()
            switch(r8) {
                case 111185: goto L_0x0175;
                case 3452698: goto L_0x0169;
                default: goto L_0x0166;
            }
        L_0x0166:
            r13 = r38
            goto L_0x0181
        L_0x0169:
            java.lang.String r8 = "push"
            r13 = r38
            boolean r8 = r13.equals(r8)
            if (r8 == 0) goto L_0x0181
            r8 = 0
            goto L_0x0183
        L_0x0175:
            r13 = r38
            java.lang.String r8 = "pop"
            boolean r8 = r13.equals(r8)
            if (r8 == 0) goto L_0x0181
            r8 = 1
            goto L_0x0183
        L_0x0181:
            r8 = r20
        L_0x0183:
            switch(r8) {
                case 0: goto L_0x01ef;
                case 1: goto L_0x018c;
                default: goto L_0x0186;
            }
        L_0x0186:
            r40 = r3
            r41 = r5
            goto L_0x04a7
        L_0x018c:
            com.leedarson.base.utils.c r8 = com.leedarson.base.utils.c.h()
            java.lang.Class r8 = r8.i()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Navigator ACTION_POP: "
            r9.append(r10)
            r9.append(r8)
            java.lang.String r10 = "=="
            r9.append(r10)
            java.lang.Class r10 = r6.getClass()
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r2, r9)
            if (r8 == 0) goto L_0x01db
            java.lang.String r2 = r8.getSimpleName()
            java.lang.String r9 = "IpcMainActivity"
            boolean r2 = r2.equals(r9)
            if (r2 == 0) goto L_0x01db
            com.leedarson.base.utils.c r1 = com.leedarson.base.utils.c.h()
            r2 = 0
            r1.r(r2)
            r4.C(r6)
            com.leedarson.base.utils.c r1 = com.leedarson.base.utils.c.h()
            r2 = 0
            r1.t(r2)
            r40 = r3
            r41 = r5
            goto L_0x04a7
        L_0x01db:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r9 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r10 = "CloseWebViewShowEvent"
            r9.<init>(r10, r1, r1, r1)
            r2.l(r9)
            r40 = r3
            r41 = r5
            goto L_0x04a7
        L_0x01ef:
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x049d }
            r8.<init>((java.lang.String) r3)     // Catch:{ JSONException -> 0x049d }
            r7 = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0494 }
            r8.<init>()     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r9 = "Navigator.push dataObj: "
            r8.append(r9)     // Catch:{ JSONException -> 0x0494 }
            r8.append(r7)     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x0494 }
            android.util.Log.e(r2, r8)     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r8 = "page"
            java.lang.String r8 = r7.getString(r8)     // Catch:{ JSONException -> 0x0494 }
            r9 = r1
            r10 = 1
            r11 = 1
            int r12 = r8.hashCode()     // Catch:{ JSONException -> 0x0494 }
            switch(r12) {
                case -1674056802: goto L_0x023c;
                case 1059298460: goto L_0x0231;
                case 1247249960: goto L_0x0226;
                case 1962705036: goto L_0x021b;
                default: goto L_0x021a;
            }
        L_0x021a:
            goto L_0x024f
        L_0x021b:
            java.lang.String r12 = "IPC.signalTest"
            boolean r12 = r8.equals(r12)     // Catch:{ JSONException -> 0x0247 }
            if (r12 == 0) goto L_0x021a
            r17 = 3
            goto L_0x0251
        L_0x0226:
            java.lang.String r12 = "IPC.radarMap"
            boolean r12 = r8.equals(r12)     // Catch:{ JSONException -> 0x0247 }
            if (r12 == 0) goto L_0x021a
            r17 = 2
            goto L_0x0251
        L_0x0231:
            java.lang.String r12 = "IPC.securityCameras"
            boolean r12 = r8.equals(r12)     // Catch:{ JSONException -> 0x0247 }
            if (r12 == 0) goto L_0x021a
            r17 = 0
            goto L_0x0251
        L_0x023c:
            java.lang.String r12 = "IPC.live"
            boolean r12 = r8.equals(r12)     // Catch:{ JSONException -> 0x0247 }
            if (r12 == 0) goto L_0x021a
            r17 = 1
            goto L_0x0251
        L_0x0247:
            r0 = move-exception
            r1 = r0
            r40 = r3
            r41 = r5
            goto L_0x04a3
        L_0x024f:
            r17 = r20
        L_0x0251:
            switch(r17) {
                case 0: goto L_0x0260;
                case 1: goto L_0x0260;
                case 2: goto L_0x0260;
                case 3: goto L_0x0260;
                default: goto L_0x0254;
            }
        L_0x0254:
            r40 = r3
            r41 = r5
            r42 = r7
            r38 = r9
            r39 = r10
            goto L_0x03b1
        L_0x0260:
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x0494 }
            r38 = r9
            r39 = r10
            long r9 = r4.g     // Catch:{ JSONException -> 0x0494 }
            long r16 = r16 - r9
            r9 = 400(0x190, double:1.976E-321)
            int r9 = (r16 > r9 ? 1 : (r16 == r9 ? 0 : -1))
            if (r9 >= 0) goto L_0x0279
            r9 = 0
            int r9 = (r16 > r9 ? 1 : (r16 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x0279
            return
        L_0x0279:
            com.leedarson.base.utils.c r9 = com.leedarson.base.utils.c.h()     // Catch:{ JSONException -> 0x0494 }
            android.app.Activity r9 = r9.c()     // Catch:{ JSONException -> 0x0494 }
            java.lang.Class r9 = r9.getClass()     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r9 = r9.getSimpleName()     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r10 = "IpcMainActivity"
            boolean r9 = r9.equals(r10)     // Catch:{ JSONException -> 0x0494 }
            if (r9 == 0) goto L_0x029c
            com.leedarson.base.utils.c r9 = com.leedarson.base.utils.c.h()     // Catch:{ JSONException -> 0x0247 }
            android.app.Activity r9 = r9.c()     // Catch:{ JSONException -> 0x0247 }
            r9.finish()     // Catch:{ JSONException -> 0x0247 }
        L_0x029c:
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x0494 }
            r4.g = r9     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r9 = "params"
            boolean r9 = r7.has(r9)     // Catch:{ JSONException -> 0x0494 }
            if (r9 == 0) goto L_0x03ab
            android.content.Context r9 = r4.b     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r10 = "current_params"
            java.lang.String r12 = "params"
            java.lang.String r12 = r7.getString(r12)     // Catch:{ JSONException -> 0x0494 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r9, r10, r12)     // Catch:{ JSONException -> 0x0494 }
            java.lang.String r9 = "params"
            org.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ JSONException -> 0x0494 }
            boolean r10 = r9.has(r15)     // Catch:{ JSONException -> 0x0494 }
            if (r10 == 0) goto L_0x02c8
            java.lang.String r10 = r9.getString(r15)     // Catch:{ JSONException -> 0x0247 }
            r1 = r10
        L_0x02c8:
            java.lang.String r10 = "tab"
            boolean r10 = r9.has(r10)     // Catch:{ JSONException -> 0x0494 }
            if (r10 == 0) goto L_0x02d7
            java.lang.String r10 = "tab"
            java.lang.String r10 = r9.getString(r10)     // Catch:{ JSONException -> 0x0247 }
            goto L_0x02d9
        L_0x02d7:
            r10 = r38
        L_0x02d9:
            java.lang.String r12 = "needFinish"
            boolean r12 = r9.has(r12)     // Catch:{ JSONException -> 0x0494 }
            if (r12 == 0) goto L_0x02e8
            java.lang.String r12 = "needFinish"
            boolean r12 = r9.getBoolean(r12)     // Catch:{ JSONException -> 0x0247 }
            goto L_0x02ea
        L_0x02e8:
            r12 = r39
        L_0x02ea:
            r40 = r3
            java.lang.String r3 = "cloudPlayback"
            boolean r3 = r9.has(r3)     // Catch:{ JSONException -> 0x03a3 }
            if (r3 == 0) goto L_0x0302
            java.lang.String r3 = "cloudPlayback"
            boolean r3 = r9.getBoolean(r3)     // Catch:{ JSONException -> 0x02fc }
            r11 = r3
            goto L_0x0302
        L_0x02fc:
            r0 = move-exception
            r1 = r0
            r41 = r5
            goto L_0x04a3
        L_0x0302:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r3 = a     // Catch:{ JSONException -> 0x03a3 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ JSONException -> 0x03a3 }
        L_0x0308:
            boolean r19 = r3.hasNext()     // Catch:{ JSONException -> 0x03a3 }
            if (r19 == 0) goto L_0x0396
            java.lang.Object r19 = r3.next()     // Catch:{ JSONException -> 0x03a3 }
            com.leedarson.bean.IpcDeviceBean r19 = (com.leedarson.bean.IpcDeviceBean) r19     // Catch:{ JSONException -> 0x03a3 }
            r38 = r19
            r39 = r3
            timber.log.a$b r3 = timber.log.a.g(r2)     // Catch:{ JSONException -> 0x03a3 }
            r41 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0390 }
            r5.<init>()     // Catch:{ JSONException -> 0x0390 }
            r42 = r7
            java.lang.String r7 = "进入直播页: hasCode:"
            r5.append(r7)     // Catch:{ JSONException -> 0x048f }
            int r7 = r38.hashCode()     // Catch:{ JSONException -> 0x048f }
            r5.append(r7)     // Catch:{ JSONException -> 0x048f }
            java.lang.String r7 = ",id:"
            r5.append(r7)     // Catch:{ JSONException -> 0x048f }
            r7 = r38
            r38 = r10
            java.lang.String r10 = r7.id     // Catch:{ JSONException -> 0x048f }
            r5.append(r10)     // Catch:{ JSONException -> 0x048f }
            java.lang.String r10 = ",radarConfig:"
            r5.append(r10)     // Catch:{ JSONException -> 0x048f }
            boolean r10 = r7.hasPath()     // Catch:{ JSONException -> 0x048f }
            r5.append(r10)     // Catch:{ JSONException -> 0x048f }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x048f }
            r19 = r12
            r10 = 0
            java.lang.Object[] r12 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x048f }
            r3.m(r5, r12)     // Catch:{ JSONException -> 0x048f }
            java.lang.String r3 = r7.id     // Catch:{ JSONException -> 0x048f }
            boolean r3 = r1.equals(r3)     // Catch:{ JSONException -> 0x048f }
            if (r3 == 0) goto L_0x037b
            java.lang.String r3 = "isOwner"
            boolean r3 = r9.has(r3)     // Catch:{ JSONException -> 0x048f }
            if (r3 == 0) goto L_0x0371
            java.lang.String r3 = "isOwner"
            boolean r3 = r9.getBoolean(r3)     // Catch:{ JSONException -> 0x048f }
            r7.isOwner = r3     // Catch:{ JSONException -> 0x048f }
            r3 = 1
            goto L_0x0374
        L_0x0371:
            r3 = 1
            r7.isOwner = r3     // Catch:{ JSONException -> 0x048f }
        L_0x0374:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)     // Catch:{ JSONException -> 0x048f }
            r7.isCurrentDevice = r5     // Catch:{ JSONException -> 0x048f }
            goto L_0x0382
        L_0x037b:
            r3 = 0
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)     // Catch:{ JSONException -> 0x048f }
            r7.isCurrentDevice = r5     // Catch:{ JSONException -> 0x048f }
        L_0x0382:
            r7.cloudPlayback = r11     // Catch:{ JSONException -> 0x048f }
            r10 = r38
            r3 = r39
            r5 = r41
            r7 = r42
            r12 = r19
            goto L_0x0308
        L_0x0390:
            r0 = move-exception
            r42 = r7
            r1 = r0
            goto L_0x04a3
        L_0x0396:
            r41 = r5
            r42 = r7
            r38 = r10
            r19 = r12
            r9 = r38
            r10 = r19
            goto L_0x03b5
        L_0x03a3:
            r0 = move-exception
            r41 = r5
            r42 = r7
            r1 = r0
            goto L_0x04a3
        L_0x03ab:
            r40 = r3
            r41 = r5
            r42 = r7
        L_0x03b1:
            r9 = r38
            r10 = r39
        L_0x03b5:
            r3 = 0
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ JSONException -> 0x048f }
            r5.<init>()     // Catch:{ JSONException -> 0x048f }
            java.lang.String r7 = "IPC.live"
            boolean r7 = r7.equals(r8)     // Catch:{ JSONException -> 0x048f }
            if (r7 == 0) goto L_0x03f2
            if (r10 == 0) goto L_0x03cc
            com.leedarson.base.utils.c r2 = com.leedarson.base.utils.c.h()     // Catch:{ JSONException -> 0x048f }
            r2.g()     // Catch:{ JSONException -> 0x048f }
        L_0x03cc:
            android.content.Intent r2 = new android.content.Intent     // Catch:{ JSONException -> 0x048f }
            java.lang.Class<com.leedarson.newui.IpcLiveActivity> r7 = com.leedarson.newui.IpcLiveActivity.class
            r2.<init>(r6, r7)     // Catch:{ JSONException -> 0x048f }
            r3 = r2
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x048f }
            if (r2 != 0) goto L_0x0475
            java.lang.String r2 = "events"
            boolean r2 = r2.equals(r9)     // Catch:{ JSONException -> 0x048f }
            if (r2 == 0) goto L_0x0475
            java.lang.String r2 = "isEvent"
            r7 = 1
            r5.putBoolean(r2, r7)     // Catch:{ JSONException -> 0x048f }
            android.content.Intent r2 = new android.content.Intent     // Catch:{ JSONException -> 0x048f }
            java.lang.Class<com.leedarson.newui.EventsActivity> r7 = com.leedarson.newui.EventsActivity.class
            r2.<init>(r6, r7)     // Catch:{ JSONException -> 0x048f }
            r3 = r2
            goto L_0x0475
        L_0x03f2:
            java.lang.String r7 = "IPC.securityCameras"
            boolean r7 = r7.equals(r8)     // Catch:{ JSONException -> 0x048f }
            if (r7 == 0) goto L_0x0410
            android.content.Intent r2 = new android.content.Intent     // Catch:{ JSONException -> 0x048f }
            java.lang.Class<com.leedarson.newui.smartwidget.SecurityCamsActivity> r7 = com.leedarson.newui.smartwidget.SecurityCamsActivity.class
            r2.<init>(r6, r7)     // Catch:{ JSONException -> 0x048f }
            r3 = r2
            java.lang.String r2 = "cloudPlayback"
            r5.putBoolean(r2, r11)     // Catch:{ JSONException -> 0x048f }
            com.leedarson.base.utils.c r2 = com.leedarson.base.utils.c.h()     // Catch:{ JSONException -> 0x048f }
            r7 = 0
            r2.t(r7)     // Catch:{ JSONException -> 0x048f }
            goto L_0x0475
        L_0x0410:
            java.lang.String r7 = "IPC.radarMap"
            boolean r7 = r7.equals(r8)     // Catch:{ JSONException -> 0x048f }
            if (r7 == 0) goto L_0x0442
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x048f }
            r7.<init>()     // Catch:{ JSONException -> 0x048f }
            java.lang.String r12 = "radar deviceId:"
            r7.append(r12)     // Catch:{ JSONException -> 0x048f }
            r7.append(r1)     // Catch:{ JSONException -> 0x048f }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x048f }
            android.util.Log.i(r2, r7)     // Catch:{ JSONException -> 0x048f }
            com.alibaba.android.arouter.launcher.a r2 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x048f }
            java.lang.String r7 = "/location/radar"
            com.alibaba.android.arouter.facade.a r2 = r2.a(r7)     // Catch:{ JSONException -> 0x048f }
            com.alibaba.android.arouter.facade.a r2 = r2.T(r15, r1)     // Catch:{ JSONException -> 0x048f }
            r2.D(r6)     // Catch:{ JSONException -> 0x048f }
            r2 = 0
            r6.overridePendingTransition(r2, r2)     // Catch:{ JSONException -> 0x048f }
            return
        L_0x0442:
            java.lang.String r2 = "IPC.signalTest"
            boolean r2 = r2.equals(r8)     // Catch:{ JSONException -> 0x048f }
            if (r2 == 0) goto L_0x0475
            android.content.Intent r2 = new android.content.Intent     // Catch:{ JSONException -> 0x048f }
            java.lang.Class<com.leedarson.newui.signal_test.SignalTestActivity> r7 = com.leedarson.newui.signal_test.SignalTestActivity.class
            r2.<init>(r6, r7)     // Catch:{ JSONException -> 0x048f }
            r3 = r2
            r2 = 0
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ JSONException -> 0x048f }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ JSONException -> 0x048f }
        L_0x0459:
            boolean r12 = r7.hasNext()     // Catch:{ JSONException -> 0x048f }
            if (r12 == 0) goto L_0x0470
            java.lang.Object r12 = r7.next()     // Catch:{ JSONException -> 0x048f }
            com.leedarson.bean.IpcDeviceBean r12 = (com.leedarson.bean.IpcDeviceBean) r12     // Catch:{ JSONException -> 0x048f }
            java.lang.String r15 = r12.id     // Catch:{ JSONException -> 0x048f }
            boolean r15 = r15.equals(r1)     // Catch:{ JSONException -> 0x048f }
            if (r15 == 0) goto L_0x046f
            r2 = r12
            goto L_0x0470
        L_0x046f:
            goto L_0x0459
        L_0x0470:
            java.lang.String r7 = "current_device"
            r5.putParcelable(r7, r2)     // Catch:{ JSONException -> 0x048f }
        L_0x0475:
            if (r3 != 0) goto L_0x0478
            return
        L_0x0478:
            java.lang.String r2 = "devices"
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ JSONException -> 0x048f }
            r5.putParcelableArrayList(r2, r7)     // Catch:{ JSONException -> 0x048f }
            r3.putExtras(r5)     // Catch:{ JSONException -> 0x048f }
            r6.startActivity(r3)     // Catch:{ JSONException -> 0x048f }
            int r2 = com.leedarson.R$anim.ipc_slide_in_right     // Catch:{ JSONException -> 0x048f }
            int r7 = com.leedarson.R$anim.ipc_slide_out_left     // Catch:{ JSONException -> 0x048f }
            r6.overridePendingTransition(r2, r7)     // Catch:{ JSONException -> 0x048f }
            r7 = r42
            goto L_0x04a7
        L_0x048f:
            r0 = move-exception
            r7 = r42
            r1 = r0
            goto L_0x04a3
        L_0x0494:
            r0 = move-exception
            r40 = r3
            r41 = r5
            r42 = r7
            r1 = r0
            goto L_0x04a3
        L_0x049d:
            r0 = move-exception
            r40 = r3
            r41 = r5
            r1 = r0
        L_0x04a3:
            r1.printStackTrace()
        L_0x04a7:
            r10 = r40
            r19 = r41
            r3 = r4
            r34 = r6
            r35 = r13
            r33 = r14
            goto L_0x1a2a
        L_0x04b4:
            r8 = r9
            java.lang.String r9 = "Device"
            boolean r9 = r9.equals(r11)
            if (r9 == 0) goto L_0x0639
            int r2 = r13.hashCode()
            switch(r2) {
                case -737222600: goto L_0x04db;
                case 139509591: goto L_0x04d0;
                case 1470826922: goto L_0x04c5;
                default: goto L_0x04c4;
            }
        L_0x04c4:
            goto L_0x04e6
        L_0x04c5:
            java.lang.String r2 = "partialUpdate"
            boolean r2 = r13.equals(r2)
            if (r2 == 0) goto L_0x04c4
            r19 = 1
            goto L_0x04e8
        L_0x04d0:
            java.lang.String r2 = "thingUpdate"
            boolean r2 = r13.equals(r2)
            if (r2 == 0) goto L_0x04c4
            r19 = 2
            goto L_0x04e8
        L_0x04db:
            java.lang.String r2 = "fullUpdate"
            boolean r2 = r13.equals(r2)
            if (r2 == 0) goto L_0x04c4
            r19 = 0
            goto L_0x04e8
        L_0x04e6:
            r19 = r20
        L_0x04e8:
            switch(r19) {
                case 0: goto L_0x0623;
                case 1: goto L_0x0618;
                case 2: goto L_0x04f1;
                default: goto L_0x04eb;
            }
        L_0x04eb:
            r41 = r11
            r23 = r14
            goto L_0x062c
        L_0x04f1:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "ACTION_THINGUPDATE: "
            r2.append(r5)
            java.lang.String r5 = r7.toString()
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r8, r2)
            java.lang.String r2 = "payload"
            boolean r2 = r7.has(r2)     // Catch:{ Exception -> 0x060e }
            if (r2 == 0) goto L_0x0609
            java.lang.String r2 = "payload"
            org.json.JSONArray r2 = r7.getJSONArray(r2)     // Catch:{ Exception -> 0x060e }
            r5 = 0
        L_0x0518:
            int r6 = r2.length()     // Catch:{ Exception -> 0x060e }
            if (r5 >= r6) goto L_0x0602
            org.json.JSONObject r6 = r2.getJSONObject(r5)     // Catch:{ Exception -> 0x060e }
            if (r6 == 0) goto L_0x05f2
            boolean r9 = r6.has(r3)     // Catch:{ Exception -> 0x060e }
            if (r9 == 0) goto L_0x05f2
            android.content.Context r9 = r4.b     // Catch:{ Exception -> 0x060e }
            java.lang.String r15 = r6.getString(r3)     // Catch:{ Exception -> 0x060e }
            r38 = r2
            java.lang.String r2 = r6.toString()     // Catch:{ Exception -> 0x060e }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r9, r15, r2)     // Catch:{ Exception -> 0x060e }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r2 = a     // Catch:{ Exception -> 0x060e }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Exception -> 0x060e }
        L_0x053f:
            boolean r9 = r2.hasNext()     // Catch:{ Exception -> 0x060e }
            if (r9 == 0) goto L_0x05d4
            java.lang.Object r9 = r2.next()     // Catch:{ Exception -> 0x060e }
            com.leedarson.bean.IpcDeviceBean r9 = (com.leedarson.bean.IpcDeviceBean) r9     // Catch:{ Exception -> 0x060e }
            java.lang.String r15 = r9.productId     // Catch:{ Exception -> 0x060e }
            r39 = r2
            java.lang.String r2 = r6.getString(r3)     // Catch:{ Exception -> 0x060e }
            boolean r2 = r15.equals(r2)     // Catch:{ Exception -> 0x060e }
            if (r2 == 0) goto L_0x05ca
            com.google.gson.Gson r2 = new com.google.gson.Gson     // Catch:{ Exception -> 0x060e }
            r2.<init>()     // Catch:{ Exception -> 0x060e }
            java.lang.String r15 = r6.toString()     // Catch:{ Exception -> 0x060e }
            r41 = r11
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$i r11 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$i     // Catch:{ Exception -> 0x05c5 }
            r11.<init>()     // Catch:{ Exception -> 0x05c5 }
            java.lang.reflect.Type r11 = r11.getType()     // Catch:{ Exception -> 0x05c5 }
            java.lang.Object r11 = r2.fromJson((java.lang.String) r15, (java.lang.reflect.Type) r11)     // Catch:{ Exception -> 0x05c5 }
            com.leedarson.bean.PushBean r11 = (com.leedarson.bean.PushBean) r11     // Catch:{ Exception -> 0x05c5 }
            r9.pushBean = r11     // Catch:{ Exception -> 0x05c5 }
            timber.log.a$b r15 = timber.log.a.g(r8)     // Catch:{ Exception -> 0x05c5 }
            r39 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x05c5 }
            r2.<init>()     // Catch:{ Exception -> 0x05c5 }
            r23 = r14
            java.lang.String r14 = "thingUpdate hasCode:"
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            int r14 = r9.hashCode()     // Catch:{ Exception -> 0x05ef }
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r14 = ",deviceId:"
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r14 = r9.id     // Catch:{ Exception -> 0x05ef }
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r14 = ",radarConfig:"
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            boolean r14 = r9.hasPath()     // Catch:{ Exception -> 0x05ef }
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r14 = ",pushBean.radar:"
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            boolean r14 = r11.radarConfig     // Catch:{ Exception -> 0x05ef }
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r14 = ",pushBean.rangeConfig:"
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            int[] r14 = r11.radarRangeConfig     // Catch:{ Exception -> 0x05ef }
            r2.append(r14)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x05ef }
            r40 = r9
            r14 = 0
            java.lang.Object[] r9 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x05ef }
            r15.m(r2, r9)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05d8
        L_0x05c5:
            r0 = move-exception
            r23 = r14
            r1 = r0
            goto L_0x0614
        L_0x05ca:
            r40 = r9
            r41 = r11
            r23 = r14
            r2 = r39
            goto L_0x053f
        L_0x05d4:
            r41 = r11
            r23 = r14
        L_0x05d8:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x05ef }
            com.leedarson.serviceinterface.event.Event r9 = new com.leedarson.serviceinterface.event.Event     // Catch:{ Exception -> 0x05ef }
            java.lang.String r11 = "ThingUpdateEvent"
            java.lang.String r14 = r6.getString(r3)     // Catch:{ Exception -> 0x05ef }
            java.lang.String r15 = r6.toString()     // Catch:{ Exception -> 0x05ef }
            r9.<init>(r11, r1, r14, r15)     // Catch:{ Exception -> 0x05ef }
            r2.l(r9)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05f8
        L_0x05ef:
            r0 = move-exception
            r1 = r0
            goto L_0x0614
        L_0x05f2:
            r38 = r2
            r41 = r11
            r23 = r14
        L_0x05f8:
            int r5 = r5 + 1
            r2 = r38
            r11 = r41
            r14 = r23
            goto L_0x0518
        L_0x0602:
            r38 = r2
            r41 = r11
            r23 = r14
            goto L_0x060d
        L_0x0609:
            r41 = r11
            r23 = r14
        L_0x060d:
            goto L_0x062c
        L_0x060e:
            r0 = move-exception
            r41 = r11
            r23 = r14
            r1 = r0
        L_0x0614:
            r1.printStackTrace()
            goto L_0x062c
        L_0x0618:
            r41 = r11
            r23 = r14
            boolean r1 = r4.h(r7)
            if (r1 == 0) goto L_0x062c
            return
        L_0x0623:
            r41 = r11
            r23 = r14
            r1 = 1
            r4.a(r7, r1, r1)
        L_0x062c:
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r34 = r12
            r35 = r13
            r33 = r23
            goto L_0x1a28
        L_0x0639:
            r41 = r11
            r23 = r14
            int r3 = r13.hashCode()
            switch(r3) {
                case -2129411402: goto L_0x085e;
                case -1955228794: goto L_0x0853;
                case -1909077165: goto L_0x0848;
                case -1723436919: goto L_0x083d;
                case -1722768896: goto L_0x0832;
                case -1676188992: goto L_0x0827;
                case -1462359519: goto L_0x081c;
                case -1391995149: goto L_0x0811;
                case -1234022968: goto L_0x0806;
                case -1217487446: goto L_0x07fb;
                case -1202570762: goto L_0x07ef;
                case -934426579: goto L_0x07e3;
                case -806066213: goto L_0x07d7;
                case -802967076: goto L_0x07cb;
                case -759238347: goto L_0x07bf;
                case -416447130: goto L_0x07b4;
                case -196712072: goto L_0x07a8;
                case -56260442: goto L_0x079c;
                case 71895: goto L_0x0790;
                case 111185: goto L_0x0784;
                case 296577: goto L_0x0778;
                case 3363353: goto L_0x076d;
                case 3529469: goto L_0x0762;
                case 85668886: goto L_0x0756;
                case 106440182: goto L_0x074a;
                case 109757538: goto L_0x073f;
                case 246728622: goto L_0x0733;
                case 374196665: goto L_0x0728;
                case 374747017: goto L_0x071c;
                case 510657429: goto L_0x0710;
                case 530804061: goto L_0x0705;
                case 620286171: goto L_0x06f9;
                case 736755160: goto L_0x06ee;
                case 745779366: goto L_0x06e2;
                case 746556853: goto L_0x06d6;
                case 814354637: goto L_0x06ca;
                case 931034442: goto L_0x06be;
                case 951351530: goto L_0x06b2;
                case 990157655: goto L_0x06a6;
                case 1039374146: goto L_0x069a;
                case 1442020093: goto L_0x068e;
                case 1557370132: goto L_0x0682;
                case 1600309922: goto L_0x0676;
                case 1759101819: goto L_0x066a;
                case 1849900413: goto L_0x065e;
                case 1858452955: goto L_0x0652;
                case 1948599716: goto L_0x0646;
                default: goto L_0x0644;
            }
        L_0x0644:
            goto L_0x0869
        L_0x0646:
            java.lang.String r3 = "startMutiCamera"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 46
            goto L_0x086b
        L_0x0652:
            java.lang.String r3 = "toPlayLive"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 12
            goto L_0x086b
        L_0x065e:
            java.lang.String r3 = "talkToDevice"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 39
            goto L_0x086b
        L_0x066a:
            java.lang.String r3 = "destroyPlayer"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 23
            goto L_0x086b
        L_0x0676:
            java.lang.String r3 = "MP4Record"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 29
            goto L_0x086b
        L_0x0682:
            java.lang.String r3 = "destory"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 22
            goto L_0x086b
        L_0x068e:
            java.lang.String r3 = "createPlayer"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 25
            goto L_0x086b
        L_0x069a:
            java.lang.String r3 = "showQuality"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 36
            goto L_0x086b
        L_0x06a6:
            java.lang.String r3 = "reconnect"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 24
            goto L_0x086b
        L_0x06b2:
            java.lang.String r3 = "connect"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 34
            goto L_0x086b
        L_0x06be:
            java.lang.String r3 = "getRecordsWithDay"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 41
            goto L_0x086b
        L_0x06ca:
            java.lang.String r3 = "deleteEventlist"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 17
            goto L_0x086b
        L_0x06d6:
            java.lang.String r3 = "captureImage"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 27
            goto L_0x086b
        L_0x06e2:
            java.lang.String r3 = "thumbnailPhoto"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 45
            goto L_0x086b
        L_0x06ee:
            java.lang.String r3 = "startTalkBackServer"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 7
            goto L_0x086b
        L_0x06f9:
            java.lang.String r3 = "faceCollection"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 33
            goto L_0x086b
        L_0x0705:
            java.lang.String r3 = "hideQuality"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 4
            goto L_0x086b
        L_0x0710:
            java.lang.String r3 = "PTZCommand"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 32
            goto L_0x086b
        L_0x071c:
            java.lang.String r3 = "playControl"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 28
            goto L_0x086b
        L_0x0728:
            java.lang.String r3 = "themesColor"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 0
            goto L_0x086b
        L_0x0733:
            java.lang.String r3 = "openLocalAlbum"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 38
            goto L_0x086b
        L_0x073f:
            java.lang.String r3 = "start"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 1
            goto L_0x086b
        L_0x074a:
            java.lang.String r3 = "pause"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 13
            goto L_0x086b
        L_0x0756:
            java.lang.String r3 = "getSDRecordList"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 16
            goto L_0x086b
        L_0x0762:
            java.lang.String r3 = "show"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 3
            goto L_0x086b
        L_0x076d:
            java.lang.String r3 = "mute"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 5
            goto L_0x086b
        L_0x0778:
            java.lang.String r3 = "getTimeList"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 15
            goto L_0x086b
        L_0x0784:
            java.lang.String r3 = "pop"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 19
            goto L_0x086b
        L_0x0790:
            java.lang.String r3 = "HUD"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 31
            goto L_0x086b
        L_0x079c:
            java.lang.String r3 = "jumpToAlbum"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 37
            goto L_0x086b
        L_0x07a8:
            java.lang.String r3 = "stopTalkBackServer"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 8
            goto L_0x086b
        L_0x07b4:
            java.lang.String r3 = "screenshot"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 6
            goto L_0x086b
        L_0x07bf:
            java.lang.String r3 = "clearCache"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 44
            goto L_0x086b
        L_0x07cb:
            java.lang.String r3 = "deleteRecord"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 43
            goto L_0x086b
        L_0x07d7:
            java.lang.String r3 = "fullScreen"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 11
            goto L_0x086b
        L_0x07e3:
            java.lang.String r3 = "resume"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 14
            goto L_0x086b
        L_0x07ef:
            java.lang.String r3 = "getRecordsWithHour"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 42
            goto L_0x086b
        L_0x07fb:
            java.lang.String r3 = "hidden"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 2
            goto L_0x086b
        L_0x0806:
            java.lang.String r3 = "releasePlayer"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 30
            goto L_0x086b
        L_0x0811:
            java.lang.String r3 = "stopRecord"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 10
            goto L_0x086b
        L_0x081c:
            java.lang.String r3 = "alertView"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 20
            goto L_0x086b
        L_0x0827:
            java.lang.String r3 = "screenSwitch"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 40
            goto L_0x086b
        L_0x0832:
            java.lang.String r3 = "setPlayerSkin"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 35
            goto L_0x086b
        L_0x083d:
            java.lang.String r3 = "ToShowSD"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 18
            goto L_0x086b
        L_0x0848:
            java.lang.String r3 = "startRecord"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 9
            goto L_0x086b
        L_0x0853:
            java.lang.String r3 = "mqttStatusChange"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 21
            goto L_0x086b
        L_0x085e:
            java.lang.String r3 = "startPlay"
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0644
            r3 = 26
            goto L_0x086b
        L_0x0869:
            r3 = r20
        L_0x086b:
            java.lang.String r9 = "path"
            java.lang.String r11 = "data"
            java.lang.String r14 = "type"
            r38 = r13
            java.lang.String r13 = "-SD"
            r16 = r5
            java.lang.String r5 = "status"
            switch(r3) {
                case 0: goto L_0x18c5;
                case 1: goto L_0x18a9;
                case 2: goto L_0x1865;
                case 3: goto L_0x1849;
                case 4: goto L_0x182c;
                case 5: goto L_0x17a8;
                case 6: goto L_0x178c;
                case 7: goto L_0x1770;
                case 8: goto L_0x1754;
                case 9: goto L_0x1738;
                case 10: goto L_0x171c;
                case 11: goto L_0x16c2;
                case 12: goto L_0x16a6;
                case 13: goto L_0x1689;
                case 14: goto L_0x166c;
                case 15: goto L_0x165c;
                case 16: goto L_0x164c;
                case 17: goto L_0x163c;
                case 18: goto L_0x1620;
                case 19: goto L_0x1604;
                case 20: goto L_0x15d1;
                case 21: goto L_0x1590;
                case 22: goto L_0x1564;
                case 23: goto L_0x1564;
                case 24: goto L_0x1548;
                case 25: goto L_0x1406;
                case 26: goto L_0x13e9;
                case 27: goto L_0x13b3;
                case 28: goto L_0x137f;
                case 29: goto L_0x134a;
                case 30: goto L_0x1303;
                case 31: goto L_0x1276;
                case 32: goto L_0x1234;
                case 33: goto L_0x11b9;
                case 34: goto L_0x119a;
                case 35: goto L_0x1178;
                case 36: goto L_0x111e;
                case 37: goto L_0x10fd;
                case 38: goto L_0x10fd;
                case 39: goto L_0x108e;
                case 40: goto L_0x1021;
                case 41: goto L_0x0ec6;
                case 42: goto L_0x0d7f;
                case 43: goto L_0x0baa;
                case 44: goto L_0x0b0f;
                case 45: goto L_0x08b2;
                case 46: goto L_0x0889;
                default: goto L_0x087c;
            }
        L_0x087c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r34 = r12
            r33 = r23
            goto L_0x1a28
        L_0x0889:
            if (r7 == 0) goto L_0x08a5
            android.content.Intent r1 = new android.content.Intent
            java.lang.Class<com.leedarson.ui.MutiCameraActivity> r2 = com.leedarson.ui.MutiCameraActivity.class
            r1.<init>(r12, r2)
            r1.putExtra(r11, r10)
            r12.startActivity(r1)
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r34 = r12
            r33 = r23
            goto L_0x1a28
        L_0x08a5:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r34 = r12
            r33 = r23
            goto L_0x1a28
        L_0x08b2:
            if (r7 == 0) goto L_0x0af7
            java.lang.String r2 = r7.getString(r15)     // Catch:{ Exception -> 0x0ada }
            java.lang.String r3 = "command"
            java.lang.String r3 = r7.getString(r3)     // Catch:{ Exception -> 0x0ada }
            com.leedarson.manager.a r5 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0ada }
            java.lang.String r5 = r5.k(r2)     // Catch:{ Exception -> 0x0ada }
            com.leedarson.manager.a r6 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0ada }
            com.leedarson.smartcamera.sdk.a r6 = r6.m(r2)     // Catch:{ Exception -> 0x0ada }
            java.lang.String r13 = "get"
            boolean r13 = r3.equals(r13)     // Catch:{ Exception -> 0x0ada }
            if (r13 == 0) goto L_0x0a95
            java.lang.String r13 = r7.getString(r14)     // Catch:{ Exception -> 0x0ada }
            java.lang.String r14 = r7.getString(r9)     // Catch:{ Exception -> 0x0ada }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0ada }
            r15.<init>()     // Catch:{ Exception -> 0x0ada }
            r40 = r10
            android.content.Context r10 = r4.b     // Catch:{ Exception -> 0x0a8b }
            java.io.File r10 = r10.getFilesDir()     // Catch:{ Exception -> 0x0a8b }
            java.lang.String r10 = r10.getPath()     // Catch:{ Exception -> 0x0a8b }
            r15.append(r10)     // Catch:{ Exception -> 0x0a8b }
            java.lang.String r10 = "/web/"
            r15.append(r10)     // Catch:{ Exception -> 0x0a8b }
            java.lang.String r10 = "build"
            java.lang.String r10 = r14.replace(r10, r1)     // Catch:{ Exception -> 0x0a8b }
            r15.append(r10)     // Catch:{ Exception -> 0x0a8b }
            java.lang.String r10 = r15.toString()     // Catch:{ Exception -> 0x0a8b }
            java.io.File r15 = new java.io.File     // Catch:{ Exception -> 0x0a8b }
            r15.<init>(r10)     // Catch:{ Exception -> 0x0a8b }
            boolean r16 = r15.exists()     // Catch:{ Exception -> 0x0a8b }
            if (r16 != 0) goto L_0x091e
            r15.mkdirs()     // Catch:{ Exception -> 0x0913 }
            goto L_0x091e
        L_0x0913:
            r0 = move-exception
            r1 = r0
            r5 = r4
            r25 = r7
            r24 = r12
            r12 = r23
            goto L_0x0ae5
        L_0x091e:
            com.leedarson.smartcamera.utils.d.f(r10)     // Catch:{ Exception -> 0x0a8b }
            r39 = r10
            java.lang.String r10 = "times"
            org.json.JSONArray r10 = r7.getJSONArray(r10)     // Catch:{ Exception -> 0x0a8b }
            org.json.JSONArray r16 = new org.json.JSONArray     // Catch:{ Exception -> 0x0a8b }
            r16.<init>()     // Catch:{ Exception -> 0x0a8b }
            r42 = r16
            java.util.ArrayList r16 = new java.util.ArrayList     // Catch:{ Exception -> 0x0a8b }
            r16.<init>()     // Catch:{ Exception -> 0x0a8b }
            r20 = r16
            r16 = 0
            r24 = r12
            r12 = r16
        L_0x093d:
            r16 = r15
            int r15 = r10.length()     // Catch:{ Exception -> 0x0a82 }
            if (r12 >= r15) goto L_0x0a0c
            long r25 = r10.getLong(r12)     // Catch:{ Exception -> 0x0a03 }
            r27 = r25
            java.lang.String r15 = "getThumbnai"
            r22 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a03 }
            r10.<init>()     // Catch:{ Exception -> 0x0a03 }
            r25 = r7
            java.lang.String r7 = "request: "
            r10.append(r7)     // Catch:{ Exception -> 0x09fc }
            r7 = r2
            r26 = r3
            r2 = r27
            r10.append(r2)     // Catch:{ Exception -> 0x09fc }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x09fc }
            com.leedarson.smartcamera.utils.e.c(r15, r10)     // Catch:{ Exception -> 0x09fc }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x09fc }
            r10.<init>()     // Catch:{ Exception -> 0x09fc }
            r10.append(r2)     // Catch:{ Exception -> 0x09fc }
            r10.append(r1)     // Catch:{ Exception -> 0x09fc }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x09fc }
            boolean r10 = com.leedarson.smartcamera.utils.d.e(r13, r5, r10)     // Catch:{ Exception -> 0x09fc }
            if (r10 == 0) goto L_0x09c8
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Exception -> 0x09fc }
            r10.<init>()     // Catch:{ Exception -> 0x09fc }
            java.lang.String r15 = "time"
            r10.put((java.lang.String) r15, (long) r2)     // Catch:{ Exception -> 0x09fc }
            java.util.Locale r15 = java.util.Locale.US     // Catch:{ Exception -> 0x09fc }
            r27 = r1
            java.lang.String r1 = "%s-%s-%s.jpg"
            r28 = r4
            r29 = r7
            r4 = 3
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x09f4 }
            r4 = 0
            r7[r4] = r13     // Catch:{ Exception -> 0x09f4 }
            r4 = 1
            r7[r4] = r5     // Catch:{ Exception -> 0x09f4 }
            java.lang.Long r4 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x09f4 }
            r30 = r5
            r5 = 2
            r7[r5] = r4     // Catch:{ Exception -> 0x09f4 }
            java.lang.String r1 = java.lang.String.format(r15, r1, r7)     // Catch:{ Exception -> 0x09f4 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x09f4 }
            r4.<init>()     // Catch:{ Exception -> 0x09f4 }
            r4.append(r14)     // Catch:{ Exception -> 0x09f4 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x09f4 }
            r4.append(r7)     // Catch:{ Exception -> 0x09f4 }
            r4.append(r1)     // Catch:{ Exception -> 0x09f4 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x09f4 }
            r10.put((java.lang.String) r9, (java.lang.Object) r4)     // Catch:{ Exception -> 0x09f4 }
            r4 = r42
            r4.put((java.lang.Object) r10)     // Catch:{ Exception -> 0x09f4 }
            r7 = r20
            goto L_0x09dc
        L_0x09c8:
            r27 = r1
            r28 = r4
            r30 = r5
            r29 = r7
            r5 = 2
            r4 = r42
            java.lang.Long r1 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x09f4 }
            r7 = r20
            r7.add(r1)     // Catch:{ Exception -> 0x09f4 }
        L_0x09dc:
            int r12 = r12 + 1
            r42 = r4
            r20 = r7
            r15 = r16
            r10 = r22
            r7 = r25
            r3 = r26
            r1 = r27
            r4 = r28
            r2 = r29
            r5 = r30
            goto L_0x093d
        L_0x09f4:
            r0 = move-exception
            r1 = r0
            r12 = r23
            r5 = r28
            goto L_0x0ae5
        L_0x09fc:
            r0 = move-exception
            r1 = r0
            r5 = r4
            r12 = r23
            goto L_0x0ae5
        L_0x0a03:
            r0 = move-exception
            r25 = r7
            r1 = r0
            r5 = r4
            r12 = r23
            goto L_0x0ae5
        L_0x0a0c:
            r29 = r2
            r26 = r3
            r28 = r4
            r30 = r5
            r25 = r7
            r22 = r10
            r7 = r20
            r4 = r42
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a7a }
            r1.<init>()     // Catch:{ Exception -> 0x0a7a }
            java.lang.String r2 = "noCacheTimes: "
            r1.append(r2)     // Catch:{ Exception -> 0x0a7a }
            int r2 = r7.size()     // Catch:{ Exception -> 0x0a7a }
            r1.append(r2)     // Catch:{ Exception -> 0x0a7a }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0a7a }
            com.leedarson.smartcamera.utils.e.c(r8, r1)     // Catch:{ Exception -> 0x0a7a }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0a7a }
            r1.<init>()     // Catch:{ Exception -> 0x0a7a }
            java.lang.String r2 = "cachelist"
            r1.put((java.lang.String) r2, (java.lang.Object) r4)     // Catch:{ Exception -> 0x0a7a }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0a7a }
            r2.<init>()     // Catch:{ Exception -> 0x0a7a }
            java.lang.String r3 = "code"
            r5 = 200(0xc8, float:2.8E-43)
            r2.put((java.lang.String) r3, (int) r5)     // Catch:{ Exception -> 0x0a7a }
            r2.put((java.lang.String) r11, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0a7a }
            org.greenrobot.eventbus.c r3 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0a7a }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0a7a }
            java.lang.String r8 = r2.toString()     // Catch:{ Exception -> 0x0a7a }
            r12 = r23
            r5.<init>(r12, r8)     // Catch:{ Exception -> 0x0a74 }
            r3.l(r5)     // Catch:{ Exception -> 0x0a74 }
            if (r6 == 0) goto L_0x0a6e
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$q r3 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$q     // Catch:{ Exception -> 0x0a74 }
            r5 = r28
            r8 = r29
            r3.<init>(r14, r8, r13)     // Catch:{ Exception -> 0x0ad7 }
            r6.T0(r13, r7, r3)     // Catch:{ Exception -> 0x0ad7 }
            goto L_0x0a72
        L_0x0a6e:
            r5 = r28
            r8 = r29
        L_0x0a72:
            goto L_0x0ae8
        L_0x0a74:
            r0 = move-exception
            r5 = r28
            r1 = r0
            goto L_0x0ae5
        L_0x0a7a:
            r0 = move-exception
            r12 = r23
            r5 = r28
            r1 = r0
            goto L_0x0ae5
        L_0x0a82:
            r0 = move-exception
            r5 = r4
            r25 = r7
            r12 = r23
            r1 = r0
            goto L_0x0ae5
        L_0x0a8b:
            r0 = move-exception
            r5 = r4
            r25 = r7
            r24 = r12
            r12 = r23
            r1 = r0
            goto L_0x0ae5
        L_0x0a95:
            r8 = r2
            r26 = r3
            r30 = r5
            r25 = r7
            r40 = r10
            r24 = r12
            r12 = r23
            r5 = r4
            java.lang.String r1 = "disconnect"
            r2 = r26
            boolean r1 = r2.equals(r1)     // Catch:{ Exception -> 0x0ad7 }
            if (r1 == 0) goto L_0x0ae8
            if (r6 == 0) goto L_0x0ab2
            r6.D0()     // Catch:{ Exception -> 0x0ad7 }
        L_0x0ab2:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0ad7 }
            r1.<init>()     // Catch:{ Exception -> 0x0ad7 }
            java.lang.String r3 = "code"
            r4 = 200(0xc8, float:2.8E-43)
            r1.put((java.lang.String) r3, (int) r4)     // Catch:{ Exception -> 0x0ad7 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0ad7 }
            r3.<init>()     // Catch:{ Exception -> 0x0ad7 }
            r1.put((java.lang.String) r11, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0ad7 }
            org.greenrobot.eventbus.c r4 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0ad7 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r7 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0ad7 }
            java.lang.String r9 = r1.toString()     // Catch:{ Exception -> 0x0ad7 }
            r7.<init>(r12, r9)     // Catch:{ Exception -> 0x0ad7 }
            r4.l(r7)     // Catch:{ Exception -> 0x0ad7 }
            goto L_0x0ae8
        L_0x0ad7:
            r0 = move-exception
            r1 = r0
            goto L_0x0ae5
        L_0x0ada:
            r0 = move-exception
            r5 = r4
            r25 = r7
            r40 = r10
            r24 = r12
            r12 = r23
            r1 = r0
        L_0x0ae5:
            r1.printStackTrace()
        L_0x0ae8:
            r35 = r38
            r9 = r40
            r19 = r41
            r3 = r5
            r33 = r12
            r34 = r24
            r1 = r25
            goto L_0x1a28
        L_0x0af7:
            r5 = r4
            r25 = r7
            r40 = r10
            r24 = r12
            r12 = r23
            r35 = r38
            r9 = r40
            r19 = r41
            r3 = r5
            r33 = r12
            r34 = r24
            r1 = r25
            goto L_0x1a28
        L_0x0b0f:
            r27 = r1
            r5 = r4
            r25 = r7
            r40 = r10
            r24 = r12
            r12 = r23
            if (r25 == 0) goto L_0x0b9a
            java.lang.String r1 = "cachesArray"
            r3 = r25
            org.json.JSONArray r1 = r3.getJSONArray(r1)     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r2 = r3.getString(r9)     // Catch:{ Exception -> 0x0b82 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0b82 }
            r4.<init>()     // Catch:{ Exception -> 0x0b82 }
            android.content.Context r6 = r5.b     // Catch:{ Exception -> 0x0b82 }
            java.io.File r6 = r6.getFilesDir()     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r6 = r6.getPath()     // Catch:{ Exception -> 0x0b82 }
            r4.append(r6)     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r6 = "/web/"
            r4.append(r6)     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r6 = "build"
            r7 = r27
            java.lang.String r6 = r2.replace(r6, r7)     // Catch:{ Exception -> 0x0b82 }
            r4.append(r6)     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r6 = "extension"
            java.lang.String r6 = r3.getString(r6)     // Catch:{ Exception -> 0x0b82 }
            java.io.File r7 = new java.io.File     // Catch:{ Exception -> 0x0b82 }
            r7.<init>(r4)     // Catch:{ Exception -> 0x0b82 }
            java.io.File[] r8 = r7.listFiles()     // Catch:{ Exception -> 0x0b82 }
            r9 = 0
        L_0x0b5e:
            int r10 = r1.length()     // Catch:{ Exception -> 0x0b82 }
            if (r9 >= r10) goto L_0x0b81
            int r10 = r8.length     // Catch:{ Exception -> 0x0b82 }
            r11 = 0
        L_0x0b66:
            if (r11 >= r10) goto L_0x0b7e
            r13 = r8[r11]     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r14 = r13.getName()     // Catch:{ Exception -> 0x0b82 }
            java.lang.String r15 = r1.getString(r9)     // Catch:{ Exception -> 0x0b82 }
            boolean r14 = r14.contains(r15)     // Catch:{ Exception -> 0x0b82 }
            if (r14 == 0) goto L_0x0b7b
            r13.delete()     // Catch:{ Exception -> 0x0b82 }
        L_0x0b7b:
            int r11 = r11 + 1
            goto L_0x0b66
        L_0x0b7e:
            int r9 = r9 + 1
            goto L_0x0b5e
        L_0x0b81:
            goto L_0x0b8c
        L_0x0b82:
            r0 = move-exception
            r1 = r0
            goto L_0x0b89
        L_0x0b85:
            r0 = move-exception
            r3 = r25
            r1 = r0
        L_0x0b89:
            r1.printStackTrace()
        L_0x0b8c:
            r35 = r38
            r9 = r40
            r19 = r41
            r1 = r3
            r3 = r5
            r33 = r12
            r34 = r24
            goto L_0x1a28
        L_0x0b9a:
            r3 = r25
            r35 = r38
            r9 = r40
            r19 = r41
            r1 = r3
            r3 = r5
            r33 = r12
            r34 = r24
            goto L_0x1a28
        L_0x0baa:
            r5 = r4
            r3 = r7
            r40 = r10
            r24 = r12
            r12 = r23
            if (r3 == 0) goto L_0x0d6c
            java.lang.String r1 = r3.getString(r15)     // Catch:{ Exception -> 0x0d54 }
            java.lang.String r2 = "eventList"
            org.json.JSONArray r2 = r3.getJSONArray(r2)     // Catch:{ Exception -> 0x0d54 }
            int r4 = r2.length()     // Catch:{ Exception -> 0x0d54 }
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Exception -> 0x0d54 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Exception -> 0x0d54 }
            r6.<init>()     // Catch:{ Exception -> 0x0d54 }
            r7 = 0
        L_0x0bca:
            int r8 = r2.length()     // Catch:{ Exception -> 0x0d54 }
            if (r7 >= r8) goto L_0x0c0b
            java.lang.String r8 = r2.getString(r7)     // Catch:{ Exception -> 0x0bfb }
            r4[r7] = r8     // Catch:{ Exception -> 0x0bfb }
            java.text.SimpleDateFormat r8 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r9 = "yyyy-MM-dd HH:mm:ss"
            r8.<init>(r9)     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r9 = "GMT+0"
            java.util.TimeZone r9 = java.util.TimeZone.getTimeZone(r9)     // Catch:{ Exception -> 0x0bfb }
            r8.setTimeZone(r9)     // Catch:{ Exception -> 0x0bfb }
            r9 = r4[r7]     // Catch:{ Exception -> 0x0bfb }
            java.util.Date r9 = r8.parse(r9)     // Catch:{ Exception -> 0x0bfb }
            long r10 = r9.getTime()     // Catch:{ Exception -> 0x0bfb }
            java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x0bfb }
            r6.add(r10)     // Catch:{ Exception -> 0x0bfb }
            int r7 = r7 + 1
            goto L_0x0bca
        L_0x0bfb:
            r0 = move-exception
            r35 = r38
            r20 = r40
            r19 = r41
            r1 = r0
            r25 = r3
            r33 = r12
            r34 = r24
            goto L_0x0d62
        L_0x0c0b:
            int r7 = r3.getInt(r14)     // Catch:{ Exception -> 0x0d54 }
            java.lang.String r8 = "eventType"
            int r8 = r3.getInt(r8)     // Catch:{ Exception -> 0x0d54 }
            r14 = r8
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$o r8 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$o     // Catch:{ Exception -> 0x0d54 }
            r8.<init>(r1)     // Catch:{ Exception -> 0x0d54 }
            r15 = r8
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0d54 }
            boolean r8 = r8.o(r1)     // Catch:{ Exception -> 0x0d54 }
            if (r8 == 0) goto L_0x0d35
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r5.f     // Catch:{ Exception -> 0x0d54 }
            if (r8 == 0) goto L_0x0c43
            boolean r8 = r8.r1()     // Catch:{ Exception -> 0x0bfb }
            if (r8 != 0) goto L_0x0c31
            goto L_0x0c43
        L_0x0c31:
            r35 = r38
            r20 = r40
            r19 = r41
            r40 = r2
            r25 = r3
            r39 = r4
            r33 = r12
            r34 = r24
            goto L_0x0d25
        L_0x0c43:
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0d54 }
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r8.j(r1)     // Catch:{ Exception -> 0x0d54 }
            r16 = r8
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0d54 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0d54 }
            r9.<init>()     // Catch:{ Exception -> 0x0d54 }
            r9.append(r1)     // Catch:{ Exception -> 0x0d54 }
            r9.append(r13)     // Catch:{ Exception -> 0x0d54 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0d54 }
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r8.j(r9)     // Catch:{ Exception -> 0x0d54 }
            r5.f = r8     // Catch:{ Exception -> 0x0d54 }
            if (r16 == 0) goto L_0x0d15
            if (r8 != 0) goto L_0x0ca4
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = new com.leedarson.smartcamera.kvswebrtc.f0     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r26 = r16.S0()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r27 = r16.V0()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r28 = r16.l1()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r29 = r16.W0()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r30 = r16.f1()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r31 = "userId"
            r32 = 0
            r25 = r8
            r25.<init>(r26, r27, r28, r29, r30, r31, r32)     // Catch:{ Exception -> 0x0bfb }
            r5.f = r8     // Catch:{ Exception -> 0x0bfb }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0bfb }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0bfb }
            r9.<init>()     // Catch:{ Exception -> 0x0bfb }
            r9.append(r1)     // Catch:{ Exception -> 0x0bfb }
            r9.append(r13)     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0bfb }
            com.leedarson.smartcamera.kvswebrtc.f0 r10 = r5.f     // Catch:{ Exception -> 0x0bfb }
            r8.a(r9, r10)     // Catch:{ Exception -> 0x0bfb }
            goto L_0x0ced
        L_0x0ca4:
            java.lang.String r8 = r16.S0()     // Catch:{ Exception -> 0x0d54 }
            com.leedarson.smartcamera.kvswebrtc.f0 r9 = r5.f     // Catch:{ Exception -> 0x0d54 }
            java.lang.String r9 = r9.S0()     // Catch:{ Exception -> 0x0d54 }
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0d54 }
            if (r8 != 0) goto L_0x0ced
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = new com.leedarson.smartcamera.kvswebrtc.f0     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r26 = r16.S0()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r27 = r16.V0()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r28 = r16.l1()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r29 = r16.W0()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r30 = r16.f1()     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r31 = "userId"
            r32 = 0
            r25 = r8
            r25.<init>(r26, r27, r28, r29, r30, r31, r32)     // Catch:{ Exception -> 0x0bfb }
            r5.f = r8     // Catch:{ Exception -> 0x0bfb }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0bfb }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0bfb }
            r9.<init>()     // Catch:{ Exception -> 0x0bfb }
            r9.append(r1)     // Catch:{ Exception -> 0x0bfb }
            r9.append(r13)     // Catch:{ Exception -> 0x0bfb }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0bfb }
            com.leedarson.smartcamera.kvswebrtc.f0 r10 = r5.f     // Catch:{ Exception -> 0x0bfb }
            r8.a(r9, r10)     // Catch:{ Exception -> 0x0bfb }
        L_0x0ced:
            com.leedarson.smartcamera.kvswebrtc.f0 r13 = r5.f     // Catch:{ Exception -> 0x0d54 }
            android.content.Context r11 = r5.b     // Catch:{ Exception -> 0x0d54 }
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$p r10 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$p     // Catch:{ Exception -> 0x0d54 }
            r8 = r10
            r9 = r5
            r39 = r4
            r4 = r40
            r40 = r2
            r2 = r10
            r10 = r7
            r19 = r41
            r20 = r4
            r4 = r11
            r11 = r14
            r33 = r12
            r34 = r24
            r12 = r6
            r35 = r38
            r25 = r3
            r3 = r13
            r13 = r15
            r8.<init>(r10, r11, r12, r13)     // Catch:{ Exception -> 0x0d51 }
            r3.H0(r4, r2)     // Catch:{ Exception -> 0x0d51 }
            goto L_0x0d25
        L_0x0d15:
            r35 = r38
            r20 = r40
            r19 = r41
            r40 = r2
            r25 = r3
            r39 = r4
            r33 = r12
            r34 = r24
        L_0x0d25:
            com.leedarson.smartcamera.kvswebrtc.f0 r2 = r5.f     // Catch:{ Exception -> 0x0d51 }
            if (r2 == 0) goto L_0x0d65
            boolean r2 = r2.r1()     // Catch:{ Exception -> 0x0d51 }
            if (r2 == 0) goto L_0x0d65
            com.leedarson.smartcamera.kvswebrtc.f0 r2 = r5.f     // Catch:{ Exception -> 0x0d51 }
            r2.N0(r7, r14, r6, r15)     // Catch:{ Exception -> 0x0d51 }
            goto L_0x0d65
        L_0x0d35:
            r35 = r38
            r20 = r40
            r19 = r41
            r40 = r2
            r25 = r3
            r39 = r4
            r33 = r12
            r34 = r24
            com.leedarson.manager.a r2 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0d51 }
            com.leedarson.smartcamera.sdk.a r2 = r2.m(r1)     // Catch:{ Exception -> 0x0d51 }
            r2.B0(r7, r14, r6, r15)     // Catch:{ Exception -> 0x0d51 }
            goto L_0x0d65
        L_0x0d51:
            r0 = move-exception
            r1 = r0
            goto L_0x0d62
        L_0x0d54:
            r0 = move-exception
            r35 = r38
            r20 = r40
            r19 = r41
            r25 = r3
            r33 = r12
            r34 = r24
            r1 = r0
        L_0x0d62:
            r1.printStackTrace()
        L_0x0d65:
            r3 = r5
            r9 = r20
            r1 = r25
            goto L_0x1a28
        L_0x0d6c:
            r35 = r38
            r20 = r40
            r19 = r41
            r25 = r3
            r33 = r12
            r34 = r24
            r3 = r5
            r9 = r20
            r1 = r25
            goto L_0x1a28
        L_0x0d7f:
            r35 = r38
            r19 = r41
            r5 = r4
            r25 = r7
            r20 = r10
            r34 = r12
            r33 = r23
            if (r25 == 0) goto L_0x0ebf
            r1 = r25
            java.lang.String r2 = r1.getString(r15)     // Catch:{ Exception -> 0x0eb1 }
            java.lang.String r3 = "startTime"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x0eb1 }
            java.lang.String r4 = "endTime"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ Exception -> 0x0eb1 }
            int r6 = r1.getInt(r14)     // Catch:{ Exception -> 0x0eb1 }
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$m r7 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$m     // Catch:{ Exception -> 0x0eb1 }
            r7.<init>(r2)     // Catch:{ Exception -> 0x0eb1 }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0eb1 }
            boolean r8 = r8.o(r2)     // Catch:{ Exception -> 0x0eb1 }
            if (r8 == 0) goto L_0x0e9e
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r5.f     // Catch:{ Exception -> 0x0eb1 }
            if (r8 == 0) goto L_0x0dc8
            boolean r8 = r8.r1()     // Catch:{ Exception -> 0x0dc2 }
            if (r8 != 0) goto L_0x0dbe
            goto L_0x0dc8
        L_0x0dbe:
            r25 = r1
            goto L_0x0e8e
        L_0x0dc2:
            r0 = move-exception
            r25 = r1
            r1 = r0
            goto L_0x0eb5
        L_0x0dc8:
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0eb1 }
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r8.j(r2)     // Catch:{ Exception -> 0x0eb1 }
            r14 = r8
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0eb1 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0eb1 }
            r9.<init>()     // Catch:{ Exception -> 0x0eb1 }
            r9.append(r2)     // Catch:{ Exception -> 0x0eb1 }
            r9.append(r13)     // Catch:{ Exception -> 0x0eb1 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0eb1 }
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r8.j(r9)     // Catch:{ Exception -> 0x0eb1 }
            r5.f = r8     // Catch:{ Exception -> 0x0eb1 }
            if (r14 == 0) goto L_0x0e8a
            if (r8 != 0) goto L_0x0e28
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = new com.leedarson.smartcamera.kvswebrtc.f0     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r22 = r14.S0()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r23 = r14.V0()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r24 = r14.l1()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r25 = r14.W0()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r26 = r14.f1()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r27 = "userId"
            r28 = 0
            r21 = r8
            r21.<init>(r22, r23, r24, r25, r26, r27, r28)     // Catch:{ Exception -> 0x0dc2 }
            r5.f = r8     // Catch:{ Exception -> 0x0dc2 }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0dc2 }
            r9.<init>()     // Catch:{ Exception -> 0x0dc2 }
            r9.append(r2)     // Catch:{ Exception -> 0x0dc2 }
            r9.append(r13)     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0dc2 }
            com.leedarson.smartcamera.kvswebrtc.f0 r10 = r5.f     // Catch:{ Exception -> 0x0dc2 }
            r8.a(r9, r10)     // Catch:{ Exception -> 0x0dc2 }
            goto L_0x0e71
        L_0x0e28:
            java.lang.String r8 = r14.S0()     // Catch:{ Exception -> 0x0eb1 }
            com.leedarson.smartcamera.kvswebrtc.f0 r9 = r5.f     // Catch:{ Exception -> 0x0eb1 }
            java.lang.String r9 = r9.S0()     // Catch:{ Exception -> 0x0eb1 }
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x0eb1 }
            if (r8 != 0) goto L_0x0e71
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = new com.leedarson.smartcamera.kvswebrtc.f0     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r22 = r14.S0()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r23 = r14.V0()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r24 = r14.l1()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r25 = r14.W0()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r26 = r14.f1()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r27 = "userId"
            r28 = 0
            r21 = r8
            r21.<init>(r22, r23, r24, r25, r26, r27, r28)     // Catch:{ Exception -> 0x0dc2 }
            r5.f = r8     // Catch:{ Exception -> 0x0dc2 }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0dc2 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0dc2 }
            r9.<init>()     // Catch:{ Exception -> 0x0dc2 }
            r9.append(r2)     // Catch:{ Exception -> 0x0dc2 }
            r9.append(r13)     // Catch:{ Exception -> 0x0dc2 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0dc2 }
            com.leedarson.smartcamera.kvswebrtc.f0 r10 = r5.f     // Catch:{ Exception -> 0x0dc2 }
            r8.a(r9, r10)     // Catch:{ Exception -> 0x0dc2 }
        L_0x0e71:
            com.leedarson.smartcamera.kvswebrtc.f0 r15 = r5.f     // Catch:{ Exception -> 0x0eb1 }
            android.content.Context r13 = r5.b     // Catch:{ Exception -> 0x0eb1 }
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$n r12 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$n     // Catch:{ Exception -> 0x0eb1 }
            r8 = r12
            r9 = r5
            r10 = r3
            r11 = r4
            r38 = r14
            r14 = r12
            r12 = r6
            r25 = r1
            r1 = r13
            r13 = r7
            r8.<init>(r10, r11, r12, r13)     // Catch:{ Exception -> 0x0eae }
            r15.H0(r1, r14)     // Catch:{ Exception -> 0x0eae }
            goto L_0x0e8e
        L_0x0e8a:
            r25 = r1
            r38 = r14
        L_0x0e8e:
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r5.f     // Catch:{ Exception -> 0x0eae }
            if (r1 == 0) goto L_0x0eb8
            boolean r1 = r1.r1()     // Catch:{ Exception -> 0x0eae }
            if (r1 == 0) goto L_0x0eb8
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r5.f     // Catch:{ Exception -> 0x0eae }
            r1.h1(r3, r4, r6, r7)     // Catch:{ Exception -> 0x0eae }
            goto L_0x0eb8
        L_0x0e9e:
            r25 = r1
            com.leedarson.manager.a r1 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0eae }
            com.leedarson.smartcamera.sdk.a r1 = r1.m(r2)     // Catch:{ Exception -> 0x0eae }
            if (r1 == 0) goto L_0x0eb8
            r1.N0(r3, r4, r6, r7)     // Catch:{ Exception -> 0x0eae }
            goto L_0x0eb8
        L_0x0eae:
            r0 = move-exception
            r1 = r0
            goto L_0x0eb5
        L_0x0eb1:
            r0 = move-exception
            r25 = r1
            r1 = r0
        L_0x0eb5:
            r1.printStackTrace()
        L_0x0eb8:
            r3 = r5
            r9 = r20
            r1 = r25
            goto L_0x1a28
        L_0x0ebf:
            r3 = r5
            r9 = r20
            r1 = r25
            goto L_0x1a28
        L_0x0ec6:
            r35 = r38
            r19 = r41
            r5 = r4
            r25 = r7
            r20 = r10
            r34 = r12
            r33 = r23
            if (r25 == 0) goto L_0x1017
            r1 = r25
            java.lang.String r2 = r1.getString(r15)     // Catch:{ Exception -> 0x100a }
            java.lang.String r3 = "startTime"
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Exception -> 0x100a }
            java.lang.String r4 = "endTime"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ Exception -> 0x100a }
            int r6 = r1.getInt(r14)     // Catch:{ Exception -> 0x100a }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x100a }
            r7.<init>()     // Catch:{ Exception -> 0x100a }
            java.lang.String r9 = "GETRECORDS_DAY: "
            r7.append(r9)     // Catch:{ Exception -> 0x100a }
            com.leedarson.manager.a r9 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x100a }
            boolean r9 = r9.o(r2)     // Catch:{ Exception -> 0x100a }
            r7.append(r9)     // Catch:{ Exception -> 0x100a }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x100a }
            android.util.Log.e(r8, r7)     // Catch:{ Exception -> 0x100a }
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$k r7 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$k     // Catch:{ Exception -> 0x100a }
            r7.<init>(r2)     // Catch:{ Exception -> 0x100a }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x100a }
            boolean r8 = r8.o(r2)     // Catch:{ Exception -> 0x100a }
            if (r8 == 0) goto L_0x0ff7
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r5.f     // Catch:{ Exception -> 0x100a }
            if (r8 == 0) goto L_0x0f30
            boolean r8 = r8.r1()     // Catch:{ Exception -> 0x0f2a }
            if (r8 != 0) goto L_0x0f21
            goto L_0x0f30
        L_0x0f21:
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r5.f     // Catch:{ Exception -> 0x0f2a }
            r8.k1(r3, r4, r6, r7)     // Catch:{ Exception -> 0x0f2a }
            r28 = r5
            goto L_0x1011
        L_0x0f2a:
            r0 = move-exception
            r2 = r0
            r28 = r5
            goto L_0x100e
        L_0x0f30:
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x100a }
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r8.j(r2)     // Catch:{ Exception -> 0x100a }
            r14 = r8
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x100a }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x100a }
            r9.<init>()     // Catch:{ Exception -> 0x100a }
            r9.append(r2)     // Catch:{ Exception -> 0x100a }
            r9.append(r13)     // Catch:{ Exception -> 0x100a }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x100a }
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = r8.j(r9)     // Catch:{ Exception -> 0x100a }
            r5.f = r8     // Catch:{ Exception -> 0x100a }
            if (r14 == 0) goto L_0x0ff2
            if (r8 != 0) goto L_0x0f90
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = new com.leedarson.smartcamera.kvswebrtc.f0     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r22 = r14.S0()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r23 = r14.V0()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r24 = r14.l1()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r25 = r14.W0()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r26 = r14.f1()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r27 = "userId"
            r28 = 0
            r21 = r8
            r21.<init>(r22, r23, r24, r25, r26, r27, r28)     // Catch:{ Exception -> 0x0f2a }
            r5.f = r8     // Catch:{ Exception -> 0x0f2a }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0f2a }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0f2a }
            r9.<init>()     // Catch:{ Exception -> 0x0f2a }
            r9.append(r2)     // Catch:{ Exception -> 0x0f2a }
            r9.append(r13)     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0f2a }
            com.leedarson.smartcamera.kvswebrtc.f0 r10 = r5.f     // Catch:{ Exception -> 0x0f2a }
            r8.a(r9, r10)     // Catch:{ Exception -> 0x0f2a }
            goto L_0x0fd9
        L_0x0f90:
            java.lang.String r8 = r14.S0()     // Catch:{ Exception -> 0x100a }
            com.leedarson.smartcamera.kvswebrtc.f0 r9 = r5.f     // Catch:{ Exception -> 0x100a }
            java.lang.String r9 = r9.S0()     // Catch:{ Exception -> 0x100a }
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x100a }
            if (r8 != 0) goto L_0x0fd9
            com.leedarson.smartcamera.kvswebrtc.f0 r8 = new com.leedarson.smartcamera.kvswebrtc.f0     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r22 = r14.S0()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r23 = r14.V0()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r24 = r14.l1()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r25 = r14.W0()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r26 = r14.f1()     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r27 = "userId"
            r28 = 0
            r21 = r8
            r21.<init>(r22, r23, r24, r25, r26, r27, r28)     // Catch:{ Exception -> 0x0f2a }
            r5.f = r8     // Catch:{ Exception -> 0x0f2a }
            com.leedarson.manager.a r8 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0f2a }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0f2a }
            r9.<init>()     // Catch:{ Exception -> 0x0f2a }
            r9.append(r2)     // Catch:{ Exception -> 0x0f2a }
            r9.append(r13)     // Catch:{ Exception -> 0x0f2a }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0f2a }
            com.leedarson.smartcamera.kvswebrtc.f0 r10 = r5.f     // Catch:{ Exception -> 0x0f2a }
            r8.a(r9, r10)     // Catch:{ Exception -> 0x0f2a }
        L_0x0fd9:
            com.leedarson.smartcamera.kvswebrtc.f0 r15 = r5.f     // Catch:{ Exception -> 0x100a }
            android.content.Context r13 = r5.b     // Catch:{ Exception -> 0x100a }
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$l r12 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$l     // Catch:{ Exception -> 0x100a }
            r8 = r12
            r9 = r5
            r10 = r3
            r11 = r4
            r28 = r5
            r5 = r12
            r12 = r6
            r38 = r14
            r14 = r13
            r13 = r7
            r8.<init>(r10, r11, r12, r13)     // Catch:{ Exception -> 0x1007 }
            r15.H0(r14, r5)     // Catch:{ Exception -> 0x1007 }
            goto L_0x0ff6
        L_0x0ff2:
            r28 = r5
            r38 = r14
        L_0x0ff6:
            goto L_0x1011
        L_0x0ff7:
            r28 = r5
            com.leedarson.manager.a r5 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x1007 }
            com.leedarson.smartcamera.sdk.a r5 = r5.m(r2)     // Catch:{ Exception -> 0x1007 }
            if (r5 == 0) goto L_0x1011
            r5.Q0(r3, r4, r6, r7)     // Catch:{ Exception -> 0x1007 }
            goto L_0x1011
        L_0x1007:
            r0 = move-exception
            r2 = r0
            goto L_0x100e
        L_0x100a:
            r0 = move-exception
            r28 = r5
            r2 = r0
        L_0x100e:
            r2.printStackTrace()
        L_0x1011:
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x1017:
            r28 = r5
            r1 = r25
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x1021:
            r35 = r38
            r19 = r41
            r28 = r4
            r2 = r5
            r1 = r7
            r20 = r10
            r34 = r12
            r33 = r23
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ACTION_SCREENSWITCH: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "vary"
            com.leedarson.log.f.b(r4, r3)
            if (r1 == 0) goto L_0x1088
            boolean r3 = r1.has(r2)
            if (r3 == 0) goto L_0x1088
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x107d }
            r3 = 1
            if (r2 != r3) goto L_0x1070
            java.lang.String r2 = "width"
            double r2 = r1.getDouble(r2)     // Catch:{ JSONException -> 0x107d }
            float r2 = (float) r2     // Catch:{ JSONException -> 0x107d }
            java.lang.String r3 = "height"
            double r3 = r1.getDouble(r3)     // Catch:{ JSONException -> 0x107d }
            float r3 = (float) r3     // Catch:{ JSONException -> 0x107d }
            org.greenrobot.eventbus.c r4 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x107d }
            com.leedarson.event.FullScreenEvent r5 = new com.leedarson.event.FullScreenEvent     // Catch:{ JSONException -> 0x107d }
            r6 = 0
            r5.<init>(r6, r2, r3)     // Catch:{ JSONException -> 0x107d }
            r4.l(r5)     // Catch:{ JSONException -> 0x107d }
            goto L_0x1082
        L_0x1070:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x107d }
            com.leedarson.serviceinterface.event.ToPortraitEvent r3 = new com.leedarson.serviceinterface.event.ToPortraitEvent     // Catch:{ JSONException -> 0x107d }
            r3.<init>()     // Catch:{ JSONException -> 0x107d }
            r2.l(r3)     // Catch:{ JSONException -> 0x107d }
            goto L_0x1082
        L_0x107d:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x1082:
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x1088:
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x108e:
            r35 = r38
            r19 = r41
            r28 = r4
            r2 = r5
            r1 = r7
            r20 = r10
            r34 = r12
            r33 = r23
            if (r1 == 0) goto L_0x10f5
            boolean r3 = r1.has(r2)
            if (r3 == 0) goto L_0x10f5
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x10e6 }
            r3 = 1
            if (r2 != r3) goto L_0x10c7
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x10e6 }
            com.leedarson.event.StartTalkEvent r3 = new com.leedarson.event.StartTalkEvent     // Catch:{ JSONException -> 0x10e6 }
            r4 = r33
            r3.<init>(r4)     // Catch:{ JSONException -> 0x10e3 }
            r2.l(r3)     // Catch:{ JSONException -> 0x10e3 }
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x10e3 }
            com.leedarson.serviceinterface.event.PlayerTouchEvent r3 = new com.leedarson.serviceinterface.event.PlayerTouchEvent     // Catch:{ JSONException -> 0x10e3 }
            r5 = 0
            r3.<init>(r5)     // Catch:{ JSONException -> 0x10e3 }
            r2.l(r3)     // Catch:{ JSONException -> 0x10e3 }
            goto L_0x10ed
        L_0x10c7:
            r4 = r33
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x10e3 }
            com.leedarson.event.StopTalkEvent r3 = new com.leedarson.event.StopTalkEvent     // Catch:{ JSONException -> 0x10e3 }
            r3.<init>()     // Catch:{ JSONException -> 0x10e3 }
            r2.l(r3)     // Catch:{ JSONException -> 0x10e3 }
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x10e3 }
            com.leedarson.serviceinterface.event.PlayerTouchEvent r3 = new com.leedarson.serviceinterface.event.PlayerTouchEvent     // Catch:{ JSONException -> 0x10e3 }
            r5 = 1
            r3.<init>(r5)     // Catch:{ JSONException -> 0x10e3 }
            r2.l(r3)     // Catch:{ JSONException -> 0x10e3 }
            goto L_0x10ed
        L_0x10e3:
            r0 = move-exception
            r2 = r0
            goto L_0x10ea
        L_0x10e6:
            r0 = move-exception
            r4 = r33
            r2 = r0
        L_0x10ea:
            r2.printStackTrace()
        L_0x10ed:
            r33 = r4
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x10f5:
            r4 = r33
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x10fd:
            r35 = r38
            r19 = r41
            r28 = r4
            r1 = r7
            r20 = r10
            r34 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.OpenAlbumEvent r3 = new com.leedarson.event.OpenAlbumEvent
            r3.<init>()
            r2.l(r3)
            r33 = r4
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x111e:
            r35 = r38
            r19 = r41
            r28 = r4
            r2 = r5
            r1 = r7
            r20 = r10
            r34 = r12
            r4 = r23
            if (r1 == 0) goto L_0x1163
            boolean r3 = r1.has(r2)
            if (r3 == 0) goto L_0x1163
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x1156 }
            r3 = 1
            if (r2 != r3) goto L_0x1148
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1156 }
            com.leedarson.event.ShowQualityEvent r5 = new com.leedarson.event.ShowQualityEvent     // Catch:{ JSONException -> 0x1156 }
            r5.<init>(r3)     // Catch:{ JSONException -> 0x1156 }
            r2.l(r5)     // Catch:{ JSONException -> 0x1156 }
            goto L_0x115b
        L_0x1148:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1156 }
            com.leedarson.event.ShowQualityEvent r3 = new com.leedarson.event.ShowQualityEvent     // Catch:{ JSONException -> 0x1156 }
            r5 = 0
            r3.<init>(r5)     // Catch:{ JSONException -> 0x1156 }
            r2.l(r3)     // Catch:{ JSONException -> 0x1156 }
            goto L_0x115b
        L_0x1156:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x115b:
            r33 = r4
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x1163:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.ShowQualityEvent r3 = new com.leedarson.event.ShowQualityEvent
            r5 = 1
            r3.<init>(r5)
            r2.l(r3)
            r33 = r4
            r9 = r20
            r3 = r28
            goto L_0x1a28
        L_0x1178:
            r35 = r38
            r19 = r41
            r28 = r4
            r1 = r7
            r20 = r10
            r34 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.SetPlayerConfigEvent r3 = new com.leedarson.event.SetPlayerConfigEvent
            r6 = r20
            r3.<init>(r6)
            r2.l(r3)
            r33 = r4
            r9 = r6
            r3 = r28
            goto L_0x1a28
        L_0x119a:
            r35 = r38
            r19 = r41
            r28 = r4
            r1 = r7
            r6 = r10
            r34 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.TutkConnectEvent r3 = new com.leedarson.event.TutkConnectEvent
            r3.<init>(r4, r6)
            r2.l(r3)
            r33 = r4
            r9 = r6
            r3 = r28
            goto L_0x1a28
        L_0x11b9:
            r35 = r38
            r19 = r41
            r28 = r4
            r6 = r10
            r34 = r12
            r4 = r23
            r36 = r7
            r7 = r1
            r1 = r36
            boolean r2 = r1.has(r11)     // Catch:{ Exception -> 0x1224 }
            if (r2 == 0) goto L_0x11d9
            java.lang.String r2 = r1.optString(r11)     // Catch:{ Exception -> 0x11d4 }
            goto L_0x11da
        L_0x11d4:
            r0 = move-exception
            r2 = r0
            r12 = r34
            goto L_0x1228
        L_0x11d9:
            r2 = r7
        L_0x11da:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x1224 }
            if (r3 != 0) goto L_0x1219
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x1224 }
            r3.<init>((java.lang.String) r2)     // Catch:{ Exception -> 0x1224 }
            java.lang.String r5 = "collectionType"
            boolean r5 = r3.has(r5)     // Catch:{ Exception -> 0x1224 }
            if (r5 == 0) goto L_0x1216
            java.lang.String r5 = "collectionType"
            int r5 = r3.optInt(r5)     // Catch:{ Exception -> 0x1224 }
            r7 = 1
            if (r5 != r7) goto L_0x1213
            com.alibaba.android.arouter.launcher.a r5 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x1224 }
            java.lang.String r7 = "/face/check"
            com.alibaba.android.arouter.facade.a r5 = r5.a(r7)     // Catch:{ Exception -> 0x1224 }
            com.alibaba.android.arouter.facade.a r5 = r5.T(r11, r2)     // Catch:{ Exception -> 0x1224 }
            java.lang.String r7 = "callbackKey"
            com.alibaba.android.arouter.facade.a r5 = r5.T(r7, r4)     // Catch:{ Exception -> 0x1224 }
            r12 = r34
            r5.D(r12)     // Catch:{ Exception -> 0x1210 }
            goto L_0x121b
        L_0x1210:
            r0 = move-exception
            r2 = r0
            goto L_0x1228
        L_0x1213:
            r12 = r34
            goto L_0x121b
        L_0x1216:
            r12 = r34
            goto L_0x121b
        L_0x1219:
            r12 = r34
        L_0x121b:
            r33 = r4
            r9 = r6
            r34 = r12
            r3 = r28
            goto L_0x1a28
        L_0x1224:
            r0 = move-exception
            r12 = r34
            r2 = r0
        L_0x1228:
            r2.printStackTrace()
            r33 = r4
            r9 = r6
            r34 = r12
            r3 = r28
            goto L_0x1a28
        L_0x1234:
            r35 = r38
            r19 = r41
            r28 = r4
            r1 = r7
            r6 = r10
            r4 = r23
            if (r1 == 0) goto L_0x126d
            java.lang.String r2 = "speed"
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x125f }
            java.lang.String r3 = "command"
            int r3 = r1.getInt(r3)     // Catch:{ JSONException -> 0x125f }
            java.lang.String r5 = r1.getString(r15)     // Catch:{ JSONException -> 0x125f }
            com.leedarson.manager.a r7 = com.leedarson.manager.a.i()     // Catch:{ JSONException -> 0x125f }
            com.leedarson.smartcamera.sdk.a r7 = r7.m(r5)     // Catch:{ JSONException -> 0x125f }
            if (r7 == 0) goto L_0x126d
            r7.d1(r3, r2)     // Catch:{ JSONException -> 0x125f }
            goto L_0x126d
        L_0x125f:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r33 = r4
            r9 = r6
            r34 = r12
            r3 = r28
            goto L_0x1a28
        L_0x126d:
            r33 = r4
            r9 = r6
            r34 = r12
            r3 = r28
            goto L_0x1a28
        L_0x1276:
            r35 = r38
            r19 = r41
            r3 = r4
            r2 = r5
            r6 = r10
            r4 = r23
            r5 = 2
            r36 = r7
            r7 = r1
            r1 = r36
            r8 = 1
            int[] r13 = new int[r8]
            r8 = 0
            r13[r8] = r8
            if (r1 == 0) goto L_0x12fb
            r8 = 0
            int r9 = r1.getInt(r14)     // Catch:{ JSONException -> 0x12ee }
            r14 = r9
            r8 = 0
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x12ee }
            java.lang.String r8 = "desc"
            boolean r8 = r1.has(r8)     // Catch:{ JSONException -> 0x12ee }
            if (r8 == 0) goto L_0x12ad
            java.lang.String r8 = "desc"
            java.lang.String r8 = r1.getString(r8)     // Catch:{ JSONException -> 0x12a9 }
            r7 = r8
            goto L_0x12ad
        L_0x12a9:
            r0 = move-exception
            r2 = r0
            r15 = r12
            goto L_0x12f1
        L_0x12ad:
            if (r14 != 0) goto L_0x12c1
            if (r12 == 0) goto L_0x12bf
            r10 = r2
            r11 = r1
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$j r5 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$j     // Catch:{ JSONException -> 0x12ee }
            r8 = r5
            r9 = r3
            r15 = r12
            r8.<init>(r10, r11, r12, r13)     // Catch:{ JSONException -> 0x12cc }
            r15.runOnUiThread(r5)     // Catch:{ JSONException -> 0x12cc }
            goto L_0x12fc
        L_0x12bf:
            r15 = r12
            goto L_0x12fc
        L_0x12c1:
            r15 = r12
            r8 = 1
            if (r14 != r8) goto L_0x12cf
            android.content.Context r5 = r3.b     // Catch:{ JSONException -> 0x12cc }
            r8 = 0
            r3.D(r15, r5, r8, r7)     // Catch:{ JSONException -> 0x12cc }
            goto L_0x12fc
        L_0x12cc:
            r0 = move-exception
            r2 = r0
            goto L_0x12f1
        L_0x12cf:
            r8 = 3
            if (r14 != r8) goto L_0x12fc
            android.content.Context r8 = r3.b     // Catch:{ JSONException -> 0x12cc }
            android.content.res.Resources r8 = r8.getResources()     // Catch:{ JSONException -> 0x12cc }
            android.content.res.Configuration r8 = r8.getConfiguration()     // Catch:{ JSONException -> 0x12cc }
            int r8 = r8.orientation     // Catch:{ JSONException -> 0x12cc }
            if (r8 != r5) goto L_0x12e7
            android.content.Context r5 = r3.b     // Catch:{ JSONException -> 0x12cc }
            r8 = 0
            r3.D(r15, r5, r8, r7)     // Catch:{ JSONException -> 0x12cc }
            goto L_0x12fc
        L_0x12e7:
            android.content.Context r5 = r3.b     // Catch:{ JSONException -> 0x12cc }
            r8 = 1
            r3.D(r15, r5, r8, r7)     // Catch:{ JSONException -> 0x12cc }
            goto L_0x12fc
        L_0x12ee:
            r0 = move-exception
            r15 = r12
            r2 = r0
        L_0x12f1:
            r2.printStackTrace()
            r33 = r4
            r9 = r6
            r34 = r15
            goto L_0x1a28
        L_0x12fb:
            r15 = r12
        L_0x12fc:
            r33 = r4
            r9 = r6
            r34 = r15
            goto L_0x1a28
        L_0x1303:
            r35 = r38
            r19 = r41
            r3 = r4
            r6 = r10
            r5 = r12
            r4 = r23
            r36 = r7
            r7 = r1
            r1 = r36
            if (r1 == 0) goto L_0x1343
            boolean r2 = r1.has(r15)     // Catch:{ JSONException -> 0x1337 }
            if (r2 == 0) goto L_0x132a
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1337 }
            com.leedarson.serviceinterface.event.ClearFragmentEvent r7 = new com.leedarson.serviceinterface.event.ClearFragmentEvent     // Catch:{ JSONException -> 0x1337 }
            java.lang.String r8 = r1.getString(r15)     // Catch:{ JSONException -> 0x1337 }
            r7.<init>((java.lang.String) r8)     // Catch:{ JSONException -> 0x1337 }
            r2.l(r7)     // Catch:{ JSONException -> 0x1337 }
            goto L_0x133c
        L_0x132a:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1337 }
            com.leedarson.serviceinterface.event.ClearFragmentEvent r8 = new com.leedarson.serviceinterface.event.ClearFragmentEvent     // Catch:{ JSONException -> 0x1337 }
            r8.<init>((java.lang.String) r7)     // Catch:{ JSONException -> 0x1337 }
            r2.l(r8)     // Catch:{ JSONException -> 0x1337 }
            goto L_0x133c
        L_0x1337:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x133c:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x1343:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x134a:
            r35 = r38
            r19 = r41
            r3 = r4
            r2 = r5
            r1 = r7
            r6 = r10
            r5 = r12
            r4 = r23
            if (r1 == 0) goto L_0x1378
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x136c }
            com.leedarson.event.CloudRecordEvent r8 = new com.leedarson.event.CloudRecordEvent     // Catch:{ JSONException -> 0x136c }
            java.lang.String r9 = r1.getString(r9)     // Catch:{ JSONException -> 0x136c }
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x136c }
            r8.<init>(r9, r2)     // Catch:{ JSONException -> 0x136c }
            r7.l(r8)     // Catch:{ JSONException -> 0x136c }
            goto L_0x1371
        L_0x136c:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x1371:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x1378:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x137f:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r6 = r10
            r5 = r12
            r4 = r23
            if (r1 == 0) goto L_0x13ac
            r2 = 0
            java.lang.String r7 = "controlCode"
            int r7 = r1.getInt(r7)     // Catch:{ Exception -> 0x13a0 }
            r2 = r7
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x13a0 }
            com.leedarson.serviceinterface.event.PlayControlEvent r8 = new com.leedarson.serviceinterface.event.PlayControlEvent     // Catch:{ Exception -> 0x13a0 }
            r8.<init>(r2)     // Catch:{ Exception -> 0x13a0 }
            r7.l(r8)     // Catch:{ Exception -> 0x13a0 }
            goto L_0x13ac
        L_0x13a0:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x13ac:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x13b3:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r6 = r10
            r5 = r12
            r4 = r23
            if (r1 == 0) goto L_0x13e2
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x13d6 }
            com.leedarson.event.CloudCaptureEvent r7 = new com.leedarson.event.CloudCaptureEvent     // Catch:{ JSONException -> 0x13d6 }
            java.lang.String r8 = r1.getString(r9)     // Catch:{ JSONException -> 0x13d6 }
            java.lang.String r9 = "saveStatus"
            int r9 = r1.getInt(r9)     // Catch:{ JSONException -> 0x13d6 }
            r7.<init>(r8, r9)     // Catch:{ JSONException -> 0x13d6 }
            r2.l(r7)     // Catch:{ JSONException -> 0x13d6 }
            goto L_0x13db
        L_0x13d6:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x13db:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x13e2:
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x13e9:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r6 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.StartPlayEvent r7 = new com.leedarson.event.StartPlayEvent
            r7.<init>(r6)
            r2.l(r7)
            r33 = r4
            r34 = r5
            r9 = r6
            goto L_0x1a28
        L_0x1406:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            if (r1 == 0) goto L_0x1542
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x1537 }
            r2.<init>()     // Catch:{ Exception -> 0x1537 }
            boolean r7 = r1.has(r6)     // Catch:{ Exception -> 0x1537 }
            if (r7 == 0) goto L_0x1466
            org.json.JSONObject r7 = r1.getJSONObject(r6)     // Catch:{ Exception -> 0x1537 }
            java.lang.String r8 = "themeColor"
            java.lang.String r7 = r7.getString(r8)     // Catch:{ Exception -> 0x1537 }
            android.content.Context r8 = r3.b     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = "themeColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r8, r10, r7)     // Catch:{ Exception -> 0x1537 }
            org.json.JSONObject r8 = r1.getJSONObject(r6)     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = "tabbarColor"
            boolean r8 = r8.has(r10)     // Catch:{ Exception -> 0x1537 }
            if (r8 == 0) goto L_0x144b
            org.json.JSONObject r8 = r1.getJSONObject(r6)     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = "tabbarColor"
            java.lang.String r8 = r8.getString(r10)     // Catch:{ Exception -> 0x1537 }
            android.content.Context r10 = r3.b     // Catch:{ Exception -> 0x1537 }
            java.lang.String r11 = "tabbarColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r10, r11, r8)     // Catch:{ Exception -> 0x1537 }
        L_0x144b:
            org.json.JSONObject r8 = r1.getJSONObject(r6)     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = "previewUrl"
            boolean r8 = r8.has(r10)     // Catch:{ Exception -> 0x1537 }
            if (r8 == 0) goto L_0x1466
            org.json.JSONObject r6 = r1.getJSONObject(r6)     // Catch:{ Exception -> 0x1537 }
            java.lang.String r8 = "previewUrl"
            java.lang.String r6 = r6.getString(r8)     // Catch:{ Exception -> 0x1537 }
            java.lang.String r8 = "previewUrl"
            r2.put((java.lang.String) r8, (java.lang.Object) r6)     // Catch:{ Exception -> 0x1537 }
        L_0x1466:
            java.lang.String r6 = "frame"
            boolean r6 = r1.has(r6)     // Catch:{ Exception -> 0x1537 }
            if (r6 == 0) goto L_0x1496
            java.lang.String r6 = "frame"
            org.json.JSONArray r6 = r1.getJSONArray(r6)     // Catch:{ Exception -> 0x1537 }
            android.content.Context r7 = r3.b     // Catch:{ Exception -> 0x1491 }
            java.lang.String r8 = "playerCloudY_PER"
            r10 = 1
            java.lang.String r10 = r6.getString(r10)     // Catch:{ Exception -> 0x1491 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r7, r8, r10)     // Catch:{ Exception -> 0x1491 }
            android.content.Context r7 = r3.b     // Catch:{ Exception -> 0x1491 }
            java.lang.String r8 = "WH_PER"
            r10 = 3
            java.lang.String r10 = r6.getString(r10)     // Catch:{ Exception -> 0x1491 }
            float r10 = java.lang.Float.parseFloat(r10)     // Catch:{ Exception -> 0x1491 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefFloat(r7, r8, r10)     // Catch:{ Exception -> 0x1491 }
            goto L_0x1496
        L_0x1491:
            r0 = move-exception
            r7 = r0
            r7.printStackTrace()     // Catch:{ Exception -> 0x1537 }
        L_0x1496:
            r6 = 0
            boolean r7 = r1.has(r14)     // Catch:{ Exception -> 0x1537 }
            if (r7 == 0) goto L_0x14a2
            int r7 = r1.getInt(r14)     // Catch:{ Exception -> 0x1537 }
            r6 = r7
        L_0x14a2:
            java.lang.String r7 = "callbackKey"
            r2.put((java.lang.String) r7, (java.lang.Object) r4)     // Catch:{ Exception -> 0x1537 }
            r2.put((java.lang.String) r14, (int) r6)     // Catch:{ Exception -> 0x1537 }
            boolean r7 = r1.has(r15)     // Catch:{ Exception -> 0x1537 }
            if (r7 == 0) goto L_0x14b7
            java.lang.Object r7 = r1.get(r15)     // Catch:{ Exception -> 0x1537 }
            r2.put((java.lang.String) r15, (java.lang.Object) r7)     // Catch:{ Exception -> 0x1537 }
        L_0x14b7:
            java.lang.String r7 = "audioCodec"
            boolean r7 = r1.has(r7)     // Catch:{ Exception -> 0x1537 }
            if (r7 == 0) goto L_0x14df
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Exception -> 0x14da }
            java.lang.String r8 = "audioCodec"
            java.lang.String r8 = r1.getString(r8)     // Catch:{ Exception -> 0x14da }
            r7.<init>((java.lang.String) r8)     // Catch:{ Exception -> 0x14da }
            java.lang.String r8 = "rate"
            java.lang.String r7 = r7.getString(r8)     // Catch:{ Exception -> 0x14da }
            int r8 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x14da }
            java.lang.String r10 = "audioRate"
            r2.put((java.lang.String) r10, (int) r8)     // Catch:{ Exception -> 0x14da }
            goto L_0x14df
        L_0x14da:
            r0 = move-exception
            r7 = r0
            r7.printStackTrace()     // Catch:{ Exception -> 0x1537 }
        L_0x14df:
            java.lang.String r7 = "skinType"
            switch(r6) {
                case 0: goto L_0x1519;
                case 1: goto L_0x14f6;
                case 2: goto L_0x14e5;
                default: goto L_0x14e4;
            }
        L_0x14e4:
            goto L_0x1542
        L_0x14e5:
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x1537 }
            com.leedarson.serviceinterface.event.AddPlaySDEvent r8 = new com.leedarson.serviceinterface.event.AddPlaySDEvent     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = r2.toString()     // Catch:{ Exception -> 0x1537 }
            r8.<init>(r10)     // Catch:{ Exception -> 0x1537 }
            r7.l(r8)     // Catch:{ Exception -> 0x1537 }
            goto L_0x1542
        L_0x14f6:
            boolean r8 = r1.has(r7)     // Catch:{ Exception -> 0x1537 }
            if (r8 == 0) goto L_0x1504
            int r8 = r1.getInt(r7)     // Catch:{ Exception -> 0x1537 }
            r2.put((java.lang.String) r7, (int) r8)     // Catch:{ Exception -> 0x1537 }
            goto L_0x1508
        L_0x1504:
            r8 = 0
            r2.put((java.lang.String) r7, (int) r8)     // Catch:{ Exception -> 0x1537 }
        L_0x1508:
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x1537 }
            com.leedarson.serviceinterface.event.AddPlayCloudEvent r8 = new com.leedarson.serviceinterface.event.AddPlayCloudEvent     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = r2.toString()     // Catch:{ Exception -> 0x1537 }
            r8.<init>(r10)     // Catch:{ Exception -> 0x1537 }
            r7.l(r8)     // Catch:{ Exception -> 0x1537 }
            goto L_0x1542
        L_0x1519:
            boolean r8 = r1.has(r7)     // Catch:{ Exception -> 0x1537 }
            if (r8 == 0) goto L_0x1526
            int r8 = r1.getInt(r7)     // Catch:{ Exception -> 0x1537 }
            r2.put((java.lang.String) r7, (int) r8)     // Catch:{ Exception -> 0x1537 }
        L_0x1526:
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x1537 }
            com.leedarson.serviceinterface.event.AddLiveEvent r8 = new com.leedarson.serviceinterface.event.AddLiveEvent     // Catch:{ Exception -> 0x1537 }
            java.lang.String r10 = r2.toString()     // Catch:{ Exception -> 0x1537 }
            r8.<init>(r10)     // Catch:{ Exception -> 0x1537 }
            r7.l(r8)     // Catch:{ Exception -> 0x1537 }
            goto L_0x1542
        L_0x1537:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1542:
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1548:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.ReconnectEvent r6 = new com.leedarson.serviceinterface.event.ReconnectEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1564:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.DestoryIpcEvent r6 = new com.leedarson.serviceinterface.event.DestoryIpcEvent
            r6.<init>()
            r2.l(r6)
            com.leedarson.base.views.g r2 = r3.c
            if (r2 == 0) goto L_0x158a
            r2.dismiss()
            r2 = 0
            r3.c = r2
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x158a:
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1590:
            r35 = r38
            r19 = r41
            r3 = r4
            r2 = r5
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            r6 = 0
            r7 = 0
            if (r1 == 0) goto L_0x15cb
            java.lang.String r8 = "standbyStatus"
            int r8 = r1.getInt(r8)     // Catch:{ Exception -> 0x15c0 }
            r7 = r8
            boolean r8 = r1.has(r2)     // Catch:{ Exception -> 0x15c0 }
            if (r8 == 0) goto L_0x15b1
            boolean r15 = r1.getBoolean(r2)     // Catch:{ Exception -> 0x15c0 }
            goto L_0x15b2
        L_0x15b1:
            r15 = 0
        L_0x15b2:
            r6 = r15
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x15c0 }
            com.leedarson.event.IpcMqttStatusChangeEvent r8 = new com.leedarson.event.IpcMqttStatusChangeEvent     // Catch:{ Exception -> 0x15c0 }
            r8.<init>(r6, r7)     // Catch:{ Exception -> 0x15c0 }
            r2.l(r8)     // Catch:{ Exception -> 0x15c0 }
            goto L_0x15c5
        L_0x15c0:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x15c5:
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x15cb:
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x15d1:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "alert:"
            r2.append(r6)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]
            timber.log.a.c(r2, r6)
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.AlertEvent r6 = new com.leedarson.serviceinterface.event.AlertEvent
            r6.<init>(r9, r4)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1604:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.DestoryIpcEvent r6 = new com.leedarson.serviceinterface.event.DestoryIpcEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1620:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.AddPlaySDEvent r6 = new com.leedarson.serviceinterface.event.AddPlaySDEvent
            r6.<init>(r9)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x163c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x164c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x165c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x166c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.RecordPlayStatusEvent r6 = new com.leedarson.event.RecordPlayStatusEvent
            r7 = 1
            r6.<init>(r7)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1689:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.RecordPlayStatusEvent r6 = new com.leedarson.event.RecordPlayStatusEvent
            r7 = 0
            r6.<init>(r7)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x16a6:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.PlayLiveEvent r6 = new com.leedarson.event.PlayLiveEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x16c2:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            r2 = -1
            r6 = 0
            r7 = 0
            if (r1 == 0) goto L_0x16f4
            java.lang.String r10 = "angle"
            boolean r10 = r1.has(r10)
            if (r10 == 0) goto L_0x16f4
            java.lang.String r10 = "width"
            int r10 = r1.getInt(r10)     // Catch:{ JSONException -> 0x16ef }
            r6 = r10
            java.lang.String r10 = "height"
            int r10 = r1.getInt(r10)     // Catch:{ JSONException -> 0x16ef }
            r7 = r10
            java.lang.String r10 = "angle"
            int r10 = r1.getInt(r10)     // Catch:{ JSONException -> 0x16ef }
            r2 = r10
            goto L_0x16f4
        L_0x16ef:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
        L_0x16f4:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "ACTION_FULLSCREEN: "
            r10.append(r11)
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            com.leedarson.log.f.b(r8, r10)
            org.greenrobot.eventbus.c r8 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.FullScreenEvent r10 = new com.leedarson.event.FullScreenEvent
            float r11 = (float) r6
            float r12 = (float) r7
            r10.<init>(r2, r11, r12)
            r8.l(r10)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x171c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.StopRecordEvent r6 = new com.leedarson.event.StopRecordEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1738:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.StartRecordEvent r6 = new com.leedarson.event.StartRecordEvent
            r6.<init>(r4)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1754:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.StopTalkEvent r6 = new com.leedarson.event.StopTalkEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1770:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.StartTalkEvent r6 = new com.leedarson.event.StartTalkEvent
            r6.<init>(r4)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x178c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.ScreenShotEvent r6 = new com.leedarson.event.ScreenShotEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x17a8:
            r35 = r38
            r19 = r41
            r3 = r4
            r2 = r5
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            r6 = 0
            if (r1 == 0) goto L_0x17e6
            java.lang.String r7 = "state"
            boolean r7 = r1.has(r7)
            if (r7 == 0) goto L_0x17e6
            java.lang.String r7 = "state"
            boolean r7 = r1.getBoolean(r7)     // Catch:{ JSONException -> 0x17e1 }
            r6 = r7
            timber.log.a$b r7 = timber.log.a.g(r8)     // Catch:{ JSONException -> 0x17e1 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x17e1 }
            r10.<init>()     // Catch:{ JSONException -> 0x17e1 }
            java.lang.String r11 = "isMute:"
            r10.append(r11)     // Catch:{ JSONException -> 0x17e1 }
            r10.append(r6)     // Catch:{ JSONException -> 0x17e1 }
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x17e1 }
            r11 = 0
            java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch:{ JSONException -> 0x17e1 }
            r7.h(r10, r12)     // Catch:{ JSONException -> 0x17e1 }
            goto L_0x17e6
        L_0x17e1:
            r0 = move-exception
            r7 = r0
            r7.printStackTrace()
        L_0x17e6:
            if (r1 == 0) goto L_0x181a
            boolean r7 = r1.has(r2)
            if (r7 == 0) goto L_0x181a
            int r2 = r1.getInt(r2)     // Catch:{ JSONException -> 0x1815 }
            r7 = 1
            if (r2 != r7) goto L_0x17f7
            r14 = 1
            goto L_0x17f8
        L_0x17f7:
            r14 = 0
        L_0x17f8:
            r6 = r14
            timber.log.a$b r7 = timber.log.a.g(r8)     // Catch:{ JSONException -> 0x1815 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x1815 }
            r8.<init>()     // Catch:{ JSONException -> 0x1815 }
            java.lang.String r10 = "isMute:"
            r8.append(r10)     // Catch:{ JSONException -> 0x1815 }
            r8.append(r6)     // Catch:{ JSONException -> 0x1815 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x1815 }
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x1815 }
            r7.h(r8, r10)     // Catch:{ JSONException -> 0x1815 }
            goto L_0x181a
        L_0x1815:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x181a:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.SetMuteEvent r7 = new com.leedarson.event.SetMuteEvent
            r7.<init>(r6)
            r2.l(r7)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x182c:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.event.ShowQualityEvent r6 = new com.leedarson.event.ShowQualityEvent
            r7 = 0
            r6.<init>(r7)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1849:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.ShowFragmentEvent r6 = new com.leedarson.serviceinterface.event.ShowFragmentEvent
            r6.<init>()
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x1865:
            r35 = r38
            r19 = r41
            r3 = r4
            r2 = r5
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            if (r1 == 0) goto L_0x18a3
            java.lang.String r2 = r1.getString(r2)     // Catch:{ JSONException -> 0x1898 }
            java.lang.String r6 = "finish"
            boolean r2 = r2.equals(r6)     // Catch:{ JSONException -> 0x1898 }
            if (r2 == 0) goto L_0x188b
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1898 }
            com.leedarson.serviceinterface.event.DestoryIpcEvent r6 = new com.leedarson.serviceinterface.event.DestoryIpcEvent     // Catch:{ JSONException -> 0x1898 }
            r6.<init>()     // Catch:{ JSONException -> 0x1898 }
            r2.l(r6)     // Catch:{ JSONException -> 0x1898 }
            goto L_0x18a3
        L_0x188b:
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1898 }
            com.leedarson.serviceinterface.event.HideFragmentEvent r6 = new com.leedarson.serviceinterface.event.HideFragmentEvent     // Catch:{ JSONException -> 0x1898 }
            r6.<init>()     // Catch:{ JSONException -> 0x1898 }
            r2.l(r6)     // Catch:{ JSONException -> 0x1898 }
            goto L_0x18a3
        L_0x1898:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x18a3:
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x18a9:
            r35 = r38
            r19 = r41
            r3 = r4
            r1 = r7
            r9 = r10
            r5 = r12
            r4 = r23
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.AddLiveEvent r6 = new com.leedarson.serviceinterface.event.AddLiveEvent
            r6.<init>(r9)
            r2.l(r6)
            r33 = r4
            r34 = r5
            goto L_0x1a28
        L_0x18c5:
            r35 = r38
            r19 = r41
            r3 = r4
            r9 = r10
            r5 = r12
            r4 = r23
            r36 = r7
            r7 = r1
            r1 = r36
            if (r1 == 0) goto L_0x1a24
            java.lang.String r6 = "json"
            boolean r10 = r1.has(r6)
            if (r10 == 0) goto L_0x1a24
            org.json.JSONObject r10 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x1a1a }
            java.lang.String r11 = "themes"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x1a1a }
            if (r10 == 0) goto L_0x1903
            org.json.JSONObject r10 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x18fb }
            java.lang.String r11 = "themes"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x18fb }
            android.content.Context r11 = r3.b     // Catch:{ JSONException -> 0x18fb }
            java.lang.String r12 = "themes"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r11, r12, r10)     // Catch:{ JSONException -> 0x18fb }
            goto L_0x1903
        L_0x18fb:
            r0 = move-exception
            r2 = r0
            r33 = r4
            r34 = r5
            goto L_0x1a20
        L_0x1903:
            org.json.JSONObject r10 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x1a1a }
            java.lang.String r11 = "color"
            boolean r10 = r10.has(r11)     // Catch:{ JSONException -> 0x1a1a }
            if (r10 == 0) goto L_0x1920
            org.json.JSONObject r10 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x18fb }
            java.lang.String r11 = "color"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ JSONException -> 0x18fb }
            android.content.Context r11 = r3.b     // Catch:{ JSONException -> 0x18fb }
            java.lang.String r12 = "themesColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r11, r12, r10)     // Catch:{ JSONException -> 0x18fb }
        L_0x1920:
            org.json.JSONObject r10 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x1a1a }
            boolean r10 = r10.has(r2)     // Catch:{ JSONException -> 0x1a1a }
            if (r10 == 0) goto L_0x1938
            android.content.Context r10 = r3.b     // Catch:{ JSONException -> 0x18fb }
            org.json.JSONObject r11 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x18fb }
            java.lang.String r11 = r11.getString(r2)     // Catch:{ JSONException -> 0x18fb }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r10, r2, r11)     // Catch:{ JSONException -> 0x18fb }
            goto L_0x193d
        L_0x1938:
            android.content.Context r10 = r3.b     // Catch:{ JSONException -> 0x1a1a }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r10, r2, r7)     // Catch:{ JSONException -> 0x1a1a }
        L_0x193d:
            org.json.JSONObject r2 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x1a1a }
            r10 = r16
            boolean r2 = r2.has(r10)     // Catch:{ JSONException -> 0x1a1a }
            if (r2 == 0) goto L_0x19ed
            android.content.Context r2 = r3.b     // Catch:{ JSONException -> 0x1a1a }
            org.json.JSONObject r7 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x1a1a }
            java.lang.String r7 = r7.getString(r10)     // Catch:{ JSONException -> 0x1a1a }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r10, r7)     // Catch:{ JSONException -> 0x1a1a }
            org.json.JSONObject r2 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x1a1a }
            org.json.JSONArray r2 = r2.getJSONArray(r10)     // Catch:{ JSONException -> 0x1a1a }
            r6 = 0
            r10 = 0
            r12 = 1
            java.lang.String r12 = r2.getString(r12)     // Catch:{ Exception -> 0x1977 }
            double r12 = java.lang.Double.parseDouble(r12)     // Catch:{ Exception -> 0x1977 }
            r6 = r12
            r12 = 3
            java.lang.String r12 = r2.getString(r12)     // Catch:{ Exception -> 0x1977 }
            double r12 = java.lang.Double.parseDouble(r12)     // Catch:{ Exception -> 0x1977 }
            r10 = r12
            goto L_0x197c
        L_0x1977:
            r0 = move-exception
            r12 = r0
            r12.printStackTrace()     // Catch:{ JSONException -> 0x1a1a }
        L_0x197c:
            android.view.WindowManager r12 = r5.getWindowManager()     // Catch:{ JSONException -> 0x1a1a }
            android.util.DisplayMetrics r13 = new android.util.DisplayMetrics     // Catch:{ JSONException -> 0x1a1a }
            r13.<init>()     // Catch:{ JSONException -> 0x1a1a }
            android.view.Display r14 = r12.getDefaultDisplay()     // Catch:{ JSONException -> 0x1a1a }
            r14.getMetrics(r13)     // Catch:{ JSONException -> 0x1a1a }
            int r14 = r13.heightPixels     // Catch:{ JSONException -> 0x1a1a }
            int r15 = com.leedarson.serviceinterface.utils.PubUtils.getStatusBarHeight(r5)     // Catch:{ JSONException -> 0x1a1a }
            int r14 = r14 + r15
            double r14 = (double) r14     // Catch:{ JSONException -> 0x1a1a }
            r38 = r2
            int r2 = r13.widthPixels     // Catch:{ JSONException -> 0x1a1a }
            r39 = r12
            r40 = r13
            double r12 = (double) r2     // Catch:{ JSONException -> 0x1a1a }
            timber.log.a$b r2 = timber.log.a.g(r8)     // Catch:{ JSONException -> 0x1a1a }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x1a1a }
            r8.<init>()     // Catch:{ JSONException -> 0x1a1a }
            r8.append(r12)     // Catch:{ JSONException -> 0x1a1a }
            r34 = r5
            java.lang.String r5 = "==="
            r8.append(r5)     // Catch:{ JSONException -> 0x19e8 }
            r8.append(r14)     // Catch:{ JSONException -> 0x19e8 }
            java.lang.String r5 = "widthPixels: "
            r8.append(r5)     // Catch:{ JSONException -> 0x19e8 }
            r16 = 4625196817309499392(0x4030000000000000, double:16.0)
            double r16 = r12 / r16
            r23 = 4621256167635550208(0x4022000000000000, double:9.0)
            r33 = r4
            double r4 = r16 * r23
            r8.append(r4)     // Catch:{ JSONException -> 0x1a17 }
            java.lang.String r4 = "=="
            r8.append(r4)     // Catch:{ JSONException -> 0x1a17 }
            double r4 = r12 * r10
            r8.append(r4)     // Catch:{ JSONException -> 0x1a17 }
            java.lang.String r4 = r8.toString()     // Catch:{ JSONException -> 0x1a17 }
            r5 = 0
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x1a17 }
            r2.h(r4, r8)     // Catch:{ JSONException -> 0x1a17 }
            double r4 = r6 * r14
            double r4 = java.lang.Math.ceil(r4)     // Catch:{ JSONException -> 0x1a17 }
            int r2 = (int) r4     // Catch:{ JSONException -> 0x1a17 }
            android.content.Context r4 = r3.b     // Catch:{ JSONException -> 0x1a17 }
            java.lang.String r5 = "playerFrameY"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefInt(r4, r5, r2)     // Catch:{ JSONException -> 0x1a17 }
            goto L_0x19fe
        L_0x19e8:
            r0 = move-exception
            r33 = r4
            r2 = r0
            goto L_0x1a20
        L_0x19ed:
            r33 = r4
            r34 = r5
            android.content.Context r2 = r3.b     // Catch:{ JSONException -> 0x1a17 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r10, r7)     // Catch:{ JSONException -> 0x1a17 }
            android.content.Context r2 = r3.b     // Catch:{ JSONException -> 0x1a17 }
            java.lang.String r4 = "playerFrameY"
            r5 = 0
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefInt(r2, r4, r5)     // Catch:{ JSONException -> 0x1a17 }
        L_0x19fe:
            r2 = r22
            boolean r4 = r1.has(r2)     // Catch:{ JSONException -> 0x1a17 }
            if (r4 == 0) goto L_0x1a10
            android.content.Context r4 = r3.b     // Catch:{ JSONException -> 0x1a17 }
            int r5 = r1.getInt(r2)     // Catch:{ JSONException -> 0x1a17 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefInt(r4, r2, r5)     // Catch:{ JSONException -> 0x1a17 }
            goto L_0x1a23
        L_0x1a10:
            android.content.Context r4 = r3.b     // Catch:{ JSONException -> 0x1a17 }
            r5 = 0
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefInt(r4, r2, r5)     // Catch:{ JSONException -> 0x1a17 }
            goto L_0x1a23
        L_0x1a17:
            r0 = move-exception
            r2 = r0
            goto L_0x1a20
        L_0x1a1a:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r2 = r0
        L_0x1a20:
            r2.printStackTrace()
        L_0x1a23:
            goto L_0x1a28
        L_0x1a24:
            r33 = r4
            r34 = r5
        L_0x1a28:
            r7 = r1
            r10 = r9
        L_0x1a2a:
            java.lang.String r1 = "connect"
            r2 = r35
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x1a89
            java.lang.String r1 = "createPlayer"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x1a89
            java.lang.String r1 = "alertView"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x1a89
            java.lang.String r1 = "faceCollection"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x1a89
            java.lang.String r1 = "getKeyParames"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x1a86
            java.lang.String r1 = "getClientKeyExchange"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x1a86
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r4 = "code"
            r5 = 200(0xc8, float:2.8E-43)
            r1.put((java.lang.String) r4, (int) r5)     // Catch:{ JSONException -> 0x1a7e }
            org.greenrobot.eventbus.c r4 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x1a7e }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x1a7e }
            java.lang.String r6 = r1.toString()     // Catch:{ JSONException -> 0x1a7e }
            r8 = r33
            r5.<init>(r8, r6)     // Catch:{ JSONException -> 0x1a7b }
            r4.l(r5)     // Catch:{ JSONException -> 0x1a7b }
            goto L_0x1a8b
        L_0x1a7b:
            r0 = move-exception
            r4 = r0
            goto L_0x1a82
        L_0x1a7e:
            r0 = move-exception
            r8 = r33
            r4 = r0
        L_0x1a82:
            r4.printStackTrace()
            goto L_0x1a8b
        L_0x1a86:
            r8 = r33
            goto L_0x1a8b
        L_0x1a89:
            r8 = r33
        L_0x1a8b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl.handleData(android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class i extends TypeToken<PushBean> {
        i() {
        }
    }

    public class j implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ Activity f;
        final /* synthetic */ int[] q;

        j(int i, JSONObject jSONObject, Activity activity, int[] iArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = activity;
            this.q = iArr;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8061, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (this.c == 1) {
                        String themeColor = "";
                        if (this.d.has("themeColor")) {
                            themeColor = this.d.getString("themeColor");
                        }
                        if (IpcServiceImpl.this.c != null && IpcServiceImpl.this.c.isShowing()) {
                            IpcServiceImpl.this.c.dismiss();
                        }
                        com.leedarson.base.views.g unused = IpcServiceImpl.this.c = null;
                        if (IpcServiceImpl.this.c == null) {
                            com.leedarson.base.views.g unused2 = IpcServiceImpl.this.c = new com.leedarson.base.views.g(this.f);
                            IpcServiceImpl.this.c.setCanceledOnTouchOutside(false);
                        }
                        if (IpcServiceImpl.this.c != null) {
                            if (this.d.has(TypedValues.AttributesType.S_FRAME)) {
                                JSONArray array = this.d.getJSONArray(TypedValues.AttributesType.S_FRAME);
                                double marginTopPer = 0.0d;
                                try {
                                    marginTopPer = (Double.parseDouble(array.getString(3)) + Double.parseDouble(array.getString(1))) / 2.0d;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                WindowManager vm = this.f.getWindowManager();
                                DisplayMetrics dm = new DisplayMetrics();
                                vm.getDefaultDisplay().getMetrics(dm);
                                this.q[0] = (int) Math.ceil(marginTopPer * ((double) dm.heightPixels));
                                Window dialogWindow = IpcServiceImpl.this.c.getWindow();
                                dialogWindow.setFlags(32, 32);
                                dialogWindow.setGravity(48);
                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                                lp.width = -1;
                                lp.height = -1;
                                lp.x = -1;
                                lp.y = this.q[0] - com.leedarson.base.utils.d.b(IpcServiceImpl.this.b, 19.0f);
                                dialogWindow.setAttributes(lp);
                            }
                            if (!themeColor.isEmpty()) {
                                IpcServiceImpl.this.c.f(themeColor);
                            }
                        }
                        IpcServiceImpl.this.c.show();
                    } else if (IpcServiceImpl.this.c != null) {
                        IpcServiceImpl.this.c.dismiss();
                        com.leedarson.base.views.g unused3 = IpcServiceImpl.this.c = null;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (IpcServiceImpl.this.c != null && IpcServiceImpl.this.c.isShowing()) {
                        IpcServiceImpl.this.c.dismiss();
                    }
                    com.leedarson.base.views.g unused4 = IpcServiceImpl.this.c = null;
                }
            }
        }
    }

    public class k implements com.leedarson.smartcamera.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        k(String str) {
            this.a = str;
        }

        public void onSuccess(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8062, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("getSDTimeList", "" + data.length);
                try {
                    if (data.length == 0) {
                        data = new byte[72];
                    }
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) this.a);
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put(Constants.ACTION_STATE, 1);
                    jsonObject2.put("eventlist", (Object) new JSONArray((Object) data));
                    jsonObject2.put("messageCode", 1002);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("TUTK", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class l implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;
        final /* synthetic */ com.leedarson.smartcamera.listener.e d;

        l(String str, String str2, int i, com.leedarson.smartcamera.listener.e eVar) {
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = eVar;
        }

        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8065, new Class[0], Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onAddStream: ");
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 8066, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onDataChannelStateChange: ");
                    if (state == DataChannel.State.OPEN) {
                        f0 l = IpcServiceImpl.this.f;
                        l lVar = l.this;
                        l.k1(lVar.a, lVar.b, lVar.c, lVar.d);
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 8067, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onMessage: " + bytes.length);
                }
            }

            public void onError(String desc) {
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8063, new Class[0], Void.TYPE).isSupported) {
                Log.e("IpcServiceImpl", "onOpen: ");
                IpcServiceImpl.this.f.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8064, new Class[0], Void.TYPE).isSupported) {
                Log.e("IpcServiceImpl", "onClose: ");
            }
        }

        public void a(Event event) {
        }

        public void onException(Exception e2) {
        }

        public void c(String message) {
        }

        public void g(String message) {
        }

        public void e(String message) {
        }

        public void d(int stateCode) {
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    public class m implements com.leedarson.smartcamera.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        m(String str) {
            this.a = str;
        }

        public void onSuccess(List<Long> recordTimestamps) {
            if (!PatchProxy.proxy(new Object[]{recordTimestamps}, this, changeQuickRedirect, false, 8068, new Class[]{List.class}, Void.TYPE).isSupported) {
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) this.a);
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put(Constants.ACTION_STATE, 1);
                    String[] dataStr = new String[recordTimestamps.size()];
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                    JSONArray timeArr = new JSONArray();
                    for (int i = 0; i < recordTimestamps.size(); i++) {
                        String ds = simpleDateFormat.format(new Date(recordTimestamps.get(i).longValue()));
                        dataStr[i] = ds;
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(Progress.DATE, (Object) ds);
                        jsonObject.put("timestamp", recordTimestamps.get(i).longValue() / 1000);
                        timeArr.put((Object) jsonObject);
                    }
                    jsonObject2.put("eventlist", (Object) new JSONArray((Object) dataStr));
                    jsonObject2.put("timestamplist", (Object) timeArr);
                    jsonObject2.put("messageCode", 1003);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("TUTK", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class n implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;
        final /* synthetic */ com.leedarson.smartcamera.listener.b d;

        n(String str, String str2, int i, com.leedarson.smartcamera.listener.b bVar) {
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = bVar;
        }

        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8071, new Class[0], Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onAddStream: ");
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 8072, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onDataChannelStateChange: ");
                    if (state == DataChannel.State.OPEN) {
                        f0 l = IpcServiceImpl.this.f;
                        n nVar = n.this;
                        l.h1(nVar.a, nVar.b, nVar.c, nVar.d);
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 8073, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onMessage: " + bytes.length);
                }
            }

            public void onError(String desc) {
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8069, new Class[0], Void.TYPE).isSupported) {
                Log.e("IpcServiceImpl", "onOpen: ");
                IpcServiceImpl.this.f.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8070, new Class[0], Void.TYPE).isSupported) {
                Log.e("IpcServiceImpl", "onClose: ");
            }
        }

        public void a(Event event) {
        }

        public void onException(Exception e2) {
        }

        public void e(String message) {
        }

        public void c(String message) {
        }

        public void g(String message) {
        }

        public void d(int stateCode) {
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    public class o implements com.leedarson.smartcamera.listener.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        o(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8074, new Class[0], Void.TYPE).isSupported) {
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) this.a);
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put(Constants.ACTION_STATE, 1);
                    jsonObject2.put("messageCode", 1004);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("TUTK", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int code) {
        }
    }

    public class p implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;
        final /* synthetic */ int b;
        final /* synthetic */ List c;
        final /* synthetic */ com.leedarson.smartcamera.listener.a d;

        p(int i, int i2, List list, com.leedarson.smartcamera.listener.a aVar) {
            this.a = i;
            this.b = i2;
            this.c = list;
            this.d = aVar;
        }

        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8077, new Class[0], Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onAddStream: ");
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 8078, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onDataChannelStateChange: ");
                    if (state == DataChannel.State.OPEN) {
                        f0 l = IpcServiceImpl.this.f;
                        p pVar = p.this;
                        l.N0(pVar.a, pVar.b, pVar.c, pVar.d);
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 8079, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    Log.e("IpcServiceImpl", "onMessage: " + bytes.length);
                }
            }

            public void onError(String desc) {
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8075, new Class[0], Void.TYPE).isSupported) {
                Log.e("IpcServiceImpl", "onOpen: ");
                IpcServiceImpl.this.f.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8076, new Class[0], Void.TYPE).isSupported) {
                Log.e("IpcServiceImpl", "onClose: ");
            }
        }

        public void a(Event event) {
        }

        public void onException(Exception e2) {
        }

        public void g(String message) {
        }

        public void c(String message) {
        }

        public void e(String message) {
        }

        public void d(int stateCode) {
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    public class q implements com.leedarson.smartcamera.listener.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        q(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void c(int status) {
            Object[] objArr = {new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, NetworkListener.DEFAULT_NETWORK_PORT, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("getThumbnai", "connectStatusChange: " + status);
            }
        }

        public void a(long time, String filepath) {
            Object[] objArr = {new Long(time), filepath};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8081, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
                StringBuilder sb = new StringBuilder();
                sb.append("onSuccess: ");
                sb.append(this.a);
                String str = File.separator;
                sb.append(str);
                sb.append(filepath);
                com.leedarson.smartcamera.utils.e.c("getThumbnai", sb.toString());
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) this.b);
                    jsonObject2.put("command", (Object) "get");
                    jsonObject2.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) this.c);
                    jsonObject2.put("time", time);
                    jsonObject2.put("path", (Object) this.a + str + filepath);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("TUTK", H5ActionName.THUMBNAI_NOTIFY_ACTION, jsonObject2.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void b(long time, byte[] imgBytes) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x01d3 A[Catch:{ JSONException -> 0x027e }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0219 A[Catch:{ JSONException -> 0x027e }] */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x025d A[Catch:{ JSONException -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0264 A[Catch:{ JSONException -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0272 A[Catch:{ JSONException -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x0461 A[Catch:{ Exception -> 0x04ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:221:0x04a4 A[Catch:{ Exception -> 0x04ad }] */
    /* JADX WARNING: Removed duplicated region for block: B:243:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x018d A[Catch:{ JSONException -> 0x027e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.json.JSONObject r21, boolean r22, boolean r23) {
        /*
            r20 = this;
            java.lang.String r1 = "StreamType"
            java.lang.String r2 = "dynamicStream"
            java.lang.String r3 = "props"
            java.lang.String r0 = "payload"
            java.lang.String r4 = "sirenRing"
            java.lang.String r5 = "trackingMode"
            java.lang.String r6 = "LightOnOff"
            java.lang.String r7 = "TurnOnOff"
            r8 = 3
            java.lang.Object[] r9 = new java.lang.Object[r8]
            r15 = 0
            r9[r15] = r21
            java.lang.Byte r10 = new java.lang.Byte
            r14 = r22
            r10.<init>(r14)
            r13 = 1
            r9[r13] = r10
            java.lang.Byte r10 = new java.lang.Byte
            r12 = r23
            r10.<init>(r12)
            r11 = 2
            r9[r11] = r10
            com.meituan.robust.ChangeQuickRedirect r16 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r8]
            java.lang.Class<org.json.JSONObject> r10 = org.json.JSONObject.class
            r8[r15] = r10
            java.lang.Class r10 = java.lang.Boolean.TYPE
            r8[r13] = r10
            r8[r11] = r10
            java.lang.Class r17 = java.lang.Void.TYPE
            r18 = 0
            r19 = 8025(0x1f59, float:1.1245E-41)
            r10 = r20
            r11 = r16
            r12 = r18
            r13 = r19
            r14 = r8
            r8 = r15
            r15 = r17
            com.meituan.robust.PatchProxyResult r9 = com.meituan.robust.PatchProxy.proxy(r9, r10, r11, r12, r13, r14, r15)
            boolean r9 = r9.isSupported
            if (r9 == 0) goto L_0x0053
            return
        L_0x0053:
            r9 = r20
            r10 = r22
            r11 = r21
            r12 = r23
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04b8 }
            r13.<init>()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r14 = "MqttLog 开始进行全量升级 _actualFullUpdate  updateFromWeb="
            r13.append(r14)     // Catch:{ Exception -> 0x04b8 }
            r13.append(r10)     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x04b8 }
            java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x04b8 }
            timber.log.a.i(r13, r14)     // Catch:{ Exception -> 0x04b8 }
            if (r11 == 0) goto L_0x04b3
            boolean r13 = r11.has(r0)     // Catch:{ Exception -> 0x04b8 }
            if (r13 == 0) goto L_0x04b3
            com.alibaba.android.arouter.launcher.a r13 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x04b8 }
            java.lang.Class<com.leedarson.serviceinterface.LDSBaseMqttService> r14 = com.leedarson.serviceinterface.LDSBaseMqttService.class
            java.lang.Object r13 = r13.g(r14)     // Catch:{ Exception -> 0x04b8 }
            com.leedarson.serviceinterface.LDSBaseMqttService r13 = (com.leedarson.serviceinterface.LDSBaseMqttService) r13     // Catch:{ Exception -> 0x04b8 }
            if (r13 == 0) goto L_0x00b5
            io.reactivex.disposables.b r14 = r9.j     // Catch:{ Exception -> 0x00ae }
            if (r14 == 0) goto L_0x0096
            boolean r14 = r14.isDisposed()     // Catch:{ Exception -> 0x00ae }
            if (r14 != 0) goto L_0x0096
            io.reactivex.disposables.b r14 = r9.j     // Catch:{ Exception -> 0x00ae }
            r14.dispose()     // Catch:{ Exception -> 0x00ae }
        L_0x0096:
            io.reactivex.e r14 = r13.fullIpcDevicesUpdate(r11)     // Catch:{ Exception -> 0x00ae }
            io.reactivex.i r15 = com.leedarson.base.http.observer.l.b()     // Catch:{ Exception -> 0x00ae }
            io.reactivex.e r14 = r14.c(r15)     // Catch:{ Exception -> 0x00ae }
            com.leedarson.serviceimpl.ipcservice.a r15 = com.leedarson.serviceimpl.ipcservice.a.c     // Catch:{ Exception -> 0x00ae }
            com.leedarson.serviceimpl.ipcservice.f r8 = com.leedarson.serviceimpl.ipcservice.f.c     // Catch:{ Exception -> 0x00ae }
            io.reactivex.disposables.b r8 = r14.I(r15, r8)     // Catch:{ Exception -> 0x00ae }
            r9.j = r8     // Catch:{ Exception -> 0x00ae }
            goto L_0x00b5
        L_0x00ae:
            r0 = move-exception
            r21 = r11
            r22 = r12
            goto L_0x04bd
        L_0x00b5:
            org.json.JSONArray r0 = r11.getJSONArray(r0)     // Catch:{ Exception -> 0x04b8 }
            r8 = r0
            com.google.gson.Gson r0 = new com.google.gson.Gson     // Catch:{ Exception -> 0x04b8 }
            r0.<init>()     // Catch:{ Exception -> 0x04b8 }
            r14 = r0
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x04b8 }
            r0.<init>()     // Catch:{ Exception -> 0x04b8 }
            r15 = r0
            r0 = 0
            r21 = r11
            r11 = r0
        L_0x00ca:
            int r0 = r8.length()     // Catch:{ Exception -> 0x04af }
            if (r11 >= r0) goto L_0x030a
            java.lang.Object r0 = r8.get(r11)     // Catch:{ Exception -> 0x04af }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x04af }
            r22 = r12
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$a r12 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$a     // Catch:{ Exception -> 0x04ad }
            r12.<init>()     // Catch:{ Exception -> 0x04ad }
            java.lang.reflect.Type r12 = r12.getType()     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r0 = r14.fromJson((java.lang.String) r0, (java.lang.reflect.Type) r12)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r0 = (com.leedarson.bean.IpcDeviceBean) r0     // Catch:{ Exception -> 0x04ad }
            r12 = r0
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0292 }
            r23 = r14
            com.leedarson.serviceinterface.event.PartialUpdateEvent r14 = new com.leedarson.serviceinterface.event.PartialUpdateEvent     // Catch:{ JSONException -> 0x028b }
            java.lang.Object r17 = r8.get(r11)     // Catch:{ JSONException -> 0x028b }
            r18 = r13
            java.lang.String r13 = r17.toString()     // Catch:{ JSONException -> 0x0286 }
            r14.<init>(r13)     // Catch:{ JSONException -> 0x0286 }
            r0.l(r14)     // Catch:{ JSONException -> 0x0286 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0286 }
            java.lang.Object r13 = r8.get(r11)     // Catch:{ JSONException -> 0x0286 }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x0286 }
            r0.<init>((java.lang.String) r13)     // Catch:{ JSONException -> 0x0286 }
            boolean r13 = r0.has(r3)     // Catch:{ JSONException -> 0x0286 }
            if (r13 == 0) goto L_0x0280
            org.json.JSONObject r13 = r0.getJSONObject(r3)     // Catch:{ JSONException -> 0x0286 }
            boolean r14 = r13.has(r7)     // Catch:{ JSONException -> 0x0286 }
            r17 = r0
            java.lang.String r0 = "1"
            if (r14 == 0) goto L_0x0149
            java.lang.Object r14 = r13.get(r7)     // Catch:{ JSONException -> 0x0286 }
            boolean r14 = r14 instanceof java.lang.String     // Catch:{ JSONException -> 0x0286 }
            if (r14 == 0) goto L_0x0149
            java.lang.String r14 = r13.getString(r7)     // Catch:{ JSONException -> 0x0286 }
            boolean r14 = r14.equals(r0)     // Catch:{ JSONException -> 0x0286 }
            if (r14 == 0) goto L_0x013d
            com.leedarson.bean.PropsBean r14 = r12.props     // Catch:{ JSONException -> 0x0286 }
            r19 = r3
            r3 = 1
            r14.TurnOnOff = r3     // Catch:{ JSONException -> 0x0146 }
            goto L_0x0187
        L_0x013d:
            r19 = r3
            r3 = 1
            com.leedarson.bean.PropsBean r14 = r12.props     // Catch:{ JSONException -> 0x0146 }
            r3 = 0
            r14.TurnOnOff = r3     // Catch:{ JSONException -> 0x027e }
            goto L_0x0187
        L_0x0146:
            r0 = move-exception
            goto L_0x029a
        L_0x0149:
            r19 = r3
            boolean r3 = r13.has(r7)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x016b
            java.lang.Object r3 = r13.get(r7)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.Integer     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x016b
            int r3 = r13.getInt(r7)     // Catch:{ JSONException -> 0x027e }
            r14 = 1
            if (r3 != r14) goto L_0x0165
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027b }
            r3.TurnOnOff = r14     // Catch:{ JSONException -> 0x027b }
            goto L_0x0187
        L_0x0165:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 0
            r3.TurnOnOff = r14     // Catch:{ JSONException -> 0x027e }
            goto L_0x0187
        L_0x016b:
            boolean r3 = r13.has(r7)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x0182
            java.lang.Object r3 = r13.get(r7)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.Boolean     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x0182
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            boolean r14 = r13.getBoolean(r7)     // Catch:{ JSONException -> 0x027e }
            r3.TurnOnOff = r14     // Catch:{ JSONException -> 0x027e }
            goto L_0x0187
        L_0x0182:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 1
            r3.TurnOnOff = r14     // Catch:{ JSONException -> 0x027b }
        L_0x0187:
            boolean r3 = r13.has(r6)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x01cd
            java.lang.Object r3 = r13.get(r6)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.String     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x01ab
            java.lang.String r3 = r13.getString(r6)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3.equals(r0)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x01a5
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 1
            r3.LightOnOff = r14     // Catch:{ JSONException -> 0x027b }
            goto L_0x01cd
        L_0x01a5:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 0
            r3.LightOnOff = r14     // Catch:{ JSONException -> 0x027e }
            goto L_0x01cd
        L_0x01ab:
            java.lang.Object r3 = r13.get(r6)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.Integer     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x01c5
            int r3 = r13.getInt(r6)     // Catch:{ JSONException -> 0x027e }
            r14 = 1
            if (r3 != r14) goto L_0x01bf
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027b }
            r3.LightOnOff = r14     // Catch:{ JSONException -> 0x027b }
            goto L_0x01cd
        L_0x01bf:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 0
            r3.LightOnOff = r14     // Catch:{ JSONException -> 0x027e }
            goto L_0x01cd
        L_0x01c5:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            boolean r14 = r13.getBoolean(r6)     // Catch:{ JSONException -> 0x027e }
            r3.LightOnOff = r14     // Catch:{ JSONException -> 0x027e }
        L_0x01cd:
            boolean r3 = r13.has(r5)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x0213
            java.lang.Object r3 = r13.get(r5)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.String     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x01f1
            java.lang.String r3 = r13.getString(r5)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3.equals(r0)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x01eb
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 1
            r3.trackingMode = r14     // Catch:{ JSONException -> 0x027b }
            goto L_0x0213
        L_0x01eb:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 0
            r3.trackingMode = r14     // Catch:{ JSONException -> 0x027e }
            goto L_0x0213
        L_0x01f1:
            java.lang.Object r3 = r13.get(r5)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.Integer     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x020b
            int r3 = r13.getInt(r5)     // Catch:{ JSONException -> 0x027e }
            r14 = 1
            if (r3 != r14) goto L_0x0205
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027b }
            r3.trackingMode = r14     // Catch:{ JSONException -> 0x027b }
            goto L_0x0213
        L_0x0205:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            r14 = 0
            r3.trackingMode = r14     // Catch:{ JSONException -> 0x027e }
            goto L_0x0213
        L_0x020b:
            com.leedarson.bean.PropsBean r3 = r12.props     // Catch:{ JSONException -> 0x027e }
            boolean r14 = r13.getBoolean(r5)     // Catch:{ JSONException -> 0x027e }
            r3.trackingMode = r14     // Catch:{ JSONException -> 0x027e }
        L_0x0213:
            boolean r3 = r13.has(r4)     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x025d
            java.lang.Object r3 = r13.get(r4)     // Catch:{ JSONException -> 0x027e }
            boolean r3 = r3 instanceof java.lang.String     // Catch:{ JSONException -> 0x027e }
            if (r3 == 0) goto L_0x0239
            java.lang.String r3 = r13.getString(r4)     // Catch:{ JSONException -> 0x027e }
            boolean r0 = r3.equals(r0)     // Catch:{ JSONException -> 0x027e }
            if (r0 == 0) goto L_0x0232
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x027e }
            r3 = 1
            r0.sirenRing = r3     // Catch:{ JSONException -> 0x0146 }
            r3 = 1
            goto L_0x025e
        L_0x0232:
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x027e }
            r3 = 0
            r0.sirenRing = r3     // Catch:{ JSONException -> 0x027e }
            r3 = 1
            goto L_0x025e
        L_0x0239:
            java.lang.Object r0 = r13.get(r4)     // Catch:{ JSONException -> 0x027e }
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ JSONException -> 0x027e }
            if (r0 == 0) goto L_0x0253
            int r0 = r13.getInt(r4)     // Catch:{ JSONException -> 0x027e }
            r3 = 1
            if (r0 != r3) goto L_0x024d
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x0146 }
            r0.sirenRing = r3     // Catch:{ JSONException -> 0x0146 }
            goto L_0x025e
        L_0x024d:
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x0146 }
            r14 = 0
            r0.sirenRing = r14     // Catch:{ JSONException -> 0x0146 }
            goto L_0x025e
        L_0x0253:
            r3 = 1
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x0146 }
            boolean r14 = r13.getBoolean(r4)     // Catch:{ JSONException -> 0x0146 }
            r0.sirenRing = r14     // Catch:{ JSONException -> 0x0146 }
            goto L_0x025e
        L_0x025d:
            r3 = 1
        L_0x025e:
            boolean r0 = r13.has(r2)     // Catch:{ JSONException -> 0x0146 }
            if (r0 == 0) goto L_0x026c
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x0146 }
            java.lang.String r14 = r13.getString(r2)     // Catch:{ JSONException -> 0x0146 }
            r0.dynamicStream = r14     // Catch:{ JSONException -> 0x0146 }
        L_0x026c:
            boolean r0 = r13.has(r1)     // Catch:{ JSONException -> 0x0146 }
            if (r0 == 0) goto L_0x0285
            com.leedarson.bean.PropsBean r0 = r12.props     // Catch:{ JSONException -> 0x0146 }
            java.lang.String r14 = r13.getString(r1)     // Catch:{ JSONException -> 0x0146 }
            r0.StreamType = r14     // Catch:{ JSONException -> 0x0146 }
            goto L_0x0285
        L_0x027b:
            r0 = move-exception
            r3 = r14
            goto L_0x029a
        L_0x027e:
            r0 = move-exception
            goto L_0x0289
        L_0x0280:
            r17 = r0
            r19 = r3
            r3 = 1
        L_0x0285:
            goto L_0x029d
        L_0x0286:
            r0 = move-exception
            r19 = r3
        L_0x0289:
            r3 = 1
            goto L_0x029a
        L_0x028b:
            r0 = move-exception
            r19 = r3
            r18 = r13
            r3 = 1
            goto L_0x029a
        L_0x0292:
            r0 = move-exception
            r19 = r3
            r18 = r13
            r23 = r14
            r3 = 1
        L_0x029a:
            r0.printStackTrace()     // Catch:{ Exception -> 0x04ad }
        L_0x029d:
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x02f2 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02f2 }
            r13.<init>()     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = "firmware_"
            r13.append(r14)     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = r12.id     // Catch:{ Exception -> 0x02f2 }
            r13.append(r14)     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = r12.firmwareVersion     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r0, r13, r14)     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x02f2 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02f2 }
            r13.<init>()     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = "hardware_"
            r13.append(r14)     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = r12.id     // Catch:{ Exception -> 0x02f2 }
            r13.append(r14)     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = r12.hardwareVersion     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r0, r13, r14)     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x02f2 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02f2 }
            r13.<init>()     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = "modelId_"
            r13.append(r14)     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = r12.id     // Catch:{ Exception -> 0x02f2 }
            r13.append(r14)     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r14 = r12.modelId     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r0, r13, r14)     // Catch:{ Exception -> 0x02f2 }
            goto L_0x02f6
        L_0x02f2:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x04ad }
        L_0x02f6:
            r15.add(r12)     // Catch:{ Exception -> 0x04ad }
            java.lang.String r0 = r12.id     // Catch:{ Exception -> 0x04ad }
            com.leedarson.serviceimpl.ipcservice.i.c(r0)     // Catch:{ Exception -> 0x04ad }
            int r11 = r11 + 1
            r12 = r22
            r14 = r23
            r13 = r18
            r3 = r19
            goto L_0x00ca
        L_0x030a:
            r22 = r12
            r18 = r13
            r23 = r14
            r3 = 1
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r0 = a     // Catch:{ Exception -> 0x04ad }
            int r0 = r0.size()     // Catch:{ Exception -> 0x04ad }
            java.lang.String r1 = "deviceId"
            java.lang.String r2 = "iot/v1/cb/deviceId/#"
            java.lang.String r4 = "topic"
            java.lang.String r5 = ""
            if (r0 <= 0) goto L_0x0433
            int r0 = r15.size()     // Catch:{ Exception -> 0x04ad }
            if (r0 <= 0) goto L_0x0433
            int r0 = r15.size()     // Catch:{ Exception -> 0x04ad }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r6 = a     // Catch:{ Exception -> 0x04ad }
            int r6 = r6.size()     // Catch:{ Exception -> 0x04ad }
            if (r0 >= r6) goto L_0x0430
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r0 = a     // Catch:{ Exception -> 0x04ad }
            r6 = 0
            java.lang.Object r0 = r0.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r0 = (com.leedarson.bean.IpcDeviceBean) r0     // Catch:{ Exception -> 0x04ad }
            java.lang.String r0 = r0.houseId     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r7 = r15.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r7 = (com.leedarson.bean.IpcDeviceBean) r7     // Catch:{ Exception -> 0x04ad }
            java.lang.String r6 = r7.houseId     // Catch:{ Exception -> 0x04ad }
            boolean r0 = r0.equals(r6)     // Catch:{ Exception -> 0x04ad }
            if (r0 == 0) goto L_0x0430
            r0 = 0
        L_0x034d:
            int r6 = r15.size()     // Catch:{ Exception -> 0x04ad }
            if (r0 >= r6) goto L_0x03ad
            r6 = 0
        L_0x0354:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ Exception -> 0x04ad }
            int r7 = r7.size()     // Catch:{ Exception -> 0x04ad }
            if (r6 >= r7) goto L_0x03aa
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r7 = r7.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r7 = (com.leedarson.bean.IpcDeviceBean) r7     // Catch:{ Exception -> 0x04ad }
            java.lang.String r7 = r7.id     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r11 = r15.get(r0)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r11 = (com.leedarson.bean.IpcDeviceBean) r11     // Catch:{ Exception -> 0x04ad }
            java.lang.String r11 = r11.id     // Catch:{ Exception -> 0x04ad }
            boolean r7 = r7.equals(r11)     // Catch:{ Exception -> 0x04ad }
            if (r7 == 0) goto L_0x03a7
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r7 = r7.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r7 = (com.leedarson.bean.IpcDeviceBean) r7     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.PropsBean r7 = r7.props     // Catch:{ Exception -> 0x04ad }
            java.lang.String r7 = r7.liveType     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r11 = r15.get(r0)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r11 = (com.leedarson.bean.IpcDeviceBean) r11     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.PropsBean r11 = r11.props     // Catch:{ Exception -> 0x04ad }
            java.lang.String r11 = r11.liveType     // Catch:{ Exception -> 0x04ad }
            boolean r7 = r7.equals(r11)     // Catch:{ Exception -> 0x04ad }
            if (r7 != 0) goto L_0x03a1
            com.leedarson.manager.a r7 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x04ad }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r11 = a     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r11 = r11.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r11 = (com.leedarson.bean.IpcDeviceBean) r11     // Catch:{ Exception -> 0x04ad }
            java.lang.String r11 = r11.id     // Catch:{ Exception -> 0x04ad }
            r7.q(r11)     // Catch:{ Exception -> 0x04ad }
        L_0x03a1:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ Exception -> 0x04ad }
            r7.remove(r6)     // Catch:{ Exception -> 0x04ad }
            goto L_0x03aa
        L_0x03a7:
            int r6 = r6 + 1
            goto L_0x0354
        L_0x03aa:
            int r0 = r0 + 1
            goto L_0x034d
        L_0x03ad:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04ad }
            r0.<init>()     // Catch:{ Exception -> 0x04ad }
            java.lang.String r6 = "FULL_UPDATE delete: "
            r0.append(r6)     // Catch:{ Exception -> 0x04ad }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r6 = a     // Catch:{ Exception -> 0x04ad }
            int r6 = r6.size()     // Catch:{ Exception -> 0x04ad }
            r0.append(r6)     // Catch:{ Exception -> 0x04ad }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x04ad }
            com.leedarson.log.f.a(r0)     // Catch:{ Exception -> 0x04ad }
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Exception -> 0x04ad }
            r0.<init>()     // Catch:{ Exception -> 0x04ad }
            r6 = 0
        L_0x03cd:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ Exception -> 0x04ad }
            int r7 = r7.size()     // Catch:{ Exception -> 0x04ad }
            if (r6 >= r7) goto L_0x0412
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r7 = a     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r7 = r7.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r7 = (com.leedarson.bean.IpcDeviceBean) r7     // Catch:{ Exception -> 0x04ad }
            java.lang.String r7 = r7.id     // Catch:{ Exception -> 0x04ad }
            java.lang.String r7 = r2.replace(r1, r7)     // Catch:{ Exception -> 0x04ad }
            r0.put((java.lang.Object) r7)     // Catch:{ Exception -> 0x04ad }
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x04ad }
            com.leedarson.serviceinterface.event.Event r11 = new com.leedarson.serviceinterface.event.Event     // Catch:{ Exception -> 0x04ad }
            java.lang.String r12 = "DeleteDeviceEvent"
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r13 = a     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r13 = r13.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r13 = (com.leedarson.bean.IpcDeviceBean) r13     // Catch:{ Exception -> 0x04ad }
            java.lang.String r13 = r13.id     // Catch:{ Exception -> 0x04ad }
            r11.<init>(r12, r5, r5, r13)     // Catch:{ Exception -> 0x04ad }
            r7.l(r11)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.manager.a r7 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x04ad }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r11 = a     // Catch:{ Exception -> 0x04ad }
            java.lang.Object r11 = r11.get(r6)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r11 = (com.leedarson.bean.IpcDeviceBean) r11     // Catch:{ Exception -> 0x04ad }
            java.lang.String r11 = r11.id     // Catch:{ Exception -> 0x04ad }
            r7.h(r11)     // Catch:{ Exception -> 0x04ad }
            int r6 = r6 + 1
            goto L_0x03cd
        L_0x0412:
            int r6 = r0.length()     // Catch:{ Exception -> 0x04ad }
            if (r6 <= 0) goto L_0x042d
            if (r10 != 0) goto L_0x042d
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x04ad }
            r6.<init>()     // Catch:{ Exception -> 0x04ad }
            r6.put((java.lang.String) r4, (java.lang.Object) r0)     // Catch:{ Exception -> 0x04ad }
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$b r7 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$b     // Catch:{ Exception -> 0x04ad }
            r7.<init>()     // Catch:{ Exception -> 0x04ad }
            r11 = r18
            r11.unSubscribeTopic(r6, r7)     // Catch:{ Exception -> 0x04ad }
            goto L_0x0435
        L_0x042d:
            r11 = r18
            goto L_0x0435
        L_0x0430:
            r11 = r18
            goto L_0x0435
        L_0x0433:
            r11 = r18
        L_0x0435:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r0 = a     // Catch:{ Exception -> 0x04ad }
            r0.clear()     // Catch:{ Exception -> 0x04ad }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r0 = a     // Catch:{ Exception -> 0x04ad }
            r0.addAll(r15)     // Catch:{ Exception -> 0x04ad }
            android.content.Context r0 = r9.b     // Catch:{ Exception -> 0x04ad }
            java.lang.String r6 = "owner"
            java.lang.String r0 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r6, r5)     // Catch:{ Exception -> 0x04ad }
            android.content.Context r6 = r9.b     // Catch:{ Exception -> 0x04ad }
            java.lang.String r7 = "userId"
            java.lang.String r5 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r6, r7, r5)     // Catch:{ Exception -> 0x04ad }
            r6 = 0
            org.json.JSONArray r7 = new org.json.JSONArray     // Catch:{ Exception -> 0x04ad }
            r7.<init>()     // Catch:{ Exception -> 0x04ad }
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r12 = a     // Catch:{ Exception -> 0x04ad }
            java.util.Iterator r12 = r12.iterator()     // Catch:{ Exception -> 0x04ad }
        L_0x045b:
            boolean r13 = r12.hasNext()     // Catch:{ Exception -> 0x04ad }
            if (r13 == 0) goto L_0x0498
            java.lang.Object r13 = r12.next()     // Catch:{ Exception -> 0x04ad }
            com.leedarson.bean.IpcDeviceBean r13 = (com.leedarson.bean.IpcDeviceBean) r13     // Catch:{ Exception -> 0x04ad }
            r13.account = r0     // Catch:{ Exception -> 0x04ad }
            boolean r14 = r13.isSupportPreCon()     // Catch:{ Exception -> 0x04ad }
            if (r14 == 0) goto L_0x0471
            r14 = r3
            goto L_0x0472
        L_0x0471:
            r14 = 0
        L_0x0472:
            if (r14 == 0) goto L_0x0489
            com.leedarson.manager.a r3 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x04ad }
            r17 = r0
            java.lang.String r0 = r13.id     // Catch:{ Exception -> 0x04ad }
            com.leedarson.smartcamera.kvswebrtc.f0 r0 = r3.j(r0)     // Catch:{ Exception -> 0x04ad }
            if (r0 != 0) goto L_0x048b
            com.leedarson.newui.channelstratages.d r0 = new com.leedarson.newui.channelstratages.d     // Catch:{ Exception -> 0x04ad }
            r3 = 0
            r0.<init>(r13, r5, r3)     // Catch:{ Exception -> 0x04ad }
            goto L_0x048b
        L_0x0489:
            r17 = r0
        L_0x048b:
            java.lang.String r0 = r13.id     // Catch:{ Exception -> 0x04ad }
            java.lang.String r0 = r2.replace(r1, r0)     // Catch:{ Exception -> 0x04ad }
            r7.put((java.lang.Object) r0)     // Catch:{ Exception -> 0x04ad }
            r0 = r17
            r3 = 1
            goto L_0x045b
        L_0x0498:
            r17 = r0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x04ad }
            r0.<init>()     // Catch:{ Exception -> 0x04ad }
            r0.put((java.lang.String) r4, (java.lang.Object) r7)     // Catch:{ Exception -> 0x04ad }
            if (r10 != 0) goto L_0x04b7
            com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$c r1 = new com.leedarson.serviceimpl.ipcservice.IpcServiceImpl$c     // Catch:{ Exception -> 0x04ad }
            r1.<init>()     // Catch:{ Exception -> 0x04ad }
            r11.subscribeTopic(r0, r1)     // Catch:{ Exception -> 0x04ad }
            goto L_0x04b7
        L_0x04ad:
            r0 = move-exception
            goto L_0x04bd
        L_0x04af:
            r0 = move-exception
            r22 = r12
            goto L_0x04bd
        L_0x04b3:
            r21 = r11
            r22 = r12
        L_0x04b7:
            goto L_0x04db
        L_0x04b8:
            r0 = move-exception
            r21 = r11
            r22 = r12
        L_0x04bd:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = " exception="
            r1.append(r2)
            java.lang.String r2 = r0.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            timber.log.a.c(r1, r2)
            r0.printStackTrace()
        L_0x04db:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl.a(org.json.JSONObject, boolean, boolean):void");
    }

    static /* synthetic */ void r(Boolean saveStatue) {
    }

    static /* synthetic */ void s(Throwable throwable) {
    }

    public class a extends TypeToken<IpcDeviceBean> {
        a() {
        }
    }

    public class b implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
        }

        public void onActionFail(int code, String taskId, String desc) {
        }
    }

    public class c implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
        }

        public void onActionFail(int code, String taskId, String desc) {
        }
    }

    public void actualFullUpdateByNativeMqtt(JSONObject dataObj, boolean updateFromWeb, boolean flagNeedPrelink) {
        Object[] objArr = {dataObj, new Byte(updateFromWeb ? (byte) 1 : 0), new Byte(flagNeedPrelink ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8026, new Class[]{JSONObject.class, cls, cls}, Void.TYPE).isSupported) {
            a(dataObj, updateFromWeb, flagNeedPrelink);
        }
    }

    private boolean h(JSONObject jSONObject) {
        JSONObject proObj;
        IpcDeviceBean deviceBean;
        String str = "networkRssi";
        String str2 = "name";
        String str3 = "Dimming";
        String str4 = "liveType";
        String str5 = "isAuto";
        String str6 = "isSupportAuto";
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jSONObject}, this, changeQuickRedirect, false, 8027, new Class[]{JSONObject.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        JSONObject dataObj = jSONObject;
        if (dataObj != null) {
            try {
                if (dataObj.has("payload")) {
                    JSONObject payloadObj = dataObj.getJSONObject("payload");
                    i.b(payloadObj);
                    JSONObject jSONObject2 = dataObj;
                    try {
                        j.a(payloadObj, BaseApplication.b());
                        String str7 = "payload";
                        org.greenrobot.eventbus.c.c().l(new PartialUpdateEvent(payloadObj.toString()));
                        String deviceId = payloadObj.getString("id");
                        com.leedarson.manager.a.i().p(deviceId, payloadObj);
                        String _keyProps = "props";
                        String _keyExtensions = "extensions";
                        if (payloadObj.has(_keyProps)) {
                            try {
                                proObj = payloadObj.getJSONObject(_keyProps);
                            } catch (JSONException e2) {
                                e = e2;
                                e.printStackTrace();
                                return false;
                            }
                        } else {
                            String _keyExtensions2 = _keyExtensions;
                            if (payloadObj.has(_keyExtensions2)) {
                                String str8 = _keyExtensions2;
                                proObj = payloadObj.getJSONObject(_keyExtensions2);
                            } else {
                                String str9 = _keyExtensions2;
                                proObj = null;
                            }
                        }
                        if (proObj != null) {
                            Iterator<IpcDeviceBean> it = a.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    String str10 = _keyProps;
                                    deviceBean = null;
                                    break;
                                }
                                JSONObject payloadObj2 = payloadObj;
                                deviceBean = it.next();
                                String _keyProps2 = _keyProps;
                                if (deviceBean.id.equals(deviceId)) {
                                    IpcDeviceBean _targetDevice = deviceBean;
                                    break;
                                }
                                payloadObj = payloadObj2;
                                _keyProps = _keyProps2;
                            }
                            if (deviceBean == null) {
                                return true;
                            }
                            String deviceId2 = deviceId;
                            if (proObj.has("TurnOnOff")) {
                                if (proObj.get("TurnOnOff") instanceof String) {
                                    if (proObj.getString("TurnOnOff").equals("1")) {
                                        deviceBean.props.TurnOnOff = true;
                                    } else {
                                        deviceBean.props.TurnOnOff = false;
                                    }
                                } else if (!(proObj.get("TurnOnOff") instanceof Integer)) {
                                    deviceBean.props.TurnOnOff = proObj.getBoolean("TurnOnOff");
                                } else if (proObj.getInt("TurnOnOff") == 1) {
                                    deviceBean.props.TurnOnOff = true;
                                } else {
                                    deviceBean.props.TurnOnOff = false;
                                }
                            }
                            try {
                                if (proObj.has("LightOnOff")) {
                                    if (proObj.get("LightOnOff") instanceof String) {
                                        if (proObj.getString("LightOnOff").equals("1")) {
                                            deviceBean.props.LightOnOff = true;
                                        } else {
                                            deviceBean.props.LightOnOff = false;
                                        }
                                    } else if (!(proObj.get("LightOnOff") instanceof Integer)) {
                                        deviceBean.props.LightOnOff = proObj.getBoolean("LightOnOff");
                                    } else if (proObj.getInt("LightOnOff") == 1) {
                                        deviceBean.props.LightOnOff = true;
                                    } else {
                                        deviceBean.props.LightOnOff = false;
                                    }
                                }
                                if (proObj.has("trackingMode")) {
                                    if (proObj.get("trackingMode") instanceof String) {
                                        if (proObj.getString("trackingMode").equals("1")) {
                                            deviceBean.props.trackingMode = true;
                                        } else {
                                            deviceBean.props.trackingMode = false;
                                        }
                                    } else if (!(proObj.get("trackingMode") instanceof Integer)) {
                                        deviceBean.props.trackingMode = proObj.getBoolean("trackingMode");
                                    } else if (proObj.getInt("trackingMode") == 1) {
                                        deviceBean.props.trackingMode = true;
                                    } else {
                                        deviceBean.props.trackingMode = false;
                                    }
                                }
                                if (proObj.has("sirenRing")) {
                                    if (proObj.get("sirenRing") instanceof String) {
                                        if (proObj.getString("sirenRing").equals("1")) {
                                            deviceBean.props.sirenRing = true;
                                        } else {
                                            deviceBean.props.sirenRing = false;
                                        }
                                    } else if (!(proObj.get("sirenRing") instanceof Integer)) {
                                        deviceBean.props.sirenRing = proObj.getBoolean("sirenRing");
                                    } else if (proObj.getInt("sirenRing") == 1) {
                                        deviceBean.props.sirenRing = true;
                                    } else {
                                        deviceBean.props.sirenRing = false;
                                    }
                                }
                                if (proObj.has("micEnable")) {
                                    deviceBean.props.micEnable = proObj.getInt("micEnable");
                                }
                                if (proObj.has("SdcardRecord_Type")) {
                                    deviceBean.props.SdcardRecord_Type = proObj.getString("SdcardRecord_Type");
                                }
                                if (proObj.has("Battery_remaining")) {
                                    deviceBean.props.Battery_remaining = proObj.getString("Battery_remaining");
                                }
                                if (proObj.has(MeshConstants.AC_STATE_LOGIN_SUCCESS)) {
                                    deviceBean.online = Boolean.valueOf(proObj.getBoolean(MeshConstants.AC_STATE_LOGIN_SUCCESS));
                                }
                                if (proObj.has("MotionDetection_Enable")) {
                                    deviceBean.props.MotionDetection_Enable = proObj.getString("MotionDetection_Enable");
                                }
                                if (proObj.has("charging")) {
                                    deviceBean.props.charging = proObj.getString("charging");
                                }
                                if (proObj.has("lowPowerStatus")) {
                                    deviceBean.props.lowPowerStatus = proObj.getString("lowPowerStatus");
                                }
                                if (proObj.has("splicingDistance")) {
                                    deviceBean.props.splicingDistance = proObj.getString("splicingDistance");
                                }
                                String str11 = str6;
                                if (proObj.has(str11)) {
                                    deviceBean.props.isSupportAuto = proObj.getBoolean(str11);
                                }
                                String str12 = str5;
                                if (proObj.has(str12)) {
                                    deviceBean.props.isAuto = proObj.getBoolean(str12);
                                }
                                String str13 = str4;
                                if (proObj.has(str13)) {
                                    String tempType = proObj.getString(str13);
                                    if (TextUtils.isEmpty(tempType) || tempType.equals(deviceBean.props.liveType)) {
                                    } else {
                                        com.leedarson.manager.a.i().q(deviceId2);
                                    }
                                    deviceBean.props.liveType = tempType;
                                }
                                String str14 = str3;
                                if (proObj.has(str14)) {
                                    deviceBean.props.Dimming = proObj.getString(str14);
                                }
                                String str15 = str2;
                                if (proObj.has(str15)) {
                                    deviceBean.name = proObj.getString(str15);
                                }
                                String str16 = str;
                                if (proObj.has(str16)) {
                                    deviceBean.props.networkRssi = proObj.getString(str16);
                                }
                                if (proObj.has("dynamicStream")) {
                                    deviceBean.props.dynamicStream = proObj.getString("dynamicStream");
                                }
                                if (proObj.has("StreamType")) {
                                    deviceBean.props.StreamType = proObj.getString("StreamType");
                                }
                                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                                if (_mqttService != null) {
                                    try {
                                        io.reactivex.disposables.b bVar = this.j;
                                        if (bVar != null && !bVar.isDisposed()) {
                                            this.j.dispose();
                                        }
                                        timber.log.a.i("开始更新", new Object[0]);
                                        JSONObject _writeFileJsonObj = new JSONObject();
                                        _writeFileJsonObj.put(CacheEntity.KEY, (Object) "Device.fullUpdate");
                                        _writeFileJsonObj.put(str7, (Object) new JSONArray(new Gson().toJson((Object) a)));
                                        this.j = _mqttService.fullIpcDevicesUpdate(_writeFileJsonObj).c(com.leedarson.base.http.observer.l.b()).I(h.c, e.c);
                                        return false;
                                    } catch (JSONException e3) {
                                        e = e3;
                                        e.printStackTrace();
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } catch (JSONException e4) {
                                e = e4;
                                e.printStackTrace();
                                return false;
                            }
                        } else {
                            JSONObject jSONObject3 = payloadObj;
                            String str17 = _keyProps;
                            return false;
                        }
                    } catch (JSONException e5) {
                        e = e5;
                        e.printStackTrace();
                        return false;
                    }
                }
            } catch (JSONException e6) {
                e = e6;
                JSONObject jSONObject4 = dataObj;
                e.printStackTrace();
                return false;
            }
        }
        JSONObject jSONObject5 = dataObj;
        return false;
    }

    static /* synthetic */ void t(Boolean bool) {
        if (!PatchProxy.proxy(new Object[]{bool}, (Object) null, changeQuickRedirect, true, 8053, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            timber.log.a.i(" 设备列表缓存成功----》", new Object[0]);
        }
    }

    static /* synthetic */ void u(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 8052, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            timber.log.a.i(" 设备列表缓存失败----》throwable=" + throwable.toString(), new Object[0]);
        }
    }

    public void actualUpdatePartialFieldByNativeMqtt(JSONObject dataObj) {
        if (!PatchProxy.proxy(new Object[]{dataObj}, this, changeQuickRedirect, false, 8028, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            h(dataObj);
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8029, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.b = context;
            com.leedarson.smartcamera.sdk.a.X0();
            com.leedarson.smartcamera.codec.c.E();
            q();
            f0.q1();
        }
    }

    private void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8030, new Class[0], Void.TYPE).isSupported) {
            com.downloader.g.d(BaseApplication.b(), com.downloader.h.f().d(30000).b(30000).c(true).a());
        }
    }

    public void unInitTutkIpc() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8031, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.manager.a.i().d();
            com.leedarson.smartcamera.sdk.a.V1();
            Handler handler = this.i;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    public void disconnectAll() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8032, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.manager.a.i().d();
        }
    }

    public void disConnectAllWebRtcChannel() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8033, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.manager.a.i().f();
        }
    }

    public void preconnectAll() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8034, new Class[0], Void.TYPE).isSupported) {
            try {
                if (a != null) {
                    String userId = SharePreferenceUtils.getPrefString(this.b, "userId", "");
                    Iterator<IpcDeviceBean> it = a.iterator();
                    while (it.hasNext()) {
                        IpcDeviceBean deviceBean = it.next();
                        if (deviceBean != null && deviceBean.isSupportPreCon()) {
                            new com.leedarson.newui.channelstratages.d(deviceBean, userId, true);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void showNoNetTipView(Activity activity, INoNetSnapTipView iNoNetSnapTipView) {
        if (!PatchProxy.proxy(new Object[]{activity, iNoNetSnapTipView}, this, changeQuickRedirect, false, 8035, new Class[]{Activity.class, INoNetSnapTipView.class}, Void.TYPE).isSupported) {
            INoNetSnapTipView listerner = iNoNetSnapTipView;
            Activity activity2 = activity;
            long j2 = this.k;
            if ((j2 == 0 || j2 + 1800000 <= System.currentTimeMillis()) && !com.leedarson.newui.view.ldsnakebar.c.f()) {
                Activity _currentActivity = com.leedarson.base.utils.c.h().c();
                Configuration cf = _currentActivity.getResources().getConfiguration();
                int screenWidth = ScreenUtils.getScreenSize(_currentActivity)[0];
                int screenHeight = ScreenUtils.getScreenSize(_currentActivity)[1];
                AutoSizeConfig.getInstance().setScreenWidth(screenWidth);
                AutoSizeConfig.getInstance().setScreenHeight(screenHeight);
                if (cf.orientation == 1) {
                    AutoSize.autoConvertDensityBaseOnWidth(_currentActivity, 375.0f);
                    timber.log.a.i("showNoNetTipView updateBaseLineOfUIPage(竖屏)-->width=" + screenWidth + ",height=" + screenHeight, new Object[0]);
                } else {
                    AutoSize.autoConvertDensityBaseOnWidth(_currentActivity, 812.0f);
                    timber.log.a.i("showNoNetTipView updateBaseLineOfUIPage(横屏取消)-->width=" + screenWidth + ",height=" + screenHeight, new Object[0]);
                }
                View tipViewContent = LayoutInflater.from(activity2).inflate(R$layout.lds_network_unavailable_tips_layout_v2, (ViewGroup) null);
                LDSTextView tvDescription = (LDSTextView) tipViewContent.findViewById(R$id.tvNetworkDescription);
                String firstDescript = PubUtils.getString(BaseApplication.b(), R$string.lds_has_no_net_tip_first_word) + JustifyTextView.TWO_CHINESE_BLANK;
                String secondDescript = PubUtils.getString(BaseApplication.b(), R$string.lds_has_no_net_tip_second_word) + " >";
                SpannableStringBuilder contentStyle = new SpannableStringBuilder(firstDescript + secondDescript);
                UnderlineSpan us = new UnderlineSpan();
                TextPaint tp = new TextPaint();
                tp.setColor(ContextCompat.getColor(BaseApplication.b(), R$color.white100));
                us.updateDrawState(tp);
                Activity activity3 = _currentActivity;
                contentStyle.setSpan(us, firstDescript.length() + 4, (firstDescript + secondDescript).length() - 1, 0);
                d dVar = new d(listerner);
                Configuration configuration = cf;
                contentStyle.setSpan(dVar, firstDescript.length(), (firstDescript + secondDescript).length(), 33);
                d dVar2 = dVar;
                contentStyle.setSpan(new ForegroundColorSpan(Color.parseColor("#FC8E35")), firstDescript.length(), (firstDescript + secondDescript).length(), 34);
                tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
                tvDescription.setText(contentStyle);
                tipViewContent.findViewById(R$id.iv_network_tips_close).setOnClickListener(new d(this, listerner));
                com.leedarson.newui.view.ldsnakebar.c.c(activity2, tipViewContent, NeedPermissionEvent.PER_ANDROID_S_BLE).g(-2).a(new e()).b();
            }
        }
    }

    public class d extends ClickableSpan {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ INoNetSnapTipView c;

        d(INoNetSnapTipView iNoNetSnapTipView) {
            this.c = iNoNetSnapTipView;
        }

        @SensorsDataInstrumented
        public void onClick(@NonNull View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8054, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            com.leedarson.utils.k.e();
            INoNetSnapTipView iNoNetSnapTipView = this.c;
            if (iNoNetSnapTipView != null) {
                iNoNetSnapTipView.onDissmissClick();
            }
            com.leedarson.newui.view.ldsnakebar.c.d();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: v */
    public /* synthetic */ void w(INoNetSnapTipView listerner, View view) {
        Class[] clsArr = {INoNetSnapTipView.class, View.class};
        if (PatchProxy.proxy(new Object[]{listerner, view}, this, changeQuickRedirect, false, 8051, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (listerner != null) {
            listerner.onDissmissClick();
        }
        this.k = System.currentTimeMillis();
        com.leedarson.newui.view.ldsnakebar.c.d();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class e extends TopSnackbar.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ void a(Object obj, int i) {
            if (!PatchProxy.proxy(new Object[]{obj, new Integer(i)}, this, changeQuickRedirect, false, 8058, new Class[]{Object.class, Integer.TYPE}, Void.TYPE).isSupported) {
                c((TopSnackbar) obj, i);
            }
        }

        public /* bridge */ /* synthetic */ void b(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8057, new Class[]{Object.class}, Void.TYPE).isSupported) {
                d((TopSnackbar) obj);
            }
        }

        public void c(TopSnackbar transientBottomBar, int event) {
            if (!PatchProxy.proxy(new Object[]{transientBottomBar, new Integer(event)}, this, changeQuickRedirect, false, 8055, new Class[]{TopSnackbar.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.c(transientBottomBar, event);
                com.leedarson.newui.view.ldsnakebar.c.d();
            }
        }

        public void d(TopSnackbar sb) {
            if (!PatchProxy.proxy(new Object[]{sb}, this, changeQuickRedirect, false, 8056, new Class[]{TopSnackbar.class}, Void.TYPE).isSupported) {
                super.d(sb);
            }
        }
    }

    public void dismissNoNetTipView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8036, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.ldsnakebar.c.d();
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;
        final /* synthetic */ boolean d;
        final /* synthetic */ String f;

        f(Context context, boolean z, String str) {
            this.c = context;
            this.d = z;
            this.f = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8059, new Class[0], Void.TYPE).isSupported) {
                try {
                    View toastRoot = LayoutInflater.from(this.c).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                    if (IpcServiceImpl.this.d == null) {
                        Toast unused = IpcServiceImpl.this.d = new Toast(this.c);
                    }
                    if (this.d) {
                        IpcServiceImpl.this.d.setGravity(48, 0, SharePreferenceUtils.getPrefInt(this.c, "playerCenterY", 260));
                    } else {
                        IpcServiceImpl.this.d.setGravity(17, 0, 0);
                    }
                    IpcServiceImpl.this.d.setView(toastRoot);
                    ((TextView) toastRoot.findViewById(R$id.toast_notice)).setText(this.f);
                    IpcServiceImpl.this.d.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void D(Activity activity, Context context, boolean isPlayerCenter, String desc) {
        Object[] objArr = {activity, context, new Byte(isPlayerCenter ? (byte) 1 : 0), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8037, new Class[]{Activity.class, Context.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            if (activity != null) {
                activity.runOnUiThread(new f(context, isPlayerCenter, desc));
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0055 A[Catch:{ Exception -> 0x00a9 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void C(android.app.Activity r13) {
        /*
            r12 = this;
            java.lang.String r0 = "cloudPlayback"
            java.lang.String r1 = "isOwner"
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r10 = 0
            r3[r10] = r13
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r2]
            java.lang.Class<android.app.Activity> r4 = android.app.Activity.class
            r8[r10] = r4
            java.lang.Class r9 = java.lang.Void.TYPE
            r6 = 0
            r7 = 8039(0x1f67, float:1.1265E-41)
            r4 = r12
            com.meituan.robust.PatchProxyResult r3 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r3 = r3.isSupported
            if (r3 == 0) goto L_0x0021
            return
        L_0x0021:
            r3 = r12
            r4 = 0
            android.content.Context r5 = r3.b     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r6 = "current_params"
            java.lang.String r7 = ""
            java.lang.String r5 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r5, r6, r7)     // Catch:{ Exception -> 0x00a9 }
            r4 = r5
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x00a9 }
            r5.<init>((java.lang.String) r4)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r6 = "deviceId"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x00a9 }
            boolean r7 = r5.has(r1)     // Catch:{ Exception -> 0x00a9 }
            if (r7 == 0) goto L_0x0048
            boolean r7 = r5.getBoolean(r1)     // Catch:{ Exception -> 0x00a9 }
            if (r7 == 0) goto L_0x0046
            goto L_0x0048
        L_0x0046:
            r7 = r10
            goto L_0x0049
        L_0x0048:
            r7 = r2
        L_0x0049:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r8 = a     // Catch:{ Exception -> 0x00a9 }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ Exception -> 0x00a9 }
        L_0x004f:
            boolean r9 = r8.hasNext()     // Catch:{ Exception -> 0x00a9 }
            if (r9 == 0) goto L_0x0088
            java.lang.Object r9 = r8.next()     // Catch:{ Exception -> 0x00a9 }
            com.leedarson.bean.IpcDeviceBean r9 = (com.leedarson.bean.IpcDeviceBean) r9     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r11 = r9.id     // Catch:{ Exception -> 0x00a9 }
            boolean r11 = r6.equals(r11)     // Catch:{ Exception -> 0x00a9 }
            if (r11 == 0) goto L_0x0081
            boolean r11 = r5.has(r1)     // Catch:{ Exception -> 0x00a9 }
            if (r11 == 0) goto L_0x006c
            r9.isOwner = r7     // Catch:{ Exception -> 0x00a9 }
            goto L_0x006e
        L_0x006c:
            r9.isOwner = r2     // Catch:{ Exception -> 0x00a9 }
        L_0x006e:
            boolean r11 = r5.has(r0)     // Catch:{ Exception -> 0x00a9 }
            if (r11 == 0) goto L_0x007a
            boolean r11 = r5.getBoolean(r0)     // Catch:{ Exception -> 0x00a9 }
            r9.cloudPlayback = r11     // Catch:{ Exception -> 0x00a9 }
        L_0x007a:
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r2)     // Catch:{ Exception -> 0x00a9 }
            r9.isCurrentDevice = r11     // Catch:{ Exception -> 0x00a9 }
            goto L_0x0087
        L_0x0081:
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r10)     // Catch:{ Exception -> 0x00a9 }
            r9.isCurrentDevice = r11     // Catch:{ Exception -> 0x00a9 }
        L_0x0087:
            goto L_0x004f
        L_0x0088:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Exception -> 0x00a9 }
            java.lang.Class<com.leedarson.newui.IpcLiveActivity> r1 = com.leedarson.newui.IpcLiveActivity.class
            r0.<init>(r13, r1)     // Catch:{ Exception -> 0x00a9 }
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Exception -> 0x00a9 }
            r1.<init>()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r2 = "devices"
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r8 = a     // Catch:{ Exception -> 0x00a9 }
            r1.putParcelableArrayList(r2, r8)     // Catch:{ Exception -> 0x00a9 }
            r0.putExtras(r1)     // Catch:{ Exception -> 0x00a9 }
            r13.startActivity(r0)     // Catch:{ Exception -> 0x00a9 }
            int r2 = com.leedarson.R$anim.ipc_slide_in_left     // Catch:{ Exception -> 0x00a9 }
            int r8 = com.leedarson.R$anim.ipc_slide_out_right     // Catch:{ Exception -> 0x00a9 }
            r13.overridePendingTransition(r2, r8)     // Catch:{ Exception -> 0x00a9 }
            goto L_0x00ad
        L_0x00a9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ipcservice.IpcServiceImpl.C(android.app.Activity):void");
    }

    public static IpcDeviceBean o(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 8040, new Class[]{String.class}, IpcDeviceBean.class);
        if (proxy.isSupported) {
            return (IpcDeviceBean) proxy.result;
        }
        for (int i2 = 0; i2 < a.size(); i2++) {
            if (deviceId.equals(a.get(i2).id)) {
                IpcDeviceBean deviceBean = a.get(i2);
                if (deviceBean != null && deviceBean.pushBean == null) {
                    String thingStr = SharePreferenceUtils.getPrefString(BaseApplication.b(), deviceBean.productId, "");
                    if (!TextUtils.isEmpty(thingStr)) {
                        deviceBean.pushBean = (PushBean) new Gson().fromJson(thingStr, new g().getType());
                    }
                }
                return deviceBean;
            }
        }
        return null;
    }

    public class g extends TypeToken<PushBean> {
        g() {
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Uri c;
        final /* synthetic */ int d;
        final /* synthetic */ int f;
        final /* synthetic */ int q;
        final /* synthetic */ int x;
        final /* synthetic */ int y;
        final /* synthetic */ FragmentActivity z;

        h(Uri uri, int i, int i2, int i3, int i4, int i5, FragmentActivity fragmentActivity) {
            this.c = uri;
            this.d = i;
            this.f = i2;
            this.q = i3;
            this.x = i4;
            this.y = i5;
            this.z = fragmentActivity;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8060, new Class[0], Void.TYPE).isSupported) {
                SnapAnimaFragment.o1(this.c, this.d, this.f, this.q, this.x, this.y).show(this.z.getSupportFragmentManager(), "snap");
            }
        }
    }

    public void showSnap(FragmentActivity fragmentActivity, Uri uri, int i2, int i3, int i4, int i5, int i6) {
        Object[] objArr = {fragmentActivity, uri, new Integer(i2), new Integer(i3), new Integer(i4), new Integer(i5), new Integer(i6)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {FragmentActivity.class, Uri.class, cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8041, clsArr, Void.TYPE).isSupported) {
            Uri uri2 = uri;
            int marginTop = i5;
            int height = i3;
            FragmentActivity activity = fragmentActivity;
            int width = i2;
            int marginRight = i6;
            int marginLeft = i4;
            if (activity != null) {
                activity.runOnUiThread(new h(uri2, width, height, marginLeft, marginTop, marginRight, activity));
            }
        }
    }

    public JSONObject getIPCDeviceInfoByDeviceId(String deviceId) {
        int i2 = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 8042, new Class[]{String.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        int i3 = 0;
        while (i3 < a.size()) {
            if (deviceId == null || !deviceId.equals(a.get(i3).id)) {
                i3++;
            } else {
                IpcDeviceBean _ipceDeviceBean = a.get(i3);
                JSONObject resultObj = new JSONObject();
                try {
                    resultObj.put("hardwareVersion", (Object) _ipceDeviceBean.hardwareVersion);
                    resultObj.put("firmwareVersion", (Object) _ipceDeviceBean.firmwareVersion);
                    if (!_ipceDeviceBean.online.booleanValue()) {
                        i2 = 0;
                    }
                    resultObj.put("onlineStatue", i2);
                    resultObj.put("modelId", (Object) _ipceDeviceBean.modelId);
                    resultObj.put("liveType", (Object) _ipceDeviceBean.props.liveType);
                    resultObj.put("p2pCache", (Object) _ipceDeviceBean.props.p2pCache);
                    resultObj.put("powerType", B(_ipceDeviceBean));
                    return resultObj;
                } catch (Exception e2) {
                    return new JSONObject();
                }
            }
        }
        return new JSONObject();
    }

    private int B(IpcDeviceBean ipcDeviceBean) {
        boolean isLowerPower = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ipcDeviceBean}, this, changeQuickRedirect, false, 8043, new Class[]{IpcDeviceBean.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String str = ipcDeviceBean.modelId;
        if (str != null && (str.contains("IPC.A001108") || ipcDeviceBean.modelId.contains("IPC.A001360") || ipcDeviceBean.modelId.contains("LK.IPC.A001513"))) {
            isLowerPower = true;
        }
        if (isLowerPower) {
            return 2;
        }
        return 1;
    }

    public void notifyOfSignalMessageComing(String deviceId, JSONObject jsonObjectBody) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, jsonObjectBody}, this, changeQuickRedirect, false, 8044, clsArr, Void.TYPE).isSupported) {
            com.leedarson.manager.a.i().p(deviceId, jsonObjectBody);
        }
    }

    public void onNetWorkChange(NetWorkStatusEvent netWorkStatusEvent) {
        if (!PatchProxy.proxy(new Object[]{netWorkStatusEvent}, this, changeQuickRedirect, false, 8045, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            NetWorkStatusEvent event = netWorkStatusEvent;
            long diffWithAppStart = System.currentTimeMillis() - BaseApplication.b().a1;
            long lastNotifyTimeDif = System.currentTimeMillis() - this.l;
            a.b g2 = timber.log.a.g("IpcServiceImpl");
            g2.h("[网络切换] IPC设备检测到网络连接发生变化 :  | " + event.getNetWorkStatus() + " 距离App启动时间--》" + diffWithAppStart + " ms lastNotifyTimeDif:" + lastNotifyTimeDif + " 网络是否可用：" + event.checkNetWorkEnable() + "  | " + event.toString(), new Object[0]);
            if (!event.checkNetWorkEnable() && diffWithAppStart > 10000 && lastNotifyTimeDif > GroupCtrlAdapter.RETRY_TIMEOUT) {
                timber.log.a.g("IpcServiceImpl").h("[网络切换] IPC 服务开始释放通道连接", new Object[0]);
                this.l = System.currentTimeMillis();
                com.leedarson.manager.a.i().e();
            }
            if (event.checkNetWorkEnable() && diffWithAppStart > 10000 && lastNotifyTimeDif > GroupCtrlAdapter.RETRY_TIMEOUT) {
                timber.log.a.g("IpcServiceImpl").h("[网络切换] IPC 服务开始释放通道连接", new Object[0]);
                this.l = System.currentTimeMillis();
                com.leedarson.manager.a.i().g();
            }
        }
    }

    public boolean checkWebRtcChannelIsFromBack(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 8046, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        f0 temp = com.leedarson.manager.a.i().j(deviceId);
        if (temp == null) {
            return false;
        }
        return temp.x1();
    }

    /* access modifiers changed from: private */
    /* renamed from: z */
    public /* synthetic */ void A(String deviceId, io.reactivex.f emitter) {
        if (!PatchProxy.proxy(new Object[]{deviceId, emitter}, this, changeQuickRedirect, false, 8048, new Class[]{String.class, io.reactivex.f.class}, Void.TYPE).isSupported) {
            this.m.k(deviceId, false).G(new com.leedarson.base.http.observer.j(2, 1500)).c(com.leedarson.base.http.observer.l.c()).I(new b(emitter, deviceId), new c(emitter, deviceId));
        }
    }

    public io.reactivex.e<kotlin.n<Boolean, String>> wakeUpDeviceById(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 8047, new Class[]{String.class}, io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : io.reactivex.e.d(new g(this, deviceId), io.reactivex.a.DROP);
    }

    static /* synthetic */ void x(io.reactivex.f emitter, String deviceId, LDSBaseBean ldsBaseBean) {
        Class[] clsArr = {io.reactivex.f.class, String.class, LDSBaseBean.class};
        if (!PatchProxy.proxy(new Object[]{emitter, deviceId, ldsBaseBean}, (Object) null, changeQuickRedirect, true, 8050, clsArr, Void.TYPE).isSupported) {
            emitter.onNext(new kotlin.n(true, "WebRTC 预连接唤醒接口调用成功--> " + ldsBaseBean.desc + "  deviceId=" + deviceId));
            emitter.onComplete();
        }
    }

    static /* synthetic */ void y(io.reactivex.f emitter, String deviceId, Throwable throwable) {
        Class[] clsArr = {io.reactivex.f.class, String.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{emitter, deviceId, throwable}, (Object) null, changeQuickRedirect, true, 8049, clsArr, Void.TYPE).isSupported) {
            emitter.onNext(new kotlin.n(true, "WebRTC 预连接唤醒接口调用失败--> " + throwable.toString() + "  deviceId=" + deviceId));
            emitter.onComplete();
        }
    }
}
