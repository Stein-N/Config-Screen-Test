package net.xstopho.screen_test.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class CategoryEntryList extends ContainerObjectSelectionList<CategoryEntry> {

    private final CategoryTab tab;

    public CategoryEntryList(CategoryTab tab, Minecraft minecraft, int width, int contentHeight, int headerHeight, int itemSpacing, Iterable<CategoryEntry> entries) {
        super(minecraft, width, contentHeight, headerHeight, itemSpacing);
        this.tab = tab;
        entries.forEach(this::addEntry);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {

    }

    @Override
    public int getRowWidth() {
        return this.width - 120;
    }
}
