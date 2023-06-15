package apple.mc.utilities.inventory.gui.acd.slot;

import apple.mc.utilities.inventory.gui.acd.slot.base.InventoryClickable;
import apple.mc.utilities.inventory.item.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ACDSlotFactory extends InventoryUtils {
    ACDSlotFactory instance = new ACDSlotFactory() {
    };

    static ACDSlotFactory get() {
        return instance;
    }

    default ItemGuiSlotImplACD slotImpl(InventoryClickable dealWithClick, ItemStack itemStack) {
        return new ItemGuiSlotImplACD(dealWithClick, itemStack);
    }

    default ItemGuiSlotDisplayACD slotDoNothing(ItemStack item) {
        return ItemGuiSlotDisplayACD.get(item);
    }

    default <EnumType extends ItemGuiSlotCycleable<EnumType>> ItemGuiSlotCycleACD<EnumType> slotCycle(Supplier<EnumType> getter, Consumer<EnumType> setter) {
        return new ItemGuiSlotCycleACD<>(getter, setter);
    }

    default ItemStack backItem() {
        return makeItem(Material.RED_TERRACOTTA, "Back");
    }

    default ItemStack forwardItem() {
        return makeItem(Material.GREEN_TERRACOTTA, "Forward");
    }

    default ItemStack saveItem() {
        return makeItem(Material.GREEN_TERRACOTTA, "Save");
    }

    default ItemStack blackGlassEmpty() {
        return makeItem(Material.BLACK_STAINED_GLASS_PANE, "");
    }

    default ItemGuiSlotDisplayACD blackGlassDoNothing() {
        return ItemGuiSlotDisplayACD.get(blackGlassEmpty());
    }
}
