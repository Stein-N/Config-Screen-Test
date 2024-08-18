package net.xstopho.config_screen.screen.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.screen.widgets.CategoryEntryList;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;

import java.util.List;
import java.util.function.Consumer;

public class CategoryTab implements Tab {

    protected final CategoryEntryList list;

    private final Component component;
    private final Screen screen;

    public CategoryTab(Screen screen, Component component, List<BaseEntry> entries) {
        this.component = component;
        this.screen = screen;

        this.list = new CategoryEntryList(Minecraft.getInstance(), this.screen.width,
                this.screen.height, 0, 24, entries);
    }

    @Override
    public Component getTabTitle() {
        return component;
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(this.list);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {
        this.list.setRectangle(screenRectangle.width(), screenRectangle.height(), screenRectangle.left(), screenRectangle.top());
    }

    public Screen getScreen() {
        return screen;
    }
}
