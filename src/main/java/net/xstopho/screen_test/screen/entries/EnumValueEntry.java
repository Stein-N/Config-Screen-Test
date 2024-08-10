package net.xstopho.screen_test.screen.entries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.SingleSelectionScreen;
import net.xstopho.screen_test.screen.entries.base.BaseEntry;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class EnumValueEntry<T extends Enum<T>> extends ValueEntry<T> {

    private final TestConfigEntry.EnumConfigEntry<T> entry;
    private final EditBox editBox;
    private final Screen currentScreen = Minecraft.getInstance().screen;

    public EnumValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.EnumConfigEntry<T> entry) {
        super(entryLabel, entryTooltip);
        this.entry = entry;

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth() + 50, 18, Component.literal(""));
        this.editBox.setValue(entry.getConfigValue().toString());
        this.editBox.setEditable(false);

        this.children.add(editBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);

        drawStringWithTooltip(guiGraphics, entryLabel, entryTooltip, xPos, yPos + 6, mouseX, mouseY, hovered);

        editBox.setX(xPos + entryWidth - getValueWidgetWidth() - 50);
        editBox.setY(yPos + 1);
        editBox.setWidth(getValueWidgetWidth() + 50 - (undoButton.getWidth() + resetButton.getWidth()) - 1);
        this.editBox.setResponder(string -> setUndoState(!Objects.equals(string, entry.getConfigValue().toString())));

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);

        if (editBox.isFocused()) {
            editBox.setFocused(false);
            Minecraft.getInstance().setScreen(new SingleSelectionScreen(currentScreen, this.entryLabel, createEntries()));
        }
    }

    private List<BaseEntry> createEntries() {
        List<BaseEntry> list = new LinkedList<>();

        for (Enum<T> value : entry.getEnumValues()) {
            list.add(new EnumSelectionEntry<>(currentScreen, Component.literal(value.toString()), Component.literal("Description for " + value), editBox, value));
        }

        return list;
    }

    @Override
    public T getChangedValue() {
        return Enum.valueOf(entry.getEnumClass(), editBox.getValue());
    }

    @Override
    public void saveChangedValue() {
        entry.setConfigValue(getChangedValue());
        setUndoState(false);
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
