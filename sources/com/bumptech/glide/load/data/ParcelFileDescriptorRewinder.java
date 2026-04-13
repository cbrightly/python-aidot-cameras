package com.bumptech.glide.load.data;

import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.data.e;
import java.io.IOException;

public final class ParcelFileDescriptorRewinder implements e<ParcelFileDescriptor> {
    private final InternalRewinder a;

    public static boolean c() {
        return Build.VERSION.SDK_INT >= 21;
    }

    @RequiresApi(21)
    public ParcelFileDescriptorRewinder(ParcelFileDescriptor parcelFileDescriptor) {
        this.a = new InternalRewinder(parcelFileDescriptor);
    }

    @RequiresApi(21)
    @NonNull
    /* renamed from: d */
    public ParcelFileDescriptor a() {
        return this.a.rewind();
    }

    public void b() {
    }

    @RequiresApi(21)
    public static final class a implements e.a<ParcelFileDescriptor> {
        @NonNull
        /* renamed from: c */
        public e<ParcelFileDescriptor> b(@NonNull ParcelFileDescriptor parcelFileDescriptor) {
            return new ParcelFileDescriptorRewinder(parcelFileDescriptor);
        }

        @NonNull
        public Class<ParcelFileDescriptor> a() {
            return ParcelFileDescriptor.class;
        }
    }

    @RequiresApi(21)
    public static final class InternalRewinder {
        private final ParcelFileDescriptor a;

        InternalRewinder(ParcelFileDescriptor parcelFileDescriptor) {
            this.a = parcelFileDescriptor;
        }

        /* access modifiers changed from: package-private */
        public ParcelFileDescriptor rewind() {
            try {
                Os.lseek(this.a.getFileDescriptor(), 0, OsConstants.SEEK_SET);
                return this.a;
            } catch (ErrnoException e) {
                throw new IOException(e);
            }
        }
    }
}
