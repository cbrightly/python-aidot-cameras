package com.sensorsdata.analytics.android.sdk;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionRelatedManager {
    private static volatile SessionRelatedManager mSessionRelatedManager = null;
    public final String EVENT_SESSION_ID = "$event_session_id";
    private final String KEY_LAST_EVENT_TIME = "lastEventTime";
    private final String KEY_SESSION_ID = "sessionID";
    private final String KEY_START_TIME = "startTime";
    private final long SESSION_LAST_INTERVAL_TIME = 1800000;
    private final long SESSION_START_INTERVAL_TIME = 43200000;
    private final String SHARED_PREF_SESSION_CUTDATA = "sensorsdata.session.cutdata";
    private long mLastEventTime;
    private String mSessionID;
    private long mStartTime;

    public static SessionRelatedManager getInstance() {
        if (mSessionRelatedManager == null) {
            synchronized (SessionRelatedManager.class) {
                if (mSessionRelatedManager == null) {
                    mSessionRelatedManager = new SessionRelatedManager();
                }
            }
        }
        return mSessionRelatedManager;
    }

    private SessionRelatedManager() {
        try {
            if (!AbstractSensorsDataAPI.getConfigOptions().isEnableSession()) {
                deleteSessionData();
            } else {
                readSessionData();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void handleEventOfSession(String eventName, JSONObject property, long time) {
        if (AbstractSensorsDataAPI.getConfigOptions().isEnableSession()) {
            try {
                handleSessionState(time);
                if ("$AppEnd".equals(eventName)) {
                    if (!property.has("$event_session_id")) {
                        return;
                    }
                }
                property.put("$event_session_id", (Object) this.mSessionID);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
    }

    private void updateSessionLastTime(long eventTime) {
        this.mLastEventTime = eventTime;
        SAStoreManager.getInstance().setString("sensorsdata.session.cutdata", getSessionDataPack());
    }

    private void deleteSessionData() {
        this.mSessionID = null;
        this.mStartTime = -1;
        this.mLastEventTime = -1;
        SAStoreManager.getInstance().remove("sensorsdata.session.cutdata");
    }

    private void createSessionData(long eventTime, boolean isSessionTypeByEvent) {
        this.mSessionID = UUID.randomUUID().toString();
        if (isSessionTypeByEvent) {
            this.mStartTime = eventTime;
        }
        this.mLastEventTime = Math.max(eventTime, this.mLastEventTime);
        SAStoreManager.getInstance().setString("sensorsdata.session.cutdata", getSessionDataPack());
    }

    private void readSessionData() {
        String sessionJson = SAStoreManager.getInstance().getString("sensorsdata.session.cutdata", "");
        if (!TextUtils.isEmpty(sessionJson)) {
            try {
                JSONObject jsonObject = new JSONObject(sessionJson);
                if (jsonObject.has("sessionID")) {
                    this.mSessionID = jsonObject.optString("sessionID");
                }
                if (jsonObject.has("startTime")) {
                    this.mStartTime = jsonObject.optLong("startTime");
                }
                if (jsonObject.has("lastEventTime")) {
                    this.mLastEventTime = jsonObject.optLong("lastEventTime");
                }
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
    }

    private void handleSessionState(long eventTime) {
        if (eventTime > 0) {
            if (TextUtils.isEmpty(this.mSessionID) || eventTime - this.mLastEventTime > 1800000 || eventTime - this.mStartTime > 43200000) {
                createSessionData(eventTime, true);
            } else {
                updateSessionLastTime(eventTime);
            }
        }
    }

    public void refreshSessionByTimer(long refreshTime) {
        if (refreshTime - this.mLastEventTime > 1800000) {
            createSessionData(refreshTime, false);
        }
    }

    private String getSessionDataPack() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sessionID", (Object) this.mSessionID);
            jsonObject.put("startTime", this.mStartTime);
            jsonObject.put("lastEventTime", this.mLastEventTime);
            return jsonObject.toString();
        } catch (JSONException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public String getSessionID() {
        return this.mSessionID;
    }
}
