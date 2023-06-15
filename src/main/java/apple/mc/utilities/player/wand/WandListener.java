package apple.mc.utilities.player.wand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class WandListener implements Listener {

    private static final Map<JavaPlugin, WandListener> listeners = new HashMap<>();
    private final Map<NamespacedKey, WandType<?>> keyToWandType = new HashMap<>();

    public WandListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void register(JavaPlugin plugin, NamespacedKey itemKey, WandType<?> wandType) {
        WandListener listener = listeners.computeIfAbsent(plugin, WandListener::new);
        listener.register(itemKey, wandType);
    }

    private void register(NamespacedKey itemKey, WandType<?> wandType) {
        synchronized (keyToWandType) {
            this.keyToWandType.put(itemKey, wandType);
        }
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        Set<NamespacedKey> keys = meta.getPersistentDataContainer().getKeys();
        for (NamespacedKey key : keys) {
            WandType<?> wandType;
            synchronized (keyToWandType) {
                wandType = keyToWandType.get(key);
            }
            if (wandType != null) wandType.onEvent(event);
        }
    }
}
