package net.xstopho.config_screen.screen.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;

public class CategoryEntry extends BaseEntry {

    private final Component categoryLabel;
    private final Component tooltip;

    public CategoryEntry(Component categoryLabel, Component tooltip) {
        this.categoryLabel = categoryLabel.copy().withStyle(ChatFormatting.GOLD);
        this.tooltip = tooltip;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        drawStringWithTooltip(guiGraphics, categoryLabel, tooltip,
                xPos + (entryWidth / 2) - (font.width(categoryLabel.getString()) / 2),
                yPos + 6, mouseX, mouseY, hovered);
    }

    @Override
    public void saveChangedValue() {}

    @Override
    public void undoChanges() {}

    @Override
    public void resetValues() {}
}
