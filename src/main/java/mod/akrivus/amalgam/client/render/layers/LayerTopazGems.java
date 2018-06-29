package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerTopazGems implements LayerRenderer<EntityFusedTopaz> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	public LayerTopazGems(RenderLivingBase<?> gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	public void doRenderLayer(EntityFusedTopaz gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		EntityGem gem1 = gem.gem1;
		this.gemRenderer.bindTexture(this.getTexture(gem1));
		int color1 = gem1.getGemColor();
		float r1 = (float) ((color1 & 16711680) >> 16) / 255f;
        float g1 = (float) ((color1 & 65280) >> 8) / 255f;
        float b1 = (float) ((color1 & 255) >> 0) / 255f;
		GlStateManager.color(r1, g1, b1);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.gemRenderer.bindTexture(this.getTexture(gem1));
        EntityGem gem2 = gem.gem2;
		int color2 = gem2.getGemColor();
		float r2 = (float) ((color2 & 16711680) >> 16) / 255f;
        float g2 = (float) ((color2 & 65280) >> 8) / 255f;
        float b2 = (float) ((color2 & 255) >> 0) / 255f;
		GlStateManager.color(r2, g2, b2);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	public ResourceLocation getTexture(EntityGem gem) {
		return new ResourceLocation("amalgam:textures/entities/fused_topaz/gems/" + gem.getGemPlacement().id + "_" + gem.getGemCut().id + ".png");
	}
	public boolean shouldCombineTextures() {
		return true;
	}
}