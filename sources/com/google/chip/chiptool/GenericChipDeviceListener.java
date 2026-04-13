package com.google.chip.chiptool;

import chip.devicecontroller.ChipDeviceController;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: GenericChipDeviceListener.kt */
public class GenericChipDeviceListener implements ChipDeviceController.CompletionListener {
    public void onConnectDeviceComplete() {
    }

    public void onStatusUpdate(int status) {
    }

    public void onPairingComplete(int code) {
    }

    public void onPairingDeleted(int code) {
    }

    public void onCommissioningComplete(long nodeId, int errorCode) {
    }

    public void onReadCommissioningInfo(int vendorId, int productId, int wifiEndpointId, int threadEndpointId) {
    }

    public void onCommissioningStatusUpdate(long nodeId, @NotNull String stage, int errorCode) {
        k.e(stage, "stage");
    }

    public void onNotifyChipConnectionClosed() {
    }

    public void onCloseBleComplete() {
    }

    public void onError(@Nullable Throwable error) {
    }

    public void onOpCSRGenerationComplete(@NotNull byte[] csr) {
        k.e(csr, "csr");
    }
}
