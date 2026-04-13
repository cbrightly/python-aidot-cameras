package com.trello.rxlifecycle3.components.support;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import com.trello.rxlifecycle3.b;
import com.trello.rxlifecycle3.c;
import io.reactivex.subjects.a;

public abstract class RxAppCompatDialogFragment extends AppCompatDialogFragment implements b<com.trello.rxlifecycle3.android.b> {
    private final a<com.trello.rxlifecycle3.android.b> c = a.p0();

    @SensorsDataInstrumented
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        FragmentTrackHelper.trackOnHiddenChanged(this, z);
    }

    @SensorsDataInstrumented
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z);
    }

    @CheckResult
    @NonNull
    public final <T> c<T> h0() {
        return com.trello.rxlifecycle3.android.c.b(this.c);
    }

    @CallSuper
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.c.onNext(com.trello.rxlifecycle3.android.b.ATTACH);
    }

    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.c.onNext(com.trello.rxlifecycle3.android.b.CREATE);
    }

    @CallSuper
    @SensorsDataInstrumented
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.c.onNext(com.trello.rxlifecycle3.android.b.CREATE_VIEW);
        FragmentTrackHelper.onFragmentViewCreated(this, view, savedInstanceState);
    }

    @CallSuper
    public void onStart() {
        super.onStart();
        this.c.onNext(com.trello.rxlifecycle3.android.b.START);
    }

    @CallSuper
    @SensorsDataInstrumented
    public void onResume() {
        super.onResume();
        this.c.onNext(com.trello.rxlifecycle3.android.b.RESUME);
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @CallSuper
    @SensorsDataInstrumented
    public void onPause() {
        this.c.onNext(com.trello.rxlifecycle3.android.b.PAUSE);
        super.onPause();
        FragmentTrackHelper.trackFragmentPause(this);
    }

    @CallSuper
    public void onStop() {
        this.c.onNext(com.trello.rxlifecycle3.android.b.STOP);
        super.onStop();
    }

    @CallSuper
    public void onDestroyView() {
        this.c.onNext(com.trello.rxlifecycle3.android.b.DESTROY_VIEW);
        super.onDestroyView();
    }

    @CallSuper
    public void onDestroy() {
        this.c.onNext(com.trello.rxlifecycle3.android.b.DESTROY);
        super.onDestroy();
    }

    @CallSuper
    public void onDetach() {
        this.c.onNext(com.trello.rxlifecycle3.android.b.DETACH);
        super.onDetach();
    }
}
