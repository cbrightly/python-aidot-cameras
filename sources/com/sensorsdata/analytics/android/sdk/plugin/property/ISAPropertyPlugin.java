package com.sensorsdata.analytics.android.sdk.plugin.property;

import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import java.util.Map;
import java.util.Set;

public interface ISAPropertyPlugin {
    void appendDynamicProperties(Map<String, Object> map);

    void appendProperties(Map<String, Object> map);

    void eventNameFilter(Set<String> set);

    void eventTypeFilter(Set<EventType> set);

    SAPropertyPluginPriority priority();

    void propertyKeyFilter(Set<String> set);
}
