package com.didichuxing.doraemonkit.kit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import net.sqlcipher.database.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractKit.kt */
public abstract class AbstractKit implements IKit {
    private boolean canShow = true;

    public final void startUniversalActivity(@Nullable Context context, int fragmentIndex) {
        if (context != null) {
            Intent intent = new Intent(context, UniversalActivity.class);
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent.putExtra(BundleKey.FRAGMENT_INDEX, fragmentIndex);
            context.startActivity(intent);
        }
    }

    public boolean isInnerKit() {
        return false;
    }

    public final boolean getCanShow() {
        return this.canShow;
    }

    public final void setCanShow(boolean z) {
        this.canShow = z;
    }

    @NotNull
    public String innerKitId() {
        return "";
    }

    @Nullable
    public final Activity currentActivity() {
        return a.b();
    }

    public int getCategory() {
        return 9;
    }
}
