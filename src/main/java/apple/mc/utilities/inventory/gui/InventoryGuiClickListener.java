package apple.mc.utilities.inventory.gui;

import apple.mc.utilities.ApplePluginUtil;
import apple.mc.utilities.inventory.gui.acd.GuiInventoryListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryGuiClickListener implements Listener {

    public InventoryGuiClickListener() {
        ApplePluginUtil.get().registerEvents(this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onGuiClick(InventoryClickEvent event) {
        final Inventory clickedInventory = event.getClickedInventory();
        if ((clickedInventory != null
            && clickedInventory.getHolder() instanceof GuiInventoryListener gui)) {
            // clicking from the turret
            gui.onGuiInventory(event);
        } else {
            final InventoryHolder topInventory = event.getView().getTopInventory().getHolder();
            if (topInventory instanceof GuiInventoryListener gui) {
                gui.onPlayerInventory(event);
            }
        }
    }
}
