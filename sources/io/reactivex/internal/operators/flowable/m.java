package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.functions.e;
import org.reactivestreams.c;

/* compiled from: FlowableInternalHelper */
public enum m implements e<c> {
    INSTANCE;

    public void accept(c t) {
        t.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }
}
