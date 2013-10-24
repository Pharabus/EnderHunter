package pharabus.mods.enderhunter;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class EntityLivingHandler {
    
    
    protected Random rand;
    
    public EntityLivingHandler()
    {
        this.rand = new Random();
    }
    
    @ForgeSubscribe
    public void onLivingDrops(LivingDropsEvent evt)
    {
        
        if(evt.recentlyHit && evt.source.getSourceOfDamage() instanceof EntityPlayer 
                && evt.entity instanceof EntityEnderman 
                && ((EntityPlayer)evt.source.getSourceOfDamage()).getCurrentEquippedItem() != null
                && ((EntityPlayer)evt.source.getSourceOfDamage()).getCurrentEquippedItem().itemID == ConfigHelper.ShiftedItemAthameId)
        {
            int enchantLevel =EnchantmentHelper.getEnchantmentLevel(EnderHunter.enchantmentEnderHunter.effectId, ((EntityPlayer) evt.source.getSourceOfDamage()).getCurrentEquippedItem());
            if(enchantLevel > 0)
            {
                Random rand = new Random();
                int k = rand.nextInt(2 + enchantLevel);
                    evt.entity.dropItem(Item.enderPearl.itemID, k);           
            }
            evt.setCanceled(true);
        }
    }
    
    @ForgeSubscribe
    public void  onEndermanTeleport(EnderTeleportEvent evt)
    {
        if(evt.entityLiving instanceof  EntityEnderman && evt.entityLiving.func_94060_bK() != null
                && evt.entityLiving.func_94060_bK() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)evt.entityLiving.func_94060_bK();
            if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == ConfigHelper.ShiftedItemAthameId
                    && EnchantmentHelper.getEnchantmentLevel(EnderHunter.enchantmentEnderHunter.effectId, player.getCurrentEquippedItem()) > 2)
            {
                evt.setCanceled(true);
            }
        }
    }
  
    @ForgeSubscribe
    public void onAttackEntity(AttackEntityEvent evt)
    {
       if(evt.entity instanceof EntityPlayer 
        && (evt.target instanceof EntityEnderman || evt.target instanceof EntityDragon)
        && evt.entityPlayer.getCurrentEquippedItem().itemID == ConfigHelper.ShiftedItemAthameId)
        {
          
           ItemStack stack = evt.entityPlayer.getCurrentEquippedItem();
           if (stack != null && stack.getItem().onLeftClickEntity(stack, evt.entityPlayer, evt.target))
           {
               return;
           }
           if (evt.target.canAttackWithItem())
           {
               if (!evt.target.hitByEntity(evt.entityPlayer))
               {
                   float f = ConfigHelper.Enderdamage;
                   int i = 0;
                   float f1 = 0.0F;

                   if (evt.target instanceof EntityLivingBase)
                   {
                       f1 = EnchantmentHelper.getEnchantmentModifierLiving(evt.entityPlayer, (EntityLivingBase)evt.target);
                       i += EnchantmentHelper.getKnockbackModifier(evt.entityPlayer, (EntityLivingBase)evt.target);
                   }

                   if (evt.entity.isSprinting())
                   {
                       ++i;
                   }

                   if (f > 0.0F || f1 > 0.0F)
                   {
                       boolean flag = evt.entityPlayer.fallDistance > 0.0F && !evt.entity.onGround && !evt.entityPlayer.isOnLadder() && !evt.entityPlayer.isInWater() && !evt.entityPlayer.isPotionActive(Potion.blindness) && evt.entityPlayer.ridingEntity == null && evt.target instanceof EntityLivingBase;

                       if (flag && f > 0.0F)
                       {
                           f *= 1.5F;
                       }

                       f += f1;
                       boolean flag1 = false;
                       int j = EnchantmentHelper.getFireAspectModifier(evt.entityPlayer);

                       if (evt.target instanceof EntityLivingBase && j > 0 && !evt.target.isBurning())
                       {
                           flag1 = true;
                           evt.target.setFire(1);
                       }

                       boolean flag2 = evt.target.attackEntityFrom(DamageSource.causePlayerDamage(evt.entityPlayer), f);

                       if (flag2)
                       {
                           if (i > 0)
                           {
                               evt.target.addVelocity((double)(-MathHelper.sin(evt.entityPlayer.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(evt.entityPlayer.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                               evt.entityPlayer.motionX *= 0.6D;
                               evt.entityPlayer.motionZ *= 0.6D;
                               evt.entityPlayer.setSprinting(false);
                           }

                           if (flag)
                           {
                               evt.entityPlayer.onCriticalHit(evt.target);
                           }

                           if (f1 > 0.0F)
                           {
                               evt.entityPlayer.onEnchantmentCritical(evt.target);
                           }

                           if (f >= 18.0F)
                           {
                               evt.entityPlayer.triggerAchievement(AchievementList.overkill);
                           }

                           evt.entityPlayer.setLastAttacker(evt.target);

                           if (evt.target instanceof EntityLivingBase)
                           {
                               EnchantmentThorns.func_92096_a(evt.entityPlayer, (EntityLivingBase)evt.target, this.rand);
                           }
                       }

                       ItemStack itemstack = evt.entityPlayer.getCurrentEquippedItem();
                       Object object = evt.target;

                       if (evt.target instanceof EntityDragonPart)
                       {
                           IEntityMultiPart ientitymultipart = ((EntityDragonPart)evt.target).entityDragonObj;

                           if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase)
                           {
                               object = (EntityLivingBase)ientitymultipart;
                           }
                       }

                       if (itemstack != null && object instanceof EntityLivingBase)
                       {
                           itemstack.hitEntity((EntityLivingBase)object, evt.entityPlayer);

                           if (itemstack.stackSize <= 0)
                           {
                               evt.entityPlayer.destroyCurrentEquippedItem();
                           }
                       }

                       if (evt.target instanceof EntityLivingBase)
                       {
                           evt.entityPlayer.addStat(StatList.damageDealtStat, Math.round(f * 10.0F));

                           if (j > 0 && flag2)
                           {
                               evt.target.setFire(j * 4);
                           }
                           else if (flag1)
                           {
                               evt.target.extinguish();
                           }
                       }

                       evt.entityPlayer.addExhaustion(0.3F);
                   }
               }
           }
           
           
           evt.setCanceled(true);
        }
    }
}
