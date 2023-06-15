package apple.mc.utilities.inventory.gui.acd.slot.base;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface InventoryClickable {
    void dealWithClick(InventoryClickEvent event);
}
