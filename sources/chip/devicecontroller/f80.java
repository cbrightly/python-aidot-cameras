package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class f80 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ f80 a = new f80();

    private /* synthetic */ f80() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readColorTempPhysicalMaxMiredsAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
