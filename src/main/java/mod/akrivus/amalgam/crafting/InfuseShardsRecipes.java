package mod.akrivus.amalgam.crafting;

import mod.akrivus.amalgam.enchant.EnchantShard;
import mod.akrivus.amalgam.items.ItemGemShard;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfuseShardsRecipes implements IRecipe {
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack gem = ItemStack.EMPTY;
		ItemStack item = ItemStack.EMPTY;
		for (int slot = 0; slot < inventory.getSizeInventory(); ++slot) {
			if (inventory.getStackInSlot(slot).getItem() instanceof ItemGemShard) {
				gem = inventory.getStackInSlot(slot);
			}
			else {
				item = inventory.getStackInSlot(slot);
			}
			if (!gem.isEmpty() && !item.isEmpty()) {
				break;
			}
		}
		if (!gem.isEmpty() && !item.isEmpty()) {
			NBTTagList enchantments = item.getEnchantmentTagList();
			for (int i = 0; i < enchantments.tagCount(); i++) {
				if (Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")) instanceof EnchantShard) {
					return ItemStack.EMPTY;
				}
			}
			item.addEnchantment(EnchantShard.ENCHANTS.get(gem.getUnlocalizedName().replaceAll("item\\.", "")), 1);
			gem.shrink(1);
			return item;
		}
        return item;
    }
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean hasGem = true;
		for (int y = 0; y < inv.getHeight(); ++y) {
            for (int x = 0; x < inv.getWidth(); ++x) {
                ItemStack itemstack = inv.getStackInRowAndColumn(x, y);
                if (!itemstack.isEmpty()) {
                    if (itemstack.getItem() instanceof ItemGemShard) {
                    	hasGem = true;
                    }
                    else if (hasGem) {
                    	return true;
                    }
                }
            }
        }
        return false;
	}
	public int getRecipeSize() {
		return 2;
	}
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }
        return nonnulllist;
	}
	public IRecipe setRegistryName(ResourceLocation name) {
		return this;
	}
	public ResourceLocation getRegistryName() {
		return new ResourceLocation("amalgam:infuse_shards");
	}
	public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}
	@Override
	public boolean canFit(int width, int height) {
		return true;
	}
}