package com.trello.rxlifecycle3.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import com.trello.rxlifecycle3.c;

public abstract class RxActivity extends Activity implements b<a> {
    private final io.reactivex.subjects.a<a> c = io.reactivex.subjects.a.p0();

    /* access modifiers changed from: protected */
    @SensorsDataInstrumented
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PushAutoTrackHelper.onNewIntent(this, intent);
    }

    @CheckResult
    @NonNull
    public final <T> c<T> h0() {
        return com.trello.rxlifecycle3.android.c.a(this.c);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.c.onNext(a.CREATE);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onStart() {
        super.onStart();
        this.c.onNext(a.START);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onResume() {
        super.onResume();
        this.c.onNext(a.RESUME);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onPause() {
        this.c.onNext(a.PAUSE);
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onStop() {
        this.c.onNext(a.STOP);
        super.onStop();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onDestroy() {
        this.c.onNext(a.DESTROY);
        super.onDestroy();
    }
}
