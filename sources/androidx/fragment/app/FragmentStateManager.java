package androidx.fragment.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.SpecialEffectsController;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;

public class FragmentStateManager {
    private static final String TAG = "FragmentManager";
    private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    private static final String TARGET_STATE_TAG = "android:target_state";
    private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    private static final String VIEW_REGISTRY_STATE_TAG = "android:view_registry_state";
    private static final String VIEW_STATE_TAG = "android:view_state";
    private final FragmentLifecycleCallbacksDispatcher mDispatcher;
    @NonNull
    private final Fragment mFragment;
    private int mFragmentManagerState = -1;
    private final FragmentStore mFragmentStore;
    private boolean mMovingToState = false;

    FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher dispatcher, @NonNull FragmentStore fragmentStore, @NonNull Fragment fragment) {
        this.mDispatcher = dispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
    }

    FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher dispatcher, @NonNull FragmentStore fragmentStore, @NonNull ClassLoader classLoader, @NonNull FragmentFactory fragmentFactory, @NonNull FragmentState fs) {
        this.mDispatcher = dispatcher;
        this.mFragmentStore = fragmentStore;
        Fragment instantiate = fragmentFactory.instantiate(classLoader, fs.mClassName);
        this.mFragment = instantiate;
        Bundle bundle = fs.mArguments;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
        instantiate.setArguments(fs.mArguments);
        instantiate.mWho = fs.mWho;
        instantiate.mFromLayout = fs.mFromLayout;
        instantiate.mRestored = true;
        instantiate.mFragmentId = fs.mFragmentId;
        instantiate.mContainerId = fs.mContainerId;
        instantiate.mTag = fs.mTag;
        instantiate.mRetainInstance = fs.mRetainInstance;
        instantiate.mRemoving = fs.mRemoving;
        instantiate.mDetached = fs.mDetached;
        instantiate.mHidden = fs.mHidden;
        instantiate.mMaxState = Lifecycle.State.values()[fs.mMaxLifecycleState];
        Bundle bundle2 = fs.mSavedFragmentState;
        if (bundle2 != null) {
            instantiate.mSavedFragmentState = bundle2;
        } else {
            instantiate.mSavedFragmentState = new Bundle();
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "Instantiated fragment " + instantiate);
        }
    }

    FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher dispatcher, @NonNull FragmentStore fragmentStore, @NonNull Fragment retainedFragment, @NonNull FragmentState fs) {
        this.mDispatcher = dispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = retainedFragment;
        retainedFragment.mSavedViewState = null;
        retainedFragment.mSavedViewRegistryState = null;
        retainedFragment.mBackStackNesting = 0;
        retainedFragment.mInLayout = false;
        retainedFragment.mAdded = false;
        Fragment fragment = retainedFragment.mTarget;
        retainedFragment.mTargetWho = fragment != null ? fragment.mWho : null;
        retainedFragment.mTarget = null;
        Bundle bundle = fs.mSavedFragmentState;
        if (bundle != null) {
            retainedFragment.mSavedFragmentState = bundle;
        } else {
            retainedFragment.mSavedFragmentState = new Bundle();
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Fragment getFragment() {
        return this.mFragment;
    }

    /* access modifiers changed from: package-private */
    public void setFragmentManagerState(int state) {
        this.mFragmentManagerState = state;
    }

    /* access modifiers changed from: package-private */
    public int computeExpectedState() {
        Fragment fragment;
        ViewGroup viewGroup;
        Fragment fragment2 = this.mFragment;
        if (fragment2.mFragmentManager == null) {
            return fragment2.mState;
        }
        int maxState = this.mFragmentManagerState;
        switch (AnonymousClass2.$SwitchMap$androidx$lifecycle$Lifecycle$State[fragment2.mMaxState.ordinal()]) {
            case 1:
                break;
            case 2:
                maxState = Math.min(maxState, 5);
                break;
            case 3:
                maxState = Math.min(maxState, 1);
                break;
            case 4:
                maxState = Math.min(maxState, 0);
                break;
            default:
                maxState = Math.min(maxState, -1);
                break;
        }
        Fragment fragment3 = this.mFragment;
        if (fragment3.mFromLayout) {
            if (fragment3.mInLayout) {
                maxState = Math.max(this.mFragmentManagerState, 2);
                View view = this.mFragment.mView;
                if (view != null && view.getParent() == null) {
                    maxState = Math.min(maxState, 2);
                }
            } else {
                maxState = this.mFragmentManagerState < 4 ? Math.min(maxState, fragment3.mState) : Math.min(maxState, 1);
            }
        }
        if (!this.mFragment.mAdded) {
            maxState = Math.min(maxState, 1);
        }
        SpecialEffectsController.Operation.LifecycleImpact awaitingEffect = null;
        if (FragmentManager.USE_STATE_MANAGER && (viewGroup = fragment.mContainer) != null) {
            awaitingEffect = SpecialEffectsController.getOrCreateController(viewGroup, (fragment = this.mFragment).getParentFragmentManager()).getAwaitingCompletionLifecycleImpact(this);
        }
        if (awaitingEffect == SpecialEffectsController.Operation.LifecycleImpact.ADDING) {
            maxState = Math.min(maxState, 6);
        } else if (awaitingEffect == SpecialEffectsController.Operation.LifecycleImpact.REMOVING) {
            maxState = Math.max(maxState, 3);
        } else {
            Fragment fragment4 = this.mFragment;
            if (fragment4.mRemoving) {
                if (fragment4.isInBackStack()) {
                    maxState = Math.min(maxState, 1);
                } else {
                    maxState = Math.min(maxState, -1);
                }
            }
        }
        Fragment fragment5 = this.mFragment;
        if (fragment5.mDeferStart && fragment5.mState < 5) {
            maxState = Math.min(maxState, 4);
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "computeExpectedState() of " + maxState + " for " + this.mFragment);
        }
        return maxState;
    }

    /* renamed from: androidx.fragment.app.FragmentStateManager$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$State;

        static {
            int[] iArr = new int[Lifecycle.State.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$State = iArr;
            try {
                iArr[Lifecycle.State.RESUMED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.CREATED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.INITIALIZED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void moveToExpectedState() {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        ViewGroup viewGroup3;
        if (!this.mMovingToState) {
            boolean z = false;
            z = true;
            try {
                while (true) {
                    int computeExpectedState = computeExpectedState();
                    int newState = computeExpectedState;
                    Fragment fragment = this.mFragment;
                    int i = fragment.mState;
                    if (computeExpectedState != i) {
                        if (newState <= i) {
                            switch (i - 1) {
                                case -1:
                                    detach();
                                    break;
                                case 0:
                                    destroy();
                                    break;
                                case 1:
                                    destroyFragmentView();
                                    this.mFragment.mState = z ? 1 : 0;
                                    break;
                                case 2:
                                    fragment.mInLayout = z;
                                    fragment.mState = 2;
                                    break;
                                case 3:
                                    if (FragmentManager.isLoggingEnabled(3)) {
                                        Log.d(TAG, "movefrom ACTIVITY_CREATED: " + this.mFragment);
                                    }
                                    Fragment fragment2 = this.mFragment;
                                    if (fragment2.mView != null && fragment2.mSavedViewState == null) {
                                        saveViewState();
                                    }
                                    Fragment fragment3 = this.mFragment;
                                    if (!(fragment3.mView == null || (viewGroup2 = fragment3.mContainer) == null)) {
                                        SpecialEffectsController.getOrCreateController(viewGroup2, fragment3.getParentFragmentManager()).enqueueRemove(this);
                                    }
                                    this.mFragment.mState = 3;
                                    break;
                                case 4:
                                    stop();
                                    break;
                                case 5:
                                    fragment.mState = 5;
                                    break;
                                case 6:
                                    pause();
                                    break;
                            }
                        } else {
                            switch (i + 1) {
                                case 0:
                                    attach();
                                    break;
                                case 1:
                                    create();
                                    break;
                                case 2:
                                    ensureInflatedView();
                                    createView();
                                    break;
                                case 3:
                                    activityCreated();
                                    break;
                                case 4:
                                    if (!(fragment.mView == null || (viewGroup3 = fragment.mContainer) == null)) {
                                        SpecialEffectsController.getOrCreateController(viewGroup3, fragment.getParentFragmentManager()).enqueueAdd(SpecialEffectsController.Operation.State.from(this.mFragment.mView.getVisibility()), this);
                                    }
                                    this.mFragment.mState = 4;
                                    break;
                                case 5:
                                    start();
                                    break;
                                case 6:
                                    fragment.mState = 6;
                                    break;
                                case 7:
                                    resume();
                                    break;
                            }
                        }
                    } else {
                        if (FragmentManager.USE_STATE_MANAGER && fragment.mHiddenChanged) {
                            if (!(fragment.mView == null || (viewGroup = fragment.mContainer) == null)) {
                                SpecialEffectsController controller = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager());
                                if (this.mFragment.mHidden) {
                                    controller.enqueueHide(this);
                                } else {
                                    controller.enqueueShow(this);
                                }
                            }
                            Fragment fragment4 = this.mFragment;
                            FragmentManager fragmentManager = fragment4.mFragmentManager;
                            if (fragmentManager != null) {
                                fragmentManager.invalidateMenuForFragment(fragment4);
                            }
                            Fragment fragment5 = this.mFragment;
                            fragment5.mHiddenChanged = z;
                            fragment5.onHiddenChanged(fragment5.mHidden);
                        }
                        this.mMovingToState = z;
                        return;
                    }
                }
            } finally {
                this.mMovingToState = z;
            }
        } else if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "Ignoring re-entrant call to moveToExpectedState() for " + getFragment());
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureInflatedView() {
        Fragment fragment = this.mFragment;
        if (fragment.mFromLayout && fragment.mInLayout && !fragment.mPerformedCreateView) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d(TAG, "moveto CREATE_VIEW: " + this.mFragment);
            }
            Fragment fragment2 = this.mFragment;
            fragment2.performCreateView(fragment2.performGetLayoutInflater(fragment2.mSavedFragmentState), (ViewGroup) null, this.mFragment.mSavedFragmentState);
            View view = this.mFragment.mView;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                Fragment fragment3 = this.mFragment;
                fragment3.mView.setTag(R.id.fragment_container_view_tag, fragment3);
                Fragment fragment4 = this.mFragment;
                if (fragment4.mHidden) {
                    fragment4.mView.setVisibility(8);
                }
                this.mFragment.performViewCreated();
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
                Fragment fragment5 = this.mFragment;
                fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(fragment5, fragment5.mView, fragment5.mSavedFragmentState, false);
                this.mFragment.mState = 2;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreState(@NonNull ClassLoader classLoader) {
        Bundle bundle = this.mFragment.mSavedFragmentState;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
            Fragment fragment = this.mFragment;
            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
            Fragment fragment2 = this.mFragment;
            fragment2.mSavedViewRegistryState = fragment2.mSavedFragmentState.getBundle(VIEW_REGISTRY_STATE_TAG);
            Fragment fragment3 = this.mFragment;
            fragment3.mTargetWho = fragment3.mSavedFragmentState.getString(TARGET_STATE_TAG);
            Fragment fragment4 = this.mFragment;
            if (fragment4.mTargetWho != null) {
                fragment4.mTargetRequestCode = fragment4.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
            }
            Fragment fragment5 = this.mFragment;
            Boolean bool = fragment5.mSavedUserVisibleHint;
            if (bool != null) {
                fragment5.mUserVisibleHint = bool.booleanValue();
                this.mFragment.mSavedUserVisibleHint = null;
            } else {
                fragment5.mUserVisibleHint = fragment5.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
            }
            Fragment fragment6 = this.mFragment;
            if (!fragment6.mUserVisibleHint) {
                fragment6.mDeferStart = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void attach() {
        FragmentStateManager targetFragmentStateManager;
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto ATTACHED: " + this.mFragment);
        }
        Fragment fragment = this.mFragment;
        Fragment fragment2 = fragment.mTarget;
        if (fragment2 != null) {
            targetFragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment2.mWho);
            if (targetFragmentStateManager != null) {
                Fragment fragment3 = this.mFragment;
                fragment3.mTargetWho = fragment3.mTarget.mWho;
                fragment3.mTarget = null;
            } else {
                throw new IllegalStateException("Fragment " + this.mFragment + " declared target fragment " + this.mFragment.mTarget + " that does not belong to this FragmentManager!");
            }
        } else {
            String str = fragment.mTargetWho;
            if (str != null) {
                targetFragmentStateManager = this.mFragmentStore.getFragmentStateManager(str);
                if (targetFragmentStateManager == null) {
                    throw new IllegalStateException("Fragment " + this.mFragment + " declared target fragment " + this.mFragment.mTargetWho + " that does not belong to this FragmentManager!");
                }
            } else {
                targetFragmentStateManager = null;
            }
        }
        if (targetFragmentStateManager != null && (FragmentManager.USE_STATE_MANAGER || targetFragmentStateManager.getFragment().mState < 1)) {
            targetFragmentStateManager.moveToExpectedState();
        }
        Fragment fragment4 = this.mFragment;
        fragment4.mHost = fragment4.mFragmentManager.getHost();
        Fragment fragment5 = this.mFragment;
        fragment5.mParentFragment = fragment5.mFragmentManager.getParent();
        this.mDispatcher.dispatchOnFragmentPreAttached(this.mFragment, false);
        this.mFragment.performAttach();
        this.mDispatcher.dispatchOnFragmentAttached(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    public void create() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto CREATED: " + this.mFragment);
        }
        Fragment fragment = this.mFragment;
        if (!fragment.mIsCreated) {
            this.mDispatcher.dispatchOnFragmentPreCreated(fragment, fragment.mSavedFragmentState, false);
            Fragment fragment2 = this.mFragment;
            fragment2.performCreate(fragment2.mSavedFragmentState);
            FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
            Fragment fragment3 = this.mFragment;
            fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentCreated(fragment3, fragment3.mSavedFragmentState, false);
            return;
        }
        fragment.restoreChildFragmentState(fragment.mSavedFragmentState);
        this.mFragment.mState = 1;
    }

    /* JADX WARNING: type inference failed for: r4v9, types: [android.view.View] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void createView() {
        /*
            r9 = this;
            androidx.fragment.app.Fragment r0 = r9.mFragment
            boolean r0 = r0.mFromLayout
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 3
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r0)
            java.lang.String r1 = "FragmentManager"
            if (r0 == 0) goto L_0x0026
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "moveto CREATE_VIEW: "
            r0.append(r2)
            androidx.fragment.app.Fragment r2 = r9.mFragment
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
        L_0x0026:
            androidx.fragment.app.Fragment r0 = r9.mFragment
            android.os.Bundle r2 = r0.mSavedFragmentState
            android.view.LayoutInflater r0 = r0.performGetLayoutInflater(r2)
            r2 = 0
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.ViewGroup r4 = r3.mContainer
            if (r4 == 0) goto L_0x0039
            android.view.ViewGroup r2 = r3.mContainer
            goto L_0x00bb
        L_0x0039:
            int r4 = r3.mContainerId
            if (r4 == 0) goto L_0x00bb
            r5 = -1
            if (r4 == r5) goto L_0x009d
            androidx.fragment.app.FragmentManager r3 = r3.mFragmentManager
            androidx.fragment.app.FragmentContainer r3 = r3.getContainer()
            androidx.fragment.app.Fragment r4 = r9.mFragment
            int r4 = r4.mContainerId
            android.view.View r4 = r3.onFindViewById(r4)
            r2 = r4
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            if (r2 != 0) goto L_0x00bb
            androidx.fragment.app.Fragment r4 = r9.mFragment
            boolean r5 = r4.mRestored
            if (r5 == 0) goto L_0x005a
            goto L_0x00bb
        L_0x005a:
            android.content.res.Resources r1 = r4.getResources()     // Catch:{ NotFoundException -> 0x0067 }
            androidx.fragment.app.Fragment r4 = r9.mFragment     // Catch:{ NotFoundException -> 0x0067 }
            int r4 = r4.mContainerId     // Catch:{ NotFoundException -> 0x0067 }
            java.lang.String r1 = r1.getResourceName(r4)     // Catch:{ NotFoundException -> 0x0067 }
            goto L_0x006c
        L_0x0067:
            r1 = move-exception
            java.lang.String r4 = "unknown"
            r1 = r4
        L_0x006c:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "No view found for id 0x"
            r5.append(r6)
            androidx.fragment.app.Fragment r6 = r9.mFragment
            int r6 = r6.mContainerId
            java.lang.String r6 = java.lang.Integer.toHexString(r6)
            r5.append(r6)
            java.lang.String r6 = " ("
            r5.append(r6)
            r5.append(r1)
            java.lang.String r6 = ") for fragment "
            r5.append(r6)
            androidx.fragment.app.Fragment r6 = r9.mFragment
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x009d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Cannot create fragment "
            r3.append(r4)
            androidx.fragment.app.Fragment r4 = r9.mFragment
            r3.append(r4)
            java.lang.String r4 = " for a container view with no id"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            throw r1
        L_0x00bb:
            androidx.fragment.app.Fragment r3 = r9.mFragment
            r3.mContainer = r2
            android.os.Bundle r4 = r3.mSavedFragmentState
            r3.performCreateView(r0, r2, r4)
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.View r3 = r3.mView
            r4 = 2
            if (r3 == 0) goto L_0x017f
            r5 = 0
            r3.setSaveFromParentEnabled(r5)
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.View r6 = r3.mView
            int r7 = androidx.fragment.R.id.fragment_container_view_tag
            r6.setTag(r7, r3)
            if (r2 == 0) goto L_0x00dd
            r9.addViewToContainer()
        L_0x00dd:
            androidx.fragment.app.Fragment r3 = r9.mFragment
            boolean r6 = r3.mHidden
            if (r6 == 0) goto L_0x00ea
            android.view.View r3 = r3.mView
            r6 = 8
            r3.setVisibility(r6)
        L_0x00ea:
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.View r3 = r3.mView
            boolean r3 = androidx.core.view.ViewCompat.isAttachedToWindow(r3)
            if (r3 == 0) goto L_0x00fc
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.View r3 = r3.mView
            androidx.core.view.ViewCompat.requestApplyInsets(r3)
            goto L_0x0108
        L_0x00fc:
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.View r3 = r3.mView
            androidx.fragment.app.FragmentStateManager$1 r6 = new androidx.fragment.app.FragmentStateManager$1
            r6.<init>(r3)
            r3.addOnAttachStateChangeListener(r6)
        L_0x0108:
            androidx.fragment.app.Fragment r3 = r9.mFragment
            r3.performViewCreated()
            androidx.fragment.app.FragmentLifecycleCallbacksDispatcher r3 = r9.mDispatcher
            androidx.fragment.app.Fragment r6 = r9.mFragment
            android.view.View r7 = r6.mView
            android.os.Bundle r8 = r6.mSavedFragmentState
            r3.dispatchOnFragmentViewCreated(r6, r7, r8, r5)
            androidx.fragment.app.Fragment r3 = r9.mFragment
            android.view.View r3 = r3.mView
            int r3 = r3.getVisibility()
            androidx.fragment.app.Fragment r6 = r9.mFragment
            android.view.View r6 = r6.mView
            float r6 = r6.getAlpha()
            boolean r7 = androidx.fragment.app.FragmentManager.USE_STATE_MANAGER
            if (r7 == 0) goto L_0x0174
            androidx.fragment.app.Fragment r5 = r9.mFragment
            r5.setPostOnViewCreatedAlpha(r6)
            androidx.fragment.app.Fragment r5 = r9.mFragment
            android.view.ViewGroup r7 = r5.mContainer
            if (r7 == 0) goto L_0x017f
            if (r3 != 0) goto L_0x017f
            android.view.View r5 = r5.mView
            android.view.View r5 = r5.findFocus()
            if (r5 == 0) goto L_0x016b
            androidx.fragment.app.Fragment r7 = r9.mFragment
            r7.setFocusedView(r5)
            boolean r7 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r4)
            if (r7 == 0) goto L_0x016b
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "requestFocus: Saved focused view "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = " for Fragment "
            r7.append(r8)
            androidx.fragment.app.Fragment r8 = r9.mFragment
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.v(r1, r7)
        L_0x016b:
            androidx.fragment.app.Fragment r1 = r9.mFragment
            android.view.View r1 = r1.mView
            r7 = 0
            r1.setAlpha(r7)
            goto L_0x017f
        L_0x0174:
            androidx.fragment.app.Fragment r1 = r9.mFragment
            if (r3 != 0) goto L_0x017d
            android.view.ViewGroup r7 = r1.mContainer
            if (r7 == 0) goto L_0x017d
            r5 = 1
        L_0x017d:
            r1.mIsNewlyAdded = r5
        L_0x017f:
            androidx.fragment.app.Fragment r1 = r9.mFragment
            r1.mState = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentStateManager.createView():void");
    }

    /* access modifiers changed from: package-private */
    public void activityCreated() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto ACTIVITY_CREATED: " + this.mFragment);
        }
        Fragment fragment = this.mFragment;
        fragment.performActivityCreated(fragment.mSavedFragmentState);
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        Fragment fragment2 = this.mFragment;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated(fragment2, fragment2.mSavedFragmentState, false);
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto STARTED: " + this.mFragment);
        }
        this.mFragment.performStart();
        this.mDispatcher.dispatchOnFragmentStarted(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    public void resume() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto RESUMED: " + this.mFragment);
        }
        View focusedView = this.mFragment.getFocusedView();
        if (focusedView != null && isFragmentViewChild(focusedView)) {
            boolean success = focusedView.requestFocus();
            if (FragmentManager.isLoggingEnabled(2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("requestFocus: Restoring focused view ");
                sb.append(focusedView);
                sb.append(" ");
                sb.append(success ? "succeeded" : "failed");
                sb.append(" on Fragment ");
                sb.append(this.mFragment);
                sb.append(" resulting in focused view ");
                sb.append(this.mFragment.mView.findFocus());
                Log.v(TAG, sb.toString());
            }
        }
        this.mFragment.setFocusedView((View) null);
        this.mFragment.performResume();
        this.mDispatcher.dispatchOnFragmentResumed(this.mFragment, false);
        Fragment fragment = this.mFragment;
        fragment.mSavedFragmentState = null;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
    }

    private boolean isFragmentViewChild(@NonNull View view) {
        if (view == this.mFragment.mView) {
            return true;
        }
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == this.mFragment.mView) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void pause() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom RESUMED: " + this.mFragment);
        }
        this.mFragment.performPause();
        this.mDispatcher.dispatchOnFragmentPaused(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom STARTED: " + this.mFragment);
        }
        this.mFragment.performStop();
        this.mDispatcher.dispatchOnFragmentStopped(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public FragmentState saveState() {
        FragmentState fs = new FragmentState(this.mFragment);
        Fragment fragment = this.mFragment;
        if (fragment.mState <= -1 || fs.mSavedFragmentState != null) {
            fs.mSavedFragmentState = fragment.mSavedFragmentState;
        } else {
            Bundle saveBasicState = saveBasicState();
            fs.mSavedFragmentState = saveBasicState;
            if (this.mFragment.mTargetWho != null) {
                if (saveBasicState == null) {
                    fs.mSavedFragmentState = new Bundle();
                }
                fs.mSavedFragmentState.putString(TARGET_STATE_TAG, this.mFragment.mTargetWho);
                int i = this.mFragment.mTargetRequestCode;
                if (i != 0) {
                    fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, i);
                }
            }
        }
        return fs;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Fragment.SavedState saveInstanceState() {
        Bundle result;
        if (this.mFragment.mState <= -1 || (result = saveBasicState()) == null) {
            return null;
        }
        return new Fragment.SavedState(result);
    }

    private Bundle saveBasicState() {
        Bundle result = new Bundle();
        this.mFragment.performSaveInstanceState(result);
        this.mDispatcher.dispatchOnFragmentSaveInstanceState(this.mFragment, result, false);
        if (result.isEmpty()) {
            result = null;
        }
        if (this.mFragment.mView != null) {
            saveViewState();
        }
        if (this.mFragment.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, this.mFragment.mSavedViewState);
        }
        if (this.mFragment.mSavedViewRegistryState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBundle(VIEW_REGISTRY_STATE_TAG, this.mFragment.mSavedViewRegistryState);
        }
        if (!this.mFragment.mUserVisibleHint) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBoolean(USER_VISIBLE_HINT_TAG, this.mFragment.mUserVisibleHint);
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public void saveViewState() {
        if (this.mFragment.mView != null) {
            SparseArray<Parcelable> mStateArray = new SparseArray<>();
            this.mFragment.mView.saveHierarchyState(mStateArray);
            if (mStateArray.size() > 0) {
                this.mFragment.mSavedViewState = mStateArray;
            }
            Bundle outBundle = new Bundle();
            this.mFragment.mViewLifecycleOwner.performSave(outBundle);
            if (!outBundle.isEmpty()) {
                this.mFragment.mSavedViewRegistryState = outBundle;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void destroyFragmentView() {
        View view;
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom CREATE_VIEW: " + this.mFragment);
        }
        Fragment fragment = this.mFragment;
        ViewGroup viewGroup = fragment.mContainer;
        if (!(viewGroup == null || (view = fragment.mView) == null)) {
            viewGroup.removeView(view);
        }
        this.mFragment.performDestroyView();
        this.mDispatcher.dispatchOnFragmentViewDestroyed(this.mFragment, false);
        Fragment fragment2 = this.mFragment;
        fragment2.mContainer = null;
        fragment2.mView = null;
        fragment2.mViewLifecycleOwner = null;
        fragment2.mViewLifecycleOwnerLiveData.setValue(null);
        this.mFragment.mInLayout = false;
    }

    /* access modifiers changed from: package-private */
    public void destroy() {
        Fragment target;
        boolean shouldClear;
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom CREATED: " + this.mFragment);
        }
        Fragment fragment = this.mFragment;
        boolean beingRemoved = fragment.mRemoving && !fragment.isInBackStack();
        if (beingRemoved || this.mFragmentStore.getNonConfig().shouldDestroy(this.mFragment)) {
            FragmentHostCallback<?> host = this.mFragment.mHost;
            if (host instanceof ViewModelStoreOwner) {
                shouldClear = this.mFragmentStore.getNonConfig().isCleared();
            } else if (host.getContext() instanceof Activity) {
                shouldClear = true ^ ((Activity) host.getContext()).isChangingConfigurations();
            } else {
                shouldClear = true;
            }
            if (beingRemoved || shouldClear) {
                this.mFragmentStore.getNonConfig().clearNonConfigState(this.mFragment);
            }
            this.mFragment.performDestroy();
            this.mDispatcher.dispatchOnFragmentDestroyed(this.mFragment, false);
            for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
                if (fragmentStateManager != null) {
                    Fragment fragment2 = fragmentStateManager.getFragment();
                    if (this.mFragment.mWho.equals(fragment2.mTargetWho)) {
                        fragment2.mTarget = this.mFragment;
                        fragment2.mTargetWho = null;
                    }
                }
            }
            Fragment fragment3 = this.mFragment;
            String str = fragment3.mTargetWho;
            if (str != null) {
                fragment3.mTarget = this.mFragmentStore.findActiveFragment(str);
            }
            this.mFragmentStore.makeInactive(this);
            return;
        }
        String str2 = this.mFragment.mTargetWho;
        if (!(str2 == null || (target = this.mFragmentStore.findActiveFragment(str2)) == null || !target.mRetainInstance)) {
            this.mFragment.mTarget = target;
        }
        this.mFragment.mState = 0;
    }

    /* access modifiers changed from: package-private */
    public void detach() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom ATTACHED: " + this.mFragment);
        }
        this.mFragment.performDetach();
        boolean beingRemoved = false;
        this.mDispatcher.dispatchOnFragmentDetached(this.mFragment, false);
        Fragment fragment = this.mFragment;
        fragment.mState = -1;
        fragment.mHost = null;
        fragment.mParentFragment = null;
        fragment.mFragmentManager = null;
        if (fragment.mRemoving && !fragment.isInBackStack()) {
            beingRemoved = true;
        }
        if (beingRemoved || this.mFragmentStore.getNonConfig().shouldDestroy(this.mFragment)) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d(TAG, "initState called for fragment: " + this.mFragment);
            }
            this.mFragment.initState();
        }
    }

    /* access modifiers changed from: package-private */
    public void addViewToContainer() {
        int index = this.mFragmentStore.findFragmentIndexInContainer(this.mFragment);
        Fragment fragment = this.mFragment;
        fragment.mContainer.addView(fragment.mView, index);
    }
}
