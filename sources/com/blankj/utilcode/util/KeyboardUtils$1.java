package com.blankj.utilcode.util;

import android.os.Bundle;
import android.os.ResultReceiver;

public final class KeyboardUtils$1 extends ResultReceiver {
    /* access modifiers changed from: protected */
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == 1 || resultCode == 3) {
            o.c();
        }
    }
}
