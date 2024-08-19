package net.xstopho.config_screen.screen.entries.selection.single.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;


public abstract class SingleSelectionEntry extends BaseEntry {

    protected final Button button;

    public SingleSelectionEntry(Screen previous, Component entryLabel, Component entryTooltip, EditBox parent) {

        this.button = Button.builder(entryLabel, b -> {
            Minecraft.getInstance().setScreen(previous);
            parent.setValue(this.valueToString());
            parent.setCursorPosition(0);
            parent.setHighlightPos(0);
        }).tooltip(Tooltip.create(entryTooltip)).build();

        this.children.add(button);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        button.setX(xPos + (entryWidth / 2) - 75);
        button.setY(yPos);

        button.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    public abstract String valueToString();

    @Override
    public void saveChangedValue() {}

    @Override
    public void undoChanges() {}

    @Override
    public void resetValues() {}
}
