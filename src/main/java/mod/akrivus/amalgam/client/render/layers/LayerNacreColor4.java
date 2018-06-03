package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.client.render.RenderNacre;
import mod.akrivus.amalgam.gem.EntityNacre;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerNacreColor4 implements LayerRenderer<EntityNacre> {
	private final RenderNacre gemRenderer;
	private final ModelBase gemModel;
	private float offset;
	private String name;
	
	public LayerNacreColor4(RenderNacre gemRenderer) {
		this(gemRenderer, 0F);
	}

	public LayerNacreColor4(RenderNacre gemRenderer, float offset) {
		this(gemRenderer, offset, null);
	}
	
	public LayerNacreColor4(RenderNacre gemRenderer, float offset, String name) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.offset = offset;
		this.name = name;
	}

	@Override
	public void doRenderLayer(EntityNacre gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		int skin = gem.getLayerColor(gem.getColor4());
		float r = (float) ((skin & 16711680) >> 16) / 255f;
		float g = (float) ((skin & 65280) >> 8) / 255f;
		float b = (float) ((skin & 255) >> 0) / 255f;
		//KAGIC.instance.chatInfoMessage("Skin color is " + r + " , " + g + " , " + b);
		GlStateManager.color(r+ this.offset, g + this.offset, b + this.offset, 1f);
		//GlStateManager.enableBlend();
		//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}

	public ResourceLocation getTexture(EntityNacre gem) {
		return new ResourceLocation("amalgam:textures/entities/nacre/color_4.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}