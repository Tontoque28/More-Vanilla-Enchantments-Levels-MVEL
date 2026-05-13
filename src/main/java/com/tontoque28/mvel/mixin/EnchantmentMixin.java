package com.tontoque28.mvel.mixin;

import com.tontoque28.mvel.MVEL;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = {"getMaxLevel", "m_6586_"}, at = @At("RETURN"), cancellable = true, remap = false, require = 0)
    private void onGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        // cir.getReturnValue() contiene el valor original de Minecraft.
        // Lo pasamos como default para evitar llamar a getMaxLevel() de nuevo.
        int customMax = MVEL.getMaxLevelFor((Enchantment) (Object) this, cir.getReturnValue());
        if (customMax != cir.getReturnValue()) {
            cir.setReturnValue(customMax);
        }
    }
}
