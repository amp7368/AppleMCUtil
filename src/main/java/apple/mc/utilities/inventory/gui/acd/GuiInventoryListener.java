package apple.mc.utilities.inventory.gui.acd;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GuiInventoryListener extends InventoryHolder {

    void onGuiInventory(InventoryClickEvent event);

    void onPlayerInventory(InventoryClickEvent event);

    void initialize();

    void initialize2();

    default void initAll() {
        initialize();
        initialize2();
    }

    default void refresh() {
        refresh(null);
    }

    default void refresh(@Nullable List<HumanEntity> viewers) {
        refreshPageItems();
        finalizePageItems();
        showPageItems(viewers);
    }

    default void refreshPageItems() {
    }

    void finalizePageItems();

    void showPageItems(@Nullable List<HumanEntity> viewers);
}
