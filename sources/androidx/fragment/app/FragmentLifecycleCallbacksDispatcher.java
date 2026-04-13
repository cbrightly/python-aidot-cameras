package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FragmentLifecycleCallbacksDispatcher {
    @NonNull
    private final FragmentManager mFragmentManager;
    @NonNull
    private final CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList<>();

    public static final class FragmentLifecycleCallbacksHolder {
        @NonNull
        final FragmentManager.FragmentLifecycleCallbacks mCallback;
        final boolean mRecursive;

        FragmentLifecycleCallbacksHolder(@NonNull FragmentManager.FragmentLifecycleCallbacks callback, boolean recursive) {
            this.mCallback = callback;
            this.mRecursive = recursive;
        }
    }

    FragmentLifecycleCallbacksDispatcher(@NonNull FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks cb, boolean recursive) {
        this.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(cb, recursive));
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks cb) {
        synchronized (this.mLifecycleCallbacks) {
            int i = 0;
            int count = this.mLifecycleCallbacks.size();
            while (true) {
                if (i >= count) {
                    break;
                } else if (this.mLifecycleCallbacks.get(i).mCallback == cb) {
                    this.mLifecycleCallbacks.remove(i);
                    break;
                } else {
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentPreAttached(@NonNull Fragment f, boolean onlyRecursive) {
        Context context = this.mFragmentManager.getHost().getContext();
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPreAttached(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentPreAttached(this.mFragmentManager, f, context);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentAttached(@NonNull Fragment f, boolean onlyRecursive) {
        Context context = this.mFragmentManager.getHost().getContext();
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentAttached(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentAttached(this.mFragmentManager, f, context);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentPreCreated(@NonNull Fragment f, @Nullable Bundle savedInstanceState, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPreCreated(f, savedInstanceState, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentPreCreated(this.mFragmentManager, f, savedInstanceState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentCreated(@NonNull Fragment f, @Nullable Bundle savedInstanceState, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentCreated(f, savedInstanceState, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentCreated(this.mFragmentManager, f, savedInstanceState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentActivityCreated(@NonNull Fragment f, @Nullable Bundle savedInstanceState, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentActivityCreated(f, savedInstanceState, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentActivityCreated(this.mFragmentManager, f, savedInstanceState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentViewCreated(@NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentViewCreated(f, v, savedInstanceState, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentViewCreated(this.mFragmentManager, f, v, savedInstanceState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentStarted(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentStarted(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentStarted(this.mFragmentManager, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentResumed(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentResumed(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentResumed(this.mFragmentManager, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentPaused(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPaused(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentPaused(this.mFragmentManager, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentStopped(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentStopped(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentStopped(this.mFragmentManager, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentSaveInstanceState(@NonNull Fragment f, @NonNull Bundle outState, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentSaveInstanceState(f, outState, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentSaveInstanceState(this.mFragmentManager, f, outState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentViewDestroyed(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentViewDestroyed(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentViewDestroyed(this.mFragmentManager, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentDestroyed(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentDestroyed(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentDestroyed(this.mFragmentManager, f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnFragmentDetached(@NonNull Fragment f, boolean onlyRecursive) {
        Fragment parent = this.mFragmentManager.getParent();
        if (parent != null) {
            parent.getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentDetached(f, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder holder = it.next();
            if (!onlyRecursive || holder.mRecursive) {
                holder.mCallback.onFragmentDetached(this.mFragmentManager, f);
            }
        }
    }
}
