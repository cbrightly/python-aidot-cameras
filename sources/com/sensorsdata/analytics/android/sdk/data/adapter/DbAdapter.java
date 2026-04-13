package com.sensorsdata.analytics.android.sdk.data.adapter;

import android.content.ContentValues;
import android.content.Context;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.LoginIdKeyPersistent;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoginId;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentRemoteSDKConfig;
import com.sensorsdata.analytics.android.sdk.data.persistent.UserIdentityPersistent;
import com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt;
import com.sensorsdata.analytics.android.sdk.util.Base64Coder;
import org.json.JSONException;
import org.json.JSONObject;

public class DbAdapter {
    private static DbAdapter instance;
    private final DbParams mDbParams;
    private DataOperation mPersistentOperation;
    private DataOperation mTrackEventOperation;

    private DbAdapter(Context context, String packageName, SensorsDataEncrypt sensorsDataEncrypt) {
        this.mDbParams = DbParams.getInstance(packageName);
        if (sensorsDataEncrypt != null) {
            this.mTrackEventOperation = new EncryptDataOperation(context.getApplicationContext(), sensorsDataEncrypt);
        } else {
            this.mTrackEventOperation = new EventDataOperation(context.getApplicationContext());
        }
        this.mPersistentOperation = new PersistentDataOperation(context.getApplicationContext());
    }

    public static DbAdapter getInstance(Context context, String packageName, SensorsDataEncrypt sensorsDataEncrypt) {
        if (instance == null) {
            instance = new DbAdapter(context, packageName, sensorsDataEncrypt);
        }
        return instance;
    }

    public static DbAdapter getInstance() {
        DbAdapter dbAdapter = instance;
        if (dbAdapter != null) {
            return dbAdapter;
        }
        throw new IllegalStateException("The static method getInstance(Context context, String packageName) should be called before calling getInstance()");
    }

    public int addJSON(JSONObject j) {
        int code = this.mTrackEventOperation.insertData(this.mDbParams.getEventUri(), j);
        if (code == 0) {
            return this.mTrackEventOperation.queryDataCount(this.mDbParams.getEventUri());
        }
        return code;
    }

    public void deleteAllEvents() {
        this.mTrackEventOperation.deleteData(this.mDbParams.getEventUri(), "DB_DELETE_ALL");
    }

    public int cleanupEvents(String last_id) {
        this.mTrackEventOperation.deleteData(this.mDbParams.getEventUri(), last_id);
        return this.mTrackEventOperation.queryDataCount(this.mDbParams.getEventUri());
    }

