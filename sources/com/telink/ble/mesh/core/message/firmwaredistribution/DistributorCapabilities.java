package com.telink.ble.mesh.core.message.firmwaredistribution;

import com.meituan.robust.ChangeQuickRedirect;

public interface DistributorCapabilities {

    public enum OOBRetrievalSupported {
        SUPPORTED(1),
        NotSupported(0);
        
        public static ChangeQuickRedirect changeQuickRedirect;
        final int value;

        private OOBRetrievalSupported(int value2) {
            this.value = value2;
        }
    }
}
