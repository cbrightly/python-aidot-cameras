package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class gt implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ gt a = new gt();

    private /* synthetic */ gt() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DescriptorCluster) baseChipCluster).readPartsListAttribute((ChipClusters.DescriptorCluster.PartsListAttributeCallback) obj);
    }
}
