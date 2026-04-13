package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class fd implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ fd a = new fd();

    private /* synthetic */ fd() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$40(l, i);
    }
}
