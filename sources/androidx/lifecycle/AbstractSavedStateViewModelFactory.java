package androidx.lifecycle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

public abstract class AbstractSavedStateViewModelFactory extends ViewModelProvider.OnRequeryFactory implements ViewModelProvider.Factory {
    static final String TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag";
    private Bundle mDefaultArgs;
    private Lifecycle mLifecycle;
    private SavedStateRegistry mSavedStateRegistry;

    /* access modifiers changed from: protected */
    @NonNull
    public abstract <T extends ViewModel> T create(@NonNull String str, @NonNull Class<T> cls, @NonNull SavedStateHandle savedStateHandle);

    public AbstractSavedStateViewModelFactory() {
    }

    @SuppressLint({"LambdaLast"})
    public AbstractSavedStateViewModelFactory(@NonNull SavedStateRegistryOwner owner, @Nullable Bundle defaultArgs) {
        this.mSavedStateRegistry = owner.getSavedStateRegistry();
        this.mLifecycle = owner.getLifecycle();
        this.mDefaultArgs = defaultArgs;
    }

    @NonNull
    public final <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        String key = (String) extras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (key == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        } else if (this.mSavedStateRegistry != null) {
            return create(key, modelClass);
        } else {
            return create(key, modelClass, SavedStateHandleSupport.createSavedStateHandle(extras));
        }
    }

    @NonNull
    private <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass) {
        SavedStateHandleController controller = LegacySavedStateHandleController.create(this.mSavedStateRegistry, this.mLifecycle, key, this.mDefaultArgs);
        T viewmodel = create(key, modelClass, controller.getHandle());
        viewmodel.setTagIfAbsent(TAG_SAVED_STATE_HANDLE_CONTROLLER, controller);
        return viewmodel;
    }

    @NonNull
    public final <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        } else if (this.mLifecycle != null) {
            return create(canonicalName, modelClass);
        } else {
            throw new UnsupportedOperationException("AbstractSavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onRequery(@NonNull ViewModel viewModel) {
        SavedStateRegistry savedStateRegistry = this.mSavedStateRegistry;
        if (savedStateRegistry != null) {
            LegacySavedStateHandleController.attachHandleIfNeeded(viewModel, savedStateRegistry, this.mLifecycle);
        }
    }
}
