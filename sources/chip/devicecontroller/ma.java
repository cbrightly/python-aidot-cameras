package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class ma implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ ma a = new ma();

    private /* synthetic */ ma() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$19(l, i);
    }
}
