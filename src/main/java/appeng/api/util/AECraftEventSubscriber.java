package appeng.api.util;

import appeng.api.networking.crafting.CraftingJobStatus;

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
    private static Map<Object, BiConsumer<CraftingJobStatus, Status>> _consumers;

    public static <T extends Handler> void register(T handlerClass) {
        _consumers.remove(handlerClass);

        _consumers.put(handlerClass, handlerClass::handle);
    }

    public static void notify(CraftingJobStatus job, Status status) {
        for (BiConsumer<CraftingJobStatus, Status> value : _consumers.values()) {
            value.accept(job, status);
        }
    }
}
