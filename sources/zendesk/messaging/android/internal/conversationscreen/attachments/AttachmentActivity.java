package zendesk.messaging.android.internal.conversationscreen.attachments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.io.j;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;

/* compiled from: AttachmentActivity.kt */
public final class AttachmentActivity extends AppCompatActivity {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    @NotNull
    private final g d = i.b(new b(this));
    @Nullable
    private Uri f;

    /* compiled from: AttachmentActivity.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<zendesk.messaging.android.internal.a> {
        final /* synthetic */ AttachmentActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(AttachmentActivity attachmentActivity) {
            super(0);
            this.this$0 = attachmentActivity;
        }

        @NotNull
        public final zendesk.messaging.android.internal.a invoke() {
            return new zendesk.messaging.android.internal.a(this.this$0);
        }
    }

    private final zendesk.messaging.android.internal.a N() {
        return (zendesk.messaging.android.internal.a) this.d.getValue();
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("is_camera_request", false)) {
            X();
        } else if (getIntent().getBooleanExtra("is_file_request", false)) {
            Y();
        } else {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);
        if (this.f != null && requestCode == 41 && resultCode == -1) {
            V(this, this.f);
            return;
        }
        int i = 0;
        if (returnIntent != null && requestCode == 42 && resultCode == -1) {
            Uri singleFileUri = returnIntent.getData();
            ClipData clipData = returnIntent.getClipData();
            if (clipData != null) {
                int count = clipData.getItemCount();
                ArrayList attachments = new ArrayList(count);
                if (count > 0) {
                    do {
                        int i2 = i;
                        i++;
                        Uri fileUri = clipData.getItemAt(i2).getUri();
                        k.d(fileUri, "clipData.getItemAt(i).uri");
                        getContentResolver().takePersistableUriPermission(fileUri, 1);
                        attachments.add(M(fileUri));
                    } while (i < count);
                }
                W(this, attachments);
            } else if (singleFileUri != null) {
                getContentResolver().takePersistableUriPermission(singleFileUri, 1);
                V(this, singleFileUri);
            } else {
                setResult(0);
                finish();
            }
        } else {
            setResult(0);
            finish();
        }
    }

    private static final void W(AttachmentActivity this$0, ArrayList<Attachment> attachments) {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("attachment_files_result", attachments);
        this$0.setResult(-1, resultIntent);
        this$0.finish();
    }

    private static final void V(AttachmentActivity this$0, Uri uri) {
        if (uri != null) {
            W(this$0, q.c(this$0.M(uri)));
            return;
        }
        this$0.setResult(0);
        this$0.finish();
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        k.e(permissions, "permissions");
        k.e(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 43) {
            if (!(!(grantResults.length == 0)) || grantResults[0] != 0) {
                Z();
            } else {
                c0(this);
            }
        }
    }

    private final void X() {
        if (S(this)) {
            zendesk.logger.a.e("Attachment", "App has camera permission, checking permission", new Object[0]);
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
                zendesk.logger.a.e("Attachment", "App has camera permission & granted, starting intent", new Object[0]);
                c0(this);
                return;
            }
            zendesk.logger.a.e("Attachment", "App has camera permission & not granted, requesting", new Object[0]);
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 43);
            return;
        }
        zendesk.logger.a.e("Attachment", "App does not have camera permission, starting intent", new Object[0]);
        c0(this);
    }

    private final void Y() {
        startActivityForResult(N().d(), 42);
    }

    private final boolean S(Context context) {
        try {
            String[] declaredPermissions = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (declaredPermissions != null) {
                if (!(declaredPermissions.length == 0)) {
                    int length = declaredPermissions.length;
                    int i = 0;
                    while (i < length) {
                        String permission = declaredPermissions[i];
                        i++;
                        if (k.a(permission, "android.permission.CAMERA")) {
                            return true;
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    @SuppressLint({"SimpleDateFormat"})
    private final void c0(Activity activity) {
        Intent takePhotoIntent = N().c();
        if (takePhotoIntent.resolveActivity(activity.getPackageManager()) != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            k.d(timeStamp, "SimpleDateFormat(\"yyyyMMdd_HHmmss\").format(Date())");
            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            Uri photoUri = FileProvider.getUriForFile(activity, k.l(getPackageName(), ".zendesk.messaging.fileprovider"), File.createTempFile("JPEG_" + timeStamp + '_', ".jpg", storageDir));
            this.f = photoUri;
            takePhotoIntent.putExtra("output", photoUri);
            activity.startActivityForResult(takePhotoIntent, 41);
        }
    }

    private final Attachment M(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String name = cursor == null ? "" : O(cursor);
        long size = cursor == null ? 0 : R(cursor);
        if (cursor != null) {
            cursor.close();
        }
        String mimeType = c.a(j.d(new File(name)));
        String uri2 = uri.toString();
        k.d(uri2, "uri.toString()");
        return new Attachment(name, uri2, mimeType, size);
    }

    private final String O(Cursor $this$getDisplayName) {
        try {
            String string = $this$getDisplayName.getString($this$getDisplayName.getColumnIndexOrThrow("_display_name"));
            k.d(string, "{\n            getString(….DISPLAY_NAME))\n        }");
            return string;
        } catch (Exception e) {
            return "";
        }
    }

    private final long R(Cursor $this$getSize) {
        try {
            return $this$getSize.getLong($this$getSize.getColumnIndexOrThrow("_size"));
        } catch (Exception e) {
            return 0;
        }
    }

    private final void Z() {
        BottomSheetDialog permissionRationaleDialog = new BottomSheetDialog(this);
        permissionRationaleDialog.setContentView(R$layout.zma_attachment_permission_rationale);
        View settingButton = permissionRationaleDialog.findViewById(R$id.zma_setting_button);
        if (settingButton != null) {
            settingButton.setOnClickListener(new a(this, permissionRationaleDialog));
        }
        permissionRationaleDialog.setCancelable(true);
        permissionRationaleDialog.setOnCancelListener(new b(this));
        permissionRationaleDialog.show();
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void a0(AttachmentActivity this$0, BottomSheetDialog $permissionRationaleDialog, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        k.e($permissionRationaleDialog, "$permissionRationaleDialog");
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", this$0.getPackageName(), (String) null));
        this$0.startActivity(intent);
        $permissionRationaleDialog.dismiss();
        this$0.finish();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    public static final void b0(AttachmentActivity this$0, DialogInterface it) {
        k.e(this$0, "this$0");
        this$0.setResult(0);
        this$0.finish();
    }

    /* compiled from: AttachmentActivity.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        public final void c(@NotNull Activity activity, int requestCode) {
            k.e(activity, "activity");
            Intent intent = new Intent(activity, AttachmentActivity.class);
            intent.putExtra("is_camera_request", true);
            activity.startActivityForResult(intent, requestCode);
        }

        public final void b(@NotNull Activity activity, int requestCode) {
            k.e(activity, "activity");
            Intent intent = new Intent(activity, AttachmentActivity.class);
            intent.putExtra("is_file_request", true);
            activity.startActivityForResult(intent, requestCode);
        }

        @NotNull
        public final List<Attachment> a(@Nullable Intent intent) {
            Iterable parcelableArrayListExtra;
            List<Attachment> list = null;
            if (!(intent == null || (parcelableArrayListExtra = intent.getParcelableArrayListExtra("attachment_files_result")) == null)) {
                Iterable<Attachment> $this$map$iv = parcelableArrayListExtra;
                Collection destination$iv$iv = new ArrayList(r.r($this$map$iv, 10));
                for (Attachment it : $this$map$iv) {
                    if (it != null) {
                        destination$iv$iv.add(it);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type zendesk.messaging.android.internal.conversationscreen.attachments.Attachment");
                    }
                }
                list = y.C0(destination$iv$iv);
            }
            if (list == null) {
                return q.g();
            }
            return list;
        }
    }
}
