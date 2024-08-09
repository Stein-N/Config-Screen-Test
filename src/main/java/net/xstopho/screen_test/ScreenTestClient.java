package net.xstopho.screen_test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.screen.ModConfigScreen;

public class ScreenTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("initValues").executes(context -> {
                ModConfigScreen.createEntries();
                context.getSource().sendFeedback(Component.literal("Created config entries!"));
                return 1;
            }));
            dispatcher.register(ClientCommandManager.literal("clearValues").executes(context -> {
                ModConfigScreen.resetEntries();
                context.getSource().sendFeedback(Component.literal("Cleared config entries!"));
                return 1;
            }));
        });
    }
}
