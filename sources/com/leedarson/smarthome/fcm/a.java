package com.leedarson.smarthome.fcm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.bumptech.glide.request.target.h;
import com.bumptech.glide.request.transition.b;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.iotsolution.preaidot.R;
import com.leedarson.base.utils.c;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smarthome.ui.MainActivity;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: NotificationUtils */
public class a {
    public static String a = "NotificationUtils";
    private static Handler b;
    public static ChangeQuickRedirect changeQuickRedirect;

    static /* synthetic */ PendingIntent a(Context x0, String x1, int x2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1, new Integer(x2)}, (Object) null, changeQuickRedirect, true, 10670, new Class[]{Context.class, String.class, Integer.TYPE}, PendingIntent.class);
        return proxy.isSupported ? (PendingIntent) proxy.result : e(x0, x1, x2);
    }

    public static void c(Context context, String str, String str2, String str3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, str, str2, str3}, (Object) null, changeQuickRedirect, true, 10665, new Class[]{Context.class, cls, cls, cls}, Void.TYPE).isSupported) {
            Context context2 = context;
            String messageBody = str2;
            String title = str;
            String tag = str3;
            a.b g = timber.log.a.g(a);
            g.h("sendNotification: " + messageBody, new Object[0]);
            if (messageBody != null) {
                try {
                    int notifyId = SharePreferenceUtils.getPrefInt(context2, "notify_id", 0);
                    PendingIntent mp = e(context2, tag, notifyId);
                    String channelId = context2.getString(R.string.default_notification_channel_id);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context2, channelId).setSmallIcon((int) R.drawable.noti_small).setLargeIcon(BitmapFactory.decodeResource(context2.getResources(), R.mipmap.logo)).setColor(context2.getResources().getColor(R.color.noti_bg_color)).setContentTitle(title).setContentText(messageBody).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setTicker(title).setDefaults(-1).setPriority(1).setContentIntent(mp);
                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                    bigTextStyle.setBigContentTitle(title).bigText(messageBody);
                    notificationBuilder.setStyle(bigTextStyle);
                    NotificationManager notificationManager = (NotificationManager) context2.getSystemService("notification");
                    if (Build.VERSION.SDK_INT >= 26) {
                        notificationManager.createNotificationChannel(new NotificationChannel(channelId, "Channel human readable title", 3));
                    }
                    Notification build = notificationBuilder.build();
                    notificationManager.notify(notifyId, build);
                    PushAutoTrackHelper.onNotify(notificationManager, notifyId, build);
                    SharePreferenceUtils.setPrefInt(context2, "notify_id", notifyId + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void d(Context context, String str, String str2, String str3, String str4) {
        Class<String> cls = String.class;
        Class[] clsArr = {Context.class, cls, cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{context, str, str2, str3, str4}, (Object) null, changeQuickRedirect, true, 10666, clsArr, Void.TYPE).isSupported) {
            Context context2 = context;
            String messageBody = str2;
            String extra = str4;
            String title = str;
            String url = str3;
            a.b g = timber.log.a.g(a);
            g.h("sendNotification message:" + messageBody + " tag:" + extra + " title:" + title + ",url:" + url, new Object[0]);
            JSONObject pushObj = new JSONObject();
            try {
                pushObj.put("title", (Object) title);
                pushObj.put(FirebaseAnalytics.Param.CONTENT, (Object) messageBody);
                pushObj.put("extraData", (Object) extra);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String tag = pushObj.toString();
            if (TextUtils.isEmpty(url)) {
                c(context2, title, messageBody, tag);
                return;
            }
            try {
                if (b == null) {
                    b = new Handler(Looper.getMainLooper());
                }
                Handler handler = b;
                C0182a aVar = r1;
                C0182a aVar2 = new C0182a(context2, url, title, messageBody, tag);
                handler.post(aVar);
            } catch (Exception e2) {
                a.b g2 = timber.log.a.g(a);
                String str5 = a;
                g2.c(str5, "sendNotification: error" + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.leedarson.smarthome.fcm.a$a  reason: collision with other inner class name */
    /* compiled from: NotificationUtils */
    public class C0182a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;
        final /* synthetic */ String d;
        final /* synthetic */ String f;
        final /* synthetic */ String q;
        final /* synthetic */ String x;

        C0182a(Context context, String str, String str2, String str3, String str4) {
            this.c = context;
            this.d = str;
            this.f = str2;
            this.q = str3;
            this.x = str4;
        }

        /* renamed from: com.leedarson.smarthome.fcm.a$a$a  reason: collision with other inner class name */
        /* compiled from: NotificationUtils */
        public class C0183a extends h<Bitmap> {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0183a() {
            }

            public /* bridge */ /* synthetic */ void d(@NonNull Object obj, @Nullable b bVar) {
                Class[] clsArr = {Object.class, b.class};
                if (!PatchProxy.proxy(new Object[]{obj, bVar}, this, changeQuickRedirect, false, 10674, clsArr, Void.TYPE).isSupported) {
                    h((Bitmap) obj, bVar);
                }
            }

            public void f(@Nullable Drawable errorDrawable) {
                if (!PatchProxy.proxy(new Object[]{errorDrawable}, this, changeQuickRedirect, false, 10672, new Class[]{Drawable.class}, Void.TYPE).isSupported) {
                    super.f(errorDrawable);
                    timber.log.a.g(a.a).h("sendNotification:onLoadFailed", new Object[0]);
                    C0182a aVar = C0182a.this;
                    a.c(aVar.c, aVar.f, aVar.q, aVar.x);
                }
            }

            public void h(@NonNull Bitmap resource, @Nullable b<? super Bitmap> bVar) {
                if (!PatchProxy.proxy(new Object[]{resource, bVar}, this, changeQuickRedirect, false, 10673, new Class[]{Bitmap.class, b.class}, Void.TYPE).isSupported) {
                    timber.log.a.g(a.a).h("sendNotification: onResourceReady", new Object[0]);
                    C0182a aVar = C0182a.this;
                    if (aVar.q != null) {
                        int notifyId = SharePreferenceUtils.getPrefInt(aVar.c, "notify_id", 0);
                        C0182a aVar2 = C0182a.this;
                        PendingIntent mp = a.a(aVar2.c, aVar2.x, notifyId);
                        String channelId = C0182a.this.c.getString(R.string.default_notification_channel_id);
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(C0182a.this.c, channelId).setSmallIcon((int) R.drawable.noti_small).setLargeIcon(BitmapFactory.decodeResource(C0182a.this.c.getResources(), R.mipmap.logo)).setColor(C0182a.this.c.getResources().getColor(R.color.noti_bg_color)).setContentTitle(C0182a.this.f).setContentText(C0182a.this.q).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setTicker(C0182a.this.f).setDefaults(-1).setPriority(1).setContentIntent(mp);
                        NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle();
                        pictureStyle.bigPicture(resource);
                        pictureStyle.setBigContentTitle(C0182a.this.f);
                        pictureStyle.bigLargeIcon(BitmapFactory.decodeResource(C0182a.this.c.getResources(), R.mipmap.logo));
                        notificationBuilder.setStyle(pictureStyle);
                        NotificationManager notificationManager = (NotificationManager) C0182a.this.c.getSystemService("notification");
                        if (Build.VERSION.SDK_INT >= 26) {
                            notificationManager.createNotificationChannel(new NotificationChannel(channelId, "Channel human readable title", 3));
                        }
                        Notification build = notificationBuilder.build();
                        notificationManager.notify(notifyId, build);
                        PushAutoTrackHelper.onNotify(notificationManager, notifyId, build);
                        SharePreferenceUtils.setPrefInt(C0182a.this.c, "notify_id", notifyId + 1);
                    }
                }
            }
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10671, new Class[0], Void.TYPE).isSupported) {
                com.bumptech.glide.b.t(this.c).i().M0(this.d).D0(new C0183a());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00c2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.app.PendingIntent e(android.content.Context r12, java.lang.String r13, int r14) {
        /*
            java.lang.String r0 = "houseId"
            java.lang.String r1 = "data"
            r2 = 3
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r10 = 0
            r3[r10] = r12
            r11 = 1
            r3[r11] = r13
            java.lang.Integer r4 = new java.lang.Integer
            r4.<init>(r14)
            r5 = 2
            r3[r5] = r4
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r2]
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r8[r10] = r2
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r8[r11] = r2
            java.lang.Class r2 = java.lang.Integer.TYPE
            r8[r5] = r2
            java.lang.Class<android.app.PendingIntent> r9 = android.app.PendingIntent.class
            r4 = 0
            r2 = 1
            r7 = 10668(0x29ac, float:1.4949E-41)
            r5 = r6
            r6 = r2
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r3 = r2.isSupported
            if (r3 == 0) goto L_0x003a
            java.lang.Object r12 = r2.result
            android.app.PendingIntent r12 = (android.app.PendingIntent) r12
            return r12
        L_0x003a:
            java.lang.String r2 = ""
            android.content.Intent r3 = new android.content.Intent
            java.lang.Class<smarthome.receiver.NotificationBroadcastReceiver> r4 = smarthome.receiver.NotificationBroadcastReceiver.class
            r3.<init>(r12, r4)
            java.lang.String r4 = "Notification_action"
            r3.setAction(r4)
            java.lang.String r4 = "type"
            r3.putExtra(r4, r11)
            r4 = 268435456(0x10000000, float:2.5243549E-29)
            r3.setFlags(r4)
            if (r13 == 0) goto L_0x009b
            java.lang.String r4 = "push_data"
            r3.putExtra(r4, r13)
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x008a }
            r4.<init>((java.lang.String) r13)     // Catch:{ JSONException -> 0x008a }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x008a }
            java.lang.String r6 = "extraData"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ JSONException -> 0x008a }
            r5.<init>((java.lang.String) r6)     // Catch:{ JSONException -> 0x008a }
            boolean r6 = r5.has(r1)     // Catch:{ JSONException -> 0x008a }
            if (r6 == 0) goto L_0x0089
            org.json.JSONObject r1 = r5.getJSONObject(r1)     // Catch:{ JSONException -> 0x008a }
            boolean r6 = r1.has(r0)     // Catch:{ JSONException -> 0x008a }
            if (r6 == 0) goto L_0x0089
            java.lang.String r0 = r1.getString(r0)     // Catch:{ JSONException -> 0x008a }
            java.lang.String r2 = "notification_houseId"
            r3.putExtra(r2, r0)     // Catch:{ JSONException -> 0x0086 }
            r2 = r0
            goto L_0x0089
        L_0x0086:
            r1 = move-exception
            r2 = r0
            goto L_0x008b
        L_0x0089:
            goto L_0x009b
        L_0x008a:
            r1 = move-exception
        L_0x008b:
            r0 = r1
            java.lang.String r1 = "NotificationUtil"
            timber.log.a$b r1 = timber.log.a.g(r1)
            java.lang.String r4 = r0.getMessage()
            java.lang.Object[] r5 = new java.lang.Object[r10]
            r1.c(r4, r5)
        L_0x009b:
            r0 = 0
            boolean r1 = com.leedarson.base.utils.w.R()
            if (r1 == 0) goto L_0x00c2
            android.content.Intent r1 = b(r12, r13, r2)
            r4 = 67108864(0x4000000, float:1.5046328E-36)
            if (r1 == 0) goto L_0x00b6
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.hookIntentGetActivity(r12, r14, r1, r4)
            android.app.PendingIntent r5 = android.app.PendingIntent.getActivity(r12, r14, r1, r4)
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.hookPendingIntentGetActivity(r5, r12, r14, r1, r4)
            r0 = r5
            goto L_0x00c1
        L_0x00b6:
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.hookIntentGetBroadcast(r12, r14, r3, r4)
            android.app.PendingIntent r5 = android.app.PendingIntent.getBroadcast(r12, r14, r3, r4)
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.hookPendingIntentGetBroadcast(r5, r12, r14, r3, r4)
            r0 = r5
        L_0x00c1:
            goto L_0x00cf
        L_0x00c2:
            r1 = 134217728(0x8000000, float:3.85186E-34)
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.hookIntentGetBroadcast(r12, r14, r3, r1)
            android.app.PendingIntent r4 = android.app.PendingIntent.getBroadcast(r12, r14, r3, r1)
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.hookPendingIntentGetBroadcast(r4, r12, r14, r3, r1)
            r0 = r4
        L_0x00cf:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smarthome.fcm.a.e(android.content.Context, java.lang.String, int):android.app.PendingIntent");
    }

    public static Intent b(Context context, String push_data, String notification_houseId) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, push_data, notification_houseId}, (Object) null, changeQuickRedirect2, true, 10669, new Class[]{Context.class, cls, cls}, Intent.class);
        if (proxy.isSupported) {
            return (Intent) proxy.result;
        }
        Activity currentActivity = c.h().c();
        Bundle bundle = new Bundle();
        bundle.putString("push_data", push_data);
        bundle.putString("notification_houseId", notification_houseId);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(bundle);
        if (currentActivity == null) {
            timber.log.a.g("NotificationUtil").a("android 12 getAndroidSIntent currentActivity == null", new Object[0]);
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        } else {
            timber.log.a.g("NotificationUtil").a("android 12 getAndroidSIntent currentActivity is running", new Object[0]);
            intent.addFlags(131072);
        }
        return intent;
    }
}
