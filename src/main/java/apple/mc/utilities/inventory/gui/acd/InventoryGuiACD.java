package apple.mc.utilities.inventory.gui.acd;

import apple.mc.utilities.inventory.gui.acd.page.GuiPageable;
import apple.utilities.util.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryGuiACD implements GuiInventoryListener, GuiPageable {

    protected final List<GuiInventoryListener> pageMap = new ArrayList<>();
    protected final List<GuiInventoryListener> subPages = new ArrayList<>();

    protected int page = 0;

    public InventoryGuiACD() {
    }

    protected GuiInventoryListener getPage() {
        return this.subPages.isEmpty() ? pageMap.get(page) : subPages.get(0);
    }

    protected void addPage(GuiInventoryListener... pageGuis) {
        for (GuiInventoryListener pageGui : pageGuis) {
            pageGui.initAll();
            pageMap.add(pageGui);
        }
    }

    @Override
    public void parentAddSubPage(GuiInventoryListener subPage) {
        List<HumanEntity> viewers = getInventory().getViewers();
        subPage.initAll();
        this.subPages.add(0, subPage);
        refresh(viewers);
    }

    @Override
    public void parentRemoveSubPage() {
        List<HumanEntity> viewers = getInventory().getViewers();
        if (!subPages.isEmpty())
            this.subPages.remove(0);
        refresh(viewers);
    }

    @Override
    public void parentNext(int count) {
        List<HumanEntity> viewers = getInventory().getViewers();
        page = NumberUtils.getBetween(0, page + count, pageMap.size());
        refresh(viewers);
    }

    @Override
    public void parentRefresh() {
        refresh();
    }

    @Override
    public void finalizePageItems() {
        getPage().finalizePageItems();
    }

    @Override
    public void refreshPageItems() {
        getPage().refreshPageItems();
    }

    @Override
    public void showPageItems(@Nullable List<HumanEntity> viewers) {
        getPage().showPageItems(viewers);
    }

    @Override
    public void onGuiInventory(InventoryClickEvent event) {
        GuiInventoryListener p = getPage();
        if (p != null) {
            event.setCancelled(true);
            p.onGuiInventory(event);
        }
    }

    @Override
    public void onPlayerInventory(InventoryClickEvent event) {
        GuiInventoryListener p = getPage();
        event.setCancelled(true);
        if (p != null) {
            p.onPlayerInventory(event);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void initialize2() {
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return getPage().getInventory();
    }
}
