package net.iceyleagons.icicle.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Contains operations regarding the servers TPS
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class TPSUtils {

    public static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("##.##");

    private static Object server = null;
    private static Field tpsField = null;


    /**
     * Sets up the server instance
     */
    private static void setupServerInstance() {
        Class<?> clazz = Reflections.getNormalNMSClass("MinecraftServer");
        server = Objects.requireNonNull(Reflections.getMethod(clazz, "getServer", true, null));

    }

    /**
     * Sets up the TPS field
     *
     * @throws NoSuchFieldException if the field not exists
     */
    private static void setupTpsField() throws NoSuchFieldException {
        if (server == null) setupServerInstance();
        tpsField = server.getClass().getField("recentTps");
    }

    /**
     * This will return the TPS for the specific {@link TPSTime}
     *
     * @param tpsTime the {@link TPSTime}
     * @return the TPS for that tpsTime
     */
    @SneakyThrows
    public static double getTPS(TPSTime tpsTime) {
        if (tpsField == null) setupTpsField();
        return ((double[]) tpsField.get(server))[tpsTime.getId()];
    }

    /**
     * This will return the TPS as a string formatted with the given {@link DecimalFormat}
     *
     * @param tpsTime the {@link TPSTime}
     * @param decimalFormat the {@link DecimalFormat}
     * @return the formatted string
     */
    public static String getTPSString(TPSTime tpsTime, DecimalFormat decimalFormat) {
        return decimalFormat.format(getTPS(tpsTime));
    }

    /**
     * This will return the TPS as a string formatted with the format of ##.##
     *
     * @param tpsTime the {@link TPSTime}
     * @return the formatted string
     */
    public static String getTPSString(TPSTime tpsTime) {
        return getTPSString(tpsTime, DEFAULT_DECIMAL_FORMAT);
    }

    @Getter
    @AllArgsConstructor
    enum TPSTime {
        /**
         * Used to check the TPS for the last minute
         */
        LAST_MINUTE(0),
        /**
         * Used to check the TPS for the last five minutes
         */
        FIVE_MINUTES(1),
        /**
         * Used to check the TPS for the last fifteen minutes
         */
        FIFTEEN_MINUTES(2);

        /**
         * Id of the tps time
         */
        private final int id;
    }

}
