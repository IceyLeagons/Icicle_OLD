package net.iceyleagons.icicle.bungee.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@RequiredArgsConstructor
public abstract class BungeeMessage {

    /**
     * Id of this instance <b>must be unique!</b>
     */
    private final String id;

    /**
     * Returns the message to be sent, ideally use {@link JSONObject#toString()}
     */
    public abstract String getMessage();

}
