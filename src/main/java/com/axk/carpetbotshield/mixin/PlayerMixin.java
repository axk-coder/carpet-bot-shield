package com.axk.carpetbotshield.mixin;

import com.axk.carpetbotshield.CarpetBotShieldExtension;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public abstract class PlayerMixin {
	@ModifyArg(
		method = "blockUsingItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/component/BlocksAttacks;disable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/item/ItemStack;)V"
		),
		index = 2
	)
	private float modifyBotShieldAxeBreakDuration(float originalSeconds) {
		if (CarpetBotShieldExtension.isBotShieldBreakFast() && CarpetBotShieldExtension.isCarpetBot((Player) (Object) this)) {
			return CarpetBotShieldExtension.getBotShieldBreakSeconds();
		}
		return originalSeconds;
	}
}
