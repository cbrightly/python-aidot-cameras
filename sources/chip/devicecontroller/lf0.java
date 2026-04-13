package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class lf0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ lf0 a = new lf0();

    private /* synthetic */ lf0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readLocalConfigDisabledAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
