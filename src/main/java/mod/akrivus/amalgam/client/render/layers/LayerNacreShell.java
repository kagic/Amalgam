package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.client.render.RenderNacre;
import mod.akrivus.amalgam.gem.EntityNacre;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerNacreShell implements LayerRenderer<EntityNacre> {
	private final RenderNacre gemRenderer;
	private final ModelBase gemModel;
	
	public LayerNacreShell(RenderNacre gemRenderer) {
		this(gemRenderer, 0F);
	}

	public LayerNacreShell(RenderNacre gemRenderer, float offset) {
		this(gemRenderer, offset, null);
	}
	
	public LayerNacreShell(RenderNacre gemRenderer, float offset, String name) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityNacre gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}

	public ResourceLocation getTexture(EntityNacre gem) {
		return new ResourceLocation("amalgam:textures/entities/nacre/shell_normal.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}