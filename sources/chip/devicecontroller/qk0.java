package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qk0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qk0 a = new qk0();

    private /* synthetic */ qk0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DescriptorCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.DescriptorCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
