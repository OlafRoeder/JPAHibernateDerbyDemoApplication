package concurrency;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;

/**
 * An {@link ExecutorService} that handles shutdown when JVM stops, logs
 * uncaught Exceptions and supports Thread renaming. <br>
 * API: <br>
 * {@link #submit(Runnable)}<br>
 * {@link #setTaskName(String)}
 */
public class GlobalExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExecutorService.class);

    private static final boolean ASYNC_MODE = true;
    private static final long TIMEOUT = 1000;

    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER =
            (t, e) -> logger.error("Thread " + t.getName() + " threw exception " + e.getClass(), e);

    /**
     * Same as {@link Executors#newWorkStealingPool()} plus custom
     * {@link UncaughtExceptionHandler}.
     */
    private static final ExecutorService EXECUTOR = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
            ForkJoinPool.defaultForkJoinWorkerThreadFactory, UNCAUGHT_EXCEPTION_HANDLER, ASYNC_MODE);

    static {

        Runtime.getRuntime().addShutdownHook(new Thread("GlobalExecutorService Shutdown Hook") {

            @Override
            public void run() {
                shutdown();
            }
        });
    }

    private GlobalExecutorService() {
        // singleton class
    }

    /**
     * Sets name as name of the thread the task is run in.
     *
     * @param taskName Name to set for the active thread
     */
    private static void setTaskName(String taskName) {
        Thread.currentThread().setName(taskName);
    }

    /**
     * Submits a {@link Runnable} task to the underlying {@link ExecutorService}.
     * The task will be executed asynchronously, so there is no guarantee that
     * subsequent submitted tasks are run in order.
     *
     * @param task A {@link Runnable} object.
     *
     * @return A {@link Future} holding the result of the task
     */
    public static Future<?> submit(@NonNull Runnable task) {

        if (EXECUTOR.isShutdown())
            throw new IllegalStateException(
                    "Executor was shut down. Do not call GlobalExecutorService.submit(Runnable) after GlobalExecutorService.shutdown()!");

        return EXECUTOR.submit(task);
    }

    private static void shutdown() {

        logger.debug("Shutting down " + EXECUTOR);

        EXECUTOR.shutdown();

        try {

            if (!EXECUTOR.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS))
                EXECUTOR.shutdownNow().forEach(task -> logger.debug("Still running task: " + task.getClass()));

        } catch (InterruptedException e) {
            EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}