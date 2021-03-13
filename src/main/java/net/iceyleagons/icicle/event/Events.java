package net.iceyleagons.icicle.event;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import net.iceyleagons.icicle.event.packets.PacketEvent;
import net.iceyleagons.icicle.event.packets.PacketHandler;
import net.iceyleagons.icicle.event.packets.PacketListener;
import net.iceyleagons.icicle.event.packets.enums.PacketType;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * An utility class containing all sorts of useful stuff regarding events.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public class Events {
    static Scheduler.Worker worker;
    static Map<PacketType, Map<Method, Object>> packetMethods = new ConcurrentHashMap<>();
    static Map<String, Set<EventConsumer>> events = new ConcurrentHashMap<>();

    static {
        worker = Schedulers.from(ForkJoinPool.commonPool()).createWorker();
    }

    /**
     * Registers a new packet listener.
     *
     * @param packetListener the listener we wish to register.
     */
    public static void registerPacketListener(PacketListener packetListener) {
        for (Method method : Arrays.stream(packetListener.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(PacketHandler.class)).collect(Collectors.toList())) {
            PacketHandler annotation = method.getAnnotation(PacketHandler.class);
            if (!packetMethods.containsKey(annotation.type()))
                packetMethods.put(annotation.type(), new HashMap<>());

            packetMethods.get(annotation.type()).put(method, packetListener);
        }
    }

    /**
     * Handles events/packets.
     *
     * @param address the address we want to handle things at.
     * @param handle  the object we want to distribute.
     * @param <T>     the type of the handle.
     * @deprecated internal use only!
     */
    @Deprecated
    public static <T> void handle(String address, T handle) {
        try {
            events.get(address).forEach(eventConsumer -> eventConsumer.handle(handle));
        } catch (NullPointerException ignored) {
            // Ignored.
        }
    }

    /**
     * Handles packets exclusively.
     *
     * @param type   the type of the packet we want to address.
     * @param handle the event we want to distribute.
     * @return whether or not to cancel the event.
     * @deprecated internal use only!
     */
    @Deprecated
    public static boolean handlePacket(PacketType type, PacketEvent handle) {
        handle(type.name().replace("_", "").toLowerCase(), handle);

        try {
            for (Map.Entry<Method, Object> entry : packetMethods.get(type).entrySet())
                entry.getKey().invoke(entry.getValue(), handle);
        } catch (NullPointerException ignored) {
            // Ignored.
        } catch (IllegalAccessException | InvocationTargetException a) {
            a.printStackTrace();
        }

        return handle.isCancelled();
    }

    /**
     * Creates a new consumer for the provided class.
     * <p>
     * Example usage:
     * <p>
     * {@code Events.createConsumer(String.class).asObservable().subscribe(String.out::println);}
     *
     * @param event the event we wish to create a consumer for.
     * @param <T>   the type of the event.
     * @return an event consumer for the provided event.
     */
    public static <T> EventConsumer<T> createConsumer(Class<T> event) {
        EventConsumer<T> consumer = EventConsumerImpl.from(event);
        events.computeIfAbsent(event.getSimpleName().toLowerCase(), ignored -> new CopyOnWriteArraySet<>()).add(consumer);
        return consumer;
    }

    /**
     * Creates a consumer for the provided packet type.
     * <p>
     * Example usage:
     * <p>
     * {@code Events.createPacketConsumer(PacketType.PLAY_OUT_ENTITY_HEAD_ROTATION).asObservable().filter(event -> event.getPlayer().isOp()).subscribe(event -> event.setCancelled(true));}
     *
     * @param type the type of the packet we wish to listen for.
     * @return an event consumer for the provided packet type.
     */
    public static EventConsumer<PacketEvent> createPacketConsumer(PacketType type) {
        EventConsumer<PacketEvent> consumer = EventConsumerImpl.from(type.name().toLowerCase());
        events.computeIfAbsent(type.name().replace("_", "").toLowerCase(), ignored -> new CopyOnWriteArraySet<>()).add(consumer);
        return consumer;
    }

    /**
     * Creates a consumer for a bukkit event.
     * <p>
     * Example usage:
     * <p>
     * {@code Events.createBukkitConsumer(this, PlayerJoinEvent.class).asObservable().filter(event -> event.getPlayer().isOp()).subscribe(event -> event.setJoinMessage("I just joined!"));}
     *
     * @param plugin the plugin we want to register this event at.
     * @param event  the event we want to register.
     * @param <T>    the type of the event.
     * @return an event consumer for the provided bukkit event.
     */
    public static <T> EventConsumer<T> createBukkitConsumer(Plugin plugin, Class<T> event) {
        if (!events.containsKey(event.getSimpleName().toLowerCase())) {
            RegisteredListener registeredListener = new RegisteredListener(new Listener() {
            }, (listener, event1) -> handle(event1.getClass().getSimpleName().toLowerCase(), event1), EventPriority.NORMAL, plugin, false);
            try {
                HandlerList handlerList = (HandlerList) event.getMethod("getHandlerList").invoke(null);
                handlerList.register(registeredListener);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return createConsumer(event);
    }

}
