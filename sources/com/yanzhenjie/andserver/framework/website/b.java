package com.yanzhenjie.andserver.framework.website;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.util.a;
import com.yanzhenjie.andserver.util.k;
import java.io.File;

/* compiled from: StorageWebsite */
public class b extends a implements k {
    private final String i;

    public b(@NonNull String rootPath) {
        this(rootPath, "index.html");
    }

    public b(@NonNull String rootPath, @NonNull String indexFileName) {
        super(indexFileName);
        a.b(!TextUtils.isEmpty(rootPath), "The rootPath cannot be empty.");
        a.b(rootPath.matches(k.c), "The format of [%s] is wrong, it should be like [/root/project].");
        this.i = rootPath;
    }

    public boolean a(@NonNull c request) {
        return i(request.getPath()) != null;
    }

    public String f(@NonNull c request) {
        File file = i(request.getPath());
        if (file == null) {
            return null;
        }
        return com.yanzhenjie.andserver.util.b.f(file.getAbsolutePath() + file.lastModified());
    }

    public long e(@NonNull c request) {
        File file = i(request.getPath());
        if (file != null) {
            return file.lastModified();
        }
        return -1;
    }

    private File i(@NonNull String httpPath) {
        File targetFile = new File(this.i, httpPath);
        if (targetFile.exists() && targetFile.isFile()) {
            return targetFile;
        }
        File indexFile = new File(targetFile, h());
        if (!indexFile.exists() || !indexFile.isFile()) {
            return null;
        }
        return indexFile;
    }
}
