package mod.akrivus.amalgam.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class AmSounds {
	public static final SoundEvent RECORD_LOVE_LIKE_YOU = new SoundEvent(new ResourceLocation("amalgam:records.love_like_you"));
	
	public static final SoundEvent PYRITE_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.pyrite.hurt"));
	public static final SoundEvent PYRITE_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.pyrite.obey"));
	public static final SoundEvent PYRITE_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.pyrite.death"));
	
	public static final SoundEvent ENDER_PEARL_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.hurt"));
	public static final SoundEvent ENDER_PEARL_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.obey"));
	public static final SoundEvent ENDER_PEARL_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.death"));
	public static final SoundEvent ENDER_PEARL_SING = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.sing"));
	public static final SoundEvent ENDER_PEARL_WEIRD = new SoundEvent(new ResourceLocation("amalgam:entities.ender_pearl.weird"));
	
	public static final SoundEvent NACRE_SNEEZE = new SoundEvent(new ResourceLocation("amalgam:entities.nacre.sneeze"));
	public static final SoundEvent NACRE_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.nacre.hurt"));
	public static final SoundEvent NACRE_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.nacre.obey"));
	public static final SoundEvent NACRE_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.nacre.death"));
	
	public static final SoundEvent BABY_PEARL_OH = new SoundEvent(new ResourceLocation("amalgam:entities.baby_pearl.oh"));
	public static final SoundEvent BABY_PEARL_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.baby_pearl.obey"));
	
	public static final SoundEvent NEPHRITE_INJURE = new SoundEvent(new ResourceLocation("amalgam:entities.nephrite.injure"));
	public static final SoundEvent NEPHRITE_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.nephrite.obey"));
	public static final SoundEvent NEPHRITE_LIVING = new SoundEvent(new ResourceLocation("amalgam:entities.nephrite.living"));
	
	public static final SoundEvent STEVEN_HELLO = new SoundEvent(new ResourceLocation("amalgam:entities.steven.hello"));
	public static final SoundEvent STEVEN_LIVING = new SoundEvent(new ResourceLocation("amalgam:entities.steven.living"));
	public static final SoundEvent STEVEN_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.steven.hurt"));
	public static final SoundEvent STEVEN_PROTECT = new SoundEvent(new ResourceLocation("amalgam:entities.steven.protect"));
	public static final SoundEvent STEVEN_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.steven.death"));
	public static final SoundEvent STEVEN_SNEEZE = new SoundEvent(new ResourceLocation("amalgam:entities.steven.sneeze"));
	
	public static final SoundEvent CONNIE_HELLO = new SoundEvent(new ResourceLocation("amalgam:entities.connie.hello"));
	public static final SoundEvent CONNIE_LIVING = new SoundEvent(new ResourceLocation("amalgam:entities.connie.living"));
	public static final SoundEvent CONNIE_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.connie.hurt"));
	public static final SoundEvent CONNIE_PROTECT = new SoundEvent(new ResourceLocation("amalgam:entities.connie.protect"));
	public static final SoundEvent CONNIE_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.connie.death"));
	
	public static final SoundEvent STEVONNIE_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.stevonnie.hurt"));
	public static final SoundEvent STEVONNIE_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.stevonnie.death"));
	public static final SoundEvent STEVONNIE_LIVING = new SoundEvent(new ResourceLocation("amalgam:entities.stevonnie.living"));
	
	public static final SoundEvent EMERALD_HURT = new SoundEvent(new ResourceLocation("amalgam:entities.emerald.hurt"));
	public static final SoundEvent EMERALD_DEATH = new SoundEvent(new ResourceLocation("amalgam:entities.emerald.death"));
	public static final SoundEvent EMERALD_OBEY = new SoundEvent(new ResourceLocation("amalgam:entities.emerald.obey"));
	public static final SoundEvent EMERALD_LIVING = new SoundEvent(new ResourceLocation("amalgam:entities.emerald.living"));
	
	public static void register(RegistryEvent.Register<SoundEvent> event) {
		registerSound(RECORD_LOVE_LIKE_YOU, new ResourceLocation("amalgam:records.love_like_you"), event);

		registerSound(PYRITE_HURT, new ResourceLocation("amalgam:entities.pyrite.hurt"), event);
		registerSound(PYRITE_OBEY, new ResourceLocation("amalgam:entities.pyrite.obey"), event);
		registerSound(PYRITE_DEATH, new ResourceLocation("amalgam:entities.pyrite.death"), event);
		
		registerSound(ENDER_PEARL_HURT, new ResourceLocation("amalgam:entities.ender_pearl.hurt"), event);
		registerSound(ENDER_PEARL_OBEY, new ResourceLocation("amalgam:entities.ender_pearl.obey"), event);
		registerSound(ENDER_PEARL_DEATH, new ResourceLocation("amalgam:entities.ender_pearl.death"), event);
		registerSound(ENDER_PEARL_SING, new ResourceLocation("amalgam:entities.ender_pearl.sing"), event);
		registerSound(ENDER_PEARL_WEIRD, new ResourceLocation("amalgam:entities.ender_pearl.weird"), event);
	
		registerSound(NACRE_SNEEZE, new ResourceLocation("amalgam:entities.nacre.sneeze"), event);
		registerSound(NACRE_HURT, new ResourceLocation("amalgam:entities.nacre.hurt"), event);
		registerSound(NACRE_OBEY, new ResourceLocation("amalgam:entities.nacre.obey"), event);
		registerSound(NACRE_DEATH, new ResourceLocation("amalgam:entities.nacre.death"), event);
		
		registerSound(BABY_PEARL_OH, new ResourceLocation("amalgam:entities.baby_pearl.oh"), event);
		registerSound(BABY_PEARL_OBEY, new ResourceLocation("amalgam:entities.baby_pearl.obey"), event);
		
		registerSound(NEPHRITE_INJURE, new ResourceLocation("amalgam:entities.nephrite.injure"), event);
		registerSound(NEPHRITE_OBEY, new ResourceLocation("amalgam:entities.nephrite.obey"), event);
		registerSound(NEPHRITE_LIVING, new ResourceLocation("amalgam:entities.nephrite.living"), event);
		
		registerSound(STEVEN_HELLO, new ResourceLocation("amalgam:entities.steven.hello"), event);
		registerSound(STEVEN_LIVING, new ResourceLocation("amalgam:entities.steven.living"), event);
		registerSound(STEVEN_HURT, new ResourceLocation("amalgam:entities.steven.hurt"), event);
		registerSound(STEVEN_PROTECT, new ResourceLocation("amalgam:entities.steven.protect"), event);
		registerSound(STEVEN_DEATH, new ResourceLocation("amalgam:entities.steven.death"), event);
		registerSound(STEVEN_SNEEZE, new ResourceLocation("amalgam:entities.steven.sneeze"), event);
		
		registerSound(CONNIE_HELLO, new ResourceLocation("amalgam:entities.connie.hello"), event);
		registerSound(CONNIE_LIVING, new ResourceLocation("amalgam:entities.connie.living"), event);
		registerSound(CONNIE_HURT, new ResourceLocation("amalgam:entities.connie.hurt"), event);
		registerSound(CONNIE_PROTECT, new ResourceLocation("amalgam:entities.connie.protect"), event);
		registerSound(CONNIE_DEATH, new ResourceLocation("amalgam:entities.connie.death"), event);
		
		registerSound(EMERALD_HURT, new ResourceLocation("amalgam:entities.emerald.hurt"), event);
		registerSound(EMERALD_DEATH, new ResourceLocation("amalgam:entities.emerald.death"), event);
		registerSound(EMERALD_OBEY, new ResourceLocation("amalgam:entities.emerald.obey"), event);
		registerSound(EMERALD_LIVING, new ResourceLocation("amalgam:entities.emerald.living"), event);
		
		registerSound(STEVONNIE_HURT, new ResourceLocation("amalgam:entities.stevonnie.hurt"), event);
		registerSound(STEVONNIE_DEATH, new ResourceLocation("amalgam:entities.stevonnie.death"), event);
		registerSound(STEVONNIE_LIVING, new ResourceLocation("amalgam:entities.stevonnie.living"), event);
	}
	private static void registerSound(SoundEvent sound, ResourceLocation location, RegistryEvent.Register<SoundEvent> event) {
		sound.setRegistryName(location);
		event.getRegistry().register(sound);
	}
}
