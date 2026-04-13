package com.amazonaws.util;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XpathUtils {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static Log log = LogFactory.getLog(XpathUtils.class);

    public static Document documentFrom(InputStream is) {
        InputStream is2 = new NamespaceRemovingInputStream(is);
        Document doc = factory.newDocumentBuilder().parse(is2);
        is2.close();
        return doc;
    }

    public static Document documentFrom(String xml) {
        return documentFrom((InputStream) new ByteArrayInputStream(xml.getBytes(StringUtils.UTF8)));
    }

    public static Document documentFrom(URL url) {
        return documentFrom(url.openStream());
    }

    public static Double asDouble(String expression, Node node) {
        String doubleString = evaluateAsString(expression, node);
        if (isEmptyString(doubleString)) {
            return null;
        }
        return Double.valueOf(doubleString);
    }

    public static String asString(String expression, Node node) {
        return evaluateAsString(expression, node);
    }

    public static Integer asInteger(String expression, Node node) {
        String intString = evaluateAsString(expression, node);
        if (isEmptyString(intString)) {
            return null;
        }
        return Integer.valueOf(intString);
    }

    public static Boolean asBoolean(String expression, Node node) {
        String booleanString = evaluateAsString(expression, node);
        if (isEmptyString(booleanString)) {
            return null;
        }
        return Boolean.valueOf(booleanString);
    }

    public static Float asFloat(String expression, Node node) {
        String floatString = evaluateAsString(expression, node);
        if (isEmptyString(floatString)) {
            return null;
        }
        return Float.valueOf(floatString);
    }

    public static Long asLong(String expression, Node node) {
        String longString = evaluateAsString(expression, node);
        if (isEmptyString(longString)) {
            return null;
        }
        return Long.valueOf(longString);
    }

    public static Byte asByte(String expression, Node node) {
        String byteString = evaluateAsString(expression, node);
        if (isEmptyString(byteString)) {
            return null;
        }
        return Byte.valueOf(byteString);
    }

    public static Date asDate(String expression, Node node) {
        String dateString = evaluateAsString(expression, node);
        if (isEmptyString(dateString)) {
            return null;
        }
        return DateUtils.parseISO8601Date(dateString);
    }

    public static ByteBuffer asByteBuffer(String expression, Node node) {
        String base64EncodedString = evaluateAsString(expression, node);
        if (!isEmptyString(base64EncodedString) && !isEmpty(node)) {
            return ByteBuffer.wrap(Base64.decode(base64EncodedString));
        }
        return null;
    }

    public static boolean isEmpty(Node node) {
        return node == null;
    }

    public static int nodeLength(NodeList list) {
        if (list == null) {
            return 0;
        }
        return list.getLength();
    }

    private static String evaluateAsString(String expression, Node node) {
        if (isEmpty(node)) {
            return null;
        }
        if (".".equals(expression) || asNode(expression, node) != null) {
            return xpath().evaluate(expression, node).trim();
        }
        return null;
    }

    public static Node asNode(String nodeName, Node node) {
        if (node == null) {
            return null;
        }
        return (Node) xpath().evaluate(nodeName, node, XPathConstants.NODE);
    }

    private static boolean isEmptyString(String s) {
        if (s != null && !"".equals(s.trim())) {
            return false;
        }
        return true;
    }

    public static XPath xpath() {
        return XPathFactory.newInstance().newXPath();
    }
}
