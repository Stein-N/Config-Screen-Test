package net.xstopho.screen_test.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.ScreenTest;

import java.util.List;

public class CategoryEntry extends ContainerObjectSelectionList.Entry<CategoryEntry> {

    private final String entryTitle;
    private final Component tabTitle;

    public CategoryEntry(Component tabTitle, String entryTitle) {
        this.entryTitle = entryTitle;
        this.tabTitle = tabTitle;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTick) {
        Component label = Component.literal(tabTitle.getString() + " " + entryTitle);

        guiGraphics.drawString(Minecraft.getInstance().font, label, xPos, yPos + 5, -1, false);

        EditBox editBox = new EditBox(Minecraft.getInstance().font, 0, 0, 150, 18, Component.literal(entryTitle));
        Button button = Button.builder(Component.literal(entryTitle), onPress -> ScreenTest.LOGGER.error("Button from Entry '{}' was pressed", entryTitle)).bounds(0, 0, 50, 20).build();

        editBox.setX(xPos + entryWidth - 158);
        editBox.setY(yPos + 1);

        button.setX(xPos + entryWidth - button.getWidth() - 2);
        button.setY(yPos);

        editBox.setWidth(158 - button.getWidth() - 2);

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);
        button.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
