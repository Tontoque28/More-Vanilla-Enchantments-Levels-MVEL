package com.tontoque28.mvel.mixin;

import com.tontoque28.mvel.MVEL;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = {"getMaxLevel", "func_77325_b"}, at = @At("RETURN"), cancellable = true, remap = false, require = 0)
    private void onGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        int customMax = MVEL.getMaxLevelFor((Enchantment) (Object) this, cir.getReturnValue());
        if (customMax != cir.getReturnValue()) {
            cir.setReturnValue(customMax);
        }
    }
}
