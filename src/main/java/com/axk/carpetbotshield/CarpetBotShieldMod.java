package com.axk.carpetbotshield;

import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class CarpetBotShieldMod implements ModInitializer {
	@Override
	public void onInitialize() {
		CarpetServer.manageExtension(CarpetBotShieldExtension.INSTANCE);
	}
}
