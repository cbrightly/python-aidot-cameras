package com.telink.bluetooth.light;

import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;
import java.util.UUID;

/* compiled from: UuidInformation */
public enum k {
    TELINK_SERVICE(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1910"), "Telink SmartLight Service"),
    TELINK_CHARACTERISTIC_PAIR(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1914"), "pair"),
    TELINK_CHARACTERISTIC_COMMAND(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1912"), "command"),
    TELINK_CHARACTERISTIC_NOTIFY(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1911"), "notify"),
    TELINK_CHARACTERISTIC_OTA(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1913"), "ota"),
    SERVICE_DEVICE_INFORMATION(UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"), "Device Information Service"),
    CHARACTERISTIC_FIRMWARE(UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb"), "Firmware Revision"),
    NEW_SERVICE_DEVICE_INFORMATION(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1920"), "Device Information Service"),
    NEW_CHARACTERISTIC_FIRMWARE(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1921"), "New Firmware Revision"),
    CHARACTERISTIC_MANUFACTURER(UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb"), "Manufacturer Name"),
    CHARACTERISTIC_MODEL(UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb"), "Model Number"),
    CHARACTERISTIC_HARDWARE(UUID.fromString("00002a27-0000-1000-8000-00805f9b34fb"), "Hardware Revision"),
    SERVICE_DEVICEFW_INFORMATION(UUID.fromString("19200d0c-0b0a-0908-0706-050403020100"), "Device Information Service"),
    CHARACTERISTICFW_FIRMWARE(UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1926"), "Firmware ID");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    private String info;
    private UUID value;

    private k(UUID value2, @Nullable String info2) {
        this.value = value2;
        this.info = info2;
    }

    public String getInfo() {
        return this.info;
    }

    public UUID getValue() {
        return this.value;
    }
}
