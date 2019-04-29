package mod.amalgam.blocks;

import java.util.Random;

import mod.amalgam.init.AmBlocks;
import mod.amalgam.tileentity.TileEntityWailingStone;
import mod.kagic.init.ModCreativeTabs;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWailingStone extends BlockFalling implements ITileEntityProvider {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public BlockWailingStone() {
		super(Material.ROCK);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(2);
		this.setUnlocalizedName("wailing_stone");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	}
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(world, pos, state);
		super.onBlockAdded(world, pos, state);
	}
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity container = world.getTileEntity(pos);
		if (container != null && container instanceof TileEntityWailingStone) {
			TileEntityWailingStone wailingStone = (TileEntityWailingStone) container;
			wailingStone.toggleActivate();
		}
		return true;
	}
	@Override
	protected void onStartFalling(EntityFallingBlock block) {
		block.setHurtEntities(true);
    }
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune){
		return Item.getItemFromBlock(this);
	}
	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.GOLD;
    }
	
	/*********************************************************
	 * Methods related to block states and direction.		*
	 *********************************************************/
	private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			IBlockState north = world.getBlockState(pos.north());
			IBlockState south = world.getBlockState(pos.south());
			IBlockState east = world.getBlockState(pos.west());
			IBlockState west = world.getBlockState(pos.east());
			EnumFacing facing = (EnumFacing)(state.getValue(FACING));
			if (facing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
				facing = EnumFacing.SOUTH;
			}
			else if (facing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
				facing = EnumFacing.NORTH;
			}
			else if (facing == EnumFacing.WEST && east.isFullBlock() && !west.isFullBlock()) {
				facing = EnumFacing.EAST;
			}
			else if (facing == EnumFacing.EAST && west.isFullBlock() && !east.isFullBlock()) {
				facing = EnumFacing.WEST;
			}
			world.setBlockState(pos, state.withProperty(FACING, facing), 2);
		}
	}
	public static void setState(boolean active, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		world.setBlockState(pos, AmBlocks.WAILING_STONE.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		world.setBlockState(pos, AmBlocks.WAILING_STONE.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, facing);
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWailingStone();
	}
	
	/*********************************************************
	 * Methods related to block rendering.				   *
	 *********************************************************/
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.9375D, 0.8125D);
    }
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.9375D, 0.8125D);
    }
}