package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class pe implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ pe a = new pe();

    private /* synthetic */ pe() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$25(l, i);
    }
}
