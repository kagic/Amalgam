package mod.akrivus.amalgam.blocks;

import java.util.Random;

import mod.akrivus.amalgam.init.AmBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarbonite extends Block {
	public boolean powered;
	public BlockCarbonite(boolean powered, int color) {
		super(Material.ROCK, MapColor.getBlockColor(EnumDyeColor.byDyeDamage(color)));
		this.setUnlocalizedName("carbonite_" + color + "_" + (powered ? "on" : "off"));
        this.powered = powered;
        if (this.powered) {
        	this.setBlockUnbreakable();
        	this.setResistance(6000000);
        	this.setLightLevel(2);
        }
        else {
        	this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
        	this.setResistance(30);
        	this.setHardness(2);
        }
	}
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		super.neighborChanged(state, world, pos, block, fromPos);
		IBlockState from = world.getBlockState(fromPos);
		boolean powered = world.isBlockPowered(pos);
		if (from.getBlock() instanceof BlockCarbonite) {
			BlockCarbonite carbonite = (BlockCarbonite)(from.getBlock());
			if (carbonite.powered) {
				powered = true;
			}
			else if (powered) {
				world.setBlockState(fromPos, carbonite.getPoweredVariety().getDefaultState());
			}
		}
		if (!this.powered && powered) {
			world.setBlockState(pos, this.getPoweredVariety().getDefaultState());
		}
		if (!powered && this.powered) {
			world.setBlockState(pos, this.getNormalVariety().getDefaultState());
		}
    }
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}
    @Override
	public Item getItemDropped(IBlockState state, Random random, int fortune){
		return Item.getItemFromBlock(this);
	}
    public BlockCarbonite getPoweredVariety() {
    	if (this == AmBlocks.WHITE_CARBONITE_OFF) {
    		return AmBlocks.WHITE_CARBONITE_ON;
    	}
    	if (this == AmBlocks.ORANGE_CARBONITE_OFF) {
    		return AmBlocks.ORANGE_CARBONITE_ON;
    	}
    	if (this == AmBlocks.MAGENTA_CARBONITE_OFF) {
    		return AmBlocks.MAGENTA_CARBONITE_ON;
    	}
    	if (this == AmBlocks.LIGHT_BLUE_CARBONITE_OFF) {
    		return AmBlocks.LIGHT_BLUE_CARBONITE_ON;
    	}
    	if (this == AmBlocks.YELLOW_CARBONITE_OFF) {
    		return AmBlocks.YELLOW_CARBONITE_ON;
    	}
    	if (this == AmBlocks.LIME_CARBONITE_OFF) {
    		return AmBlocks.LIME_CARBONITE_ON;
    	}
    	if (this == AmBlocks.PINK_CARBONITE_OFF) {
    		return AmBlocks.PINK_CARBONITE_ON;
    	}
    	if (this == AmBlocks.GRAY_CARBONITE_OFF) {
    		return AmBlocks.GRAY_CARBONITE_ON;
    	}
    	if (this == AmBlocks.SILVER_CARBONITE_OFF) {
    		return AmBlocks.SILVER_CARBONITE_ON;
    	}
    	if (this == AmBlocks.CYAN_CARBONITE_OFF) {
    		return AmBlocks.CYAN_CARBONITE_ON;
    	}
    	if (this == AmBlocks.PURPLE_CARBONITE_OFF) {
    		return AmBlocks.PURPLE_CARBONITE_ON;
    	}
    	if (this == AmBlocks.BLUE_CARBONITE_OFF) {
    		return AmBlocks.BLUE_CARBONITE_ON;
    	}
    	if (this == AmBlocks.BROWN_CARBONITE_OFF) {
    		return AmBlocks.BROWN_CARBONITE_ON;
    	}
    	if (this == AmBlocks.GREEN_CARBONITE_OFF) {
    		return AmBlocks.GREEN_CARBONITE_ON;
    	}
    	if (this == AmBlocks.RED_CARBONITE_OFF) {
    		return AmBlocks.RED_CARBONITE_ON;
    	}
    	if (this == AmBlocks.BLACK_CARBONITE_OFF) {
    		return AmBlocks.BLACK_CARBONITE_ON;
    	}
    	return AmBlocks.WHITE_CARBONITE_ON;
    }
    public BlockCarbonite getNormalVariety() {
    	if (this == AmBlocks.WHITE_CARBONITE_ON) {
    		return AmBlocks.WHITE_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.ORANGE_CARBONITE_ON) {
    		return AmBlocks.ORANGE_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.MAGENTA_CARBONITE_ON) {
    		return AmBlocks.MAGENTA_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.LIGHT_BLUE_CARBONITE_ON) {
    		return AmBlocks.LIGHT_BLUE_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.YELLOW_CARBONITE_ON) {
    		return AmBlocks.YELLOW_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.LIME_CARBONITE_ON) {
    		return AmBlocks.LIME_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.PINK_CARBONITE_ON) {
    		return AmBlocks.PINK_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.GRAY_CARBONITE_ON) {
    		return AmBlocks.GRAY_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.SILVER_CARBONITE_ON) {
    		return AmBlocks.SILVER_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.CYAN_CARBONITE_ON) {
    		return AmBlocks.CYAN_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.PURPLE_CARBONITE_ON) {
    		return AmBlocks.PURPLE_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.BLUE_CARBONITE_ON) {
    		return AmBlocks.BLUE_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.BROWN_CARBONITE_ON) {
    		return AmBlocks.BROWN_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.GREEN_CARBONITE_ON) {
    		return AmBlocks.GREEN_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.RED_CARBONITE_ON) {
    		return AmBlocks.RED_CARBONITE_OFF;
    	}
    	if (this == AmBlocks.BLACK_CARBONITE_ON) {
    		return AmBlocks.BLACK_CARBONITE_OFF;
    	}
    	return AmBlocks.WHITE_CARBONITE_OFF;
    }
}
