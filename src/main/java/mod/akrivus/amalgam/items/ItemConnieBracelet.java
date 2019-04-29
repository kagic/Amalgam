package mod.akrivus.amalgam.items;

import java.util.List;

import javax.annotation.Nullable;

import mod.kagic.init.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemConnieBracelet extends Item {
	public ItemConnieBracelet() {
		this.setUnlocalizedName("connie_bracelet");
		this.setMaxStackSize(1);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	}
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		EnumActionResult result = super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		if (result == EnumActionResult.PASS && !worldIn.isRemote) {
			ITextComponent message = new TextComponentTranslation(String.format("command.amalgam.connie_doesnt_spawn"));
			message.getStyle().setColor(TextFormatting.GRAY);
			playerIn.sendMessage(message);
	       	return EnumActionResult.SUCCESS;
		}
		return result;
	}
	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.isDead = false;
		entity.setEntityInvulnerable(true);
		entity.extinguish();
        return false;
    }
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
    }
    @Override
	public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }
}