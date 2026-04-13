package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.android.gms.cloudmessaging.CloudMessage;
import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.FcmBroadcastProcessor;
import com.google.firebase.messaging.MessagingAnalytics;
import java.util.concurrent.ExecutionException;

public final class FirebaseInstanceIdReceiver extends CloudMessagingReceiver {
    private static final String TAG = "FirebaseMessaging";

    private static Intent createServiceIntent(@NonNull Context context, @NonNull String action, @NonNull Bundle data) {
        return new Intent(action).putExtras(data);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public int onMessageReceive(@NonNull Context context, @NonNull CloudMessage message) {
        try {
            return ((Integer) Tasks.await(new FcmBroadcastProcessor(context).process(message.getIntent()))).intValue();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("FirebaseMessaging", "Failed to send message to service.", e);
            return 500;
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public void onNotificationDismissed(@NonNull Context context, @NonNull Bundle data) {
        Intent notificationDismissedIntent = createServiceIntent(context, CloudMessagingReceiver.IntentActionKeys.NOTIFICATION_DISMISS, data);
        if (MessagingAnalytics.shouldUploadScionMetrics(notificationDismissedIntent)) {
            MessagingAnalytics.logNotificationDismiss(notificationDismissedIntent);
        }
    }
}
