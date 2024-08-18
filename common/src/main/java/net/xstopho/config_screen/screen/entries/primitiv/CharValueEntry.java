package net.xstopho.config_screen.screen.entries.primitiv;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.config.TestConfigEntry;
import net.xstopho.config_screen.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Pattern;

public class CharValueEntry extends ValueEntry<Character> {

    private final EditBox editBox;
    private final Pattern charPattern = Pattern.compile("[a-zA-Z]?");

    public CharValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry<Character> entry) {
        super(entryLabel, entryTooltip, entry);

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth(), 18, Component.literal(""));
        this.editBox.setFilter(value -> charPattern.matcher(value).matches());
        this.editBox.setValue(entry.getConfigValue().toString());
        this.editBox.setResponder(value -> setUndoState(!Objects.equals(value, entry.getConfigValue().toString())));

        this.children.add(editBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);

        drawStringWithTooltip(guiGraphics, entryLabel, entryTooltip, xPos, yPos + 6, mouseX, mouseY, hovered);

        editBox.setX(xPos + entryWidth - getValueWidgetWidth());
        editBox.setY(yPos + 1);
        editBox.setWidth(getValueWidgetWidth() - (undoButton.getWidth() + resetButton.getWidth()) - 1);

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public Character getChangedValue() {
        if (editBox.getValue().isEmpty()) return entry.getConfigValue();
        return this.editBox.getValue().charAt(0);
    }

    @Override
    public void undoChanges() {
        editBox.setValue(String.valueOf(this.entry.getConfigValue()));
    }

    @Override
    public void resetValues() {
        editBox.setValue(String.valueOf(this.entry.getDefaultValue()));
    }
}
