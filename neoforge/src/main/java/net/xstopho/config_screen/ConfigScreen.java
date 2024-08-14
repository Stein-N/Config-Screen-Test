package net.xstopho.config_screen;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.xstopho.config_screen.screen.ModConfigScreen;

@Mod(ConfigScreenConstants.MOD_ID)
public class ConfigScreen {

    public ConfigScreen(IEventBus bus) {
        bus.addListener(this::doStuff);
    }

    private void doStuff(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, screen) -> new ModConfigScreen(screen)
        );
    }
}
