package net.xstopho.screen_test.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.helper.ConfigEntryCreator;
import net.xstopho.screen_test.screen.entries.base.BaseEntry;
import net.xstopho.screen_test.screen.entries.base.ValueEntry;

import java.util.ArrayList;
import java.util.List;

public class ModConfigScreen extends Screen {

    private final Screen previous;
    private final HeaderAndFooterLayout layout;

    private final TabManager tabManager;
    private TabNavigationBar tabNavigationBar;

    private final Component saveComponent = Component.translatable("screen-test.components.footer.save.label");
    private final Component closeComponent = Component.translatable("screen-test.components.footer.close.label");
    private final Component resetComponent = Component.translatable("screen-test.components.footer.reset.label");

    private static List<BaseEntry> commonEntries = new ArrayList<>();
    private static List<BaseEntry> clientEntries = new ArrayList<>();
    private static List<BaseEntry> serverEntries = new ArrayList<>();

    public ModConfigScreen(Screen previous, Component component) {
        super(component);
        this.previous = previous;
        this.layout = new HeaderAndFooterLayout(this, 32, 32);

        tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
    }

    @Override
    protected void init() {
        if (commonEntries.isEmpty()) commonEntries = ConfigEntryCreator.createCommonEntries();
        if (clientEntries.isEmpty()) clientEntries = ConfigEntryCreator.createClientEntries();
        if (serverEntries.isEmpty()) serverEntries = ConfigEntryCreator.createServerEntries();

        TabNavigationBar.Builder builder = TabNavigationBar.builder(this.tabManager, this.width);

        builder.addTabs(new CategoryTab(this, Component.literal("Common"), commonEntries),
                new CategoryTab(this, Component.literal("Client"), clientEntries),
                new CategoryTab(this, Component.literal("Server"), serverEntries));

        this.tabNavigationBar = builder.build();
        this.addRenderableWidget(this.tabNavigationBar);

        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(saveComponent, this::saveAllEntries).width(100).build());
        footer.addChild(Button.builder(resetComponent, this::resetAllEntries).width(100).build());
        footer.addChild(Button.builder(closeComponent, button -> this.onClose()).width(100).build());

        this.layout.visitWidgets(this::addRenderableWidget);

        this.tabNavigationBar.selectTab(0, false);
        this.repositionElements();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, ticks);
        super.render(guiGraphics, mouseX, mouseY, ticks);
        RenderSystem.enableBlend();
        guiGraphics.blit(Screen.FOOTER_SEPARATOR, 0, this.height - getHeaderHeight() - 10, 0.0F, 0.0F, this.width, 2, 32, 2);
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
            ScreenRectangle screenRectangle = new ScreenRectangle(0, i, this.width, this.height - (i * 2) - 10);
            this.tabManager.setTabArea(screenRectangle);
            this.layout.setHeaderHeight(i);
            this.layout.arrangeElements();
        }
    }

    public int getHeaderHeight() {
        return this.layout.getHeaderHeight();
    }

    @Override
    public void onClose() {
        undoAllEntries();
        Minecraft.getInstance().setScreen(previous);
    }

    private void saveAllEntries(Button button) {
        commonEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                if (valueEntry.wasChanged()) valueEntry.saveChangedValue();
            }
        });
        clientEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                if (valueEntry.wasChanged()) valueEntry.saveChangedValue();
            }
        });
        serverEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                if (valueEntry.wasChanged()) valueEntry.saveChangedValue();
            }
        });
    }

    private void resetAllEntries(Button button) {
        commonEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                valueEntry.resetToDefault();
            }
        });
        clientEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                valueEntry.resetToDefault();
            }
        });
        serverEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                valueEntry.resetToDefault();
            }
        });
    }

    private void undoAllEntries() {
        commonEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                valueEntry.undoOnClose();
            }
        });
        clientEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                valueEntry.undoOnClose();
            }
        });
        serverEntries.forEach(baseEntry -> {
            if (baseEntry instanceof ValueEntry<?> valueEntry) {
                valueEntry.undoOnClose();
            }
        });
    }
}
