package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.serialize.JsonSerializing;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.inventory.ItemStack;

public class ItemStackTypeAdapter implements JsonSerializing<ItemStack> {

    public static ItemStackTypeAdapter get() {
        return new ItemStackTypeAdapter();
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        Map<String, Object> itemMap = new HashMap<>();
        for (Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet())
            itemMap.put(entry.getKey(), entry.getValue());
        return ItemStack.deserialize(itemMap);
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject out = new JsonObject();
        for (Entry<String, Object> entry : src.serialize().entrySet()) {
            out.add(entry.getKey(), context.serialize(entry.getValue()));
        }
        return out;
    }
}
