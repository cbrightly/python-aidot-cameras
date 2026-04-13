package org.glassfish.tyrus.core;

import jakarta.websocket.Decoder;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import jakarta.websocket.d;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class ComponentProviderService {
    private final List<ComponentProvider> providers;
    private final Map<Session, Map<Class<?>, Object>> sessionToObject;

    public static ComponentProviderService create() {
        List<ComponentProvider> foundProviders = new ArrayList<>();
        Iterator<ComponentProvider> it = ServiceFinder.find(ComponentProvider.class).iterator();
        while (it.hasNext()) {
            foundProviders.add(it.next());
        }
        foundProviders.add(new DefaultComponentProvider());
        return new ComponentProviderService((List<ComponentProvider>) Collections.unmodifiableList(foundProviders));
    }

    public static ComponentProviderService createClient() {
        return new ComponentProviderService((List<ComponentProvider>) Collections.unmodifiableList(Arrays.asList(new ComponentProvider[]{new DefaultComponentProvider()})));
    }

    private ComponentProviderService(List<ComponentProvider> providers2) {
        this.providers = providers2;
        this.sessionToObject = new ConcurrentHashMap();
    }

    public ComponentProviderService(ComponentProviderService componentProviderService) {
        this.providers = componentProviderService.providers;
        this.sessionToObject = componentProviderService.sessionToObject;
    }

    public <T> Object getInstance(Class<T> c, Session session, ErrorCollector collector) {
        Object loaded;
        Map<Class<?>, Object> classObjectMap = this.sessionToObject.get(session);
        if (classObjectMap != null) {
            try {
                synchronized (classObjectMap) {
                    if (classObjectMap.containsKey(c)) {
                        loaded = classObjectMap.get(c);
                    } else {
                        loaded = getEndpointInstance(c);
                        this.sessionToObject.get(session).put(c, loaded);
                    }
                }
                return loaded;
            } catch (Exception e) {
                collector.addException(new DeploymentException(LocalizationMessages.COMPONENT_PROVIDER_THREW_EXCEPTION(c.getName()), e));
                return null;
            }
        } else {
            Object loaded2 = getEndpointInstance(c);
            HashMap<Class<?>, Object> hashMap = new HashMap<>();
            hashMap.put(c, loaded2);
            this.sessionToObject.put(session, hashMap);
            return loaded2;
        }
    }

    public <T> Object getCoderInstance(Class<T> c, Session session, EndpointConfig endpointConfig, ErrorCollector collector) {
        Object loaded = null;
        Map<Class<?>, Object> classObjectMap = this.sessionToObject.get(session);
        if (classObjectMap != null) {
            try {
                synchronized (classObjectMap) {
                    if (classObjectMap.containsKey(c)) {
                        loaded = classObjectMap.get(c);
                    } else {
                        loaded = getInstance(c);
                        if (loaded != null) {
                            if (loaded instanceof d) {
                                ((d) loaded).init(endpointConfig);
                            } else if (loaded instanceof Decoder) {
                                ((Decoder) loaded).init(endpointConfig);
                            }
                            this.sessionToObject.get(session).put(c, loaded);
                        }
                    }
                }
            } catch (InstantiationException e) {
                collector.addException(new DeploymentException(LocalizationMessages.COMPONENT_PROVIDER_THREW_EXCEPTION(c.getName()), e));
            }
        } else {
            loaded = getInstance(c);
            if (loaded != null) {
                if (loaded instanceof d) {
                    ((d) loaded).init(endpointConfig);
                } else if (loaded instanceof Decoder) {
                    ((Decoder) loaded).init(endpointConfig);
                }
                HashMap<Class<?>, Object> hashMap = new HashMap<>();
                hashMap.put(c, loaded);
                this.sessionToObject.put(session, hashMap);
            }
        }
        return loaded;
    }

    public Method getInvocableMethod(Method method) {
        for (ComponentProvider componentProvider : this.providers) {
            if (componentProvider.isApplicable(method.getDeclaringClass())) {
                return componentProvider.getInvocableMethod(method);
            }
        }
        return method;
    }

    private <T> Object getInstance(Class<T> clazz) {
        Object t;
        for (ComponentProvider componentProvider : this.providers) {
            if (componentProvider.isApplicable(clazz) && (t = componentProvider.create(clazz)) != null) {
                return t;
            }
        }
        throw new InstantiationException(LocalizationMessages.COMPONENT_PROVIDER_NOT_FOUND(clazz.getName()));
    }

    public void removeSession(Session session) {
        Map<Class<?>, Object> classObjectMap = this.sessionToObject.get(session);
        if (classObjectMap != null) {
            synchronized (classObjectMap) {
                for (Object o : classObjectMap.values()) {
                    if (o instanceof d) {
                        ((d) o).destroy();
                    } else if (o instanceof Decoder) {
                        ((Decoder) o).destroy();
                    }
                    Iterator<ComponentProvider> it = this.providers.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (it.next().destroy(o)) {
                            break;
                        }
                    }
                }
            }
        }
        this.sessionToObject.remove(session);
    }

    public <T> Object getEndpointInstance(Class<T> endpointClass) {
        return getInstance(endpointClass);
    }
}
