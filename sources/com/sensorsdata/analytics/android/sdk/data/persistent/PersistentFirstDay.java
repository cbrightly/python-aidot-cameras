package com.sensorsdata.analytics.android.sdk.data.persistent;

import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;

public class PersistentFirstDay extends PersistentIdentity<String> {
    public PersistentFirstDay() {
        super(DbParams.PersistentName.FIRST_DAY, new PersistentIdentity.PersistentSerializer<String>() {
            public String load(String value) {
                return value;
            }

            public String save(String item) {
                return item;
            }

            public String create() {
                return null;
            }
        });
    }
}
