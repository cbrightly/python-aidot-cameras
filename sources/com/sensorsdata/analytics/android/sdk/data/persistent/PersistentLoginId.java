package com.sensorsdata.analytics.android.sdk.data.persistent;

import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;

public class PersistentLoginId extends PersistentIdentity<String> {
    public PersistentLoginId() {
        super(DbParams.PersistentName.LOGIN_ID, new PersistentIdentity.PersistentSerializer<String>() {
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
