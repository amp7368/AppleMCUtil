package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.serialize.JsonSerializing;
import com.google.gson.*;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Type;

public class NamespacedKeyTypeAdapter implements JsonSerializing<NamespacedKey> {
    private static NamespacedKeyTypeAdapter instance = new NamespacedKeyTypeAdapter();

    public static NamespacedKeyTypeAdapter get() {
        return instance;
    }

    @Override
    public NamespacedKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String[] keyValue = json.getAsString().split(":");
        return new NamespacedKey(keyValue[0], keyValue[1]);
    }

    @Override
    public JsonElement serialize(NamespacedKey src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
