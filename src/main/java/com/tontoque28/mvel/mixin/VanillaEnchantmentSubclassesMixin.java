package com.tontoque28.mvel.mixin;

import com.tontoque28.mvel.MVEL;
import net.minecraft.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {
    AquaAffinityEnchantment.class,
    ChannelingEnchantment.class,
    DamageEnchantment.class,
    DepthStriderEnchantment.class,
    EfficiencyEnchantment.class,
    FireAspectEnchantment.class,
    FlameEnchantment.class,
    FrostWalkerEnchantment.class,
    ImpalingEnchantment.class,
    InfinityEnchantment.class,
    KnockbackEnchantment.class,
    LootBonusEnchantment.class,
    LoyaltyEnchantment.class,
    LureEnchantment.class,
    MendingEnchantment.class,
    MultishotEnchantment.class,
    PiercingEnchantment.class,
    PowerEnchantment.class,
    ProtectionEnchantment.class,
    PunchEnchantment.class,
    QuickChargeEnchantment.class,
    RespirationEnchantment.class,
    RiptideEnchantment.class,
    SilkTouchEnchantment.class,
    SoulSpeedEnchantment.class,
    SweepingEnchantment.class,
    ThornsEnchantment.class,
    UnbreakingEnchantment.class
})
public abstract class VanillaEnchantmentSubclassesMixin {

    @Inject(method = {"getMaxLevel", "func_77325_b"}, at = @At("RETURN"), cancellable = true, remap = false, require = 0)
    private void onGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        int customMax = MVEL.getMaxLevelFor((Enchantment) (Object) this, cir.getReturnValue());
        if (customMax != cir.getReturnValue()) {
            cir.setReturnValue(customMax);
        }
    }
}
