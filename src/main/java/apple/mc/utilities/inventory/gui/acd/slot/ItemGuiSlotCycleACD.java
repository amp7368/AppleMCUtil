package apple.mc.utilities.inventory.gui.acd.slot;

import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotSplitACD;
import apple.mc.utilities.inventory.item.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemGuiSlotCycleACD<EnumType extends ItemGuiSlotCycleable<EnumType>> implements ItemGuiSlotSplitACD {
    private final Supplier<EnumType> currentTypeGetter;
    private final Consumer<EnumType> currentTypeSetter;
    private Function<EnumType, Material> convertToMaterial = null;
    private Function<EnumType, String> convertToName = null;
    private Function<EnumType, Integer> convertToCount = null;
    private Function<EnumType, List<String>> convertToLore = null;
    private Function<EnumType, ItemStack> convertToItem = null;

    public ItemGuiSlotCycleACD(Supplier<EnumType> currentTypeGetter, Consumer<EnumType> currentTypeSetter) {
        this.currentTypeGetter = currentTypeGetter;
        this.currentTypeSetter = currentTypeSetter;
    }

    @Override
    public void leftClick(InventoryClickEvent event) {
        currentTypeSetter.accept(currentTypeGetter.get().left());
    }

    @Override
    public void rightClick(InventoryClickEvent event) {
        currentTypeSetter.accept(currentTypeGetter.get().right());
    }

    @Override
    public ItemStack getItem() {
        if (convertToItem != null) {
            return convertToItem.apply(currentTypeGetter.get());
        } else {
            Material material = convertToMaterial == null ? currentTypeGetter.get()
                                                                             .itemMaterial() : convertToMaterial.apply(
                    currentTypeGetter.get());
            int count = convertToCount == null ? currentTypeGetter.get().itemCount() : convertToCount.apply(
                    currentTypeGetter.get());
            String name = convertToName == null ? currentTypeGetter.get().itemName() : convertToName.apply(
                    currentTypeGetter.get());
            List<String> lore = convertToLore == null ? currentTypeGetter.get().itemLore() : convertToLore.apply(
                    currentTypeGetter.get());

            return InventoryUtils.get().makeItem(
                    material,
                    count,
                    name,
                    lore
            );
        }
    }

    public ItemGuiSlotCycleACD<EnumType> withConvertToMaterial(Function<EnumType, Material> convertToMaterial) {
        this.convertToMaterial = convertToMaterial;
        return this;
    }

    public ItemGuiSlotCycleACD<EnumType> withConvertToName(Function<EnumType, String> convertToName) {
        this.convertToName = convertToName;
        return this;
    }

    public ItemGuiSlotCycleACD<EnumType> withConvertToCount(Function<EnumType, Integer> convertToCount) {
        this.convertToCount = convertToCount;
        return this;
    }

    public ItemGuiSlotCycleACD<EnumType> withConvertToLore(Function<EnumType, List<String>> convertToLore) {
        this.convertToLore = convertToLore;
        return this;
    }

    public ItemGuiSlotCycleACD<EnumType> withConvertToItem(Function<EnumType, ItemStack> convertToItem) {
        this.convertToItem = convertToItem;
        return this;
    }
}
