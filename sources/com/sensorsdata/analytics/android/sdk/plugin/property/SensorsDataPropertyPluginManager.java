package com.sensorsdata.analytics.android.sdk.plugin.property;

import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public final class SensorsDataPropertyPluginManager {
    private static final String TAG = "SA.SAPropertyPluginManager";
    private final Map<String, SAPropertyPlugin> plugins;

    public static class SingleHolder {
        /* access modifiers changed from: private */
        public static final SensorsDataPropertyPluginManager INSTANCE = new SensorsDataPropertyPluginManager();

        private SingleHolder() {
        }
    }

    public static SensorsDataPropertyPluginManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    private SensorsDataPropertyPluginManager() {
        this.plugins = new LinkedHashMap();
    }

    public final void registerPropertyPlugin(SAPropertyPlugin plugin) {
        if (plugin != null) {
            try {
                String propertyType = getPluginType(plugin);
                if (!this.plugins.containsKey(propertyType)) {
                    this.plugins.put(propertyType, plugin);
                    plugin.start();
                    return;
                }
                SALog.i(TAG, String.format("plugin [ %s ] has exist!", new Object[]{propertyType}));
            } catch (Exception e) {
                SALog.i(TAG, "register property plugin exception! " + e.toString());
            }
        }
    }

    public final JSONObject properties(String eventName, EventType eventType, JSONObject properties) {
        JSONObject jsonObject;
        long startPropertiesTime = System.currentTimeMillis();
        try {
            jsonObject = properties(filter(eventName, eventType, properties));
        } catch (Exception e) {
            SALog.i(TAG, String.format("Event [%s] error is happened when matching property-plugins, e=%s", new Object[]{eventName, e.toString()}));
            jsonObject = new JSONObject();
        }
        SALog.i(TAG, String.format("Event [%s] spend [%sms] on matching property-plugins", new Object[]{eventName, Long.valueOf(System.currentTimeMillis() - startPropertiesTime)}));
        return jsonObject;
    }

    private String getPluginType(SAPropertyPlugin plugin) {
        if (plugin == null) {
            return "";
        }
        return plugin.getClass().getName();
    }

    private List<SAPropertyPlugin> filter(String eventName, EventType eventType, JSONObject properties) {
        List<SAPropertyPlugin> filterPlugins = new LinkedList<>();
        for (SAPropertyPlugin plugin : this.plugins.values()) {
            if (isMatchEventType(plugin.getEventTypeFilter(), eventType) && isMatchEventName(plugin.getEventNameFilter(), eventName) && isMatchPropertyKey(plugin.getPropertyKeyFilter(), properties)) {
                filterPlugins.add(plugin);
            }
        }
        Collections.sort(filterPlugins, new Comparator<SAPropertyPlugin>() {
            public int compare(SAPropertyPlugin o1, SAPropertyPlugin o2) {
                if (o1.priority().getPriority() >= o2.priority().getPriority()) {
                    return 0;
                }
                return -1;
            }
        });
        return filterPlugins;
    }

    private boolean isMatchEventName(Set<String> eventNameFilter, String eventName) {
        if (eventNameFilter == null || eventNameFilter.size() == 0) {
            return true;
        }
        return eventNameFilter.contains(eventName);
    }

    private boolean isMatchPropertyKey(Set<String> propertyKeyFilter, JSONObject properties) {
        if (propertyKeyFilter == null || propertyKeyFilter.size() == 0) {
            return true;
        }
        if (properties == null) {
            return false;
        }
        for (String propertyKey : propertyKeyFilter) {
            if (properties.has(propertyKey)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatchEventType(Set<EventType> eventTypeFilter, EventType eventType) {
        if (((eventTypeFilter == null || eventTypeFilter.isEmpty()) && eventType == EventType.TRACK) || eventTypeFilter.contains(EventType.ALL)) {
            return true;
        }
        return eventTypeFilter.contains(eventType);
    }

    private JSONObject properties(List<SAPropertyPlugin> plugins2) {
        JSONObject jsonObject = new JSONObject();
        if (plugins2 == null) {
            return jsonObject;
        }
        for (SAPropertyPlugin plugin : plugins2) {
            Map<String, Object> properties = plugin.properties();
            if (properties != null && !properties.isEmpty()) {
                JSONObject pluginProperties = new JSONObject((Map<?, ?>) properties);
                try {
                    SADataHelper.assertPropertyTypes(pluginProperties);
                    SensorsDataUtils.mergeJSONObject(pluginProperties, jsonObject);
                } catch (InvalidDataException e) {
                    SALog.printStackTrace(e);
                }
            }
        }
        return jsonObject;
    }

    public final Map<String, Object> getPropertiesByPlugin(Class<?> clazz) {
        SAPropertyPlugin plugin;
        Map<String, Object> properties = new HashMap<>();
        if (clazz == null) {
            return properties;
        }
        String pluginType = clazz.getName();
        if (this.plugins.containsKey(pluginType) && (plugin = this.plugins.get(pluginType)) != null) {
            properties.putAll(plugin.properties());
        }
        return properties;
    }
}
