package com.caverock.androidsvg;

import org.xml.sax.SAXException;

public class SVGParseException extends SAXException {
    SVGParseException(String msg) {
        super(msg);
    }

    SVGParseException(String msg, Exception cause) {
        super(msg, cause);
    }
}
