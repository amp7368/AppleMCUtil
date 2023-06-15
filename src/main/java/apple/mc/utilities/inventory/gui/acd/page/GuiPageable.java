package apple.mc.utilities.inventory.gui.acd.page;

import apple.mc.utilities.inventory.gui.acd.GuiInventoryListener;
import org.bukkit.inventory.InventoryHolder;

public interface GuiPageable extends InventoryHolder {

    void parentAddSubPage(GuiInventoryListener subPage);

    void parentRemoveSubPage();

    void parentNext(int i);

    void parentRefresh();

    default void parentPrev() {
        parentNext(-1);
    }

    default void parentNext() {
        parentNext(1);
    }

}
