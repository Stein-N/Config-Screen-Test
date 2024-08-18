package net.xstopho.config_screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigScreenConstants {

	public static final String MOD_ID = "config_screen";
	public static final String MOD_NAME = "Config Screen Test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static final int HEADER_HEIGHT = 22;
	public static final int FOOTER_HEIGHT = 34;

	public static final Component SAVE_AND_CLOSE = Component.translatable("config_screen.components.footer.save.label");
	public static final Component CLOSE = Component.translatable("config_screen.components.footer.close.label");
	public static final Component RESET = Component.translatable("config_screen.components.footer.reset.label");
	public static final Component UNDO = Component.literal("");

	public static final Component RESET_TOOLTIP = Component.translatable("config_screen.components.reset.tooltip");
	public static final Component UNDO_TOOLTIP = Component.translatable("config_screen.components.undo.tooltip");

	public static final Component BOOLEAN_ENABLED = Component.translatable("config_screen.components.boolean.enabled").withStyle(ChatFormatting.GREEN);
	public static final Component BOOLEAN_DISABLED = Component.translatable("config_screen.components.boolean.disabled").withStyle(ChatFormatting.RED);
}