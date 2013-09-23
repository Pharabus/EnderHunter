package pharabus.mods.enderhunter;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;

public class EnchantmentEnderHunter extends Enchantment{

    protected EnchantmentEnderHunter(int id, int strength,
            EnumEnchantmentType EnumEnchantmentType) {
        super(id, strength, EnumEnchantmentType);
        this.setName(ConfigHelper.EnchantmentEnderHunterUnlocalisedname);
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int par1)
    {
        return 15 + (par1 - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 50;
    }

    
    @Override
    public float calcModifierLiving(int level,
            EntityLivingBase entity) {
        if (((entity instanceof EntityEnderman)) || ((entity instanceof EntityDragon)))
        {
            return level * 2.5F;
        }
        return 0;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment enchant) {
        if (enchant == this) return false;
        return !(enchant instanceof EnchantmentDamage) && !(enchant.effectId == looting.effectId);
    }
    
    @Override
    public boolean canApply(ItemStack itemStack) {
        // TODO Auto-generated method stub
        return itemStack.itemID == ConfigHelper.ShiftedItemAthameId;
    }
    
}
