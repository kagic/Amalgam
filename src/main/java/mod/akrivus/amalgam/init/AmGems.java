package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.entity.EntityGemShard;
import mod.akrivus.amalgam.entity.EntitySpitball;
import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntityCitrine;
import mod.akrivus.amalgam.gem.EntityConnie;
import mod.akrivus.amalgam.gem.EntityEmerald;
import mod.akrivus.amalgam.gem.EntityEnderPearl;
import mod.akrivus.amalgam.gem.EntityFusedPyrite;
import mod.akrivus.amalgam.gem.EntityFusedRuby;
import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import mod.akrivus.amalgam.gem.EntityNacre;
import mod.akrivus.amalgam.gem.EntityNephrite;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.EntityStevonnie;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.ModEntities;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class AmGems {
	private static int currentID = 0;
	public static void register() {
		ModEntities.registerExternalGem("amalgam", "citrine", EntityCitrine.class, "mod/akrivus/amalgam/client/render/RenderCitrine", 0xECF404, 0xEBFD64, false);
		ModEntities.registerExternalGem("amalgam", "pyrite", EntityPyrite.class, "mod/akrivus/amalgam/client/render/RenderPyrite", 0xEADC73, 0x71512B, false);
		ModEntities.registerExternalGem("amalgam", "ender_pearl", EntityEnderPearl.class, "mod/akrivus/amalgam/client/render/RenderEnderPearl", 0x000000, 0xFF00FF, false);
		ModEntities.registerExternalGem("amalgam", "nacre", EntityNacre.class, "mod/akrivus/amalgam/client/render/RenderNacre", 0xEDEFF4, 0xFFCF96, false);
		ModEntities.registerExternalGem("amalgam", "baby_pearl", EntityBabyPearl.class, "mod/akrivus/amalgam/client/render/RenderBabyPearl", 0xFCCCB1, 0x92EAD9, false);
		ModEntities.registerExternalGem("amalgam", "nephrite", EntityNephrite.class, "mod/akrivus/amalgam/client/render/RenderNephrite", 0x428F33, 0x0E0D09, false);
		ModEntities.registerExternalGem("amalgam", "emerald", EntityEmerald.class, "mod/akrivus/amalgam/client/render/RenderEmerald", 0x2AC861, 0xB2F731, false);
		
		registerFusion("fused_topaz", EntityFusedTopaz.class);
		registerFusion("fused_ruby", EntityFusedRuby.class);
		registerFusion("fused_pyrite", EntityFusedPyrite.class);
		registerFusion("fused_ruby", EntityFusedRuby.class);
		
		registerMob("gem_shard", EntityGemShard.class, 0x9AA4AF, 0xDDDDDD);
		registerMob("steven", EntitySteven.class, 0xFD6270, 0xFFD248);
		registerMob("connie", EntityConnie.class, 0x99D3CD, 0xAF4E3D);
		registerEntity("stevonnie", EntityStevonnie.class);
		registerEntity("spitball", EntitySpitball.class);
		
		//if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
		//	RenderingRegistry.registerEntityRenderingHandler(EntityHessonite.class, new RenderHessonite());
		//}
	}
	public static <T extends Entity> void registerMob(String name, Class<T> entity, int back, int fore) {
		EntityRegistry.registerModEntity(new ResourceLocation("amalgam:" + name), entity, name, currentID, Amalgam.instance, 256, 1, true, back, fore);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) Amalgam.class.getClassLoader().loadClass("mod/akrivus/amalgam/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		++currentID;
	}
	public static <T extends Entity> void registerEntity(String name, Class<T> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("amalgam:" + name), entity, name, currentID, Amalgam.instance, 256, 1, true);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) Amalgam.class.getClassLoader().loadClass("mod/akrivus/amalgam/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		++currentID;
	}
	public static <T extends Entity> void registerFusion(String name, Class<? extends EntityGem> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("amalgam:" + name), entity, name, currentID, Amalgam.instance, 256, 1, true);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends EntityGem>> render = (Class<Render<? extends EntityGem>>) Amalgam.class.getClassLoader().loadClass("mod/akrivus/amalgam/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		ModEntities.GEMS.put(name, entity);
		++currentID;
	}
}
