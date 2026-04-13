package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

public final class zzbg implements Channel.GetInputStreamResult {
    private final InputStream zzct;
    private final Status zzp;

    zzbg(Status status, @Nullable InputStream inputStream) {
        this.zzp = (Status) Preconditions.checkNotNull(status);
        this.zzct = inputStream;
    }

    public final Status getStatus() {
        return this.zzp;
    }

    @Nullable
    public final InputStream getInputStream() {
        return this.zzct;
    }

    public final void release() {
        InputStream inputStream = this.zzct;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }
}
