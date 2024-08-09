package net.xstopho.screen_test.screen.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Pattern;

public class DoubleValueEntry extends ValueEntry<Double> {

    private final TestConfigEntry.DoubleEntry entry;
    private final EditBox editBox;

    private final Pattern DOUBLE_PATTERN = Pattern.compile("[0-9]{0,10}(\\.[0-9]{0,2})?");

    public DoubleValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.DoubleEntry entry) {
        super(entryLabel, entryTooltip);
        this.entry = entry;

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth(), 18, Component.literal(""));
        this.editBox.setFilter(value -> DOUBLE_PATTERN.matcher(value).matches());
        this.editBox.setValue(entry.getConfigValue().toString());
        this.editBox.setResponder(string -> setUndoState(!Objects.equals(string, entry.getConfigValue().toString())));

        this.children.add(editBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        drawStringWithTooltip(guiGraphics, entryLabel, entryTooltip, xPos, yPos + 6, mouseX, mouseY, hovered);

        editBox.setX(xPos + entryWidth - getValueWidgetWidth());
        editBox.setY(yPos + 1);

        undoButton.setX(xPos + entryWidth - undoButton.getWidth() - resetButton.getWidth());
        undoButton.setY(yPos);

        resetButton.setX(xPos + entryWidth - resetButton.getWidth());
        resetButton.setY(yPos);

        editBox.setWidth(getValueWidgetWidth() - (undoButton.getWidth() + resetButton.getWidth()) - 1);

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);
        undoButton.render(guiGraphics, mouseX, mouseY, partialTick);
        resetButton.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(undoSprite, undoButton.getX() + 3, undoButton.getY() + 3, 0.0F, 0.0F, 14, 14, 14, 14);
    }

    @Override
    public Double getChangedValue() {
        return Double.parseDouble(editBox.getValue());
    }

    @Override
    public void saveChangedValue() {
        entry.setConfigValue(getChangedValue());
        setUndoState(false);
    }

    @Override
    public void undoChanges() {
        editBox.setValue(this.entry.getConfigValue().toString());
    }

    @Override
    public void resetValues() {
        editBox.setValue(this.entry.getDefaultValue().toString());
    }
}
