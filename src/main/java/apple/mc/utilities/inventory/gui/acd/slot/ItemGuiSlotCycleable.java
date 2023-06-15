package apple.mc.utilities.inventory.gui.acd.slot;

import org.bukkit.Material;

import java.util.List;

public interface ItemGuiSlotCycleable<Me extends ItemGuiSlotCycleable<Me>> {
    default Me left() {
        return offset(-1);
    }

    default Me right() {
        return offset(1);
    }

    default Me offset(int i) {
        int size = size();
        i = (index() + i) % size;
        while (i < 0) i += size;
        return valuesList()[i];
    }

    default int index() {
        return ordinal();
    }

    int ordinal();

    Me[] valuesList();

    default int size() {
        return valuesList().length;
    }

    default Material itemMaterial() {
        return Material.BEDROCK;
    }

    default int itemCount() {
        return 1;
    }

    default String itemName() {
        return name();
    }

    String name();

    default List<String> itemLore() {
        return List.of(
                String.format("Previous: '%s'", left().itemName()),
                String.format("Next: '%s'", right().itemName())
        );
    }
}
