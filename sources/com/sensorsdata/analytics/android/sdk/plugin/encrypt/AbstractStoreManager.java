package com.sensorsdata.analytics.android.sdk.plugin.encrypt;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import com.didichuxing.doraemonkit.constant.SpInputType;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.util.SASpUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractStoreManager {
    private static final String TAG = "SA.AbstractStoreManager";
    protected boolean mDefaultState = true;
    private final Lock mLock = new ReentrantLock(true);
    private final LruCacheData mLruCacheSPData = new LruCacheData(10);
    /* access modifiers changed from: private */
    public String mMaxPluginType;
    private StorePlugin mMaxPriorityPlugin;
    private final List<StorePlugin> mStorePluginList = new ArrayList();
    private final Set<String> mStoreTypes = new HashSet();

    protected AbstractStoreManager() {
    }

    public void registerPlugin(StorePlugin plugin) {
        if (plugin != null) {
            String pluginType = plugin.type();
            if (TextUtils.isEmpty(pluginType)) {
                SALog.i(TAG, "PluginType is null");
                return;
            }
            if (this.mStoreTypes.contains(pluginType)) {
                Iterator<StorePlugin> it = this.mStorePluginList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    StorePlugin storePlugin = it.next();
                    if (TextUtils.equals(pluginType, storePlugin.type())) {
                        this.mStorePluginList.remove(storePlugin);
                        break;
                    }
                }
            } else {
                this.mStoreTypes.add(pluginType);
            }
            this.mStorePluginList.add(0, plugin);
            this.mMaxPriorityPlugin = plugin;
            this.mMaxPluginType = plugin.type();
        }
    }

    public void setString(String key, String value) {
        this.mLock.lock();
        try {
            if (this.mDefaultState) {
                storeKeys(key, value, SpInputType.STRING);
                this.mLock.unlock();
                return;
            }
            if (value == null) {
                for (StorePlugin plugin : this.mStorePluginList) {
                    plugin.remove(plugin.type() + key);
                }
                this.mLruCacheSPData.remove(key);
            } else {
                removeUselessValue(key);
                StorePlugin storePlugin = this.mMaxPriorityPlugin;
                storePlugin.setString(this.mMaxPluginType + key, value);
                this.mLruCacheSPData.put(key, value);
            }
            this.mLock.unlock();
        } catch (Exception e) {
            SALog.i(TAG, "save data failed,key = " + key + "value = " + value, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public void setBool(String key, boolean value) {
        this.mLock.lock();
        try {
            if (this.mDefaultState) {
                storeKeys(key, Boolean.valueOf(value), "Bool");
                this.mLock.unlock();
                return;
            }
            removeUselessValue(key);
            StorePlugin storePlugin = this.mMaxPriorityPlugin;
            storePlugin.setBool(this.mMaxPluginType + key, value);
            this.mLruCacheSPData.put(key, Boolean.valueOf(value));
            this.mLock.unlock();
        } catch (Exception e) {
            SALog.i(TAG, "save data failed,key = " + key + "value = " + value, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public void setInteger(String key, int value) {
        this.mLock.lock();
        try {
            if (this.mDefaultState) {
                storeKeys(key, Integer.valueOf(value), SpInputType.INTEGER);
                this.mLock.unlock();
                return;
            }
            removeUselessValue(key);
            StorePlugin storePlugin = this.mMaxPriorityPlugin;
            storePlugin.setInteger(this.mMaxPluginType + key, value);
            this.mLruCacheSPData.put(key, Integer.valueOf(value));
            this.mLock.unlock();
        } catch (Exception e) {
            SALog.i(TAG, "save data failed,key = " + key + "value = " + value, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public void setFloat(String key, float value) {
        this.mLock.lock();
        try {
            if (this.mDefaultState) {
                storeKeys(key, Float.valueOf(value), SpInputType.FLOAT);
                this.mLock.unlock();
                return;
            }
            removeUselessValue(key);
            StorePlugin storePlugin = this.mMaxPriorityPlugin;
            storePlugin.setFloat(this.mMaxPluginType + key, value);
            this.mLruCacheSPData.put(key, Float.valueOf(value));
            this.mLock.unlock();
        } catch (Exception e) {
            SALog.i(TAG, "save data failed,key = " + key + "value = " + value, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public void setLong(String key, long value) {
        this.mLock.lock();
        try {
            if (this.mDefaultState) {
                storeKeys(key, Long.valueOf(value), SpInputType.LONG);
                this.mLock.unlock();
                return;
            }
            removeUselessValue(key);
            StorePlugin storePlugin = this.mMaxPriorityPlugin;
            storePlugin.setLong(this.mMaxPluginType + key, value);
            this.mLruCacheSPData.put(key, Long.valueOf(value));
            this.mLock.unlock();
        } catch (Exception e) {
            SALog.i(TAG, "save data failed,key = " + key + "value = " + value, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public String getString(String key, String defaultValue) {
        this.mLock.lock();
        try {
            String value = (String) this.mLruCacheSPData.get(key);
            if (value != null) {
                return value;
            }
            if (this.mDefaultState) {
                String str = (String) getValue(key, SpInputType.STRING, defaultValue);
                this.mLock.unlock();
                return str;
            }
            Iterator<StorePlugin> it = this.mStorePluginList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                StorePlugin plugin = it.next();
                value = plugin.getString(plugin.type() + key);
                if (!TextUtils.isEmpty(value)) {
                    if (plugin != this.mMaxPriorityPlugin) {
                        plugin.remove(plugin.type() + key);
                        StorePlugin storePlugin = this.mMaxPriorityPlugin;
                        storePlugin.setString(this.mMaxPluginType + key, value);
                    }
                    this.mLruCacheSPData.put(key, value);
                }
            }
            String str2 = value == null ? defaultValue : value;
            this.mLock.unlock();
            return str2;
        } catch (Exception e) {
            SALog.i(TAG, "get data failed,key = " + key, e);
            return defaultValue;
        } finally {
            this.mLock.unlock();
        }
    }

    public boolean getBool(String key, boolean defaultValue) {
        this.mLock.lock();
        try {
            Boolean value = (Boolean) this.mLruCacheSPData.get(key);
            if (value != null) {
                return value.booleanValue();
            }
            if (this.mDefaultState) {
                boolean booleanValue = ((Boolean) getValue(key, "Bool", Boolean.valueOf(defaultValue))).booleanValue();
                this.mLock.unlock();
                return booleanValue;
            }
            Iterator<StorePlugin> it = this.mStorePluginList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                StorePlugin plugin = it.next();
                value = plugin.getBool(plugin.type() + key);
                if (value != null) {
                    if (plugin != this.mMaxPriorityPlugin) {
                        plugin.remove(plugin.type() + key);
                        StorePlugin storePlugin = this.mMaxPriorityPlugin;
                        storePlugin.setBool(this.mMaxPluginType + key, value.booleanValue());
                    }
                    this.mLruCacheSPData.put(key, value);
                }
            }
            boolean booleanValue2 = value == null ? defaultValue : value.booleanValue();
            this.mLock.unlock();
            return booleanValue2;
        } catch (Exception e) {
            SALog.i(TAG, "get data failed,key = " + key, e);
            return defaultValue;
        } finally {
            this.mLock.unlock();
        }
    }

    public int getInteger(String key, int defaultValue) {
        this.mLock.lock();
        try {
            Integer value = (Integer) this.mLruCacheSPData.get(key);
            if (value != null) {
                return value.intValue();
            }
            if (this.mDefaultState) {
                int intValue = ((Integer) getValue(key, SpInputType.INTEGER, Integer.valueOf(defaultValue))).intValue();
                this.mLock.unlock();
                return intValue;
            }
            Iterator<StorePlugin> it = this.mStorePluginList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                StorePlugin plugin = it.next();
                value = plugin.getInteger(plugin.type() + key);
                if (value != null) {
                    if (plugin != this.mMaxPriorityPlugin) {
                        plugin.remove(plugin.type() + key);
                        StorePlugin storePlugin = this.mMaxPriorityPlugin;
                        storePlugin.setInteger(this.mMaxPluginType + key, value.intValue());
                    }
                    this.mLruCacheSPData.put(key, value);
                }
            }
            int intValue2 = value == null ? defaultValue : value.intValue();
            this.mLock.unlock();
            return intValue2;
        } catch (Exception e) {
            SALog.i(TAG, "get data failed,key = " + key, e);
            return defaultValue;
        } finally {
            this.mLock.unlock();
        }
    }

    public float getFloat(String key, float defaultValue) {
        this.mLock.lock();
        try {
            Float value = (Float) this.mLruCacheSPData.get(key);
            if (value != null) {
                return value.floatValue();
            }
            if (this.mDefaultState) {
                float floatValue = ((Float) getValue(key, SpInputType.FLOAT, Float.valueOf(defaultValue))).floatValue();
                this.mLock.unlock();
                return floatValue;
            }
            Iterator<StorePlugin> it = this.mStorePluginList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                StorePlugin plugin = it.next();
                value = plugin.getFloat(plugin.type() + key);
                if (value != null) {
                    if (plugin != this.mMaxPriorityPlugin) {
                        plugin.remove(plugin.type() + key);
                        StorePlugin storePlugin = this.mMaxPriorityPlugin;
                        storePlugin.setFloat(this.mMaxPluginType + key, value.floatValue());
                    }
                    this.mLruCacheSPData.put(key, value);
                }
            }
            float floatValue2 = value == null ? defaultValue : value.floatValue();
            this.mLock.unlock();
            return floatValue2;
        } catch (Exception e) {
            SALog.i(TAG, "get data failed,key = " + key, e);
            return defaultValue;
        } finally {
            this.mLock.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    public Long getLong(String key, long defaultValue) {
        this.mLock.lock();
        try {
            Long value = (Long) this.mLruCacheSPData.get(key);
            if (value != null) {
                this.mLock.unlock();
                return value;
            } else if (this.mDefaultState) {
                Long l = (Long) getValue(key, SpInputType.LONG, Long.valueOf(defaultValue));
                this.mLock.unlock();
                return l;
            } else {
                Iterator<StorePlugin> it = this.mStorePluginList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    StorePlugin plugin = it.next();
                    value = plugin.getLong(plugin.type() + key);
                    if (value != null) {
                        if (plugin != this.mMaxPriorityPlugin) {
                            plugin.remove(plugin.type() + key);
                            StorePlugin storePlugin = this.mMaxPriorityPlugin;
                            storePlugin.setLong(this.mMaxPluginType + key, value.longValue());
                        }
                        this.mLruCacheSPData.put(key, value);
                    }
                }
                Long valueOf = Long.valueOf(value == null ? defaultValue : value.longValue());
                this.mLock.unlock();
                return valueOf;
            }
        } catch (Exception e) {
            SALog.i(TAG, "get data failed,key = " + key, e);
            this.mLock.unlock();
            return Long.valueOf(defaultValue);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public void remove(String key) {
        this.mLock.lock();
        try {
            for (StorePlugin plugin : this.mStorePluginList) {
                if (!this.mDefaultState) {
                    plugin.remove(plugin.type() + key);
                } else if ((plugin instanceof DefaultStorePlugin) && ((DefaultStorePlugin) plugin).storeKeys() != null && ((DefaultStorePlugin) plugin).storeKeys().contains(key)) {
                    plugin.remove(key);
                }
            }
            this.mLruCacheSPData.remove(key);
        } catch (Exception e) {
            SALog.i(TAG, "remove failed,key = " + key, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void upgrade() {
        this.mLock.lock();
        try {
            for (int i = this.mStorePluginList.size() - 1; i >= 0; i--) {
                StorePlugin storePlugin = this.mStorePluginList.get(i);
                StorePlugin previousPlugin = null;
                int previousIndex = i - 1;
                if (previousIndex >= 0) {
                    previousPlugin = this.mStorePluginList.get(previousIndex);
                }
                if (previousPlugin != null) {
                    previousPlugin.upgrade(storePlugin);
                }
            }
        } catch (Exception e) {
            SALog.i(TAG, "upgrade failed", e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public boolean isExists(String key) {
        this.mLock.lock();
        try {
            if (TextUtils.isEmpty(key)) {
                this.mLock.unlock();
                return false;
            }
            for (StorePlugin plugin : this.mStorePluginList) {
                if (plugin.isExists(plugin.type() + key)) {
                    this.mLock.unlock();
                    return true;
                }
            }
            this.mLock.unlock();
            return false;
        } catch (Exception e) {
            SALog.i(TAG, "isExists failed,key = " + key, e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isRegisterPlugin(Context context, String name) {
        try {
            File SPFile = new File("data/data/" + context.getPackageName() + "/shared_prefs", name + FileUtil.XML);
            if (!SPFile.exists()) {
                return false;
            }
            if (SASpUtils.getSharedPreferences(context, name, 0).getAll().size() == 0) {
                SALog.i(TAG, "delete sp: " + name);
                return true ^ SPFile.delete();
            }
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void removeUselessValue(String key) {
        for (StorePlugin plugin : this.mStorePluginList) {
            if (plugin != this.mMaxPriorityPlugin) {
                plugin.remove(plugin.type() + key);
            }
        }
    }

    private void storeKeys(String key, Object value, String type) {
        StorePlugin tempPlugin = this.mMaxPriorityPlugin;
        Iterator<StorePlugin> it = this.mStorePluginList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StorePlugin plugin = it.next();
            if ((plugin instanceof DefaultStorePlugin) && ((DefaultStorePlugin) plugin).storeKeys() != null && ((DefaultStorePlugin) plugin).storeKeys().contains(key)) {
                tempPlugin = plugin;
                break;
            }
        }
        char c = 65535;
        switch (type.hashCode()) {
            case -1808118735:
                if (type.equals(SpInputType.STRING)) {
                    c = 0;
                    break;
                }
                break;
            case -672261858:
                if (type.equals(SpInputType.INTEGER)) {
                    c = 1;
                    break;
                }
                break;
            case 2076426:
                if (type.equals("Bool")) {
                    c = 4;
                    break;
                }
                break;
            case 2374300:
                if (type.equals(SpInputType.LONG)) {
                    c = 3;
                    break;
                }
                break;
            case 67973692:
                if (type.equals(SpInputType.FLOAT)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                tempPlugin.setString(tempPlugin.type() + key, (String) value);
                return;
            case 1:
                tempPlugin.setInteger(tempPlugin.type() + key, ((Integer) value).intValue());
                return;
            case 2:
                tempPlugin.setFloat(tempPlugin.type() + key, ((Float) value).floatValue());
                return;
            case 3:
                tempPlugin.setLong(tempPlugin.type() + key, ((Long) value).longValue());
                return;
            case 4:
                tempPlugin.setBool(tempPlugin.type() + key, ((Boolean) value).booleanValue());
                return;
            default:
                return;
        }
    }

    private <T> T getValue(String key, String type, T defaultValue) {
        StorePlugin tempPlugin = this.mMaxPriorityPlugin;
        Iterator<StorePlugin> it = this.mStorePluginList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StorePlugin plugin = it.next();
            if ((plugin instanceof DefaultStorePlugin) && ((DefaultStorePlugin) plugin).storeKeys() != null && ((DefaultStorePlugin) plugin).storeKeys().contains(key)) {
                tempPlugin = plugin;
                break;
            }
        }
        Object value = null;
        char c = 65535;
        switch (type.hashCode()) {
            case -1808118735:
                if (type.equals(SpInputType.STRING)) {
                    c = 0;
                    break;
                }
                break;
            case -672261858:
                if (type.equals(SpInputType.INTEGER)) {
                    c = 1;
                    break;
                }
                break;
            case 2076426:
                if (type.equals("Bool")) {
                    c = 4;
                    break;
                }
                break;
            case 2374300:
                if (type.equals(SpInputType.LONG)) {
                    c = 3;
                    break;
                }
                break;
            case 67973692:
                if (type.equals(SpInputType.FLOAT)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                value = tempPlugin.getString(tempPlugin.type() + key);
                break;
            case 1:
                value = tempPlugin.getInteger(tempPlugin.type() + key);
                break;
            case 2:
                value = tempPlugin.getFloat(tempPlugin.type() + key);
                break;
            case 3:
                value = tempPlugin.getLong(tempPlugin.type() + key);
                break;
            case 4:
                value = tempPlugin.getBool(tempPlugin.type() + key);
                break;
        }
        return value == null ? defaultValue : value;
    }

    public class LruCacheData {
        private LruCache<String, Object> mCacheSPData;

        public LruCacheData(int maxSize) {
            if (Build.VERSION.SDK_INT >= 12) {
                this.mCacheSPData = new LruCache<>(maxSize);
            }
        }

        /* access modifiers changed from: package-private */
        public Object get(String key) {
            if (Build.VERSION.SDK_INT < 12) {
                return null;
            }
            LruCache<String, Object> lruCache = this.mCacheSPData;
            return lruCache.get(AbstractStoreManager.this.mMaxPluginType + key);
        }

        /* access modifiers changed from: package-private */
        public void put(String key, Object value) {
            if (Build.VERSION.SDK_INT >= 12) {
                LruCache<String, Object> lruCache = this.mCacheSPData;
                lruCache.put(AbstractStoreManager.this.mMaxPluginType + key, value);
            }
        }

        /* access modifiers changed from: package-private */
        public void remove(String key) {
            if (Build.VERSION.SDK_INT >= 12) {
                LruCache<String, Object> lruCache = this.mCacheSPData;
                lruCache.remove(AbstractStoreManager.this.mMaxPluginType + key);
            }
        }
    }
}
