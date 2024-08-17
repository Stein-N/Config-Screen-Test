package net.xstopho.config_screen.screen.entries.reference;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.config.TestConfigEntry;
import net.xstopho.config_screen.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StringValueEntry extends ValueEntry<String> {

    private final EditBox editBox;


    public StringValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.StringEntry entry) {
        super(entryLabel, entryTooltip, entry);

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth() + 50, 18, Component.literal(""));
        this.editBox.setResponder(string -> setUndoState(!Objects.equals(string, entry.getConfigValue())));
        this.editBox.setValue(entry.getConfigValue());
        this.editBox.setCursorPosition(0);
        this.editBox.setHighlightPos(0);

        this.children.add(editBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);

        drawStringWithTooltip(guiGraphics, entryLabel, entryTooltip, xPos, yPos + 6, mouseX, mouseY, hovered);

        editBox.setX(xPos + entryWidth - (getValueWidgetWidth() + 50));
        editBox.setY(yPos + 1);

        editBox.setWidth((getValueWidgetWidth() + 50) - (undoButton.getWidth() + resetButton.getWidth()) - 1);

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public String getChangedValue() {
        if (editBox.getValue().isEmpty()) return entry.getConfigValue();
        return editBox.getValue();
    }

    @Override
    public void undoChanges() {
        editBox.setValue(this.entry.getConfigValue());
    }

    @Override
    public void resetValues() {
        editBox.setValue(this.entry.getDefaultValue());
    }
}
