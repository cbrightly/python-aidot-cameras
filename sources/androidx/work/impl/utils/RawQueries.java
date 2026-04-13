package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.work.WorkInfo;
import androidx.work.WorkQuery;
import androidx.work.impl.model.WorkTypeConverters;
import com.meituan.robust.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class RawQueries {
    private RawQueries() {
    }

    @NonNull
    public static SupportSQLiteQuery workQueryToRawQuery(@NonNull WorkQuery querySpec) {
        List<Object> arguments = new ArrayList<>();
        StringBuilder builder = new StringBuilder("SELECT * FROM workspec");
        String conjunction = " WHERE";
        List<WorkInfo.State> states = querySpec.getStates();
        if (!states.isEmpty()) {
            List<Integer> stateIds = new ArrayList<>(states.size());
            for (WorkInfo.State state : states) {
                stateIds.add(Integer.valueOf(WorkTypeConverters.stateToInt(state)));
            }
            builder.append(conjunction);
            builder.append(" state IN (");
            bindings(builder, stateIds.size());
            builder.append(")");
            arguments.addAll(stateIds);
            conjunction = " AND";
        }
        List<UUID> ids = querySpec.getIds();
        if (!ids.isEmpty()) {
            List<String> workSpecIds = new ArrayList<>(ids.size());
            for (UUID id : ids) {
                workSpecIds.add(id.toString());
            }
            builder.append(conjunction);
            builder.append(" id IN (");
            bindings(builder, ids.size());
            builder.append(")");
            arguments.addAll(workSpecIds);
            conjunction = " AND";
        }
        List<String> tags = querySpec.getTags();
        if (!tags.isEmpty()) {
            builder.append(conjunction);
            builder.append(" id IN (SELECT work_spec_id FROM worktag WHERE tag IN (");
            bindings(builder, tags.size());
            builder.append("))");
            arguments.addAll(tags);
            conjunction = " AND";
        }
        List<String> uniqueWorkNames = querySpec.getUniqueWorkNames();
        if (!uniqueWorkNames.isEmpty()) {
            builder.append(conjunction);
            builder.append(" id IN (SELECT work_spec_id FROM workname WHERE name IN (");
            bindings(builder, uniqueWorkNames.size());
            builder.append("))");
            arguments.addAll(uniqueWorkNames);
        }
        builder.append(Constants.PACKNAME_END);
        return new SimpleSQLiteQuery(builder.toString(), arguments.toArray());
    }

    private static void bindings(@NonNull StringBuilder builder, int count) {
        if (count > 0) {
            builder.append("?");
            for (int i = 1; i < count; i++) {
                builder.append(",");
                builder.append("?");
            }
        }
    }
}
