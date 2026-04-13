package com.google.android.gms.maps.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public interface TileProvider {
    @NonNull
    public static final Tile NO_TILE = new Tile(-1, -1, (byte[]) null);

    @Nullable
    Tile getTile(int i, int i2, int i3);
}
