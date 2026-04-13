package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class uu0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ uu0 a = new uu0();

    private /* synthetic */ uu0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.UserLabelCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.UserLabelCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
