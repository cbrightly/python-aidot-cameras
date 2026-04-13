package org.webrtc;

import java.util.Locale;

public class SessionDescription {
    public final String description;
    public final Type type;

    public enum Type {
        OFFER,
        PRANSWER,
        ANSWER,
        ROLLBACK;

        public String canonicalForm() {
            return name().toLowerCase(Locale.US);
        }

        @CalledByNative("Type")
        public static Type fromCanonicalForm(String canonical) {
            return (Type) Enum.valueOf(Type.class, canonical.toUpperCase(Locale.US));
        }
    }

    @CalledByNative
    public SessionDescription(Type type2, String description2) {
        this.type = type2;
        this.description = description2;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public String getDescription() {
        return this.description;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public String getTypeInCanonicalForm() {
        return this.type.canonicalForm();
    }
}
