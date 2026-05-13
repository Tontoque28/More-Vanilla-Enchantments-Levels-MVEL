package com.tontoque28.mvel;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(MVEL.MODID)
public class MVEL {
    public static final String MODID = "mvel";

    public MVEL() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
                if (!enchantment.isAllowedOnBooks()) continue;
                int maxLevel = getMaxLevelFor(enchantment, 0);
                if (maxLevel == 0) maxLevel = enchantment.getMaxLevel();
                
                for (int level = 1; level <= maxLevel; level++) {
                    event.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level)));
                }
            }
        }
    }

    public static int getMaxLevelFor(Enchantment enchantment, int defaultValue) {
        ResourceLocation id = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
        if (id == null) return defaultValue;
        String path = id.getPath();
        switch (path) {
            // Utility & Durability
            case "unbreaking": return 12;
            case "mending": return 1;
            
            // Looting & Economy
            case "looting": case "fortune": case "luck_of_the_sea": case "lure": return 7;
            
            // Offensive (Melee & Ranged) - Scaled for PTD
            case "sharpness": case "smite": case "bane_of_arthropods": case "power": case "impaling": return 10;
            case "sweeping_edge": return 6;
            case "knockback": case "punch": return 5;
            case "flame": case "fire_aspect": return 4;
            case "infinity": return 1;
            case "piercing": return 6;
            case "multishot": return 1;
            
            // Defensive
            case "protection": case "fire_protection": case "blast_protection": case "projectile_protection": return 10;
            case "feather_falling": return 7;
            case "thorns": return 6;
            case "respiration": return 6;
            case "aqua_affinity": return 1;
            
            // Tools & Movement
            case "efficiency": return 10;
            case "silk_touch": return 1;
            case "depth_strider": return 8; // BUFFED: Agilidad Acuática
            case "frost_walker": return 5;
            case "soul_speed": case "swift_sneak": return 6;
            
            // Special
            case "quick_charge": return 5;
            case "channeling": return 1;
            case "loyalty": case "riptide": return 6;

            default: return defaultValue;
        }
    }
}
