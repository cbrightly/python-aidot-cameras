package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t91 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t91 a = new t91();

    private /* synthetic */ t91() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BinaryInputBasicCluster) baseChipCluster).readPresentValueAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
