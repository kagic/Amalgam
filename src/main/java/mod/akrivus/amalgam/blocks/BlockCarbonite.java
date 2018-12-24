package mod.akrivus.amalgam.blocks;

import java.util.ArrayList;
import java.util.Random;

import mod.akrivus.amalgam.init.AmBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarbonite extends Block {
	public static final ArrayList<BlockCarbonite> POWERED_BLOCKS = new ArrayList<BlockCarbonite>();
	public static final ArrayList<BlockCarbonite> NORMAL_BLOCKS = new ArrayList<BlockCarbonite>();
	public static final PropertyInteger COLOR = PropertyInteger.create("color", 0, 15);
	public boolean powered;
	public BlockCarbonite(boolean powered) {
		super(Material.ROCK);
		this.setRegistryName("carbonite_when_" + (powered ? "on" : "off"));
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, 0));
        this.powered = powered;
        if (this.powered) {
        	this.setBlockUnbreakable();
        	this.setLightLevel(4);
        }
        else {
        	this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
        	this.setHardness(4);
        }
	}
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if (world instanceof World) {
			World this_world = (World)(world);
			if (this_world.isBlockPowered(pos) && !this.powered) {
				this_world.setBlockState(pos, AmBlocks.CARBONITE_WHEN_ON.getDefaultState().withProperty(COLOR, this_world.getBlockState(pos).getValue(COLOR)));
			}
			else if (this.powered) {
				this_world.setBlockState(pos, AmBlocks.CARBONITE_WHEN_OFF.getDefaultState().withProperty(COLOR, this_world.getBlockState(pos).getValue(COLOR)));
			}
		}
    }
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (this.powered) {
	        if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH || side == EnumFacing.EAST || side == EnumFacing.WEST) {
	        	return 1;
	        }
		}
        return 0;
    }
	@Override
    public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (this.powered) {
	        if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH || side == EnumFacing.EAST || side == EnumFacing.WEST) {
	        	return 1;
	        }
		}
        return 0;
    }
    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOR, meta);
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR);
	}
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { COLOR });
	}
    @Override
	public Item getItemDropped(IBlockState state, Random random, int fortune){
		return Item.getItemFromBlock(this);
	}
	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.getBlockColor(EnumDyeColor.byDyeDamage(state.getValue(COLOR)));
    }
}
