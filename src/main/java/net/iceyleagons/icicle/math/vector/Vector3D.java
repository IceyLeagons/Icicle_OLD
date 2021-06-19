package net.iceyleagons.icicle.math.vector;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.util.Vector;

@Data
@EqualsAndHashCode
public class Vector3D {

    private double x;
    private double z;
    private double y;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(@NonNull Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    public Vector3D(@NonNull Vector3D vector3D) {
        this(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    public double distance(Vector3D otherPoint) {
        return Math.sqrt(Math.pow(x - otherPoint.getX(), 2) + Math.pow(y - otherPoint.getY(), 2) + Math.pow(z - otherPoint.getZ(), 2));
    }

    public boolean same(Vector3D otherVector) {
        return Math.round(x) == Math.round(otherVector.getX()) && Math.round(y) == Math.round(otherVector.getY()) && Math.round(z) == Math.round(otherVector.getZ());
    }
}
