package org.glassfish.grizzly;

public enum IOEvent {
    NONE(0),
    READ(1),
    WRITE(4),
    SERVER_ACCEPT(16),
    ACCEPTED(0),
    CLIENT_CONNECTED(8),
    CONNECTED(0),
    CLOSED(0);
    
    private final int selectionKeyInterest;

    private IOEvent(int selectionKeyInterest2) {
        this.selectionKeyInterest = selectionKeyInterest2;
    }

    public int getSelectionKeyInterest() {
        return this.selectionKeyInterest;
    }
}
