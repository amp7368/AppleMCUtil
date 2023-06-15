package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.serialize.JsonSerializing;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityTypes;

public class EntityTypeTypeAdapter implements JsonSerializing<EntityTypes<?>> {

    private static final EntityTypeTypeAdapter instance = new EntityTypeTypeAdapter();

    public static EntityTypeTypeAdapter get() {
        return instance;
    }

    @Override
    public EntityTypes<?> deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
        return EntityTypes.a(json.getAsString()).orElse(null);
    }

    @Override
    public JsonElement serialize(EntityTypes<?> src, Type typeOfSrc,
        JsonSerializationContext context) {
        MinecraftKey key = EntityTypes.a(src);
        return new JsonPrimitive(key.toString());
    }
}
