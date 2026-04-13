package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.e;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

public class FragmentViewLifecycleOwner implements HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, ViewModelStoreOwner {
    private ViewModelProvider.Factory mDefaultFactory;
    private final Fragment mFragment;
    private LifecycleRegistry mLifecycleRegistry = null;
    private SavedStateRegistryController mSavedStateRegistryController = null;
    private final ViewModelStore mViewModelStore;

    public /* synthetic */ CreationExtras getDefaultViewModelCreationExtras() {
        return e.a(this);
    }

    FragmentViewLifecycleOwner(@NonNull Fragment fragment, @NonNull ViewModelStore viewModelStore) {
        this.mFragment = fragment;
        this.mViewModelStore = viewModelStore;
    }

    @NonNull
    public ViewModelStore getViewModelStore() {
        initialize();
        return this.mViewModelStore;
    }

    /* access modifiers changed from: package-private */
    public void initialize() {
        if (this.mLifecycleRegistry == null) {
            this.mLifecycleRegistry = new LifecycleRegistry(this);
            this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInitialized() {
        return this.mLifecycleRegistry != null;
    }

    @NonNull
    public Lifecycle getLifecycle() {
        initialize();
        return this.mLifecycleRegistry;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentState(@NonNull Lifecycle.State state) {
        this.mLifecycleRegistry.setCurrentState(state);
    }

    /* access modifiers changed from: package-private */
    public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        this.mLifecycleRegistry.handleLifecycleEvent(event);
    }

    @NonNull
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        ViewModelProvider.Factory currentFactory = this.mFragment.getDefaultViewModelProviderFactory();
        if (!currentFactory.equals(this.mFragment.mDefaultFactory)) {
            this.mDefaultFactory = currentFactory;
            return currentFactory;
        }
        if (this.mDefaultFactory == null) {
            Application application = null;
            Context appContext = this.mFragment.requireContext().getApplicationContext();
            while (true) {
                if (!(appContext instanceof ContextWrapper)) {
                    break;
                } else if (appContext instanceof Application) {
                    application = (Application) appContext;
                    break;
                } else {
                    appContext = ((ContextWrapper) appContext).getBaseContext();
                }
            }
            this.mDefaultFactory = new SavedStateViewModelFactory(application, this, this.mFragment.getArguments());
        }
        return this.mDefaultFactory;
    }

    @NonNull
    public SavedStateRegistry getSavedStateRegistry() {
        initialize();
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    /* access modifiers changed from: package-private */
    public void performRestore(@Nullable Bundle savedState) {
        this.mSavedStateRegistryController.performRestore(savedState);
    }

    /* access modifiers changed from: package-private */
    public void performSave(@NonNull Bundle outBundle) {
        this.mSavedStateRegistryController.performSave(outBundle);
    }
}
