package androidx.work;

import androidx.annotation.NonNull;
import androidx.work.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OverwritingInputMerger extends InputMerger {
    @NonNull
    public Data merge(@NonNull List<Data> inputs) {
        Data.Builder output = new Data.Builder();
        Map<String, Object> mergedValues = new HashMap<>();
        for (Data input : inputs) {
            mergedValues.putAll(input.getKeyValueMap());
        }
        output.putAll(mergedValues);
        return output.build();
    }
}
