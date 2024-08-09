package net.xstopho.screen_test.screen.entries;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BooleanValueEntry extends ValueEntry<Boolean> {

    private final TestConfigEntry.BooleanEntry entry;
    private final Button entryButton;

    private boolean buttonState;

    private final Component enabled = Component.translatable("screen-test.components.boolean.enabled").withStyle(ChatFormatting.GREEN);
    private final Component disabled = Component.translatable("screen-test.components.boolean.disabled").withStyle(ChatFormatting.RED);


    public BooleanValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry.BooleanEntry entry) {
        super(entryLabel, entryTooltip);
        this.entry = entry;
        this.buttonState = entry.getConfigValue();

        this.entryButton = Button.builder(buttonState ? enabled : disabled, this::changeButtonState).bounds(0, 0, getValueWidgetWidth(), 20).build();

        this.children.add(entryButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        drawStringWithTooltip(guiGraphics, entryLabel, entryTooltip, xPos, yPos + 6, mouseX, mouseY, hovered);

        entryButton.setX(xPos + entryWidth - getValueWidgetWidth());
        entryButton.setY(yPos);

        undoButton.setX(xPos + entryWidth - undoButton.getWidth() - resetButton.getWidth());
        undoButton.setY(yPos);

        resetButton.setX(xPos + entryWidth - resetButton.getWidth());
        resetButton.setY(yPos);

        entryButton.setWidth(getValueWidgetWidth() - (undoButton.getWidth() + resetButton.getWidth()));

        entryButton.render(guiGraphics, mouseX, mouseY, partialTick);
        undoButton.render(guiGraphics, mouseX, mouseY, partialTick);
        resetButton.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(undoSprite, undoButton.getX() + 3, undoButton.getY() + 3, 0.0F, 0.0F, 14, 14, 14, 14);
    }

    private void changeButtonState(Button button) {
        buttonState = !buttonState;

        if (buttonState) button.setMessage(enabled);
        else button.setMessage(disabled);

        setUndoState(!Objects.equals(buttonState, entry.getConfigValue()));
    }

    @Override
    public Boolean getChangedValue() {
        return buttonState;
    }

    @Override
    public void saveChangedValue() {
        entry.setConfigValue(getChangedValue());
        setUndoState(false);
    }

    @Override
    public void undoChanges() {
        entryButton.setMessage(entry.getConfigValue() ? enabled : disabled);
        buttonState = entry.getConfigValue();
        setUndoState(false);
    }

    @Override
    public void resetValues() {
        entryButton.setMessage(entry.getDefaultValue() ? enabled : disabled);
        buttonState = entry.getDefaultValue();
        setUndoState(!Objects.equals(buttonState, entry.getConfigValue()));
    }
}
