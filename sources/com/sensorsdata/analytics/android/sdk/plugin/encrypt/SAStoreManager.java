package com.sensorsdata.analytics.android.sdk.plugin.encrypt;

import android.content.Context;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.encrypt.AESSecretManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SAStoreManager extends AbstractStoreManager {
    public static final String SECRET_KEY = "sa_sp_encrypt_secret_key";
    private static final String SP_SENSORS_DATA = "sensorsdata";
    private static final String SP_SENSORS_DATA_API = "com.sensorsdata.analytics.android.sdk.SensorsDataAPI";
    private static final String TAG = "SA.SAStoreManager";
    /* access modifiers changed from: private */
    public final ArrayList<String> mAPIStoreKeys;

    private SAStoreManager() {
        this.mAPIStoreKeys = new ArrayList<>();
    }

    public static SAStoreManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void registerPlugins(List<StorePlugin> configPlugins, Context context) {
        if (configPlugins == null || configPlugins.isEmpty()) {
            this.mDefaultState = true;
            registerAPIPlugin(context);
            registerSensorsDataPlugin(context);
            return;
        }
        this.mDefaultState = false;
        AESSecretManager.getInstance().initSecretKey(context);
        if (isRegisterPlugin(context, SP_SENSORS_DATA_API)) {
            registerAPIPlugin(context);
        }
        if (isRegisterPlugin(context, "sensorsdata")) {
            registerSensorsDataPlugin(context);
        }
        for (StorePlugin plugin : configPlugins) {
            registerPlugin(plugin);
        }
    }

    public static class SingletonHolder {
        /* access modifiers changed from: private */
        public static final SAStoreManager INSTANCE = new SAStoreManager();

        private SingletonHolder() {
        }
    }

    private void registerAPIPlugin(Context context) {
        registerPlugin(new DefaultStorePlugin(context, SP_SENSORS_DATA_API) {
            public List<String> storeKeys() {
                if (SAStoreManager.this.mAPIStoreKeys.isEmpty()) {
                    SAStoreManager.this.initAPIKeys();
                }
                return SAStoreManager.this.mAPIStoreKeys;
            }
        });
    }

    private void registerSensorsDataPlugin(Context context) {
        registerPlugin(new DefaultStorePlugin(context, "sensorsdata") {
            public List<String> storeKeys() {
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void initAPIKeys() {
        try {
            for (Field field : DbParams.PersistentName.class.getDeclaredFields()) {
                this.mAPIStoreKeys.add((String) field.get((Object) null));
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
