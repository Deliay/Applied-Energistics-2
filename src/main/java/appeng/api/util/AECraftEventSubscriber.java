package appeng.api.util;

import appeng.api.networking.crafting.CraftingJobStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class AECraftEventSubscriber {
    public enum Status {
        STARTED,
        CANCELLED,
        DONE,
    }
    public interface Handler {
        void handle(CraftingJobStatus job, Status status);
    }
    private static final Map<Object, BiConsumer<CraftingJobStatus, Status>> _consumers = new HashMap<>();

    public static <T extends Handler> void register(T handlerClass) {
        _consumers.remove(handlerClass);

        _consumers.put(handlerClass, handlerClass::handle);
    }

    public static void notify(CraftingJobStatus job, Status status) {
        try {
            for (BiConsumer<CraftingJobStatus, Status> value : _consumers.values()) {
                value.accept(job, status);
            }
        } catch (Exception e) {
            // ignored
        }
    }
}
