package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import net.sqlcipher.database.SQLiteDatabase;

public class AppSettingsDialog implements Parcelable {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Parcelable.Creator<AppSettingsDialog> CREATOR = new a();
    private Context a1;
    @StyleRes
    private final int c;
    private final String d;
    private final String f;
    private Object p0;
    private final String q;
    private final String x;
    private final int y;
    private final int z;

    /* synthetic */ AppSettingsDialog(Parcel x0, a x1) {
        this(x0);
    }

    /* synthetic */ AppSettingsDialog(Object x0, int x1, String x2, String x3, String x4, String x5, int x6, int x7, a x8) {
        this(x0, x1, x2, x3, x4, x5, x6, x7);
    }

    public class a implements Parcelable.Creator<AppSettingsDialog> {
        a() {
        }

        /* renamed from: a */
        public AppSettingsDialog createFromParcel(Parcel in) {
            return new AppSettingsDialog(in, (a) null);
        }

        /* renamed from: b */
        public AppSettingsDialog[] newArray(int size) {
            return new AppSettingsDialog[size];
        }
    }

    private AppSettingsDialog(Parcel in) {
        this.c = in.readInt();
        this.d = in.readString();
        this.f = in.readString();
        this.q = in.readString();
        this.x = in.readString();
        this.y = in.readInt();
        this.z = in.readInt();
    }

    private AppSettingsDialog(@NonNull Object activityOrFragment, @StyleRes int themeResId, @Nullable String rationale, @Nullable String title, @Nullable String positiveButtonText, @Nullable String negativeButtonText, int requestCode, int intentFlags) {
        c(activityOrFragment);
        this.c = themeResId;
        this.d = rationale;
        this.f = title;
        this.q = positiveButtonText;
        this.x = negativeButtonText;
        this.y = requestCode;
        this.z = intentFlags;
    }

    static AppSettingsDialog a(Intent intent, Activity activity) {
        AppSettingsDialog dialog = (AppSettingsDialog) intent.getParcelableExtra("extra_app_settings");
        if (dialog == null) {
            Log.e("EasyPermissions", "Intent contains null value for EXTRA_APP_SETTINGS: intent=" + intent + ", extras=" + intent.getExtras());
            dialog = new b(activity).a();
        }
        dialog.c(activity);
        return dialog;
    }

    private void c(Object activityOrFragment) {
        this.p0 = activityOrFragment;
        if (activityOrFragment instanceof Activity) {
            this.a1 = (Activity) activityOrFragment;
        } else if (activityOrFragment instanceof Fragment) {
            this.a1 = ((Fragment) activityOrFragment).getContext();
        } else {
            throw new IllegalStateException("Unknown object: " + activityOrFragment);
        }
    }

    /* access modifiers changed from: package-private */
    public AlertDialog d(DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder;
        int i = this.c;
        if (i != -1) {
            builder = new AlertDialog.Builder(this.a1, i);
        } else {
            builder = new AlertDialog.Builder(this.a1);
        }
        return builder.setCancelable(false).setTitle((CharSequence) this.f).setMessage((CharSequence) this.d).setPositiveButton((CharSequence) this.q, positiveListener).setNegativeButton((CharSequence) this.x, negativeListener).show();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.c);
        dest.writeString(this.d);
        dest.writeString(this.f);
        dest.writeString(this.q);
        dest.writeString(this.x);
        dest.writeInt(this.y);
        dest.writeInt(this.z);
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.z;
    }

    public static class b {
        private final Object a;
        private final Context b;
        @StyleRes
        private int c = -1;
        private String d;
        private String e;
        private String f;
        private String g;
        private int h = -1;
        private boolean i = false;

        public b(@NonNull Activity activity) {
            this.a = activity;
            this.b = activity;
        }

        @NonNull
        public AppSettingsDialog a() {
            this.d = TextUtils.isEmpty(this.d) ? this.b.getString(R$string.rationale_ask_again) : this.d;
            this.e = TextUtils.isEmpty(this.e) ? this.b.getString(R$string.title_settings_dialog) : this.e;
            this.f = TextUtils.isEmpty(this.f) ? this.b.getString(17039370) : this.f;
            this.g = TextUtils.isEmpty(this.g) ? this.b.getString(17039360) : this.g;
            int i2 = this.h;
            if (i2 <= 0) {
                i2 = 16061;
            }
            this.h = i2;
            int intentFlags = 0;
            if (this.i) {
                intentFlags = 0 | SQLiteDatabase.CREATE_IF_NECESSARY;
            }
            return new AppSettingsDialog(this.a, this.c, this.d, this.e, this.f, this.g, this.h, intentFlags, (a) null);
        }
    }
}
