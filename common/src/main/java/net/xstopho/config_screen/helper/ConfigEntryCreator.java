package net.xstopho.config_screen.helper;

import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.config.TestConfigEntry;
import net.xstopho.config_screen.config.TestEnums;
import net.xstopho.config_screen.screen.entries.CategoryEntry;
import net.xstopho.config_screen.screen.entries.base.BaseEntry;
import net.xstopho.config_screen.screen.entries.primitiv.*;
import net.xstopho.config_screen.screen.entries.reference.EnumValueEntry;
import net.xstopho.config_screen.screen.entries.reference.StringValueEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigEntryCreator {

    public static List<BaseEntry> createAllEntries() {
        return List.of(
                new CategoryEntry(Component.literal("Primitive Datatypes"), Component.literal("All Primitive Datatypes")),
                new BooleanValueEntry(Component.literal("Boolean Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.BooleanEntry(true, false)),
                new ByteValueEntry(Component.literal("Byte Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.ByteEntry((byte) 25, (byte) 120)),
                new CharValueEntry(Component.literal("Char Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.CharEntry('a', 'm')),
                new DoubleValueEntry(Component.literal("Double Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.DoubleEntry(2.5, 10.42)),
                new FloatValueEntry(Component.literal("Float Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.FloatEntry(25.3f, 14.354f)),
                new IntegerValueEntry(Component.literal("Integer Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.IntegerEntry(11, 25)),
                new LongValueEntry(Component.literal("Long Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.LongEntry(12543845L, 1242816547L)),
                new ShortValueEntry(Component.literal("Short Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.ShortEntry((short) 25342, (short) 31258)),

                new CategoryEntry(Component.literal("Reference Datatypes"), Component.literal("All supported Reference Datatypes")),

                new StringValueEntry(Component.literal("String Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.StringEntry("Test String", "Edited String")),
                new EnumValueEntry<>(Component.literal("Enum Entry"), Component.literal("Dummy Tooltip"), new TestConfigEntry.EnumConfigEntry<>(TestEnums.Items.class, TestEnums.Items.STICK, TestEnums.Items.BERRY))
        );
    }

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
                new BooleanValueEntry(Component.literal("Render bigger In-Hand Models"), Component.literal("Choose if the bigger Item Model of <ITEM_NAME> should be rendered"), new TestConfigEntry.BooleanEntry(true, true)),
                new EnumValueEntry<>(Component.literal("Start Item"), Component.literal("Select one start Item"), new TestConfigEntry.EnumConfigEntry<>(TestEnums.Items.class, TestEnums.Items.COAL, TestEnums.Items.BERRY))
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

    private static List<String> dummyStringList() {
        return List.of("Homefront", "World of Warcraft", "Minecraft", "Destiny 2", "Among Us", "Splitgate", "Monster Hunter: World", "XDefiant");
    }

    private static List<String> modifiedStringList() {
        return List.of("Homefront", "World of Warcraft", "Monster Hunter: World", "XDefiant");
    }
}
