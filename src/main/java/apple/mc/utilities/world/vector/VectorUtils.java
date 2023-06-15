package apple.mc.utilities.world.vector;

import apple.mc.utilities.data.region.XYZ;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VectorUtils {

    public static double yaw(Vector vector) {
        return yaw(vector.getX(), vector.getZ());
    }

    public static double yaw(double x, double z) {
        return Math.toDegrees(Math.atan2(z, x));
    }

    @NotNull
    public static Vector rotateVector(double x1, double z1, double x2, double z2, double y, double theta) {
        double angleX = Math.cos(theta);
        double angleZ = Math.sin(theta);

        double x2Old = x1 + x2;
        double z2Old = z1 + z2;
        // rotate these two points
        double x1New = x1 * angleX - z1 * angleZ;
        double z1New = z1 * angleX + x1 * angleZ;
        double x2New = x2Old * angleX - z2Old * angleZ;
        double z2New = z2Old * angleX + x2Old * angleZ;

        return new Vector(x2New - x1New, y, z2New - z1New);
    }

    @NotNull
    public static Vector rotateVector(double facingX, double facingZ, double facingY, double rotation) {
        double angleStarting = Math.atan2(facingZ, facingX);
        angleStarting += rotation;
        return new Vector(Math.cos(angleStarting), facingY, Math.sin(angleStarting));
    }

    public static Location rotateLocAroundY(Location loc, double thetaDeg) {
        double thetaRad = Math.toRadians(-thetaDeg);
        Vector locPos = loc.toVector();
        // directionStart and directionEnd are points of the vector "direction"
        Vector directionStart = locPos.clone().rotateAroundY(thetaRad);
        Vector directionEnd = loc.getDirection().add(locPos).rotateAroundY(thetaRad);
        Vector direction = directionEnd.subtract(directionStart);

        return new Location(loc.getWorld(),
            directionStart.getX(),
            directionStart.getY(),
            directionStart.getZ()
        ).setDirection(direction);
    }

    public static Location rotate(XYZ<Double> relPos, XYZ<Double> relFacing, XYZ<Double> center, double rotation) {
        return rotate(relPos.getX(),
            relPos.getY(),
            relPos.getZ(),
            relFacing.getX(),
            relFacing.getZ(),
            center.getX(),
            center.getY(),
            center.getZ(),
            Math.cos(rotation),
            Math.sin(rotation)
        );
    }

    public static Location rotate(double posX, double posY, double posZ, double facingX, double facingZ, double centerX,
        double centerY, double centerZ, double angleX, double angleZ) {
        double radius = magnitude(posX, 0, posZ);

        // do the position rotation
        double angle = Math.atan2(posZ, posX);
        angle += Math.atan2(angleZ, angleX);
        double x = Math.cos(angle) * radius + centerX;
        double z = Math.sin(angle) * radius + centerZ;
        double y = posY + centerY;

        // do the facing rotation
        double theta = Math.atan2(angleZ, angleX);
        Vector newEntityFacing = rotateVector(posX, posZ, facingX, facingZ, y, theta);
        Location newLocation = new Location(null, x, y, z);
        newLocation.setDirection(newEntityFacing);
        return newLocation;
    }

    public static double magnitude(Vector velocity) {
        double x = velocity.getX();
        double y = velocity.getY();
        double z = velocity.getZ();
        return magnitude(x, y, z);
    }

    public static double magnitude(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static double magnitude(Location l) {
        return magnitude(l.getX(), l.getY(), l.getZ());
    }

    public static double magnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double dot(double a1, double a2, double b1, double b2) {
        return a1 * b1 + a2 * b2;
    }

    public static double dot(double a1, double a2, double a3, double b1, double b2, double b3) {
        return a1 * b1 + a2 * b2 + a3 * b3;
    }

    @Nullable
    public static Location getHitLocation(@NotNull Location location, @NotNull Vector direction, int maxDistance,
        FluidCollisionMode fluidCollision, boolean ignorePassable) {
        @Nullable RayTraceResult raytrace = location
            .getWorld()
            .rayTraceBlocks(location, direction, maxDistance, fluidCollision, ignorePassable);
        if (raytrace == null) return null;
        @Nullable Block hitBlock = raytrace.getHitBlock();
        if (hitBlock == null) return null;
        return hitBlock.getLocation();
    }

    @Nullable
    public static Location getHitLocation(@NotNull Location locationAndDirection, int maxDistance, FluidCollisionMode fluidCollision,
        boolean ignorePassable) {
        return getHitLocation(locationAndDirection,
            locationAndDirection.getDirection(),
            maxDistance,
            fluidCollision,
            ignorePassable
        );
    }

    public static double distance(@Nullable Location aLocation, @Nullable Location bLocation) {
        if (aLocation == null || bLocation == null) return Double.MAX_VALUE;
        if (aLocation.getWorld().getUID() != bLocation.getWorld().getUID()) {
            return Double.MAX_VALUE;
        }
        int aX = aLocation.getBlockX();
        int aY = aLocation.getBlockY();
        int aZ = aLocation.getBlockZ();
        int bX = bLocation.getBlockX();
        int bY = bLocation.getBlockY();
        int bZ = bLocation.getBlockZ();
        int dx = aX - bX;
        int dy = aY - bY;
        int dz = aZ - bZ;
        return magnitude(dx, dy, dz);
    }
}
