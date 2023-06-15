package apple.mc.utilities.inventory.gui.acd.slot;

import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotACD;
import apple.mc.utilities.inventory.item.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ItemGuiSlotDisplayACD implements ItemGuiSlotACD {
    private static final ItemGuiSlotDisplayACD EMPTY = get(Material.AIR);
    private final ItemStack item;
    private final Supplier<ItemStack> itemSupplier;

    private ItemGuiSlotDisplayACD(ItemStack item) {
        this.itemSupplier = null;
        this.item = item;
    }

    private ItemGuiSlotDisplayACD(Supplier<ItemStack> item) {
        this.itemSupplier = item;
        this.item = null;
    }

    public static ItemGuiSlotDisplayACD get(@Nullable Material material) {
        if (material == null) return EMPTY;
        return new ItemGuiSlotDisplayACD(InventoryUtils.get().makeItem(material));
    }

    public static ItemGuiSlotDisplayACD get(ItemStack item) {
        return new ItemGuiSlotDisplayACD(item);
    }

    public static ItemGuiSlotDisplayACD get(Supplier<ItemStack> item) {
        return new ItemGuiSlotDisplayACD(item);
    }

    public static ItemGuiSlotDisplayACD empty() {
        return EMPTY;
    }

    @Override
    public ItemStack getItem() {
        if (itemSupplier != null) return itemSupplier.get();
        return item;
    }

    @Override
    public void dealWithClick(InventoryClickEvent event) {
    }
}
