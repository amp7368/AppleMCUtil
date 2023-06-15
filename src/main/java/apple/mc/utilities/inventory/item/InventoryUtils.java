package apple.mc.utilities.inventory.item;

import apple.mc.utilities.ApplePluginUtil;
import apple.utilities.util.ArrayUtils;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InventoryUtils {

    NamespacedKey ITEM_FLAGS = ApplePluginUtil.get().namespacedKey("item_flags");
    PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
    InventoryUtils instance = new InventoryUtils() {
    };

    static InventoryUtils get() {
        return instance;
    }

    default ItemStack makeItem(Material material) {
        return makeItem(material, 1, (String) null, null);
    }

    default ItemStack makeItem(Material material, String name) {
        return makeItem(material, 1, name, null);
    }

    default ItemStack makeItem(Material material, int amount, @Nullable String name,
        @Nullable List<String> lore) {
        final ItemStack item = new ItemStack(material, amount);
        this.lore(lore, item);
        return this.displayName(item, name);
    }


    default ItemStack addDataString(@NotNull ItemStack itemStack,
        @NotNull NamespacedKey namespacedKey, @NotNull String value) {
        return addData(itemStack, namespacedKey, PersistentDataType.STRING, value);
    }


    default <T, Z> ItemStack addData(@NotNull ItemStack itemStack,
        @NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> type,
        @NotNull Z value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, type, value);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    default String getString(@Nullable ItemStack itemStack, @NotNull NamespacedKey namespacedKey) {
        return getData(itemStack, namespacedKey, PersistentDataType.STRING);
    }


    default <T, Z> Z getData(@Nullable ItemStack itemStack, @NotNull NamespacedKey namespacedKey,
        @NotNull PersistentDataType<T, Z> type) {
        if (itemStack == null)
            return null;
        else
            return itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, type);
    }

    @NotNull
    default String[] getItemFlags(ItemStack item) {
        @Nullable String data = getData(item, ITEM_FLAGS, PersistentDataType.STRING);
        if (data == null)
            return new String[0];
        return data.split(",");
    }

    default void addItemFlags(ItemStack item, String... flags) {
        String[] oldFlags = getItemFlags(item);
        oldFlags = ArrayUtils.combine(oldFlags, flags, (Function<Integer, String[]>) String[]::new);
        setItemFlags(item, oldFlags);
    }

    default void setItemFlags(ItemStack item, String... flags) {
        addData(item, ITEM_FLAGS, PersistentDataType.STRING, String.join(",", flags));
    }

    default ItemStack displayName(ItemStack item, String name) {
        if (name == null)
            return item;
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(serializer.deserializeOr(name, Component.text(name)));
        item.setItemMeta(itemMeta);
        return item;
    }

    default ItemStack lore(@Nullable List<String> lore, ItemStack item) {
        if (lore == null)
            return item;
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> deserialized = lore
            .stream()
            .map(l -> serializer.deserializeOr(l, Component.text(l)).asComponent())
            .toList();
        itemMeta.lore(deserialized);
        item.setItemMeta(itemMeta);
        return item;
    }


    default @NotNull List<String> getLore(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        @Nullable List<Component> lore = itemMeta.lore();
        if (lore == null)
            return Collections.emptyList();
        return lore.stream().map(serializer::serialize).toList();
    }

    default @NotNull String getDisplayName(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        Component displayName = itemMeta.displayName();
        if (displayName == null)
            return item.getType().name();
        return serializer.serialize(displayName);
    }
}
