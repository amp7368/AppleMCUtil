package apple.mc.utilities.player.wand;

import apple.mc.utilities.inventory.item.InventoryUtils;
import com.voltskiya.lib.AbstractVoltPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandType<W extends Wand> {

    private final Map<UUID, W> playersToWand = new HashMap<>();
    private final Function<Player, W> create;
    private final NamespacedKey itemKey;

    public WandType(Function<Player, W> create, AbstractVoltPlugin plugin, NamespacedKey itemKey) {
        this.create = create;
        this.itemKey = itemKey;
        WandListener.register(plugin, itemKey, this);
    }

    public boolean isItemWand(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().has(this.itemKey);
    }

    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        getWand(player).onEvent(event);
    }

    public W getWand(Player player) {
        return playersToWand.computeIfAbsent(player.getUniqueId(),
            (uuid) -> this.createWand(player));
    }

    private W createWand(Player player) {
        return this.create.apply(player);
    }

    public ItemStack createItem(Material material, String name) {
        ItemStack item = InventoryUtils.get().makeItem(material, name);
        return InventoryUtils.get().addDataString(item, this.itemKey, "");
    }
}
