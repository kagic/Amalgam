package mod.akrivus.amalgam.enchant;

import java.util.HashMap;

import mod.akrivus.amalgam.entity.EntityGemShard;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantShard extends Enchantment {
	public static final HashMap<String, EnchantShard> ENCHANTS = new HashMap<String, EnchantShard>();
	public final int color;
	public EnchantShard(int color) {
		super(Rarity.UNCOMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND });
		this.setRegistryName(new ResourceLocation("amalgam:gem_shard_" + color));
		this.setName("gem_shard_" + color + ".name");
		this.color = color;
		ENCHANTS.put("gem_shard_" + color, this);
	}
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
		if (target instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase)(target);
			for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
				ItemStack stack = entity.getItemStackFromSlot(slot);
				NBTTagList enchantments = stack.getEnchantmentTagList();
				for (int i = 0; i < enchantments.tagCount(); i++) {
					if (Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")) instanceof EnchantShard) {
						EnchantShard en = (EnchantShard)(Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")));
						switch (en.color) {
						case 0:
							entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400));
							break;
						case 1:
							entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 400));
							break;
						case 2:
							entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 400));
							break;
						case 3:
							entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 400));
							break;
						case 4:
							entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 400));
							break;
						case 5:
							entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 400));
							break;
						case 6:
							entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 400));
							break;
						case 7:
							entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
							break;
						case 8:
							entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
							break;
						case 9:
							entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400));
							break;
						case 10:
							entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 400));
							break;
						case 11:
							entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400));
							break;
						case 12:
							entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 400));
							break;
						case 13:
							entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 400));
							break;
						case 14:
							entity.setFire(800);
							break;
						case 15:
							entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400));
							break;
						}
					}
				}
			}
		}
    }
    public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
    	/*
    	for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
    		ItemStack stack = user.getItemStackFromSlot(slot);
			NBTTagList enchantments = stack.getEnchantmentTagList();
			for (int i = 0; i < enchantments.tagCount(); i++) {
				if (Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")) instanceof EnchantShard) {
					EnchantShard en = (EnchantShard)(Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")));
					EntityGemShard shard = new EntityGemShard(user.world);
					shard.setPositionAndRotation(user.posX, user.posY, user.posZ, user.rotationYaw, user.rotationPitch);
					shard.setColor(en.color);
					shard.setItem(stack);
					user.world.spawnEntity(shard);
					user.setItemStackToSlot(slot, ItemStack.EMPTY);
					if (attacker instanceof EntityLivingBase) {
						shard.setAttackTarget((EntityLivingBase) attacker);
					}
				}
			}
		}
		*/
    }
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
    public boolean isTreasureEnchantment() {
        return true;
    }
    public boolean isCurse() {
        return true;
    }
	public boolean isAllowedOnBooks() {
		return false;
	}
	public int getMinEnchantability(int enchantmentLevel) {
        return 5;
    }
    public int getMaxEnchantability(int enchantmentLevel) {
        return 55;
    }
	public int getMaxLevel() {
        return 1;
    }
}
