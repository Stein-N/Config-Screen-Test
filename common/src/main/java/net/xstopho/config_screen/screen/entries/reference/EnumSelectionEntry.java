package net.xstopho.config_screen.screen.entries.reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;

public class EnumSelectionEntry<T extends Enum<T>> extends BaseEntry {

    private final Button valueButton;

    public EnumSelectionEntry(Screen previous, Component entryLabel, Component entryTooltip, EditBox parentBox, Enum<T> value) {

        this.valueButton = Button.builder(entryLabel, b -> {
            Minecraft.getInstance().setScreen(previous);
            parentBox.setValue(value.toString());
            parentBox.setCursorPosition(0);
            parentBox.setHighlightPos(0);
        }).tooltip(Tooltip.create(entryTooltip)).width(150).build();

        this.children.add(valueButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        valueButton.setX(xPos + (entryWidth / 2) - 75);
        valueButton.setY(yPos);

        valueButton.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void saveChangedValue() {}

    @Override
    public void undoChanges() {}

    @Override
    public void resetValues() {}
}
