package chip.devicecontroller;

public interface OpenCommissioningCallback {
    void onError(int i, long j);

    void onSuccess(long j, String str, String str2);
}
