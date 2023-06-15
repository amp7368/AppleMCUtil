package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.serialize.JsonSerializing;
import com.google.gson.*;
import de.tr7zw.nbtapi.NBTContainer;

import java.lang.reflect.Type;

public class NBTTagJsonTypeAdapter implements JsonSerializing<NBTContainer> {
    private static final NBTTagJsonTypeAdapter instance = new NBTTagJsonTypeAdapter();

    public static NBTTagJsonTypeAdapter get() {
        return instance;
    }

    @Override
    public NBTContainer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new NBTContainer(jsonElement.toString());
    }

    @Override
    public JsonElement serialize(NBTContainer nbtTagCompound, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(nbtTagCompound.toString());
    }
}
