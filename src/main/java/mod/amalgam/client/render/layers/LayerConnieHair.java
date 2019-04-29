package mod.amalgam.client.render.layers;

import mod.amalgam.human.EntityConnie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerConnieHair implements LayerRenderer<EntityConnie> {
	private final RenderLivingBase<?> renderer;
	public LayerConnieHair(RenderLivingBase<?> renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityConnie connie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!connie.isInvisible()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			this.renderer.bindTexture(this.getTexture(connie));
	        this.renderer.getMainModel().render(connie, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
		}
	}
	public ResourceLocation getTexture(EntityConnie connie) {
		return new ResourceLocation("amalgam:textures/entities/connie/hair_" + connie.getHairstyle() + ".png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}