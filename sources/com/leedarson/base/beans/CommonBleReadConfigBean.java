package com.leedarson.base.beans;

import com.leedarson.serviceinterface.BleC075Service;
import java.util.UUID;

public class CommonBleReadConfigBean {
    public static final String ENCRYPT_AES_128 = "aes128";
    public static final String ENCRYPT_AES_256 = "aes256";
    public static final String ENCRYPT_AES_256_CTR = "aes256CTR";
    public UUID characterUUID;
    public BleC075Service.CommonBleCallback commonBleCallback;
    public String encrypt = "";
    public String encryptKey;
    public String jsbridgeCallbackKey;
    public String mac;
    public boolean retryWhenInterrupt = true;
    public UUID serviceUUID;
}
