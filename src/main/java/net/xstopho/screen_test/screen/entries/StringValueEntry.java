package net.xstopho.screen_test.screen.entries;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StringValueEntry extends ValueEntry<String> {

    private final TestConfigEntry.StringEntry entry;
    private final EditBox editBox;

    public StringValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.StringEntry entry) {
        super(entryLabel, entryTooltip);
        this.entry = entry;

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth(), 18, Component.literal(""));
        this.editBox.setResponder(string -> checkUndoState(!Objects.equals(string, entry.getConfigValue())));
        this.editBox.setValue(entry.getConfigValue());

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
    public String getChangedValue() {
        return editBox.getValue();
    }

    @Override
    public boolean wasChanged() {
        return !Objects.equals(editBox.getValue(), entry.getConfigValue().toString());
    }

    @Override
    protected void undoChange(Button button) {
        editBox.setValue(this.entry.getConfigValue());
    }

    @Override
    protected void resetValue(Button button) {
        editBox.setValue(this.entry.getDefaultValue());
    }
}
