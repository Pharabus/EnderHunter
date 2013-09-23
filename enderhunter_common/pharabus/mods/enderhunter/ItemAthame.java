package pharabus.mods.enderhunter;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAthame extends ItemSword {

    public ItemAthame(int id) {
        super(id,EnumToolMaterial.GOLD);
        setCreativeTab(CreativeTabs.tabCombat);
        setMaxStackSize(1);
        setUnlocalizedName(ConfigHelper.ItemAthameUnlocalisedname);
        
    }
    
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        return 1.0F;
      }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {        
        itemIcon = register.registerIcon(ConfigHelper.TEXTURE_LOCATION + ":" + ConfigHelper.ATHAME_ICON);
    }
    
    @Override
      public boolean getIsRepairable(ItemStack ist1, ItemStack ist2)
      {
        if (ist2.isItemEqual(new ItemStack(Item.ingotGold)))
          return true;
        return false;
      }
    @Override
      public int getItemEnchantability() {
        return 30;
      }

}