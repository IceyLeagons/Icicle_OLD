package net.iceyleagons.icicle.math.vector;

import lombok.Data;
import org.bukkit.util.Vector;

@Data
public class Plane {

    private Vector xAxis;
    private Vector yAxis;

    public Plane(Vector xAxis, Vector yAxis) {
        this.xAxis = xAxis.normalize();
        this.yAxis = yAxis.normalize();
    }

    public Vector getXAxisClone() {
        return xAxis.clone();
    }

    public Vector getYAxisClone() {
        return yAxis.clone();
    }

    public Vector translate(double x, double y) {
        return getXAxis().multiply(x).add(getYAxis().multiply(y));
    }
}

