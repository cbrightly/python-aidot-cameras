package pub.devrel.easypermissions;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.Size;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import pub.devrel.easypermissions.helper.e;

/* compiled from: PermissionRequest */
public final class c {
    private final e a;
    private final String[] b;
    private final int c;
    private final String d;
    private final String e;
    private final String f;
    private final int g;
    private b h;

    private c(e helper, String[] perms, int requestCode, String rationale, String positiveButtonText, String negativeButtonText, int theme, b dialogStrategy) {
        this.a = helper;
        this.b = (String[]) perms.clone();
        this.c = requestCode;
        this.d = rationale;
        this.e = positiveButtonText;
        this.f = negativeButtonText;
        this.g = theme;
        this.h = dialogStrategy;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public e b() {
        return this.a;
    }

    @NonNull
    public String[] d() {
        return (String[]) this.b.clone();
    }

    public int g() {
        return this.c;
    }

    @NonNull
    public String f() {
        return this.d;
    }

    @NonNull
    public String e() {
        return this.e;
    }

    @NonNull
    public String c() {
        return this.f;
    }

    @StyleRes
    public int h() {
        return this.g;
    }

    public b a() {
        return this.h;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        c request = (c) o;
        if (!Arrays.equals(this.b, request.b) || this.c != request.c) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (Arrays.hashCode(this.b) * 31) + this.c;
    }

    public String toString() {
        return "PermissionRequest{mHelper=" + this.a + ", mPerms=" + Arrays.toString(this.b) + ", mRequestCode=" + this.c + ", mRationale='" + this.d + '\'' + ", mPositiveButtonText='" + this.e + '\'' + ", mNegativeButtonText='" + this.f + '\'' + ", mTheme=" + this.g + '}';
    }

    /* compiled from: PermissionRequest */
    public static final class b {
        private final e a;
        private final int b;
        private final String[] c;
        private String d;
        private String e;
        private String f;
        private int g = -1;
        private b h;

        public b(@NonNull Activity activity, int requestCode, @Size(min = 1) @NonNull String... perms) {
            this.a = e.d(activity);
            this.b = requestCode;
            this.c = perms;
        }

        public b(@NonNull Fragment fragment, int requestCode, @Size(min = 1) @NonNull String... perms) {
            this.a = e.e(fragment);
            this.b = requestCode;
            this.c = perms;
        }

        @NonNull
        public b g(@Nullable String rationale) {
            this.d = rationale;
            return this;
        }

        @NonNull
        public b f(@StringRes int resId) {
            this.d = this.a.b().getString(resId);
            return this;
        }

        @NonNull
        public b e(@Nullable String positiveButtonText) {
            this.e = positiveButtonText;
            return this;
        }

        @NonNull
        public b d(@StringRes int resId) {
            this.e = this.a.b().getString(resId);
            return this;
        }

        @NonNull
        public b c(@Nullable String negativeButtonText) {
            this.f = negativeButtonText;
            return this;
        }

        @NonNull
        public b b(@StringRes int resId) {
            this.f = this.a.b().getString(resId);
            return this;
        }

        @NonNull
        public b h(@StyleRes int theme) {
            this.g = theme;
            return this;
        }

        @NonNull
        public c a() {
            if (this.d == null) {
                this.d = this.a.b().getString(R$string.rationale_ask);
            }
            if (this.e == null) {
                this.e = this.a.b().getString(17039370);
            }
            if (this.f == null) {
                this.f = this.a.b().getString(17039360);
            }
            if (this.h == null) {
                try {
                    this.h = (b) Class.forName("com.leedarson.base.views.f").newInstance();
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                } catch (IllegalAccessException e3) {
                    e3.printStackTrace();
                } catch (InstantiationException e4) {
                    e4.printStackTrace();
                }
            }
            return new c(this.a, this.c, this.b, this.d, this.e, this.f, this.g, this.h);
        }
    }
}
