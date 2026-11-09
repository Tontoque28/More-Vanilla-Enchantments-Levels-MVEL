package com.tontoque28.mvel.mixin;

import com.tontoque28.mvel.MVEL;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {
        ArrowDamageEnchantment.class,
        ArrowKnockbackEnchantment.class,
        KnockbackEnchantment.class,
        FireAspectEnchantment.class,
        LootBonusEnchantment.class,
        SweepingEdgeEnchantment.class,
        ThornsEnchantment.class,
        TridentImpalerEnchantment.class,
        TridentRiptideEnchantment.class,
        TridentLoyaltyEnchantment.class,
        QuickChargeEnchantment.class,
        ArrowPiercingEnchantment.class,
        FrostWalkerEnchantment.class,
        SoulSpeedEnchantment.class,
        SwiftSneakEnchantment.class,
        DamageEnchantment.class,
        ProtectionEnchantment.class,
        DiggingEnchantment.class,
        DigDurabilityEnchantment.class,
        FishingSpeedEnchantment.class,
        OxygenEnchantment.class,
        WaterWalkerEnchantment.class
})
public abstract class VanillaEnchantmentSubclassesMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true, require = 0)
    private void onGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        int customMax = MVEL.getMaxLevelFor((Enchantment) (Object) this, cir.getReturnValue());
        if (customMax != cir.getReturnValue()) {
            cir.setReturnValue(customMax);
        }
    }

    @Inject(method = "getMinCost", at = @At("RETURN"), cancellable = true, require = 0)
    private void onGetMinCost(int level, CallbackInfoReturnable<Integer> cir) {
        int custom = MVEL.getMinCostFor((Enchantment) (Object) this, level, cir.getReturnValue());
        if (custom != cir.getReturnValue()) {
            cir.setReturnValue(custom);
        }
    }

    @Inject(method = "getMaxCost", at = @At("RETURN"), cancellable = true, require = 0)
    private void onGetMaxCost(int level, CallbackInfoReturnable<Integer> cir) {
        int custom = MVEL.getMaxCostFor((Enchantment) (Object) this, level, cir.getReturnValue());
        if (custom != cir.getReturnValue()) {
            cir.setReturnValue(custom);
        }
    }
}
