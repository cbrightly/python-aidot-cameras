package com.google.android.datatransport.runtime.dagger.internal;

import com.google.android.datatransport.runtime.dagger.MembersInjector;

public final class MembersInjectors {
    public static <T> MembersInjector<T> noOp() {
        return NoOpMembersInjector.INSTANCE;
    }

    public enum NoOpMembersInjector implements MembersInjector<Object> {
        INSTANCE;

        public void injectMembers(Object instance) {
            Preconditions.checkNotNull(instance, "Cannot inject members into a null reference");
        }
    }

    private MembersInjectors() {
    }
}
