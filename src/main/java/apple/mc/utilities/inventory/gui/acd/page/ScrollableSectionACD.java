package apple.mc.utilities.inventory.gui.acd.page;

import apple.mc.utilities.inventory.gui.acd.slot.ItemGuiSlotDisplayACD;
import apple.mc.utilities.inventory.gui.acd.slot.base.ItemGuiSlotACD;
import apple.utilities.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScrollableSectionACD {
    private final List<ItemGuiSlotACD> scrollables = new ArrayList<>();
    private final String name;
    private final int[] slotIndexes;
    private int currentIndex = 0;
    private int scrollAmount;

    public ScrollableSectionACD(String name, int[] slotIndexes) {
        this.name = name;
        this.slotIndexes = slotIndexes;
        this.scrollAmount = 9;
    }

    public ScrollableSectionACD(String name, int lower, int upper, int scrollAmount) {
        this(name, lower, upper);
        this.scrollAmount = scrollAmount;
    }

    public ScrollableSectionACD(String name, int lower, int upper) {
        this.name = name;
        List<Integer> slots = new ArrayList<>();
        int upperMod = (upper - 1) % 9;
        int lowerMod = lower % 9;
        for (int i = lower; i < upper; i++) {
            slots.add(i);
            if (i % 9 == upperMod) {
                i = (i / 9 + 1) * 9 - 1 + lowerMod;
            }
        }
        this.scrollAmount = upperMod - lowerMod + 1;
        this.slotIndexes = slots.stream().mapToInt(Integer::intValue).toArray();
    }

    public void addItem(ItemGuiSlotACD item) {
        scrollables.add(item);
    }

    public int[] getSlotIndex() {
        return slotIndexes;
    }

    public void scroll(int i) {
        this.currentIndex += i * scrollAmount;
    }

    public String getName() {
        return name;
    }

    public ItemGuiSlotACD getItem(int index) {
        if (NumberUtils.between(0, index, scrollables.size()))
            return Objects.requireNonNullElseGet(scrollables.get(index), ItemGuiSlotDisplayACD::empty);
        return ItemGuiSlotDisplayACD.empty();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void clear() {
        this.scrollables.clear();
    }

    public void removeItem(ItemGuiSlotACD slot) {
        this.scrollables.remove(slot);
    }
}
