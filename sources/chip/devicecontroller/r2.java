package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class r2 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ r2 a = new r2();

    private /* synthetic */ r2() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$39(l, i);
    }
}
