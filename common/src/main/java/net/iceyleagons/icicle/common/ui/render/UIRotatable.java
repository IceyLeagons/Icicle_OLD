package net.iceyleagons.icicle.common.ui.render;

public interface UIRotatable {

    /**
     * Rotates the instance 90deg clockwise in the UI.
     *
     * @param times the amount of times this rotation should be applied
     */
    void rotate90DegClockwise(int times);

    /**
     * Rotates the instance 90deg counterclockwise in the UI.
     *
     * @param times the amount of times this rotation should be applied
     */
    void rotate90DegCounterClockwise(int times);

}
