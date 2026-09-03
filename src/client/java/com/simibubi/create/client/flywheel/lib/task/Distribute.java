package com.simibubi.create.client.flywheel.lib.task;

import com.simibubi.create.client.flywheel.api.task.Plan;
import com.simibubi.create.client.flywheel.api.task.TaskExecutor;
import com.simibubi.create.client.flywheel.impl.FlwImpl;
import com.simibubi.create.client.flywheel.lib.math.MoreMath;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public final class Distribute {
    private Distribute() {
    }

    /**
     * Distribute the given list of tasks across the threads of the task executor.
     *
     * <p>An effort is made to balance the load across threads while also ensuring each
     * runnable passed to the executor is large enough to amortize the cost of scheduling it.</p>
     *
     * @param taskExecutor The task executor to run on.
     * @param context      The context to pass to each task.
     * @param onCompletion The action to run when all tasks are complete.
     * @param list         The list of objects to run tasks on.
     * @param action       The action to run on each object.
     * @param <C>          The context type.
     * @param <T>          The object type.
     */
    public static <C, T> void tasks(
        TaskExecutor taskExecutor,
        C context,
        Runnable onCompletion,
        List<T> list,
        BiConsumer<T, C> action
    ) {
        final int size = list.size();

        if (size == 0) {
            onCompletion.run();
            return;
        }

        final int sliceSize = sliceSize(taskExecutor, size);

        if (size <= sliceSize) {
            for (T t : list) {
                runSafely(() -> action.accept(t, context));
            }
            onCompletion.run();
        } else if (sliceSize == 1) {
            var synchronizer = new Synchronizer(size, onCompletion);
            for (T t : list) {
                taskExecutor.execute(() -> {
                    try {
                        runSafely(() -> action.accept(t, context));
                    } finally {
                        synchronizer.decrementAndEventuallyRun();
                    }
                });
            }
        } else {
            var synchronizer = new Synchronizer(MoreMath.ceilingDiv(size, sliceSize), onCompletion);
            int remaining = size;

            while (remaining > 0) {
                int end = remaining;
                remaining -= sliceSize;
                int start = Math.max(remaining, 0);

                var subList = list.subList(start, end);
                taskExecutor.execute(() -> {
                    try {
                        for (T t : subList) {
                            runSafely(() -> action.accept(t, context));
                        }
                    } finally {
                        synchronizer.decrementAndEventuallyRun();
                    }
                });
            }
        }
    }

    /**
     * Distribute the given list of tasks in chunks across the threads of the task executor.
     *
     * <p>Unlike {@link #tasks(TaskExecutor, Object, Runnable, List, BiConsumer)}, this method
     * gives the action a list of objects to work on, rather than a single object. This may be handy
     * for when you can share some thread local objects between individual elements of the list.</p>
     *
     * <p>An effort is made to balance the load across threads while also ensuring each
     * runnable passed to the executor is large enough to amortize the cost of scheduling it.</p>
     *
     * @param taskExecutor The task executor to run on.
     * @param context      The context to pass to each task.
     * @param onCompletion The action to run when all tasks are complete.
     * @param list         The list of objects to run tasks on.
     * @param action       The action to run on each slice.
     * @param <C>          The context type.
     * @param <T>          The object type.
     */
    public static <C, T> void slices(
        TaskExecutor taskExecutor,
        C context,
        Runnable onCompletion,
        List<T> list,
        BiConsumer<List<T>, C> action
    ) {
        final int size = list.size();

        if (size == 0) {
            onCompletion.run();
            return;
        }

        final int sliceSize = sliceSize(taskExecutor, size);

        if (size <= sliceSize) {
            runSafely(() -> action.accept(list, context));
            onCompletion.run();
        } else if (sliceSize == 1) {
            var synchronizer = new Synchronizer(size, onCompletion);
            for (T t : list) {
                taskExecutor.execute(() -> {
                    try {
                        runSafely(() -> action.accept(Collections.singletonList(t), context));
                    } finally {
                        synchronizer.decrementAndEventuallyRun();
                    }
                });
            }
        } else {
            var synchronizer = new Synchronizer(MoreMath.ceilingDiv(size, sliceSize), onCompletion);
            int remaining = size;

            while (remaining > 0) {
                int end = remaining;
                remaining -= sliceSize;
                int start = Math.max(remaining, 0);

                var subList = list.subList(start, end);
                taskExecutor.execute(() -> {
                    try {
                        runSafely(() -> action.accept(subList, context));
                    } finally {
                        synchronizer.decrementAndEventuallyRun();
                    }
                });
            }
        }
    }

    /**
     * Distribute the given list of plans across the threads of the task executor.
     *
     * <p>Plan scheduling is normally lightweight compared to the cost of execution,
     * but when many hundreds or thousands of plans need to be scheduled it may be beneficial
     * to parallelize. This method does exactly that, distributing larger chunks of plans to
     * be scheduled in batches.</p>
     *
     * <p>An effort is made to balance the load across threads while also ensuring each
     * runnable passed to the executor is large enough to amortize the cost of scheduling it.</p>
     *
     * @param taskExecutor The task executor to run on.
     * @param context      The context to pass to the plans.
     * @param onCompletion The action to run when all plans are complete.
     * @param plans        The list of plans to execute.
     * @param <C>          The context type.
     */
    public static <C> void plans(TaskExecutor taskExecutor, C context, Runnable onCompletion, List<Plan<C>> plans) {
        final int size = plans.size();

        if (size == 0) {
            onCompletion.run();
            return;
        }

        var synchronizer = new Synchronizer(size, onCompletion);
        final int sliceSize = sliceSize(taskExecutor, size, 8);

        if (size <= sliceSize) {
            for (var t : plans) {
                executePlanSafely(t, taskExecutor, context, synchronizer);
            }
        } else if (sliceSize == 1) {
            for (var t : plans) {
                taskExecutor.execute(() -> executePlanSafely(t, taskExecutor, context, synchronizer));
            }
        } else {
            int remaining = size;

            while (remaining > 0) {
                int end = remaining;
                remaining -= sliceSize;
                int start = Math.max(remaining, 0);

                var subList = plans.subList(start, end);
                taskExecutor.execute(() -> {
                    for (var t : subList) {
                        executePlanSafely(t, taskExecutor, context, synchronizer);
                    }
                });
            }
        }
    }

    private static void runSafely(Runnable action) {
        try {
            action.run();
        } catch (Exception | LinkageError e) {
            // A single broken visual/addon task must not prevent the frame/tick
            // completion signal from firing. Without this guard, one exception
            // can leave Flywheel waiting forever and appear as a game freeze.
            FlwImpl.LOGGER.error("Flywheel distributed task failed; continuing with remaining work", e);
        }
    }

    private static <C> void executePlanSafely(
        Plan<C> plan,
        TaskExecutor taskExecutor,
        C context,
        Runnable onCompletion
    ) {
        try {
            plan.execute(taskExecutor, context, onCompletion);
        } catch (Exception | LinkageError e) {
            FlwImpl.LOGGER.error("Flywheel plan failed before scheduling completion; continuing", e);
            onCompletion.run();
        }
    }

    public static int sliceSize(TaskExecutor taskExecutor, int totalSize) {
        return sliceSize(taskExecutor, totalSize, 32);
    }

    public static int sliceSize(TaskExecutor taskExecutor, int totalSize, int denominator) {
        return MoreMath.ceilingDiv(totalSize, taskExecutor.threadCount() * denominator);
    }
}
