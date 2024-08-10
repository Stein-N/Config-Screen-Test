package net.xstopho.screen_test.screen.entries;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Pattern;

public class FloatValueEntry extends ValueEntry<Float> {

    private final TestConfigEntry.FloatEntry entry;
    private final EditBox editBox;

    private final Pattern FLOAT_PATTERN = Pattern.compile("[0-9]{0,10}(\\.[0-9]{0,2})?");

    public FloatValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.FloatEntry entry) {
        super(entryLabel, entryTooltip);
        this.entry = entry;

        this.editBox = new EditBox(getFont(), 0, 0, getValueWidgetWidth(), 18, Component.literal(""));
        this.editBox.setFilter(value -> FLOAT_PATTERN.matcher(value).matches());
        this.editBox.setValue(entry.getConfigValue().toString());
        this.editBox.setResponder(string -> setUndoState(!Objects.equals(string, entry.getConfigValue().toString())));

        this.children.add(editBox);
    }

    @Override
    public Float getChangedValue() {
        return Float.valueOf(editBox.getValue());
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
