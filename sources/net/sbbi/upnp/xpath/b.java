package net.sbbi.upnp.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;

/* compiled from: JXPathFilterSource */
public class b extends InputSource {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private final char b = 0;

    public b() {
    }

    public b(InputStream byteStream) {
        super(byteStream);
        if (byteStream != null) {
            StringBuffer xml = new StringBuffer();
            try {
                byte[] buffer = new byte[512];
                while (true) {
                    int read = byteStream.read(buffer);
                    int readen = read;
                    if (read == -1) {
                        break;
                    }
                    xml.append(new String(buffer, 0, readen));
                }
                String doc = xml.toString();
                Logger logger = a;
                logger.fine("Readen raw xml doc:\n" + doc);
                setByteStream(new ByteArrayInputStream((doc.indexOf(0) != -1 ? doc.replace(0, ' ') : doc).getBytes()));
            } catch (IOException ex) {
                a.log(Level.SEVERE, "IOException occured during XML reception", ex);
            }
        }
    }
}
