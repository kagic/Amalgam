package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.client.render.RenderTourmaline;
import mod.akrivus.amalgam.gem.EntityTourmaline;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerLowerHalf implements LayerRenderer<EntityTourmaline> {
	private final RenderTourmaline gemRenderer;
	private final ModelBase gemModel;
	private float offset;
	private String name;
	
	public LayerLowerHalf(RenderTourmaline gemRenderer) {
		this(gemRenderer, 0F);
	}

	public LayerLowerHalf(RenderTourmaline gemRenderer, float offset) {
		this(gemRenderer, offset, null);
	}
	
	public LayerLowerHalf(RenderTourmaline gemRenderer, float offset, String name) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.offset = offset;
		this.name = name;
	}

	@Override
	public void doRenderLayer(EntityTourmaline gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		int skin = gem.getLowerColor();
		float r = ((skin & 16711680) >> 16) / 255f;
		float g = ((skin & 65280) >> 8) / 255f;
		float b = ((skin & 255) >> 0) / 255f;
		GlStateManager.color(r+ this.offset, g + this.offset, b + this.offset, 1f);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}

	public ResourceLocation getTexture(EntityTourmaline gem) {
		return new ResourceLocation("amalgam:textures/entities/watermelon_tourmaline/half.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}