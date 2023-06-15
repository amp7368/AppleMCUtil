package apple.mc.utilities.inventory.gui.acd.page;

import apple.mc.utilities.inventory.gui.acd.GuiInventoryListener;
import apple.mc.utilities.inventory.gui.acd.slot.ACDSlotFactory;
import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotACD;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface InventoryGuiPageACD extends GuiInventoryListener, ACDSlotFactory {
    String getName();

    @NotNull Inventory getInventory();

    void setSlot(ItemGuiSlotACD item, int... slot);

    default void setSlot(int slot, ItemGuiSlotACD item) {
        setSlot(item, slot);
    }

    @Override
    default void showPageItems(@Nullable List<HumanEntity> viewers) {
        Inventory inventory = this.getInventory();
        if (viewers == null) viewers = inventory.getViewers();
        for (HumanEntity viewer : new ArrayList<>(viewers)) {
            viewer.openInventory(inventory);
        }
    }

    int size();
}
