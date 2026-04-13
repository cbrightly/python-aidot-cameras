package net.sbbi.upnp.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

/* compiled from: JXPathContext */
public class a {
    private final XPath a;
    private Node b;
    private Node c;

    private a(Node src, XPath xpath) {
        this.a = xpath;
        this.b = src;
        this.c = src;
    }

    public a(Node src) {
        this.a = XPathFactory.newInstance().newXPath();
        this.b = src;
        this.c = src;
    }

    public Node b(String expr) {
        try {
            Node node = (Node) this.a.evaluate(expr, this.c, XPathConstants.NODE);
            if (node != null) {
                return node;
            }
            throw new XPathException("Null Pointer - \"" + expr + "\"");
        } catch (Exception ex) {
            throw new XPathException(ex);
        }
    }

    public String d(String expr) {
        try {
            return (String) this.a.evaluate(expr, this.c, XPathConstants.STRING);
        } catch (Exception ex) {
            throw new XPathException(ex);
        }
    }

    public Double a(String expr) {
        try {
            return (Double) this.a.evaluate(expr, this.c, XPathConstants.NUMBER);
        } catch (Exception ex) {
            throw new XPathException(ex);
        }
    }

    public a c(Node pointer) {
        return new a(pointer, this.a);
    }
}
