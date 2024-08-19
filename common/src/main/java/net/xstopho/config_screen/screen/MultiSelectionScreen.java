package net.xstopho.config_screen.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.ConfigScreenConstants;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;
import net.xstopho.config_screen.screen.widgets.CategoryEntryList;

import java.util.List;

public class MultiSelectionScreen extends Screen {

    private final Font font = Minecraft.getInstance().font;
    private final CategoryEntryList list;
    private final HeaderAndFooterLayout layout;
    private final Screen previous;

    private final int headerHeight = ConfigScreenConstants.HEADER_HEIGHT;
    private final int footerHeight = ConfigScreenConstants.FOOTER_HEIGHT;
    private final Component saveComponent = ConfigScreenConstants.SAVE_AND_CLOSE;
    private final Component closeComponent = ConfigScreenConstants.CLOSE;

    public MultiSelectionScreen(Screen previous, Component title, List<BaseEntry> entries) {
        super(title.copy().withStyle(ChatFormatting.GOLD));
        this.previous = previous;

        this.layout = new HeaderAndFooterLayout(this, headerHeight, footerHeight);
        this.list = new CategoryEntryList(Minecraft.getInstance(), this.width,
                this.height - (headerHeight + footerHeight), 0, 24, entries);
    }

    @Override
    protected void init() {
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal());
        footer.addChild(Button.builder(saveComponent, b -> ConfigScreenConstants.LOGGER.error("Save and Close from Multi selection Screen")).width(100).build());
        footer.addChild(Button.builder(closeComponent, b -> this.onClose()).width(100).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.list.visitWidgets(this::addRenderableWidget);

        this.layout.arrangeElements();
        this.list.setRectangle(this.width, this.height - (headerHeight + footerHeight), 0, headerHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        guiGraphics.drawString(font, getTitle(), (this.width / 2) - (font.width(getTitle().getString()) / 2), (headerHeight / 2) - (font.lineHeight / 2), -1);
        guiGraphics.blit(Screen.FOOTER_SEPARATOR, 0, this.height - footerHeight, 0.0F, 0.0F, this.width, 2, 32, 2);
        guiGraphics.blit(Screen.HEADER_SEPARATOR, 0, headerHeight, 0.0F, 0.0F, this.width, 2, 32, 2);
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
