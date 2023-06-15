package apple.mc.utilities.inventory.gui;

import com.voltskiya.lib.AbstractModule;

public class PluginInventoryGui extends AbstractModule {

    @Override
    public void enable() {
        new InventoryGuiClickListener();
    }

    @Override
    public String getName() {
        return "InventoryGui";
    }


}
