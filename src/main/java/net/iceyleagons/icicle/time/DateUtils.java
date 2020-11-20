package net.iceyleagons.icicle.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains useful methods regarding {@link java.util.Date} and Time
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateUtils {

    /**
     * @return the current time in the format of yyyy-MM-dd - HH:mm:ss
     */
    public static String getCurrentLocalTime() {
        return getCurrentLocalTime("yyyy-MM-dd - HH:mm:ss");
    }

    /**
     * @param format is the format (ex. yyyy-MM-dd - HH:mm:ss)
     * @return the current time with the given format
     */
    public static String getCurrentLocalTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Return the suffix for the date. Ex it's the 1st of .... or the 3rd of ....
     *
     * @param value the number to generated the suffix for
     * @return the suffix. (Does not contain the number!)
     */
    public static String getDateSuffix(int value) {
        int hunRem = value % 100;
        int tenRem = value % 10;

        if ( hunRem - tenRem == 10 ) {
            return "th";
        }
        switch ( tenRem ) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}
