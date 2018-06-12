package mod.akrivus.amalgam.enchant;

import java.util.HashMap;

import mod.akrivus.amalgam.entity.EntityGemShard;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
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
					if (target instanceof EntityLivingBase) {
						shard.setAttackTarget((EntityLivingBase) target);
					}
				}
			}
		}
    }
    public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
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
