package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class dg implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ dg a = new dg();

    private /* synthetic */ dg() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ModeSelectCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.ModeSelectCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
