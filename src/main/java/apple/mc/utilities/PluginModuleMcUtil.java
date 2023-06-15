package apple.mc.utilities;

import apple.mc.utilities.player.wand.Wand;
import apple.mc.utilities.player.wand.WandType;
import com.voltskiya.lib.AbstractVoltPlugin;
import java.util.function.Function;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public interface PluginModuleMcUtil {

    default <W extends Wand> WandType<W> createWand(Function<Player, W> fn, String itemKey) {
        AbstractVoltPlugin plugin = getPlugin();
        return new WandType<>(fn, plugin, new NamespacedKey(plugin, itemKey));
    }

    AbstractVoltPlugin getPlugin();
}
