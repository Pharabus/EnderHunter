package pharabus.mods.enderhunter;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "enderhunter", name = "Ender Hunter", dependencies = "required-after:Forge@[9.10,);required-after:FML@[6.2,)")
@NetworkMod(versionBounds = "[1.0,)", clientSideRequired = true, serverSideRequired = false)
public class EnderHunter {
    public static ItemAthame itemAthane;
   // public static ItemEnderPoo itemEnderPoo;
    public static Enchantment enchantmentEnderHunter;
    public static EnumToolMaterial enderisedGold;
    @Instance("enderhunter")
    public static EnderHunter instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ConfigHelper.init(event);
        enderisedGold = EnumHelper.addToolMaterial("EnderisedGold", 1, 200,12.0F, 0.0F, 22);
        itemAthane = new ItemAthame(ConfigHelper.ItemAthameId);
        //itemEnderPoo = new ItemEnderPoo(ConfigHelper.ItemEnderPooId);
        GameRegistry.registerItem(itemAthane, itemAthane.getUnlocalizedName());
        

    }

    @EventHandler
    public void load(FMLInitializationEvent evt) {
        ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(itemAthane, 1),new Object[] { " G "," E " ," W ", Character.valueOf('G'), Item.ingotGold, Character.valueOf('E'), Item.enderPearl, Character.valueOf('W'), Item.stick });
        GameRegistry.addRecipe(recipe);
        
     //  PotionHelper.potionRequirements.put(10, "0 & 1 & !2 & !3 & 0+6");
        
       enchantmentEnderHunter = new EnchantmentEnderHunter(ConfigHelper.EnchantmentEnderHunterId, 10, EnumEnchantmentType.weapon);
        
    // Register the EntityLiving Handler
       MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());
       
     
       
       //LanguageRegistry.addName(itemAthane, ConfigHelper.ItemAthanename);
       LanguageRegistry.instance().addStringLocalization("item." + ConfigHelper.ItemAthameUnlocalisedname + ".name","en_US",ConfigHelper.ItemAthamename);
       LanguageRegistry.instance().addStringLocalization("enchantment." + ConfigHelper.EnchantmentEnderHunterUnlocalisedname,"en_US" ,ConfigHelper.EnchantmentEnderHuntername);
    }}
