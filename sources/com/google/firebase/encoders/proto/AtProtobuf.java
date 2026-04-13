package com.google.firebase.encoders.proto;

import com.google.firebase.encoders.proto.Protobuf;
import java.lang.annotation.Annotation;

public final class AtProtobuf {
    private Protobuf.IntEncoding intEncoding = Protobuf.IntEncoding.DEFAULT;
    private int tag;

    public AtProtobuf tag(int tag2) {
        this.tag = tag2;
        return this;
    }

    public AtProtobuf intEncoding(Protobuf.IntEncoding intEncoding2) {
        this.intEncoding = intEncoding2;
        return this;
    }

    public static AtProtobuf builder() {
        return new AtProtobuf();
    }

    public Protobuf build() {
        return new ProtobufImpl(this.tag, this.intEncoding);
    }

    public static final class ProtobufImpl implements Protobuf {
        private final Protobuf.IntEncoding intEncoding;
        private final int tag;

        ProtobufImpl(int tag2, Protobuf.IntEncoding intEncoding2) {
            this.tag = tag2;
            this.intEncoding = intEncoding2;
        }

        public Class<? extends Annotation> annotationType() {
            return Protobuf.class;
        }

        public int tag() {
            return this.tag;
        }

        public Protobuf.IntEncoding intEncoding() {
            return this.intEncoding;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Protobuf)) {
                return false;
            }
            Protobuf that = (Protobuf) other;
            if (this.tag != that.tag() || !this.intEncoding.equals(that.intEncoding())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.tag ^ 14552422) + (this.intEncoding.hashCode() ^ 2041407134);
        }

        public String toString() {
            return "@com.google.firebase.encoders.proto.Protobuf" + '(' + "tag=" + this.tag + "intEncoding=" + this.intEncoding + ')';
        }
    }
}
