package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderBubble;
import mod.amalgam.entity.EntityBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerBubbledItem implements LayerRenderer<EntityBubble> {
	public final RenderBubble renderer;
	public LayerBubbledItem(RenderBubble renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityBubble bubble, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		ItemStack stack = bubble.getItem();
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			if (!stack.isEmpty()) {
				GlStateManager.pushMatrix();
				GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(ageInTicks, 0.0F, 1.0F, 0.0F);
		        GlStateManager.scale(0.6, 0.6, 0.6);
				GlStateManager.translate(0F, -2.25F, 0F);
				Minecraft.getMinecraft().getItemRenderer().renderItemSide(bubble, stack, ItemCameraTransforms.TransformType.GROUND, true);
				GlStateManager.popMatrix();
			}
			GlStateManager.popMatrix();
		}
		int color = bubble.getColor();
		float r = ((color & 16711680) >> 16) / 255f;
        float g = ((color & 65280) >> 8) / 255f;
        float b = ((color & 255) >> 0) / 255f;
        GlStateManager.enableBlend();
        GlStateManager.color(r, g, b, 0.3F);
        this.renderer.bindTexture(new ResourceLocation("amalgam:textures/entities/bubble.png"));
        this.renderer.getMainModel().render(bubble, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}