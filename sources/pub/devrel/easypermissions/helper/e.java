package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.List;
import pub.devrel.easypermissions.b;

/* compiled from: PermissionHelper */
public abstract class e<T> {
    private T a;

    public abstract void a(int i, @NonNull String... strArr);

    public abstract Context b();

    public abstract boolean i(@NonNull String str);

    public abstract void j(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i, int i2, b bVar, @NonNull String... strArr);

    @NonNull
    public static e<? extends Activity> d(Activity host) {
        if (Build.VERSION.SDK_INT < 23) {
            return new d(host);
        }
        if (host instanceof AppCompatActivity) {
            return new b((AppCompatActivity) host);
        }
        return new a(host);
    }

    @NonNull
    public static e<Fragment> e(Fragment host) {
        if (Build.VERSION.SDK_INT < 23) {
            return new d(host);
        }
        return new f(host);
    }

    public e(@NonNull T host) {
        this.a = host;
    }

    private boolean h(@NonNull String... perms) {
        for (String perm : perms) {
            if (i(perm)) {
                return true;
            }
        }
        return false;
    }

    public void g(@NonNull String rationale, @NonNull String positiveButton, @NonNull String negativeButton, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String... perms) {
        if (!h(perms)) {
            a(requestCode, perms);
        } else if (TextUtils.isEmpty(rationale) || (dialogStrategy != null && !dialogStrategy.c())) {
            a(requestCode, perms);
        } else {
            j(rationale, positiveButton, negativeButton, theme, requestCode, dialogStrategy, perms);
        }
    }

    public boolean l(@NonNull List<String> perms) {
        for (String deniedPermission : perms) {
            if (f(deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    public boolean f(@NonNull String perms) {
        return !i(perms);
    }

    public boolean k(@NonNull String... perms) {
        return h(perms);
    }

    @NonNull
    public T c() {
        return this.a;
    }
}
