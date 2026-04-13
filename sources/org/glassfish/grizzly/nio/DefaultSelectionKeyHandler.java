package org.glassfish.grizzly.nio;

import java.nio.channels.SelectionKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;

public final class DefaultSelectionKeyHandler implements SelectionKeyHandler {
    private static final Logger LOGGER = Grizzly.logger(DefaultSelectionKeyHandler.class);
    private static final IOEvent[][] ioEventMap = new IOEvent[32][];

    static {
        int i = 0;
        while (true) {
            IOEvent[][] iOEventArr = ioEventMap;
            if (i < iOEventArr.length) {
                int idx = 0;
                IOEvent[] tmpArray = new IOEvent[4];
                if ((i & 1) != 0) {
                    tmpArray[0] = IOEvent.READ;
                    idx = 0 + 1;
                }
                if ((i & 4) != 0) {
                    tmpArray[idx] = IOEvent.WRITE;
                    idx++;
                }
                if ((i & 8) != 0) {
                    tmpArray[idx] = IOEvent.CLIENT_CONNECTED;
                    idx++;
                }
                if ((i & 16) != 0) {
                    tmpArray[idx] = IOEvent.SERVER_ACCEPT;
                    idx++;
                }
                iOEventArr[i] = (IOEvent[]) Arrays.copyOf(tmpArray, idx);
                i++;
            } else {
                return;
            }
        }
    }

    public void onKeyRegistered(SelectionKey key) {
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "KEY IS REGISTERED: {0}", key);
        }
    }

    public void onKeyDeregistered(SelectionKey key) {
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "KEY IS DEREGISTERED: {0}", key);
        }
    }

    public void cancel(SelectionKey key) {
        onKeyDeregistered(key);
        key.cancel();
    }

    /* renamed from: org.glassfish.grizzly.nio.DefaultSelectionKeyHandler$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$IOEvent;

        static {
            int[] iArr = new int[IOEvent.values().length];
            $SwitchMap$org$glassfish$grizzly$IOEvent = iArr;
            try {
                iArr[IOEvent.READ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.SERVER_ACCEPT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.CLIENT_CONNECTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public int ioEvent2SelectionKeyInterest(IOEvent ioEvent) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$IOEvent[ioEvent.ordinal()]) {
            case 1:
                return 1;
            case 2:
                return 4;
            case 3:
                return 16;
            case 4:
                return 8;
            default:
                return 0;
        }
    }

    public IOEvent[] getIOEvents(int interest) {
        return ioEventMap[interest];
    }

    public IOEvent selectionKeyInterest2IoEvent(int selectionKeyInterest) {
        if ((selectionKeyInterest & 1) != 0) {
            return IOEvent.READ;
        }
        if ((selectionKeyInterest & 4) != 0) {
            return IOEvent.WRITE;
        }
        if ((selectionKeyInterest & 16) != 0) {
            return IOEvent.SERVER_ACCEPT;
        }
        if ((selectionKeyInterest & 8) != 0) {
            return IOEvent.CLIENT_CONNECTED;
        }
        return IOEvent.NONE;
    }

    public boolean onProcessInterest(SelectionKey key, int interest) {
        return true;
    }

    public NIOConnection getConnectionForKey(SelectionKey selectionKey) {
        return (NIOConnection) selectionKey.attachment();
    }

    public void setConnectionForKey(NIOConnection connection, SelectionKey selectionKey) {
        selectionKey.attach(connection);
    }
}
