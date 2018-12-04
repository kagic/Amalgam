package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.client.render.RenderAquaAuraQuartz;
import mod.akrivus.amalgam.gem.EntityAquaAuraQuartz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerAura implements LayerRenderer<EntityAquaAuraQuartz> {
	private final RenderAquaAuraQuartz gemRenderer;
	private final ModelBase gemModel;
	public LayerAura(RenderAquaAuraQuartz gemRenderer) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
	}
	@Override
	public void doRenderLayer(EntityAquaAuraQuartz gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		float mult = (float)(gem.ticksExisted + partialTicks);
		this.gemRenderer.bindTexture(new ResourceLocation("textures/misc/enchanted_item_glint.png"));
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            GlStateManager.color(0.67F, 0.54F, 0.717F, 0.25F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.scale(0.33333334F, 0.33333334F, 0.33333334F);
            GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, mult * (0.001F + (i) * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}