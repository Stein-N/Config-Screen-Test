package net.xstopho.config_screen.screen.entries.reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.xstopho.config_screen.ConfigScreenConstants;
import net.xstopho.config_screen.config.TestConfigEntry;
import net.xstopho.config_screen.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemListValueEntry extends ValueEntry<List<Item>> {

    private final Button button;
    private final Screen configScreen = Minecraft.getInstance().screen;

    public ItemListValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry<List<Item>> entry) {
        super(entryLabel, entryTooltip, entry);

        this.button = Button.builder(ConfigScreenConstants.EDIT, b -> doNothing()).build();
    }

    private void doNothing() {}

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);

        button.setX(xPos + entryWidth - getValueWidgetWidth());
        button.setY(yPos + 1);
        button.setWidth(getCorrectedWidgetWidth());

        button.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public List<Item> getChangedValue() {
        return entry.getConfigValue();
    }

    @Override
    public void undoChanges() {}

    @Override
    public void resetValues() {}
}
