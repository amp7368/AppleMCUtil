package apple.mc.utilities.data.serialize;

import apple.utilities.json.gson.GsonBuilderDynamic;
import com.google.gson.GsonBuilder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityTypes;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public interface GsonSerializeMC {

    static GsonBuilder completeGsonBuilderMC() {
        GsonBuilder gson = new GsonBuilder();
        registerLocationTypeAdapter(gson, GsonSerializeLocation.PRECISE_OPTIONS);
        registerNBTTagTypeAdapter(gson);
        registerNamespacedKeyTypeAdapter(gson);
        registerMinecraftKeyTypeAdapter(gson);
        registerItemStackTypeAdapter(gson);
        return gson;
    }


    static GsonBuilderDynamic completeGsonDynamicMC() {
        GsonBuilderDynamic gson = new GsonBuilderDynamic();
        registerLocationTypeAdapter(gson, GsonSerializeLocation.PRECISE_OPTIONS);
        registerNBTTagTypeAdapter(gson);
        registerNamespacedKeyTypeAdapter(gson);
        registerMinecraftKeyTypeAdapter(gson);
        registerItemStackTypeAdapter(gson);
        return gson;
    }

    static void registerItemStackTypeAdapter(GsonBuilder gson) {
        gson.registerTypeAdapter(ItemStack.class, ItemStackTypeAdapter.get());
    }

    static void registerItemStackTypeAdapter(GsonBuilderDynamic gson) {
        gson.registerTypeAdapter(ItemStack.class, ItemStackTypeAdapter.get());
    }

    static GsonBuilder registerLocationTypeAdapter(GsonBuilder gson,
        GsonSerializeLocation.Options options) {
        return gson.registerTypeHierarchyAdapter(Location.class, getLocationTypeAdapter(options));
    }

    static GsonBuilderDynamic registerLocationTypeAdapter(GsonBuilderDynamic gson,
        GsonSerializeLocation.Options options) {
        return gson.registerTypeHierarchyAdapter(Location.class, getLocationTypeAdapter(options));
    }

    static GsonSerializeLocation getLocationTypeAdapter(GsonSerializeLocation.Options options) {
        return new GsonSerializeLocation(options);
    }

    static GsonBuilder registerNBTTagTypeAdapter(GsonBuilder gson) {
        return gson.registerTypeHierarchyAdapter(NBTTagCompound.class, getNBTTagTypeAdapter());
    }

    static GsonBuilderDynamic registerNBTTagTypeAdapter(GsonBuilderDynamic gson) {
        return gson.registerTypeHierarchyAdapter(NBTTagCompound.class, getNBTTagTypeAdapter());
    }

    static NBTTagJsonTypeAdapter getNBTTagTypeAdapter() {
        return NBTTagJsonTypeAdapter.get();
    }

    static GsonBuilder registerMinecraftKeyTypeAdapter(GsonBuilder gson) {
        return gson.registerTypeHierarchyAdapter(MinecraftKey.class, getMinecraftKeyTypeAdapter());
    }

    static GsonBuilderDynamic registerMinecraftKeyTypeAdapter(GsonBuilderDynamic gson) {
        return gson.registerTypeHierarchyAdapter(MinecraftKey.class, getMinecraftKeyTypeAdapter());
    }

    static MinecraftKeyJsonTypeAdapter getMinecraftKeyTypeAdapter() {
        return MinecraftKeyJsonTypeAdapter.get();
    }

    static GsonBuilderDynamic registerNamespacedKeyTypeAdapter(GsonBuilderDynamic gson) {
        return gson.registerTypeHierarchyAdapter(NamespacedKey.class,
            getNamespacedKeyTypeAdapter());
    }

    static GsonBuilder registerNamespacedKeyTypeAdapter(GsonBuilder gson) {
        return gson.registerTypeHierarchyAdapter(NamespacedKey.class,
            getNamespacedKeyTypeAdapter());
    }

    static NamespacedKeyTypeAdapter getNamespacedKeyTypeAdapter() {
        return NamespacedKeyTypeAdapter.get();
    }

    static GsonBuilder registerEntityTypeTypeAdapter(GsonBuilder gson) {
        return gson.registerTypeHierarchyAdapter(EntityTypes.class, getEntityTypeTypeAdapter());
    }

    static GsonBuilderDynamic registerEntityTypeTypeAdapter(GsonBuilderDynamic gson) {
        return gson.registerTypeHierarchyAdapter(EntityTypes.class, getEntityTypeTypeAdapter());
    }

    static EntityTypeTypeAdapter getEntityTypeTypeAdapter() {
        return EntityTypeTypeAdapter.get();
    }
}
