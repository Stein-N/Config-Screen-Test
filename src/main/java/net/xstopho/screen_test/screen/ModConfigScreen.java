package net.xstopho.screen_test.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen extends Screen {

    private final Screen previous;
    private final HeaderAndFooterLayout layout;

    private final TabManager tabManager;
    private final String[] tabs = { "Common", "Client", "Server" };
    private TabNavigationBar tabNavigationBar;

    public ModConfigScreen(Screen previous, Component component) {
        super(component);
        this.previous = previous;
        this.layout = new HeaderAndFooterLayout(this, 32, 32);

        tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
    }

    @Override
    protected void init() {
        TabNavigationBar.Builder builder = TabNavigationBar.builder(this.tabManager, this.width);

        for (String tab : tabs) builder.addTabs(new CategoryTab(this, Component.literal(tab)));

        this.tabNavigationBar = builder.build();

        this.addRenderableWidget(this.tabNavigationBar);
        this.tabNavigationBar.selectTab(0, false);
        this.repositionElements();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, ticks);
        super.render(guiGraphics, mouseX, mouseY, ticks);
        RenderSystem.enableBlend();
        guiGraphics.blit(Screen.FOOTER_SEPARATOR, 0, this.height - getHeaderHeight(), 0.0F, 0.0F, this.width, 2, 32, 2);
        RenderSystem.disableBlend();
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
    protected void repositionElements() {
        if (this.tabNavigationBar != null) {
            this.tabNavigationBar.setWidth(this.width);
            this.tabNavigationBar.arrangeElements();
            int i = this.tabNavigationBar.getRectangle().bottom();
            ScreenRectangle screenRectangle = new ScreenRectangle(0, i, this.width, this.height - (i * 2));
            this.tabManager.setTabArea(screenRectangle);
            this.layout.setHeaderHeight(i);
            this.layout.arrangeElements();
        }
    }

    public int getHeaderHeight() {
        return this.layout.getHeaderHeight();
    }
}
