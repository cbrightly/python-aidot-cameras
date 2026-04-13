package org.glassfish.grizzly.http.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.naming.NamingContext;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.CharChunk;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.MessageBytes;
import org.glassfish.grizzly.utils.Charsets;
import org.slf4j.e;

public class Mapper {
    private static final String DEFAULT_SERVLET = System.getProperty("org.glassfish.grizzly.servlet.defaultServlet", "default");
    private static final String JSP_SERVLET = System.getProperty("org.glassfish.grizzly.servlet.jspServlet", "jsp");
    private static final CharChunk SLASH;
    private static boolean allowReplacement = false;
    private static final Logger logger = Grizzly.logger(Mapper.class);
    protected final Context context = new Context();
    private final Map<String, String> defaultContextPathsMap = new HashMap();
    protected String defaultHostName = null;
    protected Host[] hosts = new Host[0];
    private int port = 0;

    static {
        CharChunk charChunk = new CharChunk();
        SLASH = charChunk;
        try {
            charChunk.append('/');
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void setAllowReplacement(boolean ar) {
        allowReplacement = ar;
    }

    public static boolean allowReplacement() {
        return allowReplacement;
    }

    public void setPort(int port2) {
        this.port = port2;
    }

    public int getPort() {
        return this.port;
    }

    public String getDefaultHostName() {
        return this.defaultHostName;
    }

    public void setDefaultHostName(String defaultHostName2) {
        this.defaultHostName = defaultHostName2;
    }

    public synchronized void addHost(String name, String[] aliases, Object host) {
        Host[] newHosts = new Host[(this.hosts.length + 1)];
        Host newHost = new Host();
        ContextList contextList = new ContextList();
        Context[] defaultContexts = new Context[1];
        String[] defaultContextPaths = new String[1];
        newHost.name = name;
        newHost.contextList = contextList;
        newHost.object = host;
        newHost.defaultContexts = defaultContexts;
        newHost.defaultContextPaths = defaultContextPaths;
        Host oldElem = (Host) insertMapIgnoreCase(this.hosts, newHosts, newHost);
        if (oldElem == null) {
            this.hosts = newHosts;
        } else if (allowReplacement) {
            oldElem.object = host;
            contextList = oldElem.contextList;
        }
        for (String alias : aliases) {
            Host[] newHosts2 = new Host[(this.hosts.length + 1)];
            newHost = new Host();
            newHost.name = alias;
            newHost.contextList = contextList;
            newHost.defaultContexts = defaultContexts;
            newHost.defaultContextPaths = defaultContextPaths;
            newHost.object = host;
            if (insertMapIgnoreCase(this.hosts, newHosts2, newHost) == null) {
                this.hosts = newHosts2;
            }
        }
        String defaultContextPath = this.defaultContextPathsMap.get(name);
        if (defaultContextPath != null) {
            newHost.defaultContextPaths[0] = defaultContextPath;
        }
    }

    public synchronized void removeHost(String name) {
        int pos = findIgnoreCase((MapElement[]) this.hosts, name);
        if (pos >= 0) {
            Host[] hostArr = this.hosts;
            Object host = hostArr[pos].object;
            Host[] newHosts = new Host[(hostArr.length - 1)];
            if (removeMapIgnoreCase(hostArr, newHosts, name)) {
                this.hosts = newHosts;
            }
            for (Host newHost : newHosts) {
                if (newHost.object == host) {
                    Host[] hostArr2 = this.hosts;
                    Host[] newHosts2 = new Host[(hostArr2.length - 1)];
                    if (removeMapIgnoreCase(hostArr2, newHosts2, newHost.name)) {
                        this.hosts = newHosts2;
                    }
                }
            }
            this.defaultContextPathsMap.remove(name);
        }
    }

    public String[] getHosts() {
        String[] hostN = new String[this.hosts.length];
        int i = 0;
        while (true) {
            Host[] hostArr = this.hosts;
            if (i >= hostArr.length) {
                return hostN;
            }
            hostN[i] = hostArr[i].name;
            i++;
        }
    }

    public void setContext(String path, String[] welcomeResources, NamingContext resources) {
        Context context2 = this.context;
        context2.name = path;
        context2.welcomeResources = welcomeResources;
        context2.resources = resources;
    }

    public void addContext(String hostName, String path, Object context2, String[] welcomeResources, NamingContext resources) {
        addContext(hostName, path, context2, welcomeResources, resources, (List<AlternateDocBase>) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addContext(java.lang.String r18, java.lang.String r19, java.lang.Object r20, java.lang.String[] r21, org.glassfish.grizzly.http.server.naming.NamingContext r22, java.util.List<org.glassfish.grizzly.http.server.util.AlternateDocBase> r23) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r22
            org.glassfish.grizzly.http.server.util.Mapper$Host[] r0 = r1.hosts
            int r7 = findIgnoreCase((org.glassfish.grizzly.http.server.util.Mapper.MapElement[]) r0, (java.lang.String) r2)
            r8 = 0
            if (r7 >= 0) goto L_0x0025
            java.lang.String[] r9 = new java.lang.String[r8]
            java.lang.String r10 = ""
            r1.addHost(r2, r9, r10)
            org.glassfish.grizzly.http.server.util.Mapper$Host[] r0 = r1.hosts
            int r7 = findIgnoreCase((org.glassfish.grizzly.http.server.util.Mapper.MapElement[]) r0, (java.lang.String) r2)
            r9 = r7
            r7 = r0
            goto L_0x0027
        L_0x0025:
            r9 = r7
            r7 = r0
        L_0x0027:
            r0 = 1
            if (r9 >= 0) goto L_0x0041
            java.util.logging.Logger r10 = logger
            java.util.logging.Level r11 = java.util.logging.Level.FINE
            java.lang.String r12 = "No host found: {0} for Mapper listening on port: {1}"
            r13 = 2
            java.lang.Object[] r13 = new java.lang.Object[r13]
            r13[r8] = r2
            int r8 = r1.port
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r13[r0] = r8
            r10.log(r11, r12, r13)
            return
        L_0x0041:
            r10 = r7[r9]
            java.lang.String r11 = r10.name
            boolean r11 = r11.equalsIgnoreCase(r2)
            if (r11 == 0) goto L_0x00a1
            int r11 = slashCount(r19)
            monitor-enter(r10)
            org.glassfish.grizzly.http.server.util.Mapper$ContextList r12 = r10.contextList     // Catch:{ all -> 0x009a }
            org.glassfish.grizzly.http.server.util.Mapper$Context[] r13 = r12.contexts     // Catch:{ all -> 0x009a }
            int r14 = r12.nesting     // Catch:{ all -> 0x009a }
            if (r11 <= r14) goto L_0x005a
            r12.nesting = r11     // Catch:{ all -> 0x009a }
        L_0x005a:
            int r12 = r13.length     // Catch:{ all -> 0x009a }
            int r12 = r12 + r0
            org.glassfish.grizzly.http.server.util.Mapper$Context[] r0 = new org.glassfish.grizzly.http.server.util.Mapper.Context[r12]     // Catch:{ all -> 0x009a }
            org.glassfish.grizzly.http.server.util.Mapper$Context r12 = new org.glassfish.grizzly.http.server.util.Mapper$Context     // Catch:{ all -> 0x009a }
            r12.<init>()     // Catch:{ all -> 0x009a }
            r12.name = r3     // Catch:{ all -> 0x009a }
            r12.object = r4     // Catch:{ all -> 0x009a }
            r12.welcomeResources = r5     // Catch:{ all -> 0x009a }
            r12.resources = r6     // Catch:{ all -> 0x009a }
            r14 = r23
            r12.alternateDocBases = r14     // Catch:{ all -> 0x009f }
            org.glassfish.grizzly.http.server.util.Mapper$MapElement r15 = insertMap(r13, r0, r12)     // Catch:{ all -> 0x009f }
            org.glassfish.grizzly.http.server.util.Mapper$Context r15 = (org.glassfish.grizzly.http.server.util.Mapper.Context) r15     // Catch:{ all -> 0x009f }
            if (r15 != 0) goto L_0x008e
            org.glassfish.grizzly.http.server.util.Mapper$ContextList r8 = r10.contextList     // Catch:{ all -> 0x009f }
            r8.contexts = r0     // Catch:{ all -> 0x009f }
            java.lang.String[] r8 = r10.defaultContextPaths     // Catch:{ all -> 0x009f }
            r16 = 0
            r8 = r8[r16]     // Catch:{ all -> 0x009f }
            boolean r8 = r3.equals(r8)     // Catch:{ all -> 0x009f }
            if (r8 == 0) goto L_0x0098
            org.glassfish.grizzly.http.server.util.Mapper$Context[] r8 = r10.defaultContexts     // Catch:{ all -> 0x009f }
            r16 = 0
            r8[r16] = r12     // Catch:{ all -> 0x009f }
            goto L_0x0098
        L_0x008e:
            boolean r8 = allowReplacement     // Catch:{ all -> 0x009f }
            if (r8 == 0) goto L_0x0098
            r15.object = r4     // Catch:{ all -> 0x009f }
            r15.welcomeResources = r5     // Catch:{ all -> 0x009f }
            r15.resources = r6     // Catch:{ all -> 0x009f }
        L_0x0098:
            monitor-exit(r10)     // Catch:{ all -> 0x009f }
            goto L_0x00a3
        L_0x009a:
            r0 = move-exception
            r14 = r23
        L_0x009d:
            monitor-exit(r10)     // Catch:{ all -> 0x009f }
            throw r0
        L_0x009f:
            r0 = move-exception
            goto L_0x009d
        L_0x00a1:
            r14 = r23
        L_0x00a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.util.Mapper.addContext(java.lang.String, java.lang.String, java.lang.Object, java.lang.String[], org.glassfish.grizzly.http.server.naming.NamingContext, java.util.List):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeContext(java.lang.String r12, java.lang.String r13) {
        /*
            r11 = this;
            org.glassfish.grizzly.http.server.util.Mapper$Host[] r0 = r11.hosts
            int r1 = findIgnoreCase((org.glassfish.grizzly.http.server.util.Mapper.MapElement[]) r0, (java.lang.String) r12)
            if (r1 >= 0) goto L_0x0009
            return
        L_0x0009:
            r2 = r0[r1]
            java.lang.String r3 = r2.name
            boolean r3 = r3.equalsIgnoreCase(r12)
            if (r3 == 0) goto L_0x004a
            monitor-enter(r2)
            org.glassfish.grizzly.http.server.util.Mapper$ContextList r3 = r2.contextList     // Catch:{ all -> 0x0047 }
            org.glassfish.grizzly.http.server.util.Mapper$Context[] r3 = r3.contexts     // Catch:{ all -> 0x0047 }
            int r4 = r3.length     // Catch:{ all -> 0x0047 }
            if (r4 != 0) goto L_0x001d
            monitor-exit(r2)     // Catch:{ all -> 0x0047 }
            return
        L_0x001d:
            int r4 = r3.length     // Catch:{ all -> 0x0047 }
            int r4 = r4 + -1
            org.glassfish.grizzly.http.server.util.Mapper$Context[] r4 = new org.glassfish.grizzly.http.server.util.Mapper.Context[r4]     // Catch:{ all -> 0x0047 }
            boolean r5 = removeMap(r3, r4, r13)     // Catch:{ all -> 0x0047 }
            if (r5 == 0) goto L_0x0045
            org.glassfish.grizzly.http.server.util.Mapper$ContextList r5 = r2.contextList     // Catch:{ all -> 0x0047 }
            r5.contexts = r4     // Catch:{ all -> 0x0047 }
            r6 = 0
            r5.nesting = r6     // Catch:{ all -> 0x0047 }
            int r5 = r4.length     // Catch:{ all -> 0x0047 }
        L_0x0030:
            if (r6 >= r5) goto L_0x0045
            r7 = r4[r6]     // Catch:{ all -> 0x0047 }
            java.lang.String r8 = r7.name     // Catch:{ all -> 0x0047 }
            int r8 = slashCount(r8)     // Catch:{ all -> 0x0047 }
            org.glassfish.grizzly.http.server.util.Mapper$ContextList r9 = r2.contextList     // Catch:{ all -> 0x0047 }
            int r10 = r9.nesting     // Catch:{ all -> 0x0047 }
            if (r8 <= r10) goto L_0x0042
            r9.nesting = r8     // Catch:{ all -> 0x0047 }
        L_0x0042:
            int r6 = r6 + 1
            goto L_0x0030
        L_0x0045:
            monitor-exit(r2)     // Catch:{ all -> 0x0047 }
            goto L_0x004a
        L_0x0047:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0047 }
            throw r3
        L_0x004a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.util.Mapper.removeContext(java.lang.String, java.lang.String):void");
    }

    public String[] getContextNames() {
        List<String> list = new ArrayList<>();
        for (Host host : this.hosts) {
            int j = 0;
            while (true) {
                Context[] contextArr = host.contextList.contexts;
                if (j >= contextArr.length) {
                    break;
                }
                String cname = contextArr[j].name;
                StringBuilder sb = new StringBuilder();
                sb.append("//");
                sb.append(host.name);
                String str = "/";
                if (cname.startsWith(str)) {
                    str = cname;
                }
                sb.append(str);
                list.add(sb.toString());
                j++;
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public void addWrapper(String hostName, String contextPath, String path, Object wrapper) {
        addWrapper(hostName, contextPath, path, wrapper, false);
    }

    public void addWrapper(String hostName, String contextPath, String path, Object wrapper, boolean jspWildCard) {
        addWrapper(hostName, contextPath, path, wrapper, jspWildCard, (String) null, false);
    }

    public void addWrapper(String hostName, String contextPath, String path, Object wrapper, boolean jspWildCard, String servletName, boolean isEmptyPathSpecial) {
        String str = hostName;
        String str2 = contextPath;
        Host[] newHosts = this.hosts;
        int pos = findIgnoreCase((MapElement[]) newHosts, str);
        if (pos >= 0) {
            Host host = newHosts[pos];
            if (host.name.equalsIgnoreCase(str)) {
                Context[] contexts = host.contextList.contexts;
                int pos2 = find((MapElement[]) contexts, str2);
                if (pos2 < 0) {
                    logger.log(Level.SEVERE, "No context found: {0}", str2);
                    return;
                }
                Context ctx = contexts[pos2];
                if (ctx.name.equals(str2)) {
                    addWrapper(ctx, path, wrapper, jspWildCard, servletName, isEmptyPathSpecial);
                }
            }
        }
    }

    public void addWrapper(String path, Object wrapper) {
        addWrapper(this.context, path, wrapper);
    }

    public void addWrapper(String path, Object wrapper, boolean jspWildCard, boolean isEmptyPathSpecial) {
        addWrapper(this.context, path, wrapper, jspWildCard, isEmptyPathSpecial);
    }

    public void addWrapper(String path, Object wrapper, boolean jspWildCard, String servletName, boolean isEmptyPathSpecial) {
        addWrapper(this.context, path, wrapper, jspWildCard, servletName, isEmptyPathSpecial);
    }

    /* access modifiers changed from: protected */
    public void addWrapper(Context context2, String path, Object wrapper) {
        addWrapper(context2, path, wrapper, false, false);
    }

    /* access modifiers changed from: protected */
    public void addWrapper(Context context2, String path, Object wrapper, boolean jspWildCard, boolean isEmptyPathSpecial) {
        addWrapper(context2, path, wrapper, jspWildCard, (String) null, isEmptyPathSpecial);
    }

    /* access modifiers changed from: protected */
    public void addWrapper(Context context2, String path, Object wrapper, boolean jspWildCard, String servletName, boolean isEmptyPathSpecial) {
        synchronized (context2) {
            Wrapper newWrapper = new Wrapper();
            newWrapper.object = wrapper;
            newWrapper.jspWildCard = jspWildCard;
            newWrapper.servletName = servletName;
            newWrapper.path = path;
            if (path.endsWith("/*")) {
                newWrapper.name = path.substring(0, path.length() - 2);
                Wrapper[] oldWrappers = context2.wildcardWrappers;
                Wrapper[] newWrappers = new Wrapper[(oldWrappers.length + 1)];
                Wrapper oldElem = (Wrapper) insertMap(oldWrappers, newWrappers, newWrapper);
                if (oldElem == null) {
                    context2.wildcardWrappers = newWrappers;
                    int slashCount = slashCount(newWrapper.name);
                    if (slashCount > context2.nesting) {
                        context2.nesting = slashCount;
                    }
                } else if (allowReplacement != 0) {
                    oldElem.object = wrapper;
                    oldElem.jspWildCard = jspWildCard;
                }
            } else if (path.startsWith("*.")) {
                newWrapper.name = path.substring(2);
                Wrapper[] oldWrappers2 = context2.extensionWrappers;
                Wrapper[] newWrappers2 = new Wrapper[(oldWrappers2.length + 1)];
                Wrapper oldElem2 = (Wrapper) insertMap(oldWrappers2, newWrappers2, newWrapper);
                if (oldElem2 == null) {
                    context2.extensionWrappers = newWrappers2;
                } else if (allowReplacement) {
                    oldElem2.object = wrapper;
                    oldElem2.jspWildCard = jspWildCard;
                }
            } else {
                boolean isSlashPath = "/".equals(path);
                if (isSlashPath) {
                    newWrapper.name = "";
                    context2.defaultWrapper = newWrapper;
                }
                if (!isSlashPath || !DEFAULT_SERVLET.equals(servletName)) {
                    newWrapper.name = path;
                    if (!isEmptyPathSpecial || path.length() != 0) {
                        Wrapper[] oldWrappers3 = context2.exactWrappers;
                        Wrapper[] newWrappers3 = new Wrapper[(oldWrappers3.length + 1)];
                        Wrapper oldElem3 = (Wrapper) insertMap(oldWrappers3, newWrappers3, newWrapper);
                        if (oldElem3 == null) {
                            context2.exactWrappers = newWrappers3;
                        } else if (allowReplacement) {
                            oldElem3.object = wrapper;
                            oldElem3.jspWildCard = jspWildCard;
                        }
                    } else {
                        context2.emptyPathWrapper = newWrapper;
                    }
                }
            }
        }
    }

    public void removeWrapper(String path) {
        removeWrapper(this.context, path);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        r3 = r2.contextList.contexts;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeWrapper(java.lang.String r8, java.lang.String r9, java.lang.String r10) {
        /*
            r7 = this;
            org.glassfish.grizzly.http.server.util.Mapper$Host[] r0 = r7.hosts
            int r1 = findIgnoreCase((org.glassfish.grizzly.http.server.util.Mapper.MapElement[]) r0, (java.lang.String) r8)
            if (r1 >= 0) goto L_0x0009
            return
        L_0x0009:
            r2 = r0[r1]
            java.lang.String r3 = r2.name
            boolean r3 = r3.equalsIgnoreCase(r8)
            if (r3 == 0) goto L_0x002b
            org.glassfish.grizzly.http.server.util.Mapper$ContextList r3 = r2.contextList
            org.glassfish.grizzly.http.server.util.Mapper$Context[] r3 = r3.contexts
            int r4 = find((org.glassfish.grizzly.http.server.util.Mapper.MapElement[]) r3, (java.lang.String) r9)
            if (r4 >= 0) goto L_0x001e
            return
        L_0x001e:
            r5 = r3[r4]
            java.lang.String r6 = r5.name
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x002b
            r7.removeWrapper(r5, r10)
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.util.Mapper.removeWrapper(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public void removeWrapper(Context context2, String path) {
        synchronized (context2) {
            if (path.endsWith("/*")) {
                String name = path.substring(0, path.length() - 2);
                Wrapper[] oldWrappers = context2.wildcardWrappers;
                Wrapper[] newWrappers = new Wrapper[(oldWrappers.length - 1)];
                if (removeMap(oldWrappers, newWrappers, name)) {
                    context2.nesting = 0;
                    for (Wrapper newWrapper : newWrappers) {
                        int slashCount = slashCount(newWrapper.name);
                        if (slashCount > context2.nesting) {
                            context2.nesting = slashCount;
                        }
                    }
                    context2.wildcardWrappers = newWrappers;
                }
            } else if (path.startsWith("*.")) {
                String name2 = path.substring(2);
                Wrapper[] oldWrappers2 = context2.extensionWrappers;
                Wrapper[] newWrappers2 = new Wrapper[(oldWrappers2.length - 1)];
                if (removeMap(oldWrappers2, newWrappers2, name2)) {
                    context2.extensionWrappers = newWrappers2;
                }
            } else if ("/".equals(path)) {
                context2.defaultWrapper = null;
            } else {
                Wrapper[] oldWrappers3 = context2.exactWrappers;
                Wrapper[] newWrappers3 = new Wrapper[(oldWrappers3.length - 1)];
                if (removeMap(oldWrappers3, newWrappers3, path)) {
                    context2.exactWrappers = newWrappers3;
                }
            }
        }
    }

    public String getWrappersString(String host, String context2) {
        String[] names = getWrapperNames(host, context2);
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name);
            sb.append(":");
        }
        return sb.toString();
    }

    public String[] getWrapperNames(String host, String context2) {
        List<String> list = new ArrayList<>();
        if (host == null) {
            host = "";
        }
        if (context2 == null) {
            context2 = "";
        }
        for (Host host1 : this.hosts) {
            if (host.equals(host1.name)) {
                int j = 0;
                while (true) {
                    Context[] contextArr = host1.contextList.contexts;
                    if (j >= contextArr.length) {
                        break;
                    }
                    if (context2.equals(contextArr[j].name)) {
                        Context ctx = host1.contextList.contexts[j];
                        list.add(ctx.defaultWrapper.path);
                        int k = 0;
                        while (true) {
                            Wrapper[] wrapperArr = ctx.exactWrappers;
                            if (k >= wrapperArr.length) {
                                break;
                            }
                            list.add(wrapperArr[k].path);
                            k++;
                        }
                        for (int k2 = 0; k2 < ctx.wildcardWrappers.length; k2++) {
                            list.add(ctx.wildcardWrappers[k2].path + e.ANY_MARKER);
                        }
                        for (int k3 = 0; k3 < ctx.extensionWrappers.length; k3++) {
                            list.add("*." + ctx.extensionWrappers[k3].path);
                        }
                    }
                    j++;
                }
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public void setDefaultContextPath(String hostName, String defaultContextPath) {
        if (defaultContextPath != null) {
            this.defaultContextPathsMap.put(hostName, defaultContextPath);
        }
        int pos = findIgnoreCase((MapElement[]) this.hosts, hostName);
        if (pos >= 0) {
            Host[] hostArr = this.hosts;
            hostArr[pos].defaultContextPaths[0] = defaultContextPath;
            if (defaultContextPath != null) {
                addDefaultContext(hostArr[pos], defaultContextPath);
                return;
            }
            hostArr[pos].defaultContexts[0] = null;
            this.defaultContextPathsMap.remove(hostName);
        }
    }

    private void addDefaultContext(Host host, String defaultContextPath) {
        boolean defaultContextFound = false;
        Context[] contexts = host.contextList.contexts;
        if (contexts != null) {
            int length = contexts.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Context context1 = contexts[i];
                if (context1.name.equals(defaultContextPath)) {
                    host.defaultContexts[0] = context1;
                    defaultContextFound = true;
                    break;
                }
                i++;
            }
        }
        if (!defaultContextFound) {
            throw new Exception("No context matching " + defaultContextPath + " deployed on virtual server " + host.name);
        }
    }

    public void mapUriWithSemicolon(HttpRequestPacket requestPacket, DataChunk decodedURI, MappingData mappingData, int semicolonPos) {
        CharChunk charChunk = decodedURI.getCharChunk();
        int oldEnd = charChunk.getEnd();
        if (semicolonPos == 0) {
            semicolonPos = decodedURI.indexOf(';', 0);
        }
        DataChunk localDecodedURI = decodedURI;
        if (semicolonPos >= 0) {
            charChunk.setEnd(semicolonPos);
            localDecodedURI = mappingData.tmpMapperDC;
            localDecodedURI.duplicate(decodedURI);
        }
        map(requestPacket, localDecodedURI, mappingData);
        charChunk.setEnd(oldEnd);
    }

    public void mapUriWithSemicolon(DataChunk serverName, DataChunk decodedURI, MappingData mappingData, int semicolonPos) {
        CharChunk charChunk = decodedURI.getCharChunk();
        int oldEnd = charChunk.getEnd();
        if (semicolonPos == 0) {
            semicolonPos = decodedURI.indexOf(';', 0);
        }
        DataChunk localDecodedURI = decodedURI;
        if (semicolonPos >= 0) {
            charChunk.setEnd(semicolonPos);
            localDecodedURI = mappingData.tmpMapperDC;
            localDecodedURI.duplicate(decodedURI);
        }
        map(serverName, localDecodedURI, mappingData);
        charChunk.setEnd(oldEnd);
    }

    public void map(DataChunk host, DataChunk uri, MappingData mappingData) {
        if (host.isNull()) {
            host.getCharChunk().append(this.defaultHostName);
        } else if (host.getLength() == 0) {
            throw new Exception("Host is not set");
        }
        host.toChars(Constants.DEFAULT_HTTP_CHARSET);
        uri.toChars(Charsets.UTF8_CHARSET);
        internalMap(host.getCharChunk(), uri.getCharChunk(), mappingData);
    }

    public void map(HttpRequestPacket requestPacket, DataChunk uri, MappingData mappingData) {
        CharChunk hostCC;
        if (this.hosts.length > 1) {
            DataChunk host = requestPacket.serverName();
            if (host.isNull()) {
                host.getCharChunk().append(this.defaultHostName);
            } else if (host.getLength() != 0) {
                host.toChars(Constants.DEFAULT_HTTP_CHARSET);
            } else {
                throw new Exception("Host is not set");
            }
            hostCC = host.getCharChunk();
        } else {
            hostCC = null;
        }
        uri.toChars(Charsets.UTF8_CHARSET);
        internalMap(hostCC, uri.getCharChunk(), mappingData);
    }

    public void map(MessageBytes uri, MappingData mappingData) {
        uri.toChars();
        CharChunk uricc = uri.getCharChunk();
        uricc.setLimit(-1);
        internalMapWrapper(this.context, uricc, mappingData);
    }

    private void internalMap(CharChunk host, CharChunk uri, MappingData mappingData) {
        int lastSlash;
        int pos;
        CharChunk charChunk = host;
        CharChunk charChunk2 = uri;
        MappingData mappingData2 = mappingData;
        charChunk2.setLimit(-1);
        Context[] contexts = null;
        Context ctx = null;
        int nesting = 0;
        int hostPos = -1;
        if (mappingData2.host == null) {
            Host[] newHosts = this.hosts;
            int pos2 = (charChunk == null || host.isNull()) ? -1 : findIgnoreCase((MapElement[]) newHosts, charChunk);
            if (pos2 == -1 || !charChunk.equalsIgnoreCase((CharSequence) newHosts[pos2].name)) {
                String str = this.defaultHostName;
                if (str != null && (pos = findIgnoreCase((MapElement[]) newHosts, str)) != -1 && this.defaultHostName.equalsIgnoreCase(newHosts[pos].name)) {
                    mappingData2.host = newHosts[pos].object;
                    hostPos = pos;
                    contexts = newHosts[pos].contextList.contexts;
                    nesting = newHosts[pos].contextList.nesting;
                } else {
                    return;
                }
            } else {
                mappingData2.host = newHosts[pos2].object;
                hostPos = pos2;
                contexts = newHosts[pos2].contextList.contexts;
                nesting = newHosts[pos2].contextList.nesting;
            }
        }
        if (mappingData2.context == null) {
            boolean found = false;
            int pos3 = find((MapElement[]) contexts, charChunk2);
            if (pos3 == -1) {
                Host[] hostArr = this.hosts;
                if (hostArr[hostPos].defaultContexts[0] != null) {
                    ctx = hostArr[hostPos].defaultContexts[0];
                    mappingData2.context = ctx.object;
                    mappingData2.contextPath.setString(ctx.name);
                    found = true;
                    mappingData2.isDefaultContext = true;
                } else {
                    return;
                }
            }
            if (!found) {
                int lastSlash2 = -1;
                int uriEnd = uri.getEnd();
                while (true) {
                    if (pos3 < 0) {
                        break;
                    }
                    if (contexts != null && charChunk2.startsWith(contexts[pos3].name)) {
                        int length = contexts[pos3].name.length();
                        if (uri.getLength() != length) {
                            if (charChunk2.startsWithIgnoreCase("/", length)) {
                                found = true;
                                break;
                            }
                        } else {
                            found = true;
                            break;
                        }
                    }
                    if (lastSlash2 == -1) {
                        lastSlash = nthSlash(charChunk2, nesting + 1);
                    } else {
                        lastSlash = lastSlash(uri);
                    }
                    lastSlash2 = lastSlash;
                    charChunk2.setEnd(lastSlash2);
                    pos3 = find((MapElement[]) contexts, charChunk2);
                }
                charChunk2.setEnd(uriEnd);
                if (found) {
                    ctx = contexts[pos3];
                } else if (contexts == null || !"".equals(contexts[0].name)) {
                    Host[] hostArr2 = this.hosts;
                    if (hostArr2[hostPos].defaultContexts[0] != null) {
                        Context ctx2 = hostArr2[hostPos].defaultContexts[0];
                        mappingData2.isDefaultContext = true;
                        ctx = ctx2;
                    }
                } else {
                    ctx = contexts[0];
                }
                if (ctx != null) {
                    mappingData2.context = ctx.object;
                    mappingData2.contextPath.setString(ctx.name);
                }
            }
        }
        if (ctx != null && mappingData2.wrapper == null) {
            internalMapWrapper(ctx, charChunk2, mappingData2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:128:0x025b  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x02e1 A[SYNTHETIC, Splitter:B:154:0x02e1] */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x030b  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ea  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void internalMapWrapper(org.glassfish.grizzly.http.server.util.Mapper.Context r24, org.glassfish.grizzly.http.util.CharChunk r25, org.glassfish.grizzly.http.server.util.MappingData r26) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = r26
            int r0 = r25.getStart()
            int r5 = r25.getEnd()
            r6 = 0
            boolean r7 = r4.isDefaultContext
            r8 = 47
            if (r7 == 0) goto L_0x001d
            r7 = r0
            r9 = r7
            r7 = r6
            r6 = r5
            r5 = r0
            goto L_0x003e
        L_0x001d:
            java.lang.String r7 = r2.name
            int r7 = r7.length()
            int r9 = r5 - r0
            if (r7 == r9) goto L_0x002d
            int r9 = r0 + r7
            r7 = r6
            r6 = r5
            r5 = r0
            goto L_0x003e
        L_0x002d:
            r6 = 1
            r3.append((char) r8)
            int r0 = r25.getStart()
            int r5 = r25.getEnd()
            int r9 = r0 + r7
            r7 = r6
            r6 = r5
            r5 = r0
        L_0x003e:
            r3.setStart(r9)
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper r0 = r2.emptyPathWrapper
            java.lang.String r10 = "/"
            r11 = 1
            if (r0 == 0) goto L_0x006d
            org.glassfish.grizzly.http.util.CharChunk r0 = SLASH
            boolean r0 = r3.equals((org.glassfish.grizzly.http.util.CharChunk) r0)
            if (r0 == 0) goto L_0x006d
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper r0 = r2.emptyPathWrapper
            java.lang.Object r0 = r0.object
            r4.wrapper = r0
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.requestPath
            java.lang.String r12 = ""
            r0.setString(r12)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.wrapperPath
            r0.setString(r12)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.pathInfo
            r0.setString(r10)
            r4.mappingType = r11
            r4.descriptorPath = r10
            r4.matchedPath = r10
        L_0x006d:
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper[] r12 = r2.exactWrappers
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x0076
            r1.internalMapExactWrapper(r12, r3, r4)
        L_0x0076:
            r0 = 0
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper[] r13 = r2.wildcardWrappers
            java.lang.Object r14 = r4.wrapper
            r15 = 0
            if (r14 != 0) goto L_0x00ac
            int r14 = r2.nesting
            r1.internalMapWildcardWrapper(r13, r14, r3, r4)
            java.lang.Object r14 = r4.wrapper
            if (r14 == 0) goto L_0x00ac
            boolean r14 = r4.jspWildCard
            if (r14 == 0) goto L_0x00ac
            char[] r14 = r25.getBuffer()
            int r16 = r6 + -1
            char r11 = r14[r16]
            if (r11 != r8) goto L_0x009a
            r4.wrapper = r15
            r0 = 1
            r8 = r0
            goto L_0x00ad
        L_0x009a:
            org.glassfish.grizzly.http.util.DataChunk r11 = r4.wrapperPath
            int r15 = r25.getStart()
            int r8 = r25.getEnd()
            r11.setChars(r14, r15, r8)
            org.glassfish.grizzly.http.util.DataChunk r8 = r4.pathInfo
            r8.recycle()
        L_0x00ac:
            r8 = r0
        L_0x00ad:
            if (r7 == 0) goto L_0x00db
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x00b5
            r0 = 1
            goto L_0x00b6
        L_0x00b5:
            r0 = 0
        L_0x00b6:
            if (r0 != 0) goto L_0x00ca
            org.glassfish.grizzly.http.util.DataChunk r14 = r4.wrapperPath
            java.lang.String r14 = r14.toString()
            if (r14 == 0) goto L_0x00c8
            int r15 = r14.length()
            if (r15 != 0) goto L_0x00c8
            r15 = 1
            goto L_0x00c9
        L_0x00c8:
            r15 = 0
        L_0x00c9:
            r0 = r15
        L_0x00ca:
            if (r0 == 0) goto L_0x00db
            org.glassfish.grizzly.http.util.DataChunk r10 = r4.redirectPath
            char[] r11 = r25.getBuffer()
            r10.setChars(r11, r5, r6)
            int r10 = r6 + -1
            r3.setEnd(r10)
            return
        L_0x00db:
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper[] r14 = r2.extensionWrappers
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x00e6
            if (r8 != 0) goto L_0x00e6
            r1.internalMapExtensionWrapper(r14, r3, r4)
        L_0x00e6:
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x025b
            r0 = r8
            if (r0 != 0) goto L_0x0100
            if (r6 <= 0) goto L_0x0100
            char[] r18 = r25.getBuffer()
            int r19 = r6 + -1
            char r15 = r18[r19]
            r11 = 47
            if (r15 != r11) goto L_0x00fd
            r11 = 1
            goto L_0x00fe
        L_0x00fd:
            r11 = 0
        L_0x00fe:
            r0 = r11
            goto L_0x0101
        L_0x0100:
            r11 = r0
        L_0x0101:
            if (r11 == 0) goto L_0x0253
            r0 = 0
            r15 = r0
        L_0x0105:
            java.lang.String[] r0 = r2.welcomeResources
            int r0 = r0.length
            if (r15 >= r0) goto L_0x01ea
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x01ea
            r3.setStart(r5)
            r3.setEnd(r6)
            java.lang.String[] r0 = r2.welcomeResources
            r17 = r7
            r7 = r0[r15]
            r0 = r0[r15]
            int r0 = r0.length()
            r18 = r11
            r11 = 0
            r3.append((java.lang.String) r7, (int) r11, (int) r0)
            r3.setStart(r9)
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x01da
            org.glassfish.grizzly.http.server.naming.NamingContext r0 = r2.resources
            if (r0 == 0) goto L_0x01da
            r7 = 0
            java.lang.String r11 = r25.toString()
            java.util.List<org.glassfish.grizzly.http.server.util.AlternateDocBase> r0 = r2.alternateDocBases
            if (r0 == 0) goto L_0x015f
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0141
            goto L_0x015f
        L_0x0141:
            java.util.List<org.glassfish.grizzly.http.server.util.AlternateDocBase> r0 = r2.alternateDocBases
            org.glassfish.grizzly.http.server.util.AlternateDocBase r20 = org.glassfish.grizzly.http.server.util.AlternateDocBase.findMatch(r11, r0)
            if (r20 == 0) goto L_0x0155
            org.glassfish.grizzly.http.server.naming.DirContext r0 = r20.getResources()     // Catch:{ NamingException -> 0x0153 }
            java.lang.Object r0 = r0.lookup(r11)     // Catch:{ NamingException -> 0x0153 }
            r7 = r0
        L_0x0152:
            goto L_0x0169
        L_0x0153:
            r0 = move-exception
            goto L_0x0152
        L_0x0155:
            org.glassfish.grizzly.http.server.naming.NamingContext r0 = r2.resources     // Catch:{ NamingException -> 0x015d }
            java.lang.Object r0 = r0.lookup(r11)     // Catch:{ NamingException -> 0x015d }
            r7 = r0
            goto L_0x0169
        L_0x015d:
            r0 = move-exception
            goto L_0x0169
        L_0x015f:
            org.glassfish.grizzly.http.server.naming.NamingContext r0 = r2.resources     // Catch:{ NamingException -> 0x0167 }
            java.lang.Object r0 = r0.lookup(r11)     // Catch:{ NamingException -> 0x0167 }
            r7 = r0
        L_0x0166:
            goto L_0x0169
        L_0x0167:
            r0 = move-exception
            goto L_0x0166
        L_0x0169:
            if (r7 == 0) goto L_0x01d3
            boolean r0 = r7 instanceof org.glassfish.grizzly.http.server.naming.DirContext
            if (r0 != 0) goto L_0x01d3
            r1.internalMapExactWrapper(r12, r3, r4)
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x017b
            int r0 = r2.nesting
            r1.internalMapWildcardWrapper(r13, r0, r3, r4)
        L_0x017b:
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x0182
            r1.internalMapExtensionWrapper(r14, r3, r4)
        L_0x0182:
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x01cc
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper r0 = r2.defaultWrapper
            if (r0 == 0) goto L_0x01cc
            r20 = r7
            java.lang.Object r7 = r0.object
            r4.wrapper = r7
            java.lang.String r0 = r0.servletName
            r4.servletName = r0
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.requestPath
            char[] r7 = r25.getBuffer()
            r21 = r8
            int r8 = r25.getStart()
            r22 = r14
            int r14 = r25.getEnd()
            r0.setChars(r7, r8, r14)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.wrapperPath
            char[] r7 = r25.getBuffer()
            int r8 = r25.getStart()
            int r14 = r25.getEnd()
            r0.setChars(r7, r8, r14)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.requestPath
            r0.setString(r11)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.wrapperPath
            r0.setString(r11)
            r7 = 2
            r4.mappingType = r7
            r4.descriptorPath = r10
            r4.matchedPath = r10
            goto L_0x01de
        L_0x01cc:
            r20 = r7
            r21 = r8
            r22 = r14
            goto L_0x01de
        L_0x01d3:
            r20 = r7
            r21 = r8
            r22 = r14
            goto L_0x01de
        L_0x01da:
            r21 = r8
            r22 = r14
        L_0x01de:
            int r15 = r15 + 1
            r7 = r17
            r11 = r18
            r8 = r21
            r14 = r22
            goto L_0x0105
        L_0x01ea:
            r17 = r7
            r21 = r8
            r18 = r11
            r22 = r14
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x024a
            r0 = 0
        L_0x01f7:
            java.lang.String[] r7 = r2.welcomeResources
            int r7 = r7.length
            if (r0 >= r7) goto L_0x0247
            java.lang.Object r7 = r4.wrapper
            if (r7 != 0) goto L_0x0247
            r3.setStart(r5)
            r3.setEnd(r6)
            java.lang.String[] r7 = r2.welcomeResources
            r8 = r7[r0]
            r7 = r7[r0]
            int r7 = r7.length()
            r11 = 0
            r3.append((java.lang.String) r8, (int) r11, (int) r7)
            r3.setStart(r9)
            r1.internalMapExactWrapper(r12, r3, r4)
            java.lang.Object r7 = r4.wrapper
            if (r7 != 0) goto L_0x0223
            int r7 = r2.nesting
            r1.internalMapWildcardWrapper(r13, r7, r3, r4)
        L_0x0223:
            java.lang.Object r7 = r4.wrapper
            if (r7 != 0) goto L_0x022d
            r7 = r22
            r1.internalMapExtensionWrapper(r7, r3, r4)
            goto L_0x022f
        L_0x022d:
            r7 = r22
        L_0x022f:
            java.lang.Object r8 = r4.wrapper
            if (r8 == 0) goto L_0x0241
            java.lang.String r8 = JSP_SERVLET
            java.lang.String r14 = r4.servletName
            boolean r8 = r8.equals(r14)
            if (r8 == 0) goto L_0x0241
            r8 = 0
            r4.wrapper = r8
            goto L_0x0242
        L_0x0241:
            r8 = 0
        L_0x0242:
            int r0 = r0 + 1
            r22 = r7
            goto L_0x01f7
        L_0x0247:
            r7 = r22
            goto L_0x024c
        L_0x024a:
            r7 = r22
        L_0x024c:
            r3.setStart(r9)
            r3.setEnd(r6)
            goto L_0x0260
        L_0x0253:
            r17 = r7
            r21 = r8
            r18 = r11
            r7 = r14
            goto L_0x0260
        L_0x025b:
            r17 = r7
            r21 = r8
            r7 = r14
        L_0x0260:
            java.lang.Object r0 = r4.wrapper
            if (r0 != 0) goto L_0x0315
            if (r21 != 0) goto L_0x0315
            org.glassfish.grizzly.http.server.util.Mapper$Wrapper r0 = r2.defaultWrapper
            if (r0 == 0) goto L_0x02a1
            java.lang.Object r8 = r0.object
            r4.wrapper = r8
            java.lang.String r0 = r0.servletName
            r4.servletName = r0
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.requestPath
            char[] r8 = r25.getBuffer()
            int r11 = r25.getStart()
            int r14 = r25.getEnd()
            r0.setChars(r8, r11, r14)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.wrapperPath
            char[] r8 = r25.getBuffer()
            int r11 = r25.getStart()
            int r14 = r25.getEnd()
            r0.setChars(r8, r11, r14)
            r8 = 2
            r4.mappingType = r8
            r4.descriptorPath = r10
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.requestPath
            java.lang.String r0 = r0.toString()
            r4.matchedPath = r0
        L_0x02a1:
            char[] r8 = r25.getBuffer()
            org.glassfish.grizzly.http.server.naming.NamingContext r0 = r2.resources
            if (r0 == 0) goto L_0x0315
            if (r6 <= 0) goto L_0x0315
            int r0 = r6 + -1
            char r0 = r8[r0]
            r10 = 47
            if (r0 == r10) goto L_0x0315
            r10 = 0
            java.lang.String r11 = r25.toString()
            java.util.List<org.glassfish.grizzly.http.server.util.AlternateDocBase> r0 = r2.alternateDocBases
            if (r0 == 0) goto L_0x02e1
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x02c3
            goto L_0x02e1
        L_0x02c3:
            java.util.List<org.glassfish.grizzly.http.server.util.AlternateDocBase> r0 = r2.alternateDocBases
            org.glassfish.grizzly.http.server.util.AlternateDocBase r14 = org.glassfish.grizzly.http.server.util.AlternateDocBase.findMatch(r11, r0)
            if (r14 == 0) goto L_0x02d7
            org.glassfish.grizzly.http.server.naming.DirContext r0 = r14.getResources()     // Catch:{ NamingException -> 0x02d5 }
            java.lang.Object r0 = r0.lookup(r11)     // Catch:{ NamingException -> 0x02d5 }
            r10 = r0
        L_0x02d4:
            goto L_0x02eb
        L_0x02d5:
            r0 = move-exception
            goto L_0x02d4
        L_0x02d7:
            org.glassfish.grizzly.http.server.naming.NamingContext r0 = r2.resources     // Catch:{ NamingException -> 0x02df }
            java.lang.Object r0 = r0.lookup(r11)     // Catch:{ NamingException -> 0x02df }
            r10 = r0
            goto L_0x02eb
        L_0x02df:
            r0 = move-exception
            goto L_0x02eb
        L_0x02e1:
            org.glassfish.grizzly.http.server.naming.NamingContext r0 = r2.resources     // Catch:{ NamingException -> 0x02e9 }
            java.lang.Object r0 = r0.lookup(r11)     // Catch:{ NamingException -> 0x02e9 }
            r10 = r0
        L_0x02e8:
            goto L_0x02eb
        L_0x02e9:
            r0 = move-exception
            goto L_0x02e8
        L_0x02eb:
            if (r10 == 0) goto L_0x030b
            boolean r0 = r10 instanceof org.glassfish.grizzly.http.server.naming.DirContext
            if (r0 == 0) goto L_0x030b
            r3.setStart(r5)
            r14 = 47
            r3.append((char) r14)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.redirectPath
            char[] r14 = r25.getBuffer()
            int r15 = r25.getStart()
            int r1 = r25.getEnd()
            r0.setChars(r14, r15, r1)
            goto L_0x0315
        L_0x030b:
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.requestPath
            r0.setString(r11)
            org.glassfish.grizzly.http.util.DataChunk r0 = r4.wrapperPath
            r0.setString(r11)
        L_0x0315:
            r3.setStart(r5)
            r3.setEnd(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.util.Mapper.internalMapWrapper(org.glassfish.grizzly.http.server.util.Mapper$Context, org.glassfish.grizzly.http.util.CharChunk, org.glassfish.grizzly.http.server.util.MappingData):void");
    }

    private void internalMapExactWrapper(Wrapper[] wrappers, CharChunk path, MappingData mappingData) {
        int pos = find((MapElement[]) wrappers, path);
        if (pos != -1 && path.equals((CharSequence) wrappers[pos].name)) {
            mappingData.requestPath.setString(wrappers[pos].name);
            mappingData.wrapperPath.setString(wrappers[pos].name);
            mappingData.wrapper = wrappers[pos].object;
            mappingData.servletName = wrappers[pos].servletName;
            mappingData.descriptorPath = wrappers[pos].path;
            String charChunk = path.toString();
            mappingData.matchedPath = charChunk;
            mappingData.mappingType = "/".equals(charChunk) ? (byte) 2 : 4;
        }
    }

    private void internalMapWildcardWrapper(Wrapper[] wrappers, int nesting, CharChunk path, MappingData mappingData) {
        int pathEnd = path.getEnd();
        int lastSlash = -1;
        int length = -1;
        int pos = find((MapElement[]) wrappers, path);
        if (pos != -1) {
            boolean found = false;
            while (true) {
                if (pos < 0) {
                    break;
                }
                if (path.startsWith(wrappers[pos].name)) {
                    length = wrappers[pos].name.length();
                    if (path.getLength() != length) {
                        if (path.startsWithIgnoreCase("/", length)) {
                            found = true;
                            break;
                        }
                    } else {
                        found = true;
                        break;
                    }
                }
                if (lastSlash == -1) {
                    lastSlash = nthSlash(path, nesting + 1);
                } else {
                    lastSlash = lastSlash(path);
                }
                path.setEnd(lastSlash);
                pos = find((MapElement[]) wrappers, path);
            }
            path.setEnd(pathEnd);
            if (found) {
                mappingData.wrapperPath.setString(wrappers[pos].name);
                if (path.getLength() > length) {
                    mappingData.pathInfo.setChars(path.getBuffer(), path.getStart() + length, path.getEnd());
                }
                mappingData.requestPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
                mappingData.wrapper = wrappers[pos].object;
                mappingData.servletName = wrappers[pos].servletName;
                mappingData.jspWildCard = wrappers[pos].jspWildCard;
                mappingData.mappingType = MappingData.PATH;
                mappingData.descriptorPath = wrappers[pos].path;
                mappingData.matchedPath = path.toString();
            }
        }
    }

    private void internalMapExtensionWrapper(Wrapper[] wrappers, CharChunk path, MappingData mappingData) {
        char[] buf = path.getBuffer();
        int pathEnd = path.getEnd();
        int servletPath = path.getStart();
        int slash = -1;
        int i = pathEnd - 1;
        while (true) {
            if (i < servletPath) {
                break;
            } else if (buf[i] == '/') {
                slash = i;
                break;
            } else {
                i--;
            }
        }
        if (slash >= 0) {
            int period = -1;
            int i2 = pathEnd - 1;
            while (true) {
                if (i2 <= slash) {
                    break;
                } else if (buf[i2] == '.') {
                    period = i2;
                    break;
                } else {
                    i2--;
                }
            }
            if (period >= 0) {
                path.setStart(period + 1);
                path.setEnd(pathEnd);
                int pos = find((MapElement[]) wrappers, path);
                if (pos != -1 && path.equals((CharSequence) wrappers[pos].name)) {
                    mappingData.wrapperPath.setChars(buf, servletPath, pathEnd);
                    mappingData.requestPath.setChars(buf, servletPath, pathEnd);
                    mappingData.wrapper = wrappers[pos].object;
                    mappingData.servletName = wrappers[pos].servletName;
                    mappingData.mappingType = 8;
                    mappingData.descriptorPath = wrappers[pos].path;
                }
                path.setStart(servletPath);
                path.setEnd(pathEnd);
                mappingData.matchedPath = path.toString();
            }
        }
    }

    private static int find(MapElement[] map, CharChunk name) {
        return find(map, name, name.getStart(), name.getEnd());
    }

    private static int find(MapElement[] map, CharChunk name, int start, int end) {
        int a = 0;
        int b = map.length - 1;
        if (b == -1 || compare(name, start, end, map[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }
        do {
            int i = (b + a) >>> 1;
            int result = compare(name, start, end, map[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
        } while (b - a != 1);
        if (compare(name, start, end, map[b].name) < 0) {
            return a;
        }
        return b;
    }

    private static int findIgnoreCase(MapElement[] map, CharChunk name) {
        return findIgnoreCase(map, name, name.getStart(), name.getEnd());
    }

    private static int findIgnoreCase(MapElement[] map, CharChunk name, int start, int end) {
        int a = 0;
        int b = map.length - 1;
        if (b == -1 || compareIgnoreCase(name, start, end, map[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }
        do {
            int i = (b + a) >>> 1;
            int result = compareIgnoreCase(name, start, end, map[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
        } while (b - a != 1);
        if (compareIgnoreCase(name, start, end, map[b].name) < 0) {
            return a;
        }
        return b;
    }

    private static int findIgnoreCase(MapElement[] map, String name) {
        int a = 0;
        int b = map.length - 1;
        if (b == -1 || compareIgnoreCase(name, map[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }
        do {
            int i = (b + a) >>> 1;
            int result = compareIgnoreCase(name, map[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
        } while (b - a != 1);
        if (compareIgnoreCase(name, map[b].name) < 0) {
            return a;
        }
        return b;
    }

    private static int find(MapElement[] map, String name) {
        int a = 0;
        int b = map.length - 1;
        if (b == -1 || name.compareTo(map[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }
        do {
            int i = (b + a) >>> 1;
            int result = name.compareTo(map[i].name);
            if (result > 0) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
        } while (b - a != 1);
        if (name.compareTo(map[b].name) < 0) {
            return a;
        }
        return b;
    }

    private static int compare(CharChunk name, int start, int end, String compareTo) {
        int result = 0;
        char[] c = name.getBuffer();
        int len = compareTo.length();
        if (end - start < len) {
            len = end - start;
        }
        for (int i = 0; i < len && result == 0; i++) {
            if (c[i + start] > compareTo.charAt(i)) {
                result = 1;
            } else if (c[i + start] < compareTo.charAt(i)) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareTo.length() > end - start) {
            return -1;
        }
        if (compareTo.length() < end - start) {
            return 1;
        }
        return result;
    }

    private static int compareIgnoreCase(CharChunk name, int start, int end, String compareTo) {
        int result = 0;
        char[] c = name.getBuffer();
        int len = compareTo.length();
        if (end - start < len) {
            len = end - start;
        }
        for (int i = 0; i < len && result == 0; i++) {
            if (Ascii.toLower((int) c[i + start]) > Ascii.toLower((int) compareTo.charAt(i))) {
                result = 1;
            } else if (Ascii.toLower((int) c[i + start]) < Ascii.toLower((int) compareTo.charAt(i))) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareTo.length() > end - start) {
            return -1;
        }
        if (compareTo.length() < end - start) {
            return 1;
        }
        return result;
    }

    private static int compareIgnoreCase(String name, String compareTo) {
        int result = 0;
        int nameLen = name.length();
        int compareToLen = compareTo.length();
        int len = nameLen < compareToLen ? nameLen : compareToLen;
        for (int i = 0; i < len && result == 0; i++) {
            int nameLower = Ascii.toLower((int) name.charAt(i));
            int compareToLower = Ascii.toLower((int) compareTo.charAt(i));
            if (nameLower > compareToLower) {
                result = 1;
            } else if (nameLower < compareToLower) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareToLen > nameLen) {
            return -1;
        }
        if (compareToLen < nameLen) {
            return 1;
        }
        return result;
    }

    private static int lastSlash(CharChunk name) {
        char[] c = name.getBuffer();
        int end = name.getEnd();
        int start = name.getStart();
        int pos = end;
        while (pos > start) {
            pos--;
            if (c[pos] == '/') {
                break;
            }
        }
        return pos;
    }

    private static int nthSlash(CharChunk name, int n) {
        char[] c = name.getBuffer();
        int end = name.getEnd();
        int pos = name.getStart();
        int count = 0;
        while (pos < end) {
            int pos2 = pos + 1;
            if (c[pos] == '/' && (count = count + 1) == n) {
                return pos2 - 1;
            }
            pos = pos2;
        }
        return pos;
    }

    private static int slashCount(String name) {
        int pos = -1;
        int count = 0;
        while (true) {
            int indexOf = name.indexOf(47, pos + 1);
            pos = indexOf;
            if (indexOf == -1) {
                return count;
            }
            count++;
        }
    }

    private static MapElement insertMap(MapElement[] oldMap, MapElement[] newMap, MapElement newElement) {
        int pos = find(oldMap, newElement.name);
        if (pos != -1 && newElement.name.equals(oldMap[pos].name)) {
            return oldMap[pos];
        }
        System.arraycopy(oldMap, 0, newMap, 0, pos + 1);
        newMap[pos + 1] = newElement;
        System.arraycopy(oldMap, pos + 1, newMap, pos + 2, (oldMap.length - pos) - 1);
        return null;
    }

    private static MapElement insertMapIgnoreCase(MapElement[] oldMap, MapElement[] newMap, MapElement newElement) {
        CharChunk cc = new CharChunk();
        char[] chars = newElement.name.toCharArray();
        cc.setChars(chars, 0, chars.length);
        int pos = findIgnoreCase(oldMap, cc);
        if (pos != -1 && newElement.name.equalsIgnoreCase(oldMap[pos].name)) {
            return oldMap[pos];
        }
        System.arraycopy(oldMap, 0, newMap, 0, pos + 1);
        newMap[pos + 1] = newElement;
        System.arraycopy(oldMap, pos + 1, newMap, pos + 2, (oldMap.length - pos) - 1);
        return null;
    }

    private static boolean removeMap(MapElement[] oldMap, MapElement[] newMap, String name) {
        int pos = find(oldMap, name);
        if (pos == -1 || !name.equals(oldMap[pos].name)) {
            return false;
        }
        System.arraycopy(oldMap, 0, newMap, 0, pos);
        System.arraycopy(oldMap, pos + 1, newMap, pos, (oldMap.length - pos) - 1);
        return true;
    }

    private static boolean removeMapIgnoreCase(MapElement[] oldMap, MapElement[] newMap, String name) {
        CharChunk cc = new CharChunk();
        char[] chars = name.toCharArray();
        cc.setChars(chars, 0, chars.length);
        int pos = findIgnoreCase(oldMap, cc);
        if (pos == -1 || !name.equalsIgnoreCase(oldMap[pos].name)) {
            return false;
        }
        System.arraycopy(oldMap, 0, newMap, 0, pos);
        System.arraycopy(oldMap, pos + 1, newMap, pos, (oldMap.length - pos) - 1);
        return true;
    }

    public static abstract class MapElement {
        public String name = null;
        public Object object = null;

        protected MapElement() {
        }
    }

    public static final class Host extends MapElement {
        public ContextList contextList = null;
        public String[] defaultContextPaths = null;
        public Context[] defaultContexts = null;

        protected Host() {
        }
    }

    public static final class ContextList {
        public Context[] contexts = new Context[0];
        public int nesting = 0;

        protected ContextList() {
        }
    }

    public static final class Context extends MapElement {
        public List<AlternateDocBase> alternateDocBases = null;
        public Wrapper defaultWrapper = null;
        public Wrapper emptyPathWrapper = null;
        public Wrapper[] exactWrappers = new Wrapper[0];
        public Wrapper[] extensionWrappers = new Wrapper[0];
        public int nesting = 0;
        public String path = null;
        public NamingContext resources = null;
        public String[] welcomeResources = new String[0];
        public Wrapper[] wildcardWrappers = new Wrapper[0];

        protected Context() {
        }
    }

    public static class Wrapper extends MapElement {
        public boolean jspWildCard = false;
        public String path = null;
        public String servletName = null;

        protected Wrapper() {
        }
    }
}
