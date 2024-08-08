package net.xstopho.screen_test.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.xstopho.screen_test.ScreenTest;

import java.util.ArrayList;
import java.util.List;

public class CategoryEntry extends ContainerObjectSelectionList.Entry<CategoryEntry> {

    public Component label;
    public Component category;

    private final List<AbstractWidget> children = new ArrayList<>();
    private final Font font = Minecraft.getInstance().font;

    public EditBox editBox;
    public Button resetButton, undoButton;

    private final int EDIT_BOX_WIDTH = 150;

    public CategoryEntry(Component category) {
        this.category = category;
    }

    public CategoryEntry(Component tabTitle, String entryTitle) {
        this.editBox = new EditBox(font, 0, 0, EDIT_BOX_WIDTH, 18, Component.literal(entryTitle));
        this.editBox.setValue("TEST");

        this.resetButton = Button.builder(Component.literal("R"), button -> ScreenTest.LOGGER.error("Reset Value!")).bounds(0, 0, 20, 20).build();
        this.undoButton = Button.builder(Component.literal("U"), button -> ScreenTest.LOGGER.error("Undo Value changes!")).bounds(0, 0, 20, 20).build();

        this.label = Component.literal(tabTitle.getString() + " " + entryTitle);

        this.children.add(editBox);
        this.children.add(resetButton);
        this.children.add(undoButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTick) {
        if (category != null) {
            drawStringWithTooltip(guiGraphics, category, Component.literal("This is Category Tooltip. Possibly for more Information."),
                    xPos + (entryWidth / 2) - (font.width(category.getString()) / 2), yPos + 6, mouseX, mouseY, hovered);
        }

        if (editBox != null && resetButton != null && undoButton != null) {
            drawStringWithTooltip(guiGraphics, label, Component.literal("This is a Config Entry Tooltip to explain the Setting."), xPos, yPos + 6, mouseX, mouseY, hovered);

            editBox.setX(xPos + entryWidth - EDIT_BOX_WIDTH);
            editBox.setY(yPos + 1);

            undoButton.setX(xPos + entryWidth - undoButton.getWidth() - resetButton.getWidth());
            undoButton.setY(yPos);

            resetButton.setX(xPos + entryWidth - undoButton.getWidth());
            resetButton.setY(yPos);

            editBox.setWidth(EDIT_BOX_WIDTH - (undoButton.getWidth() + resetButton.getWidth()) - 1);

            editBox.render(guiGraphics, mouseX, mouseY, partialTick);
            resetButton.render(guiGraphics, mouseX, mouseY, partialTick);
            undoButton.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    private void drawStringWithTooltip(GuiGraphics guiGraphics, Component component, Component tooltip, int xPos, int yPos, int mouseX, int mouseY, boolean hovered) {
        if (component != null) {
            guiGraphics.drawString(font, component, xPos, yPos, -1, false);
            if (hovered) {
                int xMax = xPos + font.width(component.getString());
                int yMax = yPos + font.lineHeight;
                if (xPos <= mouseX && xMax >= mouseX && yPos <= mouseY && yMax >= mouseY) {
                    guiGraphics.renderTooltip(font, splitTooltip(tooltip), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        if (this.getFocused() instanceof EditBox) {
            return this.getFocused().keyPressed(keyCode, scanCode, modifier);
        }
        return super.keyPressed(keyCode, scanCode, modifier);
    }

    @Override
    public boolean charTyped(char c, int i) {
        if (this.getFocused() instanceof EditBox) {
            return this.getFocused().charTyped(c, i);
        }
        return false;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }


    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    private List<FormattedCharSequence> splitTooltip(Component component) {
        return font.split(component, 170);
    }
}
