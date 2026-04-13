package chip.devicecontroller;

public class GetConnectedDeviceCallbackJni {
    private long callbackHandle;
    private GetConnectedDeviceCallback wrappedCallback;

    public interface GetConnectedDeviceCallback {
        void onConnectionFailure(long j, Exception exc);

        void onDeviceConnected(long j);
    }

    private native void deleteCallback(long j);

    private native long newCallback(GetConnectedDeviceCallback getConnectedDeviceCallback);

    public GetConnectedDeviceCallbackJni(GetConnectedDeviceCallback wrappedCallback2) {
        this.wrappedCallback = wrappedCallback2;
        this.callbackHandle = newCallback(wrappedCallback2);
    }

    /* access modifiers changed from: package-private */
    public long getCallbackHandle() {
        return this.callbackHandle;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        super.finalize();
        long j = this.callbackHandle;
        if (j != 0) {
            deleteCallback(j);
            this.callbackHandle = 0;
        }
    }
}
