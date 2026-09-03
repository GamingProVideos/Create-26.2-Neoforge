package com.simibubi.create.client.flywheel.impl.compat;

import com.simibubi.create.client.flywheel.api.visualization.BlockEntityVisualizer;
import com.simibubi.create.client.flywheel.impl.FlwImpl;
import com.simibubi.create.client.flywheel.lib.visualization.VisualizationHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Sodium compatibility implemented reflectively so Create can still build when
 * Sodium is not on the compile classpath.
 */
public final class SodiumCompat {
    public static final boolean ACTIVE = CompatMod.SODIUM.isLoaded && Internals.AVAILABLE;

    static {
        if (ACTIVE) {
            FlwImpl.LOGGER.debug("Detected Sodium block-entity rendering API");
        } else if (CompatMod.SODIUM.isLoaded) {
            FlwImpl.LOGGER.warn("Sodium is loaded but its block-entity API could not be resolved; using vanilla extraction fallback");
        }
    }

    private SodiumCompat() {
    }

    @Nullable
    public static <T extends BlockEntity> Object onSetBlockEntityVisualizer(
        BlockEntityType<T> type,
        @Nullable BlockEntityVisualizer<? super T> oldVisualizer,
        @Nullable BlockEntityVisualizer<? super T> newVisualizer,
        @Nullable Object predicate
    ) {
        if (!ACTIVE) {
            return null;
        }

        if (oldVisualizer == null && newVisualizer != null) {
            if (predicate != null) {
                FlwImpl.LOGGER.warn("Discarding stale Sodium render predicate while installing Flywheel visualizer for {}", type);
            }
            return Internals.addPredicate(type);
        }

        if (oldVisualizer != null && newVisualizer == null) {
            if (predicate != null) {
                Internals.removePredicate(type, predicate);
            }
            return null;
        }

        return predicate;
    }

    private static final class Internals {
        private static final Class<?> PREDICATE_CLASS;
        private static final Object HANDLER;
        private static final Method ADD_PREDICATE;
        private static final Method REMOVE_PREDICATE;
        private static final boolean AVAILABLE;

        static {
            Class<?> predicateClass = null;
            Object handler = null;
            Method add = null;
            Method remove = null;
            boolean available = false;

            try {
                Class<?> handlerClass = Class.forName(
                    "net.caffeinemc.mods.sodium.api.blockentity.BlockEntityRenderHandler"
                );
                predicateClass = Class.forName(
                    "net.caffeinemc.mods.sodium.api.blockentity.BlockEntityRenderPredicate"
                );
                Method instance = handlerClass.getMethod("instance");
                handler = instance.invoke(null);
                add = handlerClass.getMethod("addRenderPredicate", BlockEntityType.class, predicateClass);
                remove = handlerClass.getMethod("removeRenderPredicate", BlockEntityType.class, predicateClass);
                available = handler != null;
            } catch (ReflectiveOperationException | LinkageError e) {
                FlwImpl.LOGGER.debug("Could not bind Sodium block-entity API", e);
            }

            PREDICATE_CLASS = predicateClass;
            HANDLER = handler;
            ADD_PREDICATE = add;
            REMOVE_PREDICATE = remove;
            AVAILABLE = available;
        }

        static <T extends BlockEntity> Object addPredicate(BlockEntityType<T> type) {
            if (!AVAILABLE) {
                return null;
            }

            Object predicate = Proxy.newProxyInstance(
                SodiumCompat.class.getClassLoader(),
                new Class<?>[]{PREDICATE_CLASS},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return switch (method.getName()) {
                            case "toString" -> "Create/Flywheel Sodium fallback predicate";
                            case "hashCode" -> System.identityHashCode(proxy);
                            case "equals" -> proxy == (args == null ? null : args[0]);
                            default -> null;
                        };
                    }

                    BlockEntity be = args != null && args.length >= 3 && args[2] instanceof BlockEntity found
                        ? found
                        : null;
                    if (be == null) {
                        return true;
                    }

                    // If a live visual exists, let Flywheel own the render. Otherwise
                    // keep Sodium's normal renderer enabled and queue visual creation.
                    if (VisualizationHelper.skipVanillaRender(be)) {
                        return false;
                    }

                    VisualizationHelper.tryAddBlockEntity(be);
                    return true;
                }
            );

            try {
                ADD_PREDICATE.invoke(HANDLER, type, predicate);
                return predicate;
            } catch (ReflectiveOperationException | LinkageError e) {
                FlwImpl.LOGGER.error("Failed to register Sodium render predicate for {}", type, e);
                return null;
            }
        }

        static <T extends BlockEntity> void removePredicate(BlockEntityType<T> type, Object predicate) {
            if (!AVAILABLE || predicate == null) {
                return;
            }

            try {
                REMOVE_PREDICATE.invoke(HANDLER, type, predicate);
            } catch (ReflectiveOperationException | LinkageError e) {
                FlwImpl.LOGGER.error("Failed to remove Sodium render predicate for {}", type, e);
            }
        }
    }
}
