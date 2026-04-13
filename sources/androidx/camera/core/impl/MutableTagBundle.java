package androidx.camera.core.impl;

import android.util.ArrayMap;
import androidx.annotation.NonNull;
import java.util.Map;

public class MutableTagBundle extends TagBundle {
    private MutableTagBundle(Map<String, Integer> source) {
        super(source);
    }

    @NonNull
    public static MutableTagBundle create() {
        return new MutableTagBundle(new ArrayMap());
    }

    @NonNull
    public static MutableTagBundle from(@NonNull TagBundle otherTagBundle) {
        Map<String, Integer> tags = new ArrayMap<>();
        for (String key : otherTagBundle.listKeys()) {
            tags.put(key, otherTagBundle.getTag(key));
        }
        return new MutableTagBundle(tags);
    }

    public void putTag(@NonNull String key, @NonNull Integer value) {
        this.mTagMap.put(key, value);
    }

    public void addTagBundle(@NonNull TagBundle bundle) {
        Map<String, Integer> map;
        Map<String, Integer> map2 = this.mTagMap;
        if (map2 != null && (map = bundle.mTagMap) != null) {
            map2.putAll(map);
        }
    }
}
