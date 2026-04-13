package chip.platform;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

public class PreferencesConfigurationManager implements ConfigurationManager {
    private final String PREFERENCE_FILE_KEY = "chip.platform.ConfigurationManager";
    private final String TAG = KeyValueStoreManager.class.getSimpleName();
    private SharedPreferences preferences;

    public PreferencesConfigurationManager(Context context) {
        this.preferences = context.getSharedPreferences("chip.platform.ConfigurationManager", 0);
        try {
            String keyUniqueId = getKey(ConfigurationManager.kConfigNamespace_ChipFactory, ConfigurationManager.kConfigKey_UniqueId);
            if (!this.preferences.contains(keyUniqueId)) {
                this.preferences.edit().putString(keyUniqueId, UUID.randomUUID().toString().replaceAll("-", "")).apply();
            }
        } catch (AndroidChipPlatformException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readConfigValueLong(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = r4.getKey(r5, r6)
            int r1 = r0.hashCode()
            switch(r1) {
                case -1052036721: goto L_0x0020;
                case -1038552502: goto L_0x0016;
                case -881918589: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x002a
        L_0x000c:
            java.lang.String r1 = "chip-factory:software-version"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 2
            goto L_0x002b
        L_0x0016:
            java.lang.String r1 = "chip-factory:product-id"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 0
            goto L_0x002b
        L_0x0020:
            java.lang.String r1 = "chip-factory:hardware-ver"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 1
            goto L_0x002b
        L_0x002a:
            r1 = -1
        L_0x002b:
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0046;
                case 1: goto L_0x0045;
                case 2: goto L_0x0044;
                default: goto L_0x0030;
            }
        L_0x0030:
            android.content.SharedPreferences r1 = r4.preferences
            boolean r1 = r1.contains(r0)
            if (r1 == 0) goto L_0x004a
            android.content.SharedPreferences r1 = r4.preferences
            r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            long r1 = r1.getLong(r0, r2)
            return r1
        L_0x0044:
            return r2
        L_0x0045:
            return r2
        L_0x0046:
            r1 = 32771(0x8003, double:1.6191E-319)
            return r1
        L_0x004a:
            java.lang.String r1 = r4.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Key '"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = "' not found in shared preferences"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            chip.platform.AndroidChipPlatformException r1 = new chip.platform.AndroidChipPlatformException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: chip.platform.PreferencesConfigurationManager.readConfigValueLong(java.lang.String, java.lang.String):long");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readConfigValueStr(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = r4.getKey(r5, r6)
            int r1 = r0.hashCode()
            switch(r1) {
                case -2130344416: goto L_0x0052;
                case -1929739266: goto L_0x0048;
                case -1616392198: goto L_0x003e;
                case -1254840078: goto L_0x0034;
                case -566229773: goto L_0x002a;
                case 903669694: goto L_0x0020;
                case 1429591909: goto L_0x0016;
                case 1492432615: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x005c
        L_0x000c:
            java.lang.String r1 = "chip-factory:software-version-str"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 2
            goto L_0x005d
        L_0x0016:
            java.lang.String r1 = "chip-factory:product-label"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 7
            goto L_0x005d
        L_0x0020:
            java.lang.String r1 = "chip-factory:serial-num"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 4
            goto L_0x005d
        L_0x002a:
            java.lang.String r1 = "chip-factory:hardware-ver-str"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 1
            goto L_0x005d
        L_0x0034:
            java.lang.String r1 = "chip-factory:part-number"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 5
            goto L_0x005d
        L_0x003e:
            java.lang.String r1 = "chip-factory:product-name"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 0
            goto L_0x005d
        L_0x0048:
            java.lang.String r1 = "chip-factory:mfg-date"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 3
            goto L_0x005d
        L_0x0052:
            java.lang.String r1 = "chip-factory:product-url"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 6
            goto L_0x005d
        L_0x005c:
            r1 = -1
        L_0x005d:
            switch(r1) {
                case 0: goto L_0x0085;
                case 1: goto L_0x0082;
                case 2: goto L_0x007f;
                case 3: goto L_0x007c;
                case 4: goto L_0x0079;
                case 5: goto L_0x0076;
                case 6: goto L_0x0073;
                case 7: goto L_0x0070;
                default: goto L_0x0060;
            }
        L_0x0060:
            android.content.SharedPreferences r1 = r4.preferences
            boolean r1 = r1.contains(r0)
            if (r1 == 0) goto L_0x0088
            android.content.SharedPreferences r1 = r4.preferences
            r2 = 0
            java.lang.String r1 = r1.getString(r0, r2)
            return r1
        L_0x0070:
            java.lang.String r1 = "X10"
            return r1
        L_0x0073:
            java.lang.String r1 = "https://buildwithmatter.com/"
            return r1
        L_0x0076:
            java.lang.String r1 = "TEST_ANDROID_PRODUCT_BLUE"
            return r1
        L_0x0079:
            java.lang.String r1 = "TEST_ANDROID_SN"
            return r1
        L_0x007c:
            java.lang.String r1 = "2021-12-06"
            return r1
        L_0x007f:
            java.lang.String r1 = "prerelease(android)"
            return r1
        L_0x0082:
            java.lang.String r1 = "TEST_ANDROID_VERSION"
            return r1
        L_0x0085:
            java.lang.String r1 = "TEST_ANDROID_PRODUCT"
            return r1
        L_0x0088:
            java.lang.String r1 = r4.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Key '"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = "' not found in shared preferences"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            chip.platform.AndroidChipPlatformException r1 = new chip.platform.AndroidChipPlatformException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: chip.platform.PreferencesConfigurationManager.readConfigValueStr(java.lang.String, java.lang.String):java.lang.String");
    }

    public byte[] readConfigValueBin(String namespace, String name) {
        String key = getKey(namespace, name);
        if (this.preferences.contains(key)) {
            return Base64.getDecoder().decode(this.preferences.getString(key, (String) null));
        }
        String value = this.TAG;
        Log.d(value, "Key '" + key + "' not found in shared preferences");
        throw new AndroidChipPlatformException();
    }

    public void writeConfigValueLong(String namespace, String name, long val) {
        this.preferences.edit().putLong(getKey(namespace, name), val).apply();
    }

    public void writeConfigValueStr(String namespace, String name, String val) {
        this.preferences.edit().putString(getKey(namespace, name), val).apply();
    }

    public void writeConfigValueBin(String namespace, String name, byte[] val) {
        String key = getKey(namespace, name);
        if (val != null) {
            this.preferences.edit().putString(key, Base64.getEncoder().encodeToString(val)).apply();
            return;
        }
        this.preferences.edit().remove(key).apply();
    }

    public void clearConfigValue(String namespace, String name) {
        if (namespace != null && name != null) {
            this.preferences.edit().remove(getKey(namespace, name)).apply();
        } else if (namespace != null && name == null) {
            String pre = getKey(namespace, (String) null);
            SharedPreferences.Editor editor = this.preferences.edit();
            for (Map.Entry<String, ?> entry : this.preferences.getAll().entrySet()) {
                String key = entry.getKey();
                if (key.startsWith(pre)) {
                    editor.remove(key);
                }
            }
            editor.apply();
        } else if (namespace == null && name == null) {
            this.preferences.edit().clear().apply();
        }
    }

    public boolean configValueExists(String namespace, String name) {
        return this.preferences.contains(getKey(namespace, name));
    }

    private String getKey(String namespace, String name) {
        if (namespace != null && name != null) {
            return namespace + ":" + name;
        } else if (namespace == null || name != null) {
            throw new AndroidChipPlatformException();
        } else {
            return namespace + ":";
        }
    }
}
