package net.xstopho.screen_test.screen.entries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.helper.ConfigEntryCreator;
import net.xstopho.screen_test.screen.SingleSelectionScreen;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

public class EnumValueEntry<T extends Enum<T>> extends ValueEntry<T> {

    private final TestConfigEntry.EnumConfigEntry<T> entry;
    private final EditBox editBox;

    public EnumValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.EnumConfigEntry<T> entry) {
        super(entryLabel, entryTooltip);
        this.entry = entry;

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth(), 18, Component.literal(""));
        this.editBox.setValue(entry.getConfigValue().toString());
        this.editBox.setEditable(false);

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

        if (editBox.isFocused()) {
            editBox.setFocused(false);
            Minecraft.getInstance().setScreen(new SingleSelectionScreen(Minecraft.getInstance().screen, this.entryLabel, ConfigEntryCreator.createDummies()));
        }
    }

    @Override
    public T getChangedValue() {
        return Enum.valueOf(entry.getEnumClass(), editBox.getValue());
    }

    @Override
    public void saveChangedValue() {
        entry.setConfigValue(getChangedValue());
    }

    @Override
    public void undoChanges() {
        editBox.setValue(entry.getConfigValue().toString());
    }

    @Override
    public void resetValues() {
        editBox.setValue(entry.getDefaultValue().toString());
    }
}
