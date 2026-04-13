package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class ua implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ ua a = new ua();

    private /* synthetic */ ua() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$42(l, i);
    }
}
