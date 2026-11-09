package com.tontoque28.mvel.mixin;

import com.tontoque28.mvel.MVEL;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void onGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        Enchantment self = (Enchantment) (Object) this;
        if (self.description().getContents() instanceof TranslatableContents contents) {
            int customMax = MVEL.getMaxLevelFor(contents.getKey(), cir.getReturnValue());
            if (customMax != cir.getReturnValue()) {
                cir.setReturnValue(customMax);
            }
        }
    }
}
