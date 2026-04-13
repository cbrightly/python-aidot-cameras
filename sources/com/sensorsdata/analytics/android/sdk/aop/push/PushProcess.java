package com.sensorsdata.analytics.android.sdk.aop.push;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.ThreadNameConstants;
import com.sensorsdata.analytics.android.sdk.encrypt.AESSecretManager;
import com.sensorsdata.analytics.android.sdk.util.FileUtils;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class PushProcess {
    private static final String DIR_NAME = "sensors.push";
    private static final int GT_PUSH_MSG = 1;
    private static PushProcess INSTANCE = null;
    private static final String SA_PUSH_ID = "SA_PUSH_ID";
    private static final String TAG = "SA.NotificationProcessor";
    private final boolean customizeEnable;
    /* access modifiers changed from: private */
    public final Map<String, NotificationInfo> mGeTuiPushInfoMap;
    private WeakReference<Intent> mLastIntentRef;
    private final WeakHashMap<PendingIntent, String> mPendingIntent2Ids;
    private File mPushFile;
    private final Handler mPushHandler;
    private final AtomicInteger mSAIntentId;
    private final int myPid;

    private PushProcess() {
        Context context = SensorsDataAPI.sharedInstance().getContext();
        if (context != null) {
            this.mPushFile = new File(context.getFilesDir(), DIR_NAME);
        }
        this.mSAIntentId = new AtomicInteger();
        this.myPid = Process.myPid();
        this.customizeEnable = Build.VERSION.SDK_INT >= 19;
        this.mPendingIntent2Ids = new WeakHashMap<>();
        this.mGeTuiPushInfoMap = new HashMap();
        HandlerThread thread = new HandlerThread(ThreadNameConstants.THREAD_PUSH_HANDLER);
        thread.start();
        this.mPushHandler = new Handler(thread.getLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try {
                        String msgId = (String) msg.obj;
                        if (!TextUtils.isEmpty(msgId) && PushProcess.this.mGeTuiPushInfoMap.containsKey(msgId)) {
                            NotificationInfo push = (NotificationInfo) PushProcess.this.mGeTuiPushInfoMap.get(msgId);
                            PushProcess.this.mGeTuiPushInfoMap.remove(msgId);
                            if (push != null) {
                                PushAutoTrackHelper.trackGeTuiNotificationClicked(push.title, push.content, (String) null, push.time);
                            }
                        }
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            }
        };
    }

    public static synchronized PushProcess getInstance() {
        PushProcess pushProcess;
        synchronized (PushProcess.class) {
            if (INSTANCE == null) {
                INSTANCE = new PushProcess();
            }
            pushProcess = INSTANCE;
        }
        return pushProcess;
    }

    public void hookIntent(Intent intent) {
        if (this.customizeEnable) {
            try {
                if (!isHooked(intent)) {
                    intent.putExtra(SA_PUSH_ID, this.myPid + "-" + this.mSAIntentId.getAndIncrement());
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void hookPendingIntent(Intent intent, PendingIntent pendingIntent) {
        if (this.customizeEnable) {
            this.mPendingIntent2Ids.put(pendingIntent, intent.getStringExtra(SA_PUSH_ID));
        }
    }

    public void onNotificationClick(Context context, Intent intent) {
        if (intent != null) {
            try {
                WeakReference<Intent> weakReference = this.mLastIntentRef;
                if (weakReference == null || weakReference.get() != intent) {
                    this.mLastIntentRef = new WeakReference<>(intent);
                    if (this.customizeEnable) {
                        trackCustomizeClick(intent);
                    }
                    if (context instanceof Activity) {
                        PushAutoTrackHelper.trackJPushOpenActivity(intent);
                    }
                    SALog.i(TAG, "onNotificationClick");
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void onNotify(String tag, int id, final Notification notification) {
        if (this.customizeEnable) {
            try {
                if (notification.contentIntent != null) {
                    SALog.i(TAG, "onNotify, tag: " + tag + ", id=" + id);
                    final NotificationInfo push = getNotificationInfo(notification);
                    if (push != null) {
                        this.mPushHandler.post(new Runnable() {
                            public void run() {
                                PushProcess.this.checkAndStoreNotificationInfo(notification.contentIntent, push);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void trackGTClickDelayed(String messageId, String title, String content) {
        try {
            Message message = Message.obtain();
            message.what = 1;
            message.obj = messageId;
            this.mGeTuiPushInfoMap.put(messageId, new NotificationInfo(title, content, System.currentTimeMillis()));
            this.mPushHandler.sendMessageDelayed(message, 200);
            SALog.i(TAG, "sendMessageDelayed,msgId = " + messageId);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void trackReceiveMessageData(String sfDate, String msgId) {
        try {
            if (this.mPushHandler.hasMessages(1) && this.mGeTuiPushInfoMap.containsKey(msgId)) {
                this.mPushHandler.removeMessages(1);
                SALog.i(TAG, "remove GeTui Push Message");
                NotificationInfo push = this.mGeTuiPushInfoMap.get(msgId);
                if (push != null) {
                    PushAutoTrackHelper.trackGeTuiNotificationClicked(push.title, push.content, sfDate, push.time);
                }
                this.mGeTuiPushInfoMap.remove(msgId);
                SALog.i(TAG, " onGeTuiReceiveMessage:msg id : " + msgId);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private boolean isHooked(Intent intent) {
        try {
            return intent.hasExtra(SA_PUSH_ID);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void checkAndStoreNotificationInfo(PendingIntent pendingIntent, NotificationInfo info) {
        if (pendingIntent == null) {
            SALog.i(TAG, "pendingIntent is null");
            return;
        }
        try {
            String intentId = this.mPendingIntent2Ids.get(pendingIntent);
            if (intentId != null) {
                storeNotificationInfo(info, intentId);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void storeNotificationInfo(NotificationInfo push, String intentId) {
        SALog.i(TAG, "storeNotificationInfo: id=" + intentId + ", actionInfo" + push);
        try {
            initAndCleanDir();
            File toFile = new File(this.mPushFile, intentId);
            if (toFile.exists()) {
                SALog.i(TAG, "toFile exists");
                toFile.delete();
            }
            FileUtils.writeToFile(toFile, AESSecretManager.getInstance().encryptAES(push.toJson()));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private synchronized void initAndCleanDir() {
        try {
            if (!this.mPushFile.exists()) {
                this.mPushFile.mkdirs();
            }
            File[] files = this.mPushFile.listFiles();
            if (files != null) {
                long currentTime = System.currentTimeMillis();
                for (File file : files) {
                    if (currentTime - file.lastModified() > CostTimeUtil.DAY) {
                        SALog.i(TAG, "clean file: " + file.toString());
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return;
    }

    private NotificationInfo getNotificationInfo(Notification notification) {
        NotificationInfo push = null;
        if (Build.VERSION.SDK_INT < 19) {
            return null;
        }
        try {
            String title = notification.extras.getString(NotificationCompat.EXTRA_TITLE);
            String content = notification.extras.getString(NotificationCompat.EXTRA_TEXT);
            push = new NotificationInfo(title, content, 0);
            SALog.i(TAG, "NotificationInfo: title = " + title + "content = " + content);
            return push;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return push;
        }
    }

    /* access modifiers changed from: private */
    public NotificationInfo getNotificationInfo(String id) {
        try {
            initAndCleanDir();
            File inFile = new File(this.mPushFile, id);
            if (!inFile.exists()) {
                return null;
            }
            String json = FileUtils.readFileToString(inFile);
            if (TextUtils.isEmpty(json)) {
                return null;
            }
            String decryptJson = AESSecretManager.getInstance().decryptAES(json);
            SALog.i(TAG, "cache local notification info:" + decryptJson);
            return NotificationInfo.fromJson(decryptJson);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private void trackCustomizeClick(Intent intent) {
        if (this.customizeEnable) {
            try {
                if (isHooked(intent)) {
                    final String id = intent.getStringExtra(SA_PUSH_ID);
                    intent.removeExtra(SA_PUSH_ID);
                    if (TextUtils.isEmpty(id)) {
                        SALog.i(TAG, "intent tag is null");
                    } else {
                        this.mPushHandler.post(new Runnable() {
                            public void run() {
                                NotificationInfo push = PushProcess.this.getNotificationInfo(id);
                                if (push != null) {
                                    PushAutoTrackHelper.trackNotificationOpenedEvent((String) null, push.title, push.content, "Local", (String) null);
                                }
                            }
                        });
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public static class NotificationInfo {
        String content;
        long time;
        String title;

        NotificationInfo(String title2, String content2, long time2) {
            this.title = title2;
            this.content = content2;
            this.time = time2;
        }

        public static NotificationInfo fromJson(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                return new NotificationInfo(jsonObject.optString("title"), jsonObject.optString(FirebaseAnalytics.Param.CONTENT), jsonObject.optLong("time"));
            } catch (JSONException e) {
                SALog.printStackTrace(e);
                return null;
            }
        }

        public String toString() {
            return "NotificationInfo{title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", time=" + this.time + '}';
        }

        public String toJson() {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", (Object) this.title);
                jsonObject.put(FirebaseAnalytics.Param.CONTENT, (Object) this.content);
                jsonObject.put("time", this.time);
                return jsonObject.toString();
            } catch (JSONException e) {
                SALog.printStackTrace(e);
                return null;
            }
        }
    }
}
