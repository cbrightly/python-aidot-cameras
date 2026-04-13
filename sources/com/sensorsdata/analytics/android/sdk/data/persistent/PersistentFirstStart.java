package com.sensorsdata.analytics.android.sdk.data.persistent;

import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;

public class PersistentFirstStart extends PersistentIdentity<Boolean> {
    public PersistentFirstStart() {
        super(DbParams.PersistentName.FIRST_START, new PersistentIdentity.PersistentSerializer<Boolean>() {
            public Boolean load(String value) {
                return false;
            }

            public String save(Boolean item) {
                return item == null ? create().toString() : String.valueOf(true);
            }

            public Boolean create() {
                return true;
            }
        });
    }
}
