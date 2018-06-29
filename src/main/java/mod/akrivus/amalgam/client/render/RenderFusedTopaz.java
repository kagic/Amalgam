package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.render.layers.LayerPyritePlacement;
import mod.akrivus.amalgam.client.render.layers.LayerTopazGems;
import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import mod.akrivus.kagic.client.model.ModelTopaz;
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

public class RenderFusedTopaz extends RenderGemBase<EntityFusedTopaz> {
	public RenderFusedTopaz() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTopaz(), 0.25F);
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerTopazGems(this));
    }
	protected void preRenderCallback(EntityFusedTopaz gem, float partialTickTime) {
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
	}
	protected ResourceLocation getEntityTexture(EntityFusedTopaz entity) {
		return new ResourceLocation("amalgam:textures/entities/fused_topaz/topaz.png");
	}
}