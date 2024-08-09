package net.xstopho.screen_test.screen.entries.base;

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

        undoButton = Button.builder(undoComponent, this::undoChange).tooltip(Tooltip.create(undoTooltip)).bounds(0, 0, 20, 20).build();
        resetButton = Button.builder(resetComponent, this::resetValue).tooltip(Tooltip.create(resetTooltip)).bounds(0, 0, 50, 20).build();

        undoButton.active = false;

        this.children.add(undoButton);
        this.children.add(resetButton);
    }

    public int getValueWidgetWidth() {
        return valueWidgetWidth;
    }

    protected void checkUndoState(boolean bool) {
        undoButton.active = bool;
    }

    public abstract T getChangedValue();

    protected abstract void undoChange(Button button);

    protected abstract void resetValue(Button button);
}
