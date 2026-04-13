package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.NodeClient;
import java.util.List;

public final class zzfl extends NodeClient {
    private final NodeApi zzem = new zzfg();

    public zzfl(@NonNull Context context, @NonNull GoogleApi.Settings settings) {
        super(context, settings);
    }

    public zzfl(@NonNull Activity activity, @NonNull GoogleApi.Settings settings) {
        super(activity, settings);
    }

    public final Task<Node> getLocalNode() {
        return PendingResultUtil.toTask(this.zzem.getLocalNode(asGoogleApiClient()), zzfm.zzbx);
    }

    public final Task<List<Node>> getConnectedNodes() {
        return PendingResultUtil.toTask(this.zzem.getConnectedNodes(asGoogleApiClient()), zzfn.zzbx);
    }
}
