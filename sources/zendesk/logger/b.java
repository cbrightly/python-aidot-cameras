package zendesk.logger;

import java.util.ArrayList;
import java.util.List;

/* compiled from: LoggerHelper */
public class b {
    static List<String> c(String message, int maxLength) {
        List<String> messages = new ArrayList<>();
        if (c.b(message)) {
            messages.add("");
            return messages;
        } else if (maxLength < 1) {
            messages.add(message);
            return messages;
        } else if (message.length() <= maxLength) {
            messages.add(message);
            return messages;
        } else {
            int i = 0;
            int len = message.length();
            while (i < len) {
                int indexOfSeparator = message.indexOf(c.a, i);
                int newLine = indexOfSeparator != -1 ? indexOfSeparator : len;
                do {
                    int end = Math.min(newLine, i + maxLength);
                    messages.add(message.substring(i, end));
                    i = end;
                } while (i < newLine);
                i++;
            }
            return messages;
        }
    }

    static char b(int priority) {
        switch (priority) {
            case 2:
                return 'V';
            case 3:
                return 'D';
            case 5:
                return 'W';
            case 6:
                return 'E';
            case 7:
                return 'A';
            default:
                return 'I';
        }
    }

    static String a(String tag) {
        if (c.b(tag)) {
            return "Zendesk";
        }
        return tag.length() > 23 ? tag.substring(0, 23) : tag;
    }
}
