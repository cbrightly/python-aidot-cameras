package com.sensorsdata.analytics.android.sdk.data.persistent;

import android.content.Context;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.UUID;

public class PersistentDistinctId extends PersistentIdentity<String> {
    public PersistentDistinctId(final Context context) {
        super(DbParams.PersistentName.DISTINCT_ID, new PersistentIdentity.PersistentSerializer<String>() {
            public String load(String value) {
                return value;
            }

            public String save(String item) {
                return item == null ? create() : item;
            }

            public String create() {
                String androidId = SensorsDataUtils.getAndroidID(context);
                if (SensorsDataUtils.isValidAndroidId(androidId)) {
                    return androidId;
                }
                return UUID.randomUUID().toString();
            }
        });
    }
}
