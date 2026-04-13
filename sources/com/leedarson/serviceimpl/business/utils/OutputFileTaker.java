package com.leedarson.serviceimpl.business.utils;

import com.meituan.robust.ChangeQuickRedirect;
import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageExt.kt */
public final class OutputFileTaker {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private File file;

    public OutputFileTaker() {
        this((File) null, 1, (DefaultConstructorMarker) null);
    }

    public OutputFileTaker(@Nullable File file2) {
        this.file = file2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ OutputFileTaker(File file2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : file2);
    }

    @Nullable
    public final File getFile() {
        return this.file;
    }

    public final void setFile(@Nullable File file2) {
        this.file = file2;
    }
}
