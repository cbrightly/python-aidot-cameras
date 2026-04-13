package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat;

public class CallbackWithHandler {
    @NonNull
    private final FontsContractCompat.FontRequestCallback mCallback;
    @NonNull
    private final Handler mCallbackHandler;

    CallbackWithHandler(@NonNull FontsContractCompat.FontRequestCallback callback, @NonNull Handler callbackHandler) {
        this.mCallback = callback;
        this.mCallbackHandler = callbackHandler;
    }

    CallbackWithHandler(@NonNull FontsContractCompat.FontRequestCallback callback) {
        this.mCallback = callback;
        this.mCallbackHandler = CalleeHandler.create();
    }

    private void onTypefaceRetrieved(@NonNull final Typeface typeface) {
        final FontsContractCompat.FontRequestCallback callback = this.mCallback;
        this.mCallbackHandler.post(new Runnable() {
            public void run() {
                callback.onTypefaceRetrieved(typeface);
            }
        });
    }

    private void onTypefaceRequestFailed(final int reason) {
        final FontsContractCompat.FontRequestCallback callback = this.mCallback;
        this.mCallbackHandler.post(new Runnable() {
            public void run() {
                callback.onTypefaceRequestFailed(reason);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void onTypefaceResult(@NonNull FontRequestWorker.TypefaceResult typefaceResult) {
        if (typefaceResult.isSuccess()) {
            onTypefaceRetrieved(typefaceResult.mTypeface);
        } else {
            onTypefaceRequestFailed(typefaceResult.mResult);
        }
    }
}
