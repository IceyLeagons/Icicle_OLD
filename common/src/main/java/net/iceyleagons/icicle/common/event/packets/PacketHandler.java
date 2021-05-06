package net.iceyleagons.icicle.common.event.packets;

import net.iceyleagons.icicle.common.event.packets.enums.PacketType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate methods with this to mark them as packet handlers.
 *
 * @author Gábe
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PacketHandler {

    /**
     * @return the type of packet this method handles.
     */
    PacketType type();

}
