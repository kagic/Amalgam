package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderNacre;
import mod.amalgam.gem.EntityNacre;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerNacreShell implements LayerRenderer<EntityNacre> {
	private final RenderNacre renderer;
	public LayerNacreShell(RenderNacre renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityNacre gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.renderer.bindTexture(this.getTexture(gem));
		GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.renderer.getMainModel().render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	public ResourceLocation getTexture(EntityNacre gem) {
		if (gem.isCracked()) {
			return new ResourceLocation("amalgam:textures/entities/nacre/shell_cracked.png");
		}
		return new ResourceLocation("amalgam:textures/entities/nacre/shell_normal.png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}