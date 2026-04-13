package com.google.firebase.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.firebase.messaging.Constants;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class FirebaseMessagingService extends EnhancedIntentService {
    public static final String ACTION_DIRECT_BOOT_REMOTE_INTENT = "com.google.firebase.messaging.RECEIVE_DIRECT_BOOT";
    static final String ACTION_NEW_TOKEN = "com.google.firebase.messaging.NEW_TOKEN";
    static final String ACTION_REMOTE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    static final String EXTRA_TOKEN = "token";
    private static final int RECENTLY_RECEIVED_MESSAGE_IDS_MAX_SIZE = 10;
    private static final Queue<String> recentlyReceivedMessageIds = new ArrayDeque(10);

    @WorkerThread
    public void onMessageReceived(@NonNull RemoteMessage message) {
    }

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageSent(@NonNull String msgId) {
    }

    @WorkerThread
    public void onSendError(@NonNull String msgId, @NonNull Exception exception) {
    }

    @WorkerThread
    public void onNewToken(@NonNull String token) {
    }

    /* access modifiers changed from: protected */
    public Intent getStartCommandIntent(Intent originalIntent) {
        return ServiceStarter.getInstance().getMessagingEvent();
    }

    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (ACTION_REMOTE_INTENT.equals(action) || ACTION_DIRECT_BOOT_REMOTE_INTENT.equals(action)) {
            handleMessageIntent(intent);
        } else if (ACTION_NEW_TOKEN.equals(action)) {
            onNewToken(intent.getStringExtra(EXTRA_TOKEN));
        } else {
            Log.d(Constants.TAG, "Unknown intent action: " + intent.getAction());
        }
    }

    private void handleMessageIntent(Intent intent) {
        if (!alreadyReceivedMessage(intent.getStringExtra(Constants.MessagePayloadKeys.MSGID))) {
            passMessageIntentToSdk(intent);
        }
    }

    private void passMessageIntentToSdk(Intent intent) {
        String messageType = intent.getStringExtra(Constants.MessagePayloadKeys.MESSAGE_TYPE);
        if (messageType == null) {
            messageType = Constants.MessageTypes.MESSAGE;
        }
        char c = 65535;
        switch (messageType.hashCode()) {
            case -2062414158:
                if (messageType.equals(Constants.MessageTypes.DELETED)) {
                    c = 1;
                    break;
                }
                break;
            case 102161:
                if (messageType.equals(Constants.MessageTypes.MESSAGE)) {
                    c = 0;
                    break;
                }
                break;
            case 814694033:
                if (messageType.equals(Constants.MessageTypes.SEND_ERROR)) {
                    c = 3;
                    break;
                }
                break;
            case 814800675:
                if (messageType.equals(Constants.MessageTypes.SEND_EVENT)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                MessagingAnalytics.logNotificationReceived(intent);
                dispatchMessage(intent);
                return;
            case 1:
                onDeletedMessages();
                return;
            case 2:
                onMessageSent(intent.getStringExtra(Constants.MessagePayloadKeys.MSGID));
                return;
            case 3:
                onSendError(getMessageId(intent), new SendException(intent.getStringExtra("error")));
                return;
            default:
                Log.w(Constants.TAG, "Received message with unknown type: " + messageType);
                return;
        }
    }

    private void dispatchMessage(Intent intent) {
        Bundle data = intent.getExtras();
        if (data == null) {
            data = new Bundle();
        }
        data.remove("androidx.content.wakelockid");
        if (NotificationParams.isNotification(data)) {
            NotificationParams params = new NotificationParams(data);
            ExecutorService executor = FcmExecutors.newNetworkIOExecutor();
            try {
                if (!new DisplayNotification(this, params, executor).handleNotification()) {
                    executor.shutdown();
                    if (MessagingAnalytics.shouldUploadScionMetrics(intent)) {
                        MessagingAnalytics.logNotificationForeground(intent);
                    }
                } else {
                    return;
                }
            } finally {
                executor.shutdown();
            }
        }
        onMessageReceived(new RemoteMessage(data));
    }

    private boolean alreadyReceivedMessage(String messageId) {
        if (TextUtils.isEmpty(messageId)) {
            return false;
        }
        Queue<String> queue = recentlyReceivedMessageIds;
        if (!queue.contains(messageId)) {
            if (queue.size() >= 10) {
                queue.remove();
            }
            queue.add(messageId);
            return false;
        } else if (!Log.isLoggable(Constants.TAG, 3)) {
            return true;
        } else {
            Log.d(Constants.TAG, "Received duplicate message: " + messageId);
            return true;
        }
    }

    private String getMessageId(Intent intent) {
        String messageId = intent.getStringExtra(Constants.MessagePayloadKeys.MSGID);
        if (messageId == null) {
            return intent.getStringExtra(Constants.MessagePayloadKeys.MSGID_SERVER);
        }
        return messageId;
    }

    @VisibleForTesting
    static void resetForTesting() {
        recentlyReceivedMessageIds.clear();
    }
}
