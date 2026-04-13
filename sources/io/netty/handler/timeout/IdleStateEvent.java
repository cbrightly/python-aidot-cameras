package io.netty.handler.timeout;

import com.leedarson.bean.Constants;
import io.netty.util.internal.ObjectUtil;

public class IdleStateEvent {
    public static final IdleStateEvent ALL_IDLE_STATE_EVENT;
    public static final IdleStateEvent FIRST_ALL_IDLE_STATE_EVENT;
    public static final IdleStateEvent FIRST_READER_IDLE_STATE_EVENT;
    public static final IdleStateEvent FIRST_WRITER_IDLE_STATE_EVENT;
    public static final IdleStateEvent READER_IDLE_STATE_EVENT;
    public static final IdleStateEvent WRITER_IDLE_STATE_EVENT;
    private final boolean first;
    private final IdleState state;

    static {
        IdleState idleState = IdleState.READER_IDLE;
        FIRST_READER_IDLE_STATE_EVENT = new IdleStateEvent(idleState, true);
        READER_IDLE_STATE_EVENT = new IdleStateEvent(idleState, false);
        IdleState idleState2 = IdleState.WRITER_IDLE;
        FIRST_WRITER_IDLE_STATE_EVENT = new IdleStateEvent(idleState2, true);
        WRITER_IDLE_STATE_EVENT = new IdleStateEvent(idleState2, false);
        IdleState idleState3 = IdleState.ALL_IDLE;
        FIRST_ALL_IDLE_STATE_EVENT = new IdleStateEvent(idleState3, true);
        ALL_IDLE_STATE_EVENT = new IdleStateEvent(idleState3, false);
    }

    protected IdleStateEvent(IdleState state2, boolean first2) {
        this.state = (IdleState) ObjectUtil.checkNotNull(state2, Constants.ACTION_STATE);
        this.first = first2;
    }

    public IdleState state() {
        return this.state;
    }

    public boolean isFirst() {
        return this.first;
    }
}
