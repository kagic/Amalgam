package mod.akrivus.amalgam.gem;

import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;

public class EntityBabyPearl extends EntityPearl {
	public EntityBabyPearl(World worldIn) {
		super(worldIn);
		this.setSize(0.3F, 0.95F);
		this.stepHeight = 0.5F;
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.9D, EntityNacre.class));
		this.tasks.addTask(3, new EntityAITempt(this, 0.9D, Items.SUGAR, false));
		this.tasks.addTask(4, new EntityAIPanic(this, 0.9D));
		this.targetTasks.addTask(2, new EntityAICallForBackup(this, EntityGem.class));
	}
	public void onDeath(DamageSource cause) {
		if (this.isDefective()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		}
		super.onDeath(cause);
	}
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {  
						//player.addStat(ModAchievements.THAT_WILL_BE_ALL);
		    			if (stack.getItem() == Items.SHEARS) {
		    				int hair = this.getHairStyle() + 1;
		    				if (hair >= EntityPearl.PEARL_HAIR_STYLES.size() || hair < 0) {
		    					hair = 0;
		    				}
			        		this.setHairStyle(hair);
		    				this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, this.getSoundVolume(), this.getSoundPitch());
			        		if (!player.capabilities.isCreativeMode) {
			        			stack.damageItem(1, this);
			        		}
			        		return true;
		        		}
		    			else if (DyeUtils.isDye(stack)) {
		    				if (player.isSneaking()) {
		    					int oldColor = this.getHairColor();
				        		this.setHairColor(DyeUtils.metaFromStack(stack).orElse(0));
				        		if (!player.capabilities.isCreativeMode && oldColor != this.getHairColor()) {
				        			stack.shrink(1);
				        		}
		    				}
		    				else {
			    				int oldColor = this.getColor();
				        		this.setColor(15 - stack.getItemDamage());
				        		if (!player.capabilities.isCreativeMode && oldColor != this.getColor()) {
				        			stack.shrink(1);
				        		}
		    				}
			        		return true;
		    			}
		    			else if (stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) {
		    				if (player.isSneaking()) {
		    					if (this.hasVisor()) {
			    					ItemStack newstack = new ItemStack(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE));
			    					Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE).setDamage(newstack, this.getVisorColor());
			    					this.entityDropItem(newstack, 0.0F);
			    					this.setHasVisor(false);
		    					}
		    				}
		    				else {
			    				if (this.hasVisor()) {
			    					ItemStack newstack = new ItemStack(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE));
			    					Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE).setDamage(newstack, this.getVisorColor());
			    					this.entityDropItem(newstack, 0.0F);
			    				}
			    				else {
			    					this.setHasVisor(true);
			    				}
			    				this.setVisorColor(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE).getMetadata(stack));
			    				stack.shrink(1);
		    				}
		    			}
		    			else if (stack.getItem() == Item.getItemFromBlock(Blocks.WOOL)) {
		    				if (this.isNaked()) {
		    					this.setInsigniaColor(stack.getItemDamage());
		    					this.setNaked(false);
		    					stack.shrink(1);
		    				}
		    				if (stack.getCount() > 0) {
			    				if (player.isSneaking()) {
			    					int dress = this.getDressStyle() + 1;
				    				if (dress >= EntityPearl.PEARL_DRESS_STYLES.size() || dress < 0) {
				    					dress = 0;
				    				}
					        		this.setDressStyle(dress);
					        		this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, this.getSoundVolume(), this.getSoundPitch());
					        		return true;
			    				}
			    				else {
				    				int oldColor = this.getInsigniaColor();
					        		this.setInsigniaColor(stack.getItemDamage());
					        		if (!player.capabilities.isCreativeMode) {
					        			if (oldColor != this.getInsigniaColor()) {
					        				stack.shrink(1);
					        			}
					        		}
			    				}
		    				}
			        		return true;
		    			}
		    			else if (this.isCoreItem(stack)) {
		    				return super.processInteract(player, hand);
		    			}
		    			else {
		            		this.openGUI(player);
		            		this.playObeySound();
		            		return true;
		            	}
		        	}
				}
				if (stack.getItem() == Items.SUGAR) {
					this.setOwnerId(player.getUniqueID());
					this.setLeader(player);
					this.setServitude(EntityGem.SERVE_HUMAN);
					this.navigator.clearPath();
					this.setAttackTarget(null);
					this.setHealth(this.getMaxHealth());
					this.playTameEffect();
					this.world.setEntityState(this, (byte) 7);
					this.playObeySound();
					player.sendMessage(new TextComponentTranslation("command.kagic.now_serves_you", this.getName()));
					if (!player.capabilities.isCreativeMode) {
						stack.shrink(1);
					}
					return true;
				}
			}
		}
		return super.processInteract(player, hand);
	}
	public void onInventoryChanged(IInventory inventory) {
		ItemStack firstItem = this.gemStorage.getStackInSlot(0);
		if (firstItem.getItem().isDamageable()) {
			this.setDefective(true);
		}
		else {
			this.setDefective(false);
		}
		super.onInventoryChanged(inventory);
	}
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.world.isRemote && this.isDefective() && this.ticksExisted > 20) {
			this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0.0F);
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.gemStorage.setInventorySlotContents(0, ItemStack.EMPTY);
			this.onInventoryChanged(this.gemStorage);
			this.playHurtSound(DamageSource.GENERIC);
		}
		return super.attackEntityFrom(source, amount);
	}
	public void jump() {
		if (!this.world.isRemote && this.isDefective() && this.ticksExisted > 20) {
			this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0.0F);
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.gemStorage.setInventorySlotContents(0, ItemStack.EMPTY);
			this.onInventoryChanged(this.gemStorage);
			this.playHurtSound(DamageSource.GENERIC);
		}
		super.jump();
	}
	public void fall(float distance, float damageMultiplier) {
		if (!this.world.isRemote && this.isDefective() && this.ticksExisted > 20) {
			this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0.0F);
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.gemStorage.setInventorySlotContents(0, ItemStack.EMPTY);
			this.onInventoryChanged(this.gemStorage);
			this.playHurtSound(DamageSource.GENERIC);
		}
		super.fall(distance, damageMultiplier);
	}
	public int getMaxInventorySlots() {
		int slots = 27;
		if (this.getGemPlacement() == GemPlacements.FOREHEAD) {
			slots += 9;
		}
		return slots;
	}
	public int getTalkInterval() {
		return 120;
	}
	protected SoundEvent getLivingSound() {
		if (this.rand.nextInt(4) == 0) {
			return AmSounds.BABY_PEARL_OBEY;
		}
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.BABY_PEARL_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getWeirdSound() {
		return AmSounds.BABY_PEARL_OH;
	}
	public int playNote(int tone, SoundEvent sound) {
		// 2 = half note
		// 7 = half rest
		// 0 = whole note
		// 5 = whole rest
		switch (tone) {
		case 0:
			this.playSound(sound, 20.0F, this.pitch * 2.0F);
		case 5:
			return 5;
		case 1:
			this.playSound(sound, 20.0F, this.pitch * 2.5F);
		case 6:
			return 4;
		case 2:
			this.playSound(sound, 20.0F, this.pitch * 3.0F);
		case 7:
			return 3;
		case 3:
			this.playSound(sound, 20.0F, this.pitch * 3.5F);
		case 8:
			return 2;
		case 4:
			this.playSound(sound, 20.0F, this.pitch * 4.0F);
		case 9:
			return 1;
		}
		return 6;
	}
}
