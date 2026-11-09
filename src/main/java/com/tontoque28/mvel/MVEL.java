package com.tontoque28.mvel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class MVEL implements ModInitializer {
    public static final String MODID = "mvel";

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                int maxLevel = getMaxLevelFor(enchantment, 0);
                if (maxLevel == 0) maxLevel = enchantment.getMaxLevel();

                for (int level = 1; level <= maxLevel; level++) {
                    entries.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level)));
                }
            }
        });
    }

    public static int getMaxLevelFor(Enchantment enchantment, int defaultValue) {
        ResourceLocation id = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
        if (id == null) return defaultValue;
        String path = id.getPath();
        switch (path) {
            case "unbreaking": return 12;
            case "mending": return 1;

            case "looting": case "fortune": case "luck_of_the_sea": case "lure": return 7;

            case "sharpness": case "smite": case "bane_of_arthropods": case "power": case "impaling": return 10;
            case "sweeping": case "sweeping_edge": return 6;
            case "knockback": case "punch": return 5;
            case "flame": case "fire_aspect": return 4;
            case "infinity": return 1;
            case "piercing": return 6;
            case "multishot": return 1;

            case "protection": case "fire_protection": case "blast_protection": case "projectile_protection": return 10;
            case "feather_falling": return 7;
            case "thorns": return 6;
            case "respiration": return 6;
            case "aqua_affinity": return 1;

            case "efficiency": return 10;
            case "silk_touch": return 1;
            case "depth_strider": return 8;
            case "frost_walker": return 5;
            case "soul_speed": case "swift_sneak": return 6;

            case "quick_charge": return 5;
            case "channeling": return 1;
            case "loyalty": case "riptide": return 6;

            default: return defaultValue;
        }
    }

    private static int getOriginalMaxLevel(String path) {
        switch (path) {
            case "unbreaking": return 3;
            case "mending": return 1;
            case "looting": case "fortune": case "luck_of_the_sea": case "lure": return 3;
            case "sharpness": case "smite": case "bane_of_arthropods": case "power": case "impaling": return 5;
            case "sweeping": case "sweeping_edge": return 3;
            case "knockback": case "punch": return 2;
            case "flame": case "fire_aspect": return 2;
            case "infinity": return 1;
            case "piercing": return 4;
            case "multishot": return 1;
            case "protection": case "fire_protection": case "blast_protection": case "projectile_protection": return 4;
            case "feather_falling": return 4;
            case "thorns": return 3;
            case "respiration": return 3;
            case "aqua_affinity": return 1;
            case "efficiency": return 5;
            case "silk_touch": return 1;
            case "depth_strider": return 3;
            case "frost_walker": return 2;
            case "soul_speed": case "swift_sneak": return 3;
            case "quick_charge": return 3;
            case "channeling": return 1;
            case "loyalty": case "riptide": return 3;
            default: return -1;
        }
    }

    public static int getMinCostFor(Enchantment enchantment, int level, int defaultValue) {
        ResourceLocation id = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
        if (id == null) return defaultValue;
        int originalMax = getOriginalMaxLevel(id.getPath());
        if (originalMax <= 0 || level <= originalMax) return defaultValue;
        int extra = level - originalMax;
        return 6 + extra * extra * 12;
    }

    public static int getMaxCostFor(Enchantment enchantment, int level, int defaultValue) {
        ResourceLocation id = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
        if (id == null) return defaultValue;
        int originalMax = getOriginalMaxLevel(id.getPath());
        if (originalMax <= 0 || level <= originalMax) return defaultValue;
        int extra = level - originalMax;
        int min = getMinCostFor(enchantment, level, defaultValue);
        return min + Math.max(15 - extra * 3, 5);
    }
}
