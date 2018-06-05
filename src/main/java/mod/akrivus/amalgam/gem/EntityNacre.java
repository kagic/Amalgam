package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;

import mod.akrivus.amalgam.gem.ai.EntityAIEatBlocks;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;

public class EntityNacre extends EntityPearl {
	private static final DataParameter<Boolean> CRACKED = EntityDataManager.<Boolean>createKey(EntityNacre.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> COLOR_1 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_2 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_3 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_4 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	
	public static final HashMap<IBlockState, Double> NACRE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double NACRE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double NACRE_DEPTH_THRESHOLD = 255;
	
	private static final int SKIN_COLOR_BEGIN = 0xEDEFF4; 
	private static final int SKIN_COLOR_MID = 0xBDCBED; 
	private static final int SKIN_COLOR_END = 0xE1CBED; 
	private static final int HAIR_COLOR_BEGIN = 0x97C6EF;
	private static final int HAIR_COLOR_END = 0xFFC6EF; 
	private static final int NUM_HAIRSTYLES = 4;
	
	public int ticksUntilSneeze;
	public int ticksUntilCough;
	public int totalExpected;
	public double stressLevel;
	public double foodLevel;
	
	public EntityNacre(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 2.5F);
		this.droppedGemItem = AmItems.NACRE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_NACRE_GEM;
		this.droppedGemItem = AmItems.NACRE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_NACRE_GEM;
		this.nativeColor = 9;
		this.tasks.addTask(2, new EntityAIEatBlocks(this, 0.9D));
		this.dataManager.register(COLOR_1, this.rand.nextInt(16));
		this.dataManager.register(COLOR_2, this.rand.nextInt(16));
		this.dataManager.register(COLOR_3, this.rand.nextInt(16));
		this.dataManager.register(COLOR_4, this.rand.nextInt(16));
		this.setHairColor(this.generateHairColor());
	}
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityNacre.SKIN_COLOR_BEGIN);
		skinColors.add(EntityNacre.SKIN_COLOR_MID);
		skinColors.add(EntityNacre.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	public int generateHairStyle() {
		return this.rand.nextInt(EntityNacre.NUM_HAIRSTYLES);
	}
	public int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityNacre.HAIR_COLOR_BEGIN);
		hairColors.add(EntityNacre.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	public int generateGemColor() {
		return 0xAE98F2;
	}
	public int getLayerColor(int layer) {
		switch (layer) {
    	case 0:
    		return 0xE9ECEC;
    	case 1:
    		return 0xED975A;
    	case 2:
    		return 0xBC7CB8;
    	case 3:
    		return 0x7BC1D8;
    	case 4:
    		return 0xF7D571;
    	case 5:
    		return 0x89B750;
    	case 6:
    		return 0xEAD3DA;
    	case 7:
    		return 0x7F8B91;
    	case 8:
    		return 0xD8D8CD;
    	case 9:
    		return 0x63D2D8;
    	case 10:
    		return 0x8C5DAA;
    	case 11:
    		return 0x63669B;
    	case 12:
    		return 0xBC7643;
    	case 13:
    		return 0x8EB72D;
    	case 14:
    		return 0xED9693;
    	case 15:
    		return 0x333333;
    	}
		return 0xFFFFFF;
	}
	public int getColor() {
		return -1;
	}
	public int getColor1() {
		return this.dataManager.get(COLOR_1);
	}
	public void setColor1(int color) {
		this.dataManager.set(COLOR_1, color);
	}
	public int getColor2() {
		return this.dataManager.get(COLOR_2);
	}
	public void setColor2(int color) {
		this.dataManager.set(COLOR_2, color);
	}
	public int getColor3() {
		return this.dataManager.get(COLOR_3);
	}
	public void setColor3(int color) {
		this.dataManager.set(COLOR_3, color);
	}
	public int getColor4() {
		return this.dataManager.get(COLOR_4);
	}
	public void setColor4(int color) {
		this.dataManager.set(COLOR_4, color);
	}
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.itemDataToGemData(0);
		return livingdata;
	}
	public void itemDataToGemData(int data) {
		this.setGemColor(this.generateGemColor());
		this.setSkinColor(this.generateSkinColor());
		this.setHairColor(this.generateHairColor());
		this.setInsigniaColor(this.getColor1());
		this.setUniformColor(this.getColor2());
		this.nativeColor = this.getColor2();
	}
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("ticksUntilSneeze", this.ticksUntilSneeze);
		compound.setInteger("ticksUntilCough", this.ticksUntilCough);
		compound.setInteger("totalExpected", this.totalExpected);
		compound.setDouble("stressLevel", this.stressLevel);
		compound.setDouble("foodLevel", this.foodLevel);
        compound.setInteger("color_1", this.getColor1());
        compound.setInteger("color_2", this.getColor2());
        compound.setInteger("color_3", this.getColor3());
        compound.setInteger("color_4", this.getColor4());
	}
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.ticksUntilSneeze = compound.getInteger("ticksUntilSneeze");
		this.ticksUntilCough = compound.getInteger("ticksUntilCough");
		this.totalExpected = compound.getInteger("totalExpected");
		this.stressLevel = compound.getDouble("stressLevel");
		this.foodLevel = compound.getDouble("foodLevel");
		this.setColor1(compound.getInteger("color_1"));
		this.setColor2(compound.getInteger("color_2"));
		this.setColor3(compound.getInteger("color_3"));
		this.setColor4(compound.getInteger("color_4"));
	}
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {  
		    			if (stack.getItem() == Items.SHEARS || stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) {
			        		return true;
		    			}
		    			else if (DyeUtils.isDye(stack)) {
		    				if (player.isSneaking()) {
		    					this.setUniformColor(DyeUtils.metaFromStack(stack).orElse(0));
								this.uniformColorChanged = true;
								return true;
		    				}
		    				else {
		    					this.setInsigniaColor(DyeUtils.metaFromStack(stack).orElse(0));
		    					return true;
		    				}
		    			}
		        	}
				}
			}
		}
		return super.processInteract(player, hand);
    }
	public boolean canPickUpItem(Item itemIn) {
		return false;
	}
	public boolean canPickUpLoot() {
		return false;
	}
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
	public boolean canChangeInsigniaColorByDefault() {
		return true;
	}
	public boolean canChangeUniformColorByDefault() {
		return true;
	}
	public void setHairColor(int color) {
		if (color > EntityNacre.HAIR_COLOR_BEGIN && color < EntityNacre.HAIR_COLOR_END) {
			super.setHairColor(color);
		}
	}
}
