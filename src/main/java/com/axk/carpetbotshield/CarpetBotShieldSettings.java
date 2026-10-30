package com.axk.carpetbotshield;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public class CarpetBotShieldSettings {
	public static final String BOT = "bot";

	@Rule(
		categories = { RuleCategory.SURVIVAL, RuleCategory.FEATURE, BOT },
		options = { "true", "false" }
	)
	public static boolean botShieldAxeBreakFast = true;

	@Rule(
		categories = { RuleCategory.SURVIVAL, RuleCategory.FEATURE, BOT },
		options = { "1", "2", "3", "5" },
		strict = false
	)
	public static int botShieldAxeBreakTime = 1;
}
