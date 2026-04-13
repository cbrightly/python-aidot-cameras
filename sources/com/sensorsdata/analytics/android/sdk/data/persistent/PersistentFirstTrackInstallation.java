package com.sensorsdata.analytics.android.sdk.data.persistent;

import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentIdentity;

public class PersistentFirstTrackInstallation extends PersistentIdentity<Boolean> {
    public PersistentFirstTrackInstallation() {
        super(DbParams.PersistentName.FIRST_INSTALL, new PersistentIdentity.PersistentSerializer<Boolean>() {
            public Boolean load(String value) {
                return false;
            }

            public String save(Boolean item) {
                return item == null ? create().toString() : String.valueOf(item);
            }

            public Boolean create() {
                return true;
            }
        });
    }
}
