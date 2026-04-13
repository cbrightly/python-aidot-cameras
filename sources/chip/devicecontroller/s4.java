package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class s4 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ s4 a = new s4();

    private /* synthetic */ s4() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$29(l, i);
    }
}
