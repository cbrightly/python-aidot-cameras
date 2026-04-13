package com.sensorsdata.analytics.android.sdk.data.persistent;

import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;
import org.json.JSONException;
import org.json.JSONObject;

public class PersistentSuperProperties extends PersistentIdentity<JSONObject> {
    public PersistentSuperProperties() {
        super(DbParams.PersistentName.SUPER_PROPERTIES, new PersistentIdentity.PersistentSerializer<JSONObject>() {
            public JSONObject load(String value) {
                try {
                    return new JSONObject(value);
                } catch (JSONException e) {
                    SALog.d("Persistent", "failed to load SuperProperties from SharedPreferences.", e);
                    return new JSONObject();
                }
            }

            public String save(JSONObject item) {
                return item == null ? create().toString() : item.toString();
            }

            public JSONObject create() {
                return new JSONObject();
            }
        });
    }
}
