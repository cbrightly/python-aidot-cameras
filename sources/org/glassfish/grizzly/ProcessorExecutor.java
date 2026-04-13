package org.glassfish.grizzly;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.ProcessorResult;
import org.glassfish.grizzly.localization.LogMessages;

public final class ProcessorExecutor {
    private static final Logger LOGGER = Grizzly.logger(ProcessorExecutor.class);

    public static void execute(Connection connection, IOEvent ioEvent, Processor processor, IOEventLifeCycleListener lifeCycleListener) {
        execute(Context.create(connection, processor, ioEvent, lifeCycleListener));
    }

    public static void execute(Context context) {
        ProcessorResult result;
        boolean isRerun;
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "executing connection ({0}). IOEvent={1} processor={2}", new Object[]{context.getConnection(), context.getIoEvent(), context.getProcessor()});
        }
        do {
            try {
                result = context.getProcessor().process(context);
                isRerun = result.getStatus() == ProcessorResult.Status.RERUN;
                if (isRerun) {
                    Context newContext = (Context) result.getData();
                    rerun(context, newContext);
                    context = newContext;
                    continue;
                }
            } catch (Throwable t) {
                Logger logger2 = LOGGER;
                Level level2 = Level.WARNING;
                if (logger2.isLoggable(level2)) {
                    logger2.log(level2, LogMessages.WARNING_GRIZZLY_PROCESSOR_ERROR(context.getConnection(), context.getIoEvent(), context.getProcessor()), t);
                }
                try {
                    error(context, t);
                    return;
                } catch (Exception e) {
                    return;
                }
            }
        } while (isRerun);
        complete0(context, result);
    }

    public static void resume(Context context) {
        execute(context);
    }

    private static void complete(Context context, Object data) {
        int sz = context.lifeCycleListeners.size();
        IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[]) context.lifeCycleListeners.array();
        int i = 0;
        while (i < sz) {
            try {
                listeners[i].onComplete(context, data);
                i++;
            } catch (Throwable th) {
                context.recycle();
                throw th;
            }
        }
        context.recycle();
    }

    private static void leave(Context context) {
        int sz = context.lifeCycleListeners.size();
        IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[]) context.lifeCycleListeners.array();
        int i = 0;
        while (i < sz) {
            try {
                listeners[i].onLeave(context);
                i++;
            } catch (Throwable th) {
                context.recycle();
                throw th;
            }
        }
        context.recycle();
    }

    private static void reregister(Context context, Object data) {
        Context realContext = (Context) data;
        int sz = context.lifeCycleListeners.size();
        IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[]) context.lifeCycleListeners.array();
        int i = 0;
        while (i < sz) {
            try {
                listeners[i].onReregister(realContext);
                i++;
            } catch (Throwable th) {
                realContext.recycle();
                throw th;
            }
        }
        realContext.recycle();
    }

    private static void rerun(Context context, Context newContext) {
        int sz = context.lifeCycleListeners.size();
        IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[]) context.lifeCycleListeners.array();
        for (int i = 0; i < sz; i++) {
            listeners[i].onRerun(context, newContext);
        }
    }

    private static void error(Context context, Object description) {
        int sz = context.lifeCycleListeners.size();
        IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[]) context.lifeCycleListeners.array();
        int i = 0;
        while (i < sz) {
            try {
                listeners[i].onError(context, description);
                i++;
            } catch (Throwable th) {
                context.release();
                throw th;
            }
        }
        context.release();
    }

    private static void notRun(Context context) {
        int sz = context.lifeCycleListeners.size();
        IOEventLifeCycleListener[] listeners = (IOEventLifeCycleListener[]) context.lifeCycleListeners.array();
        int i = 0;
        while (i < sz) {
            try {
                listeners[i].onNotRun(context);
                i++;
            } catch (Throwable th) {
                context.recycle();
                throw th;
            }
        }
        context.recycle();
    }

    static void complete(Context context, ProcessorResult result) {
        try {
            complete0(context, result);
        } catch (Throwable t) {
            try {
                error(context, t);
            } catch (Exception e) {
            }
        }
    }

    private static void complete0(Context context, ProcessorResult result) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$ProcessorResult$Status[result.getStatus().ordinal()]) {
            case 1:
                complete(context, result.getData());
                return;
            case 2:
                leave(context);
                return;
            case 3:
                return;
            case 4:
                reregister(context, result.getData());
                return;
            case 5:
                error(context, result.getData());
                return;
            case 6:
                notRun(context);
                return;
            default:
                throw new IllegalStateException();
        }
    }

    /* renamed from: org.glassfish.grizzly.ProcessorExecutor$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status;

        static {
            int[] iArr = new int[ProcessorResult.Status.values().length];
            $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status = iArr;
            try {
                iArr[ProcessorResult.Status.COMPLETE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status[ProcessorResult.Status.LEAVE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status[ProcessorResult.Status.TERMINATE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status[ProcessorResult.Status.REREGISTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status[ProcessorResult.Status.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$ProcessorResult$Status[ProcessorResult.Status.NOT_RUN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
