package com.didichuxing.doraemonkit.kit.filemanager;

import android.content.Intent;
import android.view.View;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.l;
import net.sqlcipher.database.SQLiteDatabase;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: FileTransferFragment.kt */
public final class FileTransferFragment$onViewCreated$2 implements View.OnClickListener {
    final /* synthetic */ FileTransferFragment this$0;

    FileTransferFragment$onViewCreated$2(FileTransferFragment fileTransferFragment) {
        this.this$0 = fileTransferFragment;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View view2 = view;
        Intent intent = new Intent(this.this$0.getContext(), UniversalActivity.class);
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, 32);
        this.this$0.startActivity(intent);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
