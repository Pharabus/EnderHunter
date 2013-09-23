package pharabus.mods.enderhunter;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public final class ConfigHelper {

    public static int EnchantmentEnderHunterId;
    public static final String EnchantmentEnderHunterkey = "EnchantmentEnderHunter"; 
    public static int ItemAthameId;
    public static int ShiftedItemAthameId;
    public static final String ItemAthameKey = "ItemAthame";
    public static final String EnderDamageKey = "Enderdamage";
    public static int Enderdamage;
    
    public static final String ItemAthameUnlocalisedname = "itemAthame";
    public static final String ItemAthamename = "Athame";
    
    public static final String EnchantmentEnderHunterUnlocalisedname = "enchantEnderHunter";
    public static final String EnchantmentEnderHuntername = "Ender Hunter";
    
    public static final String TEXTURE_LOCATION = "enderhunter";
    public static final String ATHAME_ICON = "athame";
    
    public static void init(FMLPreInitializationEvent event)
    {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        cfg.load();
        
        EnchantmentEnderHunterId = cfg.get("Enchants", EnchantmentEnderHunterkey, 79).getInt();
        ShiftedItemAthameId = cfg.get("Items", ItemAthameKey, 5023).getInt();
        ItemAthameId =  ShiftedItemAthameId - 256;
        
        Enderdamage = cfg.get("Damage", EnderDamageKey, 10).getInt();
    }
}
