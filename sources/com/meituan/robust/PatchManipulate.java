package com.meituan.robust;

import android.content.Context;
import java.util.List;

public abstract class PatchManipulate {
    /* access modifiers changed from: protected */
    public abstract boolean ensurePatchExist(Patch patch);

    /* access modifiers changed from: protected */
    public abstract List<Patch> fetchPatchList(Context context);

    /* access modifiers changed from: protected */
    public abstract boolean verifyPatch(Context context, Patch patch);
}
