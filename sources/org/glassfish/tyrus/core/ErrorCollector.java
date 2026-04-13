package org.glassfish.tyrus.core;

import jakarta.websocket.DeploymentException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorCollector {
    private static final Logger LOGGER = Logger.getLogger(ErrorCollector.class.getName());
    private final List<Exception> exceptionsToPublish = new ArrayList();

    public void addException(Exception exception) {
        LOGGER.log(Level.FINE, "Adding exception", exception);
        this.exceptionsToPublish.add(exception);
    }

    public DeploymentException composeComprehensiveException() {
        StringBuilder sb = new StringBuilder();
        for (Exception exception : this.exceptionsToPublish) {
            sb.append(exception.getMessage());
            sb.append("\n");
        }
        return new DeploymentException(sb.toString());
    }

    public boolean isEmpty() {
        return this.exceptionsToPublish.isEmpty();
    }
}
