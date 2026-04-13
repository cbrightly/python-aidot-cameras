package org.osgi.service.resolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResolutionException extends Exception {
    private static final long serialVersionUID = 1;
    private final transient Collection<?> c;

    public ResolutionException(String message, Throwable cause, Collection<?> unresolvedRequirements) {
        super(message, cause);
        if (unresolvedRequirements == null || unresolvedRequirements.isEmpty()) {
            this.c = null;
        } else {
            this.c = Collections.unmodifiableCollection(new ArrayList(unresolvedRequirements));
        }
    }

    public ResolutionException(String message) {
        super(message);
        this.c = null;
    }

    public ResolutionException(Throwable cause) {
        super(cause);
        this.c = null;
    }

    private static Collection<?> a() {
        return Collections.EMPTY_LIST;
    }

    public Collection<?> getUnresolvedRequirements() {
        Collection<?> collection = this.c;
        return collection != null ? collection : a();
    }
}
