package com.yanzhenjie.andserver.http.multipart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;

/* compiled from: MultipartFile */
public interface b {
    @Nullable
    String getFilename();

    void transferTo(@NonNull File file);
}
