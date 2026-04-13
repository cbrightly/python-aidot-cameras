package org.glassfish.grizzly.attributes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class AttributeBuilderInitializer {
    private static final Logger LOGGER = Grizzly.logger(AttributeBuilderInitializer.class);
    private static final String PROP = "org.glassfish.grizzly.DEFAULT_ATTRIBUTE_BUILDER";

    AttributeBuilderInitializer() {
    }

    static AttributeBuilder initBuilder() {
        String className = System.getProperty(PROP);
        if (className == null) {
            return new DefaultAttributeBuilder();
        }
        try {
            return (AttributeBuilder) Class.forName(className, true, AttributeBuilder.class.getClassLoader()).newInstance();
        } catch (Exception e) {
            Logger logger = LOGGER;
            Level level = Level.SEVERE;
            if (logger.isLoggable(level)) {
                logger.log(level, "Unable to load or create a new instance of AttributeBuilder {0}.  Cause: {1}", new Object[]{className, e.getMessage()});
            }
            Level level2 = Level.FINE;
            if (logger.isLoggable(level2)) {
                logger.log(level2, e.toString(), e);
            }
            return new DefaultAttributeBuilder();
        }
    }
}
