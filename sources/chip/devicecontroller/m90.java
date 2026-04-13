package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class m90 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ m90 a = new m90();

    private /* synthetic */ m90() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readProductNameAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
