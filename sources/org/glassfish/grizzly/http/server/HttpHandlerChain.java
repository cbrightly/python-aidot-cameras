package org.glassfish.grizzly.http.server;

import java.io.CharConversionException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.jmxbase.JmxEventListener;
import org.glassfish.grizzly.http.server.jmxbase.Monitorable;
import org.glassfish.grizzly.http.server.naming.NamingContext;
import org.glassfish.grizzly.http.server.util.DispatcherHelper;
import org.glassfish.grizzly.http.server.util.Mapper;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.RequestURIRef;
import org.glassfish.grizzly.localization.LogMessages;

public class HttpHandlerChain extends HttpHandler implements JmxEventListener {
    private static final String LOCAL_HOST = "localhost";
    private static final Logger LOGGER = Grizzly.logger(HttpHandlerChain.class);
    private static final Map<HttpHandlerRegistration, PathUpdater> ROOT_URLS;
    private final DispatcherHelper dispatchHelper;
    /* access modifiers changed from: private */
    public final FullUrlPathResolver fullUrlPathResolver = new FullUrlPathResolver(this);
    private final ConcurrentMap<HttpHandler, HttpHandlerRegistration[]> handlers = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public final ConcurrentMap<String, HttpHandler> handlersByName = new ConcurrentHashMap();
    private int handlersCount;
    private final HttpServer httpServer;
    private boolean isRootConfigured = false;
    /* access modifiers changed from: private */
    public final Mapper mapper;
    private final ReentrantReadWriteLock mapperUpdateLock = new ReentrantReadWriteLock();
    private final ConcurrentMap<HttpHandler, Object> monitors = new ConcurrentHashMap();
    private volatile RootHttpHandler rootHttpHandler;
    private boolean started;

    public interface PathUpdater {
        void update(HttpHandlerChain httpHandlerChain, HttpHandler httpHandler, Request request);
    }

    static {
        HashMap hashMap = new HashMap(3);
        ROOT_URLS = hashMap;
        hashMap.put(HttpHandlerRegistration.fromString(""), new EmptyPathUpdater());
        hashMap.put(HttpHandlerRegistration.fromString("/"), new SlashPathUpdater());
        hashMap.put(HttpHandlerRegistration.fromString("/*"), new SlashStarPathUpdater());
    }

    public HttpHandlerChain(HttpServer httpServer2) {
        this.httpServer = httpServer2;
        Mapper mapper2 = new Mapper();
        this.mapper = mapper2;
        mapper2.setDefaultHostName(LOCAL_HOST);
        this.dispatchHelper = new DispatchHelperImpl();
        setDecodeUrl(false);
    }

    public void jmxEnabled() {
        this.mapperUpdateLock.readLock().lock();
        try {
            for (HttpHandler httpHandler : this.handlers.keySet()) {
                if (httpHandler instanceof Monitorable) {
                    registerJmxForHandler(httpHandler);
                }
            }
        } finally {
            this.mapperUpdateLock.readLock().unlock();
        }
    }

