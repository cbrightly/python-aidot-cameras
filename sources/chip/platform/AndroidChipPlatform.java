package chip.platform;

import timber.log.a;

public final class AndroidChipPlatform {
    private static final String TAG = "AndroidChipPlatform";
    private BleManager mBleManager = null;

    private native void initChipStack();

    private native void nativeSetBLEManager(BleManager bleManager);

    private native void nativeSetDnssdDelegates(ServiceResolver serviceResolver, ServiceBrowser serviceBrowser, ChipMdnsCallback chipMdnsCallback);

    private native void setConfigurationManager(ConfigurationManager configurationManager);

    private native void setDiagnosticDataProviderManager(DiagnosticDataProvider diagnosticDataProvider);

    private native void setKeyValueStoreManager(KeyValueStoreManager keyValueStoreManager);

    public native void handleConnectionError(int i);

    public native void handleIndicationReceived(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    public native void handleSubscribeComplete(int i, byte[] bArr, byte[] bArr2, boolean z);

    public native void handleUnsubscribeComplete(int i, byte[] bArr, byte[] bArr2, boolean z);

    public native void handleWriteConfirmation(int i, byte[] bArr, byte[] bArr2, boolean z);

    public native boolean updateCommissionableDataProviderData(String str, String str2, int i, long j, int i2);

    public AndroidChipPlatform(BleManager ble, KeyValueStoreManager kvm, ConfigurationManager cfg, ServiceResolver resolver, ServiceBrowser browser, ChipMdnsCallback chipMdnsCallback, DiagnosticDataProvider dataProvider) {
        try {
            setBLEManager(ble);
            setKeyValueStoreManager(kvm);
            setConfigurationManager(cfg);
            setDnssdDelegates(resolver, browser, chipMdnsCallback);
            setDiagnosticDataProviderManager(dataProvider);
            initChipStack();
        } catch (Throwable throwable) {
            a.b g = a.g(TAG);
            g.m("AndroidChipPlatform Constructor fail:" + throwable.getMessage(), new Object[0]);
        }
    }

    public BleManager getBLEManager() {
        return this.mBleManager;
    }

    private void setBLEManager(BleManager manager) {
        if (manager != null) {
            this.mBleManager = manager;
            manager.setAndroidChipPlatform(this);
            nativeSetBLEManager(manager);
        }
    }

    private void setDnssdDelegates(ServiceResolver resolver, ServiceBrowser browser, ChipMdnsCallback chipMdnsCallback) {
        if (resolver != null) {
            nativeSetDnssdDelegates(resolver, browser, chipMdnsCallback);
        }
    }
}
