package net.xstopho.config_screen.screen.entries.primitiv;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.config.TestConfigEntry;
import net.xstopho.config_screen.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Pattern;

public class ByteValueEntry extends ValueEntry<Byte> {

    private final EditBox editBox;

    private final Pattern bytePattern = Pattern.compile("^(?:-?(?:12[0-7]|1[01][0-9]|[1-9]?[0-9])|-)?$");

    public ByteValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry<Byte> entry) {
        super(entryLabel, entryTooltip, entry);

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth(), 18, Component.literal(""));
        this.editBox.setFilter(value -> bytePattern.matcher(value).matches());
        this.editBox.setValue(entry.getConfigValue().toString());
        this.editBox.setResponder(value -> setUndoState(!Objects.equals(value, entry.getConfigValue().toString())));

        this.children.add(editBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);

        editBox.setX(xPos + entryWidth - getValueWidgetWidth());
        editBox.setY(yPos + 1);
        editBox.setWidth(getCorrectedWidgetWidth());

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public Byte getChangedValue() {
        if (editBox.getValue().isEmpty()) {
            return entry.getConfigValue();
        }
        return Byte.valueOf(editBox.getValue());
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