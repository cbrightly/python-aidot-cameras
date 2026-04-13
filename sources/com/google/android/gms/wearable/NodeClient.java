package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Wearable;
import java.util.List;

public abstract class NodeClient extends GoogleApi<Wearable.WearableOptions> {
    public NodeClient(@NonNull Context context, @NonNull GoogleApi.Settings settings) {
        super(context, Wearable.API, null, settings);
    }

    public abstract Task<List<Node>> getConnectedNodes();

    public abstract Task<Node> getLocalNode();

    public NodeClient(@NonNull Activity activity, @NonNull GoogleApi.Settings settings) {
        super(activity, Wearable.API, null, settings);
    }
}
