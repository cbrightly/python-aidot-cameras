package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class u81 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ u81 a = new u81();

    private /* synthetic */ u81() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readColorPointRXAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
