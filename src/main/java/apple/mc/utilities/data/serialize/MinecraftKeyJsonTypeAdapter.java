package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.serialize.JsonSerializing;
import com.google.gson.*;
import net.minecraft.resources.MinecraftKey;

import java.lang.reflect.Type;

public class MinecraftKeyJsonTypeAdapter implements JsonSerializing<MinecraftKey> {
    private static final MinecraftKeyJsonTypeAdapter instance = new MinecraftKeyJsonTypeAdapter();

    public static MinecraftKeyJsonTypeAdapter get() {
        return instance;
    }

    @Override
    public MinecraftKey deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return MinecraftKey.a(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(MinecraftKey minecraftKey, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(minecraftKey.toString());
    }
}