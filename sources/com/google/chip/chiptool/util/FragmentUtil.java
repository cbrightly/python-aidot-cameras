package com.google.chip.chiptool.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentUtil.kt */
public final class FragmentUtil {
    @NotNull
    public static final FragmentUtil INSTANCE = new FragmentUtil();

    private FragmentUtil() {
    }

    @Nullable
    public static final <T> T getHost(@NotNull Fragment source, @NotNull Class<T> hostClass) {
        String activityName;
        k.e(source, "source");
        k.e(hostClass, "hostClass");
        FragmentActivity activity = source.getActivity();
        Fragment parentFragment = source.getParentFragment();
        if (hostClass.isInstance(parentFragment)) {
            return hostClass.cast(parentFragment);
        }
        if (hostClass.isInstance(activity)) {
            return hostClass.cast(activity);
        }
        String parentName = null;
        if (activity == null) {
            activityName = null;
        } else {
            activityName = activity.getClass().getSimpleName();
        }
        if (parentFragment != null) {
            parentName = parentFragment.getClass().getSimpleName();
        }
        d0 d0Var = d0.a;
        String exceptionString = String.format(Locale.US, "Neither the parent Fragment " + parentName + " nor the host Activity " + activityName + " of " + source.getClass().getSimpleName() + " implement " + hostClass.getSimpleName() + '.', Arrays.copyOf(new Object[0], 0));
        k.d(exceptionString, "java.lang.String.format(locale, format, *args)");
        throw new IllegalStateException(exceptionString);
    }
}
