package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.client.render.RenderNacre;
import mod.akrivus.amalgam.gem.EntityNacre;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerNacreColor2 implements LayerRenderer<EntityNacre> {
	private final RenderNacre gemRenderer;
	private final ModelBase gemModel;
	private float offset;
	public LayerNacreColor2(RenderNacre gemRenderer) {
		this(gemRenderer, 0F);
	}
	public LayerNacreColor2(RenderNacre gemRenderer, float offset) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.offset = offset;
	}
	@Override
	public void doRenderLayer(EntityNacre gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		int skin = gem.getLayerColor(gem.getColor2());
		float r = ((skin & 16711680) >> 16) / 255f;
		float g = ((skin & 65280) >> 8) / 255f;
		float b = ((skin & 255) >> 0) / 255f;
		GlStateManager.color(r + this.offset, g + this.offset, b + this.offset, 1f);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	public ResourceLocation getTexture(EntityNacre gem) {
		return new ResourceLocation("amalgam:textures/entities/nacre/color_2.png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}