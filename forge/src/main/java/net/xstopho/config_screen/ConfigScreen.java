package net.xstopho.config_screen;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.xstopho.config_screen.screen.ModConfigScreen;

@Mod(ConfigScreenConstants.MOD_ID)
public class ConfigScreen {

    public ConfigScreen() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doStuff);
    }

    private void doStuff(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ModConfigScreen(screen)));
    }
}
