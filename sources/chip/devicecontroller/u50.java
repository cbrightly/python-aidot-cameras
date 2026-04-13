package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class u50 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ u50 a = new u50();

    private /* synthetic */ u50() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).readCurrentPositionTiltAttribute((ChipClusters.WindowCoveringCluster.CurrentPositionTiltAttributeCallback) obj);
    }
}
