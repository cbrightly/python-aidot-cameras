package com.google.firebase.concurrent;

import java.util.concurrent.ScheduledExecutorService;

public interface PausableScheduledExecutorService extends ScheduledExecutorService, PausableExecutorService {
}
