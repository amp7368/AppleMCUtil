package apple.mc.utilities.inventory.gui.acd.slot;

import apple.mc.utilities.inventory.gui.acd.slot.base.InventoryClickable;
import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotACD;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ItemGuiSlotImplACD implements ItemGuiSlotACD {
    private final InventoryClickable dealWithEvent;
    private final ItemStack item;
    private final Supplier<ItemStack> itemSupplier;

    public ItemGuiSlotImplACD(InventoryClickable dealWithEvent, ItemStack item) {
        this.dealWithEvent = dealWithEvent;
        this.item = item;
        this.itemSupplier = null;
    }

    public ItemGuiSlotImplACD(InventoryClickable dealWithEvent, Supplier<ItemStack> itemSupplier) {
        this.dealWithEvent = dealWithEvent;
        this.item = null;
        this.itemSupplier = itemSupplier;
    }

    @Override
    public void dealWithClick(InventoryClickEvent event) {
        dealWithEvent.dealWithClick(event);
    }

    @Override
    public ItemStack getItem() {
        if (itemSupplier != null) return itemSupplier.get();
        return item;
    }
}
