package gdut.bsx.share2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.utils.IntentUtils;
import com.yanzhenjie.andserver.util.h;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: Share2 */
public class a {
    private Activity a;
    private String b;
    private String c;
    private Uri d;
    private String e;
    private String f;
    private String g;
    private int h;
    private boolean i;

    private a(@NonNull b builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.f;
        this.e = builder.g;
        this.f = builder.d;
        this.g = builder.e;
        this.h = builder.h;
        this.i = builder.i;
    }

    public void c() {
        if (a()) {
            Intent shareIntent = b();
            if (shareIntent == null) {
                Log.e("Share2", "shareBySystem cancel.");
                return;
            }
            if (this.c == null) {
                this.c = "";
            }
            if (this.i) {
                shareIntent = Intent.createChooser(shareIntent, this.c);
            }
            if (shareIntent.resolveActivity(this.a.getPackageManager()) != null) {
                try {
                    int i2 = this.h;
                    if (i2 != -1) {
                        this.a.startActivityForResult(shareIntent, i2);
                    } else {
                        this.a.startActivity(shareIntent);
                    }
                } catch (Exception e2) {
                    Log.e("Share2", Log.getStackTraceString(e2));
                }
            }
        }
    }

    private Intent b() {
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        shareIntent.addCategory("android.intent.category.DEFAULT");
        if (!TextUtils.isEmpty(this.f) && !TextUtils.isEmpty(this.g)) {
            shareIntent.setComponent(new ComponentName(this.f, this.g));
        }
        String str = this.b;
        char c2 = 65535;
        switch (str.hashCode()) {
            case -661257167:
                if (str.equals("audio/*")) {
                    c2 = 2;
                    break;
                }
                break;
            case 41861:
                if (str.equals(h.ALL_VALUE)) {
                    c2 = 4;
                    break;
                }
                break;
            case 452781974:
                if (str.equals(IntentUtils.TYPE_VIDEO)) {
                    c2 = 3;
                    break;
                }
                break;
            case 817335912:
                if (str.equals(h.TEXT_PLAIN_VALUE)) {
                    c2 = 0;
                    break;
                }
                break;
            case 1911932022:
                if (str.equals(IntentUtils.TYPE_IMAGE)) {
                    c2 = 1;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                shareIntent.putExtra("android.intent.extra.TEXT", this.e);
                shareIntent.setType(h.TEXT_PLAIN_VALUE);
                return shareIntent;
            case 1:
            case 2:
            case 3:
            case 4:
                shareIntent.setAction("android.intent.action.SEND");
                shareIntent.addCategory("android.intent.category.DEFAULT");
                shareIntent.setType(this.b);
                shareIntent.putExtra("android.intent.extra.STREAM", this.d);
                shareIntent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                shareIntent.addFlags(1);
                timber.log.a.g(Constants.SERVICE_DEBUG).a("Share2Share uri: " + this.d.toString(), new Object[0]);
                if (Build.VERSION.SDK_INT > 19) {
                    return shareIntent;
                }
                for (ResolveInfo resolveInfo : this.a.getPackageManager().queryIntentActivities(shareIntent, 65536)) {
                    this.a.grantUriPermission(resolveInfo.activityInfo.packageName, this.d, 1);
                }
                return shareIntent;
            default:
                timber.log.a.g(Constants.SERVICE_DEBUG).c("Share2" + this.b + " is not support share type.", new Object[0]);
                return null;
        }
    }

    private boolean a() {
        if (this.a == null) {
            timber.log.a.g(Constants.SERVICE_DEBUG).c("Share2activity is null.", new Object[0]);
            return false;
        } else if (TextUtils.isEmpty(this.b)) {
            timber.log.a.g(Constants.SERVICE_DEBUG).c("Share2Share content type is empty.", new Object[0]);
            return false;
        } else if (h.TEXT_PLAIN_VALUE.equals(this.b)) {
            if (!TextUtils.isEmpty(this.e)) {
                return true;
            }
            timber.log.a.g(Constants.SERVICE_DEBUG).c("Share2Share text context is empty.", new Object[0]);
            return false;
        } else if (this.d != null) {
            return true;
        } else {
            timber.log.a.g(Constants.SERVICE_DEBUG).c("Share2Share file path is null.", new Object[0]);
            return false;
        }
    }

    /* compiled from: Share2 */
    public static class b {
        /* access modifiers changed from: private */
        public Activity a;
        /* access modifiers changed from: private */
        public String b = h.ALL_VALUE;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public String e;
        /* access modifiers changed from: private */
        public Uri f;
        /* access modifiers changed from: private */
        public String g;
        /* access modifiers changed from: private */
        public int h = -1;
        /* access modifiers changed from: private */
        public boolean i = true;

        public b(Activity activity) {
            this.a = activity;
        }

        public b k(String contentType) {
            this.b = contentType;
            return this;
        }

        public b n(@NonNull String title) {
            this.c = title;
            return this;
        }

        public b m(Uri shareFileUri) {
            this.f = shareFileUri;
            return this;
        }

        public b l(int requestCode) {
            this.h = requestCode;
            return this;
        }

        public a j() {
            return new a(this);
        }
    }
}
