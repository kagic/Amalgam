package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelEmerald;
import mod.akrivus.amalgam.client.render.layers.LayerEmeraldItem;
import mod.akrivus.amalgam.gem.EntityEmerald;
import mod.kagic.client.render.RenderGemBase;
import mod.kagic.client.render.layers.LayerBirthdayHat;
import mod.kagic.client.render.layers.LayerGemPlacement;
import mod.kagic.client.render.layers.LayerHair;
import mod.kagic.client.render.layers.LayerInsignia;
import mod.kagic.client.render.layers.LayerSantaHat;
import mod.kagic.client.render.layers.LayerSkin;
import mod.kagic.client.render.layers.LayerUniform;
import mod.kagic.client.render.layers.LayerVisor;
import mod.kagic.client.render.layers.LayerWitchHat;
import mod.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderEmerald extends RenderGemBase<EntityEmerald> {
	public RenderEmerald() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelEmerald(), 0.5F);
		this.addLayer(new LayerEmeraldItem(this));
		this.addLayer(new LayerSkin(this));
		this.addLayer(new LayerHair(this));
		this.addLayer(new LayerVisor(this));
		this.addLayer(new LayerUniform(this));
		this.addLayer(new LayerInsignia(this));
		this.addLayer(new LayerGemPlacement(this));
	}
	@Override
	protected void preRenderCallback(EntityEmerald gem, float partialTicks) {
		if (gem.isDefective()) {
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
		}
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityEmerald entity) {
		return new ResourceLocation("amalgam:textures/entities/emerald/emerald.png");
	}
}
