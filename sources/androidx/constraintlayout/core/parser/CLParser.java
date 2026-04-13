package androidx.constraintlayout.core.parser;

import java.io.PrintStream;

public class CLParser {
    static boolean DEBUG = false;
    private boolean hasComment = false;
    private int lineNumber;
    private String mContent;

    public enum TYPE {
        UNKNOWN,
        OBJECT,
        ARRAY,
        NUMBER,
        STRING,
        KEY,
        TOKEN
    }

    public static CLObject parse(String string) {
        return new CLParser(string).parse();
    }

    public CLParser(String content) {
        this.mContent = content;
    }

    public CLObject parse() {
        int startIndex;
        char[] content = this.mContent.toCharArray();
        int length = content.length;
        int i = 1;
        this.lineNumber = 1;
        int startIndex2 = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char c = content[i2];
            if (c == '{') {
                startIndex2 = i2;
                break;
            }
            if (c == 10) {
                this.lineNumber++;
            }
            i2++;
        }
        if (startIndex2 != -1) {
            CLObject root = CLObject.allocate(content);
            root.setLine(this.lineNumber);
            root.setStart((long) startIndex2);
            CLElement currentElement = root;
            int i3 = startIndex2 + 1;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                char c2 = content[i3];
                if (c2 == 10) {
                    this.lineNumber += i;
                }
                if (this.hasComment) {
                    if (c2 == 10) {
                        this.hasComment = false;
                    } else {
                        startIndex = startIndex2;
                        i3++;
                        startIndex2 = startIndex;
                        i = 1;
                    }
                }
                if (currentElement == null) {
                    int i4 = startIndex2;
                    break;
                }
                if (currentElement.isDone()) {
                    currentElement = getNextJsonElement(i3, c2, currentElement, content);
                    startIndex = startIndex2;
                } else if (currentElement instanceof CLObject) {
                    if (c2 == '}') {
                        currentElement.setEnd((long) (i3 - 1));
                        startIndex = startIndex2;
                    } else {
                        currentElement = getNextJsonElement(i3, c2, currentElement, content);
                        startIndex = startIndex2;
                    }
                } else if (currentElement instanceof CLArray) {
                    if (c2 == ']') {
                        currentElement.setEnd((long) (i3 - 1));
                        startIndex = startIndex2;
                    } else {
                        currentElement = getNextJsonElement(i3, c2, currentElement, content);
                        startIndex = startIndex2;
                    }
                } else if (currentElement instanceof CLString) {
                    long j = currentElement.start;
                    if (content[(int) j] == c2) {
                        currentElement.setStart(j + 1);
                        currentElement.setEnd((long) (i3 - 1));
                    }
                    startIndex = startIndex2;
                } else {
                    if (currentElement instanceof CLToken) {
                        CLToken token = (CLToken) currentElement;
                        startIndex = startIndex2;
                        if (!token.validate(c2, (long) i3)) {
                            throw new CLParsingException("parsing incorrect token " + token.content() + " at line " + this.lineNumber, token);
                        }
                    } else {
                        startIndex = startIndex2;
                    }
                    if ((currentElement instanceof CLKey) || (currentElement instanceof CLString)) {
                        long j2 = currentElement.start;
                        char ck = content[(int) j2];
                        if ((ck == '\'' || ck == '\"') && ck == c2) {
                            currentElement.setStart(j2 + 1);
                            currentElement.setEnd((long) (i3 - 1));
                        }
                    }
                    if (!currentElement.isDone() && (c2 == '}' || c2 == ']' || c2 == ',' || c2 == ' ' || c2 == 9 || c2 == 13 || c2 == 10 || c2 == ':')) {
                        currentElement.setEnd((long) (i3 - 1));
                        if (c2 == '}' || c2 == ']') {
                            currentElement = currentElement.getContainer();
                            currentElement.setEnd((long) (i3 - 1));
                            if (currentElement instanceof CLKey) {
                                currentElement = currentElement.getContainer();
                                currentElement.setEnd((long) (i3 - 1));
                            }
                        }
                    }
                }
                if (currentElement.isDone() && (!(currentElement instanceof CLKey) || ((CLKey) currentElement).mElements.size() > 0)) {
                    currentElement = currentElement.getContainer();
                }
                i3++;
                startIndex2 = startIndex;
                i = 1;
            }
            while (currentElement != null && !currentElement.isDone()) {
                if (currentElement instanceof CLString) {
                    currentElement.setStart((long) (((int) currentElement.start) + 1));
                }
                currentElement.setEnd((long) (length - 1));
                currentElement = currentElement.getContainer();
            }
            if (DEBUG) {
                System.out.println("Root: " + root.toJSON());
            }
            return root;
        }
        throw new CLParsingException("invalid json content", (CLElement) null);
    }

    private CLElement getNextJsonElement(int position, char c, CLElement currentElement, char[] content) {
        switch (c) {
            case 9:
            case 10:
            case 13:
            case ' ':
            case ',':
            case ':':
                return currentElement;
            case '\"':
            case '\'':
                if (currentElement instanceof CLObject) {
                    return createElement(currentElement, position, TYPE.KEY, true, content);
                }
                return createElement(currentElement, position, TYPE.STRING, true, content);
            case '+':
            case '-':
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return createElement(currentElement, position, TYPE.NUMBER, true, content);
            case '/':
                if (position + 1 >= content.length || content[position + 1] != '/') {
                    return currentElement;
                }
                this.hasComment = true;
                return currentElement;
            case '[':
                return createElement(currentElement, position, TYPE.ARRAY, true, content);
            case ']':
            case '}':
                currentElement.setEnd((long) (position - 1));
                CLElement currentElement2 = currentElement.getContainer();
                currentElement2.setEnd((long) position);
                return currentElement2;
            case '{':
                return createElement(currentElement, position, TYPE.OBJECT, true, content);
            default:
                if (!(currentElement instanceof CLContainer) || (currentElement instanceof CLObject)) {
                    return createElement(currentElement, position, TYPE.KEY, true, content);
                }
                CLElement currentElement3 = createElement(currentElement, position, TYPE.TOKEN, true, content);
                CLToken token = (CLToken) currentElement3;
                if (token.validate(c, (long) position)) {
                    return currentElement3;
                }
                throw new CLParsingException("incorrect token <" + c + "> at line " + this.lineNumber, token);
        }
    }

    private CLElement createElement(CLElement currentElement, int position, TYPE type, boolean applyStart, char[] content) {
        CLElement newElement = null;
        if (DEBUG) {
            PrintStream printStream = System.out;
            printStream.println("CREATE " + type + " at " + content[position]);
        }
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[type.ordinal()]) {
            case 1:
                newElement = CLObject.allocate(content);
                position++;
                break;
            case 2:
                newElement = CLArray.allocate(content);
                position++;
                break;
            case 3:
                newElement = CLString.allocate(content);
                break;
            case 4:
                newElement = CLNumber.allocate(content);
                break;
            case 5:
                newElement = CLKey.allocate(content);
                break;
            case 6:
                newElement = CLToken.allocate(content);
                break;
        }
        if (newElement == null) {
            return null;
        }
        newElement.setLine(this.lineNumber);
        if (applyStart) {
            newElement.setStart((long) position);
        }
        if (currentElement instanceof CLContainer) {
            newElement.setContainer((CLContainer) currentElement);
        }
        return newElement;
    }

    /* renamed from: androidx.constraintlayout.core.parser.CLParser$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE;

        static {
            int[] iArr = new int[TYPE.values().length];
            $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE = iArr;
            try {
                iArr[TYPE.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.KEY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.TOKEN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
