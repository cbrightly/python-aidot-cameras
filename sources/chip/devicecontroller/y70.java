package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class y70 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ y70 a = new y70();

    private /* synthetic */ y70() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ActionsCluster) baseChipCluster).readSetupURLAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
