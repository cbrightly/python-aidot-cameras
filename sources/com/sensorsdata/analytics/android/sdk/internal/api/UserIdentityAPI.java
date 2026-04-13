package com.sensorsdata.analytics.android.sdk.internal.api;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentDistinctId;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import com.sensorsdata.analytics.android.sdk.listener.SAEventListener;
import com.sensorsdata.analytics.android.sdk.monitor.TrackMonitor;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.SAContextManager;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public final class UserIdentityAPI implements IUserIdentityAPI {
    private static final String ANDROID_ID = "$identity_android_id";
    private static final String ANDROID_UUID = "$identity_android_uuid";
    private static final String ANONYMOUS_ID = "$identity_anonymous_id";
    private static final String COOKIE_ID = "$identity_cookie_id";
    private static final String IDENTITIES_KEY = "identities";
    private static final String LOGIN_ID = "$identity_login_id";
    private static final String TAG = "UserIdentityAPI";
    private String LOGIN_ID_KEY = LOGIN_ID;
    private boolean isResetAnonymousId = false;
    private final PersistentDistinctId mDistinctId;
    private JSONObject mIdentities;
    private final Object mLoginIdLock = new Object();
    private String mLoginIdValue = null;
    private JSONObject mLoginIdentities;
    private SAContextManager mSAContextManager;
    private JSONObject mUnbindIdentities;

    public UserIdentityAPI(SAContextManager contextManager, SAConfigOptions saConfigOptions) {
        this.mSAContextManager = contextManager;
        this.mDistinctId = (PersistentDistinctId) PersistentLoader.loadPersistent(DbParams.PersistentName.DISTINCT_ID);
        try {
            String loginIDKey = saConfigOptions.getLoginIDKey();
            if (!LOGIN_ID.equals(loginIDKey)) {
                if (!SADataHelper.assertPropertyKey(loginIDKey) || !isKeyValid(loginIDKey)) {
                    SALog.i(TAG, "The LoginIDKey '" + loginIDKey + "' is invalid.");
                } else {
                    this.LOGIN_ID_KEY = loginIDKey;
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        initIdentities();
    }

    public String getDistinctId() {
        try {
            String loginId = getLoginId();
            if (!TextUtils.isEmpty(loginId)) {
                return loginId;
            }
            return getAnonymousId();
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return "";
        }
    }

    public String getAnonymousId() {
        try {
            synchronized (this.mDistinctId) {
                if (!AbstractSensorsDataAPI.getConfigOptions().isDataCollect()) {
                    return "";
                }
                String str = (String) this.mDistinctId.get();
                return str;
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return null;
        }
    }

    public void resetAnonymousId() {
        String newDistinctId;
        try {
            synchronized (this.mDistinctId) {
                SALog.i(TAG, "resetAnonymousId is called");
                String androidId = this.mSAContextManager.getAndroidId();
                if (androidId.equals(this.mDistinctId.get())) {
                    SALog.i(TAG, "DistinctId not change");
                    return;
                }
                this.isResetAnonymousId = true;
                if (AbstractSensorsDataAPI.getConfigOptions().isDataCollect()) {
                    if (SensorsDataUtils.isValidAndroidId(androidId)) {
                        newDistinctId = androidId;
                    } else {
                        newDistinctId = UUID.randomUUID().toString();
                    }
                    this.mDistinctId.commit(newDistinctId);
                    if (this.mIdentities.has(ANONYMOUS_ID)) {
                        updateIdentities(ANONYMOUS_ID, (String) this.mDistinctId.get());
                    }
                    if (this.mSAContextManager.getEventListenerList() != null) {
                        for (SAEventListener eventListener : this.mSAContextManager.getEventListenerList()) {
                            try {
                                eventListener.resetAnonymousId();
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                        }
                    }
                    TrackMonitor.getInstance().callResetAnonymousId(newDistinctId);
                }
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public String getLoginId() {
        if (AppInfoUtils.isTaskExecuteThread()) {
            return DbAdapter.getInstance().getLoginId();
        }
        return this.mLoginIdValue;
    }

    public void identify(String distinctId) {
        try {
            SALog.i(TAG, "identify is called");
            synchronized (this.mDistinctId) {
                if (!distinctId.equals(this.mDistinctId.get())) {
                    this.mDistinctId.commit(distinctId);
                    updateIdentities(ANONYMOUS_ID, distinctId);
                    if (this.mSAContextManager.getEventListenerList() != null) {
                        for (SAEventListener eventListener : this.mSAContextManager.getEventListenerList()) {
                            try {
                                eventListener.identify();
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                        }
                    }
                    TrackMonitor.getInstance().callIdentify(distinctId);
                }
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void login(String loginId) {
        login(loginId, (JSONObject) null);
    }

    public void login(String loginId, JSONObject properties) {
        try {
            this.mLoginIdValue = loginId;
            DbAdapter.getInstance().commitLoginId(loginId);
            this.mLoginIdentities = new JSONObject(DbAdapter.getInstance().getIdentities());
            DbAdapter.getInstance().commitLoginIdKey(this.LOGIN_ID_KEY);
            updateIdentities(this.LOGIN_ID_KEY, loginId);
            clearIdentities(Arrays.asList(new String[]{ANDROID_ID, ANDROID_UUID, this.LOGIN_ID_KEY}));
            if (this.mSAContextManager.getEventListenerList() != null) {
                for (SAEventListener eventListener : this.mSAContextManager.getEventListenerList()) {
                    try {
                        eventListener.login();
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            }
            TrackMonitor.getInstance().callLogin(loginId);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void logout() {
        try {
            synchronized (this.mLoginIdLock) {
                SALog.i(TAG, "logout is called");
                if (!TextUtils.isEmpty(getLoginId())) {
                    try {
                        DbAdapter.getInstance().commitLoginId((String) null);
                        this.mLoginIdValue = null;
                    } catch (Exception ex) {
                        SALog.printStackTrace(ex);
                    }
                    if (this.mSAContextManager.getEventListenerList() != null) {
                        for (SAEventListener eventListener : this.mSAContextManager.getEventListenerList()) {
                            try {
                                eventListener.logout();
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                        }
                    }
                    TrackMonitor.getInstance().callLogout();
                    SALog.i(TAG, "Clean loginId");
                }
                DbAdapter.getInstance().commitLoginIdKey("");
                clearIdentities(Arrays.asList(new String[]{ANDROID_ID, ANDROID_UUID}));
            }
        } catch (Exception ex2) {
            SALog.printStackTrace(ex2);
        }
    }

    public void bind(String key, String value) {
        if (!isKeyValid(key) || !SADataHelper.assertPropertyKey(key)) {
            throw new InvalidDataException("bind key is invalid, key = " + key);
        }
        SADataHelper.assertDistinctId(value);
        try {
            updateIdentities(key, value);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void unbind(String key, String value) {
        if (!isKeyValid(key) || !SADataHelper.assertPropertyKey(key)) {
            throw new InvalidDataException("unbind key is invalid, key = " + key);
        }
        SADataHelper.assertDistinctId(value);
        try {
            JSONObject jSONObject = new JSONObject();
            this.mUnbindIdentities = jSONObject;
            jSONObject.put(key, (Object) value);
            if (this.mIdentities.has(key) && value.equals(this.mIdentities.getString(key))) {
                this.mIdentities.remove(key);
                DbAdapter.getInstance().commitIdentities(this.mIdentities.toString());
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public JSONObject getIdentities(EventType eventType) {
        if (EventType.TRACK_SIGNUP == eventType) {
            return this.mLoginIdentities;
        }
        if (EventType.TRACK_ID_UNBIND == eventType) {
            return this.mUnbindIdentities;
        }
        return this.mIdentities;
    }

    public void updateLoginId(String loginId) {
        this.mLoginIdValue = loginId;
    }

    public void enableDataCollect(String androidId) {
        String key;
        String uuid;
        try {
            if (SensorsDataUtils.isValidAndroidId(androidId)) {
                if (TextUtils.isEmpty((CharSequence) this.mDistinctId.get()) || this.isResetAnonymousId) {
                    this.mDistinctId.commit(androidId);
                }
                uuid = ANDROID_ID;
                key = androidId;
            } else {
                String uuid2 = UUID.randomUUID().toString();
                if (TextUtils.isEmpty((CharSequence) this.mDistinctId.get()) || this.isResetAnonymousId) {
                    this.mDistinctId.commit(uuid2);
                }
                String str = uuid2;
                uuid = ANDROID_UUID;
                key = str;
            }
            if (this.mIdentities.has(ANONYMOUS_ID) && this.isResetAnonymousId) {
                updateIdentities(ANONYMOUS_ID, (String) this.mDistinctId.get());
            }
            this.mLoginIdentities.put(uuid, (Object) key);
            this.mIdentities.put(uuid, (Object) key);
            DbAdapter.getInstance().commitIdentities(this.mIdentities.toString());
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void updateIdentities(JSONObject identitiesJson) {
        try {
            if (SensorsDataUtils.isValidAndroidId(this.mSAContextManager.getAndroidId())) {
                identitiesJson.put(ANDROID_ID, (Object) this.mSAContextManager.getAndroidId());
            } else {
                identitiesJson.put(ANDROID_UUID, this.mDistinctId.get());
            }
            if (this.mIdentities.has(ANONYMOUS_ID) && this.isResetAnonymousId) {
                identitiesJson.put(ANONYMOUS_ID, this.mDistinctId.get());
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void mergeH5Identities(EventType eventType, JSONObject eventObject) {
        JSONObject identityJson = null;
        String identities = eventObject.optString(IDENTITIES_KEY);
        try {
            if (!TextUtils.isEmpty(identities)) {
                identityJson = new JSONObject(identities);
                identityJson.remove(ANDROID_ID);
                identityJson.remove(ANONYMOUS_ID);
                identityJson.remove(ANDROID_UUID);
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
        try {
            if (EventType.TRACK_SIGNUP == eventType) {
                mergeSignUpH5(eventObject, identityJson);
            } else if (EventType.TRACK_ID_BIND == eventType) {
                mergeBindH5(eventObject, identityJson);
            } else if (EventType.TRACK_ID_UNBIND != eventType || identityJson == null) {
                mergeTrackH5(eventObject, identityJson);
            } else {
                mergeUnbindH5(eventObject, identityJson);
            }
        } catch (JSONException exception) {
            SALog.printStackTrace(exception);
        }
    }

    public void loadIdentitiesFromFile() {
        try {
            this.mIdentities = new JSONObject(DbAdapter.getInstance().getIdentities());
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    public boolean isLoginIdValid(String loginId) {
        String anonymousId = getAnonymousId();
        return (!loginId.equals(DbAdapter.getInstance().getLoginId()) && !loginId.equals(anonymousId)) || (!DbAdapter.getInstance().getLoginIdKey().equals(this.LOGIN_ID_KEY) && !loginId.equals(anonymousId));
    }

    private void updateIdentities(String key, String value) {
        try {
            this.mIdentities.put(key, (Object) value);
            this.mLoginIdentities.put(key, (Object) value);
            DbAdapter.getInstance().commitIdentities(this.mIdentities.toString());
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
    }

    private void initIdentities() {
        try {
            this.mIdentities = new JSONObject();
            this.mLoginIdentities = new JSONObject();
            String cacheIdentities = DbAdapter.getInstance().getIdentitiesFromLocal();
            if (!TextUtils.isEmpty(cacheIdentities)) {
                JSONObject jSONObject = new JSONObject(cacheIdentities);
                this.mIdentities = jSONObject;
                if (jSONObject.has(ANONYMOUS_ID)) {
                    this.mIdentities.put(ANONYMOUS_ID, this.mDistinctId.get());
                }
            } else {
                if (this.mDistinctId.isExists()) {
                    this.mIdentities.put(ANONYMOUS_ID, this.mDistinctId.get());
                }
                if (AbstractSensorsDataAPI.getConfigOptions().isDataCollect()) {
                    if (SensorsDataUtils.isValidAndroidId(this.mSAContextManager.getAndroidId())) {
                        this.mIdentities.put(ANDROID_ID, (Object) this.mSAContextManager.getAndroidId());
                    } else {
                        this.mIdentities.put(ANDROID_UUID, (Object) UUID.randomUUID().toString());
                    }
                }
            }
            String loginIdValue = DbAdapter.getInstance().getLoginIdFromLocal();
            String oldLoginKey = DbAdapter.getInstance().getLoginIdKeyFromLocal();
            if (!TextUtils.isEmpty(loginIdValue)) {
                this.mLoginIdValue = loginIdValue;
                if (!this.mIdentities.has(oldLoginKey)) {
                    this.mIdentities.put(this.LOGIN_ID_KEY, (Object) loginIdValue);
                    DbAdapter.getInstance().commitLoginIdKey(this.LOGIN_ID_KEY);
                    clearIdentities(Arrays.asList(new String[]{ANDROID_ID, ANDROID_UUID, this.LOGIN_ID_KEY}));
                } else if (!loginIdValue.equals(this.mIdentities.getString(oldLoginKey))) {
                    this.mIdentities.put(oldLoginKey, (Object) loginIdValue);
                    clearIdentities(Arrays.asList(new String[]{ANDROID_ID, ANDROID_UUID, oldLoginKey}));
                }
            } else {
                if (this.mIdentities.has(oldLoginKey)) {
                    this.mLoginIdValue = null;
                    clearIdentities(Arrays.asList(new String[]{ANDROID_ID, ANDROID_UUID, ANONYMOUS_ID}));
                }
                DbAdapter.getInstance().commitLoginIdKey("");
            }
            String identities = this.mIdentities.toString();
            this.mLoginIdentities = new JSONObject(identities);
            DbAdapter.getInstance().commitIdentities(identities);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    private void clearIdentities(List<String> whiteListKey) {
        JSONObject jSONObject = this.mIdentities;
        if (jSONObject != null) {
            Iterator<String> iterator = jSONObject.keys();
            while (iterator.hasNext()) {
                if (!whiteListKey.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            DbAdapter.getInstance().commitIdentities(this.mIdentities.toString());
        }
    }

    private void mergeSignUpH5(JSONObject eventObject, JSONObject identityJson) {
        String loginId;
        boolean isH5IdentityHasLoginId = true;
        if (identityJson != null) {
            loginId = identityJson.optString(this.LOGIN_ID_KEY);
            if (TextUtils.isEmpty(loginId)) {
                isH5IdentityHasLoginId = false;
            }
        } else {
            identityJson = new JSONObject();
            loginId = eventObject.optString("distinct_id");
        }
        if (!isH5IdentityHasLoginId) {
            SensorsDataUtils.mergeJSONObject(this.mIdentities, identityJson);
            eventObject.put(IDENTITIES_KEY, (Object) identityJson);
        } else if (isLoginIdValid(loginId)) {
            SADataHelper.assertDistinctId(loginId);
            this.mIdentities.put(this.LOGIN_ID_KEY, (Object) loginId);
            SensorsDataUtils.mergeJSONObject(this.mIdentities, identityJson);
            eventObject.put(IDENTITIES_KEY, (Object) identityJson);
            eventObject.put("login_id", (Object) loginId);
            login(loginId);
        } else {
            throw new InvalidDataException("The " + loginId + " is invalid.");
        }
    }

    private void mergeBindH5(JSONObject eventObject, JSONObject identityJson) {
        if (identityJson == null) {
            identityJson = new JSONObject();
        } else {
            Iterator<String> iteratorKeys = identityJson.keys();
            while (iteratorKeys.hasNext()) {
                String key = iteratorKeys.next();
                if (!this.mIdentities.has(key)) {
                    this.mIdentities.put(key, (Object) identityJson.optString(key));
                }
            }
            this.mIdentities.remove(COOKIE_ID);
            DbAdapter.getInstance().commitIdentities(this.mIdentities.toString());
        }
        SensorsDataUtils.mergeJSONObject(this.mIdentities, identityJson);
        eventObject.put(IDENTITIES_KEY, (Object) identityJson);
    }

    private void mergeUnbindH5(JSONObject eventObject, JSONObject identityJson) {
        Iterator<String> iteratorKeys = identityJson.keys();
        while (iteratorKeys.hasNext()) {
            String key = iteratorKeys.next();
            if (!ANONYMOUS_ID.equals(key) && !ANDROID_UUID.equals(key) && !ANDROID_ID.equals(key) && this.mIdentities.has(key) && this.mIdentities.get(key).equals(identityJson.opt(key))) {
                this.mIdentities.remove(key);
            }
        }
        eventObject.put(IDENTITIES_KEY, (Object) identityJson);
        DbAdapter.getInstance().commitIdentities(this.mIdentities.toString());
    }

    private void mergeTrackH5(JSONObject eventObject, JSONObject identityJson) {
        if (identityJson == null) {
            try {
                identityJson = new JSONObject();
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
        SensorsDataUtils.mergeJSONObject(this.mIdentities, identityJson);
        eventObject.put(IDENTITIES_KEY, (Object) identityJson);
        try {
            if (this.mSAContextManager.getEventListenerList() != null) {
                for (SAEventListener eventListener : this.mSAContextManager.getEventListenerList()) {
                    try {
                        eventListener.trackEvent(eventObject);
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                    }
                }
            }
        } catch (Exception e3) {
            SALog.printStackTrace(e3);
        }
        TrackMonitor.getInstance().callTrack(eventObject);
    }

    private boolean isKeyValid(String key) {
        return !ANONYMOUS_ID.equals(key) && !ANDROID_UUID.equals(key) && !ANDROID_ID.equals(key) && !this.LOGIN_ID_KEY.equals(key) && !LOGIN_ID.equals(key) && !IUserIdentityAPI.BIND_ID.equals(key) && !IUserIdentityAPI.UNBIND_ID.equals(key);
    }
}
