package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public abstract class SpecialEffectsController {
    private final ViewGroup mContainer;
    boolean mIsContainerPostponed = false;
    boolean mOperationDirectionIsPop = false;
    final ArrayList<Operation> mPendingOperations = new ArrayList<>();
    final ArrayList<Operation> mRunningOperations = new ArrayList<>();

    /* access modifiers changed from: package-private */
    public abstract void executeOperations(@NonNull List<Operation> list, boolean z);

    @NonNull
    static SpecialEffectsController getOrCreateController(@NonNull ViewGroup container, @NonNull FragmentManager fragmentManager) {
        return getOrCreateController(container, fragmentManager.getSpecialEffectsControllerFactory());
    }

    @NonNull
    static SpecialEffectsController getOrCreateController(@NonNull ViewGroup container, @NonNull SpecialEffectsControllerFactory factory) {
        int i = R.id.special_effects_controller_view_tag;
        Object controller = container.getTag(i);
        if (controller instanceof SpecialEffectsController) {
            return (SpecialEffectsController) controller;
        }
        SpecialEffectsController newController = factory.createController(container);
        container.setTag(i, newController);
        return newController;
    }

    SpecialEffectsController(@NonNull ViewGroup container) {
        this.mContainer = container;
    }

    @NonNull
    public ViewGroup getContainer() {
        return this.mContainer;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Operation.LifecycleImpact getAwaitingCompletionLifecycleImpact(@NonNull FragmentStateManager fragmentStateManager) {
        Operation.LifecycleImpact lifecycleImpact = null;
        Operation pendingOperation = findPendingOperation(fragmentStateManager.getFragment());
        if (pendingOperation != null) {
            lifecycleImpact = pendingOperation.getLifecycleImpact();
        }
        Operation runningOperation = findRunningOperation(fragmentStateManager.getFragment());
        if (runningOperation == null || (lifecycleImpact != null && lifecycleImpact != Operation.LifecycleImpact.NONE)) {
            return lifecycleImpact;
        }
        return runningOperation.getLifecycleImpact();
    }

    @Nullable
    private Operation findPendingOperation(@NonNull Fragment fragment) {
        Iterator<Operation> it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            if (operation.getFragment().equals(fragment) && !operation.isCanceled()) {
                return operation;
            }
        }
        return null;
    }

    @Nullable
    private Operation findRunningOperation(@NonNull Fragment fragment) {
        Iterator<Operation> it = this.mRunningOperations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            if (operation.getFragment().equals(fragment) && !operation.isCanceled()) {
                return operation;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void enqueueAdd(@NonNull Operation.State finalState, @NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(finalState, Operation.LifecycleImpact.ADDING, fragmentStateManager);
    }

    /* access modifiers changed from: package-private */
    public void enqueueShow(@NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing show operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.VISIBLE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    /* access modifiers changed from: package-private */
    public void enqueueHide(@NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing hide operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.GONE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    /* access modifiers changed from: package-private */
    public void enqueueRemove(@NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing remove operation for fragment " + fragmentStateManager.getFragment());
        }
        enqueue(Operation.State.REMOVED, Operation.LifecycleImpact.REMOVING, fragmentStateManager);
    }

    private void enqueue(@NonNull Operation.State finalState, @NonNull Operation.LifecycleImpact lifecycleImpact, @NonNull FragmentStateManager fragmentStateManager) {
        synchronized (this.mPendingOperations) {
            CancellationSignal signal = new CancellationSignal();
            Operation existingOperation = findPendingOperation(fragmentStateManager.getFragment());
            if (existingOperation != null) {
                existingOperation.mergeWith(finalState, lifecycleImpact);
                return;
            }
            final FragmentStateManagerOperation operation = new FragmentStateManagerOperation(finalState, lifecycleImpact, fragmentStateManager, signal);
            this.mPendingOperations.add(operation);
            operation.addCompletionListener(new Runnable() {
                public void run() {
                    if (SpecialEffectsController.this.mPendingOperations.contains(operation)) {
                        operation.getFinalState().applyState(operation.getFragment().mView);
                    }
                }
            });
            operation.addCompletionListener(new Runnable() {
                public void run() {
                    SpecialEffectsController.this.mPendingOperations.remove(operation);
                    SpecialEffectsController.this.mRunningOperations.remove(operation);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void updateOperationDirection(boolean isPop) {
        this.mOperationDirectionIsPop = isPop;
    }

    /* access modifiers changed from: package-private */
    public void markPostponedState() {
        synchronized (this.mPendingOperations) {
            updateFinalState();
            this.mIsContainerPostponed = false;
            int index = this.mPendingOperations.size() - 1;
            while (true) {
                if (index < 0) {
                    break;
                }
                Operation operation = this.mPendingOperations.get(index);
                Operation.State currentState = Operation.State.from(operation.getFragment().mView);
                Operation.State finalState = operation.getFinalState();
                Operation.State state = Operation.State.VISIBLE;
                if (finalState == state && currentState != state) {
                    this.mIsContainerPostponed = operation.getFragment().isPostponed();
                    break;
                }
                index--;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void forcePostponedExecutePendingOperations() {
        if (this.mIsContainerPostponed) {
            this.mIsContainerPostponed = false;
            executePendingOperations();
        }
    }

    /* access modifiers changed from: package-private */
    public void executePendingOperations() {
        if (!this.mIsContainerPostponed) {
            if (!ViewCompat.isAttachedToWindow(this.mContainer)) {
                forceCompleteAllOperations();
                this.mOperationDirectionIsPop = false;
                return;
            }
            synchronized (this.mPendingOperations) {
                if (!this.mPendingOperations.isEmpty()) {
                    ArrayList<Operation> currentlyRunningOperations = new ArrayList<>(this.mRunningOperations);
                    this.mRunningOperations.clear();
                    Iterator<Operation> it = currentlyRunningOperations.iterator();
                    while (it.hasNext()) {
                        Operation operation = it.next();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + operation);
                        }
                        operation.cancel();
                        if (!operation.isComplete()) {
                            this.mRunningOperations.add(operation);
                        }
                    }
                    updateFinalState();
                    ArrayList<Operation> newPendingOperations = new ArrayList<>(this.mPendingOperations);
                    this.mPendingOperations.clear();
                    this.mRunningOperations.addAll(newPendingOperations);
                    Iterator<Operation> it2 = newPendingOperations.iterator();
                    while (it2.hasNext()) {
                        it2.next().onStart();
                    }
                    executeOperations(newPendingOperations, this.mOperationDirectionIsPop);
                    this.mOperationDirectionIsPop = false;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void forceCompleteAllOperations() {
        String str;
        String str2;
        boolean attachedToWindow = ViewCompat.isAttachedToWindow(this.mContainer);
        synchronized (this.mPendingOperations) {
            updateFinalState();
            Iterator<Operation> it = this.mPendingOperations.iterator();
            while (it.hasNext()) {
                it.next().onStart();
            }
            Iterator<Operation> it2 = new ArrayList<>(this.mRunningOperations).iterator();
            while (it2.hasNext()) {
                Operation operation = it2.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("SpecialEffectsController: ");
                    if (attachedToWindow) {
                        str2 = "";
                    } else {
                        str2 = "Container " + this.mContainer + " is not attached to window. ";
                    }
                    sb.append(str2);
                    sb.append("Cancelling running operation ");
                    sb.append(operation);
                    Log.v("FragmentManager", sb.toString());
                }
                operation.cancel();
            }
            Iterator<Operation> it3 = new ArrayList<>(this.mPendingOperations).iterator();
            while (it3.hasNext()) {
                Operation operation2 = it3.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("SpecialEffectsController: ");
                    if (attachedToWindow) {
                        str = "";
                    } else {
                        str = "Container " + this.mContainer + " is not attached to window. ";
                    }
                    sb2.append(str);
                    sb2.append("Cancelling pending operation ");
                    sb2.append(operation2);
                    Log.v("FragmentManager", sb2.toString());
                }
                operation2.cancel();
            }
        }
    }

    private void updateFinalState() {
        Iterator<Operation> it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            Operation operation = it.next();
            if (operation.getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                operation.mergeWith(Operation.State.from(operation.getFragment().requireView().getVisibility()), Operation.LifecycleImpact.NONE);
            }
        }
    }

    public static class Operation {
        @NonNull
        private final List<Runnable> mCompletionListeners = new ArrayList();
        @NonNull
        private State mFinalState;
        @NonNull
        private final Fragment mFragment;
        private boolean mIsCanceled = false;
        private boolean mIsComplete = false;
        @NonNull
        private LifecycleImpact mLifecycleImpact;
        @NonNull
        private final HashSet<CancellationSignal> mSpecialEffectsSignals = new HashSet<>();

        public enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING
        }

        public enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            @NonNull
            static State from(@NonNull View view) {
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    return INVISIBLE;
                }
                return from(view.getVisibility());
            }

            @NonNull
            static State from(int visibility) {
                switch (visibility) {
                    case 0:
                        return VISIBLE;
                    case 4:
                        return INVISIBLE;
                    case 8:
                        return GONE;
                    default:
                        throw new IllegalArgumentException("Unknown visibility " + visibility);
                }
            }

            /* access modifiers changed from: package-private */
            public void applyState(@NonNull View view) {
                switch (AnonymousClass3.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[ordinal()]) {
                    case 1:
                        ViewGroup parent = (ViewGroup) view.getParent();
                        if (parent != null) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "SpecialEffectsController: Removing view " + view + " from container " + parent);
                            }
                            parent.removeView(view);
                            return;
                        }
                        return;
                    case 2:
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to VISIBLE");
                        }
                        view.setVisibility(0);
                        return;
                    case 3:
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to GONE");
                        }
                        view.setVisibility(8);
                        return;
                    case 4:
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to INVISIBLE");
                        }
                        view.setVisibility(4);
                        return;
                    default:
                        return;
                }
            }
        }

        Operation(@NonNull State finalState, @NonNull LifecycleImpact lifecycleImpact, @NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            this.mFinalState = finalState;
            this.mLifecycleImpact = lifecycleImpact;
            this.mFragment = fragment;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                public void onCancel() {
                    Operation.this.cancel();
                }
            });
        }

        @NonNull
        public State getFinalState() {
            return this.mFinalState;
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public LifecycleImpact getLifecycleImpact() {
            return this.mLifecycleImpact;
        }

        @NonNull
        public final Fragment getFragment() {
            return this.mFragment;
        }

        /* access modifiers changed from: package-private */
        public final boolean isCanceled() {
            return this.mIsCanceled;
        }

        @NonNull
        public String toString() {
            return "Operation " + "{" + Integer.toHexString(System.identityHashCode(this)) + "} " + "{" + "mFinalState = " + this.mFinalState + "} " + "{" + "mLifecycleImpact = " + this.mLifecycleImpact + "} " + "{" + "mFragment = " + this.mFragment + "}";
        }

        /* access modifiers changed from: package-private */
        public final void cancel() {
            if (!isCanceled()) {
                this.mIsCanceled = true;
                if (this.mSpecialEffectsSignals.isEmpty()) {
                    complete();
                    return;
                }
                Iterator<CancellationSignal> it = new ArrayList<>(this.mSpecialEffectsSignals).iterator();
                while (it.hasNext()) {
                    it.next().cancel();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public final void mergeWith(@NonNull State finalState, @NonNull LifecycleImpact lifecycleImpact) {
            switch (AnonymousClass3.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact[lifecycleImpact.ordinal()]) {
                case 1:
                    if (this.mFinalState == State.REMOVED) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.mLifecycleImpact + " to ADDING.");
                        }
                        this.mFinalState = State.VISIBLE;
                        this.mLifecycleImpact = LifecycleImpact.ADDING;
                        return;
                    }
                    return;
                case 2:
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> REMOVED. mLifecycleImpact  = " + this.mLifecycleImpact + " to REMOVING.");
                    }
                    this.mFinalState = State.REMOVED;
                    this.mLifecycleImpact = LifecycleImpact.REMOVING;
                    return;
                case 3:
                    if (this.mFinalState != State.REMOVED) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> " + finalState + ". ");
                        }
                        this.mFinalState = finalState;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: package-private */
        public final void addCompletionListener(@NonNull Runnable listener) {
            this.mCompletionListeners.add(listener);
        }

        /* access modifiers changed from: package-private */
        public void onStart() {
        }

        public final void markStartedSpecialEffect(@NonNull CancellationSignal signal) {
            onStart();
            this.mSpecialEffectsSignals.add(signal);
        }

        public final void completeSpecialEffect(@NonNull CancellationSignal signal) {
            if (this.mSpecialEffectsSignals.remove(signal) && this.mSpecialEffectsSignals.isEmpty()) {
                complete();
            }
        }

        /* access modifiers changed from: package-private */
        public final boolean isComplete() {
            return this.mIsComplete;
        }

        @CallSuper
        public void complete() {
            if (!this.mIsComplete) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
                }
                this.mIsComplete = true;
                for (Runnable listener : this.mCompletionListeners) {
                    listener.run();
                }
            }
        }
    }

    /* renamed from: androidx.fragment.app.SpecialEffectsController$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact;
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[Operation.LifecycleImpact.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact = iArr;
            try {
                iArr[Operation.LifecycleImpact.ADDING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact[Operation.LifecycleImpact.REMOVING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact[Operation.LifecycleImpact.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr2;
            try {
                iArr2[Operation.State.REMOVED.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[Operation.State.VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[Operation.State.GONE.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[Operation.State.INVISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static class FragmentStateManagerOperation extends Operation {
        @NonNull
        private final FragmentStateManager mFragmentStateManager;

        FragmentStateManagerOperation(@NonNull Operation.State finalState, @NonNull Operation.LifecycleImpact lifecycleImpact, @NonNull FragmentStateManager fragmentStateManager, @NonNull CancellationSignal cancellationSignal) {
            super(finalState, lifecycleImpact, fragmentStateManager.getFragment(), cancellationSignal);
            this.mFragmentStateManager = fragmentStateManager;
        }

        /* access modifiers changed from: package-private */
        public void onStart() {
            if (getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                Fragment fragment = this.mFragmentStateManager.getFragment();
                View focusedView = fragment.mView.findFocus();
                if (focusedView != null) {
                    fragment.setFocusedView(focusedView);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "requestFocus: Saved focused view " + focusedView + " for Fragment " + fragment);
                    }
                }
                View view = getFragment().requireView();
                if (view.getParent() == null) {
                    this.mFragmentStateManager.addViewToContainer();
                    view.setAlpha(0.0f);
                }
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    view.setVisibility(4);
                }
                view.setAlpha(fragment.getPostOnViewCreatedAlpha());
            }
        }

        public void complete() {
            super.complete();
            this.mFragmentStateManager.moveToExpectedState();
        }
    }
}
