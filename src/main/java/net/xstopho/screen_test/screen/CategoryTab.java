package net.xstopho.screen_test.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.entries.*;
import net.xstopho.screen_test.screen.entries.base.BaseEntry;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CategoryTab implements Tab {

    protected final CategoryEntryList list;

    private final Component component;
    private final Screen screen;

    public CategoryTab(Screen screen, Component component) {
        this.component = component;
        this.screen = screen;

        this.list = new CategoryEntryList(Minecraft.getInstance(), this.screen.width,
                this.screen.height, 0, 24, createEntries());
    }

    protected List<BaseEntry> createEntries() {
        List<BaseEntry> entries = new LinkedList<>();

        for (int i = 1; i <= 5; i++) {
            entries.add(new CategoryEntry(Component.literal("Test Category " + i).withStyle(ChatFormatting.GOLD), Component.literal("Category Tooltip for more Information!")));
            entries.add(new IntegerValueEntry(Component.literal("Integer Config Entry"), Component.literal("Explains the usage of the Config Value."), new TestConfigEntry.IntegerEntry(100, 50)));
            entries.add(new DoubleValueEntry(Component.literal("Double Config Entry"), Component.literal("Explains the usage of the Config Value."), new TestConfigEntry.DoubleEntry(2.5, 0.5)));
            entries.add(new BooleanValueEntry(Component.literal("Boolean Config Entry"), Component.literal("Explains the usage of the Config Value."), new TestConfigEntry.BooleanEntry(true, false)));
            entries.add(new StringValueEntry(Component.literal("String Config Entry"), Component.literal("Explains the usage of the Config Value."),
                    new TestConfigEntry.StringEntry("Hello User!", "This is you custom message.")));
        }
        return entries;
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