    public void jmxDisabled() {
        this.mapperUpdateLock.readLock().lock();
        try {
            for (HttpHandler httpHandler : this.handlers.keySet()) {
                if (httpHandler instanceof Monitorable) {
                    deregisterJmxForHandler(httpHandler);
                }
            }
        } finally {
            this.mapperUpdateLock.readLock().unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean doHandle(Request request, Response response) {
        HttpHandler httpHandler;
        response.setErrorPageGenerator(getErrorPageGenerator(request));
        try {
            RootHttpHandler rootHttpHandlerLocal = this.rootHttpHandler;
            if (rootHttpHandlerLocal != null) {
                HttpHandler rh = rootHttpHandlerLocal.httpHandler;
                rootHttpHandlerLocal.pathUpdater.update(this, rh, request);
                return rh.doHandle(request, response);
            }
            RequestURIRef uriRef = request.getRequest().getRequestURIRef();
            uriRef.setDefaultURIEncoding(getRequestURIEncoding());
            DataChunk decodedURI = uriRef.getDecodedRequestURIBC(isAllowEncodedSlash());
            MappingData mappingData = request.obtainMappingData();
            this.mapper.mapUriWithSemicolon(request.getRequest(), decodedURI, mappingData, 0);
            Object obj = mappingData.context;
            if (obj == null || !(obj instanceof HttpHandler)) {
                response.sendError(404);
                return true;
            }
            Object obj2 = mappingData.wrapper;
            if (obj2 != null) {
                httpHandler = (HttpHandler) obj2;
            } else {
                httpHandler = (HttpHandler) obj;
            }
            HttpHandler.updatePaths(request, mappingData);
            return httpHandler.doHandle(request, response);
        } catch (Exception t) {
            try {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
                Logger logger = LOGGER;
                Level level = Level.FINE;
                if (!logger.isLoggable(level)) {
                    return true;
                }
                logger.log(level, "Internal server error", t);
                return true;
            } catch (Exception ex2) {
                Logger logger2 = LOGGER;
                Level level2 = Level.WARNING;
                if (!logger2.isLoggable(level2)) {
                    return true;
                }
                logger2.log(level2, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLERCHAIN_ERRORPAGE(), ex2);
                return true;
            }
        }
    }

    public void service(Request request, Response response) {
        throw new IllegalStateException("Method doesn't have to be called");
    }

    public void addHandler(HttpHandler httpHandler, String[] mappings) {
    }

    public void addHandler(HttpHandler httpHandler, HttpHandlerRegistration[] mappings) {
        String wrapper;
        String ctx;
        HttpHandler httpHandler2 = httpHandler;
        HttpHandlerRegistration[] httpHandlerRegistrationArr = mappings;
        this.mapperUpdateLock.writeLock().lock();
        try {
            if (httpHandlerRegistrationArr.length == 0) {
                addHandler(httpHandler2, new String[]{""});
            } else {
                if (this.started) {
                    httpHandler.start();
                    if (httpHandler2 instanceof Monitorable) {
                        registerJmxForHandler(httpHandler);
                    }
                }
                boolean z = true;
                if (this.handlers.put(httpHandler2, httpHandlerRegistrationArr) == null) {
                    this.handlersCount++;
                }
                String name = httpHandler.getName();
                if (name != null) {
                    this.handlersByName.put(name, httpHandler2);
                }
                httpHandler2.setDispatcherHelper(this.dispatchHelper);
                int length = httpHandlerRegistrationArr.length;
                int i = 0;
                while (i < length) {
                    HttpHandlerRegistration reg = httpHandlerRegistrationArr[i];
                    String ctx2 = reg.getContextPath();
                    String wrapper2 = reg.getUrlPattern();
                    if (ctx2.length() != 0) {
                        wrapper = wrapper2;
                        this.mapper.addContext(LOCAL_HOST, ctx2, httpHandler, new String[]{"index.html", "index.htm"}, (NamingContext) null);
                        ctx = ctx2;
                    } else {
                        wrapper = wrapper2;
                        if (this.isRootConfigured || !wrapper.startsWith("*.")) {
                            String ctx3 = ctx2;
                            ctx = ctx3;
                            this.mapper.addContext(LOCAL_HOST, ctx3, httpHandler, new String[]{"index.html", "index.htm"}, (NamingContext) null);
                        } else {
                            this.isRootConfigured = z;
                            String ctx4 = ctx2;
                            this.mapper.addContext(LOCAL_HOST, ctx4, new HttpHandler() {
                                public void service(Request request, Response response) {
                                    response.sendError(404);
                                }
                            }, new String[]{"index.html", "index.htm"}, (NamingContext) null);
                            ctx = ctx4;
                        }
                    }
                    this.mapper.addWrapper(LOCAL_HOST, ctx, wrapper, (Object) httpHandler2);
                    i++;
                    z = true;
                }
                if (this.handlersCount == 1 && httpHandlerRegistrationArr.length == 1) {
                    Map<HttpHandlerRegistration, PathUpdater> map = ROOT_URLS;
                    if (map.containsKey(httpHandlerRegistrationArr[0])) {
                        this.rootHttpHandler = new RootHttpHandler(httpHandler2, map.get(httpHandlerRegistrationArr[0]));
                    }
                }
                this.rootHttpHandler = null;
            }
        } finally {
            this.mapperUpdateLock.writeLock().unlock();
        }
    }

    public boolean removeHttpHandler(HttpHandler httpHandler) {
        if (httpHandler != null) {
            this.mapperUpdateLock.writeLock().lock();
            try {
                String name = httpHandler.getName();
                if (name != null) {
                    this.handlersByName.remove(name);
                }
                HttpHandlerRegistration[] mappings = (HttpHandlerRegistration[]) this.handlers.remove(httpHandler);
                boolean z = false;
                if (mappings != null) {
                    for (HttpHandlerRegistration mapping : mappings) {
                        String contextPath = mapping.getContextPath();
                        this.mapper.removeWrapper(LOCAL_HOST, contextPath, mapping.getUrlPattern());
                        if (this.mapper.getWrapperNames(LOCAL_HOST, name).length == 0) {
                            this.mapper.removeContext(LOCAL_HOST, contextPath);
                        }
                    }
                    deregisterJmxForHandler(httpHandler);
                    httpHandler.destroy();
                    int i = this.handlersCount - 1;
                    this.handlersCount = i;
                    if (i == 1) {
                        HttpHandlerRegistration[] lastHttpHandlerMappings = ((Map.Entry) this.handlers.entrySet().iterator().next()).getValue();
                        if (lastHttpHandlerMappings.length == 1) {
                            Map<HttpHandlerRegistration, PathUpdater> map = ROOT_URLS;
                            if (map.containsKey(lastHttpHandlerMappings[0])) {
                                this.rootHttpHandler = new RootHttpHandler(httpHandler, map.get(lastHttpHandlerMappings[0]));
                            }
                        }
                        this.rootHttpHandler = null;
                    } else {
                        this.rootHttpHandler = null;
                    }
                }
                if (mappings != null) {
                    z = true;
                }
                return z;
            } finally {
                this.mapperUpdateLock.writeLock().unlock();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public void removeAllHttpHandlers() {
        this.mapperUpdateLock.writeLock().lock();
        try {
            for (HttpHandler handler : this.handlers.keySet()) {
                removeHttpHandler(handler);
            }
        } finally {
            this.mapperUpdateLock.writeLock().unlock();
        }
    }

    public synchronized void start() {
        this.mapperUpdateLock.readLock().lock();
        try {
            for (HttpHandler httpHandler : this.handlers.keySet()) {
                try {
                    httpHandler.start();
                } catch (Throwable th) {
                    th = th;
                }
            }
            this.mapperUpdateLock.readLock().unlock();
            this.started = true;
        } catch (Throwable th2) {
            th = th2;
            this.mapperUpdateLock.readLock().unlock();
            throw th;
        }
    }

    public synchronized void destroy() {
        this.mapperUpdateLock.writeLock().lock();
        try {
            for (HttpHandler httpHandler : this.handlers.keySet()) {
                try {
                    httpHandler.destroy();
                } catch (Throwable th) {
                    th = th;
                }
            }
            this.mapperUpdateLock.writeLock().unlock();
            this.started = false;
        } catch (Throwable th2) {
            th = th2;
            this.mapperUpdateLock.writeLock().unlock();
            throw th;
        }
    }

    private void registerJmxForHandler(HttpHandler httpHandler) {
        Object jmx = ((Monitorable) httpHandler).createManagementObject();
        if (this.monitors.putIfAbsent(httpHandler, jmx) == null) {
            this.httpServer.jmxManager.register(this.httpServer.managementObject, jmx);
        }
    }

    private void deregisterJmxForHandler(HttpHandler httpHandler) {
        Object jmx = this.monitors.remove(httpHandler);
        if (jmx != null) {
            this.httpServer.jmxManager.deregister(jmx);
        }
    }

    public final class DispatchHelperImpl implements DispatcherHelper {
        private DispatchHelperImpl() {
        }

        public void mapPath(HttpRequestPacket requestPacket, DataChunk path, MappingData mappingData) {
            HttpHandlerChain.this.mapper.map(requestPacket, path, mappingData);
        }

        public void mapName(DataChunk name, MappingData mappingData) {
            String nameStr = name.toString();
            HttpHandler handler = (HttpHandler) HttpHandlerChain.this.handlersByName.get(nameStr);
            if (handler != null) {
                mappingData.wrapper = handler;
                mappingData.servletName = nameStr;
            }
        }
    }

    public static final class RootHttpHandler {
        /* access modifiers changed from: private */
        public final HttpHandler httpHandler;
        /* access modifiers changed from: private */
        public final PathUpdater pathUpdater;

        public RootHttpHandler(HttpHandler httpHandler2, PathUpdater pathUpdater2) {
            this.httpHandler = httpHandler2;
            this.pathUpdater = pathUpdater2;
        }
    }

    public static class SlashPathUpdater implements PathUpdater {
        private SlashPathUpdater() {
        }

        public void update(HttpHandlerChain handlerChain, HttpHandler httpHandler, Request request) {
            request.setContextPath("");
            request.setPathInfo((String) null);
            request.setHttpHandlerPath((Request.PathResolver) handlerChain.fullUrlPathResolver);
        }
    }

    public static class SlashStarPathUpdater implements PathUpdater {
        private SlashStarPathUpdater() {
        }

        public void update(HttpHandlerChain handlerChain, HttpHandler httpHandler, Request request) {
            request.setContextPath("");
            request.setPathInfo((Request.PathResolver) handlerChain.fullUrlPathResolver);
            request.setHttpHandlerPath("");
        }
    }

    public static class EmptyPathUpdater implements PathUpdater {
        private EmptyPathUpdater() {
        }

        public void update(HttpHandlerChain handlerChain, HttpHandler httpHandler, Request request) {
            request.setContextPath("");
            request.setPathInfo((String) null);
            request.setHttpHandlerPath((String) null);
        }
    }

    public static class FullUrlPathResolver implements Request.PathResolver {
        private final HttpHandler httpHandler;

        public FullUrlPathResolver(HttpHandler httpHandler2) {
            this.httpHandler = httpHandler2;
        }

        public String resolve(Request request) {
            try {
                RequestURIRef uriRef = request.getRequest().getRequestURIRef();
                uriRef.setDefaultURIEncoding(this.httpHandler.getRequestURIEncoding());
                DataChunk decodedURI = uriRef.getDecodedRequestURIBC(this.httpHandler.isAllowEncodedSlash());
                int pos = decodedURI.indexOf(';', 0);
                return pos < 0 ? decodedURI.toString() : decodedURI.toString(0, pos);
            } catch (CharConversionException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
