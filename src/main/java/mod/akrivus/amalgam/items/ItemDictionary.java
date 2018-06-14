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
        nbt.setString("author", "a Peridot");
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < PAGES.length; ++i) {
        	list.appendTag(new NBTTagString(PAGES[i]));
        }
        nbt.setTag("pages", list);
		stack.setTagCompound(nbt);
    }
	public final static String[] PAGES = new String[] {
		"Official\n§nKindergartener's§r\nDictionary§r",
		"Long ago on this world called Redemption, a balance was made between DARKNESS and MYSTERY, two combined, yet opposing forces of nature. When our sisters invaded this planet to expand the empire, the dark and mysterious forces of this world united to",
		"quell us, but with the help of our Luminous, Righteous, and Lustrous Diamond, we emerged victorious. I wrote this document to commemorate our victory and instruct others on how to reap the offerings of this planet we righteously fought for and own.",
		"Redemption is a Class J planet that orbits about 152 million units from its home star and harbors 1 natural satellite that was created in a catastrophic volcanic eruption during the planet's infancy. We can decipher it's origin by examining",
		"geological signatures, which are about 90% identical between Redemption and its moon. About 80% of Redemption is made out of felsic rhyolite, pinpointing a violent, and volcanic origin to Redemption and making it EXTREMELY ideal for growing Quartzes.",
		"The extrusive, non-mafic crust of Redemption makes a very quick transition into a hot, pressurized and ultra-mafic mantle consisting of basalt, schist, andesite, and peridotite. The quick transition is a result of different compositional densities due to",
		"the sudden inclusion of iron rich minerals, making deep crust operations ideal for harvesting Peridots. Redemption truly is a world of wonders, as it also encompasses multiple biospheres that have adapted to a wide array of temperatures and conditions.",
		"Redemption's asthenosphere is blocked by a nearly impenetrable layer of dark rock, but when penetrated reveals an almost solid metal core. It's components are similar to the lower levels of the lithosphere with a higher concentration of metals. Deeper",
		"into the asthenosphere, pressure increases to about 360 gigapascals and over 9800 degrees centigrade. Convection currents from the liquid iron outer core create movement between tectonic plates that split Redemption's crust into multiple pieces and",
		"prevent it from exploding from bottled pressure and volcanic activity. In the inner core, the very center of the planet, higher density materials solidify and become so hot that radioactive ores such as uranium and plutonium react and undergo advanced",
		"states of decay, continuously fueling the planet's heat cycles and activating an impressive magnetic field that protects the life harbored on the planet from ultraviolet rays. Speaking of life, it's inclusion has made an outstanding change to the",
		"planet, utilizing oxygen from photosynthetic reactions to completely change the atmosphere and allow for liquid water oceans to form. As a matter of fact, sediment samples as far back as billions of years of geological strata have turned up",
		"microorganisms capable of single celled reproduction. Previous escapades with life on this planet have shown that many are capable of abstract thought, and few were even capable of communicating with gems and demonstrating strategic victories over elite",
		"strike squads. Thankfully, such resurgences were quickly quelled by the empire, but their effectiveness is impressive for an organic being.\r\n" + 
		"\r\n" + 
		"Let's begin, shall we?",
		"Cut: the designation for a single kindergarten.\r\n" + 
		"\r\n" + 
		"Culet: the blanket designation for a row of gems.\r\n" + 
		"\r\n" + 
		"Facet: the designation for a column of gems.\r\n" + 
		"\r\n" + 
		"Scint: the collective noun for a group of the same gems.",
		"Brill: the collective noun for a group of different gems.\r\n" + 
		"\r\n" + 
		"Barbie: gems that have a tenacity to grow to the bottom of the crust.\r\n" + 
		"\r\n" + 
		"Surfie: gems that have a tenacity to grow to the surface.\r\n" + 
		"\r\n" + 
		"Cagie: gems that don't create exit holes.",
		"Off-Color: gems that don't appear normal are off-color.\r\n" + 
		"\r\n" + 
		"Defective: gems that don't act normal are defective.\r\n" + 
		"\r\n" + 
		"Primary: gems that melt rock when coming out are primary.\r\n" + 
		"\r\n" + 
		"Slag: gems that don't have enough resources to form are slag.",
		"Crux: minerals that contribute to gem development.\r\n" + 
		"\r\n" + 
		"Synth: synthetic cruxes, guarantee a gem's growth.\r\n" + 
		"\r\n" + 
		"Part: short for partiative, mid-range cruxes.\r\n" + 
		"\r\n" + 
		"Residues: low-range cruxes; arbitrary interferences.",
		"Depth: measure of \"barbieness\" or surface-vs-bottom growth.\r\n" + 
		"\r\n" + 
		"Density: the amount of cruxes in the search range.\r\n" + 
		"\r\n" + 
		"Yield: the average number of gems per facet.\r\n" + 
		"\r\n" + 
		"Adaptation: natural changes made to the gem to accommodate their point of birth.",
		"Bucking: filling exit holes to extract all possible resources from an environment.\r\n" + 
		"\r\n" + 
		"Drainage: residual material left behind from previous kindergartening.\r\n" + 
		"\r\n" + 
		"Burning: destroying drainage to leave room for resources.\r\n" + 
		"\r\n" + 
		"Arable: high density areas; make it easy to grow gems.",
		"Pitching: when an exit hole leads to another exit hole.\r\n" + 
		"\r\n" + 
		"Hooking: when an exit hole goes the wrong way.\r\n" + 
		"\r\n" + 
		"Marking: when a torch is used to direct exit holes.\r\n" + 
		"\r\n" + 
		"Clamming: when an exit holes goes into a place that kills the gem.",
		"Clod: archaic term meaning defective/meaningless.\r\n" + 
		"\r\n" + 
		"Dull: archaic term meaning off-color/unsightly.\r\n" + 
		"\r\n" + 
		"Lump: archaic term meaning slag/unwanted."
	};
}
