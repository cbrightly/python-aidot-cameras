package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class DomainNameMappingBuilder<V> {
    private final V defaultValue;
    private final Map<String, V> map;

    public DomainNameMappingBuilder(V defaultValue2) {
        this(4, defaultValue2);
    }

    public DomainNameMappingBuilder(int initialCapacity, V defaultValue2) {
        this.defaultValue = ObjectUtil.checkNotNull(defaultValue2, "defaultValue");
        this.map = new LinkedHashMap(initialCapacity);
    }

    public DomainNameMappingBuilder<V> add(String hostname, V output) {
        this.map.put(ObjectUtil.checkNotNull(hostname, "hostname"), ObjectUtil.checkNotNull(output, "output"));
        return this;
    }

    public DomainNameMapping<V> build() {
        return new ImmutableDomainNameMapping(this.defaultValue, this.map);
    }

    public static final class ImmutableDomainNameMapping<V> extends DomainNameMapping<V> {
        private static final int REPR_CONST_PART_LENGTH = ((REPR_HEADER.length() + REPR_MAP_OPENING.length()) + REPR_MAP_CLOSING.length());
        private static final String REPR_HEADER = "ImmutableDomainNameMapping(default: ";
        private static final String REPR_MAP_CLOSING = "})";
        private static final String REPR_MAP_OPENING = ", map: {";
        private final String[] domainNamePatterns;
        private final Map<String, V> map;
        private final V[] values;

        private ImmutableDomainNameMapping(V defaultValue, Map<String, V> map2) {
            super((Map) null, defaultValue);
            Set<Map.Entry<String, V>> mappings = map2.entrySet();
            int numberOfMappings = mappings.size();
            this.domainNamePatterns = new String[numberOfMappings];
            this.values = new Object[numberOfMappings];
            Map<String, V> mapCopy = new LinkedHashMap<>(map2.size());
            int index = 0;
            for (Map.Entry<String, V> mapping : mappings) {
                String hostname = DomainNameMapping.normalizeHostname(mapping.getKey());
                V value = mapping.getValue();
                this.domainNamePatterns[index] = hostname;
                this.values[index] = value;
                mapCopy.put(hostname, value);
                index++;
            }
            this.map = Collections.unmodifiableMap(mapCopy);
        }

        @Deprecated
        public DomainNameMapping<V> add(String hostname, V v) {
            throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
        }

        public V map(String hostname) {
            if (hostname != null) {
                String hostname2 = DomainNameMapping.normalizeHostname(hostname);
                int length = this.domainNamePatterns.length;
                for (int index = 0; index < length; index++) {
                    if (DomainNameMapping.matches(this.domainNamePatterns[index], hostname2)) {
                        return this.values[index];
                    }
                }
            }
            return this.defaultValue;
        }

        public Map<String, V> asMap() {
            return this.map;
        }

        public String toString() {
            String defaultValueStr = this.defaultValue.toString();
            String[] strArr = this.domainNamePatterns;
            int numberOfMappings = strArr.length;
            if (numberOfMappings == 0) {
                return REPR_HEADER + defaultValueStr + REPR_MAP_OPENING + REPR_MAP_CLOSING;
            }
            String pattern0 = strArr[0];
            String value0 = this.values[0].toString();
            StringBuilder sb = new StringBuilder(estimateBufferSize(defaultValueStr.length(), numberOfMappings, pattern0.length() + value0.length() + 3));
            sb.append(REPR_HEADER);
            sb.append(defaultValueStr);
            StringBuilder sb2 = sb.append(REPR_MAP_OPENING);
            appendMapping(sb2, pattern0, value0);
            for (int index = 1; index < numberOfMappings; index++) {
                sb2.append(", ");
                appendMapping(sb2, index);
            }
            sb2.append(REPR_MAP_CLOSING);
            return sb2.toString();
        }

        private static int estimateBufferSize(int defaultValueLength, int numberOfMappings, int estimatedMappingLength) {
            return REPR_CONST_PART_LENGTH + defaultValueLength + ((int) (((double) (estimatedMappingLength * numberOfMappings)) * 1.1d));
        }

        private StringBuilder appendMapping(StringBuilder sb, int mappingIndex) {
            return appendMapping(sb, this.domainNamePatterns[mappingIndex], this.values[mappingIndex].toString());
        }

        private static StringBuilder appendMapping(StringBuilder sb, String domainNamePattern, String value) {
            sb.append(domainNamePattern);
            sb.append('=');
            sb.append(value);
            return sb;
        }
    }
}
