package org.bigfoot.swingplus.util;

import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;

import javax.swing.*;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Jonathan la Roi
 * @since 10/15/2020
 */
@CommonsLog
public class JPLambdaRepeatingSwingWorker<TYPE> extends SwingWorker<Void, TYPE> {

    private final ScheduledExecutorService service;

    private final int delay;

    private final int period;

    private final TimeUnit timeUnit;

    private final Supplier<TYPE> supplier;

    private final Consumer<TYPE> consumer;

    @Getter
    private boolean running = false;

    private boolean stopped = false;

    public JPLambdaRepeatingSwingWorker(Supplier<TYPE> supplier, Consumer<TYPE> consumer) {
        this(supplier, consumer, 0, 10, TimeUnit.SECONDS);
    }

    public JPLambdaRepeatingSwingWorker(Supplier<TYPE> supplier, Consumer<TYPE> consumer, int period) {
        this(supplier, consumer, 0, period, TimeUnit.SECONDS);
    }

    public JPLambdaRepeatingSwingWorker(Supplier<TYPE> supplier, Consumer<TYPE> consumer, int period, TimeUnit timeUnit) {
        this(supplier, consumer, 0, period, timeUnit);
    }

    public JPLambdaRepeatingSwingWorker(Supplier<TYPE> supplier, Consumer<TYPE> consumer, int delay, int period, TimeUnit timeUnit) {
        super();
        this.supplier = supplier;
        this.consumer = consumer;
        this.delay = delay;
        this.period = period;
        this.timeUnit = timeUnit;
        service = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    protected Void doInBackground() throws Exception {
        running = true;
        service.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                publish(supplier.get());
            }
        }, delay, period, timeUnit);
        return null;
    }

    @Override
    protected void process(List<TYPE> chunks) {
        if (!stopped) {
            for (TYPE chunk : chunks) {
                consumer.accept(chunk);
            }
        }
    }

    public void interrupt() {
        stopped = true;
        service.shutdown();
        cancel(true);
        running = false;
        log.debug("JPLambdaRepeatingSwingWorker has been interrupted");
    }

    public void cancel() {
        stopped = true;
        service.shutdown();
        cancel(false);
        running = false;
        log.debug("JPLambdaRepeatingSwingWorker has been cancelled");
    }
}
