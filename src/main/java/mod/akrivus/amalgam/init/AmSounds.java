package mod.akrivus.amalgam.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class AmSounds {
	public static final SoundEvent BUBBLE_BUBBLE = new SoundEvent(new ResourceLocation("amalgam:bubble.bubble"));
	public static final SoundEvent BUBBLE_POP = new SoundEvent(new ResourceLocation("amalgam:bubble.pop"));
	public static final SoundEvent BUBBLE_SEND = new SoundEvent(new ResourceLocation("amalgam:bubble.send"));
	
	public static final SoundEvent WAILING_STONE = new SoundEvent(new ResourceLocation("amalgam:wailing_stone"));
	
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
	
	public static void register(RegistryEvent.Register<SoundEvent> event) {
		registerSound(BUBBLE_BUBBLE, new ResourceLocation("amalgam:bubble.bubble"), event);
		registerSound(BUBBLE_POP, new ResourceLocation("amalgam:bubble.pop"), event);
		registerSound(BUBBLE_SEND, new ResourceLocation("amalgam:bubble.send"), event);
		
		registerSound(WAILING_STONE, new ResourceLocation("amalgam:wailing_stone"), event);
		
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
		
		registerSound(STEVONNIE_HURT, new ResourceLocation("amalgam:entities.stevonnie.hurt"), event);
		registerSound(STEVONNIE_DEATH, new ResourceLocation("amalgam:entities.stevonnie.death"), event);
		registerSound(STEVONNIE_LIVING, new ResourceLocation("amalgam:entities.stevonnie.living"), event);
	}
	private static void registerSound(SoundEvent sound, ResourceLocation location, RegistryEvent.Register<SoundEvent> event) {
		sound.setRegistryName(location);
		event.getRegistry().register(sound);
	}
}
