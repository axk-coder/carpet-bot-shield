package com.axk.carpetbotshield;

import carpet.CarpetExtension;
import carpet.api.settings.SettingsManager;
import carpet.utils.Translations;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class CarpetBotShieldExtension implements CarpetExtension {
	public static final CarpetBotShieldExtension INSTANCE = new CarpetBotShieldExtension();
	private final SettingsManager settingsManager;

	private CarpetBotShieldExtension() {
		this.settingsManager = new SettingsManager("1.0.0", "carpet-bot-shield", "Carpet Bot Shield");
	}

	@Override
	public void onGameStarted() {
		registerTranslations();
		Translations.updateLanguage();
		this.settingsManager.parseSettingsClass(CarpetBotShieldSettings.class);
	}

	@Override
	public SettingsManager extensionSettingsManager() {
		return this.settingsManager;
	}

	@Override
	public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
		this.settingsManager.registerCommand(dispatcher, context);
	}

	@Override
	public String version() {
		return "1.0.0";
	}

	public static void registerTranslations() {
		Translations.registerFallbackTranslation("carpet-bot-shield.rule.botShieldAxeBreakFast.name", "botShieldAxeBreakFast");
		Translations.registerFallbackTranslation("carpet-bot-shield.rule.botShieldAxeBreakFast.desc", "Reduces axe shield break disable time for carpet bots");
		Translations.registerFallbackTranslation("carpet-bot-shield.rule.botShieldAxeBreakTime.name", "botShieldAxeBreakTime");
		Translations.registerFallbackTranslation("carpet-bot-shield.rule.botShieldAxeBreakTime.desc", "Axe shield break duration in seconds for carpet bots");
	}

	@Override
	public Map<String, String> canHasTranslations(String lang) {
		Map<String, String> map = new HashMap<>();
		map.put("carpet-bot-shield.rule.botShieldAxeBreakFast.name", "botShieldAxeBreakFast");
		map.put("carpet-bot-shield.rule.botShieldAxeBreakFast.desc", "Reduces axe shield break disable time for carpet bots");
		map.put("carpet-bot-shield.rule.botShieldAxeBreakFast.extra.0", "When enabled carpet bots recover in 1 second instead of 5 seconds");
		map.put("carpet-bot-shield.rule.botShieldAxeBreakTime.name", "botShieldAxeBreakTime");
		map.put("carpet-bot-shield.rule.botShieldAxeBreakTime.desc", "Axe shield break duration in seconds for carpet bots");
		map.put("carpet-bot-shield.rule.botShieldAxeBreakTime.extra.0", "Defaults to 1 second");
		return map;
	}

	public static boolean isBotShieldBreakFast() {
		return CarpetBotShieldSettings.botShieldAxeBreakFast;
	}

	public static float getBotShieldBreakSeconds() {
		return Math.max(0.0f, Math.min(300.0f, (float) CarpetBotShieldSettings.botShieldAxeBreakTime));
	}

	public static boolean isCarpetBot(Player player) {
		if (player == null) {
			return false;
		}
		if (player instanceof carpet.patches.EntityPlayerMPFake) {
			return true;
		}
		String className = player.getClass().getName();
		if (className.contains("EntityPlayerMPFake") || className.contains("FakePlayer") || className.contains("McpFakePlayer")) {
			return true;
		}
		if (player instanceof ServerPlayer sp && sp.connection != null) {
			String connName = sp.connection.getClass().getName();
			if (connName.contains("Fake")) {
				return true;
			}
		}
		return false;
	}
}
