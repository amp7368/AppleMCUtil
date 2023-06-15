package apple.mc.utilities.inventory.gui.acd.slot;

import apple.mc.utilities.inventory.gui.acd.slot.base.InventoryClickable;
import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotSplitACD;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ItemGuiSlotSplitImplACD implements ItemGuiSlotSplitACD {
    private final ItemStack item;
    private final Supplier<ItemStack> itemSupplier;
    private InventoryClickable noShiftLeftClick = null;
    private InventoryClickable shiftLeftClick = null;
    private InventoryClickable noShiftRightClick = null;
    private InventoryClickable shiftRightClick = null;
    private InventoryClickable leftClick = null;
    private InventoryClickable rightClick = null;
    private InventoryClickable keyboardClick = null;

    public ItemGuiSlotSplitImplACD(ItemStack item) {
        this.item = item;
        this.itemSupplier = null;
    }

    public ItemGuiSlotSplitImplACD(Supplier<ItemStack> itemSupplier) {
        this.item = null;
        this.itemSupplier = itemSupplier;
    }

    public ItemGuiSlotSplitImplACD withNoShiftLeftClick(InventoryClickable noShiftLeftClick) {
        this.noShiftLeftClick = noShiftLeftClick;
        return this;
    }

    public ItemGuiSlotSplitImplACD withShiftLeftClick(InventoryClickable shiftLeftClick) {
        this.shiftLeftClick = shiftLeftClick;
        return this;
    }

    public ItemGuiSlotSplitImplACD withNoShiftRightClick(InventoryClickable noShiftRightClick) {
        this.noShiftRightClick = noShiftRightClick;
        return this;
    }

    public ItemGuiSlotSplitImplACD withShiftRightClick(InventoryClickable shiftRightClick) {
        this.shiftRightClick = shiftRightClick;
        return this;
    }

    public ItemGuiSlotSplitImplACD withLeftClick(InventoryClickable leftClick) {
        this.leftClick = leftClick;
        return this;
    }

    public ItemGuiSlotSplitImplACD withRightClick(InventoryClickable rightClick) {
        this.rightClick = rightClick;
        return this;
    }

    public ItemGuiSlotSplitImplACD withKeyboardClick(InventoryClickable keyboardClick) {
        this.keyboardClick = keyboardClick;
        return this;
    }

    @Override
    public ItemStack getItem() {
        if (itemSupplier != null) return itemSupplier.get();
        return item;
    }

    @Override
    public void noShiftLeftClick(InventoryClickEvent event) {
        if (noShiftLeftClick != null) noShiftLeftClick.dealWithClick(event);
    }

    @Override
    public void shiftLeftClick(InventoryClickEvent event) {
        if (shiftLeftClick != null) shiftLeftClick.dealWithClick(event);
    }

    @Override
    public void noShiftRightClick(InventoryClickEvent event) {
        if (noShiftRightClick != null) noShiftRightClick.dealWithClick(event);
    }

    @Override
    public void shiftRightClick(InventoryClickEvent event) {
        if (shiftRightClick != null) shiftRightClick.dealWithClick(event);
    }

    @Override
    public void leftClick(InventoryClickEvent event) {
        if (leftClick != null) leftClick.dealWithClick(event);
    }

    @Override
    public void rightClick(InventoryClickEvent event) {
        if (rightClick != null) rightClick.dealWithClick(event);
    }

    @Override
    public void keyboardClick(InventoryClickEvent event) {
        if (keyboardClick != null) keyboardClick.dealWithClick(event);
    }
}
