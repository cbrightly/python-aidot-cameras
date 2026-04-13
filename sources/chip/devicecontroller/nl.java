package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class nl implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ nl a = new nl();

    private /* synthetic */ nl() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ScenesCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.ScenesCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
