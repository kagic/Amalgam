package mod.akrivus.amalgam.gem;

import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityBabyPearl extends EntityPearl {
	public EntityBabyPearl(World worldIn) {
		super(worldIn);
	}
	public int getMaxInventorySlots() {
		int slots = 27;
		if (this.getGemPlacement() == GemPlacements.FOREHEAD) {
			slots += 9;
		}
		return slots;
	}
	protected SoundEvent getLivingSound() {
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.BABY_PEARL_OH;
	}
	protected SoundEvent getWeirdSound() {
		return AmSounds.BABY_PEARL_OH;
	}
}
