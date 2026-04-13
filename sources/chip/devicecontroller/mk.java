package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class mk implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ mk a = new mk();

    private /* synthetic */ mk() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.WindowCoveringCluster.AttributeListAttributeCallback) obj);
    }
}
