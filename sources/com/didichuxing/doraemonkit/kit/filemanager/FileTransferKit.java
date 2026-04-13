package com.didichuxing.doraemonkit.kit.filemanager;

import android.content.Context;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileTransferKit.kt */
public final class FileTransferKit extends AbstractKit {
    public int getName() {
        return R.string.dk_kit_file_transfer;
    }

    public int getIcon() {
        return R.mipmap.dk_icon_file_manager;
    }

    public void onClick(@Nullable Context context) {
        startUniversalActivity(context, 31);
    }

    public void onAppInit(@Nullable Context context) {
    }

    public boolean isInnerKit() {
        return true;
    }

    @NotNull
    public String innerKitId() {
        return "dokit_sdk_platform_ck_file_sync";
    }
}
