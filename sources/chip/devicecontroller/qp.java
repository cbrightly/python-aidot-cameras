package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qp implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qp a = new qp();

    private /* synthetic */ qp() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PowerSourceCluster) baseChipCluster).readBatChargeLevelAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
