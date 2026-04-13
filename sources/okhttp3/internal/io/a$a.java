package okhttp3.internal.io;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.jvm.internal.k;
import okio.b0;
import okio.e0;
import okio.p;
import okio.q;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileSystem.kt */
public final class a$a implements b {
    a$a() {
    }

    @NotNull
    public e0 e(@NotNull File file) {
        k.f(file, "file");
        return p.k(file);
    }

    @NotNull
    public b0 f(@NotNull File file) {
        k.f(file, "file");
        try {
            return q.g(file, false, 1, (Object) null);
        } catch (FileNotFoundException e) {
            file.getParentFile().mkdirs();
            return q.g(file, false, 1, (Object) null);
        }
    }

    @NotNull
    public b0 c(@NotNull File file) {
        k.f(file, "file");
        try {
            return p.a(file);
        } catch (FileNotFoundException e) {
            file.getParentFile().mkdirs();
            return p.a(file);
        }
    }

    public void h(@NotNull File file) {
        k.f(file, "file");
        if (!file.delete() && file.exists()) {
            throw new IOException("failed to delete " + file);
        }
    }

    public boolean b(@NotNull File file) {
        k.f(file, "file");
        return file.exists();
    }

    public long d(@NotNull File file) {
        k.f(file, "file");
        return file.length();
    }

    public void g(@NotNull File from, @NotNull File to) {
        k.f(from, "from");
        k.f(to, TypedValues.TransitionType.S_TO);
        h(to);
        if (!from.renameTo(to)) {
            throw new IOException("failed to rename " + from + " to " + to);
        }
    }

    public void a(@NotNull File directory) {
        k.f(directory, "directory");
        File[] files = directory.listFiles();
        if (files != null) {
            int length = files.length;
            int i = 0;
            while (i < length) {
                File file = files[i];
                k.b(file, "file");
                if (file.isDirectory()) {
                    a(file);
                }
                if (file.delete()) {
                    i++;
                } else {
                    throw new IOException("failed to delete " + file);
                }
            }
            return;
        }
        throw new IOException("not a readable directory: " + directory);
    }

    @NotNull
    public String toString() {
        return "FileSystem.SYSTEM";
    }
}
