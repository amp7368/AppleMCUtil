package apple.mc.utilities.inventory.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class ItemSerial {
    private Material material;
    private int count;
    private String name;
    private List<String> lore;

    public ItemSerial(@Nullable ItemStack item, boolean ignoreCount) {
        if (item == null) {
            this.material = Material.AIR;
            this.count = 0;
            this.name = null;
            this.lore = null;
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
        if (itemMeta.hasDisplayName()) {
            Component displayName = itemMeta.displayName();
            if (displayName == null) this.name = null;
            else this.name = serializer.serialize(displayName);
        } else {
            this.name = null;
        }
        if (itemMeta.hasDisplayName()) {
            @Nullable List<Component> displayName = itemMeta.lore();
            if (displayName == null) this.lore = null;
            else {
                this.lore = displayName.stream().map(serializer::serialize).collect(Collectors.toList());
            }
        } else {
            this.lore = null;
        }
        this.material = item.getType();
        this.count = ignoreCount ? 1 : item.getAmount();
    }

    public ItemSerial(Material material, int count, String name, List<String> lore) {
        this.material = material;
        this.count = count;
        this.name = name;
        this.lore = lore;
    }

    public ItemSerial() {
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemStack getItem() {
        return InventoryUtils.get().makeItem(material, count, name, lore);
    }

    public ItemSerial copy() {
        return new ItemSerial(this.material, this.count, this.name, this.lore);
    }
}
