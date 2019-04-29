package mod.amalgam.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mod.amalgam.init.AmBlocks;
import mod.ke2.init.Ke2CreativeTabs;
import mod.ke2.tileentity.TileEntityCarbonite;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarbonite extends Block implements ITileEntityProvider {
	public static final Map<BlockCarbonite, BlockCarbonite> CONVERSION_TABLE = new HashMap<BlockCarbonite, BlockCarbonite>(); 
	public static void registerConversionTables() {
		CONVERSION_TABLE.put(AmBlocks.BLACK_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.BLACK_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.BLUE_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.BLUE_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.BROWN_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.BROWN_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.CYAN_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.CYAN_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.GRAY_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.GRAY_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.GREEN_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.GREEN_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.LIGHTBLUE_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.LIGHTBLUE_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.LIME_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.LIME_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.MAGENTA_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.MAGENTA_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.ORANGE_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.ORANGE_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.PINK_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.PINK_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.PURPLE_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.PURPLE_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.RED_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.RED_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.SILVER_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.SILVER_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.WHITE_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.WHITE_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.YELLOW_HOLOGRAPHIC_CARBONITE_OFF, AmBlocks.YELLOW_HOLOGRAPHIC_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.BLACK_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.BLACK_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.BLUE_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.BLUE_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.BROWN_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.BROWN_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.CYAN_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.CYAN_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.GRAY_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.GRAY_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.GREEN_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.GREEN_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.LIGHTBLUE_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.LIGHTBLUE_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.LIME_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.LIME_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.MAGENTA_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.MAGENTA_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.ORANGE_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.ORANGE_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.PINK_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.PINK_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.PURPLE_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.PURPLE_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.RED_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.RED_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.SILVER_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.SILVER_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.WHITE_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.WHITE_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.YELLOW_HOLOGRAPHIC_CARBONITE_ON, AmBlocks.YELLOW_HOLOGRAPHIC_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.BLACK_CARBONITE_OFF, AmBlocks.BLACK_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.BLUE_CARBONITE_OFF, AmBlocks.BLUE_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.BROWN_CARBONITE_OFF, AmBlocks.BROWN_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.CYAN_CARBONITE_OFF, AmBlocks.CYAN_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.GRAY_CARBONITE_OFF, AmBlocks.GRAY_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.GREEN_CARBONITE_OFF, AmBlocks.GREEN_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.LIGHTBLUE_CARBONITE_OFF, AmBlocks.LIGHTBLUE_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.LIME_CARBONITE_OFF, AmBlocks.LIME_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.MAGENTA_CARBONITE_OFF, AmBlocks.MAGENTA_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.ORANGE_CARBONITE_OFF, AmBlocks.ORANGE_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.PINK_CARBONITE_OFF, AmBlocks.PINK_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.PURPLE_CARBONITE_OFF, AmBlocks.PURPLE_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.RED_CARBONITE_OFF, AmBlocks.RED_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.SILVER_CARBONITE_OFF, AmBlocks.SILVER_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.WHITE_CARBONITE_OFF, AmBlocks.WHITE_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.YELLOW_CARBONITE_OFF, AmBlocks.YELLOW_CARBONITE_ON);
		CONVERSION_TABLE.put(AmBlocks.BLACK_CARBONITE_ON, AmBlocks.BLACK_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.BLUE_CARBONITE_ON, AmBlocks.BLUE_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.BROWN_CARBONITE_ON, AmBlocks.BROWN_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.CYAN_CARBONITE_ON, AmBlocks.CYAN_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.GRAY_CARBONITE_ON, AmBlocks.GRAY_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.GREEN_CARBONITE_ON, AmBlocks.GREEN_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.LIGHTBLUE_CARBONITE_ON, AmBlocks.LIGHTBLUE_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.LIME_CARBONITE_ON, AmBlocks.LIME_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.MAGENTA_CARBONITE_ON, AmBlocks.MAGENTA_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.ORANGE_CARBONITE_ON, AmBlocks.ORANGE_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.PINK_CARBONITE_ON, AmBlocks.PINK_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.PURPLE_CARBONITE_ON, AmBlocks.PURPLE_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.RED_CARBONITE_ON, AmBlocks.RED_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.SILVER_CARBONITE_ON, AmBlocks.SILVER_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.WHITE_CARBONITE_ON, AmBlocks.WHITE_CARBONITE_OFF);
		CONVERSION_TABLE.put(AmBlocks.YELLOW_CARBONITE_ON, AmBlocks.YELLOW_CARBONITE_OFF);
	}
	public enum Variety {
		INERT(false, "$_carbonite"), ACTIVE(true, "$_carbonite"),
		DECORATIVE(false, "$_decorative_carbonite"),
		HOLOGRAPHIC(true, "$_holographic_carbonite");
		private final boolean canBePowered;
		private final String localName;
		Variety(boolean canBePowered, String localName) {
			this.canBePowered = canBePowered;
			this.localName = localName;
		}
		public boolean canBePowered() {
			return this.canBePowered;
		}
		public String getName() {
			return this.localName;
		}
	}
	private Variety variety = Variety.INERT;
	private boolean canBePowered = true;
	private boolean powered;
	private int color;
	public BlockCarbonite(Variety variety, int color, boolean powered) {
		super(Material.ROCK);
		String name = EnumDyeColor.byMetadata(color).toString().toLowerCase();
		this.canBePowered = variety.canBePowered(); this.variety = variety;
        this.powered = powered; this.color = color;
    	if (this.powered) {
    		this.setUnlocalizedName(variety.getName().replaceAll("\\$", name) + "_on");
        	this.setResistance(6000000);
        	this.setHardness(-1);
    	}
    	else {
            if (this.canBePowered) {
            	this.setUnlocalizedName(variety.getName().replaceAll("\\$", name) + "_off");
            }
            else {
            	this.setUnlocalizedName(variety.getName().replaceAll("\\$", name));
            }
        	this.setResistance(30);
        	this.setHardness(2);
    	}
    	this.setCreativeTab(Ke2CreativeTabs.GEM_TECH);
	}
	public BlockCarbonite(Variety variety, int color) {
		this(variety, color, false);
	}
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCarbonite();
	}
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (this.is(Variety.HOLOGRAPHIC) && this.isPowered()) {
			return Block.NULL_AABB;
		}
		else {
			return Block.FULL_BLOCK_AABB;
		}
    }
	@Override
    public BlockRenderLayer getBlockLayer() {
		if (this.is(Variety.HOLOGRAPHIC) && this.isPowered()) {
			return BlockRenderLayer.TRANSLUCENT;
		}
		else {
			return BlockRenderLayer.SOLID;
		}
    }
	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.getBlockColor(EnumDyeColor.byDyeDamage(this.color));
    }
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}
	@Override
    public boolean isFullCube(IBlockState state) {
        return !(this.is(Variety.HOLOGRAPHIC) && this.isPowered());
    }
	@Override
	public boolean isOpaqueCube(IBlockState state) {
        return !(this.is(Variety.HOLOGRAPHIC) && this.isPowered());
    }
	@Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		IBlockState offset = world.getBlockState(pos.offset(side));
        Block block = offset.getBlock();
        if (this.is(Variety.HOLOGRAPHIC) && this.isPowered()) {
            if (state != offset) {
                return true;
            }
        }
        if (block == this) {
        	return false;
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }
    @Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		return Item.getItemFromBlock(this);
	}
    public boolean is(Variety variety) {
    	return this.variety == variety;
    }
    public boolean isPowered() {
    	return this.powered;
    }
    public boolean canBePowered() {
    	return this.canBePowered;
    }
    public IBlockState getReverseState() {
    	return CONVERSION_TABLE.get(this).getDefaultState();
    }
}