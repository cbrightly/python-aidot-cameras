package org.glassfish.tyrus.core;

import jakarta.websocket.Extension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class TyrusExtension implements Extension, Serializable {
    private static final transient Logger LOGGER = Logger.getLogger(TyrusExtension.class.getName());
    private static final long serialVersionUID = -3671075267907614851L;
    private final String name;
    private final ArrayList<Extension.Parameter> parameters;

    public enum ParserState {
        NAME,
        PARAM_NAME,
        PARAM_VALUE,
        PARAM_VALUE_QUOTED,
        PARAM_VALUE_QUOTED_POST,
        PARAM_VALUE_QUOTED_QP,
        ERROR
    }

    public TyrusExtension(String name2) {
        this(name2, (List<Extension.Parameter>) null);
    }

    public TyrusExtension(String name2, List<Extension.Parameter> parameters2) {
        if (name2 == null || name2.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.name = name2;
        if (parameters2 != null) {
            this.parameters = new ArrayList<>(parameters2);
        } else {
            this.parameters = new ArrayList<>();
        }
    }

    public String getName() {
        return this.name;
    }

    public List<Extension.Parameter> getParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    public String toString() {
        return "TyrusExtension{" + "name='" + this.name + '\'' + ", parameters=" + this.parameters + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TyrusExtension that = (TyrusExtension) o;
        if (!this.name.equals(that.name) || !this.parameters.equals(that.parameters)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.parameters.hashCode();
    }

    static String toString(Extension extension) {
        StringBuilder sb = new StringBuilder();
        sb.append(extension.getName());
        List<Extension.Parameter> extensionParameters = extension.getParameters();
        if (extensionParameters != null && !extensionParameters.isEmpty()) {
            for (Extension.Parameter p : extensionParameters) {
                sb.append("; ");
                sb.append(TyrusParameter.toString(p));
            }
        }
        return sb.toString();
    }

    public static List<Extension> fromString(List<String> s) {
        return fromHeaders(s);
    }

    public static List<Extension> fromHeaders(List<String> extensionHeaders) {
        String singleHeader;
        String str;
        List<Extension> extensions = new ArrayList<>();
        if (extensionHeaders == null) {
            return extensions;
        }
        Iterator<String> it = extensionHeaders.iterator();
        while (it.hasNext() && (singleHeader = it.next()) != null) {
            char[] chars = singleHeader.toCharArray();
            int i = 0;
            ParserState next = ParserState.NAME;
            StringBuilder name2 = new StringBuilder();
            StringBuilder paramName = new StringBuilder();
            StringBuilder paramValue = new StringBuilder();
            List<Extension.Parameter> params = new ArrayList<>();
            do {
                str = null;
                switch (AnonymousClass1.$SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[next.ordinal()]) {
                    case 1:
                        switch (chars[i]) {
                            case ',':
                                if (name2.length() > 0) {
                                    extensions.add(new TyrusExtension(name2.toString().trim(), params));
                                    name2 = new StringBuilder();
                                    paramName = new StringBuilder();
                                    paramValue = new StringBuilder();
                                    params.clear();
                                }
                                next = ParserState.NAME;
                                break;
                            case ';':
                                next = ParserState.PARAM_NAME;
                                break;
                            case '=':
                                next = ParserState.ERROR;
                                break;
                            default:
                                name2.append(chars[i]);
                                break;
                        }
                    case 2:
                        switch (chars[i]) {
                            case ',':
                                next = ParserState.NAME;
                                params.add(new TyrusParameter(paramName.toString().trim(), (String) null));
                                paramName = new StringBuilder();
                                paramValue = new StringBuilder();
                                if (name2.length() > 0) {
                                    extensions.add(new TyrusExtension(name2.toString().trim(), params));
                                    name2 = new StringBuilder();
                                    paramName = new StringBuilder();
                                    paramValue = new StringBuilder();
                                    params.clear();
                                    break;
                                }
                                break;
                            case ';':
                                next = ParserState.PARAM_NAME;
                                params.add(new TyrusParameter(paramName.toString().trim(), (String) null));
                                paramName = new StringBuilder();
                                paramValue = new StringBuilder();
                                break;
                            case '=':
                                next = ParserState.PARAM_VALUE;
                                break;
                            default:
                                paramName.append(chars[i]);
                                break;
                        }
                    case 3:
                        switch (chars[i]) {
                            case '\"':
                                if (paramValue.length() <= 0) {
                                    next = ParserState.PARAM_VALUE_QUOTED;
                                    break;
                                } else {
                                    next = ParserState.ERROR;
                                    break;
                                }
                            case ',':
                                next = ParserState.NAME;
                                params.add(new TyrusParameter(paramName.toString().trim(), paramValue.toString().trim()));
                                paramName = new StringBuilder();
                                paramValue = new StringBuilder();
                                if (name2.length() > 0) {
                                    extensions.add(new TyrusExtension(name2.toString().trim(), params));
                                    name2 = new StringBuilder();
                                    paramName = new StringBuilder();
                                    paramValue = new StringBuilder();
                                    params.clear();
                                    break;
                                }
                                break;
                            case ';':
                                next = ParserState.PARAM_NAME;
                                params.add(new TyrusParameter(paramName.toString().trim(), paramValue.toString().trim()));
                                paramName = new StringBuilder();
                                paramValue = new StringBuilder();
                                break;
                            case '=':
                                next = ParserState.ERROR;
                                break;
                            default:
                                paramValue.append(chars[i]);
                                break;
                        }
                    case 4:
                        switch (chars[i]) {
                            case '\"':
                                next = ParserState.PARAM_VALUE_QUOTED_POST;
                                params.add(new TyrusParameter(paramName.toString().trim(), paramValue.toString()));
                                paramName = new StringBuilder();
                                paramValue = new StringBuilder();
                                break;
                            case '=':
                                next = ParserState.ERROR;
                                break;
                            case '\\':
                                next = ParserState.PARAM_VALUE_QUOTED_QP;
                                break;
                            default:
                                paramValue.append(chars[i]);
                                break;
                        }
                    case 5:
                        next = ParserState.PARAM_VALUE_QUOTED;
                        paramValue.append(chars[i]);
                        break;
                    case 6:
                        switch (chars[i]) {
                            case ',':
                                next = ParserState.NAME;
                                if (name2.length() > 0) {
                                    extensions.add(new TyrusExtension(name2.toString().trim(), params));
                                    name2 = new StringBuilder();
                                    paramName = new StringBuilder();
                                    paramValue = new StringBuilder();
                                    params.clear();
                                    break;
                                }
                                break;
                            case ';':
                                next = ParserState.PARAM_NAME;
                                break;
                            default:
                                next = ParserState.ERROR;
                                break;
                        }
                    case 7:
                        LOGGER.fine(String.format("Error during parsing Extension: %s", new Object[]{name2}));
                        if (name2.length() > 0) {
                            name2 = new StringBuilder();
                            paramName = new StringBuilder();
                            paramValue = new StringBuilder();
                            params.clear();
                        }
                        switch (chars[i]) {
                            case ',':
                                next = ParserState.NAME;
                                if (name2.length() > 0) {
                                    extensions.add(new TyrusExtension(name2.toString().trim(), params));
                                    name2 = new StringBuilder();
                                    paramName = new StringBuilder();
                                    paramValue = new StringBuilder();
                                    params.clear();
                                    break;
                                }
                                break;
                            case ';':
                                next = ParserState.PARAM_NAME;
                                break;
                        }
                }
                i++;
            } while (i < chars.length);
            if (name2.length() <= 0 || next == ParserState.ERROR) {
                LOGGER.fine(String.format("Unable to parse Extension: %s", new Object[]{name2}));
            } else {
                if (paramName.length() > 0) {
                    String paramValueString = paramValue.toString();
                    String trim = paramName.toString().trim();
                    if (!paramValueString.equals("")) {
                        str = paramValueString;
                    }
                    params.add(new TyrusParameter(trim, str));
                }
                extensions.add(new TyrusExtension(name2.toString().trim(), params));
                params.clear();
            }
        }
        return extensions;
    }

    /* renamed from: org.glassfish.tyrus.core.TyrusExtension$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState;

        static {
            int[] iArr = new int[ParserState.values().length];
            $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState = iArr;
            try {
                iArr[ParserState.NAME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[ParserState.PARAM_NAME.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[ParserState.PARAM_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[ParserState.PARAM_VALUE_QUOTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[ParserState.PARAM_VALUE_QUOTED_QP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[ParserState.PARAM_VALUE_QUOTED_POST.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusExtension$ParserState[ParserState.ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static class TyrusParameter implements Extension.Parameter, Serializable {
        private static final long serialVersionUID = -6818457211703933087L;
        private final String name;
        private final String value;

        public TyrusParameter(String name2, String value2) {
            this.name = name2;
            this.value = value2;
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return "TyrusParameter{" + "name='" + this.name + '\'' + ", value='" + this.value + '\'' + '}';
        }

        static String toString(Extension.Parameter parameter) {
            StringBuilder sb = new StringBuilder();
            sb.append(parameter.getName());
            String value2 = parameter.getValue();
            if (value2 != null) {
                sb.append('=');
                sb.append(value2);
            }
            return sb.toString();
        }
    }
}
