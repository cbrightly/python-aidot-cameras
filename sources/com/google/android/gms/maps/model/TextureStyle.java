package com.google.android.gms.maps.model;

import androidx.annotation.NonNull;
import com.google.android.gms.maps.model.StampStyle;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class TextureStyle extends StampStyle {

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static final class Builder extends StampStyle.Builder<Builder> {
        private Builder() {
        }

        /* synthetic */ Builder(zzx zzx) {
        }

        @NonNull
        public TextureStyle build() {
            return new TextureStyle(this.zza, (zzy) null);
        }

        /* access modifiers changed from: protected */
        @NonNull
        public Builder self() {
            return this;
        }
    }

    /* synthetic */ TextureStyle(BitmapDescriptor bitmapDescriptor, zzy zzy) {
        super(bitmapDescriptor);
    }

    @NonNull
    public static Builder newBuilder(@NonNull BitmapDescriptor stamp) {
        return (Builder) new Builder((zzx) null).stamp(stamp);
    }
}
