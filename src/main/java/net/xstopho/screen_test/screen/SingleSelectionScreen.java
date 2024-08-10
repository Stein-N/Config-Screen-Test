package net.xstopho.screen_test.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.screen.components.CategoryEntryList;
import net.xstopho.screen_test.screen.entries.base.BaseEntry;

import java.util.List;

public class SingleSelectionScreen extends Screen {

    private final Font font = Minecraft.getInstance().font;
    private final CategoryEntryList list;
    private final HeaderAndFooterLayout layout;
    private final Screen previous;

    private final int HEADER_HEIGHT = 22;
    private final int FOOTER_HEIGHT = 34;
    private final Component closeComponent = Component.translatable("screen-test.components.footer.close.label");

    public SingleSelectionScreen(Screen previous, Component component, List<BaseEntry> entries) {
        super(component.copy().withStyle(ChatFormatting.GOLD));
        this.previous = previous;

        this.layout = new HeaderAndFooterLayout(this, HEADER_HEIGHT, FOOTER_HEIGHT);
        this.list = new CategoryEntryList(Minecraft.getInstance(), this.width,
                this.height - (HEADER_HEIGHT + FOOTER_HEIGHT), 0, 24, entries);
    }

    @Override
    protected void init() {
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal());
        footer.addChild(Button.builder(closeComponent, b -> this.onClose()).width(100).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.list.visitWidgets(this::addRenderableWidget);

        this.layout.arrangeElements();
        this.list.setRectangle(this.width, this.height - (HEADER_HEIGHT + FOOTER_HEIGHT), 0, HEADER_HEIGHT);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        guiGraphics.drawString(font, getTitle(), (this.width / 2) - (font.width(getTitle().getString()) / 2), (this.HEADER_HEIGHT / 2) - (font.lineHeight / 2), -1);
        guiGraphics.blit(Screen.FOOTER_SEPARATOR, 0, this.height - FOOTER_HEIGHT, 0.0F, 0.0F, this.width, 2, 32, 2);
        guiGraphics.blit(Screen.HEADER_SEPARATOR, 0, HEADER_HEIGHT, 0.0F, 0.0F, this.width, 2, 32, 2);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            Minecraft.getInstance().setScreen(previous);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(previous);
    }
}
