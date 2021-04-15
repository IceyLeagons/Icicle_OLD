package net.iceyleagons.icicle.wrapping.helpers;

public class WrappingHelpers {

    public static boolean fromBooleanObject(Boolean value) { return value != null && value; }

    public static int fromIntegerObject(Integer value) {
        return value == null ? 0 : value;
    }

    public static long fromLongObject(Long value) {
        return value == null ? 0L : value;
    }

    public static short fromShortObject(Short value) {
        return value == null ? 0 : value;
    }

    public static double fromDoubleObject(Double value) {
        return value == null ? 0 : value;
    }

    public static float fromFloatObject(Float value) {
        return value == null ? 0 : value;
    }

}
