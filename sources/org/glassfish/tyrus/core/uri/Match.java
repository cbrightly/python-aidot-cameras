package org.glassfish.tyrus.core.uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.uri.internal.PathSegment;
import org.glassfish.tyrus.core.uri.internal.UriComponent;

public class Match {
    private static final Logger LOGGER = Logger.getLogger(Match.class.getName());
    private final TyrusEndpointWrapper endpointWrapper;
    private final Map<String, String> parameters = new HashMap();
    private final List<Integer> variableSegmentIndices = new ArrayList();

    private Match(TyrusEndpointWrapper endpointWrapper2) {
        this.endpointWrapper = endpointWrapper2;
    }

    /* access modifiers changed from: package-private */
    public List<Integer> getVariableSegmentIndices() {
        return this.variableSegmentIndices;
    }

    /* access modifiers changed from: package-private */
    public int getLowestVariableSegmentIndex() {
        if (getVariableSegmentIndices().isEmpty()) {
            return -1;
        }
        return getVariableSegmentIndices().get(0).intValue();
    }

    /* access modifiers changed from: package-private */
    public void addParameter(String name, String value, int index) {
        this.parameters.put(name, value);
        this.variableSegmentIndices.add(Integer.valueOf(index));
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public TyrusEndpointWrapper getEndpointWrapper() {
        return this.endpointWrapper;
    }

    public String toString() {
        return this.endpointWrapper.getEndpointPath();
    }

    /* access modifiers changed from: package-private */
    public boolean isExact() {
        return getLowestVariableSegmentIndex() == -1;
    }

    public static List<Match> getAllMatches(String requestPath, Set<TyrusEndpointWrapper> endpoints, DebugContext debugContext) {
        List<Match> matches = new ArrayList<>();
        for (TyrusEndpointWrapper endpoint : endpoints) {
            Match m = matchPath(requestPath, endpoint, debugContext);
            if (m != null) {
                matches.add(m);
            }
        }
        Collections.sort(matches, new MatchComparator(debugContext));
        debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Endpoints matched to the request URI: ", matches);
        return matches;
    }

    private static Match matchPath(String requestPath, TyrusEndpointWrapper endpoint, DebugContext debugContext) {
        String str = requestPath;
        DebugContext debugContext2 = debugContext;
        Logger logger = LOGGER;
        Level level = Level.FINE;
        DebugContext.Type type = DebugContext.Type.MESSAGE_IN;
        debugContext2.appendTraceMessage(logger, level, type, "Matching request URI ", str, " against ", endpoint.getEndpointPath());
        List<PathSegment> requestPathSegments = UriComponent.decodePath(str, true);
        List<PathSegment> endpointPathSegments = UriComponent.decodePath(endpoint.getEndpointPath(), true);
        if (requestPathSegments.size() != endpointPathSegments.size()) {
            debugContext2.appendTraceMessage(logger, level, type, "URIs ", str, " and ", endpoint.getEndpointPath(), " have different length");
            return null;
        }
        Match m = new Match(endpoint);
        boolean somethingMatched = false;
        for (int i = 0; i < requestPathSegments.size(); i++) {
            String requestSegment = requestPathSegments.get(i).getPath();
            String endpointSegment = endpointPathSegments.get(i).getPath();
            if (requestSegment.equals(endpointSegment)) {
                somethingMatched = true;
            } else if (isVariable(endpointSegment)) {
                somethingMatched = true;
                m.addParameter(getVariableName(endpointSegment), requestSegment, i);
            } else {
                debugContext2.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Segment \"", endpointSegment, "\" does not match");
                return null;
            }
        }
        if (somethingMatched) {
            return m;
        }
        return null;
    }

    public static boolean isEquivalent(String path1, String path2) {
        return asEquivalenceList(path1).equals(asEquivalenceList(path2));
    }

    private static List<String> asEquivalenceList(String path) {
        List<String> equivalenceList = new ArrayList<>();
        for (PathSegment next : UriComponent.decodePath(path, true)) {
            if (isVariable(next.getPath())) {
                equivalenceList.add("{x}");
            } else {
                equivalenceList.add(next.getPath());
            }
        }
        return equivalenceList;
    }

    private static boolean isVariable(String segment) {
        return segment.startsWith("{") && segment.endsWith("}");
    }

    private static String getVariableName(String segment) {
        return segment.substring(1, segment.length() - 1);
    }
}
