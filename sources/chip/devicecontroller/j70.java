package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class j70 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ j70 a = new j70();

    private /* synthetic */ j70() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BinaryInputBasicCluster) baseChipCluster).readOutOfServiceAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
