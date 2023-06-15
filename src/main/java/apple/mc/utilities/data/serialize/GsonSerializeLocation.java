package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.serialize.JsonSerializing;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public record GsonSerializeLocation(Options options) implements JsonSerializing<Location> {

    public static Options PRECISE_OPTIONS = new Options(true, true, true);

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        if (options().isWorldIncluded()) {
            World world = location.getWorld();
            if (world == null) json.add("world", JsonNull.INSTANCE);
            else json.add("world", context.serialize(world.getUID()));
        }
        if (options().isPreciseDoubleLocation()) {
            json.add("x", new JsonPrimitive(location.getX()));
            json.add("y", new JsonPrimitive(location.getY()));
            json.add("z", new JsonPrimitive(location.getZ()));
        } else {
            json.add("x", new JsonPrimitive(location.getBlockX()));
            json.add("y", new JsonPrimitive(location.getBlockY()));
            json.add("z", new JsonPrimitive(location.getBlockZ()));
        }
        if (options().isDirectionIncluded()) {
            json.add("yaw", new JsonPrimitive(location.getYaw()));
            json.add("pitch", new JsonPrimitive(location.getPitch()));
        }
        return json;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type,
        JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        World world;
        if (options().isWorldIncluded()) {
            UUID uuid = context.deserialize(json.get("world"), UUID.class);
            world = Bukkit.getWorld(uuid);
        } else {
            world = Bukkit.getWorlds().get(0);
        }
        float yaw = 0, pitch = 0;
        if (options().isDirectionIncluded()) {
            yaw = json.get("yaw").getAsFloat();
            pitch = json.get("pitch").getAsFloat();
        }
        double x, y, z;
        if (options().isPreciseDoubleLocation()) {
            x = json.get("x").getAsDouble();
            y = json.get("y").getAsDouble();
            z = json.get("z").getAsDouble();
        } else {
            x = json.get("x").getAsInt();
            y = json.get("y").getAsInt();
            z = json.get("z").getAsInt();
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    public record Options(boolean isPreciseDoubleLocation, boolean isDirectionIncluded,
                          boolean isWorldIncluded) {

    }
}
