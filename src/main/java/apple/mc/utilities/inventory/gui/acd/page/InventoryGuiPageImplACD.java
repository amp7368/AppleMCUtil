package apple.mc.utilities.inventory.gui.acd.page;

import apple.mc.utilities.inventory.gui.acd.GuiInventoryListener;
import apple.mc.utilities.inventory.gui.acd.slot.ItemGuiSlotDisplayACD;
import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotACD;
import java.util.Arrays;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryGuiPageImplACD<Parent extends GuiPageable> implements InventoryGuiPageACD, GuiPageable {

    protected final ItemGuiSlotACD[] clicking = new ItemGuiSlotACD[size()];
    private final Inventory inventory;
    protected Parent parent;

    public InventoryGuiPageImplACD(Parent parent) {
        this.inventory = Bukkit.createInventory(this, size(), Component.text(getName()));
        this.parent = parent;
        Arrays.fill(clicking, ItemGuiSlotDisplayACD.empty());
    }

    @Override
    public void setSlot(ItemGuiSlotACD item, int... slot) {
        for (Integer i : slot) clicking[i] = item;
    }

    @Override
    public void parentAddSubPage(GuiInventoryListener subPage) {
        parent.parentAddSubPage(subPage);
    }

    @Override
    public void parentRemoveSubPage() {
        parent.parentRemoveSubPage();
    }

    @Override
    public void parentNext(int i) {
        parent.parentNext(i);
    }

    @Override
    public void parentRefresh() {
        refresh();
        parent.parentRefresh();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void initialize2() {
        refresh();
    }

    @Override
    public void onGuiInventory(InventoryClickEvent event) {
        int slot = event.getSlot();
        if (slot < 0 || slot >= clicking.length) return;
        clicking[slot].dealWithClick(event);
        parentRefresh();
    }

    @Override
    public void onPlayerInventory(InventoryClickEvent event) {
    }

    public void finalizePageItems() {
        for (int i = 0; i < clicking.length; i++) {
            this.getInventory().setItem(i, clicking[i].getItem());
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    @Override
    abstract public int size();
}
