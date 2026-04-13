package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class f70 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ f70 a = new f70();

    private /* synthetic */ f70() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.LevelControlCluster) baseChipCluster).readCurrentFrequencyAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
