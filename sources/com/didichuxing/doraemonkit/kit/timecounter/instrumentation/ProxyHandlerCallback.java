package com.didichuxing.doraemonkit.kit.timecounter.instrumentation;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterManager;
import com.didichuxing.doraemonkit.util.Reflector;

public class ProxyHandlerCallback implements Handler.Callback {
    private static final int EXECUTE_TRANSACTION = 159;
    private static final int LAUNCH_ACTIVITY = 100;
    private static final String LAUNCH_ITEM_CLASS = "android.app.servertransaction.ResumeActivityItem";
    private static final int PAUSE_ACTIVITY = 101;
    private static final String PAUSE_ITEM_CLASS = "android.app.servertransaction.PauseActivityItem";
    private static final String TAG = "ProxyHandlerCallback";
    public final Handler mHandler;
    private final Handler.Callback mOldCallback;

    ProxyHandlerCallback(Handler.Callback oldCallback, Handler handler) {
        this.mOldCallback = oldCallback;
        this.mHandler = handler;
    }

    public boolean handleMessage(Message msg) {
        int msgType = preDispatch(msg);
        Handler.Callback callback = this.mOldCallback;
        if (callback == null || !callback.handleMessage(msg)) {
            this.mHandler.handleMessage(msg);
            postDispatch(msgType);
            return true;
        }
        postDispatch(msgType);
        return true;
    }

    private int preDispatch(Message msg) {
        switch (msg.what) {
            case 100:
                TimeCounterManager.get().onActivityLaunch();
                break;
            case 101:
                TimeCounterManager.get().onActivityPause();
                break;
            case 159:
                return handlerActivity(msg);
        }
        return msg.what;
    }

    private int handlerActivity(Message msg) {
        Object activityCallback = Reflector.QuietReflector.with(msg.obj).method("getLifecycleStateRequest", (Class<?>[]) new Class[0]).call(new Object[0]);
        if (activityCallback != null) {
            String transactionName = activityCallback.getClass().getCanonicalName();
            if (TextUtils.equals(transactionName, LAUNCH_ITEM_CLASS)) {
                TimeCounterManager.get().onActivityLaunch();
                return 100;
            } else if (TextUtils.equals(transactionName, PAUSE_ITEM_CLASS)) {
                TimeCounterManager.get().onActivityPause();
                return 101;
            }
        }
        return msg.what;
    }

    private void postDispatch(int msgType) {
        switch (msgType) {
            case 100:
                TimeCounterManager.get().onActivityLaunched();
                return;
            case 101:
                TimeCounterManager.get().onActivityPaused();
                return;
            default:
                return;
        }
    }
}
