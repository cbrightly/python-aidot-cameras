package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ii implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ii a = new ii();

    private /* synthetic */ ii() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readCompensationTextAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
