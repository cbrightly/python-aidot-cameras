package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fv implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fv a = new fv();

    private /* synthetic */ fv() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DescriptorCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.DescriptorCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
