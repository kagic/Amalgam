package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelEmerald;
import mod.akrivus.amalgam.gem.EntityEmerald;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderEmerald extends RenderGemBase<EntityEmerald> {
	public RenderEmerald() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelEmerald(), 0.5F);
		//this.addLayer(new LayerQuartzItem(this));
		//this.addLayer(new LayerSkin(this));
		//this.addLayer(new LayerHair(this));
		//this.addLayer(new LayerUniform(this));
		//this.addLayer(new LayerInsignia(this));
		//this.addLayer(new LayerQuartzCape(this));
		//this.addLayer(new LayerQuartzCape(this, true, true));
		//this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		}
		else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		}
		else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
	}
	@Override
	protected void preRenderCallback(EntityEmerald gem, float partialTickTime) {
		if (gem.isDefective()) {
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
		}
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityEmerald entity) {
		return new ResourceLocation("amalgam:textures/entities/emerald/emerald.png");
	}
}
