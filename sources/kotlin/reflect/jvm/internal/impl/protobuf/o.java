package kotlin.reflect.jvm.internal.impl.protobuf;

/* compiled from: MessageLite */
public interface o extends p {

    /* compiled from: MessageLite */
    public interface a extends Cloneable, p {
        a J(e eVar, f fVar);

        o build();
    }

    q<? extends o> getParserForType();

    int getSerializedSize();

    a newBuilderForType();

    a toBuilder();

    void writeTo(CodedOutputStream codedOutputStream);
}