    public void commitActivityCount(int activityCount) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getActivityStartCountUri(), new JSONObject().put("value", activityCount));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public int getActivityCount() {
        String[] values = this.mPersistentOperation.queryData(this.mDbParams.getActivityStartCountUri(), 1);
        if (values == null || values.length <= 0) {
            return 0;
        }
        return Integer.parseInt(values[0]);
    }

    public void commitAppStartTime(long appStartTime) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getAppStartTimeUri(), new JSONObject().put("value", appStartTime));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public long getAppStartTime() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getAppStartTimeUri(), 1);
            if (values == null || values.length <= 0) {
                return 0;
            }
            return Long.parseLong(values[0]);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return 0;
        }
    }

    public void commitAppEndData(String appEndData) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getAppEndDataUri(), new JSONObject().put("value", (Object) appEndData));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public String getAppEndData() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getAppEndDataUri(), 1);
            if (values == null || values.length <= 0) {
                return "";
            }
            return values[0];
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void commitLoginId(String loginId) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getLoginIdUri(), new JSONObject().put("value", (Object) loginId));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public String getLoginId() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getLoginIdUri(), 1);
            if (values == null || values.length <= 0) {
                return "";
            }
            return values[0];
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public String getLoginIdFromLocal() {
        try {
            PersistentLoginId persistentLoginId = (PersistentLoginId) PersistentLoader.loadPersistent(DbParams.PersistentName.LOGIN_ID);
            if (persistentLoginId == null) {
                return "";
            }
            return (String) persistentLoginId.get();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void commitSessionIntervalTime(int sessionIntervalTime) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getSessionTimeUri(), new JSONObject().put("value", sessionIntervalTime));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public int getSessionIntervalTime() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getSessionTimeUri(), 1);
            if (values != null && values.length > 0) {
                return Integer.parseInt(values[0]);
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
        return 0;
    }

    public boolean isFirstChannelEvent(String[] eventName) {
        try {
            return this.mTrackEventOperation.queryDataCount(this.mDbParams.getChannelPersistentUri(), (String[]) null, "event_name = ? or event_name = ? ", eventName, (String) null) <= 0;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public void addChannelEvent(String eventName) {
        try {
            ContentValues values = new ContentValues();
            values.put(DbParams.KEY_CHANNEL_EVENT_NAME, eventName);
            this.mTrackEventOperation.insertData(this.mDbParams.getChannelPersistentUri(), values);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void commitSubProcessFlushState(boolean flushState) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getSubProcessUri(), new JSONObject().put("value", flushState));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public boolean isSubProcessFlushing() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getSubProcessUri(), 1);
            if (values == null || values.length <= 0 || Integer.parseInt(values[0]) == 1) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
        return true;
    }

    public void commitIdentities(String identities) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getUserIdentities(), new JSONObject().put("value", (Object) "Base64:" + Base64Coder.encodeString(identities)));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public String getIdentities() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getUserIdentities(), 1);
            if (values == null || values.length <= 0) {
                return null;
            }
            return decodeIdentities(values[0]);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return null;
        }
    }

    public String getIdentitiesFromLocal() {
        try {
            return decodeIdentities((String) ((UserIdentityPersistent) PersistentLoader.loadPersistent(DbParams.PersistentName.PERSISTENT_USER_ID)).get());
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private String decodeIdentities(String identities) {
        if (identities == null) {
            return null;
        }
        return Base64Coder.decodeString(identities.substring(identities.indexOf(":") + 1));
    }

    public void commitLoginIdKey(String loginIdKey) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getLoginIdKeyUri(), new JSONObject().put("value", (Object) loginIdKey));
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public String getLoginIdKey() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getLoginIdKeyUri(), 1);
            if (values == null || values.length <= 0) {
                return "";
            }
            return values[0];
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public String getLoginIdKeyFromLocal() {
        try {
            LoginIdKeyPersistent loginIdKeyPersistent = (LoginIdKeyPersistent) PersistentLoader.loadPersistent(DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY);
            if (loginIdKeyPersistent == null) {
                return "";
            }
            return (String) loginIdKeyPersistent.get();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void commitRemoteConfig(String config) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getRemoteConfigUri(), new JSONObject().put("value", (Object) config));
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public String getRemoteConfig() {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getRemoteConfigUri(), 1);
            if (values == null || values.length <= 0) {
                return "";
            }
            return values[0];
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public String getRemoteConfigFromLocal() {
        try {
            PersistentRemoteSDKConfig persistentRemoteSDKConfig = (PersistentRemoteSDKConfig) PersistentLoader.loadPersistent(DbParams.PersistentName.REMOTE_CONFIG);
            if (persistentRemoteSDKConfig == null) {
                return "";
            }
            return (String) persistentRemoteSDKConfig.get();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void commitPushID(String key, String pushId) {
        try {
            this.mPersistentOperation.insertData(this.mDbParams.getPushIdUri(), new JSONObject().put(DbParams.PUSH_ID_KEY, (Object) key).put(DbParams.PUSH_ID_VALUE, (Object) pushId));
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public String getPushId(String key) {
        try {
            String[] values = this.mPersistentOperation.queryData(this.mDbParams.getPushIdUri().buildUpon().appendQueryParameter(DbParams.PUSH_ID_KEY, key).build(), 1);
            if (values == null || values.length <= 0) {
                return "";
            }
            return values[0];
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void removePushId(String key) {
        this.mPersistentOperation.deleteData(this.mDbParams.getPushIdUri(), key);
    }

    public String[] generateDataString(String tableName, int limit) {
        try {
            return this.mTrackEventOperation.queryData(this.mDbParams.getEventUri(), limit);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }
}
