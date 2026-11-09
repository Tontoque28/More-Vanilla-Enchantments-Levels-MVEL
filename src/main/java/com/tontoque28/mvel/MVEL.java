package com.tontoque28.mvel;

import net.minecraftforge.fml.common.Mod;

@Mod(MVEL.MODID)
public class MVEL {
    public static final String MODID = "mvel";

    public static int getMaxLevelFor(String translationKey, int defaultValue) {
        String path = translationKey.substring(translationKey.lastIndexOf('.') + 1);
        switch (path) {
            case "unbreaking": return 12;
            case "mending": return 1;
            case "looting": case "fortune": case "luck_of_the_sea": case "lure": return 7;
            case "sharpness": case "smite": case "bane_of_arthropods": case "power": case "impaling": return 10;
            case "sweeping_edge": return 6;
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
}
