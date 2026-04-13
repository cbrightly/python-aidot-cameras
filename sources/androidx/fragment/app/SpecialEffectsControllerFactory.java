package androidx.fragment.app;

import android.view.ViewGroup;
import androidx.annotation.NonNull;

public interface SpecialEffectsControllerFactory {
    @NonNull
    SpecialEffectsController createController(@NonNull ViewGroup viewGroup);
}
