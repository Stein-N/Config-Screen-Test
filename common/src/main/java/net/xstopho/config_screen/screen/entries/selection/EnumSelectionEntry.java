package net.xstopho.config_screen.screen.entries.selection;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class EnumSelectionEntry<T extends Enum<T>> extends SingleSelectionEntry {

    private final Enum<T> value;

    public EnumSelectionEntry(Screen previous, Component entryLabel, Component entryTooltip, EditBox parent, Enum<T> value) {
        super(previous, entryLabel, entryTooltip, parent);
        this.value = value;
    }

    @Override
    public String valueToString() {
        return value.toString();
    }
}
