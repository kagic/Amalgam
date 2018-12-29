package mod.akrivus.amalgam.init;

import java.util.Iterator;
import java.util.UUID;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntityFusedPyrite;
import mod.akrivus.amalgam.gem.EntityFusedRuby;
import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.amalgam.gem.ai.EntityAIBubbleItems;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.ai.EntityAIStayWithinRadius;
import mod.akrivus.amalgam.gem.tweaks.EntityAICrossFuse;
import mod.akrivus.amalgam.gem.tweaks.EntityAIFixAnvils;
import mod.akrivus.amalgam.gem.tweaks.EntityAIFixInjectors;
import mod.akrivus.amalgam.gem.tweaks.EntityAIFixPalanquins;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriAlignGems;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriPartyBurnStuff;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriPartyDance;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriPartyFireworks;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIAlignGems;
import mod.akrivus.kagic.entity.ai.EntityAIProtectionFuse;
import mod.akrivus.kagic.entity.ai.EntityAIRubyFuse;
import mod.akrivus.kagic.entity.ai.EntityAITopazFuse;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.entity.gem.EntityQuartzSoldier;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.entity.gem.fusion.EntityGarnet;
import mod.akrivus.kagic.entity.gem.fusion.EntityMalachite;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import mod.akrivus.kagic.entity.gem.fusion.EntityRainbowQuartz;
import mod.akrivus.kagic.entity.gem.fusion.EntityRhodonite;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class AmTweaks {
	public static class Aquamarine extends Gem<EntityAquamarine> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("c61d7b46-3f22-48cc-8838-5c69724f622f"), "amNerfHealth",  -30, 0);
		protected static void tweak(EntityAquamarine gem) {
			apply(gem);
		}
		protected static void apply(EntityAquamarine gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Bismuth extends Gem<EntityBismuth> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("f8f7e3b1-8bff-4b86-9f79-90b165da0c66"), "amNerfHealth", -140, 0);
		protected static void tweak(EntityBismuth gem) {
			gem.tasks.addTask(4, new EntityAIFixPalanquins(gem, 0.6D));
			gem.tasks.addTask(4, new EntityAIFixAnvils(gem, 0.6D));
			apply(gem);
		}
		protected static void apply(EntityBismuth gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Hessonite extends Gem<EntityHessonite> {
		private static final AttributeModifier BUFF_DAMAGE = new AttributeModifier(UUID.fromString("e69af949-93de-4b6d-be97-fd6c3f7aafba"), "amBuffDamage",   12, 0);
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("91f0a1cb-5445-4679-8027-837e3f667e9d"), "amNerfHealth",  -20, 0);
		protected static void tweak(EntityHessonite gem) {
			gem.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityGem>(gem, EntityGem.class, 10, true, false, new Predicate<EntityGem>() {
	            @Override
				public boolean apply(EntityGem input) {
	                return input != null && (input.isDefective() || input.isTraitor());
	            }
	        }));
			apply(gem);
		}
		protected static void apply(EntityHessonite gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(BUFF_DAMAGE);
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class LapisLazuli extends Gem<EntityLapisLazuli> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("b03e2fa4-fdf7-44c4-b58a-f88eedcb4d01"), "amNerfHealth",  -60, 0);
		protected static void tweak(EntityLapisLazuli gem) {
			apply(gem);
		}
		protected static void apply(EntityLapisLazuli gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Pearl extends Gem<EntityPearl> {
		private static final AttributeModifier BUFF_HEALTH = new AttributeModifier(UUID.fromString("dfeb118c-16b3-4766-adb5-d4208acb661f"), "amBuffHealth",   20, 0);
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("0edfaca3-8517-4543-9523-faa88d576380"), "amNerfHealth",  -30, 0);
		protected static void tweak(EntityPearl gem) {
			apply(gem);
		}
		protected static void apply(EntityPearl gem) {
			try {
				if (gem.isDefective()) {
					gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(BUFF_HEALTH);
				}
				else {
					gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
				}
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
		}
	}
	public static class Peridot extends Gem<EntityPeridot> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("ca9e0ad5-50df-4f7d-8765-8b86350ef726"), "amNerfHealth", -100, 0);
		protected static void tweak(EntityPeridot gem) {
			Iterator<EntityAITaskEntry> tasks = gem.tasks.taskEntries.iterator();
			while (tasks.hasNext()) {
				EntityAIBase ai = tasks.next().action;
				if (ai instanceof EntityAIAlignGems) {
					tasks.remove();
				}
			}
			gem.tasks.addTask(4, new EntityAIPeriAlignGems(gem, 0.9D));
			gem.tasks.addTask(4, new EntityAIFixInjectors(gem, 0.6D));
			gem.tasks.addTask(6, new EntityAIPeriPartyBurnStuff(gem, 0.6D));
			gem.tasks.addTask(6, new EntityAIPeriPartyDance(gem));
			gem.tasks.addTask(6, new EntityAIPeriPartyFireworks(gem));
			apply(gem);
		}
		protected static void apply(EntityPeridot gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Quartz extends Gem<EntityQuartzSoldier> {
		private static final AttributeModifier NERF_DAMAGE = new AttributeModifier(UUID.fromString("a936d8ae-5c3d-4334-ba0e-84b9f8e23bd3"), "amNerfDamage",   -4, 0);
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("5635dcf0-500b-42fe-abc4-6c97582fed94"), "amNerfHealth",  -90, 0);
		protected static void tweak(EntityQuartzSoldier gem) {
			apply(gem);
		}
		protected static void apply(EntityQuartzSoldier gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(NERF_DAMAGE);
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Ruby extends Gem<EntityRuby> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("13c28c04-36d0-48ae-b269-46d008707b31"), "amNerfHealth",  -60, 0);
		protected static void tweak(EntityRuby gem) {
			if (AmConfigs.socializeRubies) {
				gem.tasks.addTask(4, new EntityAIFollowLeaderGem(gem, 0.8D, GemPlacements.NOSE, EntityJasper.class));
				gem.tasks.addTask(4, new EntityAIFollowLeaderGem(gem, 0.8D, GemPlacements.CHEST, EntityPyrite.class));
				gem.tasks.addTask(4, new EntityAIFollowLeaderGem(gem, 0.8D, GemPlacements.CHEST, EntityRuby.class));
				gem.tasks.addTask(4, new EntityAIFollowOtherGem(gem, 0.8D, EntityBabyPearl.class));
				gem.tasks.addTask(4, new EntityAIFollowOtherGem(gem, 0.8D, EntityFusedRuby.class));
				gem.tasks.addTask(4, new EntityAIFollowOtherGem(gem, 0.8D, EntityFusedPyrite.class));
				gem.targetTasks.addTask(2, new EntityAICallForBackup(gem, EntityRuby.class));
			}
			apply(gem);
		}
		protected static void apply(EntityRuby gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Rutile extends Gem<EntityRutile> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("f3341645-9ac4-4861-a035-c1b49840237a"), "amNerfHealth",  -30, 0);
		protected static void tweak(EntityRutile gem) {
			apply(gem);
		}
		protected static void apply(EntityRutile gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Sapphire extends Gem<EntitySapphire> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("c2ca3f40-e98f-4208-8e11-ffcd8bd7c35d"), "amNerfHealth",  -30, 0);
		protected static void tweak(EntitySapphire gem) {
			apply(gem);
		}
		protected static void apply(EntitySapphire gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Topaz extends Gem<EntityTopaz> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("b6895006-fffa-48c7-a784-bb77f7df4f71"), "amNerfHealth",  -60, 0);
		protected static void tweak(EntityTopaz gem) {
			apply(gem);
		}
		protected static void apply(EntityTopaz gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Zircon extends Gem<EntityZircon> {
		private static final AttributeModifier NERF_HEALTH = new AttributeModifier(UUID.fromString("4c4bd2cd-ff33-4bb1-8b1b-e829322d3b38"), "amNerfHealth",  -30, 0);
		protected static void tweak(EntityZircon gem) {
			gem.tasks.addTask(1, new EntityAIBubbleItems(gem, 0.6D));
			apply(gem);
		}
		protected static void apply(EntityZircon gem) {
			try {
				gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(NERF_HEALTH);
			} catch (IllegalArgumentException e) {
				System.out.println(gem.getName() + " has already been tweaked!");
			}
			if (gem.getHealth() > gem.getMaxHealth()) {
				gem.setHealth(gem.getMaxHealth());
			}
		}
	}
	public static class Gem<T extends EntityGem> {
		public static void applyWailingStoneTweaks(EntityGem gem) {
			gem.tasks.addTask(1, new EntityAIStayWithinRadius(gem, 0.8D));
		}
		public static void applyRenameTweaks(EntityGem gem) {
			boolean tweak = gem.getGemCut() == GemCuts.CABOCHON;
			if (!tweak) {
				if (gem instanceof EntityLapisLazuli || gem instanceof EntityAquamarine) {
					tweak = true;
				}
				else if (gem instanceof EntityJasper && !gem.isPrimary() && gem.getSpecial() == 0) {
					tweak = true;
				}
			}
			if (tweak) {
				gem.setSpecificName(gem.getSpecificName().replace("Cut", "Cabochon"));
			}
		}
		public static void applyFusionTweaks(EntityGem gem) {
			if (gem instanceof EntityAmethyst || gem instanceof EntityJasper
			 || gem instanceof EntityRoseQuartz || gem instanceof EntityRuby
			 || gem instanceof EntityTopaz) {
				Iterator<EntityAITaskEntry> tasks = gem.tasks.taskEntries.iterator();
				while (tasks.hasNext()) {
					EntityAIBase ai = tasks.next().action;
					if (ai instanceof EntityAIProtectionFuse || ai instanceof EntityAITopazFuse || ai instanceof EntityAIRubyFuse) {
						tasks.remove();
					}
				}
				if (gem instanceof EntityAmethyst) {
					gem.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityOpal>(gem, EntityPearl.class, EntityOpal.class, 16));
				}
				else if (gem instanceof EntityJasper) {
					gem.tasks.addTask(3, new EntityAICrossFuse<EntityLapisLazuli, EntityMalachite>(gem, EntityLapisLazuli.class, EntityMalachite.class, 16));
				}
				else if (gem instanceof EntityRoseQuartz) {
					gem.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityRainbowQuartz>(gem, EntityPearl.class, EntityRainbowQuartz.class, 16));
				}
				else if (gem instanceof EntityRuby) {
					gem.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityRhodonite>(gem, EntityPearl.class, EntityRhodonite.class, 16));
					gem.tasks.addTask(3, new EntityAICrossFuse<EntitySapphire, EntityGarnet>(gem, EntitySapphire.class, EntityGarnet.class, 16));
					gem.tasks.addTask(3, new EntityAICrossFuse<EntityRuby, EntityFusedRuby>(gem, EntityRuby.class, EntityFusedRuby.class, 16));
				}
				else if (gem instanceof EntityTopaz) {
					gem.tasks.addTask(3, new EntityAICrossFuse<EntityTopaz, EntityFusedTopaz>(gem, EntityTopaz.class, EntityFusedTopaz.class, 16));
				}
			}
		}
		public static void applyGemTweaks(EntityGem gem) {
			if (gem instanceof EntityAquamarine) {
				AmTweaks.Aquamarine.tweak((EntityAquamarine)(gem));
				return;
			}
			if (gem instanceof EntityBismuth) {
				AmTweaks.Bismuth.tweak((EntityBismuth)(gem));
				return;
			}
			if (gem instanceof EntityHessonite) {
				AmTweaks.Hessonite.tweak((EntityHessonite)(gem));
				return;
			}
			if (gem instanceof EntityLapisLazuli) {
				AmTweaks.LapisLazuli.tweak((EntityLapisLazuli)(gem));
				return;
			}
			if (gem instanceof EntityPearl) {
				AmTweaks.Pearl.tweak((EntityPearl)(gem));
				return;
			}
			if (gem instanceof EntityPeridot) {
				AmTweaks.Peridot.tweak((EntityPeridot)(gem));
				return;
			}
			if (gem instanceof EntityQuartzSoldier) {
				AmTweaks.Quartz.tweak((EntityQuartzSoldier)(gem));
				return;
			}
			if (gem instanceof EntityRuby) {
				AmTweaks.Ruby.tweak((EntityRuby)(gem));
				return;
			}
			if (gem instanceof EntityRutile) {
				AmTweaks.Rutile.tweak((EntityRutile)(gem));
				return;
			}
			if (gem instanceof EntitySapphire) {
				AmTweaks.Sapphire.tweak((EntitySapphire)(gem));
				return;
			}
			if (gem instanceof EntityTopaz) {
				AmTweaks.Topaz.tweak((EntityTopaz)(gem));
				return;
			}
			if (gem instanceof EntityZircon) {
				AmTweaks.Zircon.tweak((EntityZircon)(gem));
				return;
			}
		}
	}
}
