package chip.devicecontroller;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;
import java.util.Optional;

/* compiled from: lambda */
public final /* synthetic */ class z6 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ z6 a = new z6();

    private /* synthetic */ z6() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ActionsCluster) baseChipCluster).enableActionWithDuration((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("actionID"), (Optional) map.get("invokeID"), (Long) map.get(TypedValues.TransitionType.S_DURATION));
    }
}
