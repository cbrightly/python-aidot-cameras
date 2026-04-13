package androidx.camera.core.impl;

import android.util.ArrayMap;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class TagBundle {
    private static final TagBundle EMPTY_TAGBUNDLE = new TagBundle(new ArrayMap());
    protected final Map<String, Integer> mTagMap;

    protected TagBundle(@NonNull Map<String, Integer> tagMap) {
        this.mTagMap = tagMap;
    }

    @NonNull
    public static TagBundle emptyBundle() {
        return EMPTY_TAGBUNDLE;
    }

    @NonNull
    public static TagBundle create(@NonNull Pair<String, Integer> source) {
        Map<String, Integer> map = new ArrayMap<>();
        map.put((String) source.first, (Integer) source.second);
        return new TagBundle(map);
    }

    @NonNull
    public static TagBundle from(@NonNull TagBundle otherTagBundle) {
        Map<String, Integer> tags = new ArrayMap<>();
        for (String key : otherTagBundle.listKeys()) {
            tags.put(key, otherTagBundle.getTag(key));
        }
        return new TagBundle(tags);
    }

    @Nullable
    public Integer getTag(@NonNull String key) {
        return this.mTagMap.get(key);
    }

    @NonNull
    public Set<String> listKeys() {
        return this.mTagMap.keySet();
    }
}
