package androidx.camera.core.impl;

import android.content.Context;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CameraDeviceSurfaceManager {

    public interface Provider {
        @NonNull
        CameraDeviceSurfaceManager newInstance(@NonNull Context context, @Nullable Object obj, @NonNull Set<String> set);
    }

    boolean checkSupported(String str, List<SurfaceConfig> list);

    @NonNull
    Map<UseCaseConfig<?>, Size> getSuggestedResolutions(@NonNull String str, @NonNull List<SurfaceConfig> list, @NonNull List<UseCaseConfig<?>> list2);

    SurfaceConfig transformSurfaceConfig(String str, int i, Size size);
}
