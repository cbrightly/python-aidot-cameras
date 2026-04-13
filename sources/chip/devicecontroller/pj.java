package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pj implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pj a = new pj();

    private /* synthetic */ pj() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.FixedLabelCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.FixedLabelCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
