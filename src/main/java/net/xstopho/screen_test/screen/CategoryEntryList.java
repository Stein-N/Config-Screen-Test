package net.xstopho.screen_test.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

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
        var entry = this.getHovered();
        if (entry != null) {
            if (entry instanceof CategoryEntry categoryEntry) {
                if (categoryEntry.button != null && categoryEntry.button.isHovered()) {
                    tab.getScreen().setTooltipForNextRenderPass(Component.literal("Tooltip for a Button!"));
                    return;
                }
                if (categoryEntry.editBox != null && categoryEntry.editBox.isHovered()) {
                    tab.getScreen().setTooltipForNextRenderPass(Component.literal("This is a tooltip for Ranged values!"));
                }
            }
        }
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {

    }

    @Override
    public int getRowWidth() {
        Screen screen = Minecraft.getInstance().screen;
        return this.width - (screen.width / 4);
    }
}
