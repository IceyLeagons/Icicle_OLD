package net.iceyleagons.icicle.event;

import io.reactivex.rxjava3.core.Observable;

/**
 * A consumer for all sorts of events or packets.
 *
 * @param <T> the type this event consumer broadcasts.
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public interface EventConsumer<T> {

    /**
     * Handles an event and broadcasts to the observable subscribers.
     *
     * @param object the event we wish to broadcast.
     * @deprecated internal use only!
     */
    @Deprecated
    void handle(T object);

    /**
     * Converts this event consumer into an RxJava observable.
     *
     * @return the conversion result.
     */
    Observable<T> asObservable();

    /**
     * @deprecated internal use only!
     */
    @Deprecated
    void close();

}
