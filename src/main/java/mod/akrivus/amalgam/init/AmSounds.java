package mod.akrivus.amalgam.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class AmSounds {
	public static final SoundEvent PYRITE_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.pyrite.hurt"));
	public static final SoundEvent PYRITE_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.pyrite.obey"));
	public static final SoundEvent PYRITE_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.pyrite.death"));
	
	public static final SoundEvent ENDER_PEARL_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.hurt"));
	public static final SoundEvent ENDER_PEARL_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.obey"));
	public static final SoundEvent ENDER_PEARL_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.death"));
	public static final SoundEvent ENDER_PEARL_SING = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.sing"));
	public static final SoundEvent ENDER_PEARL_WEIRD = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.weird"));
	
	public static void register(RegistryEvent.Register<SoundEvent> event) {
		registerSound(PYRITE_HURT, new ResourceLocation("amalgam:entities.pyrite.hurt"), event);
		registerSound(PYRITE_OBEY, new ResourceLocation("amalgam:entities.pyrite.obey"), event);
		registerSound(PYRITE_DEATH, new ResourceLocation("amalgam:entities.pyrite.death"), event);
		registerSound(ENDER_PEARL_HURT, new ResourceLocation("amalgam:entities.ender_pearl.hurt"), event);
		registerSound(ENDER_PEARL_OBEY, new ResourceLocation("amalgam:entities.ender_pearl.obey"), event);
		registerSound(ENDER_PEARL_DEATH, new ResourceLocation("amalgam:entities.ender_pearl.death"), event);
		registerSound(ENDER_PEARL_SING, new ResourceLocation("amalgam:entities.ender_pearl.sing"), event);
		registerSound(ENDER_PEARL_WEIRD, new ResourceLocation("amalgam:entities.ender_pearl.weird"), event);
	}
	private static void registerSound(SoundEvent sound, ResourceLocation location, RegistryEvent.Register<SoundEvent> event) {
		sound.setRegistryName(location);
		event.getRegistry().register(sound);
	}
}
