package net.xstopho.screen_test.screen.entries.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.screen_test.ScreenTest;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("all")
public abstract class ValueEntry<T> extends BaseEntry {

    protected final Component entryLabel, entryTooltip;

    private final Component undoComponent = Component.literal("");
    private final Component undoTooltip = Component.translatable("screen-test.components.undo.tooltip");
    protected final ResourceLocation undoSprite = ResourceLocation.fromNamespaceAndPath(ScreenTest.MOD_ID, "textures/gui/sprites/undo.png");

    private final Component resetComponent = Component.translatable("screen-test.components.reset.label");
    private final Component resetTooltip = Component.translatable("screen-test.components.reset.tooltip");

    protected final Button undoButton, resetButton;
    private final int valueWidgetWidth = 150;

    public ValueEntry(Component entryLabel, @Nullable Component entryTooltip) {
        this.entryLabel = entryLabel;
        this.entryTooltip = entryTooltip;

        undoButton = Button.builder(undoComponent, b -> this.undoChanges()).tooltip(Tooltip.create(undoTooltip)).bounds(0, 0, 20, 20).build();
        resetButton = Button.builder(resetComponent, b -> this.resetValues()).tooltip(Tooltip.create(resetTooltip)).bounds(0, 0, 50, 20).build();

        undoButton.active = false;

        this.children.add(undoButton);
        this.children.add(resetButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {

        undoButton.setX(xPos + entryWidth - undoButton.getWidth() - resetButton.getWidth());
        undoButton.setY(yPos);

        resetButton.setX(xPos + entryWidth - resetButton.getWidth());
        resetButton.setY(yPos);

        undoButton.render(guiGraphics, mouseX, mouseY, partialTick);
        resetButton.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(undoSprite, undoButton.getX() + 3, undoButton.getY() + 3, 0.0F, 0.0F, 14, 14, 14, 14);
    }

    public int getValueWidgetWidth() {
        return valueWidgetWidth;
    }

    protected void setUndoState(boolean bool) {
        undoButton.active = bool;
    }

    public abstract T getChangedValue();
}
