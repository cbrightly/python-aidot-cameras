package com.telink.ble.mesh.core.access;

import com.meituan.robust.ChangeQuickRedirect;

public class BlobTransfer {
    Direction a = Direction.INITIATOR_TO_DISTRIBUTOR;

    public enum Direction {
        INITIATOR_TO_DISTRIBUTOR,
        DISTRIBUTOR_TO_NODE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }
}
