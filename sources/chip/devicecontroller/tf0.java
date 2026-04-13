package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tf0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tf0 a = new tf0();

    private /* synthetic */ tf0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.NetworkCommissioningCluster) baseChipCluster).readFeatureMapAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
