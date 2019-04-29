package mod.amalgam.client.render;

import mod.amalgam.gem.EntityFusedTopaz;
import mod.kagic.client.model.ModelTopaz;
import mod.kagic.client.render.RenderGemBase;
import mod.kagic.client.render.layers.LayerBirthdayHat;
import mod.kagic.client.render.layers.LayerCrossFusionGemPlacement;
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

public class RenderFusedTopaz extends RenderGemBase<EntityFusedTopaz> {
	public RenderFusedTopaz() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTopaz(), 0.25F);
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
		this.addLayer(new LayerCrossFusionGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }
	@Override
	protected void preRenderCallback(EntityFusedTopaz gem, float partialTickTime) {
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		this.shadowSize = 1.25F;
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityFusedTopaz entity) {
		return new ResourceLocation("amalgam:textures/entities/fused_topaz/topaz.png");
	}
}