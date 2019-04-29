package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderCitrine;
import mod.amalgam.gem.EntityCitrine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerAmetrineHalf implements LayerRenderer<EntityCitrine> {
	private final RenderCitrine renderer;
	public LayerAmetrineHalf(RenderCitrine renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityCitrine gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (gem.isDefective()) {
			this.renderer.bindTexture(this.getTexture(gem));
			int skin = gem.getDefectiveColor();
			float red = ((skin & 16711680) >> 16) / 255f;
			float green = ((skin & 65280) >> 8) / 255f;
			float blue = ((skin & 255) >> 0) / 255f;
			GlStateManager.color(red, green, blue);
	        this.renderer.getMainModel().render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
		}
	}
	public ResourceLocation getTexture(EntityCitrine gem) {
		return new ResourceLocation("amalgam:textures/entities/citrine/half.png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}