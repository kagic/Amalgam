package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderTourmaline;
import mod.amalgam.gem.EntityTourmaline;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerTourmalineHalf implements LayerRenderer<EntityTourmaline> {
	private final RenderTourmaline renderer;
	public LayerTourmalineHalf(RenderTourmaline renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityTourmaline gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.renderer.bindTexture(this.getTexture(gem));
		int skin = gem.getLowerColor();
		float red = ((skin & 16711680) >> 16) / 255f;
		float green = ((skin & 65280) >> 8) / 255f;
		float blue = ((skin & 255) >> 0) / 255f;
		GlStateManager.color(red, green, blue);
        this.renderer.getMainModel().render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	public ResourceLocation getTexture(EntityTourmaline gem) {
		return new ResourceLocation("amalgam:textures/entities/wtourmaline/half.png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}