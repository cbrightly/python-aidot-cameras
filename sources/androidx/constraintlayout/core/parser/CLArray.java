package androidx.constraintlayout.core.parser;

import com.meituan.robust.Constants;
import java.util.Iterator;

public class CLArray extends CLContainer {
    public CLArray(char[] content) {
        super(content);
    }

    public static CLElement allocate(char[] content) {
        return new CLArray(content);
    }

    /* access modifiers changed from: protected */
    public String toJSON() {
        StringBuilder content = new StringBuilder(getDebugName() + Constants.ARRAY_TYPE);
        boolean first = true;
        for (int i = 0; i < this.mElements.size(); i++) {
            if (!first) {
                content.append(", ");
            } else {
                first = false;
            }
            content.append(this.mElements.get(i).toJSON());
        }
        return content + "]";
    }

    /* access modifiers changed from: protected */
    public String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder json = new StringBuilder();
        String val = toJSON();
        if (forceIndent > 0 || val.length() + indent >= CLElement.MAX_LINE) {
            json.append("[\n");
            boolean first = true;
            Iterator<CLElement> it = this.mElements.iterator();
            while (it.hasNext()) {
                CLElement element = it.next();
                if (!first) {
                    json.append(",\n");
                } else {
                    first = false;
                }
                addIndent(json, CLElement.BASE_INDENT + indent);
                json.append(element.toFormattedJSON(CLElement.BASE_INDENT + indent, forceIndent - 1));
            }
            json.append("\n");
            addIndent(json, indent);
            json.append("]");
        } else {
            json.append(val);
        }
        return json.toString();
    }
}
