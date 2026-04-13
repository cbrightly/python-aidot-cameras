package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class a0 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ a0 a = new a0();

    private /* synthetic */ a0() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$20(l, i);
    }
}
