package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.render.layers.LayerPyritePlacement;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.kagic.client.model.ModelRuby;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderPyrite extends RenderGemBase<EntityPyrite> {
	public RenderPyrite() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelRuby(), 0.25F);
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerGemPlacement(this));
        this.addLayer(new LayerPyritePlacement(this));
    }
	protected void preRenderCallback(EntityPyrite gem, float partialTickTime) {
		if (gem.isFusion()) {
			GlStateManager.scale(gem.getFusionCount(), gem.getFusionCount(), gem.getFusionCount());
		}
		else if (gem.isDefective()) {
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
		}
	}
	protected ResourceLocation getEntityTexture(EntityPyrite entity) {
		return new ResourceLocation("amalgam:textures/entities/pyrite/pyrite.png");
	}
}