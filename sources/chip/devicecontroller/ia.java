package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class ia implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ ia a = new ia();

    private /* synthetic */ ia() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$55(l, i);
    }
}
