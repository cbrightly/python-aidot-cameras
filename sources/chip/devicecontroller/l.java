package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class l implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ l a = new l();

    private /* synthetic */ l() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$26(l, i);
    }
}
