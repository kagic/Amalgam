package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderStevonnie;
import mod.amalgam.human.EntityStevonnie;
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
public class LayerStevonnieItem implements LayerRenderer<EntityStevonnie> {
	protected final RenderStevonnie renderer;
	public LayerStevonnieItem(RenderStevonnie renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityStevonnie stevonnie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		boolean flag = stevonnie.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack mainhand = flag ? stevonnie.getHeldItemOffhand() : stevonnie.getHeldItemMainhand();
		ItemStack offhand = flag ? stevonnie.getHeldItemMainhand() : stevonnie.getHeldItemOffhand();
		GlStateManager.pushMatrix();
		this.renderHeldItem(stevonnie, offhand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
		this.renderHeldItem(stevonnie, mainhand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
		this.renderBackItem(stevonnie, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
		GlStateManager.popMatrix();
	}
	private void renderHeldItem(EntityStevonnie entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			if (entity.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}
			this.setSide(handSide);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = handSide == EnumHandSide.LEFT;
			GlStateManager.translate((flag ? -1 : 1) / 6.0F, 0.125F, -0.75F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
			GlStateManager.popMatrix();
		}
	}
	private void renderBackItem(EntityStevonnie entity, ItemCameraTransforms.TransformType camera) {
		if (!entity.getBackStack().isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
			GlStateManager.translate(0.2F, 0.2F, -0.4F);
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