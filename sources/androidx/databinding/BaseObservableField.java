package androidx.databinding;

import androidx.databinding.Observable;

public abstract class BaseObservableField extends BaseObservable {
    public BaseObservableField() {
    }

    public BaseObservableField(Observable... dependencies) {
        if (dependencies != null && dependencies.length != 0) {
            DependencyCallback callback = new DependencyCallback();
            for (Observable addOnPropertyChangedCallback : dependencies) {
                addOnPropertyChangedCallback.addOnPropertyChangedCallback(callback);
            }
        }
    }

    public class DependencyCallback extends Observable.OnPropertyChangedCallback {
        DependencyCallback() {
        }

        public void onPropertyChanged(Observable sender, int propertyId) {
            BaseObservableField.this.notifyChange();
        }
    }
}
