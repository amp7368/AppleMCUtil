package apple.mc.utilities.data.region;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

public class AppleRegion {
    private XYZ<Double> primary;
    private XYZ<Double> secondary;

    public AppleRegion() {
    }

    public void setPrimary(XYZ<Double> xyz) {
        this.primary = xyz;
    }

    public void setSecondary(XYZ<Double> xyz) {
        this.secondary = xyz;
    }

    public void setPrimary(double x, double y, double z) {
        this.primary = new XYZ<>(x, y, z);
    }

    public void setSecondary(double x, double y, double z) {
        this.secondary = new XYZ<>(x, y, z);
    }

    public void setPrimary(Location block) {
        this.primary = new XYZ<>(block.getX(), block.getY(), block.getZ());
    }

    public void setSecondary(Location block) {
        this.secondary = new XYZ<>(block.getX(), block.getY(), block.getZ());
    }

    public XYZ<Double> min() {
        double x = Math.min(primary.getX(), secondary.getX());
        double y = Math.min(primary.getY(), secondary.getY());
        double z = Math.min(primary.getZ(), secondary.getZ());
        return new XYZ<>(x, y, z);
    }

    public XYZ<Double> max() {
        double x = Math.max(primary.getX(), secondary.getX());
        double y = Math.max(primary.getY(), secondary.getY());
        double z = Math.max(primary.getZ(), secondary.getZ());
        return new XYZ<>(x, y, z);
    }

    public BoundingBox getAsBoundingBox() {
        XYZ<Double> min = this.min();
        XYZ<Double> max = this.max();
        return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }
}
