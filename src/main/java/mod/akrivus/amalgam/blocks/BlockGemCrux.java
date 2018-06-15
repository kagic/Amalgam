package mod.akrivus.amalgam.blocks;

import mod.akrivus.amalgam.init.AmBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockGemCrux extends Block {
	public BlockGemCrux(String name, MapColor color) {
		super(Material.ROCK, color);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	}
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity){
        if (entity instanceof EntityDragon) {
            Block block = state.getBlock();
            if (block == AmBlocks.SODALITE || block == AmBlocks.BISMITE || block == AmBlocks.SERPENTINITE) {
            	return false;
            }
        }
        return super.canEntityDestroy(state, world, pos, entity);
    }
}
