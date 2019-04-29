package mod.amalgam.items;

import mod.amalgam.init.AmItems;
import mod.kagic.entity.EntityGem;
import mod.kagic.init.ModCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemDebugStaff extends Item {
	public ItemDebugStaff() {
		this.setUnlocalizedName("debug_staff");
		this.setMaxStackSize(1);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		MinecraftForge.EVENT_BUS.register(this);
	}
	@SubscribeEvent
	public void onEntityInteract(EntityInteract e) {
		if (!e.getWorld().isRemote) { 
			EntityPlayer p = e.getEntityPlayer();
			Entity entity = e.getTarget();
			if (e.getItemStack().getItem() == AmItems.DEBUG_STAFF) {
				boolean alternate = p.isSneaking(); p.swingArm(e.getHand());
				if (entity instanceof EntityGem) {
					EntityGem gem = (EntityGem)(entity);
					if (alternate) {
						this.say(p, "Shift functionality is reserved for a future update.");
					}
					else {
						this.say(p, "%s (%s) is an %s.", gem.getName(), gem.getSpecificName(), gem.getClass().getSimpleName());
						this.say(p, "Her persistent ID is \"%s.\"", gem.getGemId().toString().split("-")[0]);
						this.say(p, "Cut is %s, placement is %s.", gem.getGemCut(), gem.getGemPlacement());
						this.say(p, "Skin color is #%x, gem color is #%x.", gem.getSkinColor(), gem.getGemColor());
						this.say(p, "Hair color is #%x, hair style is %d.", gem.getHairColor(), gem.getHairStyle());
						this.say(p, "Insignia is %d, uniform is %d.", gem.getInsigniaColor(), gem.getUniformColor());
						switch (gem.getServitude()) {
						case EntityGem.SERVE_NONE:
							this.say(p, "She serves no specific master.");
							break;
						case EntityGem.SERVE_HOSTILE:
							this.say(p, "She serves no master and will fight for her autonomy.");
							break;
						case EntityGem.SERVE_ITSELF:
							this.say(p, "She serves herself and prefers the peace.");
							break;
						case EntityGem.SERVE_HUMAN:
							String master = "an unknown player.";
							try {
								master =  e.getWorld().getMinecraftServer().getServer().getPlayerProfileCache().getProfileByUUID(gem.getOwnerId()).getName();
							} catch (Exception x) { }
							this.say(p, "She serves %s.", master);
							break;
						case EntityGem.SERVE_WHITE_DIAMOND:
							this.say(p, "She serves White Diamond.");
							break;
						case EntityGem.SERVE_YELLOW_DIAMOND:
							this.say(p, "She serves Yellow Diamond.");
							break;
						case EntityGem.SERVE_BLUE_DIAMOND:
							this.say(p, "She serves Blue Diamond.");
							break;
						case EntityGem.SERVE_REBELLION:
							this.say(p, "She serves Pink Diamond.");
							break;
						}
						this.say(p, gem.isTraitor() ? "She is a rebel." : "She is not a rebel.");
						this.say(p, gem.isDefective() ? "She is defective." : (gem.isPrimary() ? "She is prime." : "She is normal."));
						this.say(p, "Health: %f / %f", gem.getHealth(), gem.getMaxHealth());
					}
				}
				else {
					this.say(p, "%s is an %s.", entity.getName(), entity.getClass().getSimpleName());
					if (entity instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase)(entity);
						this.say(p, "Health: %f / %f", living.getHealth(), living.getMaxHealth());
					}
				}
			}
		}
	}
	public void say(EntityPlayer p, String line, Object... args) {
		p.sendMessage(new TextComponentString(String.format(line, args)));
	}
}