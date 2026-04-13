package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class bo0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ bo0 a = new bo0();

    private /* synthetic */ bo0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readClusterRevisionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
