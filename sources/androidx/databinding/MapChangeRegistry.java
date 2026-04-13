package androidx.databinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.CallbackRegistry;
import androidx.databinding.ObservableMap;

public class MapChangeRegistry extends CallbackRegistry<ObservableMap.OnMapChangedCallback, ObservableMap, Object> {
    private static CallbackRegistry.NotifierCallback<ObservableMap.OnMapChangedCallback, ObservableMap, Object> NOTIFIER_CALLBACK = new CallbackRegistry.NotifierCallback<ObservableMap.OnMapChangedCallback, ObservableMap, Object>() {
        public void onNotifyCallback(ObservableMap.OnMapChangedCallback callback, ObservableMap sender, int arg, Object arg2) {
            callback.onMapChanged(sender, arg2);
        }
    };

    public MapChangeRegistry() {
        super(NOTIFIER_CALLBACK);
    }

    public void notifyChange(@NonNull ObservableMap sender, @Nullable Object key) {
        notifyCallbacks(sender, 0, key);
    }
}
