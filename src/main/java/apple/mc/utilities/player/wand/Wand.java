package apple.mc.utilities.player.wand;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class Wand implements WandUtil {
    protected final Player player;

    public Wand(Player player) {
        this.player = player;
    }

    public abstract void onEvent(PlayerInteractEvent event);
}
