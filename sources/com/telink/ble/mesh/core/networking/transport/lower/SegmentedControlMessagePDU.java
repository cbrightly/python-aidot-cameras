package com.telink.ble.mesh.core.networking.transport.lower;

import com.meituan.robust.ChangeQuickRedirect;

public class SegmentedControlMessagePDU extends LowerTransportPDU {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final byte g = 1;
    private int h = 0;
}
