package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.b;
import com.bumptech.glide.i;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SupportRequestManagerFragment extends Fragment {
    private final a c;
    private final p d;
    private final Set<SupportRequestManagerFragment> f;
    @Nullable
    private SupportRequestManagerFragment q;
    @Nullable
    private i x;
    @Nullable
    private Fragment y;

    public SupportRequestManagerFragment() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    public SupportRequestManagerFragment(@NonNull a lifecycle) {
        this.d = new a();
        this.f = new HashSet();
        this.c = lifecycle;
    }

    public void w1(@Nullable i requestManager) {
        this.x = requestManager;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public a n1() {
        return this.c;
    }

    @Nullable
    public i p1() {
        return this.x;
    }

    @NonNull
    public p q1() {
        return this.d;
    }

    private void l1(SupportRequestManagerFragment child) {
        this.f.add(child);
    }

    private void u1(SupportRequestManagerFragment child) {
        this.f.remove(child);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Set<SupportRequestManagerFragment> m1() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.q;
        if (supportRequestManagerFragment == null) {
            return Collections.emptySet();
        }
        if (equals(supportRequestManagerFragment)) {
            return Collections.unmodifiableSet(this.f);
        }
        Set<SupportRequestManagerFragment> descendants = new HashSet<>();
        for (SupportRequestManagerFragment fragment : this.q.m1()) {
            if (s1(fragment.o1())) {
                descendants.add(fragment);
            }
        }
        return Collections.unmodifiableSet(descendants);
    }

    /* access modifiers changed from: package-private */
    public void v1(@Nullable Fragment parentFragmentHint) {
        FragmentManager rootFragmentManager;
        this.y = parentFragmentHint;
        if (parentFragmentHint != null && parentFragmentHint.getContext() != null && (rootFragmentManager = r1(parentFragmentHint)) != null) {
            t1(parentFragmentHint.getContext(), rootFragmentManager);
        }
    }

    @Nullable
    private static FragmentManager r1(@NonNull Fragment fragment) {
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment.getFragmentManager();
    }

    @Nullable
    private Fragment o1() {
        Fragment fragment = getParentFragment();
        return fragment != null ? fragment : this.y;
    }

    private boolean s1(@NonNull Fragment fragment) {
        Fragment root = o1();
        while (true) {
            Fragment parentFragment = fragment.getParentFragment();
            Fragment parentFragment2 = parentFragment;
            if (parentFragment == null) {
                return false;
            }
            if (parentFragment2.equals(root)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    private void t1(@NonNull Context context, @NonNull FragmentManager fragmentManager) {
        x1();
        SupportRequestManagerFragment k = b.c(context).k().k(fragmentManager);
        this.q = k;
        if (!equals(k)) {
            this.q.l1(this);
        }
    }

    private void x1() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.q;
        if (supportRequestManagerFragment != null) {
            supportRequestManagerFragment.u1(this);
            this.q = null;
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentManager rootFragmentManager = r1(this);
        if (rootFragmentManager != null) {
            try {
                t1(getContext(), rootFragmentManager);
            } catch (IllegalStateException e) {
                if (Log.isLoggable("SupportRMFragment", 5)) {
                    Log.w("SupportRMFragment", "Unable to register fragment with root", e);
                }
            }
        } else if (Log.isLoggable("SupportRMFragment", 5)) {
            Log.w("SupportRMFragment", "Unable to register fragment with root, ancestor detached");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.y = null;
        x1();
    }

    public void onStart() {
        super.onStart();
        this.c.d();
    }

    public void onStop() {
        super.onStop();
        this.c.e();
    }

    public void onDestroy() {
        super.onDestroy();
        this.c.c();
        x1();
    }

    public String toString() {
        return super.toString() + "{parent=" + o1() + "}";
    }

    public class a implements p {
        a() {
        }

        @NonNull
        public Set<i> a() {
            Set<SupportRequestManagerFragment> descendantFragments = SupportRequestManagerFragment.this.m1();
            Set<RequestManager> descendants = new HashSet<>(descendantFragments.size());
            for (SupportRequestManagerFragment fragment : descendantFragments) {
                if (fragment.p1() != null) {
                    descendants.add(fragment.p1());
                }
            }
            return descendants;
        }

        public String toString() {
            return super.toString() + "{fragment=" + SupportRequestManagerFragment.this + "}";
        }
    }
}
