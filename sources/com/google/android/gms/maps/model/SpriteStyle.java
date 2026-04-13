package com.google.android.gms.maps.model;

import androidx.annotation.NonNull;
import com.google.android.gms.maps.model.StampStyle;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class SpriteStyle extends StampStyle {

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public static final class Builder extends StampStyle.Builder<Builder> {
        private Builder() {
        }

        /* synthetic */ Builder(zzn zzn) {
        }

        @NonNull
        public SpriteStyle build() {
            return new SpriteStyle(this.zza);
        }

        /* access modifiers changed from: protected */
        @NonNull
        public Builder self() {
            return this;
        }
    }

    public SpriteStyle(@NonNull BitmapDescriptor stamp) {
        super(stamp);
    }

    @NonNull
    public static Builder newBuilder(@NonNull BitmapDescriptor stamp) {
        return (Builder) new Builder((zzn) null).stamp(stamp);
    }
}
