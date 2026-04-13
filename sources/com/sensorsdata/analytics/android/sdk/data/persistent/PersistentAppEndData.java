package com.sensorsdata.analytics.android.sdk.data.persistent;

import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;

public class PersistentAppEndData extends PersistentIdentity<String> {
    public PersistentAppEndData() {
        super(DbParams.PersistentName.APP_END_DATA, new PersistentIdentity.PersistentSerializer<String>() {
            public String load(String value) {
                return value;
            }

            public String save(String item) {
                return item == null ? create() : item;
            }

            public String create() {
                return "";
            }
        });
    }
}
