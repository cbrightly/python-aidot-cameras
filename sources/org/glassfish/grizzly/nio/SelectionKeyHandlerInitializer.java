package org.glassfish.grizzly.nio;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class SelectionKeyHandlerInitializer {
    private static final Logger LOGGER = Grizzly.logger(SelectionKeyHandlerInitializer.class);
    private static final String PROP = "org.glassfish.grizzly.DEFAULT_SELECTION_KEY_HANDLER";

    SelectionKeyHandlerInitializer() {
    }

    static SelectionKeyHandler initHandler() {
        String className = System.getProperty(PROP);
        if (className == null) {
            return new DefaultSelectionKeyHandler();
        }
        try {
            return (SelectionKeyHandler) Class.forName(className, true, SelectionKeyHandler.class.getClassLoader()).newInstance();
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.SEVERE;
            if (logger.isLoggable(level)) {
                logger.log(level, "Unable to load or create a new instance of SelectionKeyHandler {0}.  Cause: {1}", new Object[]{className, e.getMessage()});
            }
            Level level2 = Level.FINE;
            if (logger.isLoggable(level2)) {
                logger.log(level2, e.toString(), e);
            }
            return new DefaultSelectionKeyHandler();
        }
    }
}
