package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class s80 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ s80 a = new s80();

    private /* synthetic */ s80() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.SwitchCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.SwitchCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
