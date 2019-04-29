package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderConnie;
import mod.amalgam.human.EntityConnie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerConnieItem implements LayerRenderer<EntityConnie> {
	protected final RenderConnie renderer;
	public LayerConnieItem(RenderConnie renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityConnie connie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		boolean flag = connie.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack offhand = flag ? connie.getHeldItemOffhand() : connie.getHeldItemMainhand();
		ItemStack mainhand = flag ? connie.getHeldItemMainhand() : connie.getHeldItemOffhand();
		GlStateManager.pushMatrix();
		this.renderHeldItem(connie, mainhand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
		this.renderHeldItem(connie, offhand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
		this.renderBackItem(connie, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
		GlStateManager.popMatrix();
	}
	private void renderHeldItem(EntityConnie entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			if (entity.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}
			this.setSide(handSide);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = handSide == EnumHandSide.LEFT;
			GlStateManager.translate((flag ? -1 : 1) / 24.0F, 0.125F, -0.75F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
			GlStateManager.popMatrix();
		}
	}
	private void renderBackItem(EntityConnie entity, ItemCameraTransforms.TransformType camera) {
		if (!entity.getBackStack().isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
			GlStateManager.translate(0.2F, 0.4F, -0.4F);
			Minecraft.getMinecraft().getItemRenderer().renderItem(entity, entity.getBackStack(), camera);
			GlStateManager.popMatrix();
		}
	}
	protected void setSide(EnumHandSide side) {
		((ModelBiped)(this.renderer.getMainModel())).postRenderArm(0.04F, side);
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}