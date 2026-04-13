package com.bumptech.glide.load.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.d;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.i;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: StreamEncoder */
public class t implements d<InputStream> {
    private final b a;

    public t(b byteArrayPool) {
        this.a = byteArrayPool;
    }

    /* renamed from: c */
    public boolean a(@NonNull InputStream data, @NonNull File file, @NonNull i options) {
        byte[] buffer = (byte[]) this.a.e(65536, byte[].class);
        boolean success = false;
        OutputStream os = null;
        try {
            OutputStream os2 = new FileOutputStream(file);
            while (true) {
                int read = data.read(buffer);
                int read2 = read;
                if (read == -1) {
                    break;
                }
                os2.write(buffer, 0, read2);
            }
            os2.close();
            success = true;
            try {
                os2.close();
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            if (Log.isLoggable("StreamEncoder", 3)) {
                Log.d("StreamEncoder", "Failed to encode data onto the OutputStream", e2);
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e3) {
                }
            }
        } catch (Throwable th) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e4) {
                }
            }
            this.a.put(buffer);
            throw th;
        }
        this.a.put(buffer);
        return success;
    }
}
