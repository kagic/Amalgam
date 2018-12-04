package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.gem.EntityFusedRuby;
import mod.akrivus.kagic.client.model.ModelRuby;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerCrossFusionGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderFusedRuby extends RenderGemBase<EntityFusedRuby> {
	public RenderFusedRuby() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelRuby(), 0.25F);
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerHair(this));
		this.addLayer(new LayerCrossFusionGemPlacement(this));
    }
	@Override
	protected void preRenderCallback(EntityFusedRuby gem, float partialTickTime) {
		GlStateManager.scale(0.8F * gem.getFusionCount(), 0.8F * gem.getFusionCount(), 0.8F * gem.getFusionCount());
		this.shadowSize = 0.25F * gem.getFusionCount();
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityFusedRuby entity) {
		return new ResourceLocation("amalgam:textures/entities/fused_ruby/ruby.png");
	}
}