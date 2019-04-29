package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderNacre;
import mod.amalgam.gem.EntityNacre;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerNacreColor1 implements LayerRenderer<EntityNacre> {
	private final RenderNacre renderer;
	public LayerNacreColor1(RenderNacre renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityNacre nacre, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.renderer.bindTexture(this.getTexture(nacre));
		int skin = nacre.getLayerColor(nacre.getColor1());
		float red = ((skin & 16711680) >> 16) / 255f;
		float green = ((skin & 65280) >> 8) / 255f;
		float blue = ((skin & 255) >> 0) / 255f;
		GlStateManager.color(red, green, blue);
        this.renderer.getMainModel().render(nacre, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	public ResourceLocation getTexture(EntityNacre nacre) {
		return new ResourceLocation("amalgam:textures/entities/nacre/color_1.png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}