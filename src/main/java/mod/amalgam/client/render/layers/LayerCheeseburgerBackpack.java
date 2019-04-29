package mod.amalgam.client.render.layers;

import mod.amalgam.human.EntitySteven;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerCheeseburgerBackpack implements LayerRenderer<EntitySteven> {
	private final RenderLivingBase<?> renderer;
	public LayerCheeseburgerBackpack(RenderLivingBase<?> renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntitySteven gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!gem.isInvisible() && gem.isBackpacked()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			this.renderer.bindTexture(this.getTexture(gem));
	        this.renderer.getMainModel().render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
		}
	}
	public ResourceLocation getTexture(EntitySteven gem) {
		return new ResourceLocation("amalgam:textures/entities/steven/backpack.png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}