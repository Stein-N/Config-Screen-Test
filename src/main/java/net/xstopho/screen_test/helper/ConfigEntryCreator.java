package net.xstopho.screen_test.helper;

import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.config.TestConfigEntry;
import net.xstopho.screen_test.screen.entries.BooleanValueEntry;
import net.xstopho.screen_test.screen.entries.CategoryEntry;
import net.xstopho.screen_test.screen.entries.IntegerValueEntry;
import net.xstopho.screen_test.screen.entries.StringValueEntry;
import net.xstopho.screen_test.screen.entries.base.BaseEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigEntryCreator {

    public static List<BaseEntry> createCommonEntries() {
        return List.of(
            new CategoryEntry(Component.literal("Common Category"), Component.literal("Will inherit all Settings that are used on Client and Server.")),
                new IntegerValueEntry(Component.literal("Diamond drop multiplier"), Component.literal("Change the amount of dropped Diamonds."), new TestConfigEntry.IntegerEntry(1, 5)),
                new IntegerValueEntry(Component.literal("Backpack rows"), Component.literal("Change the amount of rows the Backpack has"), new TestConfigEntry.IntegerEntry(3, 5)),
                new IntegerValueEntry(Component.literal("Backpack columns"), Component.literal("Change the amount of columns the Backpack has"), new TestConfigEntry.IntegerEntry(9, 13))
        );
    }

    public static List<BaseEntry> createClientEntries() {
        return List.of(
            new CategoryEntry(Component.literal("Client Settings"), Component.literal("Settings that are only necessary on the Client Side.")),
                new BooleanValueEntry(Component.literal("Render bigger In-Hand Models"), Component.literal("Choose if the bigger Item Model of <ITEM_NAME> should be rendered"), new TestConfigEntry.BooleanEntry(true, true))
        );
    }

    public static List<BaseEntry> createServerEntries() {
        return List.of(
            new CategoryEntry(Component.literal("Server Settings"), Component.literal("Settings that are only necessary in the Server Side.")),
                new BooleanValueEntry(Component.literal("Kick AFK player"), Component.literal("Should AFK Player be kicked."), new TestConfigEntry.BooleanEntry(false, false)),
                new IntegerValueEntry(Component.literal("Time to get kicked"), Component.literal("Time in Minutes where AFK Player gets kicked."), new TestConfigEntry.IntegerEntry(3, 10)),
                new StringValueEntry(Component.literal("AFK kick Message"), Component.literal("Set what message should be displayed when the AFK Player gets kicked"), new TestConfigEntry.StringEntry("You where AFK.", "You where AFK for to long!"))
        );
    }

    public static List<BaseEntry> createDummies() {
        List<BaseEntry> list = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            int defaultValue = new Random().nextInt(0, 100);
            int configValue = defaultValue + new Random().nextInt(0, 100);
            list.add(new IntegerValueEntry(Component.literal("Test " + i), Component.literal("Test Tooltip " + i), new TestConfigEntry.IntegerEntry(defaultValue, configValue)));
        }

        return list;
    }
}
