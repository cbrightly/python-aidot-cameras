package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class e91 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ e91 a = new e91();

    private /* synthetic */ e91() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.BasicCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
