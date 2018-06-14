package mod.akrivus.amalgam.items;

import mod.akrivus.amalgam.init.Amalgam;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDictionary extends ItemWrittenBook {
	public ItemDictionary() {
		this.setUnlocalizedName("dictionary");
        this.setMaxStackSize(1);
    }
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.openGui(Amalgam.instance, 0, playerIn.world, (int)(playerIn.posX), (int)(playerIn.posY), (int)(playerIn.posZ));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("title", "Kindergartening for Clods");
        nbt.setString("author", "Peridot");
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < PAGES.length; ++i) {
        	list.appendTag(new NBTTagString(PAGES[i]));
        }
        nbt.setTag("pages", list);
		stack.setTagCompound(nbt);
    }
	public final static String[] PAGES = new String[] { };
}
