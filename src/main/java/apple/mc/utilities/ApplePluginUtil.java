package apple.mc.utilities;

import apple.mc.utilities.inventory.gui.PluginInventoryGui;
import apple.mc.utilities.player.wand.ModuleWand;
import com.voltskiya.lib.AbstractModule;
import com.voltskiya.lib.AbstractVoltPlugin;
import java.util.Collection;
import java.util.List;

public class ApplePluginUtil extends AbstractVoltPlugin {

    private static ApplePluginUtil instance;

    public ApplePluginUtil() {
        instance = this;
    }

    public static ApplePluginUtil get() {
        return instance;
    }

    @Override
    public Collection<AbstractModule> getModules() {
        return List.of(new ModuleWand(), new PluginInventoryGui());
    }
}
