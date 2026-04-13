package org.glassfish.grizzly.http.server.accesslog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;

public class FileAppender extends StreamAppender {
    private static final Logger LOGGER = Grizzly.logger(HttpServer.class);

    public FileAppender(File file) {
        this(file, true);
    }

    public FileAppender(File file, boolean append) {
        super(new FileOutputStream(file, append));
        Logger logger = LOGGER;
        logger.info("Access log file \"" + file.getAbsolutePath() + "\" opened");
    }
}
