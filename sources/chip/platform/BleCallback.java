package chip.platform;

public interface BleCallback {
    void onCloseBleComplete(int i);

    void onNotifyChipConnectionClosed(int i);
}
