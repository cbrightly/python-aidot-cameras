package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.b;
import com.bumptech.glide.i;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* compiled from: RequestManagerFragment */
public class n extends Fragment {
    private final a c;
    private final p d;
    private final Set<n> f;
    @Nullable
    private i q;
    @Nullable
    private n x;
    @Nullable
    private Fragment y;

    public n() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    n(@NonNull a lifecycle) {
        this.d = new a();
        this.f = new HashSet();
        this.c = lifecycle;
    }

    public void k(@Nullable i requestManager) {
        this.q = requestManager;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public a c() {
        return this.c;
    }

    @Nullable
    public i e() {
        return this.q;
    }

    @NonNull
    public p f() {
        return this.d;
    }

    private void a(n child) {
        this.f.add(child);
    }

    private void i(n child) {
        this.f.remove(child);
    }

    /* access modifiers changed from: package-private */
    @TargetApi(17)
    @NonNull
    public Set<n> b() {
        if (equals(this.x)) {
            return Collections.unmodifiableSet(this.f);
        }
        if (this.x == null || Build.VERSION.SDK_INT < 17) {
            return Collections.emptySet();
        }
        Set<RequestManagerFragment> descendants = new HashSet<>();
        for (n fragment : this.x.b()) {
            if (g(fragment.getParentFragment())) {
                descendants.add(fragment);
            }
        }
        return Collections.unmodifiableSet(descendants);
    }

    /* access modifiers changed from: package-private */
    public void j(@Nullable Fragment parentFragmentHint) {
        this.y = parentFragmentHint;
        if (parentFragmentHint != null && parentFragmentHint.getActivity() != null) {
            h(parentFragmentHint.getActivity());
        }
    }

    @TargetApi(17)
    @Nullable
    private Fragment d() {
        Fragment fragment;
        if (Build.VERSION.SDK_INT >= 17) {
            fragment = getParentFragment();
        } else {
            fragment = null;
        }
        return fragment != null ? fragment : this.y;
    }

    @TargetApi(17)
    private boolean g(@NonNull Fragment fragment) {
        Fragment root = getParentFragment();
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

    private void h(@NonNull Activity activity) {
        l();
        n i = b.c(activity).k().i(activity);
        this.x = i;
        if (!equals(i)) {
            this.x.a(this);
        }
    }

    private void l() {
        n nVar = this.x;
        if (nVar != null) {
            nVar.i(this);
            this.x = null;
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            h(activity);
        } catch (IllegalStateException e) {
            if (Log.isLoggable("RMFragment", 5)) {
                Log.w("RMFragment", "Unable to register fragment with root", e);
            }
        }
    }

    public void onDetach() {
        super.onDetach();
        l();
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
        l();
    }

    public String toString() {
        return super.toString() + "{parent=" + d() + "}";
    }

    /* compiled from: RequestManagerFragment */
    public class a implements p {
        a() {
        }

        @NonNull
        public Set<i> a() {
            Set<n> b = n.this.b();
            Set<RequestManager> descendants = new HashSet<>(b.size());
            for (n fragment : b) {
                if (fragment.e() != null) {
                    descendants.add(fragment.e());
                }
            }
            return descendants;
        }

        public String toString() {
            return super.toString() + "{fragment=" + n.this + "}";
        }
    }
}
