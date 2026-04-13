package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rt0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rt0 a = new rt0();

    private /* synthetic */ rt0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readEnhancedCurrentHueAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
