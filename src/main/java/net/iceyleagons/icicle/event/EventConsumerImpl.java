package net.iceyleagons.icicle.event;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * Default implementation of EventConsumer.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public class EventConsumerImpl<T> implements EventConsumer<T> {

    static boolean overwriteEnabled = true;

    String event;
    Consumer<T> handler;
    Consumer<Throwable> errorHandler;
    boolean init = false;

    public static <T> EventConsumerImpl<T> from(Class<T> event) {
        EventConsumerImpl<T> consumer = new EventConsumerImpl<>();
        consumer.event = event.getSimpleName().toLowerCase();

        return consumer;
    }

    public static <T> EventConsumerImpl<T> from(String address) {
        EventConsumerImpl<T> consumer = new EventConsumerImpl<>();
        consumer.event = address;

        return consumer;
    }

    @Override
    public void handle(T object) {
        try {
            handler.accept(object);
        } catch (Throwable throwable) {
            try {
                errorHandler.accept(throwable);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Observable<T> asObservable() {
        if (init && !overwriteEnabled)
            throw new RuntimeException("Tried to overwrite existing observable. Was this intentional? If it was, please enable #overwriteEnabled.");

        init = true;

        return Observable.create(emitter -> {
            handler = emitter::onNext;
            errorHandler = emitter::onError;
            emitter.setCancellable(this::close);
        });
    }

    @Override
    public void close() {
        Events.events.get(event).remove(this);
    }
}