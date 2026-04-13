package com.sensorsdata.analytics.android.sdk.monitor;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.leedarson.serviceinterface.IMService;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.listener.SAFunctionListener;
import java.util.ArrayList;
import java.util.List;
import meshsdk.cache.CacheHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class TrackMonitor {
    /* access modifiers changed from: private */
    public JSONObject cacheData;
    private List<SAFunctionListener> mFunctionListener;

    private TrackMonitor() {
    }

    public static class SingletonHolder {
        /* access modifiers changed from: private */
        public static final TrackMonitor INSTANCE = new TrackMonitor();

        private SingletonHolder() {
        }
    }

    private void call(String function, JSONObject jsonObject) {
        List<SAFunctionListener> list;
        if (!TextUtils.isEmpty(function) && (list = this.mFunctionListener) != null) {
            for (SAFunctionListener listener : list) {
                listener.call(function, jsonObject);
            }
        }
    }

    public static TrackMonitor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void addFunctionListener(SAFunctionListener functionListener) {
        try {
            if (this.mFunctionListener == null) {
                this.mFunctionListener = new ArrayList();
            }
            if (functionListener != null && !this.mFunctionListener.contains(functionListener)) {
                this.mFunctionListener.add(functionListener);
            }
            JSONObject jsonObject = this.cacheData;
            if (jsonObject != null) {
                call("trackEvent", jsonObject);
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void removeFunctionListener(SAFunctionListener functionListener) {
        List<SAFunctionListener> list = this.mFunctionListener;
        if (list != null && functionListener != null) {
            list.remove(functionListener);
        }
    }

    public void callTrack(JSONObject eventObject) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventJSON", (Object) eventObject);
            if (!"$AppStart".equals(eventObject.optString(NotificationCompat.CATEGORY_EVENT)) || this.mFunctionListener != null) {
                call("trackEvent", jsonObject);
                return;
            }
            this.cacheData = jsonObject;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    JSONObject unused = TrackMonitor.this.cacheData = null;
                }
            }, CacheHandler.delayTime);
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public void callResetAnonymousId(String newDistinctId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("distinctId", (Object) newDistinctId);
            call("resetAnonymousId", jsonObject);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void callLogin(String loginId) {
        if (this.mFunctionListener != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("distinctId", (Object) loginId);
                call("login", jsonObject);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void callLogout() {
        call(IMService.LOGOUT, (JSONObject) null);
    }

    public void callIdentify(String distinctId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("distinctId", (Object) distinctId);
            call("identify", jsonObject);
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public void callEnableDataCollect() {
        call("enableDataCollect", (JSONObject) null);
    }
}
