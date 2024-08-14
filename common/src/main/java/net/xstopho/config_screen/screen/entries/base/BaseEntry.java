package net.xstopho.config_screen.screen.entries.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntry extends ContainerObjectSelectionList.Entry<BaseEntry> {

    protected final List<AbstractWidget> children = new ArrayList<>();
    protected final Font font = Minecraft.getInstance().font;

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    protected void drawStringWithTooltip(GuiGraphics guiGraphics, Component component, Component tooltip, int xPos, int yPos, int mouseX, int mouseY, boolean hovered) {
        if (component != null) {
            guiGraphics.drawString(font, component, xPos, yPos, -1, false);
            if (tooltip != null && hovered) {
                int xMax = xPos + font.width(component.getString());
                int yMax = yPos + font.lineHeight;
                if (xPos <= mouseX && xMax >= mouseX && yPos <= mouseY && yMax >= mouseY) {
                    guiGraphics.renderTooltip(font, splitTooltip(tooltip), mouseX, mouseY);
                }
            }
        }
    }

    private List<FormattedCharSequence> splitTooltip(Component component) {
        return font.split(component, 170);
    }

    public Font getFont() {
        return font;
    }

    public abstract void saveChangedValue();

    public abstract void undoChanges();

    public abstract void resetValues();
}
