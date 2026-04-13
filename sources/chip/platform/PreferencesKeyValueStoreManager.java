package chip.platform;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferencesKeyValueStoreManager implements KeyValueStoreManager {
    private final String PREFERENCE_FILE_KEY = "chip.platform.KeyValueStore";
    private final String TAG = KeyValueStoreManager.class.getSimpleName();
    private SharedPreferences preferences;

    public PreferencesKeyValueStoreManager(Context context) {
        this.preferences = context.getSharedPreferences("chip.platform.KeyValueStore", 0);
    }

    public String get(String key) {
        String value = this.preferences.getString(key, (String) null);
        if (value == null) {
            String str = this.TAG;
            Log.d(str, "Key '" + key + "' not found in shared preferences");
        }
        return value;
    }

    public void set(String key, String value) {
        this.preferences.edit().putString(key, value).apply();
    }

    public void delete(String key) {
        this.preferences.edit().remove(key).apply();
    }
}
