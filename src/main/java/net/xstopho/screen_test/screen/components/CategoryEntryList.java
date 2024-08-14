package net.xstopho.screen_test.screen.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.xstopho.screen_test.screen.entries.base.BaseEntry;

public class CategoryEntryList extends ContainerObjectSelectionList<BaseEntry> {

    public CategoryEntryList(Minecraft minecraft, int width, int contentHeight, int headerHeight, int itemSpacing, Iterable<BaseEntry> entries) {
        super(minecraft, width, contentHeight, headerHeight, itemSpacing);
        entries.forEach(this::addEntry);
    }

    @Override
    public int getRowWidth() {
        Screen screen = Minecraft.getInstance().screen;
        return this.width - (screen.width / 4);
    }
}
