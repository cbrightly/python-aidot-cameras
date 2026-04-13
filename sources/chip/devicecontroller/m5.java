package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class m5 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ m5 a = new m5();

    private /* synthetic */ m5() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).enhancedMoveHue((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("moveMode"), (Integer) map.get("rate"), (Integer) map.get("optionsMask"), (Integer) map.get("optionsOverride"));
    }
}
