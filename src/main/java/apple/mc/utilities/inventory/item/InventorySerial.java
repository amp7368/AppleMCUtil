package apple.mc.utilities.inventory.item;


import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySerial {
    private final ItemSerial[] items;

    public InventorySerial() {
        items = new ItemSerial[0];
    }

    public InventorySerial(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        this.items = new ItemSerial[contents.length];
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            this.items[i] = new ItemSerial(item, false);
        }
    }

    public ItemStack[] toContents() {
        ItemStack[] contents = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            contents[i] = items[i].getItem();
        }
        return contents;
    }
}

