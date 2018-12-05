package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelBubble;
import mod.akrivus.amalgam.client.render.layers.LayerBubbledItem;
import mod.akrivus.amalgam.entity.EntityBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBubble extends RenderLiving<EntityBubble> {
	public RenderBubble() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBubble(), 0.0F);
		this.addLayer(new LayerBubbledItem(this));
	}
	@Override
	public void doRender(EntityBubble entity, double x, double y, double z, float entityYaw, float partialTicks) {
		int color = entity.getColor();
		float r = (float)((color & 16711680) >> 16) / 255f;
        float g = (float)((color & 65280) >> 8) / 255f;
        float b = (float)((color & 255) >> 0) / 255f;
        GlStateManager.enableBlend();
        GlStateManager.color(r, g, b, 0.3F);
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityBubble entity) {
		return new ResourceLocation("amalgam:textures/entities/bubble.png");
	}
}
