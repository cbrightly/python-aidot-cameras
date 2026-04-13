package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class sd0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ sd0 a = new sd0();

    private /* synthetic */ sd0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readColorLoopStartEnhancedHueAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
