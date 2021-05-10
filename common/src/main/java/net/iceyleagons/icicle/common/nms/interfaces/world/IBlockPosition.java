package net.iceyleagons.icicle.common.nms.interfaces.world;

public interface IBlockPosition {
    boolean isValidLocation();

    int getX();

    int getY();

    int getZ();

    void setX(int x);

    void setY(int y);

    void setZ(int z);
}
