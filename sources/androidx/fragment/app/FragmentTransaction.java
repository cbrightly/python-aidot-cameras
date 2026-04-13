package androidx.fragment.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public abstract class FragmentTransaction {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SET_MAX_LIFECYCLE = 10;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_SHOW = 5;
    static final int OP_UNSET_PRIMARY_NAV = 9;
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    private final ClassLoader mClassLoader;
    ArrayList<Runnable> mCommitRunnables;
    int mEnterAnim;
    int mExitAnim;
    private final FragmentFactory mFragmentFactory;
    @Nullable
    String mName;
    ArrayList<Op> mOps;
    int mPopEnterAnim;
    int mPopExitAnim;
    boolean mReorderingAllowed;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    public abstract void commitNow();

    public abstract void commitNowAllowingStateLoss();

    public static final class Op {
        int mCmd;
        Lifecycle.State mCurrentMaxState;
        int mEnterAnim;
        int mExitAnim;
        Fragment mFragment;
        Lifecycle.State mOldMaxState;
        int mPopEnterAnim;
        int mPopExitAnim;

        Op() {
        }

        Op(int cmd, Fragment fragment) {
            this.mCmd = cmd;
            this.mFragment = fragment;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            this.mOldMaxState = state;
            this.mCurrentMaxState = state;
        }

        Op(int cmd, @NonNull Fragment fragment, Lifecycle.State state) {
            this.mCmd = cmd;
            this.mFragment = fragment;
            this.mOldMaxState = fragment.mMaxState;
            this.mCurrentMaxState = state;
        }
    }

    @Deprecated
    public FragmentTransaction() {
        this.mOps = new ArrayList<>();
        this.mAllowAddToBackStack = true;
        this.mReorderingAllowed = false;
        this.mFragmentFactory = null;
        this.mClassLoader = null;
    }

    FragmentTransaction(@NonNull FragmentFactory fragmentFactory, @Nullable ClassLoader classLoader) {
        this.mOps = new ArrayList<>();
        this.mAllowAddToBackStack = true;
        this.mReorderingAllowed = false;
        this.mFragmentFactory = fragmentFactory;
        this.mClassLoader = classLoader;
    }

    /* access modifiers changed from: package-private */
    public void addOp(Op op) {
        this.mOps.add(op);
        op.mEnterAnim = this.mEnterAnim;
        op.mExitAnim = this.mExitAnim;
        op.mPopEnterAnim = this.mPopEnterAnim;
        op.mPopExitAnim = this.mPopExitAnim;
    }

    @NonNull
    private Fragment createFragment(@NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle args) {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            ClassLoader classLoader = this.mClassLoader;
            if (classLoader != null) {
                Fragment fragment = fragmentFactory.instantiate(classLoader, fragmentClass.getName());
                if (args != null) {
                    fragment.setArguments(args);
                }
                return fragment;
            }
            throw new IllegalStateException("The FragmentManager must be attached to itshost to create a Fragment");
        }
        throw new IllegalStateException("Creating a Fragment requires that this FragmentTransaction was built with FragmentManager.beginTransaction()");
    }

    @NonNull
    public final FragmentTransaction add(@NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle args, @Nullable String tag) {
        return add(createFragment(fragmentClass, args), tag);
    }

    @NonNull
    public FragmentTransaction add(@NonNull Fragment fragment, @Nullable String tag) {
        doAddOp(0, fragment, tag, 1);
        return this;
    }

    @NonNull
    public final FragmentTransaction add(@IdRes int containerViewId, @NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle args) {
        return add(containerViewId, createFragment(fragmentClass, args));
    }

    @NonNull
    public FragmentTransaction add(@IdRes int containerViewId, @NonNull Fragment fragment) {
        doAddOp(containerViewId, fragment, (String) null, 1);
        return this;
    }

    @NonNull
    public final FragmentTransaction add(@IdRes int containerViewId, @NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle args, @Nullable String tag) {
        return add(containerViewId, createFragment(fragmentClass, args), tag);
    }

    @NonNull
    public FragmentTransaction add(@IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
        doAddOp(containerViewId, fragment, tag, 1);
        return this;
    }

    /* access modifiers changed from: package-private */
    public FragmentTransaction add(@NonNull ViewGroup container, @NonNull Fragment fragment, @Nullable String tag) {
        fragment.mContainer = container;
        return add(container.getId(), fragment, tag);
    }

    /* access modifiers changed from: package-private */
    public void doAddOp(int containerViewId, Fragment fragment, @Nullable String tag, int opcmd) {
        Class<?> fragmentClass = fragment.getClass();
        int modifiers = fragmentClass.getModifiers();
        if (fragmentClass.isAnonymousClass() || !Modifier.isPublic(modifiers) || (fragmentClass.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + fragmentClass.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (tag != null) {
            String str = fragment.mTag;
            if (str == null || tag.equals(str)) {
                fragment.mTag = tag;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + tag);
            }
        }
        if (containerViewId != 0) {
            if (containerViewId != -1) {
                int i = fragment.mFragmentId;
                if (i == 0 || i == containerViewId) {
                    fragment.mFragmentId = containerViewId;
                    fragment.mContainerId = containerViewId;
                } else {
                    throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + containerViewId);
                }
            } else {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + tag + " to container view with no id");
            }
        }
        addOp(new Op(opcmd, fragment));
    }

    @NonNull
    public final FragmentTransaction replace(@IdRes int containerViewId, @NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle args) {
        return replace(containerViewId, fragmentClass, args, (String) null);
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int containerViewId, @NonNull Fragment fragment) {
        return replace(containerViewId, fragment, (String) null);
    }

    @NonNull
    public final FragmentTransaction replace(@IdRes int containerViewId, @NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle args, @Nullable String tag) {
        return replace(containerViewId, createFragment(fragmentClass, args), tag);
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
        if (containerViewId != 0) {
            doAddOp(containerViewId, fragment, tag, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    @NonNull
    public FragmentTransaction remove(@NonNull Fragment fragment) {
        addOp(new Op(3, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction hide(@NonNull Fragment fragment) {
        addOp(new Op(4, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction show(@NonNull Fragment fragment) {
        addOp(new Op(5, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction detach(@NonNull Fragment fragment) {
        addOp(new Op(6, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction attach(@NonNull Fragment fragment) {
        addOp(new Op(7, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        addOp(new Op(8, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        addOp(new Op(10, fragment, state));
        return this;
    }

    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int enter, @AnimRes @AnimatorRes int exit) {
        return setCustomAnimations(enter, exit, 0, 0);
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int enter, @AnimRes @AnimatorRes int exit, @AnimRes @AnimatorRes int popEnter, @AnimRes @AnimatorRes int popExit) {
        this.mEnterAnim = enter;
        this.mExitAnim = exit;
        this.mPopEnterAnim = popEnter;
        this.mPopExitAnim = popExit;
        return this;
    }

    @NonNull
    public FragmentTransaction addSharedElement(@NonNull View sharedElement, @NonNull String name) {
        if (FragmentTransition.supportsTransition()) {
            String transitionName = ViewCompat.getTransitionName(sharedElement);
            if (transitionName != null) {
                if (this.mSharedElementSourceNames == null) {
                    this.mSharedElementSourceNames = new ArrayList<>();
                    this.mSharedElementTargetNames = new ArrayList<>();
                } else if (this.mSharedElementTargetNames.contains(name)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + name + "' has already been added to the transaction.");
                } else if (this.mSharedElementSourceNames.contains(transitionName)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + transitionName + "' has already been added to the transaction.");
                }
                this.mSharedElementSourceNames.add(transitionName);
                this.mSharedElementTargetNames.add(name);
            } else {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
        }
        return this;
    }

    @NonNull
    public FragmentTransaction setTransition(int transition) {
        this.mTransition = transition;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setTransitionStyle(@StyleRes int styleRes) {
        return this;
    }

    @NonNull
    public FragmentTransaction addToBackStack(@Nullable String name) {
        if (this.mAllowAddToBackStack) {
            this.mAddToBackStack = true;
            this.mName = name;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @NonNull
    public FragmentTransaction disallowAddToBackStack() {
        if (!this.mAddToBackStack) {
            this.mAllowAddToBackStack = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbTitle(@StringRes int res) {
        this.mBreadCrumbTitleRes = res;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbTitle(@Nullable CharSequence text) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = text;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbShortTitle(@StringRes int res) {
        this.mBreadCrumbShortTitleRes = res;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbShortTitle(@Nullable CharSequence text) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = text;
        return this;
    }

    @NonNull
    public FragmentTransaction setReorderingAllowed(boolean reorderingAllowed) {
        this.mReorderingAllowed = reorderingAllowed;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setAllowOptimization(boolean allowOptimization) {
        return setReorderingAllowed(allowOptimization);
    }

    @NonNull
    public FragmentTransaction runOnCommit(@NonNull Runnable runnable) {
        disallowAddToBackStack();
        if (this.mCommitRunnables == null) {
            this.mCommitRunnables = new ArrayList<>();
        }
        this.mCommitRunnables.add(runnable);
        return this;
    }
}
