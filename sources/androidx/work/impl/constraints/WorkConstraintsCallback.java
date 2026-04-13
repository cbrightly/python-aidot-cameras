package androidx.work.impl.constraints;

import androidx.annotation.NonNull;
import java.util.List;

public interface WorkConstraintsCallback {
    void onAllConstraintsMet(@NonNull List<String> list);

    void onAllConstraintsNotMet(@NonNull List<String> list);
}
