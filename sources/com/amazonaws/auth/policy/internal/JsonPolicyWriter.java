package com.amazonaws.auth.policy.internal;

import com.amazonaws.auth.policy.Action;
import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonPolicyWriter {
    private static final Log log = LogFactory.getLog("com.amazonaws.auth.policy");
    private AwsJsonWriter jsonWriter = null;
    private final Writer writer;

    public JsonPolicyWriter() {
        StringWriter stringWriter = new StringWriter();
        this.writer = stringWriter;
        this.jsonWriter = JsonUtils.getJsonWriter(stringWriter);
    }

    public String writePolicyToString(Policy policy) {
        if (isNotNull(policy)) {
            try {
                String jsonStringOf = jsonStringOf(policy);
                try {
                    this.writer.close();
                } catch (Exception e) {
                }
                return jsonStringOf;
            } catch (Exception e2) {
                throw new IllegalArgumentException("Unable to serialize policy to JSON string: " + e2.getMessage(), e2);
            } catch (Throwable th) {
                try {
                    this.writer.close();
                } catch (Exception e3) {
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("Policy cannot be null");
        }
    }

    private String jsonStringOf(Policy policy) {
        this.jsonWriter.beginObject();
        writeJsonKeyValue(JsonDocumentFields.VERSION, policy.getVersion());
        if (isNotNull(policy.getId())) {
            writeJsonKeyValue(JsonDocumentFields.POLICY_ID, policy.getId());
        }
        writeJsonArrayStart(JsonDocumentFields.STATEMENT);
        for (Statement statement : policy.getStatements()) {
            this.jsonWriter.beginObject();
            if (isNotNull(statement.getId())) {
                writeJsonKeyValue(JsonDocumentFields.STATEMENT_ID, statement.getId());
            }
            writeJsonKeyValue(JsonDocumentFields.STATEMENT_EFFECT, statement.getEffect().toString());
            List<Principal> principals = statement.getPrincipals();
            if (isNotNull(principals) && !principals.isEmpty()) {
                writePrincipals(principals);
            }
            List<Action> actions = statement.getActions();
            if (isNotNull(actions) && !actions.isEmpty()) {
                writeActions(actions);
            }
            List<Resource> resources = statement.getResources();
            if (isNotNull(resources) && !resources.isEmpty()) {
                writeResources(resources);
            }
            List<Condition> conditions = statement.getConditions();
            if (isNotNull(conditions) && !conditions.isEmpty()) {
                writeConditions(conditions);
            }
            this.jsonWriter.endObject();
        }
        writeJsonArrayEnd();
        this.jsonWriter.endObject();
        this.jsonWriter.flush();
        return this.writer.toString();
    }

    private void writeConditions(List<Condition> conditions) {
        Map<String, ConditionsByKey> conditionsByType = groupConditionsByTypeAndKey(conditions);
        writeJsonObjectStart(JsonDocumentFields.CONDITION);
        for (Map.Entry<String, ConditionsByKey> entry : conditionsByType.entrySet()) {
            ConditionsByKey conditionsByKey = conditionsByType.get(entry.getKey());
            writeJsonObjectStart(entry.getKey());
            for (String key : conditionsByKey.keySet()) {
                writeJsonArray(key, conditionsByKey.getConditionsByKey(key));
            }
            writeJsonObjectEnd();
        }
        writeJsonObjectEnd();
    }

    private void writeResources(List<Resource> resources) {
        List<String> resourceStrings = new ArrayList<>();
        for (Resource resource : resources) {
            resourceStrings.add(resource.getId());
        }
        writeJsonArray(JsonDocumentFields.RESOURCE, resourceStrings);
    }

    private void writeActions(List<Action> actions) {
        List<String> actionStrings = new ArrayList<>();
        for (Action action : actions) {
            actionStrings.add(action.getActionName());
        }
        writeJsonArray(JsonDocumentFields.ACTION, actionStrings);
    }

    private void writePrincipals(List<Principal> principals) {
        if (principals.size() == 1) {
            Principal principal = Principal.All;
            if (principals.get(0).equals(principal)) {
                writeJsonKeyValue(JsonDocumentFields.PRINCIPAL, principal.getId());
                return;
            }
        }
        writeJsonObjectStart(JsonDocumentFields.PRINCIPAL);
        Map<String, List<String>> principalsByScheme = groupPrincipalByScheme(principals);
        for (Map.Entry<String, List<String>> entry : principalsByScheme.entrySet()) {
            List<String> principalValues = principalsByScheme.get(entry.getKey());
            if (principalValues.size() == 1) {
                writeJsonKeyValue(entry.getKey(), principalValues.get(0));
            } else {
                writeJsonArray(entry.getKey(), principalValues);
            }
        }
        writeJsonObjectEnd();
    }

    private Map<String, List<String>> groupPrincipalByScheme(List<Principal> principals) {
        Map<String, List<String>> principalsByScheme = new HashMap<>();
        for (Principal principal : principals) {
            String provider = principal.getProvider();
            if (!principalsByScheme.containsKey(provider)) {
                principalsByScheme.put(provider, new ArrayList());
            }
            principalsByScheme.get(provider).add(principal.getId());
        }
        return principalsByScheme;
    }

    public static class ConditionsByKey {
        private Map<String, List<String>> conditionsByKey = new HashMap();

        public Map<String, List<String>> getConditionsByKey() {
            return this.conditionsByKey;
        }

        public void setConditionsByKey(Map<String, List<String>> conditionsByKey2) {
            this.conditionsByKey = conditionsByKey2;
        }

        public boolean containsKey(String key) {
            return this.conditionsByKey.containsKey(key);
        }

        public List<String> getConditionsByKey(String key) {
            return this.conditionsByKey.get(key);
        }

        public Set<String> keySet() {
            return this.conditionsByKey.keySet();
        }

        public void addValuesToKey(String key, List<String> values) {
            List<String> conditionValues = getConditionsByKey(key);
            if (conditionValues == null) {
                this.conditionsByKey.put(key, new ArrayList(values));
            } else {
                conditionValues.addAll(values);
            }
        }
    }

    private Map<String, ConditionsByKey> groupConditionsByTypeAndKey(List<Condition> conditions) {
        Map<String, ConditionsByKey> conditionsByType = new HashMap<>();
        for (Condition condition : conditions) {
            String type = condition.getType();
            String key = condition.getConditionKey();
            if (!conditionsByType.containsKey(type)) {
                conditionsByType.put(type, new ConditionsByKey());
            }
            conditionsByType.get(type).addValuesToKey(key, condition.getValues());
        }
        return conditionsByType;
    }

    private void writeJsonArray(String arrayName, List<String> values) {
        writeJsonArrayStart(arrayName);
        for (String value : values) {
            this.jsonWriter.value(value);
        }
        writeJsonArrayEnd();
    }

    private void writeJsonObjectStart(String fieldName) {
        this.jsonWriter.name(fieldName);
        this.jsonWriter.beginObject();
    }

    private void writeJsonObjectEnd() {
        this.jsonWriter.endObject();
    }

    private void writeJsonArrayStart(String fieldName) {
        this.jsonWriter.name(fieldName);
        this.jsonWriter.beginArray();
    }

    private void writeJsonArrayEnd() {
        this.jsonWriter.endArray();
    }

    private void writeJsonKeyValue(String fieldName, String value) {
        this.jsonWriter.name(fieldName);
        this.jsonWriter.value(value);
    }

    private boolean isNotNull(Object object) {
        return object != null;
    }
}
