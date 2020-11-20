package net.iceyleagons.icicle.math;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.util.Vector;

@Data
@RequiredArgsConstructor
public class Vector3D {

    @NonNull
    private double x, y, z;

    public Vector3D(Vector vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public double distance(Vector3D otherPoint) {
        return Math.sqrt(Math.pow(x - otherPoint.getX(), 2) + Math.pow(y - otherPoint.getY(), 2) + Math.pow(z - otherPoint.getZ(), 2));
    }

    public boolean same(Vector3D otherVector) {
        return Math.round(x) == Math.round(otherVector.getX()) && Math.round(y) == Math.round(otherVector.getY()) && Math.round(z) == Math.round(otherVector.getZ());
    }

}
