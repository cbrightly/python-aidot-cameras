package jakarta.xml.bind;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/* compiled from: Messages */
public class a {
    a() {
    }

    static String a(String property, Object arg1, Object arg2) {
        return b(property, new Object[]{arg1, arg2});
    }

    static String b(String property, Object[] args) {
        return MessageFormat.format(ResourceBundle.getBundle(a.class.getName()).getString(property), args);
    }
}
