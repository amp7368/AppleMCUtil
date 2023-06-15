package apple.mc.utilities.player.wand;

import org.bukkit.event.block.Action;

public interface WandUtil {
    default boolean actionIsLeft(Action action) {
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
    }

    default boolean actionIsRight(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }
}
