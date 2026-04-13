package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransition;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.maps.android.BuildConfig;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager implements FragmentResultOwner {
    private static boolean DEBUG = false;
    private static final String EXTRA_CREATED_FILLIN_INTENT = "androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE";
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static final String TAG = "FragmentManager";
    static boolean USE_STATE_MANAGER = true;
    ArrayList<BackStackRecord> mBackStack;
    private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    int mCurState = -1;
    private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() {
        @NonNull
        public SpecialEffectsController createController(@NonNull ViewGroup container) {
            return new DefaultSpecialEffectsController(container);
        }
    };
    private boolean mDestroyed;
    private Runnable mExecCommit = new Runnable() {
        public void run() {
            FragmentManager.this.execPendingActions(true);
        }
    };
    private boolean mExecutingActions;
    private Map<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = Collections.synchronizedMap(new HashMap());
    private FragmentFactory mFragmentFactory = null;
    /* access modifiers changed from: private */
    public final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback() {
        public void onStart(@NonNull Fragment fragment, @NonNull CancellationSignal signal) {
            FragmentManager.this.addCancellationSignal(fragment, signal);
        }

        public void onComplete(@NonNull Fragment f, @NonNull CancellationSignal signal) {
            if (!signal.isCanceled()) {
                FragmentManager.this.removeCancellationSignal(f, signal);
            }
        }
    };
    private boolean mHavePendingDeferredStart;
    private FragmentHostCallback<?> mHost;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory() {
        @NonNull
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
            return FragmentManager.this.getHost().instantiate(FragmentManager.this.getHost().getContext(), className, (Bundle) null);
        }
    };
    ArrayDeque<LaunchedFragmentInfo> mLaunchedFragments = new ArrayDeque<>();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList<>();
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
        public void handleOnBackPressed() {
            FragmentManager.this.handleOnBackPressed();
        }
    };
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    private ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    @Nullable
    Fragment mPrimaryNav;
    private ActivityResultLauncher<String[]> mRequestPermissions;
    /* access modifiers changed from: private */
    public final Map<String, LifecycleAwareResultListener> mResultListeners = Collections.synchronizedMap(new HashMap());
    /* access modifiers changed from: private */
    public final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
    private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
    private ActivityResultLauncher<Intent> mStartActivityForResult;
    private ActivityResultLauncher<IntentSenderRequest> mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;

    public interface BackStackEntry {
        @Deprecated
        @Nullable
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbShortTitleRes();

        @Deprecated
        @Nullable
        CharSequence getBreadCrumbTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbTitleRes();

        int getId();

        @Nullable
        String getName();
    }

    public interface OnBackStackChangedListener {
        @MainThread
        void onBackStackChanged();
    }

    public interface OpGenerator {
        boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2);
    }

    @FragmentStateManagerControl
    public static void enableNewStateManager(boolean enabled) {
        USE_STATE_MANAGER = enabled;
    }

    @Deprecated
    public static void enableDebugLogging(boolean enabled) {
        DEBUG = enabled;
    }

    static boolean isLoggingEnabled(int level) {
        return DEBUG || Log.isLoggable(TAG, level);
    }

    public static class LifecycleAwareResultListener implements FragmentResultListener {
        private final Lifecycle mLifecycle;
        private final FragmentResultListener mListener;
        private final LifecycleEventObserver mObserver;

        LifecycleAwareResultListener(@NonNull Lifecycle lifecycle, @NonNull FragmentResultListener listener, @NonNull LifecycleEventObserver observer) {
            this.mLifecycle = lifecycle;
            this.mListener = listener;
            this.mObserver = observer;
        }

        public boolean isAtLeast(Lifecycle.State state) {
            return this.mLifecycle.getCurrentState().isAtLeast(state);
        }

        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            this.mListener.onFragmentResult(requestKey, result);
        }

        public void removeObserver() {
            this.mLifecycle.removeObserver(this.mObserver);
        }
    }

    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        }

        public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        }

        public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        }

        @Deprecated
        public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        }

        public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        }

        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }

        public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        }
    }

    private void throwException(RuntimeException ex) {
        Log.e(TAG, ex.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter pw = new PrintWriter(new LogWriter(TAG));
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump(JustifyTextView.TWO_CHINESE_BLANK, (FileDescriptor) null, pw, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump(JustifyTextView.TWO_CHINESE_BLANK, (FileDescriptor) null, pw, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw ex;
    }

    @NonNull
    @Deprecated
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    @NonNull
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean updates = execPendingActions(true);
        forcePostponedTransactions();
        return updates;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (getBackStackEntryCount() <= 0) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        if (isPrimaryNavigation(r3.mParent) == false) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        r0.setEnabled(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r0 = r3.mOnBackPressedCallback;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateOnBackPressedCallbackEnabled() {
        /*
            r3 = this;
            java.util.ArrayList<androidx.fragment.app.FragmentManager$OpGenerator> r0 = r3.mPendingActions
            monitor-enter(r0)
            java.util.ArrayList<androidx.fragment.app.FragmentManager$OpGenerator> r1 = r3.mPendingActions     // Catch:{ all -> 0x002a }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x002a }
            r2 = 1
            if (r1 != 0) goto L_0x0013
            androidx.activity.OnBackPressedCallback r1 = r3.mOnBackPressedCallback     // Catch:{ all -> 0x002a }
            r1.setEnabled(r2)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            androidx.activity.OnBackPressedCallback r0 = r3.mOnBackPressedCallback
            int r1 = r3.getBackStackEntryCount()
            if (r1 <= 0) goto L_0x0025
            androidx.fragment.app.Fragment r1 = r3.mParent
            boolean r1 = r3.isPrimaryNavigation(r1)
            if (r1 == 0) goto L_0x0025
            goto L_0x0026
        L_0x0025:
            r2 = 0
        L_0x0026:
            r0.setEnabled(r2)
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.updateOnBackPressedCallbackEnabled():void");
    }

    /* access modifiers changed from: package-private */
    public boolean isPrimaryNavigation(@Nullable Fragment parent) {
        if (parent == null) {
            return true;
        }
        FragmentManager parentFragmentManager = parent.mFragmentManager;
        if (!parent.equals(parentFragmentManager.getPrimaryNavigationFragment()) || !isPrimaryNavigation(parentFragmentManager.mParent)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean isParentMenuVisible(@Nullable Fragment parent) {
        if (parent == null) {
            return true;
        }
        return parent.isMenuVisible();
    }

    /* access modifiers changed from: package-private */
    public void handleOnBackPressed() {
        execPendingActions(true);
        if (this.mOnBackPressedCallback.isEnabled()) {
            popBackStackImmediate();
        } else {
            this.mOnBackPressedDispatcher.onBackPressed();
        }
    }

    public void popBackStack() {
        enqueueAction(new PopBackStackState((String) null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        return popBackStackImmediate((String) null, -1, 0);
    }

    public void popBackStack(@Nullable String name, int flags) {
        enqueueAction(new PopBackStackState(name, -1, flags), false);
    }

    public boolean popBackStackImmediate(@Nullable String name, int flags) {
        return popBackStackImmediate(name, -1, flags);
    }

    public void popBackStack(int id, int flags) {
        if (id >= 0) {
            enqueueAction(new PopBackStackState((String) null, id, flags), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    public boolean popBackStackImmediate(int id, int flags) {
        if (id >= 0) {
            return popBackStackImmediate((String) null, id, flags);
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    private boolean popBackStackImmediate(@Nullable String name, int id, int flags) {
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && id < 0 && name == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean executePop = popBackStackState(this.mTmpRecords, this.mTmpIsPop, name, id, flags);
        if (executePop) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return executePop;
    }

    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @NonNull
    public BackStackEntry getBackStackEntryAt(int index) {
        return this.mBackStack.get(index);
    }

    public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(listener);
    }

    public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener listener) {
        ArrayList<OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void addCancellationSignal(@NonNull Fragment f, @NonNull CancellationSignal signal) {
        if (this.mExitAnimationCancellationSignals.get(f) == null) {
            this.mExitAnimationCancellationSignals.put(f, new HashSet());
        }
        this.mExitAnimationCancellationSignals.get(f).add(signal);
    }

    /* access modifiers changed from: package-private */
    public void removeCancellationSignal(@NonNull Fragment f, @NonNull CancellationSignal signal) {
        HashSet<CancellationSignal> signals = this.mExitAnimationCancellationSignals.get(f);
        if (signals != null && signals.remove(signal) && signals.isEmpty()) {
            this.mExitAnimationCancellationSignals.remove(f);
            if (f.mState < 5) {
                destroyFragmentView(f);
                moveToState(f);
            }
        }
    }

    public final void setFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        LifecycleAwareResultListener resultListener = this.mResultListeners.get(requestKey);
        if (resultListener == null || !resultListener.isAtLeast(Lifecycle.State.STARTED)) {
            this.mResults.put(requestKey, result);
        } else {
            resultListener.onFragmentResult(requestKey, result);
        }
    }

    public final void clearFragmentResult(@NonNull String requestKey) {
        this.mResults.remove(requestKey);
    }

    @SuppressLint({"SyntheticAccessor"})
    public final void setFragmentResultListener(@NonNull final String requestKey, @NonNull LifecycleOwner lifecycleOwner, @NonNull final FragmentResultListener listener) {
        final Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            LifecycleEventObserver observer = new LifecycleEventObserver() {
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    Bundle storedResult;
                    if (event == Lifecycle.Event.ON_START && (storedResult = (Bundle) FragmentManager.this.mResults.get(requestKey)) != null) {
                        listener.onFragmentResult(requestKey, storedResult);
                        FragmentManager.this.clearFragmentResult(requestKey);
                    }
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        lifecycle.removeObserver(this);
                        FragmentManager.this.mResultListeners.remove(requestKey);
                    }
                }
            };
            lifecycle.addObserver(observer);
            LifecycleAwareResultListener storedListener = this.mResultListeners.put(requestKey, new LifecycleAwareResultListener(lifecycle, listener, observer));
            if (storedListener != null) {
                storedListener.removeObserver();
            }
        }
    }

    public final void clearFragmentResultListener(@NonNull String requestKey) {
        LifecycleAwareResultListener listener = this.mResultListeners.remove(requestKey);
        if (listener != null) {
            listener.removeObserver();
        }
    }

    public void putFragment(@NonNull Bundle bundle, @NonNull String key, @NonNull Fragment fragment) {
        if (fragment.mFragmentManager != this) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putString(key, fragment.mWho);
    }

    @Nullable
    public Fragment getFragment(@NonNull Bundle bundle, @NonNull String key) {
        String who = bundle.getString(key);
        if (who == null) {
            return null;
        }
        Fragment f = findActiveFragment(who);
        if (f == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + key + ": unique id " + who));
        }
        return f;
    }

    @NonNull
    public static <F extends Fragment> F findFragment(@NonNull View view) {
        Fragment fragment = findViewFragment(view);
        if (fragment != null) {
            return fragment;
        }
        throw new IllegalStateException("View " + view + " does not have a Fragment set");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static androidx.fragment.app.Fragment findViewFragment(@androidx.annotation.NonNull android.view.View r4) {
        /*
        L_0x0000:
            r0 = 0
            if (r4 == 0) goto L_0x0017
            androidx.fragment.app.Fragment r1 = getViewFragment(r4)
            if (r1 == 0) goto L_0x000a
            return r1
        L_0x000a:
            android.view.ViewParent r2 = r4.getParent()
            boolean r3 = r2 instanceof android.view.View
            if (r3 == 0) goto L_0x0015
            r0 = r2
            android.view.View r0 = (android.view.View) r0
        L_0x0015:
            r4 = r0
            goto L_0x0000
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.findViewFragment(android.view.View):androidx.fragment.app.Fragment");
    }

    @Nullable
    static Fragment getViewFragment(@NonNull View view) {
        Object tag = view.getTag(R.id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void onContainerAvailable(@NonNull FragmentContainerView container) {
        View view;
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            Fragment fragment = fragmentStateManager.getFragment();
            if (fragment.mContainerId == container.getId() && (view = fragment.mView) != null && view.getParent() == null) {
                fragment.mContainer = container;
                fragmentStateManager.addViewToContainer();
            }
        }
    }

    @NonNull
    static FragmentManager findFragmentManager(@NonNull View view) {
        Fragment fragment = findViewFragment(view);
        if (fragment == null) {
            Context context = view.getContext();
            FragmentActivity fragmentActivity = null;
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    break;
                } else if (context instanceof FragmentActivity) {
                    fragmentActivity = (FragmentActivity) context;
                    break;
                } else {
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            if (fragmentActivity != null) {
                return fragmentActivity.getSupportFragmentManager();
            }
            throw new IllegalStateException("View " + view + " is not within a subclass of FragmentActivity.");
        } else if (fragment.isAdded()) {
            return fragment.getChildFragmentManager();
        } else {
            throw new IllegalStateException("The Fragment " + fragment + " that owns View " + view + " has already been destroyed. Nested fragments should always use the child FragmentManager.");
        }
    }

    @NonNull
    public List<Fragment> getFragments() {
        return this.mFragmentStore.getFragments();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ViewModelStore getViewModelStore(@NonNull Fragment f) {
        return this.mNonConfig.getViewModelStore(f);
    }

    @NonNull
    private FragmentManagerViewModel getChildNonConfig(@NonNull Fragment f) {
        return this.mNonConfig.getChildNonConfig(f);
    }

    /* access modifiers changed from: package-private */
    public void addRetainedFragment(@NonNull Fragment f) {
        this.mNonConfig.addRetainedFragment(f);
    }

    /* access modifiers changed from: package-private */
    public void removeRetainedFragment(@NonNull Fragment f) {
        this.mNonConfig.removeRetainedFragment(f);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public List<Fragment> getActiveFragments() {
        return this.mFragmentStore.getActiveFragments();
    }

    /* access modifiers changed from: package-private */
    public int getActiveFragmentCount() {
        return this.mFragmentStore.getActiveFragmentCount();
    }

    @Nullable
    public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment fragment) {
        FragmentStateManager fragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment.mWho);
        if (fragmentStateManager == null || !fragmentStateManager.getFragment().equals(fragment)) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        return fragmentStateManager.saveInstanceState();
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append("}");
        } else {
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.mHost)));
                sb.append("}");
            } else {
                sb.append(BuildConfig.TRAVIS);
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        int count;
        int count2;
        String innerPrefix = prefix + "    ";
        this.mFragmentStore.dump(prefix, fd, writer, args);
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (count2 = arrayList.size()) > 0) {
            writer.print(prefix);
            writer.println("Fragments Created Menus:");
            for (int i = 0; i < count2; i++) {
                writer.print(prefix);
                writer.print("  #");
                writer.print(i);
                writer.print(": ");
                writer.println(this.mCreatedMenus.get(i).toString());
            }
        }
        ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
        if (arrayList2 != null && (count = arrayList2.size()) > 0) {
            writer.print(prefix);
            writer.println("Back Stack:");
            for (int i2 = 0; i2 < count; i2++) {
                BackStackRecord bs = this.mBackStack.get(i2);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i2);
                writer.print(": ");
                writer.println(bs.toString());
                bs.dump(innerPrefix, writer);
            }
        }
        writer.print(prefix);
        writer.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int count3 = this.mPendingActions.size();
            if (count3 > 0) {
                writer.print(prefix);
                writer.println("Pending Actions:");
                for (int i3 = 0; i3 < count3; i3++) {
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i3);
                    writer.print(": ");
                    writer.println(this.mPendingActions.get(i3));
                }
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mHost=");
        writer.println(this.mHost);
        writer.print(prefix);
        writer.print("  mContainer=");
        writer.println(this.mContainer);
        if (this.mParent != null) {
            writer.print(prefix);
            writer.print("  mParent=");
            writer.println(this.mParent);
        }
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mStopped=");
        writer.print(this.mStopped);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
    }

    /* access modifiers changed from: package-private */
    public void performPendingDeferredStart(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment f = fragmentStateManager.getFragment();
        if (!f.mDeferStart) {
            return;
        }
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        f.mDeferStart = false;
        if (USE_STATE_MANAGER) {
            fragmentStateManager.moveToExpectedState();
        } else {
            moveToState(f);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isStateAtLeast(int state) {
        return this.mCurState >= state;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        if (r12 <= 0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        r0.create();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0058, code lost:
        if (r12 <= -1) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        r0.ensureInflatedView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        if (r12 <= 1) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        r0.createView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0062, code lost:
        if (r12 <= 2) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0064, code lost:
        r0.activityCreated();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0067, code lost:
        if (r12 <= 4) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
        r0.start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006c, code lost:
        if (r12 <= 5) goto L_0x0151;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006e, code lost:
        r0.resume();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0080, code lost:
        if (r12 >= 5) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0082, code lost:
        r0.stop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0085, code lost:
        if (r12 >= 4) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x008b, code lost:
        if (isLoggingEnabled(3) == false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x008d, code lost:
        android.util.Log.d(TAG, "movefrom ACTIVITY_CREATED: " + r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a3, code lost:
        if (r11.mView == null) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ab, code lost:
        if (r10.mHost.onShouldSaveFragmentState(r11) == false) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00af, code lost:
        if (r11.mSavedViewState != null) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b1, code lost:
        r0.saveViewState();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b4, code lost:
        if (r12 >= 2) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b6, code lost:
        r2 = null;
        r4 = r11.mView;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00b9, code lost:
        if (r4 == null) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00bb, code lost:
        r5 = r11.mContainer;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00bd, code lost:
        if (r5 == null) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00bf, code lost:
        r5.endViewTransition(r4);
        r11.mView.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00cb, code lost:
        if (r11.isRemovingParent() != false) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00d0, code lost:
        if (r10.mCurState <= -1) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00d4, code lost:
        if (r10.mDestroyed != false) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00dc, code lost:
        if (r11.mView.getVisibility() != 0) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00e2, code lost:
        if (r11.mPostponedAlpha < 0.0f) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00e4, code lost:
        r2 = androidx.fragment.app.FragmentAnim.loadAnimation(r10.mHost.getContext(), r11, false, r11.getPopDirection());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00f3, code lost:
        r11.mPostponedAlpha = 0.0f;
        r4 = r11.mContainer;
        r5 = r11.mView;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00f9, code lost:
        if (r2 == null) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00fb, code lost:
        androidx.fragment.app.FragmentAnim.animateRemoveFragment(r11, r2, r10.mFragmentTransitionCallback);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0100, code lost:
        r4.removeView(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0107, code lost:
        if (isLoggingEnabled(2) == false) goto L_0x012d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0109, code lost:
        android.util.Log.v(TAG, "Removing view " + r5 + " for fragment " + r11 + " from container " + r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x012f, code lost:
        if (r4 == r11.mContainer) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0131, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0138, code lost:
        if (r10.mExitAnimationCancellationSignals.get(r11) != null) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x013a, code lost:
        r0.destroyFragmentView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x013d, code lost:
        if (r12 >= 1) goto L_0x014c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0145, code lost:
        if (r10.mExitAnimationCancellationSignals.get(r11) == null) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0147, code lost:
        r12 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0149, code lost:
        r0.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x014c, code lost:
        if (r12 >= 0) goto L_0x0151;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x014e, code lost:
        r0.detach();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(@androidx.annotation.NonNull androidx.fragment.app.Fragment r11, int r12) {
        /*
            r10 = this;
            androidx.fragment.app.FragmentStore r0 = r10.mFragmentStore
            java.lang.String r1 = r11.mWho
            androidx.fragment.app.FragmentStateManager r0 = r0.getFragmentStateManager(r1)
            r1 = 1
            if (r0 != 0) goto L_0x0018
            androidx.fragment.app.FragmentStateManager r2 = new androidx.fragment.app.FragmentStateManager
            androidx.fragment.app.FragmentLifecycleCallbacksDispatcher r3 = r10.mLifecycleCallbacksDispatcher
            androidx.fragment.app.FragmentStore r4 = r10.mFragmentStore
            r2.<init>(r3, r4, r11)
            r0 = r2
            r0.setFragmentManagerState(r1)
        L_0x0018:
            boolean r2 = r11.mFromLayout
            r3 = 2
            if (r2 == 0) goto L_0x0029
            boolean r2 = r11.mInLayout
            if (r2 == 0) goto L_0x0029
            int r2 = r11.mState
            if (r2 != r3) goto L_0x0029
            int r12 = java.lang.Math.max(r12, r3)
        L_0x0029:
            int r2 = r0.computeExpectedState()
            int r12 = java.lang.Math.min(r12, r2)
            int r2 = r11.mState
            r4 = 5
            r5 = 4
            r6 = 3
            java.lang.String r7 = "FragmentManager"
            r8 = -1
            if (r2 > r12) goto L_0x0073
            if (r2 >= r12) goto L_0x0048
            java.util.Map<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.os.CancellationSignal>> r2 = r10.mExitAnimationCancellationSignals
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0048
            r10.cancelExitAnimation(r11)
        L_0x0048:
            int r2 = r11.mState
            switch(r2) {
                case -1: goto L_0x004e;
                case 0: goto L_0x0053;
                case 1: goto L_0x0058;
                case 2: goto L_0x0062;
                case 3: goto L_0x004d;
                case 4: goto L_0x0067;
                case 5: goto L_0x006c;
                default: goto L_0x004d;
            }
        L_0x004d:
            goto L_0x0071
        L_0x004e:
            if (r12 <= r8) goto L_0x0053
            r0.attach()
        L_0x0053:
            if (r12 <= 0) goto L_0x0058
            r0.create()
        L_0x0058:
            if (r12 <= r8) goto L_0x005d
            r0.ensureInflatedView()
        L_0x005d:
            if (r12 <= r1) goto L_0x0062
            r0.createView()
        L_0x0062:
            if (r12 <= r3) goto L_0x0067
            r0.activityCreated()
        L_0x0067:
            if (r12 <= r5) goto L_0x006c
            r0.start()
        L_0x006c:
            if (r12 <= r4) goto L_0x0071
            r0.resume()
        L_0x0071:
            goto L_0x0151
        L_0x0073:
            if (r2 <= r12) goto L_0x0151
            switch(r2) {
                case 0: goto L_0x014c;
                case 1: goto L_0x013d;
                case 2: goto L_0x00b4;
                case 3: goto L_0x0078;
                case 4: goto L_0x0085;
                case 5: goto L_0x0080;
                case 6: goto L_0x0078;
                case 7: goto L_0x007a;
                default: goto L_0x0078;
            }
        L_0x0078:
            goto L_0x0151
        L_0x007a:
            r2 = 7
            if (r12 >= r2) goto L_0x0080
            r0.pause()
        L_0x0080:
            if (r12 >= r4) goto L_0x0085
            r0.stop()
        L_0x0085:
            if (r12 >= r5) goto L_0x00b4
            boolean r2 = isLoggingEnabled(r6)
            if (r2 == 0) goto L_0x00a1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "movefrom ACTIVITY_CREATED: "
            r2.append(r4)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r7, r2)
        L_0x00a1:
            android.view.View r2 = r11.mView
            if (r2 == 0) goto L_0x00b4
            androidx.fragment.app.FragmentHostCallback<?> r2 = r10.mHost
            boolean r2 = r2.onShouldSaveFragmentState(r11)
            if (r2 == 0) goto L_0x00b4
            android.util.SparseArray<android.os.Parcelable> r2 = r11.mSavedViewState
            if (r2 != 0) goto L_0x00b4
            r0.saveViewState()
        L_0x00b4:
            if (r12 >= r3) goto L_0x013d
            r2 = 0
            android.view.View r4 = r11.mView
            if (r4 == 0) goto L_0x0132
            android.view.ViewGroup r5 = r11.mContainer
            if (r5 == 0) goto L_0x0132
            r5.endViewTransition(r4)
            android.view.View r4 = r11.mView
            r4.clearAnimation()
            boolean r4 = r11.isRemovingParent()
            if (r4 != 0) goto L_0x0132
            int r4 = r10.mCurState
            r5 = 0
            if (r4 <= r8) goto L_0x00f3
            boolean r4 = r10.mDestroyed
            if (r4 != 0) goto L_0x00f3
            android.view.View r4 = r11.mView
            int r4 = r4.getVisibility()
            if (r4 != 0) goto L_0x00f3
            float r4 = r11.mPostponedAlpha
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 < 0) goto L_0x00f3
            androidx.fragment.app.FragmentHostCallback<?> r4 = r10.mHost
            android.content.Context r4 = r4.getContext()
            r8 = 0
            boolean r9 = r11.getPopDirection()
            androidx.fragment.app.FragmentAnim$AnimationOrAnimator r2 = androidx.fragment.app.FragmentAnim.loadAnimation(r4, r11, r8, r9)
        L_0x00f3:
            r11.mPostponedAlpha = r5
            android.view.ViewGroup r4 = r11.mContainer
            android.view.View r5 = r11.mView
            if (r2 == 0) goto L_0x0100
            androidx.fragment.app.FragmentTransition$Callback r8 = r10.mFragmentTransitionCallback
            androidx.fragment.app.FragmentAnim.animateRemoveFragment(r11, r2, r8)
        L_0x0100:
            r4.removeView(r5)
            boolean r3 = isLoggingEnabled(r3)
            if (r3 == 0) goto L_0x012d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "Removing view "
            r3.append(r8)
            r3.append(r5)
            java.lang.String r8 = " for fragment "
            r3.append(r8)
            r3.append(r11)
            java.lang.String r8 = " from container "
            r3.append(r8)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.v(r7, r3)
        L_0x012d:
            android.view.ViewGroup r3 = r11.mContainer
            if (r4 == r3) goto L_0x0132
            return
        L_0x0132:
            java.util.Map<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.os.CancellationSignal>> r3 = r10.mExitAnimationCancellationSignals
            java.lang.Object r3 = r3.get(r11)
            if (r3 != 0) goto L_0x013d
            r0.destroyFragmentView()
        L_0x013d:
            if (r12 >= r1) goto L_0x014c
            java.util.Map<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.os.CancellationSignal>> r1 = r10.mExitAnimationCancellationSignals
            java.lang.Object r1 = r1.get(r11)
            if (r1 == 0) goto L_0x0149
            r12 = 1
            goto L_0x014c
        L_0x0149:
            r0.destroy()
        L_0x014c:
            if (r12 >= 0) goto L_0x0151
            r0.detach()
        L_0x0151:
            int r1 = r11.mState
            if (r1 == r12) goto L_0x0183
            boolean r1 = isLoggingEnabled(r6)
            if (r1 == 0) goto L_0x0181
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveToState: Fragment state for "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r2 = " not updated inline; expected state "
            r1.append(r2)
            r1.append(r12)
            java.lang.String r2 = " found "
            r1.append(r2)
            int r2 = r11.mState
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r7, r1)
        L_0x0181:
            r11.mState = r12
        L_0x0183:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.moveToState(androidx.fragment.app.Fragment, int):void");
    }

    private void cancelExitAnimation(@NonNull Fragment f) {
        HashSet<CancellationSignal> signals = this.mExitAnimationCancellationSignals.get(f);
        if (signals != null) {
            Iterator<CancellationSignal> it = signals.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            signals.clear();
            destroyFragmentView(f);
            this.mExitAnimationCancellationSignals.remove(f);
        }
    }

    /* access modifiers changed from: package-private */
    public void setExitAnimationOrder(@NonNull Fragment f, boolean isPop) {
        ViewGroup container = getFragmentContainer(f);
        if (container != null && (container instanceof FragmentContainerView)) {
            ((FragmentContainerView) container).setDrawDisappearingViewsLast(!isPop);
        }
    }

    private void destroyFragmentView(@NonNull Fragment fragment) {
        fragment.performDestroyView();
        this.mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(fragment, false);
        fragment.mContainer = null;
        fragment.mView = null;
        fragment.mViewLifecycleOwner = null;
        fragment.mViewLifecycleOwnerLiveData.setValue(null);
        fragment.mInLayout = false;
    }

    /* access modifiers changed from: package-private */
    public void moveToState(@NonNull Fragment f) {
        moveToState(f, this.mCurState);
    }

    private void completeShowHideFragment(@NonNull final Fragment fragment) {
        int visibility;
        Animator animator;
        if (fragment.mView != null) {
            FragmentAnim.AnimationOrAnimator anim = FragmentAnim.loadAnimation(this.mHost.getContext(), fragment, !fragment.mHidden, fragment.getPopDirection());
            if (anim == null || (animator = anim.animator) == null) {
                if (anim != null) {
                    fragment.mView.startAnimation(anim.animation);
                    anim.animation.start();
                }
                if (!fragment.mHidden || fragment.isHideReplaced()) {
                    visibility = 0;
                } else {
                    visibility = 8;
                }
                fragment.mView.setVisibility(visibility);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            } else {
                animator.setTarget(fragment.mView);
                if (!fragment.mHidden) {
                    fragment.mView.setVisibility(0);
                } else if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                } else {
                    final ViewGroup container = fragment.mContainer;
                    final View animatingView = fragment.mView;
                    container.startViewTransition(animatingView);
                    anim.animator.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            container.endViewTransition(animatingView);
                            animation.removeListener(this);
                            Fragment fragment = fragment;
                            View view = fragment.mView;
                            if (view != null && fragment.mHidden) {
                                view.setVisibility(8);
                            }
                        }
                    });
                }
                anim.animator.start();
            }
        }
        invalidateMenuForFragment(fragment);
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /* access modifiers changed from: package-private */
    public void moveFragmentToExpectedState(@NonNull Fragment f) {
        if (this.mFragmentStore.containsActiveFragment(f.mWho)) {
            moveToState(f);
            View view = f.mView;
            if (!(view == null || !f.mIsNewlyAdded || f.mContainer == null)) {
                float f2 = f.mPostponedAlpha;
                if (f2 > 0.0f) {
                    view.setAlpha(f2);
                }
                f.mPostponedAlpha = 0.0f;
                f.mIsNewlyAdded = false;
                FragmentAnim.AnimationOrAnimator anim = FragmentAnim.loadAnimation(this.mHost.getContext(), f, true, f.getPopDirection());
                if (anim != null) {
                    Animation animation = anim.animation;
                    if (animation != null) {
                        f.mView.startAnimation(animation);
                    } else {
                        anim.animator.setTarget(f.mView);
                        anim.animator.start();
                    }
                }
            }
            if (f.mHiddenChanged) {
                completeShowHideFragment(f);
            }
        } else if (isLoggingEnabled(3)) {
            Log.d(TAG, "Ignoring moving " + f + " to state " + this.mCurState + "since it is not added to " + this);
        }
    }

    /* access modifiers changed from: package-private */
    public void moveToState(int newState, boolean always) {
        FragmentHostCallback<?> fragmentHostCallback;
        if (this.mHost == null && newState != -1) {
            throw new IllegalStateException("No activity");
        } else if (always || newState != this.mCurState) {
            this.mCurState = newState;
            if (USE_STATE_MANAGER) {
                this.mFragmentStore.moveToExpectedState();
            } else {
                for (Fragment f : this.mFragmentStore.getFragments()) {
                    moveFragmentToExpectedState(f);
                }
                for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
                    Fragment f2 = fragmentStateManager.getFragment();
                    if (!f2.mIsNewlyAdded) {
                        moveFragmentToExpectedState(f2);
                    }
                    if (f2.mRemoving && !f2.isInBackStack()) {
                        this.mFragmentStore.makeInactive(fragmentStateManager);
                    }
                }
            }
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    private void startPendingDeferredFragments() {
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            performPendingDeferredStart(fragmentStateManager);
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentStateManager createOrGetFragmentStateManager(@NonNull Fragment f) {
        FragmentStateManager existing = this.mFragmentStore.getFragmentStateManager(f.mWho);
        if (existing != null) {
            return existing;
        }
        FragmentStateManager fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, f);
        fragmentStateManager.restoreState(this.mHost.getContext().getClassLoader());
        fragmentStateManager.setFragmentManagerState(this.mCurState);
        return fragmentStateManager;
    }

    /* access modifiers changed from: package-private */
    public FragmentStateManager addFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "add: " + fragment);
        }
        FragmentStateManager fragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        this.mFragmentStore.makeActive(fragmentStateManager);
        if (!fragment.mDetached) {
            this.mFragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return fragmentStateManager;
    }

    /* access modifiers changed from: package-private */
    public void removeFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean inactive = !fragment.isInBackStack();
        if (!fragment.mDetached || inactive) {
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public void hideFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
            setVisibleRemovingFragment(fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public void showFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    /* access modifiers changed from: package-private */
    public void detachFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "remove from detach: " + fragment);
                }
                this.mFragmentStore.removeFragment(fragment);
                if (isMenuAvailable(fragment)) {
                    this.mNeedMenuInvalidate = true;
                }
                setVisibleRemovingFragment(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void attachFragment(@NonNull Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                this.mFragmentStore.addFragment(fragment);
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "add from attach: " + fragment);
                }
                if (isMenuAvailable(fragment)) {
                    this.mNeedMenuInvalidate = true;
                }
            }
        }
    }

    @Nullable
    public Fragment findFragmentById(@IdRes int id) {
        return this.mFragmentStore.findFragmentById(id);
    }

    @Nullable
    public Fragment findFragmentByTag(@Nullable String tag) {
        return this.mFragmentStore.findFragmentByTag(tag);
    }

    /* access modifiers changed from: package-private */
    public Fragment findFragmentByWho(@NonNull String who) {
        return this.mFragmentStore.findFragmentByWho(who);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Fragment findActiveFragment(@NonNull String who) {
        return this.mFragmentStore.findActiveFragment(who);
    }

    private void checkStateLoss() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    /* access modifiers changed from: package-private */
    public void enqueueAction(@NonNull OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss) {
            if (this.mHost != null) {
                checkStateLoss();
            } else if (this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            } else {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
        }
        synchronized (this.mPendingActions) {
            if (this.mHost != null) {
                this.mPendingActions.add(action);
                scheduleCommit();
            } else if (!allowStateLoss) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void scheduleCommit() {
        synchronized (this.mPendingActions) {
            ArrayList<StartEnterTransitionListener> arrayList = this.mPostponedTransactions;
            boolean pendingReady = false;
            boolean postponeReady = arrayList != null && !arrayList.isEmpty();
            if (this.mPendingActions.size() == 1) {
                pendingReady = true;
            }
            if (postponeReady || pendingReady) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
                updateOnBackPressedCallbackEnabled();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int allocBackStackIndex() {
        return this.mBackStackIndex.getAndIncrement();
    }

    private void ensureExecReady(boolean allowStateLoss) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.mHost == null) {
            if (this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        } else if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
            if (!allowStateLoss) {
                checkStateLoss();
            }
            if (this.mTmpRecords == null) {
                this.mTmpRecords = new ArrayList<>();
                this.mTmpIsPop = new ArrayList<>();
            }
            this.mExecutingActions = true;
            try {
                executePostponedTransaction((ArrayList<BackStackRecord>) null, (ArrayList<Boolean>) null);
            } finally {
                this.mExecutingActions = false;
            }
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    /* access modifiers changed from: package-private */
    public void execSingleAction(@NonNull OpGenerator action, boolean allowStateLoss) {
        if (!allowStateLoss || (this.mHost != null && !this.mDestroyed)) {
            ensureExecReady(allowStateLoss);
            if (action.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
                this.mExecutingActions = true;
                try {
                    removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                } finally {
                    cleanupExec();
                }
            }
            updateOnBackPressedCallbackEnabled();
            doPendingDeferredStart();
            this.mFragmentStore.burpActive();
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public boolean execPendingActions(boolean allowStateLoss) {
        ensureExecReady(allowStateLoss);
        boolean didSomething = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                didSomething = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return didSomething;
    }

    private void executePostponedTransaction(@Nullable ArrayList<BackStackRecord> records, @Nullable ArrayList<Boolean> isRecordPop) {
        int index;
        ArrayList<StartEnterTransitionListener> arrayList = this.mPostponedTransactions;
        int numPostponed = arrayList == null ? 0 : arrayList.size();
        int i = 0;
        while (i < numPostponed) {
            StartEnterTransitionListener listener = this.mPostponedTransactions.get(i);
            if (records != null && !listener.mIsBack && (index = records.indexOf(listener.mRecord)) != -1 && isRecordPop != null && isRecordPop.get(index).booleanValue()) {
                this.mPostponedTransactions.remove(i);
                i--;
                numPostponed--;
                listener.cancelTransaction();
            } else if (listener.isReady() != 0 || (records != null && listener.mRecord.interactsWith(records, 0, records.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                numPostponed--;
                if (records != null && !listener.mIsBack) {
                    int indexOf = records.indexOf(listener.mRecord);
                    int index2 = indexOf;
                    if (!(indexOf == -1 || isRecordPop == null || !isRecordPop.get(index2).booleanValue())) {
                        listener.cancelTransaction();
                    }
                }
                listener.completeTransaction();
            }
            i++;
        }
    }

    private void removeRedundantOperationsAndExecute(@NonNull ArrayList<BackStackRecord> records, @NonNull ArrayList<Boolean> isRecordPop) {
        if (!records.isEmpty()) {
            if (records.size() == isRecordPop.size()) {
                executePostponedTransaction(records, isRecordPop);
                int numRecords = records.size();
                int startIndex = 0;
                int recordNum = 0;
                while (recordNum < numRecords) {
                    if (!records.get(recordNum).mReorderingAllowed) {
                        if (startIndex != recordNum) {
                            executeOpsTogether(records, isRecordPop, startIndex, recordNum);
                        }
                        int reorderingEnd = recordNum + 1;
                        if (isRecordPop.get(recordNum).booleanValue()) {
                            while (reorderingEnd < numRecords && isRecordPop.get(reorderingEnd).booleanValue() && !records.get(reorderingEnd).mReorderingAllowed) {
                                reorderingEnd++;
                            }
                        }
                        executeOpsTogether(records, isRecordPop, recordNum, reorderingEnd);
                        startIndex = reorderingEnd;
                        recordNum = reorderingEnd - 1;
                    }
                    recordNum++;
                }
                if (startIndex != numRecords) {
                    executeOpsTogether(records, isRecordPop, startIndex, numRecords);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void executeOpsTogether(@androidx.annotation.NonNull java.util.ArrayList<androidx.fragment.app.BackStackRecord> r20, @androidx.annotation.NonNull java.util.ArrayList<java.lang.Boolean> r21, int r22, int r23) {
        /*
            r19 = this;
            r6 = r19
            r15 = r20
            r5 = r21
            r4 = r22
            r3 = r23
            java.lang.Object r0 = r15.get(r4)
            androidx.fragment.app.BackStackRecord r0 = (androidx.fragment.app.BackStackRecord) r0
            boolean r2 = r0.mReorderingAllowed
            r0 = 0
            java.util.ArrayList<androidx.fragment.app.Fragment> r1 = r6.mTmpAddedFragments
            if (r1 != 0) goto L_0x001f
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6.mTmpAddedFragments = r1
            goto L_0x0022
        L_0x001f:
            r1.clear()
        L_0x0022:
            java.util.ArrayList<androidx.fragment.app.Fragment> r1 = r6.mTmpAddedFragments
            androidx.fragment.app.FragmentStore r7 = r6.mFragmentStore
            java.util.List r7 = r7.getFragments()
            r1.addAll(r7)
            androidx.fragment.app.Fragment r1 = r19.getPrimaryNavigationFragment()
            r7 = r22
            r16 = r0
        L_0x0035:
            r0 = 1
            if (r7 >= r3) goto L_0x0064
            java.lang.Object r8 = r15.get(r7)
            androidx.fragment.app.BackStackRecord r8 = (androidx.fragment.app.BackStackRecord) r8
            java.lang.Object r9 = r5.get(r7)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x0051
            java.util.ArrayList<androidx.fragment.app.Fragment> r10 = r6.mTmpAddedFragments
            androidx.fragment.app.Fragment r1 = r8.expandOps(r10, r1)
            goto L_0x0057
        L_0x0051:
            java.util.ArrayList<androidx.fragment.app.Fragment> r10 = r6.mTmpAddedFragments
            androidx.fragment.app.Fragment r1 = r8.trackAddedFragmentsInPop(r10, r1)
        L_0x0057:
            if (r16 != 0) goto L_0x005f
            boolean r10 = r8.mAddToBackStack
            if (r10 == 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r0 = 0
        L_0x005f:
            r16 = r0
            int r7 = r7 + 1
            goto L_0x0035
        L_0x0064:
            java.util.ArrayList<androidx.fragment.app.Fragment> r7 = r6.mTmpAddedFragments
            r7.clear()
            if (r2 != 0) goto L_0x00bc
            int r7 = r6.mCurState
            if (r7 < r0) goto L_0x00bc
            boolean r7 = USE_STATE_MANAGER
            if (r7 == 0) goto L_0x00a6
            r7 = r22
        L_0x0075:
            if (r7 >= r3) goto L_0x00a5
            java.lang.Object r8 = r15.get(r7)
            androidx.fragment.app.BackStackRecord r8 = (androidx.fragment.app.BackStackRecord) r8
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r9 = r8.mOps
            java.util.Iterator r9 = r9.iterator()
        L_0x0083:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00a2
            java.lang.Object r10 = r9.next()
            androidx.fragment.app.FragmentTransaction$Op r10 = (androidx.fragment.app.FragmentTransaction.Op) r10
            androidx.fragment.app.Fragment r11 = r10.mFragment
            if (r11 == 0) goto L_0x00a1
            androidx.fragment.app.FragmentManager r12 = r11.mFragmentManager
            if (r12 == 0) goto L_0x00a1
            androidx.fragment.app.FragmentStateManager r12 = r6.createOrGetFragmentStateManager(r11)
            androidx.fragment.app.FragmentStore r13 = r6.mFragmentStore
            r13.makeActive(r12)
        L_0x00a1:
            goto L_0x0083
        L_0x00a2:
            int r7 = r7 + 1
            goto L_0x0075
        L_0x00a5:
            goto L_0x00bc
        L_0x00a6:
            androidx.fragment.app.FragmentHostCallback<?> r7 = r6.mHost
            android.content.Context r7 = r7.getContext()
            androidx.fragment.app.FragmentContainer r8 = r6.mContainer
            r13 = 0
            androidx.fragment.app.FragmentTransition$Callback r14 = r6.mFragmentTransitionCallback
            r9 = r20
            r10 = r21
            r11 = r22
            r12 = r23
            androidx.fragment.app.FragmentTransition.startTransitions(r7, r8, r9, r10, r11, r12, r13, r14)
        L_0x00bc:
            executeOps(r20, r21, r22, r23)
            boolean r7 = USE_STATE_MANAGER
            if (r7 == 0) goto L_0x0149
            int r7 = r3 + -1
            java.lang.Object r7 = r5.get(r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            r8 = r22
        L_0x00d1:
            if (r8 >= r3) goto L_0x011e
            java.lang.Object r9 = r15.get(r8)
            androidx.fragment.app.BackStackRecord r9 = (androidx.fragment.app.BackStackRecord) r9
            if (r7 == 0) goto L_0x00fc
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r10 = r9.mOps
            int r10 = r10.size()
            int r10 = r10 - r0
        L_0x00e2:
            if (r10 < 0) goto L_0x00fb
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r11 = r9.mOps
            java.lang.Object r11 = r11.get(r10)
            androidx.fragment.app.FragmentTransaction$Op r11 = (androidx.fragment.app.FragmentTransaction.Op) r11
            androidx.fragment.app.Fragment r12 = r11.mFragment
            if (r12 == 0) goto L_0x00f8
            androidx.fragment.app.FragmentStateManager r13 = r6.createOrGetFragmentStateManager(r12)
            r13.moveToExpectedState()
        L_0x00f8:
            int r10 = r10 + -1
            goto L_0x00e2
        L_0x00fb:
            goto L_0x011b
        L_0x00fc:
            java.util.ArrayList<androidx.fragment.app.FragmentTransaction$Op> r10 = r9.mOps
            java.util.Iterator r10 = r10.iterator()
        L_0x0102:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x011b
            java.lang.Object r11 = r10.next()
            androidx.fragment.app.FragmentTransaction$Op r11 = (androidx.fragment.app.FragmentTransaction.Op) r11
            androidx.fragment.app.Fragment r12 = r11.mFragment
            if (r12 == 0) goto L_0x011a
            androidx.fragment.app.FragmentStateManager r13 = r6.createOrGetFragmentStateManager(r12)
            r13.moveToExpectedState()
        L_0x011a:
            goto L_0x0102
        L_0x011b:
            int r8 = r8 + 1
            goto L_0x00d1
        L_0x011e:
            int r8 = r6.mCurState
            r6.moveToState((int) r8, (boolean) r0)
            java.util.Set r0 = r6.collectChangedControllers(r15, r4, r3)
            java.util.Iterator r8 = r0.iterator()
        L_0x012b:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0141
            java.lang.Object r9 = r8.next()
            androidx.fragment.app.SpecialEffectsController r9 = (androidx.fragment.app.SpecialEffectsController) r9
            r9.updateOperationDirection(r7)
            r9.markPostponedState()
            r9.executePendingOperations()
            goto L_0x012b
        L_0x0141:
            r17 = r1
            r18 = r2
            r4 = r3
            r3 = r5
            goto L_0x01a8
        L_0x0149:
            r7 = r23
            if (r2 == 0) goto L_0x0171
            androidx.collection.ArraySet r8 = new androidx.collection.ArraySet
            r8.<init>()
            r6.addAddedFragments(r8)
            r14 = r0
            r0 = r19
            r17 = r1
            r1 = r20
            r18 = r2
            r2 = r21
            r13 = r3
            r3 = r22
            r12 = r4
            r4 = r23
            r11 = r5
            r5 = r8
            int r7 = r0.postponePostponableTransactions(r1, r2, r3, r4, r5)
            r6.makeRemovedFragmentsInvisible(r8)
            r0 = r7
            goto L_0x017a
        L_0x0171:
            r14 = r0
            r17 = r1
            r18 = r2
            r13 = r3
            r12 = r4
            r11 = r5
            r0 = r7
        L_0x017a:
            if (r0 == r12) goto L_0x01a6
            if (r18 == 0) goto L_0x01a6
            int r1 = r6.mCurState
            if (r1 < r14) goto L_0x019d
            androidx.fragment.app.FragmentHostCallback<?> r1 = r6.mHost
            android.content.Context r7 = r1.getContext()
            androidx.fragment.app.FragmentContainer r8 = r6.mContainer
            r1 = 1
            androidx.fragment.app.FragmentTransition$Callback r2 = r6.mFragmentTransitionCallback
            r9 = r20
            r10 = r21
            r3 = r11
            r11 = r22
            r12 = r0
            r4 = r13
            r13 = r1
            r1 = r14
            r14 = r2
            androidx.fragment.app.FragmentTransition.startTransitions(r7, r8, r9, r10, r11, r12, r13, r14)
            goto L_0x01a0
        L_0x019d:
            r3 = r11
            r4 = r13
            r1 = r14
        L_0x01a0:
            int r2 = r6.mCurState
            r6.moveToState((int) r2, (boolean) r1)
            goto L_0x01a8
        L_0x01a6:
            r3 = r11
            r4 = r13
        L_0x01a8:
            r0 = r22
        L_0x01aa:
            if (r0 >= r4) goto L_0x01cb
            java.lang.Object r1 = r15.get(r0)
            androidx.fragment.app.BackStackRecord r1 = (androidx.fragment.app.BackStackRecord) r1
            java.lang.Object r2 = r3.get(r0)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x01c5
            int r5 = r1.mIndex
            if (r5 < 0) goto L_0x01c5
            r5 = -1
            r1.mIndex = r5
        L_0x01c5:
            r1.runOnCommitRunnables()
            int r0 = r0 + 1
            goto L_0x01aa
        L_0x01cb:
            if (r16 == 0) goto L_0x01d0
            r19.reportBackStackChanged()
        L_0x01d0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.executeOpsTogether(java.util.ArrayList, java.util.ArrayList, int, int):void");
    }

    private Set<SpecialEffectsController> collectChangedControllers(@NonNull ArrayList<BackStackRecord> records, int startIndex, int endIndex) {
        ViewGroup container;
        Set<SpecialEffectsController> controllers = new HashSet<>();
        for (int index = startIndex; index < endIndex; index++) {
            Iterator<FragmentTransaction.Op> it = records.get(index).mOps.iterator();
            while (it.hasNext()) {
                Fragment fragment = it.next().mFragment;
                if (!(fragment == null || (container = fragment.mContainer) == null)) {
                    controllers.add(SpecialEffectsController.getOrCreateController(container, this));
                }
            }
        }
        return controllers;
    }

    private void makeRemovedFragmentsInvisible(@NonNull ArraySet<Fragment> fragments) {
        int numAdded = fragments.size();
        for (int i = 0; i < numAdded; i++) {
            Fragment fragment = fragments.valueAt(i);
            if (!fragment.mAdded) {
                View view = fragment.requireView();
                fragment.mPostponedAlpha = view.getAlpha();
                view.setAlpha(0.0f);
            }
        }
    }

    private int postponePostponableTransactions(@NonNull ArrayList<BackStackRecord> records, @NonNull ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, @NonNull ArraySet<Fragment> added) {
        int postponeIndex = endIndex;
        for (int i = endIndex - 1; i >= startIndex; i--) {
            BackStackRecord record = records.get(i);
            boolean isPop = isRecordPop.get(i).booleanValue();
            if (record.isPostponed() && !record.interactsWith(records, i + 1, endIndex)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList<>();
                }
                StartEnterTransitionListener listener = new StartEnterTransitionListener(record, isPop);
                this.mPostponedTransactions.add(listener);
                record.setOnStartPostponedListener(listener);
                if (isPop) {
                    record.executeOps();
                } else {
                    record.executePopOps(false);
                }
                postponeIndex--;
                if (i != postponeIndex) {
                    records.remove(i);
                    records.add(postponeIndex, record);
                }
                addAddedFragments(added);
            }
        }
        return postponeIndex;
    }

    /* access modifiers changed from: package-private */
    public void completeExecute(@NonNull BackStackRecord record, boolean isPop, boolean runTransitions, boolean moveToState) {
        if (isPop) {
            record.executePopOps(moveToState);
        } else {
            record.executeOps();
        }
        ArrayList<BackStackRecord> records = new ArrayList<>(1);
        ArrayList arrayList = new ArrayList(1);
        records.add(record);
        arrayList.add(Boolean.valueOf(isPop));
        if (runTransitions && this.mCurState >= 1) {
            FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, records, arrayList, 0, 1, true, this.mFragmentTransitionCallback);
        }
        if (moveToState) {
            moveToState(this.mCurState, true);
        }
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && record.interactsWith(fragment.mContainerId)) {
                float f = fragment.mPostponedAlpha;
                if (f > 0.0f) {
                    fragment.mView.setAlpha(f);
                }
                if (moveToState) {
                    fragment.mPostponedAlpha = 0.0f;
                } else {
                    fragment.mPostponedAlpha = -1.0f;
                    fragment.mIsNewlyAdded = false;
                }
            }
        }
    }

    private static void executeOps(@NonNull ArrayList<BackStackRecord> records, @NonNull ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            BackStackRecord record = records.get(i);
            boolean moveToState = true;
            if (isRecordPop.get(i).booleanValue()) {
                record.bumpBackStackNesting(-1);
                if (i != endIndex - 1) {
                    moveToState = false;
                }
                record.executePopOps(moveToState);
            } else {
                record.bumpBackStackNesting(1);
                record.executeOps();
            }
        }
    }

    private void setVisibleRemovingFragment(@NonNull Fragment f) {
        ViewGroup container = getFragmentContainer(f);
        if (container != null && f.getEnterAnim() + f.getExitAnim() + f.getPopEnterAnim() + f.getPopExitAnim() > 0) {
            int i = R.id.visible_removing_fragment_view_tag;
            if (container.getTag(i) == null) {
                container.setTag(i, f);
            }
            ((Fragment) container.getTag(i)).setPopDirection(f.getPopDirection());
        }
    }

    private ViewGroup getFragmentContainer(@NonNull Fragment f) {
        ViewGroup viewGroup = f.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (f.mContainerId > 0 && this.mContainer.onHasView()) {
            View view = this.mContainer.onFindViewById(f.mContainerId);
            if (view instanceof ViewGroup) {
                return (ViewGroup) view;
            }
        }
        return null;
    }

    private void addAddedFragments(@NonNull ArraySet<Fragment> added) {
        int i = this.mCurState;
        if (i >= 1) {
            int state = Math.min(i, 5);
            for (Fragment fragment : this.mFragmentStore.getFragments()) {
                if (fragment.mState < state) {
                    moveToState(fragment, state);
                    if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                        added.add(fragment);
                    }
                }
            }
        }
    }

    private void forcePostponedTransactions() {
        if (USE_STATE_MANAGER) {
            for (SpecialEffectsController controller : collectAllSpecialEffectsController()) {
                controller.forcePostponedExecutePendingOperations();
            }
        } else if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    private void endAnimatingAwayFragments() {
        if (USE_STATE_MANAGER) {
            for (SpecialEffectsController controller : collectAllSpecialEffectsController()) {
                controller.forceCompleteAllOperations();
            }
        } else if (!this.mExitAnimationCancellationSignals.isEmpty()) {
            for (Fragment fragment : this.mExitAnimationCancellationSignals.keySet()) {
                cancelExitAnimation(fragment);
                moveToState(fragment);
            }
        }
    }

    private Set<SpecialEffectsController> collectAllSpecialEffectsController() {
        Set<SpecialEffectsController> controllers = new HashSet<>();
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            ViewGroup container = fragmentStateManager.getFragment().mContainer;
            if (container != null) {
                controllers.add(SpecialEffectsController.getOrCreateController(container, getSpecialEffectsControllerFactory()));
            }
        }
        return controllers;
    }

    private boolean generateOpsForPendingActions(@NonNull ArrayList<BackStackRecord> records, @NonNull ArrayList<Boolean> isPop) {
        boolean didSomething = false;
        synchronized (this.mPendingActions) {
            if (this.mPendingActions.isEmpty()) {
                return false;
            }
            int numActions = this.mPendingActions.size();
            for (int i = 0; i < numActions; i++) {
                didSomething |= this.mPendingActions.get(i).generateOps(records, isPop);
            }
            this.mPendingActions.clear();
            this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            return didSomething;
        }
    }

    private void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addBackStackState(BackStackRecord state) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(state);
    }

    /* access modifiers changed from: package-private */
    public boolean popBackStackState(@NonNull ArrayList<BackStackRecord> records, @NonNull ArrayList<Boolean> isRecordPop, @Nullable String name, int id, int flags) {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList == null) {
            return false;
        }
        if (name == null && id < 0 && (flags & 1) == 0) {
            int last = arrayList.size() - 1;
            if (last < 0) {
                return false;
            }
            records.add(this.mBackStack.remove(last));
            isRecordPop.add(true);
        } else {
            int index = -1;
            if (name != null || id >= 0) {
                int index2 = arrayList.size() - 1;
                while (index >= 0) {
                    BackStackRecord bss = this.mBackStack.get(index);
                    if ((name != null && name.equals(bss.getName())) || (id >= 0 && id == bss.mIndex)) {
                        break;
                    }
                    index2 = index - 1;
                }
                if (index < 0) {
                    return false;
                }
                if ((flags & 1) != 0) {
                    index--;
                    while (index >= 0) {
                        BackStackRecord bss2 = this.mBackStack.get(index);
                        if ((name == null || !name.equals(bss2.getName())) && (id < 0 || id != bss2.mIndex)) {
                            break;
                        }
                        index--;
                    }
                }
            }
            if (index == this.mBackStack.size() - 1) {
                return false;
            }
            for (int i = this.mBackStack.size() - 1; i > index; i--) {
                records.add(this.mBackStack.remove(i));
                isRecordPop.add(true);
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public FragmentManagerNonConfig retainNonConfig() {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        }
        return this.mNonConfig.getSnapshot();
    }

    /* access modifiers changed from: package-private */
    public Parcelable saveAllState() {
        int size;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.setIsStateSaved(true);
        ArrayList<FragmentState> active = this.mFragmentStore.saveActiveFragments();
        if (!active.isEmpty()) {
            ArrayList<String> added = this.mFragmentStore.saveAddedFragments();
            BackStackState[] backStack = null;
            ArrayList<BackStackRecord> arrayList = this.mBackStack;
            if (arrayList != null && (size = arrayList.size()) > 0) {
                backStack = new BackStackState[size];
                for (int i = 0; i < size; i++) {
                    backStack[i] = new BackStackState(this.mBackStack.get(i));
                    if (isLoggingEnabled(2)) {
                        Log.v(TAG, "saveAllState: adding back stack #" + i + ": " + this.mBackStack.get(i));
                    }
                }
            }
            FragmentManagerState fms = new FragmentManagerState();
            fms.mActive = active;
            fms.mAdded = added;
            fms.mBackStack = backStack;
            fms.mBackStackIndex = this.mBackStackIndex.get();
            Fragment fragment = this.mPrimaryNav;
            if (fragment != null) {
                fms.mPrimaryNavActiveWho = fragment.mWho;
            }
            fms.mResultKeys.addAll(this.mResults.keySet());
            fms.mResults.addAll(this.mResults.values());
            fms.mLaunchedFragments = new ArrayList<>(this.mLaunchedFragments);
            return fms;
        } else if (!isLoggingEnabled(2)) {
            return null;
        } else {
            Log.v(TAG, "saveAllState: no fragments!");
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreAllState(@Nullable Parcelable state, @Nullable FragmentManagerNonConfig nonConfig) {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.mNonConfig.restoreFromSnapshot(nonConfig);
        restoreSaveState(state);
    }

    /* access modifiers changed from: package-private */
    public void restoreSaveState(@Nullable Parcelable state) {
        FragmentStateManager fragmentStateManager;
        if (state != null) {
            FragmentManagerState fms = (FragmentManagerState) state;
            if (fms.mActive != null) {
                this.mFragmentStore.resetActiveFragments();
                Iterator<FragmentState> it = fms.mActive.iterator();
                while (it.hasNext()) {
                    FragmentState fs = it.next();
                    if (fs != null) {
                        Fragment retainedFragment = this.mNonConfig.findRetainedFragmentByWho(fs.mWho);
                        if (retainedFragment != null) {
                            if (isLoggingEnabled(2)) {
                                Log.v(TAG, "restoreSaveState: re-attaching retained " + retainedFragment);
                            }
                            fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, retainedFragment, fs);
                        } else {
                            fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.getContext().getClassLoader(), getFragmentFactory(), fs);
                        }
                        Fragment f = fragmentStateManager.getFragment();
                        f.mFragmentManager = this;
                        if (isLoggingEnabled(2)) {
                            Log.v(TAG, "restoreSaveState: active (" + f.mWho + "): " + f);
                        }
                        fragmentStateManager.restoreState(this.mHost.getContext().getClassLoader());
                        this.mFragmentStore.makeActive(fragmentStateManager);
                        fragmentStateManager.setFragmentManagerState(this.mCurState);
                    }
                }
                for (Fragment f2 : this.mNonConfig.getRetainedFragments()) {
                    if (!this.mFragmentStore.containsActiveFragment(f2.mWho)) {
                        if (isLoggingEnabled(2)) {
                            Log.v(TAG, "Discarding retained Fragment " + f2 + " that was not found in the set of active Fragments " + fms.mActive);
                        }
                        this.mNonConfig.removeRetainedFragment(f2);
                        f2.mFragmentManager = this;
                        FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, f2);
                        fragmentStateManager2.setFragmentManagerState(1);
                        fragmentStateManager2.moveToExpectedState();
                        f2.mRemoving = true;
                        fragmentStateManager2.moveToExpectedState();
                    }
                }
                this.mFragmentStore.restoreAddedFragments(fms.mAdded);
                if (fms.mBackStack != null) {
                    this.mBackStack = new ArrayList<>(fms.mBackStack.length);
                    int i = 0;
                    while (true) {
                        BackStackState[] backStackStateArr = fms.mBackStack;
                        if (i >= backStackStateArr.length) {
                            break;
                        }
                        BackStackRecord bse = backStackStateArr[i].instantiate(this);
                        if (isLoggingEnabled(2)) {
                            Log.v(TAG, "restoreAllState: back stack #" + i + " (index " + bse.mIndex + "): " + bse);
                            PrintWriter pw = new PrintWriter(new LogWriter(TAG));
                            bse.dump(JustifyTextView.TWO_CHINESE_BLANK, pw, false);
                            pw.close();
                        }
                        this.mBackStack.add(bse);
                        i++;
                    }
                } else {
                    this.mBackStack = null;
                }
                this.mBackStackIndex.set(fms.mBackStackIndex);
                String str = fms.mPrimaryNavActiveWho;
                if (str != null) {
                    Fragment findActiveFragment = findActiveFragment(str);
                    this.mPrimaryNav = findActiveFragment;
                    dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment);
                }
                ArrayList<String> savedResultKeys = fms.mResultKeys;
                if (savedResultKeys != null) {
                    for (int i2 = 0; i2 < savedResultKeys.size(); i2++) {
                        Bundle savedResult = fms.mResults.get(i2);
                        savedResult.setClassLoader(this.mHost.getContext().getClassLoader());
                        this.mResults.put(savedResultKeys.get(i2), savedResult);
                    }
                }
                this.mLaunchedFragments = new ArrayDeque<>(fms.mLaunchedFragments);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentHostCallback<?> getHost() {
        return this.mHost;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Fragment getParent() {
        return this.mParent;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentContainer getContainer() {
        return this.mContainer;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentStore getFragmentStore() {
        return this.mFragmentStore;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: androidx.activity.OnBackPressedDispatcherOwner} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: androidx.fragment.app.Fragment} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.SuppressLint({"SyntheticAccessor"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void attachController(@androidx.annotation.NonNull androidx.fragment.app.FragmentHostCallback<?> r7, @androidx.annotation.NonNull androidx.fragment.app.FragmentContainer r8, @androidx.annotation.Nullable final androidx.fragment.app.Fragment r9) {
        /*
            r6 = this;
            androidx.fragment.app.FragmentHostCallback<?> r0 = r6.mHost
            if (r0 != 0) goto L_0x010b
            r6.mHost = r7
            r6.mContainer = r8
            r6.mParent = r9
            if (r9 == 0) goto L_0x0015
            androidx.fragment.app.FragmentManager$8 r0 = new androidx.fragment.app.FragmentManager$8
            r0.<init>(r9)
            r6.addFragmentOnAttachListener(r0)
            goto L_0x001f
        L_0x0015:
            boolean r0 = r7 instanceof androidx.fragment.app.FragmentOnAttachListener
            if (r0 == 0) goto L_0x001f
            r0 = r7
            androidx.fragment.app.FragmentOnAttachListener r0 = (androidx.fragment.app.FragmentOnAttachListener) r0
            r6.addFragmentOnAttachListener(r0)
        L_0x001f:
            androidx.fragment.app.Fragment r0 = r6.mParent
            if (r0 == 0) goto L_0x0026
            r6.updateOnBackPressedCallbackEnabled()
        L_0x0026:
            boolean r0 = r7 instanceof androidx.activity.OnBackPressedDispatcherOwner
            if (r0 == 0) goto L_0x003d
            r0 = r7
            androidx.activity.OnBackPressedDispatcherOwner r0 = (androidx.activity.OnBackPressedDispatcherOwner) r0
            androidx.activity.OnBackPressedDispatcher r1 = r0.getOnBackPressedDispatcher()
            r6.mOnBackPressedDispatcher = r1
            if (r9 == 0) goto L_0x0037
            r2 = r9
            goto L_0x0038
        L_0x0037:
            r2 = r0
        L_0x0038:
            androidx.activity.OnBackPressedCallback r3 = r6.mOnBackPressedCallback
            r1.addCallback(r2, r3)
        L_0x003d:
            if (r9 == 0) goto L_0x0048
            androidx.fragment.app.FragmentManager r0 = r9.mFragmentManager
            androidx.fragment.app.FragmentManagerViewModel r0 = r0.getChildNonConfig(r9)
            r6.mNonConfig = r0
            goto L_0x0062
        L_0x0048:
            boolean r0 = r7 instanceof androidx.lifecycle.ViewModelStoreOwner
            if (r0 == 0) goto L_0x005a
            r0 = r7
            androidx.lifecycle.ViewModelStoreOwner r0 = (androidx.lifecycle.ViewModelStoreOwner) r0
            androidx.lifecycle.ViewModelStore r0 = r0.getViewModelStore()
            androidx.fragment.app.FragmentManagerViewModel r1 = androidx.fragment.app.FragmentManagerViewModel.getInstance(r0)
            r6.mNonConfig = r1
            goto L_0x0062
        L_0x005a:
            androidx.fragment.app.FragmentManagerViewModel r0 = new androidx.fragment.app.FragmentManagerViewModel
            r1 = 0
            r0.<init>(r1)
            r6.mNonConfig = r0
        L_0x0062:
            androidx.fragment.app.FragmentManagerViewModel r0 = r6.mNonConfig
            boolean r1 = r6.isStateSaved()
            r0.setIsStateSaved(r1)
            androidx.fragment.app.FragmentStore r0 = r6.mFragmentStore
            androidx.fragment.app.FragmentManagerViewModel r1 = r6.mNonConfig
            r0.setNonConfig(r1)
            androidx.fragment.app.FragmentHostCallback<?> r0 = r6.mHost
            boolean r1 = r0 instanceof androidx.activity.result.ActivityResultRegistryOwner
            if (r1 == 0) goto L_0x010a
            androidx.activity.result.ActivityResultRegistryOwner r0 = (androidx.activity.result.ActivityResultRegistryOwner) r0
            androidx.activity.result.ActivityResultRegistry r0 = r0.getActivityResultRegistry()
            if (r9 == 0) goto L_0x0094
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r9.mWho
            r1.append(r2)
            java.lang.String r2 = ":"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x0096
        L_0x0094:
            java.lang.String r1 = ""
        L_0x0096:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "FragmentManager:"
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = "StartActivityForResult"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult r4 = new androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult
            r4.<init>()
            androidx.fragment.app.FragmentManager$9 r5 = new androidx.fragment.app.FragmentManager$9
            r5.<init>()
            androidx.activity.result.ActivityResultLauncher r3 = r0.register(r3, r4, r5)
            r6.mStartActivityForResult = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = "StartIntentSenderForResult"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            androidx.fragment.app.FragmentManager$FragmentIntentSenderContract r4 = new androidx.fragment.app.FragmentManager$FragmentIntentSenderContract
            r4.<init>()
            androidx.fragment.app.FragmentManager$10 r5 = new androidx.fragment.app.FragmentManager$10
            r5.<init>()
            androidx.activity.result.ActivityResultLauncher r3 = r0.register(r3, r4, r5)
            r6.mStartIntentSenderForResult = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r4 = "RequestPermissions"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions r4 = new androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions
            r4.<init>()
            androidx.fragment.app.FragmentManager$11 r5 = new androidx.fragment.app.FragmentManager$11
            r5.<init>()
            androidx.activity.result.ActivityResultLauncher r3 = r0.register(r3, r4, r5)
            r6.mRequestPermissions = r3
        L_0x010a:
            return
        L_0x010b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Already attached"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.attachController(androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer, androidx.fragment.app.Fragment):void");
    }

    /* access modifiers changed from: package-private */
    public void noteStateNotSaved() {
        if (this.mHost != null) {
            this.mStateSaved = false;
            this.mStopped = false;
            this.mNonConfig.setIsStateSaved(false);
            for (Fragment fragment : this.mFragmentStore.getFragments()) {
                if (fragment != null) {
                    fragment.noteStateNotSaved();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void launchStartActivityForResult(@NonNull Fragment f, @SuppressLint({"UnknownNullness"}) Intent intent, int requestCode, @Nullable Bundle options) {
        if (this.mStartActivityForResult != null) {
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(f.mWho, requestCode));
            if (!(intent == null || options == null)) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, options);
            }
            this.mStartActivityForResult.launch(intent);
            return;
        }
        this.mHost.onStartActivityFromFragment(f, intent, requestCode, options);
    }

    /* access modifiers changed from: package-private */
    public void launchStartIntentSenderForResult(@NonNull Fragment f, @SuppressLint({"UnknownNullness"}) IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) {
        Intent fillInIntent2;
        Fragment fragment = f;
        Bundle bundle = options;
        if (this.mStartIntentSenderForResult != null) {
            if (bundle != null) {
                if (fillInIntent == null) {
                    fillInIntent2 = new Intent();
                    fillInIntent2.putExtra(EXTRA_CREATED_FILLIN_INTENT, true);
                } else {
                    fillInIntent2 = fillInIntent;
                }
                if (isLoggingEnabled(2)) {
                    Log.v(TAG, "ActivityOptions " + bundle + " were added to fillInIntent " + fillInIntent2 + " for fragment " + fragment);
                }
                fillInIntent2.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
            } else {
                fillInIntent2 = fillInIntent;
            }
            IntentSenderRequest request = new IntentSenderRequest.Builder(intent).setFillInIntent(fillInIntent2).setFlags(flagsValues, flagsMask).build();
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(fragment.mWho, requestCode));
            if (isLoggingEnabled(2)) {
                Log.v(TAG, "Fragment " + fragment + "is launching an IntentSender for result ");
            }
            this.mStartIntentSenderForResult.launch(request);
            return;
        }
        IntentSender intentSender = intent;
        int i = requestCode;
        int i2 = flagsMask;
        int i3 = flagsValues;
        this.mHost.onStartIntentSenderFromFragment(f, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        Intent intent2 = fillInIntent;
    }

    /* access modifiers changed from: package-private */
    public void launchRequestPermissions(@NonNull Fragment f, @NonNull String[] permissions, int requestCode) {
        if (this.mRequestPermissions != null) {
            this.mLaunchedFragments.addLast(new LaunchedFragmentInfo(f.mWho, requestCode));
            this.mRequestPermissions.launch(permissions);
            return;
        }
        this.mHost.onRequestPermissionsFromFragment(f, permissions, requestCode);
    }

    /* access modifiers changed from: package-private */
    public void dispatchAttach() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(0);
    }

    /* access modifiers changed from: package-private */
    public void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(1);
    }

    /* access modifiers changed from: package-private */
    public void dispatchViewCreated() {
        dispatchStateChange(2);
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(4);
    }

    /* access modifiers changed from: package-private */
    public void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(5);
    }

    /* access modifiers changed from: package-private */
    public void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(7);
    }

    /* access modifiers changed from: package-private */
    public void dispatchPause() {
        dispatchStateChange(5);
    }

    /* access modifiers changed from: package-private */
    public void dispatchStop() {
        this.mStopped = true;
        this.mNonConfig.setIsStateSaved(true);
        dispatchStateChange(4);
    }

    /* access modifiers changed from: package-private */
    public void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    /* access modifiers changed from: package-private */
    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions(true);
        endAnimatingAwayFragments();
        dispatchStateChange(-1);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.mStartActivityForResult;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    /* JADX INFO: finally extract failed */
    private void dispatchStateChange(int nextState) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.dispatchStateChange(nextState);
            moveToState(nextState, false);
            if (USE_STATE_MANAGER) {
                for (SpecialEffectsController controller : collectAllSpecialEffectsController()) {
                    controller.forceCompleteAllOperations();
                }
            }
            this.mExecutingActions = false;
            execPendingActions(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performMultiWindowModeChanged(isInMultiWindowMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performPictureInPictureModeChanged(isInPictureInPictureMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchConfigurationChanged(@NonNull Configuration newConfig) {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performConfigurationChanged(newConfig);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchLowMemory() {
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null) {
                f.performLowMemory();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean show = false;
        ArrayList<Fragment> newMenus = null;
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && isParentMenuVisible(f) && f.performCreateOptionsMenu(menu, inflater)) {
                show = true;
                if (newMenus == null) {
                    newMenus = new ArrayList<>();
                }
                newMenus.add(f);
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment f2 = this.mCreatedMenus.get(i);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchPrepareOptionsMenu(@NonNull Menu menu) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean show = false;
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && isParentMenuVisible(f) && f.performPrepareOptionsMenu(menu)) {
                show = true;
            }
        }
        return show;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchOptionsItemSelected(@NonNull MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && f.performOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchContextItemSelected(@NonNull MenuItem item) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment f : this.mFragmentStore.getFragments()) {
            if (f != null && f.performContextItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void dispatchOptionsMenuClosed(@NonNull Menu menu) {
        if (this.mCurState >= 1) {
            for (Fragment f : this.mFragmentStore.getFragments()) {
                if (f != null) {
                    f.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setPrimaryNavigationFragment(@Nullable Fragment f) {
        if (f == null || (f.equals(findActiveFragment(f.mWho)) && (f.mHost == null || f.mFragmentManager == this))) {
            Fragment previousPrimaryNav = this.mPrimaryNav;
            this.mPrimaryNav = f;
            dispatchParentPrimaryNavigationFragmentChanged(previousPrimaryNav);
            dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
            return;
        }
        throw new IllegalArgumentException("Fragment " + f + " is not an active fragment of FragmentManager " + this);
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(@Nullable Fragment f) {
        if (f != null && f.equals(findActiveFragment(f.mWho))) {
            f.performPrimaryNavigationFragmentChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchPrimaryNavigationFragmentChanged() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    @Nullable
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    /* access modifiers changed from: package-private */
    public void setMaxLifecycle(@NonNull Fragment f, @NonNull Lifecycle.State state) {
        if (!f.equals(findActiveFragment(f.mWho)) || !(f.mHost == null || f.mFragmentManager == this)) {
            throw new IllegalArgumentException("Fragment " + f + " is not an active fragment of FragmentManager " + this);
        }
        f.mMaxState = state;
    }

    public void setFragmentFactory(@NonNull FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    @NonNull
    public FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getFragmentFactory();
        }
        return this.mHostFragmentFactory;
    }

    /* access modifiers changed from: package-private */
    public void setSpecialEffectsControllerFactory(@NonNull SpecialEffectsControllerFactory specialEffectsControllerFactory) {
        this.mSpecialEffectsControllerFactory = specialEffectsControllerFactory;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SpecialEffectsControllerFactory getSpecialEffectsControllerFactory() {
        SpecialEffectsControllerFactory specialEffectsControllerFactory = this.mSpecialEffectsControllerFactory;
        if (specialEffectsControllerFactory != null) {
            return specialEffectsControllerFactory;
        }
        Fragment fragment = this.mParent;
        if (fragment != null) {
            return fragment.mFragmentManager.getSpecialEffectsControllerFactory();
        }
        return this.mDefaultSpecialEffectsControllerFactory;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
        return this.mLifecycleCallbacksDispatcher;
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks cb, boolean recursive) {
        this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(cb, recursive);
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks cb) {
        this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(cb);
    }

    public void addFragmentOnAttachListener(@NonNull FragmentOnAttachListener listener) {
        this.mOnAttachListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnAttachFragment(@NonNull Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment(this, fragment);
        }
    }

    public void removeFragmentOnAttachListener(@NonNull FragmentOnAttachListener listener) {
        this.mOnAttachListeners.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public boolean checkForMenus() {
        boolean hasMenu = false;
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null) {
                hasMenu = isMenuAvailable(fragment);
                continue;
            }
            if (hasMenu) {
                return true;
            }
        }
        return false;
    }

    private boolean isMenuAvailable(@NonNull Fragment f) {
        return (f.mHasMenu && f.mMenuVisible) || f.mChildFragmentManager.checkForMenus();
    }

    /* access modifiers changed from: package-private */
    public void invalidateMenuForFragment(@NonNull Fragment f) {
        if (f.mAdded && isMenuAvailable(f)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    static int reverseTransit(int transit) {
        switch (transit) {
            case 4097:
                return 8194;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
            case 8194:
                return 4097;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory;
    }

    public class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(@Nullable String name, int id, int flags) {
            this.mName = name;
            this.mId = id;
            this.mFlags = flags;
        }

        public boolean generateOps(@NonNull ArrayList<BackStackRecord> records, @NonNull ArrayList<Boolean> isRecordPop) {
            Fragment fragment = FragmentManager.this.mPrimaryNav;
            if (fragment != null && this.mId < 0 && this.mName == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
                return false;
            }
            return FragmentManager.this.popBackStackState(records, isRecordPop, this.mName, this.mId, this.mFlags);
        }
    }

    public static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        final boolean mIsBack;
        private int mNumPostponed;
        final BackStackRecord mRecord;

        StartEnterTransitionListener(@NonNull BackStackRecord record, boolean isBack) {
            this.mIsBack = isBack;
            this.mRecord = record;
        }

        public void onStartEnterTransition() {
            int i = this.mNumPostponed - 1;
            this.mNumPostponed = i;
            if (i == 0) {
                this.mRecord.mManager.scheduleCommit();
            }
        }

        public void startListening() {
            this.mNumPostponed++;
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        /* access modifiers changed from: package-private */
        public void completeTransaction() {
            boolean z = false;
            boolean canceled = this.mNumPostponed > 0;
            for (Fragment fragment : this.mRecord.mManager.getFragments()) {
                fragment.setOnStartEnterTransitionListener((Fragment.OnStartEnterTransitionListener) null);
                if (canceled && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            BackStackRecord backStackRecord = this.mRecord;
            FragmentManager fragmentManager = backStackRecord.mManager;
            boolean z2 = this.mIsBack;
            if (!canceled) {
                z = true;
            }
            fragmentManager.completeExecute(backStackRecord, z2, z, true);
        }

        /* access modifiers changed from: package-private */
        public void cancelTransaction() {
            BackStackRecord backStackRecord = this.mRecord;
            backStackRecord.mManager.completeExecute(backStackRecord, this.mIsBack, false, false);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator<LaunchedFragmentInfo>() {
            public LaunchedFragmentInfo createFromParcel(Parcel in) {
                return new LaunchedFragmentInfo(in);
            }

            public LaunchedFragmentInfo[] newArray(int size) {
                return new LaunchedFragmentInfo[size];
            }
        };
        int mRequestCode;
        String mWho;

        LaunchedFragmentInfo(@NonNull String who, int requestCode) {
            this.mWho = who;
            this.mRequestCode = requestCode;
        }

        LaunchedFragmentInfo(@NonNull Parcel in) {
            this.mWho = in.readString();
            this.mRequestCode = in.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mWho);
            dest.writeInt(this.mRequestCode);
        }
    }

    public static class FragmentIntentSenderContract extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        FragmentIntentSenderContract() {
        }

        @NonNull
        public Intent createIntent(@NonNull Context context, IntentSenderRequest input) {
            Bundle activityOptions;
            Intent result = new Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST);
            Intent fillInIntent = input.getFillInIntent();
            if (!(fillInIntent == null || (activityOptions = fillInIntent.getBundleExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE)) == null)) {
                result.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, activityOptions);
                fillInIntent.removeExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE);
                if (fillInIntent.getBooleanExtra(FragmentManager.EXTRA_CREATED_FILLIN_INTENT, false)) {
                    input = new IntentSenderRequest.Builder(input.getIntentSender()).setFillInIntent((Intent) null).setFlags(input.getFlagsValues(), input.getFlagsMask()).build();
                }
            }
            result.putExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, input);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(FragmentManager.TAG, "CreateIntent created the following intent: " + result);
            }
            return result;
        }

        @NonNull
        public ActivityResult parseResult(int resultCode, @Nullable Intent intent) {
            return new ActivityResult(resultCode, intent);
        }
    }
}
