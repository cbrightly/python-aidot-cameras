package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class t1 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ t1 a = new t1();

    private /* synthetic */ t1() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$7(l, i);
    }
}
