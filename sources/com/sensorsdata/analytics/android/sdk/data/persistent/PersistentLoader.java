package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.content.Context;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;

public class PersistentLoader {
    private static Context context;
    private static volatile PersistentLoader instance;

    private PersistentLoader(Context context2) {
        context = context2.getApplicationContext();
    }

    public static PersistentLoader initLoader(Context context2) {
        if (instance == null) {
            instance = new PersistentLoader(context2);
        }
        return instance;
    }

    public static PersistentIdentity<?> loadPersistent(String persistentKey) {
        if (instance == null) {
            throw new RuntimeException("you should call 'PersistentLoader.initLoader(Context)' first");
        } else if (TextUtils.isEmpty(persistentKey)) {
            return null;
        } else {
            char c = 65535;
            switch (persistentKey.hashCode()) {
                case -1436067305:
                    if (persistentKey.equals(DbParams.PersistentName.LOGIN_ID)) {
                        c = 6;
                        break;
                    }
                    break;
                case -951089033:
                    if (persistentKey.equals(DbParams.PersistentName.SUPER_PROPERTIES)) {
                        c = 8;
                        break;
                    }
                    break;
                case -854148740:
                    if (persistentKey.equals(DbParams.PersistentName.FIRST_INSTALL_CALLBACK)) {
                        c = 4;
                        break;
                    }
                    break;
                case -690407917:
                    if (persistentKey.equals(DbParams.PersistentName.FIRST_START)) {
                        c = 5;
                        break;
                    }
                    break;
                case -456824111:
                    if (persistentKey.equals(DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY)) {
                        c = 11;
                        break;
                    }
                    break;
                case -266152892:
                    if (persistentKey.equals(DbParams.PersistentName.PERSISTENT_USER_ID)) {
                        c = 10;
                        break;
                    }
                    break;
                case -212773998:
                    if (persistentKey.equals(DbParams.PersistentName.VISUAL_PROPERTIES)) {
                        c = 9;
                        break;
                    }
                    break;
                case 133344653:
                    if (persistentKey.equals(DbParams.PersistentName.FIRST_DAY)) {
                        c = 2;
                        break;
                    }
                    break;
                case 721318680:
                    if (persistentKey.equals(DbParams.PersistentName.DISTINCT_ID)) {
                        c = 1;
                        break;
                    }
                    break;
                case 947194773:
                    if (persistentKey.equals(DbParams.PersistentName.REMOTE_CONFIG)) {
                        c = 7;
                        break;
                    }
                    break;
                case 1214783133:
                    if (persistentKey.equals(DbParams.PersistentName.FIRST_INSTALL)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1521941740:
                    if (persistentKey.equals(DbParams.PersistentName.APP_END_DATA)) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return new PersistentAppEndData();
                case 1:
                    return new PersistentDistinctId(context);
                case 2:
                    return new PersistentFirstDay();
                case 3:
                    return new PersistentFirstTrackInstallation();
                case 4:
                    return new PersistentFirstTrackInstallationWithCallback();
                case 5:
                    return new PersistentFirstStart();
                case 6:
                    return new PersistentLoginId();
                case 7:
                    return new PersistentRemoteSDKConfig();
                case 8:
                    return new PersistentSuperProperties();
                case 9:
                    return new PersistentVisualConfig();
                case 10:
                    return new UserIdentityPersistent();
                case 11:
                    return new LoginIdKeyPersistent();
                default:
                    return null;
            }
        }
    }
}
