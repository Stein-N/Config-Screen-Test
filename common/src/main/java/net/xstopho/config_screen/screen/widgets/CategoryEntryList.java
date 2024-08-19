package net.xstopho.config_screen.screen.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;

public class CategoryEntryList extends ContainerObjectSelectionList<BaseEntry> {

    public CategoryEntryList(Minecraft minecraft, int width, int contentHeight, int headerHeight, int itemSpacing, Iterable<BaseEntry> entries) {
        super(minecraft, width, contentHeight, headerHeight, itemSpacing);
        entries.forEach(this::addEntry);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}

    @Override
    public int getRowWidth() {
        return this.width - (Minecraft.getInstance().screen.width / 4);
    }
}
