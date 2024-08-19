package net.xstopho.config_screen.screen.entries.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.config_screen.ConfigScreenConstants;
import net.xstopho.config_screen.config.TestConfigEntry;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public abstract class ValueEntry<T> extends BaseEntry {

    protected final Component entryLabel, entryTooltip;
    protected final TestConfigEntry<T> entry;

    private final Component undoComponent = ConfigScreenConstants.UNDO;
    private final Component undoTooltip = ConfigScreenConstants.UNDO_TOOLTIP;
    private final ResourceLocation undoSprite = ResourceLocation.fromNamespaceAndPath(ConfigScreenConstants.MOD_ID, "textures/gui/sprites/undo.png");

    private final Component resetComponent = ConfigScreenConstants.RESET;
    private final Component resetTooltip = ConfigScreenConstants.RESET_TOOLTIP;

    protected final Button undoButton, resetButton;
    private final int valueWidgetWidth = 150;

    public ValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry<T> entry) {
        this.entryLabel = entryLabel;
        this.entryTooltip = entryTooltip;
        this.entry = entry;

        undoButton = Button.builder(undoComponent, b -> this.undoChanges()).tooltip(Tooltip.create(undoTooltip)).bounds(0, 0, 20, 20).build();
        resetButton = Button.builder(resetComponent, b -> this.resetValues()).tooltip(Tooltip.create(resetTooltip)).bounds(0, 0, 50, 20).build();

        undoButton.active = false;

        this.children.add(undoButton);
        this.children.add(resetButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        drawStringWithTooltip(guiGraphics, entryLabel, entryTooltip, xPos, yPos + 6, mouseX, mouseY, hovered);

        undoButton.setX(xPos + entryWidth - undoButton.getWidth() - resetButton.getWidth());
        undoButton.setY(yPos);

        resetButton.setX(xPos + entryWidth - resetButton.getWidth());
        resetButton.setY(yPos);

        undoButton.render(guiGraphics, mouseX, mouseY, partialTick);
        resetButton.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(undoSprite, undoButton.getX() + 2, undoButton.getY() + 2, 0.0F, 0.0F, 16, 16, 16, 16);
    }

    protected int getValueWidgetWidth() {
        return valueWidgetWidth;
    }

    protected int getCorrectedWidgetWidth() {
        return getValueWidgetWidth() - (undoButton.getWidth() + resetButton.getWidth()) - 1;
    }

    protected void setUndoState(boolean bool) {
        undoButton.active = bool;
    }

    @Override
    public void saveChangedValue() {
        entry.setConfigValue(getChangedValue());
        setUndoState(false);
    }

    public abstract T getChangedValue();
}
