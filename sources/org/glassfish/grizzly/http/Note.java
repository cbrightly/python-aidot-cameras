package org.glassfish.grizzly.http;

import org.glassfish.grizzly.attributes.Attribute;

public final class Note<E> {
    final Attribute<E> attribute;

    Note(Attribute<E> attribute2) {
        this.attribute = attribute2;
    }
}
