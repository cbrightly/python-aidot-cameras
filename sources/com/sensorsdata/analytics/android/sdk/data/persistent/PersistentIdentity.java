package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.annotation.SuppressLint;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;

@SuppressLint({"CommitPrefEdits"})
public abstract class PersistentIdentity<T> {
    private static final String TAG = "SA.PersistentIdentity";
    private T item;
    private final String persistentKey;
    private final SAStoreManager saStoreManager = SAStoreManager.getInstance();
    private final PersistentSerializer serializer;

    public interface PersistentSerializer<T> {
        T create();

        T load(String str);

        String save(T t);
    }

    PersistentIdentity(String persistentKey2, PersistentSerializer<T> serializer2) {
        this.serializer = serializer2;
        this.persistentKey = persistentKey2;
    }

    public T get() {
        if (this.item == null) {
            synchronized (this.saStoreManager) {
                String data = this.saStoreManager.getString(this.persistentKey, (String) null);
                if (data == null) {
                    T create = this.serializer.create();
                    this.item = create;
                    commit(create);
                } else {
                    this.item = this.serializer.load(data);
                }
            }
        }
        return this.item;
    }

    public void commit(T item2) {
        if (!AbstractSensorsDataAPI.getConfigOptions().isDisableSDK()) {
            this.item = item2;
            synchronized (this.saStoreManager) {
                if (this.item == null) {
                    this.item = this.serializer.create();
                }
                this.saStoreManager.setString(this.persistentKey, this.serializer.save(this.item));
            }
        }
    }

    public boolean isExists() {
        try {
            return this.saStoreManager.isExists(this.persistentKey);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return false;
        }
    }

    public void remove() {
        synchronized (this.saStoreManager) {
            this.saStoreManager.remove(this.persistentKey);
        }
    }
}
