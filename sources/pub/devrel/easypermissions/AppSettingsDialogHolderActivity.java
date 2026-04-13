package pub.devrel.easypermissions;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class AppSettingsDialogHolderActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private AlertDialog c;
    private int d;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSettingsDialog appSettingsDialog = AppSettingsDialog.a(getIntent(), this);
        this.d = appSettingsDialog.b();
        this.c = appSettingsDialog.d(this, this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.c;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.c.dismiss();
        }
    }

    @SensorsDataInstrumented
    public void onClick(DialogInterface dialogInterface, int i) {
        int which = i;
        DialogInterface dialogInterface2 = dialogInterface;
        if (which == -1) {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", getPackageName(), (String) null));
            intent.addFlags(this.d);
            startActivityForResult(intent, 7534);
        } else if (which == -2) {
            setResult(0);
            finish();
        } else {
            IllegalStateException illegalStateException = new IllegalStateException("Unknown button type: " + which);
            SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
            throw illegalStateException;
        }
        SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode, data);
        finish();
    }
}
